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

import org.eclipse.core.resources.IProject;
import org.eclipse.n4js.external.ExternalProject;
import org.eclipse.n4js.internal.N4JSProject;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.locations.SafeURI;
import org.eclipse.n4js.ui.projectModel.IN4JSEclipseProject;
import org.eclipse.n4js.ui.projectModel.IN4JSEclipseSourceContainer;
import org.eclipse.xtext.ui.XtextProjectHelper;

import com.google.common.collect.ImmutableList;

/**
 */
public class N4JSEclipseProject extends N4JSProject implements IN4JSEclipseProject {

	private final IProject project;

	N4JSEclipseProject(IProject project, SafeURI<?> location, N4JSEclipseModel model) {
		super(location, project instanceof ExternalProject, model);
		this.project = project;
	}

	@Override
	protected boolean checkExists() {
		if (XtextProjectHelper.hasNature(project)) {
			return project.getFile(IN4JSProject.PACKAGE_JSON).exists();
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
	public ImmutableList<? extends IN4JSEclipseProject> getDependencies() {
		if (!exists()) {
			return ImmutableList.of();
		}
		return getModel().getDependencies(this, false);
	}

	@Override
	public ImmutableList<? extends IN4JSEclipseProject> getDependenciesAndImplementedApis() {
		return getDependenciesAndImplementedApis(false);
	}

	@Override
	public ImmutableList<? extends IN4JSEclipseProject> getDependenciesAndImplementedApis(
			boolean includeAbsentProjects) {
		if (!exists()) {
			return ImmutableList.of();
		}
		return getModel().getDependenciesAndImplementedApis(this, includeAbsentProjects);
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
