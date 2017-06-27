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
package org.eclipse.n4js.tests.bugs;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Suppliers.memoize;
import static com.google.common.collect.Sets.newHashSet;
import static org.eclipse.n4js.tests.bugs.AbstractIDEBUG_Test.WorkspaceInitializer.createInitializer;
import static org.eclipse.n4js.tests.util.ProjectUtils.importProject;
import static org.apache.log4j.Logger.getLogger;
import static org.eclipse.core.resources.ResourcesPlugin.getWorkspace;
import static org.eclipse.ui.PlatformUI.isWorkbenchRunning;
import static org.eclipse.xtext.junit4.ui.util.IResourcesSetupUtil.cleanWorkspace;

import java.io.File;
import java.io.PrintWriter;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.xtext.junit4.ui.util.IResourcesSetupUtil;
import org.junit.Before;

import com.google.common.base.Supplier;

import org.eclipse.n4js.tests.builder.AbstractBuilderParticipantTest;
import org.eclipse.n4js.utils.io.FileUtils;
import org.eclipse.n4js.validation.helper.N4JSLanguageConstants;

/**
 * Base class for IDEBUG plug-in tests.
 *
 * Responsible for setting up the workspace for the tests.
 */
@SuppressWarnings("restriction")
public abstract class AbstractIDEBUG_Test extends AbstractBuilderParticipantTest {

	/** Shared logger instance. */
	protected static final Logger LOGGER = getLogger(AbstractIDEBUG_Test.class);

	/**
	 * The prefix of the generation folder. <br>
	 * <! TODO replace hard coded ES5 sub-generator ID once it is clear how to use various sub-generators for runners //
	 * (IDE-1487)>
	 */
	protected static final String ES5_SUB_GENERATOR_ID = N4JSLanguageConstants.TRANSPILER_SUBFOLDER_FOR_TESTS;

	private final Supplier<WorkspaceInitializer> initializerSupplier = memoize(
			() -> createInitializer(getProjectImporter()));

	/**
	 * Sets up the workspace only once.
	 */
	@Before
	public final void setupWorkspace() {
		initializerSupplier.get().initialize();
	}

	/**
	 * Returns with the project importer for the {@link WorkspaceInitializer workspace initializer}.
	 *
	 * <p>
	 * By default returns with the {@link ProjectImporter#NOOP NOOP} importer. Clients may override this method if
	 * importing projects is required as the part of the test setup.
	 *
	 * @return the new project importer instance.
	 */
	protected ProjectImporter getProjectImporter() {
		return ProjectImporter.NOOP;
	}

	/**
	 * Simple project importer implementation.
	 */
	protected static class ProjectImporter {

		/**
		 * The NOOP importer. Does not import anything into the workspace.
		 */
		public static ProjectImporter NOOP = new ProjectImporter();

		private final File rootFolder;

		private ProjectImporter() {
			this(FileUtils.createTempDirectory().toFile());
		}

		/**
		 * Creates a project importer with the root folder of all projects that has to be imported for the test.
		 *
		 * @param rootFolder
		 *            the root folder.
		 */
		public ProjectImporter(final File rootFolder) {
			assertNotNull("Root folder cannot be null.", rootFolder);
			assertTrue("Root folder does not exist: " + rootFolder, rootFolder.exists());
			assertTrue("Root folder must be a folder. But was a file: " + rootFolder, rootFolder.isDirectory());
			assertTrue("Root folder directory content cannot be read: " + rootFolder, rootFolder.canRead());
			assertTrue("No files were found in the directory: " + rootFolder, null != rootFolder.listFiles());
			this.rootFolder = rootFolder;
		}

		void importProjects() throws Exception {
			for (final File file : rootFolder.listFiles()) {
				if (file.exists() && file.isDirectory() && null != file.listFiles() && 0 < file.listFiles().length) {

					if (!newHashSet(file.list()).contains("_project")) {
						final String projectName = file.getName();
						LOGGER.info("Project \'" + projectName
								+ "\' does not have .project file. Creating a temporary one on the fly...");
						final File dotProject = new File(file, "_project");
						assertTrue("Error while trying to create " + dotProject + " file for \'" + projectName
								+ "\' project.", dotProject.createNewFile());
						try (final PrintWriter pw = new PrintWriter(dotProject)) {
							pw.print(DotProjectContentProvider.getDotProjectContentForProject(projectName));
						}
						dotProject.deleteOnExit();
						LOGGER.info("Temporary .project file was successfully created for \'" + projectName
								+ "\' project.");
					}

					LOGGER.info("Importing " + file.getName() + " into workspace...");
					importProject(rootFolder, file.getName());
					LOGGER.info("Project " + file.getName() + " was successfully imported into the workspace.");
				} else {
					LOGGER.warn("Skipped importing project from " + file + ".");
				}
			}
			LOGGER.info("Waiting for full-build to complete...");
			IResourcesSetupUtil.cleanBuild(); // using full build after imports.
			LOGGER.info("Auto-build successfully completed.");
		}

	}

	/**
	 * Workspace initializer responsible to set up the workspace, disable auto build, import projects finally enable the
	 * auto build exactly once.
	 */
	protected static class WorkspaceInitializer {

		private final Supplier<Void> callback;

		/**
		 * Creates a workspace initializer with the given project importer.
		 *
		 * @param importer
		 *            the importer to import the project into the workspace.
		 * @return a new initializer instance.
		 */
		protected static WorkspaceInitializer createInitializer(ProjectImporter importer) {
			assertNotNull("Project importer argument cannot be null.", importer);
			return new WorkspaceInitializer(new Supplier<Void>() {

				@Override
				public Void get() {
					assertTrue("This test requires a running workbench.", isWorkbenchRunning());
					// Disable autobuild.
					toggleAutobuild(false);
					try {
						LOGGER.info("Cleaning workspace...");
						cleanWorkspace();
						LOGGER.info("Workspace cleaned.");
						LOGGER.info("Resetting test data.");
						LOGGER.info("Test data was reseted.");
						LOGGER.info("Importing projects into workspace...");
						importer.importProjects();
						LOGGER.info("Projects were successfully imported into workspace.");
					} catch (final Exception e) {
						LOGGER.error("Error while initializing workspace for test.", e);
						throw new RuntimeException("Error while initializing workspace for test.", e);
					} finally {
						// Re-enable after imports done.
						toggleAutobuild(true);
					}

					return null;
				}
			});
		}

		private static void toggleAutobuild(final boolean enabled) {
			final IWorkspace workspace = getWorkspace();
			final IWorkspaceDescription description = workspace.getDescription();
			description.setAutoBuilding(enabled);
			try {
				LOGGER.info("Turning auto-build " + (enabled ? "on" : "off") + "...");
				workspace.setDescription(description);
				LOGGER.info("Auto-build was successfully turned " + (enabled ? "on" : "off") + ".");
			} catch (final CoreException e) {
				throw new RuntimeException("Error while toggling auto-build", e);
			}
		}

		private WorkspaceInitializer(Supplier<Void> callback) {
			checkNotNull(callback, "callback");
			this.callback = memoize(callback);
		}

		/**
		 * Ensured to initialize the workspace only once even if there are additional subsequent calls.
		 */
		protected void initialize() {
			this.callback.get();
		}

	}

}
