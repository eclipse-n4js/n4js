/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ui.console;

import org.eclipse.jdt.internal.debug.ui.console.JavaStackTraceConsoleViewer;
import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;

/**
 * @see org.eclipse.jdt.internal.debug.ui.console.AutoFormatSettingAction
 */
public class UseSourceMapSettingAction extends Action {
	private final N4JSStackTraceConsolePage fPage;
	// private final IPreferenceStore fPreferenceStore;

	public UseSourceMapSettingAction(N4JSStackTraceConsolePage page) {
		super("Use source map", SWT.TOGGLE);
		fPage = page;

		setToolTipText("Translate JS to N4JS locations via source map");
		setChecked(true);
		// setImageDescriptor(JavaDebugImages.getImageDescriptor(JavaDebugImages.IMG_ELCL_AUTO_FORMAT));
		// setHoverImageDescriptor(JavaDebugImages.getImageDescriptor(JavaDebugImages.IMG_ELCL_AUTO_FORMAT));
		// PlatformUI.getWorkbench().getHelpSystem().setHelp(this,
		// IJavaDebugHelpContextIds.CONSOLE_AUTOFORMAT_STACKTRACES_ACTION);

		// fPreferenceStore = N4JSUIJDIDebugUIPlugin.getDefault().getPreferenceStore();
		// boolean checked = fPreferenceStore.getBoolean(IJDIPreferencesConstants.PREF_AUTO_FORMAT_JSTCONSOLE);
		// setChecked(checked);
	}

	@Override
	public void run() {
		boolean checked = isChecked();
		JavaStackTraceConsoleViewer viewer = (JavaStackTraceConsoleViewer) fPage.getViewer();
		viewer.setAutoFormat(checked);
		// fPreferenceStore.setValue(IJDIPreferencesConstants.PREF_AUTO_FORMAT_JSTCONSOLE, checked);
	}

}
