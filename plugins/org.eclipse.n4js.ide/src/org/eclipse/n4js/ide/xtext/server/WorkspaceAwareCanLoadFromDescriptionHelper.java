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
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.scoping.utils.CanLoadFromDescriptionHelper;
import org.eclipse.xtext.resource.XtextResourceSet;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * A specialization that will create the resources in the correct project specific resource set.
 */
@Singleton
public class WorkspaceAwareCanLoadFromDescriptionHelper extends CanLoadFromDescriptionHelper {

	@Inject
	private XWorkspaceManager workspaceManager;

	@Inject
	private XOpenFileManager openFileManager;

	@Override
	public Resource createResource(ResourceSet resourceSet, URI resourceURI) {
		ResourceSet openFileResourceSet = openFileManager.getOrCreateResourceSetForURI(resourceSet, resourceURI);
		if (openFileResourceSet != null) {
			// for resource sets used for open files we use the default behavior:
			return super.createResource(resourceSet, resourceURI);
		}
		XtextResourceSet projectResourceSet = workspaceManager.getProjectManager(resourceURI).getResourceSet();
		return super.createResource(projectResourceSet, resourceURI);
	}

}
