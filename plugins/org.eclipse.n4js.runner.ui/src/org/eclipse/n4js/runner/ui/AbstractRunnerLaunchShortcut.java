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

import org.eclipse.core.resources.IFile;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.ILaunchShortcut;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;
import org.eclipse.n4js.runner.RunConfiguration;
import org.eclipse.n4js.runner.RunnerFrontEnd;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;

import com.google.inject.Inject;

/**
 */
public abstract class AbstractRunnerLaunchShortcut implements ILaunchShortcut {

	/** The helper for accessing the {@link IN4JSProject} and other project related information. */
	@Inject
	protected IN4JSCore in4jsCore;
	@Inject
	private RunConfigurationConverter runConfigConverter;
	@Inject
	private RunnerFrontEnd runnerFrontEnd;
	@Inject
	private ChooseImplementationHelper chooseImplHelper;

	/**
	 */
	protected abstract String getRunnerId();

	/**
	 * Provides the launch config type ID as specified in the plugin.xml for a given launch set up.
	 */
	protected abstract String getLaunchConfigTypeID();

	private static void showDialogNotImplemented(String what) {
		MessageDialog.openWarning(null, "Warning", "Launching of type " + what + " is not implemented yet!");
	}

	@Override
	public void launch(ISelection selection, String mode) {
		Object selectObj = ((IStructuredSelection) selection).getFirstElement();
		if (selectObj instanceof IFile) {
			launchFile((IFile) selectObj, mode);
		} else {
			showDialogNotImplemented(selection.getClass().getName());
		}
	}

	@Override
	public void launch(IEditorPart editor, String mode) {
		IEditorInput editorInput = editor.getEditorInput();
		if (editorInput instanceof IFileEditorInput) {
			IFile selectObj = ((IFileEditorInput) editorInput).getFile();
			launchFile(selectObj, mode);
		} else {
			showDialogNotImplemented(editor.getClass().getName());
		}
	}

	/**
	 * Computes the launch configuration name from the file to run and the configuration. The latter is appended in
	 * parentheses after the name in order to make the name unique -- otherwise, running a file with different engines
	 * would result in loosing all configuration run before the last one.
	 *
	 */
	public static String computeLaunchConfigNameFrom(IFile originalFileToRun, ILaunchConfigurationType type) {
		String configname = originalFileToRun.getFullPath().toString().replace('/', '-');
		if (configname.startsWith("-")) {
			configname = configname.substring(1);
		}
		configname += " (" + type.getName() + ")";
		return configname;
	}

	/**
	 * Launch a file, using the file information, which means using default launch configurations.
	 */
	protected void launchFile(IFile originalFileToRun, String mode) {
		final String runnerId = getRunnerId();
		final String path = originalFileToRun.getFullPath().toOSString();
		final URI moduleToRun = URI.createPlatformResourceURI(path, true);

		final String implementationId = chooseImplHelper.chooseImplementationIfRequired(runnerId, moduleToRun);
		if (implementationId == ChooseImplementationHelper.CANCEL)
			return;

		RunConfiguration runConfig = runnerFrontEnd.createConfiguration(runnerId,
				implementationId != null ? new N4JSProjectName(implementationId) : null,
				moduleToRun);

		ILaunchManager launchManager = DebugPlugin.getDefault().getLaunchManager();
		ILaunchConfigurationType type = launchManager.getLaunchConfigurationType(getLaunchConfigTypeID());
		DebugUITools.launch(runConfigConverter.toLaunchConfiguration(type, runConfig), mode);
		// execution dispatched to proper delegate LaunchConfigurationDelegate
	}
}
