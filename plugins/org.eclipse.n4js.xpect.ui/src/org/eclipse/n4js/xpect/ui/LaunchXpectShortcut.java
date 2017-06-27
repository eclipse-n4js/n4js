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

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.ILaunchShortcut;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;

/**
 * Launch shortcut for xpect execution.( Multiple selection not enabled yet in the UI.)
 */
public class LaunchXpectShortcut implements ILaunchShortcut {

	/** launch config type ID as specified in the plugin.xml */
	public final static String LAUNCHCONFIGURATIONTYPE_XPECT_ID = "org.eclipse.n4js.xpect.ui.LaunchConfigurationType.XPECT";

	/***/
	public LaunchXpectShortcut() {
	}

	/**
	 * Provides the launch config type ID as specified in the plugin.xml for a given launch set up.
	 */
	protected String getLaunchConfigTypeID() {
		return LAUNCHCONFIGURATIONTYPE_XPECT_ID;
	}

	private static void showDialogNotImplemented(String what) {
		MessageDialog.openWarning(null, "Warning", "Launching of type " + what + " is not implemeneted yet!");
	}

	@Override
	public void launch(ISelection selection, String mode) {
		try {
			Object selectObj = ((IStructuredSelection) selection).getFirstElement();
			if (selectObj instanceof IFile) {
				launchFile((IFile) selectObj, mode);
			} else {
				showDialogNotImplemented(selection.getClass().getName());
			}
		} catch (CoreException e) {
			System.out.println(e.getLocalizedMessage() + "\n");
		}
	}

	@Override
	public void launch(IEditorPart editor, String mode) {
		try {
			IEditorInput editorInput = editor.getEditorInput();
			if (editorInput instanceof IFileEditorInput) {
				IFile selectObj = ((IFileEditorInput) editorInput).getFile();
				launchFile(selectObj, mode);
			} else {
				showDialogNotImplemented(editor.getClass().getName());
			}
		} catch (CoreException e) {
			System.out.println(e.getLocalizedMessage() + "\n");
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
	 * Launch an file, using the file information, which means using default launch configurations.
	 */
	protected void launchFile(IFile fileSelectedToRun, String mode) throws CoreException {

		ILaunchManager launchManager = DebugPlugin.getDefault().getLaunchManager();
		ILaunchConfigurationType type = launchManager
				.getLaunchConfigurationType(getLaunchConfigTypeID());

		String testFileLocation = fileSelectedToRun.getRawLocationURI().toString();

		String configName = computeLaunchConfigNameFrom(fileSelectedToRun, type);

		XpectRunConfiguration runConfig = XpectRunConfiguration.createToRunXtFile(configName, testFileLocation);
		runConfig.setConfigurationType(type);

		runConfig.setWorkingDirectory(fileSelectedToRun.getRawLocation().toFile());

		DebugUITools.launch(runConfig.toLaunchConfiguration(), mode);
	}

}
