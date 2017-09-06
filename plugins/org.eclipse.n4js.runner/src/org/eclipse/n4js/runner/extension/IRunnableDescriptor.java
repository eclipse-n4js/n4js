/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.runner.extension;

/**
 * Runnable description interface. CLients registering to runnables to the extension points can use this descriptor or
 * its specializations.
 */
public interface IRunnableDescriptor {

	/**
	 * Returns the unique identifier of this runnable.
	 */
	public String getId();

	/**
	 * Returns the human-readable name of this runnable.
	 */
	public String getName();

	/**
	 * Returns the runtime environment supported by this runnable.
	 */
	public RuntimeEnvironment getEnvironment();

}
