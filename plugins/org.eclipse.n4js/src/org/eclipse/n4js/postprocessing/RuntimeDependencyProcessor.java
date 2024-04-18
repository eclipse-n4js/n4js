/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.postprocessing;

import static com.google.common.collect.Iterators.singletonIterator;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.exists;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.head;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.ImportDeclaration;
import org.eclipse.n4js.n4JS.ImportSpecifier;
import org.eclipse.n4js.n4JS.ModuleRef;
import org.eclipse.n4js.n4JS.N4ClassifierDeclaration;
import org.eclipse.n4js.n4JS.N4JSASTUtils;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.NamedImportSpecifier;
import org.eclipse.n4js.n4JS.NamespaceImportSpecifier;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.TypeReferenceNode;
import org.eclipse.n4js.smith.Measurement;
import org.eclipse.n4js.smith.N4JSDataCollectors;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.ModuleNamespaceVirtualType;
import org.eclipse.n4js.ts.types.RuntimeDependency;
import org.eclipse.n4js.ts.types.TExportableElement;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.TNamespace;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypesFactory;
import org.eclipse.n4js.utils.EcoreUtilN4;
import org.eclipse.n4js.utils.N4JSLanguageUtils;
import org.eclipse.n4js.utils.SCCUtils;
import org.eclipse.xtext.EcoreUtil2;

import com.google.inject.Singleton;

/**
 * Processor for computing direct load-time dependencies and import retention (during AST traversal) as well as
 * load-time dependency cycles (during finalization of post-processing).
 */
