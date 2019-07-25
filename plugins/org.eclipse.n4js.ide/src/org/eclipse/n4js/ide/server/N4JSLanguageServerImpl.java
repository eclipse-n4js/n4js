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
package org.eclipse.n4js.ide.server;

import java.util.Iterator;

import org.eclipse.emf.common.util.URI;
import org.eclipse.lsp4j.DidChangeTextDocumentParams;
import org.eclipse.lsp4j.DidChangeWatchedFilesParams;
import org.eclipse.lsp4j.DidCloseTextDocumentParams;
import org.eclipse.lsp4j.DidOpenTextDocumentParams;
import org.eclipse.lsp4j.FileEvent;
import org.eclipse.lsp4j.services.LanguageClient;
import org.eclipse.n4js.hlc.base.HeadlessExtensionRegistrationHelper;
import org.eclipse.n4js.projectModel.lsp.ex.IProjectConfigEx;
import org.eclipse.xtext.ide.server.LanguageServerImpl;
import org.eclipse.xtext.workspace.IWorkspaceConfig;

import com.google.inject.Inject;

/**
 *
 */
@SuppressWarnings("restriction")
public class N4JSLanguageServerImpl extends LanguageServerImpl {

	// TODO we should probably use the DisposableRegistry here
	/**
	 * Called by Guice to initialize the languages. This way it is guaranteed that the registration happends exactly
	 * once.
	 *
	 * @param helper
	 *            the registrar
	 */
	@Inject
	public void registerExtensions(HeadlessExtensionRegistrationHelper helper) {
		helper.unregisterExtensions();
		helper.registerExtensions();
	}

	@Override
	public void didOpen(DidOpenTextDocumentParams params) {
		String uriString = params.getTextDocument().getUri();
		if (!isInOutputFolder(uriString)) {
			super.didOpen(params);
		}
	}

	@Override
	public void didClose(DidCloseTextDocumentParams params) {
		String uriString = params.getTextDocument().getUri();
		if (!isInOutputFolder(uriString)) {
			super.didClose(params);
		}
	}

	@Override
	public void didChange(DidChangeTextDocumentParams params) {
		String uriString = params.getTextDocument().getUri();
		if (!isInOutputFolder(uriString)) {
			super.didChange(params);
		}
	}

	@Override
	public void didChangeWatchedFiles(DidChangeWatchedFilesParams params) {
		for (Iterator<FileEvent> fileEventIter = params.getChanges().iterator(); fileEventIter.hasNext();) {
			FileEvent fileEvent = fileEventIter.next();
			String uriString = fileEvent.getUri();
			if (isInOutputFolder(uriString)) {
				fileEventIter.remove();
			}
		}
		if (!params.getChanges().isEmpty()) {
			super.didChangeWatchedFiles(params);
		}
	}

	private boolean isInOutputFolder(String uriString) {
		URI uri = URI.createURI(uriString);
		if (!uri.hasAuthority()) {
			throw new RuntimeException("Missing authority in uri " + uriString);
		}
		N4JSWorkspaceManager workspaceManager = (N4JSWorkspaceManager) getWorkspaceManager();
		IWorkspaceConfig workspaceConfig = workspaceManager.getWorkspaceConfig();
		IProjectConfigEx projectConfig = (IProjectConfigEx) workspaceConfig.findProjectContaining(uri);
		boolean isInOutputFolder = projectConfig != null && projectConfig.isInOutputFolder(uri);
		return isInOutputFolder;
	}

	@Override
	public LanguageClient getLanguageClient() {
		return super.getLanguageClient();
	}

}
