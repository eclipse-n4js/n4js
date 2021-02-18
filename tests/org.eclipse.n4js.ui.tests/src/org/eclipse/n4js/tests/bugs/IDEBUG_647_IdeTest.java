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
package org.eclipse.n4js.tests.bugs;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.eclipse.n4js.tests.utils.ConvertedIdeTest;
import org.junit.Test;

/**
 * Class for checking API implementation compilation for static polyfilled modules.
 */
public class IDEBUG_647_IdeTest extends ConvertedIdeTest {

	/***/
	@Test
	public void buildCheckGeneratedFileExists_AssertExists() {
		importProband(new File("probands", "IDEBUG_647"));
		assertNoIssues();

		File projectRoot = getProjectRootForImportedProject("org.eclipse.n4js.lib.model.common");
		assertTrue(projectRoot.isDirectory());
		File file = new File(projectRoot, "src-gen/n4/model/common/TimezoneRegion.js");
		assertTrue("TimezoneRegion.js compiled file does not exist.", file.exists());
	}
}
