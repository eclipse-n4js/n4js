/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.xtext.server.openfiles;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.TextDocumentContentChangeEvent;
import org.eclipse.n4js.ide.xtext.server.IssueAcceptor;
import org.eclipse.n4js.ide.xtext.server.XDocument;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescription.Delta;
import org.eclipse.xtext.resource.IResourceDescription.Manager.AllChangeAware;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.util.LazyStringInputStream;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.validation.Issue;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * Manages a single open file, including EMF resources and resource sets for files required by the open file.
 */
@SuppressWarnings("javadoc")
public class OpenFileManager {

	@Inject
	private IssueAcceptor issueAcceptor;

	@Inject
	private Provider<XtextResourceSet> resourceSetProvider;

	@Inject
	private IResourceServiceProvider.Registry resourceServiceProviderRegistry;

	@Inject
	private IResourceServiceProvider.Registry languagesRegistry;

	/** The {@link OpenFilesManager} that created this instance. */
	protected OpenFilesManager parent;
	/** URI of the open file represented by this {@link OpenFileManager} (i.e. URI of the main resource). */
	protected URI mainURI;
	/** Name of project containing the open file. */
	protected String mainProjectName;

	/**
	 * Contains the state of all files in the workspace. For open files managed by {@link #parent} (including the open
	 * file of this manager) this state will represent the dirty state and for all other files it will represent the
	 * persisted state (as provided by the LSP builder).
	 */
	protected ResourceDescriptionsData index;

	/** The EMF resource representing the open file. */
	protected XtextResource mainResource;
	/** The current textual content of the open file. */
	protected XDocument document = null;

	public URI getURI() {
		return mainURI;
	}

	public XtextResourceSet getResourceSet() {
		return (XtextResourceSet) mainResource.getResourceSet();
	}

	public void initialize(OpenFilesManager parent, URI uri, String projectName, IResourceDescriptions persistedState,
			ResourceDescriptionsData sharedDirtyState) {
		this.parent = parent;
		this.mainURI = uri;
		this.mainProjectName = projectName;

		this.index = createIndex(persistedState, sharedDirtyState);

		XtextResourceSet resourceSet = createResourceSet(projectName);
		this.mainResource = (XtextResource) resourceSet.createResource(uri);
	}

	protected ResourceDescriptionsData createIndex(IResourceDescriptions persistedState,
			ResourceDescriptionsData sharedDirtyState) {
		ResourceDescriptionsData result = new ResourceDescriptionsData(Collections.emptyList());
		persistedState.getAllResourceDescriptions()
				.forEach(desc -> result.addDescription(desc.getURI(), desc));
		sharedDirtyState.getAllResourceDescriptions()
				.forEach(desc -> result.addDescription(desc.getURI(), desc));
		return result;
	}

	protected XtextResourceSet createResourceSet(String projectName) {
		XtextResourceSet result = resourceSetProvider.get();

		ResourceDescriptionsData.ResourceSetAdapter.installResourceDescriptionsData(result, index);

		// TODO support external content:
		// externalContentSupport.configureResourceSet(result, workspaceManager.getOpenedDocumentsContentProvider());

		// OpenFileResourceLocator resourceLocator = new OpenFileResourceLocator(result);
		// ProjectDescription projectDescription = new ProjectDescription();
		// projectDescription.setName(projectName);
		// projectDescription.attachToEmfObject(result); // required by ChunkedResourceDescriptions
		// ConcurrentHashMap<String, ResourceDescriptionsData> concurrentMap = (ConcurrentHashMap<String,
		// ResourceDescriptionsData>) workspaceManager.getFullIndex();
		// DirtyStateAwareChunkedResourceDescriptions index = new
		// DirtyStateAwareChunkedResourceDescriptions(concurrentMap, result, dirtyState);

		return result;
	}

	protected void initOpenFile(int version, String content, CancelIndicator cancelIndicator) {
		if (mainResource.isLoaded()) {
			throw new IllegalStateException("trying to initialize an already loaded resource: " + mainURI);
		}

		document = new XDocument(version, content);

		try (InputStream in = new LazyStringInputStream(document.getContents(), mainResource.getEncoding())) {
			mainResource.load(in, null);
		} catch (IOException e) {
			throw new RuntimeException("IOException while reading from string input stream", e);
		}

		resolveAndValidateOpenFile(cancelIndicator);
	}

	protected void refreshOpenFile(CancelIndicator cancelIndicator) {
		// FIXME find better solution for updating unchanged open files!
		TextDocumentContentChangeEvent dummyChange = new TextDocumentContentChangeEvent(document.getContents());
		refreshOpenFile(document.getVersion(), Collections.singletonList(dummyChange), cancelIndicator);
	}

