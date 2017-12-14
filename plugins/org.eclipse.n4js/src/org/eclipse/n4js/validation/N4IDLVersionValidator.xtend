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
package org.eclipse.n4js.validation

import com.google.inject.Inject
import org.eclipse.n4js.n4idl.versioning.VersionHelper
import org.eclipse.n4js.ts.typeRefs.VersionedReference
import org.eclipse.n4js.validation.AbstractN4JSDeclarativeValidator
import org.eclipse.n4js.validation.IssueCodes
import org.eclipse.xtext.validation.Check
import org.eclipse.xtext.validation.EValidatorRegistrar

/**
 * Validate the use of version in N4IDL
 */
class N4IDLVersionValidator extends AbstractN4JSDeclarativeValidator {
	@Inject
	private VersionHelper versionHelper;

	/**
	 * NEEEDED
	 *
	 * when removed check methods will be called twice once by N4JSValidator, and once by
	 * AbstractDeclarativeN4JSValidator
	 */
	override void register(EValidatorRegistrar registrar) {
		// nop
	}

	/** Check that the requested version does not exceed the maximal version of the current context. */
	@Check
	def checkVersion(VersionedReference ref) {
		if (ref === null)
			return
		val context = ref.eContainer
		if (context !== null) {
			val requestedVersion = if (ref.requestedVersion === null) 0 else ref.requestedVersion.intValue
			val maxVersion = versionHelper.computeMaximumVersion(context)
			// The referenced version cannot exceed the maximum version of the context
			if (requestedVersion > maxVersion) {
				val message = IssueCodes.getMessageForIDL_INVALID_VERSION(requestedVersion, maxVersion);
				addIssue(
					message,
					context,
					ref.eContainingFeature, IssueCodes.IDL_INVALID_VERSION
				);
			}
		}
	}
}
