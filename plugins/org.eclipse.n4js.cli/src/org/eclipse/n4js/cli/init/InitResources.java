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

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.cli.N4jscConsole;
import org.eclipse.n4js.cli.N4jscException;
import org.eclipse.n4js.cli.N4jscExitCode;
import org.eclipse.n4js.cli.N4jscOptions;
import org.eclipse.n4js.packagejson.PackageJsonModificationUtils;
import org.eclipse.n4js.packagejson.PackageJsonProperties;
import org.eclipse.n4js.utils.JsonUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;

/**
 *
 */
public class InitResources {

	static final String FOLDER_SRC = "src";
	static final String FOLDER_TEST = "tests";
	static final String FOLDER_OUTPUT = "src-gen";

	private static final String CREATE_NAME_YARN_PROJECT = "yarn-project";
	private static final String CREATE_NAME_SCOPE = "@scope";
	private static final String CREATE_NAME_PROJECT = "my-project";
	private static final String CREATE_NAME_SCOPE_PROJECT = CREATE_NAME_SCOPE + "/" + CREATE_NAME_PROJECT;

	private static final String NPM_RUN_N4JSC = "n4jsc";
	private static final String NPM_RUN_BUILD = "n4jsc compile . --clean || true";
	private static final String NPM_RUN_TEST = "n4js-mangelhaft";

	static class YarnPackageJsonContents {
		transient boolean exists = false;
		transient String name = CREATE_NAME_YARN_PROJECT;
		@SerializedName("private")
		boolean _private = true;
		LinkedHashMap<String, String> devDependencies = new LinkedHashMap<>() {
			{
				put("n4js-cli", "");
			}
		};
		LinkedHashMap<String, String> scripts = new LinkedHashMap<>() {
			{
				put("n4jsc", NPM_RUN_N4JSC);
				put("build", NPM_RUN_BUILD);
			}
		};
		String[] workspaces = { "packages/*" };

		transient Set<String> userModifications = new HashSet<>();

		static YarnPackageJsonContents defaults() {
			YarnPackageJsonContents pjc = new YarnPackageJsonContents();
			return pjc;
		}

		static YarnPackageJsonContents read(Path yarnRootDir) throws N4jscException {
			YarnPackageJsonContents ypjc;
			File pckjson = yarnRootDir.resolve(N4JSGlobals.PACKAGE_JSON).toFile();
			try (JsonReader reader = new JsonReader(new FileReader(pckjson));) {
				Gson gson = JsonUtils.createGson();
				JsonElement jsonElement = JsonParser.parseReader(reader);
				JsonObject rootObj = jsonElement.getAsJsonObject();

				String[] workspacesTmp = null;
				if (rootObj.has(PackageJsonProperties.WORKSPACES_ARRAY.name)) {
					// special handling since this can be either json array or json object
					workspacesTmp = getWorkspaces(rootObj);
					rootObj.remove(PackageJsonProperties.WORKSPACES_ARRAY.name);
				}

				ypjc = gson.fromJson(jsonElement, YarnPackageJsonContents.class);
				ypjc.exists = true;
				ypjc.workspaces = workspacesTmp;

				return ypjc;
			} catch (Exception e) {
				throw new N4jscException(N4jscExitCode.INIT_ERROR_WORKING_DIR, "Error when reading " + pckjson, e);
			}
		}

		private static String[] getWorkspaces(JsonObject rootObj) {
			JsonElement workspacesElem = rootObj.get(PackageJsonProperties.WORKSPACES_ARRAY.name);

			List<JsonArray> arrays = new ArrayList<>();
			if (workspacesElem != null) {
				if (workspacesElem.isJsonArray()) {
					arrays.add(workspacesElem.getAsJsonArray());
				} else if (workspacesElem.isJsonObject()) {
					JsonObject workspacesObject = workspacesElem.getAsJsonObject();
					if (workspacesObject.has(PackageJsonProperties.PACKAGES.name)) {
						arrays.add(workspacesObject.getAsJsonArray(PackageJsonProperties.PACKAGES.name));
					}
					if (workspacesObject.has(PackageJsonProperties.NOHOIST.name)) {
						arrays.add(workspacesObject.getAsJsonArray(PackageJsonProperties.NOHOIST.name));
					}
				}
			}

			ArrayList<String> workspacesEntries = new ArrayList<>();
			for (JsonArray array : arrays) {
				for (JsonElement workspaceEntry : array) {
					workspacesEntries.add(workspaceEntry.getAsString());
				}
			}

			return workspacesEntries.toArray(new String[workspacesEntries.size()]);
		}

		YarnPackageJsonContents defaultsTested() {
			scripts.put("test", NPM_RUN_TEST);
			devDependencies.put("n4js-mangelhaft-cli", "");
			devDependencies.put("org.eclipse.n4js.mangelhaft", "");
			devDependencies.put("org.eclipse.n4js.mangelhaft.assert", "");
			return this;
		}

		boolean hasModifications() {
			if (!exists) {
				return false;
			}
			return !userModifications.isEmpty();
		}

	}

