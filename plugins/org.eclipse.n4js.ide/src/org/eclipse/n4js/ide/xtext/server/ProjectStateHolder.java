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
package org.eclipse.n4js.ide.xtext.server;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.ide.xtext.server.ProjectStatePersister.PersistedState;
import org.eclipse.n4js.ide.xtext.server.build.XBuildRequest;
import org.eclipse.n4js.ide.xtext.server.build.XIncrementalBuilder;
import org.eclipse.n4js.ide.xtext.server.build.XIndexState;
import org.eclipse.n4js.ide.xtext.server.build.XSource2GeneratedMapping;
import org.eclipse.xtext.resource.IResourceDescription.Delta;
import org.eclipse.xtext.util.IFileSystemScanner;
import org.eclipse.xtext.validation.Issue;
import org.eclipse.xtext.workspace.IProjectConfig;
import org.eclipse.xtext.workspace.ISourceFolder;

import com.google.inject.Inject;

/**
 * Holds index, hashes and issue information
 */
@SuppressWarnings("restriction")
public class ProjectStateHolder {

	/** Reads and writes the type index from/to disk */
	@Inject
	protected ProjectStatePersister projectStatePersister;

	/** Scans the file system. */
	@Inject
	protected IFileSystemScanner fileSystemScanner;

	/** Publishes issues to lsp client */
	@Inject
	protected IssueAcceptor issueAcceptor;

	/** Holds configuration about project persisting */
	@Inject
	protected ProjectStatePersisterConfig persistConfig;

	private XIndexState indexState = new XIndexState();

	private Map<URI, HashedFileContent> hashFileMap = new HashMap<>();

	private final Map<URI, Collection<Issue>> validationIssues = new HashMap<>();

	/** Clears type index of this project. */
	public void doClear() {
		hashFileMap.clear();
		setIndexState(new XIndexState());
		validationIssues.clear();
	}

	/** Deletes the persistence file on disk */
	public void deletePersistenceFile(IProjectConfig projectConfig) {
		URI persistenceFileURI = getPersistenceFile(projectConfig);
		File persistenceFile = new File(persistenceFileURI.toFileString());
		if (persistenceFile.isFile()) {
			persistenceFile.delete();
		}
	}

	/** Persists the project state to disk */
	public void writeProjectState(IProjectConfig projectConfig) {
		if (persistConfig.isWriteToDisk()) {
			Collection<HashedFileContent> hashFileContents = hashFileMap.values();
			projectStatePersister.writeProjectState(projectConfig, indexState, hashFileContents, validationIssues);
		}
	}

	/**
	 * Reads the persisted project state from disk
	 *
	 * @return set of all source URIs with modified contents
	 */
	public Set<URI> readProjectState(IProjectConfig projectConfig) {
		if (persistConfig.isDeleteState()) {
			deletePersistenceFile(projectConfig);
		}

		Set<URI> changedSources = new HashSet<>();
		doClear();

		PersistedState persistedState = projectStatePersister.readProjectState(projectConfig);
		if (persistedState != null) {

			for (HashedFileContent hfc : persistedState.fileHashs.values()) {
				URI uri = hfc.getUri();
				if (isSourceUnchanged(hfc, persistedState)) {
					hashFileMap.put(uri, hfc);
				} else {
					persistedState.indexState.getFileMappings().deleteSource(uri);
					persistedState.validationIssues.remove(uri);
				}
			}
			setIndexState(persistedState.indexState);
			mergeValidationIssues(persistedState.validationIssues);
			reportValidationIssues(persistedState.validationIssues);
		}

		Set<URI> allIndexedUris = indexState.getResourceDescriptions().getAllURIs();
		for (ISourceFolder srcFolder : projectConfig.getSourceFolders()) {
			List<URI> allSourceFolderUris = srcFolder.getAllResources(fileSystemScanner);
			for (URI srcFolderUri : allSourceFolderUris) {
				if (!allIndexedUris.contains(srcFolderUri)) {
					changedSources.add(srcFolderUri);
				}
			}
		}

		return changedSources;
	}

	/** Updates the index state, file hashes and validation issues */
	public void updateProjectState(XBuildRequest request, XIncrementalBuilder.XResult result) {
		HashMap<URI, HashedFileContent> newFileContents = new HashMap<>(hashFileMap);
		for (Delta delta : result.getAffectedResources()) {
			URI uri = delta.getUri();
			storeHash(newFileContents, uri);
		}
		for (URI deletedFile : request.getResultDeleteFiles()) {
			newFileContents.remove(deletedFile);
		}

		setIndexState(result.getIndexState());
		mergeValidationIssues(request.getResultIssues());
		hashFileMap = newFileContents;
	}

	private boolean isSourceUnchanged(HashedFileContent hfc, PersistedState persistedState) {
		URI sourceUri = hfc.getUri();
		long loadedHash = hfc.getHash();

		HashedFileContent newHash = doHash(sourceUri);
		if (newHash == null || loadedHash != newHash.getHash()) {
			return false;
		}

		XSource2GeneratedMapping sourceFileMappings = persistedState.indexState.getFileMappings();
		List<URI> prevGenerated = sourceFileMappings.getGenerated(sourceUri);
		for (URI generated : prevGenerated) {
			HashedFileContent genFingerprint = persistedState.fileHashs.get(generated);
			if (genFingerprint != null) {
				HashedFileContent generatedHash = doHash(generated);
				if (generatedHash == null || generatedHash.getHash() != genFingerprint.getHash()) {
					return false;
				}
			}
		}

		return true;
	}

	private HashedFileContent doHash(URI uri) {
		try {
			File srcFile = new File(new java.net.URI(uri.toString()));
			HashedFileContent generatedTargetContent = new HashedFileContent(uri, srcFile);
			return generatedTargetContent;
		} catch (IOException | URISyntaxException e) {
			return null;
		}
	}

	private void storeHash(HashMap<URI, HashedFileContent> newFileContents, URI uri) {
		HashedFileContent generatedTargetContent = doHash(uri);
		if (generatedTargetContent != null) {
			newFileContents.put(uri, generatedTargetContent);
		}
	}

	private void reportValidationIssues(Map<URI, ? extends Collection<Issue>> valIssues) {
		for (Map.Entry<URI, ? extends Collection<Issue>> srcIssues : valIssues.entrySet()) {
			URI source = srcIssues.getKey();
			Collection<Issue> issues = srcIssues.getValue();
			issueAcceptor.publishDiagnostics(source, issues);
		}
	}

	/** @return the file the project state is stored to */
	public URI getPersistenceFile(IProjectConfig projectConfig) {
		URI persistanceFile = projectStatePersister.getFileName(projectConfig);
		return persistanceFile;
	}

	/** Getter */
	public XIndexState getIndexState() {
		return indexState;
	}

	/** Setter */
	protected void setIndexState(XIndexState indexState) {
		this.indexState = indexState;
	}

	/** Merges the given map of source files to issues to the current state */
	protected void mergeValidationIssues(Map<URI, Collection<Issue>> issueMap) {
		for (Iterator<Entry<URI, Collection<Issue>>> iter = issueMap.entrySet().iterator(); iter.hasNext();) {
			Entry<URI, Collection<Issue>> entry = iter.next();
			URI source = entry.getKey();
			Collection<Issue> issues = entry.getValue();
			if (issues.isEmpty()) {
				validationIssues.remove(source);
			} else {
				validationIssues.put(source, issues);
			}
		}
	}
}
