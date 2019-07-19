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
import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStream
import org.eclipse.core.resources.IWorkspace
import org.eclipse.core.runtime.CoreException
import org.eclipse.core.runtime.Platform
import org.eclipse.n4js.N4JSUiInjectorProvider
import org.eclipse.n4js.projectModel.IN4JSProject
import org.eclipse.n4js.projectModel.locations.PlatformResourceURI
import org.eclipse.n4js.runner.tests.RuntimeEnvironmentResolutionTest
import org.eclipse.n4js.tests.builder.TestedN4JSWorkspace
import org.eclipse.xtext.testing.InjectWith
import org.junit.BeforeClass
import org.junit.Rule

import static org.apache.log4j.Logger.getLogger
import static org.eclipse.core.resources.ResourcesPlugin.getWorkspace
import static org.eclipse.core.runtime.Platform.isRunning
import static org.junit.Assert.*

/**
 * Class for testing the the runtime environment resolution for the N4 runners in standalone JUnit mode.
 * <p>
 * <b>NOTE:&nbsp;</b>This test class is responsible to execute exactly the same tests as defined in its
 * {@link RuntimeEnvironmentResolutionTest super class} but this implementation uses the {@link IWorkspace Eclipse workspace}
 * so it requires a running {@link Platform platform}.
 */
@InjectWith(N4JSUiInjectorProvider)
class RuntimeEnvironmentResolutionPluginUITest extends RuntimeEnvironmentResolutionTest {

	static val LOGGER = getLogger(RuntimeEnvironmentResolutionPluginUITest)
	static val NATURE_ID = "org.eclipse.xtext.ui.shared.xtextNature"; //$NON-NLS-1$

	@Inject
	@Rule
	public TestedN4JSWorkspace testedWorkspace 

	@BeforeClass
	public static def beforeClass() {
		assertTrue('Platform is not running. These tests should be executed as JUnit Plug-in Test.', isRunning)
	}

	override protected createProjectWithPackageJson(String projectName, String packageJsonContent) {
		val project = workspace.root.getProject(projectName)
		assertFalse(project.exists)
		try {
			project.create(null)
		} catch (CoreException e) {
			LOGGER.error('Error while creating project in the workspace.', e)
		}
		assertTrue(project.exists)

		if (!project.open) {
			try {
				project.open(null)
			} catch (CoreException e) {
				LOGGER.error('Error while trying to opening project.', e)
			}
		}

		try {
			testedWorkspace.addNature(project, NATURE_ID)
		} catch (CoreException e) {
			LOGGER.error('Error while trying to adding Xtext nature to the project.', e)
		}

		val packageJsonFile = project.getFile(IN4JSProject.PACKAGE_JSON)
		assertFalse(packageJsonFile.exists)

		var is = null as InputStream
		var exc = null as Exception
		try {
			is = new ByteArrayInputStream(packageJsonContent.bytes)
			packageJsonFile.create(is, true, null)
		} catch (Exception e) {
			LOGGER.error('''Error while creating package.json file for project: '«projectName»'.''')
			Throwables.throwIfUnchecked(e);
			throw new RuntimeException(e)
		} finally {
			if (null !== is) {
				try {
					is.close
				} catch (IOException e) {
					exc = e
					try {
						is.close
					} catch (IOException e2) {
						e.addSuppressed(e2)
						// Intentionally ignored.
					}
				}
			}
		}
		if (null !== exc) {
			throw new RuntimeException('Error while closing the stream for the N4JS package.json file.', exc)
		}

		assertTrue(packageJsonFile.exists)
		testedWorkspace.build
		return new PlatformResourceURI(project);
	}

}
