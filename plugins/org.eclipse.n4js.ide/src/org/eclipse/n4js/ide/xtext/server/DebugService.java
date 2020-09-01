/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.xtext.server;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.lf5.LogLevel;
import org.eclipse.lsp4j.MessageParams;
import org.eclipse.lsp4j.MessageType;
import org.eclipse.lsp4j.jsonrpc.JsonRpcException;
import org.eclipse.lsp4j.jsonrpc.Launcher.Builder;
import org.eclipse.lsp4j.jsonrpc.MessageConsumer;
import org.eclipse.lsp4j.jsonrpc.MessageIssueException;
import org.eclipse.lsp4j.jsonrpc.messages.Message;
import org.eclipse.lsp4j.jsonrpc.messages.NotificationMessage;
import org.eclipse.lsp4j.jsonrpc.messages.RequestMessage;
import org.eclipse.lsp4j.jsonrpc.services.JsonRequest;
import org.eclipse.lsp4j.jsonrpc.services.JsonSegment;
import org.eclipse.lsp4j.services.LanguageClient;
import org.eclipse.n4js.ide.xtext.server.DebugService.DebugServiceNullImpl;
import org.eclipse.n4js.ide.xtext.server.build.ConcurrentIndex;
import org.eclipse.n4js.utils.Strings;
import org.eclipse.n4js.xtext.workspace.WorkspaceConfigSnapshot;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.inject.ImplementedBy;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * The {@link DebugService} uses a separate endpoint to avoid the infrastructure of other calls to ordinary LSP
 * end-points and hence to increase robustness in case of errors in the source code.
 */
@JsonSegment("debug")
@ImplementedBy(DebugServiceNullImpl.class)
public interface DebugService {

	/** Sets the client to communicate to */
	void connect(LanguageClient client);

	/** Sets the log level of Log4j. @see {@link LogLevel} */
	@JsonRequest
	CompletableFuture<Void> setLogLevel(String level);

	/** Prints debug information as returned by {@link #getDebugInfo()} on the output channel */
	@JsonRequest
	CompletableFuture<Void> printDebugInfo();

	/**
	 * Returns debug information as a single string, without sending it to the client or otherwise reporting it. Returns
	 * <code>null</code> if not available.
	 */
	String getDebugInfo();

	/** Default null implementation */
	class DebugServiceNullImpl implements DebugService {
		@Override
		public void connect(LanguageClient client) {
			// nothing to do
		}

		@Override
		public CompletableFuture<Void> setLogLevel(String level) {
			return CompletableFuture.failedFuture(new UnsupportedOperationException());
		}

		@Override
		public CompletableFuture<Void> printDebugInfo() {
			return CompletableFuture.failedFuture(new UnsupportedOperationException());
		}

		@Override
		public String getDebugInfo() {
			return null;
		}
	}

	/** Abstract base implementation for debug services that intend to include LSP message tracing. */
	@Singleton
	public abstract class AbstractTracingDebugService implements DebugService {

		/** Number of recent LSP messages that will be shown when reporting an error. */
		public static final int TRACE_SIZE = 20;

		private static final Set<String> IGNORED_LSP_METHODS = ImmutableSet.of("window/logMessage");

		private final LinkedList<Message> trace = new LinkedList<>();

		/**
		 * Returns a wrapper that will be registered via method {@link Builder#wrapMessages(Function)} during server
		 * startup, allowing this debug service to automatically record LSP messages.
		 */
		public Function<MessageConsumer, MessageConsumer> getTracingMessageWrapper() {
			return (consumer) -> {
				return new MessageConsumer() {
					@Override
					public void consume(Message msg) throws MessageIssueException, JsonRpcException {
						onLspMessage(msg);
						consumer.consume(msg);
					}
				};
			};
		}

		private void onLspMessage(Message msg) {
			if (IGNORED_LSP_METHODS.contains(getLspMethod(msg))) {
				return;
			}
			synchronized (trace) {
				trace.addLast(msg);
				while (trace.size() > TRACE_SIZE) {
					trace.removeFirst();
				}
			}
		}

