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
package org.eclipse.n4js.tester.nodejs.ui.launch

import com.google.inject.Inject
import com.google.inject.Provider
import org.eclipse.n4js.tester.ui.TesterLaunchConfigurationMainTab
import org.eclipse.debug.ui.AbstractLaunchConfigurationTabGroup
import org.eclipse.debug.ui.ILaunchConfigurationDialog

/**
 * Node.js launch configuration tab group. Creates the main tab and one for the Node.js specific
 * settings.
 */
class NodejsTesterLaunchConfigurationTabGroup extends AbstractLaunchConfigurationTabGroup {

	@Inject
	private Provider<TesterLaunchConfigurationMainTab> mainTabProvider;

	@Inject
	private Provider<NodejsTesterLaunchConfigurationTab> nodejsTabProvider;

	@Override
	override createTabs(ILaunchConfigurationDialog dialog, String mode) {
		tabs = #[mainTabProvider.get, nodejsTabProvider.get];
	}

}
