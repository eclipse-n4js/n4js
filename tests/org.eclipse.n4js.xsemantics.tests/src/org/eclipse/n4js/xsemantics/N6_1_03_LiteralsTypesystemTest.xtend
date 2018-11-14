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
import org.eclipse.n4js.n4JS.ExpressionStatement
import org.eclipse.n4js.n4JS.Literal
import org.eclipse.n4js.n4JS.NumericLiteral
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.n4JS.UnaryExpression
import org.eclipse.n4js.n4JS.UnaryOperator
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

	def void assertLiteralType(String expectedType, String literal) {
		val script = (literal + ";").parse();
		assertNotNull(script);
		val G = RuleEnvironmentExtensions.newRuleEnvironment(script);
		val expr = (script.scriptElements.head as ExpressionStatement).expression;
		val isLiteralOrNegatedNumericLiteral = {
			expr instanceof Literal
			|| if(expr instanceof UnaryExpression) {
				(expr.op===UnaryOperator.NEG || expr.op===UnaryOperator.POS)
				&& expr.expression instanceof NumericLiteral
			} else {
				false
			}
		};
		assertTrue("expected a Literal or a negated NumericLiteral, but was " + expr.eClass.name,
			isLiteralOrNegatedNumericLiteral);
		val typeExpr = checkedType(G, expr);
		assertEquals(expectedType, typeExpr.typeRefAsString);
	}

	@Test
	def void testNullLiteral() {
		assertLiteralType("null", "null");
	}

	@Test
	def void testBooleanLiteral() {
		assertLiteralType("boolean", "true");
		assertLiteralType("boolean", "false");
	}

	@Test
	def void testIntLiteral() {
		assertLiteralType("int", "-1");
		assertLiteralType("int", "-0");
		assertLiteralType("int", "0");
		assertLiteralType("int", "1");
		assertLiteralType("int", "+0");
		assertLiteralType("int", "+1");

		assertLiteralType("number", "-2147483650");
		assertLiteralType("number", "-2147483649");
		assertLiteralType("int",    "-2147483648");
		assertLiteralType("int",    "-2147483647");

		assertLiteralType("int",     "2147483646");
		assertLiteralType("int",     "2147483647");
		assertLiteralType("number",  "2147483648");
		assertLiteralType("number",  "2147483649");

		assertLiteralType("int",    "+2147483646");
		assertLiteralType("int",    "+2147483647");
		assertLiteralType("number", "+2147483648");
		assertLiteralType("number", "+2147483649");
	}

	@Test
	def void testLiteralWithFraction() {
		assertLiteralType("number", ".0");
		assertLiteralType("number", ".1");
		assertLiteralType("number", ".0e-1");
		assertLiteralType("number", ".0E1");
		assertLiteralType("number", "2.1");
		assertLiteralType("number", "3.0e-1");
		assertLiteralType("number", "4.0E1");
		assertLiteralType("number", "4.2");
	}

	@Test
	def void testHexLiteral() {
		assertLiteralType("int", "0x0");
		assertLiteralType("int", "0x1");
		assertLiteralType("int", "0X1");
		assertLiteralType("int", "0XA");
		assertLiteralType("int", "0Xa");

		assertLiteralType("int",     "0x7fffffff");
		assertLiteralType("number",  "0x80000000"); // would be int in Java, but is number in Javascript
		assertLiteralType("number",  "0xffffffff"); // would be int in Java, but is number in Javascript
		assertLiteralType("number", "0x100000000"); // would be syntax error in Java, but valid in Javascript

		// don't get confused by leading '0' (after "0x")
		assertLiteralType("int",     "0x007fffffff");
		assertLiteralType("number",  "0x0080000000"); // would be int in Java, but is number in Javascript
		assertLiteralType("number",  "0x00ffffffff"); // would be int in Java, but is number in Javascript
		assertLiteralType("number", "0x00100000000"); // would be syntax error in Java, but valid in Javascript
	}

	@Test
	def void testOctalLiteral() {
		assertLiteralType("int", "01");
		assertLiteralType("int", "02");

		assertLiteralType("int",     "017777777777");
		assertLiteralType("number",  "020000000000"); // would be int in Java, but is number in Javascript
		assertLiteralType("number",  "037777777777"); // would be int in Java, but is number in Javascript
		assertLiteralType("number",  "040000000000"); // would be int in Java, but is number in Javascript
		assertLiteralType("number", "0100000000000"); // would be syntax error in Java, but valid in Javascript

		// don't get confused by leading '0' (after the initial "0" that triggers octal mode)
		assertLiteralType("int",     "00017777777777");
		assertLiteralType("number",  "00020000000000"); // would be int in Java, but is number in Javascript
		assertLiteralType("number",  "00037777777777"); // would be int in Java, but is number in Javascript
		assertLiteralType("number",  "00040000000000"); // would be int in Java, but is number in Javascript
		assertLiteralType("number", "000100000000000"); // would be syntax error in Java, but valid in Javascript

		// don't get confused by fake octals:
		assertLiteralType("int", "09"); // <- not an octal literal!
		assertLiteralType("number", "037777777708"); // <- not an octal literal!
	}

	@Test
	def void testScientificIntLiteral() {
		assertLiteralType("number", "1e1");
		assertLiteralType("number", "1e-1");
		assertLiteralType("number", "1E1");
		assertLiteralType("number", "1E-1");
	}

	@Test
	def void testStringLiteral() {
		assertLiteralType("string", '''"hello"''');
		assertLiteralType("string", "'world'");
		assertLiteralType("string", '''""''');
		assertLiteralType("string", "''");
		assertLiteralType("string", '''"0"''');
		assertLiteralType("string", "'1'");
	}

	@Test
	def void testRegExpLiteral() {
		assertLiteralType("RegExp", "/a/");
		assertLiteralType("RegExp", "/=a/");
		assertLiteralType("RegExp", "/a/g");
		assertLiteralType("RegExp", "/=a/g");
	}

}
