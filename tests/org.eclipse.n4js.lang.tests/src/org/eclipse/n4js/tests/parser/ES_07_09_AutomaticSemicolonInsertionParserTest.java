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
package org.eclipse.n4js.tests.parser;

import static org.eclipse.xtext.xbase.lib.IterableExtensions.last;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.size;

import org.eclipse.n4js.n4JS.Block;
import org.eclipse.n4js.n4JS.ExpressionStatement;
import org.eclipse.n4js.n4JS.IntLiteral;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.ScriptElement;
import org.eclipse.n4js.n4JS.UnaryExpression;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.n4JS.VariableStatement;
import org.eclipse.n4js.parser.InternalSemicolonInjectingParser;
import org.eclipse.n4js.tooling.N4JSDocumentationProvider;
import org.eclipse.xtext.formatting.IWhitespaceInformationProvider;
import org.eclipse.xtext.nodemodel.BidiIterator;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.ILeafNode;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.impl.LeafNodeWithSyntaxError;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProviderWithoutStructureValidation.class)
public class ES_07_09_AutomaticSemicolonInsertionParserTest extends AbstractParserTest {

	@Inject
	private IWhitespaceInformationProvider wp;
	@Inject
	private N4JSDocumentationProvider documenationProvider;

	public void hasChildren(Script p, int count) {
		assertEquals(count, p.getScriptElements().size());
	}

	@Test
	public void testSimpleExpression() {
		parseESSuccessfully("1");
	}

	@Test
	public void testPostfixExpression_01() {
		parseESSuccessfully("1++");
	}

	@Test
	public void testPostfixExpression_02() {
		Script parseResult = parseESSuccessfully("1++\n" +
				"1");
		hasChildren(parseResult, 2);
	}

	@Test
	public void testPrefixExpression_01() {
		parseESSuccessfully("++1");
	}

	@Test
	public void testPrefixExpression_02() {
		Script parseResult = parseESSuccessfully("""
				1
				++1
				""");
		hasChildren(parseResult, 2);
	}

	@Test
	public void testInvalidBinaryExpression() {
		parseESWithError("1+");
	}

	@Test
	public void testInvalidBinaryExpressionWithLineBreak() {
		parseESWithError("1+\n");
	}

	@Test
	public void testInvalidBinaryExpressionInBlock() {
		parseESWithError("{1+}");
	}

	@Test
	public void testSpecExample_01() {
		parseESWithError("{ 1 2 } 3");
	}

	@Test
	public void testSpecExample_02() {
		Script parseResult = parseESSuccessfully("""
				{ 1
				2 } 3
				""");
		hasChildren(parseResult, 2);
	}

	@Test
	public void testSpecExample_03() {
		parseESWithError("for (a; b\n" +
				")");
	}

	@Test
	public void testSpecExample_04() {
		Script parseResult = parseESSuccessfully("return\n" +
				"a+b");
		hasChildren(parseResult, 2);
	}

	@Test
	public void testSpecExample_05() {
		Script parseResult = parseESSuccessfully("a=b\n" +
				"++c");
		hasChildren(parseResult, 2);
	}

	@Test
	public void testSpecExample_06() {
		parseESWithError("if (a > b)\n" +
				"else c = d");
	}

	@Test
	public void testSpecExample_07() {
		Script parseResult = parseESSuccessfully("a=b+c\n" +
				"(d + e).print()");
		hasChildren(parseResult, 1);
	}

	@Test
	public void testSpecExample_08() {
		parseESWithError("if (true) if (a > b)\n" +
				"else c = d");
	}

	@Test
	public void testSpecExample_09() {
		Script parseResult = parseESSuccessfully("a=b++\n" +
				"c");
		hasChildren(parseResult, 2);
	}

	@Test
	public void testSpecExample_10() {
		parseESSuccessfully("{ 1 }");
	}

	/*
	 * The following examples (testOneVsTwoStatements..) are similar to those described at
	 * http://lucumr.pocoo.org/2011/2/6/automatic-semicolon-insertion/ by Armin Ronacher. More information about the
	 * effects see blog entry.
	 */

	@Test
	public void testOneVsTwoStatements_01_ASI() {
		Script parseResult = parseESSuccessfully("""
				let x = 1
				[0];
				""");
		hasChildren(parseResult, 1);
	}

	@Test
	public void testOneVsTwoStatements_01_WithSem() {
		Script parseResult = parseESSuccessfully("""
				let x = 1;
				[0];
				""");
		hasChildren(parseResult, 2);
	}

