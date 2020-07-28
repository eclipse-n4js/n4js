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

import java.util.Comparator;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.lsp4j.Diagnostic;
import org.eclipse.lsp4j.MessageParams;
import org.eclipse.lsp4j.PublishDiagnosticsParams;
import org.eclipse.lsp4j.services.LanguageClient;
import org.eclipse.n4js.cli.N4jscConsole;
import org.eclipse.n4js.ide.client.AbstractN4JSLanguageClient;
import org.eclipse.n4js.ide.xtext.server.build.XBuildRequest;
import org.eclipse.n4js.ide.xtext.server.build.XBuildRequest.AfterBuildListener;
import org.eclipse.n4js.ide.xtext.server.build.XBuildResult;
import org.eclipse.n4js.ide.xtext.server.issues.DiagnosticComparator;

import com.google.common.collect.Multimap;
import com.google.common.collect.TreeMultimap;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Overrides the lsp {@link LanguageClient} callback when used as a CLI utility
 */
@Singleton
public class N4jscLanguageClient extends AbstractN4JSLanguageClient implements AfterBuildListener {

	private Multimap<String, Diagnostic> diagnostics;
	private long trnspCount = 0;
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
	public synchronized void afterBuild(XBuildRequest request, XBuildResult result) {
		// build is done, print all received diagnostics sorted by their file location
		if (diagnostics != null) {
			diagnostics.asMap().forEach((uri, list) -> {
				N4jscConsole.println(issueSerializer.uri(uri));
				list.forEach(diag -> N4jscConsole.println(issueSerializer.diagnostics(diag)));
			});
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
	public void afterDelete(URI file) {
		delCount++;
	}

	@Override
	public void afterGenerate(URI source, URI generated) {
		trnspCount++;
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
