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
package org.eclipse.n4js.cli.compiler;

import static java.util.stream.Collectors.toList;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;

import org.eclipse.lsp4j.InitializeParams;
import org.eclipse.lsp4j.InitializedParams;
import org.eclipse.n4js.cli.N4jscConsole;
import org.eclipse.n4js.cli.N4jscException;
import org.eclipse.n4js.cli.N4jscExitCode;
import org.eclipse.n4js.cli.N4jscFactory;
import org.eclipse.n4js.cli.N4jscOptions;
import org.eclipse.n4js.ide.xtext.server.DefaultBuildRequestFactory;
import org.eclipse.n4js.ide.xtext.server.ProjectStatePersisterConfig;
import org.eclipse.n4js.ide.xtext.server.XLanguageServerImpl;
import org.eclipse.n4js.ide.xtext.server.XWorkspaceManager;
import org.eclipse.n4js.smith.Measurement;
import org.eclipse.n4js.smith.N4JSDataCollectors;
import org.eclipse.n4js.tester.TestCatalogSupplier;
import org.eclipse.xtext.workspace.IProjectConfig;

import com.google.common.base.Stopwatch;
import com.google.inject.Injector;

/**
 * The entry point for all cli calls with the goal 'compile'
 */
@SuppressWarnings("restriction")
public class N4jscCompiler {
	private final N4jscOptions options;
	private final XLanguageServerImpl languageServer;
	private final N4jscLanguageClient callback;
	private final XWorkspaceManager workspaceManager;

	/** Starts the compiler in a blocking fashion */
	static public void start(N4jscOptions options) throws Exception {
		N4jscCompiler compiler = new N4jscCompiler(options);

		try (Measurement m = N4JSDataCollectors.dcCliCompile.getMeasurement(options.toString())) {
			compiler.start();
		}
	}

	private N4jscCompiler(N4jscOptions options) {
		this.options = options;
		this.languageServer = N4jscFactory.getLanguageServer();
		this.callback = N4jscFactory.getLanguageClient();
		this.workspaceManager = N4jscFactory.getWorkspaceManager();

		setPersistionOptions();
		this.languageServer.connect(callback);
		setupWorkspaceBuildActionListener();
	}

	/** Starts the compiler in a blocking fashion */
	public void start() throws Exception {
		InitializeParams params = new InitializeParams();
		File baseDir = options.getDir();
		if (baseDir == null) {
			throw new N4jscException(N4jscExitCode.ARGUMENT_DIRS_INVALID, "No base directory");
		}

		Stopwatch compilationTime = Stopwatch.createStarted();
		params.setRootUri(baseDir.toURI().toString());
		languageServer.initialize(params).get();
		warnIfNoProjectsFound();
		verbosePrintAllProjects();

		languageServer.initialized(new InitializedParams());
		languageServer.joinInitBuildFinished();

		languageServer.shutdown();
		languageServer.exit();

		printResults(compilationTime.stop());
		writeTestCatalog();
	}

	private void setupWorkspaceBuildActionListener() {
		Injector injector = N4jscFactory.getOrCreateInjector();
		DefaultBuildRequestFactory buildRequestFactory = injector.getInstance(DefaultBuildRequestFactory.class);
		buildRequestFactory.setAfterGenerateListener(callback);
		buildRequestFactory.setAfterDeleteListener(callback);
	}

	private void setPersistionOptions() {
		Injector injector = N4jscFactory.getOrCreateInjector();
		ProjectStatePersisterConfig persisterConfig = injector.getInstance(ProjectStatePersisterConfig.class);
		persisterConfig.setDeleteState(options.isClean());
		persisterConfig.setWriteToDisk(!options.isNoPersist());
	}

	private void warnIfNoProjectsFound() {
		Set<? extends IProjectConfig> projects = workspaceManager.getWorkspaceConfig().getProjects();
		if (projects.isEmpty()) {
			N4jscConsole.println("No projects found at the given location: " + options.getDirs().get(0));
		}
	}

	private void verbosePrintAllProjects() {
		if (options.isVerbose()) {
			Set<? extends IProjectConfig> projects = workspaceManager.getWorkspaceConfig().getProjects();
			if (!projects.isEmpty()) {
				Path workspace = options.getDirs().get(0).toPath();
				List<String> projectNameList = projects.stream()
						.map(p -> p.getName() + " at " + Path.of(p.getPath().toFileString()).relativize(workspace))
						.collect(toList());

				N4jscConsole.println("Projects:");
				N4jscConsole.print("   " + String.join("\n   ", projectNameList));
			}
		}
	}

	private void printResults(Stopwatch elapsedTime) {
		long trsnp = callback.getTranspilationsCount();
		long deltd = callback.getDeletionsCount();
		long errs = callback.getErrorsCount();
		long wrns = callback.getWarningsCount();
		String durationStr = elapsedTime.toString();
		String msg = String.format(
				"Compile results - Transpiled: %d, Deleted: %d, Errors: %d, Warnings: %d, Duration: %s",
				trsnp, deltd, errs, wrns, durationStr);
		N4jscConsole.println(msg);
	}

	private void writeTestCatalog() throws N4jscException {
		File testCatalogFile = options.getTestCatalog();
		if (testCatalogFile != null) {
			Injector injector = N4jscFactory.getOrCreateInjector();
			TestCatalogSupplier testCatalogSupplier = injector.getInstance(TestCatalogSupplier.class);

			String catalog = testCatalogSupplier.get(true); // do not include "endpoint" property here

			try (FileOutputStream fos = new FileOutputStream(testCatalogFile)) {
				fos.write(catalog.getBytes());
				fos.flush();

			} catch (IOException e) {
				String msg = "Error while writing test catalog file at: " + testCatalogFile;
				throw new N4jscException(N4jscExitCode.TEST_CATALOG_ASSEMBLATION_ERROR, msg);
			}

			N4jscConsole.println("Test catalog written to " + testCatalogFile.toPath());
		}
	}

}
