/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.semver.validation;

import org.eclipse.emf.common.util.EList;
import org.eclipse.n4js.semver.Semver.SemverPackage;
import org.eclipse.n4js.semver.Semver.SimpleVersion;
import org.eclipse.n4js.semver.Semver.VersionComparator;
import org.eclipse.n4js.semver.Semver.VersionNumber;
import org.eclipse.n4js.semver.Semver.VersionPart;
import org.eclipse.xtext.validation.Check;

/**
 * This class contains custom validation rules.
 *
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#validation
 */
@SuppressWarnings("javadoc")
public class SemverValidator extends AbstractSemverValidator {

	@Check
	public void checkNoAdditionalParts(VersionNumber versionNumber) {
		EList<VersionPart> extended = versionNumber.getExtended();
		if (extended != null && !extended.isEmpty()) {
			String msg = SemverIssueCodes.SEMVER_TOO_MANY_NUMBERS.getMessage();
			addIssue(msg, versionNumber, SemverPackage.Literals.VERSION_NUMBER__EXTENDED,
					SemverIssueCodes.SEMVER_TOO_MANY_NUMBERS.name());
		}
	}

	@Check
	public void checkNoMultipleComparators(SimpleVersion simpleVersion) {
		EList<VersionComparator> comparators = simpleVersion.getComparators();
		if (comparators.size() > 1) {
			String msg = SemverIssueCodes.SEMVER_TOO_MANY_COMPARATORS.getMessage();
			addIssue(msg, simpleVersion, SemverPackage.Literals.SIMPLE_VERSION__COMPARATORS,
					SemverIssueCodes.SEMVER_TOO_MANY_COMPARATORS.name());
		}
	}

}
