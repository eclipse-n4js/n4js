package org.eclipse.n4js.ui.building;

import static com.google.common.collect.Sets.newHashSet;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceStatus;
import org.eclipse.core.resources.IWorkspace;
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
import org.eclipse.xtext.builder.builderState.AbstractBuilderState;
import org.eclipse.xtext.builder.builderState.BuilderStateUtil;
import org.eclipse.xtext.builder.builderState.impl.ResourceDescriptionImpl;
import org.eclipse.xtext.builder.clustering.ClusteringBuilderState;
import org.eclipse.xtext.builder.clustering.CopiedResourceDescription;
import org.eclipse.xtext.builder.clustering.CurrentDescriptions;
import org.eclipse.xtext.builder.debug.IBuildLogger;
import org.eclipse.xtext.builder.impl.BuildData;
import org.eclipse.xtext.builder.resourceloader.IResourceLoader;
import org.eclipse.xtext.builder.resourceloader.IResourceLoader.LoadOperation;
import org.eclipse.xtext.builder.resourceloader.IResourceLoader.LoadOperationException;
import org.eclipse.xtext.builder.resourceloader.IResourceLoader.LoadResult;
import org.eclipse.xtext.resource.CompilerPhases;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescription.Delta;
import org.eclipse.xtext.resource.IResourceDescription.Manager.AllChangeAware;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.impl.DefaultResourceDescriptionDelta;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;
import org.eclipse.xtext.resource.persistence.SourceLevelURIsAdapter;
import org.eclipse.xtext.resource.persistence.StorageAwareResource;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.util.Strings;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.inject.Inject;
import com.google.inject.name.Named;

/**
 * Heavily refactored copy of the {@link ClusteringBuilderState}. Net effect is, that we do have more control over what
 * to do with resources that are already in the resource set, when the cluster is capped. Also we see improvements to
 * the progress indication.
 *
 * TODO this needs more documentation and hooks to monitor the build times
 *
 * @author Sebastian Zarnekow - Initial contribution and API
 */
@SuppressWarnings({ "restriction", "javadoc" })
public class N4ClusteringBuilderState extends AbstractBuilderState {

	/**
	 * This class implements the logic of {@link #writeNewResourceDescriptions()}
	 */
	public static class WriteNewResourceDescriptionsImplementation {

		private final BuildData buildData;
		private final IResourceDescriptions oldState;
		private final CurrentDescriptions newState;
		private final N4ClusteringBuilderState state;
		private final SubMonitor progress;
		private final ResourceSet resourceSet;
		private final IProject currentProject;
		private LoadOperation loadOperation;

		public WriteNewResourceDescriptionsImplementation(N4ClusteringBuilderState state, BuildData buildData,
				IResourceDescriptions oldState,
				CurrentDescriptions newState, IProgressMonitor monitor) {
			this.state = state;
			this.buildData = buildData;
			this.oldState = oldState;
			this.newState = newState;
			this.progress = SubMonitor.convert(monitor, buildData.getToBeUpdated().size());
			this.resourceSet = buildData.getResourceSet();
			this.currentProject = state.getBuiltProject(buildData);
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
				final IResourceDescription description = manager.getResourceDescription(resource);
				final IResourceDescription copiedDescription = new CopiedResourceDescription(description);
				newState.register(
						new DefaultResourceDescriptionDelta(oldState.getResourceDescription(uri), copiedDescription));
				buildData.queueURI(uri);
			}
		}

		public void writeNewResourceDescriptions() {
			try {
				state.compilerPhases.setIndexing(resourceSet, true);
				doWriteResourceDescriptions();
			} finally {
				state.compilerPhases.setIndexing(resourceSet, false);
				if (loadOperation != null)
					loadOperation.cancel();
			}
		}

		private void createLoadOperation(Set<URI> toBeUpdated) {
			loadOperation = state.globalIndexResourceLoader.create(resourceSet, currentProject);
			loadOperation.load(toBeUpdated);
		}

