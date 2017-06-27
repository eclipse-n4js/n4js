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
package org.eclipse.n4js.internal;

import java.net.URL;

import org.eclipse.emf.common.util.URI;

import com.google.inject.Inject;

import org.eclipse.n4js.projectModel.IN4JSArchive;

/**
 * A simple helper that allows to find nfars at a well defined location on the class path.
 */
public class ClasspathPackageManager implements PackageManager {

	private final ClassLoader classLoader;

	/**
	 * Creates a new classpath package manager that uses the context of the given class loader.
	 *
	 * @param classLoader
	 *            the context to load resources from.
	 */
	@Inject
	public ClasspathPackageManager(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	@Override
	public URI getLocation(String projectId) {
		URL resource = classLoader.getResource("env/" + projectId + IN4JSArchive.NFAR_FILE_EXTENSION_WITH_DOT);
		if (resource != null) {
			return URI.createURI(resource.toString());
		}
		return null;
	}

}
