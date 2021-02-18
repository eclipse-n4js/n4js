/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.tests.staticpolyfill

import java.nio.file.Files
import org.junit.Test

import static org.eclipse.n4js.tests.staticpolyfill.StaticPolyfillSimpleProbands.*
import static org.junit.Assert.*

/**
 */
// converted from org.eclipse.n4js.tests.staticpolyfill.SingleProjectPluginUITest
class StaticPolyfillSimpleIdeTest extends AbstractStaticPolyfillBuilderIdeTest {

	@Test
	def void testStaticPolyfill_filling_updates_filled_module() throws Exception {
		val srcAB = src.appendSegments("a", "b");
		val src2AB = src2.appendSegments("a", "b");
		srcAB.toFile.mkdirs();
		src2AB.toFile.mkdirs();

		val cFilling = src2AB.appendSegment("Poly.n4js");
		createFile(cFilling, validContent_Filling);
		addSrc2ToSources();

		assertIssues(
			"Poly" -> #[
				"(Error, [2:30 - 2:31], Couldn't resolve reference to Type 'K'.)",
				"(Error, [7:8 - 7:19], The constructor constructor must override or implement a constructor from a super class or interface.)",
				"(Error, [14:8 - 14:13], The method greet must override or implement a method from a super class or interface.)",
				"(Error, [19:34 - 19:44], Couldn't resolve reference to IdentifiableElement 'fromParent'.)"
			]
		);

		val generatedFile = getProjectRoot().toFileURI().appendSegments("src-gen", "a", "b", "Poly.js");
		assertFalse("nothing should be generated in error case", generatedFile.exists);

		val cFilled = srcAB.appendSegment("Poly.n4js");
		createFile(cFilled, validContent_Filled);
		joinServerRequests();
		assertNoIssues();

		// assert existence of generated file.
		assertTrue("generated file should exist", generatedFile.exists);

		// now change Filling and see, that filled is updated.
		// 1. invalid code --> will remove generated,

		// open editors of test files
		openFile(cFilling);

		changeOpenedFile(cFilling, invalidContent_filling);
		saveOpenedFile(cFilling);
		joinServerRequests();

		assertIssues(
			"src2/a/b/Poly.n4js" -> #[
				"(Error, [7:8 - 7:19], Signature of constructor of class K (line 7) does not conform to overridden constructor of class K (line 11): {function(number)} is not a subtype of {function(string)}.)"
			]
		);
		assertFalse("generated file should be deleted", generatedFile.exists)


		// 2. valid code again --> will be generated again.

		changeOpenedFile(cFilling, validContent_Filling);
		saveOpenedFile(cFilling);
		joinServerRequests();

		assertNoIssues();

		//  assert generated file is back again + new content.
		assertTrue("generated file should exist", generatedFile.exists);
		val actualContent = Files.readString(generatedFile.toPath);
		assertTrue("generated file content was empty: " + generatedFile, !actualContent.nullOrEmpty);

		assertEquals("test-setup broken", 0, matchCount(pattern_1_Unique, validContent_Filled));
		assertEquals("test-setup broken", 1, matchCount(pattern_1_Unique, validContent_Filling));
		assertTrue("new filled in method was not generated", matchCount(pattern_1_Unique, actualContent) >= 1);


		// 3. set valid extended content  --> check generated

		changeOpenedFile(cFilling, validExtendedContent_filling);
		saveOpenedFile(cFilling);
		joinServerRequests();

		assertNoIssues();

		val actualContent2 = Files.readString(generatedFile.toPath);
		assertTrue('generated file content was empty: ' + generatedFile, !actualContent2.nullOrEmpty)

		assertEquals("test-setup broken", 0, matchCount(pattern_2_ExtendedContent,validContent_Filled));
		assertEquals("test-setup broken", 1, matchCount(pattern_2_ExtendedContent,validExtendedContent_filling));
		assertTrue("new filled in method was not generated", matchCount(pattern_2_ExtendedContent, actualContent2) >= 1);

		val savedModStamp_since_3 = generatedFile.resetFileModificationTimeStamp;

		// 4. update only inside a comment --> no structural change of the AST, but we expect a rebuild of the filled file.

		changeOpenedFile(cFilling, validModifiedExtendedContent_filling);
		saveOpenedFile(cFilling);
		joinServerRequests();

		assertNoIssues();

		val actualContent3 = Files.readString(generatedFile.toPath);
		assertTrue('generated file content was empty: ' + generatedFile, !actualContent3.nullOrEmpty);

		val savedModStamp_since_4 = generatedFile.fileModificationTimeStamp;
		assertEquals("test-setup broken", 0, matchCount(pattern_3_ModifiedExtendedContent,validContent_Filled));
		assertEquals("test-setup broken", 1, matchCount(pattern_3_ModifiedExtendedContent,validModifiedExtendedContent_filling));
		// The following line cannot be checked any more, since we do not transfer comments in with the new transpiler.
		//assertTrue("New filled in line of code inside of a method was not compiled.",matchCount( pattern_3_ModifiedExtendedContent, actualContent3 ) >= 1);
		assertTrue("update after comment-modification did not trigger new build", savedModStamp_since_4 > savedModStamp_since_3);
	}
}
