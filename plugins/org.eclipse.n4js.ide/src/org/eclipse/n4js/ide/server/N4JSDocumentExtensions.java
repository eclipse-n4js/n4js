/**
 * Copyright (c) 2022 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.server;

import java.util.Objects;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.lsp4j.Location;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.Range;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.resource.N4JSLocationInFileProvider;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.utils.URIUtils;
import org.eclipse.n4js.xtext.resource.XITextRegionWithLineInformation;
import org.eclipse.xtext.ide.server.DocumentExtensions;

import com.google.inject.Inject;

/**
 *
 */
public class N4JSDocumentExtensions extends DocumentExtensions {

	@Inject
	N4JSLocationInFileProvider locationProvider;

	@Override
	public Location newLocation(EObject obj) {
		Resource resource = obj.eResource();
		URI uri = resource.getURI();
		if (resource instanceof N4JSResource && ((N4JSResource) resource).isNested()) {
			uri = ((N4JSResource) resource).getHostUri();
		}
		if (Objects.equals(N4JSGlobals.DTS_FILE_EXTENSION, URIUtils.fileExtension(uri))) {
			obj = locationProvider.convertToSource(obj);
			for (EObject elem2 = obj; elem2 != null; elem2 = elem2.eContainer()) {
				for (Adapter adapter : elem2.eAdapters()) {
					if (adapter instanceof XITextRegionWithLineInformation) {
						XITextRegionWithLineInformation nodeInfo = (XITextRegionWithLineInformation) adapter;
						// convert to 0-based line numbers
						Position start = new Position(nodeInfo.getLineNumber() - 1, nodeInfo.getCharacter());
						Position end = new Position(nodeInfo.getEndLineNumber() - 1, nodeInfo.getEndCharacter());
						Range range = new Range(start, end);
						return new Location(uri.toString(), range);
					}
				}
			}
		}
		return super.newLocation(obj);
	}
}
