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
package org.eclipse.n4js.tests.parser

import com.google.inject.Inject
import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.n4JS.Script
import org.eclipse.xtext.junit4.InjectWith
import org.eclipse.xtext.junit4.XtextRunner
import org.eclipse.xtext.junit4.util.ParseHelper
import org.junit.Test
import org.junit.runner.RunWith

import static org.junit.Assert.*

/*
 *
 * JUnit test (no plugin test)
 */
@InjectWith(N4JSInjectorProvider) // for UI: JSUiIn...., is then a plugin test
@RunWith(XtextRunner)
class N4_04_02_1_GenericsParserTest {

	@Inject
	extension ParseHelper<Script> /* parseHelper -- no name required for that field, is an extension */;

	/*
	 * ParseHelper: only parsing, no linking, no validation
	 * 	parse: returns AST with linking proxies
	 *
	 *
	 */

	@Test
	def void testGenericParser_01() {
		val program = '''var list: List<List<C>>;'''.parse
		assertTrue(program.eResource.errors.toString, program.eResource.errors.empty)
	}

	@Test
	def void testGenericParser_02() {
		val program = '''var list: List<List<List<C>>>;'''.parse
		assertTrue(program.eResource.errors.toString, program.eResource.errors.empty)
	}

}
