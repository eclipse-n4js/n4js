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
package org.eclipse.n4js.ui;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.n4js.utils.URIUtils;
import org.eclipse.ui.IEditorPart;
import org.eclipse.xtext.ui.editor.LanguageSpecificURIEditorOpener;

/**
 *
 */
public class N4JSLanguageSpecificURIEditorOpener extends LanguageSpecificURIEditorOpener {

	@Override
	public IEditorPart open(final URI uri, final EReference crossReference, final int indexInList,
			final boolean select) {

		String fragment = uri.fragment();
		URI trimedFragmentUri = uri.trimFragment();
		if (trimedFragmentUri.isFile()) {
			URI platformUri = URIUtils.tryToPlatformUri(trimedFragmentUri);
			URI appendedFragmentUri = platformUri.appendFragment(fragment);
			return super.open(appendedFragmentUri, crossReference, indexInList, select);
		}

		return super.open(uri, crossReference, indexInList, select);
	}

}
