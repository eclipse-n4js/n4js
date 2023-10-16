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
package org.eclipse.n4js.tests.parser;

import static org.junit.Assert.assertTrue;

import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

/**
 * JUnit test (no plugin test)
 */
@InjectWith(N4JSInjectorProvider.class) // for UI: JSUiIn...., is then a plugin test
@RunWith(XtextRunner.class)
public class N4_04_02_1_GenericsParserTest {

	@Inject
	ParseHelper<Script> parseHelper;

	/**
	 * ParseHelper: only parsing, no linking, no validation parse: returns AST with linking proxies
	 */
	@Test
	public void testGenericParser_01() throws Exception {
		Script program = parseHelper.parse("var list: List<List<C>>;");
		assertTrue(program.eResource().getErrors().toString(), program.eResource().getErrors().isEmpty());
	}

	@Test
	public void testGenericParser_02() throws Exception {
		Script program = parseHelper.parse("var list: List<List<List<C>>>;");
		assertTrue(program.eResource().getErrors().toString(), program.eResource().getErrors().isEmpty());
	}

}
