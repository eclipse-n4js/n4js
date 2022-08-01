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

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.n4js.AnnotationDefinition;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.N4JSMetaModelUtils.N4JSMetaModelCache;
import org.eclipse.n4js.resource.N4JSResourceDescriptionStrategy;
import org.eclipse.n4js.scoping.utils.QualifiedNameUtils;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.resource.IEObjectDescription;

/**
 * Utilities for handling declaration merging in .d.ts files. See {@link DeclMergingHelper} for details.
 */
public class DeclMergingUtils {

	private static final N4JSMetaModelCache<Integer> cachedKindIndices = new N4JSMetaModelCache<>(eClass -> {
		// non-hollow elements come first
		// (and within this group, first the classifiers and then the other elements)
		if (TypesPackage.Literals.TCLASS.isSuperTypeOf(eClass)) {
			return 1;
		} else if (TypesPackage.Literals.TENUM.isSuperTypeOf(eClass)) {
			return 2;
		} else if (TypesPackage.Literals.TFUNCTION.isSuperTypeOf(eClass)) {
			return 3;
		} else if (TypesPackage.Literals.TVARIABLE.isSuperTypeOf(eClass)) {
			return 4;
		}
		// hollow elements come next
		// (again, first the classifiers and then the other elements)
		if (TypesPackage.Literals.TINTERFACE.isSuperTypeOf(eClass)) {
			return 5;
		} else if (TypesPackage.Literals.TYPE_ALIAS.isSuperTypeOf(eClass)) {
			return 6;
		}
		// other cases (esp. namespaces)
		return 7;
	});

	/**
	 * Returns <code>true</code> iff the given element is allowed to be merged with other elements by way of declaration
	 * merging.
	 */
	public static boolean mayBeMerged(IEObjectDescription desc) {
		return (ResourceType.getResourceType(desc.getEObjectURI()) == ResourceType.DTS);
	}

	/**
	 * Returns <code>true</code> iff the given element is allowed to be merged with other elements by way of declaration
	 * merging.
	 */
	public static boolean mayBeMerged(EObject elem) {
		return ResourceType.getResourceType(elem) == ResourceType.DTS;
	}

	/**
	 * Compares the elements represented by the two given {@link IEObjectDescription}s (see
	 * {@link Comparable#compareTo(Object)}) such that the first element in a collection of merged elements sorted
	 * according to this comparison will be the <em>representative</em> of these merged elements, i.e. the element that
	 * should be used whenever a single element is required (e.g. the target element of an {@link IdentifierRef}, the
	 * declared type of a {@link ParameterizedTypeRef}).
	 */
	public static int compareForMerging(IEObjectDescription d1, IEObjectDescription d2) {
		int cmp = Integer.compare(cachedKindIndices.get(d1.getEClass()), cachedKindIndices.get(d2.getEClass()));
		if (cmp != 0) {
			return cmp;
		}
		return URIUtils.compare(d1.getEObjectURI(), d2.getEObjectURI());
	}

	/** Returns <code>true</code> iff the element represented by the given description is global. */
	public static boolean isGlobal(IEObjectDescription desc) {
		return QualifiedNameUtils.isGlobal(desc.getQualifiedName());
	}

	/** Returns <code>true</code> iff the given element is global. */
	public static boolean isGlobal(EObject elem) {
		EObject root = EcoreUtil.getRootContainer(elem);
		return root instanceof TModule && AnnotationDefinition.GLOBAL.hasAnnotation((TModule) root);
	}

	/** Returns <code>true</code> iff the given element is the main module of a project. */
	public static boolean isMainModule(IEObjectDescription desc) {
		return N4JSResourceDescriptionStrategy.getMainModule(desc);
	}

	/** Returns <code>true</code> iff the given element is the main module of a project. */
	public static boolean isMainModule(EObject elem) {
		return elem instanceof TModule && ((TModule) elem).isMainModule();
	}

	/** Returns <code>true</code> iff the given element is the main module of a project or a (transitive) child. */
	public static boolean isOrInMainModule(EObject elem) {
		TModule tModule = EcoreUtil2.getContainerOfType(elem, TModule.class);
		return tModule != null && tModule.isMainModule();
	}

	/** Returns <code>true</code> iff the element represented by the given description is from a declared module. */
	public static boolean isContainedInDeclaredModule(IEObjectDescription desc) {
		return desc != null && URIUtils.isVirtualResourceURI(desc.getEObjectURI());
	}

	/** Returns <code>true</code> iff the given element is from a declared module. */
	public static boolean isContainedInDeclaredModule(EObject elem) {
		Resource resource = elem != null ? elem.eResource() : null;
		return resource != null && URIUtils.isVirtualResourceURI(resource.getURI());
	}

	/** Returns <code>true</code> iff the given element is either a non-ambient module or module augmentation. */
	public static boolean isAugmentationModuleOrModule(EObject eobj) {
		TModule tModule = EcoreUtil2.getContainerOfType(eobj, TModule.class);
		if (tModule == null) {
			return false;
		}
		URI uri = EcoreUtil.getURI(eobj);
		if (URIUtils.isVirtualResourceURI(uri)) {
			return AnnotationDefinition.MODULE_AUGMENTATION.hasAnnotation(tModule);
		} else {
			return true;
		}
	}

	/** Returns <code>true</code> iff the given element is either a non-ambient module or module augmentation. */
	public static boolean isAugmentationModuleOrModule(IEObjectDescription descr) {
		EObject eobj = descr.getEObjectOrProxy();
		return isAugmentationModuleOrModule(eobj);
	}
}
