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
package org.eclipse.n4js.integration.tests.cli.jar;

import static org.eclipse.n4js.cli.N4jscExitCode.VALIDATION_ERRORS;
import static org.eclipse.n4js.cli.N4jscTestOptions.COMPILE;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.nio.file.Path;

import org.eclipse.n4js.cli.helper.AbstractCliJarTest;
import org.eclipse.n4js.cli.helper.CliCompileResult;
import org.junit.Test;

/**
 * This test checks that the n4jsc.jar is working correctly regarding the d.ts parser. It is motivated by the fact that
 * we use antlr4 for parsing d.ts files while we still use antlr3 for Xtext. This would collide inside the fat/uber jar
 * (n4jsc.jar). To solve this conflict, maven will relocate antrl4 sources. The success of this is tested here.
 *
 * IMPORTANT: for info on how to run this test locally, see {@link AbstractCliJarTest}!
 */
public class DtsParserJarTest extends AbstractCliJarTest {

	/***/
	public DtsParserJarTest() {
		super("probands/dts-parser");
	}

	/** Compile a project that has n4js and d.ts files. */
	@Test
	public void testSingleFileCompile() {
		File project = Path.of(TARGET, WORKSPACE_FOLDER).toAbsolutePath().toFile();

		CliCompileResult cliResult = n4jsc(COMPILE(project), VALIDATION_ERRORS);
		assertEquals(cliResult.toString(), 2, cliResult.getTranspiledFilesCount());

		assertEquals(1, cliResult.getErrFiles().size());

		// contains errors due to uninstalled dependencies (unimportant for this test)
		assertEquals("package.json", cliResult.getErrFiles().iterator().next());
	}

}
