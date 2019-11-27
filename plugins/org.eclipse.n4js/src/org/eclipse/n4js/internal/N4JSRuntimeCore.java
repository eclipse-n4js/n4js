/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.internal;

import static com.google.common.collect.Sets.newHashSet;
import static java.lang.Boolean.TRUE;
import static org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider.PERSISTED_DESCRIPTIONS;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.IN4JSRuntimeCore;
import org.eclipse.n4js.projectModel.IN4JSSourceContainer;
import org.eclipse.n4js.projectModel.locations.FileURI;
import org.eclipse.n4js.projectModel.locations.SafeURI;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;
import org.eclipse.n4js.resource.OrderedResourceDescriptionsData;
import org.eclipse.n4js.ts.scoping.builtin.N4Scheme;
import org.eclipse.n4js.utils.ResourceType;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

/**
 */
@Singleton
public class N4JSRuntimeCore extends AbstractN4JSCore implements IN4JSRuntimeCore {

	private final FileBasedWorkspace workspace;

	private final N4JSModel<? extends SafeURI<?>> model;

	@Inject
	private Provider<XtextResourceSet> resourceSetProvider;

	@Inject
	private ResourceDescriptionsProvider resourceDescriptionsProvider;

	@Inject
	private IResourceServiceProvider.Registry resourceServiceProviderRegistry;

	/**
	 * Public for testing purpose.
	 */
	@Inject
	public N4JSRuntimeCore(FileBasedWorkspace workspace, N4JSModel<? extends SafeURI<?>> model) {
		this.workspace = workspace;
		this.model = model;
	}

	@Override
	public IN4JSProject create(URI location) {
		if (location == null) {
			return null;
		}
		return model.getN4JSProject(location);
	}

	@Override
	public SafeURI<?> toProjectLocation(URI uri) {
		return model.toProjectLocation(uri);
	}

	@Override
	public Optional<? extends IN4JSProject> findProject(URI nestedLocation) {
		if (nestedLocation == null || N4Scheme.isN4Scheme(nestedLocation)) {
			return Optional.absent();
		}
		IN4JSProject result = model.findProjectWith(nestedLocation);
		return Optional.fromNullable(result);
	}

	@Override
	public Set<? extends IN4JSProject> findAllProjects() {
		return model.getAllProjects();
	}

	@Override
	public Optional<? extends IN4JSProject> findProject(N4JSProjectName name) {
		return Optional.fromNullable(model.findProject(name));
	}

	@Override
	public Map<N4JSProjectName, IN4JSProject> findAllProjectMappings() {
		Map<N4JSProjectName, IN4JSProject> allProjectMappings = new HashMap<>();
		for (IN4JSProject project : findAllProjects()) {
			allProjectMappings.put(project.getProjectName(), project);
		}
		return allProjectMappings;
	}

	@Override
	public Optional<? extends IN4JSSourceContainer> findN4JSSourceContainer(URI nestedLocation) {
		if (nestedLocation == null || (nestedLocation.isFile() && nestedLocation.isRelative())) {
			return Optional.absent();
		} else {
			return model.findN4JSSourceContainer(nestedLocation);
		}
	}

	@Override
	public void registerProject(File file) {
		if (file.isDirectory()) {
			URI uri = new FileURI(file).toURI();
			workspace.registerProject((FileURI) toProjectLocation(uri));
		} else {
			throw new IllegalArgumentException(file.getAbsolutePath() + " is not a valid project location");
		}
	}

	@Override
	public ResourceSet createResourceSet(Optional<IN4JSProject> contextProject) {
		final ResourceSet resourceSet = resourceSetProvider.get();
		resourceSet.getLoadOptions().put(PERSISTED_DESCRIPTIONS, TRUE);
		createAllResourcesWorkspace(resourceSet);
		attachResourceDescriptionsData(resourceSet);
		return resourceSet;
	}

	@Override
	public IResourceDescriptions getXtextIndex(ResourceSet resourceSet) {
		return resourceDescriptionsProvider.getResourceDescriptions(resourceSet);
	}

	private void createAllResourcesWorkspace(ResourceSet resourceSet) {
		final Set<URI> uris = newHashSet();
		for (IN4JSProject project : findAllProjects()) {
			project.getSourceContainers().forEach(sc -> {
				for (URI sourceFile : sc) {
					if (isN4File(sourceFile) && uris.add(sourceFile)) {
						resourceSet.createResource(sourceFile);
					}
				}
			});
		}
	}

	private void attachResourceDescriptionsData(ResourceSet resourceSet) {
		installIndex(resourceSet);
	}

	private void installIndex(ResourceSet resourceSet) {
		// Fill index
		ResourceDescriptionsData index = new OrderedResourceDescriptionsData(
				Collections.<IResourceDescription> emptyList());
		List<Resource> resources = Lists.newArrayList(resourceSet.getResources());
		for (Resource resource : resources) {
			index(resource, resource.getURI(), index);
		}
		Adapter existing = EcoreUtil.getAdapter(resourceSet.eAdapters(), ResourceDescriptionsData.class);
		if (existing != null) {
			resourceSet.eAdapters().remove(existing);
		}
		ResourceDescriptionsData.ResourceSetAdapter.installResourceDescriptionsData(resourceSet, index);
	}

	/**
	 * Installing the ResourceDescription of a resource into the index. Raw JS-files will not be indexed.
	 */
	private void index(Resource resource, URI uri, ResourceDescriptionsData index) {

		if (isJsFile(uri)) {
			IN4JSSourceContainer sourceContainer = findN4JSSourceContainer(uri).orNull();
			if (null == sourceContainer) {
				return; // We do not want to index resources that are not in source containers.
			}
		}

		IResourceServiceProvider serviceProvider = resourceServiceProviderRegistry.getResourceServiceProvider(uri);
		if (serviceProvider != null) {
			IResourceDescription resourceDescription = serviceProvider.getResourceDescriptionManager()
					.getResourceDescription(resource);
			if (resourceDescription != null) {
				index.addDescription(uri, resourceDescription);
			}
		}
	}

	/** Check for raw JS or JSX files. This includes xpect based files. */
	protected boolean isJsFile(URI uri) {
		ResourceType resourceType = ResourceType.getResourceType(uri);
		switch (resourceType) {
		case JS:
			return true;
		case JSX:
			return true;
		default:
			return false;
		}
	}

	/** Check for non-JS, non-N4MF known files. This does NOT include xpect based files. */
	private boolean isN4File(final URI uri) {
		ResourceType resourceType = ResourceType.getResourceType(uri);
		switch (resourceType) {
		case UNKOWN:
			return false;
		default:
			return !ResourceType.xtHidesOtherExtension(uri);
		}
	}

}
