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
package org.eclipse.n4js.hlc.base.testing;

import org.eclipse.n4js.generator.headless.logging.IHeadlessLogger;
import org.eclipse.n4js.tester.TesterEventBus;
import org.eclipse.n4js.tester.domain.TestStatus;
import org.eclipse.n4js.tester.events.SessionEndedEvent;
import org.eclipse.n4js.tester.events.SessionFailedEvent;
import org.eclipse.n4js.tester.events.SessionFinishedEvent;
import org.eclipse.n4js.tester.events.SessionPingedEvent;
import org.eclipse.n4js.tester.events.SessionStartedEvent;
import org.eclipse.n4js.tester.events.TestEndedEvent;
import org.eclipse.n4js.tester.events.TestEvent;
import org.eclipse.n4js.tester.events.TestPingedEvent;
import org.eclipse.n4js.tester.events.TestStartedEvent;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;

/**
 *
 */
public class LoggingTestListener {
	private TestResults collectedResults = null;

	@Inject
	private TesterEventBus testerEventBus;

	@Inject
	private IHeadlessLogger logger;

	/** Registers this listener to the {@link TesterEventBus}, and starts listening to the {@link TestEvent}s. */
	public void startListening() {
		collectedResults = new TestResults();
		testerEventBus.register(this);
	}

	/** Unregisters this listener to the {@link TesterEventBus}, and stops listening to the {@link TestEvent}s. */
	public void stopListening() {
		collectedResults = null;
		testerEventBus.unregister(this);
	}

	public TestResults getCollcetedResults() {
		return this.collectedResults;
	}

	/**
	 * Whenever a new test event from 'mangelhaft' on the Javascript side is being received by the HTTP server, this
	 * method will be invoked.
	 * <p>
	 */
	@Subscribe
	@AllowConcurrentEvents
	public void onTestEvent(TestEvent ev) {
		handleTestEvent(ev);
	}

	/**
	 * Update UI according to given {@link TestEvent}.
	 */
	public void handleTestEvent(TestEvent event) {
		if (event instanceof TestStartedEvent) {
			logger.debug(event.toString());
			return;
		}
		if (event instanceof TestEndedEvent) {
			TestEndedEvent tee = (TestEndedEvent) event;
			TestStatus testStatus = tee.getResult().getTestStatus();
			switch (testStatus) {
			case PASSED:
				logger.info(tee.toString() + " => " + testStatus.toString());
				collectedResults.register(tee);
				return;

			case SKIPPED:
				handleSkipped(tee);
				return;
			case SKIPPED_NOT_IMPLEMENTED:
				handleSkipped(tee);
				return;
			case SKIPPED_PRECONDITION:
				handleSkipped(tee);
				return;
			case SKIPPED_IGNORE:
				handleSkipped(tee);
				return;
			case SKIPPED_FIXME:
				handleSkipped(tee);
				return;

			case FAILED:
				handleFailed(tee);
				return;
			case ERROR:
				handleFailed(tee);
				return;

			default:
				logger.error("unhandled status " + tee.toString() + " => " + testStatus.toString());
				break;
			}
			return;
		}
		if (event instanceof TestPingedEvent) {
			logger.debug(event.toString());
			return;
		}

		if (event instanceof SessionStartedEvent) {
			logger.info(event.toString());
			return;
		}

		if (event instanceof SessionEndedEvent) {
			logger.info(event.toString());
			return;
		}
		if (event instanceof SessionFinishedEvent) {
			logger.info(event.toString());
			return;
		}
		if (event instanceof SessionFailedEvent) {
			logger.info(event.toString());
			return;
		}
		if (event instanceof SessionPingedEvent) {
			logger.info(event.toString());
			return;
		}

		System.out.println("\u2620 " + " unhandled event " + event);
		return;
	}

	private void handleSkipped(TestEndedEvent tee) {
		collectedResults.registerSkipped(tee);
		logger.warn(tee.toString() + " => " + tee.getResult().getTestStatus());
	}

	private void handleFailed(TestEndedEvent tee) {
		collectedResults.registerFailed(tee);
		logger.error(tee.toString() + " => " + tee.getResult().getTestStatus());
	}

}
