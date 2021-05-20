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

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.xtext.workspace.ProjectConfigSnapshot;
import org.eclipse.xtext.resource.containers.IAllContainersState;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;

import com.google.common.collect.ImmutableList;

/**
 * Provides visibility information across projects within the resource set of a resource task context.
 */
// FIXME check if we can implement this without the need for a ResourceTaskContext
// (by obtaining the workspace config and index snapshot from the adapter list of the resource set)
// ==> then move installation of instances of this class from ResourceTaskContext to ResourceTaskManager!
public class ResourceTaskContextAllContainerState implements IAllContainersState {

	/** The resource task context this container state was created for. */
	protected final ResourceTaskContext resourceTaskContext;

	/** See {@link ResourceTaskContextAllContainerState}. */
	public ResourceTaskContextAllContainerState(ResourceTaskContext resourceTaskContext) {
		this.resourceTaskContext = resourceTaskContext;
	}

	@Override
	public boolean isEmpty(String containerHandle) {
		ResourceDescriptionsData data = resourceTaskContext.indexSnapshot.getContainer(containerHandle);
		return data != null ? data.isEmpty() : true;
	}

	@Override
	public List<String> getVisibleContainerHandles(String projectName) {
		ProjectConfigSnapshot project = resourceTaskContext.workspaceConfig.findProjectByName(projectName);
		if (project == null) {
			return Collections.singletonList(projectName);
		}
		Set<String> visible = project.getDependencies();
		Set<String> visibleAndSelf = new LinkedHashSet<>();
		visibleAndSelf.add(projectName);
		visibleAndSelf.addAll(visible);
		return ImmutableList.copyOf(visibleAndSelf);
	}

	@Override
	public Collection<URI> getContainedURIs(String containerHandle) {
		ResourceDescriptionsData data = resourceTaskContext.indexSnapshot.getContainer(containerHandle);
		return data != null ? data.getAllURIs() : Collections.emptyList();
	}

	@Override
	public String getContainerHandle(URI uri) {
		ProjectConfigSnapshot project = resourceTaskContext.workspaceConfig.findProjectContaining(uri);
		return project != null ? project.getName() : null;
	}
}
