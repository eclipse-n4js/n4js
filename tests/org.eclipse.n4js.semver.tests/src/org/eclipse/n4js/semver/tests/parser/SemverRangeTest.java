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
package org.eclipse.n4js.semver.tests.parser;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.eclipse.n4js.semver.SemverInjectorProvider;
import org.eclipse.n4js.semver.SemverMatcher;
import org.eclipse.n4js.semver.SemverParseHelper;
import org.eclipse.n4js.semver.Semver.VersionNumber;
import org.eclipse.n4js.semver.Semver.VersionRangeSetRequirement;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.util.Triple;
import org.eclipse.xtext.util.Tuples;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

/**
 * Tests for parsing SEMVER files.
 */
@RunWith(XtextRunner.class)
@InjectWith(SemverInjectorProvider.class)
public class SemverRangeTest {

	@Inject
	SemverParseHelper semverParseHelper;

	// @formatter:off
	List<Triple<List<String>,List<String>, List<String>>> data = List.of(
		//      Ranges under test       should match       should not match
		// #[#[ Ranges under test ), List.of( should match ), List.of( should not match )),
		Tuples.create(List.of(">=1.2.7"), List.of("1.2.7", "1.2.8", "1.2.99", "2.5.3", "1.3.9"), List.of("1.2.6", "1.1.0")),
		Tuples.create(List.of(">1.2.3-alpha.3"), List.of("1.2.3-alpha.7", "3.4.5"), List.of("3.4.5-alpha.9")),
		/* Tests for or combinations */
		Tuples.create(List.of("1.2.7 || >=1.2.9 <2.0.0"), List.of("1.2.7", "1.2.9", "1.4.6"), List.of("1.2.8", "2.0.0")),
		/* Tests for hyphen ranges */
		Tuples.create(List.of("1.2.3 - 2.3.4", "1.2 - 2.3.4", "1 - 2.3.4"), List.of("1.2.3", "2.3.4"), List.of("2.4.0-alpha.1")),
		Tuples.create(List.of("1.2.3 - 2.3"), List.of("1.2.3"), List.of("2.4.0", "2.4.0-alpha.1")),
		/* Tests for wildcards */
		Tuples.create(List.of("*"), List.of("0.0.0", "1.0.0", "0.1.0", "0.0.1", "3.0.1", "0.0.0-pre.1", "1.0.0-pre.1"), List.of()), // see {@link SemverMatcher#isWildcard(VersionRangeSetRequirement)}
		Tuples.create(List.of("1", "1.x", "1.x.x"), List.of("1.0.0", "1.9.99"), List.of("2.0.0", "2.0.0-pre.1")),
		Tuples.create(List.of("1.2", "1.2.x"), List.of("1.2.0", "1.2.3", "1.2.9"), List.of("1.3.0", "1.3.0-pre.2")),
		Tuples.create(List.of("1"), List.of("1.x.x", "1.0.x", "1.x"), List.of("2.0.0")),
		Tuples.create(List.of("1.2"), List.of("1.2.x", "1.2.0", "1.2.0"), List.of("2.0.0")),
		/* Tests for special rule on allowing pre-release tags */
		Tuples.create(List.of(">=9.2.3-beta.2 <9.3.0"), List.of("9.2.3-beta.2", "9.2.3-beta.3", "9.2.3", "9.2.4"), List.of("9.2.3-alpha.3", "9.2.4-beta.3", "9.3.0-alpha.0")),
		Tuples.create(List.of(">=9.2.3-beta.2 <9.2.3"), List.of("9.2.3-beta.2", "9.2.3-beta.3"), List.of("9.2.3", "9.2.4", "9.2.3-alpha.3", "9.2.4-beta.3", "9.3.0-alpha.0")),
		Tuples.create(List.of(">=9.2.3 <9.3.0-rc.2"), List.of("9.2.3", "9.2.4", "9.3.0-alpha.0"), List.of("9.2.3-beta.1", "9.3.0-rc.2", "9.3.0-rc.3")),
		/* Tests for tilde */
		Tuples.create(List.of("~1.2.3"), List.of("1.2.3", "1.2.4", "1.2.99"), List.of("1.3.0", "1.2.1", "1.2.4-alpha")),
		Tuples.create(List.of("~1.2", "~1.2.0", "~1.2.x"), List.of("1.2.0", "1.2", "1.2.3", "1.2.4", "1.2.99"), List.of("1.3.0")),
		Tuples.create(List.of("~1", "~1.x"), List.of("1.0.0", "1.3.0", "1", "1.3.0"), List.of("2", "2.0.0-pre1")),
		Tuples.create(List.of("~0.2.3"), List.of("0.2", "0.2.3", "0.2.4", "0.2.99"), List.of("0.2.0", "0.3.0")),
				Tuples.create(List.of("~0.2", "~0.2.0", "~0.2.x"), List.of("0.2", "0.2.0", "0.2.3", "0.2.4", "0.2.99"), List.of("0.3.0")),
		Tuples.create(List.of("~0", "~0.x", "~0.x.x"), List.of("0.0.0", "0.3.0", "0.9.0", "0.0.5"), List.of("0.2.4-alpha", "1.2.4")),
		Tuples.create(List.of("~1.2.3-beta.2"), List.of("1.2.3-beta.2", "1.2.3-beta.3", "1.2.3-rc.4"), List.of("1.3.0", "1.2.3-alpha.2", "1.2.4-beta.2")),
		/* Tests for caret */
		Tuples.create(List.of("^1.2.3"), List.of("1.2.3", "1.2.4", "1.3.0", "1.9.9"), List.of("2.0.0", "1.2.0", "1.2.3-rc.1")),
		Tuples.create(List.of("^0.2.3"), List.of("0.2.3", "0.2.4", "0.2.5", "0.2.9"), List.of("0.3.0", "0.2.0", "0.2.3-rc.1")),
		Tuples.create(List.of("^0.0.3"), List.of("0.0.3"), List.of("0.0.4", "0.0.2", "0.0.5", "0.0.3-rc.1")),
		Tuples.create(List.of("^1.2.3-beta.2"), List.of("1.2.3-beta.2", "1.2.3-rc.1", "1.3.3"), List.of("1.2.3-beta.1", "2.0.0", "1.2.4-beta.2", "1.3.0-beta.2")),
		Tuples.create(List.of("^0.0.3-beta"), List.of("0.0.3-beta", "0.0.3-rc", "0.0.3-pr.2"), List.of("0.0.4", "0.0.3-alpha")),
		/* Tests for caret plus wildcards */
		Tuples.create(List.of("^1.2.x", "^1.2"), List.of("1.2.0", "1.3.0"), List.of("2.0.0", "1.2.0-rc.1")),
		Tuples.create(List.of("^0.0.x", "^0.0"), List.of("0.0.0", "0.0.9"), List.of("0.1.0", "0.1.0-beta", "0.0.0-beta", "0.0.5-beta")),
		Tuples.create(List.of("^1.x", "^1.0"), List.of("1.0.0", "1.1.0", "1.0.1"), List.of("2.0.0", "1.1.0-beta", "0.0.0", "1.0.1-beta")),
		Tuples.create(List.of("^2", "^2.x"), List.of("2.0.0", "2.2.0", "2.0.2"), List.of("3.0.0", "2.1.0-beta", "1.0.0", "2.0.1-beta")),
		Tuples.create(List.of("^4.0.x-alpha", "^4.0.0"), List.of("4.0.0", "4.0.1", "4.5.0"), List.of("4.0.0-alpha", "4.0.1-alpha", "4.5.0-alpha")),
		/* Tests for pre-release qualifiers */
		Tuples.create(List.of(">1.0.0-alpha"), List.of("1.0.0-alpha.1", "1.0.0-alpha.beta", "1.0.0-beta", "1.0.0-beta.2", "1.0.0-beta.11", "1.0.0-rc.1", "1.0.0"), List.of()),
		Tuples.create(List.of(">1.0.0-alpha.1"), List.of("1.0.0-alpha.beta", "1.0.0-beta", "1.0.0-beta.2", "1.0.0-beta.11", "1.0.0-rc.1", "1.0.0"), List.of("1.0.0-alpha")),
		Tuples.create(List.of(">1.0.0-alpha.beta"), List.of("1.0.0-beta", "1.0.0-beta.2", "1.0.0-beta.11", "1.0.0-rc.1", "1.0.0"), List.of("1.0.0-alpha", "1.0.0-alpha.1")),
		Tuples.create(List.of(">1.0.0-beta"), List.of("1.0.0-beta.2", "1.0.0-beta.11", "1.0.0-rc.1", "1.0.0"), List.of("1.0.0-alpha", "1.0.0-alpha.1", "1.0.0-alpha.beta")),
		Tuples.create(List.of(">1.0.0-beta.2"), List.of("1.0.0-beta.11", "1.0.0-rc.1", "1.0.0"), List.of("1.0.0-alpha", "1.0.0-alpha.1", "1.0.0-alpha.beta", "1.0.0-beta")),
		Tuples.create(List.of(">1.0.0-beta.11"), List.of("1.0.0-rc.1", "1.0.0"), List.of("1.0.0-alpha", "1.0.0-alpha.1", "1.0.0-alpha.beta", "1.0.0-beta", "1.0.0-beta.2")),
		Tuples.create(List.of(">1.0.0-rc.1"), List.of("1.0.0"), List.of("1.0.0-alpha", "1.0.0-alpha.1", "1.0.0-alpha.beta", "1.0.0-beta", "1.0.0-beta.2", "1.0.0-beta.11")),
		Tuples.create(List.of(">1.0.0"), List.of(), List.of("1.0.0-alpha", "1.0.0-alpha.1", "1.0.0-alpha.beta", "1.0.0-beta", "1.0.0-beta.2", "1.0.0-beta.11", "1.0.0-rc.1"))
	);
	// @formatter:on

