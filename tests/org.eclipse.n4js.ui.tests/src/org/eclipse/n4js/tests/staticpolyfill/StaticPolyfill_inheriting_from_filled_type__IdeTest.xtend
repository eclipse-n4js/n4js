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

import static org.eclipse.n4js.tests.staticpolyfill.StaticPolyfill_inheriting_from_filled_type__Probands.*
import static org.junit.Assert.*

/**
 * Testing more complex static polyfill situations, which are not reproducible with xpect.
 */
// converted from StaticPolyfill_inheriting_from_filled_type__PluginTest
class StaticPolyfill_inheriting_from_filled_type__IdeTest extends AbstractStaticPolyfillBuilderIdeTest {

	@Test
	def void testStaticPolyfill_filling_inherits() throws Exception {
		val srcAB = src.appendSegments("a", "b");
		val src2AB = src2.appendSegments("a", "b");
		srcAB.toFile.mkdirs();
		src2AB.toFile.mkdirs();

		val cFilling = src2AB.appendSegment("A.n4js");
		createFile(cFilling, specStyle_Filling);
		addSrc2ToSources();

		assertIssues(
			"src2/a/b/A.n4js" -> #[
				"(Error, [3:30 - 3:31], Couldn't resolve reference to Type 'A'.)",
				"(Error, [5:11 - 5:12], Final field b must be provided with an initializer or must be initialized in the constructor.)",
				"(Error, [8:1 - 8:12], The constructor constructor must override or implement a constructor from a super class or interface.)"
			]
		);

		val cFilled = srcAB.appendSegment("A.n4js");
		createFile(cFilled, specStyle_Filled);
		joinServerRequests();

		assertNoIssues();
	}

	@Test
	def void testStaticPolyfill_idebug657() throws Exception {
		val cFilling = src2.appendSegment("A.n4js");
		val cNextToFilling = src2.appendSegment("A2.n4js");
		createFile(cFilling, idebug657_filling);
		createFile(cNextToFilling, idebug657_next_to_filling);
		addSrc2ToSources();

		assertIssues(
			"src2/A.n4js" -> #[
				"(Error, [3:33 - 3:37], Couldn't resolve reference to Type 'Poly'.)",
				"(Error, [17:14 - 17:18], Couldn't resolve reference to IdentifiableElement 'test'.)",
				"(Error, [21:11 - 21:15], Couldn't resolve reference to IdentifiableElement 'test'.)",
				"(Error, [27:34 - 27:39], Couldn't resolve reference to Type 'Buddy'.)",
				"(Error, [36:8 - 36:12], Couldn't resolve reference to IdentifiableElement 'test'.)"
			],
			"src2/A2.n4js" -> #[
				"(Error, [0:21 - 0:24], Cannot resolve plain module specifier (without project name as first segment): no matching module found.)",
				"(Error, [2:34 - 2:38], Couldn't resolve reference to Type 'Poly'.)",
				"(Error, [10:11 - 10:17], The method action must override or implement a method from a super class or interface.)",
				"(Error, [10:26 - 10:30], Couldn't resolve reference to Type 'Poly'.)",
				"(Error, [15:13 - 15:17], Couldn't resolve reference to IdentifiableElement 'test'.)",
				"(Error, [20:11 - 20:18], The method action2 must override or implement a method from a super class or interface.)",
				"(Error, [22:14 - 22:18], Couldn't resolve reference to IdentifiableElement 'test'.)"
			]
		);

		val cFilled = src.appendSegment("A.n4js");
		createFile(cFilled, idebug657_filled);
		joinServerRequests();

		// Finally all should be good:
		assertNoIssues();
	}

	@Test
	def void testStaticPolyfill_idebug662() throws Exception {
		val cNextToFilled = src.appendSegment("Abstract.n4js");
		val cFilling = src2.appendSegment("A.n4js");
		createFile(cNextToFilled, idebug662_next_to_filled);
		createFile(cFilling, idebug662_filling);
		addSrc2ToSources();

		assertIssues(
			"src2/A.n4js" -> #[
				"(Error, [4:53 - 4:57], Couldn't resolve reference to Type 'Poly'.)",
				"(Error, [6:34 - 6:35], Couldn't resolve reference to Type 'T'.)",
				"(Error, [7:33 - 7:34], Couldn't resolve reference to Type 'T'.)",
				"(Error, [9:32 - 9:33], Couldn't resolve reference to Type 'T'.)",
				"(Error, [9:43 - 9:44], Couldn't resolve reference to Type 'T'.)",
				"(Error, [10:22 - 10:26], Couldn't resolve reference to IdentifiableElement 'unit'.)",
				"(Error, [16:30 - 16:34], Couldn't resolve reference to IdentifiableElement 'unit'.)"
			]
		);

		val cFilled = src.appendSegment("A.n4js");
		createFile(cFilled, idebug662_filled);
		joinServerRequests();

		// Finally all should be good:
		assertNoIssues();
	}

	@Test
	def void testStaticPolyfill_idebug681() throws Exception {
		val cFilling = src2.appendSegment("A.n4js");
		createFile(cFilling, idebug681_filling);
		addSrc2ToSources();

		assertIssues(
			"src2/A.n4js" -> #[
				"(Error, [4:33 - 4:37], Couldn't resolve reference to Type 'Poly'.)",
				"(Error, [6:34 - 6:41], Couldn't resolve reference to IdentifiableElement 'DataMap'.)",
				"(Error, [9:24 - 9:31], Couldn't resolve reference to IdentifiableElement 'DataMap'.)"
			]
		);

		val generatedFile = getProjectRoot().toFileURI().appendSegments("src-gen", "A.js");
		assertFalse("nothing should be generated in error case", generatedFile.exists);

		val cFilled = src.appendSegment("A.n4js");
		createFile(cFilled, idebug681_filled);
		joinServerRequests();

		// Finally all should be good:
		assertNoIssues();

		// assert existence of generated file
		assertTrue("output file should exist", generatedFile.exists);

		val actualContent = Files.readString(generatedFile.toPath);
		assertTrue('generated file content was empty: ' + generatedFile, !actualContent.nullOrEmpty);

		assertEquals("static initializer not generated:(ENUM_MAP=...)", 1, matchCount(pattern_681_compiled_ENUM_Init, actualContent));
		assertEquals("static initializer not generated:(STRING=...).", 1, matchCount(pattern_681_compiled_STRING_init, actualContent));
	}
}
