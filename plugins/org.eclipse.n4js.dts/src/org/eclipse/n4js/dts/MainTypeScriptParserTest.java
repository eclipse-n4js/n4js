/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.dts;

import static org.antlr.v4.runtime.CharStreams.fromFileName;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
import org.eclipse.n4js.dts.astbuilders.DtsScriptBuilder;
import org.eclipse.n4js.n4JS.Script;

/**
 * Test class
 */
@SuppressWarnings("javadoc")
public class MainTypeScriptParserTest {
	static final Path PARSE_SINGLE_FILE = Path.of("/Users/marcusmews/GitHub/DefinitelyTyped/types/3box/index.d.ts");

	static final Path DEFINITELY_TYPED = Path.of("/Users/marcusmews/GitHub/DefinitelyTyped/types/");
	static final String START_FROM = "";
	static final String EXCLUDES[] = { "carbon__icons", "carbon__pictograms" };

	/** Test entry point */
	static public void main(String[] args) throws IOException {
		// ParseTree tree = parse("test.ts");
		// ParseTreeWalker walker = new ParseTreeWalker();
		// ParseTreeListener listener = new TypeScriptParserBaseListener();
		// walker.walk(listener, tree);

		long startTime = System.currentTimeMillis();
		// testFile();
		testFolder();
		System.out.println("\n\nElapsed Time: " + (System.currentTimeMillis() - startTime) / 1000 + "s");
	}

	static class ParseStats implements ANTLRErrorListener {
		String fileName;
		long time = 0;
		int lines = 0;
		String strategy;
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
		public void syntaxError(Recognizer<?, ?> arg0, Object arg1, int arg2, int arg3, String arg4,
				RecognitionException arg5) {
			syntaxErrorCount++;
		}
	}

	@SuppressWarnings("deprecation")
	static public ParseStats parse(Path file) throws IOException, RecognitionException {
		String fileName = file.toString();
		CharStream fileContents = fromFileName(fileName);
		long millis = System.currentTimeMillis();
		TypeScriptLexer lexer = new TypeScriptLexer(fileContents);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		TypeScriptParser parser = new TypeScriptParser(tokens);
		ParseStats stats = new ParseStats();
		stats.fileName = fileName;
		parser.addErrorListener(stats);
		// parser.setProfile(true);
		stats.strategy = "SLL";
		try {
			parser.getInterpreter().setPredictionMode(PredictionMode.SLL);
			// parser.setErrorHandler(new BailErrorStrategy());
			stats.tree = parser.program();
		} catch (Exception e) {

			// parse with LL(*) to prevent false errors
			parser.setErrorHandler(new DefaultErrorStrategy());
			parser.getInterpreter().setPredictionMode(PredictionMode.LL_EXACT_AMBIG_DETECTION);

			tokens.reset();
			stats.strategy = "LL ";
			stats.tree = parser.program();
		}

		stats.time = System.currentTimeMillis() - millis;

		stats.lines = Files.readAllLines(file).size();

		if (parser.getParseInfo() != null) {
			Arrays.stream(parser.getParseInfo().getDecisionInfo())
					.filter(decision -> decision.timeInPrediction > 100000000)
					.sorted((d1, d2) -> Long.compare(d2.timeInPrediction, d1.timeInPrediction))
					.forEach(decision -> System.out.println(
							String.format(
									"Time: %d in %d calls - LL_Lookaheads: %d Max k: %d Ambiguities: %d Errors: %d Rule: %s",
									decision.timeInPrediction / 1000000,
									decision.invocations, decision.SLL_TotalLook,
									decision.SLL_MaxLook, decision.ambiguities.size(),
									decision.errors.size(), TypeScriptParser.ruleNames[TypeScriptParser._ATN
											.getDecisionState(decision.decision).ruleIndex])));
		}

		return stats;
	}

	static void testFile() throws IOException {
		ParseStats stats = parse(PARSE_SINGLE_FILE);

		ManualParseTreeWalker walker = new ManualParseTreeWalker(stats.tree);
		DtsScriptBuilder astBuilder = new DtsScriptBuilder(walker);
		walker.start();

		Script script = astBuilder.getScript();
		System.out.println("script created: " + (script != null));
	}

	static void testFolder() throws IOException {
		List<Path> files = Files.walk(DEFINITELY_TYPED, FileVisitOption.FOLLOW_LINKS)
				.filter(file -> {
					if (!file.toString().endsWith(".d.ts")) {
						return false;
					}
					String relPath = DEFINITELY_TYPED.relativize(file).toString();
					for (String exclude : EXCLUDES) {
						if (exclude != null && !exclude.isBlank() && relPath.startsWith(exclude)) {
							return false;
						}
					}
					return true;
				})
				.collect(Collectors.toList());
		Collections.sort(files, (p1, p2) -> p1.toString().compareTo(p2.toString()));
		int filesCount = files.size();
		System.out.println("Processing " + filesCount + " files ...");
		int good = 0;
		int bad = 0;
		for (Path file : files) {
			String relPath = DEFINITELY_TYPED.relativize(file).toString();
			if (relPath.compareTo(START_FROM.toString()) < 0) {
				continue;
			}
			try {
				ParseStats stats = parse(file);
				if (stats.syntaxErrorCount == 0) {
					good++;

					// ParseTreeWalker walker = new ParseTreeWalker();
					// DtsAstBuilder treeListener = new DtsAstBuilder();
					// ManualParseTreeWalker w2 = new ManualParseTreeWalker(treeListener, stats.tree);
					// w2.start();
					// walker.walk(new TypeScriptParserBaseListener(), stats.tree);
				} else {
					bad++;
				}
				System.out.println(
						stats.time + "ms\t/ " + stats.lines + "\t- " + stats.strategy + " - [" + stats.ambiguityCount
								+ ", " + stats.contextSensitivityCount + ", " + stats.attemptingFullContextCount
								+ "] -- " + percent(bad, good + bad) + "% -- " + stats.fileName);

			} catch (RecognitionException e) {
				bad++;
			}
		}
		System.out.println("Done processing " + filesCount + " files: good " + good + " (" + percent(good, filesCount)
				+ "%) / bad " + bad + " (" + percent(bad, filesCount) + "%).");
	}

	static String percent(double n, double full) {
		return String.valueOf(Math.floor((n / full) * 1000d) / 10d);
	}
}
