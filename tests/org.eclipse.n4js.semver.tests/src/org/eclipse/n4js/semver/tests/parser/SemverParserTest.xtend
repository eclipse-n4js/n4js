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
import org.eclipse.xtext.serializer.ISerializer
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.Test
import org.junit.runner.RunWith

import static org.junit.Assert.*
import org.eclipse.n4js.semver.SemverInjectorProvider

/**
 * Tests for parsing SEMVER files.
 */
@RunWith(XtextRunner)
@InjectWith(SemverInjectorProvider)
class SemverParserTest {

	@Inject extension SemverParseHelper;
	@Inject ISerializer serializer;

	//@formatter:off
	String[] semverData = #[
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
	];

	String[] npmData = #[
		"git://github.com/coderbyheart.git#123abc",
		"git://github.com/coderbyheart.git#1.2.3-abc",
		"expressjs/express",
		"latest",
		"http://asdf.com/asdf.tar.gz",
		"git+ssh://git@github.com:npm/npm.git#v1.0.27",
		"git+ssh://git@github.com:npm/npm#semver:^5.0",
		"git+https://isaacs@github.com/npm/npm.git",
		"git://github.com/npm/npm.git#v1.0.27",
		"mochajs/mocha#4727d357ea",
		"file:../foo/bar",
		"file:../dyl",
		"github:christkv/mongodb-version-manager#master",
		"github:xyz/mongodb-version-manager#master"
	];

	String[] errorData = #[
		"001tag",
		"1s",
		"1.7.2d",
		">1.7.2d",
		"xyztag",
		"XYZtag",
		"vtag",
		"Vtag"
	];
	//@formatter:on


	/** Checks empty strings. */
	@Test
	def void testEmtyStrings() {
		''''''.parseSuccessfully // empty document
		'''	 '''.parseSuccessfully // whitespace document
		'''		'''.parseSuccessfully // whitespace document
	}

	/** Check SEMVER versions and ranges. */
	@Test
	def void testSEMVERParseAndToString() {
		internalTestParseAndToString(semverData, [s | return s.replace("x", "*").replace("X", "*").replace("V", "v")]);
	}

	/** Checks other NPM supported versions. */
	@Test
	def void testNPMParseAndToString() {
		internalTestParseAndToString(npmData, [s | return s]);
	}

	/** Checks other NPM supported versions. */
	@Test
	def void testNPMError() {
		internalTestError(errorData);
	}

	/** Checks a range. */
	private def void internalTestParseAndToString(String[] data, (String)=>String adjust) {
		for (String entry : data) {
			val versionRangeSet = entry.parseSuccessfully
			assertTrue(versionRangeSet !== null);
			val serialized = serializer.serialize(versionRangeSet);
			val adjustedEntry = if (adjust !== null) adjust.apply(entry.trim) else entry.trim;
			assertEquals(adjustedEntry, serialized);
		}
	}

	/** Checks a range. */
	private def void internalTestError(String[] data) {
		for (String entry : data) {
			entry.parseUnsuccessfully
		}
	}

}
