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
package org.eclipse.n4js.ide.tests.builder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.N4JSLanguageConstants;
import org.eclipse.n4js.ide.tests.server.AbstractIdeTest;
import org.eclipse.n4js.ide.tests.server.TestWorkspaceManager;
import org.eclipse.n4js.ide.xtext.server.ProjectStatePersisterConfig;
import org.junit.Assert;

import com.google.inject.Injector;

/**
 * Abstract base class for tests of the incremental builder in the LSP server.
 */
abstract class AbstractIncrementalBuilderTest extends AbstractIdeTest {

	@Override
	protected Injector createInjector() {
		Injector injector = super.createInjector();
		ProjectStatePersisterConfig persisterConfig = injector.getInstance(ProjectStatePersisterConfig.class);
		persisterConfig.setDeleteState(false);
		persisterConfig.setWriteToDisk(true);
		return injector;
	}

	protected File getOutputFile(String moduleName) {
		return getOutputFile(TestWorkspaceManager.DEFAULT_PROJECT_NAME, moduleName);
	}

	protected File getOutputFile(String projectName, String moduleName) {
		return new File(getProjectRoot(projectName),
				N4JSLanguageConstants.DEFAULT_PROJECT_OUTPUT + File.separator + moduleName + ".js");
	}

	protected File getProjectStateFile() {
		return getProjectStateFile(TestWorkspaceManager.DEFAULT_PROJECT_NAME);
	}

	protected File getProjectStateFile(String projectName) {
		return new File(getProjectRoot(projectName), N4JSGlobals.N4JS_PROJECT_STATE);
	}

	protected void assertOutputFileExists(String moduleName) {
		assertOutputFileExists(TestWorkspaceManager.DEFAULT_PROJECT_NAME, moduleName);
	}

	protected void assertOutputFileExists(String projectName, String moduleName) {
		File outputFile = getOutputFile(projectName, moduleName);
		Assert.assertTrue(Files.isRegularFile(outputFile.toPath()));
	}

	protected FileSnapshot createSnapshotForOutputFile(String moduleName) {
		return createSnapshotForOutputFile(TestWorkspaceManager.DEFAULT_PROJECT_NAME, moduleName);
	}

	protected FileSnapshot createSnapshotForOutputFile(String projectName, String moduleName) {
		return FileSnapshot.create(getOutputFile(projectName, moduleName));
	}

	protected FileSnapshot createSnapshotForProjectStateFile() {
		return createSnapshotForProjectStateFile(TestWorkspaceManager.DEFAULT_PROJECT_NAME);
	}

	protected FileSnapshot createSnapshotForProjectStateFile(String projectName) {
		return FileSnapshot.create(getProjectStateFile(projectName));
	}

	protected static final class FileSnapshot {

		private final File file;
		private byte[] content;

		private FileSnapshot(File file) {
			this.file = file;
			update();
		}

		static FileSnapshot create(File parent, String child) {
			return create(new File(parent, child));
		}

		static FileSnapshot create(File file) {
			return new FileSnapshot(file);
		}

		public File getFile() {
			return file;
		}

		public void update() {
			content = getFileContent(file);
		}

		public void assertExists() {
			Assert.assertTrue("expected file to exist, but it does not exist: " + file, file.exists());
		}

		public void assertNotExists() {
			Assert.assertFalse("expected file to not exist, but it exists: " + file, file.exists());
		}

		public void assertUnchanged() {
			assertExists();
			Assert.assertTrue("expected file to be unchanged, but it has changed: " + file,
					Arrays.equals(content, getFileContent(file)));
		}

		public void assertChanged() {
			assertExists();
			Assert.assertFalse("expected file to have changed, but it is unchanged: " + file,
					Arrays.equals(content, getFileContent(file)));
		}
	}

	private static byte[] getFileContent(File file) {
		try {
			return Files.readAllBytes(file.toPath());
		} catch (IOException e) {
			throw new AssertionError("error reading from file " + file, e);
		}
	}
}
