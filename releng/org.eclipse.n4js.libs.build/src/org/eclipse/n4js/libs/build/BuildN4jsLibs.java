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
import org.eclipse.n4js.utils.UtilN4;
import org.eclipse.n4js.utils.io.FileDeleter;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import com.google.inject.Injector;

/**
 * Builds the libraries under top-level folder "n4js-libs". For details see {@link #buildN4jsLibs()}.
 */
public class BuildN4jsLibs implements IWorkflowComponent {

	/** Name of the n4js-runtime project. */
	private static final String N4JS_RUNTIME_NAME = "n4js-node";

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

		// step 3: run "npm install" in project "n4js-node"
		println("==== STEP 3/3: running \"" + NPM_INSTALL + "\" in project \"" + N4JS_RUNTIME_NAME + "\"");
		final File n4jsNodeFolder = n4jsLibsRootPath.resolve(N4JS_RUNTIME_NAME).toFile();
		runNpmInstall(n4jsNodeFolder);

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

	private static void runNpmInstall(File workingDirectory) {
		final String nodePath = findNodePath();
		if (nodePath == null || nodePath.isEmpty()) {
			throw new IllegalStateException("unable to obtain location of node binary");
		}

		String npm = nodePath + File.separator + N4JSGlobals.NPM;
		final String[] cmd = new String[] { npm, "install" };

		final ProcessBuilder pb = new ProcessBuilder(cmd);
		pb.inheritIO();
		pb.directory(workingDirectory);
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
