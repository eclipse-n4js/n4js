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
package org.eclipse.n4js.ts.scoping.builtin;

import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.resource.ClasspathUriUtil;
import org.eclipse.xtext.util.Strings;

/**
 * Marker interface that defines the constant for the {@code n4scheme} built-in URI scheme.
 *
 * N4Scheme URIs use the format {@code n4scheme://environment-name/path-in-src.ext} and point to a resource from the
 * execution environment. The URI resolves to a location in the workspace if the execution environment with the given
 * name is defined locally, or to a resource that is shipped as built-in execution environment.
 *
 * It is important that the environment name is the authority of the URI to prevent from relative URI that resolve to
 * other execution environments in a semi-transparent way.
 */
public interface N4Scheme {

	/**
	 * The built-in scheme {@code n4scheme}
	 */
	String SCHEME = "n4scheme";

	/**
	 * Returns true if the given URI is using the {@link #SCHEME n4scheme}.
	 *
	 * @param uri
	 *            the URI to check (may not be null).
	 */
	static boolean isN4Scheme(URI uri) {
		return SCHEME.equals(uri.scheme());
	}

	/**
	 * Returns true if the given object is from a resource with the {@link #SCHEME n4scheme}.
	 *
	 * @param to
	 *            the object to check (may not be null).
	 */
	static boolean isFromResourceWithN4Scheme(EObject to) {
		if (to.eIsProxy()) {
			return isN4Scheme(EcoreUtil.getURI(to));
		} else {
			return isResourceWithN4Scheme(to.eResource());
		}
	}

	/**
	 * Returns true if the given resource's URI is using the {@link #SCHEME n4scheme}.
	 *
	 * @param res
	 *            the resource to check (may not be null).
	 */
	static boolean isResourceWithN4Scheme(Resource res) {
		return isN4Scheme(res.getURI());
	}

	/**
	 * Convert the given n4scheme-URI to a classpath-URI.
	 */
	static URI toClasspathURI(URI uriWithN4Scheme) {
		String[] allSegments = new String[uriWithN4Scheme.segmentCount() + 1];
		allSegments[0] = "env";
		for (int i = 0; i < uriWithN4Scheme.segmentCount(); i++) {
			allSegments[i + 1] = uriWithN4Scheme.segment(i);
		}
		URI classpathURI = URI.createHierarchicalURI(
				ClasspathUriUtil.CLASSPATH_SCHEME,
				uriWithN4Scheme.authority(),
				uriWithN4Scheme.device(),
				allSegments,
				uriWithN4Scheme.query(),
				uriWithN4Scheme.fragment());
		return classpathURI;
	}

	/**
	 * Utility to create valid URIs with the {@link N4Scheme#SCHEME n4scheme}.
	 */
	class N4URI {

		/**
		 * Create a new URI without fragment.
		 */
		public static URI create(String path) {
			return create(path, null);
		}

		/**
		 * Create a new URI with fragment.
		 */
		private static URI create(String path, String fragment) {
			List<String> segments = Strings.split(path, '/');
			URI result = URI.createHierarchicalURI(SCHEME, null, null,
					segments.toArray(new String[segments.size()]), null, fragment);
			return result;
		}

	}

}
