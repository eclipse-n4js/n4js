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
package org.eclipse.n4js.regex.tests;

import org.junit.Assert;
import org.junit.Test;

public abstract class AbstractRegexErrorTest extends Assert {

	protected abstract void assertInvalid(CharSequence expression);

	@Test
	public void testAssertionWithQuantifier() {
		assertInvalid("/^*/");
	}

	@Test
	public void testUnclosedCharacterLiteral() {
		assertInvalid("/[/");
	}

	@Test
	public void testQuantifierOnLookAhead() {
		assertInvalid("/(?=a)+/");
	}

	@Test
	public void testWikipedia() {
		assertInvalid("/(?<|=\\.) {2,}(?=[A-Z])/");
	}

	@Test
	public void test_01() {
		assertInvalid("/(?)/");
	}
}
