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
package org.eclipse.n4js.ui.utils;

import java.net.URI;

import org.eclipse.core.resources.IResource;

/**
 * Utilities for
 */
public class URIUtils {

	/** @returns the {@link org.eclipse.emf.common.util.URI} location of the given resource */
	static public org.eclipse.emf.common.util.URI convert(IResource iResource) {
		if (iResource == null) {
			return null;
		}
		// org.eclipse.emf.common.util.URI uri = org.eclipse.emf.common.util.URI
		// .createFileURI(iResource.getLocation().toFile().getAbsolutePath());
		URI locationURI = iResource.getLocationURI();
		org.eclipse.emf.common.util.URI uri = org.eclipse.emf.common.util.URI.createURI(locationURI.toString());
		return uri;
	}
}
