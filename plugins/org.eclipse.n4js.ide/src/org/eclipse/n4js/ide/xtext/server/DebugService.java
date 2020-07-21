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

import java.lang.management.LockInfo;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.MonitorInfo;
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
import org.eclipse.n4js.ide.xtext.server.build.ConcurrentIndex;
import org.eclipse.n4js.utils.Strings;
import org.eclipse.n4js.xtext.workspace.IWorkspaceConfigSnapshot;

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

	/** Sets the level of verbosity of Log4j. @see {@link LogLevel} */
	@JsonRequest
	CompletableFuture<Void> setVerboseLevel(String level);

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
		public CompletableFuture<Void> setVerboseLevel(String level) {
			return CompletableFuture.failedFuture(new UnsupportedOperationException());
		}

		@Override
		public CompletableFuture<Void> printDebugInfo() {
			return CompletableFuture.failedFuture(new UnsupportedOperationException());
		}
	}

	/** Default implementation */
	class DebugServiceDefaultImpl implements DebugService {
		private static final Logger LOG = LogManager.getLogger(DebugServiceDefaultImpl.class);

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
		public CompletableFuture<Void> setVerboseLevel(String level) {
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

		private String getDebugInfo() {
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

		// copied and modified from org.eclipse.osgi.framework.util.ThreadInfoReport
		private static String getThreadDump() {
			long currentId = Thread.currentThread().getId();
			ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
			StringBuilder dump = new StringBuilder("Thread dump:\n");
			dump.append("Current ThreadId: ").append(currentId);

			ThreadInfo[] infos = threadMXBean.dumpAllThreads(threadMXBean.isObjectMonitorUsageSupported(),
					threadMXBean.isSynchronizerUsageSupported());

			for (ThreadInfo info : infos) {
				dumpThreadIDNameState(info, dump);
				dumpLockInfo(info, dump);
				dumpStackTrace(info, dump);
			}
			return dump.toString();
		}

		// copied from org.eclipse.osgi.framework.util.ThreadInfoReport
		private static void dumpThreadIDNameState(ThreadInfo info, StringBuilder dump) {
			dump.append('\n').append('\n');
			dump.append("ThreadId: ").append(info.getThreadId());
			dump.append(" ThreadName: ").append(info.getThreadName());
			dump.append(" ThreadState: ").append(info.getThreadState());
		}

		// copied and modified from org.eclipse.osgi.framework.util.ThreadInfoReport
		private static void dumpLockInfo(ThreadInfo info, StringBuilder dump) {
			dump.append('\n');
			dump.append("  Blocked On: ");
			LockInfo blockedOn = info.getLockInfo();
			if (blockedOn == null) {
				dump.append("none");
			} else {
				dump.append(blockedOn.toString());
				dump.append(" LockOwnerId: ").append(info.getLockOwnerId());
				dump.append(" LockOwnerName: ").append(info.getLockOwnerName());
			}
			dump.append('\n');

			dump.append("  Synchronizers Locked: ");
			LockInfo[] synchronizers = info.getLockedSynchronizers();
			if (synchronizers.length == 0) {
				dump.append("none");
			} else {
				for (LockInfo sync : synchronizers) {
					dump.append('\n');
					dump.append("    ").append(sync.toString());
				}
			}
			dump.append('\n');

			dump.append("  Monitors Locked: ");
			MonitorInfo[] monitors = info.getLockedMonitors();
			if (monitors.length == 0) {
				dump.append("none");
			}
			for (MonitorInfo monitor : monitors) {
				dump.append('\n');
				dump.append("    ").append(monitor.toString());
			}
			dump.append('\n');
		}

		// copied from org.eclipse.osgi.framework.util.ThreadInfoReport
		private static void dumpStackTrace(ThreadInfo info, StringBuilder dump) {
			dump.append("  Stack Trace: ");
			for (StackTraceElement e : info.getStackTrace()) {
				dump.append('\n').append("    ").append(e);
			}
		}

		private String getExecuterServiceDump() {
			String dump = executerService.stringify();
			return dump;
		}

		private String getMemoryDump() {
			MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
			MemoryUsage memoryUsage = memoryBean.getHeapMemoryUsage();
			String dump = "Memory Usage:\n";
			dump += memoryUsage.toString();
			return dump;
		}

		private String getWorkspaceConfigDump() {
			IWorkspaceConfigSnapshot workspace = fullIndex.getWorkspaceConfig();
			String dump = "Workspace Config:\n + ";
			dump += Strings.join("\n + ", p -> p.getName(), workspace.getProjects());
			return dump;
		}
	}
}
