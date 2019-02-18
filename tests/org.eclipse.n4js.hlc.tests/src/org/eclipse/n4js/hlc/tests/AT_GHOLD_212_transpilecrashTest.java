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
package org.eclipse.n4js.hlc.tests;

import java.io.File;
import java.io.IOException;

import org.eclipse.n4js.hlc.base.ExitCodeException;
import org.eclipse.n4js.hlc.base.N4jscBase;
import org.eclipse.n4js.utils.io.FileDeleter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Predicates;

/**
 */
public class AT_GHOLD_212_transpilecrashTest extends AbstractN4jscTest {

	File workspace;
	static String WSP_212 = "GHOLD-212";

	/**
	 * Setup workspace with api & api-impl & compile
	 *
	 */
	@Before
	public void setupWorkspace() throws IOException {
		workspace = setupWorkspace(WSP_212, Predicates.alwaysTrue(), true);
	}

	/** Delete workspace. */
	@After
	public void deleteWorkspace() throws IOException {
		FileDeleter.delete(workspace.toPath(), true);
	}

	/** The Problem was, that nothing was compiled. */
	@Test
	public void testCompileOfExtendedIterator_from_RuntimeLibrary() throws ExitCodeException {

		String proot = new File(workspace, PACKAGES).getAbsolutePath().toString();

		String[] args = { "--projectlocations", proot, "--buildType", "allprojects" };

		// compile
		new N4jscBase().doMain(args);

		// Make sure, we get here and have exactly one file compiled:
		assertFilesCompiledToES(1, proot);

	}
}
