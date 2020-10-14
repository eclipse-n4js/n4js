/**
 * Copyright (c) 2016, 2017 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.n4js.ide.xtext.server.build;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.ide.server.commands.N4JSCommandService;
import org.eclipse.n4js.ide.xtext.server.ProjectStatePersisterConfig;
import org.eclipse.n4js.ide.xtext.server.ResourceChangeSet;
import org.eclipse.n4js.ide.xtext.server.XIProjectDescriptionFactory;
import org.eclipse.n4js.ide.xtext.server.build.XBuildRequest.AfterBuildListener;
import org.eclipse.n4js.ide.xtext.server.build.XBuildRequest.AfterDeleteListener;
import org.eclipse.n4js.ide.xtext.server.build.XBuildRequest.AfterValidateListener;
import org.eclipse.n4js.ide.xtext.server.issues.PublishingIssueAcceptor;
import org.eclipse.n4js.utils.URIUtils;
import org.eclipse.n4js.utils.UtilN4;
import org.eclipse.n4js.xtext.server.LSPIssue;
import org.eclipse.n4js.xtext.workspace.ProjectConfigSnapshot;
import org.eclipse.n4js.xtext.workspace.SourceFolderScanner;
import org.eclipse.n4js.xtext.workspace.SourceFolderSnapshot;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.generator.OutputConfiguration;
import org.eclipse.xtext.generator.OutputConfigurationProvider;
import org.eclipse.xtext.ide.server.ILanguageServerAccess;
import org.eclipse.xtext.resource.IExternalContentSupport;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescription.Delta;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.resource.impl.ChunkedResourceDescriptions;
import org.eclipse.xtext.resource.impl.DefaultResourceDescriptionDelta;
import org.eclipse.xtext.resource.impl.ProjectDescription;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;
import org.eclipse.xtext.service.OperationCanceledManager;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.util.IFileSystemScanner;
import org.eclipse.xtext.util.UriUtil;
import org.eclipse.xtext.validation.Issue;

import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * TODO JavaDoc
 */
@SuppressWarnings({ "deprecation" })
public class ProjectBuilder {
	private static final Logger LOG = LogManager.getLogger(ProjectBuilder.class);

	/** Used to filter the traversed resources */
	@Inject
	protected IResourceServiceProvider.Registry resourceServiceProviders;

	/** The builder. */
	@Inject
	protected XIncrementalBuilder incrementalBuilder;

	/** Creates a new resource set. */
	@Inject
	protected Provider<WorkspaceAwareResourceSet> resourceSetProvider;

	/** Scans the file system for source files contained in a {@link SourceFolderSnapshot source folder}. */
	@Inject
	protected SourceFolderScanner sourceFolderScanner;

	/** Scans the file system. */
	@Inject
	protected IFileSystemScanner fileSystemScanner;

	/** External content support for the resource set. */
	@Inject
	protected IExternalContentSupport externalContentSupport;

	/** Reads and writes the type index from/to disk */
	@Inject
	protected ProjectStatePersister projectStatePersister;

	/** Configuration for the project state persister */
	@Inject
	protected ProjectStatePersisterConfig persisterConfig;

	/** Holds information about the output settings, e.g. the output directory */
	@Inject
	protected OutputConfigurationProvider outputConfigProvider;

	/** Factory for creating the {@link ProjectDescription} attached to this project builder's resource set. */
	@Inject
	protected XIProjectDescriptionFactory projectDescriptionFactory;

	/** Checks whether the current action was cancelled */
	@Inject
	protected OperationCanceledManager operationCanceledManager;

	/** The map for this project's resources. */
	@Inject
	protected ProjectUriResourceMap uriResourceMap;

	/** The workspace manager */
	@Inject
	protected XWorkspaceManager workspaceManager;

	/** Used to publish the issues reported via {@link #reportProjectIssue(String, String, Severity)} */
	@Inject
	protected PublishingIssueAcceptor issuePublisher;

	@Inject
	private ConcurrentIndex workspaceIndex;

	private ProjectConfigSnapshot projectConfig;

	private WorkspaceAwareResourceSet resourceSet;

	private final AtomicReference<ImmutableProjectState> projectStateSnapshot = new AtomicReference<>(
			ImmutableProjectState.empty());

