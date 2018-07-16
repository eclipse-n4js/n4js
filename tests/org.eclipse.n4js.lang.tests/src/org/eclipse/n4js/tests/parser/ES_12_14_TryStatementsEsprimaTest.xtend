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

import org.eclipse.n4js.n4JS.IdentifierRef
import org.eclipse.n4js.n4JS.TryStatement
import org.junit.Test
import org.eclipse.n4js.n4JS.ExpressionStatement
import org.eclipse.n4js.n4JS.ParameterizedCallExpression
import org.eclipse.n4js.n4JS.FunctionDeclaration

class ES_12_14_TryStatementsEsprimaTest extends AbstractParserTest {

	@Test
	def void testThrow_Simple() {
		val script = 'try { } catch (e) { }'.parseESSuccessfully
		val tryStmt = script.scriptElements.head as TryStatement
		val block = tryStmt.block
		assertTrue(block.statements.empty)
		assertNull(tryStmt.^finally)
		val catchBlock = tryStmt.^catch
		assertEquals("e", catchBlock.catchVariable.name)
		assertTrue(catchBlock.block.statements.empty)
	}

	@Test
	def void testThrow_withType() {
		val script = 'try { } catch (e: any) { }'.parseESSuccessfully
		val tryStmt = script.scriptElements.head as TryStatement
		val block = tryStmt.block
		assertTrue(block.statements.empty)
		assertNull(tryStmt.^finally)
		val catchBlock = tryStmt.^catch
		assertEquals("e", catchBlock.catchVariable.name)
		assertTrue(catchBlock.block.statements.empty)
	}

	@Test
	def void testThrow_SimpleEval() {
		val script = 'try { } catch (eval) { }'.parseESSuccessfully
		val tryStmt = script.scriptElements.head as TryStatement
		val block = tryStmt.block
		assertTrue(block.statements.empty)
		assertNull(tryStmt.^finally)
		val catchBlock = tryStmt.^catch
		assertEquals("eval", catchBlock.catchVariable.name)
		assertTrue(catchBlock.block.statements.empty)
	}

	@Test
	def void testThrow_SimpleArguments() {
		val script = 'try { } catch (arguments) { }'.parseESSuccessfully
		val tryStmt = script.scriptElements.head as TryStatement
		val block = tryStmt.block
		assertTrue(block.statements.empty)
		assertNull(tryStmt.^finally)
		val catchBlock = tryStmt.^catch
		assertEquals("arguments", catchBlock.catchVariable.name)
		assertTrue(catchBlock.block.statements.empty)
	}

	@Test
	def void testThrow_WithCatchBlock() {
		val script = 'try { } catch (e) { say(e) }'.parseESSuccessfully
		val tryStmt = script.scriptElements.head as TryStatement
		val block = tryStmt.block
		assertTrue(block.statements.empty)
		assertNull(tryStmt.^finally)
		val catchBlock = tryStmt.^catch
		assertEquals("e", catchBlock.catchVariable.name)
		val callExpr = (catchBlock.block.statements.head as ExpressionStatement).expression as ParameterizedCallExpression
		assertEquals("say", (callExpr.target as IdentifierRef).text)
		assertEquals("e", (callExpr.arguments.head.expression as IdentifierRef).text)
	}

	@Test
	def void testThrow_WithFinallyBlock() {
		val script = 'try { } finally { cleanup(stuff) }'.parseESSuccessfully
		val tryStmt = script.scriptElements.head as TryStatement
		val block = tryStmt.block
		assertTrue(block.statements.empty)

		val catchBlock = tryStmt.^catch
		assertNull(catchBlock)

		val finallyBlock = tryStmt.^finally
		val callExpr = (finallyBlock.block.statements.head as ExpressionStatement).expression as ParameterizedCallExpression
		assertEquals("cleanup", (callExpr.target as IdentifierRef).text)
		assertEquals("stuff", (callExpr.arguments.head.expression as IdentifierRef).text)
	}

	@Test
	def void testThrow_WithCatchBlock2() {
		val script = 'try { doThat(); } catch (e) { say(e) }'.parseESSuccessfully
		val tryStmt = script.scriptElements.head as TryStatement
		val block = tryStmt.block

		assertEquals(1, block.statements.size)
		val callExpr0 = (block.statements.head as ExpressionStatement).expression as ParameterizedCallExpression
		assertEquals("doThat", (callExpr0.target as IdentifierRef).text)
		assertTrue(callExpr0.arguments.empty)

		assertNull(tryStmt.^finally)
		val catchBlock = tryStmt.^catch
		assertEquals("e", catchBlock.catchVariable.name)
		val callExpr = (catchBlock.block.statements.head as ExpressionStatement).expression as ParameterizedCallExpression
		assertEquals("say", (callExpr.target as IdentifierRef).text)
		assertEquals("e", (callExpr.arguments.head.expression as IdentifierRef).text)
	}

	@Test
	def void testThrow_WithCatchAndFinallyBlock() {
		val script = 'try { doThat(); } catch (e) { say(e) } finally { cleanup(stuff) }'.parseESSuccessfully
		val tryStmt = script.scriptElements.head as TryStatement
		val block = tryStmt.block

		assertEquals(1, block.statements.size)
		val callExpr0 = (block.statements.head as ExpressionStatement).expression as ParameterizedCallExpression
		assertEquals("doThat", (callExpr0.target as IdentifierRef).text)
		assertTrue(callExpr0.arguments.empty)

		val catchBlock = tryStmt.^catch
		assertEquals("e", catchBlock.catchVariable.name)
		val callExpr = (catchBlock.block.statements.head as ExpressionStatement).expression as ParameterizedCallExpression
		assertEquals("say", (callExpr.target as IdentifierRef).text)
		assertEquals("e", (callExpr.arguments.head.expression as IdentifierRef).text)

		val finallyBlock = tryStmt.^finally
		val callExpr1 = (finallyBlock.block.statements.head as ExpressionStatement).expression as ParameterizedCallExpression
		assertEquals("cleanup", (callExpr1.target as IdentifierRef).text)
		assertEquals("stuff", (callExpr1.arguments.head.expression as IdentifierRef).text)
	}

	@Test
	def void testFunctionInCatchBlockStrictMode() {
		val script = '"use strict"; try {} catch (e) { function x() {} }'.parseESSuccessfully
		val tryStmt = script.scriptElements.last as TryStatement

		val catchBlock = tryStmt.^catch
		assertEquals("e", catchBlock.catchVariable.name)
		val funDecl = catchBlock.block.statements.head as FunctionDeclaration
		assertEquals("x", funDecl.name)
	}

	@Test
	def void testFunctionInFinallyBlockStrictMode() {
		'"use strict"; try {} finally { function x() {} }'.parseESWithError
	}

	@Test
	def void testFunctionInFinallyBlock() {
		'try {} finally { function x() {} }'.parseESSuccessfully
	}


}
