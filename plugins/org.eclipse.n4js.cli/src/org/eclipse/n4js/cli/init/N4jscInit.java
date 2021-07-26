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

import static com.google.common.base.Preconditions.checkState;
import static org.eclipse.n4js.cli.N4jscConsole.println;
import static org.eclipse.n4js.cli.init.InitDialog.NL;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.cli.N4jscConsole;
import org.eclipse.n4js.cli.N4jscException;
import org.eclipse.n4js.cli.N4jscExitCode;
import org.eclipse.n4js.cli.N4jscExitState;
import org.eclipse.n4js.cli.N4jscOptions;
import org.eclipse.n4js.cli.init.InitResources.ExampleFile;
import org.eclipse.n4js.cli.init.InitResources.YarnPackageJsonContents;
import org.eclipse.n4js.packagejson.PackageJsonProperties;
import org.eclipse.n4js.utils.JsonUtils;
import org.eclipse.n4js.utils.ModuleFilterUtils;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

/**
 * Performs the n4jsc goal init
 */
public class N4jscInit {

	/** Starts the compiler for goal INIT in a blocking fashion */
	public static N4jscExitState start(N4jscOptions options) throws N4jscException {
		File parentPackageJson = getParentPackageJson(options);
		WorkingDirState workingDirState = checkAndGetWorkingDirState(options, parentPackageJson);
		InitConfiguration config = InitDialog.getInitConfiguration(options, parentPackageJson, workingDirState);
		setDirectories(options, config, parentPackageJson, workingDirState);

		if (!options.isYes() && options.getAnswers() == null) {
			String verb1 = options.isN4JS() ? "MODIFICATIONS" : "CONFIGURATION";
			String verb2 = options.isN4JS() ? "Modify" : "Create";
			N4jscConsole.print(NL);
			N4jscConsole.print("PENDING PROJECT " + verb1 + NL + NL + config.toString());
			N4jscConsole.print(NL + verb2 + " this project? (yes) ");
			String userInput = N4jscConsole.readLine();
			if (!Strings.nullToEmpty(userInput).isBlank() && !InitDialog.isYes(userInput)) {
				throw new N4jscException(N4jscExitCode.USER_CANCELLED, "User said '" + userInput + "'");
			}
		}

		if (config.isWorkspaces()) {
			initYarnProject(config);
		}
		initProject(options, config);

		String cmd = config.isWorkspaces() ? "yarn" : "npm";
		println("");
		println("Init done. Please run '" + cmd + " install' to install dependencies.");
		println("");
		println("The following scripts are available:");
		println("  '" + cmd + " run n4jsc [-- args]' -  run n4jsc with arguments. E.g. 'npm run n4jsc -- --help'");
		println("  '" + cmd + " run build'           -  build this project with N4JS compiler.");
		if (config.hasScript("test")) {
			println("  '" + cmd + " run test'            -  execute project tests.");
		}

		return N4jscExitState.SUCCESS;
	}

