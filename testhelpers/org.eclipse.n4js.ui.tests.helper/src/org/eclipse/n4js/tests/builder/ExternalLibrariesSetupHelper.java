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
package org.eclipse.n4js.tests.builder;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.n4js.external.LibraryManager;
import org.eclipse.n4js.tests.util.ProjectTestsUtils;
import org.eclipse.n4js.tests.util.ShippedCodeInitializeTestHelper;
import org.eclipse.n4js.ui.internal.N4JSActivator;

import com.google.inject.Inject;
import com.google.inject.Injector;

/**
 * Use this test helper to set up and tear down the external libraries.
 */
public class ExternalLibrariesSetupHelper {

	@Inject
	private ShippedCodeInitializeTestHelper shippedCodeInitializeTestHelper;

	/** Sets up the known external library locations with the {@code node_modules} folder. */
	public void setupShippedLibraries() throws Exception {
		shippedCodeInitializeTestHelper.setupBuiltIns();

		Injector n4jsInjector = N4JSActivator.getInstance().getInjector("org.eclipse.n4js.N4JS");
		LibraryManager libMan = n4jsInjector.getInstance(LibraryManager.class);
		libMan.registerAllExternalProjects(new NullProgressMonitor());

		ProjectTestsUtils.waitForAutoBuild();
	}

	/** Tears down the external libraries. */
	public void tearDownShippedLibraries() throws Exception {
		shippedCodeInitializeTestHelper.tearDownBuiltIns();

		Injector n4jsInjector = N4JSActivator.getInstance().getInjector("org.eclipse.n4js.N4JS");
		LibraryManager libMan = n4jsInjector.getInstance(LibraryManager.class);
		libMan.registerAllExternalProjects(new NullProgressMonitor());

		ProjectTestsUtils.waitForAutoBuild();
	}

}
