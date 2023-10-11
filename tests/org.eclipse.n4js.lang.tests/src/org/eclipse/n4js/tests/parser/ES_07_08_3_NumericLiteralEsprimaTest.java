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
package org.eclipse.n4js.tests.parser;

import org.eclipse.n4js.n4JS.BinaryIntLiteral;
import org.eclipse.n4js.n4JS.DoubleLiteral;
import org.eclipse.n4js.n4JS.ExpressionStatement;
import org.eclipse.n4js.n4JS.HexIntLiteral;
import org.eclipse.n4js.n4JS.IntLiteral;
import org.eclipse.n4js.n4JS.LegacyOctalIntLiteral;
import org.eclipse.n4js.n4JS.OctalIntLiteral;
import org.eclipse.n4js.n4JS.ScientificIntLiteral;
import org.eclipse.n4js.n4JS.Script;
import org.junit.Test;

public class ES_07_08_3_NumericLiteralEsprimaTest extends AbstractParserTest {

	@Test
	public void testZero() {
		Script script = parseESSuccessfully("0");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		IntLiteral zero = (IntLiteral) statement.getExpression();
		assertEquals(0, zero.toInt());
	}

	@Test
	public void testFourtyTwo() {
		Script script = parseESSuccessfully("42");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		IntLiteral fourtyTwo = (IntLiteral) statement.getExpression();
		assertEquals(42, fourtyTwo.toInt());
	}

	@Test
	public void testThree() {
		Script script = parseESSuccessfully("3");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		IntLiteral three = (IntLiteral) statement.getExpression();
		assertEquals(3, three.toInt());
	}

	@Test
	public void testFive() {
		Script script = parseESSuccessfully("5");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		IntLiteral five = (IntLiteral) statement.getExpression();
		assertEquals(5, five.toInt());
	}

	@Test
	public void testDot14() {
		Script script = parseESSuccessfully(".14");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		DoubleLiteral number = (DoubleLiteral) statement.getExpression();
		assertEquals(0.14, number.toDouble(), 0.00001);
	}

	@Test
	public void testPi() {
		Script script = parseESSuccessfully("3.14159");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		DoubleLiteral number = (DoubleLiteral) statement.getExpression();
		assertEquals(3.14159, number.toDouble(), 0.00001);
	}

	@Test
	public void testLargeDecimal() {
		Script script = parseESSuccessfully("6.02214179e+23");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		DoubleLiteral number = (DoubleLiteral) statement.getExpression();
		assertEquals(6.02214179e+23, number.toDouble(), 1);
	}

	@Test
	public void testSmallDecimal() {
		Script script = parseESSuccessfully("1.492417830e-10");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		DoubleLiteral number = (DoubleLiteral) statement.getExpression();
		assertEquals(1.492417830e-10, number.toDouble(), 1e-15);
	}

	@Test
	public void testHexInteger_01() {
		Script script = parseESSuccessfully("0x0");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		HexIntLiteral number = (HexIntLiteral) statement.getExpression();
		assertEquals(0, number.toInt());
	}

	@Test
	public void testHexInteger_02() {
		Script script = parseESSuccessfully("0x0;");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		HexIntLiteral number = (HexIntLiteral) statement.getExpression();
		assertEquals(0, number.toInt());
	}

	@Test
	public void testScientificInteger() {
		Script script = parseESSuccessfully("0e+100");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		ScientificIntLiteral number = (ScientificIntLiteral) statement.getExpression();
		assertEquals(0, number.toInt());
	}

	@Test
	public void testHexAbc() {
		Script script = parseESSuccessfully("0xabc;");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		HexIntLiteral number = (HexIntLiteral) statement.getExpression();
		assertEquals(0xabc, number.toInt());
	}

	@Test
	public void testHexDef() {
		Script script = parseESSuccessfully("0xdef;");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		HexIntLiteral number = (HexIntLiteral) statement.getExpression();
		assertEquals(0xdef, number.toInt());
	}

	@Test
	public void testHex1A() {
		Script script = parseESSuccessfully("0X1A;");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		HexIntLiteral number = (HexIntLiteral) statement.getExpression();
		assertEquals(0x1A, number.toInt());
	}

	@Test
	public void testHex10() {
		Script script = parseESSuccessfully("0x10;");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		HexIntLiteral number = (HexIntLiteral) statement.getExpression();
		assertEquals(0x10, number.toInt());
	}

