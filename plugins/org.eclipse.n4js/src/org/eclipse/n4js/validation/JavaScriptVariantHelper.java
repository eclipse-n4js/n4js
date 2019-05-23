/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.validation;

import org.eclipse.emf.ecore.EObject;

/**
 * The N4JS type system and validation is used not only for pure N4JS, but in the context of certain variants as plain
 * JavaScript in unrestricted or strict mode, type definition files (N4JSD) etc. It also is re-used by sub-language
 * (such as JSX). Instead of replacing the validators (or other components) for these variants (which may be an
 * additional technique), the variant helper allows fine-grained control of what kind of constraints to check. An
 * instance of the helper is to be provided by the injector; N4Js uses the {@link N4JSJavaScriptVariantHelper},
 * sub-languages may bind to a different implementation.
 */
public interface JavaScriptVariantHelper {

	/**
	 * Returns the name of the variant of the given element.
	 *
	 * This method is not intended to be used to detect the current variant. It is rather intended to be used in
	 * user-faced error messages exclusively.
	 */
	public String getVariantName(EObject eobj);

	/**
	 * Returns the name of the variant of the file extension.
	 *
	 * Returns {@code null} if fileExtension is unknown.
	 */
	public String getVariantName(String fileExtension);

	/**
	 * Return true if dynamic pseudo scope should be activated.
	 *
	 * @param eobj
	 *            The EObject providing the context for the check.
	 */
	public boolean activateDynamicPseudoScope(EObject eobj);

	/**
	 * Return true if missing implementation is allowed, for instance in external mode.
	 *
	 * @param eobj
	 *            The EObject providing the context for the check.
	 */
	public boolean allowMissingImplementation(EObject eobj);

	/**
	 * Return true if override annotation should be checked, e.g. if mode is N4JS.
	 *
	 * @param eobj
	 *            The EObject providing the context for the check.
	 */
	public boolean checkOverrideAnnotation(EObject eobj);

	/**
	 * Return true if type declaration should be checked, e.g. if the mode is N4JS.
	 *
	 * @param eobj
	 *            The EObject providing the context for the check.
	 */
	public boolean checkTypeDeclaration(EObject eobj);

	/**
	 * Return true if type declaration should be checked, e.g. if the mode is N4JS.
	 *
	 * @param eobj
	 *            The EObject providing the context for the check.
	 */
	public boolean checkMemberDeclaration(EObject eobj);

	/**
	 * Return true if variable declaration should be checked, e.g. if the mode is N4JS.
	 *
	 * @param eobj
	 *            The EObject providing the context for the check.
	 */
	public boolean checkVariable(EObject eobj);

	/**
	 * Return true if method reference should be checked.
	 *
	 * @param eobj
	 *            The EObject providing the context for the check.
	 */
	public boolean checkMethodReference(EObject eobj);

	/**
	 * Return true if call expression should be checked.
	 *
	 * @param eobj
	 *            The EObject providing the context for the check.
	 */
	public boolean checkCallExpression(EObject eobj);

	/**
	 * Return true if new expression should be checked.
	 *
	 * @param eobj
	 *            The EObject providing the context for the check.
	 */
	public boolean requireCheckNewExpression(EObject eobj);

	/**
	 * Return true if indexed access expression should be checked, only in N4JS mode.
	 *
	 * @param eobj
	 *            The EObject providing the context for the check.
	 */
	public boolean requireCheckIndexedAccessExpression(EObject eobj);

	/**
	 * Return true if function name should be checked.
	 *
	 * @param eobj
	 *            The EObject providing the context for the check.
	 */
	public boolean requireCheckFunctionName(EObject eobj);

	/**
	 * Return true if function return should be checked.
	 *
	 * @param eobj
	 *            The EObject providing the context for the check.
	 */
	public boolean requireCheckFunctionReturn(EObject eobj);

	/**
	 * Return true if function expression in expression statement should be checked.
	 *
	 * @param eobj
	 *            The EObject providing the context for the check.
	 */
	public boolean requireCheckFunctionExpressionInExpressionStatement(EObject eobj);

	/**
	 * Return true if a constant declaration has an initializer.
	 *
	 * @param eobj
	 *            The EObject providing the context for the check.
	 */
	public boolean constantHasInitializer(EObject eobj);

