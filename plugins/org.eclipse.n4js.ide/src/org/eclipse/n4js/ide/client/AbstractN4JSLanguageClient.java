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
package org.eclipse.n4js.ide.client;

import java.util.concurrent.CompletableFuture;

import org.eclipse.emf.common.util.URI;
import org.eclipse.lsp4j.MessageActionItem;
import org.eclipse.lsp4j.ShowMessageRequestParams;
import org.eclipse.lsp4j.services.LanguageClient;
import org.eclipse.n4js.ide.xtext.server.build.XBuildRequest.AfterDeleteListener;
import org.eclipse.n4js.ide.xtext.server.build.XBuildRequest.AfterGenerateListener;

/**
 * Abstract base class for N4JS language clients, i.e. implementations of {@link LanguageClient}.
 */
public abstract class AbstractN4JSLanguageClient implements LanguageClient, AfterGenerateListener, AfterDeleteListener {

	@Override
	public void telemetryEvent(Object object) {
		// ignored by default
	}

	@Override
	public CompletableFuture<MessageActionItem> showMessageRequest(ShowMessageRequestParams requestParams) {
		// ignored by default
		return null;
	}

	@Override
	public void afterGenerate(URI source, URI generated) {
		// ignored by default
	}

	@Override
	public void afterDelete(URI file) {
		// ignored by default
	}
}
