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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.lsp4j.DocumentSymbol;
import org.eclipse.lsp4j.Location;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.SymbolInformation;
import org.eclipse.lsp4j.SymbolKind;
import org.eclipse.lsp4j.WorkspaceSymbol;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.n4js.ide.server.util.SymbolKindUtil;
import org.eclipse.n4js.n4JS.BindingElement;
import org.eclipse.n4js.n4JS.N4JSASTUtils;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.PropertyNameValuePairSingleName;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.naming.N4JSQualifiedNameProvider;
import org.eclipse.n4js.resource.N4JSResourceDescriptionStrategy;
import org.eclipse.n4js.scoping.utils.PolyfillUtils;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.n4js.utils.FindReferenceHelper;
import org.eclipse.n4js.utils.UtilN4;
import org.eclipse.n4js.xtext.ide.server.symbol.XDocumentSymbolService;
import org.eclipse.xtext.findReferences.IReferenceFinder;
import org.eclipse.xtext.findReferences.IReferenceFinder.IResourceAccess;
import org.eclipse.xtext.ide.server.DocumentExtensions;
import org.eclipse.xtext.ide.server.UriExtensions;
import org.eclipse.xtext.ide.server.symbol.HierarchicalDocumentSymbolService;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.EObjectAtOffsetHelper;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.service.OperationCanceledManager;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.util.TextRegion;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;

