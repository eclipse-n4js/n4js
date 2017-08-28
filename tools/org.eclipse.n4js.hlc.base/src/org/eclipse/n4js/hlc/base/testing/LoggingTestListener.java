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

import static org.eclipse.n4js.hlc.base.ErrorExitCode.EXITCODE_TESTER_STOPPED_WITH_ERROR;

import org.eclipse.n4js.generator.headless.logging.IHeadlessLogger;
import org.eclipse.n4js.hlc.base.ExitCodeException;
import org.eclipse.n4js.tester.TesterEventBus;
import org.eclipse.n4js.tester.domain.TestTree;
import org.eclipse.n4js.tester.events.SessionEndedEvent;
import org.eclipse.n4js.tester.events.SessionFailedEvent;
import org.eclipse.n4js.tester.events.SessionFinishedEvent;
import org.eclipse.n4js.tester.events.SessionPingedEvent;
import org.eclipse.n4js.tester.events.SessionStartedEvent;
import org.eclipse.n4js.tester.events.TestEndedEvent;
import org.eclipse.n4js.tester.events.TestEndedEventDispatcher;
import org.eclipse.n4js.tester.events.TestEvent;
import org.eclipse.n4js.tester.events.TestEventDispatcher;
import org.eclipse.n4js.tester.events.TestPingedEvent;
import org.eclipse.n4js.tester.events.TestStartedEvent;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;

/**
 * Listens for the {@link TestEvent}s on the {@link TesterEventBus}. Individual events are logged with the
 * {@link IHeadlessLogger}. Additionally {@link TestTree} is updated with the results of the capture events.
 *
 * In general case there can be multiple test sessions running in parallel, in which case the {@link TesterEventBus}
 * will contain {@link TestEvent}s for <b>all</b> of them. This listener is using {@link TestTree#getSessionId() session
 * ID} to filter events to handle.
 *
 */
public class LoggingTestListener {

	private final TestEventDispatcher<ExitCodeException> dispatchTestEvent = new TestEventDispatcher<>(
			this::handleSessionStarted,
			this::handleSessionFinished,
			this::handleSessionFailed,
			this::handleSessionPinged,
			this::handleSessionEnded,
			this::handleTestPinged,
			this::handleTestStarted,
			this::handleTestEndedEvent,
			this::handleUnmatchedTestEvent);
	private final TestEndedEventDispatcher<ExitCodeException> dispatchTestEndedEvent = new TestEndedEventDispatcher<>(
			this::handleTestPassed,
			this::handleTestSkipped,
			this::handleTestFailed,
			this::handleUnmatchedStatus);

	private TestTree testTree = null;

	/** Convenience flag, allows caller to easily examine if tests had no errors or failures. */
	private boolean testsOK = true; // assume positive
	/** Convenience flag, allows caller to easily examine if test session had no errors. */
	private boolean sessionOK = true;// assume positive
	/** Convenience flag, allows caller to easily examine if test session finished. */
	private boolean sessionFinished = false;// only if

	private final TesterEventBus testerEventBus;

	private final IHeadlessLogger logger;

	/** Creates instance and automatically registers it to the provide {@link TesterEventBus} */
	public LoggingTestListener(TesterEventBus testerEventBus, IHeadlessLogger logger, TestTree tree) {
		this.testerEventBus = testerEventBus;
		this.logger = logger;
		startListening(tree);
	}

	/**
	 * Whenever a new test event from 'mangelhaft' on the Javascript side is being received by the HTTP server, this
	 * method will be invoked.
	 * <p>
	 *
	 * @throws ExitCodeException
	 *             in case of unhandled test event
	 */
	@Subscribe
	@AllowConcurrentEvents
	public void onTestEvent(TestEvent ev) throws ExitCodeException {
		if (this.testTree.getSessionId().toString().equals(ev.getSessionId()))
			handleTestEvent(ev);
	}

	/**
	 * Convenience method indicating if session indicating if all events for the {@link TestTree#getSessionId()} have
	 * been received.
	 */
	public boolean finished() {
		return sessionFinished;
	}

	/** Convenience method indicating there were no tests with errors or failures and no session errors. */
	public boolean isOK() {
		return sessionFinished && sessionOK && testsOK;
	}

	private void handleTestEndedEvent(TestEndedEvent tee) throws ExitCodeException {
		testTree.getTestCase(tee.getTestId()).setResult(tee.getResult());
		dispatchTestEndedEvent.accept(tee);
	}

	private void handleTestEvent(TestEvent event) throws ExitCodeException {
		dispatchTestEvent.accept(event);
	}

	private void handleSessionPinged(SessionPingedEvent event) {
		logger.debug(event.toString());
	}

	private void handleSessionFailed(SessionFailedEvent event) {
		logger.error(event.toString());
		sessionOK = false;
	}

	private void handleSessionFinished(SessionFinishedEvent event) {
		logger.info(event.toString());
		sessionFinished = true;
		stopListening();
	}

	private void handleSessionEnded(SessionEndedEvent event) {
		logger.info(event.toString());
		sessionOK = true;
	}

	private void handleSessionStarted(SessionStartedEvent event) {
		logger.info(event.toString());
	}

	private void handleUnmatchedTestEvent(TestEvent te) throws ExitCodeException {
		throw new ExitCodeException(EXITCODE_TESTER_STOPPED_WITH_ERROR,
				"unhandled test event " + te);
	}

	private void handleTestPinged(TestPingedEvent event) {
		logger.debug(event.toString());
	}

	private void handleTestStarted(TestStartedEvent event) {
		logger.info(event.toString());
	}

	private void handleTestPassed(TestEndedEvent tee) {
		logger.info(tee.toString() + " => " + tee.getResult().toString());
	}

	private void handleTestSkipped(TestEndedEvent tee) {
		logger.warn(tee.toString() + " => " + tee.getResult().getTestStatus());
	}

	private void handleTestFailed(TestEndedEvent tee) {
		testsOK = false;
		logger.error(tee.toString() + " => " + tee.getResult().getTestStatus());
	}

	private void handleUnmatchedStatus(TestEndedEvent tee) throws ExitCodeException {
		throw new ExitCodeException(EXITCODE_TESTER_STOPPED_WITH_ERROR,
				"unhandled test ended status " + tee + " => " + tee.getResult().getTestStatus());
	}

	/** Unregisters this listener to the {@link TesterEventBus}, and stops listening to the {@link TestEvent}s. */
	private void stopListening() {
		testerEventBus.unregister(this);
		this.testTree = null;
	}

	/**
	 * Registers this listener to the {@link TesterEventBus}, and starts listening to the {@link TestEvent}s.
	 */
	private void startListening(TestTree tree) {
		testerEventBus.register(this);
		this.testTree = tree;
	}

}
