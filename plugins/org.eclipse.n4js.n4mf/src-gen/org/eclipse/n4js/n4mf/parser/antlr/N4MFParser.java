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
package org.eclipse.n4js.n4mf.parser.antlr;

import com.google.inject.Inject;
import org.eclipse.n4js.n4mf.parser.antlr.internal.InternalN4MFParser;
import org.eclipse.n4js.n4mf.services.N4MFGrammarAccess;
import org.eclipse.xtext.parser.antlr.AbstractAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;

public class N4MFParser extends AbstractAntlrParser {

	@Inject
	private N4MFGrammarAccess grammarAccess;

	@Override
	protected void setInitialHiddenTokens(XtextTokenStream tokenStream) {
		tokenStream.setInitialHiddenTokens("RULE_WS", "RULE_ML_COMMENT", "RULE_SL_COMMENT");
	}
	

	@Override
	protected InternalN4MFParser createParser(XtextTokenStream stream) {
		return new InternalN4MFParser(stream, getGrammarAccess());
	}

	@Override 
	protected String getDefaultRuleName() {
		return "ProjectDescription";
	}

	public N4MFGrammarAccess getGrammarAccess() {
		return this.grammarAccess;
	}

	public void setGrammarAccess(N4MFGrammarAccess grammarAccess) {
		this.grammarAccess = grammarAccess;
	}
}
