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
package org.eclipse.n4js.validation.validators

import com.google.common.collect.Iterables
import com.google.inject.Inject
import java.util.HashSet
import java.util.LinkedHashSet
import java.util.List
import java.util.Set
import org.eclipse.n4js.n4JS.IdentifierRef
import org.eclipse.n4js.n4JS.ImportDeclaration
import org.eclipse.n4js.n4JS.N4JSASTUtils
import org.eclipse.n4js.n4JS.N4JSPackage
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.postprocessing.RuntimeDependencyProcessor
import org.eclipse.n4js.resource.N4JSResource
import org.eclipse.n4js.ts.types.TModule
import org.eclipse.n4js.validation.AbstractN4JSDeclarativeValidator
import org.eclipse.n4js.validation.IssueCodes
import org.eclipse.n4js.validation.JavaScriptVariantHelper
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.xtext.validation.Check
import org.eclipse.xtext.validation.EValidatorRegistrar

import static org.eclipse.n4js.utils.N4JSLanguageUtils.*

/**
 * Validations related to runtime dependencies (in particular, illegal load-time dependency cycles).
 * This class relies on the TModule-information computed in {@link RuntimeDependencyProcessor}.
 */
class RuntimeDependencyValidator extends AbstractN4JSDeclarativeValidator {

	private static final String INDENT = "    ";

	@Inject
	private JavaScriptVariantHelper javaScriptVariantHelper;

	/**
	 * NEEDED
	 *
	 * when removed check methods will be called twice once by N4JSValidator, and once by
	 * AbstractDeclarativeN4JSValidator
	 */
	override register(EValidatorRegistrar registrar) {
		// nop
	}

	/**
	 * A non-inheritance load-time reference R from a module m to an identifiable element in module m’ is an error iff
	 * <ul>
	 * <li>case m != m’: m, m’ are runtime cyclic to each other,
	 * <li>case m = m’: m is runtime cyclic to some other module m’’.
	 * </ul>
	 */
	@Check
	def void checkIllegalLoadtimeReference(IdentifierRef idRef) {
		if (!N4JSASTUtils.isTopLevelCode(idRef)) {
			return; // only interested in top-level == load-time references here!
		}
		val targetModule = getTargetModule(idRef);
		if (targetModule === null) {
			return;
		}
		val containingModule = (idRef.eResource as N4JSResource).module;
		val hasCycle = (targetModule === containingModule && !containingModule.cyclicModulesRuntime.empty)
			|| (targetModule !== containingModule && containingModule.cyclicModulesRuntime.contains(targetModule)); // FIXME linear search
		if (hasCycle) {
			val message = IssueCodes.getMessageForLTD_ILLEGAL_LOADTIME_REFERENCE()
				+ '\n' + dependencyCycleToString(containingModule, false, INDENT);
			addIssue(message, idRef, IssueCodes.LTD_ILLEGAL_LOADTIME_REFERENCE);
		}
	}

	@Check
	def void checkIllegalImportOfLTDTarget(Script script) {
		val containingModule = script.module;
		val importDecls = script.scriptElements.filter(ImportDeclaration).toList;

		val modulesInHealedCycles = new HashSet<TModule>();
		for (importDecl : importDecls) {
			if (holdsNotAnIllegalImportWithinLoadtimeCycle(containingModule, importDecl)
				&& holdsNotAnIllegalImportOfLTDTarget(containingModule, importDecl, modulesInHealedCycles)) {

				if (importDecl.retainedAtRuntime) {
					// we have a valid import that will be retained at runtime
					// --> it may contribute to healing later imports:
					val targetModule = importDecl.module;
					if (!targetModule.cyclicModulesRuntime.empty) {
						modulesInHealedCycles += targetModule;
						modulesInHealedCycles += targetModule.cyclicModulesRuntime;
					}
				}
			}
		}
	}

	/**
	 * It is an error to form load-time dependency cycles. This method will show an error on
	 * all imports that constitute the cycle.
	 */
	def private boolean holdsNotAnIllegalImportWithinLoadtimeCycle(TModule containingModule, ImportDeclaration importDecl) {
		val targetModule = importDecl.module;
		if (containingModule.cyclicModulesLoadtimeForInheritance.contains(targetModule)) { // FIXME linear search
			val message = IssueCodes.getMessageForLTD_LOADTIME_DEPENDENCY_CYCLE() + "\n"
				+ dependencyCycleToString(targetModule, true, INDENT);
			addIssue(message, importDecl, N4JSPackage.eINSTANCE.importDeclaration_Module, IssueCodes.LTD_LOADTIME_DEPENDENCY_CYCLE);
			return false;
		}
		return true;
	}

