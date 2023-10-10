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
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.ide.contentassist.antlr.N4JSParser.NameMappings;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.services.N4JSGrammarAccess;
import org.eclipse.n4js.utils.Strings;
import org.eclipse.xtext.AbstractElement;
import org.eclipse.xtext.Alternatives;
import org.eclipse.xtext.Assignment;
import org.eclipse.xtext.ide.editor.contentassist.antlr.FollowElement;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import com.google.inject.Inject;

@RunWith(XtextRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@InjectWith(N4JSInjectorProvider.class)
public abstract class AbstractContentAssistParserTest extends Assert {

	@Inject
	ParseHelper<Script> parseHelper;
	@Inject
	protected N4JSGrammarAccess grammarAccess;
	@Inject
	protected NameMappings nameMappings;
	@Inject
	GrammarPrettyPrinter prettyPrinter;

	public abstract Collection<FollowElement> getFollowElements(INode node, int start, int end, boolean strict);

	public abstract Collection<FollowElement> getFollowElements(FollowElement prev);

	public Collection<FollowElement> getFollowElements(INode node, String after) {
		return getFollowElements(node, 0, after.length(), true);
	}

	public Collection<FollowElement> getFollowElements(INode node) {
		return getFollowElements(node, 0, node.getTotalLength(), true);
	}

	public Collection<FollowElement> getFollowElements(INode node, boolean strict) {
		return getFollowElements(node, 0, node.getTotalLength(), strict);
	}

	public String prettyPrint(Collection<AbstractElement> elements) {
		return Strings.join("\n\n", prettyPrinter::apply, elements);
	}

	@Test
	public void testEmptyInput() {
		ICompositeNode node = toNode("");
		Collection<FollowElement> followElements = getFollowElements(node, 0, 0, true);
		List<AbstractElement> grammarElements = toList(map(followElements, fe -> fe.getGrammarElement()));

		assertEquals(2, grammarElements.size());
		assertTrue(grammarElements.get(0) instanceof Assignment);
		assertTrue(grammarElements.get(1) instanceof Alternatives);

		EList<AbstractElement> alternatives = ((Alternatives) grammarElements.get(1)).getElements();

		assertEquals(2, alternatives.size());
		assertTrue(alternatives.contains(grammarAccess.getScriptAccess().getAnnotationsAssignment_2_0()));
		assertTrue(alternatives.contains(grammarAccess.getScriptAccess().getScriptElementsAssignment_2_1()));
	}

	@Test
	public void testAfterNumber() {
		ICompositeNode node = toNode("1");
		Collection<FollowElement> followElements = getFollowElements(node);
		List<AbstractElement> grammarElements = toList(map(followElements, fe -> fe.getGrammarElement()));
		assertEquals(prettyPrint(grammarElements), 19, grammarElements.size());
		assertTrue(prettyPrint(grammarElements),
				grammarElements.contains(grammarAccess.getPostfixExpressionAccess().getGroup_1()));
	}

	@Test
	public void testAfterNumber_Semi() {
		ICompositeNode node = toNode("1;");
		Collection<FollowElement> followElements = getFollowElements(node);
		List<AbstractElement> grammarElements = toList(map(followElements, fe -> fe.getGrammarElement()));
		assertEquals(prettyPrint(grammarElements), 1, grammarElements.size());
		assertTrue(grammarElements.contains(grammarAccess.getScriptAccess().getAlternatives_2()));
		assertFalse(prettyPrint(grammarElements),
				grammarElements.contains(grammarAccess.getPostfixExpressionAccess().getGroup_1()));
	}

	@Test
	public void testAfterNumber_SemiAndNL() {
		ICompositeNode node = toNode("1;\n");
		Collection<FollowElement> followElements = getFollowElements(node);
		List<AbstractElement> grammarElements = toList(map(followElements, fe -> fe.getGrammarElement()));
		assertEquals(prettyPrint(grammarElements), 1, grammarElements.size());
		assertTrue(grammarElements.contains(grammarAccess.getScriptAccess().getAlternatives_2()));
		assertFalse(prettyPrint(grammarElements),
				grammarElements.contains(grammarAccess.getPostfixExpressionAccess().getGroup_1()));
	}

	@Test
	public void testAfterNumber_SemiAndComment() {
		ICompositeNode node = toNode("1;/*\n*/");
		Collection<FollowElement> followElements = getFollowElements(node);
		List<AbstractElement> grammarElements = toList(map(followElements, fe -> fe.getGrammarElement()));
		assertEquals(prettyPrint(grammarElements), 1, grammarElements.size());
		assertTrue(grammarElements.contains(grammarAccess.getScriptAccess().getAlternatives_2()));
		assertFalse(prettyPrint(grammarElements),
				grammarElements.contains(grammarAccess.getPostfixExpressionAccess().getGroup_1()));
	}

	@Test
	public void testAfterNumberNL_eof() {
		ICompositeNode node = toNode("1\n");
		Collection<FollowElement> followElements = getFollowElements(node);
		List<AbstractElement> grammarElements = toList(map(followElements, fe -> fe.getGrammarElement()));
		assertEquals(prettyPrint(grammarElements), 19, grammarElements.size());
		assertTrue(grammarElements.contains(grammarAccess.getScriptAccess().getAlternatives_2()));
		assertFalse(prettyPrint(grammarElements),
				grammarElements.contains(grammarAccess.getPostfixExpressionAccess().getGroup_1()));
	}

	@Test
	public void testAfterNumberNLComment_eof() {
		ICompositeNode node = toNode("1/*\n*/");
		Collection<FollowElement> followElements = getFollowElements(node);
		List<AbstractElement> grammarElements = toList(map(followElements, fe -> fe.getGrammarElement()));
		assertEquals(prettyPrint(grammarElements), 19, grammarElements.size());
		assertTrue(grammarElements.contains(grammarAccess.getScriptAccess().getAlternatives_2()));
		assertFalse(prettyPrint(grammarElements),
				grammarElements.contains(grammarAccess.getPostfixExpressionAccess().getGroup_1()));
	}

	@Test
	public void testAfterNumberNL_prevLine() {
		ICompositeNode node = toNode("1\n");
		Collection<FollowElement> followElements = getFollowElements(node, "1");
		List<AbstractElement> grammarElements = toList(map(followElements, fe -> fe.getGrammarElement()));
		assertEquals(prettyPrint(grammarElements), 19, grammarElements.size());
		assertTrue(prettyPrint(grammarElements),
				grammarElements.contains(grammarAccess.getPostfixExpressionAccess().getGroup_1()));
		assertFalse(prettyPrint(grammarElements),
				grammarElements.contains(grammarAccess.getScriptAccess().getScriptElementsAssignment_2_1()));
	}

	@Test
	public void testAfterReturnNL_eof() {
		ICompositeNode node = toNode("return\n");
		Collection<FollowElement> followElements = getFollowElements(node);
		List<AbstractElement> grammarElements = toList(map(followElements, fe -> fe.getGrammarElement()));
		assertEquals(prettyPrint(grammarElements), 1, grammarElements.size());
		assertTrue(prettyPrint(grammarElements),
				grammarElements.contains(grammarAccess.getScriptAccess().getAlternatives_2()));
	}

	@Test
	public void testAfterReturnNLComment_eof() {
		ICompositeNode node = toNode("return/*\n*/");
		Collection<FollowElement> followElements = getFollowElements(node);
		List<AbstractElement> grammarElements = toList(map(followElements, fe -> fe.getGrammarElement()));
		assertEquals(prettyPrint(grammarElements), 1, grammarElements.size());
		assertTrue(prettyPrint(grammarElements),
				grammarElements.contains(grammarAccess.getScriptAccess().getAlternatives_2()));
	}

	@Test
	public void testAfterReturnNL() {
		ICompositeNode node = toNode("return\n\n");
		Collection<FollowElement> followElements = getFollowElements(node, "return\n");
		List<AbstractElement> grammarElements = toList(map(followElements, fe -> fe.getGrammarElement()));

		assertEquals(1, grammarElements.size());
		assertTrue(grammarElements.get(0) instanceof Alternatives);

		EList<AbstractElement> alternatives = ((Alternatives) grammarElements.get(0)).getElements();

		assertEquals(prettyPrint(alternatives), 2, alternatives.size());
		assertTrue(alternatives.contains(grammarAccess.getScriptAccess().getAnnotationsAssignment_2_0()));
		assertTrue(alternatives.contains(grammarAccess.getScriptAccess().getScriptElementsAssignment_2_1()));

	}

	@Test
	public void testAfterReturnNLComment() {
		ICompositeNode node = toNode("return/*\n*/\n");
		Collection<FollowElement> followElements = getFollowElements(node, "return/*\n*/");
		List<AbstractElement> grammarElements = toList(map(followElements, fe -> fe.getGrammarElement()));
		assertEquals(prettyPrint(grammarElements), 1, grammarElements.size());
		assertTrue(prettyPrint(grammarElements),
				grammarElements.contains(grammarAccess.getScriptAccess().getAlternatives_2()));
	}

	@Test
	public void testAfterDot() {
		ICompositeNode node = toNode("a.");
		Collection<FollowElement> followElements = getFollowElements(node);
		List<AbstractElement> grammarElements = toList(map(followElements, fe -> fe.getGrammarElement()));
		assertEquals(prettyPrint(grammarElements), 1, grammarElements.size());
		assertEquals(3, followElements.iterator().next().getLookAhead());
		assertTrue(prettyPrint(grammarElements),
				grammarElements.contains(grammarAccess.getRootStatementAccess().getAlternatives()));
	}

	@Test
	public void testAfterDot_NextBatchOfFollowElements() {
		ICompositeNode node = toNode("a.");
		FollowElement followElement = getFollowElements(node).iterator().next();
		assertSubsequentFollowElementsInMemberExpression(followElement);
	}

	@Test
	public void testAfterDot_IDEBUG481_01() {
		ICompositeNode node = toNode("""
				const constOne = null;
				const constTwo = null;
				const f = function (f: {function(): void}) { };
				a.""");
		FollowElement followElement = getFollowElements(node).iterator().next();
		assertSubsequentFollowElementsInMemberExpression(followElement);
	}

	@Test
	public void testAfterDot_IDEBUG481_02() {
		ICompositeNode node = toNode("""
				const constOne = null;
				const constTwo = null;
				const f = function (f: {function(): void}) { }
				a.""");
		FollowElement followElement = getFollowElements(node).iterator().next();
		assertSubsequentFollowElementsInMemberExpression(followElement);
	}

	@Test
	public void testAfterDot_IDEBUG481_03() {
		ICompositeNode node = toNode("""
				const constOne = null;
				const constTwo = null;
				const f = function (f: {function(): void}) { }
				export public class A {
					test1() {
						f(() => { return null; });
						var c = constOne;
					}
					test2() {
						f(() => { return null; });
						var c = constOne;
					}
				}
				a.""");
		FollowElement followElement = getFollowElements(node).iterator().next();
		assertSubsequentFollowElementsInMemberExpression(followElement);
	}

	@Test
	public void testAfterDot_IDEBUG481_04() {
		ICompositeNode node = toNode("""
				const constOne = null;
				const constTwo = null;
				const f = function (f: {function(): void}) { }
				export public class A {
					test1() {
						f(() => { return null });
						var c = constOne;
					}
					test2() {
						f(() => { return null });
						var c = constOne;
					}
				}
				a.""");
		FollowElement followElement = getFollowElements(node).iterator().next();
		assertSubsequentFollowElementsInMemberExpression(followElement);
	}

	@Test
	public void testAfterDot_IDEBUG481_05() {
		ICompositeNode node = toNode("""
				f(() => { return null });
				a.""");
		FollowElement followElement = getFollowElements(node).iterator().next();
		assertSubsequentFollowElementsInMemberExpression(followElement);
	}

	@Test
	public void testNoModifiers_GH39_nonStrict() {
		ICompositeNode node = toNode("""
				class A {}
				function* foo(req: A) {
				  yield 5; // removing 5 fixes the problem
				  req.""");
		Collection<FollowElement> followElements = getFollowElements(node, false);
		List<AbstractElement> grammarElements = toList(map(followElements, fe -> fe.getGrammarElement()));
		assertEquals(prettyPrint(grammarElements), 3, grammarElements.size());
		assertTrue(prettyPrint(grammarElements),
				grammarElements.contains(grammarAccess.getFunctionBodyAccess().getBodyAssignment_1_0()));
		assertTrue(prettyPrint(grammarElements),
				grammarElements.contains(grammarAccess.getRootStatementAccess().getAlternatives()));
	}

	@Test
	public void testNoModifiers_GH39_strict() {
		ICompositeNode node = toNode("""
				class A {}
				function* foo(req: A) {
				  yield 5; // removing 5 fixes the problem
				  req.""");
		Collection<FollowElement> followElements = getFollowElements(node, true);
		List<AbstractElement> grammarElements = toList(map(followElements, fe -> fe.getGrammarElement()));
		assertEquals(prettyPrint(grammarElements), 2, grammarElements.size());
		assertTrue(prettyPrint(grammarElements),
				grammarElements.contains(grammarAccess.getFunctionBodyAccess().getBodyAssignment_1_0()));
		assertTrue(prettyPrint(grammarElements),
				grammarElements.contains(grammarAccess.getRootStatementAccess().getAlternatives()));
	}

	@Test
	public void testPublicModifier_GH39() {
		ICompositeNode node = toNode("""
				class A {}
				public function* foo(req: A) {
				  yield 5; // removing 5 fixes the problem
				  req.""");
		Collection<FollowElement> followElements = getFollowElements(node, false);
		List<AbstractElement> grammarElements = toList(map(followElements, fe -> fe.getGrammarElement()));
		assertEquals(prettyPrint(grammarElements), 3, grammarElements.size());
		assertTrue(prettyPrint(grammarElements),
				grammarElements.contains(grammarAccess.getFunctionBodyAccess().getBodyAssignment_1_0()));
		assertTrue(prettyPrint(grammarElements),
				grammarElements.contains(grammarAccess.getRootStatementAccess().getAlternatives()));
		assertTrue(prettyPrint(grammarElements),
				grammarElements.contains(grammarAccess.getScriptAccess().getAlternatives_2()));
	}

	@Test
	public void testExportModifier_GH39() {
		ICompositeNode node = toNode("""
				class A {}
				public function* foo(req: A) {
				  yield 5; // removing 5 fixes the problem
				  req.""");
		Collection<FollowElement> followElements = getFollowElements(node, false);
		List<AbstractElement> grammarElements = toList(map(followElements, fe -> fe.getGrammarElement()));
		assertEquals(prettyPrint(grammarElements), 3, grammarElements.size());
		assertTrue(prettyPrint(grammarElements),
				grammarElements.contains(grammarAccess.getFunctionBodyAccess().getBodyAssignment_1_0()));
		assertTrue(prettyPrint(grammarElements),
				grammarElements.contains(grammarAccess.getRootStatementAccess().getAlternatives()));
		assertTrue(prettyPrint(grammarElements),
				grammarElements.contains(grammarAccess.getScriptAccess().getAlternatives_2()));
	}

	@Test
	public void testExportPublicModifier_GH39_nonStrict() {
		ICompositeNode node = toNode("""
				class A {}
				export public function* foo(req: A) {
				  yield 5; // removing 5 fixes the problem
				  req.""");
		Collection<FollowElement> followElements = getFollowElements(node, false);
		List<AbstractElement> grammarElements = toList(map(followElements, fe -> fe.getGrammarElement()));
		assertEquals(prettyPrint(grammarElements), 2, grammarElements.size());
		assertTrue(prettyPrint(grammarElements),
				grammarElements.contains(grammarAccess.getFunctionBodyAccess().getBodyAssignment_1_0()));
		assertTrue(prettyPrint(grammarElements),
				grammarElements.contains(grammarAccess.getScriptAccess().getAlternatives_2()));
	}

	@Test
	public void testExportPublicModifier_GH39_strict() {
		ICompositeNode node = toNode("""
				class A {}
				export public function* foo(req: A) {
				  yield 5; // removing 5 fixes the problem
				  req.""");
		Collection<FollowElement> followElements = getFollowElements(node, true);
		List<AbstractElement> grammarElements = toList(map(followElements, fe -> fe.getGrammarElement()));
		assertEquals(prettyPrint(grammarElements), 1, grammarElements.size());
		assertTrue(prettyPrint(grammarElements),
				grammarElements.contains(grammarAccess.getFunctionBodyAccess().getBodyAssignment_1_0()));
	}

	@Test
	public void testExportPublicModifier_GH39_asi() {
		ICompositeNode node = toNode("""
				class A {}
				export public function* foo(req: A) {
				  yield 5
				  req.""");
		Collection<FollowElement> followElements = getFollowElements(node, true);
		List<AbstractElement> grammarElements = toList(map(followElements, fe -> fe.getGrammarElement()));
		assertEquals(prettyPrint(grammarElements), 1, grammarElements.size());
		assertTrue(prettyPrint(grammarElements),
				grammarElements.contains(grammarAccess.getFunctionBodyAccess().getBodyAssignment_1_0()));
	}

	@Test
	public void testNoModifiersFollowUp_01() {
		ICompositeNode node = toNode("""
				class A {}
				function* foo(req: A) {
				  yield 5; // removing 5 fixes the problem
				  req.""");
		FollowElement followElement = filter(getFollowElements(node, true),
				fe -> fe.getGrammarElement() == grammarAccess.getFunctionBodyAccess().getBodyAssignment_1_0())
						.iterator().next();
		assertNotNull(followElement);
		Collection<FollowElement> followElements = getFollowElements(followElement);

		List<AbstractElement> grammarElements = toList(map(followElements, fe -> fe.getGrammarElement()));
		assertEquals(prettyPrint(grammarElements), 1, grammarElements.size());
		assertTrue(prettyPrint(grammarElements),
				grammarElements.contains(grammarAccess.getRootStatementAccess().getAlternatives()));
	}

	@Test
	public void testNoModifiersFollowUp_02() {
		ICompositeNode node = toNode("""
				class A {}
				function* foo(req: A) {
				  yield 5; // removing 5 fixes the problem
				  req.""");
		FollowElement followElement = filter(getFollowElements(node, true),
				fe -> fe.getGrammarElement() == grammarAccess.getRootStatementAccess().getAlternatives())
						.iterator().next();
		assertNotNull(followElement);
		Collection<FollowElement> followElements = getFollowElements(followElement);

		List<AbstractElement> grammarElements = toList(map(followElements, fe -> fe.getGrammarElement()));
		assertEquals(prettyPrint(grammarElements), 2, grammarElements.size());
		assertTrue(prettyPrint(grammarElements),
				grammarElements.contains(grammarAccess.getFunctionBodyAccess().getBodyAssignment_1_0()));
		assertTrue(prettyPrint(grammarElements),
				grammarElements.contains(grammarAccess.getRootStatementAccess().getAlternatives()));
	}

	@Test
	public void testNoModifiersFollowUp_03() {
		ICompositeNode node = toNode("""
				class A {}
				function* foo(req: A) {
				  yield 5; // removing 5 fixes the problem
				  req.""");
		FollowElement followElement = filter(getFollowElements(node, false),
				fe -> fe.getGrammarElement() == grammarAccess.getRootStatementAccess().getAlternatives())
						.iterator().next();
		assertNotNull(followElement);
		Collection<FollowElement> followElements = getFollowElements(followElement);

		List<AbstractElement> grammarElements = toList(map(followElements, fe -> fe.getGrammarElement()));
		assertEquals(prettyPrint(grammarElements), 2, grammarElements.size());
	}

	@Test
	public void testExportPublicFollowUp_01() {
		ICompositeNode node = toNode("""
				class A {}
				export public function* foo(req: A) {
				  yield 5; // removing 5 fixes the problem
				  req.""");
		FollowElement followElement = filter(getFollowElements(node, false),
				fe -> fe.getGrammarElement() == grammarAccess.getFunctionBodyAccess().getBodyAssignment_1_0())
						.iterator().next();
		assertNotNull(followElement);
		Collection<FollowElement> followElements = getFollowElements(followElement);

		List<AbstractElement> grammarElements = toList(map(followElements, fe -> fe.getGrammarElement()));
		assertEquals(prettyPrint(grammarElements), 1, grammarElements.size());
		assertTrue(prettyPrint(grammarElements),
				grammarElements.contains(grammarAccess.getRootStatementAccess().getAlternatives()));
	}

	@Test
	public void testExportPublicFollowUp_02() {
		ICompositeNode node = toNode("""
				class A {}
				export public function* foo(req: A) {
				  yield 5; // removing 5 fixes the problem
				  req.""");
		FollowElement followElement = filter(getFollowElements(node, false),
				fe -> fe.getGrammarElement() == grammarAccess.getScriptAccess().getAlternatives_2())
						.iterator().next();
		assertNotNull(followElement);
		Collection<FollowElement> followElements = getFollowElements(followElement);

		List<AbstractElement> grammarElements = toList(map(followElements, fe -> fe.getGrammarElement()));
		assertEquals(prettyPrint(grammarElements), 0, grammarElements.size());
	}

	public void assertSubsequentFollowElementsInMemberExpression(FollowElement followElement) {
		Collection<FollowElement> followElements = getFollowElements(followElement);

		List<AbstractElement> grammarElements = toList(map(followElements, fe -> fe.getGrammarElement()));
		assertEquals(prettyPrint(grammarElements), 2, grammarElements.size());
		for (FollowElement fe : followElements) {
			assertEquals(1, fe.getLookAhead());
		}
		assertTrue(prettyPrint(grammarElements), grammarElements.contains(
				grammarAccess.getParameterizedPropertyAccessExpressionTailAccess()
						.getConcreteTypeArgumentsParserRuleCall_1()));
		assertTrue(prettyPrint(grammarElements),
				grammarElements.contains(grammarAccess.getIdentifierNameAccess().getAlternatives()));
	}

	@Test
	public void testAfterDot_nl() {
		ICompositeNode node = toNode("a.\n\n");
		Collection<FollowElement> followElements = getFollowElements(node, 0, 3, true);
		List<AbstractElement> grammarElements = toList(map(followElements, fe -> fe.getGrammarElement()));
		assertEquals(prettyPrint(grammarElements), 1, grammarElements.size());
		assertEquals(4, followElements.iterator().next().getLookAhead());
		assertTrue(prettyPrint(grammarElements),
				grammarElements.contains(grammarAccess.getRootStatementAccess().getAlternatives()));
	}

	@Test
	public void testAfterDot_nl_NextBatchOfFollowElements() {
		ICompositeNode node = toNode("a.\n\n");
		FollowElement followElement = getFollowElements(node, 0, 3, true).iterator().next();
		assertSubsequentFollowElementsInMemberExpression(followElement);
	}

	@Test
	public void testAfterDot_nl_comment() {
		ICompositeNode node = toNode("a./*\n*/\n");
		Collection<FollowElement> followElements = getFollowElements(node, "a./*\n*/");
		List<AbstractElement> grammarElements = toList(map(followElements, fe -> fe.getGrammarElement()));
		assertEquals(prettyPrint(grammarElements), 1, grammarElements.size());
		assertEquals(4, followElements.iterator().next().getLookAhead());
		assertTrue(prettyPrint(grammarElements),
				grammarElements.contains(grammarAccess.getRootStatementAccess().getAlternatives()));
	}

	@Test
	public void testAfterDot_nl_comment_NextBatchOfFollowElements() {
		ICompositeNode node = toNode("a./*\n*/\n");
		FollowElement followElement = getFollowElements(node, "a./*\n*/").iterator().next();
		assertSubsequentFollowElementsInMemberExpression(followElement);
	}

	@Test
	public void testBinaryOp_01() {
		ICompositeNode node = toNode("1+2");
		Collection<FollowElement> followElements = getFollowElements(node, "1");
		List<AbstractElement> grammarElements = toList(map(followElements, fe -> fe.getGrammarElement()));
		assertEquals(prettyPrint(grammarElements), 19, grammarElements.size());
		assertTrue(prettyPrint(grammarElements),
				grammarElements.contains(grammarAccess.getPostfixExpressionAccess().getGroup_1()));
		assertFalse(prettyPrint(grammarElements),
				grammarElements.contains(grammarAccess.getScriptAccess().getScriptElementsAssignment_2_1()));
	}

	@Test
	public void testBinaryOp_02() {
		ICompositeNode node = toNode("1\n+2");
		Collection<FollowElement> followElements = getFollowElements(node, "1");
		List<AbstractElement> grammarElements = toList(map(followElements, fe -> fe.getGrammarElement()));
		assertEquals(prettyPrint(grammarElements), 19, grammarElements.size());
		assertTrue(prettyPrint(grammarElements),
				grammarElements.contains(grammarAccess.getPostfixExpressionAccess().getGroup_1()));
		assertFalse(prettyPrint(grammarElements),
				grammarElements.contains(grammarAccess.getScriptAccess().getScriptElementsAssignment_2_1()));
	}

	@Test
	public void testBinaryOp_03() {
		ICompositeNode node = toNode("1\n+2");
		Collection<FollowElement> followElements = getFollowElements(node, 0, 2, true);
		List<AbstractElement> grammarElements = toList(map(followElements, fe -> fe.getGrammarElement()));
		assertEquals(prettyPrint(grammarElements), 19, grammarElements.size());
		assertTrue(prettyPrint(grammarElements),
				grammarElements.contains(grammarAccess.getScriptAccess().getAlternatives_2()));
		assertFalse(prettyPrint(grammarElements),
				grammarElements.contains(grammarAccess.getPostfixExpressionAccess().getGroup_1()));
	}

	@Test
	public void testBinaryOp_04() {
		ICompositeNode node = toNode("1/*\n*/+2");
		Collection<FollowElement> followElements = getFollowElements(node, 0, 6, true);
		List<AbstractElement> grammarElements = toList(map(followElements, fe -> fe.getGrammarElement()));
		assertEquals(prettyPrint(grammarElements), 19, grammarElements.size());
		assertTrue(prettyPrint(grammarElements),
				grammarElements.contains(grammarAccess.getScriptAccess().getAlternatives_2()));
		assertFalse(prettyPrint(grammarElements),
				grammarElements.contains(grammarAccess.getPostfixExpressionAccess().getGroup_1()));
	}

	@Test
	public void testUnaryOp_01() {
		ICompositeNode node = toNode("1\n++i");
		Collection<FollowElement> followElements = getFollowElements(node, "1");
		List<AbstractElement> grammarElements = toList(map(followElements, fe -> fe.getGrammarElement()));
		assertEquals(prettyPrint(grammarElements), 19, grammarElements.size());
		assertFalse(prettyPrint(grammarElements),
				grammarElements.contains(grammarAccess.getScriptAccess().getScriptElementsAssignment_2_1()));
		assertTrue(prettyPrint(grammarElements),
				grammarElements.contains(grammarAccess.getPostfixExpressionAccess().getGroup_1()));
	}

	@Test
	public void testUnaryOp_02() {
		ICompositeNode node = toNode("1\n++i");
		Collection<FollowElement> followElements = getFollowElements(node, "1\n");
		List<AbstractElement> grammarElements = toList(map(followElements, fe -> fe.getGrammarElement()));

		assertEquals(prettyPrint(grammarElements), 19, grammarElements.size());

		assertTrue(prettyPrint(grammarElements),
				grammarElements.contains(grammarAccess.getScriptAccess().getAlternatives_2()));
		assertFalse(prettyPrint(grammarElements),
				grammarElements.contains(grammarAccess.getPostfixExpressionAccess().getGroup_1()));
	}

	@Test
	public void testUnaryOp_03() {
		ICompositeNode node = toNode("1;++i");
		Collection<FollowElement> followElements = getFollowElements(node, "1");
		List<AbstractElement> grammarElements = toList(map(followElements, fe -> fe.getGrammarElement()));
		assertEquals(prettyPrint(grammarElements), 19, grammarElements.size());
		assertFalse(prettyPrint(grammarElements),
				grammarElements.contains(grammarAccess.getScriptAccess().getScriptElementsAssignment_2_1()));
		assertTrue(prettyPrint(grammarElements),
				grammarElements.contains(grammarAccess.getPostfixExpressionAccess().getGroup_1()));
	}

	@Test
	public void testUnaryOp_04() {
		ICompositeNode node = toNode("1;++i");
		Collection<FollowElement> followElements = getFollowElements(node, "1;");
		List<AbstractElement> grammarElements = toList(map(followElements, fe -> fe.getGrammarElement()));
		assertEquals(prettyPrint(grammarElements), 1, grammarElements.size());
		assertTrue(prettyPrint(grammarElements),
				grammarElements.contains(grammarAccess.getScriptAccess().getAlternatives_2()));
	}

	@Test
	public void testUnaryOp_05() {
		ICompositeNode node = toNode("1/*\n*/++i");
		Collection<FollowElement> followElements = getFollowElements(node, "1/*\n*/");
		List<AbstractElement> grammarElements = toList(map(followElements, fe -> fe.getGrammarElement()));
		assertEquals(prettyPrint(grammarElements), 19, grammarElements.size());
		assertTrue(prettyPrint(grammarElements),
				grammarElements.contains(grammarAccess.getScriptAccess().getAlternatives_2()));
		assertFalse(prettyPrint(grammarElements),
				grammarElements.contains(grammarAccess.getPostfixExpressionAccess().getGroup_1()));
	}

	private ICompositeNode toNode(CharSequence s) {
		try {
			return NodeModelUtils.getNode(parseHelper.parse(s)).getRootNode();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
