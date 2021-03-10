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
package org.eclipse.n4js.xtext.server.symbol;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.lsp4j.Location;
import org.eclipse.lsp4j.SymbolInformation;
import org.eclipse.xtext.findReferences.IReferenceFinder;
import org.eclipse.xtext.findReferences.IReferenceFinder.IResourceAccess;
import org.eclipse.xtext.findReferences.ReferenceAcceptor;
import org.eclipse.xtext.findReferences.TargetURIs;
import org.eclipse.xtext.ide.server.DocumentExtensions;
import org.eclipse.xtext.ide.server.symbol.DocumentSymbolService;
import org.eclipse.xtext.ide.util.CancelIndicatorProgressMonitor;
import org.eclipse.xtext.resource.EObjectAtOffsetHelper;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.service.OperationCanceledManager;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Minor adjustments compared to the default {@link DocumentSymbolService}.
 */
@Singleton
@SuppressWarnings("restriction")
public class XDocumentSymbolService extends DocumentSymbolService {

	@Inject
	private EObjectAtOffsetHelper eObjectAtOffsetHelper;

	@Inject
	private IReferenceFinder referenceFinder;

	@Inject
	private IResourceServiceProvider.Registry resourceServiceProviderRegistry;

	@Inject
	private DocumentExtensions documentExtensions;

	@Inject
	private OperationCanceledManager operationCanceledManager;

	// Overridden to wrap the call to #createSymbol inside resourceAccess.readOnly to re-use ResourceTaskContext
	@Override
	public List<? extends SymbolInformation> getSymbols(IResourceDescription resourceDescription, String query,
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

	// Overridden only to be able to factor out methods that accept custom reference acceptors:
	@Override
	public List<? extends Location> getReferences(
			XtextResource resource,
			int offset,
			IResourceAccess resourceAccess,
			IResourceDescriptions indexData,
			CancelIndicator cancelIndicator) {

		List<Location> locations = new ArrayList<>();
		getReferences(
				resource,
				offset,
				new ReferenceAcceptor(resourceServiceProviderRegistry, reference -> {
					doRead(resourceAccess, reference.getSourceEObjectUri(), obj -> {
						Location location = documentExtensions.newLocation(obj, reference.getEReference(),
								reference.getIndexInList());
						if (location != null) {
							locations.add(location);
						}
					});
				}),
				resourceAccess,
				indexData,
				cancelIndicator);
		return locations;
	}

	/**
	 * Same as {@link #getReferences(XtextResource, int, IResourceAccess, IResourceDescriptions, CancelIndicator)}, but
	 * a custom reference acceptor can be provided.
	 */
	public boolean getReferences(
			XtextResource resource,
			int offset,
			IReferenceFinder.Acceptor referenceAcceptor,
			IResourceAccess resourceAccess,
			IResourceDescriptions indexData,
			CancelIndicator cancelIndicator) {

		EObject element = eObjectAtOffsetHelper.resolveElementAt(resource, offset);
		if (element == null) {
			return false;
		}

		getReferences(element, referenceAcceptor, resourceAccess, indexData, cancelIndicator);
		return true;
	}

	/** Find references pointing to the given element and invoke the given acceptor for each reference. */
	public void getReferences(
			EObject element,
			IReferenceFinder.Acceptor referenceAcceptor,
			IResourceAccess resourceAccess,
			IResourceDescriptions indexData,
			CancelIndicator cancelIndicator) {

		TargetURIs targetURIs = collectTargetURIs(element);
		referenceFinder.findAllReferences(
				targetURIs,
				resourceAccess,
				indexData,
				referenceAcceptor,
				new CancelIndicatorProgressMonitor(cancelIndicator));
	}
}
