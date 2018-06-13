/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.tests.utils

import org.eclipse.n4js.n4mf.DeclaredVersion
import org.eclipse.n4js.n4mf.N4mfFactory
import org.eclipse.n4js.n4mf.VersionConstraint
import org.junit.Test

import static org.eclipse.n4js.utils.ProjectDescriptionUtils.*
import static org.junit.Assert.*

/**
 * Temporary tests for the temporary implementation of SemVer versions and version ranges.
 */
class ProjectDescriptionUtilsTest {

	@Test
	def void testSanitizeMainModulePath() {
		assertEquals("a/b/c/module", convertMainPathToModuleSpecifier("src/a/b/c/module.js", #["src"]));

		assertEquals("a/b/c/module", convertMainPathToModuleSpecifier("src/js/a/b/c/module.js", #["src/js"]));
		assertEquals("a/b/c/module", convertMainPathToModuleSpecifier("src/js/a/b/c/module.js", #["./src/js"]));
		assertEquals("a/b/c/module", convertMainPathToModuleSpecifier("./src/js/a/b/c/module.js", #["src/js"]));
		assertEquals("a/b/c/module", convertMainPathToModuleSpecifier("src/./js//a/xxx/yyy///../zzz/..//../b/c/module.js", #["src/js"]));

		assertEquals("a/b/c/module", convertMainPathToModuleSpecifier("src3/a/b/c/module.js", #["src1", "src2", "src3"]));

		assertEquals("a/b/c/module", convertMainPathToModuleSpecifier("a/b/c/module.js", #["."]));
		assertEquals("a/b/c/module", convertMainPathToModuleSpecifier("a/b/c/module.js", #["./dummy2/.."]));
	}

	@Test
	def void testSanitizeMainModulePathInvalid() {
		assertEquals(null, convertMainPathToModuleSpecifier("src/a/b/c/module.js", #[]));
		assertEquals(null, convertMainPathToModuleSpecifier("src/a/b/c/module.js", #[""]));
		assertEquals(null, convertMainPathToModuleSpecifier("src/a/b/c/module.js", #[null]));
		assertEquals(null, convertMainPathToModuleSpecifier("src/a/b/c/module", #["src"])); // wrong file extension
		assertEquals(null, convertMainPathToModuleSpecifier("src/a/b/c/module.n4js", #["src"])); // wrong file extension
		assertEquals(null, convertMainPathToModuleSpecifier("some/where/else/a/b/c/module.js", #["src"]));
		assertEquals(null, convertMainPathToModuleSpecifier("src/a/b/c/module.js", #["src1", "src2", "src3"]));
		assertEquals(null, convertMainPathToModuleSpecifier("a/../../b/c/module.js", #["src"]));
	}

	@Test
	def void testVersions() {
		assertVersion(version(1, 2, 3), parseVersion("1.2.3"));
		assertVersion(version(1, 2, 3, "alpha.1"), parseVersion("1.2.3-alpha.1"));
		assertVersion(version(1, 2, 3, "alpha.1", "c04d73a"), parseVersion("1.2.3-alpha.1+c04d73a"));
		assertVersion(version(1, 2, 3, null, "c04d73a"), parseVersion("1.2.3+c04d73a"));
		assertVersion(version(1, 2, 3, "beta-or-gamma.1-2-3", "abc-def.ghi"),
			parseVersion("1.2.3-beta-or-gamma.1-2-3+abc-def.ghi"));
	}

	@Test
	def void testVersionsInvalid() {
		// missing segments
		assertNull(parseVersion("1"));
		assertNull(parseVersion("1.2"));
		// empty segments
		assertNull(parseVersion(".2.3"));
		assertNull(parseVersion("1..3"));
		assertNull(parseVersion("1.2."));
		assertNull(parseVersion("1.2.3-"));
		assertNull(parseVersion("1.2.3+"));
		assertNull(parseVersion("1.2.3-+"));
		assertNull(parseVersion("-alpha"));
		assertNull(parseVersion("+c04d73a"));
	}

	@Test
	def void testVersionRangesSimple() {
		assertVersionConstraint(
			constraint(false, version(1, 2, 3), false, version(1, 2, 3)),
			parseVersionRange("1.2.3"));
		// here, partial versions are supported:
		assertVersionConstraint(
			constraint(false, version(1, 0, 0), false, version(1, 0, 0)),
			parseVersionRange("1"));
		assertVersionConstraint(
			constraint(false, version(1, 2, 0), false, version(1, 2, 0)),
			parseVersionRange("1.2"));
	}

	@Test
	def void testVersionRangesTilde() {
		assertVersionConstraint(
			constraint(false, version(1, 2, 3), true, version(1, 3, 0)),
			parseVersionRange("~1.2.3"));
		assertVersionConstraint(
			constraint(false, version(1, 2, 0), true, version(1, 3, 0)),
			parseVersionRange("~1.2"));
		assertVersionConstraint(
			constraint(false, version(1, 0, 0), true, version(2, 0, 0)),
			parseVersionRange("~1"));
		assertVersionConstraint(
			constraint(false, version(0, 2, 3), true, version(0, 3, 0)),
			parseVersionRange("~0.2.3"));
		assertVersionConstraint(
			constraint(false, version(0, 2, 0), true, version(0, 3, 0)),
			parseVersionRange("~0.2"));
		assertVersionConstraint(
			constraint(false, version(0, 0, 0), true, version(1, 0, 0)),
			parseVersionRange("~0"));
	}

	@Test
	def void testVersionRangesCaret() {
		assertVersionConstraint(
			constraint(false, version(1, 2, 3), true, version(2, 0, 0)),
			parseVersionRange("^1.2.3"));
		assertVersionConstraint(
			constraint(false, version(0, 2, 3), true, version(0, 3, 0)),
			parseVersionRange("^0.2.3"));
		assertVersionConstraint(
			constraint(false, version(0, 0, 3), true, version(0, 0, 4)),
			parseVersionRange("^0.0.3"));

		// the following are not supported yet:
		assertVersionConstraint(null, parseVersionRange("^1.2"));
		assertVersionConstraint(null, parseVersionRange("^0.0"));
		assertVersionConstraint(null, parseVersionRange("^1"));
		assertVersionConstraint(null, parseVersionRange("^0"));
	}

	@Test
	def void testVersionRangesInvalid() {
		assertVersionConstraint(
			null,
			parseVersionRange("1.2"));
		assertVersionConstraint(
			null,
			parseVersionRange("1"));
	}

	def private void assertVersion(DeclaredVersion expected, DeclaredVersion actual) {
		if(expected===null || actual===null) {
			assertEquals(null as Object,null as Object);
		} else {
			assertEquals(expected.major, actual.major);
			assertEquals(expected.minor, actual.minor);
			assertEquals(expected.micro, actual.micro);
			assertEquals(expected.qualifier, actual.qualifier);
			assertEquals(expected.buildMetaData, actual.buildMetaData);
		}
	}

	def private void assertVersionConstraint(VersionConstraint expected, VersionConstraint actual) {
		if(expected===null || actual===null) {
			assertEquals(null as Object,null as Object);
		} else {
			assertEquals(expected.exclLowerBound, actual.exclLowerBound);
			assertVersion(expected.lowerVersion, actual.lowerVersion);
			assertEquals(expected.exclUpperBound, actual.exclUpperBound);
			assertVersion(expected.upperVersion, actual.upperVersion);
		}
	}

	def private DeclaredVersion version(int major, int minor, int patch) {
		version(major, minor, patch, null);
	}

	def private DeclaredVersion version(int major, int minor, int patch, String preRelease) {
		version(major, minor, patch, preRelease, null);
	}

	def private DeclaredVersion version(int major, int minor, int patch, String preRelease, String buildMetaData) {
		val result = N4mfFactory.eINSTANCE.createDeclaredVersion;
		result.major = major;
		result.minor = minor;
		result.micro = patch;
		result.qualifier = preRelease;
		result.buildMetaData = buildMetaData;
		return result;
	}

	def private VersionConstraint constraint(boolean exclLower, DeclaredVersion lower, boolean exclUpper,
		DeclaredVersion upper) {
		val result = N4mfFactory.eINSTANCE.createVersionConstraint;
		result.exclLowerBound = exclLower;
		result.lowerVersion = lower;
		result.exclUpperBound = exclUpper;
		result.upperVersion = upper;
		return result;
	}
}
