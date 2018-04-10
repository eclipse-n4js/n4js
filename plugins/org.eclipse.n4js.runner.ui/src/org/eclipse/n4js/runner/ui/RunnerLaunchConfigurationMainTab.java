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
package org.eclipse.n4js.runner.ui;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.runner.RunConfiguration;

import com.google.inject.Inject;

/**
 * Main tab for runner launch configurations.
 */
public class RunnerLaunchConfigurationMainTab extends AbstractLaunchConfigurationMainTab {

	@Inject
	private SupportingRunnerPropertyTester supportTester;

	@Override
	protected String getResourceLabel() {
		return "File to Run";
	}

	@Override
	protected String getResourceRunConfigKey() {
		return RunConfiguration.USER_SELECTION;
	}

	@Override
	protected int getAcceptedResourceTypes() {
		return IResource.FILE;
	}

	@Override
	protected boolean checkSubclassSpecificConstraints(ILaunchConfiguration launchConfig, IResource resource,
			URI resourceUri) {

		if (!checkFileToRunSupportedByRunner(launchConfig, resource, resourceUri))
			return false;

		return true;
	}

	private boolean checkFileToRunSupportedByRunner(ILaunchConfiguration launchConfig, IResource resource,
			@SuppressWarnings("unused") URI resourceUri) {

		// note: we know the resource is an IFile because FILE is the only type returned by #getAcceptedResourceTypes()
		final IFile fileToRun = (IFile) resource;

		final String runnerId = RunnerUiUtils.getRunnerId(launchConfig, false);
		if (runnerId == null) {
			setErrorMessage("cannot read runner ID from launch configuration");
			return false;
		}

		final Object[] args = new Object[] { runnerId };
		// FIXME IDE-1393 once RunnerFrontEnd#canRun() has property tester logic, call it here
		boolean isSupported = supportTester.test(
				fileToRun, SupportingRunnerPropertyTester.PROPERTY_IS_SUPPORTING_RUNNER, args, "");

		if (!isSupported) {
			setErrorMessage("runner cannot handle provided file to run");
			return false;
		}
		return true;
	}
}
