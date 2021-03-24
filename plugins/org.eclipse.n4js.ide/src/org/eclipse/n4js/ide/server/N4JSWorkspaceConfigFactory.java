/**
 * Copyright (c) 2019 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.server;

import java.util.Collections;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.ide.server.build.N4JSConfigSnapshotFactory;
import org.eclipse.n4js.utils.ProjectDescriptionLoader;
import org.eclipse.n4js.utils.ProjectDiscoveryHelper;
import org.eclipse.n4js.workspace.N4JSWorkspaceConfig;
import org.eclipse.n4js.xtext.ide.server.XIWorkspaceConfigFactory;
import org.eclipse.n4js.xtext.workspace.WorkspaceConfigSnapshot;
import org.eclipse.n4js.xtext.workspace.XIWorkspaceConfig;
import org.eclipse.xtext.util.UriExtensions;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * N4JS-specific implementation of {@link XIWorkspaceConfigFactory}.
 */
@Singleton
public class N4JSWorkspaceConfigFactory implements XIWorkspaceConfigFactory {

	@Inject
	private ProjectDiscoveryHelper projectDiscoveryHelper;

	@Inject
	private ProjectDescriptionLoader projectDescriptionLoader;

	@Inject
	private N4JSConfigSnapshotFactory configSnapshotFactory;

	@Inject
	private UriExtensions uriExtensions;

	@Override
	public XIWorkspaceConfig createWorkspaceConfig(URI workspaceBaseURI) {
		N4JSWorkspaceConfig result = doCreateWorkspaceConfig(workspaceBaseURI);
		WorkspaceConfigSnapshot emptyWC = configSnapshotFactory.createWorkspaceConfigSnapshot(workspaceBaseURI);
		result.update(emptyWC, Collections.emptySet(), Collections.emptySet(), true);
		return result;
	}

	/** Actually create a new instance of {@link N4JSWorkspaceConfig}. */
	protected N4JSWorkspaceConfig doCreateWorkspaceConfig(URI workspaceBaseURI) {
		return new N4JSWorkspaceConfig(workspaceBaseURI, projectDiscoveryHelper, projectDescriptionLoader,
				configSnapshotFactory, uriExtensions);
	}
}
