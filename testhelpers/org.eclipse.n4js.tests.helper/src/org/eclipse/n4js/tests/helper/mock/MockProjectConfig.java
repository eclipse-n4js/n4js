/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.tests.helper.mock;

import org.eclipse.n4js.internal.lsp.N4JSProjectConfig;
import org.eclipse.n4js.projectDescription.ProjectDescription;
import org.eclipse.n4js.projectModel.locations.FileURI;
import org.eclipse.n4js.utils.ProjectDescriptionLoader;

/**
 * An {@link N4JSProjectConfig} that does not access the file system.
 */
public class MockProjectConfig extends N4JSProjectConfig {

	/** Creates a new {@link MockProjectConfig}. */
	public MockProjectConfig(MockWorkspaceConfig workspace, FileURI path, ProjectDescription pd,
			ProjectDescriptionLoader projectDescriptionLoader) {
		super(workspace, path, pd, projectDescriptionLoader);
	}

	@Override
	protected void readProjectStateFromDisk() {
		// disable disk access, because this test project does not exist on disk
	}
}
