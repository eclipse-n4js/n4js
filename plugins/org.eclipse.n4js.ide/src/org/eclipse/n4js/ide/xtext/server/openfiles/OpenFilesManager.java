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
		OpenFileContext newOFC = createOpenFileContext(uri, false);
		openFiles.put(uri, newOFC);

		runInOpenFileContext(uri, "openFile", (ofc, ci) -> {
			ofc.initOpenFile(version, content, ci);
		});
	}

	public synchronized void changeFile(URI uri, int version,
			Iterable<? extends TextDocumentContentChangeEvent> changes) {

		runInOpenFileContext(uri, "changeFile", (ofc, ci) -> {
			ofc.refreshOpenFile(version, changes, ci);
		});
	}

	public synchronized CompletableFuture<Void> closeAll() {
		List<CompletableFuture<Void>> cfs = new ArrayList<>(openFiles.size());
		for (URI uri : new ArrayList<>(openFiles.keySet())) {
			cfs.add(closeFile(uri));
		}
		return CompletableFuture.allOf(cfs.toArray(new CompletableFuture<?>[cfs.size()]));
	}

	public synchronized CompletableFuture<Void> closeFile(URI uri) {
		// To allow running/pending tasks in the context of the given URI's file to complete normally, we put the call
		// to #discardOpenFileInfo() on the queue (note: this does apply to tasks being submitted after this method
		// returns and before #discardOpenFileInfo() is invoked).
		// TODO reconsider sequence when closing files
		return runInOpenFileContext(uri, "closeFile", (ofc, ci) -> {
			discardOpenFileInfo(uri);
		});
	}

	/** Tries to run the given task in the context of an open file, falling back to a temporary file if necessary. */
	public synchronized <T> CompletableFuture<T> runInOpenOrTemporaryFileContext(URI uri, String description,
			BiFunction<OpenFileContext, CancelIndicator, T> task) {
		if (isOpen(uri)) {
			return runInOpenFileContext(uri, description, task);
		} else {
			return runInTemporaryFileContext(uri, description, task);
		}
	}

	public synchronized CompletableFuture<Void> runInOpenFileContext(URI uri, String description,
			BiConsumer<OpenFileContext, CancelIndicator> task) {

		return runInOpenFileContext(uri, description, (ofc, ci) -> {
			task.accept(ofc, ci);
			return null;
		});
	}

	public synchronized <T> CompletableFuture<T> runInOpenFileContext(URI uri, String description,
			BiFunction<OpenFileContext, CancelIndicator, T> task) {

		OpenFileContext ofc = openFiles.get(uri);
		if (ofc == null) {
			throw new IllegalArgumentException("no open file found for given URI: " + uri);
		}

		Object queueId = getQueueIdForOpenFileContext(uri);
		String descriptionWithContext = description + " [" + uri.lastSegment() + "]";
		return lspExecutorService.submit(queueId, descriptionWithContext, ci -> {
			return task.apply(ofc, ci);
		});
	}

	public synchronized <T> CompletableFuture<T> runInTemporaryFileContext(URI uri, String description,
			BiFunction<OpenFileContext, CancelIndicator, T> task) {

		OpenFileContext tempOFC = createOpenFileContext(uri, true);

		Object queueId = Pair.of(getQueueIdForOpenFileContext(uri), "temporary");
		String descriptionWithContext = description + " (temporary) [" + uri.lastSegment() + "]";
		return lspExecutorService.submit(queueId, descriptionWithContext, ci -> {
			tempOFC.initOpenFile(ci);
			return task.apply(tempOFC, ci);
		});
	}

	protected Object getQueueIdForOpenFileContext(URI uri) {
		return Pair.of(OpenFilesManager.class, uri);
	}

	protected OpenFileContext createOpenFileContext(URI uri, boolean isTemporary) {
		OpenFileContext ofc = openFileContextProvider.get();
		ofc.initialize(this, uri, isTemporary, persistedState,
				!isTemporary ? sharedDirtyState : new ResourceDescriptionsData(Collections.emptyList()));
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
	 * Tells whether the given resource set is one that is used to process an open file managed by this
	 * {@link OpenFilesManager}.
	 */
	public synchronized boolean isOpenFileResourceSet(ResourceSet origin) {
		OpenFileContext ofc = findOpenFileContext(origin);
		return ofc != null;
	}

	protected synchronized OpenFileContext findOpenFileContext(ResourceSet resourceSet) {
		return openFiles.values().stream()
				.filter(ofc -> ofc.getResourceSet() == resourceSet)
				.findAny().orElse(null);
	}

	public synchronized void updatePersistedState(Map<String, ResourceDescriptionsData> changedContainers,
			Set<String> removedContainerHandles) {
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
			runInOpenFileContext(currURI, "updatePersistedState in open file", (ofc, ci) -> {
				ofc.onPersistedStateChanged(changed, removed, ci);
			});
		}
	}

	protected synchronized void updateSharedDirtyState(IResourceDescription newDesc) {
		// update my dirty state instance
		URI newDescURI = newDesc.getURI();
		sharedDirtyState.addDescription(newDescURI, newDesc);
		// update dirty state instances in the context of each open file (except the one that caused the change)
		for (URI currURI : openFiles.keySet()) {
			if (currURI.equals(newDescURI)) {
				continue;
			}
			runInOpenFileContext(currURI, "updateSharedDirtyState in open file", (ofc, ci) -> {
				ofc.onDirtyStateChanged(newDesc, ci);
			});
		}
	}
}
