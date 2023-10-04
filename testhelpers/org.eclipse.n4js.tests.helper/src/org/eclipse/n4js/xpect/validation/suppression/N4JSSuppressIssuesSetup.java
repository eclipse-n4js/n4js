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
package org.eclipse.n4js.xpect.validation.suppression;

import java.util.Set;

import org.eclipse.n4js.N4JSLanguageConstants;
import org.eclipse.xpect.XpectImport;
import org.eclipse.xpect.XpectReplace;
import org.eclipse.xpect.setup.ISetupInitializer;
import org.eclipse.xpect.setup.XpectSetupFactory;
import org.eclipse.xpect.xtext.lib.setup.ThisResource;
import org.eclipse.xpect.xtext.lib.tests.ValidationTestModuleSetup.IssuesByLineProvider;
import org.eclipse.xtext.resource.XtextResource;

/**
 * Xpect setup that configures validation-based Xpect methods to filter out certain N4JS issue codes that should be
 * ignored (cf. {@link N4JSLanguageConstants#DEFAULT_SUPPRESSED_ISSUE_CODES_FOR_TESTS}).
 *
 * @See AbstractSuppressIssuesSetup
 */
@XpectSetupFactory
@XpectReplace(IssuesByLineProvider.class)
@XpectImport({ SuppressIssuesSetupRoot.class })
public class N4JSSuppressIssuesSetup extends AbstractSuppressIssuesSetup {

	/** Instantiates a new {@link N4JSSuppressIssuesSetup}. */
	N4JSSuppressIssuesSetup(@ThisResource XtextResource resource,
			ISetupInitializer<SuppressIssuesSetupRoot> setupInitializer) {
		super(resource, setupInitializer);
	}

	@Override
	protected Set<String> getDefaultSuppressedIssueCodes() {
		return N4JSLanguageConstants.DEFAULT_SUPPRESSED_ISSUE_CODES_FOR_TESTS;
	}

}
