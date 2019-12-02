/**
 * Copyright (c) 2019 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.xtext.resourceset;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ContentHandler;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.URIHandler;
import org.eclipse.xtext.util.UriExtensions;

/**
 * A {@link URIConverter} that will ensure that the normalized form of a URI does have an empty authority.
 */
public class EmptyAuthorityAddingNormalizer implements URIConverter {

	private final URIConverter delegate;
	private final UriExtensions uriExtensions;

	/**
	 * Standard constructor
	 */
	public EmptyAuthorityAddingNormalizer(URIConverter delegate, UriExtensions uriExtensions) {
		this.delegate = delegate;
		this.uriExtensions = uriExtensions;
	}

	/**
	 * @see org.eclipse.emf.ecore.resource.URIConverter#normalize(org.eclipse.emf.common.util.URI)
	 */
	@Override
	public URI normalize(URI uri) {
		URI result = delegate.normalize(uri);
		if (result != null) {
			result = uriExtensions.withEmptyAuthority(result);
		}
		return result;
	}

	/**
	 * @see org.eclipse.emf.ecore.resource.URIConverter#getURIMap()
	 */
	@Override
	public Map<URI, URI> getURIMap() {
		return delegate.getURIMap();
	}

	/**
	 * @see org.eclipse.emf.ecore.resource.URIConverter#getURIHandlers()
	 */
	@Override
	public EList<URIHandler> getURIHandlers() {
		return delegate.getURIHandlers();
	}

	/**
	 * @see org.eclipse.emf.ecore.resource.URIConverter#getURIHandler(org.eclipse.emf.common.util.URI)
	 */
	@Override
	public URIHandler getURIHandler(URI uri) {
		return delegate.getURIHandler(uri);
	}

	/**
	 * @see org.eclipse.emf.ecore.resource.URIConverter#getContentHandlers()
	 */
	@Override
	public EList<ContentHandler> getContentHandlers() {
		return delegate.getContentHandlers();
	}

	/**
	 * @see org.eclipse.emf.ecore.resource.URIConverter#createInputStream(org.eclipse.emf.common.util.URI)
	 */
	@Override
	public InputStream createInputStream(URI uri) throws IOException {
		return delegate.createInputStream(uri);
	}

	/**
	 * @see org.eclipse.emf.ecore.resource.URIConverter#createInputStream(org.eclipse.emf.common.util.URI,
	 *      java.util.Map)
	 */
	@Override
	public InputStream createInputStream(URI uri, Map<?, ?> options) throws IOException {
		return delegate.createInputStream(uri, options);
	}

	/**
	 * @see org.eclipse.emf.ecore.resource.URIConverter#createOutputStream(org.eclipse.emf.common.util.URI)
	 */
	@Override
	public OutputStream createOutputStream(URI uri) throws IOException {
		return delegate.createOutputStream(uri);
	}

	/**
	 * @see org.eclipse.emf.ecore.resource.URIConverter#createOutputStream(org.eclipse.emf.common.util.URI,
	 *      java.util.Map)
	 */
	@Override
	public OutputStream createOutputStream(URI uri, Map<?, ?> options) throws IOException {
		return delegate.createOutputStream(uri, options);
	}

	/**
	 * @see org.eclipse.emf.ecore.resource.URIConverter#delete(org.eclipse.emf.common.util.URI, java.util.Map)
	 */
	@Override
	public void delete(URI uri, Map<?, ?> options) throws IOException {
		delegate.delete(uri, options);
	}

	/**
	 * @see org.eclipse.emf.ecore.resource.URIConverter#contentDescription(org.eclipse.emf.common.util.URI,
	 *      java.util.Map)
	 */
	@Override
	public Map<String, ?> contentDescription(URI uri, Map<?, ?> options) throws IOException {
		return delegate.contentDescription(uri, options);
	}

	/**
	 * @see org.eclipse.emf.ecore.resource.URIConverter#exists(org.eclipse.emf.common.util.URI, java.util.Map)
	 */
	@Override
	public boolean exists(URI uri, Map<?, ?> options) {
		return delegate.exists(uri, options);
	}

	/**
	 * @see org.eclipse.emf.ecore.resource.URIConverter#getAttributes(org.eclipse.emf.common.util.URI, java.util.Map)
	 */
	@Override
	public Map<String, ?> getAttributes(URI uri, Map<?, ?> options) {
		return delegate.getAttributes(uri, options);
	}

	/**
	 * @see org.eclipse.emf.ecore.resource.URIConverter#setAttributes(org.eclipse.emf.common.util.URI, java.util.Map,
	 *      java.util.Map)
	 */
	@Override
	public void setAttributes(URI uri, Map<String, ?> attributes, Map<?, ?> options) throws IOException {
		delegate.setAttributes(uri, attributes, options);
	}

}
