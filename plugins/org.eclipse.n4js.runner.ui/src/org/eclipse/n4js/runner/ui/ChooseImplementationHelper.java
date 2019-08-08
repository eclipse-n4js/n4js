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
package org.eclipse.n4js.runner.ui;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.window.Window;
import org.eclipse.n4js.compare.ApiImplMapping;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;
import org.eclipse.n4js.runner.RunnerHelper;
import org.eclipse.n4js.runner.RunnerHelper.ApiUsage;
import org.eclipse.swt.widgets.Display;

import com.google.inject.Inject;

/**
 *
 */
public class ChooseImplementationHelper {
	/** Special return value to denote the user canceled the operation. */
	public static final String CANCEL = new String();

	@Inject
	private RunnerHelper runnerHelper;

	/***
	 * Choose an implementation if needed
	 *
	 * @param runnerId
	 *            the runner ID, e.g. NODEJS
	 * @param moduleToRun
	 *            the emf-full URI to the module to run
	 * @return the selected implementation Id
	 */
	public String chooseImplementationIfRequired(String runnerId, URI moduleToRun) {
		// first see if we need to supply an implementationId
		// (we need one if there are API projects among the dependencies of the moduleToRun AND there exist 2 or more
		// implementations for them)
		final ApiUsage apiUsage = runnerHelper.getProjectExtendedDeps(runnerId, moduleToRun);
		final ApiImplMapping apiImplMapping = apiUsage.apiImplMapping;

		final List<N4JSProjectName> availableImplIds = apiImplMapping.getAllImplIds();
		if (apiImplMapping.isEmpty())
			return null; // no API projects among the dependencies -> no need to bother the user
		if (availableImplIds.isEmpty())
			return null; // no implementations available -> error will be shown somewhere else
		if (availableImplIds.size() == 1)
			return availableImplIds.get(0).getRawName(); // exactly 1 implementation -> use that, no need to bother the
															// user
		// make user choose:
		// We have to open the dialog on the UI-thread
		// See:
		// http://stackoverflow.com/questions/354796/whats-the-best-way-to-get-a-return-value-out-of-an-asyncexec-in-eclipse
		final AtomicReference<String> result = new AtomicReference<>();
		final ChooseImplementationDialog dlg = new ChooseImplementationDialog(null, apiImplMapping);
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				if (dlg.open() == Window.OK) {
					result.set((String) dlg.getResult()[0]);
				} else {
					result.set(CANCEL);
				}
			}
		});

		return result.get();
	}
}
