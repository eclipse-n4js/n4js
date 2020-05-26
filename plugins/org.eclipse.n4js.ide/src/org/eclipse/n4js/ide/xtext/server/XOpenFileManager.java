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
package org.eclipse.n4js.ide.xtext.server;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl.ResourceLocator;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.TextDocumentContentChangeEvent;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.resource.impl.ChunkedResourceDescriptions;
import org.eclipse.xtext.resource.impl.ProjectDescription;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.util.LazyStringInputStream;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.validation.Issue;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@SuppressWarnings("javadoc")
@Singleton
public class XOpenFileManager {

	@Inject
	private IssueAcceptor issueAcceptor;

	@Inject
	private Provider<XtextResourceSet> resourceSetProvider;

	@Inject
	private IResourceDescription.Manager resourceDescriptionManager;

	@Inject
	private IResourceServiceProvider.Registry languagesRegistry;

	@Inject
	private XWorkspaceManager workspaceManager;

	protected final Map<URI, OpenFileInfo> openFiles = new HashMap<>();
	protected final ResourceDescriptionsData openFilesIndex = new ResourceDescriptionsData(Collections.emptyList());

	protected static class OpenFileInfo {

		/** URI of the open file (i.e. URI of the main resource). */
		final URI uri;
		/** Name of project containing the open file. */
		final String projectName;
		/** The EMF resource representing the open file. */
		final XtextResource resource;
		/** The current textual content of the open file. */
		XDocument document;

		final Map<String, ResourceSet> containerHandle2ResourceSet = new HashMap<>();

		public OpenFileInfo(URI uri, String projectName, ResourceSet resourceSet, XtextResource resource) {
			this.uri = uri;
			this.projectName = projectName;
			this.resource = resource;
			this.document = null;
			containerHandle2ResourceSet.put(projectName, resourceSet);
		}
	}

	public class OpenFileResourceLocator extends ResourceLocator {

		private OpenFileInfo info;

		public OpenFileResourceLocator(ResourceSetImpl resourceSet) {
			super(resourceSet);
		}

		@Override
		public Resource getResource(URI uri, boolean loadOnDemand) {
			if (info == null) {
				info = findInfo(resourceSet);
			}
			ResourceSet resourceSetForURI = getResourceSetForURI(info, uri, loadOnDemand);
			if (resourceSetForURI != null) {
				if (resourceSetForURI != this.resourceSet) {
					return resourceSetForURI.getResource(uri, loadOnDemand);
				}
				return basicGetResource(uri, loadOnDemand);
			}
			return null;
		}
	}

	public static class MyChunkedResourceDescriptions extends ChunkedResourceDescriptions {

		private final ResourceDescriptionsData openFilesIndex;

		public MyChunkedResourceDescriptions(ConcurrentHashMap<String, ResourceDescriptionsData> initialData,
				ResourceSet resourceSet, ResourceDescriptionsData openFilesIndex) {
			super(initialData, resourceSet);
			this.openFilesIndex = openFilesIndex;
			this.chunk2resourceDescriptions = initialData; // avoid creation of a copy!
		}

		@Override
		public IResourceDescription getResourceDescription(URI uri) {
			IResourceDescription shadowingDesc = openFilesIndex.getResourceDescription(uri);
			if (shadowingDesc != null) {
				return shadowingDesc;
			}
			return super.getResourceDescription(uri);
		}

		@Override
		public ResourceDescriptionsData getContainer(String containerHandle) {
			// FIXME the following is super slow!
			ResourceDescriptionsData data = super.getContainer(containerHandle);
			List<IResourceDescription> descs = StreamSupport
					.stream(data.getAllResourceDescriptions().spliterator(), false)
					.map(desc -> {
						IResourceDescription shadowingDesc = openFilesIndex.getResourceDescription(desc.getURI());
						return shadowingDesc != null ? shadowingDesc : desc;
					})
					.collect(Collectors.toList());
			return new ResourceDescriptionsData(descs);
		}
	}

