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

import java.util.Collections;

import org.eclipse.n4js.xtext.server.build.ConcurrentIndex;
import org.eclipse.n4js.xtext.workspace.ConfigSnapshotFactory;
import org.eclipse.n4js.xtext.workspace.WorkspaceConfigSnapshot;
import org.eclipse.n4js.xtext.workspace.XIWorkspaceConfig;
import org.eclipse.xtext.ide.server.IWorkspaceConfigFactory;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;

import com.google.inject.Inject;

/**
 * A {@link ConcurrentIndex} that does not rely on the LSP builder. Will instead always return a mocked workspace
 * configuration snapshot created by {@link MockWorkspaceConfig} and an empty {@link ResourceDescriptionsData} for the
 * mocked project.
 */
public class MockConcurrentIndex extends ConcurrentIndex {

	@Inject
	private IWorkspaceConfigFactory workspaceConfigFactory;
	@Inject
	private ConfigSnapshotFactory configSnapshotFactory;

	private boolean initialized = false;

	@Override
	public synchronized WorkspaceConfigSnapshot getWorkspaceConfigSnapshot() {
		if (!initialized) {
			initialized = true;
			doInitializeWithMockedWorkspace();
		}
		return super.getWorkspaceConfigSnapshot();
	}

	protected synchronized void doInitializeWithMockedWorkspace() {
		XIWorkspaceConfig workspaceConfig = (XIWorkspaceConfig) workspaceConfigFactory.getWorkspaceConfig(
				MockWorkspaceConfig.TEST_WORKSPACE__BASE_URI);
		WorkspaceConfigSnapshot workspaceConfigSnapshot = configSnapshotFactory.createWorkspaceConfigSnapshot(
				workspaceConfig);
		super.initialize(workspaceConfigSnapshot);
		ResourceDescriptionsData emptyIndex = new ResourceDescriptionsData(Collections.emptyList());
		super.setProjectIndex(MockWorkspaceConfig.TEST_PROJECT__PROJECT_NAME, emptyIndex);
	}
}
