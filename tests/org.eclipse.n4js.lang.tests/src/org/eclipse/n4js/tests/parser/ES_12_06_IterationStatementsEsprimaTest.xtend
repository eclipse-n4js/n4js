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

import org.eclipse.n4js.n4JS.AssignmentExpression
import org.eclipse.n4js.n4JS.Block
import org.eclipse.n4js.n4JS.BooleanLiteral
import org.eclipse.n4js.n4JS.ParameterizedCallExpression
import org.eclipse.n4js.n4JS.DoStatement
import org.eclipse.n4js.n4JS.EmptyStatement
import org.eclipse.n4js.n4JS.ExpressionStatement
import org.eclipse.n4js.n4JS.ForStatement
import org.eclipse.n4js.n4JS.IdentifierRef
import org.eclipse.n4js.n4JS.IntLiteral
import org.eclipse.n4js.n4JS.PostfixExpression
import org.eclipse.n4js.n4JS.PostfixOperator
import org.eclipse.n4js.n4JS.RelationalExpression
import org.eclipse.n4js.n4JS.WhileStatement
import org.junit.Test

class ES_12_06_IterationStatementsEsprimaTest extends AbstractParserTest {

	@Test
	def void testDoWhile_01() {
		val script = 'do keep(); while (true)'.parseESSuccessfully
		val statement = script.scriptElements.head as DoStatement
		val keepStatement = statement.statement as ExpressionStatement
		val keep = keepStatement.expression as ParameterizedCallExpression
		val identifier = keep.target as IdentifierRef
		assertEquals("keep", identifier.text)
		val bool = statement.expression as BooleanLiteral
		assertEquals(true, bool.^true)
	}

	@Test
	def void testDoWhile_02() {
		val script = 'do keep(); while (true);'.parseESSuccessfully
		val statement = script.scriptElements.head as DoStatement
		val keepStatement = statement.statement as ExpressionStatement
		val keep = keepStatement.expression as ParameterizedCallExpression
		val identifier = keep.target as IdentifierRef
		assertEquals("keep", identifier.text)
		val bool = statement.expression as BooleanLiteral
		assertEquals(true, bool.^true)
	}

	@Test
	def void testDoWhile_03() {
		val script = 'do { x++; y--; } while (x < 10)'.parseESSuccessfully
		val statement = script.scriptElements.head as DoStatement
		val block = statement.statement as Block
		assertEquals(2, block.statements.size)
	}

	@Test
	def void testDoWhileSemicolonInsertion_01() {
		val script = '{ do { } while (false) false }'.parseESSuccessfully
		val block = script.scriptElements.head as Block
		val statement = block.statements.head as DoStatement
		val innerBlock = statement.statement as Block
		assertEquals(0, innerBlock.statements.size)
	}

	@Test
	def void testDoWhileSemicolonInsertion_02() {
		val script = '{ do { } while (false)false }'.parseESSuccessfully
		val block = script.scriptElements.head as Block
		val statement = block.statements.head as DoStatement
		val innerBlock = statement.statement as Block
		assertEquals(0, innerBlock.statements.size)
	}

	@Test
	def void testWhileStatement_01() {
		val script = 'while (false) doSomething()'.parseESSuccessfully
		val statement = script.scriptElements.head as WhileStatement
		val doSomethingStatement = statement.statement as ExpressionStatement
		val doSomething = doSomethingStatement.expression as ParameterizedCallExpression
		val identifier = doSomething.target as IdentifierRef
		assertEquals("doSomething", identifier.text)
		val bool = statement.expression as BooleanLiteral
		assertEquals(false, bool.^true)
	}

	@Test
	def void testWhileStatement_02() {
		val script = 'while (x < 10) { x++; y--; }'.parseESSuccessfully
		val statement = script.scriptElements.head as WhileStatement
		val block = statement.statement as Block
		assertEquals(2, block.statements.size)
	}

	@Test
	def void testForLoop_01() {
		val script = 'for(;;);'.parseESSuccessfully
		val statement = script.scriptElements.head as ForStatement
		val empty = statement.statement as EmptyStatement
		assertNotNull(empty)
		assertTrue(statement.varDecl.empty)
		assertNull(statement.initExpr)
		assertNull(statement.expression)
		assertNull(statement.updateExpr)
		assertFalse(statement.forIn)
		assertFalse(statement.forOf)
		assertTrue(statement.forPlain)
	}

