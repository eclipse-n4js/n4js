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

/**
 * Interface for objects which require a deferred initialization. The container holding objects marked with this
 * interface has to schedule calls to {@link #lateInit()} at later points in time.
 *
 * This should give the platform an additional cycle to set up all required components.
 */
public interface IDeferredInitializer {

	/**
	 * Query the need for initialization.
	 *
	 * @return true if initialization did not succeed yet.
	 */
	public boolean isInitializationRequired();

	/**
	 * Trigger the deferred initialization.
	 *
	 * @returns true in case of successful initialization.
	 */
	public boolean lateInit();

}
