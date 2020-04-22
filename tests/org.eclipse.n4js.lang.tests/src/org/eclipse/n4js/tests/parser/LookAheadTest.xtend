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

import org.eclipse.n4js.n4JS.ExpressionStatement
import org.eclipse.n4js.n4JS.NumericLiteral
import org.eclipse.xtext.nodemodel.util.NodeModelUtils
import org.junit.Test

class LookAheadTest extends AbstractParserTest{

	@Test
	def void testNoSemi() {
		val script = '''
			1
			2
			3
		'''.parse

		assertEquals(3, script.scriptElements.size)
		
		val stmt = script.scriptElements.head as ExpressionStatement
		val expr = stmt.expression as NumericLiteral
		
		val node = NodeModelUtils.getNode(expr)
		
		assertEquals(1, node.lookAhead)
		
		val stmt2 = script.scriptElements.last as ExpressionStatement
		val expr2 = stmt2.expression as NumericLiteral
		
		val node2 = NodeModelUtils.getNode(expr2)
		
		assertEquals(1, node2.lookAhead)
	}

	@Test
	def void testSemi() {
		val script = '''
			1;
			2;
			3
		'''.parse

		assertEquals(3, script.scriptElements.size)
		
		val stmt = script.scriptElements.head as ExpressionStatement
		val expr = stmt.expression as NumericLiteral
		
		val node = NodeModelUtils.getNode(expr)
		
		assertEquals(1, node.lookAhead)
		
		val stmt2 = script.scriptElements.last as ExpressionStatement
		val expr2 = stmt2.expression as NumericLiteral
		
		val node2 = NodeModelUtils.getNode(expr2)
		
		assertEquals(1, node2.lookAhead)
	}
}