	/** Initialize this project. */
	@SuppressWarnings("hiding")
	public void initialize(ProjectConfigSnapshot projectConfig) {
		this.projectConfig = projectConfig;
		this.doClearWithoutNotification();
		this.resourceSet = createNewResourceSet(getProjectIndex());
	}

	/**
	 * Initial build reads the project state and resolves changes. Generate output files.
	 * <p>
	 * This method assumes that it is only invoked in situations when the client does not have any diagnostics stored,
	 * e.g. directly after invoking {@link #doClean(AfterDeleteListener, CancelIndicator)}. Therefore no
	 * 'publishDiagnostics' events with an empty list of diagnostics need to be sent to the client in order to remove
	 * obsolete diagnostics.
	 * <p>
	 * NOTE: this is not only invoked shortly after server startup but also at various other occasions, for example
	 * <ul>
	 * <li>when the client executes the {@link N4JSCommandService#rebuild(ILanguageServerAccess, CancelIndicator)
	 * rebuild command},
	 * <li>when the workspace folder is changed in VS Code.
	 * </ul>
	 */
	public XBuildResult doInitialBuild(IBuildRequestFactory buildRequestFactory, List<Delta> externalDeltas) {
		ResourceChangeSet changeSet = readProjectState();

		XBuildResult result = doBuild(
				createInitialBuildRequestFactory(buildRequestFactory),
				changeSet.getDirty(),
				changeSet.getDeleted(),
				UtilN4.concat(externalDeltas, changeSet.getAdditionalExternalDeltas()),
				CancelIndicator.NullImpl);

		// clear the resource set to release memory
		clearResourceSet();

		LOG.info("Project built: " + this.projectConfig.getName());
		return result;
	}

	/**
	 * Clear all resources and their contents from this project builder's resource set.
	 */
	public void clearResourceSet() {
		boolean wasDeliver = resourceSet.eDeliver();
		try {
			resourceSet.eSetDeliver(false);
			resourceSet.getResources().clear();
		} finally {
			resourceSet.eSetDeliver(wasDeliver);
		}
	}

	class ProjectStateUpdater implements AfterValidateListener, AfterDeleteListener, AfterBuildListener {
		final Map<URI, ImmutableList<? extends LSPIssue>> newValidationIssues = new HashMap<>();
		final List<URI> deleted = new ArrayList<>();

		@Override
		public void afterValidate(URI source, List<? extends Issue> issues) {
			newValidationIssues.put(source, ImmutableList.copyOf(LSPIssue.cast(issues)));
		}

		@Override
		public void afterDelete(URI file) {
			deleted.add(file);
		}

		@Override
		public void afterBuild(XBuildRequest request, XBuildResult buildResult) {
			updateProjectState(request, buildResult, newValidationIssues, deleted);

		}

		public void attachTo(XBuildRequest request) {
			request.addAfterValidateListener(this);
			request.addAfterDeleteListener(this);
			request.addAfterBuildListener(this);
		}
	}

	static class ProjectStateUpdatingBuildRequestFactory implements IBuildRequestFactory {

		private final IBuildRequestFactory delegate;
		private final ProjectStateUpdater updater;

		ProjectStateUpdatingBuildRequestFactory(IBuildRequestFactory delegate, ProjectStateUpdater updater) {
			this.delegate = delegate;
			this.updater = updater;
		}

		@Override
		public XBuildRequest getBuildRequest(String projectName, Set<URI> changedFiles, Set<URI> deletedFiles,
				List<Delta> externalDeltas) {
			XBuildRequest request = delegate.getBuildRequest(projectName, changedFiles, deletedFiles, externalDeltas);
			updater.attachTo(request);
			return request;
		}

	}

	/**
	 * Create a wrapper around the given build request factory that yields a build request which will update the stored
	 * project state via {@link ProjectBuilder#updateProjectState} and publish all previously known issues along with
	 * the newly computed issues.
	 */
	protected IBuildRequestFactory createInitialBuildRequestFactory(IBuildRequestFactory base) {
		ProjectStateUpdater updater = new ProjectStateUpdater() {
			@Override
			public void afterBuild(XBuildRequest req, XBuildResult buildResult) {
				super.afterBuild(req, buildResult);

				// now submit all validation issues, that have not been submitted yet
				getValidationIssues().asMap().forEach((uri, issues) -> {
					if (!newValidationIssues.containsKey(uri)) {
						req.afterValidate(uri, ImmutableList.copyOf(issues));
					}
				});
			}
		};
		return new ProjectStateUpdatingBuildRequestFactory(base, updater);
	}

