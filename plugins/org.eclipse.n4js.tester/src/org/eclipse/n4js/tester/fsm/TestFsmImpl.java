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

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Strings.isNullOrEmpty;
import static com.google.common.cache.CacheBuilder.newBuilder;
import static org.eclipse.n4js.tester.TesterModuleDefaults.SETUP_FSM_TIMEOUT_KEY;
import static org.eclipse.n4js.tester.fsm.FsmState.EXECUTING_TESTS;
import static org.eclipse.n4js.tester.fsm.FsmState.FAILED;
import static org.eclipse.n4js.tester.fsm.FsmState.IDLE;
import static org.eclipse.n4js.tester.fsm.FsmState.NOT_INITIALIZED;
import static org.eclipse.n4js.tester.fsm.FsmState.SESSION_STARTED;

import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.eclipse.n4js.tester.TesterEventBus;
import org.eclipse.n4js.tester.events.SessionFailedEvent;

import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import com.google.inject.name.Named;

/**
 * Stateful finite state machine implementation for the tester component.
 */
public class TestFsmImpl implements TestFsm {

	private static final Logger LOGGER = Logger.getLogger(TestFsmImpl.class);

	private final TesterEventBus bus;
	private final LoadingCache<String, String> visitedTestIds;
	private final LoadingCache<String, String> executedTestIds;
	private final TestTimeouter testTimeouter;
	private final SessionTimeouter setupTimeouter;

	private volatile long setupTimeout;
	private volatile FsmState currentState;
	private String currentSessionId;

	/**
	 * Constructor for creating the with the {@link FsmState#NOT_INITIALIZED not initialized} state.
	 *
	 * @param bus
	 *            the shared event bus for the tester component.
	 * @param setupTimeout
	 *            the setup timeout in milliseconds.
	 */
	@Inject
	public TestFsmImpl(final TesterEventBus bus, @Named(SETUP_FSM_TIMEOUT_KEY) final long setupTimeout) {

		this.bus = bus;
		this.setupTimeout = setupTimeout;

		visitedTestIds = newBuilder().build(getTestIdCacheLoader());
		executedTestIds = newBuilder().build(getTestIdCacheLoader());
		currentState = NOT_INITIALIZED;
		setupTimeouter = new SessionTimeouter();
		testTimeouter = new TestTimeouter();
	}

	@Override
	public synchronized TestFsmImpl initializeSession(final String sessionId) {
		if (isFailed()) {
			return this;
		}
		System.err.println("init: " + sessionId);
		new IllegalStateException().printStackTrace();
		this.currentSessionId = checkNotNull(sessionId, "Session ID argument cannot be null.");
		if (NOT_INITIALIZED == currentState) {
			currentState = IDLE;
			setupTimeouter.scheduleTimeouter(setupTimeout);
			return this;
		}
		return fail("Failed when initializing test session. " + toString());
	}

	@Override
	public synchronized TestFsmImpl startSession(final String sessionId) {
		if (isFailed()) {
			return this;
		}
		if (IDLE == currentState) {
			if (this.currentSessionId.equals(sessionId)) {
				setupTimeouter.scheduleTimeouter(setupTimeout);
				currentState = SESSION_STARTED;
				return this;
			}
		}
		return fail("Failed when starting test session. " + toString());
	}

	@Override
	public synchronized TestFsmImpl endSession(final String sessionId) {
		if (isFailed()) {
			return this;
		}
		System.err.println("end: " + sessionId);
		new IllegalStateException().printStackTrace();
		if (SESSION_STARTED == currentState || EXECUTING_TESTS == currentState || IDLE == currentState) {
			if (this.currentSessionId.equals(sessionId)) {
				return dispose(NOT_INITIALIZED);
			}
		}
		return fail("Failed when ending test session. " + toString());
	}

	@Override
	public synchronized TestFsmImpl pingSession(final String sessionId, final long timeout) {
		if (isFailed()) {
			return this;
		}
		if (SESSION_STARTED == currentState) {
			if (currentSessionId.equals(sessionId)) {
				setupTimeout = timeout;
				setupTimeouter.scheduleTimeouter(setupTimeout);
				return this;
			}
		}
		return fail("Failed when pinging test session. " + toString());
	}

	@Override
	public synchronized TestFsmImpl startTest(final String testId, final long timeout) {
		if (isFailed()) {
			return this;
		}
		if (SESSION_STARTED == currentState || EXECUTING_TESTS == currentState) {
			if (!isNullOrEmpty(testId) && null == visitedTestIds.getIfPresent(testId)) {
				setupTimeouter.cancel();
				currentState = EXECUTING_TESTS;
				testTimeouter.schedule(testId, timeout);
				visitedTestIds.getUnchecked(testId);
				return this;
			}
		}
		return fail("Failed when starting test: " + testId + " " + toString());
	}