	/**
	 * Return true if wrong read/write should be allowed.
	 *
	 * @param eobj
	 *            The EObject providing the context for the check.
	 */
	public boolean allowWrongReadWrite(EObject eobj);

	/**
	 * Return true if type inference should be doomed.
	 *
	 * @param eobj
	 *            The EObject providing the context for the check.
	 */
	public boolean doomTypeInference(EObject eobj);

	/**
	 * Return true if annotation should be allowed.
	 *
	 * @param eobj
	 *            The EObject providing the context for the check.
	 */
	public boolean allowAnnotation(EObject eobj);

	/**
	 * Return true if it must be checked that a final field is initialized.
	 *
	 * @param eobj
	 *            The EObject providing the context for the check.
	 */
	public boolean requireCheckFinalFieldIsInitialized(EObject eobj);

	/**
	 * Return true if it must be checked if a name starts with dollar.
	 *
	 * @param eobj
	 *            The EObject providing the context for the check.
	 */
	public boolean requireCheckNameStartsWithDollar(EObject eobj);

	/**
	 * Return true if it is required to check if body of a member is missing.
	 *
	 * @param eobj
	 *            The EObject providing the context for the check.
	 */
	public boolean requireCheckForMissingBody(EObject eobj);

	/**
	 * Return true if it is required to check type matches.
	 *
	 * @param eobj
	 *            The EObject providing the context for the check.
	 */
	public boolean requireCheckTypeMatchesExpectedType(EObject eobj);

	/**
	 * Enforce dynamic types in call cases even without explicit modifier. This is usually the case for plain
	 * ECMAScript.
	 *
	 * @param eobj
	 *            The EObject providing the context for the check.
	 */
	public boolean enforceDynamicTypes(EObject eobj);

	/**
	 * Return true if the variant is type aware, e.g. N4JS is type are but plain JS is not.
	 *
	 * @param eobj
	 *            The EObject providing the context for the check.
	 */
	public boolean isTypeAware(EObject eobj);

	/**
	 * Return true if the variant has global object.
	 *
	 * @param eobj
	 *            The EObject providing the context for the check.
	 */
	public boolean hasGlobalObject(EObject eobj);

	/**
	 * Return true if exported elements should be checked in case of visibility higher than private.
	 *
	 * @param eobj
	 *            The EObject providing the context for the check.
	 */
	public boolean requireCheckExportedWhenVisibilityHigherThanPrivate(EObject eobj);

	/**
	 * Return true if the mode is unrestricted.
	 *
	 * @param eobj
	 *            The EObject providing the context for the check.
	 */
	public boolean isUnrestrictedMode(EObject eobj);

	/**
	 * Return true if the script is defined in a N4JSD module (external mode).
	 *
	 * @param eobj
	 *            The EObject providing the context for the check.
	 */
	public boolean isExternalMode(EObject eobj);

	/**
	 * Return true if the script is defined in a N4JS module.
	 */
	public boolean isN4JSMode(EObject eobj);

	/**
	 * Return true if the script is a plain JS.
	 *
	 * @param eobj
	 *            The EObject providing the context for the check.
	 */
	public boolean isPlainJS(EObject eobj);

	/**
	 * Return true if "use strict" is declared.
	 *
	 * @param eobj
	 *            The EObject providing the context for the check.
	 */
	public boolean isStrictMode(EObject eobj);

	/**
	 * Return the variant mode.
	 *
	 * @param eobj
	 *            The EObject providing the context to find out the variant mode.
	 */
	public String variantMode(EObject eobj);

	/**
	 * Return {@code true} if in the context of the given {@link EObject}, there can be distinct elements with the same
	 * qualified name in a scope.
	 */
	public boolean isMultiQNScope(EObject eobj);

	/**
	 * Returns {@code true} if the script allows for the declaration and reference of versioned types as well as
	 * corresponding migrations.
	 */
	public boolean allowVersionedTypes(EObject eobj);

	/**
	 * Returns {@code true} if the script allows for top-level statements as opposed to just type and function
	 * declarations.
	 */
	public boolean allowTopLevelStatements(EObject eobj);

}
