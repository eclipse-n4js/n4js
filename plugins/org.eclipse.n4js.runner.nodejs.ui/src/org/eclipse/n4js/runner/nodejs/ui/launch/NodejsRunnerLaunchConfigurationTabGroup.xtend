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
package org.eclipse.n4js.runner.nodejs.ui.launch

import org.eclipse.debug.ui.AbstractLaunchConfigurationTabGroup
import org.eclipse.debug.ui.ILaunchConfigurationDialog
import org.eclipse.n4js.runner.ui.RunnerLaunchConfigurationMainTab
import com.google.inject.Provider
import com.google.inject.Inject

/**
 * Node.js launch configuration tab group. Creates the main tab and one for the Node.js specific
 * settings.
 */
class NodejsRunnerLaunchConfigurationTabGroup extends AbstractLaunchConfigurationTabGroup {

	@Inject
	private Provider<RunnerLaunchConfigurationMainTab> mainTabProvider;

	@Inject
	private Provider<NodejsLaunchConfigurationTab> nodejsTabProvider;

	override createTabs(ILaunchConfigurationDialog dialog, String mode) {
		tabs = #[mainTabProvider.get, nodejsTabProvider.get];
	}

}
