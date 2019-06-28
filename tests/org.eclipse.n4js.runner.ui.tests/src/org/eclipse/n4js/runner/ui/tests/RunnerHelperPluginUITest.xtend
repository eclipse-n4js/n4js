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

import com.google.inject.Inject
import java.util.Collections
import java.util.LinkedHashMap
import org.eclipse.core.runtime.NullProgressMonitor
import org.eclipse.emf.common.util.URI
import org.eclipse.n4js.projectModel.IN4JSCore
import org.eclipse.n4js.runner.RunnerHelper
import org.eclipse.n4js.tests.builder.AbstractBuilderParticipantTest
import org.eclipse.n4js.tests.util.ProjectTestsUtils
import org.junit.Test

import static org.junit.Assert.*

/**
 * Contains basic plug-in UI tests for {@link RunnerHelper}.
 */
class RunnerHelperPluginUITest extends AbstractBuilderParticipantTest {

	static val PROJECT_NAME = 'runner.helper.plugin.ui.test';
	static val PROJECT_URI = URI.createPlatformResourceURI(PROJECT_NAME, true);
	static val MODULE_URI = URI.createPlatformResourceURI('''«PROJECT_NAME»/src/A.n4js''', true);

	@Inject
	extension IN4JSCore;

	@Inject
	extension RunnerHelper;

	@Test
	def void testGetCoreProjectPathsWithClosedProject() {
		val actual = new LinkedHashMap(getCoreProjectPaths(Collections.singleton(closedProjectRef)));
		assertTrue(
			'''Expected empty core project paths for closed projects. Was: «actual».''',
			emptyMap == actual);
	}

	@Test
	def void testGetProjectExtendedDepsWithClosedProject() {
		getProjectExtendedDeps('org.eclipse.n4js.runner.nodejs.NODEJS', MODULE_URI);
		// expected no runtime exceptions.
	}

	@Test
	def void testRecursiveDependencyCollector() {
		val actual = recursiveDependencyCollector(closedProjectRef);
		assertTrue(
			'''Expected  empty transitive project dependencies for closed project. Was: «actual».''',
			#[] == actual);
	}


	/**
	 * Creates a new project and closes it.<br>
	 * Returns with a reference to the N4JS project before performing the close operation.
	 */
	private def getClosedProjectRef() {
		try {
			val project = ProjectTestsUtils.createJSProject(PROJECT_NAME);
			val srcFolder = project.getFolder('src');
			createTestFile(srcFolder, 'A', '''console.log('foo');''');
			waitForAutoBuild;
			val n4Project = create(PROJECT_URI);
			assertTrue(
				'''Cannot find N4JS project '«project.name»' in the workspace.''',
				null !== n4Project);

			assertTrue(
				'''Cannot find N4JS module in the new project.''',
				null !== findProject(URI.createPlatformResourceURI('''«PROJECT_NAME»/src/A.n4js''', true)));


			project.close(new NullProgressMonitor);
			waitForAutoBuild;
			val closedN4Project = create(PROJECT_URI);
			assertTrue(
				'''Closed N4JS project '«project.name»' should not be visible in the workspace.''',
				!closedN4Project.exists);
			return n4Project;
		} catch (Exception e) {
			throw new RuntimeException('Error while creating and closing project in workspace.', e);
		}
	}

}
