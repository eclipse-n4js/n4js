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
package org.eclipse.n4js.json.xpect.tests;

import org.eclipse.n4js.json.validation.suppression.JSONSuppressIssuesSetup;
import org.eclipse.n4js.xpect.common.LspCompatibleXpectTestFiles;
import org.eclipse.n4js.xpect.methods.FormatterXpectMethod;
import org.eclipse.xpect.XpectImport;
import org.eclipse.xpect.runner.XpectRunner;
import org.eclipse.xpect.runner.XpectSuiteClasses;
import org.eclipse.xpect.xtext.lib.tests.ValidationTest;
import org.junit.runner.RunWith;

/**
 */
@XpectSuiteClasses({
		ValidationTest.class,
		FormatterXpectMethod.class
})
@RunWith(XpectRunner.class)
@LspCompatibleXpectTestFiles(baseDir = "xpect")
@XpectImport({ JSONSuppressIssuesSetup.class })
public class JSONXpectTest {
	// nothing more required
}
