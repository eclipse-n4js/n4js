/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.tests.project;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.n4js.tests.builder.AbstractBuilderParticipantTest;
import org.eclipse.n4js.tests.util.ProjectTestsUtils;
import org.eclipse.n4js.utils.ProjectDescriptionUtils;
import org.junit.Test;

import com.google.common.base.Optional;

@SuppressWarnings("javadoc")
public class NpmScopesPluginTest extends AbstractBuilderParticipantTest {

	private static final String PROBANDS = "probands";
	private static final String WORKSPACE_BASE = "npmScopes";

	@Test
	public void test01() throws CoreException {
		String testName = "test01_successCase";
		importProject(testName, Optional.of("@myScope"), "Lib");
		importProject(testName, Optional.absent(), "Lib");
		importProject(testName, Optional.absent(), "XClient");
		waitForAutoBuild();
		assertNoIssues();
	}

	private void importProject(String testName, Optional<String> scopeName, String projectName) {
		if (scopeName.isPresent()) {
			assertTrue(scopeName.get().startsWith(ProjectDescriptionUtils.NPM_SCOPE_PREFIX));
		}
		try {
			File parentFolder = getFolderContainingTestProject(testName, scopeName);
			ProjectTestsUtils.importProject(parentFolder, projectName);
		} catch (CoreException e) {
			throw new RuntimeException("exception while importing test project", e);
		}
	}

	private File getFolderContainingTestProject(String testName, Optional<String> scopeName) {
		if (scopeName.isPresent()) {
			return new File(getResourceUri(PROBANDS, WORKSPACE_BASE, testName, scopeName.get()));
		}
		return new File(getResourceUri(PROBANDS, WORKSPACE_BASE, testName));
	}
}
