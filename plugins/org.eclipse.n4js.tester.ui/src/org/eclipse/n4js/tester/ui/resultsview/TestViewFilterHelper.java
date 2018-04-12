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
package org.eclipse.n4js.tester.ui.resultsview;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.n4js.tester.domain.TestStatus;
import org.eclipse.n4js.tester.ui.TesterUiActivator;

class TestViewFilterHelper {

	private class ToggleFilterAction extends Action {
		private final int actionFilter;

		ToggleFilterAction(int filter) {
			super(LABELS[filter - 1], AS_CHECK_BOX);
			setImageDescriptor(TesterUiActivator.getImageDescriptor(ICON_IDS[filter - 1]));
			actionFilter = filter;
		}

		@Override
		public void run() {
			if (isChecked()) {
				setFilter(actionFilter); // this will call updateActions
			} else {
				setFilter(SHOW_ALL);
			}
		}
	}

	// orientations
	static final int SHOW_ALL = 0;
	static final int SHOW_FAILURES = 1;
	static final int SHOW_SKIPPED = 2;

	static final String[] LABELS = { "Show Failures Only", "Show Skipped Tests Only" };
	static final String[] ICON_IDS = { TesterUiActivator.ICON_SHOW_FAILURES_ONLY,
			TesterUiActivator.ICON_SHOW_SKIPPED_ONLY };

	private final ToggleFilterAction[] actions;
	final IMenuManager orientationMenu;

	/**
	 * The treeviewer to be refereshed on change
	 */
	private final TreeViewer testTreeViewer;

	/**
	 * The current filter.
	 */
	int filter = SHOW_ALL;

	TestViewFilterHelper(TreeViewer testTreeViewer) {
		this.testTreeViewer = testTreeViewer;
		actions = new ToggleFilterAction[LABELS.length];
		orientationMenu = new MenuManager("Layout");
		for (int i = 0; i < LABELS.length; i++) {
			actions[i] = new ToggleFilterAction(i + 1);
			orientationMenu.add(actions[i]);
		}
	}

	void setFilter(int filter) {
		this.filter = filter;
		if (testTreeViewer == null)
			return;
		testTreeViewer.refresh();
		udpateActions();
	}

	public void udpateActions() {
		for (int i = 0; i < actions.length; ++i)
			actions[i].setChecked(filter == actions[i].actionFilter);
	}

	IAction getFailureAction() {
		return actions[SHOW_FAILURES - 1];
	}

	IAction getSkippedAction() {
		return actions[SHOW_SKIPPED - 1];
	}

	/**
	 * Returns true if test status matches given filter or is null.
	 */
	boolean match(TestStatus testStatus) {
		if (testStatus == null) {
			return true;
		}
		if (filter == SHOW_FAILURES) {
			return testStatus == TestStatus.ERROR || testStatus == TestStatus.FAILED;
		}
		if (filter == SHOW_SKIPPED) {
			return testStatus == TestStatus.SKIPPED
					|| testStatus == TestStatus.SKIPPED_FIXME
					|| testStatus == TestStatus.SKIPPED_IGNORE
					|| testStatus == TestStatus.SKIPPED_NOT_IMPLEMENTED
					|| testStatus == TestStatus.SKIPPED_PRECONDITION;
		}
		return true;
	}

}