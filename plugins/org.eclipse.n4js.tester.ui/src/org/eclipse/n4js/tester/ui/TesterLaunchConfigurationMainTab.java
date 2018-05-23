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

import org.eclipse.core.resources.IResource;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.runner.RunConfiguration;
import org.eclipse.n4js.runner.ui.AbstractLaunchConfigurationMainTab;

/**
 * Main tab for tester launch configurations.
 */
public class TesterLaunchConfigurationMainTab extends AbstractLaunchConfigurationMainTab {

	@Override
	protected String getResourceLabel() {
		return "Resource to Test (Project, Folder, File)";
	}

	@Override
	protected String getResourceRunConfigKey() {
		return RunConfiguration.USER_SELECTION; // FIXME
	}

	@Override
	protected int getAcceptedResourceTypes() {
		return IResource.PROJECT | IResource.FOLDER | IResource.FILE;
	}

	@Override
	protected boolean checkSubclassSpecificConstraints(ILaunchConfiguration launchConfig, IResource resource,
			URI resourceUri) {
		// FIXME check if it can be tested
		return true;
	}
}
