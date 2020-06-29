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
import org.eclipse.lsp4j.TextDocumentContentChangeEvent;
import org.eclipse.n4js.ide.xtext.server.XDocument;
import org.eclipse.n4js.ide.xtext.server.concurrent.ConcurrentIssueRegistry;
import org.eclipse.n4js.ide.xtext.server.concurrent.LSPExecutorService;
import org.eclipse.n4js.ide.xtext.server.util.CancelIndicatorUtil;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Pair;

import com.google.common.collect.ImmutableSet;
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

	protected final ThreadLocal<OpenFileContext> currentContext = new ThreadLocal<>();

	protected final Map<String, ResourceDescriptionsData> persistedStateDescriptions = new HashMap<>();
	protected final Map<String, ImmutableSet<String>> persistedStateVisibleContainers = new HashMap<>();
	protected ContainerStructureSnapshot persistedStateContainerStructure = new ContainerStructureSnapshot();

	protected final ResourceDescriptionsData sharedDirtyState = new ResourceDescriptionsData(Collections.emptyList());

	protected final List<IOpenFilesListener> listeners = new ArrayList<>();

	protected ConcurrentIssueRegistry issueRegistry = null;

	public interface IOpenFilesListener {
		/** Invoked whenever an open file was resolved, validated, etc. Invoked in the given open file context. */
		public void didRefreshOpenFile(OpenFileContext ofc, CancelIndicator ci);
	}

	public ConcurrentIssueRegistry getIssueRegistry() {
		return issueRegistry; // no need for synchronization
	}

	/** Iff set to a non-<code>null</code> registry, open files will register their issues with that registry. */
	public synchronized void setIssueRegistry(ConcurrentIssueRegistry issueRegistry) {
		this.issueRegistry = issueRegistry;
	}

	public synchronized boolean isOpen(URI uri) {
		return openFiles.containsKey(uri);
	}

	public synchronized XDocument getOpenDocument(URI uri) {
		OpenFileContext ofc = openFiles.get(uri);
		if (ofc != null) {
			// note: since we only obtain an object reference to an immutable data structure (XDocument) we do not need
			// to execute the following in the open file context:
			return ofc.getDocument();
		}
		return null;
	}

	public synchronized void openFile(URI uri, int version, String content) {
		if (openFiles.containsKey(uri)) {
			return;
		}
		OpenFileContext newOFC = createOpenFileContext(uri, false);
		openFiles.put(uri, newOFC);

		runInOpenFileContextVoid(uri, "openFile", (ofc, ci) -> {
			ofc.initOpenFile(version, content, ci);
		});
	}

	public synchronized void changeFile(URI uri, int version,
			Iterable<? extends TextDocumentContentChangeEvent> changes) {

		// cancel current tasks for this open file context (they are now out-dated, anyway)
		Object queueId = getQueueIdForOpenFileContext(uri, false);
		lspExecutorService.cancelAll(queueId);

		// refresh the open file context
		runInOpenFileContextVoid(uri, "changeFile", (ofc, ci) -> {
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
		// FIXME GH-1774 reconsider sequence when closing files
		return runInOpenFileContextVoid(uri, "closeFile", (ofc, ci) -> {
			discardOpenFileInfo(uri);
		});
	}

	/** Tries to run the given task in the context of an open file, falling back to a temporary file if necessary. */
	public synchronized <T> CompletableFuture<T> runInOpenOrTemporaryFileContext(URI uri, String description,
			BiFunction<OpenFileContext, CancelIndicator, T> task) {

		if (isOpen(uri)) {
			return runInOpenFileContext(uri, description, task);
		} else {
			return runInTemporaryFileContext(uri, description, true, task);
		}
	}

	public synchronized CompletableFuture<Void> runInOpenFileContextVoid(URI uri, String description,
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

		String descriptionWithContext = description + " [" + uri.lastSegment() + "]";
		return doSubmitTask(ofc, descriptionWithContext, task);
	}

	/**
	 * Creates a temporary open file context for the file with the given URI, initializes it, and executes the given
	 * task, <em>without</em> interfering with a possibly existing ordinary open file context for 'uri' or with other
	 * possibly existing temporary open file contexts for 'uri'.
	 * <p>
	 * The temporary open file context is not retained over a longer period of time; the given task will be the only
	 * task that will ever be executed in this temporary context.
	 * <p>
	 * Note that instead of using this method, the caller might simply create a new resource set from scratch, configure
	 * it with a {@link #createLiveScopeIndex() live scope index}, and synchronously perform any desired computation
	 * there. The intention of this method is to provide means to easily perform such computations in temporary contexts
	 * with a consistent setup/configuration and with a similar API as compared to computations in the context of
	 * actually opened files.
	 */
	public synchronized <T> CompletableFuture<T> runInTemporaryFileContext(URI uri, String description,
			boolean resolveAndValidate, BiFunction<OpenFileContext, CancelIndicator, T> task) {
		return runInTemporaryFileContext(uri, description, resolveAndValidate, CancelIndicator.NullImpl, task);
	}

	/**
	 * Same as {@link #runInTemporaryFileContext(URI, String, BiFunction)}, but accepts an outer cancel indicator as
	 * argument as an additional source of cancellation. The implementation of the given function 'task' should only use
	 * the cancel indicator passed into 'task' (it is a {@link CancelIndicatorUtil#combine(List) combination} of the
	 * given outer cancel indicator and other sources of cancellation).
	 */
	public synchronized <T> CompletableFuture<T> runInTemporaryFileContext(URI uri, String description,
			boolean resolveAndValidate, CancelIndicator outerCancelIndicator,
			BiFunction<OpenFileContext, CancelIndicator, T> task) {

		OpenFileContext tempOFC = createOpenFileContext(uri, true);

		String descriptionWithContext = description + " (temporary) [" + uri.lastSegment() + "]";
		return doSubmitTask(tempOFC, descriptionWithContext, (_tempOFC, ciFromExecutor) -> {
			CancelIndicator ciCombined = CancelIndicatorUtil.combine(outerCancelIndicator, ciFromExecutor);
			_tempOFC.initOpenFile(resolveAndValidate, ciCombined);
			return task.apply(_tempOFC, ciCombined);
		});
	}

	protected <T> CompletableFuture<T> doSubmitTask(OpenFileContext ofc, String description,
			BiFunction<OpenFileContext, CancelIndicator, T> task) {

		Object queueId = getQueueIdForOpenFileContext(ofc.getURI(), ofc.isTemporary());
		return lspExecutorService.submit(queueId, description, ci -> {
			try {
				currentContext.set(ofc);
				return task.apply(ofc, ci);
			} finally {
				currentContext.set(null);
			}
		});
	}

	protected Object getQueueIdForOpenFileContext(URI uri, boolean isTemporary) {
		if (isTemporary) {
			// for every temporary open file only a single task is being submitted that is supposed to be independent of
			// all other tasks (in particular, we can have several temporary open files for the same URI that should all
			// be independent of one another), so we use "new Object()" as the actual ID here:
			return Pair.of(OpenFilesManager.class, new Object());
		}
		return Pair.of(OpenFilesManager.class, uri);
	}

	protected OpenFileContext createOpenFileContext(URI uri, boolean isTemporary) {
		OpenFileContext ofc = openFileContextProvider.get();
		ResourceDescriptionsData index = isTemporary ? createPersistedStateIndex() : createLiveScopeIndex();
		ofc.initialize(this, uri, isTemporary, index, persistedStateContainerStructure);
		return ofc;
	}

	protected synchronized void discardOpenFileInfo(URI uri) {
		openFiles.remove(uri);
		sharedDirtyState.removeDescription(uri);
		if (issueRegistry != null) {
			issueRegistry.clearIssuesOfDirtyState(uri);
		}
		// TODO GH-1774 closing a file may lead to a change in other open files (because they will switch from using
		// dirty state to using persisted state for the file being closed)
	}

	/**
	 * If the thread invoking this method {@link #runInOpenFileContext(URI, String, BiFunction) currently runs in an
	 * open file context}, that context is returned. Otherwise returns <code>null</code>.
	 * <p>
	 * Corresponds to {@link Thread#currentThread()}.
	 */
	public OpenFileContext currentContext() {
		return currentContext.get();
	}

	protected synchronized ResourceDescriptionsData createPersistedStateIndex() {
		// TODO GH-1774 performance? (consider maintaining a ResourceDescriptionsData in addition to persistedState)
		return new ResourceDescriptionsData(IterableExtensions.flatMap(persistedStateDescriptions.values(),
				ResourceDescriptionsData::getAllResourceDescriptions));
	}

	/** Creates an index containing the persisted state shadowed by the dirty state of all open files. */
	public synchronized ResourceDescriptionsData createLiveScopeIndex() {
		ResourceDescriptionsData result = createPersistedStateIndex();
		sharedDirtyState.getAllResourceDescriptions()
				.forEach(desc -> result.addDescription(desc.getURI(), desc));
		return result;
	}

	public synchronized void updatePersistedState(
			Map<String, ResourceDescriptionsData> changedDescriptions,
			Map<String, ImmutableSet<String>> changedVisibleContainers,
			Set<String> removedContainerHandles) {

		ContainerStructureSnapshot oldCS = persistedStateContainerStructure;

		// compute modification info
		List<IResourceDescription> changed = new ArrayList<>();
		Set<URI> removed = new HashSet<>();
		for (Entry<String, ResourceDescriptionsData> entry : changedDescriptions.entrySet()) {
			String containerHandle = entry.getKey();
			ResourceDescriptionsData newData = entry.getValue();

			ResourceDescriptionsData oldContainer = persistedStateDescriptions.get(containerHandle);
			if (oldContainer != null) {
				for (IResourceDescription desc : oldContainer.getAllResourceDescriptions()) {
					URI descURI = desc.getURI();
					if (!openFiles.containsKey(descURI)) {
						removed.add(descURI);
					}
				}
			}
			for (IResourceDescription desc : newData.getAllResourceDescriptions()) {
				URI descURI = desc.getURI();
				if (!openFiles.containsKey(descURI)) {
					changed.add(desc);
					removed.remove(descURI);
				}
			}
		}

		// update my persisted state instance
		for (Entry<String, ResourceDescriptionsData> entry : changedDescriptions.entrySet()) {
			String containerHandle = entry.getKey();
			ResourceDescriptionsData newData = entry.getValue();
			persistedStateDescriptions.put(containerHandle, newData.copy());
		}
		for (Entry<String, ImmutableSet<String>> entry : changedVisibleContainers.entrySet()) {
			String containerHandle = entry.getKey();
			ImmutableSet<String> newVisibleContainers = entry.getValue();
			persistedStateVisibleContainers.put(containerHandle, newVisibleContainers);
		}
		for (String removedContainerHandle : removedContainerHandles) {
			persistedStateDescriptions.remove(removedContainerHandle);
			persistedStateVisibleContainers.remove(removedContainerHandle);
		}
		persistedStateContainerStructure = ContainerStructureSnapshot.create(persistedStateDescriptions,
				persistedStateVisibleContainers);

		// update persisted state instances in the context of each open file
		if (Iterables.isEmpty(changed) && removed.isEmpty()
				&& persistedStateContainerStructure.equals(oldCS)) {
			return;
		}
		for (URI currURI : openFiles.keySet()) {
			runInOpenFileContextVoid(currURI, "updatePersistedState in open file", (ofc, ci) -> {
				ofc.onPersistedStateChanged(changed, removed, persistedStateContainerStructure, ci);
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
			runInOpenFileContextVoid(currURI, "updateSharedDirtyState in open file", (ofc, ci) -> {
				ofc.onDirtyStateChanged(newDesc, ci);
			});
		}
	}

	public synchronized void addListener(IOpenFilesListener l) {
		listeners.add(l);
	}

	public synchronized void removeListener(IOpenFilesListener l) {
		listeners.remove(l);
	}

	protected /* NOT synchronized */ void onDidRefreshOpenFile(OpenFileContext ofc, CancelIndicator ci) {
		if (ofc.isTemporary()) {
			return; // temporarily opened files do not send out events to open file listeners
		}
		notifyOpenFileListeners(ofc, ci);
	}

	protected /* NOT synchronized */ void notifyOpenFileListeners(OpenFileContext ofc, CancelIndicator ci) {
		List<IOpenFilesListener> ls;
		synchronized (this) {
			ls = new ArrayList<>(listeners);
		}
		for (IOpenFilesListener l : ls) {
			l.didRefreshOpenFile(ofc, ci);
		}
	}
}
