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

import static org.eclipse.n4js.cli.N4jscTestOptions.COMPILE;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.mwe2.runtime.workflow.IWorkflowComponent;
import org.eclipse.emf.mwe2.runtime.workflow.IWorkflowContext;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.cli.N4jscMain;
import org.eclipse.n4js.cli.helper.CliCompileResult;
import org.eclipse.n4js.cli.helper.CliTools;
import org.eclipse.n4js.utils.UtilN4;
import org.eclipse.n4js.utils.io.FileDeleter;

import com.google.common.base.Throwables;

/**
 * Builds the libraries under top-level folder "n4js-libs". For details see {@link #buildN4jsLibs()}.
 */
public class BuildN4jsLibs implements IWorkflowComponent {

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
		println("==== STEP 1/2: remove all node_modules folders top-level folder \"" + N4JSGlobals.N4JS_LIBS_FOLDER_NAME
				+ "\" in n4js repository:");
		removeNodeModulesFolders(n4jsLibsRoot);

		// step 2: compile projects under top-level folder "n4js-libs"
		println("==== STEP 2/2: compiling code under top-level folder \"" + N4JSGlobals.N4JS_LIBS_FOLDER_NAME
				+ "\" in n4js repository:");
		compile(n4jsLibsRootPath);

		println("==== BUILD N4JS-LIBS finished ====");
	}

	private static void removeNodeModulesFolders(File... foldersContainingProjectFolders) throws IOException {
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

	private static void compile(Path n4jsLibsRootPath) {
		try {
			CliTools cliTools = new CliTools();
			cliTools.setInheritIO(true);
			cliTools.setEnvironmentVariable("NPM_TOKEN", "dummy");

			cliTools.yarnInstall(n4jsLibsRootPath);

			CliCompileResult compileResult = new CliCompileResult();
			cliTools.callN4jscInprocess(COMPILE(n4jsLibsRootPath.toFile()), false, compileResult);

		} catch (Exception e) {
			println("EXCEPTION while compiling n4js-libs:");
			e.printStackTrace();

			Throwable root = Throwables.getRootCause(e);
			if (root != e) {
				println("Root cause:");
				root.printStackTrace();
			}

			Throwables.throwIfUnchecked(e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * Don't use a log4j logger here, because:
	 * <ol>
	 * <li>the call to {@link N4jscMain#main(String...)} will alter the dependency injection and logger configuration,
	 * <li>we want to make sure the messages printed via this method always show up.
	 * </ol>
	 */
	private static void println(String message) {
		System.out.println(message);
	}
}
