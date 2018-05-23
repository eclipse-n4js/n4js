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
package org.eclipse.n4js.ts.parser.antlr;

import com.google.inject.Inject;
import org.eclipse.n4js.ts.parser.antlr.internal.InternalTypesParser;
import org.eclipse.n4js.ts.services.TypesGrammarAccess;
import org.eclipse.xtext.parser.antlr.AbstractAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;

public class TypesParser extends AbstractAntlrParser {

	@Inject
	private TypesGrammarAccess grammarAccess;

	@Override
	protected void setInitialHiddenTokens(XtextTokenStream tokenStream) {
		tokenStream.setInitialHiddenTokens("RULE_WS", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_EOL");
	}
	

	@Override
	protected InternalTypesParser createParser(XtextTokenStream stream) {
		return new InternalTypesParser(stream, getGrammarAccess());
	}

	@Override 
	protected String getDefaultRuleName() {
		return "TypeDefs";
	}

	public TypesGrammarAccess getGrammarAccess() {
		return this.grammarAccess;
	}

	public void setGrammarAccess(TypesGrammarAccess grammarAccess) {
		this.grammarAccess = grammarAccess;
	}
}
