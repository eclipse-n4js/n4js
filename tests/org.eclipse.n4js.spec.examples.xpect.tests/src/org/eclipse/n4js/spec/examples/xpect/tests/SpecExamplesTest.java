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
package org.eclipse.n4js.spec.examples.xpect.tests;

import org.eclipse.n4js.N4JSStandaloneTestsModule;
import org.eclipse.n4js.xpect.ui.methods.OutputXpectMethod;
import org.eclipse.n4js.xpect.validation.suppression.SuppressIssuesSetup;
import org.junit.runner.RunWith;
import org.xpect.XpectImport;
import org.xpect.runner.XpectRunner;
import org.xpect.runner.XpectSuiteClasses;
import org.xpect.runner.XpectTestFiles;
import org.xpect.runner.XpectTestFiles.FileRoot;
import org.xpect.xtext.lib.tests.ValidationTest;

/**
 */
@XpectSuiteClasses({
		ValidationTest.class,
		OutputXpectMethod.class
})
@XpectTestFiles(relativeTo = FileRoot.PROJECT, baseDir = "testdata", fileExtensions = { "xt" })
@RunWith(XpectRunner.class)
@XpectImport({ N4JSStandaloneTestsModule.class, SuppressIssuesSetup.class })
public class SpecExamplesTest {
	// nop
}
