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
package org.eclipse.n4js.ide.tests.bugreports

import org.eclipse.lsp4j.Hover
import org.eclipse.lsp4j.Location
import org.eclipse.lsp4j.Position
import org.eclipse.lsp4j.Range
import org.eclipse.lsp4j.TextDocumentIdentifier
import org.eclipse.lsp4j.TextDocumentPositionParams
import org.eclipse.n4js.ide.tests.helper.server.AbstractIdeTest
import org.junit.Assert
import org.junit.Test

import static org.junit.Assert.assertEquals

/**
 * If TModule reconciliation fails due to a hash mismatch, the resource was left in an invalid state.
 */
class GH_1956_RelinkTModuleLeavingResourceInInvalidState extends AbstractIdeTest {
	
	@Test
	def void testGH1956() throws Exception {
		testWorkspaceManager.createTestOnDisk(
			"ProjectMain" -> #[
				"X" -> '''
					export public class X {}
				''',
				"A" -> '''
					import * as N from "X"
					/** Some documentation. */
					export public class SomeClass {}
					let x: N.X;
					console.log(x);
				''',
				"Main" -> '''
					import {SomeClass} from "A"
					new SomeClass();
				'''
			]
		);
		startAndWaitForLspServer();
		assertNoIssues();

		openFile("Main");
		joinServerRequests();

		// now: A is in resource set of editor "Main", loaded from index

		changeFileOnDiskWithoutNotification("A", "Some doc" -> "Some nice doc");
		// NOTE: even with notification the bug would occur, because this is only a change inside a comment and
		// therefore resource "A" in resource set of editor "Main" won't be affected and thus won't be updated!

		val h1 = injEnv.languageServer.hover(textDocPos("Main", 1, 8));
		joinServerRequests();
// for the time being, we still have to log relink-failure due to hash mismatch as error, thus next line is commented out:
//		assertNoErrorsInLogOrOutput(); // logged two exceptions
// TODO GH-1958 comment in previous line and delete next two lines
clearLogMessages();
clearOutput();

		val h2 = injEnv.languageServer.hover(textDocPos("Main", 0, 12));
		joinServerRequests();
		assertNoErrorsInLogOrOutput(); // logged hundreds of exceptions (via post-processing)

		val d = injEnv.languageServer.definition(textDocPos("Main", 0, 12));
		joinServerRequests();
		assertNoErrorsInLogOrOutput(); // logged hundreds of exceptions (via a different call path)

		assertHoverResult("markdown", "Some nice documentation.", h1.get);
		assertHoverResult("markdown", "Some nice documentation.", h2.get);

		assertEquals(#[ location("A", 2, 20, 2, 29) ], d.get.getLeft);
	}

	/** Line/column is zero-based. */
	def private TextDocumentPositionParams textDocPos(String moduleName, int line, int column) {
		return new TextDocumentPositionParams(
			new TextDocumentIdentifier(getFileURIFromModuleName(moduleName).toString),
			new Position(line, column)
		);
	}

	def private Location location(String moduleName, int startLine, int startColumn, int endLine, int endColumn) {
		return new Location(
			getFileURIFromModuleName(moduleName).toString,
			new Range(
				new Position(startLine, startColumn),
				new Position(endLine, endColumn)
			)
		);
	}

	def private void assertHoverResult(String expectedLanguage, String expectedText, Hover actualResult) {
		val success = actualResult.contents.getLeft.filter[isRight].exists[
			getRight.language == expectedLanguage && getRight.value == expectedText
		];
		if (!success) {
			Assert.fail(
				'''expected a hover with language="«expectedLanguage»" and value="«expectedText»", but got:\n'''
				+ actualResult);
		}
	}
}
