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
package org.eclipse.n4js.ide.tests.server;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.PrepareRenameResult;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.RenameParams;
import org.eclipse.lsp4j.TextDocumentIdentifier;
import org.eclipse.lsp4j.TextDocumentPositionParams;
import org.eclipse.lsp4j.TextEdit;
import org.eclipse.lsp4j.WorkspaceEdit;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.n4js.ide.tests.server.AbstractRenameTest.RenameTestConfiguration;
import org.eclipse.n4js.projectModel.locations.FileURI;
import org.eclipse.n4js.tests.codegen.Project;
import org.eclipse.xtext.xbase.lib.Pair;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;

/**
 * Abstract test class for rename protocol tests.
 */
abstract public class AbstractRenameTest extends AbstractStructuredIdeTest<RenameTestConfiguration> {

	/** Configuration for a rename test. Supports testing same rename scenario at multiple cursor positions. */
	public static class RenameTestConfiguration {
		/** Source code before the rename happens (does not contain any {@link #CURSOR_SYMBOL cursor markers}). */
		final Map<String, Map<String, String>> projectsModulesSourcesBefore;
		/** Cursor positions where rename should be initiated. */
		final List<RenamePosition> positions;
		/** The new name used during rename. */
		final String newName;
		/** Test expectation for the source code after the rename happened. */
		final Map<String, Map<String, String>> projectsModulesExpectedSourcesAfter;

		/** Creates a new {@link RenameTestConfiguration}. */
		public RenameTestConfiguration(
				Map<String, Map<String, String>> projectsModulesSourcesBefore,
				List<RenamePosition> positions,
				String newName,
				Map<String, Map<String, String>> projectsModulesExpectedSourcesAfter) {

			this.projectsModulesSourcesBefore = projectsModulesSourcesBefore;
			this.positions = ImmutableList.copyOf(positions);
			this.newName = newName;
			this.projectsModulesExpectedSourcesAfter = projectsModulesExpectedSourcesAfter;
		}
	}

	/** Position where a rename should be initiated. */
	public static class RenamePosition {
		/** Name of the project containing the module in which the rename should be initiated. */
		final String projectName;
		/** Name of the module in which the rename should be initiated. */
		final String moduleName;
		/** Line of the cursor position where the rename should be initiated. */
		final int line;
		/** Column of the cursor position where the rename should be initiated. */
		final int column;

		/** Creates a new {@link RenamePosition}. */
		public RenamePosition(String projectName, String moduleName, int line, int column) {
			this.projectName = projectName;
			this.moduleName = moduleName;
			this.line = line;
			this.column = column;
		}
	}

	/** Call this method in a single-file test. */
	protected void testAtCursors(CharSequence sourceBefore, String newName, CharSequence expectedSourceAfter) {
		Pair<String, String> sourceBeforeAsPair = Pair.of(
				TestWorkspaceManager.DEFAULT_MODULE_NAME,
				sourceBefore.toString());
		Pair<String, String> expectedSourceAfterAsPair = Pair.of(
				TestWorkspaceManager.DEFAULT_MODULE_NAME,
				expectedSourceAfter.toString());
		testAtCursors(
				Collections.singletonList(sourceBeforeAsPair),
				newName,
				Collections.singletonList(expectedSourceAfterAsPair));
	}

	/** Call this method in a single-project, multi-file test. */
	protected void testAtCursors(
			Iterable<Pair<String, String>> modulesSourcesBefore,
			String newName,
			Iterable<Pair<String, String>> modulesExpectedSourcesAfter) {

		Pair<String, ? extends Iterable<Pair<String, String>>> sourceBeforeAsPair = Pair.of(
				TestWorkspaceManager.DEFAULT_PROJECT_NAME,
				modulesSourcesBefore);
		Pair<String, ? extends Iterable<Pair<String, String>>> expectedSourceAfterAsPair = Pair.of(
				TestWorkspaceManager.DEFAULT_PROJECT_NAME,
				modulesExpectedSourcesAfter);
		testAtCursorsWS(
				Collections.singletonList(sourceBeforeAsPair),
				newName,
				Collections.singletonList(expectedSourceAfterAsPair));
	}

