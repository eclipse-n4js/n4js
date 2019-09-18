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
import org.eclipse.n4js.cli.lsp.LspServer;

/**
 * Entry point of n4jsc compiler
 */
public class N4jscMain {

	/** Entry point of n4jsc compiler */
	public static void main(String[] args) {
		final N4jscOptions options = getOptions(args);

		// inform user about data collection
		if (options.getPerformanceReport() != null) {
			System.out.println("Performance Data Collection is enabled.");
		}

		// debug before help, shortcut for check-settings without running
		if (options.isShowSetup()) {
			System.out.println(options.toSettingsString());
		}
		if (!options.isVerbose()) {
			// Reconfigure Logging to be quiet:
			Logger.getRootLogger().removeAllAppenders();
			Logger.getRootLogger().addAppender(new NullAppender());
		}

		try {
			performGoal(options);

		} catch (N4jscException e) {
			System.out.println(e.toUserString());
			System.exit(e.getExitCode());

		} catch (Exception e) {
			System.out.println(e.toString());
			System.exit(-1);
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
					System.out.println(options.toCallString());
				}
			}

			N4jscOptionsValidater.validate(options);

			return options;

		} catch (N4jscException e) {
			System.out.println(e.toUserString());
			System.out.println(N4jscOptions.USAGE);
			System.exit(e.getExitCode());
		}

		return null; // never happens
	}

	private static void performGoal(N4jscOptions options) throws Exception {
		switch (options.getGoal()) {
		case help:
			options.printUsage(System.out);
			return;

		case lsp:
			LspServer.start(options);
			return;

		case clean:
			throw new N4jscException(N4jscExitCode.NOT_IMPLEMENTED);

		case compile:
			throw new N4jscException(N4jscExitCode.NOT_IMPLEMENTED);

		case api:
			throw new N4jscException(N4jscExitCode.NOT_IMPLEMENTED);

		case watch:
			throw new N4jscException(N4jscExitCode.NOT_IMPLEMENTED);
		}
	}

	// private static void doStuff(N4jscOptions options) {
	// // Injection should not be called before making sure the argument parsing successfully finished. Such as
	// // help.
	// initInjection(refProperties());
	//
	// // Register extensions manually
	// headlessExtensionRegistrationHelper.registerExtensions();
	//
	// // compute build set based on user settings (e.g. #buildmode, #srcFiles, #projectlocations)
	// BuildSet buildSet = computeBuildSet();
	//
	// // make sure all projects in the build set are registered with the workspace
	// registerProjects(buildSet);
	//
	// if (clean) {
	// // clean without compiling anything.
	// clean();
	// return SuccessExitStatus.INSTANCE;
	// }
	//
	// final BuildSet targetPlatformBuildSet = computeTargetPlatformBuildSet(buildSet.getAllProjects());
	// // make sure all newly installed dependencies are registered with the workspace
	// registerProjects(targetPlatformBuildSet);
	//
	// // add newly installed external libraries to the discoveredProjects of the buildSet
	// buildSet = BuildSet.combineDiscovered(buildSet, targetPlatformBuildSet);
	//
	// // run and dispatch.
	// doCompileAndTestAndRun(buildSet);
	//
	// Injector injector = new N4JSIdeSetup().createInjectorAndDoEMFRegistration();
	// N4JSLanguageServerImpl languageServer = injector.getInstance(N4JSLanguageServerImpl.class);
	//
	// writePerformanceReport();
	// }

}
