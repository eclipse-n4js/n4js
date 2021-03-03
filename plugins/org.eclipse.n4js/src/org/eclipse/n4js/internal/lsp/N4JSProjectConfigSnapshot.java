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
package org.eclipse.n4js.internal.lsp;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.packagejson.PackageJsonProperties;
import org.eclipse.n4js.projectDescription.ProjectDescription;
import org.eclipse.n4js.projectDescription.ProjectType;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;
import org.eclipse.n4js.xtext.workspace.ProjectConfigSnapshot;
import org.eclipse.n4js.xtext.workspace.SourceFolderSnapshot;
import org.eclipse.n4js.xtext.workspace.WorkspaceChanges;
import org.eclipse.n4js.xtext.workspace.WorkspaceConfigSnapshot;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

/**
 * Extends Xtext's default {@link ProjectConfigSnapshot} by some additional attributes (e.g. project type).
 */
public class N4JSProjectConfigSnapshot extends ProjectConfigSnapshot {

	private final ProjectDescription projectDescription;
	private final ImmutableList<String> sortedDependencies;

	/** Creates a new {@link N4JSProjectConfigSnapshot}. */
	public N4JSProjectConfigSnapshot(ProjectDescription projectDescription, URI path, boolean indexOnly,
			boolean generatorEnabled, Iterable<String> dependencies, Iterable<String> sortedDependencies,
			Iterable<? extends SourceFolderSnapshot> sourceFolders) {

		super(projectDescription.getProjectName(), path,
				Collections.singleton(path.trimSegments(1).appendSegment(N4JSGlobals.PACKAGE_JSON)),
				indexOnly, generatorEnabled, dependencies, sourceFolders);

		this.projectDescription = Objects.requireNonNull(projectDescription);
		this.sortedDependencies = ImmutableList.copyOf(sortedDependencies);
	}

	/** Returns the {@link ProjectDescription}. */
	public ProjectDescription getProjectDescription() {
		return projectDescription;
	}

	/** Returns the value of the {@link PackageJsonProperties#DEFINES_PACKAGE "definesPackage"} property. */
	public N4JSProjectName getDefinesPackage() {
		String definesPackage = projectDescription.getDefinesPackage();
		return definesPackage != null ? new N4JSProjectName(definesPackage) : null;
	}

	/**
	 * Returns the {@link IN4JSProject#getSortedDependencies() sorted dependencies}.
	 * <p>
	 * WARNING: this value depends on other projects' properties (esp. "definesPackage") so it may change whenever other
	 * projects' <code>package.json</code> files change! See
	 * {@link N4JSWorkspaceConfig#recomputeSortedDependenciesIfNecessary(WorkspaceConfigSnapshot, WorkspaceChanges)
	 * here} for details.
	 */
	public List<String> getSortedDependencies() {
		return sortedDependencies;
	}

	@Override
	protected int computeHashCode() {
		return Objects.hash(
				super.computeHashCode(),
				projectDescription,
				sortedDependencies);
	}

	@Override
	protected boolean computeEquals(Object obj) {
		N4JSProjectConfigSnapshot other = (N4JSProjectConfigSnapshot) obj;
		return super.computeEquals(other)
				&& Objects.equals(projectDescription, other.projectDescription)
				&& Objects.equals(sortedDependencies, other.sortedDependencies);
	}

	// ==============================================================================================================
	// Convenience and utility methods (do not introduce additional data)

	@Override
	public ImmutableSet<N4JSSourceFolderSnapshot> getSourceFolders() {
		@SuppressWarnings("unchecked")
		ImmutableSet<N4JSSourceFolderSnapshot> sourceFolders = (ImmutableSet<N4JSSourceFolderSnapshot>) super.getSourceFolders();
		return sourceFolders;
	}

	/** Returns the {@link ProjectType project type}. */
	public ProjectType getType() {
		return projectDescription.getProjectType();
	}
}
