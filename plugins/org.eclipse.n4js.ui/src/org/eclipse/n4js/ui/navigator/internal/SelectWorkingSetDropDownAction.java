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

import static org.eclipse.swt.SWT.CHECK;

import java.util.Collection;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import com.google.inject.Inject;

import org.eclipse.n4js.ui.ImageDescriptorCache.ImageRef;
import org.eclipse.n4js.ui.workingsets.WorkingSetManager;
import org.eclipse.n4js.ui.workingsets.WorkingSetManagerBroker;

/**
 * A drop down action for selecting and activating registered working set managers.
 */
public class SelectWorkingSetDropDownAction extends DropDownAction {

	@Inject
	private WorkingSetManagerBroker workingSetManagerBroker;

	/**
	 * Creates a new drop down working set action.
	 */
	public SelectWorkingSetDropDownAction() {
		super(ImageRef.WORKING_SET.asImageDescriptor().orNull());
		setToolTipText("Select Active Working Set Manager");
	}

	@Override
	protected void createMenuItems(final Menu parent) {
		final Collection<WorkingSetManager> managers = workingSetManagerBroker.getWorkingSetManagers();
		for (final WorkingSetManager manager : managers) {
			final MenuItem item = new MenuItem(parent, CHECK);
			item.setText(manager.getLabel());
			item.setImage(manager.getImage().orNull());
			item.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(final SelectionEvent e) {
					workingSetManagerBroker.setActiveManager(manager);
				}
			});
			item.setSelection(workingSetManagerBroker.isActiveManager(manager));

		}

		final WorkingSetManager activeManager = workingSetManagerBroker.getActiveManager();
		if (null != activeManager) {
			createSeparator(parent);
			final MenuItem item = new MenuItem(parent, CHECK);
			item.setText("Configure " + activeManager.getLabel() + "...");
			item.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(final SelectionEvent e) {
					activeManager.configure();
					workingSetManagerBroker.refreshNavigator();
				}
			});
		}

	}

}
