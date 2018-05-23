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

import org.eclipse.compare.CompareUI;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;
import org.junit.ComparisonFailure;
import org.junit.runner.Description;
import org.eclipse.xpect.runner.IXpectURIProvider;
import org.eclipse.xpect.runner.XpectRunner;

import org.eclipse.n4js.xpect.ui.N4IDEXpectUIPlugin;
import org.eclipse.n4js.xpect.ui.runner.N4IDEXpectTestFilesCollector.N4IDEXpectTestURIProvider;

/**
 * When executed calls {@link CompareUI#openCompareEditor} with input created from failed test in receiver. If receiver
 * is not {@link IStructuredSelection} holding {@link Description} of failed test, that failed with
 * {@link ComparisonFailure} then input cannot be constructed and compare view is not displayed.
 */
public class XpectCompareCommandHandler extends AbstractHandler {

	private N4IDEXpectView view;

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
		if (desc.isTest() && view.testsExecutionStatus.hasFailed(desc)) {
			Throwable failureException = view.testsExecutionStatus.getFailure(desc).getException();

			if (failureException instanceof ComparisonFailure) {
				ComparisonFailure cf = (ComparisonFailure) failureException;
				// display comparison view
				displayComparisonView(cf, desc);
			}
		}
		return null;
	}

	/**
	 * Display comparison view of test file with expected and actual xpect expectation
	 */
	private void displayComparisonView(ComparisonFailure cf, Description desc) {
		IXpectURIProvider uriProfider = XpectRunner.INSTANCE.getUriProvider();
		IFile fileTest = null;
		if (uriProfider instanceof N4IDEXpectTestURIProvider) {
			N4IDEXpectTestURIProvider fileCollector = (N4IDEXpectTestURIProvider) uriProfider;
			fileTest = ResourcesPlugin.getWorkspace().getRoot()
					.getFileForLocation(new Path(fileCollector.findRawLocation(desc)));
		}

		if (fileTest != null && fileTest.isAccessible()) {
			N4IDEXpectCompareEditorInput inp = new N4IDEXpectCompareEditorInput(fileTest, cf);
			CompareUI.openCompareEditor(inp);
		} else {
			throw new RuntimeException("paths in descriptions changed!");
		}
	}

}
