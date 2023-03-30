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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.function.Function;

import org.eclipse.n4js.semver.SemverInjectorProvider;
import org.eclipse.n4js.semver.SemverParseHelper;
import org.eclipse.n4js.semver.SemverUtils;
import org.eclipse.n4js.semver.Semver.GitHubVersionRequirement;
import org.eclipse.n4js.semver.Semver.LocalPathVersionRequirement;
import org.eclipse.n4js.semver.Semver.NPMVersionRequirement;
import org.eclipse.n4js.semver.Semver.TagVersionRequirement;
import org.eclipse.n4js.semver.Semver.URLVersionRequirement;
import org.eclipse.n4js.semver.Semver.VersionRangeSetRequirement;
import org.eclipse.xtext.serializer.ISerializer;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

/**
 * Tests for parsing SEMVER files.
 */
@RunWith(XtextRunner.class)
@InjectWith(SemverInjectorProvider.class)
public class SemverParserTest {

	@Inject
	SemverParseHelper semverParseHelper;
	@Inject
	ISerializer serializer;

	//@formatter:off
	List<String> semverData = List.of(
		" 1",
		" 1 ",
		"1 ",
		"1.2.3",
		"v1.2",
		"V1.2",
		"2.3.4",
		"0.2.3",
		"<2.0.0",
		">=1.2.7",
		"1.2.7",
		"1.2.8",
		"2.5.3",
		"1.3.9",
		"1.2.6",
		"1.1.0",
		"1.1.*",
		">=1.2.7 <1.3.0",
		"1.2.7 || >=1.2.9",
		"1.2.7 || >=1.2.9 <2.0.0",
		"~1.2.3-alpha.4",
		">1.2.3-alpha.4",
		"1.2.3-alpha.7",
		"3.4.5-alpha.9",
		"1.2.4-beta.0",
		"1.2.4-beta.1",
		"^2.1.0-revise",
		"^2.1.0-fi",
		"1.2.3 - 2.3.4",
		">=1.2.3 <=2.3.4",
		"1.2 - 2.3.4",
		">=1.2.0 <=2.3.4",
		"1.2.3 - 2.3",
		">=1.2.3 <2.4.0",
		"1.2.3 - 2",
		">=1.2.3 <3.0.0",
		"1.2.x",
		"1.X",
		"1.2.*",
		"*",
		"x",
		"X",
		">=0.0.0",
		"1.x",
		">=1.0.0 <2.0.0",
		"1.2.x",
		">=1.2.0 <1.3.0",
		">=0.0.0",
		"1",
		"1.x.x",
		">=1.0.0 <2.0.0",
		"1.2",
		"1.2.x",
		">=1.2.0 <1.3.0",
		"~1.2.3 ",
		"~1.2",
		"~1",
		"~1.2.3",
		">=1.2.3 <1.3.0",
		"~1.2",
		">=1.2.0 <1.3.0",
		"~1",
		">=1.0.0 <1.0.0",
		">=1.0.0 <2.0.0",
		"~0.2.3",
		">=0.2.3 <0.3.0",
		"~0.2",
		">=0.2.0 <0.3.0",
		"~0",
		">=0.0.0 <1.0.0",
		"~1.2.3-beta.2",
		">=1.2.3-beta.2 <1.3.0",
		"1.2.3-beta.4",
		"1.2.4-beta.2",
		"^1.2.3",
		"^0.2.5",
		"^0.0.4",
		"0.X >=0.1.0",
		"0.0.X",
		"^1.2.3",
		">=1.2.3 <2.0.0",
		"^0.2.3",
		">=0.2.3 <0.3.0",
		"^0.0.3",
		">=0.0.3 <0.0.4",
		"^1.2.3-beta.2",
		">=1.2.3-beta.2 <2.0.0",
		"1.2.3-beta.4",
		"1.2.4-beta.2",
		"^0.0.3-beta",
		">=0.0.3-beta <0.0.4",
		"0.0.3-pr.2",
		"^1.2.x",
		">=1.2.0 <2.0.0",
		"^0.0.x",
		">=0.0.0 <0.1.0",
		"^0.0",
		">=0.0.0 <0.1.0",
		"^1.x",
		">=1.0.0 <2.0.0",
		"^0.x",
		">=0.0.0 <1.0.0"
	);

