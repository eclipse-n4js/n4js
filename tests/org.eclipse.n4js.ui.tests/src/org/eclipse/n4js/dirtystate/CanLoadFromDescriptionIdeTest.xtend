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
package org.eclipse.n4js.dirtystate

import org.eclipse.n4js.scoping.utils.CanLoadFromDescriptionHelper
import org.junit.Assume
import org.junit.Before
import org.junit.Test

/**
 */
// converted from CanLoadFromDescriptionPluginUITest
class CanLoadFromDescriptionIdeTest extends AbstractCanLoadFromDescriptionTest {

	@Before
	def void before() {
		Assume.assumeFalse(CanLoadFromDescriptionHelper.isLoadFromSourceDeactivated());
	}

	/*
	 * A1 -> B1 -> C -> D
	 * |           ^
	 * ----> B2 -----
	 *       ^
	 * A2 ----
	 * |
	 * ----> P -> Q
	 *       ^    |
	 *       ------ 
	 */

	private val sourceA1 = '''
		import {b1} from "m/B1";
		import {b2} from "m/B2";

		var a1: string = b1;
		var a2: string = b2;
		a1; a2; // avoid unused variable warning
	''';

	private val sourceA2 = '''
		import {b2} from "m/B2";
		import {p, p2} from "m/P";

		var a2: string = b2;
		var a2p: string = p;
		var a2q: string = p2;
		a2; a2p; a2q; // avoid unused variable warning
	''';

	private val sourceB1 = '''
		import {c1} from "m/C";

		export public var b1 = c1;
	''';

	private val sourceB2 = '''
		import {c2} from "m/C";

		export public var b2 = c2;
	''';

	private val sourceC = '''
		import {d} from "m/D";

		export public var c1 = '';
		export public var c2 = d;
	''';

	private val sourceD = '''
		export public var d = '';
	''';

	private val sourceP = '''
		import {q} from "m/Q";
		export public var p = '';
		export public var p2 = q;
	''';

	private val sourceQ = '''
		import {p} from "m/P";
		export public var q = p;
	''';

	private val sourceB1_modify_b1 = sourceB1.replace(
		"b1 = c1",
		'b1 = c1 || 1');

	private val sourceC_modify_c1 = sourceC.replace(
		"c1 = ''",
		'c1 = 1');

	private val sourceD_modify_d = sourceD.replace(
		"d = ''",
		'd = 1');

	private val sourceP_modify_p = sourceP.replace(
		"p = ''",
		'p = 2');

	private val sourceQ_modify_q = sourceQ.replace(
		"q = p",
		'q = p || 1');

	def private void prepare() {
		testWorkspaceManager.createTestProjectOnDisk(
			"m/A1" -> sourceA1,
			"m/A2" -> sourceA2,
			"m/B1" -> sourceB1,
			"m/B2" -> sourceB2,
			"m/C" -> sourceC,
			"m/D" -> sourceD,
			"m/P" -> sourceP,
			"m/Q" -> sourceQ
		);
		startAndWaitForLspServer();
		assertNoIssues();
	}

	@Test
	def void test_inEditor_allFromIndex() {
		prepare();

		openFile("A1");
		joinServerRequests();
		assertNoIssues();
		assertAllFromIndex("A1");

		changeOpenedFile("A1", sourceA1 + ' ');
		joinServerRequests();
		assertNoIssues();
		assertAllFromIndex("A1");
	}
	
	@Test
	def void test_inEditor_a1_c() {
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
		assertIssues(
			"A1" -> #["(Error, [3:17 - 3:19], int is not a subtype of string.)"]
		);
		
		val expectedFromSource = #{
			getFileURIFromModuleName('A1'),
			getFileURIFromModuleName('B1'),
			getFileURIFromModuleName('B2')
			// we do not load C from source since the entry in the index is up to date
		}
		assertFromSource("A1", expectedFromSource);
	}
	
	@Test
	def void test_inEditor_a2_d() {
		prepare();

		openFile("A2");
		openFile("D");
		joinServerRequests();
		assertNoIssues();
		assertAllFromIndex("A2");

		changeOpenedFile("D", sourceD_modify_d);
		joinServerRequests();
		assertIssues(
			"A2" -> #["(Error, [3:17 - 3:19], int is not a subtype of string.)"]
		);
		
		val expectedFromSource = #{
			getFileURIFromModuleName('A2'),
			getFileURIFromModuleName('B2'),
			getFileURIFromModuleName('C')
			// we do not load D from source since the entry in the index is up to date
		}
		assertFromSource("A2", expectedFromSource);

		// We keep the resources from source as is, even though from index would be sufficient when we reset the
		// fileD
		closeFileDiscardingChanges("D", false);
		joinServerRequests();
		assertFromSource("A2", expectedFromSource);
	}

	@Test
	def void test_inEditor_a1_b1() {
		prepare();

		openFile("A1");
		openFile("B1");
		joinServerRequests();
		assertNoIssues();
		assertAllFromIndex("A1");

		changeOpenedFile("B1", sourceB1_modify_b1);
		joinServerRequests();
		assertIssues(
			"A1" -> #["(Error, [3:17 - 3:19], union{int,string} is not a subtype of string.)"]
		);

		assertAllFromIndex("A1");
	}

	@Test
	def void test_inEditor_a2_p() {
		prepare();

		openFile("A2");
		openFile("P");
		joinServerRequests();
		assertNoIssues();
		assertAllFromIndex("A2");

		changeOpenedFile("P", sourceP_modify_p);
		joinServerRequests();
		assertIssues(
			"A2" -> #[
				"(Error, [4:18 - 4:19], int is not a subtype of string.)",
				"(Error, [5:18 - 5:20], int is not a subtype of string.)"
			]
		);

		val expectedFromSource = #{
			getFileURIFromModuleName('A2'),
			getFileURIFromModuleName('P'),
			getFileURIFromModuleName('Q')
		}
		assertFromSource("A2", expectedFromSource);
	}

	@Test
	def void test_inEditor_a2_q() {
		prepare();

		openFile("A2");
		openFile("Q");
		joinServerRequests();
		assertNoIssues();
		assertAllFromIndex("A2");

		changeOpenedFile("Q", sourceQ_modify_q);
		joinServerRequests();
		assertIssues(
			"A2" -> #["(Error, [5:18 - 5:20], union{int,string} is not a subtype of string.)"]
		);

		val expectedFromSource = #{
			getFileURIFromModuleName('A2'),
			getFileURIFromModuleName('P'),
			getFileURIFromModuleName('Q')
		}
		assertFromSource("A2", expectedFromSource);
	}
}
