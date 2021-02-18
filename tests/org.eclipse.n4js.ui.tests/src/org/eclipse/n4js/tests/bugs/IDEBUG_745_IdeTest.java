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
 * Class for checking whether the order of the source containers influences the indexing order and the scoping for
 * imports.
 */
public class IDEBUG_745_IdeTest extends ConvertedIdeTest {

	/***/
	@Test
	public void buildCheckGeneratedFileExists_AssertExists() {
		importProband(new File("probands", "IDEBUG_745"));
		assertNoIssues();

		File project = getProjectRootForImportedProject("IDEBUG_745");
		assertTrue(project.isDirectory());

		File clientOne = new File(project, "client.src/ClientOne.n4js");
		assertTrue("File: client.src/ClientOne.n4js dose not exist.", clientOne.exists());

		File clientTwo = new File(project, "/client.src/ClientTwo.n4js");
		assertTrue("File: client.src/ClientTwo.n4js dose not exist.", clientTwo.exists());
	}
}