@Singleton
@SuppressWarnings("all")
public class RuntimeDependencyProcessor {
	/**
	 * Invoked during AST traversal (and thus during main post-processing).
	 */
	void recordRuntimeReferencesInCache(final EObject node, final ASTMetaInfoCache cache) {
		if (node instanceof IdentifierRef) {
			IdentifierRef idRef = (IdentifierRef) node;
			if (idRef.eContainingFeature() == N4JSPackage.Literals.NAMED_EXPORT_SPECIFIER__EXPORTED_ELEMENT) {
				return; // re-exports are not harmful
			}
			IdentifiableElement target = idRef.getTargetElement();
			if (N4JSLanguageUtils.hasRuntimeRepresentation(target)) {
				cache.elementsReferencedAtRuntime.add(target);
				// in case of namespace imports, we also want to remember that the namespace was referenced at run time:
				IdentifiableElement targetRaw = idRef.getId();
				if (targetRaw != target && targetRaw instanceof ModuleNamespaceVirtualType) {
					cache.elementsReferencedAtRuntime.add(targetRaw);
				}
			}
		} else if (node instanceof N4ClassifierDeclaration) {
			N4ClassifierDeclaration cd = (N4ClassifierDeclaration) node;
			if (N4JSLanguageUtils.hasRuntimeRepresentation(cd)) {
				for (TypeReferenceNode<ParameterizedTypeRef> targetTypeRef : cd.getSuperClassifierRefs()) {
					Type targetDeclType = targetTypeRef == null || targetTypeRef.getTypeRef() == null
							? null
							: targetTypeRef.getTypeRef().getDeclaredType();

					if (N4JSLanguageUtils.hasRuntimeRepresentation(targetDeclType)) {
						cache.elementsReferencedAtRuntime.add(targetDeclType);

						// in case of namespace imports, we also want to remember that the namespace was referenced at
						// run time:
						@SuppressWarnings("null")
						Type namespaceLikeType = targetTypeRef.getTypeRefInAST() == null
								|| targetTypeRef.getTypeRefInAST().getAstNamespaceLikeRefs() == null
								|| head(targetTypeRef.getTypeRefInAST().getAstNamespaceLikeRefs()) == null
										? null
										: head(targetTypeRef.getTypeRefInAST().getAstNamespaceLikeRefs())
												.getDeclaredType();

						if (namespaceLikeType instanceof ModuleNamespaceVirtualType
								|| namespaceLikeType instanceof TNamespace) {
							cache.elementsReferencedAtRuntime.add(namespaceLikeType);
						}
						// remember that the target's containing module was referenced from an
						// extends/implements clause:
						@SuppressWarnings("null")
						TModule targetModule = (!targetDeclType.eIsProxy())
								? EcoreUtil2.getContainerOfType(targetDeclType, TModule.class)
								: null;
						if (isDifferentModuleInSameProject(targetModule, cache)) {
							if (N4JSASTUtils.isTopLevelCode(node)) {
								// nested classifiers not supported yet, but let's be future proof
								cache.modulesReferencedAtLoadtimeForInheritance.add(targetModule);
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Invoked at the end of AST traversal (and thus, during main post-processing).
	 * <p>
	 * Also sets the flag {@link ImportSpecifier#getRetainedAtRuntime() retainedAtRuntime}.
	 */
	void storeDirectRuntimeDependenciesInTModule(Script script, ASTMetaInfoCache cache) {
		try (Measurement m = N4JSDataCollectors.dcRuntimeDepsCollect.getMeasurement()) {
			doStoreDirectRuntimeDependenciesInTModule(script, cache);
		}
	}

	private void doStoreDirectRuntimeDependenciesInTModule(Script script, ASTMetaInfoCache cache) {
		TModule module = script.getModule();

		// step 1: set runtime retention flag in import specifiers
		List<ImportDeclaration> importDecls = toList(filter(script.getScriptElements(), ImportDeclaration.class));
		for (ImportDeclaration importDecl : importDecls) {
			for (ImportSpecifier importSpec : importDecl.getImportSpecifiers()) {
				if (importSpec != null) {
					TExportableElement element;
					if (importSpec instanceof NamedImportSpecifier) {
						element = ((NamedImportSpecifier) importSpec).getImportedElement();
					} else if (importSpec instanceof NamespaceImportSpecifier) {
						element = ((NamespaceImportSpecifier) importSpec).getDefinedType();
					} else {
						throw new IllegalStateException(
								"unknown subclass of ImportSpecifier: " + importSpec.eClass().getName());
					}
					boolean retained = cache.elementsReferencedAtRuntime.contains(element);
					EcoreUtilN4.doWithDeliver(false, () -> importSpec.setRetainedAtRuntime(retained), importSpec);
				}
			}
		}

		// step 2: derive direct runtime dependencies from runtime retention of imports
		//
		// NOTE: order of the dependencies is important, here, because a change in the TModule has
		// significant implications (e.g. dependent modules will be rebuilt, see #isAffected() in
		// incremental builder); so we want to avoid a random reordering of the same dependencies and
		// therefore use a defined order derived from the order of import declarations in the AST.
		//
		Set<TModule> modulesReferencedAtRuntime = new LinkedHashSet<>();
		List<ModuleRef> refsToOtherModules = toList(
				filter(filter(script.getScriptElements(), ModuleRef.class), mr -> mr.isReferringToOtherModule()));
		for (ModuleRef moduleRef : refsToOtherModules) {
			if (moduleRef.isRetainedAtRuntime()) { // note: will also be true for bare imports and for all re-exports
				TModule targetModule = moduleRef.getModule();
				if (isDifferentModuleInSameProject(targetModule, cache)) {
					modulesReferencedAtRuntime.add(targetModule);
				}
			}
		}
		List<RuntimeDependency> dependenciesRuntime = new ArrayList<>(modulesReferencedAtRuntime.size());
		for (TModule currModule : modulesReferencedAtRuntime) {
			RuntimeDependency currDep = TypesFactory.eINSTANCE.createRuntimeDependency();
			currDep.setTarget(currModule);
			currDep.setLoadtimeForInheritance(cache.modulesReferencedAtLoadtimeForInheritance.contains(currModule));
			dependenciesRuntime.add(currDep);
		}

		// step 3: store direct runtime dependencies in TModule
		if (module != null) {
			EcoreUtilN4.doWithDeliver(false, () -> {
				module.getDependenciesRuntime().clear();
				module.getDependenciesRuntime().addAll(dependenciesRuntime);
			}, module);
		}
	}

	/**
	 * Invoked during the finalization of post-processing (and thus, after the main post-processing of all directly or
	 * indirectly required resources has finished).
	 */
	void computeAndStoreRuntimeCyclesInTModule(TModule module) {
		try (Measurement m = N4JSDataCollectors.dcRuntimeDepsFindCycles.getMeasurement()) {
			doComputeAndStoreRuntimeCyclesInTModule(module);
		}
	}

	private void doComputeAndStoreRuntimeCyclesInTModule(TModule module) {
		// NOTE: as above, the order of cyclicModulesRuntime and runtimeCyclicLoadtimeDependents stored in
		// the TModule is important, because we want to avoid random reordering of the same set of modules
		// to avoid unnecessary changes of the TModule (see above for details)
		List<TModule> cyclicModulesRuntime = getAllRuntimeCyclicModules(module, false);
		List<TModule> cyclicModulesLoadtimeForInheritance = getAllRuntimeCyclicModules(module, true);
		Set<TModule> runtimeCyclicLoadtimeDependents = new LinkedHashSet<>();
		for (TModule cyclicModule : cyclicModulesRuntime) {
			if (hasDirectLoadtimeDependencyTo(cyclicModule, module)) {
				runtimeCyclicLoadtimeDependents.add(cyclicModule);
			}
		}

		EcoreUtilN4.doWithDeliver(false, () -> {
			module.getCyclicModulesRuntime().clear();
			module.getCyclicModulesLoadtimeForInheritance().clear();
			module.getRuntimeCyclicLoadtimeDependents().clear();
			module.getCyclicModulesRuntime().addAll(cyclicModulesRuntime);
			module.getCyclicModulesLoadtimeForInheritance().addAll(cyclicModulesLoadtimeForInheritance);
			module.getRuntimeCyclicLoadtimeDependents().addAll(runtimeCyclicLoadtimeDependents);
		}, module);
	}

	private static List<TModule> getAllRuntimeCyclicModules(TModule module, boolean onlyLoadtime) {
		// Note on performance: at this point we cannot search *all* strongly connected components in the
		// graph, which would be more time efficient, because we are operating on a potentially (and usually)
		// incomplete runtime dependency graph during the build. The only thing we can rely on is that the
		// direct runtime dependencies of modules in our containing strongly connected component are up to
		// date. Therefore, we are here only searching the SCC of 'module' (i.e. pass only 'module' as first
		// argument to SCCUtils#findSCCs()) and do not make use of a more sophisticated algorithm for finding
		// all SCCs such as Johnson's algorithm (cf. Johnson, SIAM Journal on Computing, Vol. 4, No. 1, 1975).

		List<List<TModule>> sccs = SCCUtils.findSCCs(
				singletonIterator(module),
				m -> map(filter(m.getDependenciesRuntime(), dr -> !onlyLoadtime || dr.isLoadtimeForInheritance()),
						dr -> dr.getTarget()));

		List<TModule> cyclicModules = head(filter(sccs, l -> l.remove(module)));
		return cyclicModules;
	}

	private boolean hasDirectLoadtimeDependencyTo(TModule from, TModule to) {
		return exists(from.getDependenciesRuntime(), rd -> rd.isLoadtimeForInheritance() && rd.getTarget() == to);
	}

	private static boolean isDifferentModuleInSameProject(TModule module, ASTMetaInfoCache cache) {
		return module != null && !module.eIsProxy()
				&& module.eResource() != cache.getResource()
				&& java.util.Objects.equals(module.getProjectID(), cache.getProjectID());
	}
}
