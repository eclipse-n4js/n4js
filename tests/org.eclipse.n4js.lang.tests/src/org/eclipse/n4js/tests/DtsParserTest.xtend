/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.tests

import java.nio.file.Files
import java.nio.file.Path
import org.eclipse.n4js.tests.parser.AbstractParserTest
import org.junit.Test

/**
 *
 */
class DtsParserTest extends AbstractParserTest {

	private Path FILE = Path.of("/Users/mark-oliver.reiser/Desktop/dtsParser/test.d.ts");

	@Test
	def void testIt() {
		val code = Files.readString(FILE);
		val idx = code.indexOf("%%END");
		val codeTrimmed = code.substring(0, idx);
		val script = codeTrimmed.parseN4jsdSuccessfully;
		
	}
}
