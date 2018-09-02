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

import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.console.IConsoleConstants;
import org.eclipse.ui.console.IConsoleView;
import org.eclipse.ui.console.TextConsole;
import org.eclipse.ui.console.TextConsolePage;
import org.eclipse.ui.console.TextConsoleViewer;

/**
 * @see org.eclipse.jdt.internal.debug.ui.console.JavaStackTraceConsolePage
 */
public class N4JSStackTraceConsolePage extends TextConsolePage {

	private UseSourceMapSettingAction useSourceMapSettingAction;

	public N4JSStackTraceConsolePage(TextConsole console, IConsoleView view) {
		super(console, view);
	}

	@Override
	protected void createActions() {
		super.createActions();

		IActionBars actionBars = getSite().getActionBars();
		useSourceMapSettingAction = new UseSourceMapSettingAction(this);
		IToolBarManager toolBarManager = actionBars.getToolBarManager();
		toolBarManager.appendToGroup(IConsoleConstants.OUTPUT_GROUP, useSourceMapSettingAction);

		// IActionBars actionBars = getSite().getActionBars();
		// fAutoFormat = new AutoFormatSettingAction(this);
		// IToolBarManager toolBarManager = actionBars.getToolBarManager();
		// toolBarManager.appendToGroup(IConsoleConstants.OUTPUT_GROUP, fAutoFormat);
	}

	@Override
	protected TextConsoleViewer createViewer(Composite parent) {
		return new N4JSStackTraceConsoleViewer(parent, (N4JSStackTraceConsole) getConsole());
	}
}
