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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.Grammar;
import org.eclipse.xtext.util.RuntimeIOException;
// /*extension see comment in {@link doGenerate()}*/ import org.eclipse.xtext.xtext.generator.grammarAccess.GrammarAccessExtensions;
import org.eclipse.xtext.xtext.generator.model.IXtextGeneratorFileSystemAccess;
import org.eclipse.xtext.xtext.generator.model.TypeReference;
import org.eclipse.xtext.xtext.generator.parser.antlr.AbstractAntlrGeneratorFragment2;
import org.eclipse.xtext.xtext.generator.parser.antlr.AntlrGrammar;
import org.eclipse.xtext.xtext.generator.parser.antlr.AntlrOptions;
import org.eclipse.xtext.xtext.generator.parser.antlr.GrammarNaming;
import org.eclipse.xtext.xtext.generator.parser.antlr.XtextAntlrGeneratorFragment2;

import com.google.inject.Inject;
import com.google.inject.Injector;

/**
 * Custom variant of {@link XtextAntlrGeneratorFragment2} generating the highlighting grammar & parser without a
 * dedicated lexer, reuses the production lexer.
 */
public class N4JSHighlightingParserGeneratorFragment2 extends XtextAntlrGeneratorFragment2 {

	// /*extension see comment in {@link doGenerate()}*/ @Inject extension GrammarAccessExtensions grammarUtil
	@Inject
	GrammarNaming productionNaming;
	@Inject
	N4JSHighlightingGrammarNaming highlightingNaming;

	@Inject
	N4JSAntlrHighlightingGrammarGenerator highlightingGenerator;

	@Override
	protected void doGenerate() {
		// The following 2 lines are deactivated as they would attempt to install
		// adapters on the grammar AST. This already done by the production parser
		// generator fragment, but is requirement once that fragment is deactivated,
		// e.g. for testing purposes.
		// new KeywordHelper(grammar, options.ignoreCase, grammarUtil)
		// new CombinedGrammarMarker(false).attachToEmfObject(grammar)

		generateHighlightingGrammar();
	}

	/**
	 * TODO IDE-2406:Can be removed once https://github.com/eclipse/xtext-core/issues/158 is resolved and the solution
	 * is leveraged in N4JSAntlrHighlightingGrammarGenerator.
	 */
	public static class GuardedXtextGeneratorFileSystemAccess implements IXtextGeneratorFileSystemAccess {
		private final IXtextGeneratorFileSystemAccess delegate;

		/***/
		public GuardedXtextGeneratorFileSystemAccess(final IXtextGeneratorFileSystemAccess delegate) {
			super();
			this.delegate = delegate;
		}

		@Override
		public void deleteFile(final String fileName) {
			this.delegate.deleteFile(fileName);
		}

		@Override
		public void deleteFile(final String fileName, final String outputConfigurationName) {
			this.delegate.deleteFile(fileName, outputConfigurationName);
		}

		@Override
		public void generateFile(final String fileName, final CharSequence contents) {
			this.delegate.generateFile(fileName, contents);
		}

		@Override
		public void generateFile(final String fileName, final InputStream content) throws RuntimeIOException {
			this.delegate.generateFile(fileName, content);
		}

		@Override
		public void generateFile(final String fileName, final String outputConfigurationName,
				final CharSequence contents) {
			this.delegate.generateFile(fileName, outputConfigurationName, contents);
		}

		@Override
		public void generateFile(final String fileName, final String outputCfgName, final InputStream content)
				throws RuntimeIOException {
			this.delegate.generateFile(fileName, outputCfgName, content);
		}

		@Override
		public String getPath() {
			return this.delegate.getPath();
		}

		@Override
		public URI getURI(final String path) {
			return this.delegate.getURI(path);
		}

		@Override
		public URI getURI(final String path, final String outputConfiguration) {
			return this.delegate.getURI(path, outputConfiguration);
		}

		@Override
		public void initialize(final Injector injector) {
			this.delegate.initialize(injector);
		}

		@Override
		public boolean isFile(final String path) throws RuntimeIOException {
			return this.delegate.isFile(path);
		}

		@Override
		public boolean isFile(final String path, final String outputConfigurationName) throws RuntimeIOException {
			return this.delegate.isFile(path, outputConfigurationName);
		}

		@Override
		public boolean isOverwrite() {
			return this.delegate.isOverwrite();
		}

		@Override
		public InputStream readBinaryFile(final String fileName) throws RuntimeIOException {
			return this.delegate.readBinaryFile(fileName);
		}

		@Override
		public InputStream readBinaryFile(final String fileName, final String outputCfgName) throws RuntimeIOException {
			return this.delegate.readBinaryFile(fileName, outputCfgName);
		}

