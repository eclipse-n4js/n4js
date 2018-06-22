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
import static org.eclipse.ui.PlatformUI.isWorkbenchRunning;
import static org.eclipse.xtext.ui.testing.util.IResourcesSetupUtil.cleanWorkspace;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.eclipse.n4js.ui.utils.AutobuildUtils;

import com.google.common.base.Supplier;

/**
 * Workspace initializer responsible to set up the workspace, disable auto build, import projects finally enable the
 * auto build exactly once.
 */
class WorkspaceInitializer {

	private static final Logger LOG = LogManager.getLogger(WorkspaceInitializer.class);

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
				AutobuildUtils.set(false);
				try {
					LOG.info("Cleaning workspace...");
					cleanWorkspace();
					LOG.info("Workspace cleaned.");
					LOG.info("Resetting test data.");
					LOG.info("Test data was reseted.");
					LOG.info("Importing projects into workspace...");
					importer.importProjects();
					LOG.info("Projects were successfully imported into workspace.");
				} catch (final Exception e) {
					LOG.error("Error while initializing workspace for test.", e);
					throw new RuntimeException("Error while initializing workspace for test.", e);
				} finally {
					// Re-enable after imports done.
					AutobuildUtils.set(true);
				}

				return null;
			}
		});
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
