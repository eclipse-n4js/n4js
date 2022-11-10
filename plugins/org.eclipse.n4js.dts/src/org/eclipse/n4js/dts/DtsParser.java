/**
 * Copyright (c) 2022 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.dts;

import static org.antlr.v4.runtime.CharStreams.fromReader;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.List;

import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.ConsoleErrorListener;
import org.antlr.v4.runtime.DefaultErrorStrategy;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.atn.PredictionMode;
import org.antlr.v4.runtime.dfa.DFA;
import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.dts.TypeScriptParser.ProgramContext;
import org.eclipse.n4js.dts.TypeScriptParser.StatementContext;
import org.eclipse.n4js.dts.TypeScriptParser.StatementListContext;
import org.eclipse.n4js.dts.astbuilders.DtsScriptBuilder;
import org.eclipse.n4js.n4JS.ExportDeclaration;
import org.eclipse.n4js.n4JS.ExportableElement;
import org.eclipse.n4js.n4JS.N4JSFactory;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.ScriptElement;
import org.eclipse.n4js.utils.URIUtils;
import org.eclipse.n4js.xtext.ide.server.build.ILoadResultInfoAdapter;
import org.eclipse.xtext.linking.lazy.LazyLinkingResource;
import org.eclipse.xtext.nodemodel.INode;

/**
 * Entry point to parse d.ts files
 */
public class DtsParser {

	static class ParseStats implements ANTLRErrorListener {
		final List<SyntaxErrorNode> errors = new ArrayList<>();
		long time = 0;
		PredictionMode strategy;
		ProgramContext tree;
		int ambiguityCount = 0;
		int attemptingFullContextCount = 0;
		int contextSensitivityCount = 0;
		int syntaxErrorCount = 0;

		@Override
		public void reportAmbiguity(Parser arg0, DFA arg1, int arg2, int arg3, boolean arg4, BitSet arg5,
				ATNConfigSet arg6) {

			ambiguityCount++;
		}

		@Override
		public void reportAttemptingFullContext(Parser arg0, DFA arg1, int arg2, int arg3, BitSet arg4,
				ATNConfigSet arg5) {

			attemptingFullContextCount++;
		}

		@Override
		public void reportContextSensitivity(Parser arg0, DFA arg1, int arg2, int arg3, int arg4, ATNConfigSet arg5) {
			contextSensitivityCount++;
		}

		@Override
		public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine,
				String msg, RecognitionException e) {

			syntaxErrorCount++;
			errors.add(new SyntaxErrorNode(offendingSymbol, line, charPositionInLine, msg, e));
		}
	}

	/** Parses d.ts files */
	public DtsParseResult parse(Path srcFolder, Reader reader, LazyLinkingResource resource) throws IOException {
		URI uri = resource.getURI();
		if (URIUtils.isVirtualResourceURI(uri)) {
			NestedResourceAdapter adapter = NestedResourceAdapter.get(resource);
			if (adapter == null) {
				// should not happen, taken care of in caller
			}

			return parseNestedScript(srcFolder, resource, adapter);
		} else {
			return parseScript(srcFolder, reader, resource);
		}
	}

	/** Parse the given .d.ts source code to an ANTLR {@link ParserRuleContext}. Intended for testing only. */
	public static ProgramContext parseDts(CharSequence dtsCode) throws IOException {
		CharStream fileContents = fromReader(new StringReader(dtsCode.toString()));
		TypeScriptLexer lexer = new TypeScriptLexer(fileContents);
		DtsTokenStream tokens = new DtsTokenStream(lexer);
		TypeScriptParser parser = new TypeScriptParser(tokens);
		parser.getInterpreter().setPredictionMode(PredictionMode.SLL);
		ProgramContext program = parser.program();
		return program;
	}

	private DtsParseResult parseScript(Path srcFolder, Reader reader, LazyLinkingResource resource) throws IOException {
		CharStream fileContents = fromReader(reader);
		long millis = System.currentTimeMillis();

		TypeScriptLexer lexer = new TypeScriptLexer(fileContents);
		DtsTokenStream tokens = new DtsTokenStream(lexer);
		TypeScriptParser parser = new TypeScriptParser(tokens);
		ParseStats stats = new ParseStats();
		parser.addErrorListener(stats);
		lexer.removeErrorListener(ConsoleErrorListener.INSTANCE);
		parser.removeErrorListener(ConsoleErrorListener.INSTANCE);

		stats.strategy = PredictionMode.SLL;

		try {
			parser.getInterpreter().setPredictionMode(PredictionMode.SLL);
			// parser.setErrorHandler(new BailErrorStrategy()); // use BailErrorStrategy for fallback
			stats.tree = parser.program();
		} catch (Exception e) {
			// fallback

			// parse with LL(*) to prevent false errors
			parser.setErrorHandler(new DefaultErrorStrategy());
			parser.getInterpreter().setPredictionMode(PredictionMode.LL_EXACT_AMBIG_DETECTION);

			tokens.seek(0); // resets the token stream
			stats.strategy = PredictionMode.LL_EXACT_AMBIG_DETECTION;
			stats.tree = parser.program();
		}

		stats.time = System.currentTimeMillis() - millis;

		// clear proxies that will be (re-)created by the AST builder below
		resource.clearLazyProxyInformation();

		// convert parse tree to AST
		DtsScriptBuilder astBuilder = new DtsScriptBuilder(tokens, srcFolder, resource);
		Script root = astBuilder.consume(stats.tree);
		RootNode rootNode = new RootNode(stats.tree);
		Iterable<? extends INode> syntaxErrors = stats.errors;

		ILoadResultInfoAdapter loadResultInfo = ILoadResultInfoAdapter.get(resource);
		if (loadResultInfo != null) {
			loadResultInfo.ensureNestedResourcesExist(resource);
		}

		return new DtsParseResult(root, rootNode, syntaxErrors);
	}

	private DtsParseResult parseNestedScript(Path srcFolder, LazyLinkingResource resource,
			NestedResourceAdapter adapter) {

		ParserRuleContext ctx = adapter.getContext();
		List<StatementContext> statements = adapter.getStatements();
		DtsTokenStream tokens = adapter.getTokenStream();

		ProgramContext prgCtx = new ProgramContext(null, 0) {
			{
				this.start = ctx.start;
				this.stop = ctx.stop;
			}

			@Override
			public StatementListContext statementList() {
				return new StatementListContext(this, 0) {
					{
						this.start = ctx.start;
						this.stop = ctx.stop;
					}

					@Override
					public List<StatementContext> statement() {
						return statements;
					}
				};
			}
		};

		// convert parse tree to AST
		DtsScriptBuilder astBuilder = new DtsScriptBuilder(tokens, srcFolder, resource);
		Script root = astBuilder.consume(prgCtx);
		RootNode rootNode = new RootNode(ctx);

		// nested scripts represent declared modules and elements directly contained
		// in a declared module are always exported, so we have to adjust as follows:
		exportAllTopLevelElements(root);

		return new DtsParseResult(root, rootNode, Collections.emptyList());
	}

	private void exportAllTopLevelElements(Script script) {
		List<ScriptElement> elems = script.getScriptElements();
		for (int i = 0; i < elems.size(); i++) {
			ScriptElement elem = elems.get(i);
			if (elem instanceof ExportableElement) {
				ExportDeclaration exportDecl = N4JSFactory.eINSTANCE.createExportDeclaration();
				elems.set(i, exportDecl);
				exportDecl.setExportedElement((ExportableElement) elem);
			}
		}
	}
}
