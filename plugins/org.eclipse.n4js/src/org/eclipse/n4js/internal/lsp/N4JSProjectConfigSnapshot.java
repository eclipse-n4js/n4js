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
import org.eclipse.n4js.projectDescription.ProjectType;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;
import org.eclipse.n4js.xtext.workspace.ProjectConfigSnapshot;
import org.eclipse.n4js.xtext.workspace.SourceFolderSnapshot;
import org.eclipse.n4js.xtext.workspace.WorkspaceChanges;
import org.eclipse.n4js.xtext.workspace.WorkspaceConfigSnapshot;

import com.google.common.collect.ImmutableList;

/**
 * Extends Xtext's default {@link ProjectConfigSnapshot} by some additional attributes (e.g. project type).
 */
public class N4JSProjectConfigSnapshot extends ProjectConfigSnapshot {

	private final ProjectType type;
	private final N4JSProjectName definesPackage;
	private final ImmutableList<String> sortedDependencies;

	/** Creates a new {@link N4JSProjectConfigSnapshot}. */
	public N4JSProjectConfigSnapshot(String name, URI path,
			ProjectType type, N4JSProjectName definesPackage, boolean indexOnly,
			Iterable<String> dependencies, Iterable<String> sortedDependencies,
			Iterable<? extends SourceFolderSnapshot> sourceFolders) {

		super(name, path, indexOnly, dependencies, sourceFolders,
				Collections.singleton(path.trimSegments(1).appendSegment(N4JSGlobals.PACKAGE_JSON)));

		this.type = type;
		this.definesPackage = definesPackage;
		this.sortedDependencies = ImmutableList.copyOf(sortedDependencies);
	}

	/** Returns the {@link ProjectType project type}. */
	public ProjectType getType() {
		return type;
	}

	/** Returns the value of the {@link PackageJsonProperties#DEFINES_PACKAGE "definesPackage"} property. */
	public N4JSProjectName getDefinesPackage() {
		return definesPackage;
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
	public int hashCode() {
		return Objects.hash(
				super.hashCode(),
				type,
				definesPackage,
				sortedDependencies);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		if (!super.equals(obj))
			return false;
		N4JSProjectConfigSnapshot other = (N4JSProjectConfigSnapshot) obj;
		if (type != other.type)
			return false;
		if (!Objects.equals(definesPackage, other.definesPackage))
			return false;
		if (!Objects.equals(sortedDependencies, other.sortedDependencies))
			return false;
		return true;
	}
}
