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
package org.eclipse.n4js.antlr.syntaxcoloring;

import static org.eclipse.xtext.GrammarUtil.getSimpleName;

import org.eclipse.xtext.Grammar;
import org.eclipse.xtext.xtext.generator.XtextGeneratorNaming;
import org.eclipse.xtext.xtext.generator.model.TypeReference;
import org.eclipse.xtext.xtext.generator.parser.antlr.AntlrGrammar;
import org.eclipse.xtext.xtext.generator.parser.antlr.GrammarNaming;

import com.google.inject.Inject;

/**
 * Customized grammar naming applied in {@link N4JSAntlrHighlightingGrammarGenerator}.
 */
public class N4JSHighlightingGrammarNaming extends GrammarNaming {

	@Inject
	XtextGeneratorNaming generatorNaming;

	protected @Override String getParserPackage(Grammar it) {
		return generatorNaming.getEclipsePluginBasePackage(it) + ".editor.syntaxcoloring";
	}

	protected @Override String getInternalLexerPackage(Grammar it) {
		return generatorNaming.getRuntimeBasePackage(it) + ".lexer";
	}

	@Override
	public AntlrGrammar getLexerGrammar(Grammar it) {
		return new AntlrGrammar(getInternalLexerPackage(it), "Internal" + getSimpleName(it) + "Lexer");
	}

	@Override
	public AntlrGrammar getParserGrammar(Grammar it) {
		return new AntlrGrammar(getParserPackage(it), "Internal" + getSimpleName(it) + "Parser");
	}

	@Override
	public TypeReference getInternalParserSuperClass(Grammar it) {
		return new TypeReference("org.eclipse.n4js.ui.editor.syntaxcoloring.AbstractInternalHighlightingAntlrParser");
	}

}
