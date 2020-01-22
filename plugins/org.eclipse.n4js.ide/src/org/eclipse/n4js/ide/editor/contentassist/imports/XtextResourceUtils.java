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
package org.eclipse.n4js.ide.editor.contentassist.imports;

import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.xtext.resource.XtextResource;

/**
 * Simple utility for obtaining Script object from given resource.
 */
class XtextResourceUtils {

	/**
	 * If resource is {@link N4JSResource} it will use its api to get script. If it is some other {@link XtextResource}
	 * then and its contents are not empty it will return first element casted to {@link Script}. In all other cases
	 * return null.
	 *
	 * @param resource
	 *            the resource to process.
	 * @return Script instance or null
	 */
	public static Script getScript(XtextResource resource) {
		if (resource instanceof N4JSResource) {
			return ((N4JSResource) resource).getScript();
		}
		if (resource.getContents().isEmpty()) {
			return (Script) resource.getContents().get(0);
		}
		return null;
	}
}
