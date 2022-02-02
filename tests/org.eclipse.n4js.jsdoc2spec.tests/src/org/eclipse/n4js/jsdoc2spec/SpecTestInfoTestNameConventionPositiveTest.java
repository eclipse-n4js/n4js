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
package org.eclipse.n4js.jsdoc2spec;

import java.util.Arrays;
import java.util.Collection;

import org.eclipse.xtext.naming.QualifiedName;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

/**
 * Positive tests matching:
 *
 * <pre>
 * testName    ::= ('test')? ('_'? method '__')? title ('___' case)?
 * description ::= <'_' separated>
 * case        ::= <'_' separated>
 * </pre>
 */
@RunWith(Parameterized.class)
public class SpecTestInfoTestNameConventionPositiveTest {

	/**
	 * Returns test data.
	 */
	@Parameters(name = "Spec Test Convention Positive {index}: {0}")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] { //
				{ "testmethod__title___case", new String[] { "method", "title", "case" } },
				{ "method__title___case", new String[] { "method", "title", "case" } },
				{ "testtitle___case", new String[] { null, "title", "case" } },
				{ "title___case", new String[] { null, "title", "case" } },
				{ "testtitle", new String[] { null, "title", null } },
				{ "title", new String[] { null, "title", null } },
				{ "testmethod__title", new String[] { "method", "title", null } },
				{ "method__title", new String[] { "method", "title", null } },
				{
						"testmethod_with_underscore__title_with_underscore___case_with_underscore",
						new String[] { "method_with_underscore", "title_with_underscore",
								"case_with_underscore" } },
				{
						"method_with_underscore__title_with_underscore___case_with_underscore",
						new String[] { "method_with_underscore", "title_with_underscore",
								"case_with_underscore" } },
				{
						"test_method_with_underscore__title_with_underscore___case_with_underscore",
						new String[] { "method_with_underscore", "title_with_underscore",
								"case_with_underscore" } },
				{
						"_method_with_underscore__title_with_underscore___case_with_underscore",
						new String[] { "method_with_underscore", "title_with_underscore",
								"case_with_underscore" } },
				{ "testEachOnEmptyWithNullCallbackExpectError",
						new String[] { null, "EachOnEmptyWithNullCallbackExpectError", null }
				},
				{ "method__1_title___case", new String[] { "method", "1_title", "case" } },
				{ "_1_title___case", new String[] { null, "1_title", "case" } },
				{ "_1_null_and_undefined___1_undefined",
						new String[] { null, "1_null_and_undefined", "1_undefined" } }
		});
	}

	/** Injected input = full name */
	@Parameter(value = 0)
	public String input;
	/** Inject expected values */
	@Parameter(value = 1)
	public String[] expected;

	private final static String[] ARGNAME = { "Method", "Title", "Case" };

	/**
	 * Positive tests
	 */
	@Test
	public void test() {
		String[] actual = SpecTestInfo.parseName(QualifiedName.create(input));
		for (int i = 0; i < 3; i++) {
			Assert.assertEquals(ARGNAME[i] + " failed", expected[i], actual[i]);
		}

	}
}
