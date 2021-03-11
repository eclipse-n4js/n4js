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
package org.eclipse.n4js.projectModel.internal;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.internal.lsp.N4JSProjectConfigSnapshot;
import org.eclipse.n4js.internal.lsp.N4JSSourceFolderSnapshot;
import org.eclipse.n4js.internal.lsp.N4JSWorkspaceConfigSnapshot;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.projectModel.IN4JSCoreNEW;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.xtext.server.ResourceTaskManager;
import org.eclipse.n4js.xtext.server.build.ConcurrentIndex;
import org.eclipse.n4js.xtext.workspace.WorkspaceConfigAccess;
import org.eclipse.n4js.xtext.workspace.WorkspaceConfigSnapshot;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.IResourceDescriptionsProvider;
import org.eclipse.xtext.resource.ISynchronizable;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import com.google.inject.Inject;

public class N4JSCoreNEW implements IN4JSCoreNEW {

	@Inject
	private ResourceTaskManager resourceTaskManager;

	@Inject
	private IResourceDescriptionsProvider resourceDescriptionsProvider;

	@Override
	public Optional<N4JSWorkspaceConfigSnapshot> getWorkspaceConfig(Notifier context) {
		ResourceSet resourceSet = EcoreUtil2.getResourceSet(context);
		WorkspaceConfigSnapshot config = resourceSet != null
				? WorkspaceConfigAccess.getWorkspaceConfig(resourceSet)
				: null;
		return Optional.fromNullable((N4JSWorkspaceConfigSnapshot) config);
	}

	@Override
	public ImmutableSet<N4JSProjectConfigSnapshot> findAllProjects(Notifier context) {
		Optional<N4JSWorkspaceConfigSnapshot> config = getWorkspaceConfig(context);
		return config.isPresent()
				? config.get().getProjects()
				: ImmutableSet.of();
	}

	@Override
	public Optional<N4JSProjectConfigSnapshot> findProject(Resource resource) {
		return resource != null ? findProject(resource, resource.getURI()) : Optional.absent();
	}

	@Override
	public Optional<N4JSProjectConfigSnapshot> findProject(Notifier context, URI nestedLocation) {
		Optional<N4JSWorkspaceConfigSnapshot> config = getWorkspaceConfig(context);
		return config.isPresent()
				? Optional.fromNullable(config.get().findProjectByNestedLocation(nestedLocation))
				: Optional.absent();
	}

	@Override
	public Optional<N4JSProjectConfigSnapshot> findProject(Notifier context, N4JSProjectName projectName) {
		Optional<N4JSWorkspaceConfigSnapshot> config = getWorkspaceConfig(context);
		return config.isPresent()
				? Optional.fromNullable(config.get().findProjectByName(projectName.getRawName()))
				: Optional.absent();
	}

	@Override
	public Optional<N4JSProjectConfigSnapshot> findProjectContaining(Notifier context, URI nestedLocation) {
		Optional<N4JSWorkspaceConfigSnapshot> config = getWorkspaceConfig(context);
		return config.isPresent()
				? Optional.fromNullable(config.get().findProjectContaining(nestedLocation))
				: Optional.absent();
	}

	@Override
	public Optional<N4JSSourceFolderSnapshot> findN4JSSourceContainer(Notifier context, URI nestedLocation) {
		Optional<N4JSWorkspaceConfigSnapshot> config = getWorkspaceConfig(context);
		return config.isPresent()
				? Optional.fromNullable(config.get().findSourceFolderContaining(nestedLocation))
				: Optional.absent();
	}

	@Override
	public boolean isNoValidate(Notifier context, URI nestedLocation) {
		Optional<N4JSWorkspaceConfigSnapshot> config = getWorkspaceConfig(context);
		return config.isPresent() && config.get().isNoValidate(nestedLocation);
	}

	// FIXME GH-2073 important! reconsider all following methods!

	@Override
	public ResourceSet createResourceSet() {
		return resourceTaskManager.createTemporaryResourceSet();
	}

	@Override
	public Optional<IResourceDescriptions> getXtextIndex(Notifier context) {
		ResourceSet resourceSet = EcoreUtil2.getResourceSet(context);
		if (resourceSet == null) {
			return Optional.absent();
		}
		IResourceDescriptions result = resourceDescriptionsProvider.getResourceDescriptions(resourceSet);
		if (result == null) {
			// FIXME this should not be necessary! maybe implement and bind an IResourceDescriptionsProvider doing that
			result = ResourceDescriptionsData.ResourceSetAdapter.findResourceDescriptionsData(resourceSet);
		}
		return Optional.fromNullable(result);
	}

	@Override
	public TModule loadModuleFromIndex(final ResourceSet resourceSet,
			final IResourceDescription resourceDescription, boolean allowFullLoad) {
		final URI resourceURI = resourceDescription.getURI();
		Resource resource = resourceSet.getResource(resourceURI, false);
		TModule result = loadModuleFromResource(resource);
		if (result != null) {
			return result;
		}
		if (resource == null) {
			if (resourceSet instanceof ISynchronizable<?>) {
				synchronized (((ISynchronizable<?>) resourceSet).getLock()) {
					resource = resourceSet.getResource(resourceURI, false);
					result = loadModuleFromResource(resource);
					if (result != null) {
						return result;
					}
					if (resource == null) {
						resource = resourceSet.createResource(resourceURI);
					}
				}
			} else {
				resource = resourceSet.createResource(resourceURI);
			}
		}
		if (resource instanceof N4JSResource) {
			if (resource.getContents().isEmpty()) {
				final N4JSResource casted = (N4JSResource) resource;
				try {
					if (casted.loadFromDescription(resourceDescription)) {
						casted.performPostProcessing();
						return casted.getModule();
					} else if (allowFullLoad) {
						casted.unload();
						casted.load(resourceSet.getLoadOptions());
						casted.installDerivedState(false);
						return casted.getModule();
					}
				} catch (final Exception e) {
					casted.unload();
					return null;
				}
			}
		}
		return null;
	}

	private TModule loadModuleFromResource(Resource resource) {
		if (resource instanceof N4JSResource) {
			final N4JSResource resourceCasted = (N4JSResource) resource;
			final Script existingScript = resourceCasted.getScript();
			final TModule existingModule = resourceCasted.getModule();
			if (existingModule != null) {
				// resource exists already and it already has a TModule
				// -> simply return that
				return existingModule;
			} else if (existingScript != null && !existingScript.eIsProxy()) {
				// resource exists already and it already has its AST loaded (though no TModule yet)
				// -> we have to create the TModule from that AST instead of loading it from index
				resourceCasted.installDerivedState(false); // trigger installation of derived state (i.e. types builder)
				return resourceCasted.getModule();
			}
		}
		return null;
	}

	// FIXME GH-2073 important! get rid of the following!

	@Inject
	private ConcurrentIndex concurrentIndex;

	@Override
	public ImmutableSet<N4JSProjectConfigSnapshot> findAllProjects() {
		N4JSWorkspaceConfigSnapshot wcs = (N4JSWorkspaceConfigSnapshot) concurrentIndex.getWorkspaceConfigSnapshot();
		return wcs.getProjects();
	}

	@Override
	public Optional<N4JSProjectConfigSnapshot> findProject(URI nestedLocation) {
		N4JSWorkspaceConfigSnapshot wcs = (N4JSWorkspaceConfigSnapshot) concurrentIndex.getWorkspaceConfigSnapshot();
		return Optional.fromNullable(wcs.findProjectByNestedLocation(nestedLocation));
	}

}
