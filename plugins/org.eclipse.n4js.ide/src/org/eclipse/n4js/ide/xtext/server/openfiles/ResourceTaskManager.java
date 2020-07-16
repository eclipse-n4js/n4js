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
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import org.eclipse.emf.common.util.URI;
import org.eclipse.lsp4j.TextDocumentContentChangeEvent;
import org.eclipse.n4js.ide.xtext.server.XDocument;
import org.eclipse.n4js.ide.xtext.server.concurrent.ConcurrentIssueRegistry;
import org.eclipse.n4js.ide.xtext.server.concurrent.QueuedExecutorService;
import org.eclipse.n4js.ide.xtext.server.util.CancelIndicatorUtil;
import org.eclipse.n4js.xtext.workspace.IProjectConfigSnapshot;
import org.eclipse.n4js.xtext.workspace.IWorkspaceConfigSnapshot;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Pair;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Iterables;
import com.google.common.collect.SetMultimap;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class ResourceTaskManager {

	@Inject
	private Provider<ResourceTaskContext> openFileContextProvider;

	@Inject
	private QueuedExecutorService queuedExecutorService;

	@Inject
	private ConcurrentIssueRegistry issueRegistry;

	protected final Map<URI, ResourceTaskContext> uri2RTCs = new HashMap<>();

	protected final ThreadLocal<ResourceTaskContext> currentContext = new ThreadLocal<>();

	protected final Map<String, ResourceDescriptionsData> persistedStateDescriptions = new HashMap<>();
	protected final ResourceDescriptionsData sharedDirtyState = new ResourceDescriptionsData(Collections.emptyList());

	protected ImmutableSetMultimap<String, URI> containerHandle2URIs = ImmutableSetMultimap.of();
	protected IWorkspaceConfigSnapshot workspaceConfig = null;

	protected final List<IResourceTaskListener> listeners = new CopyOnWriteArrayList<>();

	public interface IResourceTaskListener {
		/** Invoked whenever an open file was resolved, validated, etc. Invoked in the given open file context. */
		public void didRefreshOpenFile(ResourceTaskContext rtc, CancelIndicator ci);
	}

	public ConcurrentIssueRegistry getIssueRegistry() {
		return issueRegistry; // no need for synchronization
	}

	/** Iff set to a non-<code>null</code> registry, open files will register their issues with that registry. */
	public synchronized void setIssueRegistry(ConcurrentIssueRegistry issueRegistry) {
		this.issueRegistry = issueRegistry;
	}

	public synchronized boolean isOpen(URI uri) {
		return uri2RTCs.containsKey(uri);
	}

	public synchronized XDocument getOpenDocument(URI uri) {
		ResourceTaskContext rtc = uri2RTCs.get(uri);
		if (rtc != null) {
			// note: since we only obtain an object reference to an immutable data structure (XDocument) we do not need
			// to execute the following in the open file context:
			return rtc.getDocument();
		}
		return null;
	}

	public synchronized void createContext(URI uri, int version, String content) {
		if (uri2RTCs.containsKey(uri)) {
			return;
		}
		ResourceTaskContext newContext = createContext(uri, false);
		uri2RTCs.put(uri, newContext);

		runInExistingContextVoid(uri, "createContext", (rtc, ci) -> {
			rtc.initContext(version, content, ci);
		});
	}

	public synchronized void changeSourceTextOfExistingContext(URI uri, int version,
			Iterable<? extends TextDocumentContentChangeEvent> changes) {

		// cancel current tasks for this context (they are now out-dated, anyway)
		Object queueId = getQueueIdForContext(uri, false);
		queuedExecutorService.cancelAll(queueId);

		// refresh the context
		runInExistingContextVoid(uri, "changeSourceTextOfExistingContext", (rtc, ci) -> {
			rtc.refreshContext(version, changes, ci);
		});
	}

	public synchronized CompletableFuture<Void> closeAll() {
		List<CompletableFuture<Void>> cfs = new ArrayList<>(uri2RTCs.size());
		for (URI uri : new ArrayList<>(uri2RTCs.keySet())) {
			cfs.add(closeContext(uri));
		}
		return CompletableFuture.allOf(cfs.toArray(new CompletableFuture<?>[cfs.size()]));
	}

	public synchronized CompletableFuture<Void> closeContext(URI uri) {
		// To allow running/pending tasks in the context of the given URI's file to complete normally, we put the call
		// to #discardOpenFileInfo() on the queue (note: this does not apply to tasks being submitted after this method
		// returns and before #discardOpenFileInfo() is invoked).
		return runInExistingContextVoid(uri, "closeContext", (rtc, ci) -> {
			discardContextInfo(uri);
		});
	}

	/** Tries to run the given task in an existing context, falling back to a temporary context if necessary. */
	public synchronized <T> CompletableFuture<T> runInExistingOrTemporaryContext(URI uri, String description,
			BiFunction<ResourceTaskContext, CancelIndicator, T> task) {

		if (isOpen(uri)) {
			return runInExistingContext(uri, description, task);
		} else {
			return runInTemporaryContext(uri, description, true, task);
		}
	}

	public synchronized CompletableFuture<Void> runInExistingContextVoid(URI uri, String description,
			BiConsumer<ResourceTaskContext, CancelIndicator> task) {

		return runInExistingContext(uri, description, (rtc, ci) -> {
			task.accept(rtc, ci);
			return null;
		});
	}

	public synchronized <T> CompletableFuture<T> runInExistingContext(URI uri, String description,
			BiFunction<ResourceTaskContext, CancelIndicator, T> task) {

		ResourceTaskContext rtc = uri2RTCs.get(uri);
		if (rtc == null) {
			throw new IllegalArgumentException("no existing context found for given URI: " + uri);
		}

		String descriptionWithContext = description + " [" + uri.lastSegment() + "]";
		return doSubmitTask(rtc, descriptionWithContext, task);
	}

	/**
	 * Creates a temporary context for the file with the given URI, initializes it, and executes the given task,
	 * <em>without</em> interfering with other possibly existing or temporary contexts for that 'uri'.
	 * <p>
	 * The temporary context is not retained over a longer period of time; the given task will be the only task that
	 * will ever be executed in this temporary context.
	 * <p>
	 * Note that instead of using this method, the caller might simply create a new resource set from scratch, configure
	 * it with a {@link #createLiveScopeIndex() live scope index}, and synchronously perform any desired computation
	 * there. The intention of this method is to provide means to easily perform such computations in temporary contexts
	 * with a consistent setup/configuration and with a similar API as compared to computations in the context of
	 * actually opened files.
	 */
	public synchronized <T> CompletableFuture<T> runInTemporaryContext(URI uri, String description,
			boolean resolveAndValidate, BiFunction<ResourceTaskContext, CancelIndicator, T> task) {
		return runInTemporaryContext(uri, description, resolveAndValidate, CancelIndicator.NullImpl, task);
	}

	/**
	 * Same as {@link #runInTemporaryContext(URI, String, boolean, BiFunction)}, but accepts an outer cancel indicator
	 * as argument as an additional source of cancellation. The implementation of the given function 'task' should only
	 * use the cancel indicator passed into 'task' (it is a {@link CancelIndicatorUtil#combine(List) combination} of the
	 * given outer cancel indicator and other sources of cancellation).
	 */
	public synchronized <T> CompletableFuture<T> runInTemporaryContext(URI uri, String description,
			boolean resolveAndValidate, CancelIndicator outerCancelIndicator,
			BiFunction<ResourceTaskContext, CancelIndicator, T> task) {

		ResourceTaskContext tempContext = createContext(uri, true);

		String descriptionWithContext = description + " (temporary) [" + uri.lastSegment() + "]";
		return doSubmitTask(tempContext, descriptionWithContext, (_tempContext, ciFromExecutor) -> {
			CancelIndicator ciCombined = CancelIndicatorUtil.combine(outerCancelIndicator, ciFromExecutor);
			_tempContext.initContext(resolveAndValidate, ciCombined);
			return task.apply(_tempContext, ciCombined);
		});
	}

	protected <T> CompletableFuture<T> doSubmitTask(ResourceTaskContext rtc, String description,
			BiFunction<ResourceTaskContext, CancelIndicator, T> task) {

		Object queueId = getQueueIdForContext(rtc.getURI(), rtc.isTemporary());
		return queuedExecutorService.submit(queueId, description, ci -> {
			try {
				currentContext.set(rtc);
				return task.apply(rtc, ci);
			} finally {
				currentContext.set(null);
			}
		});
	}

	protected Object getQueueIdForContext(URI uri, boolean isTemporary) {
		if (isTemporary) {
			// for every temporary context only a single task is being submitted that is supposed to be independent of
			// all other tasks (in particular, we can have several temporary contexts for the same URI that should all
			// be independent of one another), so we use "new Object()" as the actual ID here:
			return Pair.of(ResourceTaskManager.class, new Object());
		}
		return Pair.of(ResourceTaskManager.class, uri);
	}

	protected ResourceTaskContext createContext(URI uri, boolean isTemporary) {
		ResourceTaskContext rtc = openFileContextProvider.get();
		ResourceDescriptionsData index = isTemporary ? createPersistedStateIndex() : createLiveScopeIndex();
		rtc.initialize(this, uri, isTemporary, index, containerHandle2URIs, workspaceConfig);
		return rtc;
	}

	protected synchronized void discardContextInfo(URI uri) {
		uri2RTCs.remove(uri);
		sharedDirtyState.removeDescription(uri);
		if (issueRegistry != null) {
			issueRegistry.clearIssuesOfDirtyState(uri);
		}
		// TODO GH-1774 closing a file may lead to a change in other contexts (because they will switch from using
		// dirty state to using persisted state for the file being closed)
	}

	/**
	 * If the thread invoking this method {@link #runInExistingContext(URI, String, BiFunction) currently runs in an
	 * context}, that context is returned. Otherwise returns <code>null</code>.
	 * <p>
	 * Corresponds to {@link Thread#currentThread()}.
	 */
	public ResourceTaskContext currentContext() {
		return currentContext.get();
	}

	protected synchronized ResourceDescriptionsData createPersistedStateIndex() {
		// TODO GH-1774 performance? (consider maintaining a ResourceDescriptionsData in addition to persistedState)
		return new ResourceDescriptionsData(IterableExtensions.flatMap(persistedStateDescriptions.values(),
				ResourceDescriptionsData::getAllResourceDescriptions));
	}

	/** Creates an index containing the persisted state shadowed by the dirty state of all non-temporary contexts. */
	public synchronized ResourceDescriptionsData createLiveScopeIndex() {
		ResourceDescriptionsData result = createPersistedStateIndex();
		for (IResourceDescription desc : sharedDirtyState.getAllResourceDescriptions()) {
			result.addDescription(desc.getURI(), desc);
		}
		return result;
	}

	public synchronized void updatePersistedState(
			IWorkspaceConfigSnapshot newWorkspaceConfig,
			Map<String, ? extends ResourceDescriptionsData> changedDescriptions,
			@SuppressWarnings("unused") List<? extends IProjectConfigSnapshot> changedProjects,
			Set<String> removedContainerHandles) {

		IWorkspaceConfigSnapshot oldWC = workspaceConfig;

		// compute modification info
		List<IResourceDescription> changed = new ArrayList<>();
		Set<URI> removed = new HashSet<>();
		for (Entry<String, ? extends ResourceDescriptionsData> entry : changedDescriptions.entrySet()) {
			String containerHandle = entry.getKey();
			ResourceDescriptionsData newData = entry.getValue();

			ResourceDescriptionsData oldContainer = persistedStateDescriptions.get(containerHandle);
			if (oldContainer != null) {
				for (IResourceDescription desc : oldContainer.getAllResourceDescriptions()) {
					URI descURI = desc.getURI();
					if (!uri2RTCs.containsKey(descURI)) {
						removed.add(descURI);
					}
				}
			}
			for (IResourceDescription desc : newData.getAllResourceDescriptions()) {
				URI descURI = desc.getURI();
				if (!uri2RTCs.containsKey(descURI)) {
					changed.add(desc);
					removed.remove(descURI);
				}
			}
		}

		// update my persisted state instance
		// FIXME GH-1774 simplify index handling
		SetMultimap<String, URI> newContainerHandle2URIs = HashMultimap.create(containerHandle2URIs);
		for (Entry<String, ? extends ResourceDescriptionsData> entry : changedDescriptions.entrySet()) {
			String containerHandle = entry.getKey();
			ResourceDescriptionsData newData = entry.getValue();
			persistedStateDescriptions.put(containerHandle, newData.copy());
			newContainerHandle2URIs.removeAll(containerHandle);
			newContainerHandle2URIs.putAll(containerHandle, newData.getAllURIs());
		}
		for (String removedContainerHandle : removedContainerHandles) {
			persistedStateDescriptions.remove(removedContainerHandle);
			newContainerHandle2URIs.removeAll(removedContainerHandle);
		}
		containerHandle2URIs = ImmutableSetMultimap.copyOf(newContainerHandle2URIs);
		workspaceConfig = newWorkspaceConfig;

		// update persisted state instances in the context of each context
		if (Iterables.isEmpty(changed) && removed.isEmpty() && workspaceConfig.equals(oldWC)) {
			return;
		}
		for (URI currURI : uri2RTCs.keySet()) {
			runInExistingContextVoid(currURI, "updatePersistedState of existing context", (rtc, ci) -> {
				rtc.onPersistedStateChanged(changed, removed, containerHandle2URIs, workspaceConfig, ci);
			});
		}
	}

	protected synchronized void updateSharedDirtyState(IResourceDescription newDesc) {
		// update my dirty state instance
		URI newDescURI = newDesc.getURI();
		sharedDirtyState.addDescription(newDescURI, newDesc);
		// update dirty state instances in the context of each open file (except the one that caused the change)
		for (URI currURI : uri2RTCs.keySet()) {
			if (currURI.equals(newDescURI)) {
				continue;
			}
			runInExistingContextVoid(currURI, "updateSharedDirtyState of existing context", (rtc, ci) -> {
				rtc.onDirtyStateChanged(newDesc, ci);
			});
		}
	}

	public void addListener(IResourceTaskListener l) {
		listeners.add(l);
	}

	public void removeListener(IResourceTaskListener l) {
		listeners.remove(l);
	}

	protected /* NOT synchronized */ void onDidRefreshContext(ResourceTaskContext rtc, CancelIndicator ci) {
		if (rtc.isTemporary()) {
			return; // temporarily opened files do not send out events to resource task listeners
		}
		notifyResourceTaskListeners(rtc, ci);
	}

	protected /* NOT synchronized */ void notifyResourceTaskListeners(ResourceTaskContext rtc, CancelIndicator ci) {
		for (IResourceTaskListener l : listeners) {
			l.didRefreshOpenFile(rtc, ci);
		}
	}
}
