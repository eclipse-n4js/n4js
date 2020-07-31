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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.lsp4j.TextDocumentContentChangeEvent;
import org.eclipse.n4js.ide.xtext.server.build.BuilderFrontend;
import org.eclipse.n4js.ide.xtext.server.index.AbstractShadowedResourceDescriptions;
import org.eclipse.n4js.ide.xtext.server.index.ConcurrentIndex;
import org.eclipse.n4js.ide.xtext.server.index.ExtendedResourceDescriptionsProvider;
import org.eclipse.n4js.ide.xtext.server.index.ImmutableChunkedResourceDescriptions;
import org.eclipse.n4js.ide.xtext.server.index.ImmutableResourceDescriptions;
import org.eclipse.n4js.ide.xtext.server.index.ImmutableResourceDescriptionsData;
import org.eclipse.n4js.ide.xtext.server.index.ImmutableShadowedResourceDescriptions;
import org.eclipse.n4js.ide.xtext.server.index.MutableShadowedResourceDescriptions;
import org.eclipse.n4js.ide.xtext.server.util.CancelIndicatorUtil;
import org.eclipse.n4js.xtext.workspace.WorkspaceConfigSnapshot;
import org.eclipse.xtext.resource.IExternalContentSupport;
import org.eclipse.xtext.resource.IExternalContentSupport.IExternalContentProvider;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescription.Delta;
import org.eclipse.xtext.resource.IResourceDescription.Event;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.impl.ResourceDescriptionChangeEvent;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.util.Wrapper;
import org.eclipse.xtext.xbase.lib.Pair;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

/**
 * Manages a set of {@link ResourceTaskContext}s, including creation, disposal, and executing tasks within those
 * contexts.
 */
@Singleton
public class ResourceTaskManager {

	/** The current dirty state index */
	private final AtomicReference<ImmutableShadowedResourceDescriptions> dirtyIndex;

	private WorkspaceConfigSnapshot workspaceConfig;

	@Inject
	private Provider<ResourceTaskContext> resourceTaskContextProvider;

	@Inject
	private IExternalContentSupport externalContentSupport;

	@Inject
	private ExtendedResourceDescriptionsProvider resourceDescriptionsProvider;

	@Inject
	private QueuedExecutorService queuedExecutorService;

	/***/
	protected final Map<URI, ResourceTaskContext> uri2RTCs = new HashMap<>();

	/**
	 * For each thread that is currently executing a resource-related task, this stores the corresponding
	 * {@link ResourceTaskContext}.
	 */
	protected final ThreadLocal<ResourceTaskContext> currentContext = new ThreadLocal<>();

	/***/
	protected final List<IResourceTaskListener> listeners = new CopyOnWriteArrayList<>();

	/*
	 * Review feedback:
	 *
	 * Re-think the terminology: refresh is not something that is usually associated with an Xtext resource.
	 */
	/** Listener for events in resource task contexts. */
	public interface IResourceTaskListener {
		/** Invoked whenever an open file was resolved, validated, etc. Invoked in the given open file context. */
		public void didRefreshContext(ResourceTaskContext rtc, CancelIndicator ci);
	}

	/**
	 * Constructor.
	 */
	@Inject
	public ResourceTaskManager(ConcurrentIndex concurrentIndex) {
		this.dirtyIndex = new AtomicReference<>(
				new ImmutableShadowedResourceDescriptions(ImmutableResourceDescriptionsData.empty(),
						concurrentIndex.getWorkspaceIndex()));
		this.workspaceConfig = concurrentIndex.getWorkspaceConfig();
	}

	/**
	 * Returns the current dirty state.
	 */
	public ImmutableShadowedResourceDescriptions getDirtyIndex() {
		return dirtyIndex.get();
	}

	/** Returns true iff a non-temporary {@link ResourceTaskContext} exists for the given URI. */
	public synchronized boolean isOpen(URI uri) {
		return uri2RTCs.containsKey(uri);
	}

