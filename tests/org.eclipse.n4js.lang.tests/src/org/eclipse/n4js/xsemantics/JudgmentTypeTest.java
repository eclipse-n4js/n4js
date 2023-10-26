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

import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.head;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.last;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;

import java.util.List;

import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.ExpressionStatement;
import org.eclipse.n4js.n4JS.ParameterizedCallExpression;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.ThisLiteral;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.UnknownTypeRef;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

/**
 * Tests for judgment type, see n4js.xsemantics for judgment, axiom and rules. Tests and rules are ordered according to
 * N4JS spec
 */
@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProvider.class)
public class JudgmentTypeTest extends AbstractTypesystemTest {

	@Inject
	ParseHelper<Script> parseHelper;

	/*
	 * @see [N4JS] 6.1.1 This Keyword
	 */
	@Test
	public void testThisTypeInClass() throws Exception {
		Script script = parseHelper.parse("""
				class A{
					foo(): A {
						this.foo();
						return null;
					}
				}
				""");

		ThisLiteral thisLiteral = head(EcoreUtil2.getAllContentsOfType(script, ThisLiteral.class));
		assertTypeName("this[A]", thisLiteral);

		// eventually:
		assertNoValidationErrors(script);
	}

	/*
	 * @see [N4JS] 6.1.1 This Keyword
	 */
	@Test
	public void testThisTypeInObjectLiteral() throws Exception {
		Script script = parseHelper.parse("""
				var a = {
					foo: function() {
						this.foo();
					}
				}
				""");

		ThisLiteral thisLiteral = head(EcoreUtil2.getAllContentsOfType(script, ThisLiteral.class));
		assertTypeName("~Object with { foo: {function():void} }", thisLiteral);

		// eventually:
		assertNoValidationErrors(script);
	}

	/*
	 * @see [N4JS] 6.1.1 This Keyword
	 */
	@Test
	public void testThisTypeInPropertyInitializer() throws Exception {
		Script script = parseHelper.parse("""
				class A{
					foo(): A {
						var ol = {
							x: this.foo();
						}
					}
				}
				""");

		ThisLiteral thisLiteral = head(EcoreUtil2.getAllContentsOfType(script, ThisLiteral.class));
		assertTypeName("this[A]", thisLiteral);

		// eventually:
		// assertNoValidationErrors(script);
	}

	/*
	 * @see [N4JS] 6.1.1 This Keyword
	 */
	@Test
	public void testThisTypeInToplevelFunction() throws Exception {
		Script script = parseHelper.parse("""
				function foo() {
					this;
				}
				""");

		ThisLiteral thisLiteral = head(EcoreUtil2.getAllContentsOfType(script, ThisLiteral.class));
		assertTypeName("any", thisLiteral);
		// eventually:
		assertNoValidationErrors(script);
	}

	/*
	 * There are more identifier tests, as almost all inference rules require some identifiers bound to a declaration.
	 *
	 * @see [N4JS] 6.1.2 Identifier
	 */
	@Test
	public void testIdentifier() throws Exception {
		Script script = parseHelper.parse("""
				var a: number;
				a;
				""");

		Expression expr = ((ExpressionStatement) script.getScriptElements().get(1)).getExpression();
		assertTypeName("number", expr);

		// eventually:
		assertNoValidationErrors(script);
	}

	/*
	 * @see [N4JS] 6.1.3 Literals
	 */
	@Test
	public void testTypeNullLiteral() throws Exception {
		Script script = parseHelper.parse("""
				null;
				""");

		Expression expr = ((ExpressionStatement) script.getScriptElements().get(0)).getExpression();
		assertTypeName("null", expr);

		// eventually:
		assertNoValidationErrors(script);
	}

	/*
	 * @see [N4JS] 6.1.3 Literals
	 */
	@Test
	public void testTypeBooleanLiteral() throws Exception {
		Script script = parseHelper.parse("""
				false;
				true;
				""");

		List<Expression> expressions = toList(
				map(filter(script.getScriptElements(), ExpressionStatement.class), es -> es.getExpression()));
		assertTypeName("false", expressions.get(0));
		assertTypeName("true", expressions.get(1));

		// eventually:
		assertNoValidationErrors(script);
	}

