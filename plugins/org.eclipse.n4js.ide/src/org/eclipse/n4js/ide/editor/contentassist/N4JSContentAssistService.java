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

import org.eclipse.n4js.smith.Measurement;
import org.eclipse.n4js.xtext.ide.server.contentassist.XContentAssistService;
import org.eclipse.xtext.ide.editor.contentassist.IIdeContentProposalAcceptor;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.util.TextRegion;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * {@link XContentAssistService} with data collection.
 */
@Singleton
public class N4JSContentAssistService extends XContentAssistService {

	@Inject
	private ContentAssistDataCollectors dataCollectors;

	@Override
	protected void createProposals(String document, TextRegion selection, int caretOffset, XtextResource resource,
			IIdeContentProposalAcceptor acceptor) {
		try (Measurement m = dataCollectors.dcCreateProposals().getMeasurement()) {
			super.createProposals(document, selection, caretOffset, resource, acceptor);
		}
	}

}
