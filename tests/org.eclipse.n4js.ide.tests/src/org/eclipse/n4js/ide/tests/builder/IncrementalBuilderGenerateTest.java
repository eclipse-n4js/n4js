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
package org.eclipse.n4js.ide.tests.builder;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.eclipse.n4js.workspace.locations.FileURI;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.Test;

/**
 * Tests generation of build artifacts (i.e. output files and <code>.n4js.projectstate</code> files).
 */

public class IncrementalBuilderGenerateTest extends AbstractIncrementalBuilderTest {

	private static Map<String, String> testDataWithDependency = Map.of(
			"Other", """
						export public class C {}
					""",
			"Main", """
						import {C} from "Other";
						new C();
					""");

	private static Map<String, String> testDataWithNodeModules = Map.of(
			CFG_NODE_MODULES + "OtherProject" + CFG_SRC + "Other", """
						export public class Other {
							public m(): number { return undefined; }
						}
					""",
			"Main", """
						import {Other} from "Other";
						let n: number = new Other().m();
					""");

	@Test
	public void testChangeInNonOpenedFile_whileWorkspaceIsInCleanState() {
		testWorkspaceManager.createTestProjectOnDisk(Map.of(
				"C", "export public class C {}"));
		startAndWaitForLspServer();
		assertNoIssues();

		FileSnapshot outputFileSnapshot = createSnapshotForOutputFile("C");
		FileSnapshot projectStateSnapshot = createSnapshotForProjectStateFile();

		// the event under test:
		changeNonOpenedFile("C", Pair.of(" C ", " C1 "));
		joinServerRequests();

		outputFileSnapshot.assertChanged(); // change on disk occurred while workspace was in clean state, so
											// re-generated immediately!
		projectStateSnapshot.assertChanged();
	}

