/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js;

import org.eclipse.xtext.junit4.validation.ValidationTestHelper;

import com.google.inject.Inject;

import org.eclipse.n4js.n4JS.Script;

/**
 * Broad, top-level helper methods for testing N4JS. For more fine-grained helper functionality see classes such as
 * {@link N4JSParseHelper}, {@link N4JSValidationTestHelper}, etc.
 */
public class N4JSTestHelper {

	@Inject
	private N4JSParseHelper parseHelper;
	@Inject
	private ValidationTestHelper validationTestHelper;

	/**
	 * Parse & validate the given code, assert that there are no parser or validation errors, and return the root of the
	 * fully resolved (i.e. types builder, post-processing, etc.) AST.
	 */
	public Script parseAndValidateSuccessfully(CharSequence code) throws Exception {
		final Script script = parseHelper.parseN4js(code);
		parseHelper.assertNoParseErrors(script);
		validationTestHelper.assertNoErrors(script); // this will trigger post-processing, validation, etc.
		return script;
	}
}
