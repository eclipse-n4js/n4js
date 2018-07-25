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

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.console.TextConsoleViewer;

/**
 * Original code copied from JavaStackTraceViewer (JDT ui debug).
 */
public class N4JSStackTraceConsoleViewer extends TextConsoleViewer {

	private final N4JSStackTraceConsole console;
	private boolean useSourceMap = false;

	/**
	 * Constructor
	 *
	 * @param parent
	 *            the parent to add this viewer to
	 * @param console
	 *            the console associated with this viewer
	 */
	public N4JSStackTraceConsoleViewer(Composite parent, N4JSStackTraceConsole console) {
		super(parent, console);
		this.console = console;
		getTextWidget().setOrientation(SWT.LEFT_TO_RIGHT);

		// IPreferenceStore fPreferenceStore = JDIDebugUIPlugin.getDefault().getPreferenceStore();
		// fAutoFormat = fPreferenceStore.getBoolean(IJDIPreferencesConstants.PREF_AUTO_FORMAT_JSTCONSOLE);
	}

	/**
	 * @see org.eclipse.jface.text.source.SourceViewer#doOperation(int)
	 */
	@Override
	public void doOperation(int operation) {
		super.doOperation(operation);

		// if (fAutoFormat && operation == ITextOperationTarget.PASTE)
		// fConsole.format();
	}

	/**
	 * Sets the state of the autoformat action
	 *
	 * @param checked
	 *            the desired state of the autoformat action
	 */
	public void setUseSourceMap(boolean checked) {
		useSourceMap = checked;
	}
}
