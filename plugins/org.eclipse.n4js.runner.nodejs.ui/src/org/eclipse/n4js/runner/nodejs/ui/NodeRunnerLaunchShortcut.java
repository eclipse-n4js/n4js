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
package org.eclipse.n4js.runner.nodejs.ui;

import org.eclipse.debug.ui.ILaunchShortcut;

import org.eclipse.n4js.runner.nodejs.NodeRunner;
import org.eclipse.n4js.runner.ui.AbstractRunnerLaunchShortcut;

/**
 * Node runner {@link ILaunchShortcut}, used to create node runner launches from selections in the UI
 */
public class NodeRunnerLaunchShortcut extends AbstractRunnerLaunchShortcut {

	@Override
	protected String getRunnerId() {
		return NodeRunner.ID;
	}

	/**
	 * LaunchConfigurationTyp id/name for node.js, needs to match one used in plugin xml
	 */
	private final static String LAUNCHCONFIGURATIONTYPE_NODEJS_ID = "org.eclipse.n4js.runner.nodejs.ui.launchConfigurationType";

	@Override
	protected String getLaunchConfigTypeID() {
		return LAUNCHCONFIGURATIONTYPE_NODEJS_ID;
	}
}
