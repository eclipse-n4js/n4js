/** 
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 * NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.tests.contentassist

import org.eclipse.n4js.ide.tests.AbstractN4JSIdeTest
import org.junit.Test

/** 
 */
class CamelCaseCompletionTest extends AbstractN4JSIdeTest {

	@Test def void testCamelCasePrefix_01() {
		testCompletion [ 
			model = 'EvE'
			column = model.length
			expectedCompletionItems = '''
				EvalError -> EvalError [[0, 0] .. [0, 3]]
			'''
		]
	}
	
	@Test def void testCamelCasePrefix_02() {
		testCompletion [ 
			model = 'eURIC'
			column = model.length
			expectedCompletionItems = '''
				encodeURIComponent -> encodeURIComponent [[0, 0] .. [0, 5]]
			'''
		]
	}
	
	@Test def void testCamelCasePrefix_03() {
		testCompletion [ 
			model = 'eUC'
			column = model.length
			expectedCompletionItems = '''
				encodeURIComponent -> encodeURIComponent [[0, 0] .. [0, 3]]
			'''
		]
	}
	
	@Test def void testCamelCasePrefix_04() {
		testCompletion [ 
			model = 'eC'
			column = model.length
			expectedCompletionItems = ''
		]
	}
}
