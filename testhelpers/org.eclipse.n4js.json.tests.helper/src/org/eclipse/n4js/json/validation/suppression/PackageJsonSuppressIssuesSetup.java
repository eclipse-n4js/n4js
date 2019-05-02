/**
 * Copyright (c) 2019 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.json.validation.suppression;

import java.util.Collection;

import org.eclipse.n4js.validation.IssueCodes;
import org.eclipse.n4js.xpect.validation.suppression.SuppressIssuesSetupRoot;
import org.eclipse.xpect.XpectImport;
import org.eclipse.xpect.XpectReplace;
import org.eclipse.xpect.setup.ISetupInitializer;
import org.eclipse.xpect.setup.XpectSetupFactory;
import org.eclipse.xpect.xtext.lib.setup.ThisResource;
import org.eclipse.xpect.xtext.lib.tests.ValidationTestModuleSetup.IssuesByLineProvider;
import org.eclipse.xtext.resource.XtextResource;

import com.google.common.collect.ImmutableList;

/**
 * Default issue suppression setup intended to be used for all package.json X!PECT tests (but not for plain JSON X!PECT
 * tests.
 * <p>
 * In addition to the issues suppressed by default by {@link JSONSuppressIssuesSetup}, we also suppress the error about
 * missing mandatory dependency to "n4js-runtime"
 */
@XpectSetupFactory
@XpectReplace(IssuesByLineProvider.class)
@XpectImport({ SuppressIssuesSetupRoot.class })
public class PackageJsonSuppressIssuesSetup extends JSONSuppressIssuesSetup {

	/** Instantiates a new {@link PackageJsonSuppressIssuesSetup}. */
	public PackageJsonSuppressIssuesSetup(@ThisResource XtextResource resource,
			ISetupInitializer<SuppressIssuesSetupRoot> setupInitializer) {
		super(resource, setupInitializer);
	}

	@Override
	protected Collection<String> getDefaultSuppressedIssueCodes() {
		return ImmutableList.<String> builder()
				.addAll(super.getDefaultSuppressedIssueCodes())
				.add(IssueCodes.PKGJ_MISSING_DEPENDENCY_N4JS_RUNTIME)
				.build();
	}
}
