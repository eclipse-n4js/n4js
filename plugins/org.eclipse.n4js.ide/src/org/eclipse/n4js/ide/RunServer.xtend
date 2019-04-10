package org.eclipse.n4js.ide;

import com.google.inject.Guice
import java.net.InetSocketAddress
import java.nio.channels.AsynchronousServerSocketChannel
import java.nio.channels.Channels
import java.util.concurrent.Executors
import org.eclipse.lsp4j.jsonrpc.Launcher
import org.eclipse.lsp4j.services.LanguageClient
import org.eclipse.xtext.ide.server.LanguageServerImpl
import org.eclipse.xtext.ide.server.ServerModule
import org.eclipse.n4js.ts.scoping.builtin.ResourceSetWithBuiltInScheme
import org.eclipse.n4js.N4JSStandaloneSetup

class RunServer {

	def static void main(String[] args) throws Exception {
//		val injector = Guice.createInjector(new ServerModule())
		val injector = new N4JSIdeSetup().createInjectorAndDoEMFRegistration();
		val serverSocket = AsynchronousServerSocketChannel.open.bind(new InetSocketAddress("localhost", 5007))
		val threadPool = Executors.newCachedThreadPool()



		while (true) {
			var languageServer = injector.getInstance(LanguageServerImpl)

		//ClassLoader classLoader
			val classLoader = injector.getInstance(ClassLoader);
			val xyz = injector.getInstance(ResourceSetWithBuiltInScheme);

			val socketChannel = serverSocket.accept.get
			val in = Channels.newInputStream(socketChannel)
			val out = Channels.newOutputStream(socketChannel)
			val launcher = Launcher.createIoLauncher(languageServer, LanguageClient, in, out, threadPool, [it])
			languageServer.connect(launcher.remoteProxy)
			launcher.startListening
		}
	}
}	