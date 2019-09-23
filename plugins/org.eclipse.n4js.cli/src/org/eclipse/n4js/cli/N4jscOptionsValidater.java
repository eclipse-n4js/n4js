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
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import org.eclipse.n4js.cli.N4jscOptions.GoalRequirements;

/**
 *
 */
public class N4jscOptionsValidater {

	/**
	 */
	static public N4jscExitCode validate(N4jscOptions options) throws N4jscException {

		validateGoalDefinitions(options);

		switch (options.getGoal()) {
		case help:
			// User asked for help. Don't bother him.
			break;

		case lsp:
			validateGoalLspOptions(options);
			break;

		case clean:
			validateGoalCleanOptions(options);
			break;

		case compile:
			validateGoalCompileOptions(options);
			break;

		case api:
			break;

		case watch:
			break;
		}

		return N4jscExitCode.SUCCESS;
	}

	private static void validateGoalLspOptions(N4jscOptions options) throws N4jscException {
		if (options.getPort() < 1 || options.getPort() > 65535) {
			String msg = "Port is out of range: " + options.getPort();
			throw new N4jscException(N4jscExitCode.OPTION_INVALID, msg);
		}
	}

	private static void validateGoalCleanOptions(N4jscOptions options) throws N4jscException {
		validateFilesAndDirectories(options);
	}

	private static void validateGoalCompileOptions(N4jscOptions options) throws N4jscException {
		validateFilesAndDirectories(options);

		if (options.getTestCatalog() != null) {
			validateTestCatalogFile(options);
		}
	}

	private static void validateGoalDefinitions(N4jscOptions options) throws N4jscException {
		Map<String, GoalRequirements> nameFieldMap = options.getOptionNameToGoalRequirementMap();

		for (N4JSCmdLineParser.ParsedOption po : options.getDefinedOptions()) {
			String name = po.optionDef.name();
			if (nameFieldMap.containsKey(name)) {
				GoalRequirements goalRequirements = nameFieldMap.get(name);
				List<N4jscGoal> goals = Arrays.asList(goalRequirements.goals());
				boolean optionGoalRequirementMet = goals.contains(options.getGoal());
				if (!optionGoalRequirementMet) {
					List<String> goalNames = goals.stream().map(g -> g.name()).collect(Collectors.toList());
					String msg = "Given option " + name + " requires goal(s) " + String.join(", ", goalNames) //
							+ ", but goal " + options.getGoal() + " was given.";
					throw new N4jscException(N4jscExitCode.OPTION_INVALID, msg);
				}
			}
		}
	}

	/** Make sure the srcFiles are valid */
	private static void validateFilesAndDirectories(N4jscOptions options) throws N4jscException {
		if (options.getSrcFiles().isEmpty()) {
			String msg = "n4js file(s) or project(s) missing";
			throw new N4jscException(N4jscExitCode.ARGUMENT_FILES_INVALID, msg);
		}

		StringJoiner notExisting = new StringJoiner(",");
		StringJoiner neitherFileNorDir = new StringJoiner(",");
		for (File srcFile : options.getSrcFiles()) {
			if (!srcFile.exists()) {
				notExisting.add(srcFile.toString());
			} else if (!srcFile.isFile() && !srcFile.isDirectory()) {
				neitherFileNorDir.add(srcFile.toString());
			}
		}
		if (!notExisting.toString().isEmpty()) {
			String msg = "file(s) do not exist: " + notExisting.toString();
			throw new N4jscException(N4jscExitCode.ARGUMENT_FILES_INVALID, msg);
		}
		if (!neitherFileNorDir.toString().isEmpty()) {
			String msg = "file(s) are neither a file nor a directory: " + neitherFileNorDir.toString();
			throw new N4jscException(N4jscExitCode.ARGUMENT_FILES_INVALID, msg);
		}
	}

	private static void validateTestCatalogFile(N4jscOptions options) throws N4jscException {
		if (options.getTestCatalog().exists()) {
			if (options.getTestCatalog().isDirectory()) {
				final String msg = "The location of the test catalog file points to a directory and not to a file. "
						+ options.getTestCatalog();
				throw new N4jscException(N4jscExitCode.TEST_CATALOG_ASSEMBLATION_ERROR, msg);
			}

			if (!options.getTestCatalog().delete()) {
				final String msg = "Error while deleting existing test catalog file. " + options.getTestCatalog();
				throw new N4jscException(N4jscExitCode.TEST_CATALOG_ASSEMBLATION_ERROR, msg);
			}
		}

		try {
			if (!options.getTestCatalog().createNewFile()) {
				final String msg = "Error while creating test catalog file at: " + options.getTestCatalog();
				throw new N4jscException(N4jscExitCode.TEST_CATALOG_ASSEMBLATION_ERROR, msg);
			}
		} catch (final IOException e) {
			String msg = "Error while creating test catalog file. " + e.getMessage();
			throw new N4jscException(N4jscExitCode.TEST_CATALOG_ASSEMBLATION_ERROR, msg);
		}

		if (!options.getTestCatalog().exists() || !options.getTestCatalog().canWrite()) {
			final String msg = "Cannot access test catalog file at: " + options.getTestCatalog();
			throw new N4jscException(N4jscExitCode.TEST_CATALOG_ASSEMBLATION_ERROR, msg);
		}
	}

}
