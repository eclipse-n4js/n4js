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

import java.util.List;
import java.util.Set;
import java.util.function.Function;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.resource.impl.ProjectDescription;
import org.eclipse.xtext.workspace.IWorkspaceConfig;

/**
 * Extension of {@link IWorkspaceConfig} to modify workspace during operation and to support snapshots.
 */
@SuppressWarnings("restriction")
public interface XIWorkspaceConfig extends IWorkspaceConfig {

	@Override
	Set<? extends XIProjectConfig> getProjects();

	@Override
	XIProjectConfig findProjectContaining(URI member);

	@Override
	XIProjectConfig findProjectByName(String name);

	/** @return base directory of workspace */
	URI getPath();

	/** Updates internal data based on changes of the given resource */
	WorkspaceChanges update(List<URI> changedResources, Function<String, ProjectDescription> pdProvider);

	/** Returns a snapshot of the current state of the workspace represented by this {@link XIWorkspaceConfig}. */
	WorkspaceConfigSnapshot toSnapshot();
}
