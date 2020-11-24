/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 * NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.tests.contentassist;

import org.eclipse.n4js.ide.tests.helper.server.AbstractCompletionTest
import org.junit.Test

/**
 * Test for content assist on a receiver structurally typed as ~r~
 * but used at write location in the AST (i.e. left hand side). 
 */
public class SuppressAccessInfoTest extends AbstractCompletionTest {

	@Test
	def void test01() {
		testAtCursorPartially('''
			class T {
			    public field: string;
			}
			
			function fn(param: ~r~T) {
			    param.<|>
			    const c = 1;
			    c;
			}
		''', ''' 
			(field, Field, field, , , 00010, , , , ([5:10 - 5:10], field), [], [], , )
		''');
	}
}
