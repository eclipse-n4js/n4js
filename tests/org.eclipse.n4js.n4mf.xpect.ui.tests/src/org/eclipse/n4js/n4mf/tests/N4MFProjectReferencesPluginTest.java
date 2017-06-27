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
package org.eclipse.n4js.n4mf.tests;

import static org.xpect.runner.XpectTestFiles.FileRoot.PROJECT;

import org.junit.runner.RunWith;
import org.xpect.runner.XpectRunner;
import org.xpect.runner.XpectSuiteClasses;
import org.xpect.runner.XpectTestFiles;
import org.xpect.xtext.lib.tests.ValidationTest;

import org.eclipse.n4js.xpect.methods.NoerrorsXpectMethod;

/**
 * Test class for running Xpect tests as a plug-in tests after building the 'workspace' from the projects.
 */
@RunWith(XpectRunner.class)
@XpectSuiteClasses({
	NoerrorsXpectMethod.class,
	ValidationTest.class
})
@XpectTestFiles(relativeTo = PROJECT, baseDir = "testProjects", fileExtensions = { "xt" })
public class N4MFProjectReferencesPluginTest {
	//
}
