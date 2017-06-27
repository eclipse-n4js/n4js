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
package org.eclipse.n4js.n4jsx.validation;

import org.eclipse.emf.ecore.EObject;

import com.google.inject.Singleton;

import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.validation.BaseJavaScriptVariantHelper;
import org.eclipse.n4js.validation.N4JSJavaScriptVariantHelper;

/**
 * This class provides the constraints to be checked for N4JSX and JSX sub-languages. If the files do not end with
 * .n4jsx or .jsx, the behavior the same as N4JS's default behavior defined in {@link N4JSJavaScriptVariantHelper}
 */
@Singleton
public class N4JSXJavaScriptVariantHelper extends BaseJavaScriptVariantHelper {

	private final static String EXT_N4JSX = N4JSGlobals.N4JSX_FILE_EXTENSION;
	private final static String EXT_JSX = N4JSGlobals.JSX_FILE_EXTENSION;

	/**
	 * Initialize an instance of {@link N4JSJavaScriptVariantHelper} as successor in the chain of responsibility
	 */
	public N4JSXJavaScriptVariantHelper() {
		this.next = new N4JSJavaScriptVariantHelper();
		this.addEntry(EXT_N4JSX, DYNAMIC_PSEUDO_SCOPE, false);
		this.addEntry(EXT_JSX, DYNAMIC_PSEUDO_SCOPE, true);

		this.addEntry(EXT_N4JSX, ALLOW_MISSING_IMPLEMENTATION, false);
		this.addEntry(EXT_JSX, ALLOW_MISSING_IMPLEMENTATION, false);

		this.addEntry(EXT_N4JSX, CHECK_OVERRIDE_ANNOTATION, true);
		this.addEntry(EXT_JSX, CHECK_OVERRIDE_ANNOTATION, false);

		this.addEntry(EXT_N4JSX, CHECK_TYPE_DECLARATION, true);
		this.addEntry(EXT_JSX, CHECK_TYPE_DECLARATION, false);

		this.addEntry(EXT_N4JSX, CHECK_MEMBER_DECLARATION, true);
		this.addEntry(EXT_JSX, CHECK_MEMBER_DECLARATION, false);

		this.addEntry(EXT_N4JSX, CHECK_VARIABLE, true);
		this.addEntry(EXT_JSX, CHECK_VARIABLE, false);

		this.addEntry(EXT_N4JSX, CHECK_METHOD_REFERENCE, true);
		this.addEntry(EXT_JSX, CHECK_METHOD_REFERENCE, false);

		this.addEntry(EXT_N4JSX, CHECK_CALL_EXPRESSION, true);
		this.addEntry(EXT_JSX, CHECK_CALL_EXPRESSION, false);

		this.addEntry(EXT_N4JSX, CHECK_NEW_EXPRESSION, true);
		this.addEntry(EXT_JSX, CHECK_NEW_EXPRESSION, false);

		this.addEntry(EXT_N4JSX, CHECK_INDEXED_ACCESS_EXPRESSION, true);
		this.addEntry(EXT_JSX, CHECK_INDEXED_ACCESS_EXPRESSION, false);

		this.addEntry(EXT_N4JSX, CHECK_FUNCTION_NAME, true);
		this.addEntry(EXT_JSX, CHECK_FUNCTION_NAME, false);

		this.addEntry(EXT_N4JSX, CHECK_FUNCTION_RETURN, true);
		this.addEntry(EXT_JSX, CHECK_FUNCTION_RETURN, false);

		this.addEntry(EXT_N4JSX, CHECK_FUNCTION_EXPRESSION_IN_EXRESSION_STATEMENTMEMBER_DECLARATION, false);
		this.addEntry(EXT_JSX, CHECK_FUNCTION_EXPRESSION_IN_EXRESSION_STATEMENTMEMBER_DECLARATION, true);

		this.addEntry(EXT_N4JSX, CONSTANT_HAS_INITIALIZER, true);
		this.addEntry(EXT_JSX, CONSTANT_HAS_INITIALIZER, false);

		this.addEntry(EXT_N4JSX, CHECK_NO_N4JS_IN_RUNTIME_ENV_OR_LIB, true);
		this.addEntry(EXT_JSX, CHECK_NO_N4JS_IN_RUNTIME_ENV_OR_LIB, false);

		this.addEntry(EXT_N4JSX, ALLOW_WRONG_READ_WRITE, false);
		this.addEntry(EXT_JSX, ALLOW_WRONG_READ_WRITE, true);

		this.addEntry(EXT_N4JSX, DOOM_TYPE_INTERFENCE, false);
		this.addEntry(EXT_JSX, DOOM_TYPE_INTERFENCE, true);

		this.addEntry(EXT_N4JSX, ALLOW_ANNOTATION, true);
		this.addEntry(EXT_JSX, ALLOW_ANNOTATION, false);

		this.addEntry(EXT_N4JSX, CHECK_FINAL_FIELDS_IS_INITIALIZED, true);
		this.addEntry(EXT_JSX, CHECK_FINAL_FIELDS_IS_INITIALIZED, false);

		this.addEntry(EXT_N4JSX, CHECK_NAME_START_WITH_DOLLAR, true);
		this.addEntry(EXT_JSX, CHECK_NAME_START_WITH_DOLLAR, false);

		this.addEntry(EXT_N4JSX, CHECK_MISSING_BODY, true);
		this.addEntry(EXT_JSX, CHECK_MISSING_BODY, false);

		this.addEntry(EXT_N4JSX, ENFORCE_DYNAMIC_TYPES, false);
		this.addEntry(EXT_JSX, ENFORCE_DYNAMIC_TYPES, true);

		this.addEntry(EXT_N4JSX, TYPE_AWARE, true);
		this.addEntry(EXT_JSX, TYPE_AWARE, false);

		this.addEntry(EXT_N4JSX, CHECK_EXPORTED_WHEN_VISIBILITY_HIGHER_THAN_PRIVATE, true);
		this.addEntry(EXT_JSX, CHECK_EXPORTED_WHEN_VISIBILITY_HIGHER_THAN_PRIVATE, false);

		this.addEntry(EXT_N4JSX, EXTERNAL_MODE, false);
		this.addEntry(EXT_JSX, EXTERNAL_MODE, false);

		this.addEntry(EXT_N4JSX, IS_N4JS_MODE, true);
		this.addEntry(EXT_JSX, IS_N4JS_MODE, false);

		this.addEntry(EXT_N4JSX, IS_PLAIN_JS, false);
		this.addEntry(EXT_JSX, IS_PLAIN_JS, true);

		this.addEntry(EXT_N4JSX, VARIANT_MODE_STRINGREP, EXT_N4JSX);
		this.addEntry(EXT_JSX, VARIANT_MODE_STRINGREP, EXT_JSX);
	}

	/**
	 * Return true if EObject is contained within a module with strict mode. This method explicitly states that N4JSX is
	 * strict mode.
	 *
	 * @param eobj
	 *            the input EObject
	 */
	@Override
	public boolean isStrictMode(EObject eobj) {
		String fileExt = fileExtensionCalculator.getXpectAwareFileExtension(eobj);
		if (EXT_N4JSX.equals(fileExt)) {
			// N4JSX is strict mode
			return true;
		} else {
			return super.isStrictMode(eobj);
		}
	}
}
