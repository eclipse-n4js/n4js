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
import org.eclipse.n4js.cli.N4jscException;
import org.eclipse.n4js.cli.N4jscExitCode;
import org.eclipse.n4js.cli.N4jscFactory;
import org.eclipse.n4js.cli.N4jscOptions;
import org.eclipse.n4js.ide.server.N4JSLanguageServerImpl;
import org.eclipse.n4js.ide.server.N4JSWorkspaceManager;
import org.eclipse.xtext.workspace.IProjectConfig;

import com.google.inject.Injector;

/**
 * The entry point for all cli calls with the goal 'compile'
 */
@SuppressWarnings("restriction")
public class N4jscCompiler {
	private final N4jscOptions options;
	private final Injector injector;
	private final N4JSLanguageServerImpl languageServer;
	private final N4jscLanguageClient callback;
	private final N4JSWorkspaceManager workspaceManager;

	/** Starts the compiler in a blocking fashion */
	static public void start(N4jscOptions options) throws Exception {
		N4jscCompiler compiler = new N4jscCompiler(options);
		compiler.start();
	}

	private N4jscCompiler(N4jscOptions options) {
		this.injector = N4jscFactory.createInjector();
		this.options = options;
		this.languageServer = N4jscFactory.getLanguageServer(injector);
		this.callback = N4jscFactory.getLanguageClient(injector);
		this.workspaceManager = N4jscFactory.getWorkspaceManager(injector);
		this.languageServer.connect(callback);
	}

	/** Starts the compiler in a blocking fashion */
	public void start() throws Exception {
		InitializeParams params = new InitializeParams();
		List<File> srcs = options.getSrcFiles();
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
			languageServer.joinInitialized();
			languageServer.shutdown();
			languageServer.exit();

		} else {
			throw new N4jscException(N4jscExitCode.ERROR_UNEXPECTED, "No root directory");
		}
	}

	private void warnIfNoProjectsFound() {
		Set<? extends IProjectConfig> projects = workspaceManager.getWorkspaceConfig().getProjects();
		if (projects.isEmpty()) {
			System.out.println("No projects found at the given location: " + options.getSrcFiles().get(0));
		}
	}

	private void verbosePrintAllProjects() {
		if (options.isVerbose()) {
			Set<? extends IProjectConfig> projects = workspaceManager.getWorkspaceConfig().getProjects();
			if (!projects.isEmpty()) {
				Path workspace = options.getSrcFiles().get(0).toPath();
				List<String> projectNameList = projects.stream()
						.map(p -> p.getName() + " at " + Path.of(p.getPath().toFileString()).relativize(workspace))
						.collect(toList());

				System.out.println("Projects:");
				System.out.print("   " + String.join("\n   ", projectNameList));
			}
		}
	}
}
