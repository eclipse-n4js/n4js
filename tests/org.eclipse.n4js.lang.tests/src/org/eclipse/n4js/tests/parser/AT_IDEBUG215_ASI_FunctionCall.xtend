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
package org.eclipse.n4js.tests.parser

import org.junit.Test

class AT_IDEBUG215_ASI_FunctionCall extends AbstractParserTest {

	@Test
	def void test_1_NoASI() {
		'''
			function f(any) {
				f({}
				g;
			}
		'''.parseESWithError
	}

	@Test
	def void test_2_ASI() {
		'''function f(any) {
			f({})
			g;
		}
		'''.parseESSuccessfully
	}

	@Test
	def void test_3_Explicit() {
		'''function f(any) {
			f({});
			g;
		}
		'''.parseESSuccessfully
	}

	@Test
	def void test_1_NoASIAndAsi() {
		'''
			function g ( any ) {
				f({})
				g ;
			}


			function f ( any ) {
				f({}
				g ;
			}
		'''.parseESWithError

		'''
			function f ( any ) {
				f({}
				g ;
			}
			function g ( any ) {
				f({})
				g ;
			}

		'''.parseESWithError
	}

}