		/** Returns the last {@value #TRACE_SIZE} LSP messages. */
		protected List<Message> getTrace() {
			synchronized (trace) {
				return ImmutableList.copyOf(trace);
			}
		}

		/** Like {@link #getTrace()}, but returns a string representation of the LSP messages. */
		protected String getTraceDump() {
			String NL = System.lineSeparator();
			StringBuilder sb = new StringBuilder();
			sb.append("trace of last ");
			sb.append(TRACE_SIZE);
			sb.append(" messages:");
			List<Message> currTrace = getTrace();
			for (Message msg : currTrace) {
				String msgStr = msg.toString().replaceAll("\\s+", " ").trim();
				sb.append(NL);
				sb.append(msgStr);
			}
			return sb.toString();
		}

		/** Returns the LSP method for the given LSP message. */
		protected static String getLspMethod(Message msg) {
			if (msg instanceof NotificationMessage) {
				return ((NotificationMessage) msg).getMethod();
			} else if (msg instanceof RequestMessage) {
				return ((RequestMessage) msg).getMethod();
			} else {
				return null;
			}
		}
	}

	/** Default implementation */
	@Singleton
	class DebugServiceDefaultImpl extends AbstractTracingDebugService {
		private final Logger LOG = LogManager.getLogger(DebugServiceDefaultImpl.class);

		@Inject
		private QueuedExecutorService executerService;

		@Inject
		private ConcurrentIndex fullIndex;

		private LanguageClient client;

		@Override
		public void connect(LanguageClient _client) {
			this.client = _client;
		}

		@Override
		public CompletableFuture<Void> setLogLevel(String level) {
			Level actualLevel = Level.toLevel(level.toUpperCase());
			Logger.getRootLogger().setLevel(actualLevel);
			LOG.info("log level set to " + Logger.getRootLogger().getLevel());
			return CompletableFuture.completedFuture(null);
		}

		@Override
		public CompletableFuture<Void> printDebugInfo() {
			String msgText = getDebugInfo();
			if (msgText == null) {
				msgText = "Debug information is not available.";
			}
			MessageParams message = new MessageParams();
			message.setMessage(msgText);
			message.setType(MessageType.Log);
			client.logMessage(message);
			return CompletableFuture.completedFuture(null);
		}

		@Override
		public String getDebugInfo() {
			String info = "==   DEBUG INFO   ==\n";
			info += getTraceDump();
			info += "\n--------------\n";
			info += getWorkspaceConfigDump();
			info += "\n--------------\n";
			info += getExecuterServiceDump();
			info += "\n--------------\n";
			info += getMemoryDump();
			info += "\n--------------\n";
			info += getThreadDump();
			info += "\n== DEBUG INFO END ==\n";
			return info;
		}

		protected String getThreadDump() {
			long currentId = Thread.currentThread().getId();
			ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
			StringBuilder dump = new StringBuilder("Thread dump:\n");
			dump.append("Current ThreadId: ").append(currentId).append("\n");

			ThreadInfo[] infos = threadMXBean.dumpAllThreads(threadMXBean.isObjectMonitorUsageSupported(),
					threadMXBean.isSynchronizerUsageSupported());

			for (ThreadInfo info : infos) {
				dump.append(info.toString());
			}
			return dump.toString();
		}

		protected String getExecuterServiceDump() {
			String dump = executerService.stringify();
			return dump;
		}

		protected String getMemoryDump() {
			MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
			MemoryUsage memoryUsage = memoryBean.getHeapMemoryUsage();
			String dump = "Memory Usage:\n";
			dump += memoryUsage.toString();
			return dump;
		}

		protected String getWorkspaceConfigDump() {
			WorkspaceConfigSnapshot workspace = fullIndex.getWorkspaceConfig();
			String dump = "Workspace Config:\n + ";
			dump += Strings.join("\n + ", p -> p.getName() + " \tat " + p.getPath(), workspace.getProjects());
			return dump;
		}
	}
}
