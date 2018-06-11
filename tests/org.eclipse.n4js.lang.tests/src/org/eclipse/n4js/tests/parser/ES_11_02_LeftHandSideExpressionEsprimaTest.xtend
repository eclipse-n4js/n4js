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

import org.eclipse.n4js.n4JS.CommaExpression
import org.eclipse.n4js.n4JS.ExpressionStatement
import org.eclipse.n4js.n4JS.IdentifierRef
import org.eclipse.n4js.n4JS.IndexedAccessExpression
import org.eclipse.n4js.n4JS.IntLiteral
import org.eclipse.n4js.n4JS.NewExpression
import org.eclipse.n4js.n4JS.ParameterizedCallExpression
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression
import org.eclipse.n4js.n4JS.ParenExpression
import org.junit.Test

class ES_11_02_LeftHandSideExpressionEsprimaTest extends AbstractParserTest {

	@Test
	def void testNewButton_01() {
		val script = 'new Button'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val newExpression = statement.expression as NewExpression
		val button = newExpression.callee as IdentifierRef
		assertEquals('Button', button.text)
		assertFalse(newExpression.withArgs)
	}

	@Test
	def void testNewButton_02() {
		val script = 'new Button()'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val newExpression = statement.expression as NewExpression
		val button = newExpression.callee as IdentifierRef
		assertEquals('Button', button.text)
		assertTrue(newExpression.withArgs)
	}

	@Test
	def void testNewNewFoo_01() {
		val script = 'new new foo'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val outerNewExpression = statement.expression as NewExpression
		val innerNewExpression = outerNewExpression.callee as NewExpression
		val foo = innerNewExpression.callee as IdentifierRef
		assertEquals('foo', foo.text)
		assertFalse(innerNewExpression.withArgs)
		assertFalse(outerNewExpression.withArgs)
	}

	@Test
	def void testNewNewFoo_02() {
		val script = 'new new foo()'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val outerNewExpression = statement.expression as NewExpression
		val innerNewExpression = outerNewExpression.callee as NewExpression
		val foo = innerNewExpression.callee as IdentifierRef
		assertEquals('foo', foo.text)
		assertTrue(innerNewExpression.withArgs)
		assertFalse(outerNewExpression.withArgs)
	}

	@Test
	def void testNewFooBar_01() {
		val script = 'new foo().bar()'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val call = statement.expression as ParameterizedCallExpression
		val bar = call.target as ParameterizedPropertyAccessExpression
		assertEquals('bar', bar.propertyText)
		val newExpression = bar.target as NewExpression
		val foo = newExpression.callee as IdentifierRef
		assertEquals('foo', foo.text)
		assertTrue(newExpression.withArgs)
	}

	@Test
	def void testNewIndexAccess() {
		val script = 'new foo[bar]'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val newExpression = statement.expression as NewExpression
		val indexAccess = newExpression.callee as IndexedAccessExpression
		val foo = indexAccess.target as IdentifierRef
		assertEquals('foo', foo.text)
		val arg = indexAccess.index as IdentifierRef
		assertEquals('bar', arg.text)
	}

	@Test
	def void testNewDotAccess_01() {
		val script = 'new foo.bar()'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val newExpression = statement.expression as NewExpression
		val fooBar = newExpression.callee as ParameterizedPropertyAccessExpression
		val foo = fooBar.target as IdentifierRef
		assertEquals('foo', foo.text)
		assertEquals('bar', fooBar.propertyText)
		assertTrue(newExpression.withArgs)
	}

	@Test
	def void testNewDotAccess_02() {
		val script = '( new foo).bar()'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val call = statement.expression as ParameterizedCallExpression
		val bar = call.target as ParameterizedPropertyAccessExpression
		val paren = bar.target as ParenExpression
		val newExpression = paren.expression as NewExpression
		val foo = newExpression.callee as IdentifierRef
		assertEquals('foo', foo.text)
		assertEquals('bar', bar.propertyText)
	}

	@Test
	def void testCallExpression_01() {
		val script = 'foo(bar, baz)'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val foo = statement.expression as ParameterizedCallExpression
		assertEquals('foo', (foo.target as IdentifierRef).text)
		assertEquals(2, foo.arguments.size)
		assertEquals('bar', (foo.arguments.get(0).expression as IdentifierRef).text)
		assertEquals('baz', (foo.arguments.get(1).expression as IdentifierRef).text)
	}

	@Test
	def void testCallExpression_02() {
		val script = '(    foo  )()'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val call = statement.expression as ParameterizedCallExpression
		val foo = (call.target as ParenExpression).expression as IdentifierRef
		assertEquals('foo',foo.text)
		assertEquals(0, call.arguments.size)
	}

	@Test
	def void testDotAccess_01() {
		val script = 'universe.milkyway'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val milkyway = statement.expression as ParameterizedPropertyAccessExpression
		assertEquals('milkyway', milkyway.propertyText)
		assertEquals('universe', milkyway.target.text)
	}

