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

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.eclipse.emf.common.util.URI;
import org.eclipse.lsp4j.Diagnostic;
import org.eclipse.lsp4j.MessageActionItem;
import org.eclipse.lsp4j.MessageParams;
import org.eclipse.lsp4j.PublishDiagnosticsParams;
import org.eclipse.lsp4j.ShowMessageRequestParams;
import org.eclipse.lsp4j.services.LanguageClient;
import org.eclipse.n4js.cli.N4jscConsole;
import org.eclipse.n4js.ide.xtext.server.build.XBuildRequest.AfterDeleteListener;
import org.eclipse.n4js.ide.xtext.server.build.XBuildRequest.AfterGenerateListener;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Overrides the lsp {@link LanguageClient} callback when used as a CLI utility
 */
@Singleton
public class N4jscLanguageClient implements LanguageClient, AfterGenerateListener, AfterDeleteListener {
	private long trnspCount = 0;
	private long delCount = 0;
	private long errCount = 0;
	private long wrnCount = 0;

	/***/
	@Inject
	protected IssueSerializer issueSerializer;

	@Override
	public void telemetryEvent(Object object) {
		// TODO Auto-generated method stub

	}

	@Override
	public void publishDiagnostics(PublishDiagnosticsParams diagnostics) {
		List<Diagnostic> issueList = diagnostics.getDiagnostics();
		if (issueList.isEmpty()) {
			return;
		}

		synchronized (this) {
			N4jscConsole.println(issueSerializer.uri(diagnostics.getUri()));
			for (Diagnostic diag : issueList) {
				N4jscConsole.println(issueSerializer.diagnostics(diag));
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
	public void showMessage(MessageParams messageParams) {
		N4jscConsole.println(messageParams.getMessage());
	}

	@Override
	public CompletableFuture<MessageActionItem> showMessageRequest(ShowMessageRequestParams requestParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void logMessage(MessageParams message) {
		N4jscConsole.println(message.getMessage());
	}

	@Override
	public void afterDelete(URI file) {
		delCount++;
	}

	@Override
	public void afterGenerate(URI source, URI generated) {
		trnspCount++;
	}

	/**
	 * IMPORTANT: this does not return the number of warnings in the workspace but the number of warnings reports via
	 * event "publishDiagnostics"! See {@link #getErrorsCount()} for details.
	 *
	 * @return number of warnings
	 */
	public long getWarningsCount() {
		return wrnCount;
	}

	/**
	 * IMPORTANT: this does not return the number of errors in the workspace! Instead, it returns the number of errors
	 * reported by the server via event "publishDiagnostics" since {@link #resetCounters()} was invoked. If, for
	 * example, the server sends event "publishDiagnostics" twice for the same module containing 3 errors (which is
	 * legal), then this method will return 6 and not 3.
	 *
	 * @return number of errors
	 */
	public long getErrorsCount() {
		return errCount;
	}

	/** @return number of files that were deleted */
	public long getDeletionsCount() {
		return delCount;
	}

	/** @return number of files that were generated/transpiled */
	public long getTranspilationsCount() {
		return trnspCount;
	}

	/** Resets counters of transpiled and deleted files, and errors and warnings */
	public void resetCounters() {
		trnspCount = 0;
		delCount = 0;
		errCount = 0;
		wrnCount = 0;
	}

}
