/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.semver.parser.antlr;

import com.google.inject.Inject;
import org.eclipse.n4js.semver.parser.antlr.internal.InternalSemanticVersioningParser;
import org.eclipse.n4js.semver.services.SemanticVersioningGrammarAccess;
import org.eclipse.xtext.parser.antlr.AbstractAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;

public class SemanticVersioningParser extends AbstractAntlrParser {

	@Inject
	private SemanticVersioningGrammarAccess grammarAccess;

	@Override
	protected void setInitialHiddenTokens(XtextTokenStream tokenStream) {
		tokenStream.setInitialHiddenTokens("RULE_WS", "RULE_EOL");
	}
	

	@Override
	protected InternalSemanticVersioningParser createParser(XtextTokenStream stream) {
		return new InternalSemanticVersioningParser(stream, getGrammarAccess());
	}

	@Override 
	protected String getDefaultRuleName() {
		return "VersionRangeSet";
	}

	public SemanticVersioningGrammarAccess getGrammarAccess() {
		return this.grammarAccess;
	}

	public void setGrammarAccess(SemanticVersioningGrammarAccess grammarAccess) {
		this.grammarAccess = grammarAccess;
	}
}
