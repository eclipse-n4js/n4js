/**
 * Copyright (c) 2019 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.cli.lsp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.Channels;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.eclipse.lsp4j.jsonrpc.Launcher;
import org.eclipse.lsp4j.jsonrpc.Launcher.Builder;
import org.eclipse.lsp4j.services.LanguageClient;
import org.eclipse.n4js.cli.N4jscConsole;
import org.eclipse.n4js.cli.N4jscFactory;
import org.eclipse.n4js.cli.N4jscOptions;
import org.eclipse.n4js.ide.xtext.server.ExecuteCommandParamsTypeAdapter;
import org.eclipse.n4js.ide.xtext.server.ProjectStatePersisterConfig;
import org.eclipse.n4js.ide.xtext.server.XLanguageServerImpl;

import com.google.common.util.concurrent.Futures;
import com.google.inject.Injector;

/**
 * The language server facade.
 */
public class LspServer {
	/** The LSP client will wait for this message. */
	static final String LSP_SYNC_MESSAGE = "Listening for LSP clients";

	private final N4jscOptions options;

	/** Starts the LSP server in a blocking fashion */
	public static void start(N4jscOptions options) throws Exception {
		LspServer server = new LspServer(options);
		server.start();
	}

	private LspServer(N4jscOptions options) {
		this.options = options;
	}

	/** Starts the LSP server in a blocking fashion */
	public void start() throws Exception {
		N4jscConsole.println("LSP server starts");
		final ExecutorService threadPool = Executors.newCachedThreadPool();

		try {
			while (true) {
				setPersistionOptions();
				XLanguageServerImpl languageServer = N4jscFactory.getLanguageServer();
				setupAndRun(threadPool, languageServer);
				N4jscFactory.resetInjector();
			}

		} finally {
			N4jscConsole.println("LSP server terminated.");
		}
	}

	private void setPersistionOptions() {
		Injector injector = N4jscFactory.getOrCreateInjector();
		ProjectStatePersisterConfig persisterConfig = injector.getInstance(ProjectStatePersisterConfig.class);
		persisterConfig.setDeleteState(options.isClean());
		persisterConfig.setWriteToDisk(!options.isNoPersist());
	}

	private void setupAndRun(ExecutorService threadPool, XLanguageServerImpl languageServer)
			throws InterruptedException, ExecutionException, IOException {

		Builder<LanguageClient> lsBuilder = new PatchedLauncherBuilder<LanguageClient>()
				.setLocalService(languageServer)
				.setRemoteInterface(LanguageClient.class)
				.setExecutorService(threadPool)
				.configureGson(gsonBuilder -> {
					gsonBuilder.registerTypeAdapterFactory(new ExecuteCommandParamsTypeAdapter.Factory(languageServer));
				})
		// .traceMessages(new PrintWriter(System.out))
		// .wrapMessages(a -> a)
		;

		if (options.isStdio()) {
			setupAndRunWithSystemIO(languageServer, lsBuilder);
		} else {
			setupAndRunWithSocket(languageServer, lsBuilder);
		}
	}

	private void setupAndRunWithSocket(XLanguageServerImpl languageServer, Builder<LanguageClient> lsBuilder)
			throws InterruptedException, ExecutionException, IOException {

		InetSocketAddress address = new InetSocketAddress("localhost", options.getPort());

		try (AsynchronousServerSocketChannel serverSocket = AsynchronousServerSocketChannel.open().bind(address);) {

			// Attention: the VSCode LSP extension is waiting for this line 'Listening for LSP clients'.
			N4jscConsole.println(LSP_SYNC_MESSAGE + " on port " + options.getPort() + "...");

			try (AsynchronousSocketChannel socketChannel = serverSocket.accept().get();
					InputStream in = Channels.newInputStream(socketChannel);
					OutputStream out = Channels.newOutputStream(socketChannel)) {

				N4jscConsole.println("Connected to LSP client");
				run(languageServer, lsBuilder, in, out);
			}
		}
	}

	private void setupAndRunWithSystemIO(XLanguageServerImpl languageServer, Builder<LanguageClient> lsBuilder) {
		N4jscConsole.println(LSP_SYNC_MESSAGE + " on stdio");
		N4jscConsole.setSuppress(true);
		run(languageServer, lsBuilder, System.in, System.out);
	}

	private void run(XLanguageServerImpl languageServer, Builder<LanguageClient> lsBuilder, InputStream in,
			OutputStream out) {

		Launcher<LanguageClient> launcher = lsBuilder
				.setInput(in)
				.setOutput(out)
				.create();

		languageServer.connect(launcher.getRemoteProxy());
		Future<Void> future = launcher.startListening();
		N4jscConsole.println("LSP Server connected");

		Futures.getUnchecked(future);

		N4jscConsole.println("Shutdown connection to LSP client");
		languageServer.getQueuedExecutorService().shutdown();
	}

}