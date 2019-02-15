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

import static com.google.common.base.Suppliers.memoize;
import static org.apache.log4j.Logger.getLogger;
import static org.eclipse.n4js.tests.bugs.WorkspaceInitializer.createInitializer;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.n4js.tests.builder.AbstractBuilderParticipantTest;
import org.eclipse.n4js.tests.util.ProjectTestsUtils;
import org.junit.Before;

import com.google.common.base.Supplier;

/**
 * Base class for IDEBUG plug-in tests.
 *
 * Responsible for setting up the workspace for the tests.
 */
public abstract class AbstractIDEBUG_Test extends AbstractBuilderParticipantTest {

	/** Shared logger instance. */
	protected static final Logger LOGGER = getLogger(AbstractIDEBUG_Test.class);

	private final Supplier<WorkspaceInitializer> initializerSupplier = memoize(
			() -> createInitializer(getProjectImporter()));

	/**
	 * Sets up the workspace only once.
	 */
	@Before
	public final void setupWorkspace() {
		initializerSupplier.get().initialize();
		ProjectTestsUtils.waitForAllJobs();
		libraryManager.registerAllExternalProjects(new NullProgressMonitor());
		ProjectTestsUtils.waitForAllJobs();
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

}
