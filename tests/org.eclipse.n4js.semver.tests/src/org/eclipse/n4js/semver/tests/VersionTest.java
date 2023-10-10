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
package org.eclipse.n4js.semver.tests;

import java.util.Arrays;
import java.util.List;

import org.eclipse.n4js.semver.SemverInjectorProvider;
import org.eclipse.n4js.semver.SemverMatcher;
import org.eclipse.n4js.semver.SemverMatcher.RelationKind;
import org.eclipse.n4js.semver.SemverMatcher.VersionNumberRelation;
import org.eclipse.n4js.semver.SemverParseHelper;
import org.eclipse.n4js.semver.SemverUtils;
import org.eclipse.n4js.semver.Semver.VersionNumber;
import org.eclipse.n4js.semver.model.SemverSerializer;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.common.collect.Lists;
import com.google.inject.Inject;

/**
 * Basic version tests.
 */

@RunWith(XtextRunner.class)
@InjectWith(SemverInjectorProvider.class)
public class VersionTest {
	@Inject
	SemverParseHelper semverParseHelper;

	VersionNumber version(int major, int minor, int patch) {
		return version(major, minor, patch, null, null);
	}

	VersionNumber version(int major, int minor, int patch, String preRelease, String buildMetadata) {
		return SemverUtils.createVersionNumber(major, minor, patch, preRelease, buildMetadata);
	}

	VersionNumber closestMatch(List<VersionNumber> versions, VersionNumber version) {
		return SemverUtils.findClosestMatching(versions, version);

	}

	VersionNumber parse(String versionString) throws Exception {
		return semverParseHelper.parseVersionNumber(versionString);
	}

	@Test
	public void testParseNullExpectMissing() throws Exception {
		assertMissing(semverParseHelper.parseVersionNumber(null));
	}

	@Test
	public void testParseEmptyStringExpectMissing() throws Exception {
		assertMissing(semverParseHelper.parseVersionNumber(""));
	}

	@Test
	public void testParseTwoDotsString() throws Exception {
		assertEquals(semverParseHelper.parseVersionNumber("1.2.3"), version(1, 2, 3));
	}

	@Test
	public void testFindClosestMatchNullVersionsExpectMissing() {
		assertEquals(closestMatch(null, null), null);
	}

	@Test
	public void testFindClosestMatchEmptyVersionsExpectMissing() {
		assertEquals(closestMatch(Lists.newArrayList(), null), null);
	}

	@Test
	public void testFindClosestMatchNullToFindExpectMissing() {
		VersionNumber[] versionArr = { version(1, 2, 3) };
		List<VersionNumber> versions = Arrays.asList(versionArr);
		assertEquals(closestMatch(versions, null), null);
	}

	@Test
	public void testFindClosestExactMatchWithSingleElement() {
		VersionNumber[] versionArr = { version(1, 2, 3) };
		List<VersionNumber> versions = Arrays.asList(versionArr);
		assertEquals(closestMatch(versions, version(1, 2, 3)), version(1, 2, 3));
	}

	@Test
	public void testFindClosestExactMatchWithMultipleElement() {
		VersionNumber[] versionArr = { version(2, 3, 4), version(0, 1, 2), version(1, 2, 3) };
		List<VersionNumber> versions = Arrays.asList(versionArr);
		assertEquals(closestMatch(versions, version(1, 2, 3)), version(1, 2, 3));
	}

	@Test
	public void testFindClosestLessThanAnyElementsExpectMissing() {
		VersionNumber[] versionArr = { version(2, 3, 4), version(5, 1, 2), version(1, 2, 3) };
		List<VersionNumber> versions = Arrays.asList(versionArr);
		assertEquals(closestMatch(versions, version(0, 2, 3)), null);
	}

	@Test
	public void testFindClosestGreaterThanAnyElementsExpectGreatest() {
		VersionNumber[] versionArr = { version(2, 3, 4), version(5, 1, 2), version(1, 2, 3) };
		List<VersionNumber> versions = Arrays.asList(versionArr);
		assertEquals(closestMatch(versions, version(10, 2, 3)), version(5, 1, 2));
	}

	@Test
	public void testFindClosest() {
		VersionNumber[] versionArr = { version(2, 3, 4), version(5, 1, 2), version(1, 2, 3) };
		List<VersionNumber> versions = Arrays.asList(versionArr);
		assertEquals(closestMatch(versions, version(3, 3, 3)), version(2, 3, 4));
	}

	@Test
	public void testFindClosestWithQualifier() {
		VersionNumber[] versionArr = { version(2, 4, 0, "incomplete", "") };
		List<VersionNumber> versions = Arrays.asList(versionArr);
		assertEquals(closestMatch(versions, version(2, 4, 0)), version(2, 4, 0, "incomplete", ""));
	}

	@Test
	public void testVersions() throws Exception {
		assertEquals(version(1, 2, 3), parse("1.2.3"));
		assertEquals(version(1, 2, 3, "alpha.1", ""), parse("1.2.3-alpha.1"));
		assertEquals(version(1, 2, 3, "alpha.1", "c04d73a"), parse("1.2.3-alpha.1+c04d73a"));
		assertEquals(version(1, 2, 3, "", "c04d73a"), parse("1.2.3+c04d73a"));
		assertEquals(version(1, 2, 3, "beta-or-gamma.1-2-3", "abc-def.ghi"),
				parse("1.2.3-beta-or-gamma.1-2-3+abc-def.ghi"));
	}

	@Test
	public void testCompareWithQualifier() {
		VersionNumber lower = version(1, 2, 3, "alpha", null);
		VersionNumber greater = version(1, 2, 3, "beta", null);
		VersionNumber greatest = version(1, 2, 3);

		RelationKind relationKind = RelationKind.SemverMatchAllowPrereleaseTags;
		Assert.assertEquals(VersionNumberRelation.Equal, SemverMatcher.relation(lower, lower, relationKind));
		Assert.assertEquals(VersionNumberRelation.Smaller, SemverMatcher.relation(lower, greater, relationKind));
		Assert.assertEquals(VersionNumberRelation.Smaller, SemverMatcher.relation(lower, greatest, relationKind));
		Assert.assertEquals(VersionNumberRelation.Greater, SemverMatcher.relation(greater, lower, relationKind));
		Assert.assertEquals(VersionNumberRelation.Equal, SemverMatcher.relation(greater, greater, relationKind));
		Assert.assertEquals(VersionNumberRelation.Smaller, SemverMatcher.relation(greater, greatest, relationKind));
		Assert.assertEquals(VersionNumberRelation.Greater, SemverMatcher.relation(greatest, lower, relationKind));
		Assert.assertEquals(VersionNumberRelation.Greater, SemverMatcher.relation(greatest, greater, relationKind));
		Assert.assertEquals(VersionNumberRelation.Equal, SemverMatcher.relation(greatest, greatest, relationKind));
	}

	private void assertMissing(VersionNumber actual) {
		assertEquals(actual, null);
	}

	private void assertEquals(VersionNumber actual, VersionNumber expected) {
		String msg = "Expected '" + SemverSerializer.serialize(expected);
		msg += "'. Was: '" + SemverSerializer.serialize(actual) + "' instead.";
		Assert.assertEquals(msg, expected, actual);
	}

}
