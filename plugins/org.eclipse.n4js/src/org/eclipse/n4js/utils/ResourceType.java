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
 * This enum represents resource types for N4JS language (and its variants).
 */
public enum ResourceType {
	/** Raw file has extension <code>.js</code> or <code>.js.xt</code> */
	JS,
	/** Raw file has extension <code>.jsx</code> or <code>.jsx.xt</code> */
	JSX,
	/** Raw file has extension <code>.n4js</code> or <code>.n4js.xt</code> */
	N4JS,
	/** Raw file has extension <code>.n4jsx</code> or <code>.n4jsx.xt</code> */
	N4JSX,
	/** Raw file has extension <code>.n4jsd</code> or <code>.n4jsd.xt</code> */
	N4JSD,
	/** Raw file has extension <code>.n4mf</code> or <code>.n4mf.xt</code> */
	N4MF,
	/** Raw file has extension is <code>.xt</code> */
	XT,

	/** Not recognized, e.g. <code>.exe</code> or invalid data, e.g. <code>null</code>. */
	UNKOWN;

	private static Logger LOGGER = Logger.getLogger(ResourceType.class);
	private final static String EXT_JS = N4JSGlobals.JS_FILE_EXTENSION;
	private final static String EXT_JSX = N4JSGlobals.JSX_FILE_EXTENSION;
	private final static String EXT_N4JS = N4JSGlobals.N4JS_FILE_EXTENSION;
	private final static String EXT_N4JSX = N4JSGlobals.N4JSX_FILE_EXTENSION;
	private final static String EXT_N4JSD = N4JSGlobals.N4JSD_FILE_EXTENSION;
	private final static String EXT_N4MF = N4MFConstants.N4MF_FILE_EXTENSION;
	private final static String EXT_XT = N4JSGlobals.XT_FILE_EXTENSION;
	private final static String END_JS_XT = "." + EXT_JS + "." + EXT_XT;
	private final static String END_JSX_XT = "." + EXT_JSX + "." + EXT_XT;
	private final static String END_N4JS_XT = "." + EXT_N4JS + "." + EXT_XT;
	private final static String END_N4JSD_XT = "." + EXT_N4JSD + "." + EXT_XT;
	private final static String END_N4JSX_XT = "." + EXT_N4JSX + "." + EXT_XT;
	private final static String END_N4MF_XT = "." + EXT_N4MF + "." + EXT_XT;

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
			return JS;
		case EXT_JSX:
			return JSX;
		case EXT_N4JS:
			return N4JS;
		case EXT_N4JSX:
			return N4JSX;
		case EXT_N4JSD:
			return N4JSD;
		case EXT_N4MF:
			return N4MF;
		case EXT_XT:
			ResourceType resourceType = getXtHiddenType(uri);
			if (resourceType.equals(UNKOWN))
				return XT;
			else
				return resourceType;
		default:
			return UNKOWN;
		}
	}

	/**
	 * Convenience method for checking if a given resource is an XPECT type with hidden extension.
	 *
	 * @return true if given resource is based on file with double extension known
	 */
	public static boolean xtHidesOtherExtension(URI uri) {
		ResourceType resourceType = getXtHiddenType(uri);
		return JS.equals(resourceType)
				|| JSX.equals(resourceType)
				|| N4JS.equals(resourceType)
				|| N4JSX.equals(resourceType)
				|| N4JSD.equals(resourceType)
				|| N4MF.equals(resourceType);
	}

	/**
	 * For Xpect resources return type hidden by the xt extension.
	 */
	private static ResourceType getXtHiddenType(URI uri) {
		String uriAsString = uri.toString().toLowerCase();
		switch (uriAsString) {
		case END_JS_XT:
			return JS;
		case END_JSX_XT:
			return JSX;
		case END_N4JS_XT:
			return N4JS;
		case END_N4JSX_XT:
			return N4JSX;
		case END_N4JSD_XT:
			return N4JSD;
		case END_N4MF_XT:
			return N4MF;
		default:
			return UNKOWN;
		}
	}

}