		private boolean continueProcessing(int clusterIndex) {
			return state.clusteringPolicy.continueProcessing(resourceSet, null, clusterIndex);
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

	protected static class DoUpdateImplementation {

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
				ResourceDescriptionsData newData, IProgressMonitor monitor) {
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

		protected void initSourceLevelURIs() {
			buildData.getSourceLevelURICache().cacheAsSourceURIs(toBeDeleted);
			installSourceLevelURIs();
		}

		private void installSourceLevelURIs() {
			state.installSourceLevelURIs(buildData);
		}

		private void initRemainingURIs() {
			allRemainingURIs = getRemainingURIs();
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

				int index = 0;
				while (!queue.isEmpty()) {
					progress.setWorkRemaining(queue.size() * 4 + 1);
					int clusterIndex = doUpdateCluster(index);

					index += clusterIndex;

					installSourceLevelURIs();
					initLoadOperation();

					releaseMemory(clusterIndex);
				}
			} finally {
				done();
			}
			return allDeltas;
		}

		/**
		 * @param allRemainingURIs
		 * @param baseIndex
		 * @param monitor
		 * @return
		 */
		private int doUpdateCluster(int baseIndex) {
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

				// Stopwatch resourceWatch = Stopwatch.createUnstarted();
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

					// resourceWatch.start();
					state.buildLogger.log("indexing " + changedURI);
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
						newDelta = createDelta(changedURI, newDelta);
					}
				}
				// long elapsedMillis = resourceWatch.elapsed(TimeUnit.MILLISECONDS);
				// System.out.println("build|" + changedURI + "|" + elapsedMillis);
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

		/**
		 * @param actualResourceURI
		 * @param resource
		 * @param newDelta
		 * @return
		 */
		private Delta resolveLinks(URI actualResourceURI, Resource resource) {
			final IResourceDescription.Manager manager = state
					.getResourceDescriptionManager(resource, actualResourceURI);
			if (manager != null) {
				// Resolve links here!
				try {
					reportProgress();
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
					LOGGER.error(
							"Error resolving cross references on resource '" + actualResourceURI + "'",
							e);
					throw new LoadOperationException(actualResourceURI, e);
				}
			}
			return null;
		}

		private void reportProgress() {
			progress.split(1);
		}

		/**
		 * @param uri
		 * @param newDelta
		 * @return
		 */
		private Delta createDelta(URI uri, Delta newDelta) {
			final IResourceDescription oldDescription = state.getResourceDescription(uri);
			final IResourceDescription newDesc = newState.getResourceDescription(uri);
			ResourceDescriptionImpl indexReadyDescription = newDesc != null
					? BuilderStateUtil.create(newDesc) : null;
			if ((oldDescription != null || indexReadyDescription != null)
					&& oldDescription != indexReadyDescription) {
				newDelta = new DefaultResourceDescriptionDelta(oldDescription,
						indexReadyDescription);
			}
			return newDelta;
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

		/**
		 * @param processedInThisCluster
		 */
		private void releaseMemory(int processedInThisCluster) {
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
										newDelta = createDelta(resourceURI, newDelta);
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

		/**
		 *
		 */
		private void done() {
			if (loadOperation != null)
				loadOperation.cancel();
		}

		/**
		 * @param clusterIndex
		 * @return
		 */
		private boolean continueProcessing(int clusterIndex) {
			return state.clusteringPolicy.continueProcessing(resourceSet, null, clusterIndex);
		}

		/**
		 *
		 */
		private void initLoadOperation() {
			if (!queue.isEmpty()) {
				loadOperation = state.crossLinkingResourceLoader.create(resourceSet, currentProject);
				loadOperation.load(queue);
			}
		}

		/**
		 * @param changedURI
		 * @return
		 */
		private boolean removeFromQueue(URI changedURI) {
			queue.remove(changedURI);
			if (toBeDeleted.contains(changedURI)) {
				return false;
			}
			return true;
		}

		/**
		 *
		 */
		private void addPendingDeltas() {
			Collection<Delta> pendingDeltas = buildData.getAndRemovePendingDeltas();
			allDeltas.addAll(pendingDeltas);
		}

		private void queueAffectedResources(Collection<Delta> changedDeltas) {
			state.queueAffectedResources(allRemainingURIs, state, newState, changedDeltas, allDeltas, buildData,
					splitMonitor(changedDeltas.size()));
		}

		/**
		 * @param changedDeltas
		 * @return
		 */
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
			state.writeNewResourceDescriptions(buildData, state, newState,
					splitMonitor(buildData.getToBeUpdated().size() * 2));
		}

	}

