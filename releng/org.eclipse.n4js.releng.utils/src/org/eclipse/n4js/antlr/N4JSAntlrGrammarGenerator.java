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

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.eclipse.n4js.antlr.n4js.AutomaticSemicolonInjector;
import org.eclipse.n4js.antlr.n4js.NoLineTerminatorHandlingInjector;
import org.eclipse.n4js.antlr.n4js.RegExDisambiguationInjector;
import org.eclipse.n4js.antlr.n4js.TemplateLiteralDisambiguationInjector;
import org.eclipse.xtext.Grammar;
import org.eclipse.xtext.xtext.generator.model.IXtextGeneratorFileSystemAccess;
import org.eclipse.xtext.xtext.generator.parser.antlr.AntlrGrammarGenerator;
import org.eclipse.xtext.xtext.generator.parser.antlr.AntlrOptions;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.inject.Inject;

/**
 * Customized production ANTLR grammar generator.
 */
public class N4JSAntlrGrammarGenerator extends AntlrGrammarGenerator {

	@Inject
	AutomaticSemicolonInjector semicolonInjector;

	@Inject
	RegExDisambiguationInjector regExDisambiguationInjector;

	@Inject
	TemplateLiteralDisambiguationInjector templateLiteralDisambiguationInjector;

	@Inject
	NoLineTerminatorHandlingInjector noLineTerminatorHandlingInjector;

	List<CodeIntoGrammarInjector> steps;

	/***/
	@Inject
	public void initialize() {
		steps = List.of(
				semicolonInjector,
				regExDisambiguationInjector,
				templateLiteralDisambiguationInjector,
				noLineTerminatorHandlingInjector);
	}

	/**
	 * Replace specified extensions with custom implementation for unicode keyword lexer rules
	 */
	@Override
	protected String toAntlrKeywordRule(String keyword, AntlrOptions options) {
		return UnicodeKeywordHelper.toUnicodeKeyword(keyword);
	}

	@Override
	public void generate(Grammar it, AntlrOptions options, IXtextGeneratorFileSystemAccess fsa) {
		super.generate(it, options, fsa);
		injectCode(it, fsa);
	}

	/***/
	public void injectCode(Grammar it, IXtextGeneratorFileSystemAccess fsa) {
		String outletPath = fsa.getPath() + "/";
		String lexerGrammarFileName = outletPath + getGrammarNaming().getLexerGrammar(it).getGrammarFileName();
		String parserGrammarFileName = outletPath + getGrammarNaming().getParserGrammar(it).getGrammarFileName();
		injectCode(lexerGrammarFileName, parserGrammarFileName);
	}

	/***/
	public void injectCode(String lexerGrammarFileName, String parserGrammarFileName) {
		if (lexerGrammarFileName != null) {
			try {
				String lexerGrammarContent = Files.asCharSource(new File(lexerGrammarFileName), Charsets.UTF_8).read();
				lexerGrammarContent = processLexerGrammar(lexerGrammarContent);
				Files.asCharSink(new File(lexerGrammarFileName), Charsets.UTF_8).write(lexerGrammarContent);
			} catch (IOException e) {
				throw new RuntimeException();
			}
		}

		if (parserGrammarFileName != null) {
			try {
				String parserGrammarContent = Files.asCharSource(new File(parserGrammarFileName), Charsets.UTF_8)
						.read();
				parserGrammarContent = processParserGrammar(parserGrammarContent);
				Files.asCharSink(new File(parserGrammarFileName), Charsets.UTF_8).write(parserGrammarContent);
			} catch (IOException e) {
				throw new RuntimeException();
			}
		}
	}

	private String processParserGrammar(String grammarContent) {
		String result = grammarContent;
		for (CodeIntoGrammarInjector step : steps) {
			result = step.processParserGrammar(result);
		}
		return result;
	}

	private String processLexerGrammar(String grammarContent) {
		String result = grammarContent;
		for (CodeIntoGrammarInjector step : steps) {
			result = step.processLexerGrammar(result);
		}
		return result;
	}
}
