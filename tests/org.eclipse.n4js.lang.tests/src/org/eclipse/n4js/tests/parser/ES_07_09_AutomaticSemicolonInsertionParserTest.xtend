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
package org.eclipse.n4js.tests.parser

import com.google.inject.Inject
import org.eclipse.n4js.n4JS.Block
import org.eclipse.n4js.n4JS.ExpressionStatement
import org.eclipse.n4js.n4JS.IntLiteral
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.n4JS.UnaryExpression
import org.eclipse.n4js.n4JS.VariableStatement
import org.eclipse.xtext.formatting.IWhitespaceInformationProvider
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.nodemodel.ICompositeNode
import org.eclipse.xtext.nodemodel.ILeafNode
import org.eclipse.xtext.nodemodel.util.NodeModelUtils
import org.eclipse.xtext.resource.XtextResource
import org.junit.Test
import org.junit.runner.RunWith
import org.eclipse.n4js.documentation.N4JSDocumentationProvider
import org.eclipse.xtext.nodemodel.impl.LeafNodeWithSyntaxError
import org.eclipse.n4js.parser.InternalSemicolonInjectingParser

@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProviderWithoutStructureValidation)
class ES_07_09_AutomaticSemicolonInsertionParserTest extends AbstractParserTest {

	@Inject
	private IWhitespaceInformationProvider wp
	@Inject
	private N4JSDocumentationProvider documenationProvider

	def void hasChildren(Script p, int count) {
		assertEquals(count, p.scriptElements.size)
	}

	@Test
	def void testSimpleExpression() {
		"1+2".parseSuccessfully
	}
	@Test
	def void testPostfixExpression_01() {
		parseSuccessfully("1++");
	}
	@Test
	def void testPostfixExpression_02() {
		val parseResult = parseSuccessfully("1++\n" +
				"1");
		parseResult.hasChildren(2)
	}
	@Test
	def void testPrefixExpression_01() {
		"++1".parseSuccessfully
	}
	@Test
	def void testPrefixExpression_02() {
		val parseResult = '''
			1
			++1
		'''.parseSuccessfully
		parseResult.hasChildren(2)
	}
	@Test
	def void testInvalidBinaryExpression() {
		parseWithError("1+");
	}
	@Test
	def void testInvalidBinaryExpressionWithLineBreak() {
		parseWithError("1+\n");
	}
	@Test
	def void testInvalidBinaryExpressionInBlock() {
		parseWithError("{1+}");
	}

	@Test
	def void testSpecExample_01() {
		parseWithError("{ 1 2 } 3");
	}
	@Test
	def void testSpecExample_02() {
		val parseResult = '''
			{ 1
			2 } 3
		'''.parseSuccessfully
		parseResult.hasChildren(2)
	}
	@Test
	def void testSpecExample_03() {
		parseWithError("for (a; b\n" +
				")");
	}
	@Test
	def void testSpecExample_04() {
		val parseResult = parseSuccessfully("return\n" +
				"a+b");
		parseResult.hasChildren(2)
	}
	@Test
	def void testSpecExample_05() {
		val parseResult = parseSuccessfully("a=b\n" +
				"++c");
		parseResult.hasChildren(2)
	}
	@Test
	def void testSpecExample_06() {
		parseWithError("if (a > b)\n" +
				"else c = d");
	}
	@Test
	def void testSpecExample_07() {
		val parseResult = parseSuccessfully("a=b+c\n" +
				"(d + e).print()");
		parseResult.hasChildren(1)
	}
	@Test
	def void testSpecExample_08() {
		parseWithError("if (true) if (a > b)\n" +
				"else c = d");
	}
	@Test
	def void testSpecExample_09() {
		val parseResult = parseSuccessfully("a=b++\n" +
				"c");
		parseResult.hasChildren(2)
	}
	@Test
	def void testSpecExample_10() {
		parseSuccessfully("{ 1 }");
	}

