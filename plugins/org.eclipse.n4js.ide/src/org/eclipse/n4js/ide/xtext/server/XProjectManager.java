/**
 * Copyright (c) 2016, 2017 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.n4js.ide.xtext.server;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.ide.server.commands.N4JSCommandService;
import org.eclipse.n4js.ide.xtext.server.build.XBuildRequest;
import org.eclipse.n4js.ide.xtext.server.build.XBuildResult;
import org.eclipse.n4js.ide.xtext.server.build.XIncrementalBuilder;
import org.eclipse.n4js.ide.xtext.server.build.XIndexState;
import org.eclipse.n4js.ide.xtext.server.build.XSource2GeneratedMapping;
import org.eclipse.n4js.internal.lsp.N4JSProjectConfig;
import org.eclipse.n4js.utils.URIUtils;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.generator.OutputConfiguration;
import org.eclipse.xtext.generator.OutputConfigurationProvider;
import org.eclipse.xtext.ide.server.ILanguageServerAccess;
import org.eclipse.xtext.resource.IExternalContentSupport;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.resource.impl.ChunkedResourceDescriptions;
import org.eclipse.xtext.resource.impl.ProjectDescription;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;
import org.eclipse.xtext.service.OperationCanceledManager;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.util.IFileSystemScanner;
import org.eclipse.xtext.util.UriUtil;
import org.eclipse.xtext.validation.Issue;
import org.eclipse.xtext.workspace.IProjectConfig;
import org.eclipse.xtext.workspace.ISourceFolder;
import org.eclipse.xtext.workspace.ProjectConfigAdapter;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * @author Sven Efftinge - Initial contribution and API
 * @since 2.11
 */
@SuppressWarnings("restriction")
public class XProjectManager {
	private static final Logger LOG = LogManager.getLogger(XProjectManager.class);

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

	/** Creates build requests */
	@Inject
	protected DefaultBuildRequestFactory buildRequestFactory;

	/** Publishes issues to lsp client */
	@Inject
	protected IssueAcceptor issueAcceptor;

	/** Holds index, hashes and issue information */
	@Inject
	protected ProjectStateHolder projectStateHolder;

	/** Holds information about the output settings, e.g. the output directory */
	@Inject
	protected OutputConfigurationProvider outputConfigProvider;

	/** Checks whether the current action was cancelled */
	@Inject
	protected OperationCanceledManager operationCanceledManager;

	/**
	 * The map for this project's resource set.
	 */
	@Inject
	protected ProjectUriResourceMap uriResourceMap;

	/** The workspace manager */
	@Inject
	protected XWorkspaceManager workspaceManager;

	private Provider<Map<String, ResourceDescriptionsData>> indexProvider;

	private IExternalContentSupport.IExternalContentProvider openedDocumentsContentProvider;

	private XtextResourceSet resourceSet;

	private ProjectDescription projectDescription;

	private IProjectConfig projectConfig;

	/** Initialize this project. */
	@SuppressWarnings("hiding")
	public void initialize(ProjectDescription description, IProjectConfig projectConfig,
			IExternalContentSupport.IExternalContentProvider openedDocumentsContentProvider,
			Provider<Map<String, ResourceDescriptionsData>> indexProvider) {

		this.projectDescription = description;
		this.projectConfig = projectConfig;
		this.openedDocumentsContentProvider = openedDocumentsContentProvider;
		this.indexProvider = indexProvider;
		this.resourceSet = createNewResourceSet(new XIndexState().getResourceDescriptions());
	}

