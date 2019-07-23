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
package org.eclipse.n4js.hlc.integrationtests;

import static org.eclipse.n4js.hlc.integrationtests.HlcTestingConstants.N4JSC_JAR;
import static org.eclipse.n4js.hlc.integrationtests.HlcTestingConstants.TARGET;
import static org.eclipse.n4js.hlc.integrationtests.HlcTestingConstants.WORKSPACE_FOLDER;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.n4js.projectModel.names.N4JSProjectName;
import org.eclipse.n4js.test.helper.hlc.N4CliHelper;
import org.eclipse.n4js.utils.io.FileDeleter;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

/**
 * IMPORTANT: All the tests in the classes inheriting from this class require that <code>n4jsc.jar</code> exists in
 * folder <code>.../n4js/tests/org.eclipse.n4js.hlc.tests/target/</code>. Before executing this test, in the console,
 * you must do one of the following:
 * <ul>
 * <li>get an up-to-date <code>n4jsc.jar</code> from somewhere (e.g. download from Jenkins) and manually place it in the
 * folder mentioned above,<br>
 * OR
 * <li>change your current working directory to the folder {@code .../n4js/tools/scripts/}. Then inside that folder,
 * execute the {@code mvn-cp-n4jsjar.sh}. This script executes Maven locally to create <code>n4jsc.jar</code> and copy
 * it to the folder required by these tests.
 * </ul>
 */
public abstract class AbstractN4jscJarTest {

	/** see {@link N4CliHelper#PACKAGES} */
	protected static final String PACKAGES = N4CliHelper.PACKAGES;

	// Running directory will be ${TARGET}/${WSP}

	/** source of test data, will be copied to TARGET/WSP */
	protected final String fixture;

	/** Specifies whether before testing, the n4js libraries are copied to the workspace location. */
	protected final boolean includeN4jsLibraries;

	/**
	 * Output-log-file of external Process. The file will be assigned given on the current test-method by calling
	 * {@link #logFile()} as first statement in each test-method.
	 */
	protected File outputLogFile = null;

	/**
	 * Subclass must provide the fixture, i.e. name of folder containing test data.
	 *
	 * Per default, this will not include the n4js libraries (cf.
	 * {@link N4CliHelper#copyN4jsLibsToLocation(Path, com.google.common.base.Predicate)} in the fixture workspace.
	 */
	protected AbstractN4jscJarTest(String fixture) {
		this(fixture, false);
	}

	/**
	 * @param fixturePath
	 *            The bundle relative path of the folder that contains the test data.
	 * @param includeN4jsLibraries
	 *            Specified whether the n4js libraries should be copied to the temporary testing workspace location.
	 */
	protected AbstractN4jscJarTest(String fixturePath, boolean includeN4jsLibraries) {
		this.fixture = fixturePath;
		this.includeN4jsLibraries = includeN4jsLibraries;
	}

	/** Description object of the currently running test. */
	protected Description description;
	/** Logs test name that is executed. */
	@Rule
	public TestRule watcher = new TestWatcher() {
		@Override
		protected void starting(Description desc) {
			description = desc;
			System.out.println("Started: " + desc.getClassName() + "." + desc.getMethodName());
		}

		@Override
		protected void finished(Description desc) {
			description = null;
			System.out.println("Finished: " + desc.getClassName() + "." + desc.getMethodName());
		}

	};

	/**
	 * Copy a fresh fixture to the workspace area.
	 */
	@Before
	public void setupWorkspace() throws IOException {
		Path fixturePath = Paths.get(fixture);
		List<Path> fixtureSubFolders = Files.list(fixturePath).filter(p -> Files.isDirectory(p))
				.collect(Collectors.toList());
		boolean needYarnWorkspace = fixtureSubFolders.size() > 1;

		Path wsp = Paths.get(TARGET, WORKSPACE_FOLDER);
		Files.createDirectories(wsp);

		Predicate<N4JSProjectName> n4jsLibsPredicate = includeN4jsLibraries ? Predicates.alwaysTrue()
				: Predicates.alwaysFalse();

		N4CliHelper.setupWorkspace(fixturePath, wsp, n4jsLibsPredicate, needYarnWorkspace);
	}

	/**
	 * Append external process output to the junit std-out content.
	 */
	@After
	public void appendExternalOutputToStdout() {
		N4CliHelper.appendExternalOutputToStdout(outputLogFile);
	}

	/**
	 * Deletes the project with the given name (its folder and all contained files and sub folders) from the temporary
	 * workspace directory. This can be used to change the test data by removing selected projects at the beginning of a
	 * test method.
	 */
	protected void deleteProject(String projectName) throws IOException {
		File wsp = new File(TARGET, WORKSPACE_FOLDER);
		File project = new File(wsp, projectName);
		FileDeleter.delete(project.toPath());
	}

	/**
	 * See {@link #createAndStartProcess(Map, String...)} but uses an empty map as set of user-defined system
	 * environment variables.
	 */
	protected Process createAndStartProcess(String... args) throws IOException {
		return this.createAndStartProcess(Collections.emptyMap(), args);
	}

	/**
	 * Create & start java-Process calling n4jsc.jar jar with args from {@value #TARGET}-folder.
	 *
	 * @param environment
	 *            a map of system environment variables to set in the execution environment
	 * @param args
	 *            arguments to pass after jar - call
	 *
	 * @return running process
	 * @throws IOException
	 *             if errored.
	 */
	protected Process createAndStartProcess(Map<String, String> environment, String... args) throws IOException {
		ArrayList<String> args2 = new ArrayList<>();
		// Collections.addAll(args2, "java", "-jar", TARGET + "/" + N4JSC_JAR);
		Collections.addAll(args2, "java", "-jar", N4JSC_JAR);
		Collections.addAll(args2, args);
		return N4CliHelper.createAndStartProcessIntern(outputLogFile, TARGET, environment,
				args2.toArray(new String[args2.size()]));
	}

	/**
	 * Should be called as first line in test-mehtods.
	 *
	 * Creates an log-file in the {@link #TARGET}-folder based on the callers class/method name. something like
	 * "target/org.eclipse.n4js.hlc.test.N4jscSingleFileCompileIT.testHelp.log"
	 */
	protected void logFile() {
		StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[2];
		String name = stackTraceElement.getClassName() + "." + stackTraceElement.getMethodName() + ".log";
		outputLogFile = new File(TARGET, name);
	}
}