	@Test
	def void testForLoop_02() {
		val script = 'for(;;){}'.parseESSuccessfully
		val statement = script.scriptElements.head as ForStatement
		val block = statement.statement as Block
		assertTrue(block.statements.empty)
		assertTrue(statement.varDecl.empty)
		assertNull(statement.initExpr)
		assertNull(statement.expression)
		assertNull(statement.updateExpr)
		assertFalse(statement.forIn)
		assertFalse(statement.forOf)
		assertTrue(statement.forPlain)
	}

	@Test
	def void testForLoop_03() {
		val script = 'for(x = 0;;);'.parseESSuccessfully
		val statement = script.scriptElements.head as ForStatement
		val empty = statement.statement as EmptyStatement
		assertNotNull(empty)
		assertTrue(statement.varDecl.empty)
		val init = statement.initExpr as AssignmentExpression
		val x = init.lhs as IdentifierRef
		assertEquals('x', x.text)
		val value = init.rhs as IntLiteral
		assertEquals(0, value.toInt)
		assertNull(statement.expression)
		assertNull(statement.updateExpr)
		assertFalse(statement.forIn)
		assertFalse(statement.forOf)
		assertTrue(statement.forPlain)
	}

	@Test
	def void testForLoop_04() {
		val script = 'for(var x = 0;;);'.parseESSuccessfully
		val statement = script.scriptElements.head as ForStatement
		val empty = statement.statement as EmptyStatement
		assertNotNull(empty)
		val varDecl = statement.varDecl.head
		assertEquals('x', varDecl.name)
		val value = varDecl.expression as IntLiteral
		assertEquals(0, value.toInt)
		assertNull(statement.expression)
		assertNull(statement.updateExpr)
		assertFalse(statement.forIn)
		assertFalse(statement.forOf)
		assertTrue(statement.forPlain)
	}

	@Test
	def void testForLoop_05() {
		val script = 'for(let x = 0;;);'.parseESSuccessfully
		val statement = script.scriptElements.head as ForStatement
		val empty = statement.statement as EmptyStatement
		assertNotNull(empty)
		assertNull(statement.expression)
		assertNull(statement.updateExpr)
		assertFalse(statement.forIn)
		assertFalse(statement.forOf)
		assertTrue(statement.forPlain)
	}

	@Test
	def void testForLoop_06() {
		val script = 'for(var x = 0, y = 1;;);'.parseESSuccessfully
		val statement = script.scriptElements.head as ForStatement
		val empty = statement.statement as EmptyStatement
		assertNotNull(empty)
		val xDecl = statement.varDecl.head
		assertEquals('x', xDecl.name)
		val x = xDecl.expression as IntLiteral
		assertEquals(0, x.toInt)
		val yDecl = statement.varDecl.last
		assertEquals('y', yDecl.name)
		val y = yDecl.expression as IntLiteral
		assertEquals(1bd, y.value)
		assertNull(statement.expression)
		assertNull(statement.updateExpr)
		assertFalse(statement.forIn)
		assertFalse(statement.forOf)
		assertTrue(statement.forPlain)
	}

	@Test
	def void testForLoop_07() {
		val script = 'for(x = 0; x < 42;);'.parseESSuccessfully
		val statement = script.scriptElements.head as ForStatement
		val empty = statement.statement as EmptyStatement
		assertNotNull(empty)
		assertTrue(statement.varDecl.empty)
		val init = statement.initExpr as AssignmentExpression
		val x = init.lhs as IdentifierRef
		assertEquals('x', x.text)
		val value = init.rhs as IntLiteral
		assertEquals(0, value.toInt)
		val comparison = statement.expression as RelationalExpression
		assertEquals("x", (comparison.lhs as IdentifierRef).text)
		assertEquals(42, (comparison.rhs as IntLiteral).toInt)
		assertNull(statement.updateExpr)
		assertFalse(statement.forIn)
		assertFalse(statement.forOf)
		assertTrue(statement.forPlain)
	}

	@Test
	def void testForLoop_08() {
		val script = 'for(x = 0; x < 42; x++);'.parseESSuccessfully
		val statement = script.scriptElements.head as ForStatement
		val empty = statement.statement as EmptyStatement
		assertNotNull(empty)
		assertTrue(statement.varDecl.empty)
		assertNotNull(statement.initExpr)
		assertNotNull(statement.expression)
		val postfix = statement.updateExpr as PostfixExpression
		assertEquals(PostfixOperator.INC, postfix.op)
		assertEquals("x", (postfix.expression as IdentifierRef).text)
		assertFalse(statement.forIn)
		assertFalse(statement.forOf)
		assertTrue(statement.forPlain)
	}

