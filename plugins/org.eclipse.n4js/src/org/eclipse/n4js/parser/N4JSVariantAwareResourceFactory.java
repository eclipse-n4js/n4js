/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.parser;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.resource.XpectAwareFileExtensionCalculator;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceFactory;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * An {@link XtextResourceFactory} which is aware of N4JS language variants.
 */
public class N4JSVariantAwareResourceFactory extends XtextResourceFactory {

	@Inject
	private XpectAwareFileExtensionCalculator fileExtensionCalculator;

	/** */
	@Inject
	public N4JSVariantAwareResourceFactory(Provider<XtextResource> resourceProvider) {
		super(resourceProvider);
	}

	@Override
	public Resource createResource(URI uri) {
		XtextResource resource = (XtextResource) super.createResource(uri);

		// if resource parser support language variants
		if (resource.getParser() instanceof N4JSSemicolonInjectingParser) {
			// set variant of resource parser
			((N4JSSemicolonInjectingParser) resource.getParser())
					.setVariant(fileExtensionCalculator.getXpectAwareFileExtension(uri));
		}

		return resource;
	}

}
