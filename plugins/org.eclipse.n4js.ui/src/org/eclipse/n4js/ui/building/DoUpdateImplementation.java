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

import java.util.Collection;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceStatus;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.builder.MonitorBasedCancelIndicator;
import org.eclipse.xtext.builder.builderState.BuilderStateUtil;
import org.eclipse.xtext.builder.clustering.CurrentDescriptions;
import org.eclipse.xtext.builder.debug.IBuildLogger;
import org.eclipse.xtext.builder.impl.BuildData;
import org.eclipse.xtext.builder.resourceloader.IResourceLoader;
import org.eclipse.xtext.builder.resourceloader.IResourceLoader.LoadOperation;
import org.eclipse.xtext.builder.resourceloader.IResourceLoader.LoadOperationException;
import org.eclipse.xtext.builder.resourceloader.IResourceLoader.LoadResult;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescription.Delta;
import org.eclipse.xtext.resource.clustering.IResourceClusteringPolicy;
import org.eclipse.xtext.resource.impl.DefaultResourceDescriptionDelta;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;
import org.eclipse.xtext.util.CancelIndicator;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * Extracted from {@link N4ClusteringBuilderState}. Encapsulates
 * {@link org.eclipse.xtext.builder.builderState.AbstractBuilderState#doUpdate(BuildData, ResourceDescriptionsData, IProgressMonitor)}
 * operation.
 */
@SuppressWarnings({ "restriction", "javadoc" })
class DoUpdateImplementation {
	/** Intended for internal implementations to share logs. */
	private static final Logger LOGGER = Logger.getLogger(N4JSGenerateImmediatelyBuilderState.class);

	private final IResourceClusteringPolicy clusteringPolicy;
	private final IResourceLoader crossLinkingResourceLoader;
	private final IBuildLogger buildLogger;
	private final BuildData buildData;
	private final ResourceDescriptionsData newData;
	private final SubMonitor progress;
	private final CancelIndicator cancelMonitor;
	private final N4ClusteringBuilderState state;
	private final Set<URI> toBeDeleted;
	private final CurrentDescriptions newState;
	private final ResourceSet resourceSet;
	private final Set<Delta> allDeltas = Sets.newHashSet();
	private final IProject currentProject;
	private Queue<URI> queue;
	private final Set<URI> processedURIs = Sets.newHashSet();
	private LoadOperation loadOperation = null;
	private Set<URI> allRemainingURIs;

	public DoUpdateImplementation(N4ClusteringBuilderState state, BuildData buildData,
			ResourceDescriptionsData newData, IProgressMonitor monitor, IBuildLogger buildLogger,
			IResourceLoader crossLinkingResourceLoader, IResourceClusteringPolicy clusteringPolicy) {
		this.clusteringPolicy = clusteringPolicy;
		this.crossLinkingResourceLoader = crossLinkingResourceLoader;
		this.buildLogger = buildLogger;
		this.state = state;
		this.buildData = buildData;
		this.newData = newData;
		this.progress = SubMonitor.convert(monitor);
		this.cancelMonitor = new MonitorBasedCancelIndicator(progress);
		this.toBeDeleted = buildData.getAndRemoveToBeDeleted();
		this.resourceSet = buildData.getResourceSet();
		this.newState = new CurrentDescriptions(resourceSet, newData, buildData);
		this.currentProject = state.getBuiltProject(buildData);
		this.queue = buildData.getURIQueue();
	}

	public Collection<Delta> doUpdate() {
		initSourceLevelURIs();

		progress.setWorkRemaining(buildData.getToBeUpdated().size() * 8 + 1);

		writeNewResourceDescriptions();

		checkCancelled();
		removeDeleted();
		addPendingDeltas();
		initRemainingURIs();
		queueAffectedResources(allDeltas);

		queue = buildData.getURIQueue();
		checkCancelled();
		installSourceLevelURIs();
		try {
			initLoadOperation();

			while (!queue.isEmpty()) {
				progress.setWorkRemaining(queue.size() * 4 + 1);
				int clusterIndex = doUpdateCluster();

				installSourceLevelURIs();
				initLoadOperation();

				clearResourceSetIfNecessary(clusterIndex);
			}
		} finally {
			done();
		}
		return allDeltas;
	}

	private void initSourceLevelURIs() {
		buildData.getSourceLevelURICache().cacheAsSourceURIs(toBeDeleted);
		installSourceLevelURIs();
	}

	private void installSourceLevelURIs() {
		state.installSourceLevelURIs(buildData);
	}

	private void initRemainingURIs() {
		allRemainingURIs = getRemainingURIs();
	}

	private int doUpdateCluster() {
		int clusterIndex = 0;

		final List<Delta> changedDeltas = Lists.newArrayList();
		while (!queue.isEmpty()) {
			checkCancelled();
			if (!continueProcessing(clusterIndex)) {
				break;
			}
			URI changedURI = null;
			Resource resource = null;
			Delta newDelta = null;

			try {
				// Load the resource and create a new resource description
				LoadResult loadResult = loadOperation.next();
				changedURI = loadResult.getUri();
				progress.subTask("Linking " + changedURI.lastSegment() + " and dependencies");
				URI actualResourceURI = loadResult.getResource().getURI();
				resource = state.addResource(loadResult.getResource(), resourceSet);
				reportProgress();

				if (!removeFromQueue(changedURI)) {
					break;
				}

				buildLogger.log("Linking " + changedURI);
				newDelta = resolveLinks(actualResourceURI, resource);
			} catch (final WrappedException ex) {
				if (ex instanceof LoadOperationException) {
					changedURI = ((LoadOperationException) ex).getUri();
				}
				Throwable cause = ex.getCause();
				boolean wasResourceNotFound = false;
				if (cause instanceof CoreException) {
					if (IResourceStatus.RESOURCE_NOT_FOUND == ((CoreException) cause).getStatus()
							.getCode()) {
						wasResourceNotFound = true;
					}
				}
				if (changedURI == null) {
					LOGGER.error("Error loading resource", ex); //$NON-NLS-1$
				} else {
					if (!removeFromQueue(changedURI)) {
						break;
					}
					if (!wasResourceNotFound)
						LOGGER.error("Error loading resource from: " + changedURI.toString(), ex); //$NON-NLS-1$
					if (resource != null) {
						resourceSet.getResources().remove(resource);
					}
					newDelta = createRemoveDelta(changedURI);
				}
			}

			if (newDelta != null) {
				clusterIndex++;
				if (processNewDelta(newDelta)) {
					changedDeltas.add(newDelta);
				}
			}
		}
		loadOperation.cancel();
		queueAffectedResources(changedDeltas);
		return clusterIndex;
	}

	private Delta resolveLinks(URI actualResourceURI, Resource resource) {
		final IResourceDescription.Manager manager = state.getResourceDescriptionManager(resource, actualResourceURI);
		if (manager != null) {
			try {
				reportProgress();
				// Resolve links here!
				EcoreUtil2.resolveLazyCrossReferences(resource, cancelMonitor);

				final IResourceDescription description = manager.getResourceDescription(resource);
				final IResourceDescription copiedDescription = BuilderStateUtil.create(description);
				return manager.createDelta(state.getResourceDescription(actualResourceURI), copiedDescription);
			} catch (OperationCanceledException e) {
				loadOperation.cancel();
				throw e;
			} catch (WrappedException e) {
				throw e;
			} catch (RuntimeException e) {
				LOGGER.error("Error resolving cross references on resource '" + actualResourceURI + "'", e);
				throw new LoadOperationException(actualResourceURI, e);
			}
		}
		return null;
	}

	private void reportProgress() {
		progress.split(1);
	}

	private Delta createRemoveDelta(URI uri) {
		final IResourceDescription oldDescription = state.getResourceDescription(uri);
		if (oldDescription != null) {
			return new DefaultResourceDescriptionDelta(oldDescription, null);
		}
		return null;
	}

	private boolean processNewDelta(Delta newDelta) {
		processedURIs.add(newDelta.getUri());

		allDeltas.add(newDelta);

		// Make the new resource description known and update the map.
		newState.register(newDelta);
		// Validate now.
		if (!buildData.isIndexingOnly()) {
			try {
				progress.subTask("Compiling " + newDelta.getUri().lastSegment());
				buildLogger.log("Compiling " + newDelta.getUri());
				state.updateMarkers(newDelta, resourceSet, progress.split(2));
			} catch (OperationCanceledException e) {
				loadOperation.cancel();
				throw e;
			} catch (Exception e) {
				LOGGER.error("Error validating " + newDelta.getUri(), e);
			}
		}
		return newDelta.haveEObjectDescriptionsChanged();
	}

	private void clearResourceSetIfNecessary(int processedInThisCluster) {
		if (!queue.isEmpty() && !continueProcessing(processedInThisCluster)) {
			// System.out.println("Start release memory");
			List<Delta> changedDeltas = Lists.newArrayList();
			List<Resource> resources = resourceSet.getResources();
			progress.setWorkRemaining(queue.size() * 4 + resources.size());
			for (int i = 0; i < resources.size(); i++) {
				Resource res = resources.get(i);
				if (res instanceof N4JSResource) {
					N4JSResource casted = (N4JSResource) res;
					if (!casted.isLoadedFromDescription() && casted.isFullyProcessed()) {
						// Stopwatch stopwatch = Stopwatch.createStarted();
						URI resourceURI = casted.getURI();
						if (!processedURIs.contains(resourceURI)) {
							if (queue.remove(resourceURI)) {
								Delta newDelta = null;
								try {
									newDelta = resolveLinks(resourceURI, casted);
								} catch (WrappedException ex) {
									newDelta = createRemoveDelta(resourceURI);
								}
								if (newDelta != null && processNewDelta(newDelta)) {
									changedDeltas.add(newDelta);
									// long elapsedMillis = stopwatch.elapsed(TimeUnit.MILLISECONDS);
									// System.out.println("beforeReleaseMemory|" + res.getURI() + "|" +
									// elapsedMillis);
								}
							}
						}
					}
				}
				reportProgress();
			}
			queueAffectedResources(changedDeltas);
			state.clearResourceSet(resourceSet);
		}
	}

	private void done() {
		if (loadOperation != null)
			loadOperation.cancel();
	}

	private boolean continueProcessing(int clusterIndex) {
		return this.clusteringPolicy.continueProcessing(resourceSet, null, clusterIndex);
	}

	private void initLoadOperation() {
		if (!queue.isEmpty()) {
			loadOperation = crossLinkingResourceLoader.create(resourceSet, currentProject);
			loadOperation.load(queue);
		}
	}

	private boolean removeFromQueue(URI changedURI) {
		queue.remove(changedURI);
		if (toBeDeleted.contains(changedURI)) {
			return false;
		}
		return true;
	}

	private void addPendingDeltas() {
		Collection<Delta> pendingDeltas = buildData.getAndRemovePendingDeltas();
		allDeltas.addAll(pendingDeltas);
	}

	private void queueAffectedResources(Collection<Delta> changedDeltas) {
		state.queueAffectedResources(allRemainingURIs, state, newState, changedDeltas, allDeltas, buildData,
				splitMonitor(changedDeltas.size()));
	}

	private SubMonitor splitMonitor(int consumeWork) {
		return progress.split(consumeWork);
	}

	private Set<URI> getRemainingURIs() {
		final Set<URI> result = Sets.newLinkedHashSet(newData.getAllURIs());
		result.removeAll(buildData.getToBeUpdated());
		for (URI remainingURI : buildData.getAllRemainingURIs()) {
			result.remove(remainingURI);
		}
		return result;
	}

	private void removeDeleted() {
		if (!toBeDeleted.isEmpty()) {
			for (final URI uri : toBeDeleted) {
				newData.removeDescription(uri);
				final IResourceDescription oldDescription = state.getResourceDescription(uri);
				if (oldDescription != null) {
					allDeltas.add(new DefaultResourceDescriptionDelta(oldDescription, null));
				}
			}
		}
	}

	private void checkCancelled() {
		if (progress.isCanceled()) {
			if (loadOperation != null) {
				loadOperation.cancel();
			}
			throw new OperationCanceledException();
		}
	}

	private void writeNewResourceDescriptions() {
		SubMonitor splitMonitor = splitMonitor(buildData.getToBeUpdated().size() * 2);
		state.writeNewResourceDescriptions(buildData, state, newState, splitMonitor);
	}

}
