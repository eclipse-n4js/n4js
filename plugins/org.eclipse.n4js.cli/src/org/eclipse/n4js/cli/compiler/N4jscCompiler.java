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
import org.eclipse.n4js.ide.xtext.server.ProjectStatePersisterConfig;
import org.eclipse.n4js.ide.xtext.server.XLanguageServerImpl;
import org.eclipse.n4js.ide.xtext.server.XWorkspaceManager;
import org.eclipse.n4js.smith.Measurement;
import org.eclipse.n4js.smith.N4JSDataCollectors;
import org.eclipse.xtext.workspace.IProjectConfig;

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
	}

	/** Starts the compiler in a blocking fashion */
	public void start() throws Exception {
		InitializeParams params = new InitializeParams();
		List<File> srcs = options.getDirs();
		File firstDir = null;
		for (File src : srcs) {
			if (src.isDirectory()) {
				firstDir = src;
				break;
			}
		}
		if (firstDir != null) {
			params.setRootUri(firstDir.toURI().toString());
			languageServer.initialize(params).get();
			warnIfNoProjectsFound();
			verbosePrintAllProjects();

			languageServer.initialized(new InitializedParams());
			languageServer.joinInitBuildFinished();

			languageServer.shutdown();
			languageServer.exit();

		} else {
			throw new N4jscException(N4jscExitCode.ERROR_UNEXPECTED, "No root directory");
		}
	}

	private void setPersistionOptions() {
		Injector injector = N4jscFactory.getOrCreateInjector();
		ProjectStatePersisterConfig persisterConfig = injector.getInstance(ProjectStatePersisterConfig.class);
		persisterConfig.setDeleteState(options.isClean());
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

}
