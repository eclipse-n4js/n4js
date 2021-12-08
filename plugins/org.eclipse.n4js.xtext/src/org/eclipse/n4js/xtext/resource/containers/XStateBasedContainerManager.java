/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.xtext.resource.containers;

import java.util.Collections;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.resource.IContainer;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.containers.IAllContainersState;
import org.eclipse.xtext.resource.containers.ProjectDescriptionBasedContainerManager;
import org.eclipse.xtext.resource.containers.StateBasedContainer;
import org.eclipse.xtext.resource.containers.StateBasedContainerManager;
import org.eclipse.xtext.resource.impl.ChunkedResourceDescriptions;
import org.eclipse.xtext.resource.impl.ProjectDescription;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsBasedContainer;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;

/**
 * Minor adjustment of {@link StateBasedContainerManager}.
 */
public class XStateBasedContainerManager extends StateBasedContainerManager {

	/**
	 * Overridden to take advantage of {@link ChunkedResourceDescriptions} (if available), because otherwise the super
	 * class would create a {@link StateBasedContainer} that iterates over <em>all</em> exported elements in the entire
	 * workspace and filters them by the given container handle (see {@link StateBasedContainer#filterByURI(Iterable)}),
	 * which is a performance problem for content assist in large workspaces.
	 * <p>
	 * This happens, for example, when a {@link ResourceSet} is configured with {@link ChunkedResourceDescriptions} and
	 * an instance of {@link IAllContainersState} (instead of an instance of {@link ProjectDescription}).
	 */
	@Override
	protected IContainer createContainer(String handle, IResourceDescriptions resourceDescriptions) {
		if (resourceDescriptions instanceof ChunkedResourceDescriptions) {
			return createChunkedContainer(handle, (ChunkedResourceDescriptions) resourceDescriptions);
		}
		return super.createContainer(handle, resourceDescriptions);
	}

	/**
	 * This is modeled after
	 * {@link ProjectDescriptionBasedContainerManager#createContainer(IResourceDescriptions, ChunkedResourceDescriptions, String)}.
	 */
	protected IContainer createChunkedContainer(String handle, ChunkedResourceDescriptions resourceDescriptions) {
		ResourceDescriptionsData data = resourceDescriptions.getContainer(handle);
		return new ResourceDescriptionsBasedContainer(
				data != null ? data : new ResourceDescriptionsData(Collections.emptySet()));
	}
}
