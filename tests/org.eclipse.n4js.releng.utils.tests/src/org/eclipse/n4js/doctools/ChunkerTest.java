package org.eclipse.n4js.doctools;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;

import org.eclipse.n4js.doctools.Chunker.Range;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests Eclipse TOC generation. Test data is loaded from testres/chunker folder.
 */
@RunWith(Parameterized.class)
public class ChunkerTest {

	/**
	 * Provides test parameters.
	 */
	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ "simpleTest", null },
				{ "testHeader", "<!-- header end -->\n" },
				{ "testLongNames", null },
				{ "testLinks", null }
		});
	}

	private final String candidate;
	private final String head;
	private final String foot = null;
	private final String chunk = null;
	private final String name = null;
	private final String index = null;

	/**
	 * Parameterized test setup.
	 */
	public ChunkerTest(String candidate, String head) {
		this.candidate = candidate;
		this.head = head;
	}

	/**
	 * Loads html from folder based on candidates name, expected chunks are located there as well, content is compared.
	 */
	@Test
	public void testChunker() throws IOException {
		LinkedHashMap<String, String> expectedChunksWithHTML = readExpectedChunksWithHTML();

		String html = expectedChunksWithHTML.get("html");
		expectedChunksWithHTML.remove("html");
		Chunker chunker = null;
		try {
			chunker = new Chunker(html, head, foot, chunk, name, index);
		} catch (ChunkError e) {
			fail("Failed parsing html: " + e);
		}

		if (chunker == null) {
			fail("Cannot read chunks");
		} else {
			for (Range range : chunker.chunks) {
				String expectedChunk = expectedChunksWithHTML.get(range.name);
				expectedChunksWithHTML.remove(range.name);
				assertNotNull(expectedChunk, "Unexpected chunk " + range);
				assertEquals("Error in " + range.name, expectedChunk, chunker.getChunk(range).toString());
			}

			assertTrue("Chunks not found: " + expectedChunksWithHTML.keySet(), expectedChunksWithHTML.isEmpty());
		}

	}

	private LinkedHashMap<String, String> readExpectedChunksWithHTML() throws IOException {
		LinkedHashMap<String, String> expectedChunksWithHTML = new LinkedHashMap<>();

		File folder = Paths.get("testres", "chunker", candidate).toFile();
		for (File f : folder.listFiles()) {
			String chunkName = f.getName().substring(0, f.getName().indexOf(".html"));
			String content = new String(Files.readAllBytes(f.toPath()));
			expectedChunksWithHTML.put(chunkName, content);
		}
		return expectedChunksWithHTML;
	}

}