	// public static class MyDummyResourceDescriptions extends ResourceDescriptionsData {
	//
	// public MyDummyResourceDescriptions(Iterable<IResourceDescription> descriptions) {
	// super(descriptions);
	// }
	//
	// @Override
	// public IResourceDescription getResourceDescription(URI uri) {
	// System.out.println("!!!");
	// return super.getResourceDescription(uri);
	// }
	// }

	protected OpenFileInfo findInfo(ResourceSet resourceSet) {
		return openFiles.values().stream()
				.filter(ofi -> ofi.containerHandle2ResourceSet.containsValue(resourceSet))
				.findAny().orElse(null);
	}

	public void openFile(URI uri, int version, String content) {
		if (openFiles.containsKey(uri)) {
			return; // FIXME content gets lost in this case!
		}
		OpenFileInfo info = createNewOpenFileInfo(uri);
		openFiles.put(uri, info);

		initOpenFile(info, version, content, CancelIndicator.NullImpl); // FIXME use proper indicator!
	}

	public void changeFile(URI uri, int version, Iterable<? extends TextDocumentContentChangeEvent> changes,
			CancelIndicator cancelIndicator) {
		OpenFileInfo info = openFiles.get(uri);
		if (info == null) {
			return;
		}
		refreshOpenFile(info, changes, cancelIndicator);
	}

	public void closeFile(URI uri) {
		openFiles.remove(uri);
		openFilesIndex.removeDescription(uri);
		// TODO closing a file may lead to a change in other open files
		// TODO what about publishing diagnostics, here?
	}

	protected OpenFileInfo createNewOpenFileInfo(URI uri) {
		@SuppressWarnings("restriction")
		String projectName = workspaceManager.getProjectConfig(uri).getName();
		ResourceSet resourceSet = createNewResourceSet(projectName);
		XtextResource resource = (XtextResource) resourceSet.createResource(uri);
		OpenFileInfo info = new OpenFileInfo(uri, projectName, resourceSet, resource);
		return info;
	}

	/**
	 * If the given origin resource set is one that is used to process an open file, then this method will return the
	 * resource set responsible for containing a resource with the given uri; otherwise <code>null</code> is returned.
	 * Might return the origin resource set itself or might return a newly created resource set.
	 */
	// FIXME find better solution for this:
	public ResourceSet getOrCreateResourceSetForURI(ResourceSet origin, URI uri) {
		OpenFileInfo info = findInfo(origin);
		if (info == null) {
			return null; // origin is not a resource set related to an open file
		}
		return getResourceSetForURI(info, uri, true);
	}

	protected ResourceSet getResourceSetForURI(OpenFileInfo info, URI uri, boolean createOnDemand) {
		@SuppressWarnings("restriction")
		String containerHandle = workspaceManager.getProjectConfig(uri).getName();
		ResourceSet result = info.containerHandle2ResourceSet.get(containerHandle);
		if (result == null) {
			result = createNewResourceSet(containerHandle);
			info.containerHandle2ResourceSet.put(containerHandle, result);
		}
		return result;
	}