	/**
	 * It is an error to import an LTD target m’ from a module m except
	 * <ol>
	 * <li>there exists only a single LTD source of m’ and
	 * <li>m is an LTD source of m’.
	 * </ol>
	 * In other words, m must be the single LTD source of m’.
	 */
	def private boolean holdsNotAnIllegalImportOfLTDTarget(TModule containingModule, ImportDeclaration importDecl, Set<TModule> modulesInHealedCycles) {
		if (!importDecl.isRetainedAtRuntime()) {
			return true; // only interested in imports that are retained at runtime
		}

		val targetModule = importDecl.module;
		if (!targetModule.isLTDTarget) {
			return true; // only interested in imports of LTD targets
		}
		if (!targetModule.cyclicModulesLoadtimeForInheritance.empty) {
			// target is part of a load-time dependency cycle, so other errors are shown already there
			return true; // avoid unnecessary follow-up errors
		}

		val ltdSources = targetModule.runtimeCyclicLoadtimeDependents; // never includes the containing module itself!
		val isSingletonLTDTarget = ltdSources.size() == 1
			&& !containingModule.equals(Iterables.getFirst(ltdSources, null)); // excludes the one valid import of an LTD target
		val isMultiLTDTarget = ltdSources.size() > 1;

		if (isSingletonLTDTarget || isMultiLTDTarget) {
			if (!modulesInHealedCycles.contains(targetModule)) {
				// illegal import of an LTD target
				val withinSameDependencyCycleCluster = targetModule.cyclicModulesRuntime.contains(containingModule);
				if (withinSameDependencyCycleCluster) {
					// ERROR: importing a multi-LTD-target from within the dependency cycle cluster
					// --> load-time dependency conflict
					val otherLTDSources = otherLTDSourcesToString(containingModule, targetModule);
					val message = IssueCodes.getMessageForLTD_LOADTIME_DEPENDENCY_CONFLICT(targetModule.simpleName, otherLTDSources) + "\n"
						+ "Containing runtime dependency cycle cluster:\n"
						+ dependencyCycleToString(targetModule, false, INDENT);
					addIssue(message, importDecl, N4JSPackage.eINSTANCE.importDeclaration_Module, IssueCodes.LTD_LOADTIME_DEPENDENCY_CONFLICT);
					return false;
				} else {
					// ERROR: importing an LTD target from outside the dependency cycle cluster
					val healingModulesStr = healingModulesToString(targetModule);
					val message = IssueCodes.getMessageForLTD_IMPORT_OF_LOADTIME_DEPENDENCY_TARGET(targetModule.simpleName, healingModulesStr) + "\n"
						+ "Containing runtime dependency cycle cluster:\n"
						+ dependencyCycleToString(targetModule, false, INDENT);
					addIssue(message, importDecl, N4JSPackage.eINSTANCE.importDeclaration_Module, IssueCodes.LTD_IMPORT_OF_LOADTIME_DEPENDENCY_TARGET);
					return true; // because we assume a healing import will be added by transpiler, this import can be treated as healing in calling method
				}
			}
		}

		return true;
	}

	def private TModule getTargetModule(IdentifierRef idRef) {
		val target = idRef.targetElement;
		if (target === null || target.eIsProxy()) {
			return null;
		}

		if (!hasRuntimeRepresentation(target, javaScriptVariantHelper)) {
			return null;
		}

		val targetModule = EcoreUtil2.getContainerOfType(target, TModule);
		if (targetModule !== null) {
			return targetModule;
		}

		// references to local variables within the same module don't point to the TModule but to
		// the variable declaration in the AST, so we need the following additional check:
		val targetScript = EcoreUtil2.getContainerOfType(target, Script);
		if (targetScript !== null) {
			return targetScript.getModule();
		}

		return null;
	}

	def private String otherLTDSourcesToString(TModule module, TModule ltdTarget) {
		val otherSources = ltdTarget.runtimeCyclicLoadtimeDependents.filter[it !== module].sortModules;
		val prefix = if (otherSources.size > 1) "modules " else "module ";
		return prefix + otherSources.map[simpleName].join(", ");
	}

	def private String healingModulesToString(TModule module) {
		val healingModules = module.cyclicModulesRuntime.filter[!it.isLTDTarget].sortModules;
		val prefix = if (healingModules.size > 1) "one of the modules " else "module ";
		return prefix + healingModules.map[simpleName].join(", ");
	}

	def private String dependencyCycleToString(TModule module, boolean onlyLoadtimeForInheritance, CharSequence indent) {
		val cyclicModulesToUse = if (onlyLoadtimeForInheritance) module.cyclicModulesLoadtimeForInheritance else module.cyclicModulesRuntime;
		val cyclicModules = new LinkedHashSet(cyclicModulesToUse);
		if (cyclicModules.empty) {
			return null;
		}
		cyclicModules += module;

		val sb = new StringBuilder();
		for (cyclicModule : sortModules(cyclicModules)) {
			if (sb.length > 0) {
				sb.append('\n');
			}
			sb.append(indent);
			if (!onlyLoadtimeForInheritance && cyclicModule.isLTDTarget) {
				sb.append('*');
			}
			sb.append(cyclicModule.fileName)
			sb.append(" --> ");
			val listStart = sb.length;
			val targetsOfRuntimeDeps = cyclicModule.dependenciesRuntime.map[target];
			for (requiredModule : sortModules(targetsOfRuntimeDeps)) {
				if (cyclicModules.contains(requiredModule)) {
					if (sb.length > listStart) {
						sb.append(", ");
					}
					sb.append(requiredModule.fileName);
				}
			}
		}
		return sb.toString();
	}

	def private <T extends TModule> List<T> sortModules(Iterable<T> modules) {
		return modules.sortWith([m1, m2 |
			return CharSequence.compare(m1.simpleName, m2.simpleName);
		]);
	}

	def private boolean isLTDTarget(TModule module) {
		return !module.runtimeCyclicLoadtimeDependents.empty;
	}

	def private String getFileName(TModule module) {
		return module?.eResource?.URI?.lastSegment;
	}
}
