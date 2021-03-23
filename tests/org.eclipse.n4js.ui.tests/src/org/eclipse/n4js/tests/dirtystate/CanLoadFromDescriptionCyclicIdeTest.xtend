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
package org.eclipse.n4js.tests.dirtystate

import org.eclipse.n4js.scoping.utils.CanLoadFromDescriptionHelper
import org.eclipse.n4js.workspace.locations.FileURI
import org.junit.Assert
import org.junit.Assume
import org.junit.Before
import org.junit.Test

import static org.junit.Assert.*

/**
 * Test builder / editor behavior with multiple files and cyclic dependencies.
 */
// converted from CanLoadFromDescriptionCyclicPluginUITest
class CanLoadFromDescriptionCyclicIdeTest extends AbstractCanLoadFromDescriptionTest {

	@Before
	def void before() {
		Assume.assumeFalse(CanLoadFromDescriptionHelper.isLoadFromSourceDeactivated());
	}

	/*
	 * X <- Y <- A <- B <- C <- D
	 *           |              ^
	 *           ----------------
	 * P -> Q
	 * ^    | 
	 * ------
	 */

	private val sourceX = '''
		export public var x = "hello";
		export public var x2 = "hello";
		export public var x3 = "hello";
	''';

	private val sourceY = '''
		import {x} from "m/X";
		import {x2} from "m/X";
		import {x3} from "m/X";
		export public var y = x;
		export public var y2 = x2;
		var y3 = x3; // non-exported var, so change of y3's type won't affect Y's TModule
		y3; // avoid unused variable warning
	''';

	private val sourceA = '''
		import {y} from "m/Y";
		import {y2} from "m/Y";

		import {d} from "m/D";

		export public var a = y;
		var a2 = y2; // non-exported var, so change of a2's type won't affect A's TModule
		a2; // avoid unused variable warning

		export public var end: string = d;
	''';

	private val sourceB = '''
		import {a} from "m/A";
		export public var b = a;
	''';

	private val sourceC = '''
		import {b} from "m/B";
		export public var c = b;
	''';

	private val sourceD = '''
		import {c} from "m/C";
		export public var d = c;
	''';
	
	private val sourceP = '''
		import {q} from "m/Q";
		export public var p = '';
		var p2: string = q;
		p2; // avoid unused warning
	''';

	private val sourceQ = '''
		import {p} from "m/P";
		export public var q = p;
	''';

	// change inferred type of 'x' from string to number
	private val sourceX_modify_x = sourceX.replace(
		'x = "hello";',
		'x = 42;');

	// change inferred type of 'x2' from string to number
	private val sourceX_modify_x2 = sourceX.replace(
		'x2 = "hello";',
		'x2 = 42;');

	// change inferred type of 'x3' from string to number
	private val sourceX_modify_x3 = sourceX.replace(
		'x3 = "hello";',
		'x3 = 42;');

	// change inferred type of 'a' from string to number
	// (keep reference to 'y' to avoid unused import warning)
	private val sourceA_modify_a = sourceA.replace(
		'a = y;',
		'a = 42;y;');
		
	// change the inferred type of variable p to int
	private val sourceP_modify_p = sourceP.replace(
		"p = ''",
		'p = 1;');

	def private void prepare() {
		testWorkspaceManager.createTestProjectOnDisk(
			"m/X" -> sourceX,
			"m/Y" -> sourceY,
			"m/A" -> sourceA,
			"m/B" -> sourceB,
			"m/C" -> sourceC,
			"m/D" -> sourceD,
			"m/P" -> sourceP,
			"m/Q" -> sourceQ
		);
		startAndWaitForLspServer();
		assertNoIssues();
	}
	
	@Test
	def void test_inEditor_simple() {
		prepare();

		openFile("A");
		joinServerRequests();
		assertNoIssues();

		changeOpenedFile("A", sourceA_modify_a);
		joinServerRequests();
		assertIssues(
			"A" -> #["(Error, [9:32 - 9:33], int is not a subtype of string.)"]
		);

		changeOpenedFile("A", sourceA);
		joinServerRequests();
		assertNoIssues();
	}

	@Test
	def void test_inEditor_throughXY() {
		prepare();

		openFile("A");
		joinServerRequests();
		assertNoIssues();

		changeNonOpenedFile("X", sourceX_modify_x);
		joinServerRequests();
		assertIssues(
			"A" -> #["(Error, [9:32 - 9:33], int is not a subtype of string.)"]
		);

		changeNonOpenedFile("X", sourceX);
		joinServerRequests();
		assertNoIssues();
	}

