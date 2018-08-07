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

import org.eclipse.n4js.tester.TesterEventBus;
import org.eclipse.n4js.tester.events.SessionFinishedEvent;
import org.eclipse.n4js.tester.fsm.TestFsm;
import org.eclipse.n4js.tester.fsm.TestFsmRegistryImpl;
import org.eclipse.n4js.tester.internal.InternalTestTreeRegistry;

import com.google.inject.Inject;

/**
 * Test FSM registry implementation creating and registering a {@link DelegatingTestFsm} instances.
 */
public class MockTestFsmRegistry extends TestFsmRegistryImpl {

	private final TesterEventBus bus;

	/***/
	@Inject
	public MockTestFsmRegistry(final TesterEventBus bus, final InternalTestTreeRegistry treeRegistry) {
		super(bus, treeRegistry);
		this.bus = bus;
	}

	@Override
	public TestFsm getTestFsm(final String sessionId) {
		return new DelegatingTestFsm(super.getTestFsm(sessionId));
	}

	private final class DelegatingTestFsm implements TestFsm {
		private final TestFsm delegate;

		private DelegatingTestFsm(final TestFsm fsm) {
			delegate = fsm;
		}

		@Override
		public TestFsm initializeSession(final String sessionId) {
			delegate.initializeSession(sessionId);
			return this;
		}

		@Override
		public TestFsm startSession(final String sessionId) {
			delegate.startSession(sessionId);
			return this;
		}

		@Override
		public TestFsm endSession(final String sessionId) {
			delegate.endSession(sessionId);
			bus.post(new SessionFinishedEvent(sessionId));
			return this;
		}

		@Override
		public TestFsm pingSession(final String sessionId, final long timeout) {
			delegate.pingSession(sessionId, timeout);
			return this;
		}

		@Override
		public TestFsm startTest(final String testId, final long timeout) {
			delegate.startTest(testId, timeout);
			return this;
		}

		@Override
		public TestFsm endTest(final String testId) {
			delegate.endTest(testId);
			return this;
		}

		@Override
		public TestFsm pingTest(final String testId, final long timeout) {
			delegate.pingTest(testId, timeout);
			return this;
		}

	}

}
