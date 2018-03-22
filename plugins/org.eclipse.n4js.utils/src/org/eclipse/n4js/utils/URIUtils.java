/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.utils;

import java.io.File;
import java.net.URI;

import org.eclipse.core.resources.IResource;

/**
 * Utilities for different URI types
 */
public class URIUtils {

	/** @returns the a {@link org.eclipse.emf.common.util.URI} location for the given resource */
	static public org.eclipse.emf.common.util.URI convert(IResource iResource) {
		if (iResource == null) {
			return null;
		}

		URI locationURI = iResource.getLocationURI();
		org.eclipse.emf.common.util.URI uri = org.eclipse.emf.common.util.URI.createURI(locationURI.toString());
		return uri;
	}

	/** @returns the a {@link org.eclipse.emf.common.util.URI} location for the given {@link java.net.URI} */
	static public org.eclipse.emf.common.util.URI convert(java.net.URI jnUri) {
		if (jnUri == null) {
			return null;
		}

		File file = new File(jnUri);
		String path = file.getAbsolutePath();
		org.eclipse.emf.common.util.URI uri = org.eclipse.emf.common.util.URI.createFileURI(path);
		return uri;
	}
}
