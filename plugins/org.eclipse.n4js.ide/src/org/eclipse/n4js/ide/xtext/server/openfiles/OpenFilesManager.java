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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.lsp4j.TextDocumentContentChangeEvent;
import org.eclipse.n4js.ide.xtext.server.XWorkspaceManager;
import org.eclipse.n4js.ide.xtext.server.concurrent.LSPExecutorService;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.impl.ChunkedResourceDescriptions;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.xbase.lib.Pair;

import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@SuppressWarnings("javadoc")
@Singleton
public class OpenFilesManager {

	@Inject
	private XWorkspaceManager workspaceManager;

	@Inject
	private Provider<OpenFileContext> openFileContextProvider;

	@Inject
	private LSPExecutorService lspExecutorService;

	protected final Map<URI, OpenFileContext> openFiles = new HashMap<>();

	protected final ChunkedResourceDescriptions persistedState = new ChunkedResourceDescriptions();

	protected final ResourceDescriptionsData sharedDirtyState = new ResourceDescriptionsData(Collections.emptyList());

	public synchronized boolean isOpen(URI uri) {
		return openFiles.containsKey(uri);
	}

	public synchronized void openFile(URI uri, int version, String content) {
		if (openFiles.containsKey(uri)) {
			return; // FIXME content gets lost in this case!
		}
		OpenFileContext newOFC = createOpenFileContext(uri);
		openFiles.put(uri, newOFC);

		runInOpenFileContext(uri, (ofc, ci) -> {
			ofc.initOpenFile(version, content, ci);
		});
	}

	public synchronized void changeFile(URI uri, int version,
			Iterable<? extends TextDocumentContentChangeEvent> changes) {

		runInOpenFileContext(uri, (ofc, ci) -> {
			ofc.refreshOpenFile(version, changes, ci);
		});
	}

	public synchronized void closeFile(URI uri) {
		// To allow running/pending tasks in the context of the given URI's file to complete normally, we put the call
		// to #discardOpenFileInfo() on the queue (note: this does apply to tasks being submitted after this method
		// returns and before #discardOpenFileInfo() is invoked).
		// TODO reconsider sequence when closing files
		runInOpenFileContext(uri, (ofc, ci) -> {
			discardOpenFileInfo(uri);
		});
	}

	public synchronized CompletableFuture<Void> runInOpenFileContext(URI uri,
			BiConsumer<OpenFileContext, CancelIndicator> task) {

		return runInOpenFileContext(uri, (ofc, ci) -> {
			task.accept(ofc, ci);
			return null;
		});
	}

	public synchronized <T> CompletableFuture<T> runInOpenFileContext(URI uri,
			BiFunction<OpenFileContext, CancelIndicator, T> task) {

		OpenFileContext ofc = openFiles.get(uri);
		if (ofc == null) {
			throw new IllegalArgumentException("no open file found for given URI: " + uri);
		}

		Object queueId = getQueueIdForOpenFileContext(uri);
		return lspExecutorService.submit(queueId, ci -> {
			return task.apply(ofc, ci);
		});
	}

	protected Object getQueueIdForOpenFileContext(URI uri) {
		return Pair.of(OpenFilesManager.class, uri);
	}

	protected OpenFileContext createOpenFileContext(URI uri) {
		if (persistedState.isEmpty()) {
			// FIXME clean up when there's a top-level aggregator class for OpenFilesManager and the LSP builder
			updatePersistedState(workspaceManager.getFullIndex(), Collections.emptySet(), CancelIndicator.NullImpl);
		}
		OpenFileContext ofc = openFileContextProvider.get();
		ofc.initialize(this, uri, persistedState, sharedDirtyState);
		return ofc;
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
	// FIXME find better solution for this (it's dirty to leak a context's resource set to the outside)
	public synchronized ResourceSet getOrCreateResourceSetForURI(ResourceSet origin, URI uri) {
		OpenFileContext ofc = findOpenFileContext(origin);
		if (ofc == null) {
			return null; // origin is not a resource set related to an open file
		}
		return ofc.getResourceSet();
	}

	protected synchronized OpenFileContext findOpenFileContext(ResourceSet resourceSet) {
		return openFiles.values().stream()
				.filter(ofc -> ofc.getResourceSet() == resourceSet)
				.findAny().orElse(null);
	}

	public synchronized void updatePersistedState(Map<String, ResourceDescriptionsData> changedContainers,
			Set<String> removedContainerHandles, CancelIndicator cancelIndicator) {
		// compute modification info
		List<IResourceDescription> changed = new ArrayList<>();
		Set<URI> removed = new HashSet<>();
		for (Entry<String, ResourceDescriptionsData> entry : changedContainers.entrySet()) {
			String containerHandle = entry.getKey();
			ResourceDescriptionsData newData = entry.getValue();

			ResourceDescriptionsData oldContainer = persistedState.getContainer(containerHandle);
			if (oldContainer != null) {
				removed.addAll(oldContainer.getAllURIs());
			}
			for (IResourceDescription desc : newData.getAllResourceDescriptions()) {
				if (!openFiles.containsKey(desc.getURI())) {
					changed.add(desc);
				}
			}
			removed.removeAll(newData.getAllURIs());
		}
		// update my persisted state instance
		for (Entry<String, ResourceDescriptionsData> entry : changedContainers.entrySet()) {
			String containerHandle = entry.getKey();
			ResourceDescriptionsData newData = entry.getValue();
			persistedState.setContainer(containerHandle, newData.copy());
		}
		for (String removedContainerHandle : removedContainerHandles) {
			persistedState.removeContainer(removedContainerHandle);
		}
		// update persisted state instances in the context of each open file
		if (Iterables.isEmpty(changed) && removed.isEmpty()) {
			return;
		}
		for (URI currURI : openFiles.keySet()) {
			runInOpenFileContext(currURI, (ofc, ci) -> {
				ofc.onPersistedStateChanged(changed, removed, ci);
			});
		}
	}

	protected synchronized void updateSharedDirtyState(IResourceDescription newDesc, CancelIndicator cancelIndicator) {
		// update my dirty state instance
		URI newDescURI = newDesc.getURI();
		sharedDirtyState.addDescription(newDescURI, newDesc);
		// update dirty state instances in the context of each open file (except the one that caused the change)
		for (URI currURI : openFiles.keySet()) {
			if (currURI.equals(newDescURI)) {
				continue;
			}
			runInOpenFileContext(currURI, (ofc, ci) -> {
				ofc.onDirtyStateChanged(newDesc, ci);
			});
		}
	}
}
