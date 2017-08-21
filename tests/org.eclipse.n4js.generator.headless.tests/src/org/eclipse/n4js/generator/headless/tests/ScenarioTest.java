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
package org.eclipse.n4js.generator.headless.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.eclipse.n4js.HeadlessCompilerFactory;
import org.eclipse.n4js.generator.headless.N4HeadlessCompiler;
import org.eclipse.n4js.generator.headless.N4JSCompileException;
import org.eclipse.n4js.utils.io.FileDeleter;
import org.eclipse.n4js.validation.helper.N4JSLanguageConstants;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;

/**
 * Test data is organized in txt-files (content concatenated) under "/testdata".
 */
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
public class ScenarioTest {

	static File workspace = new File("target/testscenarios");
	static File scenarioRepository = new File("testdata");

	/** compiler path segment as "es" in "...src-gen/es/A/..." */
	static String CMPLR = N4JSLanguageConstants.TRANSPILER_SUBFOLDER_FOR_TESTS;

	/**
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// clean workspace
		FileDeleter.delete(workspace.toPath());

		// extract test-scenarios.
		DirectoryStream.Filter<Path> filter = new DirectoryStream.Filter<Path>() {
			@Override
			public boolean accept(Path file) throws IOException {
				String fname = file.getFileName().toString();
				return fname.startsWith("scenario") && fname.endsWith("txt");
			}
		};
		Path dir = scenarioRepository.toPath();
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, filter)) {
			for (Path scenario : stream) {

				String x = scenario.getFileName().toString();
				String outDir = x.substring(0, x.indexOf(".txt")); // "scenario01";

				String scenarioContent = readFile(scenario);
				// Format is '#' separated, first line is filename
				boolean skipFirst = true;
				for (String junk : Splitter.on('#').split(scenarioContent)) {
					if (skipFirst) {
						skipFirst = false;
						continue;
					}
					List<String> lines = Splitter.on("\n").splitToList(junk);
					if (!lines.isEmpty()) {
						Iterator<String> it = lines.iterator();
						String filename = it.next().trim();

						File f = new File(workspace, outDir + "/" + filename);
						String content = Joiner.on('\n').join(it);
						writeFile(f, content);
					}
				}
			}
		}
	}

	private static void writeFile(File f, String content) throws IOException {
		Files.createDirectories(f.getAbsoluteFile().getParentFile().toPath());
		Files.write(f.getAbsoluteFile().toPath(), content.getBytes(StandardCharsets.UTF_8));
	}

	static String readFile(Path path)
			throws IOException {
		byte[] encoded = Files.readAllBytes(path);
		return new String(encoded, StandardCharsets.UTF_8);
	}

	/**
	 *
	 */
	@Before
	public void setUp() throws Exception {
		//
	}

	/**
	 * Testing compiling
	 */
	@Test
	public void testScenario01BuildAllProjects() throws N4JSCompileException {
		N4HeadlessCompiler hlc = HeadlessCompilerFactory.createCompilerWithDefaults();
		File root = new File(workspace, "scenario01");
		List<File> pProjectRoots = Arrays.asList(//
				new File(root, "wsp1"), // A, NotAProject
				new File(root, "nest/wsp2"), // B,D
				new File(root, "wsp3"), // C
				new File(root, "nest/nest/wsp4"), // E,H
				new File(root, "nest/wsp5"), // F
				new File(root, "wsp6") // G
		);

		hlc.compileAllProjects(pProjectRoots);
		// expect source-files:
		assertExists(root, "wsp1/A/src-gen/" + CMPLR + "/A/packA/A.js");
		assertExists(root, "nest/wsp2/B/src-gen/" + CMPLR + "/B/packB/B.js");
		assertExists(root, "wsp3/C/src-gen/" + CMPLR + "/C/packC/C.js");
		assertExists(root, "nest/wsp2/D/src-gen/" + CMPLR + "/D/packD/D.js");
		assertExists(root, "nest/wsp2/D/src-gen/" + CMPLR + "/D/packD/D2.js");
		assertExists(root, "nest/nest/wsp4/E/src-gen/" + CMPLR + "/E/packE/E.js");
		assertExists(root, "nest/nest/wsp4/E/src-gen/" + CMPLR + "/E/packE/E2.js");
		assertExists(root, "nest/wsp5/F/src-gen/" + CMPLR + "/F/packF/F.js");
		assertExists(root, "wsp6/G/src-gen/" + CMPLR + "/G/packG/G.js");
		assertExists(root, "nest/nest/wsp4/H/src-gen/" + CMPLR + "/H/packH/H.js");
	}

