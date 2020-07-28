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
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.ide.server.commands.N4JSCommandService;
import org.eclipse.n4js.ide.xtext.server.ProjectStatePersisterConfig;
import org.eclipse.n4js.ide.xtext.server.ResourceChangeSet;
import org.eclipse.n4js.ide.xtext.server.build.ProjectStatePersister.ProjectState;
import org.eclipse.n4js.ide.xtext.server.build.XBuildRequest.AfterBuildListener;
import org.eclipse.n4js.ide.xtext.server.build.XBuildRequest.AfterDeleteListener;
import org.eclipse.n4js.ide.xtext.server.build.XBuildRequest.AfterValidateListener;
import org.eclipse.n4js.internal.lsp.N4JSProjectConfig;
import org.eclipse.n4js.utils.URIUtils;
import org.eclipse.n4js.xtext.server.LSPIssue;
import org.eclipse.n4js.xtext.workspace.XIProjectConfig;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.generator.OutputConfiguration;
import org.eclipse.xtext.generator.OutputConfigurationProvider;
import org.eclipse.xtext.ide.server.ILanguageServerAccess;
import org.eclipse.xtext.resource.IExternalContentSupport;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescription.Delta;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.resource.impl.ChunkedResourceDescriptions;
import org.eclipse.xtext.resource.impl.ProjectDescription;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;
import org.eclipse.xtext.service.OperationCanceledManager;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.util.IFileSystemScanner;
import org.eclipse.xtext.util.UriUtil;
import org.eclipse.xtext.validation.Issue;
import org.eclipse.xtext.workspace.ISourceFolder;
import org.eclipse.xtext.workspace.ProjectConfigAdapter;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * TODO JavaDoc
 */
