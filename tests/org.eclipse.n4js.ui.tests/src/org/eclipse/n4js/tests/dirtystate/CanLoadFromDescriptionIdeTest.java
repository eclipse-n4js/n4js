/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.tests.dirtystate;

import java.util.List;
import java.util.Set;

import org.eclipse.n4js.scoping.utils.CanLoadFromDescriptionHelper;
import org.eclipse.n4js.workspace.locations.FileURI;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

// converted from CanLoadFromDescriptionPluginUITest
public class CanLoadFromDescriptionIdeTest extends AbstractCanLoadFromDescriptionTest {

	@Before
	public void before() {
		Assume.assumeFalse(CanLoadFromDescriptionHelper.isLoadFromSourceDeactivated());
	}

	/*
	 * A1 -> B1 -> C -> D | ^ ----> B2 ----- ^ A2 ---- | ----> P -> Q ^ | ------
	 */

	private final String sourceA1 = """
				import {b1} from "m/B1";
				import {b2} from "m/B2";

				var a1: string = b1;
				var a2: string = b2;
				a1; a2; // avoid unused variable warning
			""";

	private final String sourceA2 = """
				import {b2} from "m/B2";
				import {p, p2} from "m/P";

				var a2: string = b2;
				var a2p: string = p;
				var a2q: string = p2;
				a2; a2p; a2q; // avoid unused variable warning
			""";

	private final String sourceB1 = """
				import {c1} from "m/C";

				export public var b1 = c1;
			""";

	private final String sourceB2 = """
				import {c2} from "m/C";

				export public var b2 = c2;
			""";

	private final String sourceC = """
				import {d} from "m/D";

				export public var c1 = '';
				export public var c2 = d;
			""";

	private final String sourceD = """
				export public var d = '';
			""";

	private final String sourceP = """
				import {q} from "m/Q";
				export public var p = '';
				export public var p2 = q;
			""";

	private final String sourceQ = """
				import {p} from "m/P";
				export public var q = p;
			""";

	private final String sourceB1_modify_b1 = sourceB1.replace(
			"b1 = c1",
			"b1 = c1 || 1");

	private final String sourceC_modify_c1 = sourceC.replace(
			"c1 = ''",
			"c1 = 1");

	private final String sourceD_modify_d = sourceD.replace(
			"d = ''",
			"d = 1");

	private final String sourceP_modify_p = sourceP.replace(
			"p = ''",
			"p = 2");

	private final String sourceQ_modify_q = sourceQ.replace(
			"q = p",
			"q = p || 1");

	private void prepare() {
		testWorkspaceManager.createTestProjectOnDisk(
				Pair.of("m/A1", sourceA1),
				Pair.of("m/A2", sourceA2),
				Pair.of("m/B1", sourceB1),
				Pair.of("m/B2", sourceB2),
				Pair.of("m/C", sourceC),
				Pair.of("m/D", sourceD),
				Pair.of("m/P", sourceP),
				Pair.of("m/Q", sourceQ));
		startAndWaitForLspServer();
		assertNoIssues();
	}

	@Test
	public void test_inEditor_allFromIndex() throws InterruptedException {
		prepare();

		openFile("A1");
		joinServerRequests();
		assertNoIssues();
		assertAllFromIndex("A1");

		changeOpenedFile("A1", sourceA1 + " ");
		joinServerRequests();
		assertNoIssues();
		assertAllFromIndex("A1");
	}

	@Test
	public void test_inEditor_a1_c() throws InterruptedException {
		prepare();

		openFile("A1");
		openFile("C");
		joinServerRequests();
		// editor for file A should not have any errors
		assertNoIssues();
		assertAllFromIndex("A1");

		changeOpenedFile("C", sourceC_modify_c1);
		joinServerRequests();
		// editor for file A1 should now have exactly 1 expected error
		assertIssues2(
				Pair.of("A1", List.of("(Error, [3:17 - 3:19], int is not a subtype of string.)")));

		Set<FileURI> expectedFromSource = Set.of(
				getFileURIFromModuleName("A1"),
				getFileURIFromModuleName("B1"),
				getFileURIFromModuleName("B2")
		// we do not load C from source since the entry in the index is up to date
		);
		assertFromSource("A1", expectedFromSource);
	}

	@Test
	public void test_inEditor_a2_d() throws InterruptedException {
		prepare();

		openFile("A2");
		openFile("D");
		joinServerRequests();
		assertNoIssues();
		assertAllFromIndex("A2");

		changeOpenedFile("D", sourceD_modify_d);
		joinServerRequests();
		assertIssues2(
				Pair.of("A2", List.of("(Error, [3:17 - 3:19], int is not a subtype of string.)")));

		Set<FileURI> expectedFromSource = Set.of(
				getFileURIFromModuleName("A2"),
				getFileURIFromModuleName("B2"),
				getFileURIFromModuleName("C")
		// we do not load D from source since the entry in the index is up to date
		);
		assertFromSource("A2", expectedFromSource);

		// We keep the resources from source as is, even though from index would be sufficient when we reset the
		// fileD
		closeFileDiscardingChanges("D", false);
		joinServerRequests();
		assertFromSource("A2", expectedFromSource);
	}

	@Test
	public void test_inEditor_a1_b1() throws InterruptedException {
		prepare();

		openFile("A1");
		openFile("B1");
		joinServerRequests();
		assertNoIssues();
		assertAllFromIndex("A1");

		changeOpenedFile("B1", sourceB1_modify_b1);
		joinServerRequests();
		assertIssues2(
				Pair.of("A1", List.of("(Error, [3:17 - 3:19], union{int,string} is not a subtype of string.)")));

		assertAllFromIndex("A1");
	}

	@Test
	public void test_inEditor_a2_p() throws InterruptedException {
		prepare();

		openFile("A2");
		openFile("P");
		joinServerRequests();
		assertNoIssues();
		assertAllFromIndex("A2");

		changeOpenedFile("P", sourceP_modify_p);
		joinServerRequests();
		assertIssues2(
				Pair.of("A2", List.of(
						"(Error, [4:18 - 4:19], int is not a subtype of string.)",
						"(Error, [5:18 - 5:20], int is not a subtype of string.)")));

		Set<FileURI> expectedFromSource = Set.of(
				getFileURIFromModuleName("A2"),
				getFileURIFromModuleName("P"),
				getFileURIFromModuleName("Q"));
		assertFromSource("A2", expectedFromSource);
	}

	@Test
	public void test_inEditor_a2_q() throws InterruptedException {
		prepare();

		openFile("A2");
		openFile("Q");
		joinServerRequests();
		assertNoIssues();
		assertAllFromIndex("A2");

		changeOpenedFile("Q", sourceQ_modify_q);
		joinServerRequests();
		assertIssues2(
				Pair.of("A2", List.of("(Error, [5:18 - 5:20], union{int,string} is not a subtype of string.)")));

		Set<FileURI> expectedFromSource = Set.of(
				getFileURIFromModuleName("A2"),
				getFileURIFromModuleName("P"),
				getFileURIFromModuleName("Q"));
		assertFromSource("A2", expectedFromSource);
	}
}
