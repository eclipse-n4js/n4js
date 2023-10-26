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

import java.util.List;

import org.eclipse.n4js.N4JSInjectorProviderWithIssueSuppression;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.utils.Strings;
import org.eclipse.n4js.validation.JavaScriptVariant;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.validation.ValidationTestHelper;
import org.eclipse.xtext.validation.Issue;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProviderWithIssueSuppression.class)
public class AT_523_ReferenceCtorViaPropertyAccessTest extends AbstractTypesystemTest {

	@Inject
	ValidationTestHelper valTestHelper;

	@Test
	public void testReferenceToConstructorViaInstance() {
		assertValidationErrors("""
				class C {}
				var c: C;
				var ctor = c.constructor;				// reference via instance of C
				var resultCtor: constructor{C} = ctor;
				var resultNewInstance: C = new ctor();
				""", """
				"constructor{? extends C} is not a subtype of constructor{C}." at line:4, column:34
				"Cannot instantiate ? extends C, because C does not have a @CovariantConstructor." at line:5, column:32
				""");

	}

	@Test
	public void testReferenceToConstructorViaClass() {
		assertValidationErrors("""
				class C {}
				var ctor = C;				// static reference
				var resultCtor: constructor{C} = ctor;
				var resultNewInstance: C = new ctor();
				""", """
				""");

	}

	@Test
	public void testMiscellaneous() {
		// this test case was taken from the task description and comments of IDE-523
		assertValidationErrors("""
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
				""", """
				"Cannot instantiate ? extends C, because C does not have a @CovariantConstructor." at line:5, column:14
				"constructor{? extends C} is not a subtype of constructor{C}." at line:15, column:5
				""");

	}

	private void assertValidationErrors(CharSequence input, CharSequence expectedErrors) {
		Script script = createScript(JavaScriptVariant.n4js, input.toString());
		List<Issue> issues = valTestHelper.validate(script);
		String issuesStr = Strings.join("\n",
				i -> "\"%s\" at line:%d, column:%d".formatted(i.getMessage(), i.getLineNumber(), i.getColumn()),
				issues);
		assertEquals(expectedErrors.toString().trim(), issuesStr);
	}
}
