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

import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.ILaunchShortcut;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorPart;

import com.google.inject.Inject;

import org.eclipse.n4js.tester.TestConfiguration;
import org.eclipse.n4js.tester.TesterFrontEnd;

/**
 */
public abstract class AbstractTesterLaunchShortcut implements ILaunchShortcut {

	@Inject
	private TesterFrontEnd testerFrontEnd;
	@Inject
	private TestConfigurationConverter testConfigConverter;

	/**
	 */
	protected abstract String getTesterId();

	/**
	 * Provides the launch configuration type ID as specified in the plugin.xml for a given launch set up.
	 */
	protected abstract String getLaunchConfigTypeID();

	@Override
	public void launch(IEditorPart editor, String mode) {
		final URI location = TestDiscoveryUIUtils.getLocationForEditor(editor);
		if (location == null)
			throw new IllegalArgumentException(
					"no valid location for given editor (should have been filtered out by property tester!!)");
		launchTest(location, mode);
	}

	@Override
	public void launch(ISelection selection, String mode) {
		final URI location = TestDiscoveryUIUtils.getLocationForSelection(selection);
		if (location == null)
			throw new IllegalArgumentException(
					"no valid location for given selection (should have been filtered out by property tester!!)");
		launchTest(location, mode);
	}

	/**
	 * Launch a test of the given URI (may point to project, folder, file).
	 */
	protected void launchTest(URI resourceToTest, String mode) {
		TestConfiguration testConfig = testerFrontEnd.createConfiguration(getTesterId(), null, resourceToTest);

		ILaunchManager launchManager = DebugPlugin.getDefault().getLaunchManager();
		ILaunchConfigurationType type = launchManager.getLaunchConfigurationType(getLaunchConfigTypeID());
		DebugUITools.launch(testConfigConverter.toLaunchConfiguration(type, testConfig), mode);
		// execution dispatched to proper ILaunchConfigurationDelegate
	}
}
