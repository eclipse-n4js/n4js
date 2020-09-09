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

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.ide.xtext.server.build.ProjectBuilder;
import org.eclipse.n4js.ide.xtext.server.build.XWorkspaceManager;
import org.eclipse.n4js.internal.MultiCleartriggerCache;
import org.eclipse.n4js.ts.scoping.builtin.N4Scheme;
import org.eclipse.n4js.xtext.workspace.WorkspaceChanges;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Customized to support lookup of resource sets per n4js uri scheme.
 */
@Singleton
public class N4JSWorkspaceManager extends XWorkspaceManager {

	@Inject
	private MultiCleartriggerCache multiCleartriggerCache;

	/**
	 * @param uri
	 *            the contained uri
	 * @return the project manager.
	 */
	@Override
	public ProjectBuilder getProjectBuilder(URI uri) {
		if (N4Scheme.isN4Scheme(uri)) {
			return anyProject();
		}
		return super.getProjectBuilder(uri);
	}

	private ProjectBuilder anyProject() {
		Collection<ProjectBuilder> allProjectBuilders = getProjectBuilders();
		if (!allProjectBuilders.isEmpty()) {
			ProjectBuilder anyProjectBuilder = allProjectBuilders.iterator().next();
			return anyProjectBuilder;
		}
		return null;
	}

	@Override
	public WorkspaceChanges update(List<URI> dirtyFiles, List<URI> deletedFiles) {
		WorkspaceChanges changes = super.update(dirtyFiles, deletedFiles);

		if (!changes.getAddedProjects().isEmpty() || !changes.getRemovedProjects().isEmpty()) {
			// since the list of dependencies cached in instances of ProjectDescriptions is based on
			// IN4JSProject#getSortedDependencies() (see N4JSProjectDescriptionFactory) and therefore does not contain
			// names of projects that do not exist (in case of unresolved dependencies), we have to recompute all those
			// dependency list in the ProjectDescription instances of all existing project builders whenever a project
			// is being added or removed:
			multiCleartriggerCache.clear(MultiCleartriggerCache.CACHE_KEY_SORTED_DEPENDENCIES);
			for (ProjectBuilder pb : getProjectBuilders()) {
				pb.onDependenciesChanged();
			}
		}

		return changes;
	}
}
