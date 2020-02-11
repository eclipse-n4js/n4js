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
package org.eclipse.n4js.regex.tests

import org.junit.Assert
import org.junit.Test


abstract class AbstractErrorTests extends Assert {

	def void assertInvalid(CharSequence expression)

	@Test
	def void testAssertionWithQuantifier() {
		'/^*/'.assertInvalid
	}

	@Test
	def void testUnclosedCharacterLiteral() {
		'/[/'.assertInvalid
	}

	@Test
	def void testQuantifierOnLookAhead() {
		'/(?=a)+/'.assertInvalid
	}

	@Test
	def void testWikipedia() {
		'''/(?<|=\.) {2,}(?=[A-Z])/'''.assertInvalid
	}

	@Test
	def void test_01() {
		'''/(?)/'''.assertInvalid
	}
}
