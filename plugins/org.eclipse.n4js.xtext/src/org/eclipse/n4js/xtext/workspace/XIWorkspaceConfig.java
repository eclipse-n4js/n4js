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

import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.workspace.IWorkspaceConfig;

/**
 * Extension of {@link IWorkspaceConfig} to modify workspace during operation and to support snapshots.
 */
@SuppressWarnings("restriction")
public interface XIWorkspaceConfig extends IWorkspaceConfig {

	/** @return base directory of workspace */
	URI getPath();

	@Override
	Set<? extends XIProjectConfig> getProjects();

	@Override
	XIProjectConfig findProjectByName(String name);

	/**
	 * No longer supported; will throw {@link UnsupportedOperationException}.
	 *
	 * @deprecated for obtaining a project by nested URI, use either of the methods
	 *             {@link WorkspaceConfigSnapshot#findProjectByNestedLocation(URI) #findProjectByNestedLocation(URI)} or
	 *             {@link WorkspaceConfigSnapshot#findProjectContaining(URI) #findProjectContaining(URI)} in
	 *             {@link WorkspaceConfigSnapshot}, instead of this method. In particular, the new
	 *             {@code XWorkspaceManager} now follows this rule.
	 */
	@Override
	@Deprecated
	XIProjectConfig findProjectContaining(URI nestedURI);

	/**
	 * Updates internal data based on changes of the given resources.
	 *
	 * @param refresh
	 *            iff <code>true</code>, will perform an entire re-computation of the workspace state in order to also
	 *            find changes for which no file-change-events were received.
	 */
	WorkspaceChanges update(WorkspaceConfigSnapshot oldWorkspaceConfig, Set<URI> dirtyFiles, Set<URI> deletedFiles,
			boolean refresh);

}
