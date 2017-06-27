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

import org.xpect.XpectImport
import org.xpect.setup.XpectSetupRoot
import org.xpect.xtext.lib.setup.InjectorSetup

/**
 * An xpect setup root which allows to configure a {@link SuppressIssuesSetup}.
 *
 * Also see {@link IssueConfiguration}.
 */
@XpectSetupRoot
@XpectImport( # [ IssueConfiguration, IssueCode, InjectorSetup] )
class SuppressIssuesSetupRoot {
	private IssueConfiguration issueConfiguration;

	def add(IssueConfiguration configuration) {
		issueConfiguration = configuration;
	}

	def getIssueConfiguration() {
		return issueConfiguration;
	}
}
