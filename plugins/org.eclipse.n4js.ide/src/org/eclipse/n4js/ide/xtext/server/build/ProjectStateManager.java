/**
 * Copyright (c) 2019 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.xtext.server.build;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.ide.xtext.server.LSPIssue;
import org.eclipse.n4js.ide.xtext.server.ProjectStatePersisterConfig;
import org.eclipse.n4js.ide.xtext.server.ResourceChangeSet;
import org.eclipse.n4js.ide.xtext.server.build.ProjectStatePersister.ProjectState;
import org.eclipse.n4js.utils.URIUtils;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescription.Delta;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;
import org.eclipse.xtext.util.IFileSystemScanner;
import org.eclipse.xtext.workspace.IProjectConfig;
import org.eclipse.xtext.workspace.ISourceFolder;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSortedSet;
import com.google.inject.Inject;

/**
 * Holds index, hashes and issue information
 */
@SuppressWarnings("restriction")
public class ProjectStateManager {

	/** Reads and writes the type index from/to disk */
	@Inject
	protected ProjectStatePersister projectStatePersister;

	/** Scans the file system. */
	@Inject
	protected IFileSystemScanner fileSystemScanner;

	/** Holds configuration about project persisting */
	@Inject
	protected ProjectStatePersisterConfig persistConfig;

	/** Used to filter the traversed resources */
	@Inject
	protected IResourceServiceProvider.Registry resourceServiceProviders;

	/** Index for all projects */
	@Inject
	protected ConcurrentIndex fullIndex;

	/** Issues registry for all projects */
	@Inject
	protected ConcurrentIssueRegistry issueRegistry;

	private IProjectConfig projectConfig;

	private XSource2GeneratedMapping fileMappings;

	private Map<URI, HashedFileContent> uriToHashedFileContents = new HashMap<>();

	/** Call after instantiation */
	public void initialize(IProjectConfig _projectConfig) {
		this.projectConfig = _projectConfig;
		doClear();
	}

	/** Clears type index of this project. */
	public void doClear() {
		uriToHashedFileContents.clear();
		fileMappings = new XSource2GeneratedMapping();
		setIndex(new ResourceDescriptionsData(CollectionLiterals.<IResourceDescription> emptySet()));
		setValidationIssues(new HashMap<>());
	}

	/** Deletes the persistence file on disk */
	public void deletePersistenceFile() {
		URI persistenceFileURI = getPersistenceFile();
		File persistenceFile = URIUtils.toFile(persistenceFileURI);
		if (persistenceFile.isFile()) {
			persistenceFile.delete();
		}
	}

	/** Persists the project state to disk */
	public void writeProjectState() {
		if (persistConfig.isWriteToDisk(projectConfig)) {
			ProjectState currentState = new ProjectState(getIndex(), fileMappings, uriToHashedFileContents,
					getValidationIssues());
			projectStatePersister.writeProjectState(projectConfig, currentState);
		}
	}

	/**
	 * Reads the persisted project state from disk
	 *
	 * @return set of all source URIs with modified contents
	 */
	public ResourceChangeSet readProjectState() {
		if (persistConfig.isDeleteState(projectConfig)) {
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
					projectState.validationIssues.remove(previouslyExistingFile);
					break;
				}
				case DELETED: {
					result.getDeleted().add(previouslyExistingFile);
					projectState.validationIssues.remove(previouslyExistingFile);
					break;
				}
				}
			}
			setIndex(projectState.index);
			setValidationIssues(projectState.validationIssues);
		}

		Set<URI> allIndexedUris = getIndex().getAllURIs();
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
	public void updateProjectState(XBuildRequest request, XBuildResult result) {
		HashMap<URI, HashedFileContent> newFileContents = new HashMap<>(uriToHashedFileContents);
		for (Delta delta : result.getAffectedResources()) {
			URI uri = delta.getUri();
			storeHash(newFileContents, uri);
		}
		for (URI deletedFile : request.getResultDeleteFiles()) {
			newFileContents.remove(deletedFile);
		}

		setIndex(result.getIndex());
		setFileMappings(result.getFileMappings());
		setValidationIssues(request.getResultIssues());
		uriToHashedFileContents = newFileContents;

		if (request.isGeneratorEnabled() && !result.getAffectedResources().isEmpty()) {
			writeProjectState();
		}
	}

	enum SourceChangeKind {
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
	public URI getPersistenceFile() {
		URI persistanceFile = projectStatePersister.getFileName(projectConfig);
		return persistanceFile;
	}

	/** @return the validation issues as an unmodifiable map. */
	public ImmutableMap<URI, ImmutableSortedSet<LSPIssue>> getValidationIssues() {
		ImmutableMap<URI, ImmutableSortedSet<LSPIssue>> issues = issueRegistry
				.getIssuesOfPersistedState(projectConfig.getName());

		if (issues == null) {
			return ImmutableMap.copyOf(Collections.emptyMap());
		}
		return issues;
	}

	/** Setter. */
	public void setValidationIssues(Map<URI, ? extends Collection<LSPIssue>> issues) {
		for (URI uri : issues.keySet()) {
			issueRegistry.setIssuesOfPersistedState(projectConfig.getName(), uri, issues.get(uri));
		}
	}

	/** Getter. */
	public ResourceDescriptionsData getIndex() {
		return fullIndex.getProjectIndex(projectConfig.getName());
	}

	/** Setter. */
	public void setIndex(ResourceDescriptionsData index) {
		this.fullIndex.setProjectIndex(projectConfig.getName(), index);
	}

	/** Getter. */
	public XSource2GeneratedMapping getFileMappings() {
		return fileMappings;
	}

	/** Setter. */
	public void setFileMappings(XSource2GeneratedMapping fileMappings) {
		this.fileMappings = fileMappings;
	}

}
