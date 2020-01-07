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
package org.eclipse.n4js.ide.xtext.server;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.ide.server.Document;
import org.eclipse.xtext.resource.XtextResource;

/**
 * Provides {@link Document} and {@link XtextResource}
 */
public interface DocumentResourceProvider {

	/**
	 * Find the XtextResource for the given uri.
	 *
	 * @param uri
	 *            the uri.
	 * @return the resource
	 */
	public XtextResource getResource(URI uri);

	/**
	 * Find the document for the given uri.
	 *
	 * @param uri
	 *            the uri.
	 * @return the document
	 */
	public XDocument getDocument(URI uri);

	/**
	 * Find the document for the given resource.
	 *
	 * @param resource
	 *            the resource.
	 * @return the document
	 */
	public XDocument getDocument(XtextResource resource);
}
