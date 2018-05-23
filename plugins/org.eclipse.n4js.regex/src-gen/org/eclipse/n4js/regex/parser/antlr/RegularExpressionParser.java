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
package org.eclipse.n4js.regex.parser.antlr;

import com.google.inject.Inject;
import org.eclipse.n4js.regex.parser.antlr.internal.InternalRegularExpressionParser;
import org.eclipse.n4js.regex.services.RegularExpressionGrammarAccess;
import org.eclipse.xtext.parser.antlr.AbstractAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;

public class RegularExpressionParser extends AbstractAntlrParser {

	@Inject
	private RegularExpressionGrammarAccess grammarAccess;

	@Override
	protected void setInitialHiddenTokens(XtextTokenStream tokenStream) {
		tokenStream.setInitialHiddenTokens();
	}
	

	@Override
	protected InternalRegularExpressionParser createParser(XtextTokenStream stream) {
		return new InternalRegularExpressionParser(stream, getGrammarAccess());
	}

	@Override 
	protected String getDefaultRuleName() {
		return "RegularExpressionLiteral";
	}

	public RegularExpressionGrammarAccess getGrammarAccess() {
		return this.grammarAccess;
	}

	public void setGrammarAccess(RegularExpressionGrammarAccess grammarAccess) {
		this.grammarAccess = grammarAccess;
	}
}
