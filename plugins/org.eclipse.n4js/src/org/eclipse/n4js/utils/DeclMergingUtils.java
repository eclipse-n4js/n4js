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
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.n4js.AnnotationDefinition;
import org.eclipse.n4js.scoping.utils.QualifiedNameUtils;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.xtext.resource.IEObjectDescription;

/**
 *
 */
public class DeclMergingUtils {

	public static boolean mayBeMerged(EObject elem) {
		return ResourceType.getResourceType(elem) == ResourceType.DTS
				&& (isGlobal(elem) || isContainedInDeclaredModule(elem));
	}

	public static boolean mayBeMerged(IEObjectDescription desc) {
		return ResourceType.getResourceType(desc.getEObjectURI()) == ResourceType.DTS
				&& (isGlobal(desc) || isContainedInDeclaredModule(desc));
	}

	private static boolean isGlobal(EObject elem) {
		EObject root = EcoreUtil.getRootContainer(elem);
		return root instanceof TModule && AnnotationDefinition.GLOBAL.hasAnnotation((TModule) root);
	}

	private static boolean isGlobal(IEObjectDescription desc) {
		return QualifiedNameUtils.isGlobal(desc.getQualifiedName());
	}

	private static boolean isContainedInDeclaredModule(EObject elem) {
		Resource resource = elem != null ? elem.eResource() : null;
		return resource != null && URIUtils.isVirtualResourceURI(resource.getURI());
	}

	private static boolean isContainedInDeclaredModule(IEObjectDescription desc) {
		return desc != null && URIUtils.isVirtualResourceURI(desc.getEObjectURI());
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
		if (TypesPackage.Literals.TCLASS.isSuperTypeOf(kind)) {
			return 1;
		} else if (TypesPackage.Literals.TINTERFACE.isSuperTypeOf(kind)) {
			return 2;
		} else if (TypesPackage.Literals.TENUM.isSuperTypeOf(kind)) {
			return 3;
		} else if (TypesPackage.Literals.TYPE_ALIAS.isSuperTypeOf(kind)) {
			return 4;
		} else if (TypesPackage.Literals.TFUNCTION.isSuperTypeOf(kind)) {
			return 5;
		} else if (TypesPackage.Literals.TVARIABLE.isSuperTypeOf(kind)) {
			return 6;
		} else {
			return 7; // e.g. namespaces
		}
	}
}
