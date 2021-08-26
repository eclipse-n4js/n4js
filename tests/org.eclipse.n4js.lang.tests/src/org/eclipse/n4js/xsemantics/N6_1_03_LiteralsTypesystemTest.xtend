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
package org.eclipse.n4js.xsemantics

import com.google.inject.Inject
import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.n4JS.Expression
import org.eclipse.n4js.n4JS.Literal
import org.eclipse.n4js.n4JS.NumericLiteral
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.n4JS.UnaryExpression
import org.eclipse.n4js.n4JS.UnaryOperator
import org.eclipse.n4js.n4JS.VariableDeclaration
import org.eclipse.n4js.n4JS.VariableStatement
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.util.ParseHelper
import org.junit.Test
import org.junit.runner.RunWith

import static org.junit.Assert.*

/**
 * N4JS Spec Test: 6.1.3. Literals, Type Inference
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProvider)
class N6_1_03_LiteralsTypesystemTest extends AbstractTypesystemTest {

	@Inject
	extension ParseHelper<Script>

	def private void assertLiteralType(String expectedTypeOfVariable, String expectedTypeOfLiteral, String literal) {
		val script = ("var x = " + literal + ";").parse();
		assertNotNull(script);
		val G = RuleEnvironmentExtensions.newRuleEnvironment(script);
		val varDecl = (script.scriptElements.head as VariableStatement).varDeclsOrBindings.head as VariableDeclaration;
		val expr = varDecl.expression;
		assertTrue("expected a Literal or a negated NumericLiteral, but was " + expr.eClass.name, isLiteralOrNegatedNumericLiteral(expr));
		val typeVarDecl = checkedType(G, varDecl);
		assertEquals(expectedTypeOfVariable, typeVarDecl.typeRefAsString);
		val typeLiteral = checkedType(G, expr);
		assertEquals(expectedTypeOfLiteral, typeLiteral.typeRefAsString);
	}

	def private boolean isLiteralOrNegatedNumericLiteral(Expression expr) {
		return expr instanceof Literal
			|| if(expr instanceof UnaryExpression) {
				(expr.op===UnaryOperator.NEG || expr.op===UnaryOperator.POS) && expr.expression instanceof NumericLiteral
			} else {
				false
			};
	}

	@Test
	def void testNullLiteral() {
		assertLiteralType("any", "null", "null");
	}

	@Test
	def void testBooleanLiteral() {
		assertLiteralType("boolean", "true",  "true");
		assertLiteralType("boolean", "false", "false");
	}

	@Test
	def void testIntLiteral() {
		assertLiteralType("int", "-1", "-1");
		assertLiteralType("int",  "0", "-0");
		assertLiteralType("int",  "0",  "0");
		assertLiteralType("int",  "1",  "1");
		assertLiteralType("int",  "0", "+0");
		assertLiteralType("int",  "1", "+1");

		assertLiteralType("number", "-2147483650", "-2147483650");
		assertLiteralType("number", "-2147483649", "-2147483649");
		assertLiteralType("int",    "-2147483648", "-2147483648");
		assertLiteralType("int",    "-2147483647", "-2147483647");

		assertLiteralType("int",     "2147483646",  "2147483646");
		assertLiteralType("int",     "2147483647",  "2147483647");
		assertLiteralType("number",  "2147483648",  "2147483648");
		assertLiteralType("number",  "2147483649",  "2147483649");

		assertLiteralType("int",     "2147483646", "+2147483646");
		assertLiteralType("int",     "2147483647", "+2147483647");
		assertLiteralType("number",  "2147483648", "+2147483648");
		assertLiteralType("number",  "2147483649", "+2147483649");
	}

	@Test
	def void testLiteralWithFraction() {
		assertLiteralType("number", "0",   ".0");
		assertLiteralType("number", "0.1", ".1");
		assertLiteralType("number", "0",   ".0e-1");
		assertLiteralType("int",    "0",   ".0E1");
		assertLiteralType("number", "2.1", "2.1");
		assertLiteralType("number", "0.3", "3.0e-1");
		assertLiteralType("int",    "40",  "4.0E1");
		assertLiteralType("number", "4.2", "4.2");
	}

	@Test
	def void testHexLiteral() {
		assertLiteralType("int", "0",  "0x0");
		assertLiteralType("int", "1",  "0x1");
		assertLiteralType("int", "1",  "0X1");
		assertLiteralType("int", "10", "0XA");
		assertLiteralType("int", "10", "0Xa");

		assertLiteralType("int",    "2147483647",  "0x7fffffff");
		assertLiteralType("number", "2147483648",  "0x80000000"); // would be int in Java, but is number in Javascript
		assertLiteralType("number", "4294967295",  "0xffffffff"); // would be int in Java, but is number in Javascript
		assertLiteralType("number", "4294967296", "0x100000000"); // would be syntax error in Java, but valid in Javascript

		// don't get confused by leading '0' (after "0x")
		assertLiteralType("int",    "2147483647",  "0x007fffffff");
		assertLiteralType("number", "2147483648",  "0x0080000000"); // would be int in Java, but is number in Javascript
		assertLiteralType("number", "4294967295",  "0x00ffffffff"); // would be int in Java, but is number in Javascript
		assertLiteralType("number", "4294967296", "0x00100000000"); // would be syntax error in Java, but valid in Javascript
	}

	@Test
	def void testOctalLiteral() {
		assertLiteralType("int", "1", "01");
		assertLiteralType("int", "2", "02");

		assertLiteralType("int",    "2147483647",  "017777777777");
		assertLiteralType("number", "2147483648",  "020000000000"); // would be int in Java, but is number in Javascript
		assertLiteralType("number", "4294967295",  "037777777777"); // would be int in Java, but is number in Javascript
		assertLiteralType("number", "4294967296",  "040000000000"); // would be int in Java, but is number in Javascript
		assertLiteralType("number", "8589934592", "0100000000000"); // would be syntax error in Java, but valid in Javascript

		// don't get confused by leading '0' (after the initial "0" that triggers octal mode)
		assertLiteralType("int",    "2147483647",  "00017777777777");
		assertLiteralType("number", "2147483648",  "00020000000000"); // would be int in Java, but is number in Javascript
		assertLiteralType("number", "4294967295",  "00037777777777"); // would be int in Java, but is number in Javascript
		assertLiteralType("number", "4294967296",  "00040000000000"); // would be int in Java, but is number in Javascript
		assertLiteralType("number", "8589934592", "000100000000000"); // would be syntax error in Java, but valid in Javascript

		// don't get confused by fake octals:
		assertLiteralType("int",    "9",           "09"); // <- not an octal literal!
		assertLiteralType("number", "37777777708", "037777777708"); // <- not an octal literal!
	}

	@Test
	def void testScientificIntLiteral() {
		assertLiteralType("int",    "10",  "1e1");
		assertLiteralType("number", "0.1", "1e-1");
		assertLiteralType("int",    "10",  "1E1");
		assertLiteralType("number", "0.1", "1E-1");
	}

	@Test
	def void testStringLiteral() {
		assertLiteralType("string", '''"hello"''', '''"hello"''');
		assertLiteralType("string", '''"world"''', "'world'");
		assertLiteralType("string", '''""''',      '''""''');
		assertLiteralType("string", '''""''',      "''");
		assertLiteralType("string", '''"0"''',     '''"0"''');
		assertLiteralType("string", '''"1"''',     "'1'");
	}

	@Test
	def void testRegExpLiteral() {
		assertLiteralType("RegExp", "RegExp", "/a/");
		assertLiteralType("RegExp", "RegExp", "/=a/");
		assertLiteralType("RegExp", "RegExp", "/a/g");
		assertLiteralType("RegExp", "RegExp", "/=a/g");
	}

}