	/*
	 * http://lucumr.pocoo.org/2011/2/6/automatic-semicolon-insertion/
	 *
	 * Excerpt regarding testBlogExample_01-03:
	 *
	 * Like Python, JavaScript has array literals ([1, 2, 3, 4]) and uses a very similar syntax to access items
	 * of objects and arrays (foo[index]). Unfortunately that particular little pseudo-ambiguity becomes a problem
	 * when you forget to place semicolons. Take the following piece of JavaScript code as example:
	 *
	 *     var name = "World"
	 *     ["Hello", "Goodbye"].forEach(function(value) {
	 *       document.write(value + " " + name + "<br>")
	 *     })
	 *
	 * That is not a syntax error, but it will fail with an odd error. Why is that? The problem is that JavaScript
	 * will insert semicolons after the document.write() call and after the .forEach() call, but not before the array
	 * literal. In fact, it will attempt to use the array literal as indexer operator to the string from the line before.
	 *
	 * (c) Copyright 2014 by Armin Ronacher.
	 */
	@Test
	def void testBlogExample_01() {
		val parseResult = parseSuccessfully("var name = \"World\"\n" +
				"[\"Hello\", \"Goodbye\"].forEach(function(value) {\n" +
				"  document.write(value + \" \" + name + \"<br>\")\n" +
				"})");
		parseResult.hasChildren(1)
	}
	@Test
	def void testBlogExample_02() {
		val parseResult = parseSuccessfully("var name = \"World\";\n" +
				"[\"Hello\", \"Goodbye\"].forEach(function(value) {\n" +
				"  document.write(value + \" \" + name + \"<br>\")\n" +
				"})");
		parseResult.hasChildren(2)
	}
	@Test
	def void testBlogExample_03() {
		parseSuccessfully("var name = \"World\";/*\n*/" +
				"[\"Hello\", \"Goodbye\"].forEach(function(value) {\n" +
				"  document.write(value + \" \" + name + \"<br>\")\n" +
				"})");
	}

	@Test
	def void testBlogExample_04() {
		parseSuccessfully("namespace.makeCounter = function() {\n" +
				"  var counter = 0\n" +
				"  return function() {\n" +
				"    return counter++\n" +
				"  }\n" +
				"}\n" +
				"(function() {\n" +
				"  namespace.exportedObject = function() {\n" +
				"    1+2\n" +
				"  }\n" +
				"})()");
	}
	@Test
	def void testBlogExample_05() {
		val parseResult = parseSuccessfully("/* this works */\n" +
				"var foo = 1 + 2\n" +
				"something.method(foo) + 42");
		parseResult.hasChildren(2)
	}
	@Test
	def void testBlogExample_06() {
		val parseResult = parseSuccessfully("/* this does not work, will try to call 2(...) */\n" +
				"var foo = 1 + 2\n" +
				"(something.method(foo) + 42).print()");
		parseResult.hasChildren(1)
	}
	@Test
	def void testBlogExample_07() {
		val parseResult = parseSuccessfully("var x=function(){}\n" +
				"var y=function(){}");
		parseResult.hasChildren(2)
	}
	@Test
	def void testBlogExample_08() {
		parseWithError("var x=function(){}var y=function(){}");
	}

	@Test
	def void testReturn_01() {
		val parseResult = '''
			return
			1
		'''.parseSuccessfully
		parseResult.hasChildren(2)
	}

	@Test
	def void testReturn_02() {
		val parseResult = '''
			return /*
			*/ 1
		'''.parseSuccessfully
		parseResult.hasChildren(2)
	}

	@Test
	def void testThrow_01() {
		'''
			throw
			a
		'''.parseWithError
	}

	@Test
	def void testThrow_02() {
		'''
			throw /*
			*/ a
		'''.parseWithError
	}

	@Test
	def void testThrow_03() {
		'''
			throw a
		'''.parseSuccessfully
	}

	@Test
	def void testThrow_04() {
		'''
			throw /* */ a
		'''.parseSuccessfully
	}

	@Test
	def void testBreak_01() {
		val parseResult = '''
			break
			a
		'''.parseSuccessfully
		parseResult.hasChildren(2)
	}

	@Test
	def void testBreak_02() {
		val parseResult = '''
			break /*
			*/ a
		'''.parseSuccessfully
		parseResult.hasChildren(2)
	}


	@Test
	def void testContinue_01() {
		val parseResult = '''
			continue
			a
		'''.parseSuccessfully
		parseResult.hasChildren(2)
	}

	@Test
	def void testContinue_02() {
		val parseResult = '''
			continue /*
			*/ a
		'''.parseSuccessfully
		parseResult.hasChildren(2)
	}

	@Test
	def void testEsprima_01() {
		val script = '''
			{ throw error/* Multiline
			Comment */ error; }
		'''.parseSuccessfully
		val block = script.scriptElements.head as Block
		assertEquals(2, block.statements.size)
	}

