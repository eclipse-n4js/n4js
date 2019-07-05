package org.eclipse.n4js.ide;

import java.net.InetSocketAddress
import java.nio.channels.AsynchronousServerSocketChannel
import java.nio.channels.Channels
import java.util.concurrent.Executors
import org.eclipse.lsp4j.jsonrpc.Launcher
import org.eclipse.lsp4j.services.LanguageClient
import org.eclipse.n4js.ide.server.N4JSLanguageServerImpl
import com.google.common.util.concurrent.Futures

class RunServer {

	def static void main(String[] args) throws Exception {
		val injector = new N4JSIdeSetup().createInjectorAndDoEMFRegistration();
		val serverSocket = AsynchronousServerSocketChannel.open.bind(new InetSocketAddress("localhost", 5007))
		val threadPool = Executors.newCachedThreadPool()

		while (true) {
			var languageServer = injector.getInstance(N4JSLanguageServerImpl)

			val socketChannel = serverSocket.accept.get
			val in = Channels.newInputStream(socketChannel)
			val out = Channels.newOutputStream(socketChannel)
			val launcher = Launcher.createIoLauncher(languageServer, LanguageClient, in, out, threadPool, [it])
			languageServer.connect(launcher.remoteProxy)
			Futures.getUnchecked(launcher.startListening)
			languageServer.requestManager.shutdown
			in.close
			out.close
			socketChannel.close
		}
	}
}	