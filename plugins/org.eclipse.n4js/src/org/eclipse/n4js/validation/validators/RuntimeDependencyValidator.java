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
package org.eclipse.n4js.validation.validators;

import static org.eclipse.n4js.utils.N4JSLanguageUtils.hasRuntimeRepresentation;
import static org.eclipse.n4js.validation.IssueCodes.LTD_ILLEGAL_LOADTIME_REFERENCE;
import static org.eclipse.n4js.validation.IssueCodes.LTD_LOADTIME_DEPENDENCY_CONFLICT;
import static org.eclipse.n4js.validation.IssueCodes.LTD_LOADTIME_DEPENDENCY_CYCLE;
import static org.eclipse.n4js.validation.IssueCodes.LTD_REFERENCE_TO_LOADTIME_DEPENDENCY_TARGET;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.sortWith;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.ModuleRef;
import org.eclipse.n4js.n4JS.N4JSASTUtils;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.postprocessing.RuntimeDependencyProcessor;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.utils.Strings;
import org.eclipse.n4js.validation.AbstractN4JSDeclarativeValidator;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.EValidatorRegistrar;

import com.google.common.collect.Iterables;

/**
 * Validations related to runtime dependencies (in particular, illegal load-time dependency cycles). This class relies
 * on the TModule-information computed in {@link RuntimeDependencyProcessor}.
 */
public class RuntimeDependencyValidator extends AbstractN4JSDeclarativeValidator {

	private static final String INDENT = "    ";

	/**
	 * NEEDED
	 *
	 * when removed check methods will be called twice once by N4JSValidator, and once by
	 * AbstractDeclarativeN4JSValidator
	 */
	@Override
	public void register(EValidatorRegistrar registrar) {
		// nop
	}

	/**
	 * Req. GH-1678, Constraint 4
	 */
	@Check
	public void checkIllegalLoadtimeReference(IdentifierRef idRef) {
		if (!N4JSASTUtils.isTopLevelCode(idRef)) {
			return; // only interested in top-level == load-time references here!
		}
		if (idRef.eContainingFeature() == N4JSPackage.Literals.NAMED_EXPORT_SPECIFIER__EXPORTED_ELEMENT) {
			return; // re-exports are not harmful
		}
		TModule targetModule = getTargetModule(idRef);
		if (targetModule == null) {
			return;
		}
		TModule containingModule = ((N4JSResource) idRef.eResource()).getModule();
		boolean hasCycle = (targetModule == containingModule && !containingModule.getCyclicModulesRuntime().isEmpty())
				|| (targetModule != containingModule
						&& containingModule.getCyclicModulesRuntime().contains(targetModule));
		if (hasCycle) {
			String cycleStr = "\n" + dependencyCycleToString(containingModule, false, INDENT);
			addIssue(idRef, LTD_ILLEGAL_LOADTIME_REFERENCE.toIssueItem(cycleStr));
		}
	}

	/***/
	@Check
	public void checkIllegalModuleRefToLTDTarget(Script script) {
		TModule containingModule = script.getModule();
		List<ModuleRef> refsToOtherModules = toList(
				filter(filter(script.getScriptElements(), ModuleRef.class), mref -> mref.isReferringToOtherModule()));

		Set<TModule> modulesInHealedCycles = new HashSet<>();
		for (ModuleRef moduleRef : refsToOtherModules) {
			if (holdsNotAnIllegalModuleRefWithinLoadtimeCycle(containingModule, moduleRef)
					&& holdsNotAnIllegalModuleRefToLTDTarget(containingModule, moduleRef, modulesInHealedCycles)) {

				if (moduleRef.isRetainedAtRuntime()) {
					// we have a valid reference to another module that will be retained at runtime
					// --> it may contribute to healing later moduleRefs:
					TModule targetModule = moduleRef.getModule();
					if (!targetModule.getCyclicModulesRuntime().isEmpty()) {
						modulesInHealedCycles.add(targetModule);
						modulesInHealedCycles.addAll(targetModule.getCyclicModulesRuntime());
					}
				}
			}
		}
	}

	/**
	 * Req. GH-1678, Constraint 1
	 * <p>
	 * This method will show an error on all module references (i.e. imports/exports with 'from "..."') that constitute
	 * the cycle.
	 */
	private boolean holdsNotAnIllegalModuleRefWithinLoadtimeCycle(TModule containingModule, ModuleRef moduleRef) {
		TModule targetModule = moduleRef.getModule();
		if (containingModule.getCyclicModulesLoadtimeForInheritance().contains(targetModule)) {
			String cycleStr = "\n" + dependencyCycleToString(targetModule, true, INDENT);
			addIssue(moduleRef, N4JSPackage.eINSTANCE.getModuleRef_Module(),
					LTD_LOADTIME_DEPENDENCY_CYCLE.toIssueItem(cycleStr));
			return false;
		}
		return true;
	}

