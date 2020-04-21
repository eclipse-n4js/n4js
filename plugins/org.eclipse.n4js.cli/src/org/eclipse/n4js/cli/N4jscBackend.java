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
 * The {@link N4jscBackend} delegates each goal to the corresponding implementation
 */
@SuppressWarnings("unused")
public class N4jscBackend {

	/** Runs the cli goal {@code compile} with the given options */
	public N4jscExitState goalCompile(N4jscOptions options) throws Exception {
		return N4jscCompiler.start(options);
	}

	/** Runs the cli goal {@code clean} with the given options */
	public N4jscExitState goalClean(N4jscOptions options) throws Exception {
		return N4jscCompiler.start(options);
	}

	/** Runs the cli goal {@code lsp} with the given options */
	public N4jscExitState goalLsp(N4jscOptions options) throws Exception {
		LspServer.start(options);
		return N4jscExitState.SUCCESS;
	}

	/** Runs the cli goal {@code api} with the given options */
	public N4jscExitState goalApi(N4jscOptions options) throws Exception {
		throw new N4jscException(N4jscExitCode.NOT_IMPLEMENTED);
	}

	/** Runs the cli goal {@code watch} with the given options */
	public N4jscExitState goalWatch(N4jscOptions options) throws Exception {
		throw new N4jscException(N4jscExitCode.NOT_IMPLEMENTED);
	}
}
