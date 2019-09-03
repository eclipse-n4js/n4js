/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.tester.server;

import static com.google.common.base.Joiner.on;
import static com.google.common.base.Optional.absent;
import static com.google.common.base.Optional.fromNullable;
import static com.google.common.cache.CacheBuilder.newBuilder;
import static com.google.common.collect.FluentIterable.from;
import static java.lang.Boolean.TRUE;
import static java.lang.Integer.parseInt;
import static java.lang.String.valueOf;
import static java.util.Collections.singletonList;
import static java.util.EnumSet.of;
import static javax.servlet.DispatcherType.ASYNC;
import static javax.servlet.DispatcherType.REQUEST;
import static org.apache.log4j.Logger.getLogger;
import static org.eclipse.jetty.servlets.CrossOriginFilter.ACCESS_CONTROL_ALLOW_CREDENTIALS_HEADER;
import static org.eclipse.jetty.servlets.CrossOriginFilter.ALLOWED_HEADERS_PARAM;
import static org.eclipse.jetty.servlets.CrossOriginFilter.ALLOWED_METHODS_PARAM;
import static org.eclipse.jetty.servlets.CrossOriginFilter.ALLOWED_ORIGINS_PARAM;
import static org.eclipse.jetty.servlets.CrossOriginFilter.ALLOW_CREDENTIALS_PARAM;
import static org.eclipse.jetty.servlets.CrossOriginFilter.PREFLIGHT_MAX_AGE_PARAM;
import static org.eclipse.n4js.tester.TesterModuleDefaults.DUMP_SERVER_ON_STOP_KEY;
import static org.eclipse.n4js.tester.TesterModuleDefaults.MAX_THREAD_COUNT_KEY;
import static org.eclipse.n4js.tester.TesterModuleDefaults.MIN_THREAD_COUNT_KEY;
import static org.eclipse.n4js.tester.TesterModuleDefaults.THREAD_POOL_BLOCKING_CAPACITY_KEY;
import static org.eclipse.n4js.tester.server.resources.ResourceRouterServlet.CONTEXT_PATH;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.util.thread.ThreadPool;
import org.eclipse.n4js.tester.server.resources.HttpMethod;
import org.eclipse.n4js.tester.server.resources.ResourceRouterServlet;
import org.eclipse.n4js.tester.server.resources.ServletHolderBuilder;

import com.google.common.base.Optional;
import com.google.common.base.Throwables;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

/**
 * {@link HttpServerManager HTTP server manager} implementation for Jetty.
 */
@Singleton
public class JettyManager implements HttpServerManager {

	private static final Logger LOGGER = getLogger(JettyManager.class);

	/** The number of seconds that pre-flight requests can be cached by the client. */
	private static final String PREFLIGHT_MAX_AGE_VALUE = "728000";
	private static final String CONTENT_TYPE = "Content-Type";
	private static final String X_PING_OTHER = "X-PINGOTHER";
	private static final String X_REQUESTED_WITH = "X-Requested-With";
	private static final String ORIGIN = "Origin";
	private static final String ACCEPT = "Accept";

	private final ServletHolderBuilder servletHolderBuilder;
	private final boolean dumpServerOnStop;
	private final int minThreadCount;
	private final int maxThreadCount;
	private final int threadPoolCapacity;

	@Inject
	/* default */ JettyManager(final ServletHolderBuilder servletHolderBuilder,
			final @Named(DUMP_SERVER_ON_STOP_KEY) boolean dumpServerOnStop,
			final @Named(MIN_THREAD_COUNT_KEY) int minThreadCount,
			final @Named(MAX_THREAD_COUNT_KEY) int maxThreadCount,
			final @Named(THREAD_POOL_BLOCKING_CAPACITY_KEY) int threadPoolCapacity) {

		this.servletHolderBuilder = servletHolderBuilder;
		this.dumpServerOnStop = dumpServerOnStop;
		this.minThreadCount = minThreadCount;
		this.maxThreadCount = maxThreadCount;
		this.threadPoolCapacity = threadPoolCapacity;

	}

