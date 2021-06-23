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

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.N4JSGlobals;

import com.google.common.base.Strings;

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
	/** Raw file has extension is <code>.xt</code> */
	XT,
	/** Raw file has extension <code>.n4idl</code> or <code>.n4idl.xt</code> */
	N4IDL,
	/** Raw file has extension <code>.ts</code> or <code>.d.ts</code> */
	TS,

	/** Not recognized, e.g. <code>.exe</code> or invalid data, e.g. <code>null</code>. */
	UNKOWN;

	private final static String EXT_JS = N4JSGlobals.JS_FILE_EXTENSION;
	private final static String EXT_JSX = N4JSGlobals.JSX_FILE_EXTENSION;
	private final static String EXT_N4JS = N4JSGlobals.N4JS_FILE_EXTENSION;
	private final static String EXT_N4JSX = N4JSGlobals.N4JSX_FILE_EXTENSION;
	private final static String EXT_N4JSD = N4JSGlobals.N4JSD_FILE_EXTENSION;
	private final static String EXT_XT = N4JSGlobals.XT_FILE_EXTENSION;
	private final static String EXT_N4IDL = N4JSGlobals.N4IDL_FILE_EXTENSION;
	private final static String EXT_TS = N4JSGlobals.TS_FILE_EXTENSION;

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
		if (uri == null)
			return UNKOWN;

		ResourceType resourceType = naiveGetResourceType(uri);

		switch (resourceType) {
		case XT:
			return naiveGetResourceType(uri.trimFileExtension());
		default:
			return resourceType;
		}
	}

	/**
	 * Convenience method for checking if a given resource is an XPECT type with hidden <b>KNOWN</b> extension.
	 *
	 * @return true if given resource is an XPECT resource based on file with double known extension
	 */
	public static boolean xtHidesOtherExtension(URI uri) {
		if (uri == null)
			return false;

		ResourceType resourceType = naiveGetResourceType(uri);
		switch (resourceType) {
		case XT:
			ResourceType innerResourceType = naiveGetResourceType(uri.trimFileExtension());
			return XT.equals(innerResourceType) == false
					&& UNKOWN.equals(innerResourceType) == false;
		default:
			return false;
		}
	}

	/**
	 * Tries to determine file resource type based on {@code URI} file extension. Does not check if data is valid. Does
	 * not handle nested extension. Internal use only.
	 */
	private static ResourceType naiveGetResourceType(URI uri) {
		String fileExtension = uri.fileExtension();

		if (Strings.isNullOrEmpty(fileExtension))
			return UNKOWN;

		switch (fileExtension.toLowerCase()) {
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
		case EXT_XT:
			return XT;
		case EXT_N4IDL:
			return N4IDL;
		case EXT_TS:
			return TS;
		default:
			return UNKOWN;
		}
	}

}
