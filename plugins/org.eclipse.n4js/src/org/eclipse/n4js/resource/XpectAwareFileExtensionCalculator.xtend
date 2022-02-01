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
package org.eclipse.n4js.resource

import com.google.common.base.Strings
import com.google.inject.Singleton
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl
import org.eclipse.n4js.N4JSGlobals
import org.eclipse.n4js.utils.URIUtils

/**
 * This class provides methods for calculating file extensions. The calculation takes into account Xpect file extension
 * {@code .xt}. Custom file Xpect file extensions are not supported e.g. when your Xpect tests are configured to run with {@code .xxt} file extension.
 * Deeply nested structures are also not supported e.g. using {@code file.n4js.xt.xt}.
 */
 @Singleton
public class XpectAwareFileExtensionCalculator {

	def public String getXpectAwareFileExtension(EObject eob) {
		val uri = getURI(eob);
		return getXpectAwareFileExtension(uri);
	}
	
	def private URI getURI(EObject eob) {
		if (eob === null) {
			return null;
		}
		if (eob.eResource !== null) {
			return eob.eResource.URI;
		}
		if (eob instanceof MinimalEObjectImpl) {
			return eob.eProxyURI.trimFragment;
		}
		return null;
	}

	/**
	 * Return the file extension of an URI
	 */
	def public String getXpectAwareFileExtension(URI uri) {
		if (uri === null) {
			return "";
		}
		return getXpectAwareFileExtensionOrEmpty(uri)
	}
	
	/**
	 * Returns the name of the file that is referenced by {@code uri}
	 * without the potential additional X!PECT file extension.
	 */
	def public String getFilenameWithoutXpectExtension(URI uri) {
		if (URIUtils.fileExtension(uri) != getXpectAwareFileExtension(uri)) {
			return URIUtils.trimFileExtension(uri).lastSegment;
		} else {
			return uri.lastSegment;
		}
	}
	
	/**
	 * Returns the URI of the given EObject but without the Xpect file
	 * extension in case that is present.
	 */
	def public URI getUriWithoutXpectExtension(EObject eob) {
		val uri = getURI(eob);
		return getUriWithoutXpectExtension(uri);
	}
	
	/**
	 * Removes the Xpect file extension in case that is present of the given URI.
	 */
	def public URI getUriWithoutXpectExtension(URI uri) {
		val fileName = getFilenameWithoutXpectExtension(uri);
		val uriWithoutXpectExtension = uri.trimSegments(1).appendSegment(fileName);
		return uriWithoutXpectExtension;
	}

	def private String getXpectAwareFileExtensionOrEmpty(URI uri){
		var ext = URIUtils.fileExtension(uri);
		if(N4JSGlobals.XT_FILE_EXTENSION.equals(ext)){
			//get nested file ext
			ext = URIUtils.fileExtension(uri.trimFileExtension)
		}
		return Strings.nullToEmpty(ext)
	}
}
