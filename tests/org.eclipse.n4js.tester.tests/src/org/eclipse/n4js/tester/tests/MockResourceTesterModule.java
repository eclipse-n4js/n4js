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

import com.google.inject.Inject;
import com.google.inject.Singleton;

import org.eclipse.n4js.tester.TesterEventBus;
import org.eclipse.n4js.tester.events.TestEvent;
import org.eclipse.n4js.tester.fsm.TestFsm;
import org.eclipse.n4js.tester.fsm.TestFsmRegistry;
import org.eclipse.n4js.tester.fsm.TestFsmRegistryImpl;
import org.eclipse.n4js.tester.internal.InternalTestTreeRegistry;

/**
 * Customized module in respect of the {@link TestFsmRegistry} implementation. This module is used for testing the
 * RESTful API not the behavior of the underlying logic.
 */
public class MockResourceTesterModule extends MockTesterModule {

	@Override
	protected void configure() {
		bind(TestFsmRegistry.class).to(NoopFsmRegistry.class);
	}

	@Singleton
	private static final class NoopFsmRegistry extends TestFsmRegistryImpl {

		@Inject
		public NoopFsmRegistry(final TesterEventBus bus, final InternalTestTreeRegistry registry) {
			super(bus, registry);
		}

		@Override
		public boolean isSessionExist(final String sessionId) {
			return true;
		}

		@Override
		public TestFsm registerFsm(final String sessionId) {
			return null;
		}

		@Override
		public void receivedTestEvent(final TestEvent event) {
			// NOOP
		}

	}

}
