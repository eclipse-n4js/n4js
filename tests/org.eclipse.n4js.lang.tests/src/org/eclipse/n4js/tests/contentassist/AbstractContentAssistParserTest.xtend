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
package org.eclipse.n4js.tests.contentassist

import com.google.inject.Inject
import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.services.N4JSGrammarAccess
import java.util.Collection
import java.util.Set
import org.eclipse.xtext.AbstractElement
import org.eclipse.xtext.ide.editor.contentassist.antlr.FollowElement
import org.eclipse.xtext.junit4.InjectWith
import org.eclipse.xtext.junit4.XtextRunner
import org.eclipse.xtext.junit4.util.ParseHelper
import org.eclipse.xtext.nodemodel.INode
import org.eclipse.xtext.nodemodel.util.NodeModelUtils
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

/**
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProvider)
abstract class AbstractContentAssistParserTest extends Assert {

	@Inject extension ParseHelper<Script>
	@Inject protected N4JSGrammarAccess grammarAccess
	@Inject GrammarPrettyPrinter prettyPrinter

	def Collection<FollowElement> getFollowElements(INode node, int start, int end)

	def Collection<FollowElement> getFollowElements(FollowElement prev)

	def Collection<FollowElement> getFollowElements(INode node, String after) {
		return node.getFollowElements(0, after.length)
	}

	def Collection<FollowElement> getFollowElements(INode node) {
		return node.getFollowElements(0, node.totalLength)
	}

	def String prettyPrint(Set<AbstractElement> elements) {
		return elements.join('\n\n', prettyPrinter)
	}

	@Test
	def void testEmptyInput() {
		val node = ''.toNode
		val followElements = getFollowElements(node, 0, 0)
		val grammarElements = followElements.map [ grammarElement ].toSet
		assertEquals(2, grammarElements.size)
		assertTrue(grammarElements.contains(grammarAccess.scriptAccess.annotationsAssignment_1))
		assertTrue(grammarElements.contains(grammarAccess.scriptAccess.scriptElementsAssignment_2))
	}

	@Test
	def void testAfterNumber() {
		val node = '1'.toNode
		val followElements = getFollowElements(node)
		val grammarElements = followElements.map [ grammarElement ].toSet
		assertEquals(grammarElements.prettyPrint, 18, grammarElements.size)
		assertTrue(grammarElements.prettyPrint, grammarElements.contains(grammarAccess.postfixExpressionAccess.group_1))
	}

	@Test
	def void testAfterNumber_Semi() {
		val node = '1;'.toNode
		val followElements = getFollowElements(node)
		val grammarElements = followElements.map [ grammarElement ].toSet
		assertEquals(grammarElements.prettyPrint, 1, grammarElements.size)
		assertTrue(grammarElements.contains(grammarAccess.scriptAccess.scriptElementsAssignment_2))
		assertFalse(grammarElements.prettyPrint, grammarElements.contains(grammarAccess.postfixExpressionAccess.group_1))
	}

	@Test
	def void testAfterNumber_SemiAndNL() {
		val node = '1;\n'.toNode
		val followElements = getFollowElements(node)
		val grammarElements = followElements.map [ grammarElement ].toSet
		assertEquals(grammarElements.prettyPrint, 1, grammarElements.size)
		assertTrue(grammarElements.contains(grammarAccess.scriptAccess.scriptElementsAssignment_2))
		assertFalse(grammarElements.prettyPrint, grammarElements.contains(grammarAccess.postfixExpressionAccess.group_1))
	}

	@Test
	def void testAfterNumber_SemiAndComment() {
		val node = '1;/*\n*/'.toNode
		val followElements = getFollowElements(node)
		val grammarElements = followElements.map [ grammarElement ].toSet
		assertEquals(grammarElements.prettyPrint, 1, grammarElements.size)
		assertTrue(grammarElements.contains(grammarAccess.scriptAccess.scriptElementsAssignment_2))
		assertFalse(grammarElements.prettyPrint, grammarElements.contains(grammarAccess.postfixExpressionAccess.group_1))
	}

	@Test
	def void testAfterNumberNL_eof() {
		val node = '1\n'.toNode
		val followElements = getFollowElements(node)
		val grammarElements = followElements.map [ grammarElement ].toSet
		assertEquals(grammarElements.prettyPrint, 18, grammarElements.size)
		assertTrue(grammarElements.contains(grammarAccess.scriptAccess.scriptElementsAssignment_2))
		assertFalse(grammarElements.prettyPrint, grammarElements.contains(grammarAccess.postfixExpressionAccess.group_1))
	}

	@Test
	def void testAfterNumberNLComment_eof() {
		val node = '1/*\n*/'.toNode
		val followElements = getFollowElements(node)
		val grammarElements = followElements.map [ grammarElement ].toSet
		assertEquals(grammarElements.prettyPrint, 18, grammarElements.size)
		assertTrue(grammarElements.contains(grammarAccess.scriptAccess.scriptElementsAssignment_2))
		assertFalse(grammarElements.prettyPrint, grammarElements.contains(grammarAccess.postfixExpressionAccess.group_1))
	}

	@Test
	def void testAfterNumberNL_prevLine() {
		val node = '1\n'.toNode
		val followElements = getFollowElements(node, '1')
		val grammarElements = followElements.map [ grammarElement ].toSet
		assertEquals(grammarElements.prettyPrint, 18, grammarElements.size)
		assertTrue(grammarElements.prettyPrint, grammarElements.contains(grammarAccess.postfixExpressionAccess.group_1))
		assertFalse(grammarElements.prettyPrint, grammarElements.contains(grammarAccess.scriptAccess.scriptElementsAssignment_2))
	}

	@Test
	def void testAfterReturnNL_eof() {
		val node = 'return\n'.toNode
		val followElements = getFollowElements(node)
		val grammarElements = followElements.map [ grammarElement ].toSet
		assertEquals(grammarElements.prettyPrint, 1, grammarElements.size)
		assertTrue(grammarElements.prettyPrint, grammarElements.contains(grammarAccess.scriptAccess.scriptElementsAssignment_2))
	}

	@Test
	def void testAfterReturnNLComment_eof() {
		val node = 'return/*\n*/'.toNode
		val followElements = getFollowElements(node)
		val grammarElements = followElements.map [ grammarElement ].toSet
		assertEquals(grammarElements.prettyPrint, 1, grammarElements.size)
		assertTrue(grammarElements.prettyPrint, grammarElements.contains(grammarAccess.scriptAccess.scriptElementsAssignment_2))
	}

	@Test
	def void testAfterReturnNL() {
		val node = 'return\n\n'.toNode
		val followElements = getFollowElements(node, 'return\n')
		val grammarElements = followElements.map [ grammarElement ].toSet
		assertEquals(grammarElements.prettyPrint, 1, grammarElements.size)
		assertTrue(grammarElements.prettyPrint, grammarElements.contains(grammarAccess.scriptAccess.scriptElementsAssignment_2))
	}

	@Test
	def void testAfterReturnNLComment() {
		val node = 'return/*\n*/\n'.toNode
		val followElements = getFollowElements(node, 'return/*\n*/')
		val grammarElements = followElements.map [ grammarElement ].toSet
		assertEquals(grammarElements.prettyPrint, 1, grammarElements.size)
		assertTrue(grammarElements.prettyPrint, grammarElements.contains(grammarAccess.scriptAccess.scriptElementsAssignment_2))
	}

	@Test
	def void testAfterDot() {
		val node = 'a.'.toNode
		val followElements = getFollowElements(node)
		val grammarElements = followElements.map [ grammarElement ].toSet
		assertEquals(grammarElements.prettyPrint, 1, grammarElements.size)
		assertEquals(3, followElements.head.lookAhead)
		assertTrue(grammarElements.prettyPrint, grammarElements.contains(grammarAccess.rootStatementAccess.alternatives))
	}

	@Test
	def void testAfterDot_NextBatchOfFollowElements() {
		val node = 'a.'.toNode
		val followElement = getFollowElements(node).head
		followElement.assertSubsequentFollowElementsInMemberExpression
	}

	@Test
	def void testAfterDot_IDEBUG481_01() {
		val node = '''
			const constOne = null;
			const constTwo = null;
			const f = function (f: {function(): void}) { };
			a.'''.toNode
		val followElement = getFollowElements(node).head
		followElement.assertSubsequentFollowElementsInMemberExpression
	}

	@Test
	def void testAfterDot_IDEBUG481_02() {
		val node = '''
			const constOne = null;
			const constTwo = null;
			const f = function (f: {function(): void}) { }
			a.'''.toNode
		val followElement = getFollowElements(node).head
		followElement.assertSubsequentFollowElementsInMemberExpression
	}

	@Test
	def void testAfterDot_IDEBUG481_03() {
		val node = '''
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
			a.'''.toNode
		val followElement = getFollowElements(node).head
		followElement.assertSubsequentFollowElementsInMemberExpression
	}

	@Test
	def void testAfterDot_IDEBUG481_04() {
		val node = '''
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
			a.'''.toNode
		val followElement = getFollowElements(node).head
		followElement.assertSubsequentFollowElementsInMemberExpression
	}

	@Test
	def void testAfterDot_IDEBUG481_05() {
		val node = '''
			f(() => { return null });
			a.'''.toNode
		val followElement = getFollowElements(node).head
		followElement.assertSubsequentFollowElementsInMemberExpression
	}

	def assertSubsequentFollowElementsInMemberExpression(FollowElement followElement) {
		val followElements = followElement.followElements

		val grammarElements = followElements.map [ grammarElement ].toSet
		assertEquals(grammarElements.prettyPrint, 2, grammarElements.size)
		followElements.forEach [
			assertEquals(1, lookAhead)
		]
		assertTrue(grammarElements.prettyPrint, grammarElements.contains(grammarAccess.parameterizedPropertyAccessExpressionTailAccess.typeArgumentsParserRuleCall_1))
		assertTrue(grammarElements.prettyPrint, grammarElements.contains(grammarAccess.identifierNameAccess.alternatives))
	}

	@Test
	def void testAfterDot_nl() {
		val node = 'a.\n\n'.toNode
		val followElements = getFollowElements(node, 0, 3)
		val grammarElements = followElements.map [ grammarElement ].toSet
		assertEquals(grammarElements.prettyPrint, 1, grammarElements.size)
		assertEquals(4, followElements.head.lookAhead)
		assertTrue(grammarElements.prettyPrint, grammarElements.contains(grammarAccess.rootStatementAccess.alternatives))
	}

	@Test
	def void testAfterDot_nl_NextBatchOfFollowElements() {
		val node = 'a.\n\n'.toNode
		val followElement = getFollowElements(node, 0, 3).head
		followElement.assertSubsequentFollowElementsInMemberExpression
	}

	@Test
	def void testAfterDot_nl_comment() {
		val node = 'a./*\n*/\n'.toNode
		val followElements = getFollowElements(node, 'a./*\n*/')
		val grammarElements = followElements.map [ grammarElement ].toSet
		assertEquals(grammarElements.prettyPrint, 1, grammarElements.size)
		assertEquals(4, followElements.head.lookAhead)
		assertTrue(grammarElements.prettyPrint, grammarElements.contains(grammarAccess.rootStatementAccess.alternatives))
	}

	@Test
	def void testAfterDot_nl_comment_NextBatchOfFollowElements() {
		val node = 'a./*\n*/\n'.toNode
		val followElement = getFollowElements(node, 'a./*\n*/').head
		followElement.assertSubsequentFollowElementsInMemberExpression
	}

	@Test
	def void testBinaryOp_01() {
		val node = '1+2'.toNode
		val followElements = getFollowElements(node, '1')
		val grammarElements = followElements.map [ grammarElement ].toSet
		assertEquals(grammarElements.prettyPrint, 18, grammarElements.size)
		assertTrue(grammarElements.prettyPrint, grammarElements.contains(grammarAccess.postfixExpressionAccess.group_1))
		assertFalse(grammarElements.prettyPrint, grammarElements.contains(grammarAccess.scriptAccess.scriptElementsAssignment_2))
	}

	@Test
	def void testBinaryOp_02() {
		val node = '1\n+2'.toNode
		val followElements = getFollowElements(node, '1')
		val grammarElements = followElements.map [ grammarElement ].toSet
		assertEquals(grammarElements.prettyPrint, 18, grammarElements.size)
		assertTrue(grammarElements.prettyPrint, grammarElements.contains(grammarAccess.postfixExpressionAccess.group_1))
		assertFalse(grammarElements.prettyPrint, grammarElements.contains(grammarAccess.scriptAccess.scriptElementsAssignment_2))
	}

	@Test
	def void testBinaryOp_03() {
		val node = '1\n+2'.toNode
		val followElements = getFollowElements(node, 0, 2)
		val grammarElements = followElements.map [ grammarElement ].toSet
		assertEquals(grammarElements.prettyPrint, 18, grammarElements.size)
		assertTrue(grammarElements.prettyPrint, grammarElements.contains(grammarAccess.scriptAccess.scriptElementsAssignment_2))
		assertFalse(grammarElements.prettyPrint, grammarElements.contains(grammarAccess.postfixExpressionAccess.group_1))
	}

	@Test
	def void testBinaryOp_04() {
		val node = '1/*\n*/+2'.toNode
		val followElements = getFollowElements(node, 0, 6)
		val grammarElements = followElements.map [ grammarElement ].toSet
		assertEquals(grammarElements.prettyPrint, 18, grammarElements.size)
		assertTrue(grammarElements.prettyPrint, grammarElements.contains(grammarAccess.scriptAccess.scriptElementsAssignment_2))
		assertFalse(grammarElements.prettyPrint, grammarElements.contains(grammarAccess.postfixExpressionAccess.group_1))
	}

	@Test
	def void testUnaryOp_01() {
		val node = '1\n++i'.toNode
		val followElements = getFollowElements(node, '1')
		val grammarElements = followElements.map [ grammarElement ].toSet
		assertEquals(grammarElements.prettyPrint, 18, grammarElements.size)
		assertFalse(grammarElements.prettyPrint, grammarElements.contains(grammarAccess.scriptAccess.scriptElementsAssignment_2))
		assertTrue(grammarElements.prettyPrint, grammarElements.contains(grammarAccess.postfixExpressionAccess.group_1))
	}

	@Test
	def void testUnaryOp_02() {
		val node = '1\n++i'.toNode
		val followElements = getFollowElements(node, '1\n')
		val grammarElements = followElements.map [ grammarElement ].toSet
		assertEquals(grammarElements.prettyPrint, 18, grammarElements.size)
		assertTrue(grammarElements.prettyPrint, grammarElements.contains(grammarAccess.scriptAccess.scriptElementsAssignment_2))
		assertFalse(grammarElements.prettyPrint, grammarElements.contains(grammarAccess.postfixExpressionAccess.group_1))
	}

	@Test
	def void testUnaryOp_03() {
		val node = '1;++i'.toNode
		val followElements = getFollowElements(node, '1')
		val grammarElements = followElements.map [ grammarElement ].toSet
		assertEquals(grammarElements.prettyPrint, 18, grammarElements.size)
		assertFalse(grammarElements.prettyPrint, grammarElements.contains(grammarAccess.scriptAccess.scriptElementsAssignment_2))
		assertTrue(grammarElements.prettyPrint, grammarElements.contains(grammarAccess.postfixExpressionAccess.group_1))
	}

	@Test
	def void testUnaryOp_04() {
		val node = '1;++i'.toNode
		val followElements = getFollowElements(node, '1;')
		val grammarElements = followElements.map [ grammarElement ].toSet
		assertEquals(grammarElements.prettyPrint, 1, grammarElements.size)
		assertTrue(grammarElements.prettyPrint, grammarElements.contains(grammarAccess.scriptAccess.scriptElementsAssignment_2))
	}

	@Test
	def void testUnaryOp_05() {
		val node = '1/*\n*/++i'.toNode
		val followElements = getFollowElements(node, '1/*\n*/')
		val grammarElements = followElements.map [ grammarElement ].toSet
		assertEquals(grammarElements.prettyPrint, 18, grammarElements.size)
		assertTrue(grammarElements.prettyPrint, grammarElements.contains(grammarAccess.scriptAccess.scriptElementsAssignment_2))
		assertFalse(grammarElements.prettyPrint, grammarElements.contains(grammarAccess.postfixExpressionAccess.group_1))
	}

	private def toNode(CharSequence s) {
		return NodeModelUtils.getNode(s.parse).rootNode
	}

}
