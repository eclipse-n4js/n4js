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

import org.eclipse.n4js.n4JS.ArrayLiteral
import org.eclipse.n4js.n4JS.AssignmentExpression
import org.eclipse.n4js.n4JS.ExpressionStatement
import org.eclipse.n4js.n4JS.IdentifierRef
import org.junit.Test
import org.eclipse.n4js.n4JS.IntLiteral

class ES_11_01_4_ArrayInitializerEsprimaTest extends AbstractParserTest {

	@Test
	def void testArrayInitializer_01() {
		val script = 'x = []'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val assignment = statement.expression as AssignmentExpression
		val identifier = assignment.lhs as IdentifierRef
		assertEquals('x', identifier.text)
		val array = assignment.rhs as ArrayLiteral
		assertEquals(0, array.elements.size)
	}

	@Test
	def void testArrayInitializer_02() {
		val script = 'x = [ ]'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val assignment = statement.expression as AssignmentExpression
		val identifier = assignment.lhs as IdentifierRef
		assertEquals('x', identifier.text)
		val array = assignment.rhs as ArrayLiteral
		assertEquals(0, array.elements.size)
	}

	@Test
	def void testArrayInitializer_03() {
		val script = 'x = [ 42 ]'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val assignment = statement.expression as AssignmentExpression
		val identifier = assignment.lhs as IdentifierRef
		assertEquals('x', identifier.text)
		val array = assignment.rhs as ArrayLiteral
		val singleElement = array.elements.head
		assertEquals(42bd, (singleElement.expression as IntLiteral).value)
	}

	@Test
	def void testArrayInitializer_04() {
		val script = 'x = [ 42, ]'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val assignment = statement.expression as AssignmentExpression
		val identifier = assignment.lhs as IdentifierRef
		assertEquals('x', identifier.text)
		val array = assignment.rhs as ArrayLiteral
		assertEquals(1, array.elements.size)
		val firstElement = array.elements.head
		assertEquals(42, (firstElement.expression as IntLiteral).toInt)
	}

	@Test
	def void testArrayInitializer_05() {
		val script = 'x = [ ,, 42 ]'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val assignment = statement.expression as AssignmentExpression
		val identifier = assignment.lhs as IdentifierRef
		assertEquals('x', identifier.text)
		val array = assignment.rhs as ArrayLiteral
		assertEquals(3, array.elements.size)
		val emptyFirstElement = array.elements.head
		assertNull(emptyFirstElement.expression)
		val emptySecondElement = array.elements.get(1)
		assertNull(emptySecondElement.expression)
		val lastElement = array.elements.last
		assertEquals(42, (lastElement.expression as IntLiteral).toInt)
	}

	@Test
	def void testArrayInitializer_06() {
		val script = 'x = [ 1, 2, 3, ]'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val assignment = statement.expression as AssignmentExpression
		val identifier = assignment.lhs as IdentifierRef
		assertEquals('x', identifier.text)
		val array = assignment.rhs as ArrayLiteral
		assertEquals(3, array.elements.size)
		val elements = array.elements.iterator
		assertEquals(1, (elements.next.expression as IntLiteral).toInt)
		assertEquals(2, (elements.next.expression as IntLiteral).toInt)
		assertEquals(3, (elements.next.expression as IntLiteral).toInt)
	}

	@Test
	def void testArrayInitializer_07() {
		val script = 'x = [ 1, 2,, 3, ]'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val assignment = statement.expression as AssignmentExpression
		val identifier = assignment.lhs as IdentifierRef
		assertEquals('x', identifier.text)
		val array = assignment.rhs as ArrayLiteral
		assertEquals(4, array.elements.size)
		val elements = array.elements.iterator
		assertEquals(1, (elements.next.expression as IntLiteral).toInt)
		assertEquals(2, (elements.next.expression as IntLiteral).toInt)
		assertNull(elements.next.expression)
		assertEquals(3, (elements.next.expression as IntLiteral).toInt)
	}

	@Test
	def void testArrayInitializer_08() {
		val script = 'x = [ 1, 2,, 3,, ]'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val assignment = statement.expression as AssignmentExpression
		val identifier = assignment.lhs as IdentifierRef
		assertEquals('x', identifier.text)
		val array = assignment.rhs as ArrayLiteral
		assertEquals(5, array.elements.size)
		val elements = array.elements.iterator
		assertEquals(1, (elements.next.expression as IntLiteral).toInt)
		assertEquals(2, (elements.next.expression as IntLiteral).toInt)
		assertNull(elements.next.expression)
		assertEquals(3, (elements.next.expression as IntLiteral).toInt)
		assertNull(elements.next.expression)
	}
}