	public static final String RESOURCELOADER_CROSS_LINKING = "org.eclipse.xtext.builder.resourceloader.crossLinking";

	public static final String RESOURCELOADER_GLOBAL_INDEX = "org.eclipse.xtext.builder.resourceloader.globalIndex";

	/** Class-wide logger. */
	private static final Logger LOGGER = Logger.getLogger(N4ClusteringBuilderState.class);

	@Inject
	private IResourceServiceProvider.Registry managerRegistry;

	@Inject
	private org.eclipse.xtext.resource.clustering.IResourceClusteringPolicy clusteringPolicy;

	@Inject
	@Named(RESOURCELOADER_GLOBAL_INDEX)
	private IResourceLoader globalIndexResourceLoader;

	@Inject
	@Named(RESOURCELOADER_CROSS_LINKING)
	private IResourceLoader crossLinkingResourceLoader;

	@Inject
	private IWorkspace workspace;

	@Inject
	private IResourceServiceProvider.Registry resourceServiceProviderRegistry;

	@Inject
	private CompilerPhases compilerPhases;

	@Inject
	private IBuildLogger buildLogger;

	/**
	 * Actually do the build.
	 *
	 * @param buildData
	 *            the data that should be considered for the update
	 * @param newData
	 *            the new resource descriptions as they are to be persisted (the new index after the build). Initially
	 *            contains the old resource descriptions.
	 * @param monitor
	 *            The progress monitor
	 * @return A list of deltas describing all changes made by the build.
	 */
	@Override
	protected Collection<Delta> doUpdate(BuildData buildData, ResourceDescriptionsData newData,
			IProgressMonitor monitor) {
		return new DoUpdateImplementation(this, buildData, newData, SubMonitor.convert(monitor, 1)).doUpdate();
	}

	protected void installSourceLevelURIs(BuildData buildData) {
		ResourceSet resourceSet = buildData.getResourceSet();
		Iterable<URI> sourceLevelUris = Iterables.concat(buildData.getToBeUpdated(), buildData.getURIQueue());
		Set<URI> sourceUris = newHashSet();
		for (URI uri : sourceLevelUris) {
			if (buildData.getSourceLevelURICache().getOrComputeIsSource(uri, resourceServiceProviderRegistry)) {
				sourceUris.add(uri);
				// unload resources loaded from storage previously
				Resource resource = resourceSet.getResource(uri, false);
				if (resource instanceof StorageAwareResource) {
					if (((StorageAwareResource) resource).isLoadedFromStorage()) {
						resource.unload();
					}
				}
			}
		}
		SourceLevelURIsAdapter.setSourceLevelUris(resourceSet, sourceUris);
	}

	/**
	 * Create new resource descriptions for a set of resources given by their URIs.
	 *
	 * @param buildData
	 *            The underlying data for the write operation.
	 * @param oldState
	 *            The old index
	 * @param newState
	 *            The new index
	 * @param monitor
	 *            The progress monitor used for user feedback
	 */
	protected void writeNewResourceDescriptions(
			BuildData buildData,
			IResourceDescriptions oldState,
			CurrentDescriptions newState,
			final IProgressMonitor monitor) {
		new WriteNewResourceDescriptionsImplementation(this, buildData, oldState, newState, monitor)
				.writeNewResourceDescriptions();
	}

