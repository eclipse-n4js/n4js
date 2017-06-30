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
package org.eclipse.n4js.spec.tests;

import org.eclipse.n4js.N4JSStandaloneTestsModule;
import org.eclipse.n4js.xpect.methods.AccessModifierXpectMethod;
import org.eclipse.n4js.xpect.methods.ElementKeywordXpectMethod;
import org.eclipse.n4js.xpect.methods.LinkingXpectMethod;
import org.eclipse.n4js.xpect.methods.NoerrorsXpectMethod;
import org.eclipse.n4js.xpect.methods.TypeXpectMethod;
import org.eclipse.n4js.xpect.methods.scoping.ScopeXpectMethod;
import org.eclipse.n4js.xpect.ui.methods.OutputXpectMethod;
import org.eclipse.n4js.xpect.validation.suppression.SuppressIssuesSetup;
import org.junit.runner.RunWith;
import org.xpect.XpectImport;
import org.xpect.runner.XpectRunner;
import org.xpect.runner.XpectSuiteClasses;
import org.xpect.runner.XpectTestFiles;
import org.xpect.runner.XpectTestFiles.FileRoot;
import org.xpect.xtext.lib.tests.ResourceDescriptionTest;
import org.xpect.xtext.lib.tests.ValidationTest;

/**
 * Test class for all Xpect tests in folder xpect-tests; this test class is also configured in the plugin.xml so that is
 * found by all these xpect tests (even when run as non-OSGi-test).
 */
@XpectSuiteClasses({
		LinkingXpectMethod.class, ResourceDescriptionTest.class,
		AccessModifierXpectMethod.class,
		TypeXpectMethod.class,
		ValidationTest.class,
		NoerrorsXpectMethod.class,
		ScopeXpectMethod.class,
		OutputXpectMethod.class,
		ElementKeywordXpectMethod.class
})
@RunWith(XpectRunner.class)
@XpectTestFiles(relativeTo = FileRoot.PROJECT, baseDir = "xpect-tests", fileExtensions = { "xt" })
@XpectImport({ N4JSStandaloneTestsModule.class, SuppressIssuesSetup.class })
public class N4JSSpecTest {
	// nop
}