	/** Returns the {@link XDocument} for the given uri iff #{@link #isOpen(URI)} holds for the given uri. */
	public synchronized XDocument getOpenDocument(URI uri) {
		ResourceTaskContext rtc = uri2RTCs.get(uri);
		if (rtc != null) {
			// note: since we only obtain an object reference to an immutable data structure (XDocument) we do not need
			// to execute the following in the open file context:
			return rtc.getDocument();
		}
		return null;
	}

	/** Create a new resource task context for the resource with the given URI. */
	public synchronized void createContext(URI uri, int version, String content) {
		if (uri2RTCs.containsKey(uri)) {
			return;
		}
		ResourceTaskContext newContext = doCreateContext(uri, false);
		uri2RTCs.put(uri, newContext);

		runInExistingContextVoid(uri, "createContext", (rtc, ci) -> {
			rtc.initContext(version, content, ci);
		});
	}

	/** Change the source text of the main resource of the resource task context for the given URI. */
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

	/** Dispose of all resource task contexts managed by this manager. */
	public synchronized CompletableFuture<Void> closeAll() {
		List<CompletableFuture<Void>> cfs = new ArrayList<>(uri2RTCs.size());
		for (URI uri : new ArrayList<>(uri2RTCs.keySet())) {
			cfs.add(closeContext(uri));
		}
		return CompletableFuture.allOf(cfs.toArray(new CompletableFuture<?>[cfs.size()]));
	}

	/** Dispose of the resource task context for the resource with the given URI. */
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

	/** Same as {@link #runInExistingContext(URI, String, BiFunction)}, but for tasks without a return value. */
	public synchronized CompletableFuture<Void> runInExistingContextVoid(URI uri, String description,
			BiConsumer<ResourceTaskContext, CancelIndicator> task) {

		return runInExistingContext(uri, description, (rtc, ci) -> {
			task.accept(rtc, ci);
			return null;
		});
	}

	/**
	 * Run the given task within the resource task context for the given URI. Assumes that a context for the given URI
	 * exists and throws an exception otherwise.
	 */
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
	 * Creates a temporary resource task context for the file with the given URI, initializes it, and executes the given
	 * task, without interfering with other possibly existing or temporary contexts for that 'uri'.
	 * <p>
	 * The temporary context is not retained over a longer period of time; the given task is the only task that will
	 * ever be executed in this temporary context.
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

		ResourceTaskContext tempContext = doCreateContext(uri, true);