	/**
	 * Scans the files system and returns URIs of source files that were added, changed, removed since this builder's
	 * current {@link #projectStateSnapshot project state} was created. Content changes in source files are detected by
	 * way of hash comparison.
	 */
	public ResourceChangeSet scanForSourceFileChanges() {
		ResourceChangeSet result = new ResourceChangeSet();
		ImmutableProjectState oldProjectState = this.projectStateSnapshot.get();
		Map<URI, HashedFileContent> oldHashes = oldProjectState.getFileHashes();
		Set<URI> oldSourceFilesURIs = oldProjectState.internalGetResourceDescriptions().getAllURIs();
		Set<URI> existingSourceFileURIs = scanForSourceFiles();
		for (URI currURI : Sets.union(oldSourceFilesURIs, existingSourceFileURIs)) {
			boolean isOld = oldSourceFilesURIs.contains(currURI);
			boolean isNew = existingSourceFileURIs.contains(currURI);
			if (!isOld && isNew) {
				// added
				result.getDirty().add(currURI);
			} else if (isOld && !isNew) {
				// removed
				result.getDeleted().add(currURI);
			} else if (isOld && isNew) {
				// compare hash ...
				HashedFileContent hfc = oldHashes.get(currURI);
				if (hfc != null) {
					switch (getSourceChangeKind(hfc, oldProjectState)) {
					case UNCHANGED: {
						break;
					}
					case CHANGED: {
						result.getDirty().add(currURI);
						break;
					}
					case DELETED: {
						result.getDeleted().add(currURI);
						break;
					}
					}
				} else {
					LOG.warn("inconsistency in project state: URI is indexed but not hashed: " + currURI);
					result.getDirty().add(currURI);
				}
			}
		}
		return result;
	}

	/** Build increments of this project. */
	public XBuildResult doIncrementalBuild(
			IBuildRequestFactory buildRequestFactory,
			Set<URI> dirtyFiles,
			Set<URI> deletedFiles,
			List<IResourceDescription.Delta> externalDeltas,
			CancelIndicator cancelIndicator) {

		return doBuild(createIncrementalBuildFactory(buildRequestFactory), dirtyFiles, deletedFiles, externalDeltas,
				cancelIndicator);
	}

	/**
	 * Create a wrapper around the given build request factory that yields a build request which will update the stored
	 * project state via {@link ProjectBuilder#updateProjectState}.
	 */
	protected ProjectStateUpdatingBuildRequestFactory createIncrementalBuildFactory(
			IBuildRequestFactory buildRequestFactory) {
		return new ProjectStateUpdatingBuildRequestFactory(buildRequestFactory, new ProjectStateUpdater());
	}

	/** Build this project according to the request provided by the given buildRequestFactory. */
	protected XBuildResult doBuild(
			IBuildRequestFactory buildRequestFactory,
			Set<URI> dirtyFiles,
			Set<URI> deletedFiles,
			List<IResourceDescription.Delta> externalDeltas,
			CancelIndicator cancelIndicator) {

		URI persistenceFile = getPersistenceFile();
		dirtyFiles.remove(persistenceFile);
		deletedFiles.remove(persistenceFile);

		XBuildRequest request = newBuildRequest(
				buildRequestFactory,
				dirtyFiles,
				deletedFiles,
				externalDeltas,
				cancelIndicator);
		resourceSet = request.getResourceSet();

		try {
			return incrementalBuilder.build(request);
		} catch (Throwable t) {
			updateResourceSetIndex(getProjectIndex());
			throw t;
		}
	}

	/** Deletes the contents of the output directory */
	public void doClean(AfterDeleteListener deleteListener, CancelIndicator cancelIndicator) {
		deletePersistenceFile();
		doClearWithNotification();

		if (projectConfig.indexOnly()) {
			return;
		}

		for (File outputDirectory : getOutputDirectories()) {
			File[] childFiles = outputDirectory.listFiles();
			if (childFiles != null) {
				for (int i = 0; i < childFiles.length; i++) {
					operationCanceledManager.checkCanceled(cancelIndicator);
					deleteFileOrFolder(deleteListener, childFiles[i]);
				}
			}
		}
	}

