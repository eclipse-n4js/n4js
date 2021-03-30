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

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

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
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.n4JS.DefaultImportSpecifier;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.ImportSpecifier;
import org.eclipse.n4js.n4JS.N4JSASTUtils;
import org.eclipse.n4js.n4JS.N4JSFeatureUtils;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.NamedImportSpecifier;
import org.eclipse.n4js.n4JS.NamespaceImportSpecifier;
import org.eclipse.n4js.ts.scoping.builtin.N4Scheme;
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.xtext.ide.server.ResourceTaskContext;
import org.eclipse.n4js.xtext.ide.server.ResourceTaskManager;
import org.eclipse.n4js.xtext.ide.server.XDocument;
import org.eclipse.n4js.xtext.ide.server.build.BuilderFrontend;
import org.eclipse.n4js.xtext.ide.server.symbol.XDocumentSymbolService;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.findReferences.IReferenceFinder.IResourceAccess;
import org.eclipse.xtext.findReferences.ReferenceAcceptor;
import org.eclipse.xtext.ide.server.DocumentExtensions;
import org.eclipse.xtext.ide.server.rename.RenameService2;
import org.eclipse.xtext.ide.server.symbol.DocumentSymbolService;
import org.eclipse.xtext.resource.EObjectAtOffsetHelper;
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
	private BuilderFrontend builderFrontend;

	@Inject
	private ResourceTaskManager resourceTaskManager;

	@Inject
	private DocumentSymbolService documentSymbolService;

	@Inject
	private EObjectAtOffsetHelper eObjectAtOffsetHelper;

	@Inject
	private DocumentExtensions documentExtensions;

	@Inject
	private IResourceServiceProvider.Registry resourceServiceProviderRegistry;

	// #################################################################################################################
	// prepareRename
	//
	// In case of #prepareRename() we largely use the implementation from the super class. Only the following two
	// overrides with N4JS-specific adjustments are required:

	@Override
	protected EObject getElementWithIdentifierAt(XtextResource resource, int offset) {
		NamedImportSpecifier originImport = getImportIfReferenceToAliasOfNamedImport(resource, offset);
		if (originImport != null) {
			return originImport;
		}
		EObject result = super.getElementWithIdentifierAt(resource, offset);
		if (result != null) {
			if (N4Scheme.isFromResourceWithN4Scheme(result) || isFromResourceInNodeModules(result)) {
				return null;
			}
		}
		return result;
	}

	@Override
	protected String getElementName(EObject element) {
		if (element instanceof NamedImportSpecifier) { // including DefaultImportSpecifier
			String alias = ((NamedImportSpecifier) element).getAlias();
			if (alias != null && !alias.isEmpty()) {
				return alias;
			}
		} else if (element instanceof NamespaceImportSpecifier) {
			String namespaceName = ((NamespaceImportSpecifier) element).getAlias();
			if (namespaceName != null && !namespaceName.isEmpty()) {
				return namespaceName;
			}
		}
		String name = N4JSASTUtils.getElementName(element);
		return name != null && !name.isEmpty() ? name : null;
	}

	// #################################################################################################################
	// rename
	//
	// In case of #rename() we entirely replace the implementation from the super class by our own implementation (in
	// order to reuse reference finding as far as possible, for consistency):

	@Override
	public WorkspaceEdit rename(Options options) {
		RenameParams renameParams = options.getRenameParams();
		CancelIndicator cancelIndicator = options.getCancelIndicator();

		ResourceTaskContext rtc = resourceTaskManager.currentContext();
		if (rtc == null || !rtc.isTemporary()) {
			throw new IllegalStateException(
					"N4JSRenameService#rename expects to be invoked within a temporary resource task context");
		}

		ResourceSet resourceSet = rtc.getResourceSet();
		XtextResource resource = rtc.getResource();
		XDocument document = rtc.getDocument();
		int offset = document.getOffSet(renameParams.getPosition());

		// in case the temporary resource task context we are using was created without 'resolveAndValidate' we need to
		// trigger resolution (not validation) here:
		EcoreUtil2.resolveLazyCrossReferences(resource, cancelIndicator);

		EObject element = getElementToBeRenamed(resource, offset);
		if (element == null) {
			return new WorkspaceEdit(); // empty edit
		}

		WorkspaceEdit result = computeRenameEdits(resourceSet, element, renameParams.getNewName(),
				resourceTaskManager.createLiveScopeIndex(), cancelIndicator);

		return result;
	}

	/**
	 * Create text edits for renaming the given element and all references pointing to it and create a workspace edit
	 * from them.
	 */
	protected WorkspaceEdit computeRenameEdits(ResourceSet resourceSet, EObject element, String newName,
			IResourceDescriptions indexData, CancelIndicator cancelIndicator) {

		ListMultimap<String, TextEdit> edits = ArrayListMultimap.create();

		// compute edit(s) for the element(s) to be renamed
		List<? extends EObject> actualElements = element instanceof TMember && ((TMember) element).isComposed()
				? ((TMember) element).getConstituentMembers()
				: Collections.singletonList(element);
		for (EObject actualElement : actualElements) {
			TextEdit edit = computeRenameEditForElement(actualElement, newName);
			if (edit != null) {
				edits.put(actualElement.eResource().getURI().toString(), edit);
			}
		}

		// compute edits for all references
		ReferenceAcceptor referenceAcceptor = new ReferenceAcceptor(resourceServiceProviderRegistry, reference -> {
			URI objURI = reference.getSourceEObjectUri();
			EObject obj = resourceSet.getEObject(objURI, true);
			Resource res = obj != null ? obj.eResource() : null;
			URI resURI = res != null ? res.getURI() : null;
			if (obj == null || res == null || resURI == null) {
				return;
			}
			TextEdit edit = computeRenameEditForReference(obj, reference.getEReference(), reference.getIndexInList(),
					newName);
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
		((XDocumentSymbolService) documentSymbolService).getReferences(element, referenceAcceptor, resourceAccess,
				indexData, cancelIndicator);

		WorkspaceEdit result = new WorkspaceEdit(Multimaps.asMap(edits));

		return result;
	}

	/** Create and return an individual text edit for renaming the given element (without references). */
	protected TextEdit computeRenameEditForElement(EObject element, String newName) {
		EStructuralFeature nameFeature = getElementNameFeature(element);
		if (nameFeature == null
				&& element instanceof NamedImportSpecifier // including DefaultImportSpecifier
				&& ((NamedImportSpecifier) element).getAlias() != null) {
			nameFeature = N4JSPackage.eINSTANCE.getNamedImportSpecifier_Alias();
		} else if (nameFeature == null
				&& element instanceof NamespaceImportSpecifier
				&& ((NamespaceImportSpecifier) element).getAlias() != null) {
			nameFeature = N4JSPackage.eINSTANCE.getNamespaceImportSpecifier_Alias();
		}
		Location location = nameFeature != null ? documentExtensions.newLocation(element, nameFeature, -1) : null;
		if (location != null) {
			TextEdit edit = new TextEdit(location.getRange(), newName);
			return edit;
		}
		return null;
	}

	/** Create and return an individual text edit for updating the given reference during rename refactoring. */
	protected TextEdit computeRenameEditForReference(EObject context, EReference eRef, int indexInList,
			String newName) {
		if (eRef != N4JSPackage.eINSTANCE.getIdentifierRef_Id()
				&& eRef != N4JSPackage.eINSTANCE.getIdentifierRef_OriginImport()
				&& eRef != N4JSPackage.eINSTANCE.getNamedImportSpecifier_ImportedElement()
				&& eRef != N4JSPackage.eINSTANCE.getParameterizedPropertyAccessExpression_Property()
				&& eRef != N4JSPackage.eINSTANCE.getLabelRef_Label()
				&& eRef != TypeRefsPackage.eINSTANCE.getParameterizedTypeRef_DeclaredType()) {
			return null;
		}

		if (context instanceof IdentifierRef && eRef == N4JSPackage.eINSTANCE.getIdentifierRef_Id()) {
			ImportSpecifier originImport = ((IdentifierRef) context).getOriginImport();
			if (originImport instanceof NamedImportSpecifier) { // including DefaultImportSpecifier
				boolean isImportedViaAlias = ((NamedImportSpecifier) originImport).getAlias() != null;
				if (isImportedViaAlias) {
					return null;
				}
			}
		} else if (context instanceof DefaultImportSpecifier
				&& eRef == N4JSPackage.eINSTANCE.getNamedImportSpecifier_ImportedElement()) {
			return null;
		}

		Location location = documentExtensions.newLocation(context, eRef, indexInList);
		if (location == null) {
			return null;
		}
		TextEdit edit = new TextEdit(location.getRange(), newName);
		return edit;
	}

	/**
	 * Returns the element to be renamed when rename refactoring is being initiated at the given source code location.
	 * If the location points to a reference instead of a named element, this method should return the actual element to
	 * be renamed, not the AST node representing the reference; therefore, the returned {@link EObject} may be contained
	 * in some other than the given resource.
	 */
	protected EObject getElementToBeRenamed(XtextResource resource, int offset) {
		// special case: when triggering rename on a reference to a target element that is imported via an aliased named
		// import, then rename the alias instead of the target element
		NamedImportSpecifier originImport = getImportIfReferenceToAliasOfNamedImport(resource, offset);
		if (originImport != null) {
			return originImport;
		}

		return eObjectAtOffsetHelper.resolveElementAt(resource, offset);
	}

	/** Returns the {@link EStructuralFeature feature} that represents the given element's name. */
	protected EStructuralFeature getElementNameFeature(EObject element) {
		return N4JSFeatureUtils.getElementNameFeature(element);
	}

	private NamedImportSpecifier getImportIfReferenceToAliasOfNamedImport(XtextResource resource, int offset) {
		EObject containedElement = eObjectAtOffsetHelper.resolveContainedElementAt(resource, offset);
		if (!(containedElement instanceof IdentifierRef) && offset > 0) {
			containedElement = eObjectAtOffsetHelper.resolveContainedElementAt(resource, offset - 1);
		}
		if (containedElement instanceof IdentifierRef) {
			ImportSpecifier originImport = ((IdentifierRef) containedElement).getOriginImport();
			if (originImport instanceof NamedImportSpecifier // including DefaultImportSpecifier
					&& ((NamedImportSpecifier) originImport).getAlias() != null) {
				return (NamedImportSpecifier) originImport;
			}
		}
		return null;
	}

	private boolean isFromResourceInNodeModules(EObject eobj) {
		Resource res = eobj != null ? eobj.eResource() : null;
		URI uri = res != null ? res.getURI() : null;
		URI uriRelative = uri != null ? builderFrontend.makeWorkspaceRelative(uri) : null;
		return uriRelative != null
				&& Stream.of(uriRelative.segments()).anyMatch(segment -> N4JSGlobals.NODE_MODULES.equals(segment));
	}
}
