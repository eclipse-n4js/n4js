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
import org.eclipse.n4js.smith.DataCollector;
import org.eclipse.n4js.smith.Measurement;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.contentassist.CompletionProposalComputer;

/**
 * CompletionProposalComputer with measurements.
 */
public class N4JSCompletionProposalComputer extends CompletionProposalComputer {

	private final DataCollector dataCollector;

	/**
	 * Constructor
	 */
	public N4JSCompletionProposalComputer(State state, ITextViewer viewer, int offset, DataCollector dataCollector) {
		super(state, viewer, offset);
		this.dataCollector = dataCollector;
	}

	@Override
	public ICompletionProposal[] exec(XtextResource resource) throws Exception {
		try (Measurement m = dataCollector.getMeasurement()) {
			return super.exec(resource);
		}
	}

}
