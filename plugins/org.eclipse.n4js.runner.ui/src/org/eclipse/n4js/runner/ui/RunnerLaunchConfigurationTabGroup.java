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

import org.eclipse.debug.ui.AbstractLaunchConfigurationTabGroup;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.debug.ui.ILaunchConfigurationTab;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * Tab group for runner tabs.
 */
public class RunnerLaunchConfigurationTabGroup extends AbstractLaunchConfigurationTabGroup {

	@Inject
	private Provider<RunnerLaunchConfigurationMainTab> mainTabProvider;

	@Override
	public void createTabs(final ILaunchConfigurationDialog dialog, final String mode) {
		setTabs(new ILaunchConfigurationTab[] { mainTabProvider.get() });
	}

}
