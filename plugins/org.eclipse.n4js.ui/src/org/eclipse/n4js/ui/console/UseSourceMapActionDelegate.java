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

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleView;

/**
 *
 */
public class UseSourceMapActionDelegate implements IViewActionDelegate {

	private N4JSStackTraceConsole console;
	private IConsoleView view;

	public UseSourceMapActionDelegate() {
	}

	public UseSourceMapActionDelegate(N4JSStackTraceConsole console) {
		console = console;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.ui.IViewActionDelegate#init(org.eclipse.ui.IViewPart)
	 */
	@Override
	public void init(IViewPart viewPart) {
		if (viewPart instanceof IConsoleView) {
			this.view = (IConsoleView) viewPart;
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	@Override
	public void run(IAction action) {
		if (console != null) {
			// fConsole.useSourceMap();
		} else if (view != null) {
			IConsole consoleOfView = view.getConsole();
			if (consoleOfView instanceof N4JSStackTraceConsole) {
				console = (N4JSStackTraceConsole) consoleOfView;
				// fConsole.useSourceMap();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction,
	 * org.eclipse.jface.viewers.ISelection)
	 */
	@Override
	public void selectionChanged(IAction action, ISelection selection) {
	}

}