	@Test
	def void testEcma_01() {
		val script = '''
			1
			++
			++
			2
		'''.parseSuccessfully
		assertEquals(2, script.scriptElements.size)
		val first = script.scriptElements.head as ExpressionStatement
		val second = script.scriptElements.last as ExpressionStatement
		val literal = first.expression as IntLiteral
		assertEquals(1, literal.value.intValue)
		val unary = second.expression as UnaryExpression
		val nestedUnary = unary.expression as UnaryExpression
		val nestedLiteral = nestedUnary.expression as IntLiteral
		assertEquals(2, nestedLiteral.value.intValue)
	}

	@Test
	def void testIssue_518_01() {
		val script = '''
			var x

			x
		'''.parseSuccessfully
		val varStatement = script.scriptElements.head as VariableStatement
		val varDecl = varStatement.varDecl.head
		assertEquals('x', varDecl.name)
		assertNull(varDecl.declaredTypeRef)
		val node = NodeModelUtils.findActualNodeFor(script)
		assertEquals(3, node.children.size) // hidden (empty as in 'no children, length=0'), var, x
	}

	@Test
	def void testIssue_518_01_N_01() {
		val script = "var x\n\nx\n".parseSuccessfully
		val varStatement = script.scriptElements.head as VariableStatement
		val varDecl = varStatement.varDecl.head
		assertEquals('x', varDecl.name)
		assertNull(varDecl.declaredTypeRef)
		val node = NodeModelUtils.findActualNodeFor(script)
		assertEquals(3, node.children.size) // hidden (empty as in 'no children, length=0'), var, x
	}

	@Test
	def void testIssue_518_01_N_02() {
		val firstScript = "var x\n\nx\n".parseSuccessfully;
		val secondScript = "var x;\nx\n".parseSuccessfully;

		val firstRootNode = (firstScript.eResource as XtextResource).parseResult.rootNode
		val secondRootNode = (secondScript.eResource as XtextResource).parseResult.rootNode

		assertEqualNodes(firstRootNode, secondRootNode)
	}

	@Test
	def void testIssue_518_01_N_03() {
		val firstScript =  "export var x: any\n\nx\n".parseSuccessfully;
		val secondScript = "export var x: any;\nx\n".parseSuccessfully;

		val firstRootNode = (firstScript.eResource as XtextResource).parseResult.rootNode
		val secondRootNode = (secondScript.eResource as XtextResource).parseResult.rootNode

		assertEqualNodes(firstRootNode, secondRootNode)
	}

	@Test
	def void testIssue_518_01_N_04() {
		val firstScript =  "export var x: any\n\nx\n".parseSuccessfully;
		val secondScript = "export var x: any;\nx\n".parseSuccessfully;

		val firstRootNode = (firstScript.eResource as XtextResource).parseResult.rootNode
		val secondRootNode = (secondScript.eResource as XtextResource).parseResult.rootNode

		assertEqualNodes(firstRootNode, secondRootNode)
	}

	@Test
	def void testIssue_518_01_N_05() {
		val firstScript = "export var x\n\nx\n".parseSuccessfully;
		val secondScript = "export var x;\nx\n".parseSuccessfully;

		val firstRootNode = (firstScript.eResource as XtextResource).parseResult.rootNode
		val secondRootNode = (secondScript.eResource as XtextResource).parseResult.rootNode

		assertEqualNodes(firstRootNode, secondRootNode)
	}


	@Test
	def void testIssue_518_01_N_06() {
		val firstScript = "function f() { var x: any\n\nx\n }".parseSuccessfully;
		val secondScript = "function f() { var x: any;\nx\n }".parseSuccessfully;

		val firstRootNode = (firstScript.eResource as XtextResource).parseResult.rootNode
		val secondRootNode = (secondScript.eResource as XtextResource).parseResult.rootNode

		assertEqualNodes(firstRootNode, secondRootNode)
	}

	@Test
	def void testIssue_518_01_N_07() {
		val firstScript = "function f() { var x\n\nx\n }".parseSuccessfully;
		val secondScript = "function f() { var x;\nx\n }".parseSuccessfully;

		val firstRootNode = (firstScript.eResource as XtextResource).parseResult.rootNode
		val secondRootNode = (secondScript.eResource as XtextResource).parseResult.rootNode

		assertEqualNodes(firstRootNode, secondRootNode)
	}

