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

import com.google.common.collect.Sets
import com.google.inject.Inject
import com.google.inject.Singleton
import java.util.ArrayList
import java.util.Collection
import java.util.LinkedHashSet
import java.util.Set
import org.eclipse.emf.ecore.EObject
import org.eclipse.n4js.n4JS.IdentifierRef
import org.eclipse.n4js.n4JS.ImportDeclaration
import org.eclipse.n4js.n4JS.ImportSpecifier
import org.eclipse.n4js.n4JS.N4ClassifierDeclaration
import org.eclipse.n4js.n4JS.N4JSASTUtils
import org.eclipse.n4js.n4JS.NamedImportSpecifier
import org.eclipse.n4js.n4JS.NamespaceImportSpecifier
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.ts.types.ModuleNamespaceVirtualType
import org.eclipse.n4js.ts.types.RunTimeDependency
import org.eclipse.n4js.ts.types.TModule
import org.eclipse.n4js.ts.types.TypesFactory
import org.eclipse.n4js.utils.EcoreUtilN4
import org.eclipse.n4js.utils.N4JSLanguageUtils
import org.eclipse.n4js.validation.JavaScriptVariantHelper
import org.eclipse.xtext.EcoreUtil2

/**
 * Processor for computing direct load-time dependencies and import retention (during AST traversal)
 * as well as load-time dependency cycles (during finalization of post-processing).
 */
@Singleton
class RunTimeDependencyProcessor {

	@Inject
	private JavaScriptVariantHelper variantHelper;

	/**
	 * Invoked during AST traversal (and thus during main post-processing).
	 */
	def package recordRunTimeReferencesInCache(EObject node, ASTMetaInfoCache cache) {
		if (node instanceof IdentifierRef) {
			val target = node.targetElement;
			if (N4JSLanguageUtils.hasRunTimeRepresentation(target, variantHelper)) {
				cache.elementsReferencedAtRunTime += target;
				// in case of namespace imports, we also want to remember that the namespace was referenced at run time:
				val targetRaw = node.id;
				if (targetRaw !== target && targetRaw instanceof ModuleNamespaceVirtualType) {
					cache.elementsReferencedAtRunTime += targetRaw;
				}
			}
		} else if (node instanceof N4ClassifierDeclaration) {
			if (N4JSLanguageUtils.hasRunTimeRepresentation(node, variantHelper)) {
				val targetTypeRefs = node.superClassifierRefs;
				for (targetTypeRef : targetTypeRefs) {
					val targetDeclType = targetTypeRef?.declaredType;
					cache.elementsReferencedAtRunTime += targetDeclType;
					val targetModule = if (targetDeclType !== null && !targetDeclType.eIsProxy) EcoreUtil2.getContainerOfType(targetDeclType, TModule);
					if (isDifferentModuleInSameProject(targetModule, cache)) {
						if (N4JSASTUtils.isTopLevelCode(node)) { // nested classifiers not supported yet, but let's be future proof
							cache.modulesReferencedAtLoadTimeForInheritance += targetModule;
						}
					}
				}
			}
		}
	}

	/**
	 * Invoked at the end of AST traversal (and thus, during main post-processing).
	 * <p>
	 * Also sets the flag {@link ImportSpecifier#getRetainedAtRunTime() retainedAtRunTime}.
	 */
	def package void storeDirectRunTimeDependenciesInTModule(Script script, ASTMetaInfoCache cache) {
		val module = script.module;
		val importDecls = script.scriptElements.filter(ImportDeclaration).toList;

		// step 1: set run-time retention flag in import specifiers
		for (importDecl : importDecls) {
			for (importSpec : importDecl.importSpecifiers) {
				if (importSpec !== null) {
					val element = switch(importSpec) {
						NamedImportSpecifier: importSpec.importedElement
						NamespaceImportSpecifier: importSpec.definedType
						default: throw new IllegalStateException("unknown subclass of ImportSpecifier: " + importSpec.eClass.name)
					};
					val retained = cache.elementsReferencedAtRunTime.contains(element);
					EcoreUtilN4.doWithDeliver(false, [
						importSpec.retainedAtRunTime = retained;
					], importSpec);
				}
			}
		}

		// step 2: derive direct run-time dependencies from run-time retention of imports
		//
		// NOTE: order of the dependencies is important, here, because a change in the TModule has
		// significant implications (e.g. dependent modules will be rebuilt, see #isAffected() in
		// incremental builder); so we want to avoid a random reordering of the same dependencies and
		// therefore use a defined order derived from the order of import declarations in the AST.
		//
		val modulesReferencedAtRunTime = newLinkedHashSet;
		for (importDecl : importDecls) {
			if (importDecl.retainedAtRunTime) { // note: will also be true for bare imports
				val targetModule = importDecl.module;
				if (isDifferentModuleInSameProject(targetModule, cache)) {
					modulesReferencedAtRunTime += targetModule;
				}
			}
		}
		val dependenciesRunTime = new ArrayList<RunTimeDependency>(modulesReferencedAtRunTime.size);
		for (currModule : modulesReferencedAtRunTime) {
			val currDep = TypesFactory.eINSTANCE.createRunTimeDependency();
			currDep.target = currModule;
			currDep.loadTimeForInheritance = cache.modulesReferencedAtLoadTimeForInheritance.contains(currModule);
			dependenciesRunTime += currDep;
		}

		// step 3: store direct run-time dependencies in TModule
		EcoreUtilN4.doWithDeliver(false, [
			module.dependenciesRunTime.clear();
			module.dependenciesRunTime += dependenciesRunTime;
		], module);
	}

