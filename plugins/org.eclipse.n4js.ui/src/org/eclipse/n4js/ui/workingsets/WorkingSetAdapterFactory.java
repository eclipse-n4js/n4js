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

import org.eclipse.core.runtime.IAdapterFactory;

/**
 * Factory for adapting N4JS {@link WorkingSet working set} instances.
 */
public class WorkingSetAdapterFactory implements IAdapterFactory {

	private static final Class<?>[] ADAPTED_CLASSES = new Class<?>[] { WorkingSet.class };

	@Override
	public <T> T getAdapter(final Object adaptableObject, final Class<T> adapterType) {

		if (adaptableObject instanceof WorkingSet) {
			return ((WorkingSet) adaptableObject).getAdapter(adapterType);
		}

		return null;
	}

	@Override
	public Class<?>[] getAdapterList() {
		return ADAPTED_CLASSES;
	}

}
