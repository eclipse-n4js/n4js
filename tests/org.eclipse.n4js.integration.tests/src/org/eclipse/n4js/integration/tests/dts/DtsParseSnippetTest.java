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

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.dts.DtsParseResult;
import org.eclipse.n4js.dts.DtsParser;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.workspace.locations.FileURI;
import org.junit.Test;

/**
 * Test for d.ts grammar
 */
public class DtsParseSnippetTest {

	/** Parse .d.ts snippet */
	@Test
	public void parseDefinitelyTyped() throws IOException {
		assertParseOK("export import atob = globalThis.atob;");
	}

	static void assertParseOK(String dtsContent) throws IOException {
		try (BufferedReader buf = new BufferedReader(new StringReader(dtsContent))) {

			N4JSResource resource = new N4JSResource();
			URI fileUri = new FileURI(new File("TMP.dts")).toURI();
			resource.setURI(fileUri);
			DtsParseResult parseResult = new DtsParser().parse(buf, resource);

			assertFalse(parseResult.hasSyntaxErrors());
		}
	}

}
