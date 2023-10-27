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

import java.util.List;

import org.eclipse.n4js.N4JSInjectorProviderWithIssueSuppression;
import org.eclipse.n4js.n4JS.Script;
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
public class NoExcessiveTypeInferenceTest extends AbstractTypesystemTest {

	@Inject
	ValidationTestHelper valTestHelper;

	@Test
	public void testAvoidExcessiveTypeInference1() {
		assertValidationErrors(
				"""
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
						""",
				"S is not a subtype of string.");
	}

	@Test
	public void testAvoidExcessiveTypeInference2() {
		assertValidationErrors(
				"""
						class X {}
						class A<T> {
							m(): T { return null }
						}

						class B {
							<S> foo(param: A<S>): void {
								var s: string = param.m();	// type of param.m() must *not* be inferred to 'string'!! (but was before bug fix)
							}
						}
						""",
				"S is not a subtype of string.");
	}

	private void assertValidationErrors(CharSequence input, CharSequence expectedErrors) {
		Script script = createScript(JavaScriptVariant.n4js, input.toString());
		List<Issue> issues = valTestHelper.validate(script);
		assertErrorMessages(issues, expectedErrors);
	}
}
