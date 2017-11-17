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
package org.eclipse.n4js.hlc.tests.jar;

import static org.eclipse.n4js.hlc.tests.jar.HlcTestingConstants.N4JSC_JAR;
import static org.eclipse.n4js.hlc.tests.jar.HlcTestingConstants.TARGET;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import org.eclipse.n4js.hlc.tests.N4CliHelper;
import org.eclipse.n4js.utils.io.FileCopier;
import org.eclipse.n4js.utils.io.FileDeleter;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

/**
 * IMPORTANT: All the tests in the classes inherited by this class require that n4jsc.jar exist. Before executing this
 * test, in the console, change your current to the folder {@code git/n4js/tools/scripts/}. Then inside that folder,
 * execute the {@code mvn-cp-n4jsjar.sh}.
 * <p>
 * This script executes Maven locally to create n4jsc.jar and copy the n4jsc.jar to the folder
 * git/n4js/tests/org.eclipse.n4js.hlc.tests/target required by these tests.
 */

public abstract class AbstractN4jscJarTest {

	// Running directory will be ${TARGET}/${WSP}

	/** Sub folder in target folder. */
	protected static final String WSP = "wsp";

	/** source of test data, will be copied to TARGET/WSP */
	protected final String fixture;

	/**
	 * Output-log-file of external Process. The file will be assigned given on the current test-method by calling
	 * {@link #logFile()} as first statement in each test-method.
	 */
	protected File outputLogFile = null;

	/**
	 * Subclass must provide the fixture, i.e. name of folder containing test data.
	 */
	protected AbstractN4jscJarTest(String fixture) {
		this.fixture = fixture;
	}

	/** Description object of the currently running test. */
	protected Description description;
	/** Logs test name that is executed. */
	@Rule
	public TestRule watcher = new TestWatcher() {
		@Override
		protected void starting(Description desc) {
			description = desc;
			System.out.println("Started of: " + desc.getClassName() + "." + desc.getMethodName());
		}

		@Override
		protected void finished(Description desc) {
			description = null;
			System.out.println("Finished of: " + desc.getClassName() + "." + desc.getMethodName());
		}

	};

	/**
	 * Copy a fresh fixture to the workspace area.
	 */
	@Before
	public void setupWorkspace() throws IOException {
		// Create target folder if not exists
		File targetFolder = new File(TARGET);
		if (!targetFolder.exists()) {
			System.out.println(TARGET + " folder does not exist. Creating one.");
			targetFolder.mkdirs();
		}

		File wsp = new File(TARGET, WSP);
		File fixtureFile = new File(fixture);

		System.out.println("BEFORE: 	current root " + new File(".").getAbsolutePath());
		System.out.println("BEFORE: current workspace would be " + wsp.getAbsolutePath());

		// clean
		// Files.deleteIfExists(wsp.toPath());
		FileDeleter.delete(wsp.toPath());
		// copy
		FileCopier.copy(fixtureFile.toPath(), wsp.toPath());

	}

	/**
	 * Append external process output to the junit std-out content.
	 */
	@After
	public void appendExternalOutpouToStdout() {
		N4CliHelper.appendExternalOutputToStdout(outputLogFile);
	}

	/**
	 * Deletes the project with the given name (its folder and all contained files and sub folders) from the temporary
	 * workspace directory. This can be used to change the test data by removing selected projects at the beginning of a
	 * test method.
	 */
	protected void deleteProject(String projectId) throws IOException {
		File wsp = new File(TARGET, WSP);
		File project = new File(wsp, projectId);
		FileDeleter.delete(project.toPath());
	}

	/**
	 * Create & start java-Process calling n4jsc.jar jar with args from {@value #TARGET}-folder.
	 *
	 * @param args
	 *            arguments to pass after jar - call
	 *
	 * @return running process
	 * @throws IOException
	 *             if errored.
	 */
	protected Process createAndStartProcess(String... args) throws IOException {
		ArrayList<String> args2 = new ArrayList<>();
		// Collections.addAll(args2, "java", "-jar", TARGET + "/" + N4JSC_JAR);
		Collections.addAll(args2, "java", "-jar", N4JSC_JAR);
		Collections.addAll(args2, args);
		return N4CliHelper.createAndStartProcessIntern(outputLogFile, TARGET, args2.toArray(new String[args2.size()]));
	}

	/**
	 * Should be called as first line in test-mehtods.
	 *
	 * Creates an log-file in the {@link #TARGET}-folder based on the callers Class/Methodname. something like
	 * "target/org.eclipse.n4js.hlc.test.N4jscSingleFileCompileIT.testHelp.log"
	 */
	protected void logFile() {
		StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[2];
		String name = stackTraceElement.getClassName() + "." + stackTraceElement.getMethodName() + ".log";
		outputLogFile = new File(TARGET, name);
	}
}