@SuppressWarnings({ "restriction", "deprecation" })
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
	protected Provider<XtextResourceSet> resourceSetProvider;

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

	/** Checks whether the current action was cancelled */
	@Inject
	protected OperationCanceledManager operationCanceledManager;

	/** The map for this project's resources. */
	@Inject
	protected ProjectUriResourceMap uriResourceMap;

	/** The workspace manager */
	@Inject
	protected XWorkspaceManager workspaceManager;

	@Inject
	private ConcurrentIndex workspaceIndex;

	private XIProjectConfig projectConfig;

	private XtextResourceSet resourceSet;

	private ProjectDescription projectDescription;

	private XSource2GeneratedMapping fileMappings;

	/*
	 * TODO use same strategy for all the state
	 *
	 * Currently the uriToIssues is being maintained continuously whereas the uriToHashedFileContents is replaced.
	 */
	private Map<URI, HashedFileContent> uriToHashedFileContents = new HashMap<>();

	private final ListMultimap<URI, LSPIssue> uriToIssues = ArrayListMultimap.create();

	/** Initialize this project. */
	public void initialize(ProjectDescription _projectDescription, XIProjectConfig _projectConfig) {
		this.projectDescription = _projectDescription;
		this.projectConfig = _projectConfig;
		this.doClear();
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
	public XBuildResult doInitialBuild(IBuildRequestFactory buildRequestFactory, CancelIndicator cancelIndicator) {
		ResourceChangeSet changeSet = readProjectState();

		XBuildResult result = doBuild(
				createInitialBuildRequestFactory(buildRequestFactory),
				changeSet.getModified(),
				changeSet.getDeleted(),
				Collections.emptyList(),
				cancelIndicator);

		// clear the resource set to release memory
		clearResourceSet();

		LOG.info("Project built: " + this.projectConfig.getName());
		return result;
	}

	private void clearResourceSet() {
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

		return incrementalBuilder.build(request);
	}

	/** Deletes the contents of the output directory */
	public void doClean(AfterDeleteListener deleteListener, CancelIndicator cancelIndicator) {
		deletePersistenceFile();

		if (projectConfig instanceof N4JSProjectConfig) {
			// TODO: merge N4JSProjectConfig#canClean() to IProjectConfig
			// TODO: extract to N4JSProjectBuilder sub-type
			N4JSProjectConfig n4pc = (N4JSProjectConfig) projectConfig;
			if (!n4pc.canClean()) {
				return;
			}
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

		doClear();
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
		LSPIssue result = new LSPIssue();
		result.setMessage(message);
		result.setCode(code);
		result.setSeverity(severity);
		result.setUriToProblem(getBaseDir());

		addValidationIssue(getBaseDir(), result);
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

		ResourceDescriptionsData indexCopy = getProjectIndex().copy();
		XSource2GeneratedMapping fileMappingsCopy = getFileMappings().copy();
		request.setIndex(indexCopy);
		request.setFileMappings(fileMappingsCopy);
		updateResourceSetIndex(indexCopy);
		request.setResourceSet(resourceSet);
		request.setCancelIndicator(cancelIndicator);
		request.setBaseDir(getBaseDir());

		if (projectConfig instanceof N4JSProjectConfig) {
			// TODO: merge N4JSProjectConfig#indexOnly() to IProjectConfig
			// TODO: extract N4JSProjectBuilder
			N4JSProjectConfig n4pc = (N4JSProjectConfig) projectConfig;
			request.setIndexOnly(n4pc.indexOnly());
		}

		return request;
	}

	/** Create an empty resource set. */
	protected void updateResourceSetIndex(ResourceDescriptionsData projectIndex) {
		ChunkedResourceDescriptions resDescs = ChunkedResourceDescriptions
				.findInEmfObject(Objects.requireNonNull(resourceSet));
		for (Entry<String, ResourceDescriptionsData> entry : workspaceIndex.entries()) {
			resDescs.setContainer(entry.getKey(), entry.getValue());
		}
		resDescs.setContainer(projectDescription.getName(), projectIndex);
	}

	/** Create and configure a new resource set for this project. */
	protected XtextResourceSet createNewResourceSet(ResourceDescriptionsData newProjectIndex) {
		XtextResourceSet result = resourceSetProvider.get();
		result.setURIResourceMap(uriResourceMap);
		projectDescription.attachToEmfObject(result);
		ProjectConfigAdapter.install(result, projectConfig);
		attachWorkspaceResourceLocator(result);

		ChunkedResourceDescriptions index = workspaceIndex.toDescriptions(result);
		index.setContainer(projectDescription.getName(), newProjectIndex);
		return result;
	}

	private WorkspaceAwareResourceLocator attachWorkspaceResourceLocator(XtextResourceSet result) {
		return new WorkspaceAwareResourceLocator(result, workspaceManager);
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
	public URI getBaseDir() {
		return getProjectConfig().getPath();
	}

	/** Getter */
	public XtextResourceSet getResourceSet() {
		return resourceSet;
	}

	/** Getter */
	public ProjectDescription getProjectDescription() {
		return projectDescription;
	}

	/** Getter */
	public XIProjectConfig getProjectConfig() {
		return projectConfig;
	}

	/** Clears type index of this project. */
	protected void doClear() {
		uriToHashedFileContents.clear();
		fileMappings = new XSource2GeneratedMapping();
		setProjectIndex(new ResourceDescriptionsData(CollectionLiterals.<IResourceDescription> emptySet()));
		uriToIssues.clear();
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
	private void writeProjectState() {
		if (persisterConfig.isWriteToDisk(projectConfig)) {
			ProjectState currentState = new ProjectState(getProjectIndex(), fileMappings, uriToHashedFileContents,
					getValidationIssues());
			projectStatePersister.writeProjectState(projectConfig, currentState);
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
		doClear();

		ProjectState projectState = projectStatePersister.readProjectState(projectConfig);
		if (projectState != null) {
			for (HashedFileContent hfc : projectState.fileHashs.values()) {
				URI previouslyExistingFile = hfc.getUri();
				switch (getSourceChangeKind(hfc, projectState)) {
				case UNCHANGED: {
					uriToHashedFileContents.put(previouslyExistingFile, hfc);
					break;
				}
				case CHANGED: {
					result.getModified().add(previouslyExistingFile);
					projectState.validationIssues.removeAll(previouslyExistingFile);
					break;
				}
				case DELETED: {
					result.getDeleted().add(previouslyExistingFile);
					projectState.validationIssues.removeAll(previouslyExistingFile);
					break;
				}
				}
			}
			setProjectIndex(projectState.index);
			setFileMappings(projectState.fileMappings);
			uriToIssues.putAll(projectState.validationIssues);
		}

		Set<URI> allIndexedUris = getProjectIndex().getAllURIs();
		for (ISourceFolder srcFolder : projectConfig.getSourceFolders()) {
			List<URI> allSourceFolderUris = srcFolder.getAllResources(fileSystemScanner);
			for (URI srcFolderUri : allSourceFolderUris) {
				if (!srcFolderUri.hasTrailingPathSeparator() && !allIndexedUris.contains(srcFolderUri)) {
					if (resourceServiceProviders.getResourceServiceProvider(srcFolderUri) != null) {
						result.getModified().add(srcFolderUri);
					}
				}
			}
		}

		return result;
	}

	/** Updates the index state, file hashes and validation issues */
	private void updateProjectState(XBuildRequest request, XBuildResult result,
			Map<URI, ? extends List<? extends LSPIssue>> issues, List<URI> deletedFiles) {
		HashMap<URI, HashedFileContent> newHashedFileContents = new HashMap<>(uriToHashedFileContents);
		for (Delta delta : result.getAffectedResources()) {
			URI uri = delta.getUri();
			storeHash(newHashedFileContents, uri);
		}
		for (URI deletedFile : deletedFiles) {
			newHashedFileContents.remove(deletedFile);
		}

		setProjectIndex(result.getIndex());
		setFileMappings(result.getFileMappings());
		mergeValidationIssues(issues);
		uriToHashedFileContents = newHashedFileContents;

		if (request.isGeneratorEnabled() && !result.getAffectedResources().isEmpty()) {
			writeProjectState();
		}
	}

	private enum SourceChangeKind {
		UNCHANGED, CHANGED, DELETED
	}

	private SourceChangeKind getSourceChangeKind(HashedFileContent hfc, ProjectState projectState) {
		URI sourceUri = hfc.getUri();
		long loadedHash = hfc.getHash();

		HashedFileContent newHash = doHash(sourceUri);
		if (newHash == null) {
			return SourceChangeKind.DELETED;
		}

		if (loadedHash != newHash.getHash()) {
			return SourceChangeKind.CHANGED;
		}

		XSource2GeneratedMapping sourceFileMappings = projectState.fileMappings;
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

	private void storeHash(HashMap<URI, HashedFileContent> newFileContents, URI uri) {
		HashedFileContent generatedTargetContent = doHash(uri);
		if (generatedTargetContent != null) {
			newFileContents.put(uri, generatedTargetContent);
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
		return ImmutableList.copyOf(uriToIssues.get(uri));
	}

	/** @return the validation issues as an unmodifiable map. */
	private ImmutableListMultimap<URI, LSPIssue> getValidationIssues() {
		return ImmutableListMultimap.copyOf(uriToIssues);
	}

	private void mergeValidationIssues(Map<URI, ? extends List<? extends LSPIssue>> issues) {
		issues.forEach(this.uriToIssues::replaceValues);
	}

	private void addValidationIssue(URI uri, LSPIssue issue) {
		this.uriToIssues.put(uri, issue);
	}

	/** Getter. */
	private ResourceDescriptionsData getProjectIndex() {
		return workspaceIndex.getProjectIndex(projectConfig.getName());
	}

	/** Setter. */
	private void setProjectIndex(ResourceDescriptionsData index) {
		this.workspaceIndex.setProjectIndex(projectConfig.getName(), index);
	}

	/** Getter. */
	private XSource2GeneratedMapping getFileMappings() {
		return fileMappings;
	}

	/** Setter. */
	private void setFileMappings(XSource2GeneratedMapping fileMappings) {
		this.fileMappings = fileMappings;
	}

	boolean isSourceFile(URI uri) {
		String fileName = uri.lastSegment();
		if (fileName.equals(projectStatePersister.getPersistedFileName())) {
			return false;
		}
		ISourceFolder sourceFolder = projectConfig.findSourceFolderContaining(uri);
		if (sourceFolder != null) {
			return true;
		}
		return false;
	}

}
