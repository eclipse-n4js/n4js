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
package org.eclipse.n4js.ui.navigator.internal;

import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.n4js.ui.workingsets.TopLevelElementChangedListener;
import org.eclipse.n4js.ui.workingsets.WorkingSet;
import org.eclipse.n4js.ui.workingsets.WorkingSetManager;
import org.eclipse.n4js.ui.workingsets.WorkingSetManagerBroker;
import org.eclipse.n4js.ui.workingsets.WorkingSetManagerStateChangedListener;
import org.eclipse.n4js.utils.Diff;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.internal.navigator.resources.actions.WorkingSetActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;

import com.google.inject.Inject;

/**
 * Customized working set provider for the CNF (Common Navigator Framework).
 *
 * Dynamically shows and hides the N4JS working set action bar contributions for switching between different working set
 * managers.
 */
@SuppressWarnings("restriction")
public class N4JSActionBarContributionProvider extends WorkingSetActionProvider
		implements TopLevelElementChangedListener, WorkingSetManagerStateChangedListener {

	@Inject
	private SelectWorkingSetDropDownAction selectWorkingSetAction;

	@Inject
	private SelectTopLevelElementActionGroup selectTopLevelElementAction;

	@Inject
	private ShowHiddenWorkingSetsDropDownAction showHiddenWorkingSetsAction;

	@Inject
	private WorkingSetManagerBroker workingSetManagerBroker;

	private IActionBars actionBars;
	private boolean alreadyContributedToViewer;
	private ActionContributionItem selectWorkingSetDelegate;
	private ActionContributionItem showHiddenWorkingSetsDelegate;

	@Override
	public void init(final ICommonActionExtensionSite site) {
		selectWorkingSetDelegate = new ActionContributionItem(selectWorkingSetAction);
		showHiddenWorkingSetsDelegate = new ActionContributionItem(showHiddenWorkingSetsAction);
		workingSetManagerBroker.addTopLevelElementChangedListener(this);
		workingSetManagerBroker.addWorkingSetManagerStateChangedListener(this);
	}

	@Override
	public void fillActionBars(@SuppressWarnings("hiding") final IActionBars actionBars) {
		if (!alreadyContributedToViewer) {
			this.actionBars = actionBars;
			try {
				topLevelElementChanged(workingSetManagerBroker.isWorkingSetTopLevel());
			} finally {
				alreadyContributedToViewer = true;
			}
		}
	}

	@Override
	public void fillContextMenu(final IMenuManager menu) {
		// Disables the default one.
	}

	@Override
	public void restoreState(final IMemento aMemento) {
		// Nothing to restore.
	}

	@Override
	public void saveState(final IMemento aMemento) {
		// Nothing to save.
	}

	@Override
	public void dispose() {
		if (selectWorkingSetAction != null) {
			selectWorkingSetAction.dispose();
		}
		workingSetManagerBroker.removeTopLevelElementChangedListener(this);
	}

	@Override
	public void topLevelElementChanged(final boolean workingSetTopLevel) {
		if (actionBars != null) {
			final IToolBarManager toolBarManager = actionBars.getToolBarManager();
			toolBarManager.remove(selectWorkingSetDelegate);
			toolBarManager.remove(showHiddenWorkingSetsDelegate);
			if (workingSetManagerBroker.isWorkingSetTopLevel()) {
				toolBarManager.add(selectWorkingSetDelegate);
				final WorkingSetManager manager = workingSetManagerBroker.getActiveManager();
				if (manager != null) {
					WorkingSet[] allItems = manager.getAllWorkingSets();
					WorkingSet[] items = manager.getWorkingSets();
					updateShowHiddenAction(allItems, items);
				}
			}
			selectTopLevelElementAction.fillActionBars(actionBars);
			actionBars.updateActionBars();
		}
	}

	@Override
	public void workingSetManagerStateChanged(final WorkingSetManagerChangeEvent event) {

		final Diff<WorkingSet> diff = event.getDiff();
		final WorkingSetManager activeManager = workingSetManagerBroker.getActiveManager();
		final String changedId = event.getId();

		if (actionBars != null
				&& activeManager != null
				&& !diff.isEmpty()
				&& workingSetManagerBroker.isWorkingSetTopLevel()
				&& activeManager.getId().equals(changedId)) {

			final WorkingSet[] allItems = diff.getNewAllItems();
			final WorkingSet[] items = diff.getNewItems();
			updateShowHiddenAction(allItems, items);
			actionBars.updateActionBars();
		}
	}

	private void updateShowHiddenAction(final WorkingSet[] allItems, final WorkingSet[] items) {
		final IToolBarManager toolBarManager = actionBars.getToolBarManager();
		toolBarManager.remove(showHiddenWorkingSetsDelegate);
		if (allItems.length > items.length) {
			toolBarManager.add(showHiddenWorkingSetsDelegate);
		}
	}

}
