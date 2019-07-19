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
package org.eclipse.n4js;

import org.eclipse.n4js.internal.InternalN4JSWorkspace;
import org.eclipse.n4js.internal.N4JSModel;
import org.eclipse.n4js.projectModel.IN4JSCore;

/**
 * An injector provider which binds a mock N4JS workspace implementation.
 */
public class N4JSInjectorProviderWithMockProject extends N4JSInjectorProvider {
	/** */
	public N4JSInjectorProviderWithMockProject() {
		super(new MockProjectModule());
	}

	/**
	 * Configure the IN4JSCore instance to use the implementation that is backed by {@link java.io.File files}.
	 */
	public static class MockProjectModule extends BaseTestModule {
		/** */
		public Class<? extends IN4JSCore> bindN4JSCore() {
			return N4JSRuntimeCoreWithMockProject.class;
		}

		/** */
		@Override
		public Class<? extends InternalN4JSWorkspace<MockURIWrapper>> bindInternalN4JSWorkspace() {
			return MockWorkspace.class;
		}

		/** */
		public Class<? extends N4JSModel<?>> bindN4JSModel() {
			return MockN4JSModel.class;
		}

	}
}
