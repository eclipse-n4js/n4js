package org.eclipse.n4js.packagejson.xpect.tests;

import org.eclipse.n4js.json.validation.suppression.PackageJsonSuppressIssuesSetup;
import org.eclipse.n4js.xpect.common.LspCompatibleXpectTestFiles;
import org.eclipse.xpect.XpectImport;
import org.eclipse.xpect.runner.XpectRunner;
import org.eclipse.xpect.runner.XpectSuiteClasses;
import org.eclipse.xpect.xtext.lib.tests.ValidationTest;
import org.junit.runner.RunWith;

/**
 */
@XpectSuiteClasses({
		ValidationTest.class
})
@RunWith(XpectRunner.class)
@LspCompatibleXpectTestFiles(baseDir = "xpect")
@XpectImport({PackageJsonXpectInjectorSetup.class, PackageJsonSuppressIssuesSetup.class})
public class PackageJsonXpectTest {
	// test setup configuration class
}
