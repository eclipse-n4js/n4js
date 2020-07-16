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
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.ide.xtext.server.ProjectStatePersister.PersistedState;
import org.eclipse.n4js.ide.xtext.server.build.XBuildRequest;
import org.eclipse.n4js.ide.xtext.server.build.XBuildResult;
import org.eclipse.n4js.ide.xtext.server.build.XIndexState;
import org.eclipse.n4js.ide.xtext.server.build.XSource2GeneratedMapping;
import org.eclipse.n4js.ide.xtext.server.concurrent.ConcurrentIssueRegistry;
import org.eclipse.n4js.utils.URIUtils;
import org.eclipse.xtext.resource.IResourceDescription.Delta;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.util.IFileSystemScanner;
import org.eclipse.xtext.workspace.IProjectConfig;
import org.eclipse.xtext.workspace.ISourceFolder;

import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.TreeMultimap;
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

	private XIndexState indexState = new XIndexState();

	private Map<URI, HashedFileContent> uriToHashedFileContents = new HashMap<>();

	// TODO GH-1774 avoid storing issues twice (in ProjectStateHolder and ConcurrentIssueRegistry)
	/*
	 * Implementation note: We use a sorted map to report the issues in a stable order. The values of the the map are
	 * sorted by offset and severity and message.
	 *
	 * URI (keys in the multimap) are sorted according to their location in the file system. Turns out that the string
	 * representation yields the same result as a comparison per path segment.
	 *
	 * The sort order will look like this: /a/b, /a/b/c, /a/b/d, /a/c, /aa
	 */
	private final Multimap<URI, LSPIssue> validationIssues = TreeMultimap.create(Comparator.comparing(URI::toString),
			ConcurrentIssueRegistry.defaultIssueComparator);

	/** Clears type index of this project. */
	public void doClear() {
		uriToHashedFileContents.clear();
		setIndexState(new XIndexState());
		validationIssues.clear();
	}

	/** Deletes the persistence file on disk */
	public void deletePersistenceFile(IProjectConfig projectConfig) {
		URI persistenceFileURI = getPersistenceFile(projectConfig);
		File persistenceFile = URIUtils.toFile(persistenceFileURI);
		if (persistenceFile.isFile()) {
			persistenceFile.delete();
		}
	}

	/** Persists the project state to disk */
	public void writeProjectState(IProjectConfig projectConfig) {
		if (persistConfig.isWriteToDisk(projectConfig)) {
			Collection<HashedFileContent> hashFileContents = uriToHashedFileContents.values();
			projectStatePersister.writeProjectState(projectConfig, indexState, hashFileContents, getValidationIssues());
		}
	}

	/**
	 * Return the validation issues as an unmodifiable map.
	 */
	public Multimap<URI, LSPIssue> getValidationIssues() {
		return Multimaps.unmodifiableMultimap(validationIssues);
	}

	/**
	 * Reads the persisted project state from disk
	 *
	 * @return set of all source URIs with modified contents
	 */
	public ResourceChangeSet readProjectState(IProjectConfig projectConfig) {
		if (persistConfig.isDeleteState(projectConfig)) {
			deletePersistenceFile(projectConfig);
		}

		ResourceChangeSet result = new ResourceChangeSet();
		doClear();

		PersistedState persistedState = projectStatePersister.readProjectState(projectConfig);
		if (persistedState != null) {
			for (HashedFileContent hfc : persistedState.fileHashs.values()) {
				URI previouslyExistingFile = hfc.getUri();
				switch (getSourceChangeKind(hfc, persistedState)) {
				case UNCHANGED: {
					uriToHashedFileContents.put(previouslyExistingFile, hfc);
					break;
				}
				case CHANGED: {
					result.getModified().add(previouslyExistingFile);
					persistedState.validationIssues.removeAll(previouslyExistingFile);
					break;
				}
				case DELETED: {
					result.getDeleted().add(previouslyExistingFile);
					persistedState.validationIssues.removeAll(previouslyExistingFile);
					break;
				}
				}
			}
			setIndexState(persistedState.indexState);
			mergeValidationIssues(persistedState.validationIssues.asMap());
		}

		Set<URI> allIndexedUris = indexState.getResourceDescriptions().getAllURIs();
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
	public void updateProjectState(XBuildRequest request, XBuildResult result, IProjectConfig projectConfig) {
		HashMap<URI, HashedFileContent> newFileContents = new HashMap<>(uriToHashedFileContents);
		for (Delta delta : result.getAffectedResources()) {
			URI uri = delta.getUri();
			storeHash(newFileContents, uri);
		}
		for (URI deletedFile : request.getResultDeleteFiles()) {
			newFileContents.remove(deletedFile);
		}

		setIndexState(result.getIndexState());
		mergeValidationIssues(request.getResultIssues());
		uriToHashedFileContents = newFileContents;

		if (request.isGeneratorEnabled() && !result.getAffectedResources().isEmpty()) {
			writeProjectState(projectConfig);
		}
	}

	enum SourceChangeKind {
		UNCHANGED, CHANGED, DELETED
	}

	private SourceChangeKind getSourceChangeKind(HashedFileContent hfc, PersistedState persistedState) {
		URI sourceUri = hfc.getUri();
		long loadedHash = hfc.getHash();

		HashedFileContent newHash = doHash(sourceUri);
		if (newHash == null) {
			return SourceChangeKind.DELETED;
		}

		if (loadedHash != newHash.getHash()) {
			return SourceChangeKind.CHANGED;
		}

		XSource2GeneratedMapping sourceFileMappings = persistedState.indexState.getFileMappings();
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
	protected void mergeValidationIssues(Map<URI, Collection<LSPIssue>> issueMap) {
		for (Iterator<Entry<URI, Collection<LSPIssue>>> iter = issueMap.entrySet().iterator(); iter.hasNext();) {
			Entry<URI, Collection<LSPIssue>> entry = iter.next();
			URI source = entry.getKey();
			validationIssues.replaceValues(source, entry.getValue());
		}
	}
}
