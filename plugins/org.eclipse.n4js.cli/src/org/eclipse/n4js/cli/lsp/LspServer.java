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

import org.eclipse.lsp4j.jsonrpc.Launcher;
import org.eclipse.lsp4j.services.LanguageClient;
import org.eclipse.n4js.cli.N4jscConsole;
import org.eclipse.n4js.cli.N4jscFactory;
import org.eclipse.n4js.cli.N4jscOptions;
import org.eclipse.n4js.ide.server.N4JSLanguageServerImpl;

import com.google.common.util.concurrent.Futures;

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
		final AsynchronousServerSocketChannel serverSocket = AsynchronousServerSocketChannel.open()
				.bind(new InetSocketAddress("localhost", options.getPort()));

		try {
			while (true) {
				run(serverSocket, threadPool);
			}

		} finally {
			N4jscConsole.println("LSP server terminated.");
		}
	}

	private void run(AsynchronousServerSocketChannel serverSocket, ExecutorService threadPool)
			throws InterruptedException, ExecutionException, IOException {

		N4JSLanguageServerImpl languageServer = N4jscFactory.getLanguageServer();

		N4jscConsole.println("Listening for LSP clients...");
		AsynchronousSocketChannel socketChannel = serverSocket.accept().get();
		N4jscConsole.println("Connected to LSP client");

		InputStream in = Channels.newInputStream(socketChannel);
		OutputStream out = Channels.newOutputStream(socketChannel);
		Launcher<LanguageClient> launcher = Launcher.createIoLauncher(languageServer, LanguageClient.class, in,
				out, threadPool, a -> a);
		languageServer.connect(launcher.getRemoteProxy());
		Futures.getUnchecked(launcher.startListening());

		N4jscConsole.println("Shutdown connection to LSP client");
		languageServer.getRequestManager().shutdown();
		in.close();
		out.close();
		socketChannel.close();
	}

}