	@Test
	public void testChangeInNonOpenedFile_whileWorkspaceIsInDirtyState() {
		testWorkspaceManager.createTestProjectOnDisk(Map.of(
				"C", "export public class C {}",
				"D", "export public class D {}"));
		startAndWaitForLspServer();
		assertNoIssues();

		FileSnapshot outputFileSnapshot = createSnapshotForOutputFile("C");
		FileSnapshot projectStateSnapshot = createSnapshotForProjectStateFile();

		// put workspace into dirty state
		openFile("D");
		changeOpenedFile("D", Pair.of(" D ", " D1 "));
		joinServerRequests();

		outputFileSnapshot.assertUnchanged();
		projectStateSnapshot.assertUnchanged();

		// the event under test:
		changeNonOpenedFile("C", Pair.of(" C ", " C1 "));
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
	public void testChangeInOpenedFile() {
		testWorkspaceManager.createTestProjectOnDisk(Map.of(
				"C", "export public class C {}"));
		startAndWaitForLspServer();
		assertNoIssues();

		FileSnapshot outputFileSnapshot = createSnapshotForOutputFile("C");
		FileSnapshot projectStateSnapshot = createSnapshotForProjectStateFile();

		openFile("C");
		joinServerRequests();
		changeOpenedFile("C", Pair.of(" C ", " C1 "));
		joinServerRequests();

		outputFileSnapshot.assertUnchanged(); // still in dirty state, so nothing re-generated yet!
		projectStateSnapshot.assertUnchanged();

		saveOpenedFile("C");
		joinServerRequests();

		outputFileSnapshot.assertChanged(); // now C must be regenerated, because workspace went back to clean state
		projectStateSnapshot.assertChanged();
	}

	/**
	 * Given two modules Main, Other with a dependency from Main to Other, this test asserts that a change in Other that
	 * has an effect on Main's output code will appear in Main's output file the moment Other is saved (assuming Other
	 * is the only file being open).
	 */
	@Test
	public void testChangeInOpenedFile_propagatesToOutputFileOfDependentModuleWhenSaved() {
		testWorkspaceManager.createTestProjectOnDisk(Map.of(
				"Other", """
							// @StringBased
							export public enum Color { RED, BLUE }
						""",
				"Main", """
							import {Color} from "Other";
							let c = Color.RED;
						"""));
		startAndWaitForLspServer();
		assertNoIssues();

		FileURI mainOutputFileURI = toFileURI(getOutputFile("Main"));

		FileSnapshot otherOutputFileSnapshot = createSnapshotForOutputFile("Other");
		FileSnapshot mainOutputFileSnapshot = createSnapshotForOutputFile("Main");
		FileSnapshot projectStateSnapshot = createSnapshotForProjectStateFile();

		assertContentOfFileOnDisk(mainOutputFileURI, """
					[...]
					let c = Color.RED;
					[...]
				""");

		openFile("Other");
		joinServerRequests();

		changeOpenedFile("Other", Pair.of("// @StringBased", "@StringBased"));
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
		assertContentOfFileOnDisk(mainOutputFileURI, """
					[...]
					let c = 'RED';
					[...]
				""");
	}

	@Test
	public void testChangesInSeveralOpenedFiles_withoutDependency() {
		testWorkspaceManager.createTestProjectOnDisk(Map.of(
				"C", "export public class C {}",
				"D", "export public class D {}"));
		startAndWaitForLspServer();
		assertNoIssues();

		FileSnapshot cOutputFileSnapshot = createSnapshotForOutputFile("C");
		FileSnapshot dOutputFileSnapshot = createSnapshotForOutputFile("D");
		FileSnapshot projectStateSnapshot = createSnapshotForProjectStateFile();

		openFile("C");
		openFile("D");
		joinServerRequests();

		changeOpenedFile("C", Pair.of("C", "C1"));
		changeOpenedFile("D", Pair.of("D", "D1"));
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
	public void testChangesInSeveralOpenedFiles_withoutDependency_closeChangedFile() {
		testWorkspaceManager.createTestProjectOnDisk(Map.of(
				"C", "export public class C {}",
				"D", "export public class D {}"));
		startAndWaitForLspServer();
		assertNoIssues();

		FileSnapshot cOutputFileSnapshot = createSnapshotForOutputFile("C");
		FileSnapshot dOutputFileSnapshot = createSnapshotForOutputFile("D");
		FileSnapshot projectStateSnapshot = createSnapshotForProjectStateFile();

		openFile("C");
		openFile("D");
		joinServerRequests();

		changeOpenedFile("C", Pair.of("C", "C1"));
		changeOpenedFile("D", Pair.of("D", "D1"));
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
	public void testChangesInSeveralOpenedFiles_withDependency() {
		testWorkspaceManager.createTestProjectOnDisk(testDataWithDependency);
		startAndWaitForLspServer();
		assertNoIssues();

		FileSnapshot otherOutputFileSnapshot = createSnapshotForOutputFile("Other");
		FileSnapshot mainOutputFileSnapshot = createSnapshotForOutputFile("Main");
		FileSnapshot projectStateSnapshot = createSnapshotForProjectStateFile();

		openFile("Other");
		openFile("Main");
		joinServerRequests();

		changeOpenedFile("Other", Pair.of("C", "C1"));
		changeOpenedFile("Main",
				Pair.of("{C}", "{C1}"),
				Pair.of("new C(", "new C1("));
		joinServerRequests();

		assertNoIssues();
		otherOutputFileSnapshot.assertUnchanged();
		mainOutputFileSnapshot.assertUnchanged();
		projectStateSnapshot.assertUnchanged();

		saveOpenedFile("Main");
		joinServerRequests();

		// even though only one of the two changed files is saved,
		// output artifacts should already be re-generated:
		assertNoIssues(); // Main has an error on disk, but it is hidden by the open editor for Main using Other's dirty
							// state!
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
	public void testChangesInSeveralOpenedFiles_withDependency_reverseSaveOrder() {
		testWorkspaceManager.createTestProjectOnDisk(testDataWithDependency);
		startAndWaitForLspServer();
		assertNoIssues();

		FileSnapshot otherOutputFileSnapshot = createSnapshotForOutputFile("Other");
		FileSnapshot mainOutputFileSnapshot = createSnapshotForOutputFile("Main");
		FileSnapshot projectStateSnapshot = createSnapshotForProjectStateFile();

		openFile("Other");
		openFile("Main");
		joinServerRequests();

		changeOpenedFile("Other", Pair.of("C", "C1"));
		changeOpenedFile("Main",
				Pair.of("{C}", "{C1}"),
				Pair.of("new C(", "new C1("));
		joinServerRequests();

		assertNoIssues();
		otherOutputFileSnapshot.assertUnchanged();
		mainOutputFileSnapshot.assertUnchanged();
		projectStateSnapshot.assertUnchanged();

		saveOpenedFile("Other");
		joinServerRequests();

		// even though only one of the two changed files is saved,
		// output artifacts should already be re-generated:
		assertNoIssues(); // Main has an error on disk, but it is hidden by the open editor for Main using Other's dirty
							// state!
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
	public void testInNodeModules_noValidation() {
		testWorkspaceManager.createTestProjectOnDisk(testDataWithNodeModules);
		startAndWaitForLspServer();
		assertNoIssues();

		changeNonOpenedFile("Other", Pair.of("undefined", "\"not a number\"")); // provoke 1 error in "Other"
		joinServerRequests();

		cleanBuildAndWait();
		assertNoIssues(); // no validation in node_modules folders
	}

	@Test
	public void testInNodeModules_changeInNonOpenedFile() {
		testWorkspaceManager.createTestProjectOnDisk(testDataWithNodeModules);
		startAndWaitForLspServer();
		assertNoIssues();

		File outputFile = getOutputFile("OtherProject", "Other");
		assertFalse(outputFile.exists());

		FileSnapshot projectStateSnapshot = createSnapshotForProjectStateFile("OtherProject");

		changeNonOpenedFile("Other", Pair.of(": number {", ": any {"));
		joinServerRequests();

		assertFalse(outputFile.exists()); // never generate output files in node_modules folders
		projectStateSnapshot.assertChanged();
		assertIssues2(Pair.of("Main", List.of("(Error, [1:17 - 1:32], any is not a subtype of number.)")));

		projectStateSnapshot.getFile().delete();

		changeNonOpenedFile("Other", Pair.of(": any {", ": string {"));
		joinServerRequests();

		assertFalse(outputFile.exists()); // never generate output files in node_modules folders
		projectStateSnapshot.assertExists(); // recreated
		assertIssues2(Pair.of("Main", List.of("(Error, [1:17 - 1:32], string is not a subtype of number.)")));

		cleanBuildAndWait();
		assertFalse(outputFile.exists());
		projectStateSnapshot.assertExists();
		assertIssues2(Pair.of("Main", List.of("(Error, [1:17 - 1:32], string is not a subtype of number.)")));
	}

	@Test
	public void testInNodeModules_changeInOpenedFile() {
		testWorkspaceManager.createTestProjectOnDisk(testDataWithNodeModules);
		startAndWaitForLspServer();
		assertNoIssues();

		File outputFile = getOutputFile("OtherProject", "Other");
		assertFalse(outputFile.exists());

		FileSnapshot projectStateSnapshot = createSnapshotForProjectStateFile("OtherProject");

		openFile("Other");
		changeOpenedFile("Other", Pair.of(": number {", ": any {"));
		joinServerRequests();

		assertFalse(outputFile.exists()); // never generate output files in node_modules folders
		projectStateSnapshot.assertUnchanged(); // not updated, because Other.n4js not saved yet
		assertIssues2(Pair.of("Main", List.of())); // not updated, because Other.n4js not saved yet

		saveOpenedFile("Other");
		joinServerRequests();

		assertFalse(outputFile.exists()); // never generate output files in node_modules folders
		projectStateSnapshot.assertChanged();
		assertIssues2(Pair.of("Main", List.of("(Error, [1:17 - 1:32], any is not a subtype of number.)")));

		projectStateSnapshot.getFile().delete();

		changeOpenedFile("Other", Pair.of(": any {", ": string {"));
		joinServerRequests();

		assertFalse(outputFile.exists()); // never generate output files in node_modules folders
		projectStateSnapshot.assertNotExists(); // not recreated, because Other.n4js not saved yet
		// not updated, because Other.n4js not saved yet
		assertIssues2(Pair.of("Main", List.of("(Error, [1:17 - 1:32], any is not a subtype of number.)")));

		saveOpenedFile("Other");
		joinServerRequests();

		assertFalse(outputFile.exists()); // never generate output files in node_modules folders
		projectStateSnapshot.assertExists(); // recreated
		assertIssues2(Pair.of("Main", List.of("(Error, [1:17 - 1:32], string is not a subtype of number.)"))); // updated
	}

	@Test
	public void testCleanAndRebuild() {
		testWorkspaceManager.createTestProjectOnDisk(testDataWithNodeModules);
		startAndWaitForLspServer();
		assertNoIssues();

		File outputFileInNodeModules = getOutputFile("OtherProject", "Other");
		File outputFileInOrdinaryProject = getOutputFile("Main");
		File projectStateInNodeModules = getProjectStateFile("OtherProject");
		File projectStateInOrdinaryProject = getProjectStateFile();

		assertFalse(outputFileInNodeModules.exists());
		assertTrue(outputFileInOrdinaryProject.exists());
		assertTrue(projectStateInNodeModules.exists());
		assertTrue(projectStateInOrdinaryProject.exists());

		languageServer.getFrontend().clean();
		joinServerRequests();

		assertFalse(outputFileInNodeModules.exists());
		assertFalse(outputFileInOrdinaryProject.exists());
		assertFalse(projectStateInNodeModules.exists());
		assertFalse(projectStateInOrdinaryProject.exists());

		languageServer.getFrontend().rebuildWorkspace();
		joinServerRequests();

		assertFalse(outputFileInNodeModules.exists());
		assertTrue(outputFileInOrdinaryProject.exists());
		assertTrue(projectStateInNodeModules.exists());
		assertTrue(projectStateInOrdinaryProject.exists());
	}
}
