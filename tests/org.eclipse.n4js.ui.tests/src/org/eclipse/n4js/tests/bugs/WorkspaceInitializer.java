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
package org.eclipse.n4js.tests.bugs;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Suppliers.memoize;
import static org.eclipse.core.resources.ResourcesPlugin.getWorkspace;
import static org.eclipse.ui.PlatformUI.isWorkbenchRunning;
import static org.eclipse.xtext.ui.testing.util.IResourcesSetupUtil.cleanWorkspace;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.runtime.CoreException;

import com.google.common.base.Supplier;

/**
 * Workspace initializer responsible to set up the workspace, disable auto build, import projects finally enable the
 * auto build exactly once.
 */
class WorkspaceInitializer {

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
					AbstractIDEBUG_Test.LOGGER.info("Cleaning workspace...");
					cleanWorkspace();
					AbstractIDEBUG_Test.LOGGER.info("Workspace cleaned.");
					AbstractIDEBUG_Test.LOGGER.info("Resetting test data.");
					AbstractIDEBUG_Test.LOGGER.info("Test data was reseted.");
					AbstractIDEBUG_Test.LOGGER.info("Importing projects into workspace...");
					importer.importProjects();
					AbstractIDEBUG_Test.LOGGER.info("Projects were successfully imported into workspace.");
				} catch (final Exception e) {
					AbstractIDEBUG_Test.LOGGER.error("Error while initializing workspace for test.", e);
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
			AbstractIDEBUG_Test.LOGGER.info("Turning auto-build " + (enabled ? "on" : "off") + "...");
			workspace.setDescription(description);
			AbstractIDEBUG_Test.LOGGER.info("Auto-build was successfully turned " + (enabled ? "on" : "off") + ".");
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
