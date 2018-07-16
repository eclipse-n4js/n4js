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
import org.eclipse.n4js.n4JS.VariableStatement
import org.eclipse.n4js.services.N4JSGrammarAccess
import org.junit.Test

class N4_09_03_1_ConstVariableDeclarationsWithConst extends AbstractParserTest {

	@Test
	def void testSimpleVar() {
		val script = 'var x = 1'.parseESSuccessfully
		val statement = script.scriptElements.head as VariableStatement
		val declaration = statement.varDecl.head
		assertEquals('x', declaration.name)
		assertFalse(declaration.isConst)
	}

	@Test
	def void testVarWithType() {
		val script = 'var x: any = 1'.parseESSuccessfully
		val statement = script.scriptElements.head as VariableStatement
		val declaration = statement.varDecl.head
		assertEquals('x', declaration.name)
		assertFalse(declaration.isConst)
	}

	@Test
	def void testSimpleConst() {
		val script = 'const x = 1'.parseESSuccessfully
		val statement = script.scriptElements.head as VariableStatement
		val declaration = statement.varDecl.head
		assertEquals('x', declaration.name)
		assertTrue("Const variable must return isConst=true", declaration.isConst)
	}

	@Inject
	protected extension N4JSGrammarAccess grammarAccess

	/**
	 * IDEBUG-214
	 */
	@Test
	def void testVarWithClassExpr() {
		'''var foo = 5,
			class A { a = new A(); }'''.parseESWithError
	}

	@Test
	def void testVarWithClassExpr2() {
		'''var foo = 5,/*
			*/
			class A { a = new A(); }'''.parseESWithError
	}
	@Test
	def void testVarWithClassExpr3() {
		'''var foo = 5,
			class A { a = new A(); }'''.parseESWithError
	}
	@Test
	def void testVarWithClassExpr4() {
		'''var foo = 5, /* hello */
			class A { a = new A(); }'''.parseESWithError
	}

	@Test
	def void testVarWithClassExpr5() {
		'''var foo = 5;
		   console.log(foo)'''.parseESSuccessfully
		'''var foo = 5
		   console.log(foo)'''.parseESSuccessfully
		val script = '''var foo = 5,
		   console.log(foo)'''.parseESWithError
		assertEquals(1, script.eResource.errors.size)
		assertEquals("no viable alternative at input '.'", script.eResource.errors.head.message)
	}

	@Test
	def void testVarWithClassExpr6() {
		'''var foo = 5,
		   x: qualified.Name = (foo)'''.parseESSuccessfully
	}
}
