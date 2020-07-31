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
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl.ResourceLocator;
import org.eclipse.lsp4j.TextDocumentContentChangeEvent;
import org.eclipse.n4js.ide.xtext.server.build.BuilderFrontend;
import org.eclipse.n4js.ide.xtext.server.index.ExtendedResourceDescriptionsData;
import org.eclipse.n4js.ide.xtext.server.index.ImmutableResourceDescriptions;
import org.eclipse.n4js.ide.xtext.server.index.ImmutableShadowedResourceDescriptions;
import org.eclipse.n4js.ide.xtext.server.index.MutableShadowedResourceDescriptions;
import org.eclipse.n4js.ide.xtext.server.issues.PublishingIssueAcceptor;
import org.eclipse.n4js.xtext.server.LSPIssue;
import org.eclipse.n4js.xtext.workspace.ProjectConfigSnapshot;
import org.eclipse.n4js.xtext.workspace.WorkspaceConfigSnapshot;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.linking.lazy.LazyLinkingResource;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescription.Manager.AllChangeAware;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.resource.impl.ProjectDescription;
import org.eclipse.xtext.resource.persistence.SerializableResourceDescription;
import org.eclipse.xtext.service.OperationCanceledManager;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.util.LazyStringInputStream;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.validation.Issue;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * Represents the context for tasks that operate on a certain EMF resource, called main resource, including all
 * necessary information and data structures for performing such task. In particular, this includes EMF resources for
 * files required by the main resource.
 */
@SuppressWarnings({ "restriction", "deprecation" })
public class ResourceTaskContext {

	@Inject
	private Provider<XtextResourceSet> resourceSetProvider;

	@Inject
	private IResourceServiceProvider.Registry resourceServiceProviderRegistry;

	@Inject
	private OperationCanceledManager operationCanceledManager;

	@Inject
	private PublishingIssueAcceptor issuePublisher;

	/*
	 * TODO this solution does not meet our quality standards. Rather than publishing the issues from #close, the caller
	 * of close should take care of that.
	 */
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

	private MutableShadowedResourceDescriptions localIndexData;
	/** The resource set used for the current resource and any other resources required for resolution. */
	private XtextResourceSet mainResourceSet;
	/** The EMF resource representing the open file. */
	private XtextResource mainResource = null;
	/** The current textual content of the open file. */
	private XDocument document = null;

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
	 * Mark this task context as no longer managed. Closes the context and sends the workspace issues to the client.
	 */
	public synchronized void close() {
		this.alive = false;
		if (!isTemporary()) {
			URI uri = mainURI;
			if (uri != null) {
				builderFrontend.asyncGetValidationIssues(uri).thenAccept(issues -> issuePublisher.accept(uri, issues));
			}
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
	 * Returns the local index data of this task context.
	 */
	public IResourceDescriptions getIndex() {
		return this.localIndexData;
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
			WorkspaceConfigSnapshot workspaceConfig,
			URI uri,
			boolean isTemporary,
			ImmutableResourceDescriptions parentIndex) {

		this.parent = parent;
		this.mainURI = uri;
		this.temporary = isTemporary;
		this.localIndexData = new MutableShadowedResourceDescriptions(new ExtendedResourceDescriptionsData(),
				parentIndex);
		this.mainResourceSet = createResourceSet();
		installProjectDescription(workspaceConfig);
		this.alive = true;
	}

	/** Returns a newly created and fully configured resource set */
	protected XtextResourceSet createResourceSet() {
		XtextResourceSet result = resourceSetProvider.get();

		parent.installIndex(result, localIndexData);
		parent.installExternalContentSupport(result);

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
		refreshContext(0, Collections.emptyList(), cancelIndicator);
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

		int oldDocumentLength = document.getContents().length();
		document = document.applyTextDocumentChanges(changes);
		mainResource.update(0, oldDocumentLength, document.getContents());

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
		IResourceDescription previous = localIndexData.add(newDesc);
		parent.updateSharedDirtyState(getResourceDescriptionManager(mainURI).createDelta(previous, newDesc));
	}

	/** Update this context's internal index and trigger a refresh if required. */
	protected void updateIndex(
			ImmutableShadowedResourceDescriptions dirtyIndex,
			WorkspaceConfigSnapshot workspaceSnapshot,
			IResourceDescription.Event event,
			CancelIndicator cancelIndicator) {

		localIndexData = localIndexData.shallowChangeParent(dirtyIndex);
		parent.installIndex(this.mainResourceSet, localIndexData);
		installProjectDescription(workspaceSnapshot);

		IResourceDescription.Manager rdm = getResourceDescriptionManager(mainURI);
		if (rdm == null) {
			return;
		}
		boolean isAffected;

		IResourceDescription candidateDesc = localIndexData.getResourceDescription(mainURI);
		if (rdm instanceof AllChangeAware) {
			isAffected = ((AllChangeAware) rdm).isAffectedByAny(event.getDeltas(), candidateDesc, localIndexData);
		} else {
			List<IResourceDescription.Delta> changedDeltas = event.getDeltas().stream()
					.filter(d -> d.haveEObjectDescriptionsChanged())
					.collect(Collectors.toList());
			isAffected = rdm.isAffected(changedDeltas, candidateDesc, localIndexData);
		}

		if (isAffected) {
			refreshContext(cancelIndicator);
		}
	}

	private void installProjectDescription(WorkspaceConfigSnapshot workspaceConfig) {
		ProjectConfigSnapshot project = workspaceConfig.findProjectContaining(mainURI);
		ProjectDescription projectDescription = new ProjectDescription();
		projectDescription.setName(project.getName());
		projectDescription.setDependencies(project.getDependencies().asList());
		ProjectDescription.removeFromEmfObject(mainResourceSet);
		projectDescription.attachToEmfObject(mainResourceSet);
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
		SerializableResourceDescription result = SerializableResourceDescription.createCopy(newDesc);
		return result;
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
