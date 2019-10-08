/**
 * Copyright (c) 2019 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.tests.parser

import org.junit.Test
import org.eclipse.n4js.n4JS.ExpressionStatement
import org.eclipse.n4js.n4JS.ConditionalExpression
import org.eclipse.n4js.n4JS.ParameterizedCallExpression

/**
 *
 */
class ExpressionWithTargetParserTest extends AbstractParserTest {

	@Test
	def void testCallExpression_01() {
		'''
			a(b);
		'''.parseJSSuccessfully
	}
	
	@Test
	def void testCallExpression_02() {
		'''
			a(b)()
		'''.parseJSSuccessfully
	}
	
	@Test
	def void testPropertyAccess_01() {
		'''
			a.b
		'''.parseJSSuccessfully
	}
	
	@Test
	def void testPropertyAccess_02() {
		'''
			a.b.c
		'''.parseJSSuccessfully
	}
	
	@Test
	def void testIndexAccess_01() {
		'''
			a[0]
		'''.parseJSSuccessfully
	}
	
	@Test
	def void testIndexAccess_02() {
		'''
			a[0][1]
		'''.parseJSSuccessfully
	}
	
	@Test
	def void testTemplateLiteral_01() {
		'''
			a`raw`
		'''.parseJSSuccessfully
	}
	
	@Test
	def void testTemplateLiteral_02() {
		'''
			a`raw``raw`
		'''.parseJSSuccessfully
	}

	@Test
	def void testOptionalCallExpression_01() {
		'''
			a?.(b);
		'''.parseJSSuccessfully
	}
	
	@Test
	def void testOptionalCallExpression_02() {
		'''
			a?.(b)?.()
		'''.parseJSSuccessfully
	}
	
	@Test
	def void testOptionalPropertyAccess_01() {
		'''
			a?.b
		'''.parseJSSuccessfully
	}
	
	@Test
	def void testOptionalPropertyAccess_02() {
		'''
			a?.b?.c
		'''.parseJSSuccessfully
	}
	
	@Test
	def void testOptionalIndexAccess_01() {
		'''
			a?.[0]
		'''.parseJSSuccessfully
	}
	
	@Test
	def void testOptionalIndexAccess_02() {
		'''
			a?.[0]?.[1]
		'''.parseJSSuccessfully
	}
	
	@Test
	def void testOptionalTemplateLiteral_01() {
		'''
			a?.`raw`
		'''.parseJSSuccessfully
	}
	
	@Test
	def void testOptionalTemplateLiteral_02() {
		'''
			a?.`raw`?.`raw`
		'''.parseJSSuccessfully
	}
	
	@Test
	def void testDisambiguateFromTernary_01() {
		val parsed = '''
			a?.1:2
		'''.parseJSSuccessfully
		val expressionStmt = parsed.scriptElements.head as ExpressionStatement
		assertTrue(expressionStmt.expression instanceof ConditionalExpression)
	}
	
	@Test
	def void testDisambiguateFromTernary_02() {
		val parsed = '''
			a?. 0 : 2
		'''.parseJSWithError
		val expressionStmt = parsed.scriptElements.head as ExpressionStatement
		assertTrue(expressionStmt.expression instanceof ParameterizedCallExpression)
	}
}