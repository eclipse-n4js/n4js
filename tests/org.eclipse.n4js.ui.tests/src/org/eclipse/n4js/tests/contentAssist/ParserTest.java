package org.eclipse.n4js.tests.contentAssist;

import java.util.Collection;

import org.antlr.runtime.RecognitionException;
import org.eclipse.n4js.ui.contentassist.antlr.N4JSParser;
import org.eclipse.xtext.ide.editor.contentassist.antlr.FollowElement;

import com.google.inject.Inject;

/**
 * @author Sebastian Zarnekow - Initial contribution and API
 */
public class ParserTest extends AbstractN4JSContentAssistParserTest {

	@Inject
	private N4JSParser parser;

	@Override
	protected Collection<FollowElement> getFollowSet(String input) throws RecognitionException {
		return this.parser.getFollowElements(input, false);
	}

	@Override
	protected Collection<FollowElement> getFollowSet(FollowElement input) throws Exception {
		return parser.getFollowElements(input);
	}
}