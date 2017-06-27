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
package org.eclipse.n4js.n4jsx.validation;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import org.eclipse.n4js.utils.ResourceType;

/**
 * This enum represents resource types for N4JSX language. There possible values are N4JSX, JSX and OTHER.
 */
public enum N4JSXResourceType {
	/** Raw file has extension <code>.n4jsx</code> or <code>.n4jsx.xt</code> */
	N4JSX,
	/** Raw file has extension <code>.jsx</code> or <code>.jsx.xt</code> */
	JSX,
	/** Raw file has other extension */
	OTHER;

	private static Logger LOGGER = Logger.getLogger(ResourceType.class);

	private final static String EXT_N4JSX = "n4jsx";
	private final static String EXT_JSX = "jsx";
	private final static String EXT_XT = "xt";
	private final static String END_N4JSX_XT = "." + EXT_N4JSX + "." + EXT_XT;
	private final static String END_JSX_XT = "." + EXT_JSX + "." + EXT_XT;

	/**
	 * Retrieve the resource type of an URI, either N4JSX, JSX or OTHER.
	 *
	 * @param uri
	 *            the resource URI
	 */
	public static N4JSXResourceType getResourceType(URI uri) {
		if (uri == null)
			return N4JSXResourceType.OTHER;

		ResourceType resourceType = ResourceType.getResourceType(uri);
		if (resourceType != ResourceType.UNKOWN && resourceType != ResourceType.XT) {
			return N4JSXResourceType.OTHER;
		} else {
			String fileExtension = uri.fileExtension();

			if (fileExtension == null) {
				LOGGER.info("URI has no file extension " + uri);
				return N4JSXResourceType.OTHER;
			} else {
				fileExtension = fileExtension.toLowerCase();
			}

			switch (fileExtension) {
			case EXT_N4JSX:
				return N4JSXResourceType.N4JSX;
			case EXT_JSX:
				return N4JSXResourceType.JSX;
			case EXT_XT:
				N4JSXResourceType resourceTypeWithinXT = getXtHiddenType(uri);
				if (resourceTypeWithinXT.equals(N4JSXResourceType.OTHER))
					return N4JSXResourceType.OTHER;
				else
					return resourceTypeWithinXT;
			default:
				return N4JSXResourceType.OTHER;
			}
		}
	}

	/**
	 * Retrieve the resource type of an EObject, either N4JSX, JSX or OTHER.
	 *
	 * * @param eobj the EObject contained in the resource
	 */
	public static N4JSXResourceType getResourceType(EObject eobj) {
		if (eobj == null)
			return N4JSXResourceType.OTHER;

		Resource resource = eobj.eResource();
		if (resource == null)
			return N4JSXResourceType.OTHER;
		URI uri = resource.getURI();
		return getResourceType(uri);
	}

	/**
	 * For Xpect resources return type hidden by the xt extension.
	 *
	 * @param uri
	 *            the resource URI
	 */
	private static N4JSXResourceType getXtHiddenType(URI uri) {
		if (uri == null) {
			return N4JSXResourceType.OTHER;
		}

		String uriAsString = uri.toString().toLowerCase();
		if (uriAsString.endsWith(END_JSX_XT)) {
			return N4JSXResourceType.JSX;
		} else if (uriAsString.endsWith(END_N4JSX_XT)) {
			return N4JSXResourceType.N4JSX;
		} else
			return N4JSXResourceType.OTHER;
	}
}
