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
package org.eclipse.n4js.ide.tests.client;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.lsp4j.Diagnostic;
import org.eclipse.lsp4j.MessageParams;
import org.eclipse.lsp4j.PublishDiagnosticsParams;
import org.eclipse.lsp4j.services.LanguageClient;
import org.eclipse.n4js.ide.client.AbstractN4JSLanguageClient;
import org.eclipse.n4js.ide.tests.server.AbstractIdeTest;
import org.eclipse.n4js.ide.tests.server.StringLSP4J;
import org.eclipse.n4js.ide.xtext.server.XWorkspaceManager;
import org.eclipse.n4js.projectModel.locations.FileURI;
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
	private XWorkspaceManager workspaceManager;

	private final Multimap<ModuleId, Diagnostic> issues = Multimaps.synchronizedMultimap(HashMultimap.create());
	private final Multimap<ModuleId, String> errors = Multimaps.synchronizedMultimap(HashMultimap.create());
	private final Multimap<ModuleId, String> warnings = Multimaps.synchronizedMultimap(HashMultimap.create());

	private StringLSP4J stringLSP4J;

	/**
	 * Identifies a module in the test workspace.
	 * <p>
	 * Use {@link IdeTestLanguageClient#getModuleId(FileURI)} to create instances.
	 */
	public static final class ModuleId {
		final String relativeModulePath;

		private ModuleId(String relativeModulePath) {
			this.relativeModulePath = relativeModulePath;
		}

		@Override
		public int hashCode() {
			return relativeModulePath.hashCode();
		}

		@Override
		public boolean equals(Object object) {
			return object instanceof ModuleId && relativeModulePath.equals(((ModuleId) object).relativeModulePath);
		}

		@Override
		public String toString() {
			return relativeModulePath;
		}
	}

	/** Clear all issues tracked by this client. */
	public void clear() {
		issues.clear();
		errors.clear();
		warnings.clear();
	}

	private StringLSP4J getStringLSP4J() {
		if (stringLSP4J == null) {
			stringLSP4J = new StringLSP4J(URIUtils.toFile(workspaceManager.getBaseDir()));
		}
		return stringLSP4J;
	}

	@Override
	public void showMessage(MessageParams messageParams) {
		// not yet used in tests
	}

	@Override
	public void logMessage(MessageParams message) {
		// not yet used in tests
	}

	@Override
	public void publishDiagnostics(PublishDiagnosticsParams diagnostics) {
		ModuleId id = getModuleId(diagnostics.getUri());

		issues.removeAll(id);
		errors.removeAll(id);
		warnings.removeAll(id);

		List<Diagnostic> issueList = diagnostics.getDiagnostics();
		if (issueList.isEmpty()) {
			return;
		}

		for (Diagnostic diag : issueList) {
			String issueString = getIssueString(diag);
			issues.put(id, diag);

			switch (diag.getSeverity()) {
			case Error:
				errors.put(id, issueString);
				break;
			case Warning:
				warnings.put(id, issueString);
				break;
			default:
				// ignore
				break;
			}
		}
	}

	/**
	 * @return all issues in the workspace as a multi-map from module ID (as returned by {@link #getModuleId(FileURI)})
	 *         to {@link Diagnostic}s.
	 */
	public Multimap<ModuleId, Diagnostic> getIssues() {
		return issues;
	}

	/** Same as {@link #getIssues(ModuleId)}, but accepts a file URI. */
	public Collection<Diagnostic> getIssues(FileURI uri) {
		return getIssues(getModuleId(uri));
	}

	/** @return issues in the module with the given ID. */
	public Collection<Diagnostic> getIssues(ModuleId moduleId) {
		return issues.get(moduleId);
	}

	/** Same as {@link #getErrors(ModuleId)}, but accepts a file URI. */
	public Collection<String> getErrors(FileURI uri) {
		return getErrors(getModuleId(uri));
	}

	/** @return messages of errors in the module with the given ID. */
	public Collection<String> getErrors(ModuleId moduleId) {
		return errors.get(moduleId);
	}

	/** Same as {@link #getWarnings(ModuleId)}, but accepts a file URI. */
	public Collection<String> getWarnings(FileURI uri) {
		return getWarnings(getModuleId(uri));
	}

	/** @return messages of warnings in the module with the given ID. */
	public Collection<String> getWarnings(ModuleId moduleId) {
		return warnings.get(moduleId);
	}

	/**
	 * @return string representation of the given diagnostic, computed in the same way as the strings returned by
	 *         methods {@link #getErrors(FileURI)}, {@link #getWarnings(FileURI)}, etc.
	 * @see StringLSP4J#toStringShort(Diagnostic)
	 */
	public String getIssueString(Diagnostic diagnostic) {
		return getStringLSP4J().toStringShort(diagnostic);
	}

	/** @return the ID for the module in the test workspace with the given URI. */
	public ModuleId getModuleId(FileURI uri) {
		return getModuleId(uri.toString());
	}

	private ModuleId getModuleId(String uriString) {
		URI relativeUri = workspaceManager.makeWorkspaceRelative(URI.createURI(uriString));
		return new ModuleId(relativeUri.toFileString());
	}
}
