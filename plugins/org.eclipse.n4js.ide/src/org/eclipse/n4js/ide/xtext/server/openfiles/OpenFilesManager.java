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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.lsp4j.TextDocumentContentChangeEvent;
import org.eclipse.n4js.ide.xtext.server.XWorkspaceManager;
import org.eclipse.n4js.ide.xtext.server.concurrent.LSPExecutorService;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.xbase.lib.Pair;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@SuppressWarnings("javadoc")
@Singleton
public class OpenFilesManager {

	@Inject
	private XWorkspaceManager workspaceManager;

	@Inject
	private Provider<OpenFileManager> openFileManagerProvider;

	@Inject
	private LSPExecutorService lspExecutorService;

	@Inject
	private IResourceDescription.Manager resourceDescriptionManager;

	protected final Map<URI, OpenFileManager> openFiles = new HashMap<>();
	protected final ResourceDescriptionsData sharedDirtyState = new ResourceDescriptionsData(Collections.emptyList());

	public synchronized void openFile(URI uri, int version, String content) {
		if (openFiles.containsKey(uri)) {
			return; // FIXME content gets lost in this case!
		}
		OpenFileManager newOFM = createOpenFileManager(uri);
		openFiles.put(uri, newOFM);

		runInOpenFileContext(uri, ofm -> {
			ofm.initOpenFile(version, content, CancelIndicator.NullImpl); // FIXME use proper indicator!
		});
	}

	public synchronized void changeFile(URI uri, int version,
			Iterable<? extends TextDocumentContentChangeEvent> changes, CancelIndicator cancelIndicator) {

		runInOpenFileContext(uri, ofm -> {
			ofm.refreshOpenFile(version, changes, cancelIndicator);
		});
	}

	public synchronized void closeFile(URI uri) {
		runInOpenFileContext(uri, ofm -> null, true);
	}

	public synchronized CompletableFuture<Void> runInOpenFileContext(URI uri, Consumer<OpenFileManager> task) {
		return runInOpenFileContext(uri, ofm -> {
			task.accept(ofm);
			return null;
		});
	}

	public synchronized <T> CompletableFuture<T> runInOpenFileContext(URI uri, Function<OpenFileManager, T> task) {
		return runInOpenFileContext(uri, task, false);
	}

	protected synchronized <T> CompletableFuture<T> runInOpenFileContext(URI uri, Function<OpenFileManager, T> task,
			boolean discardInfoWhenDone) {

		OpenFileManager ofm = openFiles.get(uri);
		if (ofm == null) {
			throw new IllegalArgumentException("no open file found for given URI: " + uri);
		}

		Object queueId = getQueueIdForOpenFileContext(uri);
		return lspExecutorService.submit(queueId, () -> {
			try {
				return task.apply(ofm);
			} finally {
				if (discardInfoWhenDone) {
					// FIXME not working yet! might have gotten more tasks for this open file while running!
					// consider (a) adding a filesBeingClosed set or (b) just say when closing files they disappear
					// immediately (even for currently running/pending tasks in those files' contexts)
					discardOpenFileInfo(uri);
				}
			}
		});
	}

	protected Object getQueueIdForOpenFileContext(URI uri) {
		return Pair.of(OpenFilesManager.class, uri);
	}

	protected OpenFileManager createOpenFileManager(URI uri) {
		@SuppressWarnings("restriction")
		String projectName = workspaceManager.getProjectConfig(uri).getName();
		OpenFileManager ofm = openFileManagerProvider.get();
		ofm.init(uri, projectName);
		return ofm;
	}

	protected synchronized void discardOpenFileInfo(URI uri) {
		openFiles.remove(uri);
		sharedDirtyState.removeDescription(uri);
		// TODO what if a task for the file being closed is currently in progress? (partially solved, see above)
		// TODO closing a file may lead to a change in other open files (because they will switch from using dirty state
		// to using persisted state for the file being closed)
		// TODO what about publishing diagnostics, here? (not required for VSCode because it sends a content change
		// event, back to original content, before closing a file with unsaved changes)
	}

	/**
	 * If the given origin resource set is one that is used to process an open file, then this method will return the
	 * resource set responsible for containing a resource with the given uri; otherwise <code>null</code> is returned.
	 * Might return the origin resource set itself or might return a newly created resource set.
	 */
	// FIXME find better solution for this:
	public synchronized ResourceSet getOrCreateResourceSetForURI(ResourceSet origin, URI uri) {
		OpenFileManager ofm = findOpenFileManager(origin);
		if (ofm == null) {
			return null; // origin is not a resource set related to an open file
		}
		return ofm.getResourceSetForURI(uri, true);
	}

	protected synchronized OpenFileManager findOpenFileManager(ResourceSet resourceSet) {
		return openFiles.values().stream()
				.filter(ofm -> ofm.containerHandle2ResourceSet.containsValue(resourceSet))
				.findAny().orElse(null);
	}

	public ResourceDescriptionsData getSharedDirtyState() {
		return sharedDirtyState; // FIXME consider making return value immutable (just create a copy?)
	}

	public synchronized void updateSharedDirtyState(URI uri, IResourceDescription newDesc,
			CancelIndicator cancelIndicator) {
		IResourceDescription oldDesc = sharedDirtyState.getResourceDescription(uri);
		IResourceDescription.Delta delta = resourceDescriptionManager.createDelta(oldDesc, newDesc);
		sharedDirtyState.register(delta);
		// notify
		onResourceChanged(delta, cancelIndicator);
	}

	private boolean refreshingAffectedOpenFiles = false; // FIXME does not work once actual refresh happens in tasks

	protected synchronized void onResourceChanged(IResourceDescription.Delta delta, CancelIndicator cancelIndicator) {
		if (refreshingAffectedOpenFiles) {
			return;
		}
		URI changedURI = delta.getUri();
		List<OpenFileManager> affectedOFMs = new ArrayList<>();
		for (OpenFileManager candidateOFM : openFiles.values()) {
			URI candidateURI = candidateOFM.getURI();
			if (candidateURI.equals(changedURI)) {
				continue;
			}
			IResourceDescription candidateDesc = sharedDirtyState.getResourceDescription(candidateURI);
			if (resourceDescriptionManager.isAffected(delta, candidateDesc)) {
				affectedOFMs.add(candidateOFM);
			}
		}
		try {
			refreshingAffectedOpenFiles = true;
			for (OpenFileManager affectedOFM : affectedOFMs) {
				affectedOFM.refreshOpenFile(cancelIndicator);
			}
		} finally {
			refreshingAffectedOpenFiles = false;
		}
	}
}
