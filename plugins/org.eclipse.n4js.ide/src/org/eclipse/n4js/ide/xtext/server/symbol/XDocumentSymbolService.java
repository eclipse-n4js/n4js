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
package org.eclipse.n4js.ide.xtext.server.symbol;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.lsp4j.Location;
import org.eclipse.xtext.findReferences.IReferenceFinder;
import org.eclipse.xtext.findReferences.IReferenceFinder.IResourceAccess;
import org.eclipse.xtext.findReferences.ReferenceAcceptor;
import org.eclipse.xtext.findReferences.TargetURIs;
import org.eclipse.xtext.ide.server.DocumentExtensions;
import org.eclipse.xtext.ide.server.symbol.DocumentSymbolService;
import org.eclipse.xtext.ide.util.CancelIndicatorProgressMonitor;
import org.eclipse.xtext.resource.EObjectAtOffsetHelper;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.util.CancelIndicator;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
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

	// FIXME consider using referenceFinder directly from N4JSRenameService (then no need for this class)
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
