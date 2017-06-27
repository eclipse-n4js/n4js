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
package org.eclipse.n4js.tester.nodejs.ui;

import org.eclipse.debug.ui.ILaunchShortcut;

import org.eclipse.n4js.tester.nodejs.NodeTester;
import org.eclipse.n4js.tester.ui.AbstractTesterLaunchShortcut;

/**
 * Node runner {@link ILaunchShortcut}, used to create node runner launches from selections in the UI
 */
public class NodejsTesterLaunchShortcut extends AbstractTesterLaunchShortcut {

	@Override
	protected String getTesterId() {
		return NodeTester.ID;
	}

	/**
	 * LaunchConfigurationType id for node.js tester, needs to match one used in plugin xml.
	 */
	private final static String LAUNCHCONFIGURATIONTYPE_NODEJS_TESTER_ID = "org.eclipse.n4js.tester.nodejs.ui.launchConfigurationType";

	@Override
	protected String getLaunchConfigTypeID() {
		return LAUNCHCONFIGURATIONTYPE_NODEJS_TESTER_ID;
	}
}