	/** @return list of output directories of this project */
	public List<File> getOutputDirectories() {
		List<File> outputDirs = new ArrayList<>();
		Set<OutputConfiguration> outputConfigurations = outputConfigProvider.getOutputConfigurations(resourceSet);
		URI projectBaseUri = projectConfig.getPath();
		for (OutputConfiguration outputConf : outputConfigurations) {
			for (String outputDirName : outputConf.getOutputDirectories()) {
				URI outputUri = projectBaseUri.appendSegment(outputDirName);
				File outputDirectory = URIUtils.toFile(outputUri);
				outputDirs.add(outputDirectory);
			}
		}
		return outputDirs;
	}

	/** Deletes the given file recursively */
	protected void deleteFileOrFolder(AfterDeleteListener deleteListener, File file) {
		if (file.isDirectory()) {
			File[] childFildes = file.listFiles();
			for (int i = 0; i < childFildes.length; i++) {
				deleteFileOrFolder(deleteListener, childFildes[i]);
			}
		}
		boolean wasFile = file.isFile();
		file.delete();
		if (wasFile) {
			URI fileUri = URI.createFileURI(file.getAbsolutePath());
			deleteListener.afterDelete(fileUri);
		}
	}

	/** Report an issue. */
	public void reportProjectIssue(String message, String code, Severity severity) {
		URI uri = getBaseDir();
		LSPIssue issue = new LSPIssue();
		issue.setMessage(message);
		issue.setCode(code);
		issue.setSeverity(severity);
		issue.setUriToProblem(uri);

		ImmutableProjectState updatedState = projectStateSnapshot.updateAndGet(snapshot -> {
			ImmutableListMultimap.Builder<URI, LSPIssue> builder = ImmutableListMultimap.builder();
			builder.putAll(snapshot.getValidationIssues());
			builder.put(uri, issue);
			return ImmutableProjectState.withoutCopy(snapshot.internalGetResourceDescriptions(),
					snapshot.getFileMappings(), snapshot.getFileHashes(), builder.build(), snapshot.getDependencies());
		});
		issuePublisher.accept(uri, updatedState.getValidationIssues().get(uri));
	}

	/** Creates a new build request for this project. */
	protected XBuildRequest newBuildRequest(
			IBuildRequestFactory buildRequestFactory,
			Set<URI> changedFiles,
			Set<URI> deletedFiles,
			List<IResourceDescription.Delta> externalDeltas,
			CancelIndicator cancelIndicator) {

		XBuildRequest request = buildRequestFactory.getBuildRequest(projectConfig.getName(), changedFiles, deletedFiles,
				externalDeltas);

		ImmutableProjectState currentProjectState = this.projectStateSnapshot.get();

		ResourceDescriptionsData indexCopy = currentProjectState.internalGetResourceDescriptions().copy();
		XSource2GeneratedMapping fileMappingsCopy = currentProjectState.getFileMappings().copy();

		request.setIndex(indexCopy);
		request.setFileMappings(fileMappingsCopy);
		updateResourceSetIndex(indexCopy);
		request.setResourceSet(resourceSet);
		request.setCancelIndicator(cancelIndicator);
		request.setBaseDir(getBaseDir());
		request.setIndexOnly(projectConfig.indexOnly());

		return request;
	}

	/** Update the index information that is attached to this project's resource set */
	protected void updateResourceSetIndex(ResourceDescriptionsData newProjectIndex) {
		ChunkedResourceDescriptions.removeFromEmfObject(resourceSet);
		ChunkedResourceDescriptions resDescs = workspaceIndex.toDescriptions(resourceSet);
		resDescs.setContainer(projectConfig.getName(), newProjectIndex);
	}

	/** Update the {@link ProjectDescription} instance that is attached to this project's resource set. */
	protected void updateResourceSetProjectDescription() {
		ProjectDescription.removeFromEmfObject(resourceSet);
		ProjectDescription newPD = projectDescriptionFactory.getProjectDescription(projectConfig);
		newPD.attachToEmfObject(resourceSet);
	}

