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
package org.eclipse.n4js.ide.tests

import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.StandardOpenOption
import java.util.HashSet
import java.util.LinkedHashSet
import java.util.List
import java.util.Objects
import java.util.Set
import java.util.regex.Matcher
import org.eclipse.lsp4j.DidChangeTextDocumentParams
import org.eclipse.lsp4j.DidChangeWatchedFilesParams
import org.eclipse.lsp4j.FileChangeType
import org.eclipse.lsp4j.FileEvent
import org.eclipse.lsp4j.Range
import org.eclipse.lsp4j.TextDocumentContentChangeEvent
import org.eclipse.lsp4j.VersionedTextDocumentIdentifier
import org.eclipse.n4js.ide.tests.server.AbstractIdeTest
import org.eclipse.n4js.ide.xtext.server.XDocument
import org.eclipse.n4js.projectModel.locations.FileURI
import org.eclipse.n4js.tests.codegen.Project
import org.junit.Test

import static org.junit.Assert.fail

/**
 * IDE test for validations related to run-time dependency analysis.
 */
class RunTimeDependencyValidationIdeTest extends AbstractIdeTest<List<Pair<String,List<String>>>> {

	// run-time dependency cycle: C -> B -> A -> Y -> X -> C
	val defaultTestCode = #[
		"A" -> '''
			import "Y"; // using bare imports to represent any form of run-time dependency that is NOT a load-time dependency
			export public class A {
				public m() {}
			}
		''',
		"B" -> '''
			import {A} from "A";
			export public class B extends A {}
			function foo() {
				// some tests will remove 'extends A' above; this reference to A makes sure we will still
				// have a run-time dependency to A after removing 'extends A' (but not a load-time dependency)
				A;
			}
		''',
		"C" -> '''
			import {B} from "B";
			export public class C extends B {}
		''',
		"X" -> '''
			import "C";
		''',
		"Y" -> '''
			import "X";
		''',
		"MainBad" -> '''
			import {A} from "A";
			new A().m();
		''',
		"MainGood" -> '''
			import {C} from "C";
			new C().m();
		'''
	];

	val defaultExpectedIssues = #[
		"MainBad" -> #[
			'''
				WRN 1:16     Import of load-time dependency target A will be healed by inserting additional import in output code.
				Containing run-time dependency cycle cluster:
				    *A.n4js --> Y.n4js
				    *B.n4js --> A.n4js
				    C.n4js --> B.n4js
				    X.n4js --> C.n4js
				    Y.n4js --> X.n4js
			'''
		]
	];

	@Test
	def void testIllegalImportOfLoadTimeTarget() throws Exception {

		createTestProjectOnDisk(defaultTestCode);
		startAndWaitForLspServer();

		assertIssues(defaultExpectedIssues);
	}

	@Test
	def void testLoadTimeDependencyCycle() throws Exception {

		// add a load-time dependency from A.n4js to C.n4js to obtain a load-time dependency cycle:
		val testCodeWithLoadTimeCycle = defaultTestCode.map[moduleNameToContent|
			if (moduleNameToContent.key == "A") {
				moduleNameToContent.key -> '''
					import {C} from "C";
					«moduleNameToContent.value»
					class D extends C {}
				'''
			} else {
				moduleNameToContent // unchanged
			}
		];

		createTestProjectOnDisk(testCodeWithLoadTimeCycle);
		startAndWaitForLspServer();

		assertIssues(
			"A" -> #[
				'''
					ERR 1:16     Load-time dependency cycle.
					    *A.n4js --> C.n4js
					    *B.n4js --> A.n4js
					    *C.n4js --> B.n4js
				'''
			],
			"B" -> #[
				'''
					ERR 1:16     Load-time dependency cycle.
					    *A.n4js --> C.n4js
					    *B.n4js --> A.n4js
					    *C.n4js --> B.n4js
				'''
			],
			"C" -> #[
				'''
					ERR 1:16     Load-time dependency cycle.
					    *A.n4js --> C.n4js
					    *B.n4js --> A.n4js
					    *C.n4js --> B.n4js
				'''
			],
			"MainBad" -> #[
				// no errors/warnings expected here! (i.e. avoid pure follow-up errors/warnings)
			]
		);
	}

	@Test
	def void testIncrementalBuild01_openCloseRunTimeCycle() throws Exception {

		createTestProjectOnDisk(defaultTestCode);
		startAndWaitForLspServer();

		assertIssues(defaultExpectedIssues);

		// comment out the run-time dependency X -> C
		changeFile("X", 'import "C";' -> '// import "C";');
		waitForRequestsDone();
cleanBuild(); // FIXME remove this line

		assertNoIssues();

		// re-enable the run-time dependency X -> C
		changeFile("X", '// import "C";' -> 'import "C";');
		waitForRequestsDone();
cleanBuild(); // FIXME remove this line
		
		assertIssues(defaultExpectedIssues);
	}

	@Test
	def void testIncrementalBuild02_addRemoveLoadTimeDependency() throws Exception {

		createTestProjectOnDisk(defaultTestCode);
		startAndWaitForLspServer();

		assertIssues(defaultExpectedIssues);

		changeFile("B", "extends A" -> "");
		waitForRequestsDone();
cleanBuild(); // FIXME remove this line

		assertIssues(
			"MainBad" -> #[], // original issue should be gone
			"MainGood" -> #[
				"ERR 2:8      Couldn't resolve reference to IdentifiableElement 'm'."
			]
		);

		changeFile("B", "class B " -> "class B extends A ");
		waitForRequestsDone();