	/**
	 * Req. GH-1678, Constraints 2 and 3.
	 */
	private boolean holdsNotAnIllegalModuleRefToLTDTarget(TModule containingModule, ModuleRef moduleRef,
			Set<TModule> modulesInHealedCycles) {
		if (!moduleRef.isRetainedAtRuntime()) {
			return true; // only interested in imports/exports that are retained at runtime
		}

		TModule targetModule = moduleRef.getModule();
		if (!isLTDTarget(targetModule)) {
			return true; // only interested in references to LTD targets
		}
		if (!targetModule.getCyclicModulesLoadtimeForInheritance().isEmpty()) {
			// target is part of a load-time dependency cycle, so other errors are shown already there
			return true; // avoid unnecessary follow-up errors
		}

		EList<TModule> ltdSources = targetModule.getRuntimeCyclicLoadtimeDependents(); // never includes the containing
																						// module itself!
		boolean isSingletonLTDTarget = ltdSources.size() == 1
				&& !containingModule.equals(Iterables.getFirst(ltdSources, null)); // excludes the one valid reference
																					// to an LTD target
		boolean isMultiLTDTarget = ltdSources.size() > 1;

		if (isSingletonLTDTarget || isMultiLTDTarget) {
			if (!modulesInHealedCycles.contains(targetModule)) {
				// illegal reference to an LTD target
				boolean withinSameDependencyCycleCluster = targetModule.getCyclicModulesRuntime()
						.contains(containingModule);
				if (withinSameDependencyCycleCluster) {
					// ERROR: referring to a multi-LTD-target from within the dependency cycle cluster (Req. GH-1678,
					// Constraint 2)
					// --> load-time dependency conflict
					String otherLTDSources = otherLTDSourcesToString(containingModule, targetModule);
					String cycleStr = "\nContaining runtime dependency cycle cluster:\n"
							+ dependencyCycleToString(targetModule, false, INDENT);
					addIssue(moduleRef, N4JSPackage.eINSTANCE.getModuleRef_Module(), LTD_LOADTIME_DEPENDENCY_CONFLICT
							.toIssueItem(targetModule.getSimpleName(), otherLTDSources, cycleStr));
					return false;
				} else {
					// ERROR: referring to an LTD target from outside the dependency cycle cluster (Req. GH-1678,
					// Constraint 3)
					String healingModulesStr = healingModulesToString(targetModule);
					String cycleStr = "\nContaining runtime dependency cycle cluster:\n"
							+ dependencyCycleToString(targetModule, false, INDENT);
					addIssue(moduleRef, N4JSPackage.eINSTANCE.getModuleRef_Module(),
							LTD_REFERENCE_TO_LOADTIME_DEPENDENCY_TARGET.toIssueItem(targetModule.getSimpleName(),
									healingModulesStr, cycleStr));
					return true; // because we assume a healing import will be added by transpiler, this module
									// reference can be treated as healing in calling method
				}
			}
		}

		return true;
	}

	private TModule getTargetModule(IdentifierRef idRef) {
		IdentifiableElement target = idRef.getTargetElement();
		if (target == null || target.eIsProxy()) {
			return null;
		}

		if (!hasRuntimeRepresentation(target)) {
			return null;
		}

		TModule targetModule = EcoreUtil2.getContainerOfType(target, TModule.class);
		if (targetModule != null) {
			return targetModule;
		}

		// references to local variables within the same module don't point to the TModule but to
		// the variable declaration in the AST, so we need the following additional check:
		Script targetScript = EcoreUtil2.getContainerOfType(target, Script.class);
		if (targetScript != null) {
			return targetScript.getModule();
		}

		return null;
	}

	private String otherLTDSourcesToString(TModule module, TModule ltdTarget) {
		List<TModule> otherSources = sortModules(
				filter(ltdTarget.getRuntimeCyclicLoadtimeDependents(), it -> it != module));
		String prefix = (otherSources.size() > 1) ? "modules " : "module ";
		return prefix + Strings.join(", ", map(otherSources, os -> os.getSimpleName()));
	}

	private String healingModulesToString(TModule module) {
		List<TModule> healingModules = sortModules(filter(module.getCyclicModulesRuntime(), it -> !isLTDTarget(it)));
		String prefix = (healingModules.size() > 1) ? "one of the modules " : "module ";
		return prefix + Strings.join(", ", map(healingModules, os -> os.getSimpleName()));
	}

	private String dependencyCycleToString(TModule module, boolean onlyLoadtimeForInheritance, CharSequence indent) {
		EList<TModule> cyclicModulesToUse = (onlyLoadtimeForInheritance)
				? module.getCyclicModulesLoadtimeForInheritance()
				: module.getCyclicModulesRuntime();
		Set<TModule> cyclicModules = new LinkedHashSet<>(cyclicModulesToUse);
		if (cyclicModules.isEmpty()) {
			return null;
		}
		cyclicModules.add(module);

		StringBuilder sb = new StringBuilder();
		for (TModule cyclicModule : sortModules(cyclicModules)) {
			if (sb.length() > 0) {
				sb.append('\n');
			}
			sb.append(indent);
			if (!onlyLoadtimeForInheritance && isLTDTarget(cyclicModule)) {
				sb.append('*');
			}
			sb.append(getFileName(cyclicModule));
			sb.append(" --> ");
			int listStart = sb.length();
			Iterable<TModule> targetsOfRuntimeDeps = map(cyclicModule.getDependenciesRuntime(), it -> it.getTarget());
			for (TModule requiredModule : sortModules(targetsOfRuntimeDeps)) {
				if (cyclicModules.contains(requiredModule)) {
					if (sb.length() > listStart) {
						sb.append(", ");
					}
					sb.append(getFileName(requiredModule));
				}
			}
		}
		return sb.toString();
	}

	private <T extends TModule> List<T> sortModules(Iterable<T> modules) {
		return sortWith(modules, (m1, m2) -> CharSequence.compare(m1.getSimpleName(), m2.getSimpleName()));
	}

	private boolean isLTDTarget(TModule module) {
		return !module.getRuntimeCyclicLoadtimeDependents().isEmpty();
	}

	private String getFileName(TModule module) {
		if (module == null || module.eResource() == null || module.eResource().getURI() == null) {
			return null;
		}
		return module.eResource().getURI().lastSegment();
	}
}
