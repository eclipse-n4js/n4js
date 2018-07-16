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
package org.eclipse.n4js.tester.tests;

import org.eclipse.n4js.N4JSStandaloneSetup;
import org.eclipse.n4js.tester.TestTreeRegistry;
import org.eclipse.n4js.tester.TesterModule;
import org.junit.runner.RunWith;

import com.google.inject.Injector;

/**
 * Class for testing the {@link TestTreeRegistry test tree registry}.
 */
@RunWith(JUnitGuiceClassRunner.class)
@InjectedModules(baseModules = { TesterModule.class }, overrides = {})
abstract public class AbstractTestTreeTest {

	/** Set the parent injector to provide N4JS related instances. */
	@WithParentInjector
	public static Injector getParentInjector() {
		return new N4JSStandaloneSetup().createInjectorAndDoEMFRegistration();
	}

}
