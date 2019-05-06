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
package org.eclipse.n4js.tester.tests.helper;

import static com.google.common.base.Joiner.on;
import static com.google.common.base.Suppliers.memoize;
import static com.google.common.collect.Iterables.size;
import static com.google.common.collect.Multimaps.synchronizedMultimap;
import static java.lang.String.valueOf;
import static java.util.Arrays.asList;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.apache.log4j.Logger.getLogger;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.CountDownLatch;

import org.apache.log4j.Logger;
import org.eclipse.n4js.tester.TesterEventBus;
import org.eclipse.n4js.tester.events.SessionFailedEvent;
import org.eclipse.n4js.tester.events.SessionFinishedEvent;
import org.eclipse.n4js.tester.events.TestEvent;
import org.junit.Assert;
import org.junit.ComparisonFailure;

import com.google.common.base.Supplier;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;

/**
 * Queue implementation for percepting {@link TestEvent test event}s from the {@link TesterEventBus event bus} and used
 * for making assertion of test session test cases by comparing the percepted test events with the expected ones.
 */
public class TestEventQueue {

	private static final Logger LOGGER = getLogger(TestEventQueue.class);

	/**
	 * The event bus to receive test events from the tester core framework.
	 */
	private final TesterEventBus bus;

	/**
	 * Mapping between test session IDs and test events represented as their {@link TestEvent#toString()}
	 * representation. Test event values are stored in an ordered collection.
	 */
	private final Multimap<String, String> events;

	/**
	 * Latch for waiting to receive all expected test events from the event bus.
	 */
	private CountDownLatch latch;

	/** Supplier to unregister instance only once. */
	private final Supplier<Void> unregisterSupplier;

	/**
	 * Constructor for creating a new event queue instance.
	 *
	 * @param bus
	 *            the event bus.
	 */
	@Inject
	public TestEventQueue(final TesterEventBus bus) {
		LOGGER.info("\n\n");
		this.bus = bus;
		unregisterSupplier = memoize(() -> {
			bus.unregister(TestEventQueue.this);
			return null;
		});
		events = synchronizedMultimap(ArrayListMultimap.create());
		this.bus.register(this);
	}

	/**
	 * Initializes the test event queue instance with the expected event count argument.
	 *
	 * @param expectedEventCout
	 *            the number of expected argument to wait before the assertion.
	 */
	public void init(final int expectedEventCout) {
		latch = new CountDownLatch(expectedEventCout);
	}

	/**
	 * Percepts any {@link TestEvent test event} and registers it to the underlying queue.
	 *
	 * @param event
	 *            the percepted test event to register.
	 */
	@Subscribe
	@AllowConcurrentEvents
	public void receivedTestEvent(final TestEvent event) {
		events.put(event.getSessionId(), valueOf(event));
		if (null != latch) {
			latch.countDown();
		}
	}

	/**
	 * Clears the underlying queue and unregisters the current instance from the event bus.
	 */
	public synchronized void dispose() {
		events.clear();
		unregisterSupplier.get();
		latch = null;
	}

	/**
	 * Sugar for {@link #assertEquals(String, Iterable)}
	 *
	 * @param sessionId
	 *            the unique ID of the session that has to be checked.
	 * @param expectedEvents
	 *            the expected events to assert against the actual ones.
	 */
	public void assertEquals(final String sessionId, final String... expectedEvents) {
		assertEquals(sessionId, asList(expectedEvents));
	}

	/**
	 * Asserts the actual events with the expected ones.
	 *
	 * @param sessionId
	 *            the unique ID of the session that has to be checked.
	 * @param expectedEvents
	 *            the expected events to assert against the actual ones.
	 */
	public void assertEquals(final String sessionId, final Iterable<String> expectedEvents) {
		assertNotNull("Queue was not initialized. One should call #init(int) before invoking #assertEquals()", latch);
		try {
			latch.await(20L, SECONDS);
			unregisterSupplier.get();
		} catch (final InterruptedException e) {
			throw new AssertionError("Time out while waiting to receive all expected test events.", e);
		}
		final Collection<String> eventsForSession = events.get(sessionId);
		if (size(expectedEvents) != eventsForSession.size()) {
			throw new ComparisonFailure("Expected:", toString(expectedEvents), toString(eventsForSession));
		}
		final Iterator<String> actItr = eventsForSession.iterator();
		final Iterator<String> expItr = expectedEvents.iterator();

		while (expItr.hasNext()) {
			if (!actItr.hasNext()) {
				throw new ComparisonFailure("Expected:", toString(expectedEvents), toString(eventsForSession));
			}
			final String expected = expItr.next();
			final String actual = actItr.next();
			if (null == expected || null == actual) {
				if (expected != actual) {
					throw new ComparisonFailure("Expected:", toString(expectedEvents), toString(eventsForSession));
				}
			} else {
				if (!expected.equals(actual)) {
					throw new ComparisonFailure("Expected:", toString(expectedEvents), toString(eventsForSession));
				}
			}

			Assert.assertEquals(expected, actual);
		}
	}

	/**
	 * Sugar for {@link #assertEquals(String, Iterable)} but assumes that the session finished successfully hence a
	 * {@link SessionFinishedEvent} will be expected as the final element of the event queue.
	 *
	 * @param sessionId
	 *            the ID of the test session to check.
	 * @param expectedEvents
	 *            an iterable of test events representing the ordered queue of events.
	 */
	public void assertSucceeded(final String sessionId, final Iterable<String> expectedEvents) {
		assertNotNull("Queue was not initialized. One should call #init(int) before invoking #assertEquals()", latch);
		try {
			latch.await(20L, SECONDS);
			unregisterSupplier.get();
		} catch (final InterruptedException e) {
			throw new AssertionError("Time outed while waiting to receive all expected test events.", e);
		}

		final String expectedMessage = new SessionFinishedEvent(sessionId).toString();
		if (events.isEmpty()) {
			fail("Expected to have '" + expectedMessage
					+ "' as the session finished event among events but event queue was empty.");
		}

		final Collection<String> eventsForSession = events.get(sessionId);
		if (!eventsForSession.contains(expectedMessage)) {
			fail("Expected to have '" + expectedMessage
					+ "' as the session finished event among events. Was:\n"
					+ toString(eventsForSession));
		}
	}

	/**
	 * Asserts that the underlying queue represents a session failure as its tail element.
	 *
	 * @param sessionId
	 *            the session that has to be failed.
	 */
	public void assertFailed(final String sessionId) {
		assertNotNull("Queue was not initialized. One should call #init(int) before invoking #assertEquals()", latch);
		try {
			latch.await(20L, SECONDS);
			unregisterSupplier.get();
		} catch (final InterruptedException e) {
			throw new AssertionError("Time outed while waiting to receive all expected test events.", e);
		}

		final String expectedMessage = new SessionFailedEvent(sessionId).toString();
		if (events.isEmpty()) {
			fail("Expected to have '" + expectedMessage
					+ "' as the session failed event among events but event queue was empty.");
		}

		final Collection<String> eventsForSession = events.get(sessionId);
		if (!eventsForSession.contains(expectedMessage)) {
			fail("Expected to have '" + expectedMessage
					+ "' as the session failed event among events. Was:\n"
					+ toString(eventsForSession));
		}
	}

	private String toString(final Iterable<String> i) {
		return on("\n").useForNull("null").join(i);
	}

}