	protected ResourceSet createNewResourceSet(String projectName) {
		XtextResourceSet result = resourceSetProvider.get();

		// result.setURIResourceMap(uriResourceMap);
		// new WorkspaceAwareResourceLocator(result, workspaceManager);
		// new OpenFileResourceLocator(result);
		ProjectDescription projectDescription = new ProjectDescription();
		projectDescription.setName(projectName);
		projectDescription.attachToEmfObject(result); // required by ChunkedResourceDescriptions
		ConcurrentHashMap<String, ResourceDescriptionsData> concurrentMap = (ConcurrentHashMap<String, ResourceDescriptionsData>) workspaceManager
				.getFullIndex();
		MyChunkedResourceDescriptions index = new MyChunkedResourceDescriptions(concurrentMap, result, openFilesIndex);
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

	protected void initOpenFile(OpenFileInfo info, int version, String content, CancelIndicator cancelIndicator) {
		if (info.resource.isLoaded()) {
			throw new IllegalStateException("trying to initialize an already loaded resource: " + info.uri);
		}

		info.document = new XDocument(version, content);

		try (InputStream in = new LazyStringInputStream(info.document.getContents(), info.resource.getEncoding())) {
			info.resource.load(in, null);
		} catch (IOException e) {
			throw new RuntimeException("IOException while reading from string input stream", e);
		}

		resolveAndValidateOpenFile(info, cancelIndicator);
	}

	protected void refreshOpenFile(OpenFileInfo info, CancelIndicator cancelIndicator) {
		// FIXME find better solution for updating unchanged open files!
		TextDocumentContentChangeEvent dummyChange = new TextDocumentContentChangeEvent(info.document.getContents());
		refreshOpenFile(info, Collections.singletonList(dummyChange), cancelIndicator);
	}

	protected void refreshOpenFile(OpenFileInfo info, Iterable<? extends TextDocumentContentChangeEvent> changes,
			CancelIndicator cancelIndicator) {
		if (!info.resource.isLoaded()) {
			throw new IllegalStateException("trying to refresh a resource that is not loaded: " + info.uri);
		}

		// TODO the following is only necessary for changed files:
		for (ResourceSet resSet : info.containerHandle2ResourceSet.values()) {
			for (Resource res : new ArrayList<>(resSet.getResources())) {
				if (res != info.resource) {
					res.unload();
					resSet.getResources().remove(res);
				}
			}
		}

		for (TextDocumentContentChangeEvent change : changes) {
			Range range = change.getRange();
			int start = range != null ? info.document.getOffSet(range.getStart()) : 0;
			int end = range != null ? info.document.getOffSet(range.getEnd()) : info.document.getContents().length();
			String replacement = change.getText();

			info.document = info.document.applyTextDocumentChanges(Collections.singletonList(change));

			info.resource.update(start, end - start, replacement);
		}

		resolveAndValidateOpenFile(info, cancelIndicator);
	}

	protected void resolveAndValidateOpenFile(OpenFileInfo info, CancelIndicator cancelIndicator) {
		// resolve
		EcoreUtil2.resolveLazyCrossReferences(info.resource, cancelIndicator);
		// validate
		IResourceServiceProvider resourceServiceProvider = languagesRegistry.getResourceServiceProvider(info.uri);
		IResourceValidator resourceValidator = resourceServiceProvider.getResourceValidator();
		// notify client
		List<Issue> issues = resourceValidator.validate(info.resource, CheckMode.ALL, cancelIndicator);
		issueAcceptor.publishDiagnostics(info.uri, issues);
		// update index of open files
		IResourceDescription oldDesc = openFilesIndex.getResourceDescription(info.uri);
		IResourceDescription newDesc = createResourceDescription(info);
		IResourceDescription.Delta delta = resourceDescriptionManager.createDelta(oldDesc, newDesc);
		openFilesIndex.register(delta);
		// notify
		onResourceChanged(delta, cancelIndicator);
	}

	protected IResourceDescription createResourceDescription(OpenFileInfo info) {
		IResourceDescription newDesc = resourceDescriptionManager.getResourceDescription(info.resource);
		// trigger lazy serialization of TModule
		// FIXME why is this required?
		IterableExtensions.forEach(newDesc.getExportedObjects(), eobjDesc -> eobjDesc.getUserDataKeys());
		return newDesc;
	}

	private boolean refreshingAffectedFiles = false;

	protected void onResourceChanged(IResourceDescription.Delta delta, CancelIndicator cancelIndicator) {
		if (refreshingAffectedFiles) {
			return;
		}
		URI changedURI = delta.getUri();
		List<OpenFileInfo> affectedInfos = new ArrayList<>();
		for (OpenFileInfo candidateInfo : openFiles.values()) {
			if (candidateInfo.uri.equals(changedURI)) {
				continue;
			}
			IResourceDescription candidateDesc = openFilesIndex.getResourceDescription(candidateInfo.uri);
			if (resourceDescriptionManager.isAffected(delta, candidateDesc)) {
				affectedInfos.add(candidateInfo);
			}
		}
		try {
			refreshingAffectedFiles = true;
			for (OpenFileInfo affectedInfo : affectedInfos) {
				refreshOpenFile(affectedInfo, cancelIndicator);
			}
		} finally {
			refreshingAffectedFiles = false;
		}
	}
}
