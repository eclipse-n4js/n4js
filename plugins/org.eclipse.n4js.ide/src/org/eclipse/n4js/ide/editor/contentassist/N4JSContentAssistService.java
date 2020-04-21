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
package org.eclipse.n4js.ide.editor.contentassist;

import org.eclipse.lsp4j.CompletionList;
import org.eclipse.lsp4j.TextDocumentPositionParams;
import org.eclipse.n4js.ide.xtext.server.contentassist.XContentAssistService;
import org.eclipse.n4js.smith.Measurement;
import org.eclipse.xtext.ide.editor.contentassist.IIdeContentProposalAcceptor;
import org.eclipse.xtext.ide.server.Document;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.util.TextRegion;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 *
 */
@Singleton
public class N4JSContentAssistService extends XContentAssistService {

	@Inject
	private ContentAssistDataCollectors dataCollectors;

	@Override
	public CompletionList createCompletionList(Document document, XtextResource resource,
			TextDocumentPositionParams params, CancelIndicator cancelIndicator) {
		try (Measurement m = dataCollectors.dcCreateCompletionsRoot().getMeasurement()) {
			return super.createCompletionList(document, resource, params, cancelIndicator);
		}
	}

	@Override
	protected void createProposals(String document, TextRegion selection, int caretOffset, XtextResource resource,
			IIdeContentProposalAcceptor acceptor) {
		try (Measurement m = dataCollectors.dcCreateProposals().getMeasurement()) {
			super.createProposals(document, selection, caretOffset, resource, acceptor);
		}
	}

}
