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
package org.eclipse.n4js.ide.init.tests;

import static org.eclipse.n4js.N4JSGlobals.PACKAGE_JSON;
import static org.eclipse.n4js.cli.N4jscExitCode.INIT_ERROR_WORKING_DIR;
import static org.eclipse.n4js.cli.N4jscExitCode.SUCCESS;
import static org.eclipse.n4js.cli.N4jscExitCode.VALIDATION_ERRORS;
import static org.eclipse.n4js.cli.N4jscTestOptions.IMPLICIT_COMPILE;
import static org.eclipse.n4js.cli.N4jscTestOptions.INIT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.eclipse.n4js.cli.N4jscTestOptions;
import org.eclipse.n4js.cli.helper.AbstractCliCompileTest;
import org.eclipse.n4js.cli.helper.CliCompileResult;
import org.eclipse.n4js.cli.helper.ProcessResult;
import org.eclipse.n4js.utils.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for goal init
 */
public class N4jscInitTest extends AbstractCliCompileTest {

	static final String CWD_NAME = "TestInit";

	File cwd;

	/** Set current working directory. */
	@Before
	public void setupWorkspace() throws IOException {
		cwd = new File(CWD_NAME);
		if (cwd.exists()) {
			FileUtils.delete(cwd);
		}
		cwd.mkdir();
	}

	/** Delete current working directory. */
	@After
	public void deleteWorkspace() throws IOException {
		FileUtils.delete(cwd);
	}

	/** Basic init test. */
	@Test
	public void optionYes() throws Exception {
		N4jscTestOptions options = INIT().setWorkingDirectory(cwd.toPath()).yes();
		n4jsc(options, SUCCESS);

		assertEquals("TestInit\n"
				+ "- package.json\n"
				+ "", FileUtils.serializeFileTree(cwd));

		npmInstall(cwd.toPath());
		CliCompileResult result = n4jsc(IMPLICIT_COMPILE(cwd).setWorkingDirectory(cwd.toPath()), SUCCESS);
		assertEquals(0, result.getTranspiledFilesCount());
	}

	/** fail due to existing package.json file */
	@Test
	public void failDueToExistingPackagejson() throws Exception {
		File packjson = new File(cwd.getAbsoluteFile(), PACKAGE_JSON);
		packjson.createNewFile();

		N4jscTestOptions options = INIT().setWorkingDirectory(cwd.toPath()).yes();
		CliCompileResult result = n4jsc(options, INIT_ERROR_WORKING_DIR);

		assertEquals(
				"ERROR-410 (Error: Unsupported working directory):  Current working directory must not contain a package.json file. Note:\n"
						+ "  In case you like to add the n4js property to an existing project, use option --n4js\n"
						+ "  In case you like to add a project to an existing workspace project, use options -w -c",
				result.getStdOut());
	}

	/** --scope test. */
	@Test
	public void optionsYesScope() throws Exception {
		File subfolder = new File(cwd, "@myScope/scopedProject");
		subfolder.mkdir();
		N4jscTestOptions options = INIT().setWorkingDirectory(subfolder.toPath()).yes().scope();
		n4jsc(options, SUCCESS);

		assertEquals("TestInit\n"
				+ "+ @myScope\n"
				+ "  + scopedProject\n"
				+ "    - package.json\n"
				+ "", FileUtils.serializeFileTree(cwd));

		String packagejsonContents = Files.readString(subfolder.toPath().resolve(PACKAGE_JSON));
		assertTrue(packagejsonContents.contains("\"name\": \"@myScope/scopedProject\""));
	}

	/** --scope test. */
	@Test
	public void optionsYesScopeCreate() throws Exception {
		N4jscTestOptions options = INIT().setWorkingDirectory(cwd.toPath()).yes().scope().create();
		n4jsc(options, SUCCESS);

		assertEquals("TestInit\n"
				+ "+ @scope\n"
				+ "  + my-project\n"
				+ "    - package.json\n"
				+ "", FileUtils.serializeFileTree(cwd));

		File subfolder = new File(cwd, "@scope/my-project");
		String packagejsonContents = Files.readString(subfolder.toPath().resolve(PACKAGE_JSON));
		assertTrue(packagejsonContents.contains("\"name\": \"@scope/my-project\""));
	}

	/** test hello world example */
	@Test
	public void helloWorld() throws Exception {
		String answers = ",,,,,,yes";
		N4jscTestOptions options = INIT().setWorkingDirectory(cwd.toPath()).answers(answers);
		n4jsc(options, SUCCESS);

		npmInstall(cwd.toPath());
		CliCompileResult result = n4jsc(IMPLICIT_COMPILE(cwd).setWorkingDirectory(cwd.toPath()), SUCCESS);
		assertEquals(1, result.getTranspiledFilesCount());
		ProcessResult resultNodejs = nodejsRun(cwd.toPath(), Path.of("."));
		assertEquals("Hello World", resultNodejs.getStdOut());
	}

