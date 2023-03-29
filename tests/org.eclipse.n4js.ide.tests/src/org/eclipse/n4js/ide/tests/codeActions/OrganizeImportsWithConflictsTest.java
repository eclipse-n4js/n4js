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
package org.eclipse.n4js.ide.tests.codeActions;

import java.util.List;

import org.eclipse.n4js.ide.tests.helper.server.AbstractOrganizeImportsTest;
import org.eclipse.n4js.ide.tests.helper.server.TestWorkspaceManager;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.Test;

/**
 * Like {@link OrganizeImportsTest}, but covers cases in which a the imported module is in conflict with another module
 * that has the same name.
 */
@SuppressWarnings("javadoc")
public class OrganizeImportsWithConflictsTest extends AbstractOrganizeImportsTest {

	@Override
	protected List<Pair<String, List<Pair<String, String>>>> getDefaultTestWorkspace() {
		return List.of(
				Pair.of("P1", List.of(
						Pair.of("CM1.n4js", "export public const K1a = 0;"),
						Pair.of("util/CM2.n4js", "export public const K2a = 0;"),
						Pair.of("util/CM3.n4js", "export public const K3a = 0;"))),
				Pair.of("P2", List.of(
						Pair.of("CM1.n4js", "export public const K1b = 0;"),
						Pair.of("CM2.n4js", "export public const K2b = 0;"),
						Pair.of("util/CM3.n4js", "export public const K3b = 0;"))),
				Pair.of("PClient" + TestWorkspaceManager.MODULE_SELECTOR, List.of(
						// test file will be added in this project
						Pair.of(CFG_DEPENDENCIES, "P1, P2"))));
	}

	@Test
	public void testSimple() {
		test("""
				let l = K1a;
				""", List.of(
				"(Error, [0:8 - 0:11], Couldn't resolve reference to IdentifiableElement 'K1a'.)"), """
						import {K1a} from "P1/CM1";
						let l = K1a;
						""");
	}

	@Test
	public void testDifferentFolders() {
		test("""
				let l = K2a;
				""", List.of(
				"(Error, [0:8 - 0:11], Couldn't resolve reference to IdentifiableElement 'K2a'.)"), """
						import {K2a} from "util/CM2";
						let l = K2a;
						""");
	}

	@Test
	public void testSubfoders() {
		test("""
				let l = K3a;
				""", List.of(
				"(Error, [0:8 - 0:11], Couldn't resolve reference to IdentifiableElement 'K3a'.)"), """
						import {K3a} from "P1/util/CM3";
						let l = K3a;
						""");
	}
}
