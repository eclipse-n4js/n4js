package org.eclipse.n4js.packagejson.xpect.ui.tests;

import org.eclipse.n4js.json.validation.suppression.PackageJsonSuppressIssuesSetup;
import org.eclipse.n4js.xpect.common.LspCompatibleXpectTestFiles;
import org.eclipse.n4js.xpect.ui.methods.HyperlinkXpectMethod;
import org.eclipse.n4js.xpect.ui.methods.contentassist.ContentAssistXpectMethod;
import org.eclipse.xpect.XpectImport;
import org.eclipse.xpect.runner.XpectRunner;
import org.eclipse.xpect.runner.XpectSuiteClasses;
import org.eclipse.xpect.xtext.lib.tests.ValidationTest;
import org.junit.runner.RunWith;

/**
 * Test suite for running Plug-In UI based N4JS package.json tests.
 */
@XpectSuiteClasses({
		ValidationTest.class,
		HyperlinkXpectMethod.class,
		ContentAssistXpectMethod.class
})
@RunWith(XpectRunner.class)
@LspCompatibleXpectTestFiles(baseDir = "xpect")
@XpectImport({PackageJsonSuppressIssuesSetup.class})
public class PackageJsonXpectPluginTest {
	// test setup configuration class
}
