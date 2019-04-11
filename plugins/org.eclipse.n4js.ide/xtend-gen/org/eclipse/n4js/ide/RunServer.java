package org.eclipse.n4js.ide;

import com.google.inject.Injector;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.Channels;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import org.eclipse.lsp4j.jsonrpc.Launcher;
import org.eclipse.lsp4j.jsonrpc.MessageConsumer;
import org.eclipse.lsp4j.services.LanguageClient;
import org.eclipse.n4js.ide.N4JSIdeSetup;
import org.eclipse.n4js.ts.scoping.builtin.ResourceSetWithBuiltInScheme;
import org.eclipse.xtext.ide.server.LanguageServerImpl;

@SuppressWarnings("all")
public class RunServer {
  public static void main(final String[] args) throws Exception {
    final Injector injector = new N4JSIdeSetup().createInjectorAndDoEMFRegistration();
    AsynchronousServerSocketChannel _open = AsynchronousServerSocketChannel.open();
    InetSocketAddress _inetSocketAddress = new InetSocketAddress("localhost", 5007);
    final AsynchronousServerSocketChannel serverSocket = _open.bind(_inetSocketAddress);
    final ExecutorService threadPool = Executors.newCachedThreadPool();
    while (true) {
      {
        LanguageServerImpl languageServer = injector.<LanguageServerImpl>getInstance(LanguageServerImpl.class);
        final ClassLoader classLoader = injector.<ClassLoader>getInstance(ClassLoader.class);
        final ResourceSetWithBuiltInScheme xyz = injector.<ResourceSetWithBuiltInScheme>getInstance(ResourceSetWithBuiltInScheme.class);
        final AsynchronousSocketChannel socketChannel = serverSocket.accept().get();
        final InputStream in = Channels.newInputStream(socketChannel);
        final OutputStream out = Channels.newOutputStream(socketChannel);
        final Function<MessageConsumer, MessageConsumer> _function = (MessageConsumer it) -> {
          return it;
        };
        final Launcher<LanguageClient> launcher = Launcher.<LanguageClient>createIoLauncher(languageServer, LanguageClient.class, in, out, threadPool, _function);
        languageServer.connect(launcher.getRemoteProxy());
        launcher.startListening();
      }
    }
  }
}
