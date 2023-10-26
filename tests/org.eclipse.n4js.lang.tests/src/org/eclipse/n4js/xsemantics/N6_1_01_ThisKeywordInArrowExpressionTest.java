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

import static org.eclipse.xtext.xbase.lib.IterableExtensions.head;

import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.n4JS.ParameterizedCallExpression;
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.ThisLiteral;
import org.eclipse.n4js.validation.JavaScriptVariant;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProvider.class)
public class N6_1_01_ThisKeywordInArrowExpressionTest extends AbstractTypesystemTest {

	@Test
	public void testThisKeywordInClassMethod() {
		Script script = createScript(JavaScriptVariant.n4js, """
				class A {
					f(): void {
						()=>this;
					}
				}
				""");
		ThisLiteral thisKeyword = head(EcoreUtil2.getAllContentsOfType(script, ThisLiteral.class));
		assertTypeByName("this[A]", thisKeyword);

		assertNoValidationErrors(script);
	}

	@Test
	public void testThisKeywordInClassWithPropertyAccessField() {
		Script script = createScript(JavaScriptVariant.n4js, """
				class B{}
				class A {
					b: B;
					f(): void {
						()=>this.b;
					}
				}
				""");
		ThisLiteral thisKeyword = head(EcoreUtil2.getAllContentsOfType(script, ThisLiteral.class));
		assertTypeByName("this[A]", thisKeyword);

		ParameterizedPropertyAccessExpression pae = head(
				EcoreUtil2.getAllContentsOfType(script, ParameterizedPropertyAccessExpression.class));
		assertTypeByName("B", pae);

		assertNoValidationErrors(script);
	}

	@Test
	public void testThisKeywordInClassWithPropertyAccessFieldCall() {
		Script script = createScript(JavaScriptVariant.n4js, """
				class B{}
				class A {
					bar(): B { return null }
					f(): void {
						()=>{ return this.bar() };
					}
				}
				""");

		ThisLiteral thisLiteral = head(EcoreUtil2.getAllContentsOfType(script, ThisLiteral.class));
		assertTypeName("this[A]", thisLiteral);

		ParameterizedPropertyAccessExpression pae = head(
				EcoreUtil2.getAllContentsOfType(script, ParameterizedPropertyAccessExpression.class));
		assertTypeByName("{function():B}", pae);

		ParameterizedCallExpression ce = head(
				EcoreUtil2.getAllContentsOfType(script, ParameterizedCallExpression.class));
		assertTypeByName("B", ce);

		assertNoValidationErrors(script);
	}

	@Test
	public void testThisKeywordInObjectLiteral() {
		Script script = createScript(JavaScriptVariant.n4js, """
				var ol = {
					p: function() {
						()=>{ return this };
					}
				}
				""");
		ThisLiteral thisKeyword = head(EcoreUtil2.getAllContentsOfType(script, ThisLiteral.class));
		assertTypeByName("~Object with { p: {function():void} }", thisKeyword);

		assertNoValidationErrors(script);
	}

	@Test
	public void testThisKeywordInObjectLiteralWithPropertyAccessDatafield() {
		Script script = createScript(JavaScriptVariant.n4js, """
				class B{}
				var ol = {
					B b: new B(),
					p: function() {
						()=>()=>this.b;
					}
				}
				""");
		ThisLiteral thisKeyword = head(EcoreUtil2.getAllContentsOfType(script, ThisLiteral.class));
		assertTypeByName("~Object with { b: B; p: {function():void} }", thisKeyword);

		ParameterizedPropertyAccessExpression pae = head(
				EcoreUtil2.getAllContentsOfType(script, ParameterizedPropertyAccessExpression.class));
		assertTypeByName("B", pae);

		assertNoValidationErrors(script);
	}

	@Test
	public void testThisKeywordInObjectLiteralWithPropertyAccessCall() {
		Script script = createScript(JavaScriptVariant.n4js, """
				class B{}
				var a = {
					bar: function(): B { return null; },
					foo: function() {
						(x,y) => this.bar();
					}
				}
				""");

		ThisLiteral thisLiteral = head(EcoreUtil2.getAllContentsOfType(script, ThisLiteral.class));
		assertTypeName("~Object with { bar: {function():B}; foo: {function():void} }", thisLiteral);

		ParameterizedCallExpression call = head(
				EcoreUtil2.getAllContentsOfType(script, ParameterizedCallExpression.class));
		assertTypeByName("B", call);

		assertNoValidationErrors(script);
	}

	@Test
	public void testThisKeywordInPropertyInitializer() {
		Script script = createScript(JavaScriptVariant.n4js, """
				class A{
					foo(): A {
						var ol = {
							x: y=>this.foo()
						}
						return null;
					}
				}
				""");

		ThisLiteral thisLiteral = head(EcoreUtil2.getAllContentsOfType(script, ThisLiteral.class));
		assertTypeName("this[A]", thisLiteral);

		assertNoValidationErrors(script);
	}

	@Test
	public void testThisKeywordInToplevelFunction() {
		Script script = createScript(JavaScriptVariant.n4js, """
				function foo() {
					x=>this;
				}
				""");

		ThisLiteral thisLiteral = head(EcoreUtil2.getAllContentsOfType(script, ThisLiteral.class));
		assertTypeName("any", thisLiteral);

		assertNoValidationErrors(script);
	}

}