	protected IProject getBuiltProject(BuildData buildData) {
		if (Strings.isEmpty(buildData.getProjectName()))
			return null;
		return workspace.getRoot().getProject(buildData.getProjectName());
	}

	/**
	 * Clears the content of the resource set without sending notifications. This avoids unnecessary, explicit unloads.
	 */
	protected void clearResourceSet(ResourceSet resourceSet) {
		boolean wasDeliver = resourceSet.eDeliver();
		try {
			resourceSet.eSetDeliver(false);
			resourceSet.getResources().clear();
		} finally {
			resourceSet.eSetDeliver(wasDeliver);
		}
	}

	/**
	 * Adds a resource to the ResourceSet if the ResourceSet doesn't contain it yet.
	 *
	 * @param resource
	 *            the resource
	 * @param resourceSet
	 *            the resource set
	 * @return the resource
	 */
	protected Resource addResource(Resource resource, ResourceSet resourceSet) {
		URI uri = resource.getURI();
		Resource r = resourceSet.getResource(uri, false);
		if (r == null) {
			resourceSet.getResources().add(resource);
			return resource;
		} else {
			return r;
		}
	}

	/**
	 * Put all resources that depend on some changes onto the queue of resources to be processed. Updates notInDelta by
	 * removing all URIs put into the queue.
	 *
	 * @param allRemainingURIs
	 *            URIs that were not considered by prior operations.
	 * @param oldState
	 *            State before the build
	 * @param newState
	 *            The current state
	 * @param changedDeltas
	 *            the deltas that have changed {@link IEObjectDescription}s
	 * @param allDeltas
	 *            All deltas
	 * @param buildData
	 *            the underlying data for this build run.
	 * @param monitor
	 *            The progress monitor used for user feedback
	 */
	protected void queueAffectedResources(
			Set<URI> allRemainingURIs,
			IResourceDescriptions oldState,
			CurrentDescriptions newState,
			Collection<Delta> changedDeltas,
			Collection<Delta> allDeltas,
			BuildData buildData,
			final IProgressMonitor monitor) {
		if (allDeltas.isEmpty()) {
			return;
		}
		final SubMonitor progress = SubMonitor.convert(monitor, allRemainingURIs.size());
		Iterator<URI> iter = allRemainingURIs.iterator();
		while (iter.hasNext()) {
			if (progress.isCanceled()) {
				throw new OperationCanceledException();
			}
			final URI candidateURI = iter.next();
			final IResourceDescription candidateDescription = oldState.getResourceDescription(candidateURI);
			final IResourceDescription.Manager manager = getResourceDescriptionManager(candidateURI);
			if (candidateDescription == null || manager == null) {
				// If there is no description in the old state, there's no need to re-check this over and over.
				iter.remove();
			} else {
				boolean affected;
				if ((manager instanceof IResourceDescription.Manager.AllChangeAware)) {
					affected = ((AllChangeAware) manager).isAffectedByAny(allDeltas, candidateDescription, newState);
				} else {
					if (changedDeltas.isEmpty()) {
						affected = false;
					} else {
						affected = manager.isAffected(changedDeltas, candidateDescription, newState);
					}
				}
				if (affected) {
					buildData.queueURI(candidateURI);
					iter.remove();
				}
			}
			progress.worked(1);
		}
	}

	protected IResourceDescription.Manager getResourceDescriptionManager(Resource resource, URI uri) {
		if (resource instanceof XtextResource) {
			return ((XtextResource) resource).getResourceServiceProvider().getResourceDescriptionManager();
		}
		return getResourceDescriptionManager(uri);
	}

	protected IResourceDescription.Manager getResourceDescriptionManager(URI uri) {
		IResourceServiceProvider resourceServiceProvider = managerRegistry.getResourceServiceProvider(uri);
		if (resourceServiceProvider == null) {
			return null;
		}
		return resourceServiceProvider.getResourceDescriptionManager();
	}

}
