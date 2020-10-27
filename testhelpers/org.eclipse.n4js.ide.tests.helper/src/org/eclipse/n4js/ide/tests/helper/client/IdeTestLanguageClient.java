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
package org.eclipse.n4js.ide.tests.helper.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.eclipse.emf.common.util.URI;
import org.eclipse.lsp4j.ApplyWorkspaceEditParams;
import org.eclipse.lsp4j.ApplyWorkspaceEditResponse;
import org.eclipse.lsp4j.Diagnostic;
import org.eclipse.lsp4j.MessageParams;
import org.eclipse.lsp4j.PublishDiagnosticsParams;
import org.eclipse.lsp4j.services.LanguageClient;
import org.eclipse.n4js.ide.client.AbstractN4JSLanguageClient;
import org.eclipse.n4js.ide.tests.helper.server.AbstractIdeTest;
import org.eclipse.n4js.ide.tests.helper.server.StringLSP4J;
import org.eclipse.n4js.ide.xtext.server.build.BuilderFrontend;
import org.eclipse.n4js.projectModel.locations.FileURI;
import org.eclipse.n4js.ts.scoping.builtin.N4Scheme;
import org.eclipse.n4js.utils.URIUtils;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.inject.Inject;

/**
 * A {@link LanguageClient language client} used in {@link AbstractIdeTest N4JS IDE tests}.
 */
public class IdeTestLanguageClient extends AbstractN4JSLanguageClient {

	@Inject
	private BuilderFrontend lspBuilder;

	private final List<IIdeTestLanguageClientListener> listeners = Collections.synchronizedList(new LinkedList<>());

	private final List<MessageParams> logMessages = Collections.synchronizedList(new ArrayList<>());

	private final Multimap<FileURI, Diagnostic> issues = Multimaps.synchronizedMultimap(HashMultimap.create());
	private final Multimap<FileURI, String> errors = Multimaps.synchronizedMultimap(HashMultimap.create());
	private final Multimap<FileURI, String> warnings = Multimaps.synchronizedMultimap(HashMultimap.create());

	private StringLSP4J stringLSP4J;

	/** Interface for listeners that will receive certain events sent by the LSP server. */
	public interface IIdeTestLanguageClientListener {
		/**
		 * Invoked when the LSP server sends the {@link LanguageClient#applyEdit(ApplyWorkspaceEditParams)
		 * workspace/applyEdit} request to the client during an {@link AbstractIdeTest N4JS IDE test}.
		 * <p>
		 * NOTE: will be invoked from one of the server's worker threads!
		 *
		 * @return <code>true</code> iff the workspace edit was applied.
		 */
		public boolean onServerRequest_applyEdit(ApplyWorkspaceEditParams params);
	}

	/** Adds a listener. */
	public void addListener(IIdeTestLanguageClientListener listener) {
		listeners.add(listener);
	}

	/** Removes a listener. */
	public void removeListener(IIdeTestLanguageClientListener listener) {
		listeners.remove(listener);
	}

	/** Clear the log messages tracked by this client. */
	public void clearLogMessages() {
		logMessages.clear();
	}

	/**
	 * Clear all issues tracked by this client.
	 * <p>
	 * IMPORTANT: usually, tests should not invoke this method! LSP clients such as VS Code won't clear issues when
	 * triggering a rebuild but instead rely on the server to delete obsolete issues by sending appropriate
	 * <code>publishDiagnostics</code> events to the client. So, to simulate this as closely as possible, the IDE tests
	 * must not clear issues by themselves. A situation when it makes sense to call this method might be the simulation
	 * of a client restart while leaving the LSP server running, for example.
	 */
	public void clearIssues() {
		issues.clear();
		errors.clear();
		warnings.clear();
	}

	private StringLSP4J getStringLSP4J() {
		if (stringLSP4J == null) {
			stringLSP4J = new StringLSP4J(URIUtils.toFile(lspBuilder.getBaseDir()));
		}
		return stringLSP4J;
	}

	@Override
	public void showMessage(MessageParams messageParams) {
		// not yet used in tests
	}

	@Override
	public void logMessage(MessageParams message) {
		logMessages.add(message);
	}

	@Override
	public void publishDiagnostics(PublishDiagnosticsParams diagnostics) {
		URI uriRaw = URI.createURI(diagnostics.getUri());
		if (N4Scheme.isN4Scheme(uriRaw)) {
			return;
		}
		if (!uriRaw.isFile()) {
			throw new IllegalArgumentException("not a file URI: " + uriRaw);
		}

		FileURI uri = new FileURI(uriRaw);

		issues.removeAll(uri);
		errors.removeAll(uri);
		warnings.removeAll(uri);

		List<Diagnostic> issueList = diagnostics.getDiagnostics();
		if (issueList.isEmpty()) {
			return;
		}

		for (Diagnostic diag : issueList) {
			String issueString = getIssueString(diag);
			issues.put(uri, diag);

			switch (diag.getSeverity()) {
			case Error:
				errors.put(uri, issueString);
				break;
			case Warning:
				warnings.put(uri, issueString);
				break;
			default:
				// ignore
				break;
			}
		}
	}

	@Override
	public CompletableFuture<ApplyWorkspaceEditResponse> applyEdit(ApplyWorkspaceEditParams params) {
		boolean applied = false;
		synchronized (listeners) {
			for (IIdeTestLanguageClientListener l : listeners) {
				applied |= l.onServerRequest_applyEdit(params);
			}
		}
		return CompletableFuture.completedFuture(new ApplyWorkspaceEditResponse(applied));
	}

	/** @return the log messages received up to this point in time. */
	public List<MessageParams> getLogMessages() {
		return new ArrayList<>(logMessages);
	}

	/** @return all issues in the workspace as a multi-map from file URI to {@link Diagnostic}s. */
	public Multimap<FileURI, Diagnostic> getIssues() {
		return issues;
	}

	/** @return issues in the module with the given file URI. */
	public Collection<Diagnostic> getIssues(FileURI uri) {
		return issues.get(uri);
	}

	/** @return messages of errors in the module with the given file URI. */
	public Collection<String> getErrors(FileURI uri) {
		return errors.get(uri);
	}

	/** @return messages of warnings in the module with the given file URI. */
	public Collection<String> getWarnings(FileURI uri) {
		return warnings.get(uri);
	}

	/**
	 * @return string representation of the given diagnostic, computed in the same way as the strings returned by
	 *         methods {@link #getErrors(FileURI)}, {@link #getWarnings(FileURI)}, etc.
	 * @see StringLSP4J#toStringShort(Diagnostic)
	 */
	public String getIssueString(Diagnostic diagnostic) {
		return getStringLSP4J().toStringShort(diagnostic);
	}
}
