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
package org.eclipse.n4js.xpect.ui.common;

import org.eclipse.xtext.resource.XtextResource;

/**
 */
public class XtextResourceCleanUtil {

	/**
	 * Remove OutdatedStateAdapter from provided{@link XtextResource} and return it.
	 *
	 * @return {@link XtextResource}
	 */
	public static XtextResource cleanXtextResource(XtextResource xtextResource) {
		// Since here we are reusing the same xtextresource and not reparsing the stream,
		// we must take care that stale Xtext-documents associated with this resource do not effect us.
		// An assertion looks for org.eclipse.xtext.ui.editor.model.XtextDocument.OutdatedStateAdapter
		// in org.eclipse.xtext.ui.editor.model.XtextDocument.setInput(XtextResource)
		// To reuse the resource we remove it here:
		// Adapter toBeRemoved = null;
		// for (Adapter a : xtextResource.eAdapters()) {
		// if (a instanceof OutdatedStateAdapter)
		// toBeRemoved = a;
		// }
		// if (toBeRemoved != null)
		// xtextResource.eAdapters().remove(toBeRemoved);

		return xtextResource;
	}

}