	/**
	 * Broken manifest will result in parse-error.
	 */
	@Test(expected = N4JSCompileException.class)
	public void testScenario02brokenManifest() throws N4JSCompileException {
		N4HeadlessCompiler hlc = HeadlessCompilerFactory.createCompilerWithDefaults();
		File root = new File(workspace, "scenario02");
		List<File> pProjectRoots = Arrays.asList(//
				new File(root, "wsp1") // A
		);

		hlc.compileAllProjects(pProjectRoots);
	}

	/**
	 * Broken source-file will result in compile-error.
	 */
	@Test(expected = N4JSCompileException.class)
	public void testScenario03brokenN4jsSyntax() throws N4JSCompileException {
		N4HeadlessCompiler hlc = HeadlessCompilerFactory.createCompilerWithDefaults();
		File root = new File(workspace, "scenario03");
		List<File> pProjectRoots = Arrays.asList(//
				new File(root, "wsp1") // A
		);
		hlc.compileAllProjects(pProjectRoots);
	}

	/**
	 * Testing compiling only a single project.
	 */
	@Test
	public void testScenario04BuildSingleProjectsWithManyProjectsOnPath() throws N4JSCompileException {
		N4HeadlessCompiler hlc = HeadlessCompilerFactory.createCompilerWithDefaults();
		File root = new File(workspace, "scenario04");
		List<File> pProjectRoots = Arrays.asList(//
				new File(root, "wsp1"), // A, NotAProject
				new File(root, "nest/wsp2"), // B,D
				new File(root, "wsp3"), // C
				new File(root, "nest/nest/wsp4"), // E,H
				new File(root, "nest/wsp5"), // F
				new File(root, "wsp6") // G
		);
		List<File> toCompile = Arrays.asList(//
				new File(root, "nest/wsp2/D") // requires B and A to be loaded.
		);

		hlc.compileProjects(pProjectRoots, toCompile);

		assertNotExists(root, "wsp1/A/src-gen/" + CMPLR + "/A/packA/A.js");
		assertNotExists(root, "nest/wsp2/B/src-gen/" + CMPLR + "/B/packB/B.js");
		assertNotExists(root, "wsp3/C/src-gen/" + CMPLR + "/C/packC/C.js");
		// Only those both should be available
		assertExists(root, "nest/wsp2/D/src-gen/" + CMPLR + "/D/packD/D.js");
		assertExists(root, "nest/wsp2/D/src-gen/" + CMPLR + "/D/packD/D2.js");

		assertNotExists(root, "nest/nest/wsp4/E/src-gen/" + CMPLR + "/E/packE/E.js");
		assertNotExists(root, "nest/nest/wsp4/E/src-gen/" + CMPLR + "/E/packE/E2.js");
		assertNotExists(root, "nest/wsp5/F/src-gen/" + CMPLR + "/F/packF/F.js");
		assertNotExists(root, "wsp6/G/src-gen/" + CMPLR + "/G/packG/G.js");
		assertNotExists(root, "nest/nest/wsp4/H/src-gen/" + CMPLR + "/H/packH/H.js");
	}

	/**
	 */
	@Test
	public void testScenario05BuildSingleProjectsWithoutProjectroot() throws N4JSCompileException {
		N4HeadlessCompiler hlc = HeadlessCompilerFactory.createCompilerWithDefaults();
		File root = new File(workspace, "scenario05");

		List<File> toCompile = Arrays.asList(//
				new File(root, "nest/wsp2/D"), // requires B and A to be loaded.
				new File(root, "nest/wsp2/B"), // requires nothing
				new File(root, "wsp1/A") // requires nothing
		);

		hlc.compileProjects(toCompile);

		// those should be available
		assertExists(root, "wsp1/A/src-gen/" + CMPLR + "/A/packA/A.js");
		assertExists(root, "nest/wsp2/B/src-gen/" + CMPLR + "/B/packB/B.js");
		assertExists(root, "nest/wsp2/D/src-gen/" + CMPLR + "/D/packD/D.js");
		assertExists(root, "nest/wsp2/D/src-gen/" + CMPLR + "/D/packD/D2.js");

	}

