/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.n4jsx.ui.organize.imports;

import org.eclipse.emf.ecore.EObject;

import com.google.common.base.Strings;

import org.eclipse.n4js.ui.organize.imports.IReferenceFilter;
import org.eclipse.n4js.ui.organize.imports.ReferenceProxyInfo;
import org.eclipse.n4js.n4jsx.n4JSX.JSXElementName;
import org.eclipse.n4js.n4jsx.n4JSX.JSXPropertyAttribute;

/**
 * N4JSX needs to filter cross refs that are HTML tags, since we did not add
 * proper N4ST definitions for them and instead we suppress error on cross ref
 * proxy in the dedicated validator).
 *
 * Long live the Hack!
 */
public class N4JSXReferencesFilter implements IReferenceFilter {

	@Override
	public boolean test(ReferenceProxyInfo proxyInfo) {
		return !isToBeIgnored(proxyInfo);
	}

	/**
	 * If reference is using name that starts with lower case it looks like HTML
	 * tag, if on top of that it is part of {@link JSXElementName} or
	 * {@link JSXPropertyAttribute} than this reference should not be subject to
	 * Organize Imports.
	 */
	private boolean isToBeIgnored(ReferenceProxyInfo proxyInfo) {
		// only if reference is looks like HTML tag
		String usedName = proxyInfo.name;
		boolean isLikeHTML = Strings.isNullOrEmpty(usedName) ? false : Character.isLowerCase(usedName.charAt(0));

		// is reference part of JSX element
		EObject refContainer = proxyInfo.eobject.eContainer();
		return isLikeHTML && (refContainer == null ? false
				: refContainer instanceof JSXElementName || refContainer instanceof JSXPropertyAttribute);
	}

}