		String descriptionWithContext = description + " (temporary) [" + uri.lastSegment() + "]";
		return doSubmitTask(tempContext, descriptionWithContext, (_tempContext, ciFromExecutor) -> {
			CancelIndicator ciCombined = CancelIndicatorUtil.combine(outerCancelIndicator, ciFromExecutor);
			_tempContext.initContext(resolveAndValidate, ciCombined);
			return task.apply(_tempContext, ciCombined);
		});
	}

	/** Submit a task for execution within a resource task context to the executor service. */
	protected <T> CompletableFuture<T> doSubmitTask(ResourceTaskContext rtc, String description,
			BiFunction<ResourceTaskContext, CancelIndicator, T> task) {

		Object queueId = getQueueIdForContext(rtc.getURI(), rtc.isTemporary());
		return queuedExecutorService.submit(queueId, description, ci -> {
			try {
				if (!rtc.isAlive()) {
					throw new CancellationException();
				}
				currentContext.set(rtc);
				return task.apply(rtc, ci);
			} finally {
				currentContext.set(null);
			}
		});
	}

	/** Returns a queue ID for tasks supposed to run in the resource task context of the given URI. */
	protected Object getQueueIdForContext(URI uri, boolean isTemporary) {
		if (isTemporary) {
			// for every temporary context only a single task is being submitted that is supposed to be independent of
			// all other tasks (in particular, we can have several temporary contexts for the same URI that should all
			// be independent of one another), so we use "new Object()" as the actual ID here:
			return Pair.of(ResourceTaskManager.class, new Object());
		}
		return Pair.of(ResourceTaskManager.class, uri);
	}

	/**
	 * Actually creates a new resource task context for the given URI.
	 * <p>
	 * TODO IDE-3402 add support for language-specific bindings of ResourceTaskContext
	 */
	protected ResourceTaskContext doCreateContext(URI uri, boolean isTemporary) {
		ResourceTaskContext rtc = resourceTaskContextProvider.get();
		ImmutableResourceDescriptions baseIndex = isTemporary
				? resourceDescriptionsProvider.createPersistedResourceDescriptions()
				: getDirtyIndex();
		rtc.initialize(this, workspaceConfig, uri, isTemporary, baseIndex);
		return rtc;
	}

	/** Internal removal of all information related to a particular resource task context. */
	protected synchronized void discardContextInfo(URI uri) {
		ResourceTaskContext result = uri2RTCs.remove(uri);
		IResourceDescription.Manager resourceDescriptionManager = null;
		if (result != null) {
			resourceDescriptionManager = result.getResourceDescriptionManager(uri);
			result.close();
		}

		Wrapper<IResourceDescription> description = Wrapper.wrap(null);
		ImmutableShadowedResourceDescriptions newDirtyState = dirtyIndex.updateAndGet(prev -> {
			MutableShadowedResourceDescriptions mutable = prev.builder();
			description.set(mutable.getResourceDescription(uri));
			mutable.dropShadowingInformation(uri);
			return mutable.snapshot();
		});

		ResourceDescriptionChangeEvent event;
		if (resourceDescriptionManager != null) {
			Delta delta = resourceDescriptionManager.createDelta(description.get(),
					newDirtyState.getResourceDescription(uri));
			event = new ResourceDescriptionChangeEvent(ImmutableList.of(delta));
		} else {
			event = new ResourceDescriptionChangeEvent(ImmutableList.of());
		}
		WorkspaceConfigSnapshot capturedWorkspaceConfig = this.workspaceConfig;
		for (URI currURI : uri2RTCs.keySet()) {
			runInExistingContextVoid(currURI, "update index-state of existing context", (rtc, ci) -> {
				rtc.updateIndex(newDirtyState, capturedWorkspaceConfig, event, ci);
			});
		}

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

	/**
	 * Updates this manager and all its {@link ResourceTaskContext}s with the given changes to the persisted state
	 * index. Should be invoked from the outside whenever changes to the persisted state become available, e.g. because
	 * the {@link BuilderFrontend builder} has rebuilt some files.
	 */
	public synchronized void updatePersistedState(
			ImmutableChunkedResourceDescriptions newWorkspaceIndex,
			WorkspaceConfigSnapshot newWorkspaceConfig,
			IResourceDescription.Event event) {

		workspaceConfig = newWorkspaceConfig;
		ImmutableShadowedResourceDescriptions newDirtyState = dirtyIndex.updateAndGet(prev -> {
			return prev.withParent(newWorkspaceIndex);
		});

		IResourceDescription.Event sanitizedEvent = sanitize(event, newDirtyState);
		for (URI currURI : uri2RTCs.keySet()) {
			runInExistingContextVoid(currURI, "update index-state of existing context", (rtc, ci) -> {
				rtc.updateIndex(newDirtyState, newWorkspaceConfig, sanitizedEvent, ci);
			});
		}
	}

	/**
	 * Update the given event such that it does not contain any deltas for resources that are shadowed.
	 */
	protected Event sanitize(Event event, AbstractShadowedResourceDescriptions shadowedDescriptions) {
		if (event instanceof IResourceDescription.CoarseGrainedEvent) {
			return event;
		}
		List<IResourceDescription.Delta> sanitizedDeltas = new ArrayList<>();
		for (IResourceDescription.Delta delta : event.getDeltas()) {
			if (!shadowedDescriptions.isShadowed(delta.getUri())) {
				sanitizedDeltas.add(delta);
			}
		}
		return new ResourceDescriptionChangeEvent(sanitizedDeltas);
	}

	/**
	 * Updates this manager and all its {@link ResourceTaskContext}s with the given changes to the persisted state
	 * index. Should only be invoked internally from {@link ResourceTaskContext} after
	 * {@link ResourceTaskContext#refreshContext(int, Iterable, CancelIndicator) refreshing} its internal state.
	 */
	protected synchronized void updateSharedDirtyState(IResourceDescription.Delta delta) {
		URI uri = delta.getUri();
		ImmutableShadowedResourceDescriptions newDirtyState = dirtyIndex.updateAndGet(prev -> {
			MutableShadowedResourceDescriptions mutable = prev.builder();
			if (delta.getNew() != null) {
				mutable.add(delta.getNew());
			} else {
				mutable.remove(uri);
			}
			return mutable.snapshot();
		});

		WorkspaceConfigSnapshot capturedWorkspaceConfig = workspaceConfig;
		ResourceDescriptionChangeEvent event = new ResourceDescriptionChangeEvent(ImmutableList.of(delta));
		for (URI currURI : uri2RTCs.keySet()) {
			if (currURI.equals(uri)) {
				continue;
			}
			runInExistingContextVoid(currURI, "update index-state of existing context", (rtc, ci) -> {
				rtc.updateIndex(newDirtyState, capturedWorkspaceConfig, event, ci);
			});
		}
	}

	/** Adds a {@link IResourceTaskListener listener}. */
	public void addListener(IResourceTaskListener l) {
		listeners.add(l);
	}

	/** Removes a {@link IResourceTaskListener listener}. */
	public void removeListener(IResourceTaskListener l) {
		listeners.remove(l);
	}

	/** Invoked by {@link ResourceTaskContext} after completing a refresh. */
	protected /* NOT synchronized */ void onDidRefreshContext(ResourceTaskContext rtc, CancelIndicator ci) {
		if (rtc.isTemporary()) {
			return; // temporarily opened files do not send out events to resource task listeners
		}
		notifyResourceTaskListeners(rtc, ci);
	}

	/** Notify listeners that a resource task context has completed a refresh. */
	protected /* NOT synchronized */ void notifyResourceTaskListeners(ResourceTaskContext rtc, CancelIndicator ci) {
		for (IResourceTaskListener l : listeners) {
			l.didRefreshContext(rtc, ci);
		}
	}

	/** Within each resource task context, this provides text contents of all other context's main resources. */
	protected static class ResourceTaskContentProvider implements IExternalContentProvider {

		private final ResourceTaskManager resourceTaskManager;

		ResourceTaskContentProvider(ResourceTaskManager resourceTaskManager) {
			this.resourceTaskManager = resourceTaskManager;
		}

		@Override
		public String getContent(URI uri) {
			XDocument doc = resourceTaskManager.getOpenDocument(uri);
			return doc != null ? doc.getContents() : null;
		}

		@Override
		public boolean hasContent(URI uri) {
			return resourceTaskManager.getOpenDocument(uri) != null;
		}

		@Override
		public IExternalContentProvider getActualContentProvider() {
			return this;
		}
	}

	/**
	 * Configure the given resource set.
	 */
	void installExternalContentSupport(ResourceSet result) {
		externalContentSupport.configureResourceSet(result, new ResourceTaskContentProvider(this));
	}

	void installLiveIndex(ResourceSet resourceSet) {
		resourceSet.getLoadOptions().put(ResourceDescriptionsProvider.LIVE_SCOPE, true);
		resourceDescriptionsProvider.attachTo(getDirtyIndex(), resourceSet);
	}

	void installIndex(ResourceSet resourceSet, IResourceDescriptions index) {
		resourceDescriptionsProvider.replace(index, resourceSet);
	}
}
