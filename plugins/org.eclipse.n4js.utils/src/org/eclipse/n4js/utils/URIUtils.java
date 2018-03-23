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

import org.eclipse.core.resources.IResource;

/**
 * Utilities for different URI types
 */
public class URIUtils {

	/**
	 * Converts the given {@link IResource} to a {@link org.eclipse.emf.common.util.URI}. In case the given resource is
	 * a workspace resource, a <i>platform resource URI</i> is returned. In case the given resource is a file based
	 * resource (e.g. an external project), a <i>file URI</i> is returned.
	 *
	 * @returns the a {@link org.eclipse.emf.common.util.URI} location for the given resource
	 */
	static public org.eclipse.emf.common.util.URI convert(IResource iResource) {
		if (iResource == null) {
			return null;
		}

		String projectPath = iResource.toString();
		String fullPathString = iResource.getFullPath().toString();

		org.eclipse.emf.common.util.URI uri;
		if (projectPath.startsWith("P/")) {
			uri = org.eclipse.emf.common.util.URI.createPlatformResourceURI(fullPathString, true);
		} else {
			uri = org.eclipse.emf.common.util.URI.createFileURI(fullPathString);
		}
		return uri;
	}

	/** @returns the a {@link org.eclipse.emf.common.util.URI} location for the given {@link java.net.URI} */
	static public org.eclipse.emf.common.util.URI toFileUri(java.net.URI jnUri) {
		if (jnUri == null) {
			return null;
		}

		File file = new File(jnUri);
		String path = file.getAbsolutePath();
		org.eclipse.emf.common.util.URI uri = org.eclipse.emf.common.util.URI.createFileURI(path);
		return uri;
	}
}
