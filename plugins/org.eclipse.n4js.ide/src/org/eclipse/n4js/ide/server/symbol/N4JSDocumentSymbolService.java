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

import org.eclipse.lsp4j.Location;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.SymbolInformation;
import org.eclipse.n4js.naming.N4JSQualifiedNameProvider;
import org.eclipse.n4js.resource.N4JSResourceDescriptionStrategy;
import org.eclipse.n4js.scoping.utils.PolyfillUtils;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.n4js.utils.UtilN4;
import org.eclipse.n4js.xtext.ide.server.symbol.XDocumentSymbolService;
import org.eclipse.xtext.findReferences.IReferenceFinder.IResourceAccess;
import org.eclipse.xtext.ide.server.UriExtensions;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Custom N4JS implementation of {@link XDocumentSymbolService} taking advantage of additional location information in
 * the user data of {@link IEObjectDescription}s, thus avoiding to load resources or deserializing TModules when
 * obtaining symbol information. See {@link #getSymbolLocation(IEObjectDescription)}.
 */
@Singleton
@SuppressWarnings("restriction")
public class N4JSDocumentSymbolService extends XDocumentSymbolService {

	@Inject
	private UriExtensions uriExtensions;

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
			Procedure1<? super SymbolInformation> acceptor) {
		SymbolInformation symbol = createSymbol(description);
		if (symbol != null) {
			acceptor.apply(symbol);
		}
	}

	@Override
	protected SymbolInformation createSymbol(IEObjectDescription description) {
		SymbolInformation symbol = super.createSymbol(description);
		if (symbol == null) {
			return null;
		}
		symbol.setContainerName(getContainerName(description));
		symbol.setLocation(getSymbolLocation(description));
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
}
