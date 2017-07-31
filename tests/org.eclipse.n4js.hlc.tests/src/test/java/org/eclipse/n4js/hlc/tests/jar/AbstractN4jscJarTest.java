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

import org.junit.After;
import org.junit.Before;
import org.eclipse.n4js.hlc.tests.N4CliHelper;
import org.eclipse.n4js.utils.io.FileCopier;
import org.eclipse.n4js.utils.io.FileDeleter;

/**
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

	/**
	 * Copy a fresh fixture to the workspace area.
	 */
	@Before
	public void setupWorkspace() throws IOException {
		File wsp = new File(TARGET, WSP);
		File fixtureFile = new File(fixture);

		System.out.println("BEFORE: 			  current root " + new File(".").getAbsolutePath());
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