	/** Checks if a range under test matches other ranges. */
	@Test
	public void testParseAndToString() throws Exception {
		for (Triple<List<String>, List<String>, List<String>> entry : data) {
			List<String> underTestStrings = entry.getFirst();
			List<String> shouldMatches = entry.getSecond();
			List<String> shouldNotMatches = entry.getThird();
			for (String underTestStr : underTestStrings) {
				internalTestVersion(underTestStr, shouldMatches, shouldNotMatches);
			}
		}
	}

	private void internalTestVersion(String underTestStr, List<String> shouldMatches, List<String> shouldNotMatches)
			throws Exception {
		VersionRangeSetRequirement underTestVRS = semverParseHelper.parseVersionRangeSet(underTestStr);
		assertTrue(underTestVRS != null);

		for (String shouldMatchStr : shouldMatches) {
			checkMatching(underTestStr, underTestVRS, shouldMatchStr, true);
		}

		for (String shouldNotMatchStr : shouldNotMatches) {
			checkMatching(underTestStr, underTestVRS, shouldNotMatchStr, false);
		}
	}

	private void checkMatching(String underTestStr, VersionRangeSetRequirement underTestVRS, String matchingString,
			boolean shouldMatch) throws Exception {
		VersionNumber versionNumber = semverParseHelper.parseVersionNumber(matchingString);
		assertTrue(versionNumber != null);

		boolean matches = SemverMatcher.matches(versionNumber, underTestVRS);
		String not = shouldMatch ? "" : "not ";
		String msg = "Version '" + matchingString + "' should " + not + " match '" + underTestStr + "'";
		assertTrue(msg, shouldMatch == matches);
	}

}
