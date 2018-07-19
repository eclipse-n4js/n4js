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
package org.eclipse.n4js.internal;

import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.IN4JSSourceContainerAware;

/**
 * Convenience enum over {@link IN4JSSourceContainerAware} type hierarchy. Allows to pass concrete literals, exposes
 * helper functions (basically instance of calls)
 */
public enum N4JSSourceContainerType {
	/** Represents the {@link IN4JSProject N4JS project} type. */
	PROJECT {
		@Override
		public boolean isTypeOf(IN4JSSourceContainerAware container) {
			return container instanceof IN4JSProject;
		}
	},
	/** Represents the {@link IN4JSSourceContainerAware N4JS source container} type. This is the default case. */
	SOURCECONTAINER {
		@Override
		public boolean isTypeOf(IN4JSSourceContainerAware container) {
			return true;
		}
	};

	/**
	 * Convenience method to check if a given container is described by {@link N4JSSourceContainerType}.
	 *
	 * @param container
	 *            to be tested for a given type
	 * @return true if provided container is of type for the given project type
	 */
	public abstract boolean isTypeOf(IN4JSSourceContainerAware container);

	/**
	 * Convenience method that returns proper {@link N4JSSourceContainerType} for given container. Allows to replace
	 * IF-ELSEIF-ELSE cascades calling {@code instanceof} by switch case code on {@link N4JSSourceContainerType}.
	 *
	 * @param container
	 *            which type is checked
	 * @return {@link N4JSSourceContainerType} of given container
	 */
	public N4JSSourceContainerType getContaienrType(IN4JSSourceContainerAware container) {
		if (PROJECT.isTypeOf(container)) {
			return PROJECT;
		} else {
			return SOURCECONTAINER;
		}
	}
}
