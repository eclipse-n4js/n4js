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

import static org.eclipse.n4js.ui.workingsets.WorkingSetManager.EMPTY_ARRAY;
import static java.util.Collections.emptyMap;

import java.util.function.Function;

import com.google.common.collect.Iterables;

import org.eclipse.n4js.utils.Diff;
import org.eclipse.n4js.utils.DiffBuilder;

/**
 * Difference builder for {@link WorkingSetManager working set manager} instances and their {@link WorkingSet working
 * sets}.
 */
public class WorkingSetDiffBuilder extends DiffBuilder<WorkingSetManager, WorkingSet> {

	/**
	 * Constant for representing an empty difference.
	 */
	public static final Diff<WorkingSet> EMPTY_DIFF = new Diff<>(
			EMPTY_ARRAY, EMPTY_ARRAY, EMPTY_ARRAY, EMPTY_ARRAY, emptyMap(), EMPTY_ARRAY, EMPTY_ARRAY);

	/**
	 * Builds the difference based on the new items and all new items arguments. This is just a sugar for
	 * {@link #build(WorkingSet[], WorkingSet[])} method.
	 */
	public Diff<WorkingSet> build(final Iterable<? extends WorkingSet> newItems,
			final Iterable<? extends WorkingSet> newAllItems) {

		return build(Iterables.toArray(newItems, WorkingSet.class), Iterables.toArray(newAllItems, WorkingSet.class));
	}

	/**
	 * Creates a new diff builder instance with the given working set manager argument. The working set manager is used
	 * to provide the initial state of the wrapped working sets.
	 *
	 * @param workingSetManager
	 *            the working set manager for the initial state.
	 */
	public WorkingSetDiffBuilder(final WorkingSetManager workingSetManager) {
		super(workingSetManager);
	}

	@Override
	protected Function<WorkingSetManager, WorkingSet[]> getOldItemsFunction() {
		return input -> input.getWorkingSets();
	}

	@Override
	protected Function<WorkingSetManager, WorkingSet[]> getAllOldItemsFunction() {
		return input -> input.getAllWorkingSets();
	}

}
