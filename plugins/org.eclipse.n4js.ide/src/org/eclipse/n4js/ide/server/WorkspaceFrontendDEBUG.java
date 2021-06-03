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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.lsp4j.Location;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.SymbolInformation;
import org.eclipse.lsp4j.SymbolKind;
import org.eclipse.lsp4j.WorkspaceSymbolParams;
import org.eclipse.n4js.resource.N4JSResourceDescriptionStrategy;
import org.eclipse.n4js.smith.CollectedDataAccess;
import org.eclipse.n4js.smith.DataCollectorUtils;
import org.eclipse.n4js.smith.DataSeries;
import org.eclipse.n4js.smith.Measurement;
import org.eclipse.n4js.smith.N4JSDataCollectors;
import org.eclipse.n4js.workspace.WorkspaceAccess;
import org.eclipse.n4js.xtext.ide.server.ResourceTaskManager;
import org.eclipse.n4js.xtext.ide.server.WorkspaceFrontend;
import org.eclipse.n4js.xtext.ide.server.util.XChunkedResourceDescriptions;
import org.eclipse.xtext.findReferences.IReferenceFinder.IResourceAccess;
import org.eclipse.xtext.ide.server.DocumentExtensions;
import org.eclipse.xtext.ide.server.UriExtensions;
import org.eclipse.xtext.ide.server.symbol.DocumentSymbolService;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.ILocationInFileProvider;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.service.OperationCanceledManager;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.util.ITextRegion;
import org.eclipse.xtext.util.concurrent.IUnitOfWork;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Handles all lsp calls that refer to the whole workspace, i.e. do not specify a specific test document.
 */
@SuppressWarnings("restriction")
@Singleton
public class WorkspaceFrontendDEBUG extends WorkspaceFrontend {

	public static int DEBUG__APPROACH = 3;

	@Inject
	private ResourceTaskManager resourceTaskManager;

