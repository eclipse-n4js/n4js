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

import java.util.List;

import org.eclipse.ui.IWorkingSet;

import org.eclipse.n4js.utils.Diff;

/**
 * Strategy for deleting an existing working set from the {@link WorkingSetManager working set manager}.
 */
public class RemoveWorkingSetModificationStrategy extends WorkingSetManagerModificationStrategyImpl {

	private final IWorkingSet toRemove;

	/**
	 * Creates a new strategy with the argument that has to be removed from the working set manager.
	 *
	 * @param toRemoved
	 *            the working set to remove.
	 */
	public RemoveWorkingSetModificationStrategy(final IWorkingSet toRemoved) {
		this.toRemove = toRemoved;
	}

	@Override
	public void execute(final WorkingSetManager manager) {
		if (toRemove != null && RESOURCE_WORKING_SET_ID.equals(toRemove.getId())) {

			final String name = toRemove.getName();
			final WorkingSet workingSet = getWorkingSetByName(manager, name);

			// Nothing to do if the working set already absent.
			if (workingSet == null) {
				return;
			}

			final WorkingSetDiffBuilder builder = new WorkingSetDiffBuilder(manager);
			builder.delete(workingSet);

			final List<WorkingSet> newAllItems = newArrayList(manager.getAllWorkingSets());
			newAllItems.remove(workingSet);

			final List<WorkingSet> newItems = newArrayList(manager.getWorkingSets());
			newItems.remove(workingSet);

			final Diff<WorkingSet> diff = builder.build(newItems, newAllItems);
			updateAndSaveState(manager, diff);
		}
	}

}
