/**
 * Copyright (c) 2022 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.server.issues;

import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.lsp4j.Diagnostic;
import org.eclipse.lsp4j.PublishDiagnosticsParams;
import org.eclipse.n4js.utils.URIUtils;
import org.eclipse.n4js.xtext.ide.server.issues.PublishingIssueAcceptor;
import org.eclipse.xtext.validation.Issue;

import com.google.inject.Singleton;

/**
 *
 */
@Singleton
public class N4JSPublishingIssueAcceptor extends PublishingIssueAcceptor {

	@Override
	public void accept(URI uri, List<? extends Issue> issues) {
		if (client != null) {
			PublishDiagnosticsParams publishDiagnosticsParams = new PublishDiagnosticsParams();
			publishDiagnosticsParams.setUri(uriExtensions.toUriString(URIUtils.getBaseOfVirtualResourceURI(uri)));
			List<Diagnostic> diags = toDiagnostics(issues);
			publishDiagnosticsParams.setDiagnostics(diags);
			client.publishDiagnostics(publishDiagnosticsParams);
		}
	}
}
