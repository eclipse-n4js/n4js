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
package org.eclipse.n4js.ide.server.rename;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.lsp4j.Location;
import org.eclipse.lsp4j.RenameParams;
import org.eclipse.lsp4j.TextEdit;
import org.eclipse.lsp4j.WorkspaceEdit;
import org.eclipse.n4js.ide.xtext.server.ResourceTaskContext;
import org.eclipse.n4js.ide.xtext.server.ResourceTaskManager;
import org.eclipse.n4js.ide.xtext.server.XDocument;
import org.eclipse.n4js.ide.xtext.server.symbol.XDocumentSymbolService;
import org.eclipse.n4js.n4JS.N4JSFeatureUtils;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.xtext.findReferences.IReferenceFinder.IResourceAccess;
import org.eclipse.xtext.findReferences.ReferenceAcceptor;
import org.eclipse.xtext.ide.server.DocumentExtensions;
import org.eclipse.xtext.ide.server.ILanguageServerAccess;
import org.eclipse.xtext.ide.server.UriExtensions;
import org.eclipse.xtext.ide.server.rename.RenameService2;
import org.eclipse.xtext.ide.server.symbol.DocumentSymbolService;
import org.eclipse.xtext.resource.EObjectAtOffsetHelper;
import org.eclipse.xtext.resource.IReferenceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.util.concurrent.IUnitOfWork;

import com.google.common.base.Throwables;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimaps;
import com.google.inject.Inject;

/**
 *
 */
@SuppressWarnings("restriction")
public class N4JSRenameService extends RenameService2 {

	@Inject
	private DocumentSymbolService documentSymbolService;

	@Inject
	private EObjectAtOffsetHelper eObjectAtOffsetHelper;

	@Inject
	private IResourceServiceProvider.Registry resourceServiceProviderRegistry;

	@Inject
	private DocumentExtensions documentExtensions;

	@Inject
	private ResourceTaskManager resourceTaskManager;

	@Inject
	private UriExtensions uriExtensions;

	// @Override
	// public Either<Range, PrepareRenameResult> prepareRename(PrepareRenameOptions options) {
	// return null;
	// }

	@Override
	public WorkspaceEdit rename(Options options) {
		RenameParams renameParams = options.getRenameParams();
		ILanguageServerAccess languageServerAccess = options.getLanguageServerAccess();
		CancelIndicator cancelIndicator = options.getCancelIndicator();

		String uriStr = renameParams.getTextDocument().getUri();
		URI uri = uriExtensions.toUri(uriStr);
		String description = "rename (" + N4JSRenameService.class.getSimpleName() + ")";
		// FIXME it would be cleaner to use languageServerAccess here
		CompletableFuture<WorkspaceEdit> result = resourceTaskManager.runInTemporaryContext(uri, description, true,
				cancelIndicator, (rtc, ci) -> doRename(rtc, renameParams, ci));

		try {
			return result.get();
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException(e);
		}
	}

	protected WorkspaceEdit doRename(ResourceTaskContext rtc, RenameParams renameParams,
			CancelIndicator cancelIndicator) {

		XtextResource resource = rtc.getResource();
		XDocument document = rtc.getDocument();
		int offset = document.getOffSet(renameParams.getPosition());
		return computeRenameEdits(
				resource,
				offset,
				renameParams.getNewName(),
				resourceTaskManager.createLiveScopeIndex(),
				cancelIndicator);
	}

	protected WorkspaceEdit computeRenameEdits(
			XtextResource resource,
			int offset,
			String newName,
			IResourceDescriptions indexData,
			CancelIndicator cancelIndicator) {

		EObject element = getElementToBeRenamed(resource, offset);
		if (element == null) {
			return new WorkspaceEdit(); // empty edit
		}

		ResourceSet resourceSet = resource.getResourceSet();
		if (resourceSet == null) {
			return new WorkspaceEdit(); // empty edit
		}

		ListMultimap<String, TextEdit> edits = ArrayListMultimap.create();

		TextEdit editForElement = computeRenameEditForElement(element, newName);
		if (editForElement != null) {
			edits.put(resource.getURI().toString(), editForElement);
		}

		ReferenceAcceptor referenceAcceptor = new ReferenceAcceptor(resourceServiceProviderRegistry, reference -> {
			URI objURI = reference.getSourceEObjectUri();
			EObject obj = resourceSet.getEObject(objURI, true);
			Resource res = obj != null ? obj.eResource() : null;
			URI resURI = res != null ? res.getURI() : null;
			if (obj == null || res == null || resURI == null) {
				return;
			}
			TextEdit edit = computeRenameEditForReference(reference, obj, reference.getEReference(),
					reference.getIndexInList(), newName);
			if (edit != null) {
				edits.put(resURI.toString(), edit);
			}
		});
		IResourceAccess resourceAccess = new IResourceAccess() {
			@Override
			public <R> R readOnly(URI targetURI, IUnitOfWork<R, ResourceSet> work) {
				try {
					return work.exec(resourceSet);
				} catch (Exception e) {
					Throwables.throwIfUnchecked(e);
					throw new RuntimeException(e);
				}
			}
		};
		((XDocumentSymbolService) documentSymbolService).getReferences(resource, offset, referenceAcceptor,
				resourceAccess, indexData, cancelIndicator);

		WorkspaceEdit result = new WorkspaceEdit(Multimaps.asMap(edits));
		return result;
	}

	protected TextEdit computeRenameEditForElement(EObject element, String newName) {
		EStructuralFeature nameFeature = N4JSFeatureUtils.attributeOfNameFeature(element);
		Location location = nameFeature != null ? documentExtensions.newLocation(element, nameFeature, -1) : null;
		if (location != null) {
			TextEdit edit = new TextEdit(location.getRange(), newName);
			return edit;
		}
		return null;
	}

	protected TextEdit computeRenameEditForReference(IReferenceDescription refDesc, EObject obj, EReference eRef,
			int indexInList, String newName) {
		if (eRef != N4JSPackage.eINSTANCE.getIdentifierRef_Id()
				&& eRef != N4JSPackage.eINSTANCE.getNamedImportSpecifier_ImportedElement()
				&& eRef != N4JSPackage.eINSTANCE.getParameterizedPropertyAccessExpression_Property()) {
			return null;
		}
		Location location = documentExtensions.newLocation(obj, eRef, indexInList);
		if (location == null) {
			return null;
		}
		TextEdit edit = new TextEdit(location.getRange(), newName);
		return edit;
	}

	protected EObject getElementToBeRenamed(XtextResource resource, int offset) {
		return eObjectAtOffsetHelper.resolveElementAt(resource, offset);
	}
}
