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
import java.util.concurrent.CompletableFuture;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.lf5.LogLevel;
import org.eclipse.lsp4j.MessageParams;
import org.eclipse.lsp4j.MessageType;
import org.eclipse.lsp4j.jsonrpc.services.JsonRequest;
import org.eclipse.lsp4j.jsonrpc.services.JsonSegment;
import org.eclipse.lsp4j.services.LanguageClient;
import org.eclipse.n4js.ide.xtext.server.DebugService.DebugServiceNullImpl;
import org.eclipse.n4js.ide.xtext.server.index.ConcurrentIndex;
import org.eclipse.n4js.utils.Strings;
import org.eclipse.n4js.xtext.workspace.WorkspaceConfigSnapshot;

import com.google.inject.ImplementedBy;
import com.google.inject.Inject;

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

	/** Prints debug information on the output channel */
	@JsonRequest
	CompletableFuture<Void> printDebugInfo();

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
	}

	/** Default implementation */
	class DebugServiceDefaultImpl implements DebugService {
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
			MessageParams message = new MessageParams();
			message.setMessage(msgText);
			message.setType(MessageType.Log);
			client.logMessage(message);
			return CompletableFuture.completedFuture(null);
		}

		protected String getDebugInfo() {
			String info = "==   DEBUG INFO   ==\n";
			info += getThreadDump();
			info += "\n--------------\n";
			info += getExecuterServiceDump();
			info += "\n--------------\n";
			info += getMemoryDump();
			info += "\n--------------\n";
			info += getWorkspaceConfigDump();
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
