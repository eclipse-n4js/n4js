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
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.n4js.HeadlessCompilerFactory;
import org.eclipse.n4js.generator.headless.BuildSet;
import org.eclipse.n4js.generator.headless.N4HeadlessCompiler;
import org.eclipse.n4js.generator.headless.N4JSCompileException;
import org.eclipse.n4js.utils.io.FileDeleter;
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

	static final File workspace = new File("target/testscenarios");
	static final File scenarioRepository = new File("testdata");

	/**
	 * Called once
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
	 * Setup before each test.
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

		hlc.compile(allProjects(hlc, pProjectRoots));
		// expect source-files:
		assertExists(root, "wsp1/A/src-gen/packA/A.js");
		assertExists(root, "nest/wsp2/B/src-gen/packB/B.js");
		assertExists(root, "wsp3/C/src-gen/packC/C.js");
		assertExists(root, "nest/wsp2/D/src-gen/packD/D.js");
		assertExists(root, "nest/wsp2/D/src-gen/packD/D2.js");
		assertExists(root, "nest/nest/wsp4/E/src-gen/packE/E.js");
		assertExists(root, "nest/nest/wsp4/E/src-gen/packE/E2.js");
		assertExists(root, "nest/wsp5/F/src-gen/packF/F.js");
		assertExists(root, "wsp6/G/src-gen/packG/G.js");
		assertExists(root, "nest/nest/wsp4/H/src-gen/packH/H.js");
	}

	/**
	 * Broken project description file (package.json) will result in parse-error.
	 */
	@Test(expected = N4JSCompileException.class)
	public void testScenario02brokenProjectDescriptionFile() throws N4JSCompileException {
		N4HeadlessCompiler hlc = HeadlessCompilerFactory.createCompilerWithDefaults();
		File root = new File(workspace, "scenario02");
		List<File> pProjectRoots = Arrays.asList(//
				new File(root, "wsp1") // A
		);

		hlc.compile(allProjects(hlc, pProjectRoots));
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
		hlc.compile(allProjects(hlc, pProjectRoots));
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

		final BuildSet buildSet = hlc.getBuildSetComputer().createProjectsBuildSet(pProjectRoots, toCompile,
				Collections.emptySet());
		hlc.compile(buildSet);

		assertNotExists(root, "wsp1/A/src-gen/packA/A.js");
		assertNotExists(root, "nest/wsp2/B/src-gen/packB/B.js");
		assertNotExists(root, "wsp3/C/src-gen/packC/C.js");
		// Only those both should be available
		assertExists(root, "nest/wsp2/D/src-gen/packD/D.js");
		assertExists(root, "nest/wsp2/D/src-gen/packD/D2.js");

		assertNotExists(root, "nest/nest/wsp4/E/src-gen/packE/E.js");
		assertNotExists(root, "nest/nest/wsp4/E/src-gen/packE/E2.js");
		assertNotExists(root, "nest/wsp5/F/src-gen/packF/F.js");
		assertNotExists(root, "wsp6/G/src-gen/packG/G.js");
		assertNotExists(root, "nest/nest/wsp4/H/src-gen/packH/H.js");
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

		hlc.compile(projects(hlc, toCompile));

		// those should be available
		assertExists(root, "wsp1/A/src-gen/packA/A.js");
		assertExists(root, "nest/wsp2/B/src-gen/packB/B.js");
		assertExists(root, "nest/wsp2/D/src-gen/packD/D.js");
		assertExists(root, "nest/wsp2/D/src-gen/packD/D2.js");

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
				new File(root, "wsp1/P1"),
				new File(root, "wsp1/org.eclipse.n4js.mangelhaft"));

		hlc.setCompileSourceCode(false);
		hlc.setProcessTestCode(true);

		hlc.compile(projects(hlc, toCompile));

		// those should be available
		assertNotExists(root, "wsp1/P1/outfolder/c/Csrc1.js");
		assertNotExists(root, "wsp1/P1/outfolder/c/X.js");
		assertNotExists(root, "wsp1/P1/outfolder/c/X2.js");
		assertExists(root, "wsp1/P1/outfolder/t/T1.js");
		assertExists(root, "wsp1/P1/outfolder/t/T2.js");

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
				new File(root, "wsp1/P1"),
				new File(root, "wsp1/org.eclipse.n4js.mangelhaft"));

		hlc.setCompileSourceCode(true);
		hlc.setProcessTestCode(false);

		hlc.compile(projects(hlc, toCompile));

		// those should be available
		assertExists(root, "wsp1/P1/outfolder/c/Csrc1.js");
		assertExists(root, "wsp1/P1/outfolder/c/X.js");
		assertExists(root, "wsp1/P1/outfolder/c/X2.js");
		assertNotExists(root, "wsp1/P1/outfolder/t/T1.js");
		assertNotExists(root, "wsp1/P1/outfolder/t/T2.js");

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

		final BuildSet buildSet = hlc.getBuildSetComputer().createSingleFilesBuildSet(toCompile,
				Collections.emptySet());
		hlc.compile(buildSet);

		assertExists(root, "nest/wsp2/B/src-gen/packB/B2.js");

		// those should be available
		assertNotExists(root, "wsp1/A/src-gen/packA/A.js");
		assertNotExists(root, "nest/wsp2/B/src-gen/packB/B.js");
		assertNotExists(root, "nest/wsp2/D/src-gen/packD/D.js");
		assertNotExists(root, "nest/wsp2/D/src-gen/packD/D2.js");

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

		final BuildSet buildSet = hlc.getBuildSetComputer().createSingleFilesBuildSet(wspRoots, toCompile,
				Collections.emptySet());
		hlc.compile(buildSet);

		assertExists(root, "nest/wsp2/D/src-gen/packD/D2.js");

		// those should be available
		assertNotExists(root, "wsp1/A/src-gen/packA/A.js");
		assertNotExists(root, "nest/wsp2/B/src-gen/packB/B.js");
		assertNotExists(root, "nest/wsp2/B/src-gen/packB/B2.js");
		assertNotExists(root, "nest/wsp2/D/src-gen/packD/D.js");

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
			hlc.compile(allProjects(hlc, pProjectRoots));
			assertFalse("Should not have reached this point.", true);
		} catch (N4JSCompileException e) {
			String msg = e.getMessage();
			String expected = "Cannot compile project D due to 4 errors.";
			assertTrue("Wrong error message: '" + msg + "' expected beginning : '" + expected + "'",
					msg.startsWith(expected));
		}
		// expect source-files (depends on the chosen build order algorithm):
		assertExists(root, "wsp1/A/src-gen/packA/A.js");
		assertExists(root, "wsp1/C/src-gen/packC/C.js");
	}

	// //////// //// /// / ///////// //// //// /// / ///////// //// //// /// / ///////// //// //// /// / ///////// ////
	// //// /// / ///

	/**
	 * Computes a 'Projects' build set based on the given project locations.
	 */
	private static BuildSet allProjects(N4HeadlessCompiler hlc, List<File> projectRoots) throws N4JSCompileException {
		return hlc.getBuildSetComputer().createAllProjectsBuildSet(projectRoots, Collections.emptySet());
	}

	/**
	 * Computes an 'All Projects' build set by scanning the given project root locations.
	 */
	private static BuildSet projects(N4HeadlessCompiler hlc, List<File> projectLocations) throws N4JSCompileException {
		return hlc.getBuildSetComputer().createProjectsBuildSet(projectLocations, Collections.emptySet());
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
