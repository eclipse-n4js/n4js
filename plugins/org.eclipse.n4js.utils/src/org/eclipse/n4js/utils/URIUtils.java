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
import java.io.IOException;
import java.nio.file.Path;

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.URI;

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

	/**
	 * Compensates for the missing {@link URI#equals(Object)} implementation in {@link URI}. Adjusts paths that contain
	 * symlinks.
	 *
	 * @return true iff the given {@link URI}s are equal
	 */
	static public boolean equals(org.eclipse.emf.common.util.URI uri1, org.eclipse.emf.common.util.URI uri2) {
		String string1 = toString(uri1);
		String string2 = toString(uri2);
		return string1.equals(string2);
	}

	/**
	 * Adjusts paths that contain symlinks.
	 *
	 * @return a hash code of the given {@link URI}s
	 */
	static public int hashCode(org.eclipse.emf.common.util.URI uri) {
		return toString(uri).hashCode();
	}

	/**
	 * Adjusts paths that contain symlinks.
	 *
	 * @return true iff the given {@link URI}s are equal
	 */
	static public String toString(org.eclipse.emf.common.util.URI uri) {
		if (uri.isFile()) {
			String fileString = uri.toFileString();
			File file = new File(fileString);
			Path path = file.toPath();
			try {
				Path realPath = path.toRealPath();
				return realPath.toString();
			} catch (IOException e) {
				return fileString;
			}
		} else {
			String string = uri.toString();
			return string;
		}
	}
}
