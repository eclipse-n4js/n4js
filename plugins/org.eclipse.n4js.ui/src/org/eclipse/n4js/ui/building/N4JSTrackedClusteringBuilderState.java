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
package org.eclipse.n4js.ui.building;

import static com.google.common.collect.Sets.newHashSet;
import static org.eclipse.n4js.projectModel.IN4JSProject.N4MF_MANIFEST;
import static org.eclipse.n4js.ui.internal.N4JSActivator.ORG_ECLIPSE_N4JS_N4JS;

import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceStatus;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.external.ExternalLibraryWorkspace;
import org.eclipse.n4js.smith.DataCollector;
import org.eclipse.n4js.smith.DataCollectors;
import org.eclipse.n4js.smith.Measurement;
import org.eclipse.n4js.ui.building.BuilderStateLogger.BuilderState;
import org.eclipse.n4js.ui.building.instructions.IBuildParticipantInstruction;
import org.eclipse.n4js.ui.internal.ContributingResourceDescriptionPersister;
import org.eclipse.n4js.ui.internal.N4JSActivator;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.builder.IXtextBuilderParticipant;
import org.eclipse.xtext.builder.IXtextBuilderParticipant.BuildType;
import org.eclipse.xtext.builder.MonitorBasedCancelIndicator;
import org.eclipse.xtext.builder.builderState.BuilderStateUtil;
import org.eclipse.xtext.builder.builderState.impl.ResourceDescriptionImpl;
import org.eclipse.xtext.builder.clustering.ClusteringBuilderState;
import org.eclipse.xtext.builder.clustering.CopiedResourceDescription;
import org.eclipse.xtext.builder.clustering.CurrentDescriptions;
import org.eclipse.xtext.builder.debug.IBuildLogger;
import org.eclipse.xtext.builder.impl.BuildData;
import org.eclipse.xtext.builder.impl.RegistryBuilderParticipant;
import org.eclipse.xtext.builder.impl.RegistryBuilderParticipant.DeferredBuilderParticipant;
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
import org.eclipse.xtext.resource.impl.DefaultResourceDescriptionDelta;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;
import org.eclipse.xtext.resource.persistence.SourceLevelURIsAdapter;
import org.eclipse.xtext.resource.persistence.StorageAwareResource;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.util.Strings;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Named;

/**
 * Clustering builder state infused with data tracking code. Allows to track build process.
 * <p>
 * For the moment it is naive copy of the {@link N4JSGenerateImmediatelyBuilderState}, but instead of delegating to the
 * {@link ClusteringBuilderState} it contains copy of its internals. For example while in the
 * {@link N4JSGenerateImmediatelyBuilderState#doUpdate} we may call {@code super.doUpdate()}, here we call
 * {@code overrideDoUpdate()}, where {@code overrideDoUpdate()} is a local copy of the
 * {@link ClusteringBuilderState#doUpdate}. This way we can insert tracking code inside of the builder internals.
 *
 * As part of the GH-238 we restrain for modifying original logic from mentioned classes, as we want just to provide the
 * build process tracking tools. Builder optimizations and refactorings may be done in separate tasks.
 *
 * @see <a href="https://github.com/eclipse/n4js/issues/238">GH-238</a>
 */
@SuppressWarnings("restriction")
public class N4JSTrackedClusteringBuilderState extends ClusteringBuilderState {

	// ====== from org.eclipse.n4js.ui.building.N4JSGenerateImmediatelyBuilderState

	@Inject
	private RegistryBuilderParticipant builderParticipant;

	@Inject
	private ContributingResourceDescriptionPersister descriptionPersister;

	@Inject
	@BuilderState
	private IBuildLogger builderStateLogger;

	private final DataCollector builderProcess;
	private final DataCollector builderBuildInstruction;
	private final DataCollector builderClusteringUpdate;
	private final DataCollector builderClusteringNewState;
	private final DataCollector builderClusteringWriteResourceDescs;
	private final DataCollector builderClusteringLoadOperation;
	private final DataCollector builderValidations;
	private final DataCollector builderProxies;

