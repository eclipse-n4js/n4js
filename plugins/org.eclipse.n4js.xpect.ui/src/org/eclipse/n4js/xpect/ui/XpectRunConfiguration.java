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
package org.eclipse.n4js.xpect.ui;

import java.io.File;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;

import com.google.common.base.Strings;

/**
 * Value object containing run configuration for xpect runner.
 */
public class XpectRunConfiguration {

	/**
	 * Key used in ILaunchConfiguration for attribute specifying the module to bootstrap.
	 */
	public final static String XT_FILE_TO_RUN = "XT_FILE_TO_RUN";
	/**
	 * Key used in ILaunchConfiguration for attribute specifying the working directory.
	 */
	public final static String WORKING_DIRECTORY = "WORKING_DIRECTORY";

	final String configName;

	final String xtFileToRun;

	File workingDirectory;

	ILaunchConfigurationType configurationType;

	/**
	 * Creates run configuration, contains raw location of the module to run.
	 */
	protected XpectRunConfiguration(String configName, String xtFileToRun) {
		this.xtFileToRun = Strings.isNullOrEmpty(xtFileToRun) ? "" : xtFileToRun;
		this.configName = configName;
	}

	/**
	 * Creates an {@link ILaunchConfiguration} containing the information of this instance, only available when run
	 * within the Eclipse IDE. If an {@link ILaunchConfiguration} with the same name has been run before, the instance
	 * from the launch manager is used. This is usually done in the {@code ILaunchShortcut}.
	 *
	 * @see #fromLaunchConfiguration(ILaunchConfiguration)
	 */
	public ILaunchConfiguration toLaunchConfiguration() throws CoreException {
		ILaunchConfiguration[] configs = DebugPlugin.getDefault().getLaunchManager()
				.getLaunchConfigurations(configurationType);
		boolean configurationHasChanged = false;
		for (ILaunchConfiguration config : configs) {
			if (configName.equals(config.getName())) {
				configurationHasChanged = hasConfigurationChanged(config);
				if (!configurationHasChanged) {
					return config;
				}
			}
		}

		IContainer container = null;
		ILaunchConfigurationWorkingCopy workingCopy = configurationType.newInstance(container, configName);

		workingCopy.setAttribute(XT_FILE_TO_RUN, xtFileToRun);
		workingCopy.setAttribute(WORKING_DIRECTORY, workingDirectory.getAbsolutePath());

		return workingCopy.doSave();
	}

	private boolean hasConfigurationChanged(ILaunchConfiguration config) throws CoreException {
		return (config.getAttribute(XT_FILE_TO_RUN, "") != xtFileToRun | config.getAttribute(WORKING_DIRECTORY, "") != workingDirectory
				.getAbsolutePath());
	}

	/**
	 * Creates a {@link XpectRunConfiguration} from a given {@link ILaunchConfiguration}, this is to be used in an
	 * {@code ILaunchConfigurationDelegate} in order to easier access the information stored in the launch
	 * configuration. The launch configuration should have been created via {@link #toLaunchConfiguration()} before.
	 */
	public static XpectRunConfiguration fromLaunchConfiguration(ILaunchConfiguration launchConfig)
			throws CoreException {

		String xtFileToRun = launchConfig.getAttribute(XT_FILE_TO_RUN, "");
		String name = launchConfig.getName();
		XpectRunConfiguration runConfig = new XpectRunConfiguration(name, xtFileToRun);
		runConfig.setConfigurationType(launchConfig.getType());

		String strWorkingDir = launchConfig.getAttribute(WORKING_DIRECTORY, "");
		File workingDir = null;
		if (!Strings.isNullOrEmpty(strWorkingDir)) {
			workingDir = new File(strWorkingDir);
		}
		runConfig.setWorkingDirectory(workingDir);

		return runConfig;
	}

	/**
	 * Creates a run configuration for running a xt file.
	 *
	 * @param xtFileToRun
	 *            string with raw location of xt file, e.g. "c:/users/user/workspace/project/src/file.n4js.xt"
	 */
	public static XpectRunConfiguration createToRunXtFile(String configName, String xtFileToRun) {
		return new XpectRunConfiguration(configName, xtFileToRun);
	}

	/**
	 * Returns true if the configuration has been created via {@link #createToRunXtFile(String, String)}.
	 */
	public boolean isRunXtFile() {
		return !Strings.isNullOrEmpty(xtFileToRun);
	}

	/**
	 * Returns string with raw location of xt file, e.g. "c:/users/user/workspace/project/src/file.n4js.xt"
	 *
	 * @return the xt file location, only valid if {@link #isRunXtFile()} is true
	 */
	public String getXtFileToRun() {
		return xtFileToRun;
	}

	/**
	 * Returns the working directory set before the engine is to be started, not used by all engines.
	 */
	public File getWorkingDirectory() {
		return workingDirectory;
	}

	/**
	 * @see #getWorkingDirectory()
	 */
	public void setWorkingDirectory(File workingDirectory) {
		this.workingDirectory = workingDirectory;
	}

	/**
	 * Returns the configuration type in order to let the debug API select the correct launch delegate. This needs to be
	 * set if the launch is to be performed by the Eclipse debug API.
	 */
	public ILaunchConfigurationType getConfigurationType() {
		return configurationType;
	}

	/**
	 * @see #getConfigurationType()
	 */
	public void setConfigurationType(ILaunchConfigurationType configurationType) {
		this.configurationType = configurationType;
	}

}
