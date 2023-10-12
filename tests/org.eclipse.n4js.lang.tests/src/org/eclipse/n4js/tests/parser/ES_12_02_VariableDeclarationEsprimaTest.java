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

import static org.eclipse.xtext.xbase.lib.IterableExtensions.last;

import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.IntLiteral;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.n4JS.VariableStatement;
import org.junit.Test;

public class ES_12_02_VariableDeclarationEsprimaTest extends AbstractParserTest {

	@Test
	public void testSimpleIdentifier() {
		Script script = parseESSuccessfully("var x");
		VariableStatement statement = (VariableStatement) script.getScriptElements().get(0);
		VariableDeclaration declaration = statement.getVarDecl().get(0);
		assertEquals("x", declaration.getName());
	}

	@Test
	public void testEscapedIdentifier() {
		Script script = parseESSuccessfully("var \\u006F");
		VariableStatement statement = (VariableStatement) script.getScriptElements().get(0);
		VariableDeclaration declaration = statement.getVarDecl().get(0);
		assertEquals("o", declaration.getName());
	}

	@Test
	public void testBrokenEscapedIdentifier_01() {
		Script script = parseESWithError("var \\u06F");
		VariableStatement statement = (VariableStatement) script.getScriptElements().get(0);
		VariableDeclaration declaration = statement.getVarDecl().get(0);
		assertEquals("u06F", declaration.getName());
	}

	@Test
	public void testBrokenEscapedIdentifier_02() {
		Script script = parseESWithError("var \\u06Fh");
		VariableStatement statement = (VariableStatement) script.getScriptElements().get(0);
		VariableDeclaration declaration = statement.getVarDecl().get(0);
		assertEquals("u06Fh", declaration.getName());
	}

	@Test
	public void testTwoDeclarations() {
		Script script = parseESSuccessfully("var x, y;");
		VariableStatement statement = (VariableStatement) script.getScriptElements().get(0);
		assertEquals(2, statement.getVarDecl().size());
		VariableDeclaration first = statement.getVarDecl().get(0);
		assertEquals("x", first.getName());
		VariableDeclaration second = last(statement.getVarDecl());
		assertEquals("y", second.getName());
	}

	@Test
	public void testWithInitializer() {
		Script script = parseESSuccessfully("var x = 42");
		VariableStatement statement = (VariableStatement) script.getScriptElements().get(0);
		VariableDeclaration declaration = statement.getVarDecl().get(0);
		assertEquals("x", declaration.getName());
		IntLiteral number = (IntLiteral) declaration.getExpression();
		assertEquals(42, number.toInt());
	}

	@Test
	public void testTwoInitializer() {
		Script script = parseESSuccessfully("var eval = 42, arguments = 23");
		VariableStatement statement = (VariableStatement) script.getScriptElements().get(0);
		VariableDeclaration first = statement.getVarDecl().get(0);
		assertEquals("eval", first.getName());
		IntLiteral firstNumber = (IntLiteral) first.getExpression();
		assertEquals(42, firstNumber.toInt());
		VariableDeclaration second = last(statement.getVarDecl());
		assertEquals("arguments", second.getName());
		IntLiteral secondNumber = (IntLiteral) second.getExpression();
		assertEquals(23, secondNumber.toInt());
	}

	@Test
	public void testThreeInitializer() {
		Script script = parseESSuccessfully("var x = 14, y = 3, z = 1977");
		VariableStatement statement = (VariableStatement) script.getScriptElements().get(0);
		assertEquals(3, statement.getVarDecl().size());
		VariableDeclaration first = statement.getVarDecl().get(0);
		assertEquals("x", first.getName());
		IntLiteral firstNumber = (IntLiteral) first.getExpression();
		assertEquals(14, firstNumber.toInt());
		VariableDeclaration second = statement.getVarDecl().get(1);
		assertEquals("y", second.getName());
		IntLiteral secondNumber = (IntLiteral) second.getExpression();
		assertEquals(3, secondNumber.toInt());
		VariableDeclaration third = last(statement.getVarDecl());
		assertEquals("z", third.getName());
		IntLiteral thirdNumber = (IntLiteral) third.getExpression();
		assertEquals(1977, thirdNumber.toInt());
	}

	@Test
	public void testKeywordsAsIdentifier_01() {
		Script script = parseESSuccessfully("var implements, interface, package");
		VariableStatement statement = (VariableStatement) script.getScriptElements().get(0);
		assertEquals(3, statement.getVarDecl().size());
		VariableDeclaration first = statement.getVarDecl().get(0);
		assertEquals("implements", first.getName());
		VariableDeclaration second = statement.getVarDecl().get(1);
		assertEquals("interface", second.getName());
		VariableDeclaration third = last(statement.getVarDecl());
		assertEquals("package", third.getName());
	}

	@Test
	public void testKeywordsAsIdentifier_02() {
		Script script = parseESSuccessfully("var private, protected, public, static");
		VariableStatement statement = (VariableStatement) script.getScriptElements().get(0);
		assertEquals(4, statement.getVarDecl().size());
		VariableDeclaration first = statement.getVarDecl().get(0);
		assertEquals("private", first.getName());
		VariableDeclaration second = statement.getVarDecl().get(1);
		assertEquals("protected", second.getName());
		VariableDeclaration third = statement.getVarDecl().get(2);
		assertEquals("public", third.getName());
		VariableDeclaration forth = last(statement.getVarDecl());
		assertEquals("static", forth.getName());
	}

	@Test
	public void testUnicodeName() {
		Script script = parseESSuccessfully("var ಠ_ಠ");
		VariableStatement statement = (VariableStatement) script.getScriptElements().get(0);
		assertEquals(1, statement.getVarDecl().size());
		VariableDeclaration ಠ_ಠ = statement.getVarDecl().get(0);
		assertEquals("ಠ_ಠ", ಠ_ಠ.getName());
	}

	@Test
	public void testCircularReference() {
		Script script = parseESSuccessfully("var v0 = v0;");
		VariableStatement statement = (VariableStatement) script.getScriptElements().get(0);
		assertEquals(1, statement.getVarDecl().size());
		VariableDeclaration v0 = statement.getVarDecl().get(0);
		assertEquals("v0", v0.getName());
		IdentifierRef initializer = (IdentifierRef) v0.getExpression();
		assertEquals("v0", initializer.getIdAsText());
		assertEquals(v0.getDefinedVariable(), initializer.getId());
	}
}
