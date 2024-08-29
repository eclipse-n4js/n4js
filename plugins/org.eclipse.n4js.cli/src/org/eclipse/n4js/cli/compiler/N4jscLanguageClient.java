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
package org.eclipse.n4js.cli.compiler;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.eclipse.emf.common.util.URI;
import org.eclipse.lsp4j.Diagnostic;
import org.eclipse.lsp4j.DiagnosticSeverity;
import org.eclipse.lsp4j.MessageParams;
import org.eclipse.lsp4j.ProgressParams;
import org.eclipse.lsp4j.PublishDiagnosticsParams;
import org.eclipse.lsp4j.WorkDoneProgressBegin;
import org.eclipse.lsp4j.WorkDoneProgressCreateParams;
import org.eclipse.lsp4j.WorkDoneProgressEnd;
import org.eclipse.lsp4j.WorkDoneProgressNotification;
import org.eclipse.lsp4j.WorkDoneProgressReport;
import org.eclipse.lsp4j.services.LanguageClient;
import org.eclipse.n4js.cli.N4jscConsole;
import org.eclipse.n4js.ide.client.AbstractN4JSLanguageClient;
import org.eclipse.n4js.xtext.ide.server.build.XBuildRequest;
import org.eclipse.n4js.xtext.ide.server.build.XBuildRequest.AfterBuildRequestListener;
import org.eclipse.n4js.xtext.ide.server.build.XBuildResult;
import org.eclipse.n4js.xtext.ide.server.issues.DiagnosticComparator;

import com.google.common.collect.Multimap;
import com.google.common.collect.TreeMultimap;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Overrides the lsp {@link LanguageClient} callback when used as a CLI utility
 */
@Singleton
public class N4jscLanguageClient extends AbstractN4JSLanguageClient implements AfterBuildRequestListener {

	private Multimap<String, Diagnostic> diagnostics;
	private long genCount = 0;
	private long delCount = 0;
	private long errCount = 0;
	private long wrnCount = 0;

	/***/
	@Inject
	protected N4jscIssueSerializer issueSerializer;

	@Override
	public void publishDiagnostics(@SuppressWarnings("hiding") PublishDiagnosticsParams diagnostics) {
		List<Diagnostic> issueList = diagnostics.getDiagnostics();
		if (issueList.isEmpty()) {
			return;
		}

		synchronized (this) {
			if (this.diagnostics == null) {
				this.diagnostics = TreeMultimap.create(Comparator.naturalOrder(), new DiagnosticComparator());
			}
			this.diagnostics.putAll(diagnostics.getUri(), issueList);
			for (Diagnostic diag : issueList) {
				switch (diag.getSeverity()) {
				case Error:
					errCount++;
					break;
				case Warning:
					wrnCount++;
					break;
				default:
					break;
				}
			}
		}
	}

	@Override
	public synchronized void afterBuildRequest(XBuildRequest request, XBuildResult result) {
		// build is done, print all received diagnostics sorted by their file location
		if (diagnostics != null) {
			for (Map.Entry<String, Collection<Diagnostic>> entry : diagnostics.asMap().entrySet()) {
				N4jscConsole.println(issueSerializer.uri(entry.getKey()));
				for (Diagnostic diag : entry.getValue()) {
					N4jscConsole.println(issueSerializer.diagnostics(diag));
				}
			}
			diagnostics = null;
		}
	}

	@Override
	public void showMessage(MessageParams messageParams) {
		N4jscConsole.println(messageParams.getMessage());
	}

	@Override
	public void logMessage(MessageParams message) {
		N4jscConsole.println(message.getMessage());
	}

	@Override
	public CompletableFuture<Void> createProgress(WorkDoneProgressCreateParams params) {
		return CompletableFuture.completedFuture(null);
	}

	@Override
	public void notifyProgress(ProgressParams params) {
		WorkDoneProgressNotification notification = params.getValue().getLeft();
		if (notification instanceof WorkDoneProgressBegin) {
			// WorkDoneProgressBegin begin = (WorkDoneProgressBegin) notification;
			// N4jscConsole.println(begin.getTitle());
		}
		if (notification instanceof WorkDoneProgressReport) {
			WorkDoneProgressReport report = (WorkDoneProgressReport) notification;
			String errSymbol = N4jscIssueSerializer.getShortSeverity(DiagnosticSeverity.Error);
			String wrnSymbol = N4jscIssueSerializer.getShortSeverity(DiagnosticSeverity.Warning);
			String msg = String.format(" %2d%%  %d%s  %d%s  %s", report.getPercentage(), errCount, errSymbol, wrnCount,
					wrnSymbol, report.getMessage());
			N4jscConsole.setInfoLine(msg);
		}
		if (notification instanceof WorkDoneProgressEnd) {
			N4jscConsole.setInfoLine(null);
			// WorkDoneProgressEnd end = (WorkDoneProgressEnd) notification;
			// N4jscConsole.println(end.getMessage());
		}
	}

	@Override
	public void afterDelete(URI file) {
		delCount++;
	}

	@Override
	public void afterGenerate(URI source, URI generated) {
		genCount++;
	}

	/**
	 * @return number of warnings sent by server since last call to {@link #resetCounters()}, also counting duplicates
	 *         in case the server sends the same issue several times.
	 */
	public long getWarningsCount() {
		return wrnCount;
	}

	/**
	 * @return number of errors sent by server since last call to {@link #resetCounters()}, also counting duplicates in
	 *         case the server sends the same issue several times.
	 */
	public long getErrorsCount() {
		return errCount;
	}

	/** @return number of files that were deleted */
	public long getDeletionsCount() {
		return delCount;
	}

	/** @return number of files that were generated */
	public long getGeneratedCount() {
		return genCount;
	}

	/** Resets counters of transpiled and deleted files, and errors and warnings */
	public void resetCounters() {
		genCount = 0;
		delCount = 0;
		errCount = 0;
		wrnCount = 0;
	}

}
