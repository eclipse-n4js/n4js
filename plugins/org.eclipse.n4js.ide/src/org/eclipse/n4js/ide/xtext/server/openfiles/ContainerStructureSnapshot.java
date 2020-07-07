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
package org.eclipse.n4js.ide.xtext.server.openfiles;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.ide.xtext.server.concurrent.ConcurrentChunkedIndex.VisibleContainerInfo;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.SetMultimap;

/**
 * Captures information about the containers existing in a workspace, their contained URIs and what other containers are
 * visible from each container.
 */
public class ContainerStructureSnapshot {

	/** Map from container handle to all contained URIs. */
	public final ImmutableSetMultimap<String, URI> containerHandle2URIs;
	/** Map from URI to the handle of the containing container. */
	public final ImmutableMap<URI, String> uri2ContainerHandle;
	/** Map from container handle to the handles of visible containers. */
	public final ImmutableMap<String, VisibleContainerInfo> containerHandle2VisibleContainers;

	/** See {@link ContainerStructureSnapshot}. */
	public ContainerStructureSnapshot() {
		this(ImmutableSetMultimap.of(), ImmutableMap.of(), ImmutableMap.of());
	}

	/** See {@link ContainerStructureSnapshot}. */
	public ContainerStructureSnapshot(ImmutableSetMultimap<String, URI> containerHandle2URIs,
			ImmutableMap<URI, String> uri2ContainerHandle,
			ImmutableMap<String, VisibleContainerInfo> containerHandle2VisibleContainers) {
		this.containerHandle2URIs = containerHandle2URIs;
		this.uri2ContainerHandle = uri2ContainerHandle;
		this.containerHandle2VisibleContainers = containerHandle2VisibleContainers;
	}

	/** Return a new snapshot that is based on this snapshot and updated by the given arguments. */
	public ContainerStructureSnapshot update(Map<String, ResourceDescriptionsData> changedDescriptions,
			Map<String, VisibleContainerInfo> changedVisibleContainers,
			Set<String> removedContainerHandles) {

		SetMultimap<String, URI> newContainerHandle2URIs = HashMultimap.create(containerHandle2URIs);
		Map<URI, String> newURI2ContainerHandle = new HashMap<>(uri2ContainerHandle);
		Map<String, VisibleContainerInfo> newContainerHandle2VisibleContainers = new HashMap<>(
				containerHandle2VisibleContainers);

		for (Entry<String, ResourceDescriptionsData> entry : changedDescriptions.entrySet()) {
			String containerHandle = entry.getKey();
			ResourceDescriptionsData newData = entry.getValue();
			Set<URI> newURIs = newData.getAllURIs();
			newContainerHandle2URIs.removeAll(containerHandle);
			newContainerHandle2URIs.putAll(containerHandle, newURIs);
			containerHandle2URIs.get(containerHandle).stream()
					.filter(uri -> !newURIs.contains(uri)) // no need to remove if it will be updated below
					.forEachOrdered(newURI2ContainerHandle::remove);
			newURIs.stream()
					.forEachOrdered(uri -> newURI2ContainerHandle.put(uri, containerHandle));
		}
		for (Entry<String, VisibleContainerInfo> entry : changedVisibleContainers.entrySet()) {
			String containerHandle = entry.getKey();
			VisibleContainerInfo newVisibleContainers = entry.getValue();
			newContainerHandle2VisibleContainers.put(containerHandle, newVisibleContainers);
		}
		for (String removedContainerHandle : removedContainerHandles) {
			newContainerHandle2URIs.removeAll(removedContainerHandle);
			containerHandle2URIs.get(removedContainerHandle).stream()
					.forEachOrdered(newURI2ContainerHandle::remove);
			newContainerHandle2VisibleContainers.remove(removedContainerHandle);
		}

		return new ContainerStructureSnapshot(
				ImmutableSetMultimap.copyOf(newContainerHandle2URIs),
				ImmutableMap.copyOf(newURI2ContainerHandle),
				ImmutableMap.copyOf(newContainerHandle2VisibleContainers));
	}

	@Override
	public int hashCode() {
		return Objects.hash(containerHandle2URIs, containerHandle2VisibleContainers);
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if (!(other instanceof ContainerStructureSnapshot)) {
			return false;
		}
		ContainerStructureSnapshot otherCasted = (ContainerStructureSnapshot) other;
		return this.containerHandle2URIs.equals(otherCasted.containerHandle2URIs)
				&& this.containerHandle2VisibleContainers.equals(otherCasted.containerHandle2VisibleContainers);
	}
}
