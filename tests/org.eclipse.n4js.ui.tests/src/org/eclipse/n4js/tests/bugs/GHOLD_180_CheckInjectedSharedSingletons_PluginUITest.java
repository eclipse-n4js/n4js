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

import org.junit.Test;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

import com.google.inject.Injector;
import com.google.inject.Singleton;

import org.eclipse.n4js.typesystem.N4JSTypeSystem;
import org.eclipse.n4js.ui.internal.N4JSActivator;
import org.eclipse.n4js.ui.utils.N4JSGuiceUIPlugin;
import org.eclipse.n4js.utils.collections.Arrays2;

/**
 * Class for checking whether singleton instances are shared among parent-child injectors.
 */
public class GHOLD_180_CheckInjectedSharedSingletons_PluginUITest extends AbstractIDEBUG_Test {

	@Override
	protected ProjectImporter getProjectImporter() {
		return ProjectImporter.NOOP;
	}

	/**
	 * Checks whether the same instance is provided by different injector instances when the class of the provided
	 * instance is annotated with {@code @Singleton}.
	 */
	@Test
	public void testInjectorsSharingSameSingleton() throws Exception {

		final Class<N4JSTypeSystem> testedType = N4JSTypeSystem.class;
		final Singleton[] singletons = testedType.getAnnotationsByType(Singleton.class);
		assertTrue(testedType.getSimpleName() + " is not annotated with " + Singleton.class.getName() + ".",
				!Arrays2.isEmpty(singletons));

		final String injectorId = N4JSActivator.ORG_ECLIPSE_N4JS_N4JS;
		final Injector parentInjector = N4JSActivator.getInstance().getInjector(injectorId);
		final MockUIPlugin mockBundle = new MockUIPlugin();

		try {
			mockBundle.start(/* context */ null);
			assertTrue("Mock bundle is not running yet.", Bundle.ACTIVE == mockBundle.getBundle().getState());
			final Injector childInjector = mockBundle.getN4JSChildInjector();

			final N4JSTypeSystem instanceFromParent = parentInjector.getInstance(testedType);
			final N4JSTypeSystem instanceFromChild = childInjector.getInstance(testedType);

			assertTrue(
					"Expected the same instance of " + testedType.getSimpleName() + " from parent and child injectors.",
					instanceFromChild == instanceFromParent);

		} finally {
			mockBundle.stop(/* context */ null);
		}
	}

	/**
	 * Mock UI plugin.
	 */
	private static final class MockUIPlugin extends N4JSGuiceUIPlugin {

		@Override
		protected void doStart(final BundleContext context) throws Exception {
			// Nothing.
		}

		@Override
		protected void doStop(final BundleContext context) throws Exception {
			// Nothing.
		}

	}

}
