/**
 * Copyright (c) 2019 NumberFour AG and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.doctools;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.xml.sax.SAXException;

/**
 * Tests Eclipse TOC generation. Test data is loaded from testres/toc folder.
 */
@RunWith(Parameterized.class)
public class EclipseHelpTOCGeneratorTest {

	/**
	 * Provides test parameters.
	 */
	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ "sample" }, { "sampleWithAppendix" }
		});
	}

	private final String candidate;

	/**
	 * Parameterized test.
	 */
	public EclipseHelpTOCGeneratorTest(String candidate) {
		this.candidate = candidate;
	}

	/**
	 * Tests generated output against expected string.
	 */
	@Test
	public void testToc() throws IOException, ParserConfigurationException, SAXException {

		File adocXML = Paths.get("testres", "toc", candidate, candidate + ".xml").toFile();
		File expectedTOCFile = Paths.get("testres", "toc", candidate, "toc" + candidate + ".xml").toFile();

		String expectedTOC = new String(Files.readAllBytes(expectedTOCFile.toPath()));
		EclipseHelpTOCGenerator generator = new EclipseHelpTOCGenerator();
		String actualTOC = generator.generateTOC(adocXML, "");

		assertEquals(expectedTOC, actualTOC);
	}

}