	/** test hello world test example */
	@Test
	public void helloWorldTested() throws Exception {
		String answers = ",,,,,,yes,yes";
		N4jscTestOptions options = INIT().setWorkingDirectory(cwd.toPath()).answers(answers);
		n4jsc(options, SUCCESS);

		npmInstall(cwd.toPath());

		ProcessResult resultBuild = npmRun(cwd.toPath(), "run", "build");
		assertTrue(resultBuild.getStdOut().contains("Errors: 0"));
		assertTrue(resultBuild.getStdOut().contains("Generated: 4"));

		ProcessResult resultNodejs = nodejsRun(cwd.toPath(), Path.of("."));
		assertEquals("Hello World", resultNodejs.getStdOut());

		ProcessResult resultTest = npmRun(cwd.toPath(), "run", "test");
		assertTrue(resultTest.getStdOut().contains(
				"Testing completed: [32mSUCCESSES[39m: 1, [31mFAILURES[39m: 0, [31mERRORS[39m: 0, [36mSKIPPED[39m: 0"));
	}

	/** test another project name */
	@Test
	public void otherName() throws Exception {
		String answers = "otherName";
		N4jscTestOptions options = INIT().setWorkingDirectory(cwd.toPath()).answers(answers);
		CliCompileResult result = n4jsc(options, SUCCESS);

		npmInstall(cwd.toPath());
		result = n4jsc(IMPLICIT_COMPILE(cwd).setWorkingDirectory(cwd.toPath()), VALIDATION_ERRORS);
		assertEquals(0, result.getTranspiledFilesCount()); // there are no files to transpile

		String packagejsonContents = Files.readString(cwd.toPath().resolve(PACKAGE_JSON));
		assertTrue(packagejsonContents.contains("  \"name\": \"otherName\",\n"));
	}

	/** test for a custom main module */
	@Test
	public void mainModule1Compile() throws Exception {
		String answers = ",,index.js";
		N4jscTestOptions options = INIT().setWorkingDirectory(cwd.toPath()).answers(answers);
		n4jsc(options, SUCCESS);

		String packagejsonContents = Files.readString(cwd.toPath().resolve(PACKAGE_JSON));
		assertEquals("{\n"
				+ "  \"name\": \"TestInit\",\n"
				+ "  \"version\": \"0.0.1\",\n"
				+ "  \"main\": \"src-gen/index.js\",\n"
				+ "  \"scripts\": {\n"
				+ "    \"n4jsc\": \"n4jsc\",\n"
				+ "    \"build\": \"n4jsc compile . --clean || true\"\n"
				+ "  },\n"
				+ "  \"dependencies\": {\n"
				+ "    \"n4js-runtime\": \"\",\n"
				+ "    \"n4js-runtime-es2015\": \"\"\n"
				+ "  },\n"
				+ "  \"devDependencies\": {\n"
				+ "    \"n4js-cli\": \"\"\n"
				+ "  },\n"
				+ "  \"n4js\": {\n"
				+ "    \"projectType\": \"library\",\n"
				+ "    \"mainModule\": \"index\",\n"
				+ "    \"output\": \"src-gen\",\n"
				+ "    \"sources\": {\n"
				+ "      \"source\": [\n"
				+ "        \"src\"\n"
				+ "      ]\n"
				+ "    },\n"
				+ "    \"requiredRuntimeLibraries\": [\n"
				+ "      \"n4js-runtime-es2015\"\n"
				+ "    ]\n"
				+ "  }\n"
				+ "}", packagejsonContents);

		assertEquals("TestInit\n"
				+ "- package.json\n"
				+ "+ src\n"
				+ "  - index.n4js\n"
				+ "", FileUtils.serializeFileTree(cwd));

		npmInstall(cwd.toPath());
		CliCompileResult result = n4jsc(IMPLICIT_COMPILE(cwd).setWorkingDirectory(cwd.toPath()), SUCCESS);
		assertEquals(1, result.getTranspiledFilesCount());
	}

