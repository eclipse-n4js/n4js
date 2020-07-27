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
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl.ResourceLocator;
import org.eclipse.lsp4j.TextDocumentContentChangeEvent;
import org.eclipse.n4js.ide.xtext.server.build.BuilderFrontend;
import org.eclipse.n4js.ide.xtext.server.issues.PublishingIssueAcceptor;
import org.eclipse.n4js.xtext.server.LSPIssue;
import org.eclipse.n4js.xtext.workspace.WorkspaceConfigSnapshot;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.linking.lazy.LazyLinkingResource;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.resource.IExternalContentSupport;
import org.eclipse.xtext.resource.IExternalContentSupport.IExternalContentProvider;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescription.Delta;
import org.eclipse.xtext.resource.IResourceDescription.Manager.AllChangeAware;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.resource.containers.DelegatingIAllContainerAdapter;
import org.eclipse.xtext.resource.containers.IAllContainersState;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;
import org.eclipse.xtext.resource.persistence.SerializableResourceDescription;
import org.eclipse.xtext.service.OperationCanceledManager;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.util.LazyStringInputStream;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.validation.Issue;

import com.google.common.collect.ImmutableSetMultimap;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * Represents the context for tasks that operate on a certain EMF resource, called main resource, including all
 * necessary information and data structures for performing such task. In particular, this includes EMF resources for
 * files required by the main resource.
 */
@SuppressWarnings({ "restriction", "deprecation" })
public class ResourceTaskContext {

	/*
	 * Review feedback:
	 *
	 * It *appears* as if we are duplicating quite some logic in this class. The builder front-end is responsible for
	 * updating resources after changes happened. We are implementing a different incremental build semantics in
	 * refreshContext with fewer optimizations and therefore a performance drag.
	 *
	 * The logic, how a given resource set is updated after an incoming change is recorded, should certainly be
	 * implemented exactly once.
	 *
	 * For xtext.ui, we made the mistake of duplicating this between the Eclipse builder and the reconciler, and this
	 * was a constant PITA.
	 *
	 * #prepareRefresh is sort-of a brute force approach that has potential for optimizations.
	 */

	@Inject
	private Provider<XtextResourceSet> resourceSetProvider;

	@Inject
	private IExternalContentSupport externalContentSupport;

	@Inject
	private IResourceServiceProvider.Registry resourceServiceProviderRegistry;

	@Inject
	private OperationCanceledManager operationCanceledManager;

	@Inject
	private PublishingIssueAcceptor issuePublisher;

	@Inject
	private BuilderFrontend builderFrontend;

	/** The {@link ResourceTaskManager} that created this instance. */
	private ResourceTaskManager parent;
	/** URI of the resource represented by this {@link ResourceTaskContext} (i.e. URI of the main resource). */
	private URI mainURI;
	/** Tells whether this context represents a temporarily opened file, see {@link #isTemporary()}. */
	private boolean temporary;
	/** Tells whether this context is still managed by the ResourceTaskManager or was already removed */
	private boolean alive;

	/**
	 * Contains the state of all files in the workspace. For open files managed by {@link #parent} (including the open
	 * file of this context) this state will represent the dirty state and for all other files it will represent the
	 * persisted state (as provided by the LSP builder).
	 */
	private ResourceDescriptionsData indexSnapshot;

	/** Maps project names to URIs of all resources contained in the project (from the last build). */
	ImmutableSetMultimap<String, URI> project2BuiltURIs;

	/** Most recent workspace configuration. */
	WorkspaceConfigSnapshot workspaceConfig;

	/** The resource set used for the current resource and any other resources required for resolution. */
	private XtextResourceSet mainResourceSet;
	/** The EMF resource representing the open file. */
	private XtextResource mainResource = null;
	/** The current textual content of the open file. */
	private XDocument document = null;

	/** Within each resource task context, this provides text contents of all other context's main resources. */
	protected class ResourceTaskContentProvider implements IExternalContentProvider {
		@Override
		public String getContent(URI uri) {
			XDocument doc = parent.getOpenDocument(uri);
			return doc != null ? doc.getContents() : null;
		}

		@Override
		public boolean hasContent(URI uri) {
			return parent.getOpenDocument(uri) != null;
		}

		@Override
		public IExternalContentProvider getActualContentProvider() {
			return this;
		}
	}

	/** @return URI of main resource. */
	public synchronized URI getURI() {
		return mainURI;
	}

