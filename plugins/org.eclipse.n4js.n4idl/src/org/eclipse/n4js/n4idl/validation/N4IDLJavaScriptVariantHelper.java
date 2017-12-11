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
package org.eclipse.n4js.n4idl.validation;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.n4idl.N4IDLGlobals;
import org.eclipse.n4js.validation.BaseJavaScriptVariantHelper;
import org.eclipse.n4js.validation.N4JSJavaScriptVariantHelper;

import com.google.inject.Singleton;

/**
 * This class defines the validation constraints to be checked for N4IDL and IDL languages.
 */
@Singleton
public class N4IDLJavaScriptVariantHelper extends BaseJavaScriptVariantHelper {

	// TODO align with N4JSGlobals
	/*
	 * This should be aligned with file extensions in N4JSGlobals (all supported file extensions should be defined
	 * there), or N4JSGlobals should be refactored and not contain file extensions for sub languages at all (i.e. it
	 * should not contain N4JSX).
	 */
	private final static String EXT_N4IDL = N4IDLGlobals.N4IDL_FILE_EXTENSION;
	private final static String EXT_IDL = N4IDLGlobals.IDL_FILE_EXTENSION;

	/**
	 * Initialize an instance of {@link N4JSJavaScriptVariantHelper} as successor in the chain of responsibility.
	 */
	public N4IDLJavaScriptVariantHelper() {
		this.next = new N4JSJavaScriptVariantHelper();
		this.addEntry(EXT_N4IDL, DYNAMIC_PSEUDO_SCOPE, false);
		this.addEntry(EXT_IDL, DYNAMIC_PSEUDO_SCOPE, true);

		this.addEntry(EXT_N4IDL, ALLOW_MISSING_IMPLEMENTATION, false);
		this.addEntry(EXT_IDL, ALLOW_MISSING_IMPLEMENTATION, false);

		this.addEntry(EXT_N4IDL, CHECK_OVERRIDE_ANNOTATION, true);
		this.addEntry(EXT_IDL, CHECK_OVERRIDE_ANNOTATION, false);

		this.addEntry(EXT_N4IDL, CHECK_TYPE_DECLARATION, true);
		this.addEntry(EXT_IDL, CHECK_TYPE_DECLARATION, false);

		this.addEntry(EXT_N4IDL, CHECK_MEMBER_DECLARATION, true);
		this.addEntry(EXT_IDL, CHECK_MEMBER_DECLARATION, false);

		this.addEntry(EXT_N4IDL, CHECK_VARIABLE, true);
		this.addEntry(EXT_IDL, CHECK_VARIABLE, false);

		this.addEntry(EXT_N4IDL, CHECK_METHOD_REFERENCE, true);
		this.addEntry(EXT_IDL, CHECK_METHOD_REFERENCE, false);

		this.addEntry(EXT_N4IDL, CHECK_CALL_EXPRESSION, true);
		this.addEntry(EXT_IDL, CHECK_CALL_EXPRESSION, false);

		this.addEntry(EXT_N4IDL, CHECK_NEW_EXPRESSION, true);
		this.addEntry(EXT_IDL, CHECK_NEW_EXPRESSION, false);

		this.addEntry(EXT_N4IDL, CHECK_INDEXED_ACCESS_EXPRESSION, true);
		this.addEntry(EXT_IDL, CHECK_INDEXED_ACCESS_EXPRESSION, false);

		this.addEntry(EXT_N4IDL, CHECK_FUNCTION_NAME, true);
		this.addEntry(EXT_IDL, CHECK_FUNCTION_NAME, false);

		this.addEntry(EXT_N4IDL, CHECK_FUNCTION_RETURN, true);
		this.addEntry(EXT_IDL, CHECK_FUNCTION_RETURN, false);

		this.addEntry(EXT_N4IDL, CHECK_FUNCTION_EXPRESSION_IN_EXRESSION_STATEMENTMEMBER_DECLARATION, false);
		this.addEntry(EXT_IDL, CHECK_FUNCTION_EXPRESSION_IN_EXRESSION_STATEMENTMEMBER_DECLARATION, true);

		this.addEntry(EXT_N4IDL, CONSTANT_HAS_INITIALIZER, true);
		this.addEntry(EXT_IDL, CONSTANT_HAS_INITIALIZER, false);

		this.addEntry(EXT_N4IDL, CHECK_NO_N4JS_IN_RUNTIME_ENV_OR_LIB, true);
		this.addEntry(EXT_IDL, CHECK_NO_N4JS_IN_RUNTIME_ENV_OR_LIB, false);

		this.addEntry(EXT_N4IDL, ALLOW_WRONG_READ_WRITE, false);
		this.addEntry(EXT_IDL, ALLOW_WRONG_READ_WRITE, true);

		this.addEntry(EXT_N4IDL, DOOM_TYPE_INTERFENCE, false);
		this.addEntry(EXT_IDL, DOOM_TYPE_INTERFENCE, true);

		this.addEntry(EXT_N4IDL, ALLOW_ANNOTATION, true);
		this.addEntry(EXT_IDL, ALLOW_ANNOTATION, false);

		this.addEntry(EXT_N4IDL, CHECK_FINAL_FIELDS_IS_INITIALIZED, true);
		this.addEntry(EXT_IDL, CHECK_FINAL_FIELDS_IS_INITIALIZED, false);

		this.addEntry(EXT_N4IDL, CHECK_NAME_START_WITH_DOLLAR, true);
		this.addEntry(EXT_IDL, CHECK_NAME_START_WITH_DOLLAR, false);

		this.addEntry(EXT_N4IDL, CHECK_MISSING_BODY, true);
		this.addEntry(EXT_IDL, CHECK_MISSING_BODY, false);

		this.addEntry(EXT_N4IDL, ENFORCE_DYNAMIC_TYPES, false);
		this.addEntry(EXT_IDL, ENFORCE_DYNAMIC_TYPES, true);

		this.addEntry(EXT_N4IDL, TYPE_AWARE, true);
		this.addEntry(EXT_IDL, TYPE_AWARE, false);

		this.addEntry(EXT_N4IDL, CHECK_EXPORTED_WHEN_VISIBILITY_HIGHER_THAN_PRIVATE, true);
		this.addEntry(EXT_IDL, CHECK_EXPORTED_WHEN_VISIBILITY_HIGHER_THAN_PRIVATE, false);

		this.addEntry(EXT_N4IDL, EXTERNAL_MODE, false);
		this.addEntry(EXT_IDL, EXTERNAL_MODE, false);

		this.addEntry(EXT_N4IDL, IS_N4JS_MODE, true);
		this.addEntry(EXT_IDL, IS_N4JS_MODE, false);

		this.addEntry(EXT_N4IDL, IS_PLAIN_JS, false);
		this.addEntry(EXT_IDL, IS_PLAIN_JS, true);

		this.addEntry(EXT_N4IDL, VARIANT_MODE_STRINGREP, EXT_N4IDL);
		this.addEntry(EXT_IDL, VARIANT_MODE_STRINGREP, EXT_IDL);
	}

	/**
	 * Return true if EObject is contained within a module with strict mode. This method explicitly states that N4IDL is
	 * strict mode.
	 *
	 * @param eobj
	 *            the input EObject
	 */
	@Override
	public boolean isStrictMode(EObject eobj) {
		String fileExt = fileExtensionCalculator.getXpectAwareFileExtension(eobj);
		if (EXT_N4IDL.equals(fileExt)) {
			// N4IDL is strict mode
			return true;
		} else {
			return super.isStrictMode(eobj);
		}
	}
}
