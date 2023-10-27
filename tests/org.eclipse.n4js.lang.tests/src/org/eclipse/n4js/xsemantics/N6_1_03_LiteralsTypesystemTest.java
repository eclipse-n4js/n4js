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
package org.eclipse.n4js.xsemantics;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.Literal;
import org.eclipse.n4js.n4JS.NumericLiteral;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.UnaryExpression;
import org.eclipse.n4js.n4JS.UnaryOperator;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.n4JS.VariableStatement;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

/**
 * N4JS Spec Test: 6.1.3. Literals, Type Inference
 */
@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProvider.class)
public class N6_1_03_LiteralsTypesystemTest extends AbstractTypesystemTest {

	@Inject
	ParseHelper<Script> parseHelper;

	private void assertLiteralType(String expectedTypeOfVariable, String expectedTypeOfLiteral, String literal) {
		try {
			Script script = parseHelper.parse("var x = " + literal + ";");

			assertNotNull(script);
			RuleEnvironment G = RuleEnvironmentExtensions.newRuleEnvironment(script);
			VariableDeclaration varDecl = (VariableDeclaration) ((VariableStatement) script.getScriptElements().get(0))
					.getVarDeclsOrBindings().get(0);
			Expression expr = varDecl.getExpression();
			assertTrue("expected a Literal or a negated NumericLiteral, but was " + expr.eClass().getName(),
					isLiteralOrNegatedNumericLiteral(expr));
			TypeRef typeVarDecl = checkedType(G, varDecl);
			assertEquals(expectedTypeOfVariable, typeVarDecl.getTypeRefAsString());
			TypeRef typeLiteral = checkedType(G, expr);
			assertEquals(expectedTypeOfLiteral, typeLiteral.getTypeRefAsString());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	private boolean isLiteralOrNegatedNumericLiteral(Expression expr) {
		if (expr instanceof Literal) {
			return true;
		}
		if (expr instanceof UnaryExpression) {
			UnaryExpression ue = (UnaryExpression) expr;
			return (ue.getOp() == UnaryOperator.NEG || ue.getOp() == UnaryOperator.POS)
					&& ue.getExpression() instanceof NumericLiteral;
		}
		return false;
	}

	@Test
	public void testNullLiteral() {
		assertLiteralType("any", "null", "null");
	}

	@Test
	public void testBooleanLiteral() {
		assertLiteralType("boolean", "true", "true");
		assertLiteralType("boolean", "false", "false");
	}

	@Test
	public void testIntLiteral() {
		assertLiteralType("int", "-1", "-1");
		assertLiteralType("int", "0", "-0");
		assertLiteralType("int", "0", "0");
		assertLiteralType("int", "1", "1");
		assertLiteralType("int", "0", "+0");
		assertLiteralType("int", "1", "+1");

		assertLiteralType("number", "-2147483650", "-2147483650");
		assertLiteralType("number", "-2147483649", "-2147483649");
		assertLiteralType("int", "-2147483648", "-2147483648");
		assertLiteralType("int", "-2147483647", "-2147483647");

		assertLiteralType("int", "2147483646", "2147483646");
		assertLiteralType("int", "2147483647", "2147483647");
		assertLiteralType("number", "2147483648", "2147483648");
		assertLiteralType("number", "2147483649", "2147483649");

		assertLiteralType("int", "2147483646", "+2147483646");
		assertLiteralType("int", "2147483647", "+2147483647");
		assertLiteralType("number", "2147483648", "+2147483648");
		assertLiteralType("number", "2147483649", "+2147483649");
	}

	@Test
	public void testLiteralWithFraction() {
		assertLiteralType("number", "0", ".0");
		assertLiteralType("number", "0.1", ".1");
		assertLiteralType("number", "0", ".0e-1");
		assertLiteralType("int", "0", ".0E1");
		assertLiteralType("number", "2.1", "2.1");
		assertLiteralType("number", "0.3", "3.0e-1");
		assertLiteralType("int", "40", "4.0E1");
		assertLiteralType("number", "4.2", "4.2");
	}

	@Test
	public void testHexLiteral() {
		assertLiteralType("int", "0", "0x0");
		assertLiteralType("int", "1", "0x1");
		assertLiteralType("int", "1", "0X1");
		assertLiteralType("int", "10", "0XA");
		assertLiteralType("int", "10", "0Xa");

		assertLiteralType("int", "2147483647", "0x7fffffff");
		// would be int in Java, but is number in Javascript
		assertLiteralType("number", "2147483648", "0x80000000");
		// would be int in Java, but is number in Javascript
		assertLiteralType("number", "4294967295", "0xffffffff");
		// would be syntax error in Java, but valid in Javascript
		assertLiteralType("number", "4294967296", "0x100000000");

		// don't get confused by leading '0' (after "0x")
		assertLiteralType("int", "2147483647", "0x007fffffff");
		// would be int in Java, but is number in Javascript
		assertLiteralType("number", "2147483648", "0x0080000000");
		// would be int in Java, but is number in Javascript
		assertLiteralType("number", "4294967295", "0x00ffffffff");
		// would be syntax error in Java, but valid in Javascript
		assertLiteralType("number", "4294967296", "0x00100000000");
	}

	@Test
	public void testOctalLiteral() {
		assertLiteralType("int", "1", "01");
		assertLiteralType("int", "2", "02");

		assertLiteralType("int", "2147483647", "017777777777");
		// would be int in Java, but is number in Javascript
		assertLiteralType("number", "2147483648", "020000000000");
		// would be int in Java, but is number in Javascript
		assertLiteralType("number", "4294967295", "037777777777");
		// would be int in Java, but is number in Javascript
		assertLiteralType("number", "4294967296", "040000000000");
		// would be syntax error in Java, but valid in Javascript
		assertLiteralType("number", "8589934592", "0100000000000");

		// don't get confused by leading '0' (after the initial "0" that triggers octal mode)
		assertLiteralType("int", "2147483647", "00017777777777");
		// would be int in Java, but is number in Javascript
		assertLiteralType("number", "2147483648", "00020000000000");
		// would be int in Java, but is number in Javascript
		assertLiteralType("number", "4294967295", "00037777777777");
		// would be int in Java, but is number in Javascript
		assertLiteralType("number", "4294967296", "00040000000000");
		// would be syntax error in Java, but valid in Javascript
		assertLiteralType("number", "8589934592", "000100000000000");

		// don't get confused by fake octals:
		// not an octal literal!
		assertLiteralType("int", "9", "09");
		// not an octal literal!
		assertLiteralType("number", "37777777708", "037777777708");
	}

	@Test
	public void testScientificIntLiteral() {
		assertLiteralType("int", "10", "1e1");
		assertLiteralType("number", "0.1", "1e-1");
		assertLiteralType("int", "10", "1E1");
		assertLiteralType("number", "0.1", "1E-1");
	}

	@Test
	public void testStringLiteral() {
		assertLiteralType("string", "\"hello\"", "\"hello\"");
		assertLiteralType("string", "\"world\"", "'world'");
		assertLiteralType("string", "\"\"", "\"\"");
		assertLiteralType("string", "\"\"", "''");
		assertLiteralType("string", "\"0\"", "\"0\"");
		assertLiteralType("string", "\"1\"", "'1'");
	}

	@Test
	public void testRegExpLiteral() {
		assertLiteralType("RegExp", "RegExp", "/a/");
		assertLiteralType("RegExp", "RegExp", "/=a/");
		assertLiteralType("RegExp", "RegExp", "/a/g");
		assertLiteralType("RegExp", "RegExp", "/=a/g");
	}

}
