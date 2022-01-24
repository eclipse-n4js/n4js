/**
 * Copyright (c) 2022 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.tests.helper.server;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import org.eclipse.lsp4j.ClientCapabilities;
import org.eclipse.lsp4j.ExecuteCommandCapabilities;
import org.eclipse.lsp4j.ExecuteCommandParams;
import org.eclipse.lsp4j.InitializeParams;
import org.eclipse.lsp4j.RenameCapabilities;
import org.eclipse.lsp4j.TextDocumentClientCapabilities;
import org.eclipse.lsp4j.WorkspaceClientCapabilities;
import org.eclipse.n4js.cli.N4jscFactory;
import org.eclipse.n4js.cli.N4jscTestFactory;
import org.eclipse.n4js.ide.server.commands.N4JSCommandService;
import org.eclipse.n4js.ide.tests.helper.client.IdeTestLanguageClient;
import org.eclipse.n4js.workspace.locations.FileURI;
import org.eclipse.n4js.xtext.ide.server.ProjectStatePersisterConfig;
import org.eclipse.n4js.xtext.ide.server.XLanguageServerImpl;

import com.google.common.base.Optional;
import com.google.inject.Injector;
import com.google.inject.Module;

/**
 * NOTE: most tests should subclass {@link AbstractIdeTest} instead of using this class!
 * <p>
 * Manages a pair of instances of the LSP {@link XLanguageServerImpl server} and {@link IdeTestLanguageClient client}.
 * This class is intended to be instantiated with <code>new</code>, not to be injected.
 */
public class TestLspManager {

	private Injector injector;

	private XLanguageServerImpl languageServer;

	private IdeTestLanguageClient languageClient;

	/** Returns the injector used for the LSP server and client managed by this instance. */
	public Injector getInjector() {
		return injector;
	}

	/** Returns the language server. */
	public XLanguageServerImpl getLanguageServer() {
		return languageServer;
	}

	/** Returns the language client. */
	public IdeTestLanguageClient getLanguageClient() {
		return languageClient;
	}

	/**
	 * Call this method after creating the test project(s) to start the LSP server and perform other initializations
	 * required for testing.
	 */
	public void startAndWaitForLspServer(File root, Optional<Class<? extends Module>> overridingModule,
			boolean enableProjectStatePersister) {
		startLspServerWithoutWaiting(root, overridingModule, enableProjectStatePersister);
		joinServerRequests();
	}

	/** Same as {@link #startAndWaitForLspServer(File, Optional, boolean)}, but without waiting. */
	public void startLspServerWithoutWaiting(File root, Optional<Class<? extends Module>> overridingModule,
			boolean enableProjectStatePersister) {
		createInjector(overridingModule, enableProjectStatePersister);
		doStartLspServer(root);
	}

	/** Creates injector for N4JS */
	protected void createInjector(Optional<Class<? extends Module>> overridingModule,
			boolean enableProjectStatePersister) {
		N4jscTestFactory.set(true, overridingModule);
		injector = N4jscFactory.getOrCreateInjector();
		languageServer = injector.getInstance(XLanguageServerImpl.class);
		languageClient = injector.getInstance(IdeTestLanguageClient.class);
		if (enableProjectStatePersister) {
			ProjectStatePersisterConfig persisterConfig = injector.getInstance(ProjectStatePersisterConfig.class);
			persisterConfig.setDeleteState(false);
			persisterConfig.setWriteToDisk(true);
		}
	}

	/** Connects, initializes and waits for the initial build of the test project. */
	@SuppressWarnings("deprecation")
	protected void doStartLspServer(File root) {
		ClientCapabilities capabilities = new ClientCapabilities();
		WorkspaceClientCapabilities wcc = new WorkspaceClientCapabilities();
		wcc.setExecuteCommand(new ExecuteCommandCapabilities());
		capabilities.setWorkspace(wcc);
		TextDocumentClientCapabilities tdcc = new TextDocumentClientCapabilities();
		tdcc.setRename(new RenameCapabilities(true, false)); // activate 'prepareRename' requests
		capabilities.setTextDocument(tdcc);
		InitializeParams initParams = new InitializeParams();
		initParams.setCapabilities(capabilities);
		initParams.setRootUri(new FileURI(root).toString());

		languageServer.connect(languageClient);
		languageServer.initialize(initParams)
				.join(); // according to LSP, we must to wait here before sending #initialized():
		languageServer.initialized(null);
	}

	/** Waits until the LSP server idles. */
	public void joinServerRequests() {
		languageServer.joinServerRequests();
	}

	/** Like {@link #cleanBuildWithoutWait()}, but {@link #joinServerRequests() waits} for LSP server to finish. */
	public void cleanBuildAndWait() {
		// NOTE: the #join() in the next line is required; the #joinServerRequests() below is not sufficient!
		cleanBuildWithoutWait().join();
		joinServerRequests();
	}

	/** Cleans and rebuilds entire workspace without waiting for LSP server to finish. */
	public CompletableFuture<Object> cleanBuildWithoutWait() {
		return executeCommand(N4JSCommandService.N4JS_REBUILD);
	}

	/** Executes the command with the given ID and the given arguments. */
	public CompletableFuture<Object> executeCommand(String commandId, Object... args) {
		Objects.requireNonNull(commandId);
		Objects.requireNonNull(args);
		ExecuteCommandParams params = new ExecuteCommandParams(commandId, Arrays.asList(args));
		return languageServer.executeCommand(params);
	}

	/** Shuts down a running LSP server. Does not clean disk. */
	public void shutdownLspServer() {
		if (languageServer == null) {
			throw new IllegalStateException("trying to shut down LSP server, but it was never started");
		}
		// clear thread pools
		languageServer.shutdown().join();
		languageClient.clearLogMessages();
		languageClient.clearIssues();
		N4jscTestFactory.unset();
	}
}
