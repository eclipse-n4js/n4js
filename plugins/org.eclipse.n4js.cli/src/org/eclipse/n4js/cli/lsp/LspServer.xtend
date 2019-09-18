package org.eclipse.n4js.cli.lsp;

import java.net.InetSocketAddress
import java.nio.channels.AsynchronousServerSocketChannel
import java.nio.channels.Channels
import java.util.concurrent.Executors
import org.eclipse.lsp4j.jsonrpc.Launcher
import org.eclipse.lsp4j.services.LanguageClient
import org.eclipse.n4js.ide.server.N4JSLanguageServerImpl
import com.google.common.util.concurrent.Futures
import org.eclipse.n4js.ide.N4JSIdeSetup
import org.eclipse.n4js.cli.N4jscOptions

class LspServer {
	val final N4jscOptions options;

	def static void main(String[] args) throws Exception {
		start(new N4jscOptions());
	}
	
	def static void start(N4jscOptions options) throws Exception {
		val server = new LspServer(options);
		server.start();
	}
	
	new(N4jscOptions options) {
		this.options = options;
	}
	
	def void start() throws Exception {
		println("LspServer start");
		
		val injector = new N4JSIdeSetup().createInjectorAndDoEMFRegistration();
		val serverSocket = AsynchronousServerSocketChannel.open.bind(new InetSocketAddress("localhost", options.port))
		val threadPool = Executors.newCachedThreadPool()

		try {
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
		
		} finally {
			println("LspServer end.");
		}
	}
}	