	/** test for a custom main module */
	@Test
	public void mainModule2() throws Exception {
		String answers = ",,index.n4js";
		N4jscTestOptions options = INIT().setWorkingDirectory(cwd.toPath()).answers(answers);
		n4jsc(options, SUCCESS);

		String packagejsonContents = Files.readString(cwd.toPath().resolve(PACKAGE_JSON));
		assertTrue(packagejsonContents.contains("  \"main\": \"src-gen/index.js\",\n"));
		assertTrue(packagejsonContents.contains("    \"mainModule\": \"index\",\n"));

		assertEquals("TestInit\n"
				+ "- package.json\n"
				+ "+ src\n"
				+ "  - index.n4js\n"
				+ "", FileUtils.serializeFileTree(cwd));
	}

	/** test for a custom main module */
	@Test
	public void mainModule3() throws Exception {
		String answers = ",,index.jsx";
		N4jscTestOptions options = INIT().setWorkingDirectory(cwd.toPath()).answers(answers);
		n4jsc(options, SUCCESS);

		String packagejsonContents = Files.readString(cwd.toPath().resolve(PACKAGE_JSON));
		assertTrue(packagejsonContents.contains("  \"main\": \"src-gen/index.jsx\",\n"));
		assertTrue(packagejsonContents.contains("    \"mainModule\": \"index\",\n"));

		assertEquals("TestInit\n"
				+ "- package.json\n"
				+ "+ src\n"
				+ "  - index.n4jsx\n"
				+ "", FileUtils.serializeFileTree(cwd));
	}

	/** test for a custom main module */
	@Test
	public void mainModule4() throws Exception {
		String answers = ",,index.n4jsx";
		N4jscTestOptions options = INIT().setWorkingDirectory(cwd.toPath()).answers(answers);
		n4jsc(options, SUCCESS);

		String packagejsonContents = Files.readString(cwd.toPath().resolve(PACKAGE_JSON));
		assertTrue(packagejsonContents.contains("  \"main\": \"src-gen/index.jsx\",\n"));
		assertTrue(packagejsonContents.contains("    \"mainModule\": \"index\",\n"));

		assertEquals("TestInit\n"
				+ "- package.json\n"
				+ "+ src\n"
				+ "  - index.n4jsx\n"
				+ "", FileUtils.serializeFileTree(cwd));
	}

	/** test also create yarn */
	@Test
	public void yarn() throws Exception {
		N4jscTestOptions options = INIT().setWorkingDirectory(cwd.toPath()).yes().workspaces();
		n4jsc(options, SUCCESS);

		assertEquals("TestInit\n"
				+ "- package.json\n"
				+ "+ packages\n"
				+ "  + my-project\n"
				+ "    - package.json\n"
				+ "", FileUtils.serializeFileTree(cwd));

		yarnInstall(cwd.toPath());
		n4jsc(IMPLICIT_COMPILE(cwd).setWorkingDirectory(cwd.toPath()), SUCCESS);
	}

	/** test yarn hello world */
	@Test
	public void yarnHelloWorld() throws Exception {
		N4jscTestOptions options = INIT().setWorkingDirectory(cwd.toPath())
				.workspaces()
				.answers(",,,,,,yes");
		n4jsc(options, SUCCESS);

		yarnInstall(cwd.toPath());
		CliCompileResult result = n4jsc(IMPLICIT_COMPILE(cwd).setWorkingDirectory(cwd.toPath()), SUCCESS);
		assertEquals(1, result.getTranspiledFilesCount());
		ProcessResult resultNodejs = nodejsRun(cwd.toPath(), Path.of("packages/my-project"));
		assertEquals("Hello World", resultNodejs.getStdOut());
	}

	/** test yarn hello world test */
	@Test
	public void yarnHelloWorldTested() throws Exception {
		N4jscTestOptions options = INIT().setWorkingDirectory(cwd.toPath())
				.workspaces()
				.answers(",,,,,,yes,yes");
		n4jsc(options, SUCCESS);

		yarnInstall(cwd.toPath());

		ProcessResult resultBuild = yarnRun(cwd.toPath(), "run", "build");
		assertTrue(resultBuild.getStdOut().contains("Errors: 0"));
		assertTrue(resultBuild.getStdOut().contains("Generated: 4"));

		ProcessResult resultNodejs = nodejsRun(cwd.toPath(), Path.of("packages/my-project"));
		assertEquals("Hello World", resultNodejs.getStdOut());

		ProcessResult resultTest = yarnRun(cwd.toPath(), "run", "test");
		assertTrue(resultTest.getStdOut().contains(
				"Testing completed: [32mSUCCESSES[39m: 1, [31mFAILURES[39m: 0, [31mERRORS[39m: 0, [36mSKIPPED[39m: 0"));
	}

