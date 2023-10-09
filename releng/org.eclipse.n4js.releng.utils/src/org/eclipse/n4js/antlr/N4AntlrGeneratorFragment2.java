/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.antlr;

import org.eclipse.xtext.xtext.generator.model.IXtextGeneratorFileSystemAccess;
import org.eclipse.xtext.xtext.generator.parser.antlr.AntlrGrammar;
import org.eclipse.xtext.xtext.generator.parser.antlr.KeywordHelper;
import org.eclipse.xtext.xtext.generator.parser.antlr.XtextAntlrGeneratorFragment2;

/**
 * Customization of the {@link XtextAntlrGeneratorFragment2} tweaking the tokens file handling. The asymmetry in the
 * tokens file handling for the runtime/production and contentAssist grammars follows that of the former generator's
 * fragments {@code org.eclipse.xtext.generator.parser.antlr.ex.rt.AntlrGeneratorFragment} and
 * {@code org.eclipse.xtext.generator.parser.antlr.ex.ca.ContentAssistParserGeneratorFragment}.
 */
public class N4AntlrGeneratorFragment2 extends XtextAntlrGeneratorFragment2 {

	@Override
	protected void cleanupParserTokensFile(AntlrGrammar lexerGrammar, AntlrGrammar parserGrammar,
			KeywordHelper helper, IXtextGeneratorFileSystemAccess fsa) {

		if (fsa == getProjectConfig().getRuntime().getSrcGen()) {
			super.cleanupParserTokensFile(lexerGrammar, parserGrammar, helper, fsa);

		} else if (fsa == getProjectConfig().getGenericIde().getSrcGen()) {
			super.normalizeTokens(fsa, parserGrammar.getTokensFileName());
		}
	}
}
