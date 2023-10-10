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

public abstract class AbstractRegexExampleTest extends Assert {

	protected abstract void assertValid(CharSequence expression);

	@Test
	public void testSampleFromJSREGEXcom() {
		assertValid("/[Tt]he\\sr\\w+/");
	}

	@Test
	public void testFiveDigits() {
		assertValid("/^\\d{5}$/");
	}

	@Test
	public void testDigits_01() {
		assertValid("/\\d+/g");
	}

	@Test
	public void testDigits_02() {
		assertValid("/[\\(\\)-]/g");
	}

	@Test
	public void testCommaWithWS() {
		assertValid("/\\s*,\\s*/");
	}

	@Test
	public void testTwoWords() {
		assertValid("/(\\w+)\\s(\\w+)/");
	}

	@Test
	public void testTwoWords_02() {
		assertValid("/(\\w.+)\\s(\\w.+)/");
	}

	@Test
	public void testValidNumber() {
		assertValid("/(^-*\\d+$)|(^-*\\d+\\.\\d+$)/");
	}

	@Test
	public void testValidDateFormat() {
		assertValid("/^\\d{1,2}(\\-|\\/|\\.)\\d{1,2}\\1\\d{4}$/");
	}

	@Test
	public void testHtmlTags() {
		assertValid("/(<)|(>)/g");
	}

	@Test
	public void testSelfhtml() {
		assertValid("/(http:\\/\\/\\S*)/g");
	}

}