	protected void refreshOpenFile(int version, Iterable<? extends TextDocumentContentChangeEvent> changes,
			CancelIndicator cancelIndicator) {
		if (!mainResource.isLoaded()) {
			throw new IllegalStateException("trying to refresh a resource that is not loaded: " + mainURI);
		}

		// TODO the following is only necessary for changed files (could be moved to #updateDirtyState())
		ResourceSet resSet = getResourceSet();
		for (Resource res : new ArrayList<>(resSet.getResources())) {
			if (res != mainResource) {
				res.unload();
				resSet.getResources().remove(res);
			}
		}

		for (TextDocumentContentChangeEvent change : changes) {
			Range range = change.getRange();
			int start = range != null ? document.getOffSet(range.getStart()) : 0;
			int end = range != null ? document.getOffSet(range.getEnd()) : document.getContents().length();
			String replacement = change.getText();

			document = document.applyTextDocumentChanges(Collections.singletonList(change));

			mainResource.update(start, end - start, replacement);
		}

		resolveAndValidateOpenFile(cancelIndicator);
	}

	protected void resolveAndValidateOpenFile(CancelIndicator cancelIndicator) {
		// resolve
		EcoreUtil2.resolveLazyCrossReferences(mainResource, cancelIndicator);
		// validate
		IResourceServiceProvider resourceServiceProvider = languagesRegistry.getResourceServiceProvider(mainURI);
		IResourceValidator resourceValidator = resourceServiceProvider.getResourceValidator();
		// notify LSP client
		List<Issue> issues = resourceValidator.validate(mainResource, CheckMode.ALL, cancelIndicator);
		issueAcceptor.publishDiagnostics(mainURI, issues);
		// update dirty state
		IResourceDescription newDesc = createResourceDescription();
		index.addDescription(mainURI, newDesc);
		parent.updateSharedDirtyState(newDesc, cancelIndicator);
	}

	/**
	 * Invoked by {@link #parent} when a change happened in another open file (not the one managed by this
	 * {@link OpenFileManager}).
	 */
	protected void onDirtyStateChanged(IResourceDescription changedDesc, CancelIndicator cancelIndicator) {
		updateIndex(Collections.singletonList(changedDesc), Collections.emptySet(), cancelIndicator);
	}

	/**
	 * Invoked by {@link #parent} when a change happened in a non-opened file.
	 */
	protected void onPersistedStateChanged(Collection<? extends IResourceDescription> changedDescs,
			Set<URI> removedURIs, CancelIndicator cancelIndicator) {
		updateIndex(changedDescs, removedURIs, cancelIndicator);
	}

	protected void updateIndex(Collection<? extends IResourceDescription> changedDescs, Set<URI> removedURIs,
			CancelIndicator cancelIndicator) {

		List<IResourceDescription.Delta> allDeltas = createDeltas(changedDescs, removedURIs);
		List<IResourceDescription.Delta> changedDeltas = allDeltas.stream()
				.filter(d -> d.haveEObjectDescriptionsChanged())
				.collect(Collectors.toList());

		changedDeltas.forEach(index::register);

		IResourceDescription.Manager rdm = getResourceDescriptionManager(mainURI);
		if (rdm == null) {
			return;
		}
		IResourceDescription candidateDesc = index.getResourceDescription(mainURI);
		boolean isAffected = rdm instanceof AllChangeAware
				? ((AllChangeAware) rdm).isAffectedByAny(allDeltas, candidateDesc, index)
				: rdm.isAffected(changedDeltas, candidateDesc, index);
		if (isAffected) {
			refreshOpenFile(cancelIndicator);
		}
	}

	protected List<IResourceDescription.Delta> createDeltas(Collection<? extends IResourceDescription> changedDescs,
			Set<URI> removedURIs) {

		List<IResourceDescription.Delta> deltas = new ArrayList<>(changedDescs.size() + removedURIs.size());

		for (IResourceDescription changedDesc : changedDescs) {
			URI changedURI = changedDesc.getURI();
			IResourceDescription oldDesc = index.getResourceDescription(changedURI);
			IResourceDescription.Manager rdm = getResourceDescriptionManager(changedURI);
			if (rdm != null) {
				Delta delta = rdm.createDelta(oldDesc, changedDesc);
				deltas.add(delta);
			}
		}

		for (URI removedURI : removedURIs) {
			IResourceDescription removedDesc = index.getResourceDescription(removedURI);
			if (removedDesc != null) {
				IResourceDescription.Manager rdm = getResourceDescriptionManager(removedURI);
				if (rdm != null) {
					Delta delta = rdm.createDelta(removedDesc, null);
					deltas.add(delta);
				}
			}
		}

		return deltas;
	}

	protected IResourceDescription createResourceDescription() {
		IResourceDescription.Manager resourceDescriptionManager = getResourceDescriptionManager(mainURI);
		IResourceDescription newDesc = resourceDescriptionManager.getResourceDescription(mainResource);
		// trigger lazy serialization of TModule
		// FIXME why is this required?
		IterableExtensions.forEach(newDesc.getExportedObjects(), eobjDesc -> eobjDesc.getUserDataKeys());
		return newDesc;
	}

	protected IResourceDescription.Manager getResourceDescriptionManager(URI uri) {
		IResourceServiceProvider resourceServiceProvider = resourceServiceProviderRegistry
				.getResourceServiceProvider(uri);
		if (resourceServiceProvider == null) {
			return null;
		}
		return resourceServiceProvider.getResourceDescriptionManager();
	}
}
