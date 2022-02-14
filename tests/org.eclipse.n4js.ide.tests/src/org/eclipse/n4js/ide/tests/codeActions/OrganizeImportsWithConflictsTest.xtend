/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.tests.codeActions

import java.util.List
import org.eclipse.n4js.ide.tests.helper.server.AbstractOrganizeImportsTest
import org.eclipse.n4js.ide.tests.helper.server.TestWorkspaceManager
import org.junit.Test

/**
 * Like {@link OrganizeImportsTest}, but covers cases in which a the imported
 * module is in conflict with another module that has the same name.
 */
class OrganizeImportsWithConflictsTest extends AbstractOrganizeImportsTest {


	override List<Pair<String, List<Pair<String, String>>>> getDefaultTestWorkspace() {
		return #[
			"P1" -> #[
				"CollisionModule.n4js" -> "export public const K1 = 0;"
			],
			"P2" -> #[
				"CollisionModule.n4js" -> "export public const K2 = 0;"
			],
			"PClient" + TestWorkspaceManager.MODULE_SELECTOR -> #[
				// test file will be added in this project
				CFG_DEPENDENCIES -> "P1, P2"
			]
		];
	}
	
	@Test
	def void testSimple() {
		test('''
			let l = K1;
		''', #[
			"(Error, [0:8 - 0:10], Couldn't resolve reference to IdentifiableElement 'K1'.)"
		], '''
			import {K1} from "P1/CollisionModule";
			let l = K1;
		''')
	}
}