	/**
	 * Initial build reads the project state and resolves changes. Generate output files.
	 * <p>
	 * This method assumes that it is only invoked in situations when the client does not have any diagnostics stored,
	 * e.g. directly after invoking {@link #doClean(CancelIndicator)}, and that therefore no 'publishDiagnostics' events
	 * with an empty list of diagnostics need to be sent to the client in order to remove obsolete diagnostics.
	 * <p>
	 * NOTE: this is not only invoked shortly after server startup but also at various other occasions, for example
	 * <ul>
	 * <li>when the client executes the {@link N4JSCommandService#rebuild(ILanguageServerAccess, CancelIndicator)
	 * rebuild command},
	 * <li>when the workspace folder is changed in VS Code.
	 * </ul>
	 */
	public XBuildResult doInitialBuild(CancelIndicator cancelIndicator) {
		ResourceChangeSet changeSet = projectStateHolder.readProjectState(projectConfig);
		XBuildResult result = doBuild(
				changeSet.getModified(), changeSet.getDeleted(), Collections.emptyList(), false, true, cancelIndicator);

		// send issues to client
		// (below code won't send empty 'publishDiagnostics' events for resources without validation issues, see API doc
		// of this method for details)
		Multimap<URI, Issue> validationIssues = projectStateHolder.getValidationIssues();
		for (URI location : validationIssues.keys()) {
			Collection<Issue> issues = validationIssues.get(location);
			issueAcceptor.publishDiagnostics(location, issues);
		}

		// clear the resource set to release memory
		boolean wasDeliver = resourceSet.eDeliver();
		try {
			resourceSet.eSetDeliver(false);
			resourceSet.getResources().clear();
		} finally {
			resourceSet.eSetDeliver(wasDeliver);
		}

		LOG.info("Project built: " + this.projectConfig.getName());
		return result;
	}

	/** Build increments of this project. */
	public XBuildResult doIncrementalBuild(Set<URI> dirtyFiles, Set<URI> deletedFiles,
			List<IResourceDescription.Delta> externalDeltas, boolean doGenerate, CancelIndicator cancelIndicator) {

		return doBuild(dirtyFiles, deletedFiles, externalDeltas, true, doGenerate, cancelIndicator);
	}

	/** Build this project. */
	protected XBuildResult doBuild(Set<URI> dirtyFiles, Set<URI> deletedFiles,
			List<IResourceDescription.Delta> externalDeltas, boolean propagateIssues, boolean doGenerate,
			CancelIndicator cancelIndicator) {

		URI persistenceFile = projectStateHolder.getPersistenceFile(projectConfig);
		dirtyFiles.remove(persistenceFile);
		deletedFiles.remove(persistenceFile);

		XBuildRequest request = newBuildRequest(dirtyFiles, deletedFiles, externalDeltas, propagateIssues, doGenerate,
				cancelIndicator);
		resourceSet = request.getResourceSet(); // resourceSet is already used during the build via #getResource(URI)

		XBuildResult result = incrementalBuilder.build(request);

		projectStateHolder.updateProjectState(request, result, projectConfig);

		ResourceDescriptionsData resourceDescriptions = projectStateHolder.getIndexState().getResourceDescriptions();

		Map<String, ResourceDescriptionsData> concurrentMap = indexProvider.get();
		concurrentMap.put(projectDescription.getName(), resourceDescriptions);
		return result;
	}

