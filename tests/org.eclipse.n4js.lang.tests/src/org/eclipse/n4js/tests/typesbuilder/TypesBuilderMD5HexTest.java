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
package org.eclipse.n4js.tests.typesbuilder;

import static org.junit.Assert.assertEquals;

import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.N4JSParseHelper;
import org.eclipse.n4js.n4JS.N4JSASTUtils;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

/**
 * Test for static function
 */
@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProvider.class)
public class TypesBuilderMD5HexTest {

	@Inject
	private N4JSParseHelper n4jsParseHelper;

	@SuppressWarnings("javadoc")
	@Test
	public void testMD5Hex() throws Exception {
		final Script script = n4jsParseHelper.parse("let hi = 'Hello World';");
		assertEquals("8e001ae691340ada4e82784c12c62606", N4JSASTUtils.md5Hex((XtextResource) script.eResource()));
	}
}
