/**
 * Copyright (c) 2017 NumberFour AG.
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
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Map;

import org.eclipse.n4js.cli.N4jscMain;
import org.eclipse.n4js.cli.N4jscOptions;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;
import org.eclipse.n4js.utils.io.FileUtils;
import org.junit.Before;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

/**
 * Subclass this class for test cases that compile n4js code and run js code
 */
public class AbstractCliCompileTest extends AbstractCliTest<N4jscOptions> {
	/** name of workspace sub-folder (inside target folder) */
	private static final String WSP = "wsp";
	/** see {@link N4CliHelper#PACKAGES} */
	protected static final String PACKAGES = N4CliHelper.PACKAGES;
	/** name of folder containing the test resources */
	protected static final String FIXTURE = "probands";
	/** name of test data set for launching testers from the command line */
	protected static final String TEST_DATA_SET__TESTERS = "testers";
	/** name of test data set for npm scopes */
	protected static final String TEST_DATA_SET__NPM_SCOPES = "npmScopes";

	/** Specifies how n4jsc is executed */
	static public enum N4jscVariant {
		/** N4jsc is called using a function call to {@link N4jscMain#main(String[])} */
		inprocess,
		/** N4jsc is called using the {@code n4jsc.jar} and a Java {@link ProcessBuilder} */
		exprocess
	}

	final private N4jscVariant variant;
	private CliTools cliTools;

	/** Constructor */
	public AbstractCliCompileTest() {
		this(N4jscVariant.inprocess);
	}

	/** Constructor */
	public AbstractCliCompileTest(N4jscVariant variant) {
		this.variant = variant;
	}

	/** Initializes {@link #cliTools} */
	@Before
	final public void setupTestProcessExecuter() {
		cliTools = new CliTools();
	}

	@Override
	public CliCompileResult createResult() {
		CliCompileResult result = null;

		switch (variant) {
		case inprocess:
			result = new CliCompileResult();
			break;
		case exprocess:
			result = new CliCompileProcessResult();
			break;
		default:
			throw new IllegalStateException();
		}

		return result;
	}

	@Override
	public void doN4jsc(N4jscOptions options, boolean ignoreFailure, boolean removeUsage, CliCompileResult result) {
		cliTools.setIgnoreFailure(ignoreFailure);
		try {
			switch (variant) {
			case inprocess:
				cliTools.callN4jscInprocess(options, removeUsage, result);
				return;
			case exprocess:
				cliTools.callN4jscExprocess(options, removeUsage, (CliCompileProcessResult) result);
				return;
			default:
				throw new IllegalStateException();
			}
		} finally {
			cliTools.setIgnoreFailure(false);
		}
	}

	/**
	 * Sets the given name and value pair to the environment.
	 * <p>
	 * <b>Note:</b> Only active when used in {@link N4jscVariant#exprocess }
	 */
	public void setEnvironmentVariable(String name, String value) {
		cliTools.setEnvironmentVariable(name, value);
	}

	/** see {@link TestProcessExecuter#runNodejs(Path, Map, Path, String[])} */
	public ProcessResult runNodejs(Path workingDir, Path runFile, String... options) {
		return cliTools.runNodejs(workingDir, runFile, options);
	}

	/** see {@link TestProcessExecuter#npmRun(Path, Map, String[])} */
	public ProcessResult npmInstall(Path workingDir, String... options) {
		return cliTools.npmInstall(workingDir, options);
	}

	/** see {@link TestProcessExecuter#npmRun(Path, Map, String[])} */
	public ProcessResult npmList(Path workingDir, String... options) {
		return cliTools.npmList(workingDir, options);
	}

	/** see {@link TestProcessExecuter#yarnRun(Path, Map, String[])} */
	public ProcessResult yarnInstall(Path workingDir, String... options) {
		return cliTools.yarnInstall(workingDir, options);
	}

	/**
	 * Copy a fresh fixture to the workspace area. Deleting old leftovers from former tests.
	 *
	 * @param createYarnWorkspace
	 *            see {@link N4CliHelper#setupWorkspace(Path, Path, Predicate, boolean)}.
	 *
	 * @returns file indicating the relative path to the copied data set
	 */
	protected static File setupWorkspace(String testDataSet, boolean createYarnWorkspace) throws IOException {
		return setupWorkspace(testDataSet, Predicates.alwaysFalse(), createYarnWorkspace);
	}

	/**
	 * Copy a fresh fixture to the workspace area. Deleting old leftovers from former tests.
	 *
	 * @param createYarnWorkspace
	 *            see {@link N4CliHelper#setupWorkspace(Path, Path, Predicate, boolean)}.
	 *
	 * @returns file indicating the relative path to the copied data set
	 */
	protected static File setupWorkspace(String testDataRoot, String testDataSet, boolean createYarnWorkspace)
			throws IOException {
		return setupWorkspace(testDataRoot, testDataSet, Predicates.alwaysFalse(), createYarnWorkspace);
	}

	/**
	 * Same as {@link #setupWorkspace(String, Predicate, boolean)}, but accepts one or more names of libraries to
	 * install instead of a predicate.
	 */
	protected static File setupWorkspace(String testDataSet, boolean createYarnWorkspace, N4JSProjectName... libNames)
			throws IOException {
		return setupWorkspace(testDataSet, libName -> Arrays.asList(libNames).contains(libName), createYarnWorkspace);
	}

	/**
	 * Copy a fresh fixture to the workspace area. Deleting old leftovers from former tests. Also includes all N4JS
	 * libraries from the {@code n4js} Git repository which name provides {@code true} value for the given predicate.
	 *
	 * @param createYarnWorkspace
	 *            see {@link N4CliHelper#setupWorkspace(Path, Path, Predicate, boolean)}.
	 *
	 * @returns file indicating the relative path to the copied data set
	 */
	protected static File setupWorkspace(String testDataSet, Predicate<N4JSProjectName> n4jsLibrariesPredicate,
			boolean createYarnWorkspace) throws IOException {
		return setupWorkspace(FIXTURE, testDataSet, n4jsLibrariesPredicate, createYarnWorkspace);
	}

	private static File setupWorkspace(String testDataRoot, String testDataSet,
			Predicate<N4JSProjectName> n4jsLibrariesPredicate, boolean createYarnWorkspace) throws IOException {
		Path fixture = new File(testDataRoot, testDataSet).toPath();
		Path root = FileUtils.createTempDirectory(testDataRoot + "_" + testDataSet + "_");
		root = root.toFile().getCanonicalFile().toPath();
		Path wsp = root.resolve(WSP);
		N4CliHelper.setupWorkspace(fixture, wsp, n4jsLibrariesPredicate, createYarnWorkspace);
		return wsp.toFile();
	}
}
