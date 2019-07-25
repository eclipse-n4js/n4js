package org.eclipse.n4js.json.xpect.ui.tests;
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

import org.eclipse.n4js.json.validation.suppression.JSONSuppressIssuesSetup;
import org.eclipse.n4js.xpect.common.LspCompatibleXpectTestFiles;
import org.eclipse.n4js.xpect.ui.methods.HyperlinkXpectMethod;
import org.eclipse.n4js.xpect.ui.methods.OrganizeImportXpectMethod;
import org.eclipse.n4js.xpect.ui.methods.OutlineXpectMethod;
import org.eclipse.n4js.xpect.ui.methods.ProposalXpectMethod;
import org.eclipse.n4js.xpect.ui.methods.contentassist.ContentAssistXpectMethod;
import org.eclipse.n4js.xpect.ui.methods.quickfix.QuickFixXpectMethod;
import org.eclipse.xpect.XpectImport;
import org.eclipse.xpect.lib.XpectTestResultTest;
import org.eclipse.xpect.runner.XpectRunner;
import org.eclipse.xpect.runner.XpectSuiteClasses;
import org.eclipse.xpect.xtext.lib.tests.ValidationTest;
import org.junit.runner.RunWith;

/**
 * Plugin linking test
 */
@XpectSuiteClasses({
		// LinkingTest.class,
		// TypeXpectMethod.class,
		// ScopeXpectMethod.class,
		// ResourceDescriptionTest.class,
		ValidationTest.class,
		OutlineXpectMethod.class,
		QuickFixXpectMethod.class,
		ContentAssistXpectMethod.class,
		ProposalXpectMethod.class,
		HyperlinkXpectMethod.class,
		OrganizeImportXpectMethod.class,
		XpectTestResultTest.class
})

@RunWith(XpectRunner.class)
@LspCompatibleXpectTestFiles(baseDir = "xpect")
@XpectImport({ JSONSuppressIssuesSetup.class })
public class JSONXpectPluginUITest {
	//
}
