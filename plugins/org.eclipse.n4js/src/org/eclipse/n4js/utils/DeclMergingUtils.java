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
package org.eclipse.n4js.utils;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.xtext.resource.IEObjectDescription;

/**
 *
 */
public class DeclMergingUtils {

	public static boolean mayBeMerged(EObject elem) {
		if (elem instanceof TModule) {
			Resource resource = elem.eResource();
			return resource != null && URIUtils.isVirtualResourceURI(resource.getURI());
		}
		return ResourceType.getResourceType(elem) == ResourceType.DTS;
	}

	public static boolean mayBeMerged(IEObjectDescription desc) {
		if (TypesPackage.Literals.TMODULE.isSuperTypeOf(desc.getEClass())) {
			return URIUtils.isVirtualResourceURI(desc.getEObjectURI());
		}
		return ResourceType.getResourceType(desc.getEObjectURI()) == ResourceType.DTS;
	}

	public static int compareForMerging(IEObjectDescription d1, IEObjectDescription d2) {
		int cmp = Integer.compare(getKindIndex(d1.getEClass()), getKindIndex(d2.getEClass()));
		if (cmp != 0) {
			return cmp;
		}
		return URIUtils.compare(d1.getEObjectURI(), d2.getEObjectURI());
	}

	private static int getKindIndex(EClass kind) {
		// FIXME improve performance
		if (TypesPackage.Literals.TVARIABLE.isSuperTypeOf(kind)) {
			return 1;
		} else if (TypesPackage.Literals.TFUNCTION.isSuperTypeOf(kind)) {
			return 2;
		} else if (TypesPackage.Literals.TCLASS.isSuperTypeOf(kind)) {
			return 3;
		} else if (TypesPackage.Literals.TINTERFACE.isSuperTypeOf(kind)) {
			return 4;
		} else if (TypesPackage.Literals.TENUM.isSuperTypeOf(kind)) {
			return 5;
		} else if (TypesPackage.Literals.TYPE_ALIAS.isSuperTypeOf(kind)) {
			return 6;
		} else {
			return 7; // e.g. namespaces
		}
	}
}
