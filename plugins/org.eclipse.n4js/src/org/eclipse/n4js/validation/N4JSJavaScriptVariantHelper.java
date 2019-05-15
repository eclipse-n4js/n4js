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
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.n4idl.N4IDLGlobals;

import com.google.inject.Singleton;

/**
 * This class defines the constraints to be checked N4JS and N4JSD.
 *
 */
@Singleton
public class N4JSJavaScriptVariantHelper extends BaseJavaScriptVariantHelper {

	/** N4JSX variant */
	public final static String EXT_N4JSX = N4JSGlobals.N4JSX_FILE_EXTENSION;
	/** JSX variant */
	public final static String EXT_JSX = N4JSGlobals.JSX_FILE_EXTENSION;
	/** N4JS variant */
	public final static String EXT_N4JS = N4JSGlobals.N4JS_FILE_EXTENSION;
	/** N4JSD variant */
	public final static String EXT_N4JSD = N4JSGlobals.N4JSD_FILE_EXTENSION;
	/** N4IDL variant */
	public final static String EXT_N4IDL = N4IDLGlobals.N4IDL_FILE_EXTENSION;

	/**
	 * Constructor
	 */
	public N4JSJavaScriptVariantHelper() {
		this.addEntry(EXT_N4JS, VARIANT_NAME, "N4JS");
		this.addEntry(EXT_N4JSD, VARIANT_NAME, "N4JSD External Definitions Language (n4jsd)");
		this.addEntry(EXT_N4JSX, VARIANT_NAME, "N4JSX");
		this.addEntry(EXT_JSX, VARIANT_NAME, "JSX");
		this.addEntry(EXT_JS, VARIANT_NAME, "JavaScript");
		this.addEntry(EXT_N4IDL, VARIANT_NAME, "N4JS Interface Definition Language (n4idl)");

		this.addEntry(EXT_N4JS, DYNAMIC_PSEUDO_SCOPE, false);
		this.addEntry(EXT_N4JSD, DYNAMIC_PSEUDO_SCOPE, false);
		this.addEntry(EXT_N4JSX, DYNAMIC_PSEUDO_SCOPE, false);
		this.addEntry(EXT_JSX, DYNAMIC_PSEUDO_SCOPE, true);
		this.addEntry(EXT_N4IDL, DYNAMIC_PSEUDO_SCOPE, false);

		this.addEntry(EXT_N4JS, ALLOW_MISSING_IMPLEMENTATION, false);
		this.addEntry(EXT_N4JSD, ALLOW_MISSING_IMPLEMENTATION, true);
		this.addEntry(EXT_N4JSX, ALLOW_MISSING_IMPLEMENTATION, false);
		this.addEntry(EXT_JSX, ALLOW_MISSING_IMPLEMENTATION, false);
		this.addEntry(EXT_N4IDL, ALLOW_MISSING_IMPLEMENTATION, false);

		this.addEntry(EXT_N4JS, CHECK_OVERRIDE_ANNOTATION, true);
		this.addEntry(EXT_N4JSD, CHECK_OVERRIDE_ANNOTATION, true);
		this.addEntry(EXT_N4JSX, CHECK_OVERRIDE_ANNOTATION, true);
		this.addEntry(EXT_JSX, CHECK_OVERRIDE_ANNOTATION, false);
		this.addEntry(EXT_N4IDL, CHECK_OVERRIDE_ANNOTATION, true);

		this.addEntry(EXT_N4JS, CHECK_TYPE_DECLARATION, true);
		this.addEntry(EXT_N4JSD, CHECK_TYPE_DECLARATION, false);
		this.addEntry(EXT_N4JSX, CHECK_TYPE_DECLARATION, true);
		this.addEntry(EXT_JSX, CHECK_TYPE_DECLARATION, false);
		this.addEntry(EXT_N4IDL, CHECK_TYPE_DECLARATION, true);

		this.addEntry(EXT_N4JS, CHECK_MEMBER_DECLARATION, true);
		this.addEntry(EXT_N4JSD, CHECK_MEMBER_DECLARATION, false);
		this.addEntry(EXT_N4JSX, CHECK_MEMBER_DECLARATION, true);
		this.addEntry(EXT_JSX, CHECK_MEMBER_DECLARATION, false);
		this.addEntry(EXT_N4IDL, CHECK_MEMBER_DECLARATION, true);

		this.addEntry(EXT_N4JS, CHECK_VARIABLE, true);
		this.addEntry(EXT_N4JSD, CHECK_VARIABLE, false);
		this.addEntry(EXT_N4JSX, CHECK_VARIABLE, true);
		this.addEntry(EXT_JSX, CHECK_VARIABLE, false);
		this.addEntry(EXT_N4IDL, CHECK_VARIABLE, true);

		this.addEntry(EXT_N4JS, CHECK_METHOD_REFERENCE, true);
		this.addEntry(EXT_N4JSD, CHECK_METHOD_REFERENCE, false);
		this.addEntry(EXT_N4JSX, CHECK_METHOD_REFERENCE, true);
		this.addEntry(EXT_JSX, CHECK_METHOD_REFERENCE, false);
		this.addEntry(EXT_N4IDL, CHECK_METHOD_REFERENCE, true);

		this.addEntry(EXT_N4JS, CHECK_CALL_EXPRESSION, true);
		this.addEntry(EXT_N4JSD, CHECK_CALL_EXPRESSION, true);
		this.addEntry(EXT_N4JSX, CHECK_CALL_EXPRESSION, true);
		this.addEntry(EXT_JSX, CHECK_CALL_EXPRESSION, false);
		this.addEntry(EXT_N4IDL, CHECK_CALL_EXPRESSION, true);

		this.addEntry(EXT_N4JS, CHECK_NEW_EXPRESSION, true);
		this.addEntry(EXT_N4JSD, CHECK_NEW_EXPRESSION, true);
		this.addEntry(EXT_N4JSX, CHECK_NEW_EXPRESSION, true);
		this.addEntry(EXT_JSX, CHECK_NEW_EXPRESSION, false);
		this.addEntry(EXT_N4IDL, CHECK_NEW_EXPRESSION, true);

		this.addEntry(EXT_N4JS, CHECK_INDEXED_ACCESS_EXPRESSION, true);
		this.addEntry(EXT_N4JSD, CHECK_INDEXED_ACCESS_EXPRESSION, false);
		this.addEntry(EXT_N4JSX, CHECK_INDEXED_ACCESS_EXPRESSION, true);
		this.addEntry(EXT_JSX, CHECK_INDEXED_ACCESS_EXPRESSION, false);
		this.addEntry(EXT_N4IDL, CHECK_INDEXED_ACCESS_EXPRESSION, true);

		this.addEntry(EXT_N4JS, CHECK_FUNCTION_NAME, true);
		this.addEntry(EXT_N4JSD, CHECK_FUNCTION_NAME, false);
		this.addEntry(EXT_N4JSX, CHECK_FUNCTION_NAME, true);
		this.addEntry(EXT_JSX, CHECK_FUNCTION_NAME, false);
		this.addEntry(EXT_N4IDL, CHECK_FUNCTION_NAME, true);

		this.addEntry(EXT_N4JS, CHECK_FUNCTION_RETURN, true);
		this.addEntry(EXT_N4JSD, CHECK_FUNCTION_RETURN, true);
		this.addEntry(EXT_N4JSX, CHECK_FUNCTION_RETURN, true);
		this.addEntry(EXT_JSX, CHECK_FUNCTION_RETURN, false);
		this.addEntry(EXT_N4IDL, CHECK_FUNCTION_RETURN, true);

		this.addEntry(EXT_N4JS, CHECK_FUNCTION_EXPRESSION_IN_EXRESSION_STATEMENTMEMBER_DECLARATION, false);
		this.addEntry(EXT_N4JSD, CHECK_FUNCTION_EXPRESSION_IN_EXRESSION_STATEMENTMEMBER_DECLARATION, false);
		this.addEntry(EXT_N4JSX, CHECK_FUNCTION_EXPRESSION_IN_EXRESSION_STATEMENTMEMBER_DECLARATION, false);
		this.addEntry(EXT_JSX, CHECK_FUNCTION_EXPRESSION_IN_EXRESSION_STATEMENTMEMBER_DECLARATION, true);
		this.addEntry(EXT_N4IDL, CHECK_FUNCTION_EXPRESSION_IN_EXRESSION_STATEMENTMEMBER_DECLARATION, false);

		this.addEntry(EXT_N4JS, CONSTANT_HAS_INITIALIZER, true);
		this.addEntry(EXT_N4JSD, CONSTANT_HAS_INITIALIZER, false);
		this.addEntry(EXT_N4JSX, CONSTANT_HAS_INITIALIZER, true);
		this.addEntry(EXT_JSX, CONSTANT_HAS_INITIALIZER, false);
		this.addEntry(EXT_N4IDL, CONSTANT_HAS_INITIALIZER, true);

		this.addEntry(EXT_N4JS, ALLOW_WRONG_READ_WRITE, false);
		this.addEntry(EXT_N4JSD, ALLOW_WRONG_READ_WRITE, false);
		this.addEntry(EXT_N4JSX, ALLOW_WRONG_READ_WRITE, false);
		this.addEntry(EXT_JSX, ALLOW_WRONG_READ_WRITE, true);
		this.addEntry(EXT_N4IDL, ALLOW_WRONG_READ_WRITE, false);

		this.addEntry(EXT_N4JS, DOOM_TYPE_INTERFENCE, false);
		this.addEntry(EXT_N4JSD, DOOM_TYPE_INTERFENCE, false);
		this.addEntry(EXT_N4JSX, DOOM_TYPE_INTERFENCE, false);
		this.addEntry(EXT_JSX, DOOM_TYPE_INTERFENCE, true);
		this.addEntry(EXT_N4IDL, DOOM_TYPE_INTERFENCE, false);

		this.addEntry(EXT_N4JS, ALLOW_ANNOTATION, true);
		this.addEntry(EXT_N4JSD, ALLOW_ANNOTATION, true);
		this.addEntry(EXT_N4JSX, ALLOW_ANNOTATION, true);
		this.addEntry(EXT_JSX, ALLOW_ANNOTATION, false);
		this.addEntry(EXT_N4IDL, ALLOW_ANNOTATION, true);

		this.addEntry(EXT_N4JS, CHECK_FINAL_FIELDS_IS_INITIALIZED, true);
		this.addEntry(EXT_N4JSD, CHECK_FINAL_FIELDS_IS_INITIALIZED, false);
		this.addEntry(EXT_N4JSX, CHECK_FINAL_FIELDS_IS_INITIALIZED, true);
		this.addEntry(EXT_JSX, CHECK_FINAL_FIELDS_IS_INITIALIZED, false);
		this.addEntry(EXT_N4IDL, CHECK_FINAL_FIELDS_IS_INITIALIZED, true);

		this.addEntry(EXT_N4JS, CHECK_NAME_START_WITH_DOLLAR, true);
		this.addEntry(EXT_N4JSD, CHECK_NAME_START_WITH_DOLLAR, false);
		this.addEntry(EXT_N4JSX, CHECK_NAME_START_WITH_DOLLAR, true);
		this.addEntry(EXT_JSX, CHECK_NAME_START_WITH_DOLLAR, false);
		this.addEntry(EXT_N4IDL, CHECK_NAME_START_WITH_DOLLAR, true);

		this.addEntry(EXT_N4JS, CHECK_MISSING_BODY, true);
		this.addEntry(EXT_N4JSD, CHECK_MISSING_BODY, false);
		this.addEntry(EXT_N4JSX, CHECK_MISSING_BODY, true);
		this.addEntry(EXT_JSX, CHECK_MISSING_BODY, false);
		this.addEntry(EXT_N4IDL, CHECK_MISSING_BODY, true);

		this.addEntry(EXT_N4JS, ENFORCE_DYNAMIC_TYPES, false);
		this.addEntry(EXT_N4JSD, ENFORCE_DYNAMIC_TYPES, false);
		this.addEntry(EXT_N4JSX, ENFORCE_DYNAMIC_TYPES, false);
		this.addEntry(EXT_JSX, ENFORCE_DYNAMIC_TYPES, true);
		this.addEntry(EXT_N4IDL, ENFORCE_DYNAMIC_TYPES, false);

		this.addEntry(EXT_N4JS, TYPE_AWARE, true);
		this.addEntry(EXT_N4JSD, TYPE_AWARE, false);
		this.addEntry(EXT_N4JSX, TYPE_AWARE, true);
		this.addEntry(EXT_JSX, TYPE_AWARE, false);
		this.addEntry(EXT_N4IDL, TYPE_AWARE, true);

		this.addEntry(EXT_N4JS, CHECK_EXPORTED_WHEN_VISIBILITY_HIGHER_THAN_PRIVATE, true);
		this.addEntry(EXT_N4JSD, CHECK_EXPORTED_WHEN_VISIBILITY_HIGHER_THAN_PRIVATE, true);
		this.addEntry(EXT_N4JSX, CHECK_EXPORTED_WHEN_VISIBILITY_HIGHER_THAN_PRIVATE, true);
		this.addEntry(EXT_JSX, CHECK_EXPORTED_WHEN_VISIBILITY_HIGHER_THAN_PRIVATE, false);
		this.addEntry(EXT_N4IDL, CHECK_EXPORTED_WHEN_VISIBILITY_HIGHER_THAN_PRIVATE, true);

		this.addEntry(EXT_N4JS, EXTERNAL_MODE, false);
		this.addEntry(EXT_N4JSD, EXTERNAL_MODE, true);
		this.addEntry(EXT_N4JSX, EXTERNAL_MODE, false);
		this.addEntry(EXT_JSX, EXTERNAL_MODE, false);
		this.addEntry(EXT_N4IDL, EXTERNAL_MODE, false);

		this.addEntry(EXT_N4JS, IS_N4JS_MODE, true);
		this.addEntry(EXT_N4JSD, IS_N4JS_MODE, false);
		this.addEntry(EXT_N4JSX, IS_N4JS_MODE, true);
		this.addEntry(EXT_JSX, IS_N4JS_MODE, false);
		this.addEntry(EXT_N4IDL, IS_N4JS_MODE, true);

		this.addEntry(EXT_N4JS, IS_PLAIN_JS, false);
		this.addEntry(EXT_N4JSD, IS_PLAIN_JS, false);
		this.addEntry(EXT_N4JSX, IS_PLAIN_JS, false);
		this.addEntry(EXT_JSX, IS_PLAIN_JS, true);
		this.addEntry(EXT_N4IDL, IS_PLAIN_JS, false);

		this.addEntry(EXT_N4JS, VARIANT_MODE_STRINGREP, EXT_N4JS);
		this.addEntry(EXT_N4JSD, VARIANT_MODE_STRINGREP, EXT_N4JSD);
		this.addEntry(EXT_N4JSX, VARIANT_MODE_STRINGREP, EXT_N4JSX);
		this.addEntry(EXT_JSX, VARIANT_MODE_STRINGREP, EXT_JSX);
		this.addEntry(EXT_N4IDL, VARIANT_MODE_STRINGREP, EXT_N4IDL);

		this.addEntry(EXT_N4JS, MULTI_QN_SCOPE, false);
		this.addEntry(EXT_N4JSD, MULTI_QN_SCOPE, false);
		this.addEntry(EXT_N4JSX, MULTI_QN_SCOPE, false);
		this.addEntry(EXT_JSX, MULTI_QN_SCOPE, false);
		this.addEntry(EXT_N4IDL, MULTI_QN_SCOPE, true);

		this.addEntry(EXT_N4JS, VERSIONED_TYPES, false);
		this.addEntry(EXT_N4JSD, VERSIONED_TYPES, false);
		this.addEntry(EXT_N4JSX, VERSIONED_TYPES, false);
		this.addEntry(EXT_JSX, VERSIONED_TYPES, false);
		this.addEntry(EXT_N4IDL, VERSIONED_TYPES, true);

		this.addEntry(EXT_N4JS, TOP_LEVEL_STATEMENTS, true);
		this.addEntry(EXT_N4JSD, TOP_LEVEL_STATEMENTS, true);
		this.addEntry(EXT_N4JSX, TOP_LEVEL_STATEMENTS, true);
		this.addEntry(EXT_JSX, TOP_LEVEL_STATEMENTS, true);
		this.addEntry(EXT_N4IDL, TOP_LEVEL_STATEMENTS, false);
	}

	/**
	 * Return true if in strict mode
	 */
	@Override
	public boolean isStrictMode(EObject eobj) {
		String fileExt = fileExtensionCalculator.getXpectAwareFileExtension(eobj);
		if (EXT_N4JS.equals(fileExt) || EXT_N4JSD.equals(fileExt) || EXT_N4JSX.equals(fileExt)
				|| EXT_N4IDL.equals(fileExt)) {
			// N4JS, N4JSX, N4JSD and N4IDL are strict mode
			return true;
		} else
			return super.isStrictMode(eobj);
	}

}
