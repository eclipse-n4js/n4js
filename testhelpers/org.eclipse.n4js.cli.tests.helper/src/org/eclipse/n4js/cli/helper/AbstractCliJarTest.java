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
package org.eclipse.n4js.cli.helper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.eclipse.n4js.cli.N4jscFactory;
import org.eclipse.n4js.cli.N4jscOptions;
import org.eclipse.n4js.cli.runner.helper.ProcessResult;
import org.eclipse.n4js.cli.runner.helper.TestProcessExecuter;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;
import org.eclipse.n4js.utils.io.FileDeleter;
import org.junit.Before;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.inject.Injector;

/**
 * IMPORTANT: All the tests in the classes inheriting from this class require that <code>n4jsc.jar</code> exists in
 * folder <code>.../n4js/tests/org.eclipse.n4js.hlc.tests/target/</code>. Before executing this test, in the console,
 * get an up-to-date <code>n4jsc.jar</code> from somewhere (e.g. download from Jenkins) and manually place it in the
 * folder mentioned above.
 */
public abstract class AbstractCliJarTest extends AbstractCliTest<N4jscOptions> {
	/** Standard Name of the executable jar. */
	static final String N4JSC_JAR = "n4jsc.jar";
	/** Standard target folder-name, the base of maven-compile results. */
	static final String TARGET = "target";
	/** Standard target folder (name+"/"), the base of maven-compile results. */
	static final String TARGET_FOLDER = TARGET + "/";
	/** Sub folder in target folder. */
	static final String WORKSPACE_FOLDER = "wsp";

	/** source of test data, will be copied to TARGET/WSP */
	protected final Path fixture;

	/** Specifies whether before testing, the n4js libraries are copied to the workspace location. */
	protected final Predicate<N4JSProjectName> n4jsLibsPredicate;

	/**
	 * Subclass must provide the fixture, i.e. name of folder containing test data.
	 *
	 * Per default, this will not include the n4js libraries (cf.
	 * {@link N4CliHelper#copyN4jsLibsToLocation(Path, com.google.common.base.Predicate)} in the fixture workspace.
	 */
	protected AbstractCliJarTest(String fixture) {
		this(fixture, false);
	}

	/**
	 * @param fixturePath
	 *            The bundle relative path of the folder that contains the test data.
	 * @param includeN4jsLibraries
	 *            Specified whether the n4js libraries should be copied to the temporary testing workspace location.
	 */
	protected AbstractCliJarTest(String fixturePath, boolean includeN4jsLibraries) {
		this.fixture = Paths.get(fixturePath);
		this.n4jsLibsPredicate = includeN4jsLibraries ? Predicates.alwaysTrue() : Predicates.alwaysFalse();
	}

	/** Copy a fresh fixture to the workspace area. */
	@Before
	public void setupWorkspace() throws IOException {
		Path wsp = Paths.get(TARGET, WORKSPACE_FOLDER);
		Files.createDirectories(wsp); // why?

		N4CliHelper.setupWorkspace(fixture, wsp, n4jsLibsPredicate);
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

	@Override
	public void doN4jsc(N4jscOptions options, CliResult cliResult) throws Exception {
		Injector injector = N4jscFactory.getOrCreateInjector();
		TestProcessExecuter tpExecuter = new TestProcessExecuter(injector);
		File fileArg = options.getSrcFiles().get(0);
		ProcessResult n4jscResult = tpExecuter.n4jscRun(fileArg.toPath(), options);

		cliResult.cause = n4jscResult.getException();
		cliResult.exitCode = n4jscResult.getExitCode();
		cliResult.stdOut = n4jscResult.getStdOut();
		cliResult.errOut = n4jscResult.getErrOut();
	}

}
