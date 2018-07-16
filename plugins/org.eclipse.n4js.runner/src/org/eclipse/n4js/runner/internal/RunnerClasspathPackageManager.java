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
package org.eclipse.n4js.runner.internal;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.internal.ClasspathPackageManager;

/**
 * Stub of the {@link ClasspathPackageManager}.
 */
class RunnerClasspathPackageManager extends ClasspathPackageManager {

	RunnerClasspathPackageManager() {
		super(null);
	}

	@Override
	public URI getLocation(String projectId) {
		return null;
	}

}
