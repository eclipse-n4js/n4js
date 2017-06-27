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
package org.eclipse.n4js.xpect.ui;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IDebugEventSetListener;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.ILaunchConfigurationDelegate;
import org.junit.runner.Result;
import org.junit.runner.notification.RunListener;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.InitializationError;
import org.xpect.runner.IXpectURIProvider;
import org.xpect.runner.XpectRunner;

import org.eclipse.n4js.xpect.ui.results.N4IDEXpectRunListener;
import org.eclipse.n4js.xpect.ui.runner.N4IDEXpectTestClass;
import org.eclipse.n4js.xpect.ui.runner.N4IDEXpectTestFilesCollector.N4IDEXpectTestURIProvider;

/**
 * Delegate that triggers xpect execution
 */
public class XpectConfigurationDelegate implements ILaunchConfigurationDelegate {

	class DebugEventSetListener implements IDebugEventSetListener {

		@Override
		public void handleDebugEvents(DebugEvent[] events) {
			for (DebugEvent e : events) {
				switch (e.getKind()) {
				case DebugEvent.TERMINATE:
					DebugPlugin.getDefault().removeDebugEventListener(this);
					terminateProcess();
					break;
				default:
					break;
				}
			}
		}
	}

	/**
	 * process of the running engine
	 */
	protected Process process;

	@Override
	public void launch(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor monitor)
			throws CoreException {

		XpectRunConfiguration runConfig = XpectRunConfiguration.fromLaunchConfiguration(configuration);

		DebugPlugin.getDefault().addDebugEventListener(new DebugEventSetListener());

		try {
			execute(launch, runConfig);
		} catch (RuntimeException e) {
			N4IDEXpectUIPlugin.logError("Error executing file", e);
		}

	}

	/**
	 * Runs provided File in Engine. Returns output of execution.
	 */
	public void execute(ILaunch launch, XpectRunConfiguration runConfiguration) throws RuntimeException {

		Job job = new Job(launch.getLaunchConfiguration().getName()) {

			@Override
			protected IStatus run(IProgressMonitor monitor) {
				XpectRunner xr;
				try {
					xr = new XpectRunner(N4IDEXpectTestClass.class);
				} catch (InitializationError e) {
					N4IDEXpectUIPlugin.logError("cannot initialize xpect runner", e);
					return Status.CANCEL_STATUS;
				}

				// TODO support multiple selection
				/*
				 * if Project provided, or package files should be discovered there. Also multiple selected files
				 */
				String testFileLocation = runConfiguration.getXtFileToRun();

				IXpectURIProvider uriprov = xr.getUriProvider();
				if (uriprov instanceof N4IDEXpectTestURIProvider) {
					((N4IDEXpectTestURIProvider) uriprov).addTestFileLocation(testFileLocation);
				}

				Result result = new Result();
				RunNotifier notifier = new RunNotifier();
				RunListener listener = result.createListener();
				N4IDEXpectRunListener n4Listener = new N4IDEXpectRunListener();

				notifier.addFirstListener(listener);
				notifier.addListener(n4Listener);

				try {
					notifier.fireTestRunStarted(xr.getDescription());
					xr.run(notifier);
					notifier.fireTestRunFinished(result);
				} finally {
					notifier.removeListener(n4Listener);
					notifier.removeListener(listener);
				}
				// Do something with test run result?
				// return result;

				return Status.OK_STATUS;
			}

		};
		job.setUser(true);
		job.schedule();
	}

	/**
	 * Destroy processes associated with this engine.
	 */
	public void terminateProcess() {
		if (process != null) {
			process.destroy();
			process = null;
		}
	}

}
