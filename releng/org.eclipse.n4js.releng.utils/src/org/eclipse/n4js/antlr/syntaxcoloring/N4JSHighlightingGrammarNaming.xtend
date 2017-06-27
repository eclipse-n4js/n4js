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
package org.eclipse.n4js.antlr.syntaxcoloring

import com.google.inject.Inject
import org.eclipse.xtext.Grammar
import org.eclipse.xtext.xtext.generator.XtextGeneratorNaming
import org.eclipse.xtext.xtext.generator.model.TypeReference
import org.eclipse.xtext.xtext.generator.parser.antlr.AntlrGrammar
import org.eclipse.xtext.xtext.generator.parser.antlr.GrammarNaming

import static extension org.eclipse.xtext.GrammarUtil.*

/**
 * Customized grammar naming applied in {@link N4JSAntlrHighlightingGrammarGenerator}.
 */
class N4JSHighlightingGrammarNaming extends GrammarNaming {

	@Inject
	extension XtextGeneratorNaming

	protected override String getParserPackage(Grammar it) '''«eclipsePluginBasePackage».editor.syntaxcoloring'''

	protected override String getInternalLexerPackage(Grammar it) {
		runtimeBasePackage + ".lexer"
	}

	override getLexerGrammar(Grammar it) {
		new AntlrGrammar(internalLexerPackage, '''Internal«simpleName»Lexer''')
	}

	override getParserGrammar(Grammar it) {
		new AntlrGrammar(parserPackage, '''Internal«simpleName»Parser''')
	}

	override getInternalParserSuperClass(Grammar it) {
		new TypeReference("org.eclipse.n4js.ui.editor.syntaxcoloring.AbstractInternalHighlightingAntlrParser")
	}

}
