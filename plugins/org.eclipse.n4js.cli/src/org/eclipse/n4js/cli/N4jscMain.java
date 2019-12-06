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

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.varia.NullAppender;
import org.eclipse.n4js.cli.compiler.N4jscCompiler;
import org.eclipse.n4js.smith.CollectedDataAccess;
import org.eclipse.n4js.smith.DataCollectorCSVExporter;
import org.eclipse.n4js.smith.Measurement;
import org.eclipse.n4js.smith.N4JSDataCollectors;
import org.eclipse.n4js.utils.N4JSLanguageUtils;

import com.google.common.base.Stopwatch;

/**
 * Entry point of n4jsc compiler
 */
public class N4jscMain {
	private static final Logger LOG = LogManager.getLogger(N4jscCompiler.class);

	/** Entry point of n4jsc compiler */
	public static void main(String[] args) {

		Stopwatch sw = Stopwatch.createStarted();
		final N4jscOptions options = getOptions(args);

		// inform user about data collection
		if (options.isDefinedPerformanceOption()) {
			N4jscConsole.println("Performance Data Collection is enabled.");
			CollectedDataAccess.setPaused(false);
		}

		// debug before help, shortcut for check-settings without running
		if (options.isShowSetup()) {
			N4jscConsole.println(options.toSettingsString());
		}

		if (!options.isVerbose()) {
			// Reconfigure Logging to be quiet:
			Logger.getRootLogger().removeAllAppenders();
			Logger.getRootLogger().addAppender(new NullAppender());
		}

		try {
			try (Measurement m = N4JSDataCollectors.dcCli.getMeasurement(N4JSDataCollectors.N4JS_CLI_COLLECTOR_NAME)) {
				performGoal(options);
			} catch (N4jscException e) {
				throw e;
			} catch (Exception e) {
				throw new N4jscException(N4jscExitCode.ERROR_UNEXPECTED, e);
			}

			writePerformanceReportIfRequested(options);
			System.out.println(sw);
			System.exit(N4jscExitCode.SUCCESS.getExitCodeValue());

		} catch (N4jscException e) {
			N4jscConsole.println(e.toUserString());
			if (options.isVerbose()) {
				e.printStackTrace();
			}
			System.out.println(sw);
			System.exit(e.getExitCode());
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
			N4jscConsole.println(N4jscOptions.USAGE);
			System.exit(e.getExitCode());
		}

		return null; // never happens
	}

	private static void performGoal(N4jscOptions options) throws Exception {
		N4jscBackend backend = N4jscFactory.createBackend();

		switch (options.getGoal()) {
		case help:
			options.printUsage(N4jscConsole.getPrintStream());
			return;

		case version:
			N4jscConsole.println(N4JSLanguageUtils.getLanguageVersion());
			return;

		case lsp:
			backend.goalLsp(options);
			return;

		case clean:
			backend.goalClean(options);
			return;

		case compile:
			backend.goalCompile(options);
			return;

		case api:
			backend.goalApi(options);
			return;

		case watch:
			backend.goalWatch(options);
			return;
		}
	}

	private static void writePerformanceReportIfRequested(N4jscOptions options) throws N4jscException {
		if (options.isDefinedPerformanceOption()) {
			String performanceKey = options.getPerformanceKey();
			File performanceReportFile = options.getPerformanceReport();

			String absFileString = performanceReportFile.toPath().toAbsolutePath().toString();

			String verb = performanceReportFile.exists() ? "Replacing " : "Writing ";
			N4jscConsole.println(verb + "performance report: " + absFileString);

			try {
				DataCollectorCSVExporter.toFile(performanceReportFile, performanceKey);
			} catch (IOException e) {
				throw new N4jscException(N4jscExitCode.PERFORMANCE_REPORT_ERROR);
			}
		}
	}
}
