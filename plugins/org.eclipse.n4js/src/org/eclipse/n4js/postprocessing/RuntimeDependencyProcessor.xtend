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
package org.eclipse.n4js.postprocessing

import com.google.common.collect.Iterators
import com.google.inject.Inject
import com.google.inject.Singleton
import java.util.ArrayList
import java.util.LinkedHashSet
import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.n4js.n4JS.IdentifierRef
import org.eclipse.n4js.n4JS.ImportDeclaration
import org.eclipse.n4js.n4JS.ImportSpecifier
import org.eclipse.n4js.n4JS.N4ClassifierDeclaration
import org.eclipse.n4js.n4JS.N4JSASTUtils
import org.eclipse.n4js.n4JS.NamedImportSpecifier
import org.eclipse.n4js.n4JS.NamespaceImportSpecifier
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.smith.N4JSDataCollectors
import org.eclipse.n4js.ts.types.ModuleNamespaceVirtualType
import org.eclipse.n4js.ts.types.RuntimeDependency
import org.eclipse.n4js.ts.types.TModule
import org.eclipse.n4js.ts.types.TypesFactory
import org.eclipse.n4js.utils.EcoreUtilN4
import org.eclipse.n4js.utils.N4JSLanguageUtils
import org.eclipse.n4js.utils.SCCUtils
import org.eclipse.n4js.validation.JavaScriptVariantHelper
import org.eclipse.xtext.EcoreUtil2

/**
 * Processor for computing direct load-time dependencies and import retention (during AST traversal)
 * as well as load-time dependency cycles (during finalization of post-processing).
 */
@Singleton
class RuntimeDependencyProcessor {

	@Inject
	private JavaScriptVariantHelper variantHelper;

