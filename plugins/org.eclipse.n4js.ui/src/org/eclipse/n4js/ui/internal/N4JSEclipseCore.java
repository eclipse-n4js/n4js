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

import static java.lang.Boolean.TRUE;
import static org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider.PERSISTED_DESCRIPTIONS;

import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.internal.AbstractN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.IN4JSSourceContainer;
import org.eclipse.n4js.projectModel.locations.SafeURI;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;
import org.eclipse.n4js.ui.projectModel.IN4JSEclipseCore;
import org.eclipse.n4js.ui.projectModel.IN4JSEclipseProject;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider;
import org.eclipse.xtext.ui.resource.IResourceSetProvider;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 */
@Singleton
public class N4JSEclipseCore extends AbstractN4JSCore implements IN4JSEclipseCore {

	@Inject
	private IResourceSetProvider resourceSetProvider;
	@Inject
	private ResourceDescriptionsProvider resourceDescriptionsProvider;

	private final N4JSEclipseModel model;

	/**
	 * Public for testing purpose.
	 *
	 * @param model
	 *            the backing model
	 */
	@Inject
	public N4JSEclipseCore(N4JSEclipseModel model) {
		this.model = model;
	}

	@Override
	public SafeURI<?> toProjectLocation(URI uri) {
		return model.toProjectLocation(uri);
	}

	/**
	 * Returns the N4JS project corresponding to the given Eclipse project.
	 * <p>
	 * Note that no check is done at this time on the existence or the Xtext nature of this project or the presence of
	 * the N4JS manifest.
	 * </p>
	 *
	 * @param project
	 *            the given project
	 * @return the n4js project corresponding to the given project, null if the given project is null
	 */
	@Override
	public Optional<? extends IN4JSEclipseProject> create(IProject project) {
		if (project == null) {
			return Optional.absent();
		}
		return Optional.fromNullable(model.getN4JSProject(project));
	}

	@Override
	public Optional<? extends IN4JSEclipseProject> findProject(URI nestedLocation) {
		if (nestedLocation == null) {
			return Optional.absent();
		}
		IN4JSEclipseProject result = model.findProjectWith(nestedLocation);
		return Optional.fromNullable(result);
	}

	@Override
	public Optional<? extends IN4JSProject> findProject(N4JSProjectName projectName) {
		IN4JSProject result = model.findProject(projectName);
		return Optional.fromNullable(result);
	}

	@Override
	public Iterable<IN4JSProject> findAllProjects() {
		return this.model.findAllProjects();
	}

	@Override
	public Map<N4JSProjectName, IN4JSProject> findAllProjectMappings() {
		return this.model.findAllProjectMappings();
	}

	@Override
	public Optional<? extends IN4JSSourceContainer> findN4JSSourceContainer(URI nestedLocation) {
		if (nestedLocation == null) {
			return Optional.absent();
		} else {
			return model.findN4JSSourceContainer(nestedLocation);
		}
	}

	@Override
	public Optional<? extends IN4JSSourceContainer> create(IFile eclipseFile) {
		if (eclipseFile == null) {
			return Optional.absent();
		}
		return model.getN4JSSourceContainer(eclipseFile);
	}

	@Override
	public Optional<? extends IN4JSSourceContainer> create(IFolder eclipseFolder) {
		if (eclipseFolder == null) {
			return Optional.absent();
		}
		return model.getN4JSSourceContainer(eclipseFolder);
	}

	@Override
	public IN4JSEclipseProject create(URI location) {
		if (location == null) {
			return null;
		}
		return model.getN4JSProject(location);
	}

	@Override
	public ResourceSet createResourceSet(Optional<IN4JSProject> contextProject) {
		final IProject eclipseProject = contextProject.isPresent()
				? ((IN4JSEclipseProject) contextProject.get()).getProject()
				: null;
		final ResourceSet resourceSet = resourceSetProvider.get(eclipseProject);
		// note_1: it is different than
		// org.eclipse.n4js.internal.N4JSRuntimeCore.createResourceSet(Optional<IN4JSProject>)
		// note_2: the value passed to #get() in previous line is not used in case of our implementation (as said by SZ
		// in
		// summer 2015 and on December 10, 2015) so it is ok to pass in 'null' for now, but this will become an issue if
		// this value will be used in the future! Therefore, client code should already pass in a valid contextProject,
		// if possible.
		resourceSet.getLoadOptions().put(PERSISTED_DESCRIPTIONS, TRUE);
		return resourceSet;
	}

	@Override
	public IResourceDescriptions getXtextIndex(ResourceSet resourceSet) {
		return resourceDescriptionsProvider.getResourceDescriptions(resourceSet);
	}

}
