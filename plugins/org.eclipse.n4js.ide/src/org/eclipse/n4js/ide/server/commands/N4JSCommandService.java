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
package org.eclipse.n4js.ide.server.commands;

import java.util.List;

import org.eclipse.lsp4j.ExecuteCommandParams;
import org.eclipse.n4js.ide.xtext.server.XLanguageServerImpl;
import org.eclipse.xtext.ide.server.ILanguageServerAccess;
import org.eclipse.xtext.ide.server.commands.IExecutableCommandService;
import org.eclipse.xtext.util.CancelIndicator;

import com.google.common.collect.Lists;
import com.google.inject.Inject;

/**
 * Provides commands for LSP clients
 */
public class N4JSCommandService implements IExecutableCommandService {
	private static final String N4JS_CLEAN = "n4js.clean";

	@Inject
	private XLanguageServerImpl lspServer;

	@Override
	public List<String> initialize() {
		return Lists.newArrayList(N4JS_CLEAN);
	}

	@Override
	public Object execute(ExecuteCommandParams params, ILanguageServerAccess access, CancelIndicator cancelIndicator) {
		String command = params.getCommand();
		switch (command) {
		case N4JS_CLEAN:
			lspServer.clean();
			lspServer.reinitWorkspace();
			break;
		default:
		}

		return null;
	}

}