	/**
	 * Invoked during the finalization of post-processing (and thus, after the main post-processing of all
	 * directly or indirectly required resources has finished).
	 */
	def package void computeAndStoreRunTimeCyclesInTModule(TModule module) {
		// NOTE: as above, the order of cyclicModulesRunTime and runTimeCyclicLoadTimeDependents stored in
		// the TModule is important, because we want to avoid random reordering of the same set of modules
		// to avoid unnecessary changes of the TModule (see above for details)
		val cyclicModulesRunTime = <TModule>newLinkedHashSet();
		val cyclicModulesLoadTimeForInheritance = <TModule>newLinkedHashSet();
		getAllRunTimeCyclicModules(module, false, cyclicModulesRunTime);
		getAllRunTimeCyclicModules(module, true, cyclicModulesLoadTimeForInheritance);
		val runTimeCyclicLoadTimeDependents = new LinkedHashSet<TModule>();
		for (cyclicModule : cyclicModulesRunTime) {
			if (cyclicModule.hasDirectLoadTimeDependencyTo(module)) {
				runTimeCyclicLoadTimeDependents.add(cyclicModule);
			}
		}

		EcoreUtilN4.doWithDeliver(false, [
			module.cyclicModulesRunTime.clear();
			module.cyclicModulesLoadTimeForInheritance.clear();
			module.runTimeCyclicLoadTimeDependents.clear();
			module.cyclicModulesRunTime += cyclicModulesRunTime;
			module.cyclicModulesLoadTimeForInheritance += cyclicModulesLoadTimeForInheritance;
			module.runTimeCyclicLoadTimeDependents += runTimeCyclicLoadTimeDependents;
		], module);
	}

	// FIXME consider optimization
	def private static void getAllRunTimeCyclicModules(TModule module, boolean onlyLoadTime, Set<TModule> addHereRunTime) {
		collectRunTimeCyclicModules(module, onlyLoadTime,
			module.getDependenciesRunTime(), Sets.newLinkedHashSet, Sets.newLinkedHashSet,
			addHereRunTime);
	}

	def private static void collectRunTimeCyclicModules(TModule start, boolean onlyLoadTime,
		Collection<RunTimeDependency> nextDeps, Set<TModule> visited, Set<TModule> currPath,
		Set<TModule> addHere) {

		val nextDepsToUse = if (onlyLoadTime) nextDeps.filter[isLoadTimeForInheritance] else nextDeps;

		for (currDep : nextDepsToUse) {
			val currModule = currDep.target;
			if (currModule === start) {
				addHere.addAll(currPath);
			} else {
				if (visited.add(currModule)) {
					try {
						currPath.add(currModule);
						collectRunTimeCyclicModules(start, onlyLoadTime,
							currModule.getDependenciesRunTime(), visited, currPath,
							addHere);
					} finally {
						currPath.remove(currModule);
					}
				} else {
					if (addHere.contains(currModule)) {
						addHere.addAll(currPath);
					}
				}
			}
		}
	}

	def private static boolean isDifferentModuleInSameProject(TModule module, ASTMetaInfoCache cache) {
		return module !== null && !module.eIsProxy
			&& module.eResource !== cache.resource
			&& module.projectName == cache.projectName;
	}
}
