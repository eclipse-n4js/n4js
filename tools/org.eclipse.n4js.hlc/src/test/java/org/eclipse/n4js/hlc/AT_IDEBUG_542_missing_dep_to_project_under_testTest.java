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
package org.eclipse.n4js.hlc;

import java.io.IOException;

import org.eclipse.n4js.hlc.base.N4jscBase;
import org.eclipse.n4js.hlc.base.N4jscBase.ExitCodeException;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.base.Predicates;

/**
 */
public class AT_IDEBUG_542_missing_dep_to_project_under_testTest extends AbstractN4jscTest {

	static String WSP_542 = "IDEBUG-542";

	/**
	 * Setup workspace with api & api-impl & compile
	 *
	 */
	@BeforeClass
	public static void setupWorkspace() throws IOException {
		setupWorkspace(WSP_542, Predicates.alwaysTrue());
	}

	/** The Problem was, that nothing was compiled. */
	@Test
	public void testCompileOfExtendedIterator_from_RuntimeLibrary() throws ExitCodeException {
		System.out.println(logMethodname());

		String proot = TARGET + "/" + WSP;

		String[] args = { "-pl", proot,
				"-t", "allprojects",
				"-v"
		};

		// compile
		new N4jscBase().doMain(args);

		// Make sure, we get here and have exactly two files compiled:
		assertFilesCompiledToES(2, proot);

	}
}