	/** Create and configure a new resource set for this project. */
	protected WorkspaceAwareResourceSet createNewResourceSet(ResourceDescriptionsData newProjectIndex) {
		WorkspaceAwareResourceSet result = resourceSetProvider.get();
		result.setWorkspaceManager(workspaceManager);
		result.setURIResourceMap(uriResourceMap);
		ProjectDescription projectDescription = projectDescriptionFactory.getProjectDescription(projectConfig);
		projectDescription.attachToEmfObject(result);

		ChunkedResourceDescriptions index = workspaceIndex.toDescriptions(result);
		index.setContainer(projectConfig.getName(), newProjectIndex);

		result.eAdapters().add(new AdapterImpl() {
			@Override
			public void notifyChanged(Notification msg) {
				Object newValue = msg.getNewValue();
				if (msg.getEventType() == Notification.ADD
						&& msg.getFeatureID(ResourceSet.class) == ResourceSet.RESOURCE_SET__RESOURCES
						&& newValue instanceof Resource) {
					onResourceAdded((Resource) newValue);
				}
			}
		});

		return result;
	}

	/**
	 * Invoked after a new resource was added to this builder's resource set.
	 *
	 * @param newResource
	 *            the resource that was added; never <code>null</code>.
	 */
	protected void onResourceAdded(Resource newResource) {
		URI newResourceURI = newResource.getURI();
		URI projectPath = projectConfig != null ? UriUtil.toFolderURI(projectConfig.getPath()) : null;
		if (newResourceURI != null && projectPath != null && !UriUtil.isPrefixOf(projectPath, newResourceURI)) {
			LOG.error("resource created in incorrect project builder (project path: " + projectPath + "; resource URI: "
					+ newResourceURI + ")");
		}
	}

	/** Get the resource with the given URI. */
	public Resource getResource(URI uri) {
		Resource resource = resourceSet.getResource(uri, true);
		resource.getContents();
		return resource;
	}

	/** @return all resource descriptions that start with the given prefix */
	public List<URI> findResourcesStartingWithPrefix(URI prefix) {
		ResourceDescriptionsData resourceDescriptionsData = getProjectIndex();

		// TODO: Moving this into ResourceDescriptionsData and using a sorted Map could increase performance
		List<URI> uris = new ArrayList<>();
		for (URI uri : resourceDescriptionsData.getAllURIs()) {
			if (UriUtil.isPrefixOf(prefix, uri)) {
				uris.add(uri);
			}
		}
		return uris;
	}

	/** Getter */
	public String getName() {
		return getProjectConfig().getName();
	}

	/** Getter */
	public URI getBaseDir() {
		return getProjectConfig().getPath();
	}

	/** Getter */
	public WorkspaceAwareResourceSet getResourceSet() {
		return resourceSet;
	}

	/** Getter */
	public ProjectConfigSnapshot getProjectConfig() {
		return projectConfig;
	}

	/** Setter */
	public void setProjectConfig(ProjectConfigSnapshot newProjectConfig) {
		boolean depsHaveChanged = projectConfig == null
				|| !projectConfig.getDependencies().equals(newProjectConfig.getDependencies());

		projectConfig = newProjectConfig;

		if (depsHaveChanged) {
			onDependenciesChanged();
		}
	}

	/** Invoked whenever the dependencies of this builder's project change. */
	protected void onDependenciesChanged() {
		// since the ProjectDescription instance attached to this#resourceSet caches the project dependencies, we have
		// to update those whenever the dependencies change:
		updateResourceSetProjectDescription();
	}

	/** Same as {@link #doClearWithoutNotification()}, but also sends corresponding notifications to the LSP client. */
	protected void doClearWithNotification() {
		// send 'publishDiagnostics' notifications to the LSP client for removing all existing issues in this project
		ImmutableProjectState projectState = projectStateSnapshot.get();
		ImmutableSet<URI> urisWithIssues = projectState.getValidationIssues().keySet();
		for (URI uri : urisWithIssues) {
			issuePublisher.accept(uri, Collections.emptyList());
		}
		// actually clear internal state
		doClearWithoutNotification();
	}

	/**
	 * Clears the {@link #projectStateSnapshot project state}, type index, and resource set of this project.
	 * <p>
	 * Does <em>not</em> send any corresponding notifications to the LSP client.
	 */
	protected void doClearWithoutNotification() {
		ImmutableProjectState newState = ImmutableProjectState.empty();
		setProjectIndex(newState.internalGetResourceDescriptions());
		this.projectStateSnapshot.set(newState);
		if (resourceSet != null) {
			uriResourceMap.clear();
			clearResourceSet();
		}
	}

