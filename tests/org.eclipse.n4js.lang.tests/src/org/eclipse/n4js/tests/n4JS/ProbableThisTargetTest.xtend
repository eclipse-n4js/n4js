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
package org.eclipse.n4js.tests.n4JS

import com.google.inject.Inject
import org.eclipse.emf.ecore.EObject
import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.n4JS.N4ClassifierDeclaration
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.n4JS.ThisLiteral
import org.eclipse.n4js.n4JS.ThisTarget
import org.eclipse.n4js.n4JS.VariableDeclaration
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.util.ParseHelper
import org.eclipse.xtext.testing.validation.ValidationTestHelper
import org.junit.Test
import org.junit.runner.RunWith

import static org.eclipse.n4js.validation.validators.UnsupportedFeatureValidator.*
import static org.junit.Assert.*

import static extension org.eclipse.n4js.n4JS.N4JSASTUtils.*

/**
 * Tests for computing probable this target.
 *
 */
@InjectWith(N4JSInjectorProvider)
@RunWith(XtextRunner)
class ProbableThisTargetTest {

	@Inject extension ParseHelper<Script>
	@Inject extension ValidationTestHelper


	def void assertThisTarget(CharSequence scriptSrc, boolean expectedErrors) {
		val script = scriptSrc.parse
		if (!expectedErrors) {
			script.assertNoErrors
		} else {
			script.validate;
		}

		var EObject targetElement = EcoreUtil2.eAllOfType(script, VariableDeclaration).filter[name=="target"].head;
		if (targetElement===null) {
			targetElement = EcoreUtil2.eAllOfType(script, N4ClassifierDeclaration).filter[name=="target"].head;
		}
		val thisTarget = if (targetElement===null) targetElement else EcoreUtil2.eAllOfType(targetElement, ThisTarget).head;

		EcoreUtil2.eAllOfType(script, ThisLiteral).forEach [ thisLiteral |
			var containingThisTarget = thisLiteral.probableThisTarget;
			assertSame(thisTarget, containingThisTarget);
		]
	}

	@Test
	def void testObjectLiteralInFunction() {
		assertThisTarget(
			'''
				var target = {
					s: "hello",
					f: function() {
						this;
					}
				}
			''', false)
	}

	@Test
	def void testObjectLiteralInGetter() {
		assertThisTarget(
			'''
				var target = {
					s: "hello",
					get x() {
						this;
						return null;
					}
				}
			''', false)
	}

	@Test
	def void testObjectLiteralInSetter() {
		assertThisTarget(
			'''
				var target = {
					s: "hello",
					set x(y) {
						this;
					}
				}
			''', false)
	}


	@Test
	def void testObjectLiteralNotInValue() {
		assertThisTarget(
			'''
				var notarget = {
					s: this
				}
			''', false)
	}

	@Test
	def void testN4ClassInMethod() {
allowClassExpressions[
		assertThisTarget(
			'''
				var target = class {
					f(): void {
						this;
					}
				}
			''', false)
]
	}

	@Test
	def void testN4ClassDeclInMethod() {
		assertThisTarget(
			'''
				class target {
					f(): void {
						this;
					}
				}
			''', false)
	}

	@Test
	def void testN4InterfaceDeclInMethod() {
		assertThisTarget(
			'''
				interface target {
					f(): void {
						this;
					}
				}
			''', false)
	}

	@Test
	def void testN4ClassInGetter() {
allowClassExpressions[
		assertThisTarget(
			'''
				var target = class {
					get x() {
						this;
						return null;
					}
				}
			''', false)
]
	}

	@Test
	def void testN4ClassInSetter() {
allowClassExpressions[
		assertThisTarget(
			'''
				var target = class {
					set x(y) {
						this;
					}
				}
			''', false)
]
	}

	@Test
	def void testN4ClassInField() {
allowClassExpressions[
		assertThisTarget(
			'''
				var target = class {
					x = this;
				}
			''', false)
]
	}


	@Test
	def void testNotInNestedFunction() {
		assertThisTarget(
			'''
				var notarget = {
					s: "hello",
					f: function() {
						var x = function() {
							this;
						}
					}
				}
			''', false)
	}


	@Test
	def void testNotInFunctionExpression() {
		assertThisTarget(
			'''
				var notarget = function() {
					this;
				}
			''', false)
	}

	@Test
	def void testNotInFunctionDeclaration() {
		assertThisTarget(
			'''
				function notarget(){
					this;
				}
			''', false)
	}


}
