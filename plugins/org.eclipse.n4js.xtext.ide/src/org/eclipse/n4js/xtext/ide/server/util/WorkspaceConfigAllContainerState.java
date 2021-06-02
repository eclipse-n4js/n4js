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
package org.eclipse.n4js.xtext.ide.server.util;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.xtext.workspace.ProjectConfigSnapshot;
import org.eclipse.n4js.xtext.workspace.WorkspaceConfigAdapter;
import org.eclipse.n4js.xtext.workspace.WorkspaceConfigSnapshot;
import org.eclipse.xtext.resource.containers.IAllContainersState;
import org.eclipse.xtext.resource.impl.ChunkedResourceDescriptions;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;

import com.google.common.collect.ImmutableList;

/**
 * Provides the services of an {@link IAllContainersState} by obtaining the necessary information from a
 * {@link WorkspaceConfigSnapshot} and {@link ChunkedResourceDescriptions} attached to a given {@link #resourceSet
 * context resource set}.
 */
public class WorkspaceConfigAllContainerState implements IAllContainersState {

	/** The context resource set. */
	protected final ResourceSet resourceSet;

	/** See {@link WorkspaceConfigAllContainerState}. */
	public WorkspaceConfigAllContainerState(ResourceSet resourceSet) {
		this.resourceSet = Objects.requireNonNull(resourceSet);
	}

	/** Obtains the current {@link WorkspaceConfigSnapshot} from the {@link #resourceSet context resource set}. */
	protected WorkspaceConfigSnapshot getWorkspaceConfig() {
		// note: the attached instance may change over time, so we have to search it upon every invocation:
		return WorkspaceConfigAdapter.getWorkspaceConfig(resourceSet);
	}

	/** Obtains the current {@link ChunkedResourceDescriptions} from the {@link #resourceSet context resource set}. */
	protected ChunkedResourceDescriptions getChunkedResourceDescriptions() {
		// note: the attached instance may change over time, so we have to search it upon every invocation:
		return ChunkedResourceDescriptions.findInEmfObject(resourceSet);
	}

	@Override
	public boolean isEmpty(String containerHandle) {
		ChunkedResourceDescriptions rd = getChunkedResourceDescriptions();
		ResourceDescriptionsData data = rd != null ? rd.getContainer(containerHandle) : null;
		return data != null ? data.isEmpty() : true;
	}

	@Override
	public List<String> getVisibleContainerHandles(String containerHandle) {
		WorkspaceConfigSnapshot ws = getWorkspaceConfig();
		ProjectConfigSnapshot project = ws != null ? ws.findProjectByName(containerHandle) : null;
		if (project == null) {
			return Collections.singletonList(containerHandle);
		}
		Set<String> visible = project.getDependencies();
		Set<String> visibleAndSelf = new LinkedHashSet<>();
		visibleAndSelf.add(containerHandle);
		visibleAndSelf.addAll(visible);
		return ImmutableList.copyOf(visibleAndSelf);
	}

	@Override
	public Collection<URI> getContainedURIs(String containerHandle) {
		ChunkedResourceDescriptions rd = getChunkedResourceDescriptions();
		ResourceDescriptionsData data = rd != null ? rd.getContainer(containerHandle) : null;
		return data != null ? data.getAllURIs() : Collections.emptyList();
	}

	@Override
	public String getContainerHandle(URI uri) {
		WorkspaceConfigSnapshot ws = getWorkspaceConfig();
		ProjectConfigSnapshot project = ws != null ? ws.findProjectContaining(uri) : null;
		return project != null ? project.getName() : null;
	}
}
