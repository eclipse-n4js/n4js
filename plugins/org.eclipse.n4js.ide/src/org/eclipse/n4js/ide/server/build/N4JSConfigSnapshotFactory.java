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
import org.eclipse.n4js.internal.lsp.N4JSProjectConfig.SourceContainerForPackageJson;
import org.eclipse.n4js.internal.lsp.N4JSProjectConfigSnapshot;
import org.eclipse.n4js.internal.lsp.N4JSSourceFolderSnapshot;
import org.eclipse.n4js.internal.lsp.N4JSSourceFolderSnapshotForPackageJson;
import org.eclipse.n4js.internal.lsp.N4JSWorkspaceConfigSnapshot;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.xtext.workspace.BuildOrderInfo;
import org.eclipse.n4js.xtext.workspace.ConfigSnapshotFactory;
import org.eclipse.n4js.xtext.workspace.ProjectConfigSnapshot;
import org.eclipse.n4js.xtext.workspace.XIProjectConfig;
import org.eclipse.xtext.workspace.ISourceFolder;

import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

/**
 * Overrides defaults to care for N4JS specific implementations
 */
@SuppressWarnings("restriction")
public class N4JSConfigSnapshotFactory extends ConfigSnapshotFactory {

	@Override
	public N4JSWorkspaceConfigSnapshot createWorkspaceConfigSnapshot(URI path,
			ImmutableBiMap<String, ? extends ProjectConfigSnapshot> name2Project,
			ImmutableMap<URI, ? extends ProjectConfigSnapshot> projectPath2Project,
			ImmutableMap<URI, ? extends ProjectConfigSnapshot> sourceFolderPath2Project,
			BuildOrderInfo buildOrderInfo) {

		@SuppressWarnings("unchecked")
		ImmutableBiMap<String, N4JSProjectConfigSnapshot> name2ProjectCasted = (ImmutableBiMap<String, N4JSProjectConfigSnapshot>) name2Project;
		@SuppressWarnings("unchecked")
		ImmutableMap<URI, N4JSProjectConfigSnapshot> projectPath2ProjectCasted = (ImmutableMap<URI, N4JSProjectConfigSnapshot>) projectPath2Project;
		@SuppressWarnings("unchecked")
		ImmutableMap<URI, N4JSProjectConfigSnapshot> sourceFolderPath2ProjectCasted = (ImmutableMap<URI, N4JSProjectConfigSnapshot>) sourceFolderPath2Project;

		return new N4JSWorkspaceConfigSnapshot(path,
				ImmutableBiMap.copyOf(name2ProjectCasted),
				ImmutableMap.copyOf(projectPath2ProjectCasted),
				ImmutableMap.copyOf(sourceFolderPath2ProjectCasted),
				buildOrderInfo);
	}

	@Override
	public N4JSProjectConfigSnapshot createProjectConfigSnapshot(XIProjectConfig projectConfig) {
		N4JSProjectConfig projectConfigCasted = (N4JSProjectConfig) projectConfig;
		IN4JSProject project = projectConfigCasted.toProject();

		List<String> sortedDependencies = Lists.transform(project.getSortedDependencies(),
				p -> p.getProjectName().getRawName());

		return new N4JSProjectConfigSnapshot(
				project.getProjectDescription(),
				projectConfig.getPath(),
				projectConfig.indexOnly(),
				projectConfig.isGeneratorEnabled(),
				projectConfig.getDependencies(),
				sortedDependencies,
				Iterables.transform(projectConfig.getSourceFolders(), this::createSourceFolderSnapshot));
	}

	@Override
	public N4JSSourceFolderSnapshot createSourceFolderSnapshot(ISourceFolder sourceFolder) {
		if (sourceFolder instanceof SourceContainerForPackageJson) {
			return new N4JSSourceFolderSnapshotForPackageJson((SourceContainerForPackageJson) sourceFolder);
		}
		return new N4JSSourceFolderSnapshot(sourceFolder.getName(), sourceFolder.getPath());
	}
}
