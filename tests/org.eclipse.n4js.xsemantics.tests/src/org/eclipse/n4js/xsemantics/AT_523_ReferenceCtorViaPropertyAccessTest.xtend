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

import static org.junit.Assert.*
import com.google.common.base.StandardSystemProperty

/*
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProviderWithIssueSuppression)
class AT_523_ReferenceCtorViaPropertyAccessTest extends AbstractTypesystemTest {

	@Inject
	extension ValidationTestHelper


	@Test
	def void testReferenceToConstructorViaInstance() {
		'''
			class C {}
			var c: C;
			var ctor = c.constructor;				// reference via instance of C
			var resultCtor: constructor{C} = ctor;
			var resultNewInstance: C = new ctor();
		'''.assertValidationErrors(
		'''
			"constructor{? extends C} is not a subtype of constructor{C}." at line:4, column:34
			"Cannot instantiate ? extends C, because C does not have a @CovariantConstructor." at line:5, column:32
		'''
		)
	}

	@Test
	def void testReferenceToConstructorViaClass() {
		'''
			class C {}
			var ctor = C;				// static reference
			var resultCtor: constructor{C} = ctor;
			var resultNewInstance: C = new ctor();
		'''.assertValidationErrors(
		'''
		'''
		)
	}

	@Test
	def void testMiscellaneous() {
		// this test case was taken from the task description and comments of IDE-523
		'''
			class C {}
			var x = C
			var c: C;
			var y = c.constructor
			var z1 = new y() // error: "Cannot instantiate ? extends C."
			var z2 = new C()


			//The type of x and y should be constructor{C}

			function fun(ctor: constructor{C}) {
			}

			fun(x)
			fun(y) // error: "constructor{? extends C} is not a subtype of constructor{C}."


			//The type of z1 and z2 should be C

			var result1: C = z1;
			var result2: C = z2;
		'''.assertValidationErrors(
		'''
			"Cannot instantiate ? extends C, because C does not have a @CovariantConstructor." at line:5, column:14
			"constructor{? extends C} is not a subtype of constructor{C}." at line:15, column:5
		'''
		)
	}

	def private assertValidationErrors(CharSequence input, CharSequence expectedErrors) {
		val script = createScript(JavaScriptVariant.n4js,input.toString)
		val issues = script.validate();
		val issuesStr = issues.map['''"«message»" at line:«lineNumber», column:«column»''']
			.join(StandardSystemProperty.LINE_SEPARATOR.value());
		assertEquals(expectedErrors.toString.trim, issuesStr);
	}
}
