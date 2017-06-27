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

import org.junit.Test

class ES_07_08_3_NumericLiteralsErrorTest extends AbstractParserTest {

	@Test
	def void testAsUnicode() {
		'\\u0030'.parseWithError
	}

	@Test
	def void testZero() {
		'0i'.parseWithError
	}

	@Test
	def void testFourtyTwo() {
		'42a'.parseWithError
	}

	@Test
	def void testThree() {
		'3_'.parseWithError
	}

	@Test
	def void testFive() {
		'5$'.parseWithError
	}


	@Test
	def void testDot14() {
		'.14i'.parseWithError
	}

	@Test
	def void testPi() {
		'3.14159m'.parseWithError
	}

	@Test
	def void testLargeDecimal() {
		'6.02214179e+23a'.parseWithError
	}

	@Test
	def void testSmallDecimal() {
		'1.492417830e-10a'.parseWithError
	}

	@Test
	def void testHexInteger_01() {
		'0x0G'.parseWithError
	}

	@Test
	def void testHexInteger_02() {
		'0x0$;'.parseWithError
	}

	@Test
	def void testScientificInteger() {
		'0e+100_'.parseWithError
	}

	@Test
	def void testHexAbc() {
		'0xabch;'.parseWithError
	}

	@Test
	def void testHexDef() {
		'0xdefi;'.parseWithError
	}

	@Test
	def void testHex1A() {
		'0X1A_;'.parseWithError
	}

	@Test
	def void testHex10() {
		'0x10$;'.parseWithError
	}

	@Test
	def void testHex100() {
		'0x100q;'.parseWithError
	}

	@Test
	def void testHex04() {
		'0X04_;'.parseWithError
	}

	@Test
	def void testBinary1() {
		'0b1_;'.parseWithError
	}

	@Test
	def void testBinary2() {
		'0b'.parseWithError
	}

	@Test
	def void testBinary3() {
		'0b_;'.parseWithError
	}

	@Test
	def void testBinary4() {
		'0b2;'.parseWithError
	}

	@Test
	def void testOctal02() {
		'0o2$;'.parseWithError
	}

	@Test
	def void testOctal0129() {
		'"use strict";0o129;'.parseWithError
	}

	@Test
	def void testOctal0129NonStrict() {
		'0o129;'.parseWithError
	}

	@Test
	def void testOctal0012() {
		'0o012o;'.parseWithError
	}

	@Test
	def void testLegacyOctal02() {
		'02$;'.parseWithError
	}

	@Test
	def void testLegacyOctal012() {
		'"use strict";0129;'.parseWithError
	}

	@Test
	def void testLegacyOctal0012() {
		'0012o;'.parseWithError
	}

}