import com.google.common.collect.Lists;
import com.google.common.graph.Traverser;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Custom N4JS implementation of {@link XDocumentSymbolService} taking advantage of additional location information in
 * the user data of {@link IEObjectDescription}s, thus avoiding to load resources or deserializing TModules when
 * obtaining symbol information. See {@link #getSymbolLocation(IEObjectDescription)}.
 */
@Singleton
@SuppressWarnings({ "restriction" })
public class N4JSDocumentSymbolService extends XDocumentSymbolService {

	@Inject
	private UriExtensions uriExtensions;
	@Inject
	private EObjectAtOffsetHelper eObjectAtOffsetHelper;
	@Inject
	private OperationCanceledManager operationCanceledManager;
	@Inject
	private IResourceServiceProvider.Registry resourceServiceProviderRegistry;
	@Inject
	private HierarchicalDocumentSymbolService hierarchicalDocumentSymbolService;
	@Inject
	private FindReferenceHelper findReferenceHelper;

	@Override
	public List<? extends Location> getDefinitions(XtextResource resource, int offset,
			IReferenceFinder.IResourceAccess resourceAccess, CancelIndicator cancelIndicator) {

		INode node = eObjectAtOffsetHelper.getCrossReferenceNode(resource, new TextRegion(offset, 0));
		EObject elemAtOffset = node == null ? null : NodeModelUtils.findActualSemanticObjectFor(node);

		EObject element = eObjectAtOffsetHelper.resolveElementAt(resource, offset);
		if (element == null) {
			return Collections.emptyList();
		}
		if (element instanceof VariableDeclaration && element.eContainer() instanceof BindingElement) {
			BindingElement parent = (BindingElement) element.eContainer();
			element = findReferenceHelper.getMemberInDestructuring(parent);
		}

		EObject type = N4JSASTUtils.getCorrespondingTypeModelElement(element);
		if (type != null) {
			element = type;
		}

		List<Location> locations = new ArrayList<>();

		if (elemAtOffset != null && elemAtOffset.eContainer() instanceof PropertyNameValuePairSingleName
				&& elemAtOffset == elemAtOffset.eContainer()
						.eGet(N4JSPackage.eINSTANCE.getPropertyNameValuePair_Expression())) {

			EObject elementInDestructuring = findReferenceHelper.getMemberInDestructuring(elemAtOffset);
			addLocations(resourceAccess, cancelIndicator, elementInDestructuring, locations);
		}

		addLocations(resourceAccess, cancelIndicator, element, locations);

		return locations;
	}

	private void addLocations(IReferenceFinder.IResourceAccess resourceAccess, CancelIndicator cancelIndicator,
			EObject element, List<Location> locations) {

		for (URI targetURI : collectTargetURIs(element)) {
			operationCanceledManager.checkCanceled(cancelIndicator);
			doRead(resourceAccess, targetURI, (EObject obj) -> {
				Location location = resourceServiceProviderRegistry
						.getResourceServiceProvider(targetURI.trimFragment())
						.get(DocumentExtensions.class).newLocation(obj);
				if (location != null) {
					locations.add(location);
				}
			});
		}
	}

	@Override
	protected boolean filter(IEObjectDescription description, String query) {
		// TModules directly correspond to their containing file, but opening files is not the responsibility of the
		// "open workspace symbol" functionality; thus we here filter out all TModules:
		if (description.getEClass() == TypesPackage.Literals.TMODULE) {
			return false;
		}

		String name = getSymbolName(description);
		if (name == null) {
			return false;
		}

		// Note: filtering of completion proposals and document symbols is performed on the client side, whereas
		// filtering of workspace symbols is performed on the server side (by this method). To have consistent
		// filtering across these use cases (at least in VS Code), we here mimic VS Code's filtering logic.
		return UtilN4.isMatchAccordingToVSCode(name, query);
	}

	@Override
	protected void createSymbol(IEObjectDescription description, IResourceAccess resourceAccess,
			Procedure1<? super WorkspaceSymbol> acceptor) {

		WorkspaceSymbol symbol = createSymbol(description);
		if (symbol != null) {
			acceptor.apply(symbol);
		}
	}

	@Override
	protected WorkspaceSymbol createSymbol(IEObjectDescription description) {
		WorkspaceSymbol symbol = super.createSymbol(description);
		if (symbol == null) {
			return null;
		}
		symbol.setContainerName(getContainerName(description));
		symbol.setLocation(Either.forLeft(getSymbolLocation(description)));
		return symbol;
	}

	@Override
	protected String getSymbolName(QualifiedName qualifiedName) {
		return qualifiedName.getLastSegment();
	}

	/** Returns the {@link SymbolInformation#getContainerName() container name} or <code>null</code>. */
	protected String getContainerName(IEObjectDescription description) {
		QualifiedName qualifiedName = description.getQualifiedName();
		int segCount = qualifiedName != null ? qualifiedName.getSegmentCount() : 0;
		if (qualifiedName != null && segCount > 1) {
			String first = qualifiedName.getFirstSegment();
			String secondToLast = qualifiedName.getSegment(segCount - 2);
			if (N4JSQualifiedNameProvider.GLOBAL_NAMESPACE_SEGMENT.equals(first)) {
				return "(global)";
			} else if (PolyfillUtils.POLYFILL_SEGMENT.equals(secondToLast)) {
				if (PolyfillUtils.MODULE_POLYFILL_SEGMENT.equals(first)) {
					qualifiedName = qualifiedName.skipFirst(1);
				}
				return qualifiedName.getSegmentCount() > 2
						? qualifiedName.skipLast(2).toString() + " (polyfill)"
						: "(polyfill)";
			}
			return qualifiedName.skipLast(1).toString();
		}
		return null;
	}

	/**
	 * Overridden to avoid loading the resource but instead obtaining the location information from the user data of the
	 * given {@link IEObjectDescription}.
	 */
	@Override
	protected void getSymbolLocation(IEObjectDescription description, IResourceAccess resourceAccess,
			Procedure1<? super Location> acceptor) {
		Location location = getSymbolLocation(description);
		if (location != null) {
			acceptor.apply(location);
		}
	}

	/**
	 * Retrieves a symbol's location from the user data of the given {@link IEObjectDescription}, see
	 * {@link N4JSResourceDescriptionStrategy#LOCATION_KEY}.
	 */
	protected Location getSymbolLocation(IEObjectDescription description) {
		String uriStr = uriExtensions.toUriString(description.getEObjectURI().trimFragment());
		N4JSResourceDescriptionStrategy.Location loc = N4JSResourceDescriptionStrategy.getLocation(description);
		Range rangeLSP = loc != null
				? new Range(new Position(loc.startLine, loc.startColumn), new Position(loc.endLine, loc.endColumn))
				: new Range(new Position(0, 0), new Position(0, 0));
		Location locLSP = new Location(uriStr, rangeLSP);
		return locLSP;
	}

	@Override
	protected SymbolKind getSymbolKind(EClass type) {
		return SymbolKindUtil.getSymbolKind(type);
	}

	@Override
	public List<DocumentSymbol> getSymbols(XtextResource resource,
			CancelIndicator cancelIndicator) {

		ArrayList<DocumentSymbol> infos = new ArrayList<>();
		List<DocumentSymbol> rootSymbols = hierarchicalDocumentSymbolService.getSymbols(resource, cancelIndicator);
		for (DocumentSymbol rootSymbol : rootSymbols) {
			Iterable<DocumentSymbol> symbols = Traverser.forTree(DocumentSymbol::getChildren)
					.depthFirstPreOrder(rootSymbol);

			infos.addAll(Lists.newArrayList(symbols));
		}
		return infos;
	}
}
