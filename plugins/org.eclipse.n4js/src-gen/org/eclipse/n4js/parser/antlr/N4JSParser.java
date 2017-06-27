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
package org.eclipse.n4js.parser.antlr;

import com.google.inject.Inject;
import org.eclipse.n4js.parser.antlr.internal.InternalN4JSParser;
import org.eclipse.n4js.services.N4JSGrammarAccess;
import org.eclipse.xtext.parser.antlr.AbstractAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;

public class N4JSParser extends AbstractAntlrParser {

	@Inject
	private N4JSGrammarAccess grammarAccess;

	@Override
	protected void setInitialHiddenTokens(XtextTokenStream tokenStream) {
		tokenStream.setInitialHiddenTokens("RULE_WS", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_EOL");
	}
	

	@Override
	protected InternalN4JSParser createParser(XtextTokenStream stream) {
		return new InternalN4JSParser(stream, getGrammarAccess());
	}

	@Override 
	protected String getDefaultRuleName() {
		return "Script";
	}

	public N4JSGrammarAccess getGrammarAccess() {
		return this.grammarAccess;
	}

	public void setGrammarAccess(N4JSGrammarAccess grammarAccess) {
		this.grammarAccess = grammarAccess;
	}
}