	/**
	 * Tells whether this {@link ResourceTaskContext} represents a temporary resource task. Such contexts do not
	 * actually represent an open editor in the LSP client but were created to perform editing-related computations in
	 * files not actually opened in the LSP client. For example, when API documentation needs to be retrieved from a
	 * file not currently opened in an editor in the LSP client, such a temporary {@code OpenFileContext} will be
	 * created.
	 * <p>
	 * Some special characteristics of temporary resource task contexts:
	 * <ul>
	 * <li>temporary contexts will not publish their state to the {@link #parent}'s dirty state index.
	 * <li>temporary contexts may be created even for files that actually have an open editor in the LSP client. This
	 * might be useful if some computation needs to be performed that should not influence the open editor's state.
	 * </ul>
	 */
	public synchronized boolean isTemporary() {
		return temporary;
	}

	/**
	 * Returns true if this context is still managed by the {@link ResourceTaskManager}.
	 */
	public synchronized boolean isAlive() {
		return alive;
	}

	/**
	 * Mark this task context as no longer managed.
	 */
	public synchronized void close() {
		this.alive = false;
		if (!isTemporary()) {
			issuePublisher.accept(mainURI, builderFrontend.getValidationIssues(mainURI));
		}
	}

	/**
	 * Tells whether this {@link ResourceTaskContext} represents a context for an open file, i.e. not a
	 * {@link #isTemporary() temporary context}.
	 */
	public synchronized boolean isOpen() {
		return !isTemporary();
	}

	/** Returns the context's resource set. Each resource task context has exactly one resource set. */
	public synchronized XtextResourceSet getResourceSet() {
		return mainResourceSet;
	}

	/**
	 * This resource task's main resource. Each resource task is associated with exactly one main resource.
	 * <p>
	 * Other resources that exist in a task's {@link #getResourceSet() resource set} were either demand-loaded during
	 * {@link LazyLinkingResource#resolveLazyCrossReferences(CancelIndicator) resolution} of the main resource or were
	 * explicitly loaded by some task running in the context (e.g. some editing functionality).
	 */
	public synchronized XtextResource getResource() {
		return mainResource;
	}

	/**
	 * May return <code>null</code> if not fully initialized yet. In contrast to most other methods of this class, this
	 * method is thread safe, i.e. may be invoked from any thread.
	 */
	public synchronized XDocument getDocument() {
		return document;
	}

	/** Initialize this context's non-injectable fields and resource set. Invoked once per context. */
	@SuppressWarnings("hiding")
	public synchronized void initialize(
			ResourceTaskManager parent,
			URI uri,
			boolean isTemporary,
			ResourceDescriptionsData index,
			ImmutableSetMultimap<String, URI> project2builtURIs,
			WorkspaceConfigSnapshot workspaceConfig) {

		this.parent = parent;
		this.mainURI = uri;
		this.temporary = isTemporary;
		this.indexSnapshot = index;
		this.project2BuiltURIs = project2builtURIs;
		this.workspaceConfig = workspaceConfig;
		this.mainResourceSet = createResourceSet();
		this.alive = true;
	}

	/** Returns a newly created and fully configured resource set */
	protected XtextResourceSet createResourceSet() {
		XtextResourceSet result = resourceSetProvider.get();
		ResourceDescriptionsData.ResourceSetAdapter.installResourceDescriptionsData(result, indexSnapshot);
		externalContentSupport.configureResourceSet(result, new ResourceTaskContentProvider());

		IAllContainersState allContainersState = new ResourceTaskContextAllContainerState(this);
		result.eAdapters().add(new DelegatingIAllContainerAdapter(allContainersState));

		return result;
	}

	/**
	 * Create & initialize this context's main resource with the given content. Invoked exactly once for
	 * NON-{@link #isTemporary() temporary} contexts, not at all for temporary contexts.
	 */
	protected void initContext(int version, String content, CancelIndicator cancelIndicator) {
		if (mainResource != null) {
			throw new IllegalStateException("trying to initialize an already initialized resource: " + mainURI);
		}

		mainResource = (XtextResource) mainResourceSet.createResource(mainURI);
		document = new XDocument(version, content);

		try (InputStream in = new LazyStringInputStream(document.getContents(), mainResource.getEncoding())) {
			mainResource.load(in, null);
		} catch (IOException e) {
			throw new RuntimeException("IOException while reading from string input stream", e);
		}

		resolveAndValidateResource(cancelIndicator);
	}

	/**
	 * Create & initialize this context's main resource based only on its URI, retrieving its content via EMF's
	 * {@link ResourceLocator}. Invoked exactly once for {@link #isTemporary() temporary} contexts, not at all for
	 * non-temporary contexts.
	 */
	protected void initContext(boolean resolveAndValidate, CancelIndicator cancelIndicator) {
		if (mainResource != null) {
			throw new IllegalStateException("trying to initialize an already initialized resource: " + mainURI);
		}

		mainResource = (XtextResource) mainResourceSet.getResource(mainURI, true); // uses the EMF ResourceLocator
		IParseResult parseResult = mainResource != null ? mainResource.getParseResult() : null;
		ICompositeNode rootNode = parseResult != null ? parseResult.getRootNode() : null;
		document = new XDocument(0, rootNode != null ? rootNode.getText() : "");

		if (resolveAndValidate) {
			resolveAndValidateResource(cancelIndicator);
		}
	}

