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
package org.eclipse.n4js.xtext.ide.server;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.xtext.ide.server.util.WorkspaceConfigAllContainerState;
import org.eclipse.n4js.xtext.workspace.WorkspaceConfigSnapshot;
import org.eclipse.xtext.resource.impl.ChunkedResourceDescriptions;

/**
 * Same as {@link WorkspaceConfigAllContainerState}, but provides a minor optimization by obtaining the workspace
 * configuration and resource descriptions directly from the resource task context instead of searching through all
 * adapters of the context resource set.
 */
public class ResourceTaskContextAllContainerState extends WorkspaceConfigAllContainerState {

	/** The resource task context this container state was created for. */
	protected final ResourceTaskContext resourceTaskContext;

	/** See {@link ResourceTaskContextAllContainerState}. */
	public ResourceTaskContextAllContainerState(ResourceSet resourceSet, ResourceTaskContext resourceTaskContext) {
		super(resourceSet);
		this.resourceTaskContext = resourceTaskContext;
	}

	@Override
	protected WorkspaceConfigSnapshot getWorkspaceConfig() {
		return resourceTaskContext.workspaceConfig;
	}

	@Override
	protected ChunkedResourceDescriptions getChunkedResourceDescriptions() {
		return resourceTaskContext.indexSnapshot;
	}
}
