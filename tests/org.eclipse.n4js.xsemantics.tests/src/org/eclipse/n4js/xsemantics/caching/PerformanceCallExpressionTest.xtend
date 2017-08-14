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
package org.eclipse.n4js.xsemantics.caching

import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.xsemantics.caching.AbstractTypesystemForPerformanceTest
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Test performance involving call expression
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProvider)
class PerformanceCallExpressionTest extends AbstractTypesystemForPerformanceTest {

	@Test
	def void testFunctionCallExpressionWithGenerics10() {
		10.functionExpressionWithGenerics
	}

	@Test(timeout=2000)
	def void testFunctionCallExpressionWithGenerics100() {
		100.functionExpressionWithGenerics
	}

	@Test(timeout=20000)
	def void testFunctionCallExpressionWithGenerics1000() {
		1000.functionExpressionWithGenerics
	}

	/**
	 * Runs tests with 2000 calls. Note that 3000 calls need more than 30 seconds on the
	 * build server (this is why we reduced the number).
	 *
	 * 2014-07-03: now even 2000 calls need more than 30 seconds -> increased time-out
	 *             to 40sec (to be clarified in the future)
	 */
	@Test(timeout=40000)
	def void testFunctionCallExpressionWithGenerics2000() {
		2000.functionExpressionWithGenerics
	}

	def protected functionExpressionWithGenerics(int n) {
		'''
			function <T> f1(p: T, f: {function(T):T}) : T {return null;}

			«FOR i : 1..n»
			f1("hello",
				function(i){
					return i;
				}
			);
			f1(10,
				function(i){
					return i;
				}
			);
			«ENDFOR»
		'''.assertValidate(0)
	}

}
