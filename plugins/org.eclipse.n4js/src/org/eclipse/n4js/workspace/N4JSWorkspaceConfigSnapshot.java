/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.workspace;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.packagejson.projectDescription.ModuleFilterType;
import org.eclipse.n4js.workspace.utils.N4JSPackageName;
import org.eclipse.n4js.xtext.workspace.BuildOrderInfo;
import org.eclipse.n4js.xtext.workspace.ProjectConfigSnapshot;
import org.eclipse.n4js.xtext.workspace.ProjectSet;
import org.eclipse.n4js.xtext.workspace.WorkspaceConfigSnapshot;
import org.eclipse.xtext.xbase.lib.Pair;

import com.google.common.collect.ImmutableSet;

/**
 * N4JS-specific adjustments to {@link WorkspaceConfigSnapshot}.
 */
public class N4JSWorkspaceConfigSnapshot extends WorkspaceConfigSnapshot {

	/** An empty N4JS workspace configuration with a {@link #EMPTY_PATH default path}. */
	@SuppressWarnings("hiding")
	public static final N4JSWorkspaceConfigSnapshot EMPTY = new N4JSWorkspaceConfigSnapshot(EMPTY_PATH,
			ProjectSet.EMPTY, BuildOrderInfo.NULL);

	/** Maps package names to projects. In case of name collisions only one is mapped. */
	// TODO: Move to {@link ProjectSet} when development setup allows
	protected final Map<N4JSPackageName, N4JSProjectConfigSnapshot> packageName2Project;

	/** Creates a {@link N4JSWorkspaceConfigSnapshot}. */
	public N4JSWorkspaceConfigSnapshot(URI path, ProjectSet projects, BuildOrderInfo buildOrderInfo) {
		super(path, projects, buildOrderInfo);
		packageName2Project = computeMap(projects);
	}

	static private Map<N4JSPackageName, N4JSProjectConfigSnapshot> computeMap(ProjectSet projects) {
		Map<N4JSPackageName, N4JSProjectConfigSnapshot> packageName2Project = new LinkedHashMap<>();
		for (ProjectConfigSnapshot prj : projects.getProjects()) {
			N4JSProjectConfigSnapshot n4jsPCS = (N4JSProjectConfigSnapshot) prj;
			packageName2Project.put(n4jsPCS.getN4JSPackageName(), n4jsPCS);
		}
		return Collections.unmodifiableMap(packageName2Project);
	}

	/** Returns a project for package name. In case of name collisions one project is returned. */
	public N4JSProjectConfigSnapshot findProjectByPackageName(N4JSPackageName name) {
		return packageName2Project.get(name);
	}

	@Override
	protected int computeHashCode() {
		return super.computeHashCode(); // no additional data in this class, so simply use super implementation
	}

	@Override
	protected boolean computeEquals(Object obj) {
		return super.computeEquals(obj); // no additional data in this class, so simply use super implementation
	}

	// ==============================================================================================================
	// Convenience and utility methods (do not introduce additional data)

	@Override
	public ImmutableSet<N4JSProjectConfigSnapshot> getProjects() {
		@SuppressWarnings("unchecked")
		ImmutableSet<N4JSProjectConfigSnapshot> result = (ImmutableSet<N4JSProjectConfigSnapshot>) super.getProjects();
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public N4JSProjectConfigSnapshot findProjectByID(String projectID) {
		return (N4JSProjectConfigSnapshot) super.findProjectByID(projectID);
	}

	/** {@inheritDoc} */
	@Override
	public N4JSProjectConfigSnapshot findProjectByPath(@SuppressWarnings("hiding") URI path) {
		return (N4JSProjectConfigSnapshot) super.findProjectByPath(path);
	}

	/** {@inheritDoc} */
	@Override
	public N4JSProjectConfigSnapshot findProjectByNestedLocation(URI nestedLocation) {
		return (N4JSProjectConfigSnapshot) super.findProjectByNestedLocation(nestedLocation);
	}

	/** {@inheritDoc} */
	@Override
	public N4JSProjectConfigSnapshot findProjectContaining(URI sourceLocation) {
		return (N4JSProjectConfigSnapshot) super.findProjectContaining(sourceLocation);
	}

	/** {@inheritDoc} */
	@Override
	public N4JSSourceFolderSnapshot findSourceFolderContaining(URI nestedSourceLocation) {
		return (N4JSSourceFolderSnapshot) super.findSourceFolderContaining(nestedSourceLocation);
	}

	/** {@inheritDoc} */
	@Override
	public Pair<N4JSProjectConfigSnapshot, N4JSSourceFolderSnapshot> findProjectAndSourceFolderContaining(
			URI nestedSourceLocation) {
		@SuppressWarnings("unchecked")
		Pair<N4JSProjectConfigSnapshot, N4JSSourceFolderSnapshot> result = (Pair<N4JSProjectConfigSnapshot, N4JSSourceFolderSnapshot>) super.findProjectAndSourceFolderContaining(
				nestedSourceLocation);
		return result;
	}

	/**
	 * Returns <code>true</code> iff the given URI denotes a source file in an N4JS project and is matched by a module
	 * filter of type {@link ModuleFilterType#NO_VALIDATE} in the project's <code>package.json</code> file.
	 */
	public boolean isNoValidate(URI nestedLocation) {
		N4JSProjectConfigSnapshot project = findProjectContaining(nestedLocation);
		return project != null && project.isMatchedByModuleFilterOfType(nestedLocation, ModuleFilterType.NO_VALIDATE);
	}
}
