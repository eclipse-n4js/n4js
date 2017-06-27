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

import org.eclipse.core.resources.IProject;
import org.eclipse.ui.IWorkingSet;

import org.eclipse.n4js.ui.workingsets.ManualAssociationAwareWorkingSetManager.ManualAssociationWorkingSet;
import org.eclipse.n4js.utils.Diff;
import org.eclipse.n4js.utils.collections.Arrays2;

/**
 * Strategy for creating and adding a new working set to the {@link WorkingSetManager working set manager}.
 */
public class AddWorkingSetModificationStrategy extends WorkingSetManagerModificationStrategyImpl {

	/**
	 * Index constant representing that the {@link List#add(int, Object)} method will be called with the
	 * {@link List#size()} {@code -1} value when adding new working set. Basically it has the same effect as calling
	 * {@link List#add(Object)}.
	 */
	static final int APPEND_TO_END_INDEX = -2;

	private final IWorkingSet toAdd;
	private final int newItemsIndex;
	private final int newAllItemsIndex;

	/**
	 * Creates a new strategy with the argument that has to be added to the working set manager as the last element.
	 *
	 * @param toAdd
	 *            the working set to add.
	 */
	public AddWorkingSetModificationStrategy(final IWorkingSet toAdd) {
		this(toAdd, APPEND_TO_END_INDEX, APPEND_TO_END_INDEX);
	}

	/**
	 * Creates a new strategy with the argument that has to be added to the working set manager into the specified all
	 * and visible item index.
	 *
	 * @param toAdd
	 *            the working set to add.
	 * @param newItemsIndex
	 *            the index where the new working set has to be added to the visible items.
	 * @param newAllItemsIndex
	 *            the index where the new working set has to be added to the all items.
	 */
	public AddWorkingSetModificationStrategy(final IWorkingSet toAdd, final int newItemsIndex,
			final int newAllItemsIndex) {
		this.toAdd = toAdd;
		this.newItemsIndex = newItemsIndex;
		this.newAllItemsIndex = newAllItemsIndex;
	}

	@Override
	public void execute(final WorkingSetManager manager) {
		if (toAdd != null && RESOURCE_WORKING_SET_ID.equals(toAdd.getId())) {

			final String name = toAdd.getName();
			// Working set with the given name already exists, nothing to do.
			if (getWorkingSetByName(manager, name) != null) {
				return;
			}

			final IProject[] projects = Arrays2.filter(toAdd.getElements(), IProject.class);
			final List<String> projectNames = Arrays2.transform(projects, p -> p.getName());
			final WorkingSet newWorkingSet = new ManualAssociationWorkingSet(projectNames, name, manager);

			final WorkingSetDiffBuilder builder = new WorkingSetDiffBuilder(manager);
			builder.add(newWorkingSet);

			final List<WorkingSet> newItems = newArrayList(manager.getWorkingSets());
			if (newItemsIndex == APPEND_TO_END_INDEX) {
				newItems.add(newWorkingSet);
			} else if (newItemsIndex >= 0) {
				newItems.add(newItemsIndex, newWorkingSet);
			}

			final List<WorkingSet> newAllItems = newArrayList(manager.getAllWorkingSets());
			if (newAllItemsIndex == APPEND_TO_END_INDEX) {
				newAllItems.add(newWorkingSet);
			} else if (newAllItemsIndex >= 0) {
				newAllItems.add(newAllItemsIndex, newWorkingSet);
			}

			final Diff<WorkingSet> diff = builder.build(newItems, newAllItems);
			updateAndSaveState(manager, diff);
		}
	}

}
