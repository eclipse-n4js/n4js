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
package org.eclipse.n4js.formatting2;

import org.eclipse.xtext.formatting2.FormatterPreferenceKeys;
import org.eclipse.xtext.preferences.BooleanKey;
import org.eclipse.xtext.preferences.IntegerKey;

/**
 *
 */
public class N4JSFormatterPreferenceKeys extends FormatterPreferenceKeys {
	/***/
	public static BooleanKey FORMAT_PARENTHESIS = new BooleanKey("format.parenthesis", false);
	/***/
	public static BooleanKey FORMAT_SURROUND_PAREN_CONTENT_WITH_SPACE = new BooleanKey(
			"format.surround_paren_content_with_space", false);
	/***/
	public static IntegerKey FORMAT_MAX_CONSECUTIVE_NEWLINES = new IntegerKey("format.max_consecutive_newlines", 2);
	/***/
	public static BooleanKey FORMAT_SWITCH_CASES_HAVE_SPACE_IN_FRONT_OF_COLON = new BooleanKey(
			"format.switch_cases_have_space_in_front_of_colon", false);
	/***/
	public static BooleanKey FORMAT_AUTO_WRAP_IN_FRONT_OF_LOGICAL_OPERATOR = new BooleanKey(
			"format.auto_wrap_in_front_of_logical_operator", true);
	/**
	 * Considering the code <code>import a, {b,c,d} from "xy";</code> a value of <code>true</code> will render an
	 * additional space after "{" and one before the closing bracket "}" Default value is <code>false</code> and the
	 * line will be rendered as above.
	 */
	public static BooleanKey FORMAT_SURROUND_IMPORT_LIST_WITH_SPACE = new BooleanKey(
			"format.surround_import_list_with_space", false);

}