	/**
	 * Invoked during AST traversal (and thus during main post-processing).
	 */
	def package recordRuntimeReferencesInCache(EObject node, ASTMetaInfoCache cache) {
		if (node instanceof IdentifierRef) {
			val target = node.targetElement;
			if (N4JSLanguageUtils.hasRuntimeRepresentation(target, variantHelper)) {
				cache.elementsReferencedAtRuntime += target;
				// in case of namespace imports, we also want to remember that the namespace was referenced at run time:
				val targetRaw = node.id;
				if (targetRaw !== target && targetRaw instanceof ModuleNamespaceVirtualType) {
					cache.elementsReferencedAtRuntime += targetRaw;
				}
			}
		} else if (node instanceof N4ClassifierDeclaration) {
			if (N4JSLanguageUtils.hasRuntimeRepresentation(node, variantHelper)) {
				val targetTypeRefs = node.superClassifierRefs;
				for (targetTypeRef : targetTypeRefs) {
					val targetDeclType = targetTypeRef?.declaredType;
					if (N4JSLanguageUtils.hasRuntimeRepresentation(targetDeclType, variantHelper)) {
						cache.elementsReferencedAtRuntime += targetDeclType;
						// in case of namespace imports, we also want to remember that the namespace was referenced at run time:
						val namespace = targetTypeRef.astNamespace;
						if (namespace !== null) {
							cache.elementsReferencedAtRuntime += namespace;
						}
						// remember that the target's containing module was referenced from an extends/implements clause:
						val targetModule = if (!targetDeclType.eIsProxy) EcoreUtil2.getContainerOfType(targetDeclType, TModule);
						if (isDifferentModuleInSameProject(targetModule, cache)) {
							if (N4JSASTUtils.isTopLevelCode(node)) { // nested classifiers not supported yet, but let's be future proof
								cache.modulesReferencedAtLoadtimeForInheritance += targetModule;
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
	def package void storeDirectRuntimeDependenciesInTModule(Script script, ASTMetaInfoCache cache) {
		val m = N4JSDataCollectors.dcRuntimeDepsCollect.getMeasurement();
		try {
			doStoreDirectRuntimeDependenciesInTModule(script, cache)
		} finally {
			m.close();
		}
	}

	def private void doStoreDirectRuntimeDependenciesInTModule(Script script, ASTMetaInfoCache cache) {
		val module = script.module;
		val importDecls = script.scriptElements.filter(ImportDeclaration).toList;

		// step 1: set runtime retention flag in import specifiers
		for (importDecl : importDecls) {
			for (importSpec : importDecl.importSpecifiers) {
				if (importSpec !== null) {
					val element = switch(importSpec) {
						NamedImportSpecifier: importSpec.importedElement
						NamespaceImportSpecifier: importSpec.definedType
						default: throw new IllegalStateException("unknown subclass of ImportSpecifier: " + importSpec.eClass.name)
					};
					val retained = cache.elementsReferencedAtRuntime.contains(element);
					EcoreUtilN4.doWithDeliver(false, [
						importSpec.retainedAtRuntime = retained;
					], importSpec);
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
		val modulesReferencedAtRuntime = newLinkedHashSet;
		for (importDecl : importDecls) {
			if (importDecl.retainedAtRuntime) { // note: will also be true for bare imports
				val targetModule = importDecl.module;
				if (isDifferentModuleInSameProject(targetModule, cache)) {
					modulesReferencedAtRuntime += targetModule;
				}
			}
		}
		val dependenciesRuntime = new ArrayList<RuntimeDependency>(modulesReferencedAtRuntime.size);
		for (currModule : modulesReferencedAtRuntime) {
			val currDep = TypesFactory.eINSTANCE.createRuntimeDependency();
			currDep.target = currModule;
			currDep.loadtimeForInheritance = cache.modulesReferencedAtLoadtimeForInheritance.contains(currModule);
			dependenciesRuntime += currDep;
		}

		// step 3: store direct runtime dependencies in TModule
		EcoreUtilN4.doWithDeliver(false, [
			module.dependenciesRuntime.clear();
			module.dependenciesRuntime += dependenciesRuntime;
		], module);
	}

	/**
	 * Invoked during the finalization of post-processing (and thus, after the main post-processing of all
	 * directly or indirectly required resources has finished).
	 */
	def package void computeAndStoreRuntimeCyclesInTModule(TModule module) {
		val m = N4JSDataCollectors.dcRuntimeDepsFindCycles.getMeasurement();
		try {
			doComputeAndStoreRuntimeCyclesInTModule(module);
		} finally {
			m.close();
		}
	}

	def private void doComputeAndStoreRuntimeCyclesInTModule(TModule module) {
		// NOTE: as above, the order of cyclicModulesRuntime and runtimeCyclicLoadtimeDependents stored in
		// the TModule is important, because we want to avoid random reordering of the same set of modules
		// to avoid unnecessary changes of the TModule (see above for details)
		val cyclicModulesRuntime = getAllRuntimeCyclicModules(module, false);
		val cyclicModulesLoadtimeForInheritance = getAllRuntimeCyclicModules(module, true);
		val runtimeCyclicLoadtimeDependents = new LinkedHashSet<TModule>();
		for (cyclicModule : cyclicModulesRuntime) {
			if (cyclicModule.hasDirectLoadtimeDependencyTo(module)) {
				runtimeCyclicLoadtimeDependents.add(cyclicModule);
			}
		}

		EcoreUtilN4.doWithDeliver(false, [
			module.cyclicModulesRuntime.clear();
			module.cyclicModulesLoadtimeForInheritance.clear();
			module.runtimeCyclicLoadtimeDependents.clear();
			module.cyclicModulesRuntime += cyclicModulesRuntime;
			module.cyclicModulesLoadtimeForInheritance += cyclicModulesLoadtimeForInheritance;
			module.runtimeCyclicLoadtimeDependents += runtimeCyclicLoadtimeDependents;
		], module);
	}

	def private static List<TModule> getAllRuntimeCyclicModules(TModule module, boolean onlyLoadtime) {
		// Note on performance: at this point we cannot search *all* strongly connected components in the
		// graph, which would be more time efficient, because we are operating on a potentially (and usually)
		// incomplete runtime dependency graph during the build. The only thing we can rely on is that the
		// direct runtime dependencies of modules in our containing strongly connected component are up to
		// date. Therefore, we are here only searching the SCC of 'module' (i.e. pass only 'module' as first
		// argument to SCCUtils#findSCCs()) and do not make use of a more sophisticated algorithm for finding
		// all SCCs such as Johnson's algorithm (cf. Johnson, SIAM Journal on Computing, Vol. 4, No. 1, 1975).
		val sccs = SCCUtils.findSCCs(Iterators.singletonIterator(module), [m|
			m.dependenciesRuntime.filter[!onlyLoadtime || isLoadtimeForInheritance].map[target]
		]);
		val cyclicModules = sccs.filter[remove(module)].head;
		return cyclicModules;
	}

	def private boolean hasDirectLoadtimeDependencyTo(TModule from, TModule to) {
		return from.dependenciesRuntime.exists[isLoadtimeForInheritance && target === to];
	}

	def private static boolean isDifferentModuleInSameProject(TModule module, ASTMetaInfoCache cache) {
		return module !== null && !module.eIsProxy
			&& module.eResource !== cache.resource
			&& module.projectName == cache.projectName;
	}
}
