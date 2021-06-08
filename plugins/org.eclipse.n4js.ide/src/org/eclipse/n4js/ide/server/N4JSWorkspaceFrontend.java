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
package org.eclipse.n4js.ide.server;

import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.lsp4j.SymbolInformation;
import org.eclipse.lsp4j.WorkspaceSymbolParams;
import org.eclipse.n4js.xtext.ide.server.ResourceTaskManager;
import org.eclipse.n4js.xtext.ide.server.WorkspaceFrontend;
import org.eclipse.n4js.xtext.ide.server.util.XChunkedResourceDescriptions;
import org.eclipse.xtext.findReferences.IReferenceFinder.IResourceAccess;
import org.eclipse.xtext.ide.server.symbol.WorkspaceSymbolService;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.util.concurrent.IUnitOfWork;

import com.google.common.base.Throwables;
import com.google.inject.Inject;

/**
 * N4JS-specific customizations of {@link WorkspaceFrontend}.
 */
@SuppressWarnings("restriction")
public class N4JSWorkspaceFrontend extends WorkspaceFrontend {

	@Inject
	private ResourceTaskManager resourceTaskManager;

	@Inject
	private WorkspaceSymbolService workspaceSymbolService;

	private static final IResourceAccess nonLoadingResourceAccess = new IResourceAccess() {
		@Override
		public <R> R readOnly(URI targetURI, IUnitOfWork<R, ResourceSet> work) {
			try {
				return work.exec(null);
			} catch (Exception e) {
				Throwables.throwIfUnchecked(e);
				throw new RuntimeException(e);
			}
		}
	};

	@Override
	protected List<? extends SymbolInformation> symbol(WorkspaceSymbolParams params, CancelIndicator cancelIndicator) {
		// in case of N4JS, all required information is contained in the IEObjectDescriptions, so we do not actually
		// need to access the resource; to make 100% sure resources aren't accessed accidentally, we pass on a special
		// resource access that will never load the resource:
		IResourceAccess resourceAccess = nonLoadingResourceAccess;
		XChunkedResourceDescriptions liveScopeIndex = resourceTaskManager.createLiveScopeIndex();
		try {
			List<? extends SymbolInformation> symbols = workspaceSymbolService.getSymbols(params.getQuery(),
					resourceAccess, liveScopeIndex, cancelIndicator);
			return symbols;
		} catch (Throwable th) {
			// It seems that if this request fails (even if due to a cancellation), then some LSP clients (e.g. VS Code)
			// will never try a 'workspace/symbols' request again, effectively turning off operation "open workspace
			// symbol". We don't want that, so we return an empty list here:
			return Collections.emptyList();
			// FIXME #1 why is this special handling required here, but not in case of other requests?
			// (could this be related to the use of #submitAndCancelPrevious() in the super class???)
			// FIXME #2 report the error via the LspLogger!!!! (if it isn't a cancellation)
		}
	}
}
