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

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.TextDocumentContentChangeEvent;
import org.eclipse.xtext.EcoreUtil2;
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
	private IResourceServiceProvider.Registry languagesRegistry;

	@Inject
	private XWorkspaceManager workspaceManager;

	protected final Map<URI, OpenFileInfo> openFiles = new HashMap<>();

	protected static class OpenFileInfo {

		final URI uri;
		final ResourceSet resourceSet;
		final XtextResource resource;
		XDocument document;

		public OpenFileInfo(URI uri, ResourceSet resourceSet, XtextResource resource) {
			this.uri = uri;
			this.resourceSet = resourceSet;
			this.resource = resource;
			this.document = null;
		}
	}

	public static class MyChunkedResourceDescriptions extends ChunkedResourceDescriptions {

		public MyChunkedResourceDescriptions(ConcurrentHashMap<String, ResourceDescriptionsData> initialData,
				ResourceSet resourceSet) {
			super(initialData, resourceSet);
			this.chunk2resourceDescriptions = initialData; // avoid creation of a copy!
		}
	}

	// FIXME find better solution for this:
	public boolean isResourceSetOfOpenFile(ResourceSet resourceSet) {
		return openFiles.values().stream().anyMatch(ofi -> ofi.resourceSet == resourceSet);
	}

	public void openFile(URI uri, int version, String content) {
		if (openFiles.containsKey(uri)) {
			return;
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
		// TODO what about publishing diagnostics, here?
	}

	protected OpenFileInfo createNewOpenFileInfo(URI uri) {
		ResourceSet resourceSet = createNewResourceSet();
		XtextResource resource = (XtextResource) resourceSet.createResource(uri);
		OpenFileInfo info = new OpenFileInfo(uri, resourceSet, resource);
		return info;
	}

	protected ResourceSet createNewResourceSet() {
		XtextResourceSet result = resourceSetProvider.get();

		ProjectDescription projectDescription = new ProjectDescription();
		projectDescription.setName("Test");

		// result.setURIResourceMap(uriResourceMap);
		projectDescription.attachToEmfObject(result); // required by ChunkedResourceDescriptions
		// new WorkspaceAwareResourceLocator(result, workspaceManager);
		ConcurrentHashMap<String, ResourceDescriptionsData> concurrentMap = (ConcurrentHashMap<String, ResourceDescriptionsData>) workspaceManager
				.getFullIndex();
		MyChunkedResourceDescriptions index = new MyChunkedResourceDescriptions(concurrentMap, result);
		// ResourceDescriptionsData newIndex = new XIndexState().getResourceDescriptions();
		// index.setContainer("Test", newIndex);
		// externalContentSupport.configureResourceSet(result, workspaceManager.getOpenedDocumentsContentProvider());

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

	protected void refreshOpenFile(OpenFileInfo info, Iterable<? extends TextDocumentContentChangeEvent> changes,
			CancelIndicator cancelIndicator) {
		if (!info.resource.isLoaded()) {
			throw new IllegalStateException("trying to refresh a resource that is not loaded: " + info.uri);
		}

		// TODO the following is only necessary for changed files:
		for (Resource res : new ArrayList<>(info.resourceSet.getResources())) {
			if (res != info.resource) {
				res.unload();
				info.resourceSet.getResources().remove(res);
			}
		}

		for (TextDocumentContentChangeEvent change : changes) {
			Range range = change.getRange();
			int start = info.document.getOffSet(range.getStart());
			int end = info.document.getOffSet(range.getEnd());
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
	}

	protected void onResourceChanged(URI uri) {

	}
}
