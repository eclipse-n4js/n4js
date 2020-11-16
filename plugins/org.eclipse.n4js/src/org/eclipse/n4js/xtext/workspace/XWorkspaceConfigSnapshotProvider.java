/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.xtext.workspace;

import java.util.Collections;

import org.eclipse.n4js.xtext.workspace.XWorkspaceConfigSnapshotProvider.NullXWorkspaceConfigSnapshotProvider;

import com.google.inject.ImplementedBy;
import com.google.inject.Inject;

/**
 * Provider for {@link XWorkspaceConfigSnapshotProvider}s
 */
@ImplementedBy(NullXWorkspaceConfigSnapshotProvider.class)
public interface XWorkspaceConfigSnapshotProvider {

	/** @return the {@link WorkspaceConfigSnapshot} of the current build */
	WorkspaceConfigSnapshot getWorkspaceConfigSnapshot();

	/**
	 * This provider returns empty {@link XWorkspaceConfigSnapshotProvider}s
	 *
	 * Note that this implementation may be removed when the N4JS Eclipse product is removed.
	 */
	class NullXWorkspaceConfigSnapshotProvider implements XWorkspaceConfigSnapshotProvider {
		@Inject
		ProjectBuildOrderInfo.Provider provider;

		@Override
		public WorkspaceConfigSnapshot getWorkspaceConfigSnapshot() {
			return new WorkspaceConfigSnapshot(null, Collections.emptyList(), provider);
		}
	}

}
