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
package org.eclipse.n4js.cli;

import org.eclipse.n4js.cli.compiler.N4jscCompiler;
import org.eclipse.n4js.cli.lsp.LspServer;

/**
 *
 */
@SuppressWarnings("unused")
public class N4jscBackend {

	/** Runs the cli goal {@code compile} with the given options */
	public void goalCompile(N4jscOptions options) throws Exception {
		N4jscCompiler.start(options);
	}

	/** Runs the cli goal {@code clean} with the given options */
	public void goalClean(N4jscOptions options) throws Exception {
		N4jscCompiler.start(options);
	}

	/** Runs the cli goal {@code lsp} with the given options */
	public void goalLsp(N4jscOptions options) throws Exception {
		LspServer.start(options);
	}

	/** Runs the cli goal {@code api} with the given options */
	public void goalApi(N4jscOptions options) throws Exception {
		throw new N4jscException(N4jscExitCode.NOT_IMPLEMENTED);
	}

	/** Runs the cli goal {@code watch} with the given options */
	public void goalWatch(N4jscOptions options) throws Exception {
		throw new N4jscException(N4jscExitCode.NOT_IMPLEMENTED);
	}
}