	private static File getParentPackageJson(N4jscOptions options) {
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

	private static WorkingDirState checkAndGetWorkingDirState(N4jscOptions options, File parentPackageJson)
			throws N4jscException {

		Path cwd = options.getWorkingDirectory();
		boolean cwdHasPackageJson = parentPackageJson != null && parentPackageJson.exists()
				&& parentPackageJson.getParentFile().equals(cwd.toFile());

		if (options.isN4JS()) {
			if (cwdHasPackageJson) {
				return WorkingDirState.InExistingProject;
			} else {
				throw new N4jscException(N4jscExitCode.INIT_ERROR_WORKING_DIR,
						"Given option --n4js requires a package.json file to be in the current working directory.");
			}
		}

		if (!options.isCreate()) {
			if (cwdHasPackageJson) {
				throw new N4jscException(N4jscExitCode.INIT_ERROR_WORKING_DIR,
						"Current working directory must not contain a package.json file. Note:" + NL
								+ "  In case you like to add the n4js property to an existing project, use option --n4js"
								+ NL
								+ "  In case you like to add a project to an existing workspace project, use options -w -c");
			}

			if (options.isScope() && !cwd.getParent().toFile().getName().startsWith("@")) {
				throw new N4jscException(N4jscExitCode.INIT_ERROR_WORKING_DIR,
						"When creating a scoped package the parent directory of current working directory must start with '@'.");
			}
		}

		if (parentPackageJson == null || !parentPackageJson.exists()) {
			return WorkingDirState.InEmptyFolder;
		}

		// there is a package.json file in the parent directory of cwd

		try (JsonReader jReader = new JsonReader(new FileReader(parentPackageJson))) {
			JsonElement packageJsonCandidate = JsonParser.parseReader(jReader);
			if (!packageJsonCandidate.isJsonObject()) {
				return WorkingDirState.InEmptyFolder;
			}

			JsonObject packageJson = (JsonObject) packageJsonCandidate;
			boolean isYarnProject = packageJson.has(PackageJsonProperties.WORKSPACES_ARRAY.name);
			if (!isYarnProject) {
				throw new N4jscException(N4jscExitCode.INIT_ERROR_WORKING_DIR,
						"Current working directory is inside the non-yarn project of " + parentPackageJson);
			}
		} catch (N4jscException e) {
			throw e;
		} catch (Exception e) {
			throw new N4jscException(N4jscExitCode.INIT_ERROR_WORKING_DIR,
					"Working directory must be either empty or inside a yarn project.", e);
		}

		// cwd is somewhere inside a yarn project

		if (!options.isWorkspaces()) {
			throw new N4jscException(N4jscExitCode.INIT_ERROR_WORKING_DIR,
					"Creating a new project inside an existing yarn project requires option '--workspaces' to be set.");
		}

		Path candidateWorkDir = parentPackageJson.getParentFile().toPath();
		if (candidateWorkDir.equals(cwd)) {
			return WorkingDirState.InYarnProjectRoot;
		}

		YarnPackageJsonContents yarnPackageJson = YarnPackageJsonContents.read(candidateWorkDir);
		Path yarnRoot = parentPackageJson.getParentFile().toPath();
		boolean isCwdWorkspaceMatch = workspaceMatch(yarnPackageJson.workspaces, yarnRoot, cwd);
		if (!options.isCreate() && !isCwdWorkspaceMatch) {
			throw new N4jscException(N4jscExitCode.INIT_ERROR_WORKING_DIR,
					"Creating a new project inside a yarn project requires the current working directory to "
							+ "be inside a new project folder of a valid workspaces directory of the yarn project."
							+ "Alternatively add option '--create' to create a new project directory.");
		}

		if (isCwdWorkspaceMatch) {
			return WorkingDirState.InYarnProjectEmptyPackage;
		}

		boolean isCwdWorkspaceParent = workspaceMatch(yarnPackageJson.workspaces, yarnRoot, cwd.resolve("test"));
		if (isCwdWorkspaceParent) {
			return WorkingDirState.InYarnProjectWorkspaces;
		}

		return WorkingDirState.InYarnProject;

	}

	private static N4jscExitState setDirectories(N4jscOptions options, InitConfiguration config, File parentPackageJson,
			WorkingDirState workingDirState) throws N4jscException {

		Path cwd = options.getWorkingDirectory();

		if (options.isN4JS()) {
			checkState(workingDirState == WorkingDirState.InExistingProject); // ensured before
			config.projectRoot = cwd;

		} else if (options.isCreate()) {
			if (options.isWorkspaces()) {
				// a new yarn workspace is created in the cwd along with a single project
				checkState(workingDirState != WorkingDirState.InExistingProject); // ensured before

				switch (workingDirState) {
				case InEmptyFolder: {
					Path yarnRoot = cwd.resolve(config.yarnPackageJson.name);
					Path projectRoot = yarnRoot.resolve("packages").resolve(config.packageJson.name);
					setConfigDirs(config, yarnRoot, projectRoot);
					break;
				}
				case InExistingProject:
					checkState(false); // ensured before
					break;
				case InYarnProjectRoot: {
					String workspacesDir = config.getWorkspacesDirectory();
					Path projectRoot = cwd.resolve(workspacesDir).resolve(config.packageJson.name);
					setConfigDirs(config, cwd, projectRoot);
					break;
				}
				case InYarnProjectWorkspaces: {
					Path yarnRoot = parentPackageJson.getParentFile().toPath();
					Path projectRoot = cwd.resolve(config.packageJson.name);
					setConfigDirs(config, yarnRoot, projectRoot);
					break;
				}
				case InYarnProjectEmptyPackage: {
					Path yarnRoot = parentPackageJson.getParentFile().toPath();
					setConfigDirs(config, yarnRoot, cwd);
					break;
				}
				case InYarnProject: {
					String workspacesDir = config.getWorkspacesDirectory();
					Path yarnRoot = parentPackageJson.getParentFile().toPath();
					Path projectRoot = yarnRoot.resolve(workspacesDir).resolve(config.packageJson.name);
					setConfigDirs(config, yarnRoot, projectRoot);
					break;
				}
				default:
					checkState(false); // ensured before
					break;
				}
			} else {
				// a single project is created in a new folder in the cwd
				checkState(workingDirState == WorkingDirState.InEmptyFolder); // ensured before
				config.projectRoot = cwd.resolve(config.packageJson.name);
			}

		} else {
			if (options.isWorkspaces()) {
				if (workingDirState == WorkingDirState.InEmptyFolder) {
					// in the cwd both a new yarn project and a new package project are initialized
					Path yarnRoot = cwd;
					Path projectRoot = yarnRoot.resolve("packages").resolve(config.packageJson.name);
					setConfigDirs(config, yarnRoot, projectRoot);
				} else if (workingDirState == WorkingDirState.InYarnProjectEmptyPackage) {
					// in an existing valid workspaces project directory a new project is initialized
					setConfigDirs(config, cwd.resolve(parentPackageJson.getParentFile().toPath()), cwd);
				} else {
					checkState(false); // ensured before
				}
			} else {
				// in the cwd a new project is initialized
				checkState(workingDirState == WorkingDirState.InEmptyFolder); // ensured before
				config.projectRoot = cwd;
			}

		}

		return N4jscExitState.SUCCESS;
	}

	private static void setConfigDirs(InitConfiguration config, Path yarnRootDir, Path projectDir) {
		Path packagesDir = projectDir.getParent();
		packagesDir = packagesDir.toFile().getName().startsWith("@") ? packagesDir.getParent() : packagesDir;

		config.yarnRoot = yarnRootDir;
		config.workspacesDir = packagesDir;
		config.projectRoot = projectDir;

		// FIXME: GH-2143
		if (config.packageJson.name.equals(config.yarnRoot.getFileName().toString())) {
			config.packageJson.name = config.packageJson.name + "2";
			config.projectRoot = config.workspacesDir.resolve(config.packageJson.name);
		}
	}

	private static boolean workspaceMatch(String[] globs, Path root, Path newProjectLocation) {
		Path relProjectLocation = root.relativize(newProjectLocation);
		for (String glob : globs) {
			boolean matches = ModuleFilterUtils.locationMatchesGlobSpecifier(glob, relProjectLocation);
			if (matches) {
				return true;
			}
		}
		return false;
	}

	private static N4jscExitState initYarnProject(InitConfiguration config) throws N4jscException {
		config.workspacesDir.toFile().mkdirs();
		Gson gson = JsonUtils.createGson();
		String yarnJsonString = gson.toJson(config.yarnPackageJson);
		try (FileWriter fw = new FileWriter(config.yarnRoot.resolve(N4JSGlobals.PACKAGE_JSON).toFile())) {
			fw.write(yarnJsonString);
		} catch (IOException e) {
			throw new N4jscException(N4jscExitCode.INIT_ERROR_WORKING_DIR, e);
		}

		return N4jscExitState.SUCCESS;
	}

	private static N4jscExitState initProject(N4jscOptions options, InitConfiguration config)
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
		InEmptyFolder, InYarnProjectRoot, InYarnProjectWorkspaces, InYarnProjectEmptyPackage, InYarnProject, InExistingProject
	}

}
