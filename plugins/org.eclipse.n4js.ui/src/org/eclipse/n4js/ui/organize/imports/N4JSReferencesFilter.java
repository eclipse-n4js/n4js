/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ui.organize.imports;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.n4JS.JSXElementName;
import org.eclipse.n4js.n4JS.JSXPropertyAttribute;
import org.eclipse.n4js.utils.ResourceType;

import com.google.common.base.Strings;

/**
 * N4JS is not doing any extra filtering on proxy cross refs, but sub languages might need to take advantage of this
 * variation point.
 */
public class N4JSReferencesFilter implements IReferenceFilter {

	@Override
	public boolean test(ReferenceProxyInfo proxyInfo) {
		ResourceType resourceType = ResourceType.getResourceType(proxyInfo.eobject);
		switch (resourceType) {
		case N4JSX:
			return !n4jxIsToBeIgnored(proxyInfo);

		default:
			return true;
		}
	}

	/**
	 * If reference is using name that starts with lower case it looks like HTML tag, if on top of that it is part of
	 * {@link JSXElementName} or {@link JSXPropertyAttribute} than this reference should not be subject to Organize
	 * Imports.
	 */
	private boolean n4jxIsToBeIgnored(ReferenceProxyInfo proxyInfo) {
		// only if reference is looks like HTML tag
		String usedName = proxyInfo.name;
		boolean isLikeHTML = Strings.isNullOrEmpty(usedName) ? false : Character.isLowerCase(usedName.charAt(0));

		// is reference part of JSX element
		EObject refContainer = proxyInfo.eobject.eContainer();
		return isLikeHTML && (refContainer == null ? false
				: refContainer instanceof JSXElementName || refContainer instanceof JSXPropertyAttribute);
	}
}
