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
package org.eclipse.n4js.xpect.ui.methods;

import static org.junit.Assert.fail;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonToken;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.Token;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.parser.antlr.lexer.InternalN4JSLexer;
import org.eclipse.n4js.tests.outline.AbstractOutlineWorkbenchTest;
import org.eclipse.xpect.runner.Xpect;
import org.eclipse.xpect.xtext.lib.setup.ThisResource;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.impl.SyntheticCompositeNode;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.outline.IOutlineNode;
import org.eclipse.xtext.util.ReplaceRegion;

import com.google.common.collect.Lists;
import com.google.inject.Provider;

/**
 */
// TODO xpect method should not extend abstract test
public class OutlineXpectMethod extends AbstractOutlineWorkbenchTest {

	@SuppressWarnings("javadoc")
	@Xpect
	public void assertNoExceptionWhenExpandingAllOutlineNodes(@ThisResource XtextResource resource) throws Exception {
		String platformStr = resource.getURI().toString().replace("platform:/resource/", "");
		file = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(platformStr));
		super.setUp();
		assertNoExceptionSmoke(resource);
	}

	private void assertNoException(String mode) throws Exception {
		try {
			traverseChildren(super.modelNode);
		} catch (Exception exc) {
			Writer writer = new StringWriter();
			PrintWriter printWriter = new PrintWriter(writer);
			exc.printStackTrace(printWriter);
			fail("Exception in outline tree construction when in mode \"" + mode + "\": \n" + writer.toString());
		}
	}

	private void assertNoExceptionSmoke(XtextResource resource) throws Exception {
		String text = document.get();
		assertNoException("default");
		skipLastCharacters(text);
		skipFirstCharacters(text);
		skipCharactersInBetween(text);
		skipThreeCharactersInBetween(text);
		skipTokensInBetween(text);
		skipNodesInBetween((Script) resource.getContents().get(0));
	}

	private void skipLastCharacters(CharSequence input) throws Exception {
		String string = input.toString();
		for (int i = 0; i < input.length(); i++) {
			processFile(string.substring(0, i), "skipLastCharacters");
		}
	}

	private void skipFirstCharacters(CharSequence input) throws Exception {
		String string = input.toString();
		for (int i = 0; i < input.length(); i++) {
			processFile(string.substring(i), "skipFirstCharacters");
		}
	}

	private void skipCharactersInBetween(CharSequence input) throws Exception {
		String string = input.toString();
		if (input.length() > 1) {
			for (int i = 0; i < input.length() - 1; i++) {
				processFile(string.substring(0, i) + string.substring(i + 1), "skipCharactersInBetween");
			}
		}
	}

	private void skipThreeCharactersInBetween(CharSequence input) throws Exception {
		String string = input.toString();
		if (input.length() > 4) {
			for (int i = 0; i < input.length() - 3; i++) {
				processFile(string.substring(0, i) + string.substring(i + 3), "skipThreeCharactersInBetween");
			}
		}
	}

	private void skipTokensInBetween(CharSequence input) throws Exception {
		String string = input.toString();
		List<CommonToken> tokenList = Lists.newArrayList();
		{
			Lexer lexer = lexerProvider.get();
			lexer.setCharStream(new ANTLRStringStream(string));
			Token token = lexer.nextToken();
			while (token != Token.EOF_TOKEN) {
				tokenList.add((CommonToken) token);
				token = lexer.nextToken();
			}
		}
		for (CommonToken token : tokenList) {
			int start = token.getStartIndex();
			int length = token.getText().length();
			processFile(string.substring(0, start) + string.substring(start + length), "skipTokensInBetween");
		}
	}

	private void skipNodesInBetween(Script script) throws Exception {
		if (script != null) {
			XtextResource resource = (XtextResource) script.eResource();
			ICompositeNode rootNode = resource.getParseResult().getRootNode();
			ReplaceRegion region = null;
			for (INode node : rootNode.getAsTreeIterable()) {
				if (node instanceof ICompositeNode && !(node instanceof SyntheticCompositeNode)) {
					ICompositeNode casted = (ICompositeNode) node;
					int offset = node.getTotalOffset();
					int length = node.getTotalLength();
					if (length != 0) {
						if (casted.getFirstChild().equals(casted.getLastChild())) {
							if (region == null || region.getOffset() != offset || region.getLength() != length) {
								region = new ReplaceRegion(offset, length, "");
								StringBuilder builder = new StringBuilder(rootNode.getText());
								region.applyTo(builder);
								processFile(builder.toString(), "skipNodesInBetween");
							}
						}
					}
				}
			}
		}
	}

	private final Provider<? extends Lexer> lexerProvider = new Provider<>() {
		@Override
		public Lexer get() {
			return new InternalN4JSLexer(null);
		}
	};

	private void processFile(String text, String mode) throws Exception {
		document.set(text);
		assertNoException(mode);
	}

	private void traverseChildren(IOutlineNode node) {
		for (IOutlineNode child : node.getChildren()) {
			traverseChildren(child);
		}
	}

	@Override
	protected boolean shouldCreateProjectStructure() {
		// as done by Xpect
		return false;
	}

	@Override
	protected boolean checkForCleanWorkspace() {
		return false;
	}

	@Override
	protected String getFileName() {
		return null;
	}

	@Override
	protected String getModuleFolder() {
		return null;
	}

	@Override
	protected String getProjectName() {
		return null;
	}

	@Override
	protected String getModelAsText() {
		return null;
	}
}
