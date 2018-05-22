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
package org.eclipse.n4js.json.validation.suppression;

import java.util.Arrays;
import java.util.Collection;

import org.eclipse.n4js.json.validation.JSONIssueCodes;
import org.eclipse.n4js.xpect.validation.suppression.AbstractSuppressIssuesSetup;
import org.eclipse.n4js.xpect.validation.suppression.SuppressIssuesSetupRoot;
import org.eclipse.xpect.XpectImport;
import org.eclipse.xpect.XpectReplace;
import org.eclipse.xpect.setup.ISetupInitializer;
import org.eclipse.xpect.setup.XpectSetupFactory;
import org.eclipse.xpect.xtext.lib.setup.ThisResource;
import org.eclipse.xpect.xtext.lib.tests.ValidationTestModuleSetup.IssuesByLineProvider;
import org.eclipse.xtext.resource.XtextResource;

/**
 * An X!PECT setup that suppresses issues with specific issue codes when running X!PECT validation tests.
 *
 * @See {@link AbstractSuppressIssuesSetup}.
 */
@XpectSetupFactory
@XpectReplace(IssuesByLineProvider.class)
@XpectImport({ SuppressIssuesSetupRoot.class })
public class JSONSuppressIssuesSetup extends AbstractSuppressIssuesSetup {

	/** Instantiates a new {@link JSONSuppressIssuesSetup}. */
	public JSONSuppressIssuesSetup(@ThisResource XtextResource resource,
			ISetupInitializer<SuppressIssuesSetupRoot> setupInitializer) {
		super(resource, setupInitializer);
	}

	@Override
	protected Collection<String> getDefaultSuppressedIssueCodes() {
		// per default, we ignore all comments-related issues in JSON X!PECT tests
		return Arrays.asList(JSONIssueCodes.JSON_COMMENT_UNSUPPORTED);
	}

}
