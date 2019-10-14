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
import org.eclipse.n4js.ide.server.N4JSLanguageServerImpl;

/**
 *
 */
public class LspServer {
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
		N4jscConsole.println("Start LSP server");

		final ExecutorService threadPool = Executors.newCachedThreadPool();

		try {
			while (true) {
				setupAndRun(threadPool);
			}

		} finally {
			N4jscConsole.println("LSP server terminated.");
		}
	}

	private void setupAndRun(ExecutorService threadPool) throws InterruptedException, ExecutionException, IOException {
		N4JSLanguageServerImpl languageServer = N4jscFactory.getLanguageServer();

		Builder<LanguageClient> lsBuilder = new Builder<LanguageClient>()
				.setLocalService(languageServer)
				.setRemoteInterface(LanguageClient.class)
				.setExecutorService(threadPool)
		// .wrapMessages(a -> a)
		// .traceMessages(trace)
		;

		if (options.isStdio()) {
			N4jscConsole.setSuppress(true);
			setupAndRunWithSystemIO(languageServer, lsBuilder);
		} else {
			setupAndRunWithSocket(languageServer, lsBuilder);
		}
	}

	private void setupAndRunWithSocket(N4JSLanguageServerImpl languageServer, Builder<LanguageClient> lsBuilder)
			throws InterruptedException, ExecutionException, IOException {

		InetSocketAddress address = new InetSocketAddress("localhost", options.getPort());

		try (AsynchronousServerSocketChannel serverSocket = AsynchronousServerSocketChannel.open().bind(address);) {

			N4jscConsole.println("Listening for LSP clients...");

			try (AsynchronousSocketChannel socketChannel = serverSocket.accept().get();
					InputStream in = Channels.newInputStream(socketChannel);
					OutputStream out = Channels.newOutputStream(socketChannel);) {

				N4jscConsole.println("Connected to LSP client");

				run(languageServer, lsBuilder, in, out);
			}
		}
	}

	private void setupAndRunWithSystemIO(N4JSLanguageServerImpl languageServer, Builder<LanguageClient> lsBuilder)
			throws InterruptedException {

		run(languageServer, lsBuilder, System.in, System.out);
	}

	private void run(N4JSLanguageServerImpl languageServer, Builder<LanguageClient> lsBuilder, InputStream in,
			OutputStream out)
			throws InterruptedException {

		Launcher<LanguageClient> launcher = lsBuilder
				.setInput(in)
				.setOutput(out)
				.create();

		languageServer.connect(launcher.getRemoteProxy());
		Future<Void> future = launcher.startListening();
		N4jscConsole.println("LSP Server connected");

		while (!future.isDone()) {
			Thread.sleep(10_000l);
		}

		N4jscConsole.println("Shutdown connection to LSP client");
		languageServer.getRequestManager().shutdown();
	}

}