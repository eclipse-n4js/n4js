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

import static org.eclipse.n4js.ide.tests.server.TestWorkspaceCreator.*
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
	def void testChangeInNonOpenedFile() {
		workspaceCreator.createTestProjectOnDisk(
			"C" -> '''export public class C {}'''
		);
		startAndWaitForLspServer();
		assertNoIssues();

		val outputFileSnapshot = createSnapshotForOutputFile("C");
		val projectStateSnapshot = createSnapshotForProjectStateFile();

		changeNonOpenedFile("C", ' C ' -> ' C1 ');
		joinServerRequests();
cleanBuildAndWait(); // FIXME remove!

		outputFileSnapshot.assertChanged();
		projectStateSnapshot.assertChanged();
	}

	@Test
	def void testChangeInOpenedFile() {
		workspaceCreator.createTestProjectOnDisk(
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

		outputFileSnapshot.assertUnchanged();
		projectStateSnapshot.assertUnchanged();

		saveOpenedFile("C");
		joinServerRequests();

		outputFileSnapshot.assertChanged();
		projectStateSnapshot.assertChanged();
	}

	@Test
	def void testChangesInSeveralOpenedFiles_withoutDependency() {
		workspaceCreator.createTestProjectOnDisk(
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

		// as long as only one of the two changed files is saved,
		// output artifacts should not be re-generated:
		assertNoIssues();
		cOutputFileSnapshot.assertUnchanged();
		dOutputFileSnapshot.assertUnchanged();
		projectStateSnapshot.assertUnchanged();

		saveOpenedFile("D");
		joinServerRequests();

		assertNoIssues();
// FIXME activate this assertion!
//		cOutputFileSnapshot.assertChanged();
		dOutputFileSnapshot.assertChanged();
		projectStateSnapshot.assertChanged();
	}

	@Test
	def void testChangesInSeveralOpenedFiles_withDependency() {
		workspaceCreator.createTestProjectOnDisk(testDataWithDependency);
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

		// as long as only one of the two changed files is saved,
		// output artifacts should not be re-generated:
		assertNoIssues();
		otherOutputFileSnapshot.assertUnchanged();
		mainOutputFileSnapshot.assertUnchanged();
		projectStateSnapshot.assertUnchanged();

		saveOpenedFile("Other");
		joinServerRequests();

		assertNoIssues();
		otherOutputFileSnapshot.assertChanged();
		mainOutputFileSnapshot.assertChanged();
		projectStateSnapshot.assertChanged();
	}

	@Test
	def void testChangesInSeveralOpenedFiles_withDependency_reverseSaveOrder() {
		workspaceCreator.createTestProjectOnDisk(testDataWithDependency);
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

		// as long as only one of the two changed files is saved,
		// output artifacts should not be re-generated:
		assertNoIssues();
		otherOutputFileSnapshot.assertUnchanged();
		mainOutputFileSnapshot.assertUnchanged();
		projectStateSnapshot.assertUnchanged();

		saveOpenedFile("Main");
		joinServerRequests();

		assertNoIssues();
// FIXME activate this assertion!
//		otherOutputFileSnapshot.assertChanged();
		mainOutputFileSnapshot.assertChanged();
		projectStateSnapshot.assertChanged();
	}

	@Test
	def void testInNodeModules_noValidation() {
		workspaceCreator.createTestProjectOnDisk(testDataWithNodeModules);
		startAndWaitForLspServer();
		assertNoIssues();

		changeNonOpenedFile("Other", 'undefined' -> '"not a number"'); // provoke 1 error in "Other"
		joinServerRequests();
		
		cleanBuildAndWait();
		assertNoIssues(); // no validation in node_modules folders
	}

	@Test
	def void testInNodeModules_changeInNonOpenedFile() {
		workspaceCreator.createTestProjectOnDisk(testDataWithNodeModules);
		startAndWaitForLspServer();
		assertNoIssues();

		val outputFile = getOutputFile("OtherProject", "Other");
		assertFalse(outputFile.exists());

		val projectStateSnapshot = createSnapshotForProjectStateFile("OtherProject");

		changeNonOpenedFile("Other", ': number {' -> ': any {');
		joinServerRequests();

		assertFalse(outputFile.exists());
		projectStateSnapshot.assertUnchanged();
		assertNoIssues(); // note: the issue in Main does not show up

		projectStateSnapshot.file.delete();

		changeNonOpenedFile("Other", ': any {' -> ': string {');
		joinServerRequests();

		assertFalse(outputFile.exists());
		projectStateSnapshot.assertNotExists();
		assertNoIssues(); // note: the issue in Main does not show up
		
		cleanBuildAndWait();
		assertFalse(outputFile.exists());
		projectStateSnapshot.assertExists();
		assertIssues("Main" -> #[ "(Error, [1:16 - 1:31], string is not a subtype of number.)" ]);
	}

	@Test
	def void testInNodeModules_changeInOpenedFile() {
		workspaceCreator.createTestProjectOnDisk(testDataWithNodeModules);
		startAndWaitForLspServer();
		assertNoIssues();

		val outputFile = getOutputFile("OtherProject", "Other");
		assertFalse(outputFile.exists());

		val projectStateSnapshot = createSnapshotForProjectStateFile("OtherProject");

		openFile("Other");
		changeOpenedFile("Other", ': number {' -> ': any {');
		joinServerRequests();

		assertFalse(outputFile.exists());
		projectStateSnapshot.assertUnchanged();
		assertNoIssues(); // note: the issue in Main does not show up

		projectStateSnapshot.file.delete();

		changeOpenedFile("Other", ': any {' -> ': string {');
		joinServerRequests();

		assertFalse(outputFile.exists());
		projectStateSnapshot.assertNotExists();
		assertNoIssues(); // note: the issue in Main does not show up
		
		cleanBuildAndWait();
		assertFalse(outputFile.exists());
		projectStateSnapshot.assertExists();
		assertIssues("Main" -> #[ "(Error, [1:16 - 1:31], string is not a subtype of number.)" ]);
	}

	@Test
	def void testCleanAndRebuild() {
		workspaceCreator.createTestProjectOnDisk(testDataWithNodeModules);
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

		languageServer.clean();
		joinServerRequests();

		assertFalse(outputFileInNodeModules.exists);
		assertFalse(outputFileInOrdinaryProject.exists);
		assertFalse(projectStateInNodeModules.exists);
		assertFalse(projectStateInOrdinaryProject.exists);

		languageServer.reinitWorkspace();
		joinServerRequests();

		assertFalse(outputFileInNodeModules.exists);
		assertTrue(outputFileInOrdinaryProject.exists);
		assertTrue(projectStateInNodeModules.exists);
		assertTrue(projectStateInOrdinaryProject.exists);
	}
}
