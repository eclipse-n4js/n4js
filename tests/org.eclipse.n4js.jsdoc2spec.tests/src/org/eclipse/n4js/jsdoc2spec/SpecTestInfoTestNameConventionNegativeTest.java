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
public class SpecTestInfoTestNameConventionNegativeTest {

	/**
	 * Returns test data.
	 */
	@Parameters(name = "Spec Test Convention Negative {index}: {0}")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] { //
				{ "testmethod__title__case" },
				{ "method__title___case__" },
				{ "__title" },
				{ "___title" }

		});
	}

	/** Injected input = full name */
	@Parameter(value = 0)
	public String input;

	/**
	 * Negative tests
	 */
	@Test(expected = IllegalArgumentException.class)
	public void test() {
		SpecTestInfo.parseName(QualifiedName.create(input));
	}
}
