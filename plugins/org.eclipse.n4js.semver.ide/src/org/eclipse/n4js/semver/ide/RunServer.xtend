package org.eclipse.n4js.semver.ide;

import com.google.inject.Guice
import java.net.InetSocketAddress
import java.nio.channels.AsynchronousServerSocketChannel
import java.nio.channels.Channels
import java.util.concurrent.Executors
import org.eclipse.lsp4j.jsonrpc.Launcher
import org.eclipse.lsp4j.services.LanguageClient
import org.eclipse.xtext.ide.server.LanguageServerImpl
import org.eclipse.xtext.ide.server.ServerModule

class RunServer {

	def static void main(String[] args) throws Exception {
		val injector = Guice.createInjector(new ServerModule())
		val serverSocket = AsynchronousServerSocketChannel.open.bind(new InetSocketAddress("localhost", 5007))
		val threadPool = Executors.newCachedThreadPool()



		while (true) {
			println("started LSP server");

			var languageServer = injector.getInstance(LanguageServerImpl)
			val socketChannel = serverSocket.accept.get
			val in = Channels.newInputStream(socketChannel)
			val out = Channels.newOutputStream(socketChannel)
			val launcher = Launcher.createIoLauncher(languageServer, LanguageClient, in, out, threadPool, [it])
			languageServer.connect(launcher.remoteProxy)
			launcher.startListening
		}
	}
}	