	/** Call this method in a multi-project test. */
	protected void testAtCursorsWS(
			Iterable<Pair<String, ? extends Iterable<Pair<String, String>>>> projectsModulesSourcesBefore,
			String newName,
			Iterable<Pair<String, ? extends Iterable<Pair<String, String>>>> projectsModulesExpectedSourcesAfter) {

		Map<String, Map<String, String>> projectsModulesSourcesBeforeAsMap = new LinkedHashMap<>();
		TestWorkspaceManager.convertProjectsModulesContentsToMap(
				projectsModulesSourcesBefore, projectsModulesSourcesBeforeAsMap, false);

		List<RenamePosition> positions = new ArrayList<>();
		for (Pair<String, ? extends Iterable<Pair<String, String>>> modulesSourcesBefore : projectsModulesSourcesBefore) {
			String projectName = modulesSourcesBefore.getKey();
			for (Pair<String, String> moduleName2SourceBefore : modulesSourcesBefore.getValue()) {
				String moduleName = moduleName2SourceBefore.getKey();
				String sourceBefore = moduleName2SourceBefore.getValue();
				if (!sourceBefore.contains(CURSOR_SYMBOL)) {
					continue;
				}
				List<ContentAndPosition> contentAndPositions = getContentAndPositions(sourceBefore.toString());
				String sourceBeforeWithoutCursors = contentAndPositions.get(0).content;
				projectsModulesSourcesBeforeAsMap.get(projectName).put(moduleName, sourceBeforeWithoutCursors);
				positions.addAll(contentAndPositions.stream()
						.map(cap -> new RenamePosition(projectName, moduleName, cap.line, cap.column))
						.collect(Collectors.toList()));
			}
		}
		assertFalse("no rename positions marked with " + CURSOR_SYMBOL + " in source code", positions.isEmpty());

		Map<String, Map<String, String>> projectsModulesExpectedSourcesAfterAsMap = new LinkedHashMap<>();
		TestWorkspaceManager.convertProjectsModulesContentsToMap(
				projectsModulesExpectedSourcesAfter, projectsModulesExpectedSourcesAfterAsMap, false);

		RenameTestConfiguration config = new RenameTestConfiguration(projectsModulesSourcesBeforeAsMap, positions,
				newName, projectsModulesExpectedSourcesAfterAsMap);

		String selectedProject = positions.get(0).projectName;
		String selectedModule = positions.get(0).moduleName;
		test(projectsModulesSourcesBeforeAsMap, selectedProject, selectedModule, config);
	}

	@Override
	protected void performTest(Project project, String moduleName, RenameTestConfiguration config)
			throws InterruptedException, ExecutionException, URISyntaxException {

		for (RenamePosition pos : config.positions) {
			performTestAtPosition(pos, config);
			joinServerRequests();
		}
	}

