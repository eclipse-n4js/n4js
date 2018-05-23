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

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.difference;
import static com.google.common.collect.Sets.newHashSet;
import static org.eclipse.swt.SWT.CHECK;
import static org.eclipse.swt.SWT.PUSH;

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.xtext.util.Arrays;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Iterables;
import com.google.inject.Inject;

import org.eclipse.n4js.ui.ImageDescriptorCache.ImageRef;
import org.eclipse.n4js.ui.workingsets.WorkingSet;
import org.eclipse.n4js.ui.workingsets.WorkingSetDiffBuilder;
import org.eclipse.n4js.ui.workingsets.WorkingSetManager;
import org.eclipse.n4js.ui.workingsets.WorkingSetManagerBroker;
import org.eclipse.n4js.utils.Diff;

/**
 * Drop down action to make hidden working sets visible on the UI.
 */
public class ShowHiddenWorkingSetsDropDownAction extends DropDownAction {

	/**
	 * Menu item text for showing all hidden working set elements.
	 */
	@VisibleForTesting
	public static final String SHOW_ALL_HIDDEN_WORKING_SETS = "Show All Hidden Working Sets";

	@Inject
	private WorkingSetManagerBroker workingSetManagerBroker;

	/**
	 * Creates an new drop down action to show hidden working sets on the UI.
	 */
	public ShowHiddenWorkingSetsDropDownAction() {
		super(ImageRef.SHOW_HIDDEN_WORKING_SETS.asImageDescriptor().orNull());
		setToolTipText("Show Hidden Working Sets");
	}

	@Override
	protected void createMenuItems(final Menu parent) {
		final WorkingSetManager manager = workingSetManagerBroker.getActiveManager();
		final WorkingSet[] allWorkingSets = manager.getAllWorkingSets();
		final WorkingSet[] workingSets = manager.getWorkingSets();
		for (final WorkingSet workingSet : allWorkingSets) {
			if (!Arrays.contains(workingSets, workingSet)) {
				final MenuItem item = new MenuItem(parent, PUSH);
				item.setText(workingSet.getName());
				item.addSelectionListener(new SelectionAdapter() {

					@Override
					public void widgetSelected(final SelectionEvent e) {
						showWorkingSet(manager, workingSet);
					}

				});
			}
		}

		createSeparator(parent);
		final MenuItem item = new MenuItem(parent, CHECK);
		item.setText(SHOW_ALL_HIDDEN_WORKING_SETS);
		item.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(final SelectionEvent e) {
				final Iterable<WorkingSet> difference = difference(newHashSet(allWorkingSets), newHashSet(workingSets));
				final WorkingSet[] toShow = Iterables.toArray(difference, WorkingSet.class);
				showWorkingSet(manager, toShow);
			}

		});

	}

	private void showWorkingSet(final WorkingSetManager manager, final WorkingSet... workingSets) {
		final WorkingSetDiffBuilder builder = new WorkingSetDiffBuilder(manager);
		final Collection<WorkingSet> visibleItems = newArrayList(manager.getWorkingSets());
		visibleItems.addAll(newArrayList(workingSets));
		final WorkingSet[] newAllItems = manager.getAllWorkingSets();
		final Iterable<WorkingSet> newItems = newArrayList(manager.getAllWorkingSets());
		for (final Iterator<WorkingSet> itr = newItems.iterator(); itr.hasNext(); /**/) {
			final WorkingSet next = itr.next();
			if (!visibleItems.contains(next)) {
				itr.remove();
			}
		}
		final Diff<WorkingSet> diff = builder.build(Iterables.toArray(newItems, WorkingSet.class), newAllItems);
		if (!diff.isEmpty()) {
			manager.updateState(diff);
			manager.saveState(new NullProgressMonitor());
			manager.getWorkingSetManagerBroker().refreshNavigator();
		}
	}

}
