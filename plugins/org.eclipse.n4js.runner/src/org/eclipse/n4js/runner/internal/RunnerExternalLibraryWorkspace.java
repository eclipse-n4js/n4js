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
package org.eclipse.n4js.runner.internal;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.internal.ClasspathPackageManager;
import org.eclipse.n4js.internal.FileBasedWorkspace;
import org.eclipse.n4js.internal.LazyProjectDescriptionHandle;

/**
 * {@link FileBasedWorkspace} with custom creation of project handles.
 */
class RunnerExternalLibraryWorkspace extends FileBasedWorkspace {

	RunnerExternalLibraryWorkspace(ClasspathPackageManager packageManager) {
		super(null, packageManager);
	}

	@Override
	protected LazyProjectDescriptionHandle createLazyDescriptionHandle(URI location, boolean archive) {
		return new RunnerLazyProjectDescriptionHandle(location, archive);
	}

}