	@Test
	public void testOneVsTwoStatements_02_ASI() {
		Script parseResult = parseESSuccessfully("""
				let f = function (){}
				(1)
				""");
		hasChildren(parseResult, 1);
	}

	@Test
	public void testOneVsTwoStatements_02_WithSem() {
		Script parseResult = parseESSuccessfully("""
				let f = function (){};
				(1)
				""");
		hasChildren(parseResult, 2);
	}

	@Test
	public void testOneVsTwoStatements_03_ASI() {
		Script parseResult = parseESSuccessfully("""
				1
				(1)
				""");
		hasChildren(parseResult, 1);
	}

	@Test
	public void testOneVsTwoStatements_03_WithSem() {
		Script parseResult = parseESSuccessfully("""
				1;
				(1)
				""");
		hasChildren(parseResult, 2);
	}

	@Test
	public void testReturn_01() {
		Script parseResult = parseESSuccessfully("""
				return
				1
				""");
		hasChildren(parseResult, 2);
	}

	@Test
	public void testReturn_02() {
		Script parseResult = parseESSuccessfully("""
				return /*
				*/ 1
				""");
		hasChildren(parseResult, 2);
	}

	@Test
	public void testThrow_01() {
		parseESWithError("""
				throw
				a
				""");
	}

	@Test
	public void testThrow_02() {
		parseESWithError("""
				throw /*
				*/ a
				""");
	}

	@Test
	public void testThrow_03() {
		parseESSuccessfully("""
				throw a
				""");
	}

	@Test
	public void testThrow_04() {
		parseESSuccessfully("""
				throw /* */ a
				""");
	}

	@Test
	public void testBreak_01() {
		Script parseResult = parseESSuccessfully("""
				break
				a
				""");
		hasChildren(parseResult, 2);
	}

	@Test
	public void testBreak_02() {
		Script parseResult = parseESSuccessfully("""
				break /*
				*/ a
				""");
		hasChildren(parseResult, 2);
	}

	@Test
	public void testContinue_01() {
		Script parseResult = parseESSuccessfully("""
				continue
				a
				""");
		hasChildren(parseResult, 2);
	}

	@Test
	public void testContinue_02() {
		Script parseResult = parseESSuccessfully("""
				continue /*
				*/ a
				""");
		hasChildren(parseResult, 2);
	}

	@Test
	public void testEsprima_01() {
		Script script = parseESSuccessfully("""
				{ throw error/* Multiline
				Comment */ error; }
				""");
		Block block = (Block) script.getScriptElements().get(0);
		assertEquals(2, block.getStatements().size());
	}

	@Test
	public void testEcma_01() {
		Script script = parseESSuccessfully("""
				1
				++
				++
				2
				""");
		assertEquals(2, script.getScriptElements().size());
		ExpressionStatement first = (ExpressionStatement) script.getScriptElements().get(0);
		ExpressionStatement second = (ExpressionStatement) last(script.getScriptElements());
		IntLiteral literal = (IntLiteral) first.getExpression();
		assertEquals(1, literal.getValue().intValue());
		UnaryExpression unary = (UnaryExpression) second.getExpression();
		UnaryExpression nestedUnary = (UnaryExpression) unary.getExpression();
		IntLiteral nestedLiteral = (IntLiteral) nestedUnary.getExpression();
		assertEquals(2, nestedLiteral.getValue().intValue());
	}

	@Test
	public void testIssue_518_01() {
		Script script = parseESSuccessfully("""
				var x

				x
				""");
		VariableStatement varStatement = (VariableStatement) script.getScriptElements().get(0);
		VariableDeclaration varDecl = varStatement.getVarDecl().get(0);
		assertEquals("x", varDecl.getName());
		assertNull(varDecl.getDeclaredTypeRefInAST());
		ICompositeNode node = NodeModelUtils.findActualNodeFor(script);
		assertEquals(3, size(node.getChildren())); // hidden (empty as. in 'no children, length=0'), var, x
	}

	@Test
	public void testIssue_518_01_N_01() {
		Script script = parseESSuccessfully("var x\n\nx");
		VariableStatement varStatement = (VariableStatement) script.getScriptElements().get(0);
		VariableDeclaration varDecl = varStatement.getVarDecl().get(0);
		assertEquals("x", varDecl.getName());
		assertNull(varDecl.getDeclaredTypeRefInAST());
		ICompositeNode node = NodeModelUtils.findActualNodeFor(script);
		assertEquals(3, size(node.getChildren()));// hidden (empty as. in 'no children, length=0'), var, x
	}

