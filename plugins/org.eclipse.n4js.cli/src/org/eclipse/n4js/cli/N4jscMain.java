/**
 * Copyright (c) 2019 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.cli;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.eclipse.n4js.smith.CollectedDataAccess;
import org.eclipse.n4js.smith.DataCollectorCSVExporter;
import org.eclipse.n4js.smith.DataCollectorUtils;
import org.eclipse.n4js.utils.N4JSLanguageUtils;
import org.eclipse.n4js.utils.io.FileUtils;

/**
 * Entry point of n4jsc compiler
 */
public class N4jscMain {
	private static final Logger LOG = LogManager.getLogger(N4jscMain.class);

	/** Entry point of n4jsc compiler */
	public static void main(String[] args) {
		try {
			main(null, args);
		} catch (SystemExitException e) {
			System.exit(e.status);
		}
	}

	/** Entry point of n4jsc compiler from tests to modify the working directory */
	public static void main(Path workDir, String[] args) {
		final N4jscOptions options = getOptions(args);
		if (workDir != null) {
			options.workingDir = workDir;
		}

		if (options.isVerbose()) {
			Logger.getRootLogger().setLevel(Level.DEBUG);
		} else {
			Logger.getRootLogger().setLevel(Level.ERROR);
		}
		LOG.info("Starting n4jsc (language version " + N4JSLanguageUtils.getLanguageVersion()
				+ ", commit " + N4JSLanguageUtils.getLanguageCommit() + ")");
		LOG.info("Log level: " + Logger.getRootLogger().getLevel());

		// inform user about data collection
		if (options.isDefinedPerformanceOption()) {
			N4jscConsole.println("Performance Data Collection is enabled.");
			CollectedDataAccess.setPaused(false);
		}

		// debug before help, shortcut for check-settings without running
		if (options.isShowSetup()) {
			N4jscConsole.println(options.toSettingsString());
		}

		try {
			N4jscExitState exitState;
			try {
				exitState = performGoal(options);
			} catch (N4jscException e) {
				throw e;
			} catch (Exception e) {
				throw new N4jscException(N4jscExitCode.ERROR_UNEXPECTED, e);
			}

			writePerformanceReportIfRequested(options);

			if (options.isVerbose() || !exitState.isSuppressUserMessage()) {
				N4jscConsole.println(exitState.toUserString());
			}
			throw new SystemExitException(exitState.getExitCode().getExitCodeValue());

		} catch (N4jscException e) {
			N4jscConsole.println(e.toUserString());
			if (options.isVerbose()) {
				e.printStackTrace();
			}
			throw new SystemExitException(e.getExitCode());
		}
	}

	private static N4jscOptions getOptions(String[] args) {
		final N4jscOptions options = new N4jscOptions();

		try {
			try {
				options.read(args);

			} finally {
				if (options.isVerbose()) { // check #isVerbose() since log4j is still active
					LOG.info("Called with: " + options.toCallString());
				}
			}

			N4jscOptionsValidater.validate(options);

			return options;

		} catch (N4jscException e) {
			N4jscConsole.println(e.toUserString());
			throw new SystemExitException(e.getExitCode());
		}
	}

	/** @return a {@link N4jscExitState} for graceful termination of n4jsc */
	@SuppressWarnings("resource")
	private static N4jscExitState performGoal(N4jscOptions options) throws Exception {
		N4jscBackend backend = N4jscFactory.createBackend();

		if (options.isVersion()) {
			N4jscConsole.println(N4JSLanguageUtils.getLanguageVersion()
					+ " (commit " + N4JSLanguageUtils.getLanguageCommit() + ")");
		}

		if (options.isHelp()) {
			options.printUsage(N4jscConsole.getPrintStream());
		}

		// Options --version and --help (which are also goals) behave like a goal wrt. exiting after the version
		// and or help was shown. Note that given as an option on the command line, the actual goal can still be
		// something else, e.g. 'n4jsc compile --version' will show the version and then exit.
		if (options.isHelp() || options.isVersion()) {
			return N4jscExitState.SUCCESS;
		}

		switch (options.getGoal()) {
		case version:
			// version printed already above
			break;

		case lsp:
			return backend.goalLsp(options);

		case clean:
			return backend.goalClean(options);

		case compile:
		case compileImplicit:
			return backend.goalCompile(options);

		case api:
			return backend.goalApi(options);

		case watch:
			return backend.goalWatch(options);

		case init:
			return backend.goalInit(options);

		case help:
			// help printed already above;
			break;

		case setversions:
			return backend.goalSetVersions(options);
		}

		throw new N4jscException(N4jscExitCode.ARGUMENT_GOAL_INVALID);
	}

	private static void writePerformanceReportIfRequested(N4jscOptions options) throws N4jscException {
		if (options.isDefinedPerformanceOption()) {
			String performanceKey = options.getPerformanceKey();
			File performanceReportFile = options.getPerformanceReport();

			performanceReportFile = FileUtils.appendTimeStampToFileName(performanceReportFile.toPath()).toFile();

			String absFileString = performanceReportFile.toPath().toAbsolutePath().toString();
			N4jscConsole.println("Writing performance report: " + absFileString);

			try {
				if (absFileString.endsWith(".csv")) {
					if ("*".equals(performanceKey.trim())) {
						throw new UnsupportedOperationException(); // a validation makes sure we won't reach this line
					}
					DataCollectorCSVExporter.toFile(performanceReportFile, performanceKey);
				} else {
					String indent = "    ";
					String dataStr = "*".equals(performanceKey)
							? DataCollectorUtils.allDataToString(indent)
							: DataCollectorUtils.dataToString(performanceKey, indent);
					Files.writeString(performanceReportFile.toPath(), dataStr + System.lineSeparator());
				}
			} catch (IOException e) {
				throw new N4jscException(N4jscExitCode.PERFORMANCE_REPORT_ERROR, e);
			}
		}
	}
}
