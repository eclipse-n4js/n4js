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
package org.eclipse.n4js.ui.contentassist;

import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.n4js.ide.editor.contentassist.ContentAssistDataCollectors;
import org.eclipse.n4js.smith.Measurement;
import org.eclipse.xtext.ui.editor.contentassist.CompletionProposalComputer;
import org.eclipse.xtext.ui.editor.contentassist.XtextContentAssistProcessor;

import com.google.inject.Inject;

/**
 * XtextContentAssistProcessor with measurements.
 */
public class N4JSContentAssistProcessor extends XtextContentAssistProcessor {

	@Inject
	private ContentAssistDataCollectors dataCollectors;

	@Override
	public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer, int offset) {
		try (Measurement m = dataCollectors.dcCreateCompletionsRoot().getMeasurement()) {
			return super.computeCompletionProposals(viewer, offset);
		}
	}

	@Override
	protected CompletionProposalComputer createCompletionProposalComputer(ITextViewer viewer, int offset) {
		return new N4JSCompletionProposalComputer(this, viewer, offset, dataCollectors.dcCreateProposals());
	}

}
