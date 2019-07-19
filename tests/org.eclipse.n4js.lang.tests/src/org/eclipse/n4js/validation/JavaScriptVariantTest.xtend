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
package org.eclipse.n4js.validation

import com.google.inject.Inject
import com.google.inject.Provider
import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.n4JS.StringLiteral
import org.eclipse.n4js.utils.URIUtils
import org.eclipse.xtext.resource.XtextResourceSet
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.util.ParseHelper
import org.junit.Test
import org.junit.runner.RunWith

import static org.junit.Assert.*

/**
 */
@InjectWith(N4JSInjectorProvider)
@RunWith(XtextRunner)
class JavaScriptVariantTest {

	@Inject Provider<XtextResourceSet> resourceSetProvider;
	@Inject extension ParseHelper<Script>

	@Test
	def void testN4JSRecognition() {
		assertVariant(JavaScriptVariant.n4js, "some/test.n4js", '''"here"''');
		assertVariant(JavaScriptVariant.n4js, "some/test.N4JS", '''"here"''');
		assertVariant(JavaScriptVariant.n4js, "some/test.n4js.xt", '''"here"''');
		assertVariant(JavaScriptVariant.n4js, "some/test.n4js", '''
			function foo() {
				"use strict"
				"here"
			}''');
	}

	@Test
	def void testStrictRecognition() {
		assertVariant(JavaScriptVariant.strict, "some/test.js", '''
			"use strict"
			"here"''');
		assertVariant(JavaScriptVariant.strict, "some/test.JS", '''
			"use strict"
			"here"''');
		assertVariant(JavaScriptVariant.strict, "some/test.js.xt", '''
			"use strict"
			"here"''');
		assertVariant(JavaScriptVariant.strict, "some/test.js", '''
			function foo() {
				"use strict"
				"here"
			}''');
		assertVariant(JavaScriptVariant.strict, "some/test.js", '''
			function foo() {
				"nothere"
			}
			function bar() {
				"use strict"
				"here"
			}''');
	}

	@Test
	def void testUnrestrictedRecognition() {
		assertVariant(JavaScriptVariant.unrestricted, "some/test.js", '''
			"here"''');
		assertVariant(JavaScriptVariant.unrestricted, "some/test.JS", '''
			"here"''');
		assertVariant(JavaScriptVariant.unrestricted, "some/test.js.xt", '''
			"here"''');
		assertVariant(JavaScriptVariant.unrestricted, "some/test.js", '''
			function foo() {
				"here"
			}''');
		assertVariant(JavaScriptVariant.unrestricted, "some/test.js", '''
			function foo() {
				"use strict"
				"nothere"
			}
			function bar() {
				"here"
			}''');
	}
	

	/**
	 * Asserts that a given position, marked by a string literal "here", is in expected variant.
	 */
	def assertVariant(JavaScriptVariant expectedVariant, String filePath, CharSequence src) {
		val rs = resourceSetProvider.get
		val script= src.parse(URIUtils.toFileUri(filePath), rs);
		val location = script.eAllContents.filter(StringLiteral).findFirst["here"==it.value];
		assertNotNull("Bogus test, did not find string literal \"here\"")
		val variant = JavaScriptVariant.getVariant(location)
		assertEquals(expectedVariant, variant);
		switch (expectedVariant) {
			case JavaScriptVariant.n4js: {
				assertTrue(JavaScriptVariant.n4js.isActive(location));
				 assertFalse(JavaScriptVariant.strict.isActive(location));
				 assertFalse(JavaScriptVariant.unrestricted.isActive(location));
			}
			case JavaScriptVariant.strict: {
				assertFalse(JavaScriptVariant.n4js.isActive(location));
				assertTrue(JavaScriptVariant.strict.isActive(location));
				assertFalse(JavaScriptVariant.unrestricted.isActive(location));
			}
			case JavaScriptVariant.unrestricted: {
				assertFalse(JavaScriptVariant.n4js.isActive(location));
				assertFalse(JavaScriptVariant.strict.isActive(location));
				assertTrue(JavaScriptVariant.unrestricted.isActive(location));
			}

			default: throw new IllegalArgumentException(expectedVariant.toString)
		}
	}

}
