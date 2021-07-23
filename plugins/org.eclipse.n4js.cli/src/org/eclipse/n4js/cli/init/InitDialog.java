/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.cli.init;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.cli.N4jscConsole;
import org.eclipse.n4js.cli.N4jscException;
import org.eclipse.n4js.cli.N4jscExitCode;
import org.eclipse.n4js.cli.N4jscOptions;
import org.eclipse.n4js.cli.N4jscOptions.InitOptions;
import org.eclipse.n4js.cli.init.InitResources.FileHelloWorld;
import org.eclipse.n4js.cli.init.InitResources.FileHelloWorldTest;
import org.eclipse.n4js.cli.init.InitResources.IndexFile;
import org.eclipse.n4js.cli.init.InitResources.PackageJsonContents;
import org.eclipse.n4js.cli.init.InitResources.YarnPackageJsonContents;
import org.eclipse.n4js.cli.init.N4jscInit.WorkingDirState;
import org.eclipse.xtext.xbase.lib.Pair;

import com.google.common.base.Objects;
import com.google.common.base.Strings;

/**
 * This class implements the dialog with the user and similar features.
 */
public class InitDialog {

	/**
	 * Indices defined in help, see {@link InitOptions#answers}
	 */
	static class UserAnswers {
		String nameProject; // 0
		String version; // 1
		String main_module; // 2
		String author; // 3
		String license; // 4
		String description; // 5
		boolean addHelloWorld; // 6
		boolean addHelloWorldTest; // 7
		String nameYarnProject; // 8

		UserAnswers() {
		}

		UserAnswers(String userAnswers) {
			String[] answers = userAnswers.split("(?<=[^\\\\]|^),");
			int idx = 0;
			this.nameProject = answers.length > idx && !answers[idx].isBlank() ? answers[idx] : null;
			idx++;
			this.version = answers.length > idx && !answers[idx].isBlank() ? answers[idx] : null;
			idx++;
			this.main_module = answers.length > idx && !answers[idx].isBlank() ? answers[idx] : null;
			idx++;
			this.author = answers.length > idx && !answers[idx].isBlank() ? answers[idx] : null;
			idx++;
			this.license = answers.length > idx && !answers[idx].isBlank() ? answers[idx] : null;
			idx++;
			this.description = answers.length > idx && !answers[idx].isBlank() ? answers[idx] : null;
			idx++;
			this.addHelloWorld = answers.length > idx && isYes(answers[idx]);
			idx++;
			this.addHelloWorldTest = answers.length > idx && isYes(answers[idx]);
			idx++;
			this.nameYarnProject = answers.length > idx && !answers[idx].isBlank() ? answers[idx] : null;
		}
	}

	/**
	 * Given options such as {@code --yes} this function computes a configuration for the {@code init} goal. This either
	 * falls back to a default configuration, uses a given set of answers passed by option {@code answers} or performs a
	 * dialog with the user to retrieve all answers.
	 *
	 * @return a complete configuration of the new project
	 */
	public static InitConfiguration getInitConfiguration(N4jscOptions options, WorkingDirState workingDirState)
			throws N4jscException {

		InitConfiguration config = getDefaultConfiguration(options, workingDirState);
		if (options.isYes()) {
			// nothing to do
		} else if (options.getAnswers() != null) {
			UserAnswers answers = new UserAnswers(options.getAnswers());
			customizeConfiguration(options, config, answers);
		} else {
			UserAnswers answers = inputUserAnswers(options, config, workingDirState);
			customizeConfiguration(options, config, answers);

			N4jscConsole.print("Create the following project? (yes)" + System.lineSeparator() + config.toString());
			String userInput = N4jscConsole.readLine();
			if (!isYes(userInput)) {
				throw new N4jscException(N4jscExitCode.USER_CANCELLED, "Goal init aborted.");
			}
		}
		return config;
	}

	static boolean isYes(String input) {
		return "y".equals(input) || "yes".equals(input);
	}

	static InitConfiguration getDefaultConfiguration(N4jscOptions options, WorkingDirState workingDirState)
			throws N4jscException {

		InitConfiguration config = new InitConfiguration();
		if (options.isWorkspaces()) {
			if (workingDirState == WorkingDirState.InEmptyFolder) {
				config.yarnPackageJson = YarnPackageJsonContents.defaults();
			} else {
				config.yarnPackageJson = YarnPackageJsonContents.read(null);
			}
		}

		if (options.isN4JS()) {
			config.packageJson.userModifications.add("n4js");
			config.packageJson.userModifications.add("dependencies");
			config.packageJson.userModifications.add("devDependencies");

			config.packageJson = PackageJsonContents.read(options);
		} else {
			config.packageJson = PackageJsonContents.defaults(options);
		}

		return config;
	}

