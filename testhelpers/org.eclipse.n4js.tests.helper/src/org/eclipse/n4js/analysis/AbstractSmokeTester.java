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
package org.eclipse.n4js.analysis;

import java.util.List;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonToken;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.Token;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.SmokeTestWriter;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.parser.antlr.lexer.InternalN4JSLexer;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.impl.SyntheticCompositeNode;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.util.ReplaceRegion;
import org.eclipse.xtext.util.StringInputStream;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 */
public abstract class AbstractSmokeTester {

	private final Provider<? extends Lexer> lexerProvider = new Provider<>() {
		@Override
		public Lexer get() {
			return new InternalN4JSLexer(null);
		}
	};

	@Inject
	private Provider<XtextResourceSet> resourceSetProvider;

	/***/
	public void assertNoException(CharSequence expression) throws Exception {
		boolean old = SmokeTestWriter.active;
		SmokeTestWriter.active = false;
		try {
			skipLastCharacters(expression);
			skipFirstCharacters(expression);
			skipCharactersInBetween(expression);
			skipThreeCharactersInBetween(expression);
			skipTokensInBetween(expression);
			skipNodesInBetween(expression);
		} finally {
			SmokeTestWriter.active = old;
		}
	}

	private void skipLastCharacters(CharSequence input) throws Exception {
		String string = input.toString();
		for (int i = 0; i < input.length(); i++) {
			processFile(string.substring(0, i));
		}
	}

	private void skipFirstCharacters(CharSequence input) throws Exception {
		String string = input.toString();
		for (int i = 0; i < input.length(); i++) {
			processFile(string.substring(i));
		}
	}

	private void skipCharactersInBetween(CharSequence input) throws Exception {
		String string = input.toString();
		if (input.length() > 1) {
			for (int i = 0; i < input.length() - 1; i++) {
				processFile(string.substring(0, i) + string.substring(i + 1));
			}
		}
	}

	private void skipThreeCharactersInBetween(CharSequence input) throws Exception {
		String string = input.toString();
		if (input.length() > 4) {
			for (int i = 0; i < input.length() - 3; i++) {
				processFile(string.substring(0, i) + string.substring(i + 3));
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
			processFile(string.substring(0, start) + string.substring(start + length));
		}
	}

	private void skipNodesInBetween(CharSequence input) throws Exception {
		String string = input.toString();
		Script script = completeScript(string);
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
								processFile(builder.toString());
							}
						}
					}
				}
			}
		}
	}

	private Script completeScript(String string) throws Exception {
		Resource resource = newResourceSet().createResource(URI.createURI("sample.js"));
		StringInputStream stream = new StringInputStream(string);
		try {
			resource.load(stream, null);
			Script script = (Script) resource.getContents().get(0);
			return script;
		} finally {
			stream.close();
		}
	}

	/**
	 * Creates a new, empty resource set.
	 */
	protected XtextResourceSet newResourceSet() {
		return resourceSetProvider.get();
	}

	/***/
	protected abstract void processFile(String input) throws Exception;
}
