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
		val hasCycle = (targetModule === containingModule && !containingModule.runTimeCyclicModules.empty)
			|| (targetModule !== containingModule && containingModule.runTimeCyclicModules.contains(targetModule)); // FIXME linear search
		if (hasCycle) {
			val message = IssueCodes.getMessageForLTD_ILLEGAL_LOAD_TIME_REFERENCE();
			addIssue(message, idRef, IssueCodes.LTD_ILLEGAL_LOAD_TIME_REFERENCE);
		}
	}

	/**
	 * It is an error to import an LTSlave m’ from a module m except
	 * <ol>
	 * <li>there exists only a single LTDX of m’ and
	 * <li>m is an LTDX of m’.
	 * </ol>
	 * In other words, m must be the single LTDX of m’.
	 */
	@Check
	def void checkIllegalImportOfLTSlave(ImportDeclaration importDecl) {
		if (!importDecl.isRetainedAtRunTime()) {
			return; // only interested in imports that are retained at run-time
		}
		val containingModule = (importDecl.eResource as N4JSResource).module;
		val targetModule = importDecl.module;
		val ltdxs = targetModule.ltdxs;

		val isSingletonLTSlaveInThisProject = ltdxs.size() == 1
			&& !containingModule.equals(Iterables.getFirst(ltdxs, null));
		val isMultiLTSlaveInThisProject = ltdxs.size() > 1;

		if (isSingletonLTSlaveInThisProject || isMultiLTSlaveInThisProject) {
			// illegal import of an LTSlave
			val kind = if (isSingletonLTSlaveInThisProject) "singleton-" else "multi-";
			val message = IssueCodes.getMessageForLTD_ILLEGAL_IMPORT_OF_LT_SLAVE(kind, targetModule.qualifiedName);
			addIssue(message, importDecl, N4JSPackage.eINSTANCE.importDeclaration_Module, IssueCodes.LTD_ILLEGAL_IMPORT_OF_LT_SLAVE);
		}
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
}
