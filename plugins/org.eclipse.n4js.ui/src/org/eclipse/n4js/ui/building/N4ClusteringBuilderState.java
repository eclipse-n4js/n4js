package org.eclipse.n4js.ui.building;

import static com.google.common.collect.Sets.newHashSet;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.xtext.builder.builderState.AbstractBuilderState;
import org.eclipse.xtext.builder.clustering.ClusteringBuilderState;
import org.eclipse.xtext.builder.clustering.CurrentDescriptions;
import org.eclipse.xtext.builder.debug.IBuildLogger;
import org.eclipse.xtext.builder.impl.BuildData;
import org.eclipse.xtext.builder.resourceloader.IResourceLoader;
import org.eclipse.xtext.resource.CompilerPhases;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescription.Delta;
import org.eclipse.xtext.resource.IResourceDescription.Manager.AllChangeAware;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.clustering.IResourceClusteringPolicy;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;
import org.eclipse.xtext.resource.persistence.SourceLevelURIsAdapter;
import org.eclipse.xtext.resource.persistence.StorageAwareResource;
import org.eclipse.xtext.util.Strings;

import com.google.common.collect.Iterables;
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
@SuppressWarnings({ "restriction" })
class N4ClusteringBuilderState extends AbstractBuilderState {

	public static final String RESOURCELOADER_CROSS_LINKING = "org.eclipse.xtext.builder.resourceloader.crossLinking";

	public static final String RESOURCELOADER_GLOBAL_INDEX = "org.eclipse.xtext.builder.resourceloader.globalIndex";

	/** Intended for internal implementations to share logs. */
	@SuppressWarnings("unused")
	private static final Logger LOGGER = Logger.getLogger(N4JSGenerateImmediatelyBuilderState.class);

	@Inject
	private IResourceServiceProvider.Registry managerRegistry;

	@Inject
	private IResourceClusteringPolicy clusteringPolicy;

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
		return new DoUpdateImplementation(this, buildData, newData,
				SubMonitor.convert(monitor, 1), this.buildLogger,
				crossLinkingResourceLoader, clusteringPolicy)
						.doUpdate();
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
		new WriteNewResourceDescriptionsImplementation(this, buildData, oldState, newState, monitor, buildLogger,
				globalIndexResourceLoader, clusteringPolicy, compilerPhases)
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
					// since the candidate is affected by any of the currently changed resources, we disable
					// the module data of the candidate to ensure that no code will see it later on by accident
					// Related tests:
					// - IncrementalBuilderCornerCasesPluginTest#testMissingReloadBug()
					// - ReproduceInvalidIndexPluginTest
					if (!N4JSGlobals.PACKAGE_JSON.equals(candidateURI.lastSegment())) {
						ResourceDescriptionWithoutModuleUserData noModuleData = new ResourceDescriptionWithoutModuleUserData(
								candidateDescription);
						newState.register(manager.createDelta(candidateDescription, noModuleData));
						// also we ensure that we do run a subsequent build.
						buildData.requestRebuild();
					}
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

	/** Visibility workaround, called by the {@link DoUpdateImplementation} */
	@Override
	protected void updateMarkers(IResourceDescription.Delta delta, ResourceSet resourceSet, IProgressMonitor monitor)
			throws OperationCanceledException {
		super.updateMarkers(delta, resourceSet, monitor);
	}

}
