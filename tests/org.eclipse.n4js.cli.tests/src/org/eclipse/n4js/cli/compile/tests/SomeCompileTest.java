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
import org.eclipse.n4js.cli.helper.AbstractCliCompileTest;
import org.eclipse.n4js.cli.helper.GeneratedJSFilesCounter;
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

		assertEquals(
				"file:///probands/basic/P1/package.json\n"
						+ "   [Error] (1:9): The package name \"P15\" does not match the name of the project folder \"P1\" on the file system.\n"
						+ "   [Error] (4:17): Missing dependency to n4js-runtime (mandatory for all N4JS projects of type library, application, test).\n"
						+ "file:///probands/basic/P1/src/A.n4js\n"
						+ "   [Error] (11:7): no viable alternative at input 'A'\n"
						+ "   [Error] (11:9): no viable alternative at input '{'\n"
						+ "   [Error] (11:0): Couldn't resolve reference to IdentifiableElement 'classl'.\n"
						+ "   [Error] (13:0): missing EOF at '}'",
				output);

		int jsFileCount = GeneratedJSFilesCounter.countFilesCompiledToES(file);
		assertEquals(4, jsFileCount);
	}

}
