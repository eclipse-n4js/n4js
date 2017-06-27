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
package org.eclipse.n4js.npmexporter

import org.eclipse.n4js.external.version.VersionConstraintFormatUtil
import org.eclipse.xtend.lib.annotations.Data
import org.junit.Test

import static org.eclipse.n4js.external.version.VersionConstraintFormatUtil.*
import static org.junit.Assert.*

/**
 * Tests conversion to npm version format done by {@link VersionConstraintFormatUtil}.
 * Checks are performed for the conversion of plain strings and boolean input data. Basically,
 * data that is obtained in IDE UI and converted to format that can be passed to the user.
 */
class VersionConversionFromBasicTypesTest {

	@Test def convertBasic() {
		'''@1.2.3'''.from('''1.2.3''');
	}

	@Test def convertWithQualifier() {
		'''@1.2.3-alpha'''.from('''1.2.3-alpha''');
	}

	@Test def convertPartial() {
		'''@1.2'''.from('''1.2''');
	}

	@Test def convertNull() {
		''''''.from('''«null»''');
	}

	@Test def convertGarbage() {
		'''@input is not validated'''.from('''input is not validated''');
	}

	@Test def convertRangeInclusive() {
		'''@">=1.0.0 <=3.0.0"'''.from(Range.with("1.0.0", false, "3.0.0", false));
	}

	@Test def convertRangeExclusive() {
		'''@">1.0.0 <3.0.0"'''.from(Range.with("1.0.0", true, "3.0.0", true));
	}

	@Test def convertRangeMissingUpper() {
		'''@1.0.0'''.from(Range.with("1.0.0", true, null, false));
	}

	@Test def convertRangeMissingLower() {
		''''''.from(Range.with(null, true, "3.0.0", false));
	}

	@Test def convertRangeMissingBoth() {
		''''''.from(Range.with(null, true, null, false));
	}

	@Test def convertRangeMixWithQualifiers() {
		'''@">1.2.3-alpha <=3.0.0-beta"'''.from(Range.with("1.2.3-alpha", true, "3.0.0-beta", false));
	}

	@Test def convertRangeMixWithQualifiersAndMeta() {
		'''@">1.0.0-beta+exp.sha.5114f85 <=3.0.0-alpha+exp.sha.e134a85"'''.from(
			Range.with("1.0.0-beta+exp.sha.5114f85", true, "3.0.0-alpha+exp.sha.e134a85", false));
	}

	@Test def convertRangeGarbage() {
		'''@">input is not validated <=not at all"'''.from(
			Range.with("input is not validated", true, "not at all", false));
	}

////////////////////////////////////////////////////////////////////////
// test utils
	private def from(CharSequence expected, CharSequence input) {
		assertEquals(expected.toString, npmVersionFormat(input.toString))
	}

	private def from(CharSequence expected, Range input) {
		assertEquals(expected.toString, npmRangeFormat(
			input.lowerVersion,
			input.isLowerExcluded,
			input.upperVersion,
			input.isUpperExcluded
		))
	}

	@Data static class Range {
		String lowerVersion
		boolean isLowerExcluded
		String upperVersion
		boolean isUpperExcluded

		static def with(String lower, boolean loweExcl, String upper, boolean upperExcl) {
			return new Range(lower, loweExcl, upper, upperExcl);
		}
	}
}
