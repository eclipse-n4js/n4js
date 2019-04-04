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
package org.eclipse.n4js.libs.build;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.mwe2.runtime.workflow.IWorkflowComponent;
import org.eclipse.emf.mwe2.runtime.workflow.IWorkflowContext;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.N4JSStandaloneSetup;
import org.eclipse.n4js.binaries.BinariesLocatorHelper;
import org.eclipse.n4js.binaries.nodejs.NodeYarnProcessBuilder;
import org.eclipse.n4js.hlc.base.ExitCodeException;
import org.eclipse.n4js.hlc.base.N4jscBase;
import org.eclipse.n4js.json.JSON.JSONDocument;
import org.eclipse.n4js.json.JSON.JSONObject;
import org.eclipse.n4js.json.JSON.NameValuePair;
import org.eclipse.n4js.json.model.utils.JSONModelUtils;
import org.eclipse.n4js.utils.UtilN4;
import org.eclipse.n4js.utils.io.FileDeleter;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import com.google.inject.Injector;

/**
 * Builds the libraries under top-level folder "n4js-libs". For details see {@link #buildN4jsLibs()}.
 */
public class BuildN4jsLibs implements IWorkflowComponent {

	private static final String NPM_INSTALL = "npm install";
	private static final String PATH = "PATH";

	@Override
	public void preInvoke() {
		// ignore
	}

	@Override
	public void invoke(IWorkflowContext ctx) {
		try {
			buildN4jsLibs();
		} catch (IOException e) {
			throw new RuntimeException(e); // let MWE2 runner report the exception
		}
	}

	@Override
	public void postInvoke() {
		// ignore
	}

	/**
	 * Compiles all code under top-level folder "n4js-libs" and runs "npm install" in "n4js-runtime".
	 */
	public static void buildN4jsLibs() throws IOException {
		println("==== Running BUILD N4JS-LIBS ====");

		final Path n4jsRootPath = UtilN4.findN4jsRepoRootPath();
		final Path n4jsLibsRootPath = n4jsRootPath.resolve(N4JSGlobals.N4JS_LIBS_SOURCES_PATH);
		final File n4jsLibsRoot = n4jsLibsRootPath.toFile();

		// step 1: clean
		println("==== STEP 1/3: cleaning projects under top-level folder \"" + N4JSGlobals.N4JS_LIBS_FOLDER_NAME
				+ "\" in n4js repository:");
		clean(n4jsLibsRoot);

		// step 2: compile projects under top-level folder "n4js-libs"
		println("==== STEP 2/3: compiling code under top-level folder \"" + N4JSGlobals.N4JS_LIBS_FOLDER_NAME
				+ "\" in n4js repository:");
		compile(n4jsLibsRoot);

		// step 3: make "nj4s-runtime" self-contained (so that it includes a node_modules folder with all its
		// dependencies)
		println("==== STEP 3/3: make project \"" + N4JSGlobals.N4JS_RUNTIME_NAME
				+ "\" self-contained (required only for tests)");

		println("==== STEP 3a: create symbolic links in " + N4JSGlobals.N4JS_RUNTIME_NAME + "/"
				+ N4JSGlobals.NODE_MODULES + " to sibling folders (iff they appear as dependency in package.json)");
		final Path n4jsRuntimePath = n4jsLibsRootPath.resolve(N4JSGlobals.N4JS_RUNTIME_NAME);
		preinstallRequiredSiblingProjects(n4jsRuntimePath);

		println("==== STEP 3b: running \"" + NPM_INSTALL + "\" in project \"" + N4JSGlobals.N4JS_RUNTIME_NAME
				+ "\" (to install remaining, third-party dependencies)");
		runNpmInstall(n4jsRuntimePath);

		println("==== BUILD N4JS-LIBS finished ====");
	}