	@Test
	public void testIssue_518_01_N_02() {
		Script firstScript = parseESSuccessfully("var x\n\nx");
		Script secondScript = parseESSuccessfully("var x;\nx");

		ICompositeNode firstRootNode = ((XtextResource) firstScript.eResource()).getParseResult().getRootNode();
		ICompositeNode secondRootNode = ((XtextResource) secondScript.eResource()).getParseResult().getRootNode();

		assertEqualNodes(firstRootNode, secondRootNode);
	}

	@Test
	public void testIssue_518_01_N_03() {
		Script firstScript = parseESSuccessfully("export var x: any\n\nx");
		Script secondScript = parseESSuccessfully("export var x: any;\nx");

		ICompositeNode firstRootNode = ((XtextResource) firstScript.eResource()).getParseResult().getRootNode();
		ICompositeNode secondRootNode = ((XtextResource) secondScript.eResource()).getParseResult().getRootNode();

		assertEqualNodes(firstRootNode, secondRootNode);
	}

	@Test
	public void testIssue_518_01_N_04() {
		Script firstScript = parseESSuccessfully("export var x: any\n\nx");
		Script secondScript = parseESSuccessfully("export var x: any;\nx");

		ICompositeNode firstRootNode = ((XtextResource) firstScript.eResource()).getParseResult().getRootNode();
		ICompositeNode secondRootNode = ((XtextResource) secondScript.eResource()).getParseResult().getRootNode();

		assertEqualNodes(firstRootNode, secondRootNode);
	}

	@Test
	public void testIssue_518_01_N_05() {
		Script firstScript = parseESSuccessfully("export var x\n\nx");
		Script secondScript = parseESSuccessfully("export var x;\nx");

		ICompositeNode firstRootNode = ((XtextResource) firstScript.eResource()).getParseResult().getRootNode();
		ICompositeNode secondRootNode = ((XtextResource) secondScript.eResource()).getParseResult().getRootNode();

		assertEqualNodes(firstRootNode, secondRootNode);
	}

	@Test
	public void testIssue_518_01_N_06() {
		Script firstScript = parseESSuccessfully("function f() { var x: any\n\nx\n");
		Script secondScript = parseESSuccessfully("function f() { var x: any;\nx\n");

		ICompositeNode firstRootNode = ((XtextResource) firstScript.eResource()).getParseResult().getRootNode();
		ICompositeNode secondRootNode = ((XtextResource) secondScript.eResource()).getParseResult().getRootNode();

		assertEqualNodes(firstRootNode, secondRootNode);
	}

	@Test
	public void testIssue_518_01_N_07() {
		Script firstScript = parseESSuccessfully("function f() { var x\n\nx\n");
		Script secondScript = parseESSuccessfully("function f() { var x;\nx\n");

		ICompositeNode firstRootNode = ((XtextResource) firstScript.eResource()).getParseResult().getRootNode();
		ICompositeNode secondRootNode = ((XtextResource) secondScript.eResource()).getParseResult().getRootNode();

		assertEqualNodes(firstRootNode, secondRootNode);
	}

	@Test
	public void testIssue_518_01_N_08() {
		Script firstScript = parseESSuccessfully("function * f() { var x: any\n\nx\n");
		Script secondScript = parseESSuccessfully("function * f() { var x: any;\nx\n");

		ICompositeNode firstRootNode = ((XtextResource) firstScript.eResource()).getParseResult().getRootNode();
		ICompositeNode secondRootNode = ((XtextResource) secondScript.eResource()).getParseResult().getRootNode();

		assertEqualNodes(firstRootNode, secondRootNode);
	}

	@Test
	public void testIssue_518_01_N_09() {
		Script firstScript = parseESSuccessfully("function * f() { var x\n\nx\n");
		Script secondScript = parseESSuccessfully("function * f() { var x;\nx\n");

		ICompositeNode firstRootNode = ((XtextResource) firstScript.eResource()).getParseResult().getRootNode();
		ICompositeNode secondRootNode = ((XtextResource) secondScript.eResource()).getParseResult().getRootNode();

		assertEqualNodes(firstRootNode, secondRootNode);
	}

	@Test
	public void testIssue_518_01_R_01() {
		Script script = parseESSuccessfully("var x\r\rx");
		VariableStatement varStatement = (VariableStatement) script.getScriptElements().get(0);
		VariableDeclaration varDecl = varStatement.getVarDecl().get(0);
		assertEquals("x", varDecl.getName());
		assertNull(varDecl.getDeclaredTypeRefInAST());
		ICompositeNode node = NodeModelUtils.findActualNodeFor(script);
		assertEquals(3, size(node.getChildren())); // hidden (empty as. in 'no children, length=0'), var, x
	}