	/*
	 * @see [N4JS] 6.1.3 Literals
	 */
	@Test
	public void testTypeIntLiteral() throws Exception {
		Script script = parseHelper.parse("""
				1;
				0;
				0xffff;
				""");

		List<Expression> expressions = toList(
				map(filter(script.getScriptElements(), ExpressionStatement.class), es -> es.getExpression()));
		assertTypeName("1", expressions.get(0));
		assertTypeName("0", expressions.get(1));
		assertTypeName("65535", expressions.get(2));

		// eventually:
		assertNoValidationErrors(script);
	}

	/*
	 * @see [N4JS] 6.1.3 Literals
	 */
	@Test
	public void testTypeNumberLiteral() throws Exception {
		Script script = parseHelper.parse("""
				2.1;
				3e10;
				4e-5;
				""");

		List<Expression> expressions = toList(
				map(filter(script.getScriptElements(), ExpressionStatement.class), es -> es.getExpression()));
		assertTypeName("2.1", expressions.get(0));
		assertTypeName("30000000000", expressions.get(1));
		assertTypeName("0.00004", expressions.get(2));

		// eventually:
		assertNoValidationErrors(script);
	}

	/*
	 * @see [N4JS] 6.1.3 Literals
	 */
	@Test
	public void testTypeStringLiteral() throws Exception {
		Script script = parseHelper.parse("""
				"Hello";
				'World';
				"";
				'';
				"Hello\\nWorld";
				""");

		List<Expression> expressions = toList(
				map(filter(script.getScriptElements(), ExpressionStatement.class), es -> es.getExpression()));
		assertTypeName("\"Hello\"", expressions.get(0));
		assertTypeName("\"World\"", expressions.get(1));
		assertTypeName("\"\"", expressions.get(2));
		assertTypeName("\"\"", expressions.get(3));
		assertTypeName("\"Hello\\nWorld\"", expressions.get(4));

		// eventually:
		assertNoValidationErrors(script);
	}

	/*
	 * @see [N4JS] 6.1.3 Literals
	 */
	@Test
	public void testTypeRegExpLiteral() throws Exception {
		Script script = parseHelper.parse("""
				/hello/g;
				""");

		for (ExpressionStatement scriptElement : filter(script.getScriptElements(), ExpressionStatement.class)) {
			assertTypeName("RegExp", scriptElement.getExpression());
		}

		// eventually:
		assertNoValidationErrors(script);
	}

	/*
	 * @see [N4JS] 6.1.4 Array Literal
	 */
	@Test
	public void testTypeArrayLiteral() throws Exception {
		Script script = parseHelper.parse("""
				["Wim", "Wendelin"];
				""");
		ExpressionStatement scriptElement = head(filter(script.getScriptElements(), ExpressionStatement.class));
		assertTypeName("Array<string>", scriptElement.getExpression());

		// eventually:
		assertNoValidationErrors(script);
	}

	/*
	 * @see [N4JS] 6.1.4 Array Literal
	 */
	@Test
	public void testTypeEmptyArrayLiteral() throws Exception {
		Script script = parseHelper.parse("""
				[];
				""");

		ExpressionStatement scriptElement = head(filter(script.getScriptElements(), ExpressionStatement.class));
		assertTypeName("Array<any>", scriptElement.getExpression());

		// eventually:
		assertNoValidationErrors(script);
	}

	@Test
	public void testFunctionCall() throws Exception {
		Script script = parseHelper.parse("""
				class B{}
				class A{
					foo(): B { return null;}
				}
				var a: A;
				a.foo();
				""");

		RuleEnvironment G = RuleEnvironmentExtensions.newRuleEnvironment(script);

		ParameterizedCallExpression call = (ParameterizedCallExpression) ((ExpressionStatement) script
				.getScriptElements().get(3)).getExpression();

		TypeRef result = ts.type(G, call);

		Assert.assertNotNull(result);
		Assert.assertFalse(result instanceof UnknownTypeRef);

		// eventually:
		assertNoValidationErrors(script);
	}

	@Test
	public void testUndefinedDeclaration() throws Exception {
		Script script = parseHelper.parse("""
				var undef: undefined;
				undef;
				""");

		// val G = newRuleEnvironment(script);
		VariableDeclaration undefDecl = head(variableDeclarations(script));
		Expression undefRef = ((ExpressionStatement) last(script.getScriptElements())).getExpression();

		assertTypeName("undefined", undefDecl);
		assertTypeName("undefined", undefRef);
	}

}