	/** Deletes the persistence file on disk */
	private void deletePersistenceFile() {
		URI persistenceFileURI = getPersistenceFile();
		File persistenceFile = URIUtils.toFile(persistenceFileURI);
		if (persistenceFile.isFile()) {
			persistenceFile.delete();
		}
	}

	/** Persists the project state to disk */
	private void writeProjectState(ImmutableProjectState state) {
		if (persisterConfig.isWriteToDisk(projectConfig)) {
			projectStatePersister.writeProjectState(projectConfig, state);
		}
	}

	/**
	 * Reads the persisted project state from disk
	 *
	 * @return set of all source URIs with modified contents
	 */
	private ResourceChangeSet readProjectState() {
		if (persisterConfig.isDeleteState(projectConfig)) {
			deletePersistenceFile();
		}

		ResourceChangeSet result = new ResourceChangeSet();
		doClearWithoutNotification(); // sets this#projectStateSnapshot to an empty project state

		boolean fullBuildRequired = false;
		ImmutableProjectState projectState = projectStatePersister.readProjectState(projectConfig);
		if (projectState != null) {
			fullBuildRequired |= handleProjectAdditionRemovalSinceProjectStateWasComputed(result, projectState);
			setProjectIndex(projectState.internalGetResourceDescriptions());
			this.projectStateSnapshot.set(projectState);
		}

		if (fullBuildRequired) {
			result.getDirty().addAll(scanForSourceFiles());
		} else {
			result.addAll(scanForSourceFileChanges());
		}

		return result;
	}

	/**
	 * Scans the file system for source files, going over all source folders.
	 */
	private Set<URI> scanForSourceFiles() {
		Set<URI> result = new HashSet<>();
		for (SourceFolderSnapshot srcFolder : projectConfig.getSourceFolders()) {
			List<URI> allSourceFileUris = sourceFolderScanner.findAllSourceFiles(srcFolder, fileSystemScanner);
			for (URI srcFileUri : allSourceFileUris) {
				if (!srcFileUri.hasTrailingPathSeparator()) {
					if (resourceServiceProviders.getResourceServiceProvider(srcFileUri) != null) {
						result.add(srcFileUri);
					}
				}
			}
		}
		return result;
	}

	/** @return <code>true</code> iff full build of this builder's project is required due to project changes. */
	protected boolean handleProjectAdditionRemovalSinceProjectStateWasComputed(ResourceChangeSet result,
			ImmutableProjectState projectState) {

		Set<String> oldExistingDeps = FluentIterable.from(projectState.getDependencies().entrySet())
				.filter(Entry::getValue) // value tells whether project did exist when project state was computed
				.transform(Entry::getKey).toSet();
		Set<String> newExistingDeps = FluentIterable.from(projectConfig.getDependencies())
				.filter(depName -> workspaceIndex.getProjectIndex(depName) != null).toSet();
		if (!Sets.difference(oldExistingDeps, newExistingDeps).isEmpty()) {
			// projects among the dependencies of this builder's project were removed since 'projectState' was computed
			// WHAT WE WOULD LIKE TO DO: treat all resources that existed before in those removed projects as deleted,
			// by creating additional external deltas for them
			// BUT: this is not possible, because we cannot create IResourceDescriptions/Deltas for them (without
			// persisting far more information in the project state files, which is not feasible)
			// -> therefore we simply perform a full build of this builder's project
			return true;
		}
		for (String depName : Sets.difference(newExistingDeps, oldExistingDeps)) {
			// project 'depName' was added since 'projectState' was persisted
			// -> treat all resources that exist now in that added project as newly created, by creating additional
			// external deltas for them
			ResourceDescriptionsData addedDepIndex = workspaceIndex.getProjectIndex(depName);
			if (addedDepIndex != null) {
				FluentIterable.from(addedDepIndex.getAllResourceDescriptions())
						.transform(desc -> new DefaultResourceDescriptionDelta(null, desc))
						.copyInto(result.getAdditionalExternalDeltas());
			}
		}
		return false;
	}

