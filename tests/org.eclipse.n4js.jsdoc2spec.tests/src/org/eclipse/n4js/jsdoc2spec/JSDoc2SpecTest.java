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
package org.eclipse.n4js.jsdoc2spec;

import org.eclipse.n4js.N4JSStandaloneTestsModule;
import org.eclipse.n4js.jsdoc2spec.xpect.SpecADocXpectMethod;
import org.eclipse.n4js.xpect.common.LspCompatibleXpectTestFiles;
import org.eclipse.xpect.XpectImport;
import org.eclipse.xpect.runner.XpectRunner;
import org.eclipse.xpect.runner.XpectSuiteClasses;
import org.junit.runner.RunWith;

/**
 * Test class for all Xpect tests in folder xpect-tests; this test class is also configured in the plugin.xml so that is
 * found by all these xpect tests (even when run as non-OSGi-test).
 */
@XpectSuiteClasses({
		SpecADocXpectMethod.class
})
@RunWith(XpectRunner.class)
@LspCompatibleXpectTestFiles(baseDir = "xpect-tests")
@XpectImport({ N4JSStandaloneTestsModule.class })
public class JSDoc2SpecTest {
	// nop

}
