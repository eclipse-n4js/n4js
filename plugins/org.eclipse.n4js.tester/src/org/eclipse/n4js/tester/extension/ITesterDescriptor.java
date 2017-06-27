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
package org.eclipse.n4js.tester.extension;

import org.eclipse.n4js.runner.extension.RuntimeEnvironment;
import org.eclipse.n4js.tester.ITester;

/**
 * Describes a tester registered via the 'testers' extension point (UI case) or directly via
 */
public interface ITesterDescriptor {

	/**
	 * Returns the unique identifier of this tester.
	 */
	public String getId();

	/**
	 * Returns the human-readable name of this tester.
	 */
	public String getName();

	/**
	 * Returns the runtime environment supported by this tester.
	 */
	public RuntimeEnvironment getEnvironment();

	/**
	 * Returns the actual tester.
	 */
	public ITester getTester();
}