	/**
	 * Testing for different output-folder, configured in manifest. Testing for external source and configuration of
	 * noModuleWrapFilter
	 */
	@Test
	public void testScenario06NoModuleWrapFilter() throws N4JSCompileException, IOException {
		N4HeadlessCompiler hlc = HeadlessCompilerFactory.createCompilerWithDefaults();
		File root = new File(workspace, "scenario06");

		List<File> toCompile = Arrays.asList(//
				new File(root, "wsp1/P1") // requires nothing
		);

		hlc.compileProjects(toCompile);

		// those should be available
		assertExists(root, "wsp1/P1/outfolder/" + CMPLR + "/P1/c/Csrc1.js");
		assertExists(root, "wsp1/P1/outfolder/" + CMPLR + "/P1/c/X.js");
		assertExists(root, "wsp1/P1/outfolder/" + CMPLR + "/P1/c/X2.js");

		// there should be no src-gen folder:
		assertNotExists(root, "wsp1/P1/src-gen");

		assertFileSystemCallStartsWith(root, "wsp1/P1/outfolder/" + CMPLR + "/P1/c/X.js",
				"System.registerDynamic([");
		assertFileSystemCallStartsNotWith(root, "wsp1/P1/outfolder/" + CMPLR + "/P1/c/X2.js",
				"System.register([");
	}

	/**
	 * Same as scenario 6, but now we have a project that ONLY contains external sources (i.e. *no* n4js file). This
	 * kind of project caused problems before.
	 */
	@Test
	public void testScenario06bNoModuleWrapFilter() throws N4JSCompileException, IOException {
		N4HeadlessCompiler hlc = HeadlessCompilerFactory.createCompilerWithDefaults();
		File root = new File(workspace, "scenario06b");

		List<File> toCompile = Arrays.asList(//
				new File(root, "wsp1/P1") // requires nothing
		);

		hlc.compileProjects(toCompile);

		// those should be available
		assertExists(root, "wsp1/P1/outfolder/" + CMPLR + "/P1/c/X.js");
		assertExists(root, "wsp1/P1/outfolder/" + CMPLR + "/P1/c/X2.js");

		// there should be no src-gen folder:
		assertNotExists(root, "wsp1/P1/src-gen");

		assertFileSystemCallStartsWith(root, "wsp1/P1/outfolder/" + CMPLR + "/P1/c/X.js",
				"System.registerDynamic([");
		assertFileSystemCallStartsNotWith(root, "wsp1/P1/outfolder/" + CMPLR + "/P1/c/X2.js",
				"System.register([");
	}

	/**
	 * Building a project but only the test-files.
	 *
	 */
	@Test
	public void testScenario07TestOnlyCompilation() throws N4JSCompileException {
		N4HeadlessCompiler hlc = HeadlessCompilerFactory.createCompilerWithDefaults();
		File root = new File(workspace, "scenario07");

		List<File> toCompile = Arrays.asList(//
				new File(root, "wsp1/P1") // requires nothing
		);

		hlc.setCompileSourceCode(false);
		hlc.setProcessTestCode(true);
		hlc.compileProjects(toCompile);

		// those should be available
		assertNotExists(root, "wsp1/P1/outfolder/" + CMPLR + "/P1/c/Csrc1.js");
		assertNotExists(root, "wsp1/P1/outfolder/" + CMPLR + "/P1/c/X.js");
		assertNotExists(root, "wsp1/P1/outfolder/" + CMPLR + "/P1/c/X2.js");
		assertExists(root, "wsp1/P1/outfolder/" + CMPLR + "/P1/t/T1.js");
		assertExists(root, "wsp1/P1/outfolder/" + CMPLR + "/P1/t/T2.js");

		// there should be no src-gen folder:
		assertNotExists(root, "wsp1/P1/src-gen");

	}

	/**
	 * Building a project but not the test-files.
	 *
	 */
	@Test
	public void testScenario08NotTestCompilation() throws N4JSCompileException {
		N4HeadlessCompiler hlc = HeadlessCompilerFactory.createCompilerWithDefaults();
		File root = new File(workspace, "scenario08");

		List<File> toCompile = Arrays.asList(//
				new File(root, "wsp1/P1") // requires nothing
		);

		hlc.setCompileSourceCode(true);
		hlc.setProcessTestCode(false);
		hlc.compileProjects(toCompile);

		// those should be available
		assertExists(root, "wsp1/P1/outfolder/" + CMPLR + "/P1/c/Csrc1.js");
		assertExists(root, "wsp1/P1/outfolder/" + CMPLR + "/P1/c/X.js");
		assertExists(root, "wsp1/P1/outfolder/" + CMPLR + "/P1/c/X2.js");
		assertNotExists(root, "wsp1/P1/outfolder/" + CMPLR + "/P1/t/T1.js");
		assertNotExists(root, "wsp1/P1/outfolder/" + CMPLR + "/P1/t/T2.js");

		// there should be no src-gen folder:
		assertNotExists(root, "wsp1/P1/src-gen");

	}

