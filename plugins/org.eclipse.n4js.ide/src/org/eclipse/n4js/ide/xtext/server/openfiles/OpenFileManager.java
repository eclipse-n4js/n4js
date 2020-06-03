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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl.ResourceLocator;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.TextDocumentContentChangeEvent;
import org.eclipse.n4js.ide.xtext.server.IssueAcceptor;
import org.eclipse.n4js.ide.xtext.server.XDocument;
import org.eclipse.n4js.ide.xtext.server.XWorkspaceManager;
import org.eclipse.n4js.ide.xtext.server.util.DirtyStateAwareChunkedResourceDescriptions;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.resource.impl.ProjectDescription;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.util.LazyStringInputStream;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.validation.Issue;
import org.eclipse.xtext.workspace.IProjectConfig;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * Manages a single open file, including EMF resources and resource sets for files required by the open file.
 */
@SuppressWarnings("javadoc")
public class OpenFileManager {

	@Inject
	protected XWorkspaceManager workspaceManager; // FIXME try to get rid of this

	@Inject
	private IssueAcceptor issueAcceptor;

	@Inject
	private Provider<XtextResourceSet> resourceSetProvider;

	@Inject
	private IResourceDescription.Manager resourceDescriptionManager;

	@Inject
	private IResourceServiceProvider.Registry languagesRegistry;

	/** The {@link OpenFilesManager} that created this instance. */
	protected OpenFilesManager parent;
	/** URI of the open file represented by this {@link OpenFileManager} (i.e. URI of the main resource). */
	protected URI mainURI;
	/** Name of project containing the open file. */
	protected String mainProjectName;

	/**
	 * Dirty state index of all open files. This instance is not shared across {@link OpenFileManager}s of different
	 * open files, but is used across all resource sets in this manager's {@link #containerHandle2ResourceSet}.
	 */
	protected ResourceDescriptionsData dirtyState;
	/** The EMF resource representing the open file. */
	protected XtextResource mainResource;
	/** The current textual content of the open file. */
	protected XDocument document = null;

	protected final Map<String, ResourceSet> containerHandle2ResourceSet = new HashMap<>();

	public URI getURI() {
		return mainURI;
	}

	public void initialize(OpenFilesManager parent, URI uri, String projectName,
			ResourceDescriptionsData sharedDirtyState) {
		this.parent = parent;
		this.mainURI = uri;
		this.mainProjectName = projectName;

		this.dirtyState = sharedDirtyState.copy();

		ResourceSet resourceSet = createResourceSet(projectName);
		this.mainResource = (XtextResource) resourceSet.createResource(uri);
		containerHandle2ResourceSet.put(projectName, resourceSet);
	}

	protected ResourceSet getResourceSetForURI(URI uri, boolean createOnDemand) {
		@SuppressWarnings("restriction")
		IProjectConfig projectConfig = workspaceManager.getProjectConfig(uri);
		if (projectConfig == null) {
			// happens for built-in types, etc. -> put it into the resource set of the main resource
			// FIXME reconsider (could also be put into its own resource set; check how main builder is doing it)
			return containerHandle2ResourceSet.get(mainProjectName);
		}
		@SuppressWarnings("restriction")
		String containerHandle = projectConfig.getName();
		ResourceSet result = containerHandle2ResourceSet.get(containerHandle);
		if (result == null && createOnDemand) {
			result = createResourceSet(containerHandle);
			containerHandle2ResourceSet.put(containerHandle, result);
		}
		return result;
	}

	protected ResourceSet createResourceSet(String projectName) {
		XtextResourceSet result = resourceSetProvider.get();

		OpenFileResourceLocator resourceLocator = new OpenFileResourceLocator(result);
		ProjectDescription projectDescription = new ProjectDescription();
		projectDescription.setName(projectName);
		projectDescription.attachToEmfObject(result); // required by ChunkedResourceDescriptions
		ConcurrentHashMap<String, ResourceDescriptionsData> concurrentMap = (ConcurrentHashMap<String, ResourceDescriptionsData>) workspaceManager
				.getFullIndex();
		DirtyStateAwareChunkedResourceDescriptions index = new DirtyStateAwareChunkedResourceDescriptions(concurrentMap,
				result, dirtyState);
		// ResourceDescriptionsData newIndex = new XIndexState().getResourceDescriptions();
		// index.setContainer("Test", newIndex);
		// externalContentSupport.configureResourceSet(result, workspaceManager.getOpenedDocumentsContentProvider());

		// ConcurrentHashMap<String, ResourceDescriptionsData> concurrentMap = (ConcurrentHashMap<String,
		// ResourceDescriptionsData>) workspaceManager
		// .getFullIndex();
		// ResourceDescriptionsData index = new MyDummyResourceDescriptions(Collections.emptyList());
		// ResourceDescriptionsData.ResourceSetAdapter.installResourceDescriptionsData(result, index);

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
		for (ResourceSet resSet : containerHandle2ResourceSet.values()) {
			for (Resource res : new ArrayList<>(resSet.getResources())) {
				if (res != mainResource) {
					res.unload();
					resSet.getResources().remove(res);
				}
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
		// update dirty state index (mine and those of all other open files)
		IResourceDescription newDesc = createResourceDescription();
		dirtyState.addDescription(mainURI, newDesc);
		parent.updateSharedDirtyState(mainURI, newDesc, cancelIndicator);
	}

	/**
	 * Invoked by {@link #parent} when a change happened in another open file (not the one managed by this
	 * {@link OpenFileManager}).
	 */
	protected void onDirtyStateChanged(IResourceDescription.Delta delta, CancelIndicator cancelIndicator) {
		dirtyState.register(delta);
		IResourceDescription candidateDesc = dirtyState.getResourceDescription(mainURI);
		if (resourceDescriptionManager.isAffected(delta, candidateDesc)) {
			refreshOpenFile(cancelIndicator);
		}
	}

	protected IResourceDescription createResourceDescription() {
		IResourceDescription newDesc = resourceDescriptionManager.getResourceDescription(mainResource);
		// trigger lazy serialization of TModule
		// FIXME why is this required?
		IterableExtensions.forEach(newDesc.getExportedObjects(), eobjDesc -> eobjDesc.getUserDataKeys());
		return newDesc;
	}

	public class OpenFileResourceLocator extends ResourceLocator {

		public OpenFileResourceLocator(ResourceSetImpl resourceSet) {
			super(resourceSet);
		}

		@Override
		public Resource getResource(URI uri, boolean loadOnDemand) {
			ResourceSet resourceSetForURI = getResourceSetForURI(uri, loadOnDemand);
			if (resourceSetForURI != null) {
				if (resourceSetForURI == this.resourceSet) { // avoid infinite loop
					return basicGetResource(uri, loadOnDemand);
				}
				return resourceSetForURI.getResource(uri, loadOnDemand);
			}
			return null;
		}
	}
}
