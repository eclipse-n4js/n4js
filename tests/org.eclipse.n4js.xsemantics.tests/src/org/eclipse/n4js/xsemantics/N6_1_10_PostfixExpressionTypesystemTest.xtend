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

import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.Test
import org.junit.runner.RunWith

import static org.eclipse.n4js.validation.JavaScriptVariant.*
import org.eclipse.n4js.N4JSInjectorProviderWithIssueSuppression
import org.eclipse.n4js.validation.JavaScriptVariant

/**
 * Test class for operator test (6.1.10- 6.1.18)
 * 
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProviderWithIssueSuppression)
class N6_1_10_PostfixExpressionTypesystemTest extends AbstractOperatorExpressionTypesystemTest {

	@Test
	def void testTypePostfixExpression() {
		for (mode : JavaScriptVariant.nonDepricatedValues()) {
			assertOperatorType(mode, "number", '''n1++''');
			assertOperatorType(mode, "number", '''n1--''');
			assertOperatorType(mode, "number", '''s1++''');
			assertOperatorType(mode, "number", '''s1++''');
		}
	}

	@Test
	def void testExpectedTypeInPostfix() {
		assertUnaryOperatorExpectedType(unrestricted, "any", "n1++");
		assertUnaryOperatorExpectedType(strict, "any", "n1++");
		assertUnaryOperatorExpectedType(n4js, "number", "n1++");

		assertOperatorSuccess(n4js, "n1++");
		assertOperatorFailure(n4js, "s1++");
		assertOperatorFailure(n4js, "f1++");
	}

}
