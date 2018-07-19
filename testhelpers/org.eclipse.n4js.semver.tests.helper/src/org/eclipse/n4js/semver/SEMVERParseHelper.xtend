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
package org.eclipse.n4js.semver

import org.eclipse.n4js.semver.Semver.VersionNumber
import org.eclipse.n4js.semver.Semver.VersionRangeConstraint
import org.eclipse.xtext.resource.XtextSyntaxDiagnostic
import org.eclipse.xtext.testing.util.ParseHelper

import static org.junit.Assert.*
import org.eclipse.n4js.semver.Semver.NPMVersionRequirement
import org.eclipse.n4js.semver.Semver.VersionRangeSetRequirement

/**
 * A parse helper for SEMVER parsing tests.
 */
class SEMVERParseHelper extends ParseHelper<NPMVersionRequirement> {

	/**
	 * Asserts that the given {@code semver} character sequence can be parsed correctly. Returns the
	 * resulting {@link VersionRangeSetRequirement} instance.
	 */
	public def NPMVersionRequirement parseSuccessfully(CharSequence semver) {
		val doc = semver.parse;
		val msg = '''"«semver»" ''' + doc.eResource.errors.join('\n')[line + ': ' + message];
		val errorList = doc.eResource.errors;
		assertTrue(msg, errorList.empty);
		return doc;
	}

	/**
	 * Asserts that the given {@code semver} character sequences cannot be parsed correctly. 
	 */
	public def void parseUnsuccessfully(CharSequence semver) {
		val doc = semver.parse;
		val msg = '''The following SemVer text did not cause any syntax errors as expected: "«semver»" ''';
		val errorList = doc.eResource.errors.filter(XtextSyntaxDiagnostic);
		assertFalse(msg, errorList.empty);
	}

	public def VersionRangeSetRequirement parseVersionRangeSet(String versionString) {
		if (versionString === null)
			return null;
		val npmVersion = versionString.parse();
		if (!(npmVersion instanceof VersionRangeSetRequirement))
			return null;
		val vrs = npmVersion as VersionRangeSetRequirement;
		return vrs;
	}

	public def VersionNumber parseVersionNumber(String versionString) {
		val vrs = parseVersionRangeSet(versionString);
		if (vrs === null || vrs.getRanges().isEmpty())
			return null;
		val versionRange = vrs.getRanges().get(0);
		if (!(versionRange instanceof VersionRangeConstraint))
			return null;
		val vrc = versionRange as VersionRangeConstraint;
		if (vrc.getVersionConstraints().isEmpty())
			return null;
		val simpleVersion = vrc.getVersionConstraints().get(0);
		return simpleVersion.getNumber();
	}
}
