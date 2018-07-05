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
import org.eclipse.n4js.semver.SEMVER.SEMVERPackage;
import org.eclipse.n4js.semver.SEMVER.VersionNumber;
import org.eclipse.n4js.semver.SEMVER.VersionPart;
import org.eclipse.xtext.validation.Check;

/**
 * This class contains custom validation rules.
 *
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#validation
 */
@SuppressWarnings("javadoc")
public class SEMVERValidator extends AbstractSEMVERValidator {

	@Check
	public void checkNoAdditionalParts(VersionNumber versionNumber) {
		EList<VersionPart> extended = versionNumber.getExtended();
		if (extended != null && !extended.isEmpty()) {
			String msg = SEMVERIssueCodes.getMessageForSEMVER_TOO_MANY_NUMBERS();
			addIssue(msg, versionNumber, SEMVERPackage.Literals.VERSION_NUMBER__EXTENDED,
					SEMVERIssueCodes.SEMVER_TOO_MANY_NUMBERS);
		}
	}

}
