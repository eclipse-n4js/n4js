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
package org.eclipse.n4js.ide.xtext.server.build;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import org.eclipse.emf.common.util.URI;
import org.eclipse.lsp4j.DidChangeWatchedFilesParams;
import org.eclipse.lsp4j.DidSaveTextDocumentParams;
import org.eclipse.lsp4j.FileChangeType;
import org.eclipse.lsp4j.FileEvent;
import org.eclipse.lsp4j.TextDocumentIdentifier;
import org.eclipse.n4js.ide.xtext.server.QueuedExecutorService;
import org.eclipse.n4js.xtext.server.LSPIssue;
import org.eclipse.xtext.ide.server.UriExtensions;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Facade for all builder-related functionality in the LSP server. From outside the builder-related classes, all use of
 * the LSP builder should go through this class.
 */
@SuppressWarnings("deprecation")
@Singleton
public class BuilderFrontend {

	@Inject
	private QueuedExecutorService queuedExecutorService;

	@Inject
	private UriExtensions uriExtensions;

	@Inject
	private XWorkspaceManager workspaceManager;

	@Inject
	private XWorkspaceBuilder workspaceBuilder;

	/**
	 * Returns the base directory of the workspace.
	 */
	public URI getBaseDir() {
		return workspaceManager.getBaseDir();
	}

	/*
	 * Review feedback:
	 *
	 * More recent versions of the Xtext LSP integration support workspaces with multiple roots. This will break the
	 * abstraction here.
	 *
	 * To me, it looks like the consumer of the issues (currently tests and the N4jscIssueSerializer) should make the
	 * URIs relative according to their own specific semantics / working directory.
	 *
	 * This method should be removed here and from the workspaceManager
	 */
	/** @return a workspace relative URI for a given URI */
	public URI makeWorkspaceRelative(URI uri) {
		return workspaceManager.makeWorkspaceRelative(uri);
	}

	/**
	 * Initialize the builder's workspace at the given location.
	 *
	 * @param newBaseDir
	 *            the location
	 */
	public void initialize(URI newBaseDir) {
		workspaceManager.initialize(newBaseDir);
	}

	/**
	 * Trigger an initial build in the background.
	 */
	public void initialBuild() {
		asyncRunBuildTask("initialBuild", workspaceBuilder::createInitialBuildTask);
	}

	/**
	 * Trigger a clean operation in the background. Afterwards, all previously build artifacts have been removed. This
	 * includes index data, source2generated mappings, cached issues and persisted index state. A subsequent build is
	 * not triggered automatically.
	 */
	public void clean() {
		asyncRunBuildTask("clean", workspaceBuilder::createCleanTask);
	}

	/**
	 * Triggers rebuild of the whole workspace
	 */
	public void reinitWorkspace() {
		asyncRunBuildTask("reinitWorkspace", workspaceBuilder::createInitialBuildTask);
	}

	/**
	 * Trigger an incremental build in the background.
	 */
	public void didSave(DidSaveTextDocumentParams params) {
		URI uri = getURI(params.getTextDocument());
		asyncRunBuildTask("didSave",
				() -> workspaceBuilder.createIncrementalBuildTask(Collections.singletonList(uri), Collections.emptyList()));
	}

	/**
	 * Trigger an incremental build in the background.
	 */
	public void didChangeWatchedFiles(DidChangeWatchedFilesParams params) {
		List<URI> dirtyFiles = new ArrayList<>();
		List<URI> deletedFiles = new ArrayList<>();
		for (FileEvent fileEvent : params.getChanges()) {
			URI uri = uriExtensions.toUri(fileEvent.getUri());
			FileChangeType changeType = fileEvent.getType();
			if (changeType == FileChangeType.Deleted) {
				deletedFiles.add(uri);
			} else {
				dirtyFiles.add(uri);
			}
		}
		if (!dirtyFiles.isEmpty() || !deletedFiles.isEmpty()) {
			asyncRunBuildTask("didChangeWatchedFiles",
					() -> workspaceBuilder.createIncrementalBuildTask(dirtyFiles, deletedFiles));
		}
	}

	// TODO accept a parameter `boolean cancelPrevious` and overload most of the other methods
	/*
	 * Use case: We don't want to cancel the clean when we run a reinitWorkspace afterwards.
	 */
	/**
	 * Obtain a buildable and run it on the queue used for the workspace builder.
	 *
	 * @param taskSupplier
	 *            the factory for the buildable.
	 */
	public CompletableFuture<?> asyncRunBuildTask(String description,
			Supplier<? extends BuildTask> taskSupplier) {
		return queuedExecutorService.submitAndCancelPrevious(XWorkspaceBuilder.class, description, cancelIndicator -> {
			return taskSupplier.get().build(cancelIndicator);
		});
	}

	/**
	 * Initiate an orderly shutdown.
	 */
	public void shutdown() {
		join();
		queuedExecutorService.shutdown();
	}

	/**
	 * Block until all submitted background work is done.
	 */
	public void join() {
		queuedExecutorService.join();
	}

	/**
	 * Returns the workspace issues known for the given URI.
	 */
	public CompletableFuture<ImmutableList<? extends LSPIssue>> asyncGetValidationIssues(URI uri) {
		return queuedExecutorService.submit(XWorkspaceManager.class, "getValidationIssues",
				cancelIndicator -> workspaceManager.getValidationIssues(uri));
	}

	/**
	 * Obtain the URI from the given identifier.
	 */
	protected URI getURI(TextDocumentIdentifier documentIdentifier) {
		return uriExtensions.toUri(documentIdentifier.getUri());
	}
}
