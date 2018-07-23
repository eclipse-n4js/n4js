/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.internal;

import org.eclipse.emf.common.util.URI;

import com.google.common.collect.ImmutableList;

import org.eclipse.n4js.projectDescription.ProjectDescription;
import org.eclipse.n4js.projectModel.IN4JSArchive;
import org.eclipse.n4js.projectModel.IN4JSSourceContainer;
import org.eclipse.n4js.projectModel.IN4JSSourceContainerAware;

/**
 */
@SuppressWarnings("javadoc")
public class N4JSArchive implements IN4JSArchive {

	private final N4JSProject project;
	private final URI archiveLocation;

	protected N4JSArchive(N4JSProject project, URI archiveLocation) {
		this.project = project;
		this.archiveLocation = archiveLocation;
	}

	@Override
	public boolean isProject() {
		return false;
	}

	@Override
	public boolean isArchive() {
		return true;
	}

	@Override
	public N4JSProject getProject() {
		return project;
	}

	@Override
	public ImmutableList<? extends IN4JSSourceContainer> getSourceContainers() {
		return getModel().getSourceContainers(this);
	}

	@Override
	public String getLibraryName() {
		ProjectDescription pd = getModel().getProjectDescription(getLocation());
		if (pd == null) {
			return null;
		}
		return pd.getProjectId();
	}

	@Override
	public String getFileName() {
		return archiveLocation.lastSegment();
	}

	@Override
	public URI getLocation() {
		return archiveLocation;
	}

	@Override
	public String getProjectId() {
		// note: currently returns project ID of containing project
		// TODO change method to return the project ID of the project inside the archive! (similar for other methods)
		ProjectDescription pd = getModel().getProjectDescription(getLocation());
		if (pd == null) {
			return null;
		}
		return pd.getProjectId();
	}

	protected N4JSModel getModel() {
		return project.getModel();
	}

	@Override
	public ImmutableList<? extends IN4JSArchive> getReferencedLibraries() {
		return getModel().getLibraries(this);
	}

	@Override
	public ImmutableList<? extends IN4JSSourceContainerAware> getAllDirectDependencies() {
		return getReferencedLibraries();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((archiveLocation == null) ? 0 : archiveLocation.hashCode());
		result = prime * result + ((project == null) ? 0 : project.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof N4JSArchive))
			return false;
		N4JSArchive other = (N4JSArchive) obj;
		if (archiveLocation == null) {
			if (other.archiveLocation != null)
				return false;
		} else if (!archiveLocation.equals(other.archiveLocation))
			return false;
		if (project == null) {
			if (other.project != null)
				return false;
		} else if (!project.equals(other.project))
			return false;
		return true;
	}

	@Override
	public boolean isExternal() {
		return false;
	}

}
