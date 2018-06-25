/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.tester.events;

import java.util.function.Consumer;

import org.eclipse.n4js.utils.ThrowingConsumer;

/**
 * {@link TestEndedEvent} consumer that dispatches to the specialized consumers based on the event result.
 */
public final class TestEventDispatcher<E extends Exception> implements ThrowingConsumer<TestEvent, E> {

	private final Consumer<SessionPingedEvent> sessionPinged;
	private final Consumer<SessionFailedEvent> sessionFailed;
	private final Consumer<SessionFinishedEvent> sessionFinished;
	private final Consumer<SessionEndedEvent> sesionEnded;
	private final Consumer<SessionStartedEvent> sessionStarted;

	private final Consumer<TestPingedEvent> testPinged;
	private final ThrowingConsumer<TestEndedEvent, E> testEnded;
	private final Consumer<TestStartedEvent> testStarted;

	private final ThrowingConsumer<TestEvent, E> unhandled;

	/** Creates instance with concrete consumers used in event dispatching. */
	public TestEventDispatcher(Consumer<SessionStartedEvent> sessionStarted,
			Consumer<SessionFinishedEvent> sessionFinished,
			Consumer<SessionFailedEvent> sessionFailed,
			Consumer<SessionPingedEvent> sessionPinged,
			Consumer<SessionEndedEvent> sesionEnded,
			Consumer<TestPingedEvent> testPinged,
			Consumer<TestStartedEvent> testStarted,
			ThrowingConsumer<TestEndedEvent, E> testEnded,
			ThrowingConsumer<TestEvent, E> unhandled) {

		this.sessionPinged = sessionPinged;
		this.sessionFailed = sessionFailed;
		this.sessionFinished = sessionFinished;
		this.sesionEnded = sesionEnded;
		this.sessionStarted = sessionStarted;

		this.testPinged = testPinged;
		this.testStarted = testStarted;
		this.testEnded = testEnded;

		this.unhandled = unhandled;
	}

	@Override
	public void accept(TestEvent event) throws E {
		if (event instanceof TestStartedEvent) {
			testStarted.accept((TestStartedEvent) event);
			return;
		}
		if (event instanceof TestEndedEvent) {
			testEnded.accept((TestEndedEvent) event);
			return;
		}
		if (event instanceof TestPingedEvent) {
			testPinged.accept((TestPingedEvent) event);
			return;
		}

		if (event instanceof SessionStartedEvent) {
			sessionStarted.accept((SessionStartedEvent) event);
			return;
		}

		if (event instanceof SessionEndedEvent) {
			sesionEnded.accept((SessionEndedEvent) event);
			return;
		}

		if (event instanceof SessionFinishedEvent) {
			sessionFinished.accept((SessionFinishedEvent) event);
			return;
		}

		if (event instanceof SessionFailedEvent) {
			sessionFailed.accept((SessionFailedEvent) event);
			return;
		}

		if (event instanceof SessionPingedEvent) {
			sessionPinged.accept((SessionPingedEvent) event);
			return;
		}

		unhandled.accept(event);
	}

}
