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

import org.eclipse.n4js.runner.extension.IRunnableDescriptor;
import org.eclipse.n4js.tester.ITester;

/**
 * Describes a tester registered via the 'testers' extension point (UI case) or directly via
 */
public interface ITesterDescriptor extends IRunnableDescriptor {

	/**
	 * Returns the actual tester.
	 */
	public ITester getTester();
}
