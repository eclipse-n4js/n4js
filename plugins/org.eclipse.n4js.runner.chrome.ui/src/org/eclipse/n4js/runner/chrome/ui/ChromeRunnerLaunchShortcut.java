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
package org.eclipse.n4js.runner.chrome.ui;

import org.eclipse.debug.ui.ILaunchShortcut;

import org.eclipse.n4js.runner.chrome.ChromeRunner;
import org.eclipse.n4js.runner.ui.AbstractRunnerLaunchShortcut;

/**
 * Chrome runner {@link ILaunchShortcut}, used to create node runner launches from selections in the UI
 */
public class ChromeRunnerLaunchShortcut extends AbstractRunnerLaunchShortcut {

	@Override
	protected String getRunnerId() {
		return ChromeRunner.ID;
	}

	/**
	 * LaunchConfigurationTyp id/name for node.js, needs to match one used in plugin xml
	 */
	private final static String LAUNCHCONFIGURATIONTYPE_CHROME_ID = "org.eclipse.n4js.runner.chrome.ui.launchConfigurationType";

	@Override
	protected String getLaunchConfigTypeID() {
		return LAUNCHCONFIGURATIONTYPE_CHROME_ID;
	}
}