	List<String> urlData = List.of(
		"git://github.com/coderbyheart.git#123abc",
		"git://github.com/coderbyheart.git#1.2.3-abc",
		"git+ssh://git@github.com:npm/npm.git#v1.0.27",
		"git+ssh://git@github.com:npm/npm#semver:^5.0",
		"git+https://isaacs@github.com/npm/npm.git",
		"http://asdf.com/asdf.tar.gz",
		"http://asdf.com/_/under_score/_before/after_/_"
	);

	List<String> localPathData = List.of(
        "file:../foo/bar",
        "file:../dyl",
        "file:babel-fi",
        "file:babel-preset",
        "file:_/under_score/_before/after_/_",
        "file:lets/try/@at/yay",
        "file:/root/subfolder/",
        "file:/root/subfolder",
        "file://root/subfolder/",
        "file://root/subfolder"
    );

	List<String> githubData = List.of(
		"expressjs/express",
		"mochajs/mocha#4727d357ea",
		"github:christkv/mongodb-version-manager#master",
		"github:xyz/mongodb-version-manager#master"
	);

	List<String> tagData =List.of(
		"latest"
	);

	List<String> errorData =List.of(
		"001tag",
		"1s",
		"1.7.2d",
		">1.7.2d",
		"xyztag",
		"XYZtag",
		"vtag",
		"Vtag"
	);
	//@formatter:on

	/** Checks empty strings. */
	@Test
	public void testEmtyStrings() {
		semverParseHelper.parseSuccessfully(""); // empty document
		semverParseHelper.parseSuccessfully("	 "); // whitespace document
		semverParseHelper.parseSuccessfully("		"); // whitespace document
	}

	/** Check SEMVER versions and ranges. */
	@Test
	public void testSEMVERParseAndToString() {
		internalTestParseAndToString(semverData, VersionRangeSetRequirement.class,
				(s) -> s.replace("x", "*").replace("X", "*").replace("V", "v"));
	}

	/** Checks other NPM supported versions. */
	@Test
	public void testNPMURLParseAndToString() {
		internalTestParseAndToString(urlData, URLVersionRequirement.class, (s) -> s);
	}

	/** Checks other NPM supported versions. */
	@Test
	public void testNPMLocalPathParseAndToString() {
		internalTestParseAndToString(localPathData, LocalPathVersionRequirement.class, (s) -> s);
	}

	/** Checks other NPM supported versions. */
	@Test
	public void testNPMGithubParseAndToString() {
		internalTestParseAndToString(githubData, GitHubVersionRequirement.class, (s) -> s);
	}

	/** Checks other NPM supported versions. */
	@Test
	public void testNPMTagParseAndToString() {
		internalTestParseAndToString(tagData, TagVersionRequirement.class, (s) -> s);
	}

	/** Checks other NPM supported versions. */
	@Test
	public void testNPMError() {
		internalTestError(errorData);
	}

	/**
	 * Checks both parsing of empty/wildcard requirements <em>and</em> the corresponding utility methods for identifying
	 * these requirements.
	 */
	@Test
	public void testEmptyAndWildcardRequirements() {
		NPMVersionRequirement empty = semverParseHelper.parseSuccessfully("");
		NPMVersionRequirement wildcard = semverParseHelper.parseSuccessfully("*");
		assertTrue(SemverUtils.isEmptyVersionRequirement(empty));
		assertTrue(SemverUtils.isWildcardVersionRequirement(wildcard));
	}

	/** Checks a range. */
	private void internalTestParseAndToString(List<String> data, Class<?> clazz, Function<String, String> adjust) {
		for (String entry : data) {
			NPMVersionRequirement versionRequirement = semverParseHelper.parseSuccessfully(entry);
			assertTrue("Parser returned null", versionRequirement != null);
			assertTrue(
					"Parser returned type " + versionRequirement.getClass().getSimpleName() + " but expected type is "
							+ clazz.getSimpleName() + ". Entry was: " + entry,
					clazz.isAssignableFrom(versionRequirement.getClass()));
			String serialized = serializer.serialize(versionRequirement);
			String adjustedEntry = (adjust != null) ? adjust.apply(entry.trim()) : entry.trim();
			assertEquals(adjustedEntry, serialized);
		}
	}

	/** Checks a range. */
	private void internalTestError(List<String> data) {
		for (String entry : data) {
			semverParseHelper.parseUnsuccessfully(entry);
		}
	}

}
