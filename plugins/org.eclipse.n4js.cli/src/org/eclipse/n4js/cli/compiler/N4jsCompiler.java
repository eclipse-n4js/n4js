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

import org.eclipse.n4js.cli.N4jscFactory;
import org.eclipse.n4js.cli.N4jscOptions;
import org.eclipse.n4js.ide.server.N4JSLanguageServerImpl;

import com.google.inject.Injector;

/**
 *
 */
public class N4jsCompiler {
	private final N4jscOptions options;
	private final Injector injector;
	private final N4JSLanguageServerImpl languageServer;

	/** Starts the compiler in a blocking fashion */
	static public void start(N4jscOptions options) throws Exception {
		N4jsCompiler compiler = new N4jsCompiler(options);
		compiler.start();
	}

	private N4jsCompiler(N4jscOptions options) {
		this.options = options;
		this.injector = N4jscFactory.createInjector();
		this.languageServer = N4jscFactory.createLanguageServer(injector);
	}

	/** Starts the compiler in a blocking fashion */
	public void start() throws Exception {
	}
}