	/** constructor for setting up tracking in the builder */
	@Inject
	public N4JSTrackedClusteringBuilderState() {
		this.builderProcess = DataCollectors.INSTANCE.getOrCreateDataCollector("builder");
		this.builderBuildInstruction = DataCollectors.INSTANCE.getOrCreateDataCollector("build instruction",
				this.builderProcess);
		this.builderClusteringUpdate = DataCollectors.INSTANCE.getOrCreateDataCollector("clustering update",
				this.builderProcess);
		this.builderClusteringNewState = DataCollectors.INSTANCE.getOrCreateDataCollector("new state",
				builderClusteringUpdate);
		this.builderClusteringWriteResourceDescs = DataCollectors.INSTANCE.getOrCreateDataCollector(
				"write resource descs", builderClusteringUpdate);
		this.builderClusteringLoadOperation = DataCollectors.INSTANCE.getOrCreateDataCollector(
				"load operation", builderClusteringUpdate);
		this.builderValidations = DataCollectors.INSTANCE.getOrCreateDataCollector("validations",
				builderClusteringUpdate);
		this.builderProxies = DataCollectors.INSTANCE.getOrCreateDataCollector("proxies", builderClusteringUpdate);
	}

	/**
	 * After the load phase, checks whether the underlying index content is empty or a recovery builder was scheduled,
	 * if so, populates the index content with the external libraries as well.
	 */
	@Override
	public synchronized void load() {
		super.load();
		// On the very first startup there will be recovery build.
		if (descriptionPersister.isRecoveryBuildRequired()) {
			descriptionPersister.scheduleRecoveryBuildOnContributions();
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * Initializes an adapter which is attached to the builder's resource set. This adapter will be used later on to
	 * process each delta after the corresponding resource was validated.
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

		Measurement measurement = builderProcess.getMeasurement("builder_" + Instant.now());

		builderStateLogger.log("N4JSGenerateImmediatelyBuilderState.doUpdate() >>>");
		logBuildData(buildData, " of before #doUpdate");

		Measurement measurement2 = builderBuildInstruction.getMeasurement("instruction_" + Instant.now());
		IProject project = getProject(buildData);
		try {
			BuildType buildType = N4JSBuildTypeTracker.getBuildType(project);
			IBuildParticipantInstruction instruction;
			if (buildType == null) {
				instruction = IBuildParticipantInstruction.NOOP;
			} else {
				instruction = findJSBuilderParticipant().prepareBuild(project, buildType);
			}
			// removed after the build automatically;
			// the resource set is discarded afterwards, anyway
			buildData.getResourceSet().eAdapters().add(instruction);
		} catch (CoreException e) {
			handleCoreException(e);
		}
		measurement2.end();
		Collection<Delta> modifiedDeltas = overrideDoUpdate(buildData, newData, monitor);
		logBuildData(buildData, " of after #doUpdate");
		builderStateLogger.log("Modified deltas: " + modifiedDeltas);
		builderStateLogger.log("N4JSGenerateImmediatelyBuilderState.doUpdate() <<<");

		measurement.end();
		return modifiedDeltas;
	}

	@SuppressWarnings("unused")
	private void logBuildData(BuildData buildData, String... tags) {
		// This log call sometimes yields a ConcurrentModificationException (see GHOLD-296)
		// We disable it as a temporary fix only until GHOLD-296 is resolved.
		// TODO Uncomment the following code when GHOLD-296 is resolved and remove the SuppressWarnings annotation.

		/* @formatter:off */
		/*
		String tag = Arrays2.isEmpty(tags) ? "" : Joiner.on(" - ").join(tags);
		String header = "---------------------- Build data" + tag + " --------------------------------------";
		builderStateLogger.log(header);
		builderStateLogger.log("Project name: " + buildData.getProjectName());
		builderStateLogger.log("To be deleted: " + ensureNotNull(buildData.getToBeDeleted()));
		builderStateLogger.log("To be updated: " + ensureNotNull(buildData.getToBeUpdated()));
		builderStateLogger.log("URI queue: " + buildData.getURIQueue());
		builderStateLogger.log("All remaining URIs: " + buildData.getAllRemainingURIs());
		builderStateLogger.log(Strings.repeat("-", header.length()) + "\n");
		*/
		/* @formatter:on */
	}

	@Override
	protected void updateMarkers(Delta delta, ResourceSet resourceSet, IProgressMonitor monitor) {
		super.updateMarkers(delta, resourceSet, monitor);

		if (resourceSet != null) { // resourceSet is null during clean build
			IBuildParticipantInstruction instruction = (IBuildParticipantInstruction) EcoreUtil.getAdapter(
					resourceSet.eAdapters(), IBuildParticipantInstruction.class);
			if (instruction == null) {
				throw new IllegalStateException();
			}
			try {
				instruction.process(delta, resourceSet, monitor);
			} catch (CoreException e) {
				handleCoreException(e);
			}
		}
	}

	@Override
	protected void clearResourceSet(final ResourceSet resourceSet) {
		N4JSResourceSetCleanerUtils.clearResourceSet(resourceSet);
	}

	private IProject getProject(BuildData buildData) {
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(buildData.getProjectName());
		if (null == project || !project.isAccessible()) {
			final IProject externalProject = getExternalLibraryWorkspace().getProject(buildData.getProjectName());
			if (null != externalProject && externalProject.exists()) {
				project = externalProject;
			}
		}
		return project;
	}

	private N4JSBuilderParticipant findJSBuilderParticipant() {
		ImmutableList<IXtextBuilderParticipant> all = builderParticipant.getParticipants();
		for (IXtextBuilderParticipant candidate : all) {
			if (candidate instanceof DeferredBuilderParticipant) {
				DeferredBuilderParticipant dbp = (DeferredBuilderParticipant) candidate;
				if (isParticipating(dbp)) {
					IXtextBuilderParticipant delegate = dbp.getDelegate();
					if (delegate instanceof N4JSBuilderParticipant) {
						return (N4JSBuilderParticipant) delegate;
					}
				}
			}
			// N4JSBuilderParticipant is never directly used, it's always delegated to via an DeferredBuilderParticipant
		}
		throw new IllegalStateException();
	}

	// cannot be injected with annotation as there will be wrong injection context
	// @Inject FileExtensionProvider fileExtensionProvider
	// doesn't work neither
	// FileExtensionProvider fileExtensionProvider =
	// N4JSActivator.getInstance().getInjector(N4JSActivator.ORG_ECLIPSE_IDE_N4JS_N4JS).getProvider(FileExtensionProvider.class).get();

	/**
	 * Check if given build participant is supporting given file type
	 */
	private boolean isParticipating(DeferredBuilderParticipant dbp) {
		// TODO switch hardcoded extensions to FileExtensionProvider query
		for (String ext : N4JSGlobals.ALL_N4_FILE_EXTENSIONS) {
			if (dbp.isParticipating(ext)) {
				return true;
			}
		}
		return false;
	}

	private void handleCoreException(CoreException e) {
		N4JSActivator.getInstance().getLog()
				.log(new Status(IStatus.ERROR, N4JSActivator.ORG_ECLIPSE_N4JS_N4JS, e.getMessage(), e));
	}

	/**
	 * Overriding this method to make sure that resources of all affected URIs are fully re-loaded if needed, instead of
	 * only loading the TModule from the corresponding resource description.
	 * <p>
	 * This is required in case the URIs in an affected resource contain indices of a changed resource; just loading the
	 * TModule from the user data won't update these indices. For details see the example provided in IDEBUG-347.
	 * <p>
	 * NOTE: this should be removed once the URI scheme has been changed to use names instead of indices.
	 */
	@Override
	protected void queueAffectedResources(
			Set<URI> allRemainingURIs,
			IResourceDescriptions oldState,
			CurrentDescriptions newState,
			Collection<Delta> changedDeltas,
			Collection<Delta> allDeltas,
			BuildData buildData,
			final IProgressMonitor monitor) {

		// don't wanna copy super-class method, so using this helper to get the set of affected URIs:
		final Set<URI> affectedURIs = new HashSet<>(allRemainingURIs);

		overrideQueueAffectedResources(allRemainingURIs, oldState, newState, changedDeltas, allDeltas, buildData,
				monitor);

		// affected URIs have been removed from allRemainingURIs by super class
		affectedURIs.removeAll(allRemainingURIs);

		for (URI currAffURI : affectedURIs) {
			final IResourceDescription resDesc = this.getResourceDescription(currAffURI);
			if (!N4MF_MANIFEST.equals(currAffURI.lastSegment())) {

				/*-
				 * This logic here is required to get rid of the invalid serialized TModules information from the index
				 * which are working with an index based approach. Consider the below example:
				 *
				 * -------Module A------
				 *1    //class XYZ { }
				 *2    function foo() { }
				 *3    export public class A { }
				 *
				 * -------Module B------
				 *1    import { A } from "A"
				 *2    import { C } from "C"
				 *3
				 *4    var arrCC : Array<A>;
				 *5    var t2 : C = new C();
				 *6    t2.m(arrCC);
				 *
				 * -------Module C------
				 *1    import { A } from "A"
				 *2
				 *3    export public class C {
				 *4        m(param : Array<A>) { }
				 *5    }
				 *
				 *
				 * Commenting out line 1 in module A will trigger rebuild of A, and related module B and C in this order.
				 * When loading module B, module C has to be resolved as it imports it, quickly jump to module C and load
				 * class A from module A, class A used to have index 1 (in the serialized TModule in the Xtext index) as
				 * it was the second top level element, but that is not true any more, because 'foo' was just commented out,
				 * so index 1 in module A is not class A any more but 'foo'. With this, line 6 in module B will fail,
				 * because it will think that the method 'm' accepts an array of 'foo' and not A any more.
				 *
				 * The following code will be executed after A was processed and B and C are the "affectedURIs". With this
				 * code, we make sure that the cached TModule of C (in the user data of C's resource description) won't be
				 * used while processing B during proxy resolution.
				 */
				newState.register(new DefaultResourceDescriptionDelta(resDesc,
						new ResourceDescriptionWithoutModuleUserData(resDesc)));
			}
		}
	}

	private ExternalLibraryWorkspace getExternalLibraryWorkspace() {
		final Injector injector = N4JSActivator.getInstance().getInjector(ORG_ECLIPSE_N4JS_N4JS);
		return injector.getInstance(ExternalLibraryWorkspace.class);
	}

	/** Added to track calls to the super class */
	@Override
	protected void writeNewResourceDescriptions(
			BuildData buildData,
			IResourceDescriptions oldState,
			CurrentDescriptions newState,
			final IProgressMonitor monitor) {
		overrideWriteNewResourceDescriptions(buildData, oldState, newState, monitor);
	}

	// ======== from org.eclipse.xtext.builder.clustering.ClusteringBuilderState

	@SuppressWarnings({ "javadoc", "hiding" })
	public static final String RESOURCELOADER_CROSS_LINKING = "org.eclipse.xtext.builder.resourceloader.crossLinking";

	@SuppressWarnings({ "javadoc", "hiding" })
	public static final String RESOURCELOADER_GLOBAL_INDEX = "org.eclipse.xtext.builder.resourceloader.globalIndex";

	/** Class-wide logger. */
	private static final Logger LOGGER = Logger.getLogger(ClusteringBuilderState.class);

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

	private static final int MONITOR_DO_UPDATE_CHUNK = 10;

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
	protected Collection<Delta> overrideDoUpdate(BuildData buildData, ResourceDescriptionsData newData,
			IProgressMonitor monitor) {
		Measurement measurement2 = builderClusteringUpdate.getMeasurement("clustering update_" + Instant.now());
		final SubMonitor progress = SubMonitor.convert(monitor, 100);

		// Step 1: Clean the set of deleted URIs. If any of them are also added, they're not deleted.
		final Set<URI> toBeDeleted = buildData.getAndRemoveToBeDeleted();

		// Step 2: Create a new state (old state minus the deleted resources). This, by virtue of the flag
		// NAMED_BUILDER_SCOPE in the resource set's load options
		// and a Guice binding, is the index that is used during the build; i.e., linking during the build will
		// use this. Once the build is completed, the persistable index is reset to the contents of newState by
		// virtue of the newMap, which is maintained in synch with this.
		Measurement measeureNewState = builderClusteringNewState.getMeasurement("new state_" + Instant.now());
		ResourceSet resourceSet = buildData.getResourceSet();
		final CurrentDescriptions newState = new CurrentDescriptions(resourceSet, newData, buildData);
		buildData.getSourceLevelURICache().cacheAsSourceURIs(toBeDeleted);
		installSourceLevelURIs(buildData);
		measeureNewState.end();
		// Step 3: Create a queue; write new temporary resource descriptions for the added or updated resources so that
		// we can link
		// subsequently; put all the added or updated resources into the queue.
		Measurement measureWrite = builderClusteringWriteResourceDescs
				.getMeasurement("writeNewResourceDescriptions_" + Instant.now());
		writeNewResourceDescriptions(buildData, this, newState, progress.newChild(20));
		measureWrite.end();

		if (progress.isCanceled()) {
			throw new OperationCanceledException();
		}

		// Step 4: Create a URI set of resources not yet in the delta. This is used for queuing; whenever a resource is
		// queued for processing, its URI is removed from this set. queueAffectedResources will consider only resources
		// in this set as potential candidates.
		for (final URI uri : toBeDeleted) {
			newData.removeDescription(uri);
		}
		final Set<URI> allRemainingURIs = Sets.newLinkedHashSet(newData.getAllURIs());
		allRemainingURIs.removeAll(buildData.getToBeUpdated());
		for (URI remainingURI : buildData.getAllRemainingURIs()) {
			allRemainingURIs.remove(remainingURI);
		}
		// TODO: consider to remove any entry from upstream projects and independent projects
		// from the set of remaining uris (template method or service?)
		// this should reduce the number of to-be-checked descriptions significantly
		// for common setups (large number of reasonable sized projects)

		// Our return value. It contains all the deltas resulting from this build.
		final Set<Delta> allDeltas = Sets.newHashSet();

		// Step 5: Put all resources depending on a deleted resource into the queue. Also register the deltas in
		// allDeltas.
		if (!toBeDeleted.isEmpty()) {
			for (final URI uri : toBeDeleted) {
				final IResourceDescription oldDescription = this.getResourceDescription(uri);
				if (oldDescription != null) {
					allDeltas.add(new DefaultResourceDescriptionDelta(oldDescription, null));
				}
			}
		}
		// Add all pending deltas to all deltas (may be scheduled java deltas)
		Collection<Delta> pendingDeltas = buildData.getAndRemovePendingDeltas();
		allDeltas.addAll(pendingDeltas);
		queueAffectedResources(allRemainingURIs, this, newState, allDeltas, allDeltas, buildData, progress.newChild(1));
		installSourceLevelURIs(buildData);

		IProject currentProject = getBuiltProject(buildData);
		LoadOperation loadOperation = null;
		try {
			Queue<URI> queue = buildData.getURIQueue();

			Measurement measureLoadQueue = builderClusteringLoadOperation
					.getMeasurement("load(queue)_" + Instant.now());
			loadOperation = crossLinkingResourceLoader.create(resourceSet, currentProject);
			loadOperation.load(queue);
			measureLoadQueue.end();

			// Step 6: Iteratively got through the queue. For each resource, create a new resource description and queue
			// all depending
			// resources that are not yet in the delta. Validate resources. Do this in chunks.
			final SubMonitor subProgress = progress.newChild(80);
			CancelIndicator cancelMonitor = new MonitorBasedCancelIndicator(progress);

			int index = 0;
			while (!queue.isEmpty()) {
				// heuristic: only 2/3 of ticks will be consumed; rest kept for affected resources
				if (index % MONITOR_DO_UPDATE_CHUNK == 0) {
					subProgress.setWorkRemaining(((queue.size() / MONITOR_DO_UPDATE_CHUNK) + 1) * 3);
				}
				int clusterIndex = 0;
				final List<Delta> changedDeltas = Lists.newArrayList();
				while (!queue.isEmpty()) {
					if (subProgress.isCanceled()) {
						loadOperation.cancel();
						throw new OperationCanceledException();
					}
					if (!clusteringPolicy.continueProcessing(resourceSet, null, clusterIndex)) {
						break;
					}
					URI changedURI = null;
					URI actualResourceURI = null;
					Resource resource = null;
					Delta newDelta = null;

					try {
						// Load the resource and create a new resource description
						LoadResult loadResult = loadOperation.next();
						changedURI = loadResult.getUri();
						actualResourceURI = loadResult.getResource().getURI();
						resource = addResource(loadResult.getResource(), resourceSet);
						if (index % MONITOR_DO_UPDATE_CHUNK == 0) {
							subProgress.subTask(
									"Updating resource descriptions chunk " + (index / MONITOR_DO_UPDATE_CHUNK + 1)
											+ " of " + ((index + queue.size()) / MONITOR_DO_UPDATE_CHUNK + 1));
						}
						if (LOGGER.isDebugEnabled()) {
							LOGGER.debug("Update resource description " + actualResourceURI);
						}
						queue.remove(changedURI);
						if (toBeDeleted.contains(changedURI)) {
							break;
						}
						buildLogger.log("indexing " + changedURI);
						final IResourceDescription.Manager manager = getResourceDescriptionManager(actualResourceURI);
						if (manager != null) {
							// Resolve links here!
							try {
								Measurement measurement = builderProxies.getMeasurement("" + changedURI);
								EcoreUtil2.resolveLazyCrossReferences(resource, cancelMonitor);
								measurement.end();
								final IResourceDescription description = manager.getResourceDescription(resource);
								final IResourceDescription copiedDescription = BuilderStateUtil.create(description);
								newDelta = manager.createDelta(this.getResourceDescription(actualResourceURI),
										copiedDescription);
							} catch (OperationCanceledException e) {
								loadOperation.cancel();
								throw e;
							} catch (WrappedException e) {
								throw e;
							} catch (RuntimeException e) {
								LOGGER.error("Error resolving cross references on resource '" + actualResourceURI + "'",
										e);
								throw new LoadOperationException(actualResourceURI, e);
							}
						}
					} catch (final WrappedException ex) {
						if (ex instanceof LoadOperationException) {
							changedURI = ((LoadOperationException) ex).getUri();
						}
						Throwable cause = ex.getCause();
						boolean wasResourceNotFound = false;
						if (cause instanceof CoreException) {
							if (IResourceStatus.RESOURCE_NOT_FOUND == ((CoreException) cause).getStatus().getCode()) {
								wasResourceNotFound = true;
							}
						}
						if (changedURI == null) {
							LOGGER.error("Error loading resource", ex); //$NON-NLS-1$
						} else {
							queue.remove(changedURI);
							if (toBeDeleted.contains(changedURI))
								break;
							if (!wasResourceNotFound)
								LOGGER.error("Error loading resource from: " + changedURI.toString(), ex); //$NON-NLS-1$
							if (resource != null) {
								resourceSet.getResources().remove(resource);
							}
							final IResourceDescription oldDescription = this.getResourceDescription(changedURI);
							final IResourceDescription newDesc = newState.getResourceDescription(changedURI);
							ResourceDescriptionImpl indexReadyDescription = newDesc != null
									? BuilderStateUtil.create(newDesc) : null;
							if ((oldDescription != null || indexReadyDescription != null)
									&& oldDescription != indexReadyDescription) {
								newDelta = new DefaultResourceDescriptionDelta(oldDescription, indexReadyDescription);
							}
						}
					}
					if (newDelta != null) {
						allDeltas.add(newDelta);
						clusterIndex++;
						if (newDelta.haveEObjectDescriptionsChanged())
							changedDeltas.add(newDelta);
						// Make the new resource description known and update the map.
						newState.register(newDelta);
						// Validate now.
						if (!buildData.isIndexingOnly()) {
							try {
								Measurement measurement = builderValidations.getMeasurement("updateMarkers");
								updateMarkers(newDelta, resourceSet, subProgress);
								measurement.end();
							} catch (OperationCanceledException e) {
								loadOperation.cancel();
								throw e;
							} catch (Exception e) {
								LOGGER.error("Error validating " + newDelta.getUri(), e);
							}
						}
					}
					index++;
					if (index % MONITOR_DO_UPDATE_CHUNK == 0) {
						subProgress.worked(1);
					}
				}

				loadOperation.cancel();

				queueAffectedResources(allRemainingURIs, this, newState, changedDeltas, allDeltas, buildData,
						subProgress.newChild(1));
				installSourceLevelURIs(buildData);
				if (queue.size() > 0) {
					loadOperation = crossLinkingResourceLoader.create(resourceSet, currentProject);
					loadOperation.load(queue);
				}

				// Release memory
				if (!queue.isEmpty() && !clusteringPolicy.continueProcessing(resourceSet, null, clusterIndex))
					clearResourceSet(resourceSet);
			}
		} finally {
			if (loadOperation != null)
				loadOperation.cancel();
			if (!progress.isCanceled())
				progress.done();
		}
		measurement2.end();
		return allDeltas;
	}

	@Override
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

	private static final int MONITOR_WRITE_CHUNK = 50;

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
	protected void overrideWriteNewResourceDescriptions(
			BuildData buildData,
			IResourceDescriptions oldState,
			CurrentDescriptions newState,
			final IProgressMonitor monitor) {
		int index = 0;
		ResourceSet resourceSet = buildData.getResourceSet();
		Set<URI> toBeUpdated = buildData.getToBeUpdated();
		final int n = toBeUpdated.size();
		final SubMonitor subMonitor = SubMonitor.convert(monitor, "Write new resource descriptions",
				n / MONITOR_WRITE_CHUNK + 1); // TODO: NLS
		IProject currentProject = getBuiltProject(buildData);
		LoadOperation loadOperation = null;
		try {
			compilerPhases.setIndexing(resourceSet, true);
			loadOperation = globalIndexResourceLoader.create(resourceSet, currentProject);
			loadOperation.load(toBeUpdated);

			while (loadOperation.hasNext()) {
				if (subMonitor.isCanceled()) {
					loadOperation.cancel();
					throw new OperationCanceledException();
				}

				if (!clusteringPolicy.continueProcessing(resourceSet, null, index)) {
					clearResourceSet(resourceSet);
				}

				URI uri = null;
				Resource resource = null;
				try {
					LoadResult loadResult = loadOperation.next();
					uri = loadResult.getUri();
					resource = addResource(loadResult.getResource(), resourceSet);
					if (index % MONITOR_WRITE_CHUNK == 0) {
						subMonitor.subTask("Writing new resource descriptions chunk "
								+ (index / MONITOR_WRITE_CHUNK + 1) + " of " + (n / MONITOR_WRITE_CHUNK + 1));
					}
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("Writing new resource description " + uri);
					}

					final IResourceDescription.Manager manager = getResourceDescriptionManager(uri);
					if (manager != null) {
						// We don't care here about links, we really just want the exported objects so that we can link
						// in the
						// next phase.
						final IResourceDescription description = manager.getResourceDescription(resource);
						final IResourceDescription copiedDescription = new CopiedResourceDescription(description);
						// We also don't care what kind of Delta we get here; it's just a temporary transport vehicle.
						// That interface
						// could do with some clean-up, too, because all we actually want to do is register the new
						// resource
						// description, not the delta.
						newState.register(new DefaultResourceDescriptionDelta(oldState.getResourceDescription(uri),
								copiedDescription));
						buildData.queueURI(uri);
					}
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
					// If we couldn't load it, there's no use trying again: do not add it to the queue
				}
				index++;
				if (index % MONITOR_WRITE_CHUNK == 0)
					subMonitor.worked(1);
			}
		} finally {
			compilerPhases.setIndexing(resourceSet, false);
			if (loadOperation != null)
				loadOperation.cancel();
		}

	}