cleanBuild(); // FIXME remove this line

		assertIssues(defaultExpectedIssues); // original issue should have come back
	}

	override protected performTest(File root, Project project, List<Pair<String,List<String>>> moduleNameToExpectedIssues) throws Exception {
		assertIssues(moduleNameToExpectedIssues);
	}

	// ####################################################################################
	// FIXME move to super class:

	def protected void startAndWaitForLspServer() {
		createInjector();
		startLspServer(getRoot());
		waitForRequestsDone();
	}

	def protected void openFile(String moduleName) throws IOException {
		val root = getRoot();
		val fileURI = getFileUriFromModuleName(root, moduleName);
		val filePath = fileURI.toJavaIoFile.toPath;
		val content = Files.readString(filePath);
		openFile(root, moduleName, content);
	}

	def protected void changeOpenFile(String moduleName, Pair<String, String> ...replacements) throws IOException {
		val fileURI = getFileUriFromModuleName(getRoot(), moduleName);
		val filePath = fileURI.toJavaIoFile.toPath;
		val oldContent = Files.readString(filePath);
		// 1) change on disk
		changeFileOnDisk(fileURI, replacements);
		// 2) notify LSP server
		val docId = new VersionedTextDocumentIdentifier(fileURI.toString, 1);
		val changes = replacementsToChangeEvents(oldContent, replacements).toList;
		val params = new DidChangeTextDocumentParams(docId, changes);
		languageServer.didChange(params);
	}

	def protected void changeFile(String moduleName, Pair<String, String> ...replacements) throws IOException {
		val fileURI = getFileUriFromModuleName(getRoot(), moduleName);
		// 1) change on disk
		changeFileOnDisk(fileURI, replacements);
		// 2) notify LSP server
		val fileEvents = #[ new FileEvent(fileURI.toString, FileChangeType.Changed) ];
		val params = new DidChangeWatchedFilesParams(fileEvents);
		languageServer.didChangeWatchedFiles(params);
	}

	def protected void changeFileOnDisk(String moduleName, Pair<String, String> ...replacements) throws IOException {
		val fileURI = getFileUriFromModuleName(getRoot(), moduleName);
		changeFileOnDisk(fileURI, replacements);
	}

	def protected void changeFileOnDisk(FileURI fileURI, Pair<String, String> ...replacements) throws IOException {
		val filePath = fileURI.toJavaIoFile.toPath;
		val oldContent = Files.readString(filePath);
		var newContent = oldContent;
		for (replacement : replacements) {
			newContent = newContent.replaceFirst(Matcher.quoteReplacement(replacement.key), replacement.value);
		}
		Files.writeString(filePath, newContent, StandardOpenOption.TRUNCATE_EXISTING);
	}

	def protected void cleanBuild() {
		cleanBuild(true);
	}

	def protected void cleanBuild(boolean wait) {
		languageClient.clear();
		languageServer.clean();
		languageServer.reinitWorkspace();
		if (wait) {
			waitForRequestsDone();
		}
	}

	def private Iterable<TextDocumentContentChangeEvent> replacementsToChangeEvents(String content, Pair<String, String> ...replacements) {
		return replacementsToChangeEvents(new XDocument(0, content), replacements);
	}

	def private Iterable<TextDocumentContentChangeEvent> replacementsToChangeEvents(XDocument document, Pair<String, String> ...replacements) {
		return replacements.map[replacement|
			val offset = document.contents.indexOf(replacement.key);
			if (offset < 0) {
				throw new IllegalArgumentException("string \"" + replacement.key + "\" not found in content of document");
			}
			val len = replacement.key.length;
			val range = new Range(document.getPosition(offset), document.getPosition(offset + len));
			new TextDocumentContentChangeEvent(range, len, replacement.value)
		];
	}

	def private void assertNoIssues() {
		assertIssues();
	}

	def private void assertIssues(Pair<String,List<String>>... moduleNameToExpectedIssues) {
		// check given expectations
		val checkedModulePaths = assertIssuesInModules(moduleNameToExpectedIssues);
		// check that there are no other issues in the workspace
		val allIssues = languageClient.getIssues();
		val modulePathsWithIssues = allIssues.keySet();
		val uncheckedModulePathsWithIssues = new HashSet(modulePathsWithIssues);
		uncheckedModulePathsWithIssues.removeAll(checkedModulePaths);

		if (!uncheckedModulePathsWithIssues.empty) {
			val msg = if (moduleNameToExpectedIssues.empty) {
				"expected no issues in workspace but found " + allIssues.size + " issue(s):"
			} else {
				"found one or more unexpected issues in workspace:"
			};
			val sb = new StringBuilder();
			for (currModulePath : uncheckedModulePathsWithIssues) {
				sb.append(currModulePath);
				sb.append(":\n");
				val currModuleIssuesAsString = allIssues.get(currModulePath)
					.map[languageClient.getIssueString(it)]
					.issuesToSortedString("    ");
				sb.append(currModuleIssuesAsString);
			}
			fail(msg + "\n" + sb.toString);
		}
	}

	def private Set<String> assertIssuesInModules(Pair<String,List<String>>... moduleNameToExpectedIssues) {
		val checkedModulePaths = newHashSet;
		for (pair : moduleNameToExpectedIssues) {
			val moduleName = pair.key;
			val expectedIssues = pair.value;

			val uri = getFileUriFromModuleName(root, moduleName).toURI();
			val actualIssues = languageClient.getErrors(uri) + languageClient.getWarnings(uri);
			val actualIssuesAsSet = new LinkedHashSet(actualIssues.map[trim].toList);
			val expectedIssuesAsSet = new LinkedHashSet(expectedIssues.map[trim].toList);
			if (!Objects.equals(expectedIssuesAsSet, actualIssuesAsSet)) {
				val indent = "    ";
				fail("issues in module " + moduleName + " do not meet expectation\n"
					+ "EXPECTED:\n"
					+ expectedIssuesAsSet.issuesToSortedString(indent) + "\n"
					+ "ACTUAL:\n"
					+ actualIssuesAsSet.issuesToSortedString(indent));
			}
			
			val modulePath = languageClient.getRelativeModulePath(uri);
			checkedModulePaths.add(modulePath);
		}
		return checkedModulePaths;
	}

	def private String issuesToSortedString(Iterable<String> issues, String indent) {
		if (issues.empty) {
			return indent + "<none>";
		}
		return indent + issues.sort.map[replace("\n", "\n" + indent)].join("\n" + indent);
	}
}
