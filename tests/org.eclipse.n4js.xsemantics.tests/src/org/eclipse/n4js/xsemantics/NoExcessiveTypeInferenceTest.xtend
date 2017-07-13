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
import org.eclipse.n4js.validation.JavaScriptVariant
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.validation.ValidationTestHelper
import org.junit.Test
import org.junit.runner.RunWith
import org.eclipse.n4js.N4JSInjectorProviderWithIssueSuppression

/**
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProviderWithIssueSuppression)
class NoExcessiveTypeInferenceTest extends AbstractTypesystemTest {

	@Inject
	extension ValidationTestHelper


	@Test
	def void testAvoidExcessiveTypeInference1() {

		'''
		class X {}
		class A<T> {
			m(): T { return null }
		}
		class B<S> {
			foo(): void {
				var a: A<S>;
				var s: string = a.m();	// type of a.m() must *not* be inferred to 'string'!! (but was before bug fix)
			}
		}
		'''.
		assertValidationErrors('''
		S is not a subtype of string.
		''')
	}


	@Test
	def void testAvoidExcessiveTypeInference2() {

		'''
		class X {}
		class A<T> {
			m(): T { return null }
		}

		class B {
			<S> foo(param: A<S>): void {
				var s: string = param.m();	// type of param.m() must *not* be inferred to 'string'!! (but was before bug fix)
			}
		}
		'''.
		assertValidationErrors('''
		S is not a subtype of string.
		''')
	}


	def private assertValidationErrors(CharSequence input, CharSequence expectedErrors) {
		val script = createScript(JavaScriptVariant.n4js,input.toString)
		val issues = script.validate();
		issues.assertErrorMessages(expectedErrors)
	}
}
