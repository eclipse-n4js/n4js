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

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.ide.server.N4JSWorkspaceConfigFactory;
import org.eclipse.n4js.ide.server.build.N4JSConfigSnapshotFactory;
import org.eclipse.n4js.internal.lsp.N4JSWorkspaceConfig;
import org.eclipse.n4js.utils.ProjectDescriptionLoader;
import org.eclipse.n4js.utils.ProjectDiscoveryHelper;
import org.eclipse.xtext.util.UriExtensions;

import com.google.inject.Inject;

/**
 *
 */
public class MockWorkspaceConfigFactory extends N4JSWorkspaceConfigFactory {

	@Inject
	private ProjectDiscoveryHelper projectDiscoveryHelper;

	@Inject
	private ProjectDescriptionLoader projectDescriptionLoader;

	@Inject
	private N4JSConfigSnapshotFactory configSnapshotFactory;

	@Inject
	private UriExtensions uriExtensions;

	@Override
	protected N4JSWorkspaceConfig doCreateWorkspaceConfig(URI workspaceBaseURI) {
		return new MockWorkspaceConfig(workspaceBaseURI, projectDiscoveryHelper, projectDescriptionLoader,
				configSnapshotFactory, uriExtensions);
	}
}