	@Override
	public synchronized TestFsmImpl endTest(final String testId) {
		if (isFailed()) {
			return this;
		}
		if (EXECUTING_TESTS == currentState) {
			if (!isNullOrEmpty(testId) && null != visitedTestIds.getIfPresent(testId)) {
				currentState = EXECUTING_TESTS;
				testTimeouter.cancel(testId);
				executedTestIds.getUnchecked(testId);
				return this;
			}
		}
		return fail("Failed when ending test: " + testId + " " + toString());
	}

	@Override
	public synchronized TestFsmImpl pingTest(final String testId, final long timeout) {
		if (isFailed()) {
			return this;
		}
		if (SESSION_STARTED == currentState || EXECUTING_TESTS == currentState) {
			if (!isNullOrEmpty(testId)) {
				if (null == executedTestIds.getIfPresent(testId)) {
					testTimeouter.schedule(testId, timeout);
				}
				return this;
			}
		}
		return fail("Failed when pinging test: " + testId + " " + toString());
	}

	/**
	 * Returns with {@code true} if the current finite state machine is in {@link FsmState#FAILED failed} state.
	 * Otherwise returns with {@code false}.
	 *
	 * @return {@code true} if the state machine failed.
	 */
	/* default */boolean isFailed() {
		return currentState == FAILED;
	}

	/* default */TestFsmImpl fail(final String comment) {
		if (FAILED != currentState) {
			if (null != comment) {
				LOGGER.info("Test session failed. " + comment);
			}
			bus.post(new SessionFailedEvent(currentSessionId, comment));
			return dispose(FAILED);
		}
		return this;
	}

	/** Created a new cache loader instance which simply returns with the argument as the loaded value. */
	private CacheLoader<String, String> getTestIdCacheLoader() {
		return new CacheLoader<String, String>() {
			@Override
			public String load(final String testId) throws Exception {
				return testId;
			}
		};
	}

	private TestFsmImpl dispose(final FsmState state) {
		testTimeouter.purge();
		testTimeouter.cancel();
		setupTimeouter.purge();
		setupTimeouter.cancel();
		visitedTestIds.invalidateAll();
		currentState = state;
		return this;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("FSM [Session ID: ");
		sb.append(currentSessionId);
		sb.append(" Current state: ");
		sb.append(currentState);
		sb.append(" Executed test IDs: ");
		sb.append(Iterables.toString(executedTestIds.asMap().keySet()));
		sb.append("]");
		return sb.toString();
	}

	/**
	 * Re-schedulable timer implementation to put the FSM into {@link FsmState#FAILED failed} state due to timeout
	 * event.
	 */
	private final class TestTimeouter extends Timer {

		private final LoadingCache<TestIdTimeoutPair, TimerTask> taskCache = newBuilder().removalListener(
				notification -> ((TimerTask) notification.getValue()).cancel()).build(
						new CacheLoader<TestIdTimeoutPair, TimerTask>() {
							@Override
							public TimerTask load(final TestIdTimeoutPair pair) throws Exception {
								return new TimerTask() {
									@Override
									public void run() {
										LOGGER.error("Test session was time outed. [Session ID: '" + currentSessionId
												+ "', Test ID: '" + pair.testId + "']");
										fail("Test '" + pair.testId + "' for session '" + currentSessionId
												+ "' was time outed after '" + pair.timeout + "' milliseconds.");
									}
								};
							}
						});

		private void schedule(final String testId, final long timeout) {
			cancel(testId);
			schedule(taskCache.getUnchecked(new TestIdTimeoutPair(testId, timeout)), timeout);
		}

		private void cancel(final String testId) {
			taskCache.invalidate(new TestIdTimeoutPair(testId, 0L));
		}
	}

	/**
	 * Class for put the FSM into failed state due to test session timeout.
	 */
	private final class SessionTimeouter extends Timer {

		private TimerTask task;

		private TimerTask newTask() {
			return new TimerTask() {
				@Override
				public void run() {
					LOGGER.error("Test session was time outed. [Session ID: '" + currentSessionId + "']");
					fail("Test session '" + currentSessionId + "' was time outed.");
				}
			};
		}

		private void scheduleTimeouter(final long timeout) {
			cancelTimeouter();
			task = newTask();
			schedule(task, timeout);
		}

		private void cancelTimeouter() {
			if (null != task) {
				task.cancel();
			}
		}

	}

	/**
	 * A pair of test ID and the associated test timeout given in milliseconds.
	 * <p>
	 * The overridden {@link #equals(Object) equals} and {@link #hashCode() hashCode} methods consider only the test ID
	 * but ignores the timeout property.
	 */
	private static final class TestIdTimeoutPair {
		private final String testId;
		private final long timeout;

		private TestIdTimeoutPair(final String testId, final long timeout) {
			super();
			this.testId = testId;
			this.timeout = timeout;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((testId == null) ? 0 : testId.hashCode());
			return result;
		}

		@Override
		public boolean equals(final Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final TestIdTimeoutPair other = (TestIdTimeoutPair) obj;
			if (testId == null) {
				if (other.testId != null)
					return false;
			} else if (!testId.equals(other.testId))
				return false;
			return true;
		}
	}

}