	@Test
	def void testForLoop_09() {
		val script = 'for(x = 0; x < 42; x++) process(x);'.parseESSuccessfully
		val statement = script.scriptElements.head as ForStatement
		val body = statement.statement as ExpressionStatement
		val process = body.expression as ParameterizedCallExpression
		assertEquals('process', (process.target as IdentifierRef).text)
		assertFalse(statement.forIn)
		assertFalse(statement.forOf)
		assertTrue(statement.forPlain)
	}

	@Test
	def void testForLoop_typeDecl() {
		val script = 'for(var x: any = 0; ;) {}'.parseESSuccessfully
		val statement = script.scriptElements.head as ForStatement
		val x = statement.varDecl.head
		assertNotNull(x.declaredTypeRefInAST)
	}


	@Test
	def void testForInLoop_01() {
		val script = 'for(x in list) process(x);'.parseESSuccessfully
		val statement = script.scriptElements.head as ForStatement
		val body = statement.statement as ExpressionStatement
		val process = body.expression as ParameterizedCallExpression
		assertEquals('process', (process.target as IdentifierRef).text)
		assertTrue(statement.forIn)
		assertFalse(statement.forOf)
		assertFalse(statement.forPlain)
	}

	@Test
	def void testForInLoop_02() {
		val script = 'for(var x in list) process(x);'.parseESSuccessfully
		val statement = script.scriptElements.head as ForStatement
		assertTrue(statement.forIn)
		assertFalse(statement.forOf)
		assertFalse(statement.forPlain)
	}

	@Test
	def void testForInLoop_03() {
		val script = 'for(var x = 42 in list) process(x);'.parseESSuccessfully
		val statement = script.scriptElements.head as ForStatement
		assertTrue(statement.forIn)
		assertFalse(statement.forOf)
		assertFalse(statement.forPlain)
	}

	@Test
	def void testForInLoop_04() {
		val script = 'for(let x in list) process(x);'.parseESSuccessfully
		val statement = script.scriptElements.head as ForStatement
		assertTrue(statement.forIn)
		assertFalse(statement.forOf)
		assertFalse(statement.forPlain)
	}

	@Test
	def void testForInLoop_05() {
		val script = 'for (var i = function() { return 10 in [] } in list) process(x);'.parseESSuccessfully
		val statement = script.scriptElements.head as ForStatement
		assertTrue(statement.forIn)
		assertFalse(statement.forOf)
		assertFalse(statement.forPlain)
	}

	@Test
	def void testForInLoop_06() {
		val script = 'for (var i = function() {} in list) process(x);'.parseESSuccessfully
		val statement = script.scriptElements.head as ForStatement
		assertTrue(statement.forIn)
		assertFalse(statement.forOf)
		assertFalse(statement.forPlain)
	}

	@Test
	def void testForInLoop_07() {
		'''
			const Object_keys = typeof Object.keys === 'function'
			    ? Object.keys
			    : function (obj) {
			        var keys: Array<string> = [];
			        for (var key in obj) keys.push(key as string);
			        return keys;
			    }
			;
		'''.parseESSuccessfully
	}

	@Test
	def void testForOfLoop_01() {
		val script = 'for(x of list) process(x);'.parseESSuccessfully
		val statement = script.scriptElements.head as ForStatement
		val body = statement.statement as ExpressionStatement
		val process = body.expression as ParameterizedCallExpression
		assertEquals('process', (process.target as IdentifierRef).text)
		assertFalse(statement.forIn)
		assertTrue(statement.forOf)
		assertFalse(statement.forPlain)
	}

	@Test
	def void testForOfLoop_02() {
		val script = 'for(var x of list) process(x);'.parseESSuccessfully
		val statement = script.scriptElements.head as ForStatement
		assertFalse(statement.forIn)
		assertTrue(statement.forOf)
		assertFalse(statement.forPlain)
	}

	@Test
	def void testForOfLoop_03() {
		val script = 'for(var x = 42 of list) process(x);'.parseESSuccessfully
		val statement = script.scriptElements.head as ForStatement
		assertFalse(statement.forIn)
		assertTrue(statement.forOf)
		assertFalse(statement.forPlain)
	}

	@Test
	def void testForOfLoop_04() {
		val script = 'for(let x of list) process(x);'.parseESSuccessfully
		val statement = script.scriptElements.head as ForStatement
		assertFalse(statement.forIn)
		assertTrue(statement.forOf)
	}

