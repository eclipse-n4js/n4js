/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ui.building;

import java.util.Collections;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.utils.resources.IBuildSuppressingResourceDescriptionManager;
import org.eclipse.xtext.builder.clustering.CopiedResourceDescription;
import org.eclipse.xtext.builder.clustering.CurrentDescriptions;
import org.eclipse.xtext.builder.debug.IBuildLogger;
import org.eclipse.xtext.builder.impl.BuildData;
import org.eclipse.xtext.builder.resourceloader.IResourceLoader;
import org.eclipse.xtext.builder.resourceloader.IResourceLoader.LoadOperation;
import org.eclipse.xtext.builder.resourceloader.IResourceLoader.LoadOperationException;
import org.eclipse.xtext.builder.resourceloader.IResourceLoader.LoadResult;
import org.eclipse.xtext.resource.CompilerPhases;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.clustering.IResourceClusteringPolicy;
import org.eclipse.xtext.resource.impl.DefaultResourceDescriptionDelta;

/**
 * Extracted from {@link N4ClusteringBuilderState}. Encapsulates
 * {@link org.eclipse.n4js.ui.building.N4ClusteringBuilderState#writeNewResourceDescriptions(BuildData, IResourceDescriptions, CurrentDescriptions, IProgressMonitor)}
 * operation.
 */
@SuppressWarnings({ "restriction" })
class WriteNewResourceDescriptionsImplementation {
	/** Intended for internal implementations to share logs. */
	private static final Logger LOGGER = Logger.getLogger(N4JSGenerateImmediatelyBuilderState.class);

	private final CompilerPhases compilerPhases;
	private final IResourceClusteringPolicy clusteringPolicy;
	private final IResourceLoader globalIndexResourceLoader;
	private final BuildData buildData;
	private final IResourceDescriptions oldState;
	private final CurrentDescriptions newState;
	private final N4ClusteringBuilderState state;
	private final SubMonitor progress;
	private final ResourceSet resourceSet;
	private final IProject currentProject;
	private final IBuildLogger buildLogger;
	private LoadOperation loadOperation;

	public WriteNewResourceDescriptionsImplementation(N4ClusteringBuilderState state, BuildData buildData,
			IResourceDescriptions oldState,
			CurrentDescriptions newState, IProgressMonitor monitor,
			IBuildLogger buildLogger,
			IResourceLoader globalIndexResourceLoader,
			IResourceClusteringPolicy clusteringPolicy, CompilerPhases compilerPhases) {
		this.compilerPhases = compilerPhases;
		this.clusteringPolicy = clusteringPolicy;
		this.globalIndexResourceLoader = globalIndexResourceLoader;
		this.state = state;
		this.buildData = buildData;
		this.oldState = oldState;
		this.newState = newState;
		this.progress = SubMonitor.convert(monitor, buildData.getToBeUpdated().size());
		this.buildLogger = buildLogger;
		this.resourceSet = buildData.getResourceSet();
		this.currentProject = state.getBuiltProject(buildData);
	}

	public void writeNewResourceDescriptions() {
		try {
			this.compilerPhases.setIndexing(resourceSet, true);
			doWriteResourceDescriptions();
		} finally {
			this.compilerPhases.setIndexing(resourceSet, false);
			if (loadOperation != null)
				loadOperation.cancel();
		}
	}

	private void doWriteResourceDescriptions() {
		int counter = 0;
		Set<URI> toBeUpdated = buildData.getToBeUpdated();
		createLoadOperation(toBeUpdated);
		while (loadOperation.hasNext()) {
			checkCancelled();

			if (!continueProcessing(counter)) {
				// System.out.println("Start release memory while indexing");
				state.clearResourceSet(resourceSet);
				counter = 0;
			}

			indexNextResource(loadOperation.next());
			counter++;
		}
	}

	private void indexNextResource(LoadResult loadResult) {
		URI uri = null;
		Resource resource = null;
		try {
			uri = loadResult.getUri();
			progress.subTask("Indexing " + uri.lastSegment());
			buildLogger.log("Indexing " + uri);
			resource = state.addResource(loadResult.getResource(), resourceSet);

			registerDelta(uri, resource);
		} catch (final RuntimeException ex) {
			if (ex instanceof LoadOperationException) {
				uri = ((LoadOperationException) ex).getUri();
			}
			if (uri == null) {
				LOGGER.error("Error loading resource", ex); //$NON-NLS-1$
			} else {
				if (resourceSet.getURIConverter().exists(uri, Collections.emptyMap())) {
					LOGGER.error("Error loading resource from: " + uri.toString(), ex); //$NON-NLS-1$
				}
				if (resource != null) {
					resourceSet.getResources().remove(resource);
				}
				final IResourceDescription oldDescription = oldState.getResourceDescription(uri);
				if (oldDescription != null) {
					newState.register(new DefaultResourceDescriptionDelta(oldDescription, null));
				}
			}
		} finally {
			progress.worked(1);
		}
	}

	private void registerDelta(URI uri, Resource resource) {
		final IResourceDescription.Manager manager = state.getResourceDescriptionManager(resource, uri);
		if (manager != null) {
			if (manager instanceof IBuildSuppressingResourceDescriptionManager) {
				if (!((IBuildSuppressingResourceDescriptionManager) manager).isToBeBuilt(uri, resource)) {
					return;
				}
			}
			final IResourceDescription description = manager.getResourceDescription(resource);
			final IResourceDescription copiedDescription = new CopiedResourceDescription(description);
			// uncomment following two lines to avoid additions to index
			newState.register(manager.createDelta(oldState.getResourceDescription(uri), copiedDescription));
			buildData.queueURI(uri);
		}
	}

	private void createLoadOperation(Set<URI> toBeUpdated) {
		loadOperation = globalIndexResourceLoader.create(resourceSet, currentProject);
		loadOperation.load(toBeUpdated);
	}

	private boolean continueProcessing(int clusterIndex) {
		return this.clusteringPolicy.continueProcessing(resourceSet, null, clusterIndex);
	}

	private void checkCancelled() {
		if (progress.isCanceled()) {
			if (loadOperation != null) {
				loadOperation.cancel();
			}
			throw new OperationCanceledException();
		}
	}
}
