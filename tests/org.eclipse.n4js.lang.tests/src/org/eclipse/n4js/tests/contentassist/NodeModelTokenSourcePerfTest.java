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
package org.eclipse.n4js.tests.contentassist;

import java.io.IOException;
import java.io.StringReader;
import java.net.URL;

import org.antlr.runtime.Token;
import org.antlr.runtime.TokenSource;
import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.ide.contentassist.antlr.lexer.InternalN4JSLexer;
import org.eclipse.n4js.ide.editor.contentassist.TokenSourceFactory;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.parser.AntlrStreamWithToString;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.common.base.Charsets;
import com.google.common.io.CharSource;
import com.google.common.io.Resources;
import com.google.inject.Inject;

/**
 * Small performance tests, since they are quite fast (and they must not be executed as plugin tests), they are using a
 * file name different from the performance test convention.
 */
@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProvider.class)
public class NodeModelTokenSourcePerfTest {

	@Inject
	ParseHelper<Script> parseHelper;

	@Inject
	TokenSourceFactory tsFactory;

	String testData;
	Script parsed;
	ICompositeNode node;

	@Before
	public void setUp() throws Exception {
		URL resourceURL = NodeModelTokenSourcePerfTest.class
				.getResource("/org/eclipse/n4js/tests/contentassist/testdata.txt");
		CharSource charSrc = Resources.asCharSource(resourceURL, Charsets.UTF_8);
		testData = charSrc.read();
		parsed = parseHelper.parse(testData);
		node = NodeModelUtils.getNode(parsed).getRootNode();
	}

	@After
	public void tearDown() {
		testData = null;
		parsed = null;
		node = null;
	}

	@Test
	public void test_nodes_1000() {
		for (int i = 1; i <= 1000; i++) {
			TokenSource source = tsFactory.toTokenSource(node, false);
			while (Token.EOF_TOKEN != source.nextToken()) {
				// empty
			}
		}
	}

	@Test
	public void test_nodes_1000_filtered() {
		for (int i = 1; i <= 1000; i++) {
			TokenSource source = tsFactory.toTokenSource(node, testData.length(), true);
			while (Token.EOF_TOKEN != source.nextToken()) {
				// empty
			}
		}
	}

	@Test
	public void test_lexer_1000() throws IOException {
		for (int i = 1; i <= 1000; i++) {
			AntlrStreamWithToString stream = new AntlrStreamWithToString(new StringReader(testData));
			InternalN4JSLexer lexer = new InternalN4JSLexer(stream);
			while (Token.EOF_TOKEN != lexer.nextToken()) {
				// empty
			}
		}
	}

	@Test
	public void test_nodes_allOffsets() {
		for (int endOffset = 0; endOffset < testData.length(); endOffset++) {
			TokenSource source = tsFactory.toTokenSource(node, endOffset, false);
			while (Token.EOF_TOKEN != source.nextToken()) {
				// empty
			}
		}
	}

	@Test
	public void test_nodes_allOffsets_filter() {
		for (int endOffset = 0; endOffset < testData.length(); endOffset++) {
			TokenSource source = tsFactory.toTokenSource(node, endOffset, true);
			while (Token.EOF_TOKEN != source.nextToken()) {
				// empty
			}
		}
	}

	@Test
	public void test_lexer_allOffsets() throws IOException {
		for (int endOffset = 0; endOffset < testData.length(); endOffset++) {
			AntlrStreamWithToString stream = new AntlrStreamWithToString(
					new StringReader(testData.substring(0, endOffset)));
			InternalN4JSLexer lexer = new InternalN4JSLexer(stream);
			while (Token.EOF_TOKEN != lexer.nextToken()) {
				// empty
			}
		}
	}

}