	/** Updates the index state, file hashes and validation issues */
	private void updateProjectState(
			XBuildRequest request,
			XBuildResult result,
			Map<URI, ? extends List<? extends LSPIssue>> issuesFromIncrementalBuild,
			List<URI> deletedFiles) {

		ImmutableMap.Builder<String, Boolean> dependencies = ImmutableMap.builder();
		for (String currDepName : projectConfig.getDependencies()) {
			boolean currDepExists = workspaceIndex.getProjectIndex(currDepName) != null;
			dependencies.put(currDepName, currDepExists);
		}

		ImmutableProjectState newState = this.projectStateSnapshot.updateAndGet(snapshot -> {
			Map<URI, HashedFileContent> newHashedFileContents = new HashMap<>(snapshot.getFileHashes());
			// TODO where do we update the generated file hashes?
			for (Delta delta : result.getAffectedResources()) {
				URI uri = delta.getUri();
				if (delta.getNew() != null) {
					storeHash(newHashedFileContents, uri);
				} else {
					newHashedFileContents.remove(uri);
				}
			}
			for (URI deletedFile : deletedFiles) {
				newHashedFileContents.remove(deletedFile);
			}

			ImmutableListMultimap.Builder<URI, LSPIssue> newIssues = ImmutableListMultimap.builder();
			snapshot.getValidationIssues().asMap().forEach((uri, oldIssues) -> {
				if (!issuesFromIncrementalBuild.containsKey(uri)) {
					newIssues.putAll(uri, oldIssues);
				}
			});
			issuesFromIncrementalBuild.forEach(newIssues::putAll);
			return ImmutableProjectState.withoutCopy(result.getIndex(), result.getFileMappings(),
					ImmutableMap.copyOf(newHashedFileContents), newIssues.build(), dependencies.build());
		});
		setProjectIndex(newState.internalGetResourceDescriptions());

		if (request.isGeneratorEnabled() && !result.getAffectedResources().isEmpty()) {
			writeProjectState(newState);
		}
	}

	private enum SourceChangeKind {
		UNCHANGED, CHANGED, DELETED
	}

	private SourceChangeKind getSourceChangeKind(HashedFileContent hfc, ImmutableProjectState projectState) {
		URI sourceUri = hfc.getUri();
		long loadedHash = hfc.getHash();

		HashedFileContent newHash = doHash(sourceUri);
		if (newHash == null) {
			return SourceChangeKind.DELETED;
		}

		if (loadedHash != newHash.getHash()) {
			return SourceChangeKind.CHANGED;
		}

		XSource2GeneratedMapping sourceFileMappings = projectState.getFileMappings();
		List<URI> allPrevGenerated = sourceFileMappings.getGenerated(sourceUri);
		for (URI prevGenerated : allPrevGenerated) {
			File prevGeneratedFile = new File(prevGenerated.path());
			if (!prevGeneratedFile.isFile()) {
				return SourceChangeKind.CHANGED;
			}
		}
		return SourceChangeKind.UNCHANGED;
	}

	private HashedFileContent doHash(URI uri) {
		try {
			File srcFile = new File(uri.path());
			if (!srcFile.isFile()) {
				return null;
			}
			HashedFileContent generatedTargetContent = new HashedFileContent(uri, srcFile);
			return generatedTargetContent;
		} catch (IOException e) {
			return null;
		}
	}

	private void storeHash(Map<URI, HashedFileContent> newFileContents, URI uri) {
		HashedFileContent fileHash = doHash(uri);
		if (fileHash != null) {
			newFileContents.put(uri, fileHash);
		} else {
			newFileContents.remove(uri);
		}
	}

	/** @return the file the project state is stored to */
	private URI getPersistenceFile() {
		URI persistanceFile = projectStatePersister.getFileName(projectConfig);
		return persistanceFile;
	}

	/**
	 * Returns the known issues for the given resource.
	 */
	public ImmutableList<? extends LSPIssue> getValidationIssues(URI uri) {
		return getValidationIssues().get(uri);
	}

	/** @return the validation issues as an unmodifiable map. */
	private ImmutableListMultimap<URI, LSPIssue> getValidationIssues() {
		return projectStateSnapshot.get().getValidationIssues();
	}

	/** Getter. */
	private ResourceDescriptionsData getProjectIndex() {
		return workspaceIndex.getProjectIndex(projectConfig.getName());
	}

	/** Setter. */
	private void setProjectIndex(ResourceDescriptionsData index) {
		this.workspaceIndex.setProjectIndex(projectConfig.getName(), index);
	}
}
