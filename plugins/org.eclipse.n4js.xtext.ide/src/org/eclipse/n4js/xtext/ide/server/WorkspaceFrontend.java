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
package org.eclipse.n4js.xtext.ide.server;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.lsp4j.ExecuteCommandParams;
import org.eclipse.lsp4j.Location;
import org.eclipse.lsp4j.SymbolInformation;
import org.eclipse.lsp4j.SymbolKind;
import org.eclipse.lsp4j.WorkspaceSymbolParams;
import org.eclipse.n4js.smith.CollectedDataAccess;
import org.eclipse.n4js.smith.DataCollectorUtils;
import org.eclipse.n4js.smith.DataSeries;
import org.eclipse.n4js.smith.Measurement;
import org.eclipse.n4js.smith.N4JSDataCollectors;
import org.eclipse.n4js.xtext.ide.server.findReferences.XWorkspaceResourceAccess;
import org.eclipse.n4js.xtext.ide.server.util.XChunkedResourceDescriptions;
import org.eclipse.xtext.findReferences.IReferenceFinder.IResourceAccess;
import org.eclipse.xtext.ide.server.DocumentExtensions;
import org.eclipse.xtext.ide.server.ILanguageServerAccess;
import org.eclipse.xtext.ide.server.commands.ExecutableCommandRegistry;
import org.eclipse.xtext.ide.server.symbol.DocumentSymbolService;
import org.eclipse.xtext.ide.server.symbol.WorkspaceSymbolService;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.service.OperationCanceledManager;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Handles all lsp calls that refer to the whole workspace, i.e. do not specify a specific test document.
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
	public void initialize(@SuppressWarnings("hiding") ILanguageServerAccess access) {
		this.resourceAccess = new XWorkspaceResourceAccess(resourceTaskManager, false);
		this.access = access;
	}

	/** Compute the symbol information. */
	public CompletableFuture<List<? extends SymbolInformation>> symbol(WorkspaceSymbolParams params) {
		return lspExecutorService.submitAndCancelPrevious(WorkspaceSymbolParams.class, "symbol",
				cancelIndicator -> symbol(params, cancelIndicator));
	}

	/** Compute the symbol information. Executed in a read request. */
	protected List<? extends SymbolInformation> symbol(WorkspaceSymbolParams params, CancelIndicator cancelIndicator) {
		XChunkedResourceDescriptions liveScopeIndex = resourceTaskManager.createLiveScopeIndex();
		// return workspaceSymbolService.getSymbols(params.getQuery(), resourceAccess, liveScopeIndex, cancelIndicator);

		N4JSDataCollectors.dcOpenSymbol.resetData();
		N4JSDataCollectors.dcOpenSymbol.setPaused(false);
		try (Measurement m = N4JSDataCollectors.dcOpenSymbol.getMeasurement()) {
			return /* workspaceSymbolService. */ getSymbols(params.getQuery(), resourceAccess, liveScopeIndex,
					CancelIndicator.NullImpl); // cancelIndicator);
		} finally {
			N4JSDataCollectors.dcOpenSymbol.setPaused(true);
			DataSeries dataSeries = CollectedDataAccess.getDataSeries(N4JSDataCollectors.dcOpenSymbol);
			String collectorStr = DataCollectorUtils.dataToString(dataSeries, " ");
			System.out.println(collectorStr);
		}
	}

	/** Execute the given command. */
	public CompletableFuture<Object> executeCommand(ExecuteCommandParams params) {
		return lspExecutorService.submit("executeCommand", cancelIndicator -> executeCommand(params, cancelIndicator));
	}

	/** Execute the command. Runs in a read request. */
	protected Object executeCommand(ExecuteCommandParams params, CancelIndicator cancelIndicator) {
		return commandRegistry.executeCommand(params, access, cancelIndicator);
	}

	// from org.eclipse.xtext.ide.server.symbol.WorkspaceSymbolService

	@Inject
	private IResourceServiceProvider.Registry resourceServiceProviderRegistry;

	@Inject
	private OperationCanceledManager operationCanceledManager;

	private List<? extends SymbolInformation> getSymbols(
			String query,
			IResourceAccess resourceAccess,
			IResourceDescriptions indexData,
			CancelIndicator cancelIndicator) {
		List<SymbolInformation> result = new LinkedList<>();

		List<IResourceDescription> all = Lists.newArrayList(indexData.getAllResourceDescriptions());
		Collections.sort(all, Comparator.comparing(rd -> rd.getURI().toString()));

		int n = 0;
		for (IResourceDescription resourceDescription : all) {
			operationCanceledManager.checkCanceled(cancelIndicator);
			URI uri = resourceDescription.getURI();
			// if (!uri.toString().endsWith(".n4js")) {
			// continue;
			// }
			// if (++n > 400) {
			// break;
			// }
			IResourceServiceProvider resourceServiceProvider = resourceServiceProviderRegistry
					.getResourceServiceProvider(uri);
			DocumentSymbolService documentSymbolService = resourceServiceProvider != null
					? resourceServiceProvider.get(DocumentSymbolService.class)
					: null;
			if (documentSymbolService != null) {
				// if (documentSymbolService instanceof XDocumentSymbolService) {

				// System.out.println(resourceDescription.getURI());

				List<? extends SymbolInformation> symbols = /* documentSymbolService. */ getSymbols(resourceDescription,
						query, resourceAccess, cancelIndicator);

				result.addAll(symbols);
			}
		}
		System.out.println(">>> result.size() == " + result.size());
		return result;
	}

	// from org.eclipse.n4js.xtext.ide.server.symbol.XDocumentSymbolService

	private List<? extends SymbolInformation> getSymbols(IResourceDescription resourceDescription, String query,
			IResourceAccess resourceAccess, CancelIndicator cancelIndicator) {

		List<IEObjectDescription> matchingObjects = CollectionLiterals.newLinkedList();
		for (IEObjectDescription description : resourceDescription.getExportedObjects()) {
			operationCanceledManager.checkCanceled(cancelIndicator);
			if (filter(description, query)) {
				matchingObjects.add(description);
			}
		}

		if (matchingObjects.isEmpty()) {
			return CollectionLiterals.newLinkedList();
		}

		// TODO GH-1881 avoid creating a context per IResourceDescription, instead caller should create a single context
		AtomicReference<List<SymbolInformation>> refSymbols = new AtomicReference<>(CollectionLiterals.newLinkedList());
		resourceAccess.readOnly(resourceDescription.getURI(), (resourceSet) -> {
			List<SymbolInformation> symbols = refSymbols.get();
			for (IEObjectDescription description : matchingObjects) {
				operationCanceledManager.checkCanceled(cancelIndicator);
				createSymbol(description, resourceAccess, symbol -> symbols.add(symbol));
			}
			return null;
		});
		return refSymbols.get();
	}

	// org.eclipse.xtext.ide.server.symbol.DocumentSymbolService

	@Inject
	private DocumentExtensions documentExtensions;

	private void createSymbol(
			IEObjectDescription description,
			IResourceAccess resourceAccess,
			Consumer<SymbolInformation> acceptor) {
		String name = getSymbolName(description);
		if (name == null)
			return;

		SymbolKind kind = getSymbolKind(description);
		if (kind == null)
			return;

		getSymbolLocation(description, resourceAccess, location -> {
			SymbolInformation symbol = new SymbolInformation(name, kind, location);
			acceptor.accept(symbol);
		});
	}

	private void getSymbolLocation(
			IEObjectDescription description,
			IResourceAccess resourceAccess,
			Consumer<Location> acceptor) {
		doRead(resourceAccess, description.getEObjectURI(), obj -> {
			Location location = getSymbolLocation(obj);
			if (location != null) {
				acceptor.accept(location);
			}
		});
	}

	private void doRead(IResourceAccess resourceAccess, URI objectURI, Consumer<EObject> acceptor) {
		resourceAccess.readOnly(objectURI, resourceSet -> {
			EObject object = resourceSet.getEObject(objectURI, true);
			if (object != null) {
				acceptor.accept(object);
			}
			return null;
		});
	}

	private boolean filter(IEObjectDescription description, String query) {
		return description.getQualifiedName().toLowerCase().toString().contains(query.toLowerCase());
	}

	private Location getSymbolLocation(EObject object) {
		return documentExtensions.newLocation(object);
	}

	private String getSymbolName(IEObjectDescription description) {
		return getSymbolName(description.getQualifiedName());
	}

	private String getSymbolName(QualifiedName qualifiedName) {
		return qualifiedName != null ? qualifiedName.toString() : null;
	}

	private SymbolKind getSymbolKind(IEObjectDescription description) {
		return getSymbolKind(description.getEClass());
	}

	private SymbolKind getSymbolKind(EClass type) {
		// TODO implement meaningful
		return SymbolKind.Property;
	}
}
