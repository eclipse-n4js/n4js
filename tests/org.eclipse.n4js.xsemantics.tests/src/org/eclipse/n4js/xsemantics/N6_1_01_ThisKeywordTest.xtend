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

import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression
import org.eclipse.n4js.n4JS.ThisLiteral
import org.eclipse.n4js.validation.JavaScriptVariant
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.Test
import org.junit.runner.RunWith
import org.eclipse.n4js.n4JS.ParameterizedCallExpression

/**
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProvider)
class N6_1_01_ThisKeywordTest extends AbstractTypesystemTest {

	@Test
	def void testThisKeywordInClassMethod() {
		val script = createScript(JavaScriptVariant.n4js, '''
			class A {
				f(): void {
					this;
				}
			}
		''')
		val thisKeyword = EcoreUtil2.getAllContentsOfType(script, ThisLiteral).head;
		assertTypeByName("this[A]", thisKeyword)

		assertNoValidationErrors(script);
	}

	@Test
	def void testThisKeywordInClassWithPropertyAccessField() {
		val script = createScript(JavaScriptVariant.n4js, '''
			class B{}
			class A {
				b: B;
				f(): void {
					this.b;
				}
			}
		''')
		val thisKeyword = EcoreUtil2.getAllContentsOfType(script, ThisLiteral).head;
		assertTypeByName("this[A]", thisKeyword)

		val pae = EcoreUtil2.getAllContentsOfType(script, ParameterizedPropertyAccessExpression).head;
		assertTypeByName("B", pae)

		assertNoValidationErrors(script);
	}

	@Test
	def void testThisKeywordInClassWithPropertyAccessFieldCall() {
		val script = createScript(JavaScriptVariant.n4js, '''
			class B{}
			class A {
				bar(): B { return null }
				f(): void {
					this.bar();
				}
			}
		''')

		val thisLiteral = EcoreUtil2.getAllContentsOfType(script, ThisLiteral).head;
		assertTypeName("this[A]", thisLiteral)

		val pae = EcoreUtil2.getAllContentsOfType(script, ParameterizedPropertyAccessExpression).head;
		assertTypeByName("{function():B}", pae)

		val ce = EcoreUtil2.getAllContentsOfType(script, ParameterizedCallExpression).head;
		assertTypeByName("B", ce)

		assertNoValidationErrors(script);
	}

	@Test
	def void testThisKeywordInObjectLiteral() {
		val script = createScript(JavaScriptVariant.n4js, '''
			var ol = {
				p: function() {
					this;
				}
			}
		''')
		val thisKeyword = EcoreUtil2.getAllContentsOfType(script, ThisLiteral).head;
		assertTypeByName("~Object with { p: {function():void} }", thisKeyword)

		assertNoValidationErrors(script);
	}

	@Test
	def void testThisKeywordInObjectLiteralWithPropertyAccessDatafield() {
		val script = createScript(JavaScriptVariant.n4js, '''
			class B{}
			var ol = {
				B b: new B(),
				p: function() {
					this.b;
				}
			}
		''')
		val thisKeyword = EcoreUtil2.getAllContentsOfType(script, ThisLiteral).head;
		assertTypeByName("~Object with { b: B; p: {function():void} }", thisKeyword)

		val pae = EcoreUtil2.getAllContentsOfType(script, ParameterizedPropertyAccessExpression).head;
		assertTypeByName("B", pae)

		assertNoValidationErrors(script);
	}

	@Test
	def void testThisKeywordInObjectLiteralWithPropertyAccessCall() {
		val script = createScript(JavaScriptVariant.n4js, '''
			class B{}
			var a = {
				bar: function(): B { return null; },
				foo: function() {
					this.bar();
				}
			}
		''')

		val thisLiteral = EcoreUtil2.getAllContentsOfType(script, ThisLiteral).head;
		assertTypeName("~Object with { bar: {function():B}; foo: {function():void} }", thisLiteral)

		val call = EcoreUtil2.getAllContentsOfType(script, ParameterizedCallExpression).head;
		assertTypeByName("B", call)

		assertNoValidationErrors(script);
	}

	@Test
	def void testThisKeywordInPropertyInitializer() {
		val script = createScript(JavaScriptVariant.n4js, '''
			class A{
				foo(): A {
					var ol = {
						x: this.foo()
					}
					return null;
				}
			}
		''')

		val thisLiteral = EcoreUtil2.getAllContentsOfType(script, ThisLiteral).head;
		assertTypeName("this[A]", thisLiteral)

		assertNoValidationErrors(script);
	}

	@Test
	def void testThisKeywordInToplevelFunction() {
		val script = createScript(JavaScriptVariant.n4js, '''
			function foo() {
				this;
			}
		''')

		val thisLiteral = EcoreUtil2.getAllContentsOfType(script, ThisLiteral).head;
		assertTypeName("undefined", thisLiteral)

		assertNoValidationErrors(script);
	}

}
