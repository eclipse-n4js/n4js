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
package org.eclipse.n4js.ide.xtext.server;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.eclipse.lsp4j.ExecuteCommandParams;
import org.eclipse.lsp4j.SymbolInformation;
import org.eclipse.lsp4j.WorkspaceSymbolParams;
import org.eclipse.n4js.ide.xtext.server.findReferences.XWorkspaceResourceAccess;
import org.eclipse.xtext.findReferences.IReferenceFinder.IResourceAccess;
import org.eclipse.xtext.ide.server.ILanguageServerAccess;
import org.eclipse.xtext.ide.server.commands.ExecutableCommandRegistry;
import org.eclipse.xtext.ide.server.symbol.WorkspaceSymbolService;
import org.eclipse.xtext.util.CancelIndicator;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 *
 */
@SuppressWarnings("restriction")
@Singleton
public class WorkspaceFrontend {
	@Inject
	private ResourceTaskManager resourceTaskManager;

	@Inject
	private QueuedExecutorService lspExecutorService;

	@Inject
	private WorkspaceSymbolService workspaceSymbolService;

	@Inject
	private ExecutableCommandRegistry commandRegistry;

	private IResourceAccess resourceAccess;

	private ILanguageServerAccess access;

	/** Sets non-injectable fields */
	public void initialize(ILanguageServerAccess _access) {
		this.resourceAccess = new XWorkspaceResourceAccess(resourceTaskManager);
		this.access = _access;
	}

	/**
	 * Compute the symbol information.
	 */
	public CompletableFuture<List<? extends SymbolInformation>> symbol(WorkspaceSymbolParams params) {
		return lspExecutorService.submitAndCancelPrevious(WorkspaceSymbolParams.class, "symbol",
				cancelIndicator -> symbol(params, cancelIndicator));
	}

	/** Compute the symbol information. Executed in a read request. */
	protected List<? extends SymbolInformation> symbol(WorkspaceSymbolParams params, CancelIndicator cancelIndicator) {
		return workspaceSymbolService.getSymbols(params.getQuery(), resourceAccess,
				resourceTaskManager.createLiveScopeIndex(), cancelIndicator);
	}

	/**
	 * Execute the given command.
	 */
	public CompletableFuture<Object> executeCommand(ExecuteCommandParams params) {
		return lspExecutorService.submit("executeCommand", cancelIndicator -> executeCommand(params, cancelIndicator));
	}

	/** Execute the command. Runs in a read request. */
	protected Object executeCommand(ExecuteCommandParams params, CancelIndicator cancelIndicator) {
		return commandRegistry.executeCommand(params, access, cancelIndicator);
	}

}
