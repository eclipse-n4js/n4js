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

import org.junit.Test;

public class ES_07_08_3_NumericLiteralsErrorTest extends AbstractParserTest {

	@Test
	public void testAsUnicode() {
		parseESWithError("\\u0030");
	}

	@Test
	public void testZero() {
		parseESWithError("0i");
	}

	@Test
	public void testFourtyTwo() {
		parseESWithError("42a");
	}

	@Test
	public void testThree() {
		parseESWithError("3_");
	}

	@Test
	public void testFive() {
		parseESWithError("5$");
	}

	@Test
	public void testDot14() {
		parseESWithError(".14i");
	}

	@Test
	public void testPi() {
		parseESWithError("3.14159m");
	}

	@Test
	public void testLargeDecimal() {
		parseESWithError("6.02214179e+23a");
	}

	@Test
	public void testSmallDecimal() {
		parseESWithError("1.492417830e-10a");
	}

	@Test
	public void testHexInteger_01() {
		parseESWithError("0x0G");
	}

	@Test
	public void testHexInteger_02() {
		parseESWithError("0x0$;");
	}

	@Test
	public void testScientificInteger() {
		parseESWithError("0e+100_");
	}

	@Test
	public void testHexAbc() {
		parseESWithError("0xabch;");
	}

	@Test
	public void testHexDef() {
		parseESWithError("0xdefi;");
	}

	@Test
	public void testHex1A() {
		parseESWithError("0X1A_;");
	}

	@Test
	public void testHex10() {
		parseESWithError("0x10$;");
	}

	@Test
	public void testHex100() {
		parseESWithError("0x100q;");
	}

	@Test
	public void testHex04() {
		parseESWithError("0X04_;");
	}

	@Test
	public void testBinary1() {
		parseESWithError("0b1_;");
	}

	@Test
	public void testBinary2() {
		parseESWithError("0b");
	}

	@Test
	public void testBinary3() {
		parseESWithError("0b_;");
	}

	@Test
	public void testBinary4() {
		parseESWithError("0b2;");
	}

	@Test
	public void testOctal02() {
		parseESWithError("0o2$;");
	}

	@Test
	public void testOctal0129() {
		parseESWithError("\"use strict\";0o129;");
	}

	@Test
	public void testOctal0129NonStrict() {
		parseESWithError("0o129;");
	}

	@Test
	public void testOctal0012() {
		parseESWithError("0o012o;");
	}

	@Test
	public void testLegacyOctal02() {
		parseESWithError("02$;");
	}

	@Test
	public void testLegacyOctal0012() {
		parseESWithError("0012o;");
	}

	// NOTE: strict mode is validated in the validation, not in the parser
	@Test
	public void testLegacyOctal012() {
		parseESWithError("\"use strict\";0129;");
	}

}