	@Test
	def void testIssue_518_01_N_08() {
		val firstScript = "function * f() { var x: any\n\nx\n }".parseSuccessfully;
		val secondScript = "function * f() { var x: any;\nx\n }".parseSuccessfully;

		val firstRootNode = (firstScript.eResource as XtextResource).parseResult.rootNode
		val secondRootNode = (secondScript.eResource as XtextResource).parseResult.rootNode

		assertEqualNodes(firstRootNode, secondRootNode)
	}

	@Test
	def void testIssue_518_01_N_09() {
		val firstScript = "function * f() { var x\n\nx\n }".parseSuccessfully;
		val secondScript = "function * f() { var x;\nx\n }".parseSuccessfully;

		val firstRootNode = (firstScript.eResource as XtextResource).parseResult.rootNode
		val secondRootNode = (secondScript.eResource as XtextResource).parseResult.rootNode

		assertEqualNodes(firstRootNode, secondRootNode)
	}

	@Test
	def void testIssue_518_01_R_01() {
		val script = "var x\r\rx\r".parseSuccessfully
		val varStatement = script.scriptElements.head as VariableStatement
		val varDecl = varStatement.varDecl.head
		assertEquals('x', varDecl.name)
		assertNull(varDecl.declaredTypeRef)
		val node = NodeModelUtils.findActualNodeFor(script)
		assertEquals(3, node.children.size) // hidden (empty as in 'no children, length=0'), var, x
	}

	@Test
	def void testIssue_518_01_R_02() {
		val firstScript = "var x\r\rx\r".parseSuccessfully;
		val secondScript = "var x;\rx\r".parseSuccessfully;

		val firstRootNode = (firstScript.eResource as XtextResource).parseResult.rootNode
		val secondRootNode = (secondScript.eResource as XtextResource).parseResult.rootNode

		assertEqualNodes(firstRootNode, secondRootNode)
	}

	@Test
	def void testIssue_518_01_RN_02() {
		val firstScript = "var x\r\n\r\nx\r\n".parseSuccessfully;
		val secondScript = "var x;\r\nx\r\n".parseSuccessfully;

		val firstRootNode = (firstScript.eResource as XtextResource).parseResult.rootNode
		val secondRootNode = (secondScript.eResource as XtextResource).parseResult.rootNode

		assertEqualNodes(firstRootNode, secondRootNode)
	}

	def dispatch void assertEqualNodes(ICompositeNode first, ICompositeNode second) {
		assertEquals(first.grammarElement, second.grammarElement)
		val firstIter = first.children.iterator
		val secondIter = second.children.iterator
		while(firstIter.hasNext) {
			val firstChild = firstIter.next
			val secondChild = secondIter.next
			assertEqualNodes(firstChild, secondChild)
		}
		assertFalse(secondIter.hasNext)
	}

	def dispatch void assertEqualNodes(ILeafNode first, ILeafNode second) {
		assertEquals(first.grammarElement, second.grammarElement)
		assertEquals(first.hidden, second.hidden)
	}


	@Test
	def void testIssue_518_01_RN_01() {
		val script = "var x\r\n\r\nx\r\n".parseSuccessfully
		val varStatement = script.scriptElements.head as VariableStatement
		val varDecl = varStatement.varDecl.head
		assertEquals('x', varDecl.name)
		assertNull(varDecl.declaredTypeRef)
		val node = NodeModelUtils.findActualNodeFor(script)
		assertEquals(3, node.children.size) // hidden (empty as in 'no children, length=0'), var, x
	}

	@Test
	def void testIssue_518_01_dynamicNewLine() {
		val sX = "var a;".parseSuccessfully
		val newLine = wp.getLineSeparatorInformation(sX.eResource.URI).lineSeparator
		val script = '''var x«newLine»«newLine»x«newLine»'''.parseSuccessfully
		val varStatement = script.scriptElements.head as VariableStatement
		val varDecl = varStatement.varDecl.head
		assertEquals('x', varDecl.name)
		assertNull(varDecl.declaredTypeRef)
		val node = NodeModelUtils.findActualNodeFor(script)
		assertEquals(3, node.children.size) // hidden (empty as in 'no children, length=0'), var, x
	}

	@Test
	def void testIssue_518_02() {
		val script = '''
			var x;

			x
		'''.parseSuccessfully
		val varStatement = script.scriptElements.head as VariableStatement
		val varDecl = varStatement.varDecl.head
		assertEquals('x', varDecl.name)
		assertNull(varDecl.declaredTypeRef)
		val node = NodeModelUtils.findActualNodeFor(script)
		assertEquals(3, node.children.size) // hidden (empty as in 'no children, length=0'), var, x
	}

