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

import org.eclipse.n4js.internal.lsp.N4JSProjectConfig;
import org.eclipse.n4js.internal.lsp.N4JSProjectConfig.SourceContainerForPackageJson;
import org.eclipse.n4js.internal.lsp.N4JSProjectConfig.SourceFolderSnapshotForPackageJson;
import org.eclipse.n4js.internal.lsp.N4JSProjectConfigSnapshot;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.xtext.workspace.ConfigSnapshotFactory;
import org.eclipse.n4js.xtext.workspace.ProjectConfigSnapshot;
import org.eclipse.n4js.xtext.workspace.SourceFolderSnapshot;
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
	public ProjectConfigSnapshot createProjectConfigSnapshot(XIProjectConfig projectConfig) {
		IN4JSProject project = ((N4JSProjectConfig) projectConfig).toProject();

		List<String> sortedDependencies = Lists.transform(project.getSortedDependencies(),
				p -> p.getProjectName().getRawName());

		return new N4JSProjectConfigSnapshot(
				projectConfig.getName(),
				projectConfig.getPath(),
				project.getProjectType(),
				project.getDefinesPackageName(),
				projectConfig.indexOnly(),
				projectConfig.getDependencies(),
				sortedDependencies,
				Iterables.transform(projectConfig.getSourceFolders(), this::createSourceFolderSnapshot));
	}

	@Override
	public SourceFolderSnapshot createSourceFolderSnapshot(ISourceFolder sourceFolder) {
		if (sourceFolder instanceof SourceContainerForPackageJson) {
			return new SourceFolderSnapshotForPackageJson((SourceContainerForPackageJson) sourceFolder);
		}
		return super.createSourceFolderSnapshot(sourceFolder);
	}
}
