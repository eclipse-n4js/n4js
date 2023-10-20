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
package org.eclipse.n4js.tests.utils;

import static org.eclipse.n4js.utils.ProjectDescriptionUtils.convertMainPathToModuleSpecifier;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

/**
 * Temporary tests for the temporary implementation of SemVer versions and version ranges.
 */
public class ProjectDescriptionUtilsTest {

	@Test
	public void testSanitizeMainModulePath() {
		assertEquals("a/b/c/module", convertMainPathToModuleSpecifier("src/a/b/c/module.js", List.of("src")));

		assertEquals("a/b/c/module", convertMainPathToModuleSpecifier("src/js/a/b/c/module.js", List.of("src/js")));
		assertEquals("a/b/c/module", convertMainPathToModuleSpecifier("src/js/a/b/c/module.js", List.of("./src/js")));
		assertEquals("a/b/c/module", convertMainPathToModuleSpecifier("./src/js/a/b/c/module.js", List.of("src/js")));
		assertEquals("a/b/c/module", convertMainPathToModuleSpecifier(
				"src/./js//a/xxx/yyy///../zzz/..//../b/c/module.js", List.of("src/js")));

		assertEquals("a/b/c/module",
				convertMainPathToModuleSpecifier("src3/a/b/c/module.js", List.of("src1", "src2", "src3")));

		assertEquals("a/b/c/module", convertMainPathToModuleSpecifier("a/b/c/module.js", List.of(".")));
		assertEquals("a/b/c/module", convertMainPathToModuleSpecifier("a/b/c/module.js", List.of("./dummy2/..")));

		// assumes .js as implicit file extension
		assertEquals("a/b/c/module", convertMainPathToModuleSpecifier("src/a/b/c/module", List.of("src")));

		// other extension
		assertEquals("a/b/c/module", convertMainPathToModuleSpecifier("src/a/b/c/module.n4js", List.of("src")));
	}

	@Test
	public void testSanitizeMainModulePathInvalid() {
		assertEquals(null, convertMainPathToModuleSpecifier("src/a/b/c/module.js", List.of()));
		assertEquals(null, convertMainPathToModuleSpecifier("src/a/b/c/module.js", List.of("")));
		assertEquals(null,
				convertMainPathToModuleSpecifier("src/a/b/c/module.js", Arrays.asList(new String[] { null })));
		assertEquals(null, convertMainPathToModuleSpecifier("some/where/else/a/b/c/module.js", List.of("src")));
		assertEquals(null, convertMainPathToModuleSpecifier("src/a/b/c/module.js", List.of("src1", "src2", "src3")));
		assertEquals(null, convertMainPathToModuleSpecifier("a/../../b/c/module.js", List.of("src")));
	}

}
