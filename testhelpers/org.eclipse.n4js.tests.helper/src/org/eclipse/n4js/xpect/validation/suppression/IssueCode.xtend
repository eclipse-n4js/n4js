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

import org.eclipse.n4js.validation.IssueCodes
import org.eclipse.xpect.XpectImport
import org.eclipse.xpect.setup.XpectSetupComponent
import org.eclipse.n4js.xpect.validation.suppression.IssueConfiguration

/**
 * An xpect setup element which allows to
 * configure issue to be skipped during validation.
 *
 * The IssueCode element only be used inside of a {@link IssueConfiguration} element.
 * Only valid IssueCodes to be found in {@link IssueCodes} are allowed.
 *
 * Example:
 * <p>
 * 	IssueCode "AST_LOCAL_VAR_UNUSED" {enabled=true}
 * </p>
 */
@XpectSetupComponent
@XpectImport(IssueCodes)
class IssueCode {
	private String name
	private var enabled = false;

	new(String name) throws IllegalArgumentException {
		this.name = name;

		if (IssueCodes.fields.filter[field | field.name == name].empty) {
			throw new IllegalArgumentException("Unknown issue code " + name);
		}
	}

	def getName() {
		return this.name;
	}

	def setEnabled(boolean state) {
		enabled = state;
	}

	def isEnabled() {
		return enabled;
	}
}
