/**
 * Copyright (c) 2019 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.cli.compile.tests;

import static org.eclipse.n4js.cli.N4jscTestOptions.COMPILE;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.eclipse.n4js.cli.N4jscOptions;
import org.junit.Test;

/**
 *
 */
public class SomeCompileTest extends AbstractCliCompileTest {

	@Test
	public void compileP1() throws IOException {
		File file = new File("probands/basic/P1");
		N4jscOptions options = COMPILE(file);

		String output = main(options);

		assertEquals("Initialized", output);

		int jsFileCount = GeneratedJSFilesCounter.countFilesCompiledToES("probands/basic/P1");
		assertEquals(4, jsFileCount);
	}

}
