/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.server.symbol;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.lsp4j.SymbolInformation;
import org.eclipse.lsp4j.WorkspaceSymbol;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.n4js.utils.ResourceType;
import org.eclipse.xtext.findReferences.IReferenceFinder.IResourceAccess;
import org.eclipse.xtext.ide.server.symbol.DocumentSymbolService;
import org.eclipse.xtext.ide.server.symbol.WorkspaceSymbolService;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.service.OperationCanceledManager;
import org.eclipse.xtext.util.CancelIndicator;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Customized to always use the {@link N4JSDocumentSymbolService} when collecting workspace symbols, not the
 * language-specific {@link DocumentSymbolService} returned by the {@link IResourceServiceProvider}.
 */
@Singleton
@SuppressWarnings({ "restriction", "deprecation" })
public class N4JSWorkspaceSymbolService extends WorkspaceSymbolService {

	@Inject // note: we deliberately inject >>N4JS<<DocumentSymbolService, not DocumentSymbolService!
	private N4JSDocumentSymbolService n4jsDocumentSymbolService;

	@Inject
	private OperationCanceledManager operationCanceledManager;

	@Override
	public Either<List<? extends SymbolInformation>, List<? extends WorkspaceSymbol>> getSymbols(String query,
			IResourceAccess resourceAccess, IResourceDescriptions indexData, CancelIndicator cancelIndicator) {

		List<WorkspaceSymbol> result = new LinkedList<>();
		for (IResourceDescription resourceDescription : indexData.getAllResourceDescriptions()) {
			operationCanceledManager.checkCanceled(cancelIndicator);
			URI uri = resourceDescription.getURI();
			ResourceType resourceType = ResourceType.getResourceType(uri);
			if (resourceType.isN4JS()) {
				List<? extends WorkspaceSymbol> symbols = n4jsDocumentSymbolService.getWorkspaceSymbols(
						resourceDescription, query, resourceAccess, cancelIndicator);
				result.addAll(symbols);
			} else {
				// ignore this
				// (plain JS files, package.json files, etc. do not contain any symbols we want to include in the list
				// of workspace symbols)
			}
		}
		return Either.forRight(result);
	}
}
