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
import org.eclipse.n4js.semver.parser.antlr.internal.InternalSEMVERParser;
import org.eclipse.n4js.semver.services.SEMVERGrammarAccess;
import org.eclipse.xtext.parser.antlr.AbstractAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;

public class SEMVERParser extends AbstractAntlrParser {

	@Inject
	private SEMVERGrammarAccess grammarAccess;

	@Override
	protected void setInitialHiddenTokens(XtextTokenStream tokenStream) {
		tokenStream.setInitialHiddenTokens("RULE_EOL");
	}
	

	@Override
	protected InternalSEMVERParser createParser(XtextTokenStream stream) {
		return new InternalSEMVERParser(stream, getGrammarAccess());
	}

	@Override 
	protected String getDefaultRuleName() {
		return "NPMVersionRequirement";
	}

	public SEMVERGrammarAccess getGrammarAccess() {
		return this.grammarAccess;
	}

	public void setGrammarAccess(SEMVERGrammarAccess grammarAccess) {
		this.grammarAccess = grammarAccess;
	}
}
