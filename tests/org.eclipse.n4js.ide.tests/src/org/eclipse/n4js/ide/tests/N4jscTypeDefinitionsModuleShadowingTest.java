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
package org.eclipse.n4js.ide.tests;

import static org.eclipse.n4js.cli.N4jscTestOptions.COMPILE;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.eclipse.n4js.cli.helper.AbstractCliCompileTest;
import org.eclipse.n4js.cli.helper.CliCompileResult;
import org.eclipse.n4js.utils.io.FileDeleter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Predicates;

/**
 * Basic tests of type definitions shadowing in the headless case.
 */
public class N4jscTypeDefinitionsModuleShadowingTest extends AbstractCliCompileTest {

	File workspace;
	File packages;

	/** Prepare workspace. */
	@Before
	public void setupWorkspace() throws IOException {
		workspace = setupWorkspace("type-definitions", Predicates.alwaysFalse(), true);
		packages = new File(workspace, PACKAGES);
	}

	/** Delete workspace. */
	@After
	public void deleteWorkspace() throws IOException {
		FileDeleter.delete(workspace.toPath(), true);
	}

	/**
	 * Builds 'Client', 'Def' and 'Impl' and asserts no issues.
	 */
	@Test
	public void testSimpleTypeDefsShadowing() {
		CliCompileResult cliResult = n4jsc(COMPILE(workspace));

		Collection<String> fileNames = cliResult.getTranspiledFileNames();

		assertEquals(cliResult.toString(), 1, cliResult.getTranspiledFilesCount());
		assertEquals(cliResult.toString(), "packages/Client/src-gen/Client.js", String.join(", ", fileNames));
		assertEquals(cliResult.toString(), //
				"packages/Broken_Client/package.json, "//
						+ "packages/Broken_Client/src/Client.n4js, "//
						+ "packages/Client/package.json",
				String.join(", ", cliResult.getErrFiles()));
	}

}