	@Test
	public void testHex100() {
		Script script = parseESSuccessfully("0x100;");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		HexIntLiteral number = (HexIntLiteral) statement.getExpression();
		assertEquals(0x100, number.toInt());
	}

	@Test
	public void testHex04() {
		Script script = parseESSuccessfully("0X04;");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		HexIntLiteral number = (HexIntLiteral) statement.getExpression();
		assertEquals(0x4, number.toInt());
	}

	@Test
	public void testOctal02() {
		Script script = parseESSuccessfully("0o2;");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		OctalIntLiteral number = (OctalIntLiteral) statement.getExpression();
		assertEquals(2, number.toInt());
	}

	@Test
	public void testOctal012() {
		Script script = parseESSuccessfully("0o12;");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		OctalIntLiteral number = (OctalIntLiteral) statement.getExpression();
		assertEquals(10, number.toInt());
	}

	@Test
	public void testOctal0012() {
		Script script = parseESSuccessfully("0o012;");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		OctalIntLiteral number = (OctalIntLiteral) statement.getExpression();
		assertEquals(10, number.toInt());
	}

	@Test
	public void testOctal0129() {
		Script script = parseESSuccessfully("0O127;");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		OctalIntLiteral number = (OctalIntLiteral) statement.getExpression();
		assertEquals(Integer.parseInt("127", 8), number.toInt());
	}

	@Test
	public void testOctal0() {
		Script script = parseESSuccessfully("0O0");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		OctalIntLiteral number = (OctalIntLiteral) statement.getExpression();
		assertEquals(0, number.toInt());
	}

	@Test
	public void testLegacyOctal02() {
		Script script = parseESSuccessfully("02;");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		LegacyOctalIntLiteral number = (LegacyOctalIntLiteral) statement.getExpression();
		assertEquals(2, number.toInt());
	}

	@Test
	public void testLegacyOctal012() {
		Script script = parseESSuccessfully("012;");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		LegacyOctalIntLiteral number = (LegacyOctalIntLiteral) statement.getExpression();
		assertEquals(10, number.toInt());
	}

	@Test
	public void testLegacyOctal0012() {
		Script script = parseESSuccessfully("0012;");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		LegacyOctalIntLiteral number = (LegacyOctalIntLiteral) statement.getExpression();
		assertEquals(10, number.toInt());
	}

	@Test
	public void testLegacyOctal0129() {
		Script script = parseESSuccessfully("0129;");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		LegacyOctalIntLiteral number = (LegacyOctalIntLiteral) statement.getExpression();
		assertEquals(129, number.toInt());
	}

	@Test
	public void testBinary0() {
		Script script = parseESSuccessfully("0b0;");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		BinaryIntLiteral number = (BinaryIntLiteral) statement.getExpression();
		assertEquals(0, number.toInt());
	}

	@Test
	public void testBinary1() {
		Script script = parseESSuccessfully("0b1;");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		BinaryIntLiteral number = (BinaryIntLiteral) statement.getExpression();
		assertEquals(1, number.toInt());
	}

	@Test
	public void testBinary2() {
		Script script = parseESSuccessfully("0b10;");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		BinaryIntLiteral number = (BinaryIntLiteral) statement.getExpression();
		assertEquals(2, number.toInt());
	}

	@Test
	public void testBinary3() {
		Script script = parseESSuccessfully("0b11;");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		BinaryIntLiteral number = (BinaryIntLiteral) statement.getExpression();
		assertEquals(3, number.toInt());
	}

	@Test
	public void testBinary4() {
		Script script = parseESSuccessfully("0b10000000000;");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		BinaryIntLiteral number = (BinaryIntLiteral) statement.getExpression();
		assertEquals(1024, number.toInt());
	}

	@Test
	public void testBinary5() {
		Script script = parseESSuccessfully("0b10000000000000000000000000000000;");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		BinaryIntLiteral number = (BinaryIntLiteral) statement.getExpression();
		assertEquals(2147483648L, number.toLong());
	}

	@Test
	public void testBinary6() {
		Script script = parseESSuccessfully("0b01111111100000000000000000000000;");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		BinaryIntLiteral number = (BinaryIntLiteral) statement.getExpression();
		assertEquals(2139095040L, number.toLong());
	}

	@Test
	public void testBinary7() {
		Script script = parseESSuccessfully("0B00000000011111111111111111111111;");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		BinaryIntLiteral number = (BinaryIntLiteral) statement.getExpression();
		assertEquals(8388607, number.toLong());
	}

}
