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

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.impl.ExtensibleURIConverterImpl;
import org.eclipse.xtext.resource.ClasspathUriResolutionException;
import org.eclipse.xtext.resource.ClasspathUriUtil;
import org.eclipse.xtext.resource.SynchronizedXtextResourceSet;
import org.eclipse.xtext.util.UriExtensions;

import com.google.inject.Inject;

/**
 * Default binding in case of standalone mode, in UI (OSGi mode) the super type {@link SynchronizedXtextResourceSet} is
 * used with the extension point (cf. IResourceSetInitializer and n4 implementations)
 */
public class ResourceSetWithBuiltInScheme extends SynchronizedXtextResourceSet {

	private final UriExtensions uriExtensions = new UriExtensions();

	@Inject
	private void configureWith(BuiltInSchemeRegistrar registrar) {
		registrar.registerScheme(this);
	}

	private URI resolveClasspathURI(URI uri) {
		return getClasspathUriResolver().resolve(getClasspathURIContext(), uri);
	}

	@Override
	public URIConverter getURIConverter() {
		if (uriConverter == null) {
			uriConverter = new ExtensibleURIConverterImpl() {
				@Override
				public URI normalize(URI uri) {
					URI normalizedURI = normalizationMap.get(uri);
					if (normalizedURI != null) {
						return normalizedURI;
					}
					if (ClasspathUriUtil.isClasspathUri(uri)) {
						URI result = ResourceSetWithBuiltInScheme.this.resolveClasspathURI(uri);
						if (ClasspathUriUtil.isClasspathUri(result))
							throw new ClasspathUriResolutionException(result);
						result = super.normalize(result);
						return result;
					}
					URI result = super.normalize(uri);
					if (!result.isRelative())
						result = uriExtensions.withEmptyAuthority(result);
					return result;
				}

				@Override
				public InputStream createInputStream(URI uri, Map<?, ?> options) throws IOException {
					// timeout is set here because e.g. SAXXMIHandler.resolveEntity(String,String) calls it without a
					// timeout
					// causing the builder to wait too long...
					options = addTimeout(options);
					return super.createInputStream(uri, options);
				}

				@Override
				public Map<String, ?> contentDescription(URI uri, Map<?, ?> options) throws IOException {
					options = addTimeout(options);
					return super.contentDescription(uri, options);
				}

			};
		}
		return super.getURIConverter();
	}

}