	private final LoadingCache<Integer, Server> serverCache = newBuilder().build(new CacheLoader<Integer, Server>() {

		@SuppressWarnings("resource")
		@Override
		public Server load(final Integer port) throws Exception {
			Server server = null;
			try {
				server = new Server(configureThreadPool(port));
				ServerConnector connector = new ServerConnector(server);
				connector.setPort(port);
				server.setConnectors(new Connector[] { connector });
				final ServletContextHandler contextHandler = new ServletContextHandler(server, CONTEXT_ROOT, true,
						false);
				ServletHolder servlet = servletHolderBuilder.build(ResourceRouterServlet.class);
				contextHandler.addServlet(servlet, CONTEXT_PATH + "*");
				contextHandler.addFilter(configureCors(), CONTEXT_PATH + "*", of(REQUEST, ASYNC));
				server.setDumpBeforeStop(dumpServerOnStop);
				server.start();
			} catch (Exception e) {
				LOGGER.error("Cache failed to start new server instance at PORT=" + port, e);
				if (server != null && server.isRunning()) {
					server.stop();
					server.join();
				}
				Throwables.throwIfUnchecked(e);
				throw new RuntimeException(e);
			}
			return server;
		}

		private ThreadPool configureThreadPool(final int port) {
			final QueuedThreadPool threadPool = new QueuedThreadPool(threadPoolCapacity);
			threadPool.setMinThreads(minThreadCount);
			threadPool.setMaxThreads(maxThreadCount);
			threadPool.setName("Jetty thread pool [" + port + "]");
			threadPool.setDetailedDump(true);
			return threadPool;
		}

		private FilterHolder configureCors() {
			final FilterHolder filter = new FilterHolder(new CrossOriginFilter());
			filter.setInitParameter(ALLOWED_ORIGINS_PARAM, "*");
			filter.setInitParameter(ACCESS_CONTROL_ALLOW_CREDENTIALS_HEADER, valueOf(TRUE));
			filter.setInitParameter(ALLOWED_METHODS_PARAM, on(",").join(HttpMethod.values()));
			filter.setInitParameter(ALLOWED_HEADERS_PARAM,
					on(",").join(X_PING_OTHER, ORIGIN, X_REQUESTED_WITH, CONTENT_TYPE, ACCEPT));
			filter.setInitParameter(PREFLIGHT_MAX_AGE_PARAM, PREFLIGHT_MAX_AGE_VALUE);
			filter.setInitParameter(ALLOW_CREDENTIALS_PARAM, valueOf(TRUE));
			return filter;
		}

	});

	@Override
	public int startServer(final Map<String, Object> config) {
		final Optional<Integer> port = getPort(config);
		if (port.isPresent()) {
			try {

				if (!isValidPort(port.get())) {
					throw new RuntimeException(
							"Cannot instantiate Jetty instance. Reason: invalid port number: " + port.get());
				}

				Server server = serverCache.getIfPresent(port.get());
				if (null == server) {
					// Might happen that original port request was modified. Consider two running IDEs at the same time.
					int checkedPort = ensurePortIsAvailable(port.get());
					if (checkedPort != port.get().intValue()) {
						LOGGER.warn("Original port was modified to: " + checkedPort + ".");
						LOGGER.warn("LDE will not be able to send HTTP requests to this server by default. "
								+ "Please check used ports or reconfigure LDE to be compatible with this embedded Jetty server.");
					}
					server = serverCache.get(checkedPort);
					LOGGER.info("Jetty instance has successfully started on '" + checkedPort + "'.");
					return checkedPort;
				} else {
					LOGGER.info("Jetty instance is already running on '" + port.get() + "'.");
					return port.get();
				}
			} catch (final Exception e) {
				LOGGER.error("Error while starting Jetty server on '" + port.get() + "'.", e);
				Throwables.throwIfUnchecked(e);
				throw new RuntimeException(e);
			}
		} else {
			LOGGER.error("Due to missing HTTP port properties Jetty cannot be started.");
			throw new RuntimeException("Due to missing HTTP port properties Jetty cannot be started.");
		}
	}