	/** Deletes the contents of the output directory */
	public void doClean(CancelIndicator cancelIndicator) {
		projectStateHolder.deletePersistenceFile(projectConfig);

		if (projectConfig instanceof N4JSProjectConfig) {
			// TODO: merge N4JSProjectConfig#canClean() to IProjectConfig
			N4JSProjectConfig n4pc = (N4JSProjectConfig) projectConfig;
			if (!n4pc.canClean()) {
				return;
			}
		}

		XBuildRequest request = buildRequestFactory.getBuildRequest();
		for (File outputDirectory : getOutputDirectories()) {
			File[] childFiles = outputDirectory.listFiles();
			if (childFiles != null) {
				for (int i = 0; i < childFiles.length; i++) {
					operationCanceledManager.checkCanceled(cancelIndicator);
					deleteFileOrFolder(request, childFiles[i]);
				}
			}
		}

		for (ISourceFolder sourceFolder : projectConfig.getSourceFolders()) {
			operationCanceledManager.checkCanceled(cancelIndicator);
			List<URI> allURIs = sourceFolder.getAllResources(fileSystemScanner);
			for (URI uri : allURIs) {
				operationCanceledManager.checkCanceled(cancelIndicator);
				issueAcceptor.publishDiagnostics(uri, Collections.emptyList());
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
	protected void deleteFileOrFolder(XBuildRequest request, File file) {
		if (file.isDirectory()) {
			File[] childFildes = file.listFiles();
			for (int i = 0; i < childFildes.length; i++) {
				deleteFileOrFolder(request, childFildes[i]);
			}
		}
		boolean wasFile = file.isFile();
		file.delete();
		if (wasFile) {
			URI fileUri = URI.createFileURI(file.getAbsolutePath());
			request.afterDelete(fileUri);
		}
	}

	/** Report an issue. */
	public void reportProjectIssue(String message, String code, Severity severity) {
		Issue.IssueImpl result = new Issue.IssueImpl();
		result.setMessage(message);
		result.setCode(code);
		result.setSeverity(severity);
		result.setUriToProblem(getBaseDir());
		issueAcceptor.publishDiagnostics(getBaseDir(), ImmutableList.of(result));
	}

	/** Creates a new build request for this project. */
	protected XBuildRequest newBuildRequest(Set<URI> changedFiles, Set<URI> deletedFiles,
			List<IResourceDescription.Delta> externalDeltas, boolean propagateIssues, boolean doGenerate,
			CancelIndicator cancelIndicator) {

		XBuildRequest result = buildRequestFactory.getBuildRequest(changedFiles, deletedFiles, externalDeltas);

		XIndexState indexState = projectStateHolder.getIndexState();
		ResourceDescriptionsData resourceDescriptionsCopy = indexState.getResourceDescriptions().copy();
		XSource2GeneratedMapping fileMappingsCopy = indexState.getFileMappings().copy();
		result.setState(new XIndexState(resourceDescriptionsCopy, fileMappingsCopy));
		result.setResourceSet(createFreshResourceSet(result.getState().getResourceDescriptions()));
		result.setCancelIndicator(cancelIndicator);
		result.setBaseDir(getBaseDir());
		result.setGeneratorEnabled(doGenerate);

		if (!propagateIssues) {
			// during initial build, we do not want to notify about any issues because it is
			// done at the end of the project for the deserialized issues and the new issues
			// altogether.
			result.setAfterValidateListener(null);
		}

		if (projectConfig instanceof N4JSProjectConfig) {
			// TODO: merge N4JSProjectConfig#indexOnly() to IProjectConfig
			N4JSProjectConfig n4pc = (N4JSProjectConfig) projectConfig;
			result.setIndexOnly(n4pc.indexOnly());
		}

		return result;
	}

	/** Create an empty resource set. */
	protected XtextResourceSet createFreshResourceSet(ResourceDescriptionsData newIndex) {
		if (resourceSet == null) {
			resourceSet = createNewResourceSet(newIndex);
		} else {
			ChunkedResourceDescriptions resDescs = ChunkedResourceDescriptions.findInEmfObject(resourceSet);

			Map<String, ResourceDescriptionsData> concurrentMap = indexProvider.get();
			concurrentMap.forEach(resDescs::setContainer);
			resDescs.setContainer(projectDescription.getName(), newIndex);
		}
		return resourceSet;
	}

	/** Create and configure a new resource set for this project. */
	protected XtextResourceSet createNewResourceSet(ResourceDescriptionsData newIndex) {
		XtextResourceSet result = resourceSetProvider.get();
		result.setURIResourceMap(uriResourceMap);
		projectDescription.attachToEmfObject(result);
		ProjectConfigAdapter.install(result, projectConfig);
		attachWorkspaceResourceLocator(result);

		Map<String, ResourceDescriptionsData> concurrentMap = indexProvider.get();
		ChunkedResourceDescriptions index = new ChunkedResourceDescriptions(concurrentMap, result);
		index.setContainer(projectDescription.getName(), newIndex);
		externalContentSupport.configureResourceSet(result, openedDocumentsContentProvider);
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
		Map<String, ResourceDescriptionsData> concurrentMap = indexProvider.get();
		ResourceDescriptionsData resourceDescriptionsData = concurrentMap.get(projectDescription.getName());

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
	protected Provider<Map<String, ResourceDescriptionsData>> getIndexProvider() {
		return indexProvider;
	}

	/** Getter */
	protected IExternalContentSupport.IExternalContentProvider getOpenedDocumentsContentProvider() {
		return openedDocumentsContentProvider;
	}

	/** Getter */
	public ProjectStateHolder getProjectStateHolder() {
		return projectStateHolder;
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
	public IProjectConfig getProjectConfig() {
		return projectConfig;
	}

}
