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
package org.eclipse.n4js.xpect.ui.results;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;
import org.junit.runner.Description;

import org.eclipse.n4js.xpect.ui.N4IDEXpectUIPlugin;
import org.eclipse.n4js.xpect.ui.runner.N4IDEXpectFileNameUtil;

/**
 * Base for generating in the console xpect reports from xpect run results. Checks if xpect results are valid for proper
 * bug report.
 */
public abstract class GenerateXpectReportCommandHandler extends AbstractHandler {

	private N4IDEXpectView view;

	/**
	 * When called will check if provided data contains {@link Description test description} with failed status stored
	 * in {@link N4IDEXpectView test view}. If that holds, will generate data for bug report in a console view,
	 * otherwise will show message to reconfigure and rerun Xpect tests.
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getCurrentSelectionChecked(event);

		IWorkbenchWindow[] windows = N4IDEXpectUIPlugin.getDefault().getWorkbench().getWorkbenchWindows();
		try {
			view = (N4IDEXpectView) windows[0].getActivePage().showView(
					N4IDEXpectView.ID);
		} catch (PartInitException e) {
			N4IDEXpectUIPlugin.logError("cannot refresh test view window", e);
		}

		Description desc = (Description) selection.getFirstElement();

		// handle failed suite
		if (desc.isSuite()) {
			final N4IDEXpectView finalview = view;

			boolean suitePassed = desc.getChildren().stream()
					.noneMatch(childDescription -> finalview.testsExecutionStatus.hasFailed(childDescription));

			if (suitePassed) {
				XpectFileContentsUtil.getXpectFileContentAccess(desc).ifPresent(
						xpectFielContentAccess -> {
							if (xpectFielContentAccess.containsFixme()) {
								generateAndDisplayReport(
										N4IDEXpectFileNameUtil.getSuiteName(desc),
										xpectFielContentAccess.getContetns());
							}
						});

			} else {
				XpectConsole console = ConsoleDisplayMgr.getOrCreate("generated bug for "
						+ N4IDEXpectFileNameUtil.getSuiteName(desc));
				console.clear();
				String ls = System.lineSeparator();
				console.log("Suite must be passing and contain XPECT FIXME marker to be submited bug report. Please :"
						+ ls + " - fix failing tests" + ls + " - mark test in question with XPECT FIXME");
			}
		}

		return null;
	}

	/**
	 * Generate bug report for a given module.
	 *
	 * @param name
	 *            of the module used in bug report
	 * @param content
	 *            of the module used in the bug report
	 */
	protected abstract void generateAndDisplayReport(String name, String content);

}
