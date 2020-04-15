/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.tests.builder

import com.google.inject.Injector
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.util.Arrays
import org.eclipse.n4js.N4JSGlobals
import org.eclipse.n4js.N4JSLanguageConstants
import org.eclipse.n4js.ide.tests.server.AbstractIdeTest
import org.eclipse.n4js.ide.tests.server.TestWorkspaceCreator
import org.eclipse.n4js.ide.xtext.server.ProjectStatePersisterConfig
import org.eclipse.n4js.projectDescription.ProjectType
import org.junit.Assert

/**
 * Abstract base class for tests of the incremental builder in the LSP server.
 */
abstract class AbstractIncrementalBuilderTest extends AbstractIdeTest {

	// FIXME reconsider the default in AbstractIdeTest
	override ProjectType getProjectType() {
		return ProjectType.LIBRARY;
	}

	// FIXME reconsider the defaults in ProjectStatePersisterConfig
	override protected Injector createInjector() {
		val injector = super.createInjector();
		val persisterConfig = injector.getInstance(ProjectStatePersisterConfig);
		persisterConfig.setDeleteState(false);
		persisterConfig.setWriteToDisk(true);
		return injector;
	}

	def protected File getOutputFile(String moduleName) {
		return getOutputFile(TestWorkspaceCreator.DEFAULT_PROJECT_NAME, moduleName);
	}

	def protected File getOutputFile(String projectName, String moduleName) {
		return new File(getProjectRoot(projectName), N4JSLanguageConstants.DEFAULT_PROJECT_OUTPUT + File.separator + moduleName + ".js");
	}

	def protected File getProjectStateFile() {
		return getProjectStateFile(TestWorkspaceCreator.DEFAULT_PROJECT_NAME);
	}

	def protected File getProjectStateFile(String projectName) {
		return new File(getProjectRoot(projectName), N4JSGlobals.N4JS_PROJECT_STATE);
	}

	def protected void assertOutputFileExists(String moduleName) {
		assertOutputFileExists(TestWorkspaceCreator.DEFAULT_PROJECT_NAME, moduleName);
	}

	def protected void assertOutputFileExists(String projectName, String moduleName) {
		val outputFile = getOutputFile(projectName, moduleName);
		Assert.assertTrue(Files.isRegularFile(outputFile.toPath()));
	}

	def protected FileSnapshot createSnapshotForOutputFile(String moduleName) {
		return createSnapshotForOutputFile(TestWorkspaceCreator.DEFAULT_PROJECT_NAME, moduleName);
	}

	def protected FileSnapshot createSnapshotForOutputFile(String projectName, String moduleName) {
		return FileSnapshot.create(getOutputFile(projectName, moduleName));
	}

	def protected FileSnapshot createSnapshotForProjectStateFile() {
		return createSnapshotForProjectStateFile(TestWorkspaceCreator.DEFAULT_PROJECT_NAME);
	}

	def protected FileSnapshot createSnapshotForProjectStateFile(String projectName) {
		return FileSnapshot.create(getProjectStateFile(projectName));
	}

	protected static final class FileSnapshot {

		private final File file;
		private byte[] content;

		private new(File file) {
			this.file = file;
			update();
		}

		def static FileSnapshot create(File parent, String child) {
			return create(new File(parent, child));
		}

		def static FileSnapshot create(File file) {
			return new FileSnapshot(file);
		}

		def public File getFile() {
			return file;
		}

		def public void update() {
			try {
				content = Files.readAllBytes(file.toPath)
			} catch (IOException e) {
				throw new AssertionError("error reading from file " + file, e)
			};
		}

		def public void assertExists() {
			Assert.assertTrue("expected file to exist, but it does not exist: " + file, file.exists());
		}

		def public void assertNotExists() {
			Assert.assertFalse("expected file to not exist, but it exists: " + file, file.exists());
		}

		def public void assertUnchanged() {
			assertExists();
			Assert.assertTrue("expected file to be unchanged, but it has changed: " + file, Arrays.equals(content, getFileContent(file)));
		}

		def public void assertChanged() {
			assertExists();
			Assert.assertFalse("expected file to have changed, but it is unchanged: " + file, Arrays.equals(content, getFileContent(file)));
		}
	}

	def private static byte[] getFileContent(File file) {
		try {
			return Files.readAllBytes(file.toPath)
		} catch (IOException e) {
			throw new AssertionError("error reading from file " + file, e)
		};
	}
}
