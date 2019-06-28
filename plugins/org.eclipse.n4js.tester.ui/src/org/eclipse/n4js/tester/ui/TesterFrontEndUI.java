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
package org.eclipse.n4js.tester.ui;

import java.util.concurrent.ExecutionException;

import org.eclipse.n4js.runner.RunConfiguration;
import org.eclipse.n4js.runner.ui.RunnerFrontEndUI;
import org.eclipse.n4js.tester.TestConfiguration;
import org.eclipse.n4js.tester.TesterFrontEnd;
import org.eclipse.n4js.tester.domain.TestTree;
import org.eclipse.n4js.tester.ui.resultsview.TestResultsView;
import org.eclipse.swt.widgets.Display;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Similar to {@link TesterFrontEnd}, but adds a method for testing code within Eclipse.
 */
@Singleton
public class TesterFrontEndUI {

	@Inject
	private RunnerFrontEndUI runnerFrontEndUI;
	@Inject
	private TesterFrontEnd testerFrontEnd;

	/**
	 * Same as {@link RunnerFrontEndUI#runInUI(RunConfiguration)}, but for testers.
	 * <p>
	 * This method may be invoked from non-UI threads.
	 */
	public Process runInUI(TestConfiguration config) throws ExecutionException {

		// prepare UI
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				final TestTree testTree = config.getTestTree();
				final TestResultsView view = TestResultsView.getInstance(true);
				view.addTestTree(config, testTree, true);
			}
		});

		return testerFrontEnd.test(config, runnerFrontEndUI.createEclipseExecutor());
	}
}
