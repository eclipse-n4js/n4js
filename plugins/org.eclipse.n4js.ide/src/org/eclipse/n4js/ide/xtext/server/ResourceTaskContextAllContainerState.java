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
package org.eclipse.n4js.ide.xtext.server;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.xtext.workspace.IProjectConfigSnapshot;
import org.eclipse.xtext.resource.containers.IAllContainersState;

import com.google.common.collect.ImmutableList;

/**
 * Provides visibility information across projects within the resource set of an resource task context.
 */
public class ResourceTaskContextAllContainerState implements IAllContainersState {

	/** The open file context this container state was created for. */
	protected final ResourceTaskContext resourceTaskContext;

	/** See {@link ResourceTaskContextAllContainerState}. */
	public ResourceTaskContextAllContainerState(ResourceTaskContext resourceTaskContext) {
		this.resourceTaskContext = resourceTaskContext;
	}

	@Override
	public boolean isEmpty(String containerHandle) {
		return resourceTaskContext.containerHandle2URIs.get(containerHandle).isEmpty();
	}

	@Override
	public List<String> getVisibleContainerHandles(String containerHandle) {
		IProjectConfigSnapshot project = resourceTaskContext.workspaceConfig.findProjectByName(containerHandle);
		if (project == null) {
			return Collections.singletonList(containerHandle);
		}
		Set<String> visible = project.getDependencies();
		Set<String> visibleAndSelf = new HashSet<>(visible);
		visibleAndSelf.add(containerHandle);
		return ImmutableList.copyOf(visibleAndSelf);
	}

	@Override
	public Collection<URI> getContainedURIs(String containerHandle) {
		return resourceTaskContext.containerHandle2URIs.get(containerHandle);
	}

	@Override
	public String getContainerHandle(URI uri) {
		IProjectConfigSnapshot project = resourceTaskContext.workspaceConfig.findProjectContaining(uri);
		return project != null ? project.getName() : null;
	}
}
