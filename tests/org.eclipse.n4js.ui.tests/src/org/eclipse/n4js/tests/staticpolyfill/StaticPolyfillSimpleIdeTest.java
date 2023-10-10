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
package org.eclipse.n4js.tests.staticpolyfill;

import static com.google.common.base.Strings.isNullOrEmpty;
import static org.eclipse.n4js.tests.staticpolyfill.StaticPolyfillSimpleProbands.invalidContent_filling;
import static org.eclipse.n4js.tests.staticpolyfill.StaticPolyfillSimpleProbands.pattern_1_Unique;
import static org.eclipse.n4js.tests.staticpolyfill.StaticPolyfillSimpleProbands.pattern_2_ExtendedContent;
import static org.eclipse.n4js.tests.staticpolyfill.StaticPolyfillSimpleProbands.pattern_3_ModifiedExtendedContent;
import static org.eclipse.n4js.tests.staticpolyfill.StaticPolyfillSimpleProbands.validContent_Filled;
import static org.eclipse.n4js.tests.staticpolyfill.StaticPolyfillSimpleProbands.validContent_Filling;
import static org.eclipse.n4js.tests.staticpolyfill.StaticPolyfillSimpleProbands.validExtendedContent_filling;
import static org.eclipse.n4js.tests.staticpolyfill.StaticPolyfillSimpleProbands.validModifiedExtendedContent_filling;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.nio.file.Files;
import java.util.List;

import org.eclipse.n4js.workspace.locations.FileURI;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.Test;

// converted from org.eclipse.n4js.tests.staticpolyfill.SingleProjectPluginUITest
public class StaticPolyfillSimpleIdeTest extends AbstractStaticPolyfillBuilderIdeTest {

	@Test
	public void testStaticPolyfill_filling_updates_filled_module() throws Exception {
		FileURI srcAB = src.appendSegments("a", "b");
		FileURI src2AB = src2.appendSegments("a", "b");
		srcAB.toFile().mkdirs();
		src2AB.toFile().mkdirs();

		FileURI cFilling = src2AB.appendSegment("Poly.n4js");
		createFile(cFilling, validContent_Filling);
		addSrc2ToSources();

		assertIssues2(
				Pair.of("Poly", List.of(
						"(Error, [2:30 - 2:31], Couldn't resolve reference to Type 'K'.)",
						"(Error, [7:8 - 7:19], The constructor constructor must override or implement a constructor from a super class or interface.)",
						"(Error, [14:8 - 14:13], The method greet must override or implement a method from a super class or interface.)",
						"(Error, [19:34 - 19:44], Couldn't resolve reference to IdentifiableElement 'fromParent'.)")));

		FileURI generatedFile = toFileURI(getProjectRoot()).appendSegments("src-gen", "a", "b", "Poly.js");
		assertFalse("nothing should be generated in error case", generatedFile.exists());

		FileURI cFilled = srcAB.appendSegment("Poly.n4js");
		createFile(cFilled, validContent_Filled);
		joinServerRequests();
		assertNoIssues();

		// assert existence of generated file.
		assertTrue("generated file should exist", generatedFile.exists());

		// now change Filling and see, that filled is updated.
		// 1. invalid code --> will remove generated,

		// open editors of test files
		openFile(cFilling);

		changeOpenedFile(cFilling, invalidContent_filling);
		saveOpenedFile(cFilling);
		joinServerRequests();

		assertIssues2(Pair.of("src2/a/b/Poly.n4js", List.of(
				"(Error, [7:8 - 7:19], Signature of constructor of class K (line 7) does not conform to overridden constructor of class K: {function(number)} is not a subtype of {function(string)}.)")));
		assertFalse("generated file should be deleted", generatedFile.exists());

		// 2. valid code again --> will be generated again.

		changeOpenedFile(cFilling, validContent_Filling);
		saveOpenedFile(cFilling);
		joinServerRequests();

		assertNoIssues();

		// assert generated file is back again + new content.
		assertTrue("generated file should exist", generatedFile.exists());
		String actualContent = Files.readString(generatedFile.toPath());
		assertTrue("generated file content was empty: " + generatedFile, !isNullOrEmpty(actualContent));

		assertEquals("test-setup broken", 0, matchCount(pattern_1_Unique, validContent_Filled));
		assertEquals("test-setup broken", 1, matchCount(pattern_1_Unique, validContent_Filling));
		assertTrue("new filled in method was not generated", matchCount(pattern_1_Unique, actualContent) >= 1);

		// 3. set valid extended content --> check generated

		changeOpenedFile(cFilling, validExtendedContent_filling);
		saveOpenedFile(cFilling);
		joinServerRequests();

		assertNoIssues();

		String actualContent2 = Files.readString(generatedFile.toPath());
		assertTrue("generated file content was empty: " + generatedFile, !isNullOrEmpty(actualContent2));

		assertEquals("test-setup broken", 0, matchCount(pattern_2_ExtendedContent, validContent_Filled));
		assertEquals("test-setup broken", 1, matchCount(pattern_2_ExtendedContent, validExtendedContent_filling));
		assertTrue("new filled in method was not generated",
				matchCount(pattern_2_ExtendedContent, actualContent2) >= 1);

		long savedModStamp_since_3 = resetFileModificationTimeStamp(generatedFile);

		// 4. update only inside a comment --> no structural change of the AST, but we expect a rebuild of the filled
		// file.

		changeOpenedFile(cFilling, validModifiedExtendedContent_filling);
		saveOpenedFile(cFilling);
		joinServerRequests();

		assertNoIssues();

		String actualContent3 = Files.readString(generatedFile.toPath());
		assertTrue("generated file content was empty: " + generatedFile, !isNullOrEmpty(actualContent3));

		long savedModStamp_since_4 = getFileModificationTimeStamp(generatedFile);
		assertEquals("test-setup broken", 0, matchCount(pattern_3_ModifiedExtendedContent, validContent_Filled));
		assertEquals("test-setup broken", 1,
				matchCount(pattern_3_ModifiedExtendedContent, validModifiedExtendedContent_filling));
		// The following line cannot be checked any more, since we do not transfer comments in with the new transpiler.
		// assertTrue("New filled in line of code inside of a method was not compiled.",matchCount(
		// pattern_3_ModifiedExtendedContent, actualContent3 ) >= 1);
		assertTrue("update after comment-modification did not trigger new build",
				savedModStamp_since_4 > savedModStamp_since_3);
	}
}
