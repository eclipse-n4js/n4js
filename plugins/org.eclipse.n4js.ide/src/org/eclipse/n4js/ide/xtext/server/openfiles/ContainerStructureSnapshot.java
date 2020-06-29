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

import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;

/**
 * Captures information about the containers existing in a workspace, their contained URIs and what other containers are
 * visible from each container.
 */
public class ContainerStructureSnapshot {

	/** Map from container handle to all contained URIs. */
	public final ImmutableMap<String, ImmutableSet<URI>> containerHandle2URIs;
	/** Map from container handle to the handles of visible containers. */
	public final ImmutableMap<String, ImmutableSet<String>> containerHandle2VisibleContainers;

	/** See {@link ContainerStructureSnapshot}. */
	public ContainerStructureSnapshot() {
		this(ImmutableMap.of(), ImmutableMap.of());
	}

	/** See {@link ContainerStructureSnapshot}. */
	public ContainerStructureSnapshot(ImmutableMap<String, ImmutableSet<URI>> containerHandle2URIs,
			ImmutableMap<String, ImmutableSet<String>> containerHandle2VisibleContainers) {
		this.containerHandle2URIs = containerHandle2URIs;
		this.containerHandle2VisibleContainers = containerHandle2VisibleContainers;
	}

	/** See {@link ContainerStructureSnapshot}. */
	public static ContainerStructureSnapshot create(Map<String, ResourceDescriptionsData> descriptions,
			Map<String, ImmutableSet<String>> visibleContainers) {

		ImmutableMap.Builder<String, ImmutableSet<URI>> containerHandle2URIs = ImmutableMap.builder();
		for (Entry<String, ResourceDescriptionsData> entry : descriptions.entrySet()) {
			Iterable<URI> uris = Iterables.transform(entry.getValue().getAllResourceDescriptions(),
					IResourceDescription::getURI);
			containerHandle2URIs.put(entry.getKey(), ImmutableSet.copyOf(uris));
		}

		return new ContainerStructureSnapshot(containerHandle2URIs.build(), ImmutableMap.copyOf(visibleContainers));
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
