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

import static com.google.common.collect.FluentIterable.from;
import static java.util.Arrays.asList;

import org.eclipse.core.runtime.NullProgressMonitor;

import com.google.common.base.Strings;

import org.eclipse.n4js.utils.Diff;

/**
 * Based working set manager modification strategy. Does not declares any base modification strategy but provides
 * convenient methods for extending classes.
 */
public abstract class WorkingSetManagerModificationStrategyImpl implements WorkingSetManagerModificationStrategy {

	/**
	 * Returns with the first working set from the manager that matches with the given name predicate.
	 *
	 * @param manager
	 *            the working set manager.
	 * @param name
	 *            the desired name of the working set we are looking for.
	 * @return the working set with the given name, or {@code null} if no working sets exist with the given name.
	 */
	protected WorkingSet getWorkingSetByName(final WorkingSetManager manager, final String name) {
		if (Strings.isNullOrEmpty(name)) {
			return null;
		}
		return from(asList(manager.getAllWorkingSets())).firstMatch(ws -> name.equals(ws.getName())).orNull();
	}

	/**
	 * Updates the state of the manager argument with the based on the give {@link Diff difference}.
	 *
	 * @param manager
	 *            the manager to update.
	 * @param diff
	 *            the difference representing the working set manager modification.
	 */
	protected void updateAndSaveState(final WorkingSetManager manager, final Diff<WorkingSet> diff) {
		if (!diff.isEmpty()) {
			manager.updateState(diff);
			manager.saveState(new NullProgressMonitor());
			refreshNavigator(manager);
		}
	}

	/**
	 * Triggers a navigator refresh through the working set manager argument.
	 *
	 * @param manager
	 *            the working set manager which updated content should be revealed in navigator.
	 */
	protected void refreshNavigator(final WorkingSetManager manager) {
		manager.getWorkingSetManagerBroker().refreshNavigator();
	}

}
