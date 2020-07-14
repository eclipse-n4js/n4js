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
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.lsp4j.DidChangeWatchedFilesParams;
import org.eclipse.lsp4j.DidSaveTextDocumentParams;
import org.eclipse.lsp4j.FileChangeType;
import org.eclipse.lsp4j.FileEvent;
import org.eclipse.lsp4j.TextDocumentIdentifier;
import org.eclipse.n4js.ide.xtext.server.XBuildManager.XBuildable;
import org.eclipse.n4js.ide.xtext.server.concurrent.ConcurrentChunkedIndex;
import org.eclipse.n4js.ide.xtext.server.concurrent.ConcurrentIssueRegistry;
import org.eclipse.n4js.ide.xtext.server.concurrent.LSPExecutorService;
import org.eclipse.xtext.ide.server.UriExtensions;
import org.eclipse.xtext.resource.IResourceDescription.Delta;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.workspace.IProjectConfig;
import org.eclipse.xtext.workspace.ISourceFolder;

import com.google.common.base.Stopwatch;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Facade for all builder-related functionality in the LSP server. From outside the builder-related classes, all use of
 * the LSP builder should go through this class.
 */
@SuppressWarnings({ "javadoc", "restriction" })
@Singleton
public class LSPBuilder {

	private static final Logger LOG = Logger.getLogger(LSPBuilder.class);

	@Inject
	private LSPExecutorService lspExecutorService;

	@Inject
	private UriExtensions uriExtensions;

	@Inject
	private ProjectStatePersister persister;

	@Inject
	private XWorkspaceManager workspaceManager;

	private ConcurrentIssueRegistry issueRegistry;

	public URI getBaseDir() {
		return workspaceManager.getBaseDir();
	}

	/** @return a workspace relative URI for a given URI */
	public URI makeWorkspaceRelative(URI uri) {
		return workspaceManager.makeWorkspaceRelative(uri);
	}

	/** Returns the index. */
	public ConcurrentChunkedIndex getIndex() {
		return workspaceManager.getIndex();
	}

	/**
	 * Initialize the builder's workspace at the given location.
	 *
	 * @param newBaseDir
	 *            the location
	 */
	@SuppressWarnings("hiding")
	public void initialize(URI newBaseDir, ConcurrentIssueRegistry issueRegistry) {
		if (this.issueRegistry != null && issueRegistry != this.issueRegistry) {
			throw new IllegalArgumentException("the issue registry must not be changed");
		}
		this.issueRegistry = issueRegistry;
		workspaceManager.initialize(newBaseDir, issueRegistry);
	}

	public void initialBuild() {
		lspExecutorService.submitAndCancelPrevious(XBuildManager.class, "initialized",
				cancelIndicator -> doInitialBuild());

	}

	protected Void doInitialBuild() {
		Stopwatch sw = Stopwatch.createStarted();
		try {
			LOG.info("Start initial build ...");
			workspaceManager.doInitialBuild(CancelIndicator.NullImpl);
		} catch (Throwable t) {
			LOG.error(t.getMessage(), t);
			throw t;
		} finally {
			LOG.info("Initial build done after " + sw);
		}
		return null;
	}

	public CompletableFuture<Void> clean() {
		return lspExecutorService.submitAndCancelPrevious(XBuildManager.class, "clean", cancelIndicator -> {
			workspaceManager.clean(CancelIndicator.NullImpl);
			return null;
		});
	}

	/**
	 * Triggers rebuild of the whole workspace
	 */
	public CompletableFuture<Void> reinitWorkspace() {
		return lspExecutorService.submitAndCancelPrevious(XBuildManager.class, "didChangeConfiguration",
				cancelIndicator -> {
					workspaceManager.reinitialize();
					workspaceManager.doInitialBuild(CancelIndicator.NullImpl);
					return null;
				});
	}

	public void didSave(DidSaveTextDocumentParams params) {
		runBuildable("didSave", () -> toBuildable(params));
	}

	public void didChangeWatchedFiles(DidChangeWatchedFilesParams params) {
		// TODO: Set watched files to client. Note: Client may have performance issues with lots of folders to watch.
		final List<URI> dirtyFiles = new ArrayList<>();
		final List<URI> deletedFiles = new ArrayList<>();
		for (FileEvent fileEvent : params.getChanges()) {
			URI uri = uriExtensions.toUri(fileEvent.getUri());

			String fileName = uri.lastSegment();
			boolean skipFile = fileName.equals(ProjectStatePersister.FILENAME);

			if (!skipFile && isSourceFile(uri)) {
				FileChangeType changeType = fileEvent.getType();

				if (changeType == FileChangeType.Deleted) {
					deletedFiles.add(uri);
				} else {
					dirtyFiles.add(uri);
				}
			}
		}
		if (!dirtyFiles.isEmpty() || !deletedFiles.isEmpty()) {
			runBuildable("didChangeWatchedFiles", () -> workspaceManager.didChangeFiles(dirtyFiles, deletedFiles));
		}
	}

	/**
	 * Evaluate the params and deduce the respective build command.
	 */
	protected XBuildable toBuildable(DidSaveTextDocumentParams params) {
		return workspaceManager.didSave(getURI(params.getTextDocument()));
	}

	/**
	 * Compute a buildable and run it on the queue used for the builder.
	 *
	 * @param newBuildable
	 *            the factory for the buildable.
	 * @return the result.
	 */
	protected CompletableFuture<List<Delta>> runBuildable(String description,
			Supplier<? extends XBuildable> newBuildable) {
		return lspExecutorService.submitAndCancelPrevious(XBuildManager.class, description, cancelIndicator -> {
			XBuildable buildable = newBuildable.get();
			return buildable.build(cancelIndicator);
		});
	}

	public CompletableFuture<Void> shutdown() {
		return runBuildable("shutdown", () -> {
			return (cancelIndicator) -> {
				joinPersister();
				return null;
			};
		}).thenApply(any -> {
			persister.close();
			return null;
		});
	}

	public void joinPersister() {
		persister.pendingWrites().join();
	}

	protected boolean isSourceFile(URI uri) {
		IProjectConfig projectConfig = workspaceManager.getWorkspaceConfig().findProjectContaining(uri);
		if (projectConfig != null) {
			ISourceFolder sourceFolder = projectConfig.findSourceFolderContaining(uri);
			if (sourceFolder != null) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Obtain the URI from the given identifier.
	 */
	protected URI getURI(TextDocumentIdentifier documentIdentifier) {
		return uriExtensions.toUri(documentIdentifier.getUri());
	}
}
