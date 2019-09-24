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

import java.io.File;
import java.util.List;

import org.eclipse.lsp4j.InitializeParams;
import org.eclipse.lsp4j.InitializedParams;
import org.eclipse.n4js.cli.N4jscException;
import org.eclipse.n4js.cli.N4jscExitCode;
import org.eclipse.n4js.cli.N4jscFactory;
import org.eclipse.n4js.cli.N4jscOptions;
import org.eclipse.n4js.ide.server.N4JSLanguageServerImpl;

import com.google.inject.Injector;

/**
 *
 */
public class N4jscCompiler {
	private final N4jscOptions options;
	private final Injector injector;
	private final N4JSLanguageServerImpl languageServer;
	private final N4jscCallback callback;

	/** Starts the compiler in a blocking fashion */
	static public void start(N4jscOptions options) throws Exception {
		N4jscCompiler compiler = new N4jscCompiler(options);
		compiler.start();
	}

	private N4jscCompiler(N4jscOptions options) {
		this.options = options;
		this.injector = N4jscFactory.createInjector();
		this.languageServer = N4jscFactory.createLanguageServer(injector);
		this.callback = new N4jscCallback();
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
			languageServer.initialized(new InitializedParams());
			languageServer.shutdown();
			languageServer.exit();

		} else {
			throw new N4jscException(N4jscExitCode.ERROR_UNEXPECTED, "No root directory");
		}
	}
}
