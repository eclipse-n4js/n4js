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

import com.google.common.io.Files
import java.nio.charset.Charset
import org.eclipse.core.resources.IFile
import org.junit.Test

import static org.eclipse.n4js.tests.staticpolyfill.StaticPolyfill_inheriting_from_filled_type__Probands.*
import static org.junit.Assert.*

/**
 * Testing more complex static polyfill situations, which are not reproducible with xpect.
 */
class StaticPolyfill_inheriting_from_filled_type__PluginTest extends AbstractStaticPolyfillBuilderTest {

	@Test
	def void testStaticPolyfill_filling_inherits() throws Exception {
		val srcAB = createFolder(src, "a/b") // filling + others
		val src2AB = createFolder(src2, "a/b") // filled
		val cFilling = createTestFile(src2AB, "A", specStyle_Filling);
		addSrc2ToSources

		// warning: the following list of error messages is out-dated (tested on Aug 12, 2016 on master)
		// line 4: Couldn't resolve reference to Type 'A'.
		// line 13: Couldn't resolve reference to IdentifiableElement 'a'.
		// line 9: The method constructor must override or implement a method from a super class or interface.
		assertMarkers("filling file should have X errors", cFilling, 3);

		val cFilled = createTestFile(srcAB, "A", specStyle_Filled);

		assertMarkers("filled file should have no errors", cFilled, 0);
		assertMarkers("filling file should have no errors", cFilling, 0);

	}

	@Test
	def void testStaticPolyfill_idebug657() throws Exception {

		val cFilling = createTestFile(src2, "A", idebug657_filling);
		val cNextToFilling = createTestFile(src2, "A2", idebug657_next_to_filling);
		addSrc2ToSources

		// line 4: Couldn't resolve reference to Type 'Poly'.
		// line 18: Couldn't resolve reference to IdentifiableElement 'test'.
		// line 22: Couldn't resolve reference to IdentifiableElement 'test'.
		// line 37: Couldn't resolve reference to IdentifiableElement 'test'.
		// line 28: Couldn't resolve reference to Type 'Buddy'.
		assertMarkers("filling file should have X errors", cFilling, 5);

		// line 1: Cannot resolve import target :: resolving simple module import : found no matching modules
		// line 1: Couldn't resolve reference to IdentifiableElement 'Poly'.
		// line 11: Couldn't resolve reference to Type 'Poly'.
		// line 3: Couldn't resolve reference to Type 'Poly'.
		// line 16: Couldn't resolve reference to IdentifiableElement 'test'.
		// line 23: Couldn't resolve reference to IdentifiableElement 'test'.
		// line 1: Import of Poly cannot be resolved.
		// line 11: The method action must override or implement a method from a super class or interface.
		// line 21: The method action2 must override or implement a method from a super class or interface.
		assertMarkers("filling file should have X errors", cNextToFilling, 9);

		val cFilled = createTestFile(src, "A", idebug657_filled);

		// Finally all should be good:
		assertMarkers("filled file should have no errors", cFilled, 0);
		assertMarkers("filling file should have no errors", cFilling, 0);
		assertMarkers("filling file should have no errors", cNextToFilling, 0);

	}

	@Test
	def void testStaticPolyfill_idebug662() throws Exception {

		val cFilling = createTestFile(src2, "A", idebug662_filling);
		val cNextToFilled = createTestFile(src, "Abstract", idebug662_next_to_filled);
		addSrc2ToSources

		// line 5: Couldn't resolve reference to Type 'Poly'.
		// line 7: Couldn't resolve reference to Type 'T'.
		// line 8: Couldn't resolve reference to Type 'T'.
		// line 10: Couldn't resolve reference to Type 'T'.
		// line 11: Couldn't resolve reference to IdentifiableElement 'unit'.
		// line 17: Couldn't resolve reference to IdentifiableElement 'unit'.
		// line 10: Couldn't resolve reference to Type 'T'. expected:<4> but was:<7>
		assertMarkers("filling file should have X errors", cFilling, 7);
		assertMarkers("next to filled file should have X errors", cNextToFilled, 0);

		val cFilled = createTestFile(src, "A", idebug662_filled);

		// Finally all should be good:
		assertMarkers("filled file should have no errors", cFilled, 0);
		assertMarkers("filling file should have no errors", cFilling, 0);
		assertMarkers("filling file should have no errors", cNextToFilled, 0);

	}

	@Test
	def void testStaticPolyfill_idebug681() throws Exception {

		val cFilling = createTestFile(src2, "A", idebug681_filling);
		addSrc2ToSources

		//line 10: Couldn't resolve reference to IdentifiableElement 'DataMap'.
		//line 7: Couldn't resolve reference to IdentifiableElement 'DataMap'.
		//line 5: Couldn't resolve reference to Type 'Poly'.
		assertMarkers("filling file should have X errors", cFilling, 3);

		// OUTPUT structure:	src-gen/a/b/Poly.js
		val xRelPath = cFilling.projectRelativePath.removeFirstSegments(1).removeFileExtension.addFileExtension("js")
		val String compiledFilePath = '''src-gen/«xRelPath.toString»'''
		val IFile compiledFile = projectUnderTest.project.getFile(compiledFilePath)

		assertFalse("Nothing should be compiled in error case.",compiledFile.exists)

		val cFilled = createTestFile(src, "A", idebug681_filled);

		// Finally all should be good:
		assertMarkers("filled file should have no errors", cFilled, 0);
		assertMarkers("filling file should have no errors", cFilling, 0);

		// assertExistence of compiled file.
		assertTrue("No compiled file.",compiledFile.exists)

		val actualContent = Files.asCharSource(compiledFile.location.toFile, Charset.defaultCharset).read();
		assertTrue('Generated file content was empty: ' + compiledFile, !actualContent.nullOrEmpty)

		assertEquals("static initializer not compiled:(ENUM_MAP=...)",1,matchCount( pattern_681_compiled_ENUM_Init, actualContent ) );
		assertEquals("static initializer not compiled:(STRING=...).",1,matchCount( pattern_681_compiled_STRING_init, actualContent ) );
	}

}
