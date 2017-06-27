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
package org.eclipse.n4js.n4jsx.resource;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.n4js.resource.N4JSLinker;
import org.eclipse.n4js.n4jsx.n4JSX.JSXPropertyAttribute;

/**
 *
 */
public class N4JSXLinker extends N4JSLinker {
	/**
	 * Set propertyAsText property for JSXPropertyAttribute
	 */
	@Override
	protected void setOtherElementAsText(String tokenText, EObject obj, Object value) {
		if ((obj instanceof JSXPropertyAttribute) && (value instanceof String)) {
			((JSXPropertyAttribute) obj).setPropertyAsText((String) value);
		}
	}
}
