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
import org.eclipse.n4js.postprocessing.RunTimeDependencyProcessor
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
 * Validations related to run-time dependencies (in particular, illegal load-time dependency cycles).
 * This class relies on the TModule-information computed in {@link RunTimeDependencyProcessor}.
 */
class RunTimeDependencyValidator extends AbstractN4JSDeclarativeValidator {

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
	 * <li>case m != m’: m, m’ are run-time cyclic to each other,
	 * <li>case m = m’: m is run-time cyclic to some other module m’’.
	 * </ul>
	 */
	@Check
	def void checkIllegalLoadTimeReference(IdentifierRef idRef) {
		if (!N4JSASTUtils.isTopLevelCode(idRef)) {
			return; // only interested in top-level == load-time references here!
		}
		val targetModule = getTargetModule(idRef);
		if (targetModule === null) {
			return;
		}
		val containingModule = (idRef.eResource as N4JSResource).module;
		val hasCycle = (targetModule === containingModule && !containingModule.cyclicModulesRunTime.empty)
			|| (targetModule !== containingModule && containingModule.cyclicModulesRunTime.contains(targetModule)); // FIXME linear search
		if (hasCycle) {
			val message = IssueCodes.getMessageForLTD_ILLEGAL_LOAD_TIME_REFERENCE()
				+ '\n' + dependencyCycleToString(containingModule, false, INDENT);
			addIssue(message, idRef, IssueCodes.LTD_ILLEGAL_LOAD_TIME_REFERENCE);
		}
	}

	@Check
	def void checkIllegalImportOfLTSlave(Script script) {
		val containingModule = script.module;
		val importDecls = script.scriptElements.filter(ImportDeclaration).toList;

		val modulesInHealedCycles = new HashSet<TModule>();
		for (importDecl : importDecls) {
			if (holdsNotAnIllegalImportWithinLoadTimeCycle(containingModule, importDecl)
				&& holdsNotAnIllegalImportOfLTSlave(containingModule, importDecl, modulesInHealedCycles)) {

				// we have a valid import, it may contribute to healing later imports:
				val targetModule = importDecl.module;
				if (!targetModule.cyclicModulesRunTime.empty) {
					modulesInHealedCycles += targetModule;
					modulesInHealedCycles += targetModule.cyclicModulesRunTime;
				}
			}
		}
	}

	/**
	 * It is an error to form load-time dependency cycles. This method will show an error on
	 * all imports that constitute the cycle.
	 */
	def private boolean holdsNotAnIllegalImportWithinLoadTimeCycle(TModule containingModule, ImportDeclaration importDecl) {
		val targetModule = importDecl.module;
		if (containingModule.cyclicModulesLoadTimeForInheritance.contains(targetModule)) { // FIXME linear search
			val message = IssueCodes.getMessageForLTD_LOAD_TIME_DEPENDENCY_CYCLE() + "\n"
				+ dependencyCycleToString(targetModule, true, INDENT);
			addIssue(message, importDecl, N4JSPackage.eINSTANCE.importDeclaration_Module, IssueCodes.LTD_LOAD_TIME_DEPENDENCY_CYCLE);
			return false;
		}
		return true;
	}