	@Test
	def void test_builder_simple() {
		prepare();

		changeNonOpenedFile("A", sourceA_modify_a);
		joinServerRequests();
		assertIssues(
			"A" -> #["(Error, [9:32 - 9:33], int is not a subtype of string.)"]
		);

		changeNonOpenedFile("A", sourceA);
		joinServerRequests();
		assertNoIssues();
	}

	/** Change A indirectly through a change in X that is forwarded through Y. */
	@Test
	def void test_builder_throughXY() {
		prepare();

		changeNonOpenedFile("X", sourceX_modify_x);
		joinServerRequests();
		assertIssues(
			"A" -> #["(Error, [9:32 - 9:33], int is not a subtype of string.)"]
		);

		changeNonOpenedFile("X", sourceX);
		joinServerRequests();
		assertNoIssues();
	}

	@Test
	def void test_builder_stopsInY() {
		prepare()

		val outputY = getOutputFileForTestFile("Y")
		val outputA = getOutputFileForTestFile("A")

		val stampY = outputY.fileModificationTimeStamp
		val stampA = outputA.fileModificationTimeStamp

		changeNonOpenedFile("X", sourceX_modify_x3)
		joinServerRequests();

		val changedY = outputY.fileModificationTimeStamp !== stampY;
		val unchangedA = outputA.fileModificationTimeStamp === stampA;
		assertTrue("output file of file Y should have been rebuilt", changedY);
		assertTrue("output file of file A should NOT have been rebuilt", unchangedA);
	}

	@Test
	def void test_builder_stopsInA() {
		prepare()

		val outputY = getOutputFileForTestFile("Y")
		val outputA = getOutputFileForTestFile("A")
		val outputB = getOutputFileForTestFile("B")
		val outputC = getOutputFileForTestFile("C")
		val outputD = getOutputFileForTestFile("D")

		val stampY = outputY.fileModificationTimeStamp
		val stampA = outputA.fileModificationTimeStamp
		val stampB = outputB.fileModificationTimeStamp
		val stampC = outputC.fileModificationTimeStamp
		val stampD = outputD.fileModificationTimeStamp

		changeNonOpenedFile("X", sourceX_modify_x2)
		joinServerRequests();

		val changedY = outputY.fileModificationTimeStamp !== stampY;
		val changedA = outputA.fileModificationTimeStamp !== stampA;
		val unchangedB = outputB.fileModificationTimeStamp === stampB;
		val unchangedC = outputC.fileModificationTimeStamp === stampC;
		val unchangedD = outputD.fileModificationTimeStamp === stampD;
		assertTrue("output file of file Y should have been rebuilt", changedY);
		assertTrue("output file of file A should have been rebuilt", changedA);
		assertTrue("output file of file B should NOT have been rebuilt", unchangedB);
		assertTrue("output file of file C should NOT have been rebuilt", unchangedC);
		assertTrue("output file of file D should NOT have been rebuilt", unchangedD);
	}

	@Test
	def void test_builder_changeP() {
		prepare();

		changeNonOpenedFile("P", sourceP_modify_p);
		joinServerRequests();
		assertIssues("P" -> #["(Error, [2:17 - 2:18], int is not a subtype of string.)"]);

		changeNonOpenedFile("P", sourceP);
		joinServerRequests();
		assertNoIssues();
	}
	
	@Test
	def void test_inEditor_changeP() {
		prepare();

		openFile("P");
		joinServerRequests();
		assertNoIssues();
		val expectedFromSource = #{
			getFileURIFromModuleName('P'),
			getFileURIFromModuleName('Q')
		}
		assertFromSource("P", expectedFromSource);

		changeOpenedFile("P", sourceP_modify_p);
		joinServerRequests();
		assertIssues(
			"P" -> #["(Error, [2:17 - 2:18], int is not a subtype of string.)"]
		);
		assertFromSource("P", expectedFromSource);

		changeOpenedFile("P", sourceP);
		joinServerRequests();
		assertNoIssues();
	}

	def private FileURI getOutputFileForTestFile(String moduleName) {
		val srcFileURI = getFileURIFromModuleName(moduleName);
		val outFileURI = srcFileURI.parent.parent.parent.resolve("src-gen/m/" + moduleName + ".js");
		Assert.assertTrue("output file not found for module " + moduleName + ": " + outFileURI,
			outFileURI.toFile.exists);
		resetFileModificationTimeStamp(outFileURI);
		return outFileURI;
	}
}