	@Override
	protected IProject getBuiltProject(BuildData buildData) {
		if (Strings.isEmpty(buildData.getProjectName()))
			return null;
		return workspace.getRoot().getProject(buildData.getProjectName());
	}

	// we use copy from N4JSGenerateImmediatelyBuilderState, see the local method above
	/**
	 * Clears the content of the resource set without sending notifications. This avoids unnecessary, explicit unloads.
	 */
	// protected void clearResourceSet(ResourceSet resourceSet) {
	// boolean wasDeliver = resourceSet.eDeliver();
	// try {
	// resourceSet.eSetDeliver(false);
	// resourceSet.getResources().clear();
	// } finally {
	// resourceSet.eSetDeliver(wasDeliver);
	// }
	// }

	/**
	 * Adds a resource to the ResourceSet if the ResourceSet doesn't contain it yet.
	 *
	 * @param resource
	 *            the resource
	 * @param resourceSet
	 *            the resource set
	 * @return the resource
	 */
	@Override
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

	private static final int MONITOR_QUEUE_CHUNK = 100;

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
	protected void overrideQueueAffectedResources(
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
		final SubMonitor progress = SubMonitor.convert(monitor, allRemainingURIs.size() / MONITOR_QUEUE_CHUNK + 1);
		Iterator<URI> iter = allRemainingURIs.iterator();
		int i = 0;
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
					System.out.println(" => queue " + candidateURI);
					buildData.queueURI(candidateURI);
					iter.remove();
				}
			}
			i++;
			if (i % MONITOR_QUEUE_CHUNK == 0)
				progress.worked(1);
		}
	}

	@Override
	protected IResourceDescription.Manager getResourceDescriptionManager(URI uri) {
		IResourceServiceProvider resourceServiceProvider = managerRegistry.getResourceServiceProvider(uri);
		if (resourceServiceProvider == null) {
			return null;
		}
		return resourceServiceProvider.getResourceDescriptionManager();
	}

}