	private static void clean(File... foldersContainingProjectFolders) throws IOException {
		final String foldersContainingProjectsStr = Stream.of(foldersContainingProjectFolders)
				.map(file -> file.getAbsolutePath())
				.collect(Collectors.joining(File.pathSeparator));

		// clean projects using the headless builder (to get rid of generated output code, etc.)
		final String[] args = {
				"--clean",
				"--buildType", "allprojects",
				"--projectlocations", foldersContainingProjectsStr,
		};
		println("Running (via #doMain()): n4jsc.jar " + Joiner.on(" ").join(args));
		try {
			new N4jscBase().doMain(args);
		} catch (ExitCodeException e) {
			println("ERROR: while cleaning the projects, an ExitCodeException is thrown; "
					+ "code: " + e.getExitCode() + ", "
					+ "message: " + e.getMessage());
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		// remove all node_modules folders
		for (File folder : foldersContainingProjectFolders) {
			List<File> nodeModulesFolders = Files.walk(folder.toPath())
					.map(path -> path.toFile())
					.filter(file -> file.isDirectory() && file.getName().equals(N4JSGlobals.NODE_MODULES))
					.collect(Collectors.toList());
			for (File nodeModulesFolder : nodeModulesFolders) {
				println("Deleting node_modules folder: " + nodeModulesFolder);
				FileDeleter.delete(nodeModulesFolder);
			}
		}
	}

	private static void compile(File... foldersContainingProjectFolders) {
		final String foldersContainingProjectsStr = Stream.of(foldersContainingProjectFolders)
				.map(file -> file.getAbsolutePath())
				.collect(Collectors.joining(File.pathSeparator));

		final String[] args = {
				"--buildType", "allprojects",
				"--installMissingDependencies",
				"--projectlocations", foldersContainingProjectsStr
		};
		try {
			NodeYarnProcessBuilder.additionalEnvironmentVariables.put("NPM_TOKEN", "dummy");
			new N4jscBase().doMain(args);
		} catch (ExitCodeException e) {
			println("ERROR: headless compiler threw ExitCodeException (probably code compiled with errors); "
					+ "exit code: " + e.getExitCode() + ", "
					+ "message: " + e.getMessage());
			e.printStackTrace();
			Throwable root = Throwables.getRootCause(e);
			if (root != e) {
				println("Root cause:");
				root.printStackTrace();
			}
			throw new RuntimeException(e);
		} finally {
			NodeYarnProcessBuilder.additionalEnvironmentVariables.clear();
		}
	}

	private static void preinstallRequiredSiblingProjects(Path projectPath) throws IOException {
		// collect sibling folders
		Map<String, Path> siblingFoldersByName = Files.list(projectPath.getParent())
				.collect(Collectors.toMap(file -> file.getFileName().toString(), file -> file));

		// create node_modules folder
		Path nodeModulesPath = projectPath.resolve(N4JSGlobals.NODE_MODULES);
		Files.createDirectories(nodeModulesPath); // does not fail if folder already exists

		// go through dependencies of project at 'projectPath' and create symbolic links for
		// required projects that are available as a sibling folder
		Path packageJsonPath = projectPath.resolve(N4JSGlobals.PACKAGE_JSON);
		JSONDocument packageJsonDoc = JSONModelUtils.loadJSON(packageJsonPath, Charsets.UTF_8);
		JSONObject dependenciesObj = (JSONObject) JSONModelUtils.getProperty(packageJsonDoc, "dependencies").get();
		for (NameValuePair nvp : dependenciesObj.getNameValuePairs()) {
			String projectName = nvp.getName();
			Path siblingFolder = siblingFoldersByName.get(projectName);
			if (siblingFolder != null) {
				Path link = nodeModulesPath.resolve(projectName);
				Path target = siblingFolder;
				println("Creating symbolic link:\n"
						+ "    at: " + link + "\n"
						+ "    to: " + target);
				Files.createSymbolicLink(link, target);
			}
		}
	}

	private static void runNpmInstall(Path workingDirectory) throws IOException {
		final String nodePath = findNodePath();
		if (nodePath == null || nodePath.isEmpty()) {
			throw new IllegalStateException("unable to obtain location of node binary");
		}

		String npm = nodePath + File.separator + N4JSGlobals.NPM;
		final String[] cmd = new String[] { npm, "install" };

		final ProcessBuilder pb = new ProcessBuilder(cmd);
		pb.inheritIO();
		pb.directory(workingDirectory.toFile());
		final Map<String, String> environment = pb.environment();
		environment.put("NPM_TOKEN", "dummy");
		final String currentPathValue = environment.get(PATH);
		if (Strings.isNullOrEmpty(currentPathValue)) {
			environment.put(PATH, nodePath);
		} else {
			environment.put(PATH, currentPathValue + File.pathSeparator + nodePath);
		}

		try {
			println("Invoking: " + String.join(" ", cmd));
			println("    in working directory: " + workingDirectory);
			final Process p = pb.start();
			final int exitCode = p.waitFor();
			if (exitCode != 0) {
				throw new IllegalStateException("npm exited with non-zero exit code: " + exitCode);
			}
			println("Finished \"" + NPM_INSTALL + "\".");
		} catch (Throwable th) {
			println("Error while running \"" + NPM_INSTALL + "\":");
			th.printStackTrace();
			throw new RuntimeException(th);
		}

		Path packageJsonLock = workingDirectory.resolve("package-lock.json");
		if (Files.exists(packageJsonLock)) {
			println("Deleting package.json lock-file: " + packageJsonLock);
			Files.delete(packageJsonLock);
		}
	}

	// BinariesLocatorHelper#findNodePath() does not truly require injection; it should be refactored into a static
	// utility method and then this method will no longer need to create an injector:
	private static String findNodePath() {
		Injector injector = N4JSStandaloneSetup.doSetup();
		final BinariesLocatorHelper locator = injector.getInstance(BinariesLocatorHelper.class);
		return locator.findNodePath().toString();
	}

	/**
	 * Don't use a log4j logger here, because:
	 * <ol>
	 * <li>the call to {@link N4jscBase#doMain(String...)} will alter the dependency injection and logger configuration,
	 * <li>we want to make sure the messages printed via this method always show up.
	 * </ol>
	 */
	private static void println(String message) {
		System.out.println(message);
	}
}
