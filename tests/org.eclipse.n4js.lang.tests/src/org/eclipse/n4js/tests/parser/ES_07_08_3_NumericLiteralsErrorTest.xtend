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
		'\\u0030'.parseESWithError
	}

	@Test
	def void testZero() {
		'0i'.parseESWithError
	}

	@Test
	def void testFourtyTwo() {
		'42a'.parseESWithError
	}

	@Test
	def void testThree() {
		'3_'.parseESWithError
	}

	@Test
	def void testFive() {
		'5$'.parseESWithError
	}


	@Test
	def void testDot14() {
		'.14i'.parseESWithError
	}

	@Test
	def void testPi() {
		'3.14159m'.parseESWithError
	}

	@Test
	def void testLargeDecimal() {
		'6.02214179e+23a'.parseESWithError
	}

	@Test
	def void testSmallDecimal() {
		'1.492417830e-10a'.parseESWithError
	}

	@Test
	def void testHexInteger_01() {
		'0x0G'.parseESWithError
	}

	@Test
	def void testHexInteger_02() {
		'0x0$;'.parseESWithError
	}

	@Test
	def void testScientificInteger() {
		'0e+100_'.parseESWithError
	}

	@Test
	def void testHexAbc() {
		'0xabch;'.parseESWithError
	}

	@Test
	def void testHexDef() {
		'0xdefi;'.parseESWithError
	}

	@Test
	def void testHex1A() {
		'0X1A_;'.parseESWithError
	}

	@Test
	def void testHex10() {
		'0x10$;'.parseESWithError
	}

	@Test
	def void testHex100() {
		'0x100q;'.parseESWithError
	}

	@Test
	def void testHex04() {
		'0X04_;'.parseESWithError
	}

	@Test
	def void testBinary1() {
		'0b1_;'.parseESWithError
	}

	@Test
	def void testBinary2() {
		'0b'.parseESWithError
	}

	@Test
	def void testBinary3() {
		'0b_;'.parseESWithError
	}

	@Test
	def void testBinary4() {
		'0b2;'.parseESWithError
	}

	@Test
	def void testOctal02() {
		'0o2$;'.parseESWithError
	}

	@Test
	def void testOctal0129() {
		'"use strict";0o129;'.parseESWithError
	}

	@Test
	def void testOctal0129NonStrict() {
		'0o129;'.parseESWithError
	}

	@Test
	def void testOctal0012() {
		'0o012o;'.parseESWithError
	}

	@Test
	def void testLegacyOctal02() {
		'02$;'.parseESWithError
	}

	@Test
	def void testLegacyOctal0012() {
		'0012o;'.parseESWithError
	}

	// NOTE: strict mode is validated in the validation, not in the parser
	@Test
	def void testLegacyOctal012() {
		'"use strict";0129;'.parseESWithError
	}

}