	@Test
	public void testIssue_518_01_R_02() {
		Script firstScript = parseESSuccessfully("var x\r\rx");
		Script secondScript = parseESSuccessfully("var x;\rx");

		ICompositeNode firstRootNode = ((XtextResource) firstScript.eResource()).getParseResult().getRootNode();
		ICompositeNode secondRootNode = ((XtextResource) secondScript.eResource()).getParseResult().getRootNode();

		assertEqualNodes(firstRootNode, secondRootNode);
	}

	@Test
	public void testIssue_518_01_RN_02() {
		Script firstScript = parseESSuccessfully("var x\r\n\r\nx\r");
		Script secondScript = parseESSuccessfully("var x;\r\nx\r");

		ICompositeNode firstRootNode = ((XtextResource) firstScript.eResource()).getParseResult().getRootNode();
		ICompositeNode secondRootNode = ((XtextResource) secondScript.eResource()).getParseResult().getRootNode();

		assertEqualNodes(firstRootNode, secondRootNode);
	}

	void assertEqualNodes(Object first, Object second) {
		if (first instanceof ICompositeNode && second instanceof ICompositeNode) {
			assertEqualNodes((ICompositeNode) first, (ICompositeNode) second);
		} else if (first instanceof ILeafNode && second instanceof ILeafNode) {
			assertEqualNodes((ILeafNode) first, (ILeafNode) second);
		}
	}

	void assertEqualNodes(ICompositeNode first, ICompositeNode second) {
		assertEquals(first.getGrammarElement(), second.getGrammarElement());
		BidiIterator<INode> firstIter = first.getChildren().iterator();
		BidiIterator<INode> secondIter = second.getChildren().iterator();
		while (firstIter.hasNext()) {
			INode firstChild = firstIter.next();
			INode secondChild = secondIter.next();
			assertEqualNodes(firstChild, secondChild);
		}
		assertFalse(secondIter.hasNext());
	}

	void assertEqualNodes(ILeafNode first, ILeafNode second) {
		assertEquals(first.getGrammarElement(), second.getGrammarElement());
		assertEquals(first.isHidden(), second.isHidden());
	}

	@Test
	public void testIssue_518_01_RN_01() {
		Script script = parseESSuccessfully("var x\r\n\r\nx\r");
		VariableStatement varStatement = (VariableStatement) script.getScriptElements().get(0);
		VariableDeclaration varDecl = varStatement.getVarDecl().get(0);
		assertEquals("x", varDecl.getName());
		assertNull(varDecl.getDeclaredTypeRefInAST());
		ICompositeNode node = NodeModelUtils.findActualNodeFor(script);
		assertEquals(3, size(node.getChildren())); // hidden (empty as. in 'no children, length=0'), var, x;
	}

	@Test
	public void testIssue_518_01_dynamicNewLine() {
		Script sX = parseESSuccessfully("var a;");
		String newLine = wp.getLineSeparatorInformation(sX.eResource().getURI()).getLineSeparator();
		Script script = parseESSuccessfully("var x" + newLine + newLine + "x" + newLine);
		VariableStatement varStatement = (VariableStatement) script.getScriptElements().get(0);
		VariableDeclaration varDecl = varStatement.getVarDecl().get(0);
		assertEquals("x", varDecl.getName());
		assertNull(varDecl.getDeclaredTypeRefInAST());
		ICompositeNode node = NodeModelUtils.findActualNodeFor(script);
		assertEquals(3, size(node.getChildren())); // hidden (empty as. in 'no children, length=0'), var, x;
	}

	@Test
	public void testIssue_518_02() {
		Script script = parseESSuccessfully("""
				var x;

				x
				""");
		VariableStatement varStatement = (VariableStatement) script.getScriptElements().get(0);
		VariableDeclaration varDecl = varStatement.getVarDecl().get(0);
		assertEquals("x", varDecl.getName());
		assertNull(varDecl.getDeclaredTypeRefInAST());
		ICompositeNode node = NodeModelUtils.findActualNodeFor(script);
		assertEquals(3, size(node.getChildren())); // hidden (empty as. in 'no children, length=0'), var, x
	}