	/**
	 */
	@Test
	public void testScenario09BuildSingleFilesWithoutProjectRoot() throws N4JSCompileException {
		N4HeadlessCompiler hlc = HeadlessCompilerFactory.createCompilerWithDefaults();
		File root = new File(workspace, "scenario09");

		List<File> toCompile = Arrays.asList(//
				// new File(root, "nest/wsp2/D"), // requires B and A to be loaded.
				new File(root, "nest/wsp2/B/src/packB/B2.n4js") // requires nothing
		// new File(root, "wsp1/A") // requires nothing
		);

		hlc.compileSingleFiles(toCompile);

		assertExists(root, "nest/wsp2/B/src-gen/" + CMPLR + "/B/packB/B2.js");

		// those should be available
		assertNotExists(root, "wsp1/A/src-gen/" + CMPLR + "/A/packA/A.js");
		assertNotExists(root, "nest/wsp2/B/src-gen/" + CMPLR + "/B/packB/B.js");
		assertNotExists(root, "nest/wsp2/D/src-gen/" + CMPLR + "/D/packD/D.js");
		assertNotExists(root, "nest/wsp2/D/src-gen/" + CMPLR + "/D/packD/D2.js");

	}

	/**
	 */
	@Test
	public void testScenario10BuildSingleFileWithProjectRootAndDependency() throws N4JSCompileException {
		N4HeadlessCompiler hlc = HeadlessCompilerFactory.createCompilerWithDefaults();
		File root = new File(workspace, "scenario10");

		List<File> toCompile = Arrays.asList(//
				// new File(root, "nest/wsp2/D"), // requires B and A to be loaded.
				new File(root, "nest/wsp2/D/src/packD/D2.n4js") // requires nothing
		// new File(root, "wsp1/A") // requires nothing
		);
		List<File> wspRoots = Arrays.asList(//
				new File(root, "nest/wsp2"),
				new File(root, "wsp1"));

		hlc.compileSingleFiles(wspRoots, toCompile);

		assertExists(root, "nest/wsp2/D/src-gen/" + CMPLR + "/D/packD/D2.js");

		// those should be available
		assertNotExists(root, "wsp1/A/src-gen/" + CMPLR + "/A/packA/A.js");
		assertNotExists(root, "nest/wsp2/B/src-gen/" + CMPLR + "/B/packB/B.js");
		assertNotExists(root, "nest/wsp2/B/src-gen/" + CMPLR + "/B/packB/B2.js");
		assertNotExists(root, "nest/wsp2/D/src-gen/" + CMPLR + "/D/packD/D.js");

	}

	/**
	 * Testing compiling
	 */
	@Test
	public void testScenario11FailDueToInvisbleProject() {
		N4HeadlessCompiler hlc = HeadlessCompilerFactory.createCompilerWithDefaults();
		File root = new File(workspace, "scenario11");
		List<File> pProjectRoots = Arrays.asList(//
				new File(root, "wsp1"));

		try {
			hlc.compileAllProjects(pProjectRoots);
			assertFalse("Should not have reached this point.", true);
		} catch (N4JSCompileException e) {
			String msg = e.getMessage();
			String expected = "Cannot compile project D due to 4 errors.";
			assertTrue("Wrong error message: '" + msg + "' expected beginning : '" + expected + "'",
					msg.startsWith(expected));
		}
		// expect source-files (depends on the chosen build order algorithm):
		assertExists(root, "wsp1/A/src-gen/" + CMPLR + "/A/packA/A.js");
		assertExists(root, "wsp1/C/src-gen/" + CMPLR + "/C/packC/C.js");
	}

	// //////// //// /// / ///////// //// //// /// / ///////// //// //// /// / ///////// //// //// /// / ///////// ////
	// //// /// / ///

	/**
	 */
	private void assertFileSystemCallStartsWith(File root, String file, String content) throws IOException {
		List<String> lines = Files.readAllLines(new File(root, file).toPath(), StandardCharsets.UTF_8);
		// First line is CJS System-patching, second is System-call:
		String firstLine = lines.get(1).trim();
		assertTrue("File " + file + " should start with \'" + content + "\' but started with \'" + firstLine
				+ "\' instead",
				firstLine.startsWith(content));
	}

	/**
	 */
	private void assertFileSystemCallStartsNotWith(File root, String file, String content) throws IOException {
		List<String> lines = Files.readAllLines(new File(root, file).toPath(), StandardCharsets.UTF_8);
		// First line is CJS System-patching, second is System-call:
		String firstLine = lines.get(1).trim();
		assertFalse("File " + file + " should NOT start with " + content, firstLine.startsWith(content));
	}

	/**
	 */
	private static void assertExists(File root, String path) {
		assertTrue("File " + path + " missing.", new File(root, path).exists());
	}

	/**
	 */
	private static void assertNotExists(File root, String path) {
		assertFalse("File " + path + " should not be there.", new File(root, path).exists());
	}
}
