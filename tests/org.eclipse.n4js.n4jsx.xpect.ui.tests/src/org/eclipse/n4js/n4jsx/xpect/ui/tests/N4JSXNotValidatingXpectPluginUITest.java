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
package org.eclipse.n4js.n4jsx.xpect.ui.tests;

import org.eclipse.n4js.tests.util.EclipseGracefulUIShutdownEnabler;
import org.eclipse.n4js.xpect.common.N4JSXpectRunner;
import org.eclipse.n4js.xpect.config.Config;
import org.eclipse.n4js.xpect.config.VarDef;
import org.eclipse.n4js.xpect.config.XpEnvironmentData;
import org.eclipse.n4js.xpect.ui.methods.HyperlinkXpectMethod;
import org.eclipse.n4js.xpect.ui.methods.OrganizeImportXpectMethod;
import org.eclipse.n4js.xpect.ui.methods.ProposalXpectMethod;
import org.eclipse.n4js.xpect.ui.methods.contentassist.ContentAssistXpectMethod;
import org.eclipse.n4js.xpect.ui.methods.quickfix.QuickFixXpectMethod;
import org.eclipse.n4js.xpect.validation.suppression.N4JSSuppressIssuesSetup;
import org.eclipse.xpect.XpectImport;
import org.eclipse.xpect.lib.XpectTestResultTest;
import org.eclipse.xpect.runner.XpectSuiteClasses;
import org.eclipse.xpect.runner.XpectTestFiles;
import org.eclipse.xpect.xtext.lib.tests.ValidationTest;
import org.junit.runner.RunWith;

/**
 * Plugin for proposal test. This plugin turns <b>off</b> validation in xpect-tests by default since most input files
 * are invalid before applying and only some become valid after applying a proposal.
 */
@XpectSuiteClasses({
		// LinkingTest.class,
		// N4JSTypeSystemXpectTestFragment.class,
		// NoerrorsValidationTestFragment.class,
		// PositionAwareScopingXpectTestFragment.class,
		// ResourceDescriptionTest.class,
		ValidationTest.class,
		QuickFixXpectMethod.class,
		ProposalXpectMethod.class,
		HyperlinkXpectMethod.class,
		ContentAssistXpectMethod.class,
		XpectTestResultTest.class,
		OrganizeImportXpectMethod.class
})
@XpectImport({ Config.class, VarDef.class, XpEnvironmentData.class, N4JSSuppressIssuesSetup.class })
@RunWith(N4JSXpectRunner.class)
@XpectTestFiles(baseDir = "testdata_nonvalidating")
public class N4JSXNotValidatingXpectPluginUITest {
	static {
		EclipseGracefulUIShutdownEnabler.enableOnce();
	}
}
