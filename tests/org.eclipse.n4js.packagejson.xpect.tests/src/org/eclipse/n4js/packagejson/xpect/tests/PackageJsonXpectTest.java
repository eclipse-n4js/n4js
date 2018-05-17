package org.eclipse.n4js.packagejson.xpect.tests;

import org.eclipse.n4js.json.validation.suppression.JSONSuppressIssuesSetup;
import org.eclipse.xpect.XpectImport;
import org.eclipse.xpect.runner.XpectRunner;
import org.eclipse.xpect.runner.XpectSuiteClasses;
import org.eclipse.xpect.runner.XpectTestFiles;
import org.eclipse.xpect.runner.XpectTestFiles.FileRoot;
import org.eclipse.xpect.xtext.lib.tests.ValidationTest;
import org.junit.runner.RunWith;

/**
 */
@XpectSuiteClasses({
		ValidationTest.class
})
@RunWith(XpectRunner.class)
@XpectTestFiles(relativeTo = FileRoot.PROJECT, baseDir = "xpect", fileExtensions = { "xt" })
@XpectImport({PackageJsonXpectInjectorSetup.class, JSONSuppressIssuesSetup.class})
public class PackageJsonXpectTest {
	// test setup configuration class
}
