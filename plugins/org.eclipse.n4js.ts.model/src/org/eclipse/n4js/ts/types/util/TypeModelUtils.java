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

import org.eclipse.emf.common.util.URI;
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
}