	private void performTestAtPosition(RenamePosition pos, RenameTestConfiguration config)
			throws InterruptedException, ExecutionException {

		FileURI fileURI = getFileURIFromModuleName(pos.moduleName);
		String uriStr = fileURI.toString();

		// ensure the file with URI 'fileURI' is open and is the only opened file
		if (!getOpenFiles().equals(Collections.singleton(fileURI))) {
			closeAllFiles();
			joinServerRequests();
			openFile(fileURI);
			joinServerRequests();
		}

		TextDocumentPositionParams textDocumentPositionParams = new TextDocumentPositionParams();
		textDocumentPositionParams.setTextDocument(new TextDocumentIdentifier(uriStr));
		textDocumentPositionParams.setPosition(new Position(pos.line, pos.column));
		Either<Range, PrepareRenameResult> result1 = languageServer.prepareRename(textDocumentPositionParams).get();
		assertTrue("element cannot be renamed",
				result1 != null && (result1.getLeft() != null || result1.getRight() != null));

		RenameParams renameParams = new RenameParams();
		renameParams.setTextDocument(new TextDocumentIdentifier(uriStr));
		renameParams.setPosition(new Position(pos.line, pos.column));
		renameParams.setNewName(config.newName);
		WorkspaceEdit workspaceEdit = languageServer.rename(renameParams).get();

		Map<FileURI, String> fileURI2ActualSourceAfter = applyWorkspaceEdit(config.projectsModulesSourcesBefore,
				workspaceEdit);

		Set<FileURI> checkedFileURIs = new LinkedHashSet<>();
		for (Map<String, String> moduleName2ExpectedSourceAfter : config.projectsModulesExpectedSourcesAfter.values()) {
			for (Entry<String, String> entry : moduleName2ExpectedSourceAfter.entrySet()) {
				String moduleName = entry.getKey();
				String expectedSourceAfter = entry.getValue();
				FileURI changedFileURI = getFileURIFromModuleName(moduleName);
				String actualSourceAfter = fileURI2ActualSourceAfter.get(changedFileURI);
				if (actualSourceAfter == null) {
					fail("expected changes in module '" + moduleName
							+ "' but rename did not lead to any changes in this module");
				} else if (!actualSourceAfter.equals(expectedSourceAfter)) {
					String msg = "rename led to incorrect source code changes in module '" + moduleName + "'\n"
							+ "EXPECTED SOURCE AFTER RENAME:\n"
							+ expectedSourceAfter.trim() + "\n"
							+ "ACTUAL SOURCE AFTER RENAME:\n"
							+ actualSourceAfter.trim();
					fail(msg);
				}
				checkedFileURIs.add(changedFileURI);
			}
		}

		for (Entry<FileURI, String> entry : fileURI2ActualSourceAfter.entrySet()) {
			FileURI changedFileURI = entry.getKey();
			String actualSourceAfter = entry.getValue();
			if (!checkedFileURIs.contains(changedFileURI)) {
				String msg = "rename led to unexpected changes in file '" + changedFileURI.getName() + "'\n"
						+ "ACTUAL SOURCE AFTER RENAME:\n"
						+ actualSourceAfter.trim();
				fail(msg);
			}
		}
	}

	/** Unchanged modules are not included in the returned map. */
	private Map<FileURI, String> applyWorkspaceEdit(Map<String, Map<String, String>> projectsModulesSourcesBefore,
			WorkspaceEdit edit) {

		Map<FileURI, List<TextEdit>> fileURI2TextEdits = new LinkedHashMap<>();
		for (Entry<String, List<TextEdit>> entry : edit.getChanges().entrySet()) {
			String uriStr = entry.getKey();
			List<TextEdit> textEdits = entry.getValue();
			FileURI fileURI = getFileURIFromURIString(uriStr);
			fileURI2TextEdits.put(fileURI, textEdits);
		}

		Map<FileURI, String> fileURI2ActualSourceAfter = new LinkedHashMap<>();
		for (Map<String, String> moduleName2SourceBefore : projectsModulesSourcesBefore.values()) {
			for (Entry<String, String> entry2 : moduleName2SourceBefore.entrySet()) {
				String moduleName = entry2.getKey();
				String sourceBefore = entry2.getValue();
				FileURI fileURI = getFileURIFromModuleName(moduleName);
				List<TextEdit> textEdits = fileURI2TextEdits.get(fileURI);
				if (textEdits != null) {
					String actualSourceAfter = applyTextEdits(sourceBefore, textEdits);
					fileURI2ActualSourceAfter.put(fileURI, actualSourceAfter);
				} else {
					// no changes in this file -> ignore
				}
			}
		}

		Set<FileURI> unknownURIs = new LinkedHashSet<>(fileURI2TextEdits.keySet());
		unknownURIs.removeAll(fileURI2ActualSourceAfter.keySet());
		assertTrue("rename led to text edits in unknown URIs: " + Joiner.on(", ").join(unknownURIs),
				unknownURIs.isEmpty());

		return fileURI2ActualSourceAfter;
	}
}
