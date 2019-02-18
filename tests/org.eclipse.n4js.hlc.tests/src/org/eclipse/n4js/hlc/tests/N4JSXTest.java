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

/**
 * Basic tests for N4jsc, like checking command line options or simple compile.
 */
public class N4JSXTest extends AbstractN4jscTest {
	File workspace;
	File proot;
	private static final String TEST_DATA_SET__N4JSX = "n4jsx";

	/** Prepare tests. */
	@Before
	public void setupWorkspace() throws IOException {
		workspace = setupWorkspace(TEST_DATA_SET__N4JSX, true);
		proot = new File(workspace, PACKAGES).getAbsoluteFile();
	}

	/** Delete workspace. */
	@After
	public void deleteWorkspace() throws IOException {
		FileDeleter.delete(workspace.toPath(), true);
	}

	/** Compile a single n4jsx file without any n4js files in the workspace. */
	@Test
	public void testOnlyN4JSX() throws ExitCodeException {
		String react = proot + "/react";
		String p1Root = proot + "/" + "P1";

		new N4jscBase().doMain("--projectlocations", proot.toString(), "--buildType", "projects",
				p1Root, react);
		assertFilesCompiledToES(1, p1Root);
	}

	/** Compile several n4js and n4jsx files, with cross-references between the files. */
	@Test
	public void testCombinedWithN4JS() throws ExitCodeException {
		String p2Root = proot + "/" + "P2";
		String react = proot + "/react";

		new N4jscBase().doMain("--projectlocations", proot.toString(), "--buildType", "projects",
				p2Root, react);
		assertFilesCompiledToES(3, p2Root);
	}

	/** Same as {@link #testCombinedWithN4JS()}, but the files are compiled individually using 'singlefile' mode. */
	@Test
	public void testCombinedWithN4JS_singleFile() throws ExitCodeException {
		String p2Root = proot + "/" + "P2";

		new N4jscBase().doMain("--projectlocations", proot.toString(), "--buildType", "singlefile",
				p2Root + "/src/foo.n4js");
		assertFilesCompiledToES(1, p2Root);
		new N4jscBase().doMain("--projectlocations", proot.toString(), "--buildType", "singlefile",
				p2Root + "/src/bar.n4jsx");
		assertFilesCompiledToES(2, p2Root);
		new N4jscBase().doMain("--projectlocations", proot.toString(), "--buildType", "singlefile",
				p2Root + "/src/bar2.n4jsx");
		assertFilesCompiledToES(3, p2Root);
	}

	/** Compile two n4js and n4jsx files, with cyclic cross-references. */
	@Test
	public void testCyclicReferencesWithN4JS() throws ExitCodeException {
		String p3Root = proot + "/" + "P3";

		new N4jscBase().doMain("--projectlocations", proot.toString(), "--buildType", "projects", p3Root);
		assertFilesCompiledToES(2, p3Root);
	}

	/**
	 * Same as {@link #testCyclicReferencesWithN4JS()}, but the files are compiled individually using 'singlefile' mode.
	 */
	@Test
	public void testCyclicReferencesWithN4JS_singleFile() throws ExitCodeException {
		String p3Root = proot + "/" + "P3";

		new N4jscBase().doMain("--projectlocations", proot.toString(), "--buildType", "singlefile",
				p3Root + "/src/foo.n4js");
		assertFilesCompiledToES(1, p3Root);
		new N4jscBase().doMain("--projectlocations", proot.toString(), "--buildType", "singlefile",
				p3Root + "/src/bar.n4jsx");
		assertFilesCompiledToES(2, p3Root);
	}
}