	@Override
	protected List<? extends SymbolInformation> symbol(WorkspaceSymbolParams params, CancelIndicator cancelIndicator) {
		XChunkedResourceDescriptions liveScopeIndex = resourceTaskManager.createLiveScopeIndex();
		// return workspaceSymbolService.getSymbols(params.getQuery(), resourceAccess, liveScopeIndex, cancelIndicator);

		N4JSDataCollectors.dcOpenSymbol.resetData();
		N4JSDataCollectors.dcOpenSymbol.setPaused(false);
		List<? extends SymbolInformation> result;
		try (Measurement m = N4JSDataCollectors.dcOpenSymbol.getMeasurement()) {
			result = /* workspaceSymbolService. */ getSymbols(params.getQuery(), null, liveScopeIndex,
					CancelIndicator.NullImpl); // cancelIndicator);
		} finally {
			N4JSDataCollectors.dcOpenSymbol.setPaused(true);
			DataSeries dataSeries = CollectedDataAccess.getDataSeries(N4JSDataCollectors.dcOpenSymbol);
			String collectorStr = DataCollectorUtils.dataToString(dataSeries, " ");
			System.out.println(collectorStr);
		}

		// write to file for result comparison
		StringBuilder sb = new StringBuilder();
		for (SymbolInformation si : result) {
			sb.append(si.getName());
			sb.append(' ');
			sb.append(si.getContainerName());
			sb.append(' ');
			sb.append(si.getLocation().getRange().getStart().getLine());
			sb.append(':');
			sb.append(si.getLocation().getRange().getStart().getCharacter());
			if (si.getLocation().getUri().endsWith(".js")) {
				sb.append("-...");
			} else if (si.getLocation().getUri().endsWith("/package.json")) {
				sb.append("-...");
			} else {
				sb.append('-');
				sb.append(si.getLocation().getRange().getEnd().getLine());
				sb.append(':');
				sb.append(si.getLocation().getRange().getEnd().getCharacter());
			}
			sb.append(' ');
			sb.append(si.getLocation().getUri());
			sb.append('\n');
		}
		try {
			Files.writeString(Path.of("/Users/mark-oliver.reiser/Desktop/allSymbols.txt"), sb.toString());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return result;
	}

	// from org.eclipse.xtext.ide.server.symbol.WorkspaceSymbolService

	@Inject
	private IResourceServiceProvider.Registry resourceServiceProviderRegistry;

	@Inject
	private OperationCanceledManager operationCanceledManager;

	@Inject
	private WorkspaceAccess workspaceAccess;

	private List<? extends SymbolInformation> getSymbols(
			String query,
			IResourceAccess resourceAccessPassedIn,
			IResourceDescriptions indexData,
			CancelIndicator cancelIndicator) {
		List<SymbolInformation> result = new LinkedList<>();

		List<IResourceDescription> all = Lists.newArrayList(indexData.getAllResourceDescriptions());
		Collections.sort(all, Comparator.comparing(rd -> rd.getURI().toString()));

		XtextResourceSet resourceSet;
		IResourceAccess resourceAccess;
		if (DEBUG__APPROACH == 0) {
			resourceAccess = resourceAccessPassedIn;
			resourceSet = null;
		} else if (DEBUG__APPROACH == 1 || DEBUG__APPROACH == 2) {
			resourceSet = resourceTaskManager.createTemporaryResourceSet();
			resourceAccess = new IResourceAccess() {
				@Override
				public <R> R readOnly(URI targetURI, IUnitOfWork<R, ResourceSet> work) {
					try {
						return work.exec(resourceSet);
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}
			};
		} else if (DEBUG__APPROACH == 3) {
			resourceSet = null;
			resourceAccess = new IResourceAccess() {
				@Override
				public <R> R readOnly(URI targetURI, IUnitOfWork<R, ResourceSet> work) {
					try {
						return work.exec(null);
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}
			};
		} else {
			throw new IllegalStateException("invalid DEBUG__APPROACH: " + DEBUG__APPROACH);
		}

		int n = 0;
		for (IResourceDescription resourceDescription : all) {
			operationCanceledManager.checkCanceled(cancelIndicator);
			URI uri = resourceDescription.getURI();
			// if (!uri.toString().endsWith(".n4js")) {
			// continue;
			// }
			// if (uri.toString().contains("/node_modules/")) {
			// continue;
			// }
			// if (++n > 1000) {
			// break;
			// }

			if (DEBUG__APPROACH == 2) {
				workspaceAccess.loadModuleFromIndex(resourceSet, resourceDescription, false);
			}

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

				if (DEBUG__APPROACH == 1 || DEBUG__APPROACH == 2) {
					resourceSet.getResources().clear();
				}
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

	@Inject
	private UriExtensions uriExtensions;

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
		if (DEBUG__APPROACH == 3) {
			String uriStr = uriExtensions.toUriString(description.getEObjectURI().trimFragment());
			N4JSResourceDescriptionStrategy.Location loc = N4JSResourceDescriptionStrategy.getLocation(description);
			Range rangeLSP = loc != null
					? new Range(new Position(loc.startLine, loc.startColumn), new Position(loc.endLine, loc.endColumn))
					: new Range(new Position(0, 0), new Position(0, 0));
			Location locLSP = new Location(uriStr, rangeLSP);
			acceptor.accept(locLSP);
			return;
		}
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
		return newLocation(object);
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

	// from org.eclipse.xtext.ide.server.DocumentExtensions

	@Inject
	private ILocationInFileProvider locationInFileProvider;

	private Location newLocation(EObject object) {
		Resource resource = object.eResource();
		ITextRegion textRegion;
		try (Measurement m = N4JSDataCollectors.dcOpenSymbol_getSignificantTextRegion.getMeasurement()) {
			textRegion = locationInFileProvider.getSignificantTextRegion(object);
		}
		return documentExtensions.newLocation(resource, textRegion);
	}
}
