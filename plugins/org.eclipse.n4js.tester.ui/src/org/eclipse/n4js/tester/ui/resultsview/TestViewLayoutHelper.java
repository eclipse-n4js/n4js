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
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.n4js.tester.ui.TesterUiActivator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.graphics.Point;

/**
 * Helper class for {@link TestResultsView} providing actions and managing state of view layout (orientation).
 *
 * Restoring and saving the state from the memento and triggering state changes is handled in the
 * {@link TestResultsView}.
 */
class TestViewLayoutHelper {

	/**
	 * Actions for view layout (orientation) modeled as radio buttons.
	 */
	private class ToggleOrientationAction extends Action {
		private final int actionOrientation;

		ToggleOrientationAction(int orientation) {
			super(LABELS[orientation], AS_RADIO_BUTTON);
			setImageDescriptor(TesterUiActivator.getImageDescriptor(ICON_IDS[orientation]));
			actionOrientation = orientation;
		}

		@Override
		public void run() {
			if (isChecked()) {
				setOrientation(actionOrientation); // this will call updateActions
			}
		}
	}

	// orientations
	static final int VIEW_ORIENTATION_VERTICAL = 0;
	static final int VIEW_ORIENTATION_HORIZONTAL = 1;
	static final int VIEW_ORIENTATION_AUTOMATIC = 2;

	static final String[] LABELS = { "Vertical", "Horizontal", "Automatic" };
	static final String[] ICON_IDS = { TesterUiActivator.ICON_TH_VERTICAL, TesterUiActivator.ICON_TH_HORIZONTAL,
			TesterUiActivator.ICON_TH_AUTOMATIC };

	/** The actions created in the constructor. */
	private final ToggleOrientationAction[] actions;

	/** The submenu created in the constructor and shown in the view's toolbar. */
	final IMenuManager orientationMenu;

	/**
	 * The sash from the test result view which layout is controlled here.
	 */
	private final SashForm sash;

	/**
	 * The current orientation; either <code>VIEW_ORIENTATION_HORIZONTAL</code> <code>VIEW_ORIENTATION_VERTICAL</code>,
	 * or <code>VIEW_ORIENTATION_AUTOMATIC</code>. Set via {@link #setOrientation(int)}.
	 */
	private int orientation = TestViewLayoutHelper.VIEW_ORIENTATION_AUTOMATIC;

	TestViewLayoutHelper(SashForm sash) {
		this.sash = sash;
		actions = new ToggleOrientationAction[LABELS.length];
		orientationMenu = new MenuManager("Layout");
		for (int i = 0; i < LABELS.length; i++) {
			actions[i] = new ToggleOrientationAction(i);
			orientationMenu.add(actions[i]);
		}
	}

	/**
	 * Returns orientation state, that is either {@link #VIEW_ORIENTATION_AUTOMATIC},
	 * {@link #VIEW_ORIENTATION_HORIZONTAL}, or {@link #VIEW_ORIENTATION_VERTICAL}.
	 */
	int getOrientation() {
		return this.orientation;
	}

	/**
	 * Sets the orientation and updates the sash and actions accordingly.
	 */
	void setOrientation(int orientation) {
		this.orientation = orientation;
		if ((sash == null) || sash.isDisposed())
			return;
		updateSashLayout();
		udpateActions();
		sash.getParent().layout();
	}

	/**
	 * Updates the sash layout to match the layout state.
	 */
	void updateSashLayout() {
		int sashLayout = SWT.VERTICAL;
		switch (orientation) {
		case TestViewLayoutHelper.VIEW_ORIENTATION_VERTICAL:
			break;
		case TestViewLayoutHelper.VIEW_ORIENTATION_HORIZONTAL:
			sashLayout = SWT.HORIZONTAL;
			break;
		default: // TestViewLayoutHelper.VIEW_ORIENTATION_AUTOMATIC:
			Point size = sash.getParent().getSize();
			sashLayout = size.x > size.y ? SWT.HORIZONTAL : SWT.VERTICAL;
		}
		if (sashLayout != sash.getOrientation()) {
			sash.setOrientation(sashLayout);
		}
	}

	/**
	 * Updates actions to reflect proper state.
	 */
	public void udpateActions() {
		for (int i = 0; i < actions.length; ++i)
			actions[i].setChecked(orientation == actions[i].actionOrientation);
	}

}
