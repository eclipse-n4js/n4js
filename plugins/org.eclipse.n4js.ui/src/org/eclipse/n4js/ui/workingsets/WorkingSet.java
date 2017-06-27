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

import org.eclipse.core.resources.mapping.ResourceMapping;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.IWorkingSet;

/**
 * Representation of a working set. A working set holds a number of {@link IAdaptable adaptable} elements. A working set
 * is intended to group elements for presentation to the user or for operations on a set of elements. Each working set
 * must have a unique {@link #getId() ID} in the container working set manager.
 */
public interface WorkingSet extends IAdaptable {

	/**
	 * The reserved ID of the default built-in working set.
	 * <p>
	 * ID: {@value}
	 */
	String OTHERS_WORKING_SET_ID = "Other Projects";

	/**
	 * Returns with the unique ID of the working set. Each working set must have its unique identifier in the same
	 * container {@link WorkingSetManager manager}.
	 *
	 * @return the unique ID of the working set.
	 */
	String getId();

	/**
	 * Returns with the name of the working set.
	 *
	 * <p>
	 * By default returns with the {@link #getId() unique identifier} of the working set.
	 *
	 * @return the human readable name of the working set.
	 */
	default String getName() {
		return getId();
	}

	/**
	 * The container manager of the working set.
	 *
	 * @return the container working set manager.
	 */
	WorkingSetManager getWorkingSetManager();

	/**
	 * Returns the elements that are contained in this working set.
	 *
	 * @return the working set elements.
	 */
	IAdaptable[] getElements();

	@SuppressWarnings("unchecked")
	@Override
	default <T> T getAdapter(final Class<T> adapter) {

		if (IWorkingSet.class == adapter || ResourceMapping.class == adapter) {
			return (T) new WorkingSetAdapter(this);
		}

		return Platform.getAdapterManager().getAdapter(this, adapter);
	}

}
