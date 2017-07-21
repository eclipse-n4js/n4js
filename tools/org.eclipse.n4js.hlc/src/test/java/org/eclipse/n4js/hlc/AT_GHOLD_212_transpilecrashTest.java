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
public class AT_GHOLD_212_transpilecrashTest extends AbstractN4jscTest {

	static String WSP_212 = "GHOLD-212";

	/**
	 * Setup workspace with api & api-impl & compile
	 *
	 */
	@BeforeClass
	public static void setupWorkspace() throws IOException {
		setupWorkspace(WSP_212, Predicates.alwaysTrue());
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

		// Make sure, we get here and have exactly one file compiled:
		assertFilesCompiledToES(1, proot);

	}
}