		@Override
		public CharSequence readTextFile(final String fileName) throws RuntimeIOException {
			return this.delegate.readTextFile(fileName);
		}

		@Override
		public CharSequence readTextFile(final String fileName, final String outputCfgName) throws RuntimeIOException {
			return this.delegate.readTextFile(fileName, outputCfgName);
		}
	}

	/***/
	protected void generateHighlightingGrammar() {
		IXtextGeneratorFileSystemAccess fsa = getProjectConfig().getEclipsePlugin().getSrcGen();

		Grammar grammar = getGrammar();
		AntlrOptions options = getOptions();
		highlightingGenerator.generate(grammar, options,
				new N4JSHighlightingParserGeneratorFragment2.GuardedXtextGeneratorFileSystemAccess(fsa) {

					/**
					 * Via this customization the generation of an additional lexer grammar is suppressed, since the
					 * production lexer is re-used. TODO IDE-2406: Can be removed once
					 * https://github.com/eclipse/xtext-core/issues/158 is resolved and the solution is leveraged in
					 * N4JSAntlrHighlightingGrammarGenerator.
					 */
					@Override
					public void generateFile(String fileName, CharSequence contents) {
						if (contents != null) {
							super.generateFile(fileName, contents);
						}
					}
				});

		runAntlr(highlightingNaming.getParserGrammar(grammar), null, fsa);

		simplifyUnorderedGroupPredicatesIfRequired(grammar, fsa, highlightingNaming.getInternalParserClass(grammar));
		splitParserAndLexerIfEnabled(fsa, highlightingNaming.getInternalParserClass(grammar),
				/* grammar.lexerClass // we didn't generate a lexer, so... */ null);
		normalizeTokens(fsa, highlightingNaming.getParserGrammar(grammar).getTokensFileName());
		suppressWarnings(fsa, highlightingNaming.getInternalParserClass(grammar)
		/* , grammar.lexerClass // we didn't generate a lexer! */);
		normalizeLineDelimiters(fsa, highlightingNaming.getInternalParserClass(grammar)
		/* , grammar.lexerClass // we didn't generate a lexer! */);
	}

	@Override
	protected void runAntlr(AntlrGrammar parserGrammar, AntlrGrammar lexerGrammar,
			IXtextGeneratorFileSystemAccess fsa) {
		IXtextGeneratorFileSystemAccess src_gen = getProjectConfig().getRuntime().getSrcGen();
		IXtextGeneratorFileSystemAccess src_gen_ui = getProjectConfig().getEclipsePlugin().getSrcGen();

		AntlrGrammar theLexerGrammar = productionNaming.getLexerGrammar(getGrammar());

		String encoding = getCodeConfig().getEncoding();
		String lexerGrammarFile = src_gen.getPath() + "/" + theLexerGrammar.getGrammarFileName();
		String lexerOutputDir = lexerGrammarFile.substring(0, lexerGrammarFile.lastIndexOf('/'));

		String parserGrammarFile = src_gen_ui.getPath() + "/" + parserGrammar.getGrammarFileName();
		List<String> parserAntlrParams = new ArrayList<>(Arrays.asList(getAntlrParams()));
		parserAntlrParams.add("-fo");
		parserAntlrParams.add(parserGrammarFile.substring(0, parserGrammarFile.lastIndexOf('/')));
		parserAntlrParams.add("-lib");
		parserAntlrParams.add(lexerOutputDir);

		getAntlrTool().runWithEncodingAndParams(parserGrammarFile, encoding, parserAntlrParams.toArray(new String[0]));
	}

	@Override
	protected void splitLexerClassFile(IXtextGeneratorFileSystemAccess fsa, TypeReference lexer) {
		// we don't have a corresponding lexer, so do nothing here
	}

	/**
	 * Needed to copy this method from the super class {@link AbstractAntlrGeneratorFragment2
	 * AbstractAntlrGeneratorFragment2} in order to deactivate the lexer specific parts.
	 */
	protected @Override void improveCodeQuality(IXtextGeneratorFileSystemAccess fsa, TypeReference lexer,
			TypeReference parser) {
		// var lexerContent = fsa.readTextFile(lexer.javaPath).toString
		// lexerContent = codeQualityHelper.stripUnnecessaryComments(lexerContent, options)
		// fsa.generateFile(lexer.javaPath, lexerContent)

		String parserContent = fsa.readTextFile(parser.getJavaPath()).toString();
		parserContent = getCodeQualityHelper().stripUnnecessaryComments(parserContent, getOptions());
		parserContent = getCodeQualityHelper().removeDuplicateBitsets(parserContent, getOptions());
		parserContent = getCodeQualityHelper().removeDuplicateDFAs(parserContent, getOptions());
		fsa.generateFile(parser.getJavaPath(), parserContent);
	}
}
