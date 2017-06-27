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
package org.eclipse.n4js.n4jsx.internal;

import org.eclipse.emf.common.util.URI;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import org.eclipse.n4js.internal.FileBasedWorkspace;
import org.eclipse.n4js.internal.N4JSModel;
import org.eclipse.n4js.internal.N4JSRuntimeCore;
import org.eclipse.n4js.n4jsx.N4JSXGlobals;
import org.eclipse.n4js.n4jsx.validation.N4JSXResourceType;

/**
 * This class overrides the isJsFile method of {@link N4JSRuntimeCore} to consider JSX files as plain JS files
 */
@Singleton
public class N4JSXRuntimeCore extends N4JSRuntimeCore {

	/**
	 * Public for testing purpose.
	 */
	@Inject
	public N4JSXRuntimeCore(FileBasedWorkspace workspace, N4JSModel model) {
		super(workspace, model);
	}

	/**
	 * Check for raw JS-files
	 *
	 * @param uri
	 *            to test
	 * @boolean if ends in .jsx or .jsx.xt or .js or .js.xt
	 */
	@Override
	protected boolean isJsFile(URI uri) {
		N4JSXResourceType resourceType = N4JSXResourceType.getResourceType(uri);
		if (resourceType.equals(N4JSXResourceType.JSX)) {
			return true;
		} else if (resourceType.equals(N4JSXResourceType.N4JSX)) {
			return false;
		}
		return super.isJsFile(uri);
	}

	/**
	 * Return true if the URI is a recognized N4JS file. Provide .n4jsx and .jsx recognized file extensions file
	 * extensions!
	 */
	@Override
	protected boolean isN4File(final URI uri) {
		final String ext = uri != null ? uri.fileExtension() : null;
		return N4JSXGlobals.ALL_JSX_FILE_EXTENSIONS.contains(ext) || super.isN4File(uri);
	}
}
