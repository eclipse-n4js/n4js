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

import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl.ResourceLocator;

/**
 * A resource locator that will redirect requests to the resource set of the containing project.
 */
public class WorkspaceAwareResourceLocator extends ResourceLocator {

	private final XProjectManager projectManager;
	private final Map<URI, Resource> allResources;

	/**
	 * Standard constructor
	 */
	public WorkspaceAwareResourceLocator(ResourceSetImpl resourceSet, XProjectManager projectManager) {
		super(resourceSet);
		this.projectManager = projectManager;
		this.allResources = resourceSet.getURIResourceMap();
	}

	@SuppressWarnings("hiding")
	@Override
	public Resource getResource(URI uri, boolean loadOnDemand) {
		Resource candidate = allResources.get(uri);
		if (candidate != null) {
			return candidate;
		}
		XProjectManager projectManager = this.projectManager.workspaceManager.getProjectManager(uri);
		if (projectManager == this.projectManager || projectManager == null) {
			return basicGetResource(uri, loadOnDemand);
		}
		return projectManager.getResourceSet().getResource(uri, loadOnDemand);
	}

}
