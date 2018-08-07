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
package org.eclipse.n4js.tester.fsm;

import static com.google.common.base.Preconditions.checkState;
import static com.google.common.cache.CacheBuilder.newBuilder;
import static org.apache.log4j.Logger.getLogger;

import java.util.Objects;

import org.apache.log4j.Logger;
import org.eclipse.n4js.tester.TesterEventBus;
import org.eclipse.n4js.tester.events.SessionEndedEvent;
import org.eclipse.n4js.tester.events.SessionFailedEvent;
import org.eclipse.n4js.tester.events.SessionFinishedEvent;
import org.eclipse.n4js.tester.events.SessionPingedEvent;
import org.eclipse.n4js.tester.events.SessionStartedEvent;
import org.eclipse.n4js.tester.events.TestEndedEvent;
import org.eclipse.n4js.tester.events.TestEvent;
import org.eclipse.n4js.tester.events.TestPingedEvent;
import org.eclipse.n4js.tester.events.TestStartedEvent;
import org.eclipse.n4js.tester.internal.InternalTestTreeRegistry;

import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

/**
 * Singleton FSM registry. Clients may initialize and create new tester FSM instances via this class.
 */
@Singleton
public class TestFsmRegistryImpl implements TestFsmRegistry {

	private static final Logger LOGGER = getLogger(TestFsmRegistryImpl.class);
	private static final boolean debugEnabled = LOGGER.isDebugEnabled();

	@Inject
	private Provider<TestFsm> fsmProvider;

	private final TesterEventBus bus;
	private final InternalTestTreeRegistry treeRegistry;
	private final LoadingCache<String, TestFsm> cache = newBuilder().build(
			new CacheLoader<String, TestFsm>() {
				@Override
				public TestFsm load(final String sessionId) throws Exception {
					return fsmProvider.get().initializeSession(sessionId);
				}
			});

	/**
	 * Constructor for creating the FSM registry instance.
	 *
	 * @param bus
	 *            the event bus for the tester component.
	 * @param treeRegistry
	 *            the registry for test trees.
	 */
	@Inject
	public TestFsmRegistryImpl(final TesterEventBus bus, final InternalTestTreeRegistry treeRegistry) {
		this.bus = bus;
		this.treeRegistry = treeRegistry;
		this.bus.register(this);
	}

	/**
	 * Creates a new FSM with the unique session ID argument and registers it into the cache.
	 *
	 * @param sessionId
	 *            the session ID for the FSM.
	 * @return return with the new FSM instance.
	 */
	@Override
	public TestFsm getTestFsm(final String sessionId) {
		if (debugEnabled) {
			LOGGER.debug("Registering new FSM with '" + sessionId + "' session ID...");
		}
		final TestFsm fsm = getSession(sessionId);
		if (debugEnabled) {
			LOGGER.debug("FSM has been successfully registered with '" + sessionId + "' session ID.");
		}
		return fsm;
	}

	/**
	 * Returns with {@code true} if a tester session exists with the given unique session ID argument.
	 *
	 * @param sessionId
	 *            the unique identifier of the session.
	 * @return {@code true} if the test session exists, otherwise {@code false}.
	 */
	@Override
	public boolean isSessionExist(final String sessionId) {
		return exists(sessionId);
	}

	/**
	 * Percepts any {@link TestEvent test event} and delegates it to the corresponding tester finite state machine.
	 *
	 * @param event
	 *            the received event that will be processed.
	 */
	@Subscribe
	@AllowConcurrentEvents
	public void receivedTestEvent(final TestEvent event) {
		if (event instanceof SessionFailedEvent && ((SessionFailedEvent) event).getComment().isPresent()) {
			LOGGER.error("Received event: " + event + ". Reason: " + ((SessionFailedEvent) event).getComment().get());
		} else {
			if (debugEnabled) {
				LOGGER.debug("Received event: " + event);
			}
		}
		final String sessionId = event.getSessionId();

		if (event instanceof SessionStartedEvent) {
			if (!isSessionExist(sessionId)) {
				getTestFsm(sessionId);
			}
			getSession(sessionId).startSession(sessionId);
		} else if (event instanceof SessionPingedEvent) {
			final long timeout = ((SessionPingedEvent) event).getTimeout();
			getSession(sessionId).pingSession(sessionId, timeout);
		} else if (event instanceof SessionEndedEvent) {
			final TestFsm fsm = getSession(sessionId).endSession(sessionId);
			if (fsm instanceof TestFsmImpl && !((TestFsmImpl) fsm).isFailed()) {
				if (!treeRegistry.validateTestTree(sessionId)) {
					((TestFsmImpl) fsm)
							.fail("Test session has been terminated unexpectedly. "
									+ "There are test cases without any test results after receiving the session ended event.");
					return;
				}
			}
			bus.post(new SessionFinishedEvent(sessionId));
		} else if (event instanceof SessionFailedEvent) {
			treeRegistry.purgeTestTree(sessionId);
			unregisterFsm(sessionId);
		} else if (event instanceof SessionFinishedEvent) {
			unregisterFsm(sessionId);

		} else if (event instanceof TestStartedEvent) {
			final String testId = ((TestStartedEvent) event).getTestId();
			final long timeout = ((TestStartedEvent) event).getTimeout();
			getSession(sessionId).startTest(testId, timeout);
		} else if (event instanceof TestPingedEvent) {
			final long timeout = ((TestPingedEvent) event).getTimeout();
			final String testId = ((TestPingedEvent) event).getTestId();
			getSession(sessionId).pingTest(testId, timeout);
		} else if (event instanceof TestEndedEvent) {
			final TestEndedEvent testEndedEvent = (TestEndedEvent) event;
			final String testId = testEndedEvent.getTestId();
			final TestFsm fsm = getSession(sessionId).endTest(testId);
			if (fsm instanceof TestFsmImpl && !((TestFsmImpl) fsm).isFailed()) {
				treeRegistry.putTestResult(sessionId, testId, testEndedEvent.getResult());
			}
		} else {
			LOGGER.error("Unexpected test event: " + event);
		}
	}

	/**
	 * Removes the FSM with the given session ID argument. Has no effect if no FSMs exist with the given session
	 * identifier.
	 *
	 * @param sessionId
	 *            the unique session ID of the FSM to remove.
	 */
	private void unregisterFsm(final String sessionId) {
		if (debugEnabled) {
			LOGGER.debug("Unregistering FSM with '" + sessionId + "' session ID...");
		}
		cache.invalidate(sessionId);
		checkState(!exists(sessionId), "Error while unregistering session with '" + sessionId + "' session ID.");
		if (debugEnabled) {
			LOGGER.debug("FSM has been successfully unregistered with '" + sessionId + "' session ID.");
		}
	}

	private boolean exists(final String sessionId) {
		return null != cache.getIfPresent(sessionId);
	}

	private TestFsm getSession(final String sessionId) {
		return cache.getUnchecked(sessionId);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append(TestFsmRegistryImpl.class.getSimpleName());
		sb.append(" [");
		sb.append(Objects.hashCode(this));
		sb.append("]");
		return sb.toString();
	}

}
