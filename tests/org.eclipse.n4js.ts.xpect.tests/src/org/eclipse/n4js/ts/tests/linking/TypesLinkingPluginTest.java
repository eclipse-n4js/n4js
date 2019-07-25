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
package org.eclipse.n4js.ts.tests.linking;

import org.eclipse.n4js.xpect.common.LspCompatibleXpectTestFiles;
import org.eclipse.xpect.runner.XpectRunner;
import org.eclipse.xpect.xtext.lib.tests.LinkingTest;
import org.junit.runner.RunWith;

/**
 */
@RunWith(XpectRunner.class)
@LspCompatibleXpectTestFiles(baseDir = "model/linking")
public class TypesLinkingPluginTest extends LinkingTest {
	// nothing more required
}
