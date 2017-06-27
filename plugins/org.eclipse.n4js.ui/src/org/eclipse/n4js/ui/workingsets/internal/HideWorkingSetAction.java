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
package org.eclipse.n4js.ui.workingsets.internal;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.actions.SelectionListenerAction;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Iterables;

import org.eclipse.n4js.ui.ImageDescriptorCache.ImageRef;
import org.eclipse.n4js.ui.workingsets.WorkingSet;
import org.eclipse.n4js.ui.workingsets.WorkingSetDiffBuilder;
import org.eclipse.n4js.ui.workingsets.WorkingSetManager;
import org.eclipse.n4js.utils.Diff;
import org.eclipse.n4js.utils.collections.Arrays2;

/**
 * Action for hiding one or more working set instances based on the actual selection.
 */
public class HideWorkingSetAction extends SelectionListenerAction implements IWorkbenchAction {

	/**
	 * Creates a new action instance.
	 */
	public HideWorkingSetAction() {
		super("Hide Selected Working Set");
		setImageDescriptor(ImageRef.CLEAR.asImageDescriptor().orNull());
	}

	@Override
	public void run() {
		final IStructuredSelection selection = getStructuredSelection();
		final Object[] selectionElements = selection.toArray();
		final WorkingSet[] selectedWorkingSets = Arrays2.filter(selectionElements, WorkingSet.class);

		final WorkingSetManager manager = selectedWorkingSets[0].getWorkingSetManager();
		final WorkingSetDiffBuilder builder = new WorkingSetDiffBuilder(manager);
		final WorkingSet[] newAllItems = manager.getAllWorkingSets();
		final List<WorkingSet> newItems = newArrayList(manager.getWorkingSets());
		for (final WorkingSet toHide : selectedWorkingSets) {
			newItems.remove(toHide);
		}
		final Diff<WorkingSet> diff = builder.build(Iterables.toArray(newItems, WorkingSet.class), newAllItems);
		if (!diff.isEmpty()) {
			manager.updateState(diff);
			manager.saveState(new NullProgressMonitor());
			manager.getWorkingSetManagerBroker().refreshNavigator();
		}
	}

	@Override
	public void dispose() {
		// Currently nothing to dispose.
	}

	@Override
	@VisibleForTesting
	public boolean updateSelection(IStructuredSelection selection) {
		if (selection == null || selection.isEmpty()) {
			return false;
		}

		final Object[] selectionElements = selection.toArray();
		final WorkingSet[] selectedWorkingSets = Arrays2.filter(selectionElements, WorkingSet.class);

		if (selectionElements.length != selectedWorkingSets.length) {
			return false;
		}

		final WorkingSetManager manager = selectedWorkingSets[0].getWorkingSetManager();
		// Cannot hide all working sets.
		return manager.getWorkingSets().length > selectedWorkingSets.length;
	}

}
