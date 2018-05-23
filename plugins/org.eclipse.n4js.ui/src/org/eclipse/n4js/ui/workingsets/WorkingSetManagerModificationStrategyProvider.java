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

import static org.eclipse.n4js.ui.workingsets.WorkingSetManagerModificationStrategy.NOOP;
import static org.eclipse.ui.IWorkingSetManager.CHANGE_WORKING_SET_ADD;
import static org.eclipse.ui.IWorkingSetManager.CHANGE_WORKING_SET_CONTENT_CHANGE;
import static org.eclipse.ui.IWorkingSetManager.CHANGE_WORKING_SET_LABEL_CHANGE;
import static org.eclipse.ui.IWorkingSetManager.CHANGE_WORKING_SET_NAME_CHANGE;
import static org.eclipse.ui.IWorkingSetManager.CHANGE_WORKING_SET_REMOVE;

import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.IWorkingSet;

/**
 * Provides different {@link WorkingSetManagerModificationStrategy working set manager modification strategy}
 * implementations to perform any actual working set manager modifications.
 */
public class WorkingSetManagerModificationStrategyProvider {

	/**
	 * Returns with the strategy for the given property change even argument,
	 *
	 * @param event
	 *            a property change argument.
	 * @return a strategy encapsulating an actual working set manager modification.
	 */
	public WorkingSetManagerModificationStrategy getStrategy(final PropertyChangeEvent event) {

		if (event == null) {
			return NOOP;
		}

		final String property = event.getProperty();
		switch (property) {
		case CHANGE_WORKING_SET_ADD:
			return new AddWorkingSetModificationStrategy((IWorkingSet) event.getNewValue());
		case CHANGE_WORKING_SET_REMOVE:
			return new RemoveWorkingSetModificationStrategy((IWorkingSet) event.getOldValue());
		case CHANGE_WORKING_SET_LABEL_CHANGE: //$FALL-THROUGH$
		case CHANGE_WORKING_SET_NAME_CHANGE: //$FALL-THROUGH$
		case CHANGE_WORKING_SET_CONTENT_CHANGE:
			return new UpdateWorkingSetModificationStraregy(
					(IWorkingSet) event.getOldValue(),
					(IWorkingSet) event.getNewValue());
		default:
			return NOOP;
		}
	}

}
