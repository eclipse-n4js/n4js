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
package org.eclipse.n4js.ts.ui.search;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.validation.TypesKeywordProvider;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IResourceServiceProvider;

import com.google.inject.Inject;

/**
 * Label provider used in the UI of find references
 */
public class ReferenceFinderLabelProvider {

	@Inject
	private IResourceServiceProvider.Registry resourceServiceProviderRegistry;

	@Inject
	private IResourceServiceProvider resourceServiceProvider;

	@Inject
	private IQualifiedNameProvider qualifiedNameProvider;

	@Inject
	private TypesKeywordProvider keywordProvider;

	/**
	 * Returns a string representation for the find references UI to represent the matches and the queried object.
	 * Dispatches to the correct, language specific implementation.
	 */
	public String getText(EObject source) {
		URI uri = source.eResource().getURI();
		if (!resourceServiceProvider.canHandle(uri)) {
			IResourceServiceProvider otherServiceProvider = resourceServiceProviderRegistry
					.getResourceServiceProvider(uri);
			if (otherServiceProvider != null) {
				ReferenceFinderLabelProvider otherLabelProvider = otherServiceProvider
						.get(ReferenceFinderLabelProvider.class);
				if (otherLabelProvider != null) {
					return otherLabelProvider.doGetText(source);
				}
			}
		}
		return doGetText(source);
	}

	/**
	 * Returns a string representation for the find references UI to represent the matches and the queried object.
	 */
	protected String doGetText(EObject source) {
		String keyword = getKeywordLabel(source);
		QualifiedName containerName = qualifiedNameProvider.getFullyQualifiedName(source);
		while (containerName == null) {
			source = source.eContainer();
			if (source != null) {
				containerName = qualifiedNameProvider.getFullyQualifiedName(source);
			} else {
				break;
			}
		}
		if (keyword != null && keyword.length() > 0) {
			return keyword + ' ' + containerName;
		}
		return String.valueOf(containerName);
	}

	/**
	 * Returns a prefix that should be used in the label, e.g. 'class' of the context is a class declaration.
	 */
	protected String getKeywordLabel(EObject source) {
		while (source instanceof TypeRef) {
			source = source.eContainer();
		}
		if (source != null) {
			return keywordProvider.keyword(source);
		}
		return null;
	}
}
