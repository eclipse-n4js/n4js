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
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.validation.JavaScriptVariant;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * N4JS Spec Test: 4.15 This Type
 */
@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProvider.class)
public class N4_15_ThisTypeTest extends AbstractTypesystemTest {

	@Test
	public void testTypeVarInGenericWithoutBounds() {
		Script script = createScript(JavaScriptVariant.n4js, """
				class A {
					f(): this {
						return null;
					}
				}
				var a: A = new A();
				var x = a.f();
				""");
		ParameterizedCallExpression call = head(
				EcoreUtil2.getAllContentsOfType(script, ParameterizedCallExpression.class));
		assertTypeByName("A", call); // it's A, not this[A], cf IDEBUG-40

		assertNoValidationErrors(script);
	}

	@Test
	public void testTypeVarInGenericWithoutBoundsWithThisReturn() {
		Script script = createScript(JavaScriptVariant.n4js, """
				class A {
					f(): this {
						return this;
					}
				}
				var a: A = new A();
				var x = a.f();
				""");
		ParameterizedCallExpression call = head(
				EcoreUtil2.getAllContentsOfType(script, ParameterizedCallExpression.class));
		assertTypeByName("A", call); // it's A, not this[A], cf IDEBUG-40

		assertNoValidationErrors(script);
	}

}
