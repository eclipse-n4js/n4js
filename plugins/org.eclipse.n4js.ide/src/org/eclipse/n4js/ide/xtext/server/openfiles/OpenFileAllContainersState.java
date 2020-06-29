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

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.resource.containers.IAllContainersState;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

/**
 * Provides visibility information across projects within the resource set of an open file context.
 */
public class OpenFileAllContainersState implements IAllContainersState {

	/** The open file context this container state was created for. */
	protected final OpenFileContext openFileContext;

	/** See {@link OpenFileAllContainersState}. */
	public OpenFileAllContainersState(OpenFileContext openFileContext) {
		this.openFileContext = openFileContext;
	}

	@Override
	public boolean isEmpty(String containerHandle) {
		Set<URI> uris = openFileContext.containerStructure.containerHandle2URIs.get(containerHandle);
		return uris != null ? uris.isEmpty() : true;
	}

	@Override
	public List<String> getVisibleContainerHandles(String containerHandle) {
		Set<String> visible = openFileContext.containerStructure.containerHandle2VisibleContainers.get(containerHandle);
		if (visible == null) {
			return Collections.singletonList(containerHandle);
		}
		Set<String> visibleAndSelf = new HashSet<>(visible);
		visibleAndSelf.add(containerHandle);
		return ImmutableList.copyOf(visibleAndSelf);
	}

	@Override
	public Collection<URI> getContainedURIs(String containerHandle) {
		Set<URI> uris = openFileContext.containerStructure.containerHandle2URIs.get(containerHandle);
		return uris != null ? uris : Collections.emptyList();
	}

	@Override
	public String getContainerHandle(URI uri) {
		for (Entry<String, ImmutableSet<URI>> entry : openFileContext.containerStructure.containerHandle2URIs
				.entrySet()) {
			if (entry.getValue().contains(uri)) {
				return entry.getKey();
			}
		}
		return null;
	}
}
