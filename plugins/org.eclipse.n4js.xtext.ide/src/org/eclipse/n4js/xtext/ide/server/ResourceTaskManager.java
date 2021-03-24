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
package org.eclipse.n4js.xtext.ide.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import org.eclipse.emf.common.util.URI;
import org.eclipse.lsp4j.TextDocumentContentChangeEvent;
import org.eclipse.n4js.xtext.ide.server.build.BuilderFrontend;
import org.eclipse.n4js.xtext.ide.server.util.CancelIndicatorUtil;
import org.eclipse.n4js.xtext.workspace.ProjectConfigSnapshot;
import org.eclipse.n4js.xtext.workspace.WorkspaceConfigAdapter;
import org.eclipse.n4js.xtext.workspace.WorkspaceConfigSnapshot;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.xbase.lib.Pair;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Iterables;
import com.google.common.collect.SetMultimap;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

/**
 * Manages a set of {@link ResourceTaskContext}s, including creation, disposal, and executing tasks within those
 * contexts.
 */
@Singleton
public class ResourceTaskManager {

	@Inject
	private Provider<ResourceTaskContext> resourceTaskContextProvider;

	@Inject
	private Provider<XtextResourceSet> resourceSetProvider;

	@Inject
	private QueuedExecutorService queuedExecutorService;

	/***/
	protected final Map<URI, ResourceTaskContext> uri2RTCs = new HashMap<>();

	/**
	 * For each thread that is currently executing a resource-related task, this stores the corresponding
	 * {@link ResourceTaskContext}.
	 */
	protected final ThreadLocal<ResourceTaskContext> currentContext = new ThreadLocal<>();

	/*
	 * Review feedback:
	 *
	 * This looks like being the same as the the ConcurrentIndex structure?
	 */
	/** The persisted state index, not taking into account dirty state from existing resource task contexts. */
	protected final ResourceDescriptionsData persistedIndex = new ResourceDescriptionsData(Collections.emptyList());
	/** The dirty state index. Contains an entry for each URI with an existing resource task context. */
	protected final ResourceDescriptionsData dirtyIndex = new ResourceDescriptionsData(Collections.emptyList());
	/** Tracks URIs per project. Derived from persisted state but applies equally to the dirty state. */
	protected SetMultimap<String, URI> project2BuiltURIs = HashMultimap.create();
	/** Immutable copy of {@link #project2BuiltURIs}. */
	protected ImmutableSetMultimap<String, URI> project2BuiltURIsImmutable = ImmutableSetMultimap
			.copyOf(project2BuiltURIs);
	/** Most recent workspace configuration. */
	protected WorkspaceConfigSnapshot workspaceConfig = null;

	/***/
	protected final List<IResourceTaskListener> listeners = new CopyOnWriteArrayList<>();

	/*
	 * Review feedback:
	 *
	 * Rethink the terminology: refresh is not something that is usually associated with an Xtext resource.
	 */
	/** Listener for events in resource task contexts. */
	public interface IResourceTaskListener {
		/** Invoked whenever an open file was resolved, validated, etc. Invoked in the given open file context. */
		public void didRefreshContext(ResourceTaskContext rtc, CancelIndicator ci);
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
		ResourceDescriptionsData index = isTemporary ? createPersistedStateIndex() : createLiveScopeIndex();
		rtc.initialize(this, uri, isTemporary, index, project2BuiltURIsImmutable, workspaceConfig);
		return rtc;
	}

