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
package org.eclipse.n4js.ide.xtext.server.index;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.resource.IContainer;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.containers.IContainerState;
import org.eclipse.xtext.resource.containers.LiveShadowedChunkedContainer;
import org.eclipse.xtext.resource.containers.ProjectDescriptionBasedContainerManager;
import org.eclipse.xtext.resource.containers.StateBasedContainer;
import org.eclipse.xtext.resource.impl.ChunkedResourceDescriptions;
import org.eclipse.xtext.resource.impl.LiveShadowedChunkedResourceDescriptions;
import org.eclipse.xtext.resource.impl.ProjectDescription;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;

import com.google.common.collect.ImmutableSet;

/**
 * TODO this entire thing is apparently garbage. We lack caching and compute the same information over and over again.
 *
 * Given that the containers are based on the projects, the entire graph needs to be computed only once and on change.
 */
public class ExtendedProjectDescriptionBasedContainerManager extends ProjectDescriptionBasedContainerManager {

	@Override
	protected ChunkedResourceDescriptions getChunkedResourceDescriptions(IResourceDescriptions resourceDescriptions) {
		ChunkedResourceDescriptions chunkedResourceDescriptions = super.getChunkedResourceDescriptions(
				resourceDescriptions);
		if (chunkedResourceDescriptions != null) {
			return chunkedResourceDescriptions;
		}
		if (resourceDescriptions instanceof AbstractShadowedResourceDescriptions) {
			return getChunkedResourceDescriptions(
					((AbstractShadowedResourceDescriptions) resourceDescriptions).getParentData());
		}
		if (resourceDescriptions instanceof ForwardingResourceDescriptions) {
			return getChunkedResourceDescriptions(
					((ForwardingResourceDescriptions) resourceDescriptions).getDelegate());
		}
		return null;
	}

	@Override
	public IContainer getContainer(IResourceDescription desc, IResourceDescriptions resourceDescriptions) {
		IResourceDescriptions delegate = resourceDescriptions;
		if (resourceDescriptions instanceof LiveShadowedChunkedResourceDescriptions) {
			delegate = ((LiveShadowedChunkedResourceDescriptions) resourceDescriptions).getGlobalDescriptions();
		}
		ChunkedResourceDescriptions chunks = getChunkedResourceDescriptions(delegate);
		ForwardingResourceDescriptions casted = (ForwardingResourceDescriptions) delegate;
		ResourceSet resourceSet = casted.getResourceSet();
		ProjectDescription projectDescription = ProjectDescription.findInEmfObject(resourceSet);
		return createContainer(resourceDescriptions, chunks, projectDescription.getName());
	}

	@Override
	public List<IContainer> getVisibleContainers(IResourceDescription desc,
			IResourceDescriptions resourceDescriptions) {
		IResourceDescriptions delegate = resourceDescriptions;
		if (resourceDescriptions instanceof LiveShadowedChunkedResourceDescriptions) {
			delegate = ((LiveShadowedChunkedResourceDescriptions) resourceDescriptions).getGlobalDescriptions();
		}
		// TODO this looks totally bonkers given that the project description is mutable and not updated afaik
		ChunkedResourceDescriptions chunks = getChunkedResourceDescriptions(delegate);
		ForwardingResourceDescriptions casted = (ForwardingResourceDescriptions) delegate;
		ResourceSet resourceSet = casted.getResourceSet();
		ProjectDescription projectDescription = ProjectDescription.findInEmfObject(resourceSet);
		List<IContainer> result = new ArrayList<>();
		result.add(createContainer(resourceDescriptions, chunks, projectDescription.getName()));
		for (String dependency : projectDescription.getDependencies()) {
			result.add(createContainer(resourceDescriptions, chunks, dependency));
		}
		return result;
	}

	@Override
	protected IContainer createContainer(IResourceDescriptions resourceDescriptions,
			ChunkedResourceDescriptions chunkedResourceDescriptions, String projectName) {
		if (resourceDescriptions instanceof LiveShadowedChunkedResourceDescriptions) {
			return new LiveShadowedChunkedContainer((LiveShadowedChunkedResourceDescriptions) resourceDescriptions,
					projectName) {
				@Override
				protected ChunkedResourceDescriptions getChunkedResourceDescriptions() {
					return chunkedResourceDescriptions;
				}
			};
		}
		ResourceDescriptionsData container = chunkedResourceDescriptions.getContainer(projectName);
		Set<URI> containerURIs;
		if (container != null) {
			containerURIs = ImmutableSet.copyOf(container.getAllURIs());
		} else {
			containerURIs = ImmutableSet.of();
		}

		return new StateBasedContainer(resourceDescriptions, new IContainerState() {

			@Override
			public boolean isEmpty() {
				return containerURIs.isEmpty();
			}

			@Override
			public Collection<URI> getContents() {
				return containerURIs;
			}

			@Override
			public boolean contains(URI uri) {
				return containerURIs.contains(uri);
			}
		});
	}

	@Override
	public boolean shouldUseProjectDescriptionBasedContainers(IResourceDescriptions resourceDescriptions) {
		if (resourceDescriptions instanceof LiveShadowedChunkedResourceDescriptions) {
			resourceDescriptions = ((LiveShadowedChunkedResourceDescriptions) resourceDescriptions)
					.getGlobalDescriptions();
		}
		if (resourceDescriptions instanceof ForwardingResourceDescriptions) {
			ForwardingResourceDescriptions casted = (ForwardingResourceDescriptions) resourceDescriptions;
			ProjectDescription projectDescription = ProjectDescription.findInEmfObject(casted.getResourceSet());
			return projectDescription != null;
		}
		return false;
	}

}
