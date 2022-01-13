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
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.DefaultErrorStrategy;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.atn.PredictionMode;
import org.antlr.v4.runtime.dfa.DFA;
import org.eclipse.n4js.dts.TypeScriptParser.ProgramContext;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.xtext.nodemodel.INode;

/**
 *
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

	public DtsParseResult parse(Reader reader) throws IOException {
		CharStream fileContents = fromReader(reader);
		long millis = System.currentTimeMillis();

		TypeScriptLexer lexer = new TypeScriptLexer(fileContents);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		TypeScriptParser parser = new TypeScriptParser(tokens);
		ParseStats stats = new ParseStats();
		parser.addErrorListener(stats);
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

		// convert parse tree to AST
		ManualParseTreeWalker walker = new ManualParseTreeWalker(stats.tree);
		DtsAstBuilder astBuilder = new DtsAstBuilder(walker);
		walker.start();

		Script root = astBuilder.getScript();
		RootNode rootNode = new RootNode(stats.tree);
		Iterable<? extends INode> syntaxErrors = stats.errors;

		return new DtsParseResult(root, rootNode, syntaxErrors);
	}

}
