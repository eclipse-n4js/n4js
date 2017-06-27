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
package org.eclipse.n4js.xpect.ui.results;

import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.junit.internal.AssumptionViolatedException;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

import org.eclipse.n4js.xpect.ui.N4IDEXpectUIPlugin;

/**
 * Listens to changes in test execution. On change notification will perform given handling (e.g write data to the
 * console, refresh test view, etc...)
 */
@SuppressWarnings("restriction")
public class N4IDEXpectRunListener extends RunListener {

	/**
	 * Create instance of the run listener
	 */
	public N4IDEXpectRunListener() {
	}

	/**
	 * Called before any tests have been run.
	 *
	 * @param description
	 *            describes the tests to be run
	 */
	@Override
	public void testRunStarted(Description description) throws Exception {
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {

				IWorkbenchWindow[] windows = N4IDEXpectUIPlugin.getDefault().getWorkbench().getWorkbenchWindows();
				try {
					N4IDEXpectView view = (N4IDEXpectView) windows[0].getActivePage().showView(
							N4IDEXpectView.ID);
					view.notifySessionStarted(description);
				} catch (PartInitException e) {
					N4IDEXpectUIPlugin.logError("cannot refresh test view window", e);
				}
			}
		});
	}

	/**
	 * Called when all tests have finished
	 *
	 * @param result
	 *            the summary of the test run, including all the tests that failed
	 */
	@Override
	public void testRunFinished(Result result) throws Exception {
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {

				IWorkbenchWindow[] windows = N4IDEXpectUIPlugin.getDefault().getWorkbench().getWorkbenchWindows();
				try {
					N4IDEXpectView view = (N4IDEXpectView) windows[0].getActivePage().showView(
							N4IDEXpectView.ID);
					view.notifySessionFinished();
				} catch (PartInitException e) {
					N4IDEXpectUIPlugin.logError("cannot refresh test view window", e);
				}
			}
		});
	}

	/**
	 * Called when an atomic test is about to be started.
	 *
	 * @param description
	 *            the description of the test that is about to be run (generally a class and method name)
	 */
	@Override
	public void testStarted(Description description) throws Exception {
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {

				IWorkbenchWindow[] windows = N4IDEXpectUIPlugin.getDefault().getWorkbench().getWorkbenchWindows();
				try {
					N4IDEXpectView view = (N4IDEXpectView) windows[0].getActivePage().showView(
							N4IDEXpectView.ID);
					view.notifyStartedExecutionOf(description);
				} catch (PartInitException e) {
					N4IDEXpectUIPlugin.logError("cannot refresh test view window", e);
				}
			}
		});
	}

	/**
	 * Called when an atomic test has finished, whether the test succeeds or fails.
	 *
	 * @param description
	 *            the description of the test that just ran
	 */
	@Override
	public void testFinished(Description description) throws Exception {
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {

				IWorkbenchWindow[] windows = N4IDEXpectUIPlugin.getDefault().getWorkbench().getWorkbenchWindows();
				try {
					N4IDEXpectView view = (N4IDEXpectView) windows[0].getActivePage().showView(
							N4IDEXpectView.ID);
					view.notifyFinishedExecutionOf(description);
				} catch (PartInitException e) {
					N4IDEXpectUIPlugin.logError("cannot refresh test view window", e);
				}
			}
		});
	}

	/**
	 * Called when an atomic test fails.
	 *
	 * @param failure
	 *            describes the test that failed and the exception that was thrown
	 */
	@Override
	public void testFailure(Failure failure) throws Exception {
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {

				IWorkbenchWindow[] windows = N4IDEXpectUIPlugin.getDefault().getWorkbench().getWorkbenchWindows();
				try {
					N4IDEXpectView view = (N4IDEXpectView) windows[0].getActivePage().showView(
							N4IDEXpectView.ID);
					view.notifyFailedExecutionOf(failure);
				} catch (PartInitException e) {
					N4IDEXpectUIPlugin.logError("cannot refresh test view window", e);
				}
			}
		});
	}

	/**
	 * Called when an atomic test flags that it assumes a condition that is false
	 *
	 * describes the test that failed and the {@link AssumptionViolatedException} that was thrown
	 */
	@Override
	public void testAssumptionFailure(Failure failure) {
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {

				IWorkbenchWindow[] windows = N4IDEXpectUIPlugin.getDefault().getWorkbench().getWorkbenchWindows();
				try {
					N4IDEXpectView view = (N4IDEXpectView) windows[0].getActivePage().showView(
							N4IDEXpectView.ID);
					view.notifyFailedExecutionOf(failure);
				} catch (PartInitException e) {
					N4IDEXpectUIPlugin.logError("cannot refresh test view window", e);
				}
			}
		});
	}

	/**
	 * Called when a test will not be run, generally because a test method is annotated with {@link org.junit.Ignore}.
	 *
	 * @param description
	 *            describes the test that will not be run
	 */
	@Override
	public void testIgnored(Description description) throws Exception {
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {

				IWorkbenchWindow[] windows = N4IDEXpectUIPlugin.getDefault().getWorkbench().getWorkbenchWindows();
				try {
					N4IDEXpectView view = (N4IDEXpectView) windows[0].getActivePage().showView(
							N4IDEXpectView.ID);
					view.notifyIgnoredExecutionOf(description);
				} catch (PartInitException e) {
					N4IDEXpectUIPlugin.logError("cannot refresh test view window", e);
				}
			}
		});
	}
}
