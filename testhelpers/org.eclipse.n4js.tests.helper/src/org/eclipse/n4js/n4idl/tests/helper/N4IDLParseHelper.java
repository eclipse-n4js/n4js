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
package org.eclipse.n4js.n4idl.tests.helper;

import org.eclipse.n4js.N4JSParseHelper;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.validation.JavaScriptVariant;
import org.junit.Assert;

import com.google.common.base.Joiner;

/**
 * Replacement for ParseHelper in order to define mode in which file is parsed, also see {@link N4IDLParseHelper}.
 */
public class N4IDLParseHelper extends N4JSParseHelper {

	/**
	 * Parses n4idl file (using n4idl as file extension).
	 */
	public Script parseN4IDL(CharSequence text) throws Exception {
		return parse(text, JavaScriptVariant.n4idl);
	}

	/**
	 * Asserts that the given script does not have any parse errors. Checks only for parse errors, does not perform a
	 * validation of the given script.
	 */
	@Override
	public void assertNoParseErrors(Script script) {
		Assert.assertTrue(Joiner.on('\n').join(script.eResource().getErrors()),
				script.eResource().getErrors().isEmpty());
	}
}
