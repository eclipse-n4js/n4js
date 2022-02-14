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
				"CM1.n4js" -> "export public const K1a = 0;",
				"util/CM2.n4js" -> "export public const K2a = 0;",
				"util/CM3.n4js" -> "export public const K3a = 0;"
			],
			"P2" -> #[
				"CM1.n4js" -> "export public const K1b = 0;",
				"CM2.n4js" -> "export public const K2b = 0;",
				"util/CM3.n4js" -> "export public const K3b = 0;"
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
			let l = K1a;
		''', #[
			"(Error, [0:8 - 0:11], Couldn't resolve reference to IdentifiableElement 'K1a'.)"
		], '''
			import {K1a} from "P1/CM1";
			let l = K1a;
		''')
	}
	
	@Test
	def void testDifferentFolders() {
		test('''
			let l = K2a;
		''', #[
			"(Error, [0:8 - 0:11], Couldn't resolve reference to IdentifiableElement 'K2a'.)"
		], '''
			import {K2a} from "util/CM2";
			let l = K2a;
		''')
	}
	
	@Test
	def void testSubfoders() {
		test('''
			let l = K3a;
		''', #[
			"(Error, [0:8 - 0:11], Couldn't resolve reference to IdentifiableElement 'K3a'.)"
		], '''
			import {K3a} from "P1/util/CM3";
			let l = K3a;
		''')
	}
}
