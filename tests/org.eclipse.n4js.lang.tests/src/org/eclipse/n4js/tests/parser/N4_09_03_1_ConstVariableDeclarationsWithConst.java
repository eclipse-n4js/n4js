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

import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.n4JS.VariableStatement;
import org.junit.Test;

public class N4_09_03_1_ConstVariableDeclarationsWithConst extends AbstractParserTest {

	@Test
	public void testSimpleVar() {
		Script script = parseESSuccessfully("var x = 1");
		VariableStatement statement = (VariableStatement) script.getScriptElements().get(0);
		VariableDeclaration declaration = statement.getVarDecl().get(0);
		assertEquals("x", declaration.getName());
		assertFalse(declaration.isConst());
	}

	@Test
	public void testVarWithType() {
		Script script = parseESSuccessfully("var x: any = 1");
		VariableStatement statement = (VariableStatement) script.getScriptElements().get(0);
		VariableDeclaration declaration = statement.getVarDecl().get(0);
		assertEquals("x", declaration.getName());
		assertFalse(declaration.isConst());
	}

	@Test
	public void testSimpleConst() {
		Script script = parseESSuccessfully("const x = 1");
		VariableStatement statement = (VariableStatement) script.getScriptElements().get(0);
		VariableDeclaration declaration = statement.getVarDecl().get(0);
		assertEquals("x", declaration.getName());
		assertTrue("Const variable must return isConst=true", declaration.isConst());
	}

	/**
	 * IDEBUG-214
	 */
	@Test
	public void testVarWithClassExpr() {
		parseESWithError("""
				var foo = 5,
				class A { a = new A(); }
				""");
	}

	@Test
	public void testVarWithClassExpr2() {
		parseESWithError("""
				var foo = 5,/*
				*/
				class A { a = new A(); }
				""");
	}

	@Test
	public void testVarWithClassExpr3() {
		parseESWithError("""
				var foo = 5,
				class A { a = new A(); }
				""");
	}

	@Test
	public void testVarWithClassExpr4() {
		parseESWithError("""
				var foo = 5, /* hello */
				class A { a = new A(); }
				""");
	}

	@Test
	public void testVarWithClassExpr5() {
		parseESSuccessfully("""
				var foo = 5;
				   console.log(foo)
				 		""");
		parseESSuccessfully("""
				var foo = 5
				   console.log(foo)
				  	""");
		Script script = parseESWithError("""
				var foo = 5,
				   console.log(foo)
				   """);
		assertEquals(1, script.eResource().getErrors().size());
		assertEquals("no viable alternative at input '.'", script.eResource().getErrors().get(0).getMessage());
	}

	@Test
	public void testVarWithClassExpr6() {
		parseESSuccessfully("""
				var foo = 5,
				   x: qualified.Name = (foo)
				   """);
	}
}