	/** test yarn create */
	@Test
	public void yarnCreate() throws Exception {
		// create environment
		N4jscTestOptions options = INIT().setWorkingDirectory(cwd.toPath()).yes().workspaces().create();
		n4jsc(options, SUCCESS);

		assertEquals("TestInit\n"
				+ "+ yarn-project\n"
				+ "  - package.json\n"
				+ "  + packages\n"
				+ "    + my-project\n"
				+ "      - package.json\n"
				+ "", FileUtils.serializeFileTree(cwd));
	}

	/** test yarn create with scope project */
	@Test
	public void yarnCreateScope() throws Exception {
		// create environment
		N4jscTestOptions options = INIT().setWorkingDirectory(cwd.toPath()).yes().workspaces().scope().create();
		n4jsc(options, SUCCESS);

		assertEquals("TestInit\n"
				+ "+ yarn-project\n"
				+ "  - package.json\n"
				+ "  + packages\n"
				+ "    + @scope\n"
				+ "      + my-project\n"
				+ "        - package.json\n"
				+ "", FileUtils.serializeFileTree(cwd));
	}

	/** test yarn at wrong location */
	@Test
	public void yarnWrongLocation1() throws Exception {
		// create environment
		N4jscTestOptions options = INIT().setWorkingDirectory(cwd.toPath()).yes().workspaces();
		n4jsc(options, SUCCESS);

		assertEquals("TestInit\n"
				+ "- package.json\n"
				+ "+ packages\n"
				+ "  + my-project\n"
				+ "    - package.json\n"
				+ "", FileUtils.serializeFileTree(cwd));

		// test
		File newProject = new File(cwd, "packages");
		options = INIT().yes().setWorkingDirectory(newProject.toPath());
		CliCompileResult result = n4jsc(options, INIT_ERROR_WORKING_DIR);

		assertEquals(
				"ERROR-410 (Error: Unsupported working directory):  "
						+ "Creating a new project inside an existing yarn project requires option '--workspaces' to be set.",
				result.getStdOut());
	}

	/** test yarn at wrong location */
	@Test
	public void yarnWrongLocation2() throws Exception {
		// create environment
		N4jscTestOptions options = INIT().setWorkingDirectory(cwd.toPath()).yes().workspaces();
		n4jsc(options, SUCCESS);

		assertEquals("TestInit\n"
				+ "- package.json\n"
				+ "+ packages\n"
				+ "  + my-project\n"
				+ "    - package.json\n"
				+ "", FileUtils.serializeFileTree(cwd));

		// test
		options = INIT().yes().setWorkingDirectory(cwd.toPath()).workspaces();
		CliCompileResult result = n4jsc(options, INIT_ERROR_WORKING_DIR);

		assertEquals(
				"ERROR-410 (Error: Unsupported working directory):  Current working directory must not contain a package.json file. Note:\n"
						+ "  In case you like to add the n4js property to an existing project, use option --n4js\n"
						+ "  In case you like to add a project to an existing workspace project, use options -w -c",
				result.getStdOut());
	}

	/** test yarn implicit */
	@Test
	public void yarnAddProject() throws Exception {
		// create environment
		N4jscTestOptions options = INIT().setWorkingDirectory(cwd.toPath()).yes().workspaces();
		n4jsc(options, SUCCESS);

		assertEquals("TestInit\n"
				+ "- package.json\n"
				+ "+ packages\n"
				+ "  + my-project\n"
				+ "    - package.json\n"
				+ "", FileUtils.serializeFileTree(cwd));

		// test
		File newProject = new File(cwd, "packages/newProject");
		newProject.mkdirs();
		options = INIT().yes().setWorkingDirectory(newProject.toPath()).workspaces();
		n4jsc(options, SUCCESS);

		assertEquals("TestInit\n"
				+ "- package.json\n"
				+ "+ packages\n"
				+ "  + my-project\n"
				+ "    - package.json\n"
				+ "  + newProject\n"
				+ "    - package.json\n"
				+ "", FileUtils.serializeFileTree(cwd));
	}

