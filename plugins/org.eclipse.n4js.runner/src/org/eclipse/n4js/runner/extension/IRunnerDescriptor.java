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
package org.eclipse.n4js.runner.extension;

import org.eclipse.n4js.runner.IRunner;

/**
 *
 * Runner description interface. CLients registering to runner extension point need to use this descriptor.
 */
public interface IRunnerDescriptor {

	/**
	 * Returns the unique identifier of this runner.
	 */
	public String getId();

	/**
	 * Returns the human-readable name of this runner.
	 */
	public String getName();

	/**
	 * Returns the runtime environment supported by this runner.
	 */
	public RuntimeEnvironment getEnvironment();

	/**
	 * Returns the actual runner.
	 */
	public IRunner getRunner();
}
