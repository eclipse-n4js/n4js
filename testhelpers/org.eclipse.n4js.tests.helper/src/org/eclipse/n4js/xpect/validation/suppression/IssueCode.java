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
package org.eclipse.n4js.xpect.validation.suppression;

import org.eclipse.n4js.validation.IssueCodes;
import org.eclipse.xpect.XpectImport;
import org.eclipse.xpect.setup.XpectSetupComponent;

/**
 * An xpect setup element which allows to configure issue to be skipped during validation.
 *
 * The IssueCode element only be used inside of a {@link IssueConfiguration} element. Only valid IssueCodes to be found
 * in {@link IssueCodes} are allowed.
 *
 * Example:
 * <p>
 * IssueCode "AST_LOCAL_VAR_UNUSED" {enabled=true}
 * </p>
 */
@XpectSetupComponent
@XpectImport(IssueCodes.class)
public class IssueCode {
	private final String name;
	private boolean enabled = false;

	
	public IssueCode(String name) throws IllegalArgumentException {
		this.name = name;
	}

	
	public String getName() {
		return this.name;
	}

	
	public void setEnabled(boolean state) {
		enabled = state;
	}

	
	public boolean isEnabled() {
		return enabled;
	}
}
