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
package org.eclipse.n4js.resource;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.utils.URIUtils;

import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.google.inject.Singleton;

/**
 * This class provides methods for calculating file extensions. The calculation takes into account Xpect file extension
 * {@code .xt}. Custom file Xpect file extensions are not supported e.g. when your Xpect tests are configured to run
 * with {@code .xxt} file extension. Deeply nested structures are also not supported e.g. using {@code file.n4js.xt.xt}.
 */
@Singleton
public class XpectAwareFileExtensionCalculator {

	/***/
	public String getXpectAwareFileExtension(EObject eob) {
		URI uri = getURI(eob);
		return getXpectAwareFileExtension(uri);
	}

	private URI getURI(EObject eob) {
		if (eob == null) {
			return null;
		}
		if (eob.eResource() != null) {
			return eob.eResource().getURI();
		}
		if (eob instanceof MinimalEObjectImpl) {
			return ((MinimalEObjectImpl) eob).eProxyURI().trimFragment();
		}
		return null;
	}

	/**
	 * Return the file extension of an URI
	 */
	public String getXpectAwareFileExtension(URI uri) {
		if (uri == null) {
			return "";
		}
		return getXpectAwareFileExtensionOrEmpty(uri);
	}

	/**
	 * Returns the name of the file that is referenced by {@code uri} without the potential additional X!PECT file
	 * extension.
	 */
	public String getFilenameWithoutXpectExtension(URI uri) {
		if (!Objects.equal(URIUtils.fileExtension(uri), getXpectAwareFileExtension(uri))) {
			return URIUtils.trimFileExtension(uri).lastSegment();
		} else {
			return uri.lastSegment();
		}
	}

	/**
	 * Returns the URI of the given EObject but without the Xpect file extension in case that is present.
	 */
	public URI getUriWithoutXpectExtension(EObject eob) {
		URI uri = getURI(eob);
		return getUriWithoutXpectExtension(uri);
	}

	/**
	 * Removes the Xpect file extension in case that is present of the given URI.
	 */
	public URI getUriWithoutXpectExtension(URI uri) {
		String fileName = getFilenameWithoutXpectExtension(uri);
		URI uriWithoutXpectExtension = uri.trimSegments(1).appendSegment(fileName);
		return uriWithoutXpectExtension;
	}

	private String getXpectAwareFileExtensionOrEmpty(URI uri) {
		String ext = URIUtils.fileExtension(uri);
		if (N4JSGlobals.XT_FILE_EXTENSION.equals(ext)) {
			// get nested file ext
			ext = URIUtils.fileExtension(uri.trimFileExtension());
		}
		return Strings.nullToEmpty(ext);
	}
}
