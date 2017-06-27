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

import com.google.inject.Singleton
import org.eclipse.n4js.N4JSGlobals
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import com.google.common.base.Strings

/**
 * This class provides methods for calculating file extensions. The calculation takes into account Xpect file extension
 * {@code .xt}. Custom file Xpect file extensions are not supported e.g. when your Xpect tests are configured to run with {@code .xxt} file extension.
 * Deeply nested structures are also not supported e.g. using {@code file.n4js.xt.xt}.
 */
 @Singleton
public class XpectAwareFileExtensionCalculator {

	def public String getXpectAwareFileExtension(EObject eob) {
		if ((eob === null) || (eob.eResource === null)) {
			return "";
		}
		return eob.eResource.URI.getXpectAwareFileExtension;
	}

	/**
	 * Return the file extension of an URI
	 */
	def public String getXpectAwareFileExtension(URI uri) {
		if (uri === null) {
			return "";
		}
		return uri.getXpectAwareFileExtensionOrEmpty
	}

	def private String getXpectAwareFileExtensionOrEmpty(URI uri){
		var ext = uri.fileExtension;
		if(N4JSGlobals.XT_FILE_EXTENSION.equals(ext)){
			//get nested file ext
			ext = uri.trimFileExtension.fileExtension
		}
		return Strings.nullToEmpty(ext)
	}
}
