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

import org.apache.log4j.Logger;
import org.apache.log4j.varia.NullAppender;
import org.eclipse.n4js.smith.Measurement;
import org.eclipse.n4js.smith.N4JSDataCollectors;
import org.eclipse.n4js.utils.N4JSLanguageUtils;

/**
 * Entry point of n4jsc compiler
 */
public class N4jscMain {

	/** Entry point of n4jsc compiler */
	public static void main(String[] args) {

		final N4jscOptions options = getOptions(args);

		// inform user about data collection
		if (options.getPerformanceReport() != null) {
			N4jscConsole.println("Performance Data Collection is enabled.");
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
		} catch (N4jscException e) {
			N4jscConsole.println(e.toUserString());
			System.exit(e.getExitCode());
		}

		System.exit(N4jscExitCode.SUCCESS.getExitCodeValue());
	}

	private static N4jscOptions getOptions(String[] args) {
		final N4jscOptions options = new N4jscOptions();

		try {
			try {
				options.read(args);
			} finally {
				if (options.isVerbose()) {
					N4jscConsole.println(options.toCallString());
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

}
