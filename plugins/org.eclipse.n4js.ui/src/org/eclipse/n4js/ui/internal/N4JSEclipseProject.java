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
package org.eclipse.n4js.ui.internal;

import static com.google.common.base.Optional.absent;
import static com.google.common.base.Optional.fromNullable;
import static org.eclipse.n4js.internal.N4JSModel.DIRECT_RESOURCE_IN_PROJECT_SEGMENTCOUNT;

import java.nio.file.Path;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.internal.N4JSProject;
import org.eclipse.n4js.ui.projectModel.IN4JSEclipseProject;
import org.eclipse.n4js.ui.projectModel.IN4JSEclipseSourceContainer;
import org.eclipse.n4js.utils.resources.ExternalProject;
import org.eclipse.xtext.ui.XtextProjectHelper;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;

/**
 */
public class N4JSEclipseProject extends N4JSProject implements IN4JSEclipseProject {

	private final IProject project;

	N4JSEclipseProject(IProject project, URI location, N4JSEclipseModel model) {
		super(location, project instanceof ExternalProject, model);
		this.project = project;
	}

	@Override
	protected boolean checkExists() {
		if (XtextProjectHelper.hasNature(project)) {
			return project.getFile(N4MF_MANIFEST).exists();
		}
		return false;
	}

	@Override
	protected N4JSEclipseModel getModel() {
		return (N4JSEclipseModel) super.getModel();
	}

	@Override
	public IProject getProject() {
		return project;
	}

	@Override
	public Path getLocationPath() {
		return project.getLocation().toFile().toPath();
	}

	@Override
	public Optional<URI> getManifestLocation() {
		if (checkExists() // Existing project AND
				&& ((getLocation().isPlatformResource()
						// Platform resource URI
						&& DIRECT_RESOURCE_IN_PROJECT_SEGMENTCOUNT == getLocation().segmentCount())
						|| isExternal())) { // OR external
			return fromNullable(getLocation().appendSegment(N4MF_MANIFEST));
		} else {
			return absent();
		}
	}

	@Override
	public ImmutableList<? extends IN4JSEclipseProject> getDependencies() {
		if (!exists()) {
			return ImmutableList.of();
		}
		return getModel().getDependencies(this);
	}

	@Override
	public ImmutableList<? extends IN4JSEclipseProject> getDependenciesAndImplementedApis() {
		if (!exists()) {
			return ImmutableList.of();
		}
		return getModel().getDependenciesAndImplementedApis(this);
	}

	@Override
	public ImmutableList<? extends IN4JSEclipseProject> getImplementedProjects() {
		if (!exists()) {
			return ImmutableList.of();
		}
		return getModel().getImplementedProjects(this);
	}

	@Override
	public ImmutableList<? extends IN4JSEclipseSourceContainer> getSourceContainers() {
		if (!exists()) {
			return ImmutableList.of();
		}
		return getModel().getN4JSSourceContainers(this);
	}

}
