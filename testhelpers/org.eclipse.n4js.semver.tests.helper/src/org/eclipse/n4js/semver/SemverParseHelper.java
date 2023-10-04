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
package org.eclipse.n4js.semver;

import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.eclipse.emf.ecore.resource.Resource.Diagnostic;
import org.eclipse.n4js.semver.Semver.NPMVersionRequirement;
import org.eclipse.n4js.semver.Semver.SimpleVersion;
import org.eclipse.n4js.semver.Semver.VersionNumber;
import org.eclipse.n4js.semver.Semver.VersionRange;
import org.eclipse.n4js.semver.Semver.VersionRangeConstraint;
import org.eclipse.n4js.semver.Semver.VersionRangeSetRequirement;
import org.eclipse.n4js.utils.Strings;
import org.eclipse.xtext.resource.XtextSyntaxDiagnostic;
import org.eclipse.xtext.testing.util.ParseHelper;

/**
 * A parse helper for SEMVER parsing tests.
 */
public class SemverParseHelper extends ParseHelper<NPMVersionRequirement> {

	/**
	 * Returns a {@link VersionRangeSetRequirement} instance or throws an exception if the given string could not be
	 * parsed.
	 */
	public NPMVersionRequirement tryParse(CharSequence semver) throws Exception {
		NPMVersionRequirement doc = parse(semver);
		return doc;
	}

	/**
	 * Asserts that the given {@code semver} character sequence can be parsed correctly. Returns the resulting
	 * {@link VersionRangeSetRequirement} instance.
	 */
	public NPMVersionRequirement parseSuccessfully(CharSequence semver) throws Exception {
		NPMVersionRequirement doc = tryParse(semver);
		assertNotNull("Could not parse " + semver, doc);
		String msg = "\"" + semver + "\""
				+ Strings.join("\n", err -> err.getLine() + ": " + err.getMessage(), doc.eResource().getErrors());
		List<Diagnostic> errorList = doc.eResource().getErrors();
		assertTrue(msg, errorList.isEmpty());
		return doc;
	}

	/**
	 * Asserts that the given {@code semver} character sequences cannot be parsed correctly.
	 */
	public void parseUnsuccessfully(CharSequence semver) throws Exception {
		NPMVersionRequirement doc = parse(semver);
		if (doc != null) {
			String msg = "The following Semver text did not cause any syntax errors as expected: \"" + semver + "\"";
			Iterable<XtextSyntaxDiagnostic> errorList = filter(doc.eResource().getErrors(),
					XtextSyntaxDiagnostic.class);
			assertFalse(msg, !errorList.iterator().hasNext());
		}
	}

	/***/
	public VersionRangeSetRequirement parseVersionRangeSet(String versionString) throws Exception {
		if (versionString == null) {
			return null;
		}
		NPMVersionRequirement npmVersion = parse(versionString);
		if (!(npmVersion instanceof VersionRangeSetRequirement)) {
			return null;
		}
		VersionRangeSetRequirement vrs = (VersionRangeSetRequirement) npmVersion;
		return vrs;
	}

	/***/
	public VersionNumber parseVersionNumber(String versionString) throws Exception {
		VersionRangeSetRequirement vrs = parseVersionRangeSet(versionString);
		if (vrs == null || vrs.getRanges().isEmpty()) {
			return null;
		}
		VersionRange versionRange = vrs.getRanges().get(0);
		if (!(versionRange instanceof VersionRangeConstraint)) {
			return null;
		}
		VersionRangeConstraint vrc = (VersionRangeConstraint) versionRange;
		if (vrc.getVersionConstraints().isEmpty()) {
			return null;
		}
		SimpleVersion simpleVersion = vrc.getVersionConstraints().get(0);
		return simpleVersion.getNumber();
	}
}