	@Test
	def void testDotAccess_02() {
		val script = 'universe.milkyway.solarsystem'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val solarsystem = statement.expression as ParameterizedPropertyAccessExpression
		assertEquals('solarsystem', solarsystem.propertyText)
		val milkyway = solarsystem.target as ParameterizedPropertyAccessExpression
		assertEquals('milkyway', milkyway.propertyText)
		val universe = milkyway.target as IdentifierRef
		assertEquals('universe', universe.text)
	}

	@Test
	def void testDotAccess_03() {
		val script = 'universe.milkyway.solarsystem.Earth'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val earth = statement.expression as ParameterizedPropertyAccessExpression
		assertEquals('Earth', earth.propertyText)
		val solarsystem = earth.target as ParameterizedPropertyAccessExpression
		assertEquals('solarsystem', solarsystem.propertyText)
		val milkyway = solarsystem.target as ParameterizedPropertyAccessExpression
		assertEquals('milkyway', milkyway.propertyText)
		val universe = milkyway.target as IdentifierRef
		assertEquals('universe', universe.text)
	}

	@Test
	def void testIndexAccess_01() {
		val script = 'universe[galaxyName, otherUselessName]'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val universe = statement.expression as IndexedAccessExpression
		assertEquals('universe', (universe.target as IdentifierRef).text)
		val comma = universe.index as CommaExpression
		assertEquals(2, comma.exprs.size)
	}

	@Test
	def void testIndexAccess_02() {
		val script = 'universe[galaxyName]'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val universe = statement.expression as IndexedAccessExpression
		assertEquals('universe', (universe.target as IdentifierRef).text)
		val identifier = universe.index as IdentifierRef
		assertEquals('galaxyName', identifier.text)
	}

	@Test
	def void testDotAccess_04() {
		val script = 'universe[42].galaxies'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val galaxies = statement.expression as ParameterizedPropertyAccessExpression
		assertEquals('galaxies', galaxies.propertyText)
		val universeIndexed = galaxies.target as IndexedAccessExpression
		val number = universeIndexed.index as IntLiteral
		assertEquals(42, number.toInt)
		val universe = universeIndexed.target as IdentifierRef
		assertEquals('universe', universe.text)
	}

	@Test
	def void testDotAccess_05() {
		val script = 'universe(42).galaxies'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val galaxies = statement.expression as ParameterizedPropertyAccessExpression
		assertEquals('galaxies', galaxies.propertyText)
		val call = galaxies.target as ParameterizedCallExpression
		val number = call.arguments.head.expression as IntLiteral
		assertEquals(42, number.toInt)
		val universe = call.target as IdentifierRef
		assertEquals('universe', universe.text)
	}

	@Test
	def void testDotAccess_06() {
		val script = 'universe(42).galaxies(14, 3, 77).milkyway'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val milkyway = statement.expression as ParameterizedPropertyAccessExpression
		assertEquals('milkyway', milkyway.propertyText)
		val galaxiesCall = milkyway.target as ParameterizedCallExpression
		val galaxiesArgs = galaxiesCall.arguments.map[expression]
		assertEquals(3, galaxiesArgs.size)
		assertEquals(14, (galaxiesArgs.get(0) as IntLiteral).toInt)
		assertEquals(3, (galaxiesArgs.get(1) as IntLiteral).toInt)
		assertEquals(77, (galaxiesArgs.get(2) as IntLiteral).toInt)
		val galaxies = galaxiesCall.target as ParameterizedPropertyAccessExpression
		assertEquals('galaxies', galaxies.propertyText)
		val universeCall = galaxies.target as ParameterizedCallExpression
		val universeArgs = universeCall.arguments.map[expression]
		assertEquals(1, universeArgs.size)
		assertEquals(42, (universeArgs.get(0) as IntLiteral).toInt)
		val universe = universeCall.target as IdentifierRef
		assertEquals('universe', universe.text)
	}

	@Test
	def void testDotAccess_07() {
		val script = 'earth.asia.Indonesia.prepareForElection(2014)'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val call = statement.expression as ParameterizedCallExpression
		assertEquals(2014, (call.arguments.head.expression as IntLiteral).toInt)
	}

	@Test
	def void testKeyword_01() {
		val script = 'universe.if'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val keyword = statement.expression as ParameterizedPropertyAccessExpression
		assertEquals('universe',(keyword.target as IdentifierRef).text)
		assertEquals('if', keyword.propertyText)
	}

	@Test
	def void testKeyword_02() {
		val script = 'universe.true'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val keyword = statement.expression as ParameterizedPropertyAccessExpression
		assertEquals('universe',(keyword.target as IdentifierRef).text)
		assertEquals('true', keyword.propertyText)
	}

	@Test
	def void testKeyword_03() {
		val script = 'universe.false'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val keyword = statement.expression as ParameterizedPropertyAccessExpression
		assertEquals('universe',(keyword.target as IdentifierRef).text)
		assertEquals('false', keyword.propertyText)
	}

	@Test
	def void testKeyword_04() {
		val script = 'universe.null'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val keyword = statement.expression as ParameterizedPropertyAccessExpression
		assertEquals('universe',(keyword.target as IdentifierRef).text)
		assertEquals('null', keyword.propertyText)
	}

}
