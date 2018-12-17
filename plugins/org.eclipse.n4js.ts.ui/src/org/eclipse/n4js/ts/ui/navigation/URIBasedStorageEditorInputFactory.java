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
package org.eclipse.n4js.ts.ui.navigation;

import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.model.ResourceForIEditorInputFactory;

/**
 * Handle our specialized {@link IStorage} implementation, e.g. hyperlinks to external library modules. The resource is
 * created from the URI in the {@link IURIBasedStorage}.
 */
public class URIBasedStorageEditorInputFactory extends ResourceForIEditorInputFactory {

	@Override
	protected Resource createResourceFor(IStorage storage) throws CoreException {
		if (storage instanceof IURIBasedStorage) {
			URI uri = ((IURIBasedStorage) storage).getURI();
			ResourceSet resourceSet = getResourceSet(uri);
			configureResourceSet(resourceSet, uri);
			XtextResource resource = (XtextResource) getResourceFactory().createResource(uri);
			resourceSet.getResources().add(resource);
			resource.setValidationDisabled(isValidationDisabled(uri, storage));
			return resource;
		} else {
			return super.createResourceFor(storage);
		}
	}

	/**
	 * Template method that can be overridden by concrete subtypes.
	 *
	 * @param uriFromStorage
	 *            the uri of the primary resource
	 */
	protected ResourceSet getResourceSet(URI uriFromStorage) {
		return getResourceSetProvider().get(null);
	}

}
