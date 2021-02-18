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
package org.eclipse.n4js.xpect.ui.tests;

import org.eclipse.n4js.ide.tests.helper.server.xt.XtFolder;
import org.eclipse.n4js.ide.tests.helper.server.xt.XtIdeTest;
import org.eclipse.n4js.ide.tests.helper.server.xt.XtParentRunner;
import org.eclipse.xpect.runner.XpectSuiteClasses;
import org.eclipse.xpect.xtext.lib.setup.workspace.WorkspaceDefaultsSetup;
import org.junit.runner.RunWith;

/**
 * Common JUnit runner implementation that uses some annotations of Xpect to enable UI features of the Eclipse IDE
 * regarding JUnit view and context menu entries.
 */
// This annotation is used only to enable UI features of JUnit and .xt files.
@XpectSuiteClasses({
		XtIdeTest.class, // This class defines test methods (using @Xpect) used in .xt files after keyword 'XPECT'
		WorkspaceDefaultsSetup.class // This class links keywords used in setup sections of .xt files
})
@RunWith(XtParentRunner.class)
@XtFolder("ideTests")
public class XtTestSetupTest { // needs to be called test to get picked up by maven
	// NOOP
}
