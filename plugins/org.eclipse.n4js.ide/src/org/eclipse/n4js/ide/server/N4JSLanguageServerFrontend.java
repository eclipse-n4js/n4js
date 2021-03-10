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

import org.eclipse.n4js.utils.N4JSLanguageUtils;
import org.eclipse.n4js.xtext.server.LanguageServerFrontend;
import org.eclipse.n4js.xtext.server.util.LspLogger;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Extends {@link LanguageServerFrontend} to customize the welcome message.
 */
@Singleton
public class N4JSLanguageServerFrontend extends LanguageServerFrontend {

	@Inject
	private LspLogger lspLogger;

	@Override
	protected void logWelcomeMessage() {
		lspLogger.log("Connected to N4JS LSP server (" + N4JSLanguageUtils.getLanguageVersion() + ")");
	}
}
