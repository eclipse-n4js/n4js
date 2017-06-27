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

import org.eclipse.jface.action.ContributionItem;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.actions.ActionGroup;

import com.google.inject.Inject;

import org.eclipse.n4js.ui.workingsets.WorkingSetManagerBroker;

/**
 * Action group for toggling whether projects or working sets are selected as top level elements in the navigator.
 */
public class SelectTopLevelElementActionGroup extends ActionGroup {

	@Inject
	private WorkingSetManagerBroker workingSetManagerBroker;

	private MenuItem projectsItem;
	private MenuItem workingSetsItem;
	private boolean hasContributedToViewMenu = false;

	@Override
	public void fillActionBars(final IActionBars actionBars) {
		if (hasContributedToViewMenu) {
			return;
		}
		final IMenuManager topLevelSubMenu = new MenuManager("Top Level Elements");
		addActions(topLevelSubMenu);
		final IMenuManager menuManager = actionBars.getMenuManager();
		menuManager.insertBefore(IWorkbenchActionConstants.MB_ADDITIONS, topLevelSubMenu);
		hasContributedToViewMenu = true;
	}

	private void addActions(final IMenuManager menuManager) {
		menuManager.add(new Separator());

		// Projects menu item
		menuManager.add(new ContributionItem() {

			@Override
			public void fill(final Menu menu, final int index) {

				projectsItem = new MenuItem(menu, SWT.CHECK);
				projectsItem.setText("Projects");
				projectsItem.addSelectionListener(new SelectionAdapter() {

					@Override
					public void widgetSelected(final SelectionEvent e) {
						projectsItem.setSelection(true);
						workingSetsItem.setSelection(false);
						workingSetManagerBroker.setWorkingSetTopLevel(false);
					}

				});

				if (!workingSetManagerBroker.isWorkingSetTopLevel()) {
					projectsItem.setSelection(true);
				}
			}
		});

		menuManager.add(new ContributionItem() {

			@Override
			public void fill(final Menu menu, final int index) {

				workingSetsItem = new MenuItem(menu, SWT.CHECK);
				workingSetsItem.setText("Working Sets");
				workingSetsItem.addSelectionListener(new SelectionAdapter() {

					@Override
					public void widgetSelected(final SelectionEvent e) {
						workingSetsItem.setSelection(true);
						projectsItem.setSelection(false);
						workingSetManagerBroker.setWorkingSetTopLevel(true);
					}

				});

				if (workingSetManagerBroker.isWorkingSetTopLevel()) {
					workingSetsItem.setSelection(true);
				}
			}
		});

	}

}