	@Override
	public void stopServer(final int port) {
		for (final int localPort : (-1 == port ? getAllRunningServerPorts() : singletonList(port))) {
			if (isRunning(localPort)) {
				try {
					final Server server = serverCache.getIfPresent(localPort);
					if (null != server) {
						server.stop();
						server.join();
						if (server.isStopped()) {
							LOGGER.info("Jetty instance has successfully stopped on '" + localPort + "'.");
							serverCache.invalidate(localPort);
						} else {
							LOGGER.warn("Unexpected behavior while shutting down Jetty on '" + localPort
									+ "'. Termination failed.");
						}
					}
				} catch (final Exception e) {
					LOGGER.error("Error while stopping Jetty server on '" + localPort + "'.", e);
				}
			}
		}
	}

	@Override
	public boolean isRunning(final int port) {

		if (!isValidPort(port)) {
			return false;
		}

		final Server server = serverCache.getIfPresent(port);
		if (null == server) {
			return false;
		}
		if (!server.isStarted()) {
			return false;
		}

		return isPortInUse(port);
	}

	private boolean isPortInUse(final int port) {
		// Plain socket, try to connect to some existing server.
		// If we succeed, we definitely know that there is
		// something running and should report "port is used":
		try (final Socket so = new Socket(LOCALHOST, port);) {
			so.setReuseAddress(true);
			so.close();
			// Succeeded, means in use !
			return true;
		} catch (final IOException e) {
			// positive case, we are looking for an unused port.
			// The Exception here signals, that it is unused.
		}

		// Server-socket is for creating our own server here:
		try (final ServerSocket ss = new ServerSocket(port);) {
			ss.setReuseAddress(true);
			ss.close();
			// Succeeded, means we can create our own server here, the port is not in use.
			return false;
		} catch (final IOException e) {
			return true;
		}
	}

	private boolean isValidPort(final int port) {
		return 0 < port && 65535 >= port;
	}

	/**
	 * Very naive implementation to check if a port is in use or not before starting Jetty server.
	 *
	 * @param port
	 *            designated port (e.g. 9415)
	 * @return actual free port.
	 */
	public int ensurePortIsAvailable(final Integer port) {
		if (null == port) {
			final int nextPort = getNextAvailablePort();
			LOGGER.warn("Port was null. Trying to use next available port: " + nextPort + ".");
			return nextPort;
		}

		if (!isValidPort(port)) {
			final int nextPort = getNextAvailablePort();
			LOGGER.warn("Port was invalid: " + port + ". Trying to use next available port: " + nextPort + ".");
			return nextPort;
		}

		if (isPortInUse(port)) {
			final int nextPort = getNextAvailablePort();
			LOGGER.warn("Port is already in use: " + port + ". Trying to use next available port: " + nextPort + ".");
			return nextPort;
		}

		return port;
	}

	private int getNextAvailablePort() {
		try (final ServerSocket ss = new ServerSocket(0)) {
			return ss.getLocalPort();
		} catch (final IOException e) {
			throw new RuntimeException("Error while trying to get next available port.", e);
		}
	}

	private int getLocalPort(Server server) {
		return ((ServerConnector) server.getConnectors()[0]).getLocalPort();
	}

	private Iterable<Integer> getAllRunningServerPorts() {
		return from(serverCache.asMap().values()).filter(s -> !s.isStopped()).transform(s -> getLocalPort(s));
	}

	private Optional<Integer> getPort(final Map<String, Object> config) {
		final Object portObject = config.get(HTTP_PORT);

		if (null == portObject) {
			LOGGER.warn("HTTP port is not configured for the Jetty.");
			return absent();
		}

		try {
			final int port = parseInt(valueOf(portObject));
			if (isValidPort(port)) {
				return fromNullable(port);
			}
			LOGGER.error("Port number must be between 0 and 65535. Was: " + port);
			return absent();
		} catch (final NumberFormatException e) {
			LOGGER.warn("Invalid HTTP port number. It was: '" + valueOf(portObject) + "'.");
			return absent();
		}
	}

}
