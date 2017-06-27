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

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Base working set implementation.
 *
 * <p>
 * Has its own {@link #equals(Object)} and {@link #hashCode()} implementation based on the {@link #getId() its unique
 * identifier} and the unique {@link WorkingSetManager#getId() ID} of the container {@link #getWorkingSetManager()
 * manager}.
 */
public abstract class WorkingSetImpl implements WorkingSet {

	private final String id;
	private final WorkingSetManager manager;

	/**
	 * Creates a new working set manager with the given ID and the container manager.
	 *
	 * @param id
	 *            the unique ID of the working set.
	 * @param manager
	 *            the container manager where this working set belongs to.
	 */
	protected WorkingSetImpl(final String id, final WorkingSetManager manager) {
		this.id = checkNotNull(id, "id");
		this.manager = checkNotNull(manager, "manager");
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public WorkingSetManager getWorkingSetManager() {
		return manager;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((manager.getId() == null) ? 0 : manager.getId().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof WorkingSetImpl)) {
			return false;
		}
		WorkingSetImpl other = (WorkingSetImpl) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (manager.getId() == null) {
			if (other.manager.getId() != null) {
				return false;
			}
		} else if (!manager.getId().equals(other.manager.getId())) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return getName();
	}

}
