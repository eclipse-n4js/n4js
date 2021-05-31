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
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.packagejson.PackageJsonModificationUtils;
import org.eclipse.n4js.packagejson.PackageJsonProperties;
import org.eclipse.n4js.utils.JsonUtils;
import org.eclipse.n4js.utils.ModuleFilterUtils;
import org.eclipse.xtext.xbase.lib.Pair;

import com.google.common.base.Strings;
import com.google.gson.Gson;
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
	public static N4jscExitState start(N4jscOptions options) throws N4jscException {
		InitConfiguration config;
		if (options.isYes()) {
			config = getDefaultConfiguration(options, null);
		} else if (options.getAnswers() != null) {
			String[] answers = { "", "", "", "", "", "", "" };
			String[] userAnswers = options.getAnswers().split("(?<=[^\\\\]|^),");
			System.arraycopy(userAnswers, 0, answers, 0, userAnswers.length);
			config = getCustomizedConfiguration(options, answers);
		} else {
			String[] answers = inputUserAnswers(options);
			config = getCustomizedConfiguration(options, answers);
		}
		initProjects(options, config);
		return N4jscExitState.SUCCESS;
	}

	private static InitConfiguration getDefaultConfiguration(N4jscOptions options, String initType)
			throws N4jscException {

		InitConfiguration config = new InitConfiguration();
		switch (Strings.nullToEmpty(initType)) {
		case "e":
			config.yarnPackageJson = YarnPackageJsonContents.defaults();
			config.packageJson = PackageJsonContents.defaults(options).helloWorld();
			config.files.add(new FileHelloWorld());
			break;
		case "t":
			config.yarnPackageJson = YarnPackageJsonContents.defaults().defaultsTested();
			config.packageJson = PackageJsonContents.defaults(options).helloWorld().helloWorldTests();
			config.files.add(new FileHelloWorld());
			config.files.add(new FileHelloWorldTest());
			break;
		default:
			config.yarnPackageJson = YarnPackageJsonContents.defaults();
			config.packageJson = PackageJsonContents.defaults(options);
			break;
		}
		return config;
	}

	private static String[] inputUserAnswers(N4jscOptions options) throws N4jscException {
		String[] answers = new String[7];

		N4jscConsole.print("Add 'Hello World' example (type 'e') including a test example (type 't')? (no) ");
		String userInput = N4jscConsole.readLine();
		answers[0] = userInput.isBlank() ? "" : userInput;
		PackageJsonContents defaults = getDefaultConfiguration(options, userInput).packageJson;

		if (!options.isN4JS()) {
			// in case of extending an already existing project to n4js, the name is not changed
			N4jscConsole.print(String.format("name: (%s) ", defaults.name));
			userInput = N4jscConsole.readLine();
			answers[1] = userInput.isBlank() ? defaults.name : userInput;
		}

		N4jscConsole.print(String.format("version: (%s) ", Strings.nullToEmpty(defaults.version)));
		userInput = N4jscConsole.readLine();
		answers[2] = userInput.isBlank() ? defaults.version : userInput;

		N4jscConsole.print(String.format("main module: (%s) ", Strings.nullToEmpty(defaults.main)));
		userInput = N4jscConsole.readLine();
		answers[3] = userInput.isBlank() ? defaults.main : userInput;

		N4jscConsole.print(String.format("author: (%s) ", Strings.nullToEmpty(defaults.author)));
		userInput = N4jscConsole.readLine();
		answers[4] = userInput.isBlank() ? defaults.author : userInput;

		N4jscConsole.print(String.format("license: (%s) ", Strings.nullToEmpty(defaults.license)));
		userInput = N4jscConsole.readLine();
		answers[5] = userInput.isBlank() ? defaults.license : userInput;

		N4jscConsole.print(String.format("description: (%s) ", Strings.nullToEmpty(defaults.description)));
		userInput = N4jscConsole.readLine();
		answers[6] = userInput.isBlank() ? defaults.description : userInput;

		return answers;
	}

	private static InitConfiguration getCustomizedConfiguration(N4jscOptions options, String[] answers)
			throws N4jscException {

		InitConfiguration config = getDefaultConfiguration(options, answers[0]);
		PackageJsonContents defaults = config.packageJson;

		if (!answers[1].isEmpty() && !options.isN4JS()) {
			defaults.name = answers[1];
			defaults.userModifications.add("name");
		}
		if (!answers[2].isEmpty()) {
			defaults.version = answers[2];
			defaults.userModifications.add("version");
		}
		if (!answers[3].isEmpty()) {
			Pair<URI, URI> moduleNames = interpretModuleNames(answers[3]);
			defaults.main = moduleNames.getKey().toFileString();
			defaults.n4js.mainModule = moduleNames.getValue().trimFileExtension().toFileString();
			config.files.add(new IndexFile(moduleNames.getValue().toFileString()));
			defaults.userModifications.add("main");
			defaults.userModifications.add("n4js");
		}
		if (!answers[4].isEmpty()) {
			defaults.author = answers[4];
			defaults.userModifications.add("author");
		}
		if (!answers[5].isEmpty()) {
			defaults.license = answers[5];
			defaults.userModifications.add("license");
		}
		if (!answers[6].isEmpty()) {
			defaults.description = answers[6];
			defaults.userModifications.add("description");
		}
		return config;
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

	static N4jscExitState initProjects(N4jscOptions options, InitConfiguration config) throws N4jscException {
		Path cwd = options.getWorkingDirectory();
		File parentPackageJson = getParentPackageJson(options);
		WorkingDirState workingDirState = getWorkingDirState(options, parentPackageJson);
		String workspacesOption = options.getWorkspaces() == null ? null : options.getWorkspaces().toString();

		switch (workingDirState) {
		case InExistingProject:
			config.projectRoot = cwd;
			config.packageJson.userModifications.add("n4js");

			break;
		case InEmptyFolder:

			if (workspacesOption == null) {
				config.projectRoot = cwd;

			} else {

				config.yarnRoot = cwd;
				config.workspacesDir = cwd.resolve(workspacesOption);
				config.projectRoot = config.workspacesDir.resolve(config.packageJson.name);
				initYarnProject(config);

				// FIXME: GH-2143
				String folder = (config.yarnRoot.endsWith(".") ? config.yarnRoot.getParent() : config.yarnRoot)
						.getFileName().toString();
				if (config.packageJson.name.equals(folder)) {
					config.packageJson.name = config.packageJson.name + "2";
					config.projectRoot = config.workspacesDir.resolve(config.packageJson.name);
				}
			}

			break;

		default:
			config.yarnRoot = parentPackageJson.getParentFile().toPath();
			List<String> workspacesProperty = getYarnWorkspaces(parentPackageJson);
			if (workspacesOption == null) {
				config.projectRoot = cwd;
				config.workspacesDir = config.projectRoot.getParent();
				if (options.isScope()) {
					config.workspacesDir = config.workspacesDir.getParent();
				}
				if (!workspaceMatch(workspacesProperty, config.yarnRoot, config.projectRoot)) {
					throw new N4jscException(N4jscExitCode.INIT_ERROR_WORKING_DIR,
							"Creating a new project inside a yarn project requires either to explicitly pass option --workspaces or "
									+ "the current working directory to be inside a new project folder of a valid workspaces directory of the yarn project.");
				}

			} else {

				config.workspacesDir = config.yarnRoot.resolve(workspacesOption);
				config.projectRoot = config.workspacesDir.resolve(config.packageJson.name);
				for (int i = 0; config.projectRoot.resolve(N4JSGlobals.PACKAGE_JSON).toFile().exists(); i++) {
					config.projectRoot = Path.of(config.projectRoot.toString() + "_" + i);
				}
				if (!workspaceMatch(workspacesProperty, cwd, config.projectRoot)) {
					try {
						PackageJsonModificationUtils.addToWorkspaces(parentPackageJson, workspacesOption + "/*");
					} catch (IOException e) {
						throw new N4jscException(N4jscExitCode.INIT_ERROR_WORKING_DIR, e);
					}
				}
			}
		}

		initProject(options, config);
		String cmd = (workingDirState == WorkingDirState.InEmptyFolder) && (workspacesOption == null) ? "npm" : "yarn";
		N4jscConsole.println("Init done. Please run '" + cmd + " install' to install dependencies.");
		return N4jscExitState.SUCCESS;
	}

	static WorkingDirState getWorkingDirState(N4jscOptions options, File candidate) throws N4jscException {
		if (options.isN4JS()) {
			if (candidate != null && candidate.exists()
					&& candidate.getParentFile().equals(options.getWorkingDirectory().toFile())) {

				return WorkingDirState.InExistingProject;
			} else {
				throw new N4jscException(N4jscExitCode.INIT_ERROR_WORKING_DIR,
						"Given option --n4js requires a package.json file to be in the current working directory.");
			}
		}

		if (candidate == null || !candidate.exists()) {
			return WorkingDirState.InEmptyFolder;
		}

		if (!options.isN4JS() && candidate.getParentFile().equals(options.getWorkingDirectory().toFile())) {
			throw new N4jscException(N4jscExitCode.INIT_ERROR_WORKING_DIR,
					"Current working directory must not contain a package.json file.");
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
			}

			return workspacesEntries;
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
		Gson gson = JsonUtils.createGson();
		String yarnJsonString = gson.toJson(config.yarnPackageJson);
		try (FileWriter fw = new FileWriter(config.yarnRoot.resolve(N4JSGlobals.PACKAGE_JSON).toFile())) {
			fw.write(yarnJsonString);
		} catch (IOException e) {
			throw new N4jscException(N4jscExitCode.INIT_ERROR_WORKING_DIR, e);
		}

		config.packageJson.inYarnProject();
		return N4jscExitState.SUCCESS;
	}

	static N4jscExitState initProject(N4jscOptions options, InitConfiguration config)
			throws N4jscException {

		try {
			config.packageJson.write(options, config.projectRoot);
			for (ExampleFile exampleFile : config.files) {
				exampleFile.writeToDisk(config.projectRoot);
			}
		} catch (IOException e) {
			throw new N4jscException(N4jscExitCode.INIT_ERROR_WORKING_DIR, e);
		}

		return N4jscExitState.SUCCESS;
	}

	enum WorkingDirState {
		InEmptyFolder, InYarnProjectRoot, InYarnProjectSubdir, InExistingProject
	}

	static final String NPM_RUN_BUILD = "n4jsc compile . --clean || true";
	static final String NPM_RUN_TEST = "n4js-mangelhaft";

	static class InitConfiguration {
		Path yarnRoot;
		Path workspacesDir;
		Path projectRoot;
		YarnPackageJsonContents yarnPackageJson;
		PackageJsonContents packageJson;
		Collection<ExampleFile> files = new ArrayList<>();
	}

	static class YarnPackageJsonContents {
		@SerializedName("private")
		boolean _private = true;
		LinkedHashMap<String, String> devDependencies = new LinkedHashMap<>() {
			{
				put("n4js-cli", "");
			}
		};
		LinkedHashMap<String, String> scripts = new LinkedHashMap<>() {
			{
				put("build", NPM_RUN_BUILD);
			}
		};
		String[] workspaces = { "packages/*" };

		static YarnPackageJsonContents defaults() {
			YarnPackageJsonContents pjc = new YarnPackageJsonContents();
			return pjc;
		}

		YarnPackageJsonContents defaultsTested() {
			scripts.put("test", NPM_RUN_TEST);
			devDependencies.put("n4js-mangelhaft-cli", "");
			devDependencies.put("org.eclipse.n4js.mangelhaft", "");
			devDependencies.put("org.eclipse.n4js.mangelhaft.assert", "");
			return this;
		}
	}

	static class PackageJsonContents {
		String name;
		String version = "0.0.1";
		String description;
		String main;
		LinkedHashMap<String, String> scripts = new LinkedHashMap<>() {
			{
				put("build", NPM_RUN_BUILD);
			}
		};
		String author;
		String license;
		LinkedHashMap<String, String> dependencies = new LinkedHashMap<>() {
			{
				put("n4js-runtime", "");
				put("n4js-runtime-es2015", "");
			}
		};
		LinkedHashMap<String, String> devDependencies = new LinkedHashMap<>() {
			{
				put("n4js-cli", "");
			}
		};
		PropertyN4JS n4js = new PropertyN4JS();

		transient Set<String> userModifications = new HashSet<>();

		static PackageJsonContents defaults(N4jscOptions options) throws N4jscException {
			PackageJsonContents pjc;
			if (options.isN4JS()) {
				File pckjson = options.getWorkingDirectory().resolve(N4JSGlobals.PACKAGE_JSON).toFile();
				try (JsonReader reader = new JsonReader(new FileReader(pckjson));) {
					Gson gson = JsonUtils.createGson();
					pjc = gson.fromJson(reader, PackageJsonContents.class);
					return pjc;
				} catch (Exception e) {
					throw new N4jscException(N4jscExitCode.INIT_ERROR_WORKING_DIR, "Error when reading " + pckjson, e);
				}
			} else {
				pjc = new PackageJsonContents();
				pjc.name = defaultPackageName(options);
				return pjc;
			}
		}

		PackageJsonContents helloWorld() {
			main = "src-gen/HelloWorld.js";
			n4js.mainModule = "HelloWorld";
			return this;
		}

		PackageJsonContents helloWorldTests() {
			scripts.put("test", NPM_RUN_TEST);
			devDependencies.put("n4js-mangelhaft-cli", "");
			devDependencies.put("org.eclipse.n4js.mangelhaft", "");
			devDependencies.put("org.eclipse.n4js.mangelhaft.assert", "");
			n4js.sources.test = new String[] { "tests" };
			return this;
		}

		PackageJsonContents inYarnProject() {
			scripts = null;
			return this;
		}

		void write(N4jscOptions options, Path target) throws IOException {
			File pckjson = target.resolve(N4JSGlobals.PACKAGE_JSON).toFile();
			if (options.isN4JS()) {
				Gson gson = JsonUtils.createGson();
				JsonElement jsonRoot = gson.toJsonTree(this, PackageJsonContents.class);
				Set<Entry<String, JsonElement>> allElements = jsonRoot.getAsJsonObject().entrySet();
				Set<Entry<String, JsonElement>> modifiedElements = new HashSet<>();
				for (Entry<String, JsonElement> element : allElements) {
					if (userModifications.contains(element.getKey())) {
						modifiedElements.add(element);
					}
				}
				PackageJsonModificationUtils.setProperties(pckjson, modifiedElements);
			} else {
				target.toFile().mkdirs();
				Gson gson = JsonUtils.createGson();
				String packageJsonString = gson.toJson(this);
				try (FileWriter fw = new FileWriter(pckjson)) {
					fw.write(packageJsonString);
				}
			}
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

	private static String defaultPackageName(N4jscOptions options) {
		Path workDir = options.getWorkingDirectory();
		int idx = workDir.getNameCount() - 1;
		String defPackageName = workDir.getName(idx).toString();
		if (".".equals(defPackageName)) {
			idx--;
			defPackageName = workDir.getName(idx).toString();
		}
		if (options.isScope()) {
			defPackageName = "@" + workDir.getName(idx - 1).toString() + "/" + defPackageName;
		}
		return defPackageName;
	}

	static abstract class ExampleFile {
		abstract Path getPath();

		abstract String[] getContents();

		void writeToDisk(Path targetDir) throws IOException {
			File file = targetDir.resolve(getPath()).toFile();
			if (file.exists()) {
				file.delete();
			}
			file.getParentFile().mkdirs();
			file.createNewFile();
			String contents = String.join(System.lineSeparator(), getContents());
			try (FileWriter fw = new FileWriter(file)) {
				fw.write(contents);
			}
		}
	}

	static class FileHelloWorld extends ExampleFile {
		@Override
		Path getPath() {
			return Path.of("src/HelloWorld.n4js");
		}

		@Override
		String[] getContents() {
			return new String[] {
					"export public class WorldClass {",
					"  greeting : string = 'Hello World';",
					"}",
					"",
					"console.log(new WorldClass().greeting);",
					""
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
					"import { WorldClass } from \"HelloWorld\";",
					"import { Assert } from \"org/eclipse/n4js/mangelhaft/assert/Assert\";",
					"",
					"export public class TestModule {",
					"  @Test",
					"  helloWorldTest() : void {",
					"    let helloWorld = new WorldClass()",
					"    Assert.equal('Hello World', helloWorld.greeting)",
					"  }",
					"}",
					""
			};
		}
	}

	static class IndexFile extends ExampleFile {
		final String name;

		IndexFile(String name) {
			this.name = name;
		}

		@Override
		Path getPath() {
			return Path.of("src/" + name);
		}

		@Override
		String[] getContents() {
			return new String[] {
					"",
					"export public class MyClass {",
					"  pi : number = 3.14;",
					"  foo() : void {",
					"  }",
					"}",
					"" };
		}
	}
}
