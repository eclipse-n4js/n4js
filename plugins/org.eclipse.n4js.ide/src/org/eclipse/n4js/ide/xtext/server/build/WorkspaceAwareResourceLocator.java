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
package org.eclipse.n4js.ide.xtext.server.build;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl.ResourceLocator;
import org.eclipse.n4js.utils.EcoreUtilN4.IResourceLocatorWithCreateSupport;

/**
 * A resource locator that will redirect requests to the resource set of the containing project.
 */
public class WorkspaceAwareResourceLocator extends ResourceLocator implements IResourceLocatorWithCreateSupport {

	private final XWorkspaceManager workspaceManager;

	/**
	 * Standard constructor
	 */
	public WorkspaceAwareResourceLocator(ResourceSetImpl resourceSet, XWorkspaceManager workspaceManager) {
		super(resourceSet);
		this.workspaceManager = workspaceManager;
	}

	/** Returns the resource set a resource with the given URI would belong to. */
	protected ResourceSet getTargetResourceSet(URI uri) {
		ProjectBuilder projectBuilder = this.workspaceManager.getProjectBuilder(uri);
		return projectBuilder != null ? projectBuilder.getResourceSet() : null;
	}

	@Override
	public Resource getResource(URI uri, boolean loadOnDemand) {
		Resource candidate = resourceSet.getURIResourceMap().get(uri);
		if (candidate != null) {
			if (!loadOnDemand || candidate.isLoaded()) {
				return candidate;
			}
		}
		ResourceSet target = getTargetResourceSet(uri);
		if (target == null || target == resourceSet) {
			return basicGetResource(uri, loadOnDemand);
		}
		return target.getResource(uri, loadOnDemand);
	}

	@Override
	public Resource createResource(URI uri) {
		ResourceSet target = getTargetResourceSet(uri);
		if (target == null || target == resourceSet) {
			return demandCreateResource(uri);
		}
		return target.createResource(uri);
	}
}
