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
package org.eclipse.n4js.ts.ui.navigation;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.URIHandler;

import org.eclipse.n4js.ts.scoping.builtin.BuiltInSchemeRegistrar;

/**
 * IStorage implementation that can create input streams from n4scheme URIs.
 */
public class N4SchemeURIBasedStorage extends URIBasedStorage {

	private final BuiltInSchemeRegistrar schemeHelper;

	/**
	 * Creates a new storage that resolves with the given helper.
	 */
	public N4SchemeURIBasedStorage(URI uri, BuiltInSchemeRegistrar schemeHelper) {
		super(uri);
		this.schemeHelper = schemeHelper;
	}

	@Override
	public InputStream getContents() throws CoreException {
		URIHandler handler = schemeHelper.createURIHandler(URIConverter.INSTANCE);
		try {
			return handler.createInputStream(getURI(), Collections.emptyMap());
		} catch (IOException e) {
			throw new CoreException(new Status(IStatus.ERROR, "org.eclipse.n4js.ts.ui", "Cannot load "
					+ getFullPath(), e));
		}
	}
}
