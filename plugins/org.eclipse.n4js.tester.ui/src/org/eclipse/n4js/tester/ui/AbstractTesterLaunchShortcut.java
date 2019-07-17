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

import java.util.Arrays;
import java.util.Collections;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.ILaunchShortcut;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.runner.RunConfiguration;
import org.eclipse.n4js.runner.ui.AbstractLaunchConfigurationMainTab;
import org.eclipse.n4js.tester.TestConfiguration;
import org.eclipse.n4js.tester.TesterFrontEnd;
import org.eclipse.n4js.ui.projectModel.IN4JSEclipseProject;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

import com.google.common.base.Optional;
import com.google.inject.Inject;

/**
 */
public abstract class AbstractTesterLaunchShortcut implements ILaunchShortcut {

	@Inject
	private TesterFrontEnd testerFrontEnd;
	@Inject
	private TestConfigurationConverter testConfigConverter;
	@Inject
	private IN4JSCore n4jsCore;

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

		copyProjectsLaunchConfigSettings(resourceToTest, testConfig, type);

		ILaunchConfiguration launchConfig = testConfigConverter.toLaunchConfiguration(type, testConfig, null);

		Display display = PlatformUI.getWorkbench().getDisplay();
		display.syncExec(() -> DebugUITools.launch(launchConfig, mode));
	}

	/**
	 * Copies ENV_VARS, ENGINE_OPTIONS, CUSTOM_ENGINE_PATH and SYSTEM_LOADER of project launch confif (if found) to more
	 * launch config of resource within this project.
	 */
	private void copyProjectsLaunchConfigSettings(URI resourceToTest, TestConfiguration testConfig,
			ILaunchConfigurationType type) {
		ILaunchManager launchManager = DebugPlugin.getDefault().getLaunchManager();
		Optional<? extends IN4JSProject> project = n4jsCore.findProject(resourceToTest);
		if (project.isPresent()) {
			IN4JSProject n4jsProject = project.get();
			if (n4jsProject instanceof IN4JSEclipseProject) {
				try {
					final String projectLocation = n4jsProject.getLocation().getAbsolutePath();
					java.util.Optional<ILaunchConfiguration> projectLaunchConfig = Arrays
							.stream(launchManager.getLaunchConfigurations(type))
							.filter(config -> {
								try {
									String resToTest = AbstractLaunchConfigurationMainTab.getResourceRunAsText(config);
									return projectLocation.equals(resToTest);
								} catch (CoreException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								return false;
							}).findAny();
					if (projectLaunchConfig.isPresent()) {
						ILaunchConfiguration pconfig = projectLaunchConfig.get();
						testConfig.setEnvironmentVariables(
								pconfig.getAttribute(RunConfiguration.ENV_VARS, Collections.emptyMap()));
						testConfig.setEngineOptions(pconfig.getAttribute(RunConfiguration.ENGINE_OPTIONS, ""));
						testConfig.setRunOptions(pconfig.getAttribute(RunConfiguration.RUN_OPTIONS, ""));
					}
				} catch (CoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
