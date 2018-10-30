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
import org.eclipse.n4js.n4JS.ParameterizedCallExpression
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.n4JS.ThisLiteral
import org.eclipse.n4js.typesystem.RuleEnvironmentExtensions
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.util.ParseHelper
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

/*
 * Tests for judgment type, see n4js.xsemantics for judgment, axiom and rules.
 * Tests and rules are ordered according to N4JS spec
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProvider)
class JudgmentTypeTest extends AbstractTypesystemTest {

	@Inject
	extension ParseHelper<Script>

	/*
	 * @see [N4JS] 6.1.1 This Keyword
	 */
	@Test
	def void testThisTypeInClass() {
		val script = '''
			class A{
				foo(): A {
					this.foo();
					return null;
				}
			}
		'''.parse()

		val thisLiteral = EcoreUtil2.getAllContentsOfType(script, ThisLiteral).head;
		assertTypeName("this[A]", thisLiteral)

		// eventually:
		assertNoValidationErrors(script);
	}

	/*
	 * @see [N4JS] 6.1.1 This Keyword
	 */
	@Test
	def void testThisTypeInObjectLiteral() {
		val script = '''
			var a = {
				foo: function() {
					this.foo();
				}
			}
		'''.parse()

		val thisLiteral = EcoreUtil2.getAllContentsOfType(script, ThisLiteral).head;
		assertTypeName("~Object with { foo: {function():void} }", thisLiteral)

		// eventually:
		assertNoValidationErrors(script);
	}

	/*
	 * @see [N4JS] 6.1.1 This Keyword
	 */
	@Test
	def void testThisTypeInPropertyInitializer() {
		val script = '''
			class A{
				foo(): A {
					var ol = {
						x: this.foo();
					}
				}
			}
		'''.parse()

		val thisLiteral = EcoreUtil2.getAllContentsOfType(script, ThisLiteral).head;
		assertTypeName("this[A]", thisLiteral)

	// eventually:
	// assertNoValidationErrors(script);
	}

	/*
	 * @see [N4JS] 6.1.1 This Keyword
	 */
	@Test
	def void testThisTypeInToplevelFunction() {
		val script = '''
			function foo() {
				this;
			}
		'''.parse()

		val thisLiteral = EcoreUtil2.getAllContentsOfType(script, ThisLiteral).head;
		assertTypeName("undefined", thisLiteral);
		// eventually:
		assertNoValidationErrors(script);
	}

	/*
	 * There are more identifier tests, as almost all inference rules require some identifiers bound to
	 * a declaration.
	 * @see [N4JS] 6.1.2 Identifier
	 */
	@Test
	def void testIdentifier() {
		val script = '''
			var a: number;
			a;
		'''.parse()

		val expr = (script.scriptElements.get(1) as ExpressionStatement).expression
		assertTypeName("number", expr)

		// eventually:
		assertNoValidationErrors(script);
	}

	/*
	 * @see [N4JS] 6.1.3 Literals
	 */
	@Test
	def void testTypeNullLiteral() {
		val script = '''
			null;
		'''.parse()

		val expr = (script.scriptElements.head as ExpressionStatement).expression;
		assertTypeName("null", expr);

		// eventually:
		assertNoValidationErrors(script);
	}

	/*
	 * @see [N4JS] 6.1.3 Literals
	 */
	@Test
	def void testTypeBooleanLiteral() {
		val script = '''
			false;
			true;
		'''.parse()

		for (scriptElement : script.scriptElements.filter(ExpressionStatement)) {
			assertTypeName("boolean", scriptElement.expression);
		}

		// eventually:
		assertNoValidationErrors(script);
	}

	/*
	 * @see [N4JS] 6.1.3 Literals
	 */
	@Test
	def void testTypeIntLiteral() {
		val script = '''
			1;
			0;
			0xffff;
		'''.parse()

		for (scriptElement : script.scriptElements.filter(ExpressionStatement)) {
			assertTypeName("int", scriptElement.expression);
		}

		// eventually:
		assertNoValidationErrors(script);
	}

	/*
	 * @see [N4JS] 6.1.3 Literals
	 */
	@Test
	def void testTypeNumberLiteral() {
		val script = '''
			2.1;
			3e10;
			4e-5;
		'''.parse()

		for (scriptElement : script.scriptElements.filter(ExpressionStatement)) {
			assertTypeName("number", scriptElement.expression);
		}

		// eventually:
		assertNoValidationErrors(script);
	}

	/*
	 * @see [N4JS] 6.1.3 Literals
	 */
	@Test
	def void testTypeStringLiteral() {
		val script = '''
			"Hello";
			'World';
			"";
			'';
			"Hello\nWorld";
		'''.parse()

		for (scriptElement : script.scriptElements.filter(ExpressionStatement)) {
			assertTypeName("string", scriptElement.expression);
		}

		// eventually:
		assertNoValidationErrors(script);
	}

	/*
	 * @see [N4JS] 6.1.3 Literals
	 */
	@Test
	def void testTypeRegExpLiteral() {
		val script = '''
			/hello/g;
		'''.parse()

		for (scriptElement : script.scriptElements.filter(ExpressionStatement)) {
			assertTypeName("RegExp", scriptElement.expression);
		}

		// eventually:
		assertNoValidationErrors(script);
	}

	/*
	 * @see [N4JS] 6.1.4 Array Literal
	 */
	@Test
	def void testTypeArrayLiteral() {
		val script = '''
			["Wim", "Wendelin"];
		'''.parse()
		val scriptElement = script.scriptElements.filter(ExpressionStatement).head;
		assertTypeName("Array<string>", scriptElement.expression);

		// eventually:
		assertNoValidationErrors(script);
	}

	/*
	 * @see [N4JS] 6.1.4 Array Literal
	 */
	@Test
	def void testTypeEmptyArrayLiteral() {
		val script = '''
			[];
		'''.parse()

		val scriptElement = script.scriptElements.filter(ExpressionStatement).head;
		assertTypeName("Array<any>", scriptElement.expression);

		// eventually:
		assertNoValidationErrors(script);
	}


	@Test
	def void testFunctionCall() {
		val script = '''
			class B{}
			class A{
				foo(): B { return null;}
			}
			var a: A;
			a.foo();
		'''.parse()

		val G = RuleEnvironmentExtensions.newRuleEnvironment(script);

		val call = (script.scriptElements.get(3) as ExpressionStatement).expression as ParameterizedCallExpression;

		var result = ts.type(G, call)

		if (result.failure) {
			Assert.fail(result.failureMessage);
		}

		// eventually:
		assertNoValidationErrors(script);
	}

	@Test
	def void testUndefinedDeclaration() {
		val script = '''
			var undef: undefined;
			undef;
		'''.parse();

		//val G = newRuleEnvironment(script);
		val undefDecl = script.variableDeclarations.head;
		val undefRef = (script.scriptElements.last as ExpressionStatement).expression;

		assertTypeName("undefined", undefDecl)
		assertTypeName("undefined", undefRef)
	}

}
