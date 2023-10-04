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

import org.eclipse.xpect.XpectImport;
import org.eclipse.xpect.setup.XpectSetupRoot;
import org.eclipse.xpect.xtext.lib.setup.InjectorSetup;

/**
 * An xpect setup root which allows to configure a {@link N4JSSuppressIssuesSetup}.
 *
 * Also see {@link IssueConfiguration}.
 */
@XpectSetupRoot
@XpectImport({ IssueConfiguration.class, IssueCode.class, InjectorSetup.class })
public class SuppressIssuesSetupRoot {
	private IssueConfiguration issueConfiguration;

	/***/
	public void add(IssueConfiguration configuration) {
		issueConfiguration = configuration;
	}

	/***/
	public IssueConfiguration getIssueConfiguration() {
		return issueConfiguration;
	}
}
