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

import org.eclipse.n4js.ide.tests.server.AbstractCompletionTest
import org.junit.Test
import java.util.List

/**
 * Code completion tests for scenarios that also might add an import statement
 * from dependency projects
 */
public class CompletionWithImportsWorkspaceTest extends AbstractCompletionTest {

	/** Some default modules that export a number of classes for all tests. */
	override final List<Pair<String, List<Pair<String, String>>>> getDefaultTestYarnWorkspace() {
		return #[
			"P1*" -> #[
				"MA"  -> '''
							export class A1 {}
							export class A2 {}''',
				"MBA" -> '''
							export class B1 {}
							export class A2 {}''',
				org.eclipse.n4js.ide.tests.server.AbstractIdeTest.TAG_DEPENDENCY -> N4JS_RUNTIME_NAME],
			org.eclipse.n4js.ide.tests.server.AbstractIdeTest.TAG_NODE_MODULES + N4JS_RUNTIME_NAME -> null
		];
	}

	@Test
	def void test01() {
		test('''
			let x = new A1<|>
		''', ''' 
			(A1, Class, MA, , , 00000, , , , ([0:12 - 0:14], A1), [([0:0 - 0:0], import {A1} from "MA";
			)], [], , )
		''');
	}

}
