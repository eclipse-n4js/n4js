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
import java.util.HashSet
import java.util.LinkedHashSet
import java.util.List
import java.util.Set
import org.eclipse.n4js.n4JS.IdentifierRef
import org.eclipse.n4js.n4JS.ModuleRef
import org.eclipse.n4js.n4JS.N4JSASTUtils
import org.eclipse.n4js.n4JS.N4JSPackage
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.postprocessing.RuntimeDependencyProcessor
import org.eclipse.n4js.resource.N4JSResource
import org.eclipse.n4js.ts.types.TModule
import org.eclipse.n4js.validation.AbstractN4JSDeclarativeValidator
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.xtext.validation.Check
import org.eclipse.xtext.validation.EValidatorRegistrar

import static org.eclipse.n4js.utils.N4JSLanguageUtils.*
import static org.eclipse.n4js.validation.IssueCodes.*

/**
 * Validations related to runtime dependencies (in particular, illegal load-time dependency cycles).
 * This class relies on the TModule-information computed in {@link RuntimeDependencyProcessor}.
 */
class RuntimeDependencyValidator extends AbstractN4JSDeclarativeValidator {

	private static final String INDENT = "    ";

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
	 * Req. GH-1678, Constraint 4
	 */
	@Check
	def void checkIllegalLoadtimeReference(IdentifierRef idRef) {
		if (!N4JSASTUtils.isTopLevelCode(idRef)) {
			return; // only interested in top-level == load-time references here!
		}
		if (idRef.eContainingFeature === N4JSPackage.Literals.NAMED_EXPORT_SPECIFIER__EXPORTED_ELEMENT) {
			return; // re-exports are not harmful
		}
		val targetModule = getTargetModule(idRef);
		if (targetModule === null) {
			return;
		}
		val containingModule = (idRef.eResource as N4JSResource).module;
		val hasCycle = (targetModule === containingModule && !containingModule.cyclicModulesRuntime.empty)
			|| (targetModule !== containingModule && containingModule.cyclicModulesRuntime.contains(targetModule));
		if (hasCycle) {
			val cycleStr = '\n' + dependencyCycleToString(containingModule, false, INDENT);
			addIssue(idRef, LTD_ILLEGAL_LOADTIME_REFERENCE.toIssueItem(cycleStr));
		}
	}

	@Check
	def void checkIllegalModuleRefToLTDTarget(Script script) {
		val containingModule = script.module;
		val refsToOtherModules = script.scriptElements.filter(ModuleRef).filter[referringToOtherModule].toList;

		val modulesInHealedCycles = new HashSet<TModule>();
		for (moduleRef : refsToOtherModules) {
			if (holdsNotAnIllegalModuleRefWithinLoadtimeCycle(containingModule, moduleRef)
				&& holdsNotAnIllegalModuleRefToLTDTarget(containingModule, moduleRef, modulesInHealedCycles)) {

				if (moduleRef.retainedAtRuntime) {
					// we have a valid reference to another module that will be retained at runtime
					// --> it may contribute to healing later moduleRefs:
					val targetModule = moduleRef.module;
					if (!targetModule.cyclicModulesRuntime.empty) {
						modulesInHealedCycles += targetModule;
						modulesInHealedCycles += targetModule.cyclicModulesRuntime;
					}
				}
			}
		}
	}

	/**
	 * Req. GH-1678, Constraint 1
	 * <p>
	 * This method will show an error on all module references (i.e. imports/exports with 'from "..."') that constitute the cycle.
	 */
	def private boolean holdsNotAnIllegalModuleRefWithinLoadtimeCycle(TModule containingModule, ModuleRef moduleRef) {
		val targetModule = moduleRef.module;
		if (containingModule.cyclicModulesLoadtimeForInheritance.contains(targetModule)) {
			val cycleStr = "\n" + dependencyCycleToString(targetModule, true, INDENT);
			addIssue(moduleRef, N4JSPackage.eINSTANCE.moduleRef_Module, LTD_LOADTIME_DEPENDENCY_CYCLE.toIssueItem(cycleStr));
			return false;
		}
		return true;
	}

	/**
	 * Req. GH-1678, Constraints 2 and 3.
	 */
	def private boolean holdsNotAnIllegalModuleRefToLTDTarget(TModule containingModule, ModuleRef moduleRef, Set<TModule> modulesInHealedCycles) {
		if (!moduleRef.isRetainedAtRuntime()) {
			return true; // only interested in imports/exports that are retained at runtime
		}

		val targetModule = moduleRef.module;
		if (!targetModule.isLTDTarget) {
			return true; // only interested in references to LTD targets
		}
		if (!targetModule.cyclicModulesLoadtimeForInheritance.empty) {
			// target is part of a load-time dependency cycle, so other errors are shown already there
			return true; // avoid unnecessary follow-up errors
		}

		val ltdSources = targetModule.runtimeCyclicLoadtimeDependents; // never includes the containing module itself!
		val isSingletonLTDTarget = ltdSources.size() == 1
			&& !containingModule.equals(Iterables.getFirst(ltdSources, null)); // excludes the one valid reference to an LTD target
		val isMultiLTDTarget = ltdSources.size() > 1;

		if (isSingletonLTDTarget || isMultiLTDTarget) {
			if (!modulesInHealedCycles.contains(targetModule)) {
				// illegal reference to an LTD target
				val withinSameDependencyCycleCluster = targetModule.cyclicModulesRuntime.contains(containingModule);
				if (withinSameDependencyCycleCluster) {
					// ERROR: referring to a multi-LTD-target from within the dependency cycle cluster (Req. GH-1678, Constraint 2)
					// --> load-time dependency conflict
					val otherLTDSources = otherLTDSourcesToString(containingModule, targetModule);
					val cycleStr = "\nContaining runtime dependency cycle cluster:\n" + dependencyCycleToString(targetModule, false, INDENT);
					addIssue(moduleRef, N4JSPackage.eINSTANCE.moduleRef_Module, LTD_LOADTIME_DEPENDENCY_CONFLICT.toIssueItem(targetModule.simpleName, otherLTDSources, cycleStr));
					return false;
				} else {
					// ERROR: referring to an LTD target from outside the dependency cycle cluster (Req. GH-1678, Constraint 3)
					val healingModulesStr = healingModulesToString(targetModule);
					val cycleStr = "\nContaining runtime dependency cycle cluster:\n" + dependencyCycleToString(targetModule, false, INDENT);
					addIssue(moduleRef, N4JSPackage.eINSTANCE.moduleRef_Module, LTD_REFERENCE_TO_LOADTIME_DEPENDENCY_TARGET.toIssueItem(targetModule.simpleName, healingModulesStr, cycleStr));
					return true; // because we assume a healing import will be added by transpiler, this module reference can be treated as healing in calling method
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

		if (!hasRuntimeRepresentation(target)) {
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