	/** ASI must not swallow jsdoc-comments. Problem occured for ML-comments on same line as ASI.
	 * GHOLD-103*/
	@Test
	def void testJsdoc_Directive_sameLine() {
		var script ='''
			"use strict"/**
			docu of C */
			class C {}
		'''.parseSuccessfully
		assertEquals(2,script.scriptElements.size);
		val docu = documenationProvider.getDocumentation(script.scriptElements.get(1))
		assertEquals("docu of C",docu)
	}

	/** Here no conflict between jsdoc and ASI are expected, since the ML-comment starts in a new line.
	 * GHOLD-103*/
	@Test
	def void testJsdoc_Directive_differentLine() {
		var script ='''
			"use strict"
			/**
			docu of C */
			class C {}
		'''.parseSuccessfully
		assertEquals(2,script.scriptElements.size);
		val docu = documenationProvider.getDocumentation(script.scriptElements.get(1))
		assertEquals("docu of C",docu)
	}

	/** Single line comments should not contribute to documentation.
	 * GHOLD-103
	 */
	@Test
	def void testNonJsdoc_Directive_differentLine() {
		var script ='''
			"use strict"
			//**docu of C */
			class C {}
		'''.parseSuccessfully
		assertEquals(2,script.scriptElements.size);
		val docu = documenationProvider.getDocumentation(script.scriptElements.get(1))
		assertNull(docu)
	}

	/** Single line comments should not contribute to documentation.
	 * GHOLD-103
	 */
	@Test
	def void testNonJsdoc_Directive_sameLine() {
		var script ='''
			"use strict"//**docu of C */
			class C {}
		'''.parseSuccessfully
		assertEquals(2,script.scriptElements.size);
		val docu = documenationProvider.getDocumentation(script.scriptElements.get(1))
		assertNull(docu)
	}

	/** <b>This test will notify programmers, if the ASI-implementation is changed.</b> At the time of writing,
	 * ASI replaced a HiddenLeaf node of MultiLine-comments by a {@code LeafNodeWithSyntaxError}. This behavior required some workarounds.
	 * If this test fails, then the above assumed behavior changed and YOU - as programmer - should look at:
	 * <ul>
	 * <li>N4JSDocumentationsProvider</li>
	 * <li>N4JSOrganizeImport</li>
	 * <li>N4JSOrganizeImportsHandler</li>
	 * </ul>
	 * GHOLD-103
	 */
	@Test
	def void testJsdoc_InvestigateChangedASIbehaviour() {
		var script ='''
			"use strict"/**
			docu of C */
			class C {}
		'''.parseSuccessfully

		assertEquals(2,script.scriptElements.size);

		// Find ASI-Nodes:
		val leafNodesWithSyntaxError = NodeModelUtils.getNode(script).leafNodes.filter(LeafNodeWithSyntaxError);
		assertEquals("Only one node of type LeafNodeWithSyntaxError expected.",1,leafNodesWithSyntaxError.size);
		val theLNWSE = leafNodesWithSyntaxError.get(0);
		assertEquals("",  InternalSemicolonInjectingParser.SEMICOLON_INSERTED,  theLNWSE.syntaxErrorMessage.issueCode);

		val completeText= theLNWSE.text.trim;

		'''
			/**
			docu of C */
		'''.toString.trim.assertEquals(completeText);

	}
	/** Compare-test without ASI. C.f. {@link #testJsdoc_InvestigateChangedASIbehaviour()}
	 * GHOLD-103 */
	@Test
	def void testJsdoc_InvestigateChangedASIbehaviour_Comparison() {
		var script ='''
			"use strict";/**
			docu of C */
			class C {}
		'''.parseSuccessfully

		assertEquals(2,script.scriptElements.size);

		val classElement = script.scriptElements.get(1);
		val completeNode = NodeModelUtils.getNode( classElement );
		val collectedHiddenText = new StringBuffer;
		var break = false;
		for( leaf : completeNode.leafNodes ){
			if( ! break ) {
				if( leaf.isHidden ) {
					collectedHiddenText.append( leaf.text );
				} else {
					break=true;
				}
			}
		}

		val completeText = collectedHiddenText.toString.trim;

		'''
			/**
			docu of C */
		'''.toString.trim.assertEquals(completeText);

	}




}