	static UserAnswers inputUserAnswers(N4jscOptions options, InitConfiguration config,
			WorkingDirState workingDirState) {

		UserAnswers answers = new UserAnswers();

		String userInput = null;

		if (options.isCreate() && options.isWorkspaces() && workingDirState == WorkingDirState.InEmptyFolder) {
			N4jscConsole.print(String.format("name of yarn project: (%s) ", config.yarnPackageJson.name));
			userInput = N4jscConsole.readLine();
			answers.nameYarnProject = userInput.isBlank() ? config.yarnPackageJson.name : userInput;
		}

		PackageJsonContents defaults = config.packageJson;
		if (!options.isN4JS()) {
			// in case of extending an already existing project to n4js, the name is not changed
			N4jscConsole.print(String.format("name: (%s) ", defaults.name));
			userInput = N4jscConsole.readLine();
			answers.nameProject = userInput.isBlank() ? defaults.name : userInput;
		}

		N4jscConsole.print("Add 'Hello World' example? (type 'y' for yes) (no) ");
		userInput = N4jscConsole.readLine();
		answers.addHelloWorld = isYes(userInput);
		if (answers.addHelloWorld) {
			N4jscConsole.print("Add Test for 'Hello World' example? (type 'y' for yes) (no) ");
			answers.addHelloWorldTest = isYes(userInput);
			addExamples(config, answers);
		}

		N4jscConsole.print(String.format("version: (%s) ", Strings.nullToEmpty(defaults.version)));
		userInput = N4jscConsole.readLine();
		answers.version = userInput.isBlank() ? defaults.version : userInput;

		N4jscConsole.print(String.format("main module: (%s) ", Strings.nullToEmpty(defaults.main)));
		userInput = N4jscConsole.readLine();
		answers.main_module = userInput.isBlank() ? defaults.main : userInput;

		N4jscConsole.print(String.format("author: (%s) ", Strings.nullToEmpty(defaults.author)));
		userInput = N4jscConsole.readLine();
		answers.author = userInput.isBlank() ? defaults.author : userInput;

		N4jscConsole.print(String.format("license: (%s) ", Strings.nullToEmpty(defaults.license)));
		userInput = N4jscConsole.readLine();
		answers.license = userInput.isBlank() ? defaults.license : userInput;

		N4jscConsole.print(String.format("description: (%s) ", Strings.nullToEmpty(defaults.description)));
		userInput = N4jscConsole.readLine();
		answers.description = userInput.isBlank() ? defaults.description : userInput;

		return answers;
	}

	static void addExamples(InitConfiguration config, UserAnswers answers) {
		if (answers != null) {
			if (answers.addHelloWorld) {
				config.packageJson = config.packageJson.helloWorld();
				config.files.add(new FileHelloWorld());

				if (answers.addHelloWorldTest) {
					config.packageJson = config.packageJson.helloWorldTests();
					config.yarnPackageJson = config.yarnPackageJson.defaultsTested();
					config.files.add(new FileHelloWorldTest());
				}
			}
		}
	}

	static void customizeConfiguration(N4jscOptions options, InitConfiguration config, UserAnswers answers) {
		addExamples(config, answers);

		if (!Strings.isNullOrEmpty(answers.nameYarnProject) && options.isCreate() && options.isWorkspaces()) {
			config.yarnPackageJson.name = answers.nameYarnProject;
		}

		PackageJsonContents defaults = config.packageJson;
		if (!Strings.isNullOrEmpty(answers.nameProject) && !options.isN4JS()) {
			defaults.name = answers.nameProject;
			defaults.userModifications.add("name");
		}
		if (!Strings.isNullOrEmpty(answers.version)) {
			defaults.version = answers.version;
			defaults.userModifications.add("version");
		}
		if (!Strings.isNullOrEmpty(answers.main_module) && !Objects.equal(answers.main_module, defaults.main)) {
			Pair<URI, URI> moduleNames = interpretModuleNames(answers.main_module);
			defaults.main = moduleNames.getKey().toFileString();
			defaults.n4js.mainModule = moduleNames.getValue().trimFileExtension().toFileString();
			config.files.add(new IndexFile(moduleNames.getValue().toFileString()));
			defaults.userModifications.add("main");
			defaults.userModifications.add("n4js");
		}
		if (!Strings.isNullOrEmpty(answers.author)) {
			defaults.author = answers.author;
			defaults.userModifications.add("author");
		}
		if (!Strings.isNullOrEmpty(answers.license)) {
			defaults.license = answers.license;
			defaults.userModifications.add("license");
		}
		if (!Strings.isNullOrEmpty(answers.description)) {
			defaults.description = answers.description;
			defaults.userModifications.add("description");
		}
	}

	static Pair<URI, URI> interpretModuleNames(String userInput) {
		if (userInput.startsWith("src/")) {
			userInput = userInput.substring("src/".length());
		}
		if (userInput.startsWith("src-gen/")) {
			userInput = userInput.substring("src-gen/".length());
		}

		int lastDotIdx = userInput.lastIndexOf(".");
		int endIdx = lastDotIdx < 1 ? userInput.length() : lastDotIdx;
		String fName = userInput.substring(0, endIdx);
		String fExtension = endIdx + 1 < userInput.length() ? userInput.substring(endIdx + 1) : "";

		String jsExtension;
		String n4jsExtension;
		switch (fExtension) {
		case "jsx":
		case "n4jsx":
			jsExtension = "jsx";
			n4jsExtension = "n4jsx";
			break;
		case "js":
		case "n4js":
		default:
			jsExtension = "js";
			n4jsExtension = "n4js";
		}

		return Pair.of(
				URI.createFileURI("src-gen/" + fName + "." + jsExtension),
				URI.createFileURI(fName + "." + n4jsExtension));
	}
}
