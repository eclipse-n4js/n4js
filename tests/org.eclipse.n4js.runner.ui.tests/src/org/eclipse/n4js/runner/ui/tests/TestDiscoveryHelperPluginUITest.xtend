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
package org.eclipse.n4js.runner.ui.tests

import com.google.common.base.Throwables
import com.google.inject.Inject
import org.eclipse.core.resources.IWorkspace
import org.eclipse.core.runtime.CoreException
import org.eclipse.core.runtime.NullProgressMonitor
import org.eclipse.emf.common.util.URI
import org.eclipse.n4js.projectModel.IN4JSCore
import org.eclipse.n4js.tester.TestDiscoveryHelper
import org.eclipse.n4js.tests.builder.AbstractBuilderTest
import org.eclipse.n4js.tests.util.ProjectTestsUtils
import org.junit.Test

import static org.junit.Assert.*

/**
 * Plugin UI test for {@link TestDiscoveryHelper}. Tests special cases such as a closed project
 * in a {@link IWorkspace Eclipse based workspace}.
 */
class TestDiscoveryHelperPluginUITest extends AbstractBuilderTest {

	static val PROJECT_NAME = 'test.discovery.helper.plugin.ui.test';
	static val PROJECT_URI = URI.createPlatformResourceURI(PROJECT_NAME, true);

	@Inject
	extension IN4JSCore;

	@Inject
	extension TestDiscoveryHelper;

	@Test
	def void testNoErrorsForClosedProjects() throws CoreException {

		val project = ProjectTestsUtils.createJSProject(PROJECT_NAME);
		waitForAutoBuild;
		val n4Project = create(PROJECT_URI);
		assertTrue('''Cannot find N4JS project '«project.name»' in the workspace.''', null !== n4Project);

		project.close(new NullProgressMonitor);
		waitForAutoBuild;
		val closedN4Project = create(PROJECT_URI);
		assertTrue('''Closed N4JS project '«project.name»' should not be visible in the workspace.''', !closedN4Project.exists);

		// Should not throw exception, but should return with false
		try {
			assertTrue('''Closed N4JS project '«project.name»' should not be testable''', !isTestable(n4Project.getLocation.toURI));
		} catch (Exception e) {
			Throwables.throwIfUnchecked(e);
			throw new RuntimeException(e);
		}
	}

}
