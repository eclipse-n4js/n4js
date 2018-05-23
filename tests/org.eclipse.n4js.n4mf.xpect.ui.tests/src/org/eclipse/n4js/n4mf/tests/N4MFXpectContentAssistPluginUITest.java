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

import static org.eclipse.xpect.runner.XpectTestFiles.FileRoot.PROJECT;

import org.eclipse.n4js.xpect.ui.methods.contentassist.ContentAssistXpectMethod;
import org.eclipse.xpect.runner.XpectRunner;
import org.eclipse.xpect.runner.XpectSuiteClasses;
import org.eclipse.xpect.runner.XpectTestFiles;
import org.junit.runner.RunWith;

/**
 * N4JS manifest content assist UI tests.
 */
@XpectSuiteClasses(ContentAssistXpectMethod.class)
@RunWith(XpectRunner.class)
@XpectTestFiles(relativeTo = PROJECT, baseDir = "xpect-tests/contentAssist", fileExtensions = { "xt" })
public class N4MFXpectContentAssistPluginUITest {
	//
}
