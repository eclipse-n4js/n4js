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
package org.eclipse.n4js.ts.tests.scoping;

import org.junit.runner.RunWith;
import org.xpect.runner.XpectRunner;
import org.xpect.runner.XpectSuiteClasses;
import org.xpect.runner.XpectTestFiles;
import org.xpect.runner.XpectTestFiles.FileRoot;
import org.xpect.xtext.lib.tests.ScopingTest;

/**
 */
@RunWith(XpectRunner.class)
@XpectSuiteClasses({ ScopingTest.class })
@XpectTestFiles(relativeTo = FileRoot.PROJECT, baseDir = "model/scoping", fileExtensions = "xt")
public class TypesScopeProviderPluginTest {
	// nothing more required
}
