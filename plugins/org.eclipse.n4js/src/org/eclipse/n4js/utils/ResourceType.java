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
package org.eclipse.n4js.utils;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.n4mf.utils.N4MFConstants;

/**
 *
 */
public enum ResourceType {
	/** Raw file has extension <code>.js</code> or <code>.js.xt</code> */
	JS,
	/** Raw file has extension <code>.n4js</code> or <code>.n4js.xt</code> */
	N4JS,
	/** Raw file has extension <code>.n4jsd</code> or <code>.n4jsd.xt</code> */
	N4JSD,
	/** Raw file has extension <code>.n4mf</code> or <code>.n4mf.xt</code> */
	N4MF,

	/**
	 * Raw file has extension is <code>.xt</code> that doesn't hide any other known extensions.
	 */
	XT,

	/** Not recognized. Provided data could be invalid, e.g. <code>null</code>. */
	UNKOWN;

	private static Logger LOGGER = Logger.getLogger(ResourceType.class);
	private final static String EXT_JS = N4JSGlobals.JS_FILE_EXTENSION;
	private final static String EXT_N4JS = N4JSGlobals.N4JS_FILE_EXTENSION;
	private final static String EXT_N4JSD = N4JSGlobals.N4JSD_FILE_EXTENSION;
	private final static String EXT_N4MF = N4MFConstants.N4MF_FILE_EXTENSION;
	private final static String EXT_XT = N4JSGlobals.XT_FILE_EXTENSION;
	private final static String END_JS_XT = "." + N4JSGlobals.JS_FILE_EXTENSION + "." + EXT_XT;
	private final static String END_N4JS_XT = "." + N4JSGlobals.N4JS_FILE_EXTENSION + "." + EXT_XT;
	private final static String END_N4JSD_XT = "." + N4JSGlobals.N4JSD_FILE_EXTENSION + "." + EXT_XT;
	private final static String END_N4MF_XT = "." + N4MFConstants.N4MF_FILE_EXTENSION + "." + EXT_XT;

	/**
	 * Based on {@link URI} of the provided {@link EObject eObject} determines type of the resource. Delegates to
	 * {@link #getResourceType(URI)}. Null safe (in that case returns {@link #UNKOWN} ).
	 */
	public static ResourceType getResourceType(EObject eObject) {
		if (eObject == null) {
			return ResourceType.UNKOWN;
		}
		return getResourceType(eObject.eResource());
	}

	/**
	 * Based on {@link URI} of the provided {Resource resource} determines type of the resource. Delegates to
	 * {@link #getResourceType(URI)}. Null safe (in that case returns {@link #UNKOWN} ).
	 */
	public static ResourceType getResourceType(Resource resource) {
		if (resource == null) {
			return ResourceType.UNKOWN;
		}
		return getResourceType(resource.getURI());
	}

	/**
	 * Based on the provided {@link URI} determines type of the resource. Null safe (in that case returns
	 * {@link #UNKOWN} ).
	 */
	public static ResourceType getResourceType(URI uri) {
		if (uri == null) {
			return UNKOWN;
		}

		String fileExtension = uri.fileExtension();
		if (fileExtension == null) {
			LOGGER.info("URI has no file extension " + uri);
			return UNKOWN;
		} else {
			fileExtension = fileExtension.toLowerCase();
		}

		switch (fileExtension) {
		case EXT_JS:
			return ResourceType.JS;
		case EXT_N4JS:
			return ResourceType.N4JS;
		case EXT_N4JSD:
			return ResourceType.N4JSD;
		case EXT_N4MF:
			return ResourceType.N4JSD;
		case EXT_XT:
			ResourceType resourceType = getXtHiddenType(uri);
			if (resourceType.equals(UNKOWN))
				return XT;
			else
				return resourceType;
		default:
			return ResourceType.UNKOWN;
		}
	}

	/**
	 * For Xpect resources return type hidden by the xt extension.
	 */
	private static ResourceType getXtHiddenType(URI uri) {
		if (uri == null) {
			return UNKOWN;
		}

		String uriAsString = uri.toString().toLowerCase();
		if (uriAsString.endsWith(END_JS_XT)) {
			return JS;
		} else if (uriAsString.endsWith(END_N4JS_XT)) {
			return N4JS;
		} else if (uriAsString.endsWith(END_N4JSD_XT)) {
			return N4JSD;
		} else if (uriAsString.endsWith(END_N4MF_XT)) {
			return N4MF;
		} else
			return ResourceType.UNKOWN;
	}

	/**
	 * Convenience method for checking if a given resource is an XPECT type with hidden extension.
	 *
	 * @return true if given resource is based on file with double extension known
	 */
	public static boolean xtHidesOtherExtension(URI uri) {
		ResourceType resourceType = getXtHiddenType(uri);
		return JS.equals(resourceType)
				|| N4JS.equals(resourceType)
				|| N4JSD.equals(resourceType)
				|| N4MF.equals(resourceType);
	}

}
