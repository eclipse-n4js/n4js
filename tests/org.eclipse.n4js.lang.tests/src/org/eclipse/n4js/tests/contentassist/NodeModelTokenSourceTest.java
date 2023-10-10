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

import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toSet;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.antlr.runtime.CharStream;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenSource;
import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.ide.editor.contentassist.TokenSourceFactory;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.parser.N4JSSemicolonInjectingParser;
import org.eclipse.n4js.parser.RegExLiteralAwareLexer;
import org.eclipse.n4js.parser.antlr.internal.InternalN4JSParser;
import org.eclipse.n4js.services.N4JSGrammarAccess;
import org.eclipse.n4js.utils.Strings;
import org.eclipse.xtext.GrammarUtil;
import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.TerminalRule;
import org.eclipse.xtext.grammaranalysis.impl.GrammarElementTitleSwitch;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.impl.InvariantChecker;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.eclipse.xtext.xbase.lib.Procedures;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

@SuppressWarnings("restriction")
@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProvider.class)
public class NodeModelTokenSourceTest implements Procedures.Procedure1<Integer> {

	@Inject
	ParseHelper<Script> parseHelper;

	@Inject
	TokenSourceFactory tsFactory;

	@Inject
	AccessibleProductionParser accessibleParser;

	@Inject
	N4JSGrammarAccess grammarAccess;

	List<Integer> expectedTokenTypes;

	@Before
	public void setUp() {
		expectedTokenTypes = new ArrayList<>();
		accessibleParser.callback = this;
	}

	@After
	public void tearDown() {
		expectedTokenTypes = null;
		accessibleParser = null;
	}

	@Test
	public void test_01() {
		String script = "1";

		assertEqualTokenTypes("1;", script);
	}

	@Test
	public void test_02() {
		String script = "1/*\n*/2";

		assertEqualTokenTypes("1;2;", script);
	}

	@Test
	public void test_03() {
		String script = """
					{ 1
					2 } 3
				""";

		assertEqualTokenTypes("{1;2;}3;", script);
	}

	@Test
	public void test_04() {
		String script = """
					{ 1
					2 }
				""";

		assertEqualTokenTypes("{1;2;}\n", script);
	}

	@Test
	public void test_05() {
		String script = """
					1
					+2
				""";

		assertEqualTokenTypes("1+2;", script);
	}

	@Test
	public void test_06() {
		String script = """
					1;
					-2
				""";

		assertEqualTokenTypes("1;-2;", script);
	}

	@Test
	public void test_07() {
		String script = """
					1/*
					*/2
				""";
		assertEqualTokenTypes("1;2;", script);
	}

	@Test
	public void test_08() {
		String script = "1+";
		assertEqualTokenTypes("1+", script);
	}

	@Test
	public void test_09() {
		String script = """
					1
					+
				""";
		assertEqualTokenTypes("1+\n", script);
	}

	@Test
	public void test_10() {
		String script = """
					1+<|>2
				""";
		assertEqualTokenTypes("1+", script);
	}

	@Test
	public void test_11() {
		String script = """
					1/*
					*/<|>2
				""";
		assertEqualTokenTypes("1;", script);
	}

	@Test
	public void test_12() {
		String script = """
					1/*
					*/<|>+2
				""";
		assertEqualTokenTypes("1/**/", script);
	}

	@Test
	public void test_13() {
		String script = """
					1/*
					*/+2
				""";
		assertEqualTokenTypes("1+2;", script);
	}

	@Test
	public void test_14() {
		String script = """
					1<|>/*
					*/2
				""";
		assertEqualTokenTypes("1", script);
	}

	@Test
	public void test_15() {
		String script = """
					1
					<|>
					2
				""";
		assertEqualTokenTypes("1;", script);
	}

	@Test
	public void test_16() {
		String script = """
					1

					<|>2
				""";
		assertEqualTokenTypes("1;\n", script);
	}

	@Test
	public void test_17() {
		String script = """
					<|>/* some copyright header */
					1+2
				""";
		assertEqualTokenTypes("", script);
	}

	@Test
	public void test_18() {
		String script = "1\n";

		assertEqualTokenTypes("1;", script);
	}

	@Test
	public void test_19() {
		String script = "1<|>";

		assertEqualTokenTypes("1", script);
	}

	@Test
	public void test_20() {
		String script = "1\n<|>";

		assertEqualTokenTypes("1;", script);
	}

	@Test
	public void test_21() {
		String script = "a.\n<|>";

		assertEqualTokenTypes("a.\n", script);
	}

	@Test
	public void test_22() {
		String script = "a.\n";
		assertEqualTokenTypes("a.\n", script);
	}

	@Test
	public void test_23() {
		String script = "return";
		assertEqualTokenTypes("return;", script);
	}

	@Test
	public void test_24() {
		String script = "ret<|>urn";
		assertEqualTokenTypes("ret", script);
	}

	@Test
	public void test_25() {
		String script = "if (true) \"st<|>ring\"";
		assertEqualTokenTypes("if(true)\"st", script);
	}

	@Test
	public void test_26() {
		String script = "f(): .. {<|>}";
		assertEqualTokenTypes("f():..{", script);
	}

