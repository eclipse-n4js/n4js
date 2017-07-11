package org.eclipse.n4js.tests.contentAssist;

import java.util.Set;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.RecognitionException;
import org.eclipse.n4js.ui.contentassist.antlr.internal.InternalN4JSParser;
import org.eclipse.n4js.ui.contentassist.antlr.lexer.InternalN4JSLexer;
import org.eclipse.xtext.ide.editor.contentassist.antlr.FollowElement;
import org.eclipse.xtext.ide.editor.contentassist.antlr.ObservableXtextTokenStream;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Sebastian Zarnekow - Initial contribution and API
 */
public class InternalParserTest extends AbstractFollowElementComputationTest {

	protected InternalN4JSParser createParser(String input) {
		CharStream stream = new ANTLRStringStream(input);
		InternalN4JSLexer lexer = new InternalN4JSLexer(stream);
		InternalN4JSParser result = new InternalN4JSParser(null);
		ObservableXtextTokenStream tokenStream = new ObservableXtextTokenStream(lexer, result);
		tokenStream.setInitialHiddenTokens("RULE_WS", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_EOL");
		tokenStream.setListener(result);
		result.setGrammarAccess(grammarAccess);
		result.setTokenStream(tokenStream);
		return result;
	}

	@Test
	public void testSetup() {
		String input = "";
		InternalN4JSParser parser = createParser(input);
		Assert.assertNotNull(parser);
	}

	@Override
	protected Set<FollowElement> getFollowSet(String input) throws RecognitionException {
		InternalN4JSParser parser = createParser(input);
		parser.entryRuleScript();
		return parser.getFollowElements();
	}

}