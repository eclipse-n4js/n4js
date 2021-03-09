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
import org.eclipse.n4js.n4JS.ParameterizedCallExpression
import org.eclipse.n4js.validation.JavaScriptVariant
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.Test
import org.junit.runner.RunWith

/**
 * N4JS Spec Test: 4.15 This Type
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProvider)
class N4_15_ThisTypeTest extends AbstractTypesystemTest {

	@Test
	def void testTypeVarInGenericWithoutBounds() {
		val script = createScript(JavaScriptVariant.n4js, '''
			class A {
				f(): this {
					return null;
				}
			}
			var a: A = new A();
			var x = a.f();
		''')
		val call = EcoreUtil2.getAllContentsOfType(script, ParameterizedCallExpression).head;
		assertTypeByName("A", call) // it's A, not this[A], cf IDEBUG-40

		assertNoValidationErrors(script);
	}

	@Test
	def void testTypeVarInGenericWithoutBoundsWithThisReturn() {
		val script = createScript(JavaScriptVariant.n4js, '''
			class A {
				f(): this {
					return this;
				}
			}
			var a: A = new A();
			var x = a.f();
		''')
		val call = EcoreUtil2.getAllContentsOfType(script, ParameterizedCallExpression).head;
		assertTypeByName("A", call) // it's A, not this[A], cf IDEBUG-40

		assertNoValidationErrors(script);
	}

}
