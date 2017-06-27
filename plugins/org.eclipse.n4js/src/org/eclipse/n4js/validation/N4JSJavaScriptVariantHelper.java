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

import com.google.inject.Singleton;

import org.eclipse.n4js.N4JSGlobals;

/**
 * This class defines the constraints to be checked N4JS and N4JSD.
 *
 */
@Singleton
public class N4JSJavaScriptVariantHelper extends BaseJavaScriptVariantHelper {

	private final static String EXT_N4JS = N4JSGlobals.N4JS_FILE_EXTENSION;
	private final static String EXT_N4JSD = N4JSGlobals.N4JSD_FILE_EXTENSION;

	/**
	 * Constructor
	 */
	public N4JSJavaScriptVariantHelper() {
		this.addEntry(EXT_N4JS, DYNAMIC_PSEUDO_SCOPE, false);
		this.addEntry(EXT_N4JSD, DYNAMIC_PSEUDO_SCOPE, false);

		this.addEntry(EXT_N4JS, ALLOW_MISSING_IMPLEMENTATION, false);
		this.addEntry(EXT_N4JSD, ALLOW_MISSING_IMPLEMENTATION, true);

		this.addEntry(EXT_N4JS, CHECK_OVERRIDE_ANNOTATION, true);
		this.addEntry(EXT_N4JSD, CHECK_OVERRIDE_ANNOTATION, true);

		this.addEntry(EXT_N4JS, CHECK_TYPE_DECLARATION, true);
		this.addEntry(EXT_N4JSD, CHECK_TYPE_DECLARATION, false);

		this.addEntry(EXT_N4JS, CHECK_MEMBER_DECLARATION, true);
		this.addEntry(EXT_N4JSD, CHECK_MEMBER_DECLARATION, false);

		this.addEntry(EXT_N4JS, CHECK_VARIABLE, true);
		this.addEntry(EXT_N4JSD, CHECK_VARIABLE, false);

		this.addEntry(EXT_N4JS, CHECK_METHOD_REFERENCE, true);
		this.addEntry(EXT_N4JSD, CHECK_METHOD_REFERENCE, false);

		this.addEntry(EXT_N4JS, CHECK_CALL_EXPRESSION, true);
		this.addEntry(EXT_N4JSD, CHECK_CALL_EXPRESSION, true);

		this.addEntry(EXT_N4JS, CHECK_NEW_EXPRESSION, true);
		this.addEntry(EXT_N4JSD, CHECK_NEW_EXPRESSION, true);

		this.addEntry(EXT_N4JS, CHECK_INDEXED_ACCESS_EXPRESSION, true);
		this.addEntry(EXT_N4JSD, CHECK_INDEXED_ACCESS_EXPRESSION, false);

		this.addEntry(EXT_N4JS, CHECK_FUNCTION_NAME, true);
		this.addEntry(EXT_N4JSD, CHECK_FUNCTION_NAME, false);

		this.addEntry(EXT_N4JS, CHECK_FUNCTION_RETURN, true);
		this.addEntry(EXT_N4JSD, CHECK_FUNCTION_RETURN, true);

		this.addEntry(EXT_N4JS, CHECK_FUNCTION_EXPRESSION_IN_EXRESSION_STATEMENTMEMBER_DECLARATION, false);
		this.addEntry(EXT_N4JSD, CHECK_FUNCTION_EXPRESSION_IN_EXRESSION_STATEMENTMEMBER_DECLARATION, false);

		this.addEntry(EXT_N4JS, CONSTANT_HAS_INITIALIZER, true);
		this.addEntry(EXT_N4JSD, CONSTANT_HAS_INITIALIZER, false);

		this.addEntry(EXT_N4JS, CHECK_NO_N4JS_IN_RUNTIME_ENV_OR_LIB, true);
		this.addEntry(EXT_N4JSD, CHECK_NO_N4JS_IN_RUNTIME_ENV_OR_LIB, false);

		this.addEntry(EXT_N4JS, ALLOW_WRONG_READ_WRITE, false);
		this.addEntry(EXT_N4JSD, ALLOW_WRONG_READ_WRITE, false);

		this.addEntry(EXT_N4JS, DOOM_TYPE_INTERFENCE, false);
		this.addEntry(EXT_N4JSD, DOOM_TYPE_INTERFENCE, false);

		this.addEntry(EXT_N4JS, ALLOW_ANNOTATION, true);
		this.addEntry(EXT_N4JSD, ALLOW_ANNOTATION, true);

		this.addEntry(EXT_N4JS, CHECK_FINAL_FIELDS_IS_INITIALIZED, true);
		this.addEntry(EXT_N4JSD, CHECK_FINAL_FIELDS_IS_INITIALIZED, false);

		this.addEntry(EXT_N4JS, CHECK_NAME_START_WITH_DOLLAR, true);
		this.addEntry(EXT_N4JSD, CHECK_NAME_START_WITH_DOLLAR, false);

		this.addEntry(EXT_N4JS, CHECK_MISSING_BODY, true);
		this.addEntry(EXT_N4JSD, CHECK_MISSING_BODY, false);

		this.addEntry(EXT_N4JS, ENFORCE_DYNAMIC_TYPES, false);
		this.addEntry(EXT_N4JSD, ENFORCE_DYNAMIC_TYPES, false);

		this.addEntry(EXT_N4JS, TYPE_AWARE, true);
		this.addEntry(EXT_N4JSD, TYPE_AWARE, false);

		this.addEntry(EXT_N4JS, CHECK_EXPORTED_WHEN_VISIBILITY_HIGHER_THAN_PRIVATE, true);
		this.addEntry(EXT_N4JSD, CHECK_EXPORTED_WHEN_VISIBILITY_HIGHER_THAN_PRIVATE, true);

		this.addEntry(EXT_N4JS, EXTERNAL_MODE, false);
		this.addEntry(EXT_N4JSD, EXTERNAL_MODE, true);

		this.addEntry(EXT_N4JS, IS_N4JS_MODE, true);
		this.addEntry(EXT_N4JSD, IS_N4JS_MODE, false);

		this.addEntry(EXT_N4JS, IS_PLAIN_JS, false);
		this.addEntry(EXT_N4JSD, IS_PLAIN_JS, false);

		this.addEntry(EXT_N4JS, VARIANT_MODE_STRINGREP, EXT_N4JS);
		this.addEntry(EXT_N4JSD, VARIANT_MODE_STRINGREP, EXT_N4JSD);
	}

	/**
	 * Return true if in strict mode
	 */
	@Override
	public boolean isStrictMode(EObject eobj) {
		String fileExt = fileExtensionCalculator.getXpectAwareFileExtension(eobj);
		if (EXT_N4JS.equals(fileExt) || EXT_N4JSD.equals(fileExt)) {
			// Both N4JS and N4JSD are strict mode
			return true;
		} else
			return super.isStrictMode(eobj);
	}

}
