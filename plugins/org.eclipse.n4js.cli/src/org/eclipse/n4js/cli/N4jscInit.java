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
package org.eclipse.n4js.cli;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.packagejson.PackageJsonModificationUtils;
import org.eclipse.n4js.packagejson.PackageJsonProperties;
import org.eclipse.n4js.utils.ModuleFilterUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;

/**
 * Performs the n4jsc goal init
 */
public class N4jscInit {

	/** Starts the compiler for goal INIT in a blocking fashion */
	public static N4jscExitState start(N4jscOptions options) {
		PackageJsonContents defaults = PackageJsonContents.defaults(options);
		if (!options.isYes()) {
			customize(defaults);
		}
		return N4jscExitState.SUCCESS;
	}

	private static void customize(PackageJsonContents defaults) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonObject jsonObj = (JsonObject) gson.toJsonTree(defaults);
		for (String name : jsonObj.keySet()) {
			N4jscConsole.print(name + ": ");
			String userInput = N4jscConsole.readLine();
			if (!userInput.isBlank()) {
				jsonObj.addProperty(name, userInput);
			}
		}
	}

	static N4jscExitState initProject(N4jscOptions options, InitConfiguration config) throws N4jscException {
		Path cwd = options.getWorkingDirectory();
		File parentPackageJson = getParentPackageJson(options);
		WorkingDirState workingDirState = getWorkingDirState(options, parentPackageJson);

		String workspacesOption = options.getWorkspaces() == null ? null : options.getWorkspaces().toString();
		if (workingDirState == WorkingDirState.InEmptyFolder) {
			if (workspacesOption == null) {
				config.projectRoot = cwd;
				initProject(config);

			} else {
				config.yarnRoot = cwd;
				config.workspacesDir = cwd.resolve(workspacesOption);
				config.projectRoot = config.workspacesDir.resolve(config.packageJson.name);
				initYarnProject(config);
				initProject(config);
			}

		} else {

			config.yarnRoot = parentPackageJson.getParentFile().toPath();
			List<String> workspacesProperty = getYarnWorkspaces(parentPackageJson);
			if (workspacesOption == null) {
				config.workspacesDir = cwd;
				config.projectRoot = config.workspacesDir.resolve(config.packageJson.name);
				if (!workspaceMatch(workspacesProperty, config.yarnRoot, config.projectRoot)) {
					throw new N4jscException(N4jscExitCode.INIT_ERROR_WORKING_DIR,
							"Creating a new project inside a yarn project requires either to explicitly pass option --workspaces or "
									+ "the current working directory to be inside a valid workspaces directory of the yarn project.");
				}

				initProject(config);

			} else {

				config.workspacesDir = config.yarnRoot.resolve(workspacesOption);
				config.projectRoot = config.workspacesDir.resolve(config.packageJson.name);
				if (!workspaceMatch(workspacesProperty, cwd, config.projectRoot)) {
					try {
						PackageJsonModificationUtils.addToWorkspaces(parentPackageJson, workspacesOption + "/*");
					} catch (IOException e) {
						throw new N4jscException(N4jscExitCode.INIT_ERROR_WORKING_DIR, e);
					}
				}

				initProject(config);
			}
		}

		N4jscConsole.println("Init done. Please run 'npm install' to install dependencies.");
		return N4jscExitState.SUCCESS;
	}

	static WorkingDirState getWorkingDirState(N4jscOptions options, File candidate) throws N4jscException {
		if (candidate == null) {
			return WorkingDirState.InEmptyFolder;
		}

		if (!candidate.exists()) {
			throw new N4jscException(N4jscExitCode.INIT_ERROR_WORKING_DIR,
					"Inconsistent state: " + candidate.toString() + " should exist but it does not.");
		}

		try (JsonReader jReader = new JsonReader(new FileReader(candidate))) {
			JsonElement packageJsonCandidate = new JsonParser().parse(jReader);
			if (!packageJsonCandidate.isJsonObject()) {
				return WorkingDirState.InEmptyFolder;
			}
			JsonObject packageJson = (JsonObject) packageJsonCandidate;
			boolean isYarnProject = packageJson.has(PackageJsonProperties.WORKSPACES_ARRAY.name);
			if (!isYarnProject) {
				throw new N4jscException(N4jscExitCode.INIT_ERROR_WORKING_DIR,
						"Current working directory is inside the non-yarn project of " + candidate);
			}

			Path candidateWorkDir = candidate.getParentFile().toPath();
			if (candidateWorkDir.equals(options.getWorkingDirectory())) {
				return WorkingDirState.InYarnProjectRoot;
			} else {
				return WorkingDirState.InYarnProjectSubdir;
			}
		} catch (Exception e) {
			throw new N4jscException(N4jscExitCode.INIT_ERROR_WORKING_DIR,
					"Working directory must be either empty or inside a yarn project.", e);
		}
	}

	static File getParentPackageJson(N4jscOptions options) {
		Path cwd = options.getWorkingDirectory();
		File candidate = cwd.resolve(N4JSGlobals.PACKAGE_JSON).toFile();
		while (!candidate.exists() && cwd.getParent() != null) {
			cwd = cwd.getParent();
			candidate = cwd.resolve(N4JSGlobals.PACKAGE_JSON).toFile();
		}
		if (cwd.getParent() == null) {
			return null;
		}
		return candidate;
	}

	static Path getYarnRootFolder(N4jscOptions options) {
		return getParentPackageJson(options).toPath();
	}

	static List<String> getYarnWorkspaces(File candidate) throws N4jscException {
		try (JsonReader jReader = new JsonReader(new FileReader(candidate))) {
			JsonElement packageJsonCandidate = new JsonParser().parse(jReader);
			JsonObject packageJson = (JsonObject) packageJsonCandidate;
			JsonElement workspacesElement = packageJson.get(PackageJsonProperties.WORKSPACES_ARRAY.name);

			List<String> workspacesEntries = new ArrayList<>();
			if (workspacesElement.isJsonArray()) {
				JsonArray workspacesArray = (JsonArray) workspacesElement;
				for (JsonElement workspaceEntry : workspacesArray) {
					workspacesEntries.add(workspaceEntry.getAsString());
				}

			} else if (workspacesElement.isJsonObject()) {
				JsonObject workspacesObject = (JsonObject) workspacesElement;

				List<JsonArray> arrays = new ArrayList<>();
				if (workspacesObject.has(PackageJsonProperties.PACKAGES.name)) {
					arrays.add(workspacesObject.getAsJsonArray(PackageJsonProperties.PACKAGES.name));
				}
				if (workspacesObject.has(PackageJsonProperties.NOHOIST.name)) {
					arrays.add(workspacesObject.getAsJsonArray(PackageJsonProperties.NOHOIST.name));
				}
				for (JsonArray array : arrays) {
					for (JsonElement workspaceEntry : array) {
						workspacesEntries.add(workspaceEntry.getAsString());
					}
				}

				return workspacesEntries;
			}

			return null;
		} catch (Exception e) {
			throw new N4jscException(N4jscExitCode.INIT_ERROR_WORKING_DIR, e);
		}
	}

	static boolean workspaceMatch(List<String> globs, Path root, Path newProjectLocation) {
		Path relProjectLocation = root.relativize(newProjectLocation);
		for (String glob : globs) {
			boolean matches = ModuleFilterUtils.locationMatchesGlobSpecifier(glob, relProjectLocation);
			if (matches) {
				return true;
			}
		}
		return false;
	}

	static N4jscExitState initYarnProject(InitConfiguration config) throws N4jscException {
		config.workspacesDir.toFile().mkdirs();
		YarnPackageJsonContents yarnPackageJsonContents = YarnPackageJsonContents.defaults();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String yarnJsonString = gson.toJson(yarnPackageJsonContents);
		try (FileWriter fw = new FileWriter(config.yarnRoot.resolve(N4JSGlobals.PACKAGE_JSON).toFile())) {
			fw.write(yarnJsonString);
		} catch (IOException e) {
			throw new N4jscException(N4jscExitCode.INIT_ERROR_WORKING_DIR, e);
		}

		return N4jscExitState.SUCCESS;
	}

	static N4jscExitState initProject(InitConfiguration config)
			throws N4jscException {

		config.projectRoot.toFile().mkdirs();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String packageJsonString = gson.toJson(config.packageJson);
		try (FileWriter fw = new FileWriter(config.projectRoot.resolve(N4JSGlobals.PACKAGE_JSON).toFile())) {
			fw.write(packageJsonString);

			for (ExampleFile exampleFile : config.files) {
				exampleFile.writeToDisk(config.projectRoot);
			}
		} catch (IOException e) {
			throw new N4jscException(N4jscExitCode.INIT_ERROR_WORKING_DIR, e);
		}

		return N4jscExitState.SUCCESS;
	}

	enum WorkingDirState {
		InEmptyFolder, InYarnProjectRoot, InYarnProjectSubdir
	}

	public static void main(String[] args) throws N4jscException {

		getPackageJsonContents();

	}

	static LinkedHashMap<String, String> getFurtherQuestions() {
		return new LinkedHashMap<>() {
			{
				put("Add 'Hello World' example with test? (no)", "");
			}
		};
	}

	static void getPackageJsonContents() throws N4jscException {

		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		N4jscOptions options = new N4jscOptions();
		options.read("init");

		PackageJsonContents pckJson = PackageJsonContents.defaults(options);
		String jsonString = gson.toJson(pckJson);
		System.out.println(jsonString);
	}

	static class InitConfiguration {
		Path yarnRoot;
		Path workspacesDir;
		Path projectRoot;
		YarnPackageJsonContents yarnPackageJson;
		PackageJsonContents packageJson;
		Collection<ExampleFile> files;
	}

	static class YarnPackageJsonContents {
		@SerializedName("private")
		boolean _private = true;
		LinkedHashMap<String, String> devDependencies = new LinkedHashMap<>() {
			{
				put("n4js-cli", "");
			}
		};
		LinkedHashMap<String, String> script = new LinkedHashMap<>() {
			{
				put("build", "n4jsc compile . --clean");
			}
		};
		String[] workspaces = { "packages/*" };

		static YarnPackageJsonContents defaults() {
			YarnPackageJsonContents pjc = new YarnPackageJsonContents();
			return pjc;
		}

		static YarnPackageJsonContents defaultsTested() {
			YarnPackageJsonContents pjc = new YarnPackageJsonContents();
			pjc.script.put("test", "mangelhaft-cli");
			pjc.devDependencies.put("n4js-mangelhaft-cli", "");
			pjc.devDependencies.put("org.eclipse.n4js.mangelhaft", "");
			pjc.devDependencies.put("org.eclipse.n4js.mangelhaft.assert", "");
			return pjc;
		}
	}

	static class PackageJsonContents {
		String name;
		String version = "0.0.1";
		String description;
		String main;
		LinkedHashMap<String, String> script = new LinkedHashMap<>() {
			{
				put("build", "n4jsc compile . --clean");
			}
		};
		String author;
		String license;
		LinkedHashMap<String, String> dependencies = new LinkedHashMap<>() {
			{
				put("n4js-runtime-es2015", "");
			}
		};
		LinkedHashMap<String, String> devDependencies = new LinkedHashMap<>() {
			{
				put("n4js-cli", "");
			}
		};
		PropertyN4JS n4js = new PropertyN4JS();

		static PackageJsonContents defaults(N4jscOptions options) {
			PackageJsonContents pjc = new PackageJsonContents();
			pjc.name = defPackageName(options);
			return pjc;
		}

		static PackageJsonContents helloWorld(N4jscOptions options) {
			PackageJsonContents helloWorld = defaults(options);
			helloWorld.main = "src-gen/HelloWorldModule.js";
			helloWorld.n4js.mainModule = "HelloWorldModule";
			return helloWorld;
		}

		static PackageJsonContents helloWorldTested(N4jscOptions options) {
			PackageJsonContents helloWorld = helloWorld(options);
			helloWorld.script.put("test", "mangelhaft-cli");
			helloWorld.devDependencies.put("n4js-mangelhaft-cli", "");
			helloWorld.devDependencies.put("org.eclipse.n4js.mangelhaft", "");
			helloWorld.devDependencies.put("org.eclipse.n4js.mangelhaft.assert", "");
			helloWorld.n4js.sources.test = new String[] { "tests" };
			return helloWorld;
		}
	}

	static class PropertyN4JS {
		String projectType = "library";
		String mainModule;
		String output = "src-gen";
		PropertySources sources = new PropertySources();
		String[] requiredRuntimeLibraries = { "n4js-runtime-es2015" };
	}

	static class PropertySources {
		String[] source = { "src" };
		String[] test;

	}

	private static String defPackageName(N4jscOptions options) {
		Path workDir = options.getWorkingDirectory();
		String defPackageName = workDir.getName(workDir.getNameCount() - 1).toString();
		if (options.isScope()) {
			defPackageName = "@" + workDir.getName(workDir.getNameCount() - 2).toString() + "/" + defPackageName;
		}
		return defPackageName;
	}

	static abstract class ExampleFile {
		abstract Path getPath();

		abstract String[] getContents();

		void writeToDisk(Path targetDir) throws IOException {
			File file = targetDir.resolve(getPath()).toFile();
			String contents = String.join(System.lineSeparator(), getContents());
			try (FileWriter fw = new FileWriter(file)) {
				fw.write(contents);
			}
		}
	}

	static class FileHelloWorld extends ExampleFile {
		@Override
		Path getPath() {
			return Path.of("src/HelloWorldModule.n4js");
		}

		@Override
		String[] getContents() {
			return new String[] {
					"export public class WorldClass {",
					"  greeting : String = 'Hello World';",
					"}",
					"",
					"console.log(new WorldClass().greeting);"
			};
		}
	}

	static class FileHelloWorldTest extends ExampleFile {
		@Override
		Path getPath() {
			return Path.of("tests/HelloWorldTest.n4js");
		}

		@Override
		String[] getContents() {
			return new String[] {
					"import { WorldClass } from \"HelloWorldModule\";",
					"",
					"class TestModule {",
					"  @Test",
					"  helloWorldTest() : void {",
					"    val helloWorld = new HelloWorld()",
					"    assertEquals('Hello World', helloWorld.greeting)",
					"  }",
					"}",
					"",
					"console.log(new WorldClass().greeting);" };
		}
	}
}
