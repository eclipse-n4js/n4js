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
package org.eclipse.n4js.ts.types.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.TypesPackage;

/**
 * Utility methods related to the type models defined in Types.xcore and TypeRefs.xcore.
 */
public class TypeModelUtils {

	/**
	 * Tells if the given URI points to a {@link TModule#getComposedMemberCaches() cached composed member} in the
	 * TModule of an N4JS resource.
	 */
	public static boolean isComposedMemberURI(URI uri) {
		return isComposedMemberURIFragment(uri.fragment());
	}

	/**
	 * Tells if the given URI fragment points to a {@link TModule#getComposedMemberCaches() cached composed member} in
	 * the TModule of an N4JS resource.
	 */
	public static boolean isComposedMemberURIFragment(String uriFragment) {
		return uriFragment.startsWith(
				"/1/@" + TypesPackage.eINSTANCE.getTModule_ComposedMemberCaches().getName() + ".");
	}

	/**
	 * Tells if the given URI fragment points to a {@link TModule#getComposedMemberCaches() cached composed member} or
	 * {@link TModule#getExposedInternalTypes() cache} in the TModule of an N4JS resource.
	 */
	public static boolean isURIFragmentToPostProcessingCache(String uriFragment) {
		return uriFragment.startsWith(
				"/1/@" + TypesPackage.eINSTANCE.getTModule_ComposedMemberCaches().getName() + ".") ||
				uriFragment.startsWith(
						"/1/@" + TypesPackage.eINSTANCE.getTModule_ExposedInternalTypes().getName() + ".");
	}

	/**
	 * @return true if the given EObject instance is a composed TMember
	 */
	public static boolean isComposedTElement(EObject eobj) {
		return ((eobj instanceof TMember) && ((TMember) eobj).isComposed());
	}

	/**
	 * @return the single list of that input element if it is not a composed element. Otherwise, return the list of
	 *         constituent members.
	 */
	public static List<EObject> getRealElements(EObject eobj) {
		List<EObject> result = new ArrayList<>();
		if (isComposedTElement(eobj)) {
			List<TMember> constituentMembers = ((TMember) eobj).getConstituentMembers();
			for (TMember constituentMember : constituentMembers) {
				result.add(constituentMember);
			}
		} else {
			result.add(eobj);
		}
		return result;
	}
}
