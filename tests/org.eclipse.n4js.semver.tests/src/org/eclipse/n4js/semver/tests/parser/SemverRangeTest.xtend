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
package org.eclipse.n4js.semver.tests.parser

import com.google.inject.Inject
import org.eclipse.n4js.semver.SemverParseHelper
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.Test
import org.junit.runner.RunWith

import static org.junit.Assert.*
import org.eclipse.n4js.semver.Semver.VersionRangeSetRequirement
import org.eclipse.n4js.semver.SemverMatcher
import org.eclipse.n4js.semver.SemverInjectorProvider

/**
 * Tests for parsing SEMVER files.
 */
@RunWith(XtextRunner)
@InjectWith(SemverInjectorProvider)
class SemverRangeTest {

	@Inject extension SemverParseHelper;

	// @formatter:off
	String[][][] data = #[
		//      Ranges under test       should match       should not match
		// #[#[ Ranges under test ], #[ should match ], #[ should not match ]],
		#[#[">=1.2.7"], #["1.2.7", "1.2.8", "1.2.99", "2.5.3", "1.3.9"], #["1.2.6", "1.1.0"]],
		#[#[">1.2.3-alpha.3"], #["1.2.3-alpha.7", "3.4.5"], #["3.4.5-alpha.9"]],
		/* Tests for or combinations */
		#[#["1.2.7 || >=1.2.9 <2.0.0"], #["1.2.7", "1.2.9", "1.4.6"], #["1.2.8", "2.0.0"]],
		/* Tests for hyphen ranges */
		#[#["1.2.3 - 2.3.4", "1.2 - 2.3.4", "1 - 2.3.4"], #["1.2.3", "2.3.4"], #["2.4.0-alpha.1"]],
		#[#["1.2.3 - 2.3"], #["1.2.3"], #["2.4.0", "2.4.0-alpha.1"]],
		/* Tests for wildcards */
		#[#["*"], #["0.0.0", "1.0.0", "0.1.0", "0.0.1", "3.0.1"], #["0.0.0-pre.1", "1.0.0-pre.1"]],
		#[#["1", "1.x", "1.x.x"], #["1.0.0", "1.9.99"], #["2.0.0", "2.0.0-pre.1"]],
		#[#["1.2", "1.2.x"], #["1.2.0", "1.2.3", "1.2.9"], #["1.3.0", "1.3.0-pre.2"]],
		#[#["1"], #["1.x.x", "1.0.x", "1.x"], #["2.0.0"]],
		#[#["1.2"], #["1.2.x", "1.2.0", "1.2.0"], #["2.0.0"]],
		/* Tests for special rule on allowing pre-release tags */
		#[#[">=9.2.3-beta.2 <9.3.0"], #["9.2.3-beta.2", "9.2.3-beta.3", "9.2.3", "9.2.4"], #["9.2.3-alpha.3", "9.2.4-beta.3", "9.3.0-alpha.0"]],
		#[#[">=9.2.3-beta.2 <9.2.3"], #["9.2.3-beta.2", "9.2.3-beta.3"], #["9.2.3", "9.2.4", "9.2.3-alpha.3", "9.2.4-beta.3", "9.3.0-alpha.0"]],
		#[#[">=9.2.3 <9.3.0-rc.2"], #["9.2.3", "9.2.4", "9.3.0-alpha.0"], #["9.2.3-beta.1", "9.3.0-rc.2", "9.3.0-rc.3"]],
		/* Tests for tilde */
		#[#["~1.2.3"], #["1.2.3", "1.2.4", "1.2.99"], #["1.3.0", "1.2.1", "1.2.4-alpha"]],
		#[#["~1.2", "~1.2.0", "~1.2.x"], #["1.2.0", "1.2", "1.2.3", "1.2.4", "1.2.99"], #["1.3.0"]],
		#[#["~1", "~1.x"], #["1.0.0", "1.3.0", "1", "1.3.0"], #["2", "2.0.0-pre1"]],
		#[#["~0.2.3"], #["0.2", "0.2.3", "0.2.4", "0.2.99"], #["0.2.0", "0.3.0"]],
		#[#["~0.2", "~0.2.0", "~0.2.x"], #["0.2", "0.2.0", "0.2.3", "0.2.4", "0.2.99"], #["0.3.0"]],
		#[#["~0", "~0.x", "~0.x.x"], #["0.0.0", "0.3.0", "0.9.0", "0.0.5"], #["0.2.4-alpha", "1.2.4"]],
		#[#["~1.2.3-beta.2"], #["1.2.3-beta.2", "1.2.3-beta.3", "1.2.3-rc.4"], #["1.3.0", "1.2.3-alpha.2", "1.2.4-beta.2"]],
		/* Tests for caret */
		#[#["^1.2.3"], #["1.2.3", "1.2.4", "1.3.0", "1.9.9"], #["2.0.0", "1.2.0", "1.2.3-rc.1"]],
		#[#["^0.2.3"], #["0.2.3", "0.2.4", "0.2.5", "0.2.9"], #["0.3.0", "0.2.0", "0.2.3-rc.1"]],
		#[#["^0.0.3"], #["0.0.3"], #["0.0.4", "0.0.2", "0.0.5", "0.0.3-rc.1"]],
		#[#["^1.2.3-beta.2"], #["1.2.3-beta.2", "1.2.3-rc.1", "1.3.3"], #["1.2.3-beta.1", "2.0.0", "1.2.4-beta.2", "1.3.0-beta.2"]],
		#[#["^0.0.3-beta"], #["0.0.3-beta", "0.0.3-rc", "0.0.3-pr.2"], #["0.0.4", "0.0.3-alpha"]],
		/* Tests for caret plus wildcards */
		#[#["^1.2.x", "^1.2"], #["1.2.0", "1.3.0"], #["2.0.0", "1.2.0-rc.1"]],
		#[#["^0.0.x", "^0.0"], #["0.0.0", "0.0.9"], #["0.1.0", "0.1.0-beta", "0.0.0-beta", "0.0.5-beta"]],
		#[#["^1.x", "^1.0"], #["1.0.0", "1.1.0", "1.0.1"], #["2.0.0", "1.1.0-beta", "0.0.0", "1.0.1-beta"]],
		#[#["^2", "^2.x"], #["2.0.0", "2.2.0", "2.0.2"], #["3.0.0", "2.1.0-beta", "1.0.0", "2.0.1-beta"]],
		#[#["^4.0.x-alpha", "^4.0.0"], #["4.0.0", "4.0.1", "4.5.0"], #["4.0.0-alpha", "4.0.1-alpha", "4.5.0-alpha"]],
		/* Tests for pre-release qualifiers */
		#[#[">1.0.0-alpha"], #["1.0.0-alpha.1", "1.0.0-alpha.beta", "1.0.0-beta", "1.0.0-beta.2", "1.0.0-beta.11", "1.0.0-rc.1", "1.0.0"], #[]],
		#[#[">1.0.0-alpha.1"], #["1.0.0-alpha.beta", "1.0.0-beta", "1.0.0-beta.2", "1.0.0-beta.11", "1.0.0-rc.1", "1.0.0"], #["1.0.0-alpha"]],
		#[#[">1.0.0-alpha.beta"], #["1.0.0-beta", "1.0.0-beta.2", "1.0.0-beta.11", "1.0.0-rc.1", "1.0.0"], #["1.0.0-alpha", "1.0.0-alpha.1"]],
		#[#[">1.0.0-beta"], #["1.0.0-beta.2", "1.0.0-beta.11", "1.0.0-rc.1", "1.0.0"], #["1.0.0-alpha", "1.0.0-alpha.1", "1.0.0-alpha.beta"]],
		#[#[">1.0.0-beta.2"], #["1.0.0-beta.11", "1.0.0-rc.1", "1.0.0"], #["1.0.0-alpha", "1.0.0-alpha.1", "1.0.0-alpha.beta", "1.0.0-beta"]],
		#[#[">1.0.0-beta.11"], #["1.0.0-rc.1", "1.0.0"], #["1.0.0-alpha", "1.0.0-alpha.1", "1.0.0-alpha.beta", "1.0.0-beta", "1.0.0-beta.2"]],
		#[#[">1.0.0-rc.1"], #["1.0.0"], #["1.0.0-alpha", "1.0.0-alpha.1", "1.0.0-alpha.beta", "1.0.0-beta", "1.0.0-beta.2", "1.0.0-beta.11"]],
		#[#[">1.0.0"], #[], #["1.0.0-alpha", "1.0.0-alpha.1", "1.0.0-alpha.beta", "1.0.0-beta", "1.0.0-beta.2", "1.0.0-beta.11", "1.0.0-rc.1"]]
	];
	// @formatter:on

	/** Checks if a range under test matches other ranges. */
	@Test
	def void testParseAndToString() {
		for (String[][] entry : data) {
			val underTestStrings = entry.get(0);
			val shouldMatches = entry.get(1);
			val shouldNotMatches = entry.get(2);
			for (String underTestStr : underTestStrings) {
				internalTestVersion(underTestStr, shouldMatches, shouldNotMatches);
			}
		}
	}

	def private void internalTestVersion(String underTestStr, String[] shouldMatches, String[] shouldNotMatches) {
		val underTestVRS = underTestStr.parseVersionRangeSet
		assertTrue(underTestVRS !== null);

		for (String shouldMatchStr : shouldMatches) {
			checkMatching(underTestStr, underTestVRS, shouldMatchStr, true);
		}

		for (String shouldNotMatchStr : shouldNotMatches) {
			checkMatching(underTestStr, underTestVRS, shouldNotMatchStr, false);
		}
	}

	def private void checkMatching(String underTestStr, VersionRangeSetRequirement underTestVRS, String matchingString, boolean shouldMatch) {
		val versionNumber = matchingString.parseVersionNumber
		assertTrue(versionNumber !== null);

		val matches = SemverMatcher.matches(versionNumber, underTestVRS);
		val not = if (shouldMatch) "" else "not "
		val msg = "Version '" + matchingString + "' should " + not + " match '" + underTestStr + "'"
		assertTrue(msg, shouldMatch == matches);
	}

}
