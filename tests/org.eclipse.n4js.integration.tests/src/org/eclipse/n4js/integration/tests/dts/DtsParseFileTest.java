/**
 * Copyright (c) 2022 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.integration.tests.dts;

import static org.junit.Assert.assertFalse;

import java.io.File;

import org.junit.Ignore;
import org.junit.Test;

/**
 * Test for d.ts grammar
 */
public class DtsParseFileTest {

	static File FILE = new File("/Users/marcusmews/test/node_modules/@types/node/buffer.d.ts");

	/** Parse .d.ts snippet */
	@Ignore // use for adhoc-testing
	@Test
	public void parseDtsFile() throws Exception {
		DtsParsesDefinitelyTypedTest.parseFile(null, FILE.toPath(), (result) -> {
			assertFalse(result.hasSyntaxErrors());
		});
	}

}
