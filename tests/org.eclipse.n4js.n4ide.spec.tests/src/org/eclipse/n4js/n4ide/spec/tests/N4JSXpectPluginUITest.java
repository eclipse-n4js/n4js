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
package org.eclipse.n4js.n4ide.spec.tests;

import org.eclipse.n4js.xpect.ui.methods.HyperlinkXpectMethod;
import org.eclipse.n4js.xpect.ui.methods.OrganizeImportXpectMethod;
import org.eclipse.n4js.xpect.ui.methods.OutlineXpectMethod;
import org.eclipse.n4js.xpect.ui.methods.ProposalXpectMethod;
import org.eclipse.n4js.xpect.ui.methods.contentassist.ContentAssistXpectMethod;
import org.eclipse.n4js.xpect.ui.methods.quickfix.QuickFixXpectMethod;
import org.eclipse.n4js.xpect.validation.suppression.SuppressIssuesSetup;
import org.junit.runner.RunWith;
import org.xpect.XpectImport;
import org.xpect.lib.XpectTestResultTest;
import org.xpect.runner.XpectRunner;
import org.xpect.runner.XpectSuiteClasses;
import org.xpect.runner.XpectTestFiles;
import org.xpect.runner.XpectTestFiles.FileRoot;
import org.xpect.xtext.lib.tests.ValidationTest;

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
@XpectTestFiles(relativeTo = FileRoot.PROJECT, baseDir = "xpect-test", fileExtensions = "xt")
@XpectImport({ SuppressIssuesSetup.class })
public class N4JSXpectPluginUITest {
	//
}
