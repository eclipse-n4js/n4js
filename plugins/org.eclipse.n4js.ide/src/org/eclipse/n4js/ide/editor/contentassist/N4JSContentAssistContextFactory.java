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
import org.eclipse.xtext.ide.editor.contentassist.ContentAssistContext;
import org.eclipse.xtext.ide.editor.contentassist.antlr.ContentAssistContextFactory;
import org.eclipse.xtext.ide.editor.contentassist.antlr.PartialContentAssistContextFactory;
import org.eclipse.xtext.parser.IParseResult;

import com.google.inject.Inject;

/**
 * {@link ContentAssistContextFactory} with data collection and support for ASI.
 *
 * Overrides the default {@link org.eclipse.xtext.ide.editor.contentassist.antlr.ContentAssistContextFactory} to use the
 * API that is introduced by the {@link CustomN4JSParser}.
 *
 * All overridden methods basically override the inherited behavior to use the production parser's node model rather
 * than a bogus own lexer.
 *
 * @see CustomN4JSParser#getFollowElements(IParseResult, int, int, boolean)
 */
public class N4JSContentAssistContextFactory extends PartialContentAssistContextFactory {

	@Inject
	private ContentAssistDataCollectors dataCollectors;

	@Override
	protected ContentAssistContext[] doCreateContexts(int offset) {
		try (Measurement m = dataCollectors.dcCreateContexts().getMeasurement()) {
			return super.doCreateContexts(offset);
		}
	}

}
