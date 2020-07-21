/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.server;

import java.util.concurrent.CompletableFuture;

import org.apache.log4j.lf5.LogLevel;
import org.eclipse.lsp4j.jsonrpc.services.JsonRequest;
import org.eclipse.lsp4j.jsonrpc.services.JsonSegment;

/**
 * The {@link DebugService} uses a separate endpoint to avoid the infrastructure of other calls to ordinary LSP
 * end-points and hence to increase robustness in case of errors in the source code.
 */
@JsonSegment("debug")
public interface DebugService {

	/** Sets the level of verbosity of Log4j. @see {@link LogLevel} */
	@JsonRequest
	CompletableFuture<Void> setVerboseLevel(String level);

	/** Prints debug information on the output channel */
	@JsonRequest
	CompletableFuture<Void> printDebugInfo();

}
