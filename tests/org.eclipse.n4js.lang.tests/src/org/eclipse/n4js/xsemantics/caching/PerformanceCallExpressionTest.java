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
package org.eclipse.n4js.xsemantics.caching;

import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Test performance involving call expression
 */
@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProvider.class)
public class PerformanceCallExpressionTest extends AbstractTypesystemForPerformanceTest {

	@Test
	public void testFunctionCallExpressionWithGenerics10() {
		functionExpressionWithGenerics(10);
	}

	@Test(timeout = 2000)
	public void testFunctionCallExpressionWithGenerics100() {
		functionExpressionWithGenerics(100);
	}

	@Test(timeout = 20000)
	public void testFunctionCallExpressionWithGenerics1000() {
		functionExpressionWithGenerics(1000);
	}

	/**
	 * Runs tests with 2000 calls. Note that 3000 calls need more than 30 seconds on the build server (this is why we
	 * reduced the number).
	 *
	 * 2014-07-03: now even 2000 calls need more than 30 seconds -> increased time-out to 40sec (to be clarified in the
	 * future)
	 */
	@Test(timeout = 40000)
	public void testFunctionCallExpressionWithGenerics2000() {
		functionExpressionWithGenerics(2000);
	}

	protected void functionExpressionWithGenerics(int n) {
		StringBuilder sb = new StringBuilder();
		sb.append("function <T> f1(p: T, f: {function(T):T}) : T {return null;}\n");
		for (int idx = 1; idx <= n; idx++) {
			sb.append("""

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
					""");
		}

		assertValidate(sb.toString(), 0);
	}

}
