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
package org.eclipse.n4js.xpect.validation.suppression

import java.util.List
import org.eclipse.xpect.setup.XpectSetupComponent
import org.eclipse.xpect.XpectImport

/**
 * An xpect setup element which allows to explicitly
 * configure issues to be enabled for the validation of an xpect file.
 *
 * An issue code configuration in the X!PECT_SETUP overrides
 * the configuration imposed by the Xpect runner class.
 *
 * Example:
 * <p>
 * IssueConfiguration {
 *   IssueCode "AST_LOCAL_VAR_UNUSED" {enabled=true}
 * 	 IssueCode "IMP_UNUSED_IMPORT" {enabled=false}
 * }
 * </p>
 */
@XpectSetupComponent
@XpectImport(IssueCode)
class IssueConfiguration {
	private List<IssueCode> issueCodes = newArrayList

	new() {}

	def add(IssueCode code) {
		issueCodes.add(code);
	}

	def getIssueCodes() {
		return issueCodes;
	}
}
