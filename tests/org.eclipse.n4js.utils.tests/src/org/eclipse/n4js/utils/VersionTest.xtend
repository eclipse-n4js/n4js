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
package org.eclipse.n4js.utils

import org.junit.Test

import static org.junit.Assert.*

/**
 * Basic version tests.
 */
class VersionTest {

	@Test
	def void testParseNullExpectMissing() {
		Version.createFromString(null).assertMissing;
	}

	@Test
	def void testParseEmptyStringExpectMissing() {
		Version.createFromString('').assertMissing;
	}

	@Test
	def void testParseZeroDotStringExpectMissing() {
		Version.createFromString('1').assertMissing;
	}

	@Test
	def void testParseOneDotStringExpectMissing() {
		Version.createFromString('1.2').assertMissing;
	}

	@Test
	def void testParseTwoDotsString() {
		Version.createFromString('1.2.3').assertEquals(new Version(1, 2, 3));
	}

	@Test
	def void testParseMoreThanTwoDotsString() {
		Version.createFromString('1.2.3.4.5').assertEquals(new Version(1, 2, 3, '4.5'));
	}

	@Test
	def void testParseNonNumberExpectMissing() {
		Version.createFromString('1.a.3').assertMissing;
	}

	@Test
	def void testFindClosestMatchNullVersionsExpectMissing() {
		Version.findClosestMatching(null, null).assertMissing;
	}

	@Test
	def void testFindClosestMatchEmptyVersionsExpectMissing() {
		Version.findClosestMatching(null, null).assertMissing;
	}

	@Test
	def void testFindClosestMatchNullToFindExpectMissing() {
		Version.findClosestMatching(#[new Version(1, 2, 3)], null).assertMissing;
	}

	@Test
	def void testFindClosestMatchMissingToFindExpectMissing() {
		Version.findClosestMatching(#[new Version(1, 2, 3)], Version.MISSING).assertMissing;
	}

	@Test
	def void testFindClosestExactMatchWithSingleElement() {
		Version.findClosestMatching(#[new Version(1, 2, 3)], new Version(1, 2, 3)).assertEquals(new Version(1, 2, 3));
	}

	@Test
	def void testFindClosestExactMatchWithMultipleElement() {
		val versions = #[new Version(2, 3, 4), new Version(0, 1, 2), new Version(1, 2, 3)];
		Version.findClosestMatching(versions, new Version(1, 2, 3)).assertEquals(new Version(1, 2, 3));
	}

	@Test
	def void testFindClosestLessThanAnyElementsExpectMissing() {
		val versions = #[new Version(2, 3, 4), new Version(5, 1, 2), new Version(1, 2, 3)];
		Version.findClosestMatching(versions, new Version(0, 2, 3)).assertMissing;
	}

	@Test
	def void testFindClosestGreaterThanAnyElementsExpectGreatest() {
		val versions = #[new Version(2, 3, 4), new Version(5, 1, 2), new Version(1, 2, 3)];
		Version.findClosestMatching(versions, new Version(10, 2, 3)).assertEquals(new Version(5, 1, 2));
	}

	@Test
	def void testFindClosest() {
		val versions = #[new Version(2, 3, 4), new Version(5, 1, 2), new Version(1, 2, 3)];
		Version.findClosestMatching(versions, new Version(3, 3, 3)).assertEquals(new Version(2, 3, 4));
	}

	@Test
	def void testFindClosestWithQualifier() {
		val versions = #[new Version(2, 4, 0, "incomplete")];
		Version.findClosestMatching(versions, new Version(2, 4, 0)).assertEquals(new Version(2, 4, 0, "incomplete"));
	}

	@Test
	def void testParseWithLeadingNonDecimal() {
		Version.createFromString('v5.0.0').assertEquals(new Version(5, 0, 0));
	}

	@Test
	def void testParseWithLeadingNonDecimalWithWhitespace() {
		Version.createFromString('v 5.0.0').assertEquals(new Version(5, 0, 0));
	}

	@Test(expected = IllegalArgumentException)
	def void testNegativeMajroVersion() {
		new Version(-1, 1, 1);
	}

	@Test(expected = IllegalArgumentException)
	def void testNegativeMinorVersion() {
		new Version(1, -1, 1);
	}

	@Test(expected = IllegalArgumentException)
	def void testNegativeMicorVersion() {
		new Version(1, 1, -1);
	}

	@Test
	def void testIsValidNull() {
		assertFalse(Version.isValid(null));
	}

	@Test
	def void testIsValidMissing() {
		assertFalse(Version.isValid(Version.MISSING));
	}

	@Test
	def void testIsValidExisting() {
		assertTrue(Version.isValid(new Version(1, 2, 3)));
	}

	@Test
	def void testCompareWithQualifier() {
		val lower = new Version(1,2,3,"alpha");
		val greater = new Version(1,2,3,"beta");
		val greatest = new Version(1,2,3);
		assertEquals( 0, lower.compareTo(lower));
		assertEquals(-1, lower.compareTo(greater));
		assertEquals(-1, lower.compareTo(greatest));
		assertEquals( 1, greater.compareTo(lower));
		assertEquals( 0, greater.compareTo(greater));
		assertEquals(-1, greater.compareTo(greatest));
		assertEquals( 1, greatest.compareTo(lower));
		assertEquals( 1, greatest.compareTo(greater));
		assertEquals( 0, greatest.compareTo(greatest));
	}

	private def assertMissing(Version actual) {
		assertEquals(actual, Version.MISSING);
	}

	private def assertEquals(Version actual, Version expected) {
		assertEquals('''Expected '«expected»'. Was: '«actual»' instead.''', expected, actual);
	}


}
