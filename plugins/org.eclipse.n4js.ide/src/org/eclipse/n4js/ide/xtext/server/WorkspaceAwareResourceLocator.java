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

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl.ResourceLocator;

/**
 * A resource locator that will redirect requests to the resource set of the containing project.
 */
public class WorkspaceAwareResourceLocator extends ResourceLocator {

	private static final Logger LOG = Logger.getLogger(WorkspaceAwareResourceLocator.class);

	private final XWorkspaceManager workspaceManager;

	/**
	 * Standard constructor
	 */
	public WorkspaceAwareResourceLocator(ResourceSetImpl resourceSet, XWorkspaceManager workspaceManager) {
		super(resourceSet);
		this.workspaceManager = workspaceManager;
	}

	@Override
	public Resource getResource(URI uri, boolean loadOnDemand) {
		Resource candidate = resourceSet.getURIResourceMap().get(uri);
		if (candidate != null) {
			// TODO check if candidate is not loaded but we want to load on demand
			if (loadOnDemand && !candidate.isLoaded()) {
				// demandLoadHelper(candidate);
				LOG.warn("Returning a resource that is not loaded even though loadOnDemand was set to true: "
						+ candidate.getURI());
			}
			return candidate;
		}
		XProjectManager projectManager = this.workspaceManager.getProjectManager(uri);
		if (projectManager == null || projectManager.getResourceSet() == resourceSet) {
			return basicGetResource(uri, loadOnDemand);
		}
		return projectManager.getResourceSet().getResource(uri, loadOnDemand);
	}

}
