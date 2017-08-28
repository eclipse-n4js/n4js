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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.n4js.tester.TesterEventBus;
import org.eclipse.n4js.tester.events.TestEvent;
import org.eclipse.n4js.tester.fsm.TestFsm;
import org.eclipse.n4js.tester.fsm.TestFsmRegistry;
import org.eclipse.n4js.tester.fsm.TestFsmRegistryImpl;
import org.eclipse.n4js.tester.internal.InternalTestTreeRegistry;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Customized module in respect of the {@link TestFsmRegistry} implementation.
 *
 * This module can be used to get information about the test events that are created in consequence of server requests.
 */
public class LoggingMockResourceTesterModule extends MockTesterModule {

	@Override
	protected void configure() {
		bind(TestFsmRegistry.class).to(LoggingFsmRegistry.class);
	}

	/**
	 * A mock-implementation of {@link TestFsmRegistryImpl} that keeps track of all processed test events.
	 */
	@Singleton
	public static final class LoggingFsmRegistry extends TestFsmRegistryImpl {

		private final List<TestEvent> events = new ArrayList<>();

		/**
		 * Initializes a new {@link LoggingFsmRegistry}.
		 */
		@Inject
		public LoggingFsmRegistry(final TesterEventBus bus, final InternalTestTreeRegistry registry) {
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
			this.events.add(event);
		}

		/**
		 * Returns a list of logged test events. New events are added to the end of the list.
		 */
		public List<TestEvent> getEvents() {
			return events;
		}

	}

}
