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

import java.io.File;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.internal.LazyProjectDescriptionHandle;
import org.eclipse.n4js.n4mf.ProjectDescription;
import org.eclipse.n4js.n4mf.utils.parsing.ProjectDescriptionProviderUtil;

/**
 * {@link LazyProjectDescriptionHandle} with custom manifest loading.
 */
class RunnerLazyProjectDescriptionHandle extends LazyProjectDescriptionHandle {

	RunnerLazyProjectDescriptionHandle(URI location, boolean archive) {
		super(location, archive, null);
	}

	@Override
	protected ProjectDescription loadManifest(URI manifest) {
		File manifestFile = new File(manifest.toFileString());
		return ProjectDescriptionProviderUtil.getFromFile(manifestFile);
	}
}