	static class PackageJsonContents {
		transient boolean exists = false;
		String name;
		String version = "0.0.1";
		String description;
		String type = "module";
		String main;
		LinkedHashMap<String, String> scripts = new LinkedHashMap<>() {
			{
				put("n4jsc", NPM_RUN_N4JSC);
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

		static PackageJsonContents defaults(N4jscOptions options) {
			PackageJsonContents pjc = new PackageJsonContents();
			pjc.name = defaultPackageName(options);
			return pjc;
		}

		static PackageJsonContents read(N4jscOptions options) throws N4jscException {
			PackageJsonContents pjc;
			File pckjson = options.getWorkingDirectory().resolve(N4JSGlobals.PACKAGE_JSON).toFile();
			try (JsonReader reader = new JsonReader(new FileReader(pckjson));) {
				Gson gson = JsonUtils.createGson();
				pjc = gson.fromJson(reader, PackageJsonContents.class);
				pjc.exists = true;
				return pjc;
			} catch (Exception e) {
				throw new N4jscException(N4jscExitCode.INIT_ERROR_WORKING_DIR, "Error when reading " + pckjson, e);
			}
		}

		PackageJsonContents helloWorld() {
			main = FOLDER_OUTPUT + "/HelloWorld.js";
			n4js.mainModule = "HelloWorld";
			return this;
		}

		PackageJsonContents helloWorldTests() {
			scripts.put("test", NPM_RUN_TEST);
			devDependencies.put("n4js-mangelhaft-cli", "");
			devDependencies.put("org.eclipse.n4js.mangelhaft", "");
			devDependencies.put("org.eclipse.n4js.mangelhaft.assert", "");
			n4js.sources.test = new String[] { FOLDER_TEST };
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
				PackageJsonModificationUtils.addProperties(pckjson, modifiedElements);
			} else {
				target.toFile().mkdirs();
				if (pckjson.isFile()) {
					N4jscConsole.println("Overwriting " + pckjson.toString());
				}
				Gson gson = JsonUtils.createGson();
				String packageJsonString = gson.toJson(this);
				try (FileWriter fw = new FileWriter(pckjson)) {
					fw.write(packageJsonString);
				}
			}
		}

		boolean hasModifications() {
			if (!exists) {
				return false;
			}
			return !userModifications.isEmpty();
		}
	}

	static class PropertyN4JS {
		String projectType = "library";
		String mainModule;
		String output = FOLDER_OUTPUT;
		PropertySources sources = new PropertySources();
		String[] requiredRuntimeLibraries = { "n4js-runtime-es2015" };
	}

	static class PropertySources {
		String[] source = { FOLDER_SRC };
		String[] test;
	}

	static String defaultPackageName(N4jscOptions options) {
		File workDir = options.getWorkingDirectory().toFile();
		if (".".equals(workDir.getName()) && workDir.getParentFile() != null) {
			workDir = workDir.getParentFile();
		}

		File parent = workDir.getParentFile();

		String defPackageName = workDir.getName().toString();
		if (options.isWorkspaces()) {
			if (options.isScope()) {
				defPackageName = CREATE_NAME_SCOPE_PROJECT;
			} else {
				defPackageName = CREATE_NAME_PROJECT;
			}

		} else {
			if (options.isScope()) {
				if (options.isCreate()) {
					defPackageName = CREATE_NAME_SCOPE_PROJECT;
				} else {
					String scopeName = parent == null ? CREATE_NAME_SCOPE : parent.getName();
					String prjName = workDir.getName();
					defPackageName = scopeName + "/" + prjName;
				}
			} else {
				if (options.isCreate()) {
					defPackageName = CREATE_NAME_PROJECT;
				} else {
					defPackageName = workDir.getName();
				}
			}
		}
		return defPackageName;
	}

	static abstract class ExampleFile {
		abstract String getDir();

		abstract String getName();

		Path getPath() {
			return Path.of(getDir()).resolve(getName());
		}

		abstract String[] getContents();

		void writeToDisk(Path targetDir) throws IOException {
			File file = targetDir.resolve(getPath()).toFile();
			if (file.exists()) {
				N4jscConsole.println("Overwriting " + file.toString());
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
		String getDir() {
			return FOLDER_SRC;
		}

		@Override
		String getName() {
			return "HelloWorld.n4js";
		}

		@Override
		String[] getContents() {
			return new String[] {
					"export  class WorldClass {",
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
		String getDir() {
			return FOLDER_TEST;
		}

		@Override
		String getName() {
			return "HelloWorldTest.n4js";
		}

		@Override
		String[] getContents() {
			return new String[] {
					"import { WorldClass } from \"HelloWorld\";",
					"import { Assert } from \"org/eclipse/n4js/mangelhaft/assert/Assert\";",
					"",
					"export  class TestModule {",
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
		String getDir() {
			return FOLDER_SRC;
		}

		@Override
		String getName() {
			return name;
		}

		@Override
		String[] getContents() {
			return new String[] {
					"",
					"export  class MyClass {",
					"  pi : number = 3.14;",
					"  foo() : void {",
					"  }",
					"}",
					"" };
		}
	}
}
