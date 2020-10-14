/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.xtext.server.build;

import java.util.Objects;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.resource.SynchronizedXtextResourceSet;

/**
 * A resource set that distributes resources across several resource sets, using one resource set per project in the
 * {@link #workspaceManager workspace}.
 */
public class WorkspaceAwareResourceSet extends SynchronizedXtextResourceSet {

	/** The workspace manager that represents the workspace that is the context of this resource set. */
	protected XWorkspaceManager workspaceManager;

	/** Set the workspace manager that represents the workspace context of this resource set. */
	public void setWorkspaceManager(XWorkspaceManager workspaceManager) {
		if (this.workspaceManager != null) {
			throw new IllegalStateException("attempt to set workspace manager but it was already set");
		}
		this.workspaceManager = Objects.requireNonNull(workspaceManager);
		attachWorkspaceAwareResourceLocator();
	}

	/** Creates and attaches a new {@link WorkspaceAwareResourceLocator} to this resource set. */
	protected WorkspaceAwareResourceLocator attachWorkspaceAwareResourceLocator() {
		return new WorkspaceAwareResourceLocator(this, workspaceManager);
	}

	// no need to override #createResource(URI) and #demandCreateResource(URI)
	// (they delegate to #createResource(URI,String))

	@Override
	public Resource createResource(URI uri, String contentType) {
		ProjectBuilder projectManager = workspaceManager != null ? workspaceManager.getProjectBuilder(uri) : null;
		if (projectManager == null || projectManager.getResourceSet() == this) {
			return super.createResource(uri, contentType);
		}
		return projectManager.getResourceSet().createResource(uri, contentType);
	}
}
