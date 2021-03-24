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

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.packagejson.projectDescription.ModuleFilterType;
import org.eclipse.n4js.packagejson.projectDescription.ProjectDescription;
import org.eclipse.n4js.workspace.locations.FileURI;
import org.eclipse.n4js.workspace.locations.SafeURI;
import org.eclipse.n4js.xtext.workspace.BuildOrderInfo;
import org.eclipse.n4js.xtext.workspace.ProjectSet;
import org.eclipse.n4js.xtext.workspace.WorkspaceConfigSnapshot;
import org.eclipse.xtext.util.UriExtensions;
import org.eclipse.xtext.xbase.lib.Pair;

import com.google.common.collect.ImmutableSet;

/**
 * N4JS-specific adjustments to {@link WorkspaceConfigSnapshot}.
 */
public class N4JSWorkspaceConfigSnapshot extends WorkspaceConfigSnapshot {

	/** Creates a {@link N4JSWorkspaceConfigSnapshot}. */
	public N4JSWorkspaceConfigSnapshot(URI path, ProjectSet projects, BuildOrderInfo buildOrderInfo) {
		super(path, projects, buildOrderInfo);
	}

	@Override
	protected int computeHashCode() {
		return super.computeHashCode(); // no additional data in this class, so simply use super implementation
	}

	@Override
	protected boolean computeEquals(Object obj) {
		return super.computeEquals(obj); // no additional data in this class, so simply use super implementation
	}

	@Override
	public String toString() {
		return "N4JS" + super.toString();
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
	public N4JSProjectConfigSnapshot findProjectByName(String name) {
		return (N4JSProjectConfigSnapshot) super.findProjectByName(name);
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

	@Override
	@SuppressWarnings("unchecked")
	public Pair<N4JSProjectConfigSnapshot, N4JSSourceFolderSnapshot> findProjectAndSourceFolderContaining(
			URI nestedSourceLocation) {
		return (Pair<N4JSProjectConfigSnapshot, N4JSSourceFolderSnapshot>) super.findProjectAndSourceFolderContaining(
				nestedSourceLocation);
	}

	// FIXME this method has a bad name (the uri is just sanitized)
	public SafeURI<?> toProjectLocation(URI uri) {
		return new FileURI(new UriExtensions().withEmptyAuthority(uri));
	}

	public boolean isNoValidate(URI nestedLocation) {
		N4JSProjectConfigSnapshot project = findProjectContaining(nestedLocation);
		return project != null && project.isMatchedByModuleFilterOfType(nestedLocation, ModuleFilterType.NO_VALIDATE);
	}

	public String getOutputPath(URI nestedLocation) {
		N4JSProjectConfigSnapshot project = findProjectContaining(nestedLocation);
		ProjectDescription pd = project != null ? project.getProjectDescription() : null;
		return pd != null ? pd.getOutputPath() : null;
	}
}