	/**
	 * Tests that no right curly braces are added to the grammar without noticing that they may have an impact on the
	 * NodeModelTokenSource.
	 */
	@Test
	public void testKnownRightCurlies() {
		Set<Keyword> curlies = toSet(filter(grammarAccess.findKeywords("}"),
				it -> !(GrammarUtil.containingRule(it) instanceof TerminalRule)));

		Assert.assertTrue(curlies.remove(grammarAccess.getNamedExportClauseAccess().getRightCurlyBracketKeyword_2()));
		Assert.assertTrue(
				curlies.remove(grammarAccess.getAnnotatedScriptElementAccess().getRightCurlyBracketKeyword_1_4_7()));

		Assert.assertTrue(
				curlies.remove(grammarAccess.getAnnotatedNamespaceElementAccess().getRightCurlyBracketKeyword_1_2_7()));

		Assert.assertTrue(
				curlies.remove(
						grammarAccess.getAnnotatedExportableElementAccess().getRightCurlyBracketKeyword_1_3_7()));

		Assert.assertTrue(
				curlies.remove(
						grammarAccess.getImportSpecifiersExceptDefaultAccess().getRightCurlyBracketKeyword_1_2()));

		Assert.assertTrue(
				curlies.remove(grammarAccess.getFunctionTypeExpressionOLDAccess().getRightCurlyBracketKeyword_9()));

		Assert.assertTrue(curlies.remove(grammarAccess.getArrowExpressionAccess().getRightCurlyBracketKeyword_1_0_2()));

		Assert.assertTrue(curlies.remove(grammarAccess.getBlockAccess().getRightCurlyBracketKeyword_2()));

		Assert.assertTrue(curlies.remove(grammarAccess.getMembersAccess().getRightCurlyBracketKeyword_2()));

		Assert.assertTrue(curlies.remove(grammarAccess.getSwitchStatementAccess().getRightCurlyBracketKeyword_7()));

		Assert.assertTrue(curlies.remove(grammarAccess.getObjectLiteralAccess().getRightCurlyBracketKeyword_3()));

		Assert.assertTrue(curlies.remove(grammarAccess.getTemplateExpressionEndAccess().getRightCurlyBracketKeyword()));

		Assert.assertTrue(curlies.remove(grammarAccess.getN4EnumDeclarationAccess().getRightCurlyBracketKeyword_3()));

		Assert.assertTrue(
				curlies.remove(grammarAccess.getN4NamespaceDeclarationAccess().getRightCurlyBracketKeyword_3()));

		Assert.assertTrue(
				curlies.remove(grammarAccess.getObjectBindingPatternAccess().getRightCurlyBracketKeyword_3()));

		Assert.assertTrue(curlies.remove(grammarAccess.getTStructMemberListAccess().getRightCurlyBracketKeyword_2()));

		Assert.assertTrue(
				curlies.remove(grammarAccess.getUnionTypeExpressionOLDAccess().getRightCurlyBracketKeyword_5()));
		Assert.assertTrue(
				curlies.remove(grammarAccess.getIntersectionTypeExpressionOLDAccess().getRightCurlyBracketKeyword_5()));
		Assert.assertTrue(curlies.remove(grammarAccess.getTypeTypeRefAccess().getRightCurlyBracketKeyword_4()));

		Assert.assertTrue(curlies.remove(grammarAccess.getJSXExpressionAccess().getRightCurlyBracketKeyword_2()));
		Assert.assertTrue(curlies.remove(grammarAccess.getJSXSpreadAttributeAccess().getRightCurlyBracketKeyword_3()));
		Assert.assertTrue(
				curlies.remove(grammarAccess.getJSXPropertyAttributeAccess().getRightCurlyBracketKeyword_1_1_3_2()));

		for (Keyword c : curlies) {
			System.out.println(new GrammarElementTitleSwitch().showRule().showAssignments().showQualified().apply(c));
		}

		Assert.assertEquals(0, curlies.size());
	}

	public void assertEqualTokenTypes(String expectation, String script) {
		int cursor = script.indexOf("<|>");
		Script parsed;
		try {
			parsed = parseHelper.parse(cursor == -1 ? script
					: script.substring(0, cursor) + script.substring(cursor + 3));
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		ICompositeNode node = NodeModelUtils.getNode(parsed).getRootNode();
		new InvariantChecker().checkInvariant(node);
		Assert.assertTrue(expectedTokenTypes.isEmpty());
		if (expectation.length() > 0) {
			accessibleParser.parse(new StringReader(expectation));
			Assert.assertFalse(expectedTokenTypes.isEmpty());
		}
		TokenSource mappingSource = cursor == -1 ? tsFactory.toTokenSource(node, false)
				: tsFactory.toTokenSource(node, cursor, false);

		List<Integer> actual = new ArrayList<>();
		Token token = mappingSource.nextToken();
		while (token != Token.EOF_TOKEN) {
			actual.add(token.getType());
			token = mappingSource.nextToken();
		}
		Assert.assertEquals(toTokenTypeList(expectedTokenTypes), toTokenTypeList(actual));
	}

	protected String toTokenTypeList(List<Integer> tokenTypes) {
		AtomicInteger idx = new AtomicInteger();
		return Strings.join("\n", it -> idx.getAndIncrement() + " - " + it,
				Arrays.asList(InternalN4JSParser.tokenNames), tokenTypes);
	}

	@Override
	public void apply(Integer p) {
		expectedTokenTypes.add(p);
	}

	static public class AccessibleLexer extends RegExLiteralAwareLexer {

		Procedures.Procedure1<Integer> callback;

		public AccessibleLexer(Procedures.Procedure1<Integer> callback) {
			this.callback = callback;
		}

		@Override
		public Token nextToken() {
			Token result = super.nextToken();
			if (result != Token.EOF_TOKEN) {
				callback.apply(result.getType());
			}
			return result;
		}

	}

	static public class AccessibleProductionParser extends N4JSSemicolonInjectingParser {

		Procedures.Procedure1<Integer> callback;

		public void setCallback(Procedures.Procedure1<Integer> callback) {
			this.callback = callback;
		}

		@Override
		protected AccessibleLexer createLexer(CharStream stream) {
			AccessibleLexer result = new AccessibleLexer(callback);
			result.setCharStream(stream);
			return result;
		}

	}
}