	/** Basic extend existing project test. */
	@Test
	public void extendExisting() throws Exception {
		npmRun(cwd.toPath(), "init", "--yes");

		String packagejsonContents = Files.readString(cwd.toPath().resolve(PACKAGE_JSON));
		assertEquals("{\n"
				+ "  \"name\": \"TestInit\",\n"
				+ "  \"version\": \"1.0.0\",\n"
				+ "  \"description\": \"\",\n"
				+ "  \"main\": \"index.js\",\n"
				+ "  \"scripts\": {\n"
				+ "    \"test\": \"echo \\\"Error: no test specified\\\" && exit 1\"\n"
				+ "  },\n"
				+ "  \"keywords\": [],\n"
				+ "  \"author\": \"\",\n"
				+ "  \"license\": \"ISC\"\n"
				+ "}\n"
				+ "", packagejsonContents);

		N4jscTestOptions options = INIT().setWorkingDirectory(cwd.toPath()).yes().n4js();
		n4jsc(options, SUCCESS);

		String packagejsonContentsExt = Files.readString(cwd.toPath().resolve(PACKAGE_JSON));
		assertEquals("{\n"
				+ "  \"name\": \"TestInit\",\n"
				+ "  \"version\": \"1.0.0\",\n"
				+ "  \"description\": \"\",\n"
				+ "  \"main\": \"index.js\",\n"
				+ "  \"scripts\": {\n"
				+ "    \"test\": \"echo \\\"Error: no test specified\\\" && exit 1\"\n"
				+ "  },\n"
				+ "  \"keywords\": [],\n"
				+ "  \"author\": \"\",\n"
				+ "  \"license\": \"ISC\",\n"
				+ "  \"devDependencies\" : {\n"
				+ "    \"n4js-cli\": \"\"\n"
				+ "  },\n"
				+ "  \"dependencies\" : {\n"
				+ "    \"n4js-runtime\": \"\",\n"
				+ "    \"n4js-runtime-es2015\": \"\"\n"
				+ "  },\n"
				+ "  \"n4js\" : {\n"
				+ "    \"projectType\": \"library\",\n"
				+ "    \"output\": \"src-gen\",\n"
				+ "    \"sources\": {\n"
				+ "      \"source\": [\n"
				+ "        \"src\"\n"
				+ "      ]\n"
				+ "    },\n"
				+ "    \"requiredRuntimeLibraries\": [\n"
				+ "      \"n4js-runtime-es2015\"\n"
				+ "    ]\n"
				+ "  }\n"
				+ "}\n"
				+ "", packagejsonContentsExt);
	}

	/** Extend existing project test and modify main module. */
	@Test
	public void extendExistingModifyMain() throws Exception {
		npmRun(cwd.toPath(), "init", "--yes");

		String packagejsonContents = Files.readString(cwd.toPath().resolve(PACKAGE_JSON));
		assertEquals("{\n"
				+ "  \"name\": \"TestInit\",\n"
				+ "  \"version\": \"1.0.0\",\n"
				+ "  \"description\": \"\",\n"
				+ "  \"main\": \"index.js\",\n"
				+ "  \"scripts\": {\n"
				+ "    \"test\": \"echo \\\"Error: no test specified\\\" && exit 1\"\n"
				+ "  },\n"
				+ "  \"keywords\": [],\n"
				+ "  \"author\": \"\",\n"
				+ "  \"license\": \"ISC\"\n"
				+ "}\n"
				+ "", packagejsonContents);

		N4jscTestOptions options = INIT().setWorkingDirectory(cwd.toPath()).answers(",,index.js").n4js();
		n4jsc(options, SUCCESS);

		assertEquals("TestInit\n"
				+ "- package.json\n"
				+ "", FileUtils.serializeFileTree(cwd));

		String packagejsonContentsExt = Files.readString(cwd.toPath().resolve(PACKAGE_JSON));
		assertEquals("{\n"
				+ "  \"name\": \"TestInit\",\n"
				+ "  \"version\": \"1.0.0\",\n"
				+ "  \"description\": \"\",\n"
				+ "  \"main\": \"index.js\",\n"
				+ "  \"scripts\": {\n"
				+ "    \"test\": \"echo \\\"Error: no test specified\\\" && exit 1\"\n"
				+ "  },\n"
				+ "  \"keywords\": [],\n"
				+ "  \"author\": \"\",\n"
				+ "  \"license\": \"ISC\",\n"
				+ "  \"devDependencies\" : {\n"
				+ "    \"n4js-cli\": \"\"\n"
				+ "  },\n"
				+ "  \"dependencies\" : {\n"
				+ "    \"n4js-runtime\": \"\",\n"
				+ "    \"n4js-runtime-es2015\": \"\"\n"
				+ "  },\n"
				+ "  \"n4js\" : {\n"
				+ "    \"projectType\": \"library\",\n"
				+ "    \"output\": \"src-gen\",\n"
				+ "    \"sources\": {\n"
				+ "      \"source\": [\n"
				+ "        \"src\"\n"
				+ "      ]\n"
				+ "    },\n"
				+ "    \"requiredRuntimeLibraries\": [\n"
				+ "      \"n4js-runtime-es2015\"\n"
				+ "    ]\n"
				+ "  }\n"
				+ "}\n"
				+ "", packagejsonContentsExt);
	}
}
