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
import org.junit.Test
import org.eclipse.n4js.n4JS.DoubleLiteral
import org.eclipse.n4js.n4JS.IntLiteral
import org.eclipse.n4js.n4JS.OctalIntLiteral
import org.eclipse.n4js.n4JS.HexIntLiteral
import org.eclipse.n4js.n4JS.ScientificIntLiteral
import org.eclipse.n4js.n4JS.BinaryIntLiteral
import org.eclipse.n4js.n4JS.LegacyOctalIntLiteral

class ES_07_08_3_NumericLiteralEsprimaTest extends AbstractParserTest {

	@Test
	def void testZero() {
		val script = '0'.parseSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val zero = statement.expression as IntLiteral
		assertEquals(0, zero.toInt)
	}

	@Test
	def void testFourtyTwo() {
		val script = '42'.parseSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val fourtyTwo = statement.expression as IntLiteral
		assertEquals(42, fourtyTwo.toInt)
	}

	@Test
	def void testThree() {
		val script = '3'.parseSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val three = statement.expression as IntLiteral
		assertEquals(3, three.toInt)
	}

	@Test
	def void testFive() {
		val script = '5'.parseSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val five = statement.expression as IntLiteral
		assertEquals(5, five.toInt)
	}

	@Test
	def void testDot14() {
		val script = '.14'.parseSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val number = statement.expression as DoubleLiteral
		assertEquals(0.14, number.toDouble, 0.00001)
	}

	@Test
	def void testPi() {
		val script = '3.14159'.parseSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val number = statement.expression as DoubleLiteral
		assertEquals(3.14159, number.toDouble, 0.00001)
	}

	@Test
	def void testLargeDecimal() {
		val script = '6.02214179e+23'.parseSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val number = statement.expression as DoubleLiteral
		assertEquals(6.02214179e+23, number.toDouble, 1)
	}

	@Test
	def void testSmallDecimal() {
		val script = '1.492417830e-10'.parseSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val number = statement.expression as DoubleLiteral
		assertEquals(1.492417830e-10, number.toDouble, 1e-15)
	}

	@Test
	def void testHexInteger_01() {
		val script = '0x0'.parseSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val number = statement.expression as HexIntLiteral
		assertEquals(0, number.toInt)
	}

	@Test
	def void testHexInteger_02() {
		val script = '0x0;'.parseSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val number = statement.expression as HexIntLiteral
		assertEquals(0, number.toInt)
	}

	@Test
	def void testScientificInteger() {
		val script = '0e+100'.parseSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val number = statement.expression as ScientificIntLiteral
		assertEquals(0, number.toInt)
	}

	@Test
	def void testHexAbc() {
		val script = '0xabc;'.parseSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val number = statement.expression as HexIntLiteral
		assertEquals(0xabc, number.toInt)
	}

	@Test
	def void testHexDef() {
		val script = '0xdef;'.parseSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val number = statement.expression as HexIntLiteral
		assertEquals(0xdef, number.toInt)
	}

	@Test
	def void testHex1A() {
		val script = '0X1A;'.parseSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val number = statement.expression as HexIntLiteral
		assertEquals(0x1A, number.toInt)
	}

	@Test
	def void testHex10() {
		val script = '0x10;'.parseSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val number = statement.expression as HexIntLiteral
		assertEquals(0x10, number.toInt)
	}

	@Test
	def void testHex100() {
		val script = '0x100;'.parseSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val number = statement.expression as HexIntLiteral
		assertEquals(0x100, number.toInt)
	}

	@Test
	def void testHex04() {
		val script = '0X04;'.parseSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val number = statement.expression as HexIntLiteral
		assertEquals(0x4, number.toInt)
	}

	@Test
	def void testOctal02() {
		val script = '0o2;'.parseSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val number = statement.expression as OctalIntLiteral
		assertEquals(2, number.toInt)
	}

	@Test
	def void testOctal012() {
		val script = '0o12;'.parseSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val number = statement.expression as OctalIntLiteral
		assertEquals(10, number.toInt)
	}

	@Test
	def void testOctal0012() {
		val script = '0o012;'.parseSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val number = statement.expression as OctalIntLiteral
		assertEquals(10, number.toInt)
	}

	@Test
	def void testOctal0129() {
		val script = '0O127;'.parseSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val number = statement.expression as OctalIntLiteral
		assertEquals(Integer.parseInt("127", 8), number.toInt)
	}

	@Test
	def void testOctal0() {
		val script = '0O0'.parseSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val number = statement.expression as OctalIntLiteral
		assertEquals(0, number.toInt)
	}

	@Test
	def void testLegacyOctal02() {
		val script = '02;'.parseSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val number = statement.expression as LegacyOctalIntLiteral
		assertEquals(2, number.toInt)
	}

	@Test
	def void testLegacyOctal012() {
		val script = '012;'.parseSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val number = statement.expression as LegacyOctalIntLiteral
		assertEquals(10, number.toInt)
	}

	@Test
	def void testLegacyOctal0012() {
		val script = '0012;'.parseSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val number = statement.expression as LegacyOctalIntLiteral
		assertEquals(10, number.toInt)
	}

	@Test
	def void testLegacyOctal0129() {
		val script = '0129;'.parseSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val number = statement.expression as LegacyOctalIntLiteral
		assertEquals(129, number.toInt)
	}

	@Test
	def void testBinary0() {
		val script = '0b0;'.parseSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val number = statement.expression as BinaryIntLiteral
		assertEquals(0, number.toInt)
	}

	@Test
	def void testBinary1() {
		val script = '0b1;'.parseSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val number = statement.expression as BinaryIntLiteral
		assertEquals(1, number.toInt)
	}

	@Test
	def void testBinary2() {
		val script = '0b10;'.parseSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val number = statement.expression as BinaryIntLiteral
		assertEquals(2, number.toInt)
	}

	@Test
	def void testBinary3() {
		val script = '0b11;'.parseSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val number = statement.expression as BinaryIntLiteral
		assertEquals(3, number.toInt)
	}

	@Test
	def void testBinary4() {
		val script = '0b10000000000;'.parseSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val number = statement.expression as BinaryIntLiteral
		assertEquals(1024, number.toInt)
	}

	@Test
	def void testBinary5() {
		val script = '0b10000000000000000000000000000000;'.parseSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val number = statement.expression as BinaryIntLiteral
		assertEquals(2147483648L, number.toLong)
	}

	@Test
	def void testBinary6() {
		val script = '0b01111111100000000000000000000000;'.parseSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val number = statement.expression as BinaryIntLiteral
		assertEquals(2139095040L, number.toLong)
	}

	@Test
	def void testBinary7() {
		val script = '0B00000000011111111111111111111111;'.parseSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val number = statement.expression as BinaryIntLiteral
		assertEquals(8388607, number.toLong)
	}

}