	@Test
	def void testForOfLoop_05() {
		val script = 'for (var i = function() { return 10 in [] } of list) process(x);'.parseESSuccessfully
		val statement = script.scriptElements.head as ForStatement
		assertFalse(statement.forIn)
		assertTrue(statement.forOf)
		assertFalse(statement.forPlain)
	}

	@Test
	def void testForOfLoop_06() {
		val script = 'for (var i = function() {} of list) process(x);'.parseESSuccessfully
		val statement = script.scriptElements.head as ForStatement
		assertFalse(statement.forIn)
		assertTrue(statement.forOf)
		assertFalse(statement.forPlain)
	}

	@Test
	def void testForOfLoop_07() {
		val script = 'for (var i = "" of ["a","b"]) process(x);'.parseESSuccessfully
		val statement = script.scriptElements.head as ForStatement
		assertFalse(statement.forIn)
		assertTrue(statement.forOf)
		assertFalse(statement.forPlain)
	}

	@Test
	def void testForOfLoop_08() {
		val script = 'for (of of []) process(x);'.parseESSuccessfully
		val statement = script.scriptElements.head as ForStatement
		assertFalse(statement.forIn)
		assertTrue(statement.forOf)
		assertFalse(statement.forPlain)
		// first 'of' should be treated as a variable name
		assertTrue(statement.initExpr instanceof IdentifierRef);
	}

	@Test
	def void testForOfLoop_09() {
		val statement = 'for (var of of []) process(x);'.parseESSuccessfully.scriptElements.head as ForStatement
		assertFalse(statement.forIn)
		assertTrue(statement.forOf)
		assertFalse(statement.forPlain)
		assertEquals('of', statement.varDecl.head.name);
	}

	@Test
	def void testForOfLoop_10() {
		val statement = 'for (var of = "" of []) process(x);'.parseESSuccessfully.scriptElements.head as ForStatement
		assertFalse(statement.forIn)
		assertTrue(statement.forOf)
		assertFalse(statement.forPlain)
		assertEquals('of', statement.varDecl.head.name);
	}

	@Test
	def void testForOfLoop_11() {
		val statement = 'for (let of of []) process(x);'.parseESSuccessfully.scriptElements.head as ForStatement
		assertFalse(statement.forIn)
		assertTrue(statement.forOf)
		assertFalse(statement.forPlain)
		assertEquals('of', statement.varDecl.head.name);
	}

	@Test
	def void testForOfLoop_12() {
		val statement = 'for (let of = "" of []) process(x);'.parseESSuccessfully.scriptElements.head as ForStatement
		assertFalse(statement.forIn)
		assertTrue(statement.forOf)
		assertFalse(statement.forPlain)
		assertEquals('of', statement.varDecl.head.name);
	}

	@Test
	def void testForOfLoop_13() {
		val statement = 'for (const of of []) process(x);'.parseESSuccessfully.scriptElements.head as ForStatement
		assertFalse(statement.forIn)
		assertTrue(statement.forOf)
		assertFalse(statement.forPlain)
		assertEquals('of', statement.varDecl.head.name);
	}

	@Test
	def void testForOfLoop_14() {
		val statement = 'for (const of = "" of []) process(x);'.parseESSuccessfully.scriptElements.head as ForStatement
		assertFalse(statement.forIn)
		assertTrue(statement.forOf)
		assertFalse(statement.forPlain)
		assertEquals('of', statement.varDecl.head.name);
	}

	@Test
	def void testForOfLoop_15() {
		val statement = 'for(var v = new X() of list) {};'.parseESSuccessfully.scriptElements.head as ForStatement
		assertFalse(statement.forIn)
		assertTrue(statement.forOf)
		assertFalse(statement.forPlain)
		assertEquals('v', statement.varDecl.head.name);
	}

	@Test
	def void testForOfLoop_17() {
		val statement = '''
			for(var [a2,b2,c2] of arr2) {}
		'''.parseESSuccessfully.scriptElements.head as ForStatement
		assertFalse(statement.forIn)
		assertTrue(statement.forOf)
		assertFalse(statement.forPlain)
		assertEquals('a2', statement.varDecl.head.name);
	}

	// NOTE: strict mode is validated in the validation, not in the parser
	@Test
	def void testForOfLoop_16() {
		val statement = '"use strict"; for(var v = new X() of list) {}'.parseESWithError.scriptElements.last as ForStatement
		assertFalse(statement.forIn)
		assertTrue(statement.forOf)
		assertFalse(statement.forPlain)
		assertEquals('v', statement.varDecl.head.name);
	}
}
