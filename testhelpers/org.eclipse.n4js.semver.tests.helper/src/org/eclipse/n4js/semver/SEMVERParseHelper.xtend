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

import org.eclipse.n4js.semver.SEMVER.VersionRangeSet
import org.eclipse.xtext.resource.XtextSyntaxDiagnostic
import org.eclipse.xtext.testing.util.ParseHelper

import static org.junit.Assert.*

/**
 * A parse helper for SEMVER parsing tests.
 */
class SEMVERParseHelper extends ParseHelper<VersionRangeSet> {

	/**
	 * Asserts that the given {@code semver} character sequence can be parsed correctly. Returns the
	 * resulting {@link VersionRangeSet} instance.
	 */
	public def VersionRangeSet parseSuccessfully(CharSequence semver) {
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
}
