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
package org.eclipse.n4js.ide.tests.bugreports.pending;

import static org.eclipse.xtext.xbase.lib.IterableExtensions.head;

import java.nio.file.Files;
import java.util.List;
import java.util.Map;

import org.eclipse.lsp4j.Diagnostic;
import org.eclipse.lsp4j.DidChangeWatchedFilesParams;
import org.eclipse.lsp4j.FileChangeType;
import org.eclipse.lsp4j.FileEvent;
import org.eclipse.n4js.N4JSLanguageConstants;
import org.eclipse.n4js.ide.tests.helper.server.AbstractIdeTest;
import org.eclipse.n4js.workspace.locations.FileURI;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.Objects;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimaps;

/**
 * See GH-2004.
 */

public class GH_2004_ReviewImportedNamesComputationTest extends AbstractIdeTest {

	@Test
	public void testMainModuleMissingInImportedNamesOfPackageJsonFile() throws Exception {
		testWorkspaceManager.createTestOnDisk(Map.of(
				"ProjectOther", Map.of(
						"some/path/Other", """
									export public class Cls {
										public m() {}
									}
								""",
						CFG_MAIN_MODULE, "some/path/Other")));

		startAndWaitForLspServer();
		assertNoIssues();

		FileURI otherFileURI = getFileURIFromModuleName("Other");
		Files.delete(otherFileURI.toFile().toPath());
		languageServer.didChangeWatchedFiles(new DidChangeWatchedFilesParams(List.of(
				new FileEvent(otherFileURI.toString(), FileChangeType.Deleted))));
		joinServerRequests();
		assertIsAffectedBug("ProjectOther/" + PACKAGE_JSON,
				"(Error, [8:17 - 8:34], Main module specifier some/path/Other does not exist.)");
	}

	@Test
	public void testOnlyDeclaredTypeConsidered1() {
		testWorkspaceManager.createTestOnDisk(Map.of(
				"ProjectMain", Map.of(
						"G", """
									export public class G<T> {}
								""",
						"B", """
									export public class B {}
								""",
						"C", """
									import {B} from "B"
									export public class C extends B {}
									B; // avoid unused import warning when "extends B" is removed
								""",
						"Other", """
									import {G} from "G"
									import {C} from "C"
									export let g: G<C>;
								""",
						"Main",
						"""
									import {G} from "G"
									import {B} from "B"
									import {g as g_from_other} from "Other"
									let g: G<? extends B> = g_from_other; // Problem: only declaredType of type of variable Other.g included in 'importedNames'
								""")));
		startAndWaitForLspServer();
		assertNoIssues();

		changeNonOpenedFile("C", Pair.of("C extends B {", "C {"));
		joinServerRequests();
		assertIsAffectedBug("Main",
				"(Error, [3:25 - 3:37], G<C> is not a subtype of G<? extends B>.)");
	}

	@Test
	public void testOnlyDeclaredTypeConsidered2() {
		testWorkspaceManager.createTestOnDisk(Map.of(
				"ProjectMain", Map.of(
						"G", """
									export public class G<T> {}
								""",
						"B", """
									export public class B {}
								""",
						"C", """
									import {B} from "B"
									export public class C extends B {}
									B; // avoid unused import warning when "extends B" is removed
								""",
						"Cls", """
									import {G} from "G"
									import {C} from "C"
									export public class Cls {
										public m(): G<C> {
											return null;
										}
									}
								""",
						"Main",
						"""
									import {G} from "G"
									import {B} from "B"
									import {Cls} from "Cls"
									let g: G<? extends B> = new Cls().m(); // Problem: only declaredType of return type of method m included in 'importedNames'
								""")));
		startAndWaitForLspServer();
		assertNoIssues();

		changeNonOpenedFile("C", Pair.of("C extends B {", "C {"));
		joinServerRequests();
		assertIsAffectedBug("Main",
				"(Error, [3:25 - 3:38], G<C> is not a subtype of G<? extends B>.)");
	}

