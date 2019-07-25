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
package org.eclipse.n4js.expectmatrix.tests;

import org.eclipse.n4js.N4JSStandaloneTestsModule;
import org.eclipse.n4js.expectmatrix.tests.utils.N4JSRuntimeTest;
import org.eclipse.n4js.xpect.common.LspCompatibleXpectTestFiles;
import org.eclipse.n4js.xpect.methods.TypeXpectMethod;
import org.eclipse.n4js.xpect.validation.suppression.N4JSSuppressIssuesSetup;
import org.eclipse.xpect.XpectImport;
import org.eclipse.xpect.runner.XpectRunner;
import org.eclipse.xpect.runner.XpectSuiteClasses;
import org.eclipse.xpect.xtext.lib.tests.LinkingTest;
import org.eclipse.xpect.xtext.lib.tests.ResourceDescriptionTest;
import org.eclipse.xpect.xtext.lib.tests.ValidationTest;
import org.junit.runner.RunWith;

/**
 */
@XpectSuiteClasses({
		LinkingTest.class, ResourceDescriptionTest.class,
		TypeXpectMethod.class,
		ValidationTest.class,
		N4JSRuntimeTest.class
})
@RunWith(XpectRunner.class)
@LspCompatibleXpectTestFiles(baseDir = "xpect-pending")
@XpectImport({ N4JSStandaloneTestsModule.class, N4JSSuppressIssuesSetup.class })
public class N4JSExpectMatrixPendingTest {
	//
}
