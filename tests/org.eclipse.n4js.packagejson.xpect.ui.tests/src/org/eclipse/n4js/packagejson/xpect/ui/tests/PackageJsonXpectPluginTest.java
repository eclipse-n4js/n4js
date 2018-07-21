package org.eclipse.n4js.packagejson.xpect.ui.tests;

import org.eclipse.n4js.json.validation.suppression.JSONSuppressIssuesSetup;
import org.eclipse.xpect.XpectImport;
import org.eclipse.xpect.runner.XpectRunner;
import org.eclipse.xpect.runner.XpectSuiteClasses;
import org.eclipse.xpect.runner.XpectTestFiles;
import org.eclipse.xpect.runner.XpectTestFiles.FileRoot;
import org.eclipse.xpect.xtext.lib.tests.ValidationTest;
import org.junit.runner.RunWith;

/**
 * Test suite for running Plug-In UI based N4JS package.json tests.
 */
@XpectSuiteClasses({
		ValidationTest.class
})
@RunWith(XpectRunner.class)
@XpectTestFiles(relativeTo = FileRoot.PROJECT, baseDir = "xpect", fileExtensions = { "xt" })
@XpectImport({JSONSuppressIssuesSetup.class})
public class PackageJsonXpectPluginTest {
	// test setup configuration class
}
