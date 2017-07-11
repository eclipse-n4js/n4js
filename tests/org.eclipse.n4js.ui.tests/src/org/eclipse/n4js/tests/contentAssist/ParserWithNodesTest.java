package org.eclipse.n4js.tests.contentAssist;

import java.util.Collection;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.ui.contentassist.CustomN4JSParser;
import org.eclipse.xtext.ide.editor.contentassist.antlr.FollowElement;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.util.LazyStringInputStream;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * @author Sebastian Zarnekow - Initial contribution and API
 */
public class ParserWithNodesTest extends AbstractN4JSContentAssistParserTest {

	@Inject
	private CustomN4JSParser parser;

	@Inject
	private Provider<XtextResource> resourceProvider;

	@SuppressWarnings("resource")
	@Override
	protected Collection<FollowElement> getFollowSet(String input) throws Exception {
		XtextResource resource = resourceProvider.get();
		resource.setURI(URI.createURI("dummy://some.n4js"));
		resource.load(new LazyStringInputStream(input), null);
		ICompositeNode rootNode = resource.getParseResult().getRootNode();
		return this.parser.getFollowElements(rootNode, 0, input.length(), false);
	}

	@Override
	protected Collection<FollowElement> getFollowSet(FollowElement input) throws Exception {
		return parser.getFollowElements(input);
	}
}