	/** Internal removal of all information related to a particular resource task context. */
	protected synchronized void discardContextInfo(URI uri) {
		ResourceTaskContext result = uri2RTCs.remove(uri);
		if (result != null) {
			result.close();
		}
		updateSharedDirtyState(uri, null);
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
	 * Creates a resource set configured with the current workspace configuration and the current
	 * {@link #createPersistedStateIndex() persisted index}. Just as temporary resource task contexts, this resource set
	 * represents a fixed point in time and will not receive any updates of the workspace configuration of files on
	 * disk.
	 */
	public XtextResourceSet createTemporaryResourceSet() {
		// FIXME avoid duplication with ResourceTaskContext#createResourceSet()
		// consider making ResourceTaskContext independent of a 'main resource' and create a temporary
		// ResourceTaskContext in these cases
		// OR: consider introducing a class XIIdeResourceSetProvider as a non-UI replacement for
		// org.eclipse.xtext.ui.resource.IResourceSetProvider (which relies on Eclipse UI)
		XtextResourceSet result = resourceSetProvider.get();
		WorkspaceConfigAdapter.installWorkspaceConfig(result, workspaceConfig);
		ResourceDescriptionsData index = createPersistedStateIndex();
		ResourceDescriptionsData.ResourceSetAdapter.installResourceDescriptionsData(result, index);
		return result;
	}

	/*
	 * Review feedback:
	 *
	 * Copying the entire index should not be necessary since we do have implementations of IResourceDescriptions that
	 * do apply sane shadowing semantics.
	 */
	/** Creates an index not containing any dirty state information. */
	protected synchronized ResourceDescriptionsData createPersistedStateIndex() {
		return persistedIndex.copy();
	}

	/** Creates an index containing the persisted state shadowed by the dirty state of all non-temporary contexts. */
	public synchronized ResourceDescriptionsData createLiveScopeIndex() {
		ResourceDescriptionsData result = createPersistedStateIndex();
		for (IResourceDescription desc : dirtyIndex.getAllResourceDescriptions()) {
			result.addDescription(desc.getURI(), desc);
		}
		return result;
	}

	/**
	 * Updates this manager and all its {@link ResourceTaskContext}s with the given changes to the persisted state
	 * index. Should be invoked from the outside whenever changes to the persisted state become available, e.g. because
	 * the {@link BuilderFrontend builder} has rebuilt some files.
	 */
	public synchronized void updatePersistedState(
			WorkspaceConfigSnapshot newWorkspaceConfig,
			Map<String, ? extends ResourceDescriptionsData> changedDescriptions,
			@SuppressWarnings("unused") List<? extends ProjectConfigSnapshot> changedProjects,
			Set<String> removedProjects) {

		WorkspaceConfigSnapshot oldWC = workspaceConfig;

		// compute "flat" modification info (not per project but on a global URI->description basis)
		List<IResourceDescription> changed = new ArrayList<>();
		Set<URI> removed = new HashSet<>();
		for (Entry<String, ? extends ResourceDescriptionsData> entry : changedDescriptions.entrySet()) {
			String projectName = entry.getKey();
			ResourceDescriptionsData newData = entry.getValue();

			Set<URI> oldURIsOfProject = new HashSet<>(project2BuiltURIs.get(projectName));
			removed.addAll(oldURIsOfProject);

			for (IResourceDescription desc : newData.getAllResourceDescriptions()) {
				URI descURI = desc.getURI();
				changed.add(desc);
				removed.remove(descURI);
			}
		}

		// update my internal state
		changed.stream().forEachOrdered(desc -> persistedIndex.addDescription(desc.getURI(), desc));
		removed.stream().forEachOrdered(persistedIndex::removeDescription);
		for (Entry<String, ? extends ResourceDescriptionsData> entry : changedDescriptions.entrySet()) {
			String projectName = entry.getKey();
			ResourceDescriptionsData newData = entry.getValue();
			project2BuiltURIs.removeAll(projectName);
			project2BuiltURIs.putAll(projectName, newData.getAllURIs());
		}
		for (String removedProjectName : removedProjects) {
			project2BuiltURIs.removeAll(removedProjectName);
		}
		project2BuiltURIsImmutable = ImmutableSetMultimap.copyOf(project2BuiltURIs);
		workspaceConfig = newWorkspaceConfig;

		// from this point forward: ignore changes/removals related to existing resource task contexts
		changed.removeIf(desc -> uri2RTCs.containsKey(desc.getURI()));
		removed.removeAll(uri2RTCs.keySet());

		// update internal state of all contexts
		if (Iterables.isEmpty(changed) && removed.isEmpty() && workspaceConfig.equals(oldWC)) {
			return;
		}
		ImmutableSetMultimap<String, URI> capturedProject2BuiltURIsImmutable = project2BuiltURIsImmutable;
		WorkspaceConfigSnapshot capturedWorkspaceConfig = workspaceConfig;
		for (URI currURI : uri2RTCs.keySet()) {
			runInExistingContextVoid(currURI, "updatePersistedState of existing context", (rtc, ci) -> {
				rtc.onPersistedStateChanged(changed, removed,
						capturedProject2BuiltURIsImmutable, capturedWorkspaceConfig, ci);
			});
		}
	}

	/**
	 * Updates this manager and all its {@link ResourceTaskContext}s with the given changes to the persisted state
	 * index. Should only be invoked internally from {@link ResourceTaskContext} after
	 * {@link ResourceTaskContext#refreshContext(int, Iterable, CancelIndicator) refreshing} its internal state.
	 */
	protected synchronized void updateSharedDirtyState(URI uri, IResourceDescription newDesc) {
		// update my dirty state instance
		if (newDesc != null) {
			dirtyIndex.addDescription(uri, newDesc);
		} else {
			dirtyIndex.removeDescription(uri);
		}
		// update dirty state instance in each resource task context (except the one that caused the change)
		ImmutableSetMultimap<String, URI> capturedProject2BuiltURIsImmutable = project2BuiltURIsImmutable;
		WorkspaceConfigSnapshot capturedWorkspaceConfig = workspaceConfig;
		IResourceDescription replacementDesc = newDesc == null ? persistedIndex.getResourceDescription(uri) : null;
		for (URI currURI : uri2RTCs.keySet()) {
			if (currURI.equals(uri)) {
				continue;
			}
			runInExistingContextVoid(currURI, "updateSharedDirtyState of existing context", (rtc, ci) -> {
				if (newDesc != null) {
					// happens in case a resource context has changed
					rtc.onDirtyStateChanged(newDesc, ci);
				} else {
					// happens in case a resource context is closed
					if (replacementDesc != null) {
						rtc.onPersistedStateChanged(Collections.singleton(replacementDesc), Collections.emptySet(),
								capturedProject2BuiltURIsImmutable, capturedWorkspaceConfig, ci);
					} else {
						rtc.onPersistedStateChanged(Collections.emptyList(), Collections.singleton(uri),
								capturedProject2BuiltURIsImmutable, capturedWorkspaceConfig, ci);
					}
				}
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
}
