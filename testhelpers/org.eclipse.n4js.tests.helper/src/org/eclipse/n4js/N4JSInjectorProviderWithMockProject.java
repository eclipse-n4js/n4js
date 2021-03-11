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

import org.eclipse.n4js.tests.helper.mock.MockConcurrentIndex;
import org.eclipse.n4js.tests.helper.mock.MockWorkspaceConfigFactory;
import org.eclipse.n4js.xtext.server.XIWorkspaceConfigFactory;
import org.eclipse.n4js.xtext.server.build.ConcurrentIndex;

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
		public Class<? extends XIWorkspaceConfigFactory> bindXIWorkspaceConfigFactory() {
			return MockWorkspaceConfigFactory.class;
		}

		/** */
		public Class<? extends ConcurrentIndex> bindConcurrentIndex() {
			return MockConcurrentIndex.class;
		}

	}
}