	/**
	 * It is an error to import an LTSlave m’ from a module m except
	 * <ol>
	 * <li>there exists only a single LTDX of m’ and
	 * <li>m is an LTDX of m’.
	 * </ol>
	 * In other words, m must be the single LTDX of m’.
	 */
	def private boolean holdsNotAnIllegalImportOfLTSlave(TModule containingModule, ImportDeclaration importDecl, Set<TModule> modulesInHealedCycles) {
		if (!importDecl.isRetainedAtRunTime()) {
			return true; // only interested in imports that are retained at run-time
		}

		val targetModule = importDecl.module;
		if (!targetModule.isLTSlave) {
			return true; // only interested in imports of LTSlaves
		}
		if (!targetModule.cyclicModulesLoadTimeForInheritance.empty) {
			// target is part of a load-time dependency cycle, so other errors are shown already there
			return true; // avoid unnecessary follow-up errors
		}

		val ltdxs = targetModule.runTimeCyclicLoadTimeDependents; // never includes the containing module itself!
		val isSingletonLTSlave = ltdxs.size() == 1
			&& !containingModule.equals(Iterables.getFirst(ltdxs, null)); // excludes the one valid import of an LTSlave
		val isMultiLTSlave = ltdxs.size() > 1;

		if (isSingletonLTSlave || isMultiLTSlave) {
			if (!modulesInHealedCycles.contains(targetModule)) {
				// illegal import of an LTSlave
				val withinSameDependencyCycleCluster = targetModule.cyclicModulesRunTime.contains(containingModule);
				if (withinSameDependencyCycleCluster) {
					// ERROR: importing a multi-LTSlave from within the dependency cycle cluster
					// --> load-time dependency conflict
					val otherLTDXs = otherLTDXsToString(containingModule, targetModule);
					val message = IssueCodes.getMessageForLTD_LOAD_TIME_DEPENDENCY_CONFLICT(targetModule.simpleName, otherLTDXs) + "\n"
						+ "Containing run-time dependency cycle cluster:\n"
						+ dependencyCycleToString(targetModule, false, INDENT);
					addIssue(message, importDecl, N4JSPackage.eINSTANCE.importDeclaration_Module, IssueCodes.LTD_LOAD_TIME_DEPENDENCY_CONFLICT);
					return false;
				} else {
					// ERROR: importing an LTSlave from outside the dependency cycle cluster
					val healingModulesStr = healingModulesToString(targetModule);
					val message = IssueCodes.getMessageForLTD_IMPORT_OF_LOAD_TIME_DEPENDENCY_TARGET(targetModule.simpleName, healingModulesStr) + "\n"
						+ "Containing run-time dependency cycle cluster:\n"
						+ dependencyCycleToString(targetModule, false, INDENT);
					addIssue(message, importDecl, N4JSPackage.eINSTANCE.importDeclaration_Module, IssueCodes.LTD_IMPORT_OF_LOAD_TIME_DEPENDENCY_TARGET);
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

		if (!hasRunTimeRepresentation(target, javaScriptVariantHelper)) {
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

	def private String otherLTDXsToString(TModule module, TModule ltSlave) {
		val otherLTDXs = ltSlave.runTimeCyclicLoadTimeDependents.filter[it !== module].sortModules;
		val prefix = if (otherLTDXs.size > 1) "modules " else "module ";
		return prefix + otherLTDXs.map[simpleName].join(", ");
	}

	def private String healingModulesToString(TModule module) {
		val healingModules = module.cyclicModulesRunTime.filter[!it.isLTSlave].sortModules;
		val prefix = if (healingModules.size > 1) "one of the modules " else "module ";
		return prefix + healingModules.map[simpleName].join(", ");
	}

	def private String dependencyCycleToString(TModule module, boolean onlyLoadTimeForInheritance, CharSequence indent) {
		val cyclicModulesToUse = if (onlyLoadTimeForInheritance) module.cyclicModulesLoadTimeForInheritance else module.cyclicModulesRunTime;
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
			if (!onlyLoadTimeForInheritance && cyclicModule.isLTSlave) {
				sb.append('*');
			}
			sb.append(cyclicModule.fileName)
			sb.append(" --> ");
			val listStart = sb.length;
			val targetsOfRunTimeDeps = cyclicModule.dependenciesRunTime.map[target];
			for (requiredModule : sortModules(targetsOfRunTimeDeps)) {
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

	def private boolean isLTSlave(TModule module) {
		return !module.runTimeCyclicLoadTimeDependents.empty;
	}

	def private String getFileName(TModule module) {
		return module?.eResource?.URI?.lastSegment;
	}
}