	@Test
	public void testMethodArgumentTypesMissing_structural() {
		testWorkspaceManager.createTestOnDisk(Map.of(
				"ProjectMain", Map.of(
						"C", """
									export public class C {
										public field: string;
									}
								""",
						"Cls", """
									import {C} from "C"
									export public class Cls {
										public m(p: ~C) {}
									}
								""",
						"Main",
						"""
									import {Cls} from "Cls"
									let o = { field: "hello" };
									new Cls().m(o); // Problem: type of argument of method m not included in 'importedNames'
								""")));
		startAndWaitForLspServer();
		assertNoIssues();

		changeNonOpenedFile("C", Pair.of(": string;", ": number;"));
		joinServerRequests();
		assertIsAffectedBug("Main",
				"(Error, [2:13 - 2:14], ~Object with { field: string } is not a structural subtype of ~C: field failed: string is not equal to number.)");
	}

	@Test
	public void testMethodArgumentTypesMissing_inheritance() {
		testWorkspaceManager.createTestOnDisk(Map.of(
				"ProjectMain", Map.of(
						"B", """
									export public class B {}
								""",
						"C", """
									import {B} from "B"
									export public class C extends B {}
									B; // avoid unused import warning when "extends B" is removed
								""",
						"Cls", """
									import {C} from "C"
									export public class Cls {
										public m(p: C) {}
									}
								""",
						"Main",
						"""
									import {B} from "B"
									import {Cls} from "Cls"
									class SubCls extends Cls {
										@Override
										public m(p: B) {} // Problem: type of argument of method super.m not included in 'importedNames'
									}
								""")));
		startAndWaitForLspServer();
		assertNoIssues();

		changeNonOpenedFile("C", Pair.of(" C extends B ", " C "));
		joinServerRequests();
		assertIsAffectedBug("Main",
				"(Error, [4:9 - 4:10], Signature of method SubCls.m does not conform to overridden method Cls.m: {function(B):void} is not a subtype of {function(C):void}.)");
	}

	/**
	 * Invoked right after an incremental build completed that *should have* produced a certain issue (i.e. the "desired
	 * issue"), but we know that due to a bug in isAffected()/getImportedNames()/etc. this does not work. Therefore,
	 * this method asserts the following expectations:
	 * <ol>
	 * <li>the desired issue did not show up after the incremental build,
	 * <li>after a following clean & full build (triggered by this method) the desired issue shows up. This ensures that
	 * the test data and desired issue string are still valid.
	 * </ol>
	 */
	private void assertIsAffectedBug(String moduleName, String desiredIssueStr) {
		HashMultimap<FileURI, Diagnostic> allIssues = HashMultimap.create(getIssues());
		allIssues.entries().removeIf(e -> N4JSLanguageConstants.DEFAULT_SUPPRESSED_ISSUE_CODES_FOR_TESTS
				.contains(e.getValue().getCode().getLeft()));

		FileURI moduleURI = getFileURIFromModuleName(moduleName);

		if (allIssues.size() == 1
				&& head(allIssues.entries()).getKey() == moduleURI
				&& Objects.equal(languageClient.getIssueString(head(allIssues.entries()).getValue()),
						desiredIssueStr)) {

			// desired issue showed up, so the incremental builder worked as intended, which is unexpected by this test:
			Assert.fail("incremental builder produced the desired issue, so this #isAffected() bug is suddenly fixed!");
		}
		if (!allIssues.isEmpty()) {
			String allIssuesStr = issuesToString(
					Multimaps.transformValues(allIssues, (uri) -> languageClient.getIssueString(uri)));
			Assert.fail("the incremental builder produced unexpected issues:\n" + allIssuesStr);
		}
		// ensure that the test data and the given 'desiredIssueStr' are still up-to-date by
		// asserting that a clean and full build will produce the desired issue:
		cleanBuildAndWait();
		// if this fails, the test data or 'desiredIssueStr' is out-dated and must be adjusted
		assertIssues2(Pair.of(moduleName, List.of(desiredIssueStr)));
	}
}