	/**
	 * ASI must not swallow jsdoc-comments. Problem occured for ML-comments on same line as. ASI. GHOLD-103
	 */
	@Test
	public void testJsdoc_Directive_sameLine() {
		Script script = parseESSuccessfully("""
				"use strict"/**
				docu of C */
				class C {}
				""");
		assertEquals(2, script.getScriptElements().size());
		String docu = documenationProvider.getDocumentation(script.getScriptElements().get(1));
		assertEquals("docu of C", docu);
	}

	/**
	 * Here no conflict between jsdoc and ASI are expected, since the ML-comment starts in a new line. GHOLD-103
	 */
	@Test
	public void testJsdoc_Directive_differentLine() {
		Script script = parseESSuccessfully("""
				"use strict"
				/**
				docu of C */
				class C {}
				""");
		assertEquals(2, script.getScriptElements().size());
		String docu = documenationProvider.getDocumentation(script.getScriptElements().get(1));
		assertEquals("docu of C", docu);
	}

	/**
	 * Single line comments should not contribute to documentation. GHOLD-103
	 */
	@Test
	public void testNonJsdoc_Directive_differentLine() {
		Script script = parseESSuccessfully("""
				"use strict"
				//**docu of C */
				class C {}
				""");
		assertEquals(2, script.getScriptElements().size());
		String docu = documenationProvider.getDocumentation(script.getScriptElements().get(1));
		assertNull(docu);
	}

	/**
	 * Single line comments should not contribute to documentation. GHOLD-103
	 */
	@Test
	public void testNonJsdoc_Directive_sameLine() {
		Script script = parseESSuccessfully("""
				"use strict"//**docu of C */
				class C {}
				""");
		assertEquals(2, script.getScriptElements().size());
		String docu = documenationProvider.getDocumentation(script.getScriptElements().get(1));
		assertNull(docu);
	}

	/**
	 * <b>This test will notify programmers, if the ASI-implementation is changed.</b> At the time of writing, ASI
	 * replaced a HiddenLeaf node of MultiLine-comments by a {@code LeafNodeWithSyntaxError}. This behavior required
	 * some workarounds. If this test fails, then the above assumed behavior changed and YOU - as. programmer - should
	 * look at:
	 * <ul>
	 * <li>N4JSDocumentationsProvider</li>
	 * <li>N4JSOrganizeImport</li>;
	 * <li>N4JSOrganizeImportsHandler</li>;
	 * </ul>
	 * GHOLD-103
	 */
	@Test
	public void testJsdoc_InvestigateChangedASIbehaviour() {
		Script script = parseESSuccessfully("""
				"use strict"/**
				docu of C */
				class C {}
				""");

		assertEquals(2, script.getScriptElements().size());

		// Find ASI-Nodes:
		Iterable<LeafNodeWithSyntaxError> leafNodesWithSyntaxError = IterableExtensions
				.filter(NodeModelUtils.getNode(script).getLeafNodes(), LeafNodeWithSyntaxError.class);
		assertEquals("Only one node of type LeafNodeWithSyntaxError expected.", 1, size(leafNodesWithSyntaxError));
		LeafNodeWithSyntaxError theLNWSE = leafNodesWithSyntaxError.iterator().next();
		assertEquals("", InternalSemicolonInjectingParser.SEMICOLON_INSERTED,
				theLNWSE.getSyntaxErrorMessage().getIssueCode());

		String completeText = theLNWSE.getText().trim();

		assertEquals("""
				/**
				docu of C */
				""".toString().trim(), completeText);

	}

	/**
	 * Compare-test without ASI. C.f. {@link #testJsdoc_InvestigateChangedASIbehaviour()} GHOLD-103
	 */
	@Test
	public void testJsdoc_InvestigateChangedASIbehaviour_Comparison() {
		Script script = parseESSuccessfully("""
				"use strict";/**
				docu of C */
				class C {}
				""");

		assertEquals(2, script.getScriptElements().size());

		ScriptElement classElement = script.getScriptElements().get(1);
		ICompositeNode completeNode = NodeModelUtils.getNode(classElement);
		StringBuffer collectedHiddenText = new StringBuffer();
		boolean doBreak = false;
		for (ILeafNode leaf : completeNode.getLeafNodes()) {
			if (!doBreak) {
				if (leaf.isHidden()) {
					collectedHiddenText.append(leaf.getText());
				} else {
					doBreak = true;
				}
			}
		}

		String completeText = collectedHiddenText.toString().trim();

		assertEquals("""
				/**
				docu of C */
				""".toString().trim(), completeText);

	}

}
