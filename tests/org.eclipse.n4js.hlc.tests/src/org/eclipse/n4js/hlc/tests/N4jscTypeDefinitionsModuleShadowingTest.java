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

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.eclipse.n4js.hlc.base.ErrorExitCode;
import org.eclipse.n4js.hlc.base.ExitCodeException;
import org.eclipse.n4js.hlc.base.N4jscBase;
import org.eclipse.n4js.hlc.base.SuccessExitStatus;
import org.eclipse.n4js.utils.io.FileDeleter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Predicates;

/**
 * Basic tests of type definitions shadowing in the headless case.
 */
public class N4jscTypeDefinitionsModuleShadowingTest extends AbstractN4jscTest {
	private static final String DEF_PROJECT_NAME = "Def";
	private static final String BROKEN_DEF_PROJECT_NAME = "Broken_Def";

	private static final String IMPL_PROJECT_NAME = "Impl";

	private static final String CLIENT_PROJECT_NAME = "Client";
	private static final String BROKEN_CLIENT_PROJECT_NAME = "Broken_Client";

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
	public void testSimpleTypeDefsShadowing() throws ExitCodeException {

		String[] args = { "--projectlocations", packages.toPath().toAbsolutePath().toString(),
				"--buildType", "projects", getProjectPath(DEF_PROJECT_NAME), getProjectPath(IMPL_PROJECT_NAME),
				getProjectPath(CLIENT_PROJECT_NAME) };

		SuccessExitStatus status = new N4jscBase().doMain(args);
		assertEquals("Should exit with success status (no compilation issues).", SuccessExitStatus.INSTANCE.code,
				status.code);
	}

	/**
	 * Builds 'Broken_Client', 'Broken_Def' and 'Impl' and expects that this will cause compilation issues.
	 */
	@Test
	public void testBrokenTypeDefsShadowing() {

		String[] args = { "--projectlocations", packages.toPath().toAbsolutePath().toString(),
				"--buildType", "projects", getProjectPath(BROKEN_DEF_PROJECT_NAME), getProjectPath(IMPL_PROJECT_NAME),
				getProjectPath(BROKEN_CLIENT_PROJECT_NAME) };

		expectCompilerException(args, ErrorExitCode.EXITCODE_COMPILE_ERROR);
	}

	private String getProjectPath(String projectName) {
		return this.packages.toPath().toAbsolutePath() + "/" + projectName;
	}

}
