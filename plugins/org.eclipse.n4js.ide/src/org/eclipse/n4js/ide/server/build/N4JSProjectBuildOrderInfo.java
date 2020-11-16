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
package org.eclipse.n4js.ide.server.build;

import java.util.Collections;
import java.util.Set;

import org.eclipse.n4js.internal.lsp.N4JSProjectConfigSnapshot;
import org.eclipse.n4js.projectDescription.ProjectType;
import org.eclipse.n4js.xtext.workspace.ProjectBuildOrderInfo;
import org.eclipse.n4js.xtext.workspace.ProjectConfigSnapshot;
import org.eclipse.n4js.xtext.workspace.WorkspaceConfigSnapshot;

/**
 * Customized in order to ignore dependencies of {@link ProjectType#PLAINJS plain-JS} projects.
 */
public class N4JSProjectBuildOrderInfo extends ProjectBuildOrderInfo {

	/** Provides instances of {@link #N4JSProjectBuildOrderInfo()} */
	public class Provider extends ProjectBuildOrderInfo.Provider {
		@Override
		public ProjectBuildOrderInfo getProjectBuildOrderInfo(WorkspaceConfigSnapshot pWorkspaceConfig) {
			return new N4JSProjectBuildOrderInfo(pWorkspaceConfig);
		}
	}

	/** Constructor */
	public N4JSProjectBuildOrderInfo(WorkspaceConfigSnapshot pWorkspaceConfig) {
		super(pWorkspaceConfig);
	}

	@Override
	protected Set<String> getDependencies(ProjectConfigSnapshot pc) {
		ProjectType type = pc instanceof N4JSProjectConfigSnapshot ? ((N4JSProjectConfigSnapshot) pc).getType() : null;
		if (type == ProjectType.PLAINJS) {
			// ignore dependencies of plain-JS projects, because
			// (1) they are irrelevant for the build order of N4JS code,
			// (2) npm packages sometimes declare cyclic dependencies (and we must not show errors for those cycles)
			return Collections.emptySet();
		}
		return super.getDependencies(pc);
	}
}
