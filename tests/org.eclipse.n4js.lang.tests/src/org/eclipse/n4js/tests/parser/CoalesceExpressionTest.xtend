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

import org.eclipse.n4js.n4JS.CoalesceExpression
import org.eclipse.n4js.n4JS.ExpressionStatement
import org.eclipse.n4js.n4JS.IdentifierRef
import org.eclipse.n4js.n4JS.NumericLiteral
import org.junit.Test
import org.eclipse.n4js.n4JS.BinaryBitwiseExpression
import org.eclipse.n4js.n4JS.BinaryLogicalExpression

class CoalesceExpressionTest extends AbstractParserTest {

	@Test
	def void testCoalesce_01() {
		val program = 'y ?? 1'.parseESSuccessfully
		val statement = program.scriptElements.head as ExpressionStatement
		val coalesce = statement.expression as CoalesceExpression
		assertNotNull(coalesce)

		val expression = coalesce.expression as IdentifierRef
		assertEquals('y', expression.text)
		val dfltExpr = coalesce.defaultExpression as NumericLiteral
		assertEquals('1', dfltExpr.text)
	}
	
	@Test
	def void testCoalesce_02() {
		val program = 'x|y ?? 1&2'.parseESSuccessfully
		val statement = program.scriptElements.head as ExpressionStatement
		val coalesce = statement.expression as CoalesceExpression
		assertNotNull(coalesce)

		val expression = coalesce.expression as BinaryBitwiseExpression
		assertEquals('x|y', expression.text)
		val dfltExpr = coalesce.defaultExpression as BinaryBitwiseExpression
		assertEquals('1&2', dfltExpr.text)
	}

	@Test
	def void testCoalesce_03() {
		val program = 'x||y ?? zonk'.parseESWithError
		val statement = program.scriptElements.head as ExpressionStatement
		val coalesce = statement.expression as CoalesceExpression
		assertNotNull(coalesce)

		val expression = coalesce.expression as BinaryLogicalExpression
		assertEquals('x||y', expression.text)
		val dfltExpr = coalesce.defaultExpression as IdentifierRef
		assertEquals('zonk', dfltExpr.idAsText)
		
		val message = program.eResource.errors.head.message
		assertEquals('Nullish coalescing expressions cannot immediately contain an || operation.', message)
	}
	
	@Test
	def void testCoalesce_04() {
		'false || (a ?? b)'.parseESSuccessfully
	}
	
	@Test
	def void testCoalesce_05() {
		val program = 'false || a ?? b'.parseESWithError
		val statement = program.scriptElements.head as ExpressionStatement
		val coalesce = statement.expression as CoalesceExpression
		assertNotNull(coalesce)

		val expression = coalesce.expression as BinaryLogicalExpression
		assertEquals('false || a', expression.text)
		val dfltExpr = coalesce.defaultExpression as IdentifierRef
		assertEquals('b', dfltExpr.idAsText)
		
		val message = program.eResource.errors.head.message
		assertEquals('Nullish coalescing expressions cannot immediately contain an || operation.', message)
	}
	
	@Test
	def void testCoalesce_06() {
		val program = 'false && a ?? b'.parseESWithError
		val statement = program.scriptElements.head as ExpressionStatement
		val coalesce = statement.expression as CoalesceExpression
		assertNotNull(coalesce)

		val expression = coalesce.expression as BinaryLogicalExpression
		assertEquals('false && a', expression.text)
		val dfltExpr = coalesce.defaultExpression as IdentifierRef
		assertEquals('b', dfltExpr.idAsText)
		
		val message = program.eResource.errors.head.message
		assertEquals('Nullish coalescing expressions cannot immediately contain an && operation.', message)
	}

	@Test
	def void testCoalesce_07() {
		'(x||y) ?? zonk'.parseESSuccessfully
	}
	
	@Test
	def void testCoalesce_08() {
		'(false && a) ?? b'.parseESSuccessfully
	}
	
	@Test
	def void testCoalesce_09() {
		'false ?? (a && b)'.parseESSuccessfully
	}
}
