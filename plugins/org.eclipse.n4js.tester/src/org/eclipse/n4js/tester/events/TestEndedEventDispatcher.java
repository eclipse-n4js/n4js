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

import org.eclipse.n4js.tester.domain.TestResult;
import org.eclipse.n4js.tester.domain.TestStatus;
import org.eclipse.n4js.utils.ThrowingConsumer;

/**
 * {@link TestEndedEvent} consumer that dispatches to the specialized consumers based on the event result.
 */
public final class TestEndedEventDispatcher<E extends Exception> implements ThrowingConsumer<TestEndedEvent, E> {
	private final Consumer<TestEndedEvent> passed;

	private final Consumer<TestEndedEvent> skipped;
	private final Consumer<TestEndedEvent> notimplemented;
	private final Consumer<TestEndedEvent> precondition;
	private final Consumer<TestEndedEvent> ignore;
	private final Consumer<TestEndedEvent> fixmme;

	private final Consumer<TestEndedEvent> failed;
	private final Consumer<TestEndedEvent> error;

	private final ThrowingConsumer<TestEndedEvent, E> unhandled;

	/**
	 * Creates instance that twill use the same one handler for all {@code skipped} events. Also uses one {@code failed}
	 * and {@code error}.
	 *
	 * @param passedConsumer
	 *            consumer of test events with {@code passed} status
	 * @param skippedConsumer
	 *            consumer of test events with {@code skipped} like status
	 * @param failedConsumer
	 *            * consumer of test events with {@code failed} like status
	 * @param unhandled
	 *            * consumer for unrecognized events
	 */
	public TestEndedEventDispatcher(Consumer<TestEndedEvent> passedConsumer, Consumer<TestEndedEvent> skippedConsumer,
			Consumer<TestEndedEvent> failedConsumer, ThrowingConsumer<TestEndedEvent, E> unhandled) {
		this.passed = passedConsumer;

		this.skipped = skippedConsumer;
		this.notimplemented = skippedConsumer;
		this.precondition = skippedConsumer;
		this.ignore = skippedConsumer;
		this.fixmme = skippedConsumer;

		this.failed = failedConsumer;
		this.error = failedConsumer;

		this.unhandled = unhandled;
	}

	@Override
	public void accept(TestEndedEvent tee) throws E {
		TestResult result = tee.getResult();
		TestStatus status = result.getTestStatus();
		switch (status) {
		case PASSED:
			passed.accept(tee);
			return;

		case SKIPPED:
			skipped.accept(tee);
			return;
		case SKIPPED_NOT_IMPLEMENTED:
			notimplemented.accept(tee);
			return;
		case SKIPPED_PRECONDITION:
			precondition.accept(tee);
			return;
		case SKIPPED_IGNORE:
			ignore.accept(tee);
			return;
		case SKIPPED_FIXME:
			fixmme.accept(tee);
			return;

		case FAILED:
			failed.accept(tee);
			return;
		case ERROR:
			error.accept(tee);
			return;

		default:
			unhandled.accept(tee);
		}
	}

}
