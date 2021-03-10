/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.tests.utils

import org.junit.Test

import static org.eclipse.n4js.utils.ProjectDescriptionUtils.*
import static org.junit.Assert.*

/**
 * Temporary tests for the temporary implementation of SemVer versions and version ranges.
 */
class ProjectDescriptionUtilsTest {

	@Test
	def void testSanitizeMainModulePath() {
		assertEquals("a/b/c/module", convertMainPathToModuleSpecifier("src/a/b/c/module.js", #["src"]));

		assertEquals("a/b/c/module", convertMainPathToModuleSpecifier("src/js/a/b/c/module.js", #["src/js"]));
		assertEquals("a/b/c/module", convertMainPathToModuleSpecifier("src/js/a/b/c/module.js", #["./src/js"]));
		assertEquals("a/b/c/module", convertMainPathToModuleSpecifier("./src/js/a/b/c/module.js", #["src/js"]));
		assertEquals("a/b/c/module", convertMainPathToModuleSpecifier("src/./js//a/xxx/yyy///../zzz/..//../b/c/module.js", #["src/js"]));

		assertEquals("a/b/c/module", convertMainPathToModuleSpecifier("src3/a/b/c/module.js", #["src1", "src2", "src3"]));

		assertEquals("a/b/c/module", convertMainPathToModuleSpecifier("a/b/c/module.js", #["."]));
		assertEquals("a/b/c/module", convertMainPathToModuleSpecifier("a/b/c/module.js", #["./dummy2/.."]));

		assertEquals("a/b/c/module", convertMainPathToModuleSpecifier("src/a/b/c/module", #["src"])); // assumes .js as implicit file extension
	}

	@Test
	def void testSanitizeMainModulePathInvalid() {
		assertEquals(null, convertMainPathToModuleSpecifier("src/a/b/c/module.js", #[]));
		assertEquals(null, convertMainPathToModuleSpecifier("src/a/b/c/module.js", #[""]));
		assertEquals(null, convertMainPathToModuleSpecifier("src/a/b/c/module.js", #[null]));
		assertEquals(null, convertMainPathToModuleSpecifier("src/a/b/c/module.n4js", #["src"])); // wrong file extension
		assertEquals(null, convertMainPathToModuleSpecifier("some/where/else/a/b/c/module.js", #["src"]));
		assertEquals(null, convertMainPathToModuleSpecifier("src/a/b/c/module.js", #["src1", "src2", "src3"]));
		assertEquals(null, convertMainPathToModuleSpecifier("a/../../b/c/module.js", #["src"]));
	}

}
