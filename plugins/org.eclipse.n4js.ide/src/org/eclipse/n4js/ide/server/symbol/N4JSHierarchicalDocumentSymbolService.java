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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.ts.types.AbstractNamespace;
import org.eclipse.n4js.ts.types.ContainerType;
import org.eclipse.n4js.ts.types.TEnum;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.xtext.ide.server.symbol.HierarchicalDocumentSymbolService;

import com.google.common.collect.Iterators;

/**
 *
 */
public class N4JSHierarchicalDocumentSymbolService extends HierarchicalDocumentSymbolService {

	@SuppressWarnings("unchecked")
	@Override
	protected Iterator<Object> getAllContents(Resource resource) {
		if (resource instanceof N4JSResource) {
			N4JSResource n4jsResource = (N4JSResource) resource;
			EList<EObject> contents = n4jsResource.getContents();
			if (contents.size() > 1) {

				EObject tModuleObj = contents.get(1);
				if (tModuleObj instanceof TModule) {
					TModule tModule = (TModule) tModuleObj;

					List<EObject> elements = new ArrayList<>();
					collectNamespaceElements(elements, tModule);
					return Iterators.concat(elements.iterator());
				}
			}
		}
		return super.getAllContents(resource);
	}

	private void collectNamespace(List<EObject> result, AbstractNamespace ns) {
		result.add(ns);
		collectNamespaceElements(result, ns);
	}

	private void collectNamespaceElements(List<EObject> result, AbstractNamespace ns) {
		ns.getNamespaces().forEach(elem -> collectNamespace(result, elem));
		ns.getTypes().forEach(elem -> collectType(result, elem));
		ns.getFunctions().forEach(result::add);
		ns.getExportedVariables().forEach(result::add);
	}

	private void collectType(List<EObject> result, Type type) {
		result.add(type);
		if (type instanceof TEnum) {
			TEnum tEnum = (TEnum) type;
			result.addAll(tEnum.getLiterals());
		} else if (type instanceof ContainerType<?>) {
			ContainerType<?> contType = (ContainerType<?>) type;
			result.addAll(contType.getOwnedMembers());
		}
	}
}