	/** Same as {@link #refreshContext(int, Iterable, CancelIndicator)}, but without changing the source text. */
	public void refreshContext(CancelIndicator cancelIndicator) {
		prepareRefresh();

		mainResource.relink();

		resolveAndValidateResource(cancelIndicator);
	}

	/**
	 * Removes all the other resources from the resource set.
	 */
	private void prepareRefresh() {
		if (mainResource == null) {
			throw new IllegalStateException("trying to refresh a resource that was not yet initialized: " + mainURI);
		}
		if (!mainResource.isLoaded()) {
			throw new IllegalStateException("trying to refresh a resource that is not yet loaded: " + mainURI);
		}

		ResourceSet resSet = getResourceSet();
		resSet.getResources().removeIf(res -> res != mainResource);
	}

	/**
	 * Refresh this context's main resource, i.e. change its source text and then parse, resolve, and validate it. Also
	 * sends out dirty state index and issue updates (not for {@link #isTemporary() temporary} contexts).
	 */
	public void refreshContext(@SuppressWarnings("unused") int version,
			Iterable<? extends TextDocumentContentChangeEvent> changes, CancelIndicator cancelIndicator) {

		prepareRefresh();

		document = document.applyTextDocumentChanges(changes);
		try {
			mainResource.reparse(document.getContents());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		resolveAndValidateResource(cancelIndicator);
	}

	/**
	 * Triggers {@link #resolveResource(CancelIndicator) resolution} and {@link #validateResource(CancelIndicator)
	 * validation} of this context's main resource.
	 */
	public List<? extends LSPIssue> resolveAndValidateResource(CancelIndicator cancelIndicator) {
		resolveResource(cancelIndicator);
		return validateResource(cancelIndicator);
	}

	/** Resolve this context's main resource and send a dirty state index update. */
	public void resolveResource(CancelIndicator cancelIndicator) {
		// resolve
		EcoreUtil2.resolveLazyCrossReferences(mainResource, cancelIndicator);
		// update dirty state
		updateSharedDirtyState();
		// notify resource task listeners
		parent.onDidRefreshContext(this, cancelIndicator);
	}

	/**
	 * Validate this context's main resource and send an issue update.
	 */
	public List<? extends LSPIssue> validateResource(CancelIndicator cancelIndicator) {
		// validate
		IResourceServiceProvider resourceServiceProvider = resourceServiceProviderRegistry
				.getResourceServiceProvider(mainURI);
		IResourceValidator resourceValidator = resourceServiceProvider.getResourceValidator();
		List<Issue> issues = resourceValidator.validate(mainResource, CheckMode.ALL, cancelIndicator);
		operationCanceledManager.checkCanceled(cancelIndicator); // #validate() sometimes returns null when canceled!

		List<? extends LSPIssue> castedIssues = LSPIssue.cast(issues);
		if (!isTemporary()) {
			issuePublisher.accept(mainResource.getURI(), castedIssues);
		}
		return castedIssues;
	}

	/** Send dirty state index update to parent. Ignored for {@link #isTemporary() temporary} contexts. */
	protected void updateSharedDirtyState() {
		if (isTemporary()) {
			return; // temporarily opened files do not contribute to the parent's shared dirty state index
		}
		IResourceDescription newDesc = createResourceDescription();
		indexSnapshot.addDescription(mainURI, newDesc);
		parent.updateSharedDirtyState(newDesc.getURI(), newDesc);
	}

	/**
	 * Invoked by {@link #parent} when a change happened in another open file (not the one represented by this
	 * {@link ResourceTaskContext}). Will never be invoked for {@link #isTemporary() temporary} contexts.
	 */
	protected void onDirtyStateChanged(IResourceDescription changedDesc, CancelIndicator cancelIndicator) {
		updateIndex(Collections.singletonList(changedDesc), Collections.emptySet(), project2BuiltURIs,
				workspaceConfig, cancelIndicator);
	}

	/**
	 * Invoked by {@link #parent} when a change happened in a non-opened file OR after an open file was closed.
	 */
	protected void onPersistedStateChanged(Collection<? extends IResourceDescription> changedDescs,
			Set<URI> removedURIs, ImmutableSetMultimap<String, URI> newProject2builtURIs,
			WorkspaceConfigSnapshot newWorkspaceConfig, CancelIndicator cancelIndicator) {
		updateIndex(changedDescs, removedURIs, newProject2builtURIs, newWorkspaceConfig, cancelIndicator);
	}

	/** Update this context's internal index and trigger a refresh if required. */
	protected void updateIndex(Collection<? extends IResourceDescription> changedDescs, Set<URI> removedURIs,
			ImmutableSetMultimap<String, URI> newProject2builtURIs, WorkspaceConfigSnapshot newWorkspaceConfig,
			CancelIndicator cancelIndicator) {

		// update my cached state

		List<IResourceDescription.Delta> allDeltas = createDeltas(changedDescs, removedURIs);
		for (IResourceDescription.Delta delta : allDeltas) {
			indexSnapshot.register(delta);
		}

		project2BuiltURIs = newProject2builtURIs;

		WorkspaceConfigSnapshot oldWorkspaceConfig = workspaceConfig;
		workspaceConfig = newWorkspaceConfig;

		// refresh if I am affected by the changes

		boolean isAffected = !workspaceConfig.equals(oldWorkspaceConfig);

		if (!isAffected) {
			IResourceDescription.Manager rdm = getResourceDescriptionManager(mainURI);
			if (rdm == null) {
				return;
			}
			IResourceDescription candidateDesc = indexSnapshot.getResourceDescription(mainURI);
			if (rdm instanceof AllChangeAware) {
				isAffected = ((AllChangeAware) rdm).isAffectedByAny(allDeltas, candidateDesc, indexSnapshot);
			} else {
				List<IResourceDescription.Delta> changedDeltas = allDeltas.stream()
						.filter(d -> d.haveEObjectDescriptionsChanged())
						.collect(Collectors.toList());
				isAffected = rdm.isAffected(changedDeltas, candidateDesc, indexSnapshot);
			}
		}

		if (isAffected) {
			refreshContext(cancelIndicator);
		}
	}

	/** Create deltas for the given changes and removals. */
	protected List<IResourceDescription.Delta> createDeltas(Collection<? extends IResourceDescription> changedDescs,
			Set<URI> removedURIs) {

		List<IResourceDescription.Delta> deltas = new ArrayList<>(changedDescs.size() + removedURIs.size());

		for (IResourceDescription changedDesc : changedDescs) {
			URI changedURI = changedDesc.getURI();
			IResourceDescription oldDesc = indexSnapshot.getResourceDescription(changedURI);
			IResourceDescription.Delta delta = createDelta(changedURI, oldDesc, changedDesc);
			if (delta != null) {
				deltas.add(delta);
			}
		}

		for (URI removedURI : removedURIs) {
			IResourceDescription removedDesc = indexSnapshot.getResourceDescription(removedURI);
			IResourceDescription.Delta delta = createDelta(removedURI, removedDesc, null);
			if (delta != null) {
				deltas.add(delta);
			}
		}

		return deltas;
	}

	/** Create a delta for the given change. 'oldDesc' and 'newDesc' may be <code>null</code>. */
	protected IResourceDescription.Delta createDelta(URI uri, IResourceDescription oldDesc,
			IResourceDescription newDesc) {

		if (oldDesc != newDesc) {
			IResourceDescription.Manager rdm = getResourceDescriptionManager(uri);
			if (rdm != null) {
				Delta delta = rdm.createDelta(oldDesc, newDesc);
				return delta;
			}
		}
		return null;
	}

	/** Create a resource description representing the current state of this context's main resource. */
	protected IResourceDescription createResourceDescription() {
		IResourceDescription.Manager resourceDescriptionManager = getResourceDescriptionManager(mainURI);
		IResourceDescription newDesc = resourceDescriptionManager.getResourceDescription(mainResource);
		// sanitize resource description
		// NOTE: it seems that resource descriptions created by the resource description manager may contain mutable
		// state (e.g. user data implemented as a ForwardingMap with lazily initialized content) and hold references to
		// the resource they were created from (i.e. 'mainResource' in this case); this means they are (1) not thread
		// safe and (2) may leak EObjects from one file context into another or to the outside. The following line
		// seems to fix that, but requires access to restricted Xtext API:
		SerializableResourceDescription newDesc2 = SerializableResourceDescription.createCopy(newDesc);
		return newDesc2;
	}

	/** Return the correct resource description manager for the resource with the given URI. */
	protected IResourceDescription.Manager getResourceDescriptionManager(URI uri) {
		IResourceServiceProvider resourceServiceProvider = resourceServiceProviderRegistry
				.getResourceServiceProvider(uri);
		if (resourceServiceProvider == null) {
			return null;
		}
		return resourceServiceProvider.getResourceDescriptionManager();
	}

}
