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

import java.io.IOException;

import org.eclipse.n4js.hlc.base.ExitCodeException;
import org.eclipse.n4js.hlc.base.N4jscBase;
import org.junit.Before;
import org.junit.Test;

/**
 * Basic tests for N4jsc, like checking command line options or simple compile.
 */
public class N4JSXTest extends AbstractN4jscTest {

	private static final String TEST_DATA_SET__N4JSX = "n4jsx";
	private static final String WS_ROOT = TARGET + "/" + WSP;

	/** Prepare tests. */
	@Before
	public void setupWorkspace() throws IOException {
		setupWorkspace(TEST_DATA_SET__N4JSX);
	}

	/** Compile a single n4jsx file without any n4js files in the workspace. */
	@Test
	public void testOnlyN4JSX() throws ExitCodeException {
		System.out.println(logMethodname());

		String p1Root = WS_ROOT + "/" + "P1";

		new N4jscBase().doMain("-pl", WS_ROOT, "-t", "projects", p1Root);
		assertFilesCompiledToES(1, p1Root);
	}

	/** Compile several n4js and n4jsx files, with cross-references between the files. */
	@Test
	public void testCombinedWithN4JS() throws ExitCodeException {
		System.out.println(logMethodname());

		String p2Root = WS_ROOT + "/" + "P2";

		new N4jscBase().doMain("-pl", WS_ROOT, "-t", "projects", p2Root);
		assertFilesCompiledToES(3, p2Root);
	}

	/** Same as {@link #testCombinedWithN4JS()}, but the files are compiled individually using 'singlefile' mode. */
	@Test
	public void testCombinedWithN4JS_singleFile() throws ExitCodeException {
		System.out.println(logMethodname());

		String p2Root = WS_ROOT + "/" + "P2";

		new N4jscBase().doMain("-pl", WS_ROOT, "-t", "singlefile", p2Root + "/src/foo.n4js");
		assertFilesCompiledToES(1, p2Root);
		new N4jscBase().doMain("-pl", WS_ROOT, "-t", "singlefile", p2Root + "/src/bar.n4jsx");
		assertFilesCompiledToES(2, p2Root);
		new N4jscBase().doMain("-pl", WS_ROOT, "-t", "singlefile", p2Root + "/src/bar2.n4jsx");
		assertFilesCompiledToES(3, p2Root);
	}

	/** Compile two n4js and n4jsx files, with cyclic cross-references. */
	@Test
	public void testCyclicReferencesWithN4JS() throws ExitCodeException {
		System.out.println(logMethodname());

		String p3Root = WS_ROOT + "/" + "P3";

		new N4jscBase().doMain("-pl", WS_ROOT, "-t", "projects", p3Root);
		assertFilesCompiledToES(2, p3Root);
	}

	/**
	 * Same as {@link #testCyclicReferencesWithN4JS()}, but the files are compiled individually using 'singlefile' mode.
	 */
	@Test
	public void testCyclicReferencesWithN4JS_singleFile() throws ExitCodeException {
		System.out.println(logMethodname());

		String p3Root = WS_ROOT + "/" + "P3";

		new N4jscBase().doMain("-pl", WS_ROOT, "-t", "singlefile", p3Root + "/src/foo.n4js");
		assertFilesCompiledToES(1, p3Root);
		new N4jscBase().doMain("-pl", WS_ROOT, "-t", "singlefile", p3Root + "/src/bar.n4jsx");
		assertFilesCompiledToES(2, p3Root);
	}
}
