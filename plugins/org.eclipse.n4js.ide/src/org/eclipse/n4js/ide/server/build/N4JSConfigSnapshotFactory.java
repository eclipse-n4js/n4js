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

import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.internal.lsp.N4JSProjectConfig;
import org.eclipse.n4js.internal.lsp.N4JSProjectConfigSnapshot;
import org.eclipse.n4js.internal.lsp.N4JSSourceFolderForPackageJson;
import org.eclipse.n4js.internal.lsp.N4JSSourceFolderSnapshot;
import org.eclipse.n4js.internal.lsp.N4JSSourceFolderSnapshotForPackageJson;
import org.eclipse.n4js.internal.lsp.N4JSWorkspaceConfigSnapshot;
import org.eclipse.n4js.projectModel.lsp.IN4JSSourceFolder;
import org.eclipse.n4js.xtext.workspace.BuildOrderInfo;
import org.eclipse.n4js.xtext.workspace.ConfigSnapshotFactory;
import org.eclipse.n4js.xtext.workspace.ProjectSet;
import org.eclipse.n4js.xtext.workspace.XIProjectConfig;
import org.eclipse.xtext.workspace.ISourceFolder;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

/**
 * Overrides defaults to care for N4JS specific implementations
 */
@SuppressWarnings("restriction")
public class N4JSConfigSnapshotFactory extends ConfigSnapshotFactory {

	@Override
	public N4JSWorkspaceConfigSnapshot createWorkspaceConfigSnapshot(URI path, ProjectSet projects,
			BuildOrderInfo buildOrderInfo) {

		return new N4JSWorkspaceConfigSnapshot(path, projects, buildOrderInfo);
	}

	@Override
	public N4JSProjectConfigSnapshot createProjectConfigSnapshot(XIProjectConfig projectConfig) {
		N4JSProjectConfig projectConfigCasted = (N4JSProjectConfig) projectConfig;

		List<String> sortedDependencies = Lists.transform(projectConfigCasted.getSortedDependencies(),
				N4JSProjectConfig::getName);

		return new N4JSProjectConfigSnapshot(
				projectConfigCasted.getProjectDescription(),
				projectConfig.getPath(),
				projectConfig.indexOnly(),
				projectConfig.isGeneratorEnabled(),
				projectConfig.getDependencies(),
				sortedDependencies,
				Iterables.transform(projectConfig.getSourceFolders(), this::createSourceFolderSnapshot));
	}

	@Override
	public N4JSSourceFolderSnapshot createSourceFolderSnapshot(ISourceFolder sourceFolder) {
		if (sourceFolder instanceof N4JSSourceFolderForPackageJson) {
			return new N4JSSourceFolderSnapshotForPackageJson((N4JSSourceFolderForPackageJson) sourceFolder);
		}
		IN4JSSourceFolder sourceFolderCasted = (IN4JSSourceFolder) sourceFolder;
		return new N4JSSourceFolderSnapshot(sourceFolder.getName(), sourceFolder.getPath(),
				sourceFolderCasted.getType(), sourceFolderCasted.getRelativePath());
	}
}
