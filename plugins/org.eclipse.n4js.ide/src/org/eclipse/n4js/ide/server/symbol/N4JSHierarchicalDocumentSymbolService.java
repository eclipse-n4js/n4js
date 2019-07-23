/**
 * Copyright (c) 2019 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.server.symbol;

import java.util.Iterator;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.xtext.ide.server.symbol.HierarchicalDocumentSymbolService;

/**
 *
 */
public class N4JSHierarchicalDocumentSymbolService extends HierarchicalDocumentSymbolService {

	@Override
	protected Iterator<Object> getAllContents(Resource resource) {
		if (resource instanceof N4JSResource) {
			N4JSResource n4jsResource = (N4JSResource) resource;
			EList<EObject> contents = n4jsResource.getContents();
			if (contents.size() > 1) {
				EObject tModule = contents.get(1);
				return EcoreUtil.getAllContents(tModule, true);
			}
		}
		return super.getAllContents(resource);
	}
}
