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
package org.eclipse.n4js.cli.helper;

import java.util.List;

import org.eclipse.lsp4j.Diagnostic;
import org.eclipse.lsp4j.PublishDiagnosticsParams;
import org.eclipse.n4js.cli.compiler.N4jscCallback;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.inject.Singleton;

/**
 *
 */
@Singleton
public class N4jscTestCallback extends N4jscCallback {
	Multimap<String, String> errors = HashMultimap.create();
	Multimap<String, String> warnings = HashMultimap.create();

	@Override
	public void publishDiagnostics(PublishDiagnosticsParams diagnostics) {
		super.publishDiagnostics(diagnostics);

		List<Diagnostic> issueList = diagnostics.getDiagnostics();
		if (issueList.isEmpty()) {
			return;
		}
		sortDiagnostics(issueList);

		String uriString = issueSerializer.uri(diagnostics.getUri());
		for (Diagnostic diag : issueList) {
			String issueString = issueSerializer.diagnostics(diag);

			switch (diag.getSeverity()) {
			case Error:
				errors.put(uriString, issueString);
				break;
			case Warning:
				warnings.put(uriString, issueString);
				break;
			default:
				// ignore
				break;
			}
		}
	}

}
