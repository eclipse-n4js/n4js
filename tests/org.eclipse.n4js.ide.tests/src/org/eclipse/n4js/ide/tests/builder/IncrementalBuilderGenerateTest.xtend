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
package org.eclipse.n4js.ide.tests.builder

import org.junit.Test

import static org.eclipse.n4js.ide.tests.server.TestWorkspaceManager.*
import static org.junit.Assert.*

/**
 * Tests generation of build artifacts (i.e. output files and <code>.n4js.projectstate</code> files).
 */
class IncrementalBuilderGenerateTest extends AbstractIncrementalBuilderTest {

	private static val testDataWithDependency = #[
		"Other" -> '''
			export public class C {}
		''',
		"Main" -> '''
			import {C} from "Other";
			new C();
		'''
	];

	private static val testDataWithNodeModules = #[
		NODE_MODULES + "OtherProject" + SRC + "Other" -> '''
			export public class Other {
				public m(): number { return undefined; }
			}
		''',
		"Main" -> '''
			import {Other} from "Other";
			let n: number = new Other().m();
		'''
	];

	@Test
	def void testChangeInNonOpenedFile_whileWorkspaceIsInCleanState() {
		testWorkspaceManager.createTestProjectOnDisk(
			"C" -> '''export public class C {}'''
		);
		startAndWaitForLspServer();
		assertNoIssues();

		val outputFileSnapshot = createSnapshotForOutputFile("C");
		val projectStateSnapshot = createSnapshotForProjectStateFile();

		// the event under test:
		changeNonOpenedFile("C", ' C ' -> ' C1 ');
		joinServerRequests();

		outputFileSnapshot.assertChanged(); // change on disk occurred while workspace was in clean state, so re-generated immediately!
		projectStateSnapshot.assertChanged();
	}

	@Test
	def void testChangeInNonOpenedFile_whileWorkspaceIsInDirtyState() {
		testWorkspaceManager.createTestProjectOnDisk(
			"C" -> '''export public class C {}''',
			"D" -> '''export public class D {}'''
		);
		startAndWaitForLspServer();
		assertNoIssues();

		val outputFileSnapshot = createSnapshotForOutputFile("C");
		val projectStateSnapshot = createSnapshotForProjectStateFile();

		// put workspace into dirty state
		openFile("D");
		changeOpenedFile("D", ' D ' -> ' D1 ');
		joinServerRequests();

		outputFileSnapshot.assertUnchanged();
		projectStateSnapshot.assertUnchanged();

		// the event under test:
		changeNonOpenedFile("C", ' C ' -> ' C1 ');
		joinServerRequests();

		outputFileSnapshot.assertChanged(); // even though workspace is in dirty state, C must already be regenerated
		projectStateSnapshot.assertChanged();

		// transition workspace into clean state
		saveOpenedFile("D");
		joinServerRequests();

		outputFileSnapshot.assertChanged(); // now C must be regenerated, because workspace went back to clean state
		projectStateSnapshot.assertChanged();
	}

	@Test
	def void testChangeInOpenedFile() {
		testWorkspaceManager.createTestProjectOnDisk(
			"C" -> '''export public class C {}'''
		);
		startAndWaitForLspServer();
		assertNoIssues();

		val outputFileSnapshot = createSnapshotForOutputFile("C");
		val projectStateSnapshot = createSnapshotForProjectStateFile();

		openFile("C");
		joinServerRequests();
		changeOpenedFile("C", ' C ' -> ' C1 ');
		joinServerRequests();

		outputFileSnapshot.assertUnchanged(); // still in dirty state, so nothing re-generated yet!
		projectStateSnapshot.assertUnchanged();

		saveOpenedFile("C");
		joinServerRequests();

		outputFileSnapshot.assertChanged(); // now C must be regenerated, because workspace went back to clean state
		projectStateSnapshot.assertChanged();
	}

	/**
	 * Given two modules Main, Other with a dependency from Main to Other, this test asserts that a change in Other
	 * that has an effect on Main's output code will appear in Main's output file the moment Other is saved (assuming
	 * Other is the only file being open).
	 */
	@Test
	def void testChangeInOpenedFile_propagatesToOutputFileOfDependentModuleWhenSaved() {
		testWorkspaceManager.createTestProjectOnDisk(
			"Other" -> '''
				// @StringBased
				export public enum Color { RED, BLUE }
			''',
			"Main" -> '''
				import {Color} from "Other";
				let c = Color.RED;
			'''
		);
		startAndWaitForLspServer();
		assertNoIssues();

		val mainOutputFileURI = getOutputFile("Main").toFileURI;

		val otherOutputFileSnapshot = createSnapshotForOutputFile("Other");
		val mainOutputFileSnapshot = createSnapshotForOutputFile("Main");
		val projectStateSnapshot = createSnapshotForProjectStateFile();

		assertContentOfFileOnDisk(mainOutputFileURI, '''
			[...]
			let c = Color.RED;
			[...]
		''');

		openFile("Other");
		joinServerRequests();

		changeOpenedFile("Other", '// @StringBased' -> '@StringBased');
		joinServerRequests();

		assertNoIssues();
		otherOutputFileSnapshot.assertUnchanged();
		mainOutputFileSnapshot.assertUnchanged();
		projectStateSnapshot.assertUnchanged();

		saveOpenedFile("Other");
		joinServerRequests();

		// due to Color now being a string-based enum, the output file of Main
		// should have changed the moment Other was saved:
		assertNoIssues();
		otherOutputFileSnapshot.assertChanged();
		mainOutputFileSnapshot.assertChanged();
		projectStateSnapshot.assertChanged();

		// ... and the enum literal should be represented as a plain string literal:
		assertContentOfFileOnDisk(mainOutputFileURI, '''
			[...]
			let c = 'RED';
			[...]
		''');
	}

	@Test
	def void testChangesInSeveralOpenedFiles_withoutDependency() {
		testWorkspaceManager.createTestProjectOnDisk(
			"C" -> '''export public class C {}''',
			"D" -> '''export public class D {}'''
		);
		startAndWaitForLspServer();
		assertNoIssues();

		val cOutputFileSnapshot = createSnapshotForOutputFile("C");
		val dOutputFileSnapshot = createSnapshotForOutputFile("D");
		val projectStateSnapshot = createSnapshotForProjectStateFile();

		openFile("C");
		openFile("D");
		joinServerRequests();

		changeOpenedFile("C", 'C' -> 'C1');
		changeOpenedFile("D", 'D' -> 'D1');
		joinServerRequests();

		assertNoIssues();
		cOutputFileSnapshot.assertUnchanged();
		dOutputFileSnapshot.assertUnchanged();
		projectStateSnapshot.assertUnchanged();

		saveOpenedFile("C");
		joinServerRequests();

		// even though only one of the two changed files is saved,
		// output artifacts should already be re-generated:
		assertNoIssues();
		cOutputFileSnapshot.assertChanged();
		dOutputFileSnapshot.assertUnchanged();
		projectStateSnapshot.assertChanged();

		saveOpenedFile("D");
		joinServerRequests();

		assertNoIssues();
		cOutputFileSnapshot.assertChanged();
		dOutputFileSnapshot.assertChanged();
		projectStateSnapshot.assertChanged();
	}

	@Test
	def void testChangesInSeveralOpenedFiles_withoutDependency_closeChangedFile() {
		testWorkspaceManager.createTestProjectOnDisk(
			"C" -> '''export public class C {}''',
			"D" -> '''export public class D {}'''
		);
		startAndWaitForLspServer();
		assertNoIssues();

		val cOutputFileSnapshot = createSnapshotForOutputFile("C");
		val dOutputFileSnapshot = createSnapshotForOutputFile("D");
		val projectStateSnapshot = createSnapshotForProjectStateFile();

		openFile("C");
		openFile("D");
		joinServerRequests();

		changeOpenedFile("C", 'C' -> 'C1');
		changeOpenedFile("D", 'D' -> 'D1');
		joinServerRequests();

		assertNoIssues();
		cOutputFileSnapshot.assertUnchanged();
		dOutputFileSnapshot.assertUnchanged();
		projectStateSnapshot.assertUnchanged();

		saveOpenedFile("C");
		joinServerRequests();

		// even though only one of the two changed files is saved,
		// output artifacts should already be re-generated:
		assertNoIssues();
		cOutputFileSnapshot.assertChanged();
		dOutputFileSnapshot.assertUnchanged();
		projectStateSnapshot.assertChanged();

		// transition into workspace clean state not by saving "D", but
		// by closing "D" (discarding its unsaved changes)
		closeFileDiscardingChanges("D", false);
		joinServerRequests();

		assertNoIssues();
		cOutputFileSnapshot.assertChanged();
		dOutputFileSnapshot.assertUnchanged();
		projectStateSnapshot.assertChanged();
	}

	@Test
	def void testChangesInSeveralOpenedFiles_withDependency() {
		testWorkspaceManager.createTestProjectOnDisk(testDataWithDependency);
		startAndWaitForLspServer();
		assertNoIssues();

		val otherOutputFileSnapshot = createSnapshotForOutputFile("Other");
		val mainOutputFileSnapshot = createSnapshotForOutputFile("Main");
		val projectStateSnapshot = createSnapshotForProjectStateFile();

		openFile("Other");
		openFile("Main");
		joinServerRequests();

		changeOpenedFile("Other", 'C' -> 'C1');
		changeOpenedFile("Main",
			'{C}' -> '{C1}',
			'new C(' -> 'new C1('
		);
		joinServerRequests();

		assertNoIssues();
		otherOutputFileSnapshot.assertUnchanged();
		mainOutputFileSnapshot.assertUnchanged();
		projectStateSnapshot.assertUnchanged();

		saveOpenedFile("Main");
		joinServerRequests();

		// even though only one of the two changed files is saved,
		// output artifacts should already be re-generated:
		assertNoIssues(); // Main has an error on disk, but it is hidden by the open editor for Main using Other's dirty state!
		otherOutputFileSnapshot.assertUnchanged();
		mainOutputFileSnapshot.assertNotExists(); // deleted, because it now has an error on disk
		projectStateSnapshot.assertChanged();

		saveOpenedFile("Other");
		joinServerRequests();

		assertNoIssues();
		otherOutputFileSnapshot.assertChanged();
		mainOutputFileSnapshot.assertChanged();
		projectStateSnapshot.assertChanged();
	}

	@Test
	def void testChangesInSeveralOpenedFiles_withDependency_reverseSaveOrder() {
		testWorkspaceManager.createTestProjectOnDisk(testDataWithDependency);
		startAndWaitForLspServer();
		assertNoIssues();

		val otherOutputFileSnapshot = createSnapshotForOutputFile("Other");
		val mainOutputFileSnapshot = createSnapshotForOutputFile("Main");
		val projectStateSnapshot = createSnapshotForProjectStateFile();

		openFile("Other");
		openFile("Main");
		joinServerRequests();

		changeOpenedFile("Other", 'C' -> 'C1');
		changeOpenedFile("Main",
			'{C}' -> '{C1}',
			'new C(' -> 'new C1('
		);
		joinServerRequests();

		assertNoIssues();
		otherOutputFileSnapshot.assertUnchanged();
		mainOutputFileSnapshot.assertUnchanged();
		projectStateSnapshot.assertUnchanged();

		saveOpenedFile("Other");
		joinServerRequests();

		// even though only one of the two changed files is saved,
		// output artifacts should already be re-generated:
		assertNoIssues(); // Main has an error on disk, but it is hidden by the open editor for Main using Other's dirty state!
		otherOutputFileSnapshot.assertChanged();
		mainOutputFileSnapshot.assertNotExists(); // deleted, because it now has an error on disk
		projectStateSnapshot.assertChanged();

		saveOpenedFile("Main");
		joinServerRequests();

		assertNoIssues();
		otherOutputFileSnapshot.assertChanged();
		mainOutputFileSnapshot.assertChanged();
		projectStateSnapshot.assertChanged();
	}

	@Test
	def void testInNodeModules_noValidation() {
		testWorkspaceManager.createTestProjectOnDisk(testDataWithNodeModules);
		startAndWaitForLspServer();
		assertNoIssues();

		changeNonOpenedFile("Other", 'undefined' -> '"not a number"'); // provoke 1 error in "Other"
		joinServerRequests();
		
		cleanBuildAndWait();
		assertNoIssues(); // no validation in node_modules folders
	}

	@Test
	def void testInNodeModules_changeInNonOpenedFile() {
		testWorkspaceManager.createTestProjectOnDisk(testDataWithNodeModules);
		startAndWaitForLspServer();
		assertNoIssues();

		val outputFile = getOutputFile("OtherProject", "Other");
		assertFalse(outputFile.exists());

		val projectStateSnapshot = createSnapshotForProjectStateFile("OtherProject");

		changeNonOpenedFile("Other", ': number {' -> ': any {');
		joinServerRequests();

		assertFalse(outputFile.exists()); // never generate output files in node_modules folders
		projectStateSnapshot.assertChanged();
		assertIssues("Main" -> #[ "(Error, [1:16 - 1:31], any is not a subtype of number.)" ]);

		projectStateSnapshot.file.delete();

		changeNonOpenedFile("Other", ': any {' -> ': string {');
		joinServerRequests();

		assertFalse(outputFile.exists()); // never generate output files in node_modules folders
		projectStateSnapshot.assertExists(); // recreated
		assertIssues("Main" -> #[ "(Error, [1:16 - 1:31], string is not a subtype of number.)" ]);

		cleanBuildAndWait();
		assertFalse(outputFile.exists());
		projectStateSnapshot.assertExists();
		assertIssues("Main" -> #[ "(Error, [1:16 - 1:31], string is not a subtype of number.)" ]);
	}

	@Test
	def void testInNodeModules_changeInOpenedFile() {
		testWorkspaceManager.createTestProjectOnDisk(testDataWithNodeModules);
		startAndWaitForLspServer();
		assertNoIssues();

		val outputFile = getOutputFile("OtherProject", "Other");
		assertFalse(outputFile.exists());

		val projectStateSnapshot = createSnapshotForProjectStateFile("OtherProject");

		openFile("Other");
		changeOpenedFile("Other", ': number {' -> ': any {');
		joinServerRequests();

		assertFalse(outputFile.exists()); // never generate output files in node_modules folders
		projectStateSnapshot.assertUnchanged(); // not updated, because Other.n4js not saved yet
		assertIssues("Main" -> #[]); // not updated, because Other.n4js not saved yet

		saveOpenedFile("Other");
		joinServerRequests();

		assertFalse(outputFile.exists()); // never generate output files in node_modules folders
		projectStateSnapshot.assertChanged();
		assertIssues("Main" -> #[ "(Error, [1:16 - 1:31], any is not a subtype of number.)" ]);

		projectStateSnapshot.file.delete();

		changeOpenedFile("Other", ': any {' -> ': string {');
		joinServerRequests();

		assertFalse(outputFile.exists()); // never generate output files in node_modules folders
		projectStateSnapshot.assertNotExists(); // not recreated, because Other.n4js not saved yet
		assertIssues("Main" -> #[ "(Error, [1:16 - 1:31], any is not a subtype of number.)" ]); // not updated, because Other.n4js not saved yet

		saveOpenedFile("Other");
		joinServerRequests();

		assertFalse(outputFile.exists()); // never generate output files in node_modules folders
		projectStateSnapshot.assertExists(); // recreated
		assertIssues("Main" -> #[ "(Error, [1:16 - 1:31], string is not a subtype of number.)" ]); // updated
	}

	@Test
	def void testCleanAndRebuild() {
		testWorkspaceManager.createTestProjectOnDisk(testDataWithNodeModules);
		startAndWaitForLspServer();
		assertNoIssues();

		val outputFileInNodeModules = getOutputFile("OtherProject", "Other");
		val outputFileInOrdinaryProject = getOutputFile("Main");
		val projectStateInNodeModules = getProjectStateFile("OtherProject");
		val projectStateInOrdinaryProject = getProjectStateFile();

		assertFalse(outputFileInNodeModules.exists);
		assertTrue(outputFileInOrdinaryProject.exists);
		assertTrue(projectStateInNodeModules.exists);
		assertTrue(projectStateInOrdinaryProject.exists);

		languageServer.getFrontend().clean();
		joinServerRequests();

		assertFalse(outputFileInNodeModules.exists);
		assertFalse(outputFileInOrdinaryProject.exists);
		assertFalse(projectStateInNodeModules.exists);
		assertFalse(projectStateInOrdinaryProject.exists);

		languageServer.getFrontend().reinitWorkspace();
		joinServerRequests();

		assertFalse(outputFileInNodeModules.exists);
		assertTrue(outputFileInOrdinaryProject.exists);
		assertTrue(projectStateInNodeModules.exists);
		assertTrue(projectStateInOrdinaryProject.exists);
	}
}
