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

import org.eclipse.ui.IWorkingSet;

import com.google.common.base.Predicate;

/**
 * Representation of a strategy that encapsulates any arbitrary {@link WorkingSetManager working set manager}
 * modifications.
 */
public interface WorkingSetManagerModificationStrategy {

	/**
	 * Unique Eclipse based {@link IWorkingSet working set} identifier of all resource based working sets.
	 */
	String RESOURCE_WORKING_SET_ID = "org.eclipse.ui.resourceWorkingSetPage";

	/**
	 * Predicate providing {@code true} result for only resource based working sets. More formally when the
	 * {@link IWorkingSet#getId() unique identifier} of the working set equals with {@value #RESOURCE_WORKING_SET_ID}.
	 */
	Predicate<IWorkingSet> RESOURCE_WORKING_SETS = ws -> RESOURCE_WORKING_SET_ID.equals(ws.getId());

	/**
	 * The NOOP strategy. Does nothing at all.
	 */
	WorkingSetManagerModificationStrategy NOOP = new WorkingSetManagerModificationStrategy() {

		@Override
		public void execute(final WorkingSetManager manager) {
			// Does nothing.
		}

	};

	/**
	 * Performs the actual modification on the working set manager argument.
	 *
	 * @param manager
	 *            the manager to modify by executing the modification strategy.
	 */
	void execute(final WorkingSetManager manager);

}
