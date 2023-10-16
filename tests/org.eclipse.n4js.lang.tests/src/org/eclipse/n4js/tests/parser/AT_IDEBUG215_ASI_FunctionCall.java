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
package org.eclipse.n4js.tests.parser;

import org.junit.Test;

public class AT_IDEBUG215_ASI_FunctionCall extends AbstractParserTest {

	@Test
	public void test_1_NoASI() throws Exception {
		parseESWithError("""
				function f(any) {
					f({}
					g;
				}
				""");
	}

	@Test
	public void test_2_ASI() throws Exception {
		parseESSuccessfully("""
				function f(any) {
					f({})
					g;
				}
				""");
	}

	@Test
	public void test_3_Explicit() throws Exception {
		parseESSuccessfully("""
				function f(any) {
					f({});
					g;
				}
				""");
	}

	@Test
	public void test_1_NoASIAndAsi() throws Exception {
		parseESWithError("""
				function g ( any ) {
					f({})
					g ;
				}


				function f ( any ) {
					f({}
					g ;
				}
				""");

		parseESWithError("""
				function f ( any ) {
					f({}
					g ;
				}
				function g ( any ) {
					f({})
					g ;
				}

				""");
	}

}
