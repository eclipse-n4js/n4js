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
package org.eclipse.n4js.xpect.ui.tests;

import org.eclipse.n4js.xpect.common.N4JSXpectRunner;
import org.eclipse.n4js.xpect.config.Config;
import org.eclipse.n4js.xpect.config.VarDef;
import org.eclipse.n4js.xpect.config.XpEnvironmentData;
import org.eclipse.n4js.xpect.ui.methods.HyperlinkXpectMethod;
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
 *
 */
@XpectSuiteClasses({
		ValidationTest.class,
		QuickFixXpectMethod.class,
		ProposalXpectMethod.class,
		HyperlinkXpectMethod.class,
		ContentAssistXpectMethod.class,
		XpectTestResultTest.class
})
@XpectImport({ Config.class, VarDef.class, XpEnvironmentData.class, N4JSSuppressIssuesSetup.class })
@RunWith(N4JSXpectRunner.class)
@XpectTestFiles(baseDir = "testdata_nonvalidating")
public class OverridingContentAssistXpectPluginUITest {
	// nothing more required
}
