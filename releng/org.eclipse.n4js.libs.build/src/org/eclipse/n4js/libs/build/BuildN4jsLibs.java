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

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.mwe2.runtime.workflow.IWorkflowComponent;
import org.eclipse.emf.mwe2.runtime.workflow.IWorkflowContext;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.cli.N4jscMain;
import org.eclipse.n4js.cli.N4jscTestOptions;
import org.eclipse.n4js.cli.helper.CliCompileResult;
import org.eclipse.n4js.cli.helper.CliTools;
import org.eclipse.n4js.cli.helper.CliTools.CliException;
import org.eclipse.n4js.utils.UtilN4;
import org.eclipse.n4js.utils.io.FileDeleter;

import com.google.common.base.Preconditions;
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
		final Path n4jsLibsRootPath = n4jsRootPath.resolve(N4JSGlobals.N4JS_LIBS_FOLDER_NAME);

		// step 1: clean
		println("==== STEP 1/2: remove all node_modules folders below top-level folder \""
				+ N4JSGlobals.N4JS_LIBS_FOLDER_NAME + "\" in n4js repository:");
		removeNodeModulesFolders(n4jsLibsRootPath);

		// step 2: compile projects under top-level folder "n4js-libs"
		println("==== STEP 2/2: compiling code under top-level folder \"" + N4JSGlobals.N4JS_LIBS_FOLDER_NAME
				+ "\" in n4js repository:");
		compile(n4jsLibsRootPath);

		println("==== BUILD N4JS-LIBS finished ====");
	}

	private static void removeNodeModulesFolders(Path folder) throws IOException {
		final List<Path> nodeModulesFolders = new ArrayList<>();
		Files.walkFileTree(folder, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
				if (N4JSGlobals.NODE_MODULES.equals(dir.getFileName().toString())) {
					nodeModulesFolders.add(dir);
					return FileVisitResult.SKIP_SUBTREE;
				}
				return FileVisitResult.CONTINUE;
			}
		});
		for (Path nodeModulesFolder : nodeModulesFolders) {
			println("Deleting node_modules folder: " + nodeModulesFolder);
			FileDeleter.delete(nodeModulesFolder);
		}
	}

	private static void compile(Path n4jsLibsRootPath) {
		CliCompileResult compileResult = new CliCompileResult();
		try {
			CliTools cliTools = new CliTools();
			cliTools.setInheritIO(true);
			cliTools.setEnvironmentVariable("NPM_TOKEN", "dummy");

			cliTools.yarnInstall(n4jsLibsRootPath);

			// we want '--clean' but *not* '--noPersist'
			N4jscTestOptions options = COMPILE(false, n4jsLibsRootPath.toFile()).clean();
			Preconditions.checkState(options.isClean());
			Preconditions.checkState(!options.isNoPersist());

			cliTools.callN4jscInprocess(options, false, compileResult);

		} catch (CliException e) {
			if (compileResult.getException() != null) {
				throw new RuntimeException(compileResult.getException());

			} else if (compileResult.getErrs() > 0) {
				// this happens when there are compile errors in the n4js-libs
				// --> the below code would emit the entire output of n4jsc several times leading to excessive output
				// (also, the compile errors were already reported by n4jsc to the console), so we exit early in this
				// case to suppress further error reporting:
				throw new RuntimeException("Errors while compiling n4js-libs");

			} else {
				throw new RuntimeException(e);
			}

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
