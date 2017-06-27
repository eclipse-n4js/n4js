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
package org.eclipse.n4js.ui.workingsets;

import static com.google.common.collect.Lists.newArrayList;

import org.eclipse.ui.IWorkingSet;

/**
 * Strategy for updating an existing working set in the working set manager by simply deleting the old state and
 * recreating it with the new state.
 */
public class UpdateWorkingSetModificationStraregy extends WorkingSetManagerModificationStrategyImpl {

	private final IWorkingSet oldValue;
	private final IWorkingSet newValue;

	/**
	 * Creates a new strategy for updating an existing working set.
	 *
	 * @param oldValue
	 *            the old state of the working set.
	 * @param newValue
	 *            the new state of the working set.
	 */
	public UpdateWorkingSetModificationStraregy(final IWorkingSet oldValue, final IWorkingSet newValue) {
		this.oldValue = oldValue;
		this.newValue = newValue;
	}

	@Override
	public void execute(final WorkingSetManager manager) {
		if (oldValue == null || newValue == null) {
			return;
		}

		final String oldName = oldValue.getName();
		final WorkingSet toRemove = getWorkingSetByName(manager, oldName);

		// Old state does not exist, do not create a new one with the new state.
		if (toRemove == null) {
			return;
		}

		int newAllItemsIndex = newArrayList(manager.getAllWorkingSets()).indexOf(toRemove);
		if (newAllItemsIndex == -1) {
			// Could not found in all working sets.
			return;
		}
		int newItemsIndex = newArrayList(manager.getWorkingSets()).indexOf(toRemove);

		new RemoveWorkingSetModificationStrategy(oldValue) {

			@Override
			protected void refreshNavigator(@SuppressWarnings("hiding") WorkingSetManager manager) {
				// Do not refresh here to avoid excessive navigator refresh.
				// Add modification will refresh the navigator.
			}

		}.execute(manager);

		new AddWorkingSetModificationStrategy(newValue, newItemsIndex, newAllItemsIndex).execute(manager);
	}

}
