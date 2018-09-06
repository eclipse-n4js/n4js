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
package org.eclipse.n4js.ui.containers;

import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.IN4JSSourceContainer;
import org.eclipse.n4js.ts.ui.navigation.URIBasedStorage;
import org.eclipse.n4js.ui.projectModel.IN4JSEclipseCore;
import org.eclipse.n4js.ui.projectModel.IN4JSEclipseProject;
import org.eclipse.n4js.ui.projectModel.IN4JSEclipseSourceContainer;
import org.eclipse.xtext.builder.builderState.IBuilderState;
import org.eclipse.xtext.builder.impl.IToBeBuiltComputerContribution;
import org.eclipse.xtext.builder.impl.ToBeBuilt;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.ui.resource.UriValidator;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Adjust the behavior of the builder in that the resources of a project are rebuilt, when the project description file
 * changes (e.g. {@code package.json}).
 *
 * @see IToBeBuiltComputerContribution
 */
@Singleton
@SuppressWarnings("restriction")
public class N4JSToBeBuiltComputer implements IToBeBuiltComputerContribution {

	@Inject
	private IBuilderState builderState;

	@Inject
	private IN4JSEclipseCore eclipseCore;

	@Inject
	private UriValidator uriValidator;

	@Override
	public void removeProject(ToBeBuilt toBeBuilt, IProject project, IProgressMonitor monitor) {
		// nothing to remove
	}

	@Override
	public void updateProject(ToBeBuilt toBeBuilt, IProject project, IProgressMonitor monitor) throws CoreException {
		// nothing to do per project, storages are processed individually
	}

	@Override
	public boolean updateStorage(ToBeBuilt toBeBuilt, IStorage storage, IProgressMonitor monitor) {
		if (storage instanceof IFile) {
			IFile file = (IFile) storage;
			if (IN4JSProject.PACKAGE_JSON.equals(file.getName())) {
				// changed project description resource - schedule all resources from source folders
				final IN4JSEclipseProject project = eclipseCore.create(file.getProject()).orNull();
				if (null != project && project.exists()) {
					List<? extends IN4JSEclipseSourceContainer> sourceContainers = project.getSourceContainers();
					Set<URI> toBeUpdated = toBeBuilt.getToBeUpdated();
					for (IN4JSEclipseSourceContainer sourceContainer : sourceContainers) {
						for (URI uri : sourceContainer) {
							if (uriValidator.canBuild(uri, new URIBasedStorage(uri))) {
								toBeUpdated.add(uri);
							}
						}
					}
				}

				// delete all resource descriptions of resources that are contained by this project
				IProject resourceProject = file.getProject();
				String projectName = resourceProject.getName();
				Set<URI> toBeDeleted = toBeBuilt.getToBeDeleted();
				for (final IResourceDescription description : builderState.getAllResourceDescriptions()) {
					URI uri = description.getURI();
					if (uri.isPlatformResource()) {
						if (projectName.equals(uri.segment(1))) {
							toBeDeleted.add(uri);
						}
					}
				}

				// still return false because we want to do the normal processing for the manifest file, too
				return false;
			}
		}
		return false;
	}

	@Override
	public boolean removeStorage(ToBeBuilt toBeBuilt, IStorage storage, IProgressMonitor monitor) {
		// nothing to remove
		return false;
	}

	@Override
	public boolean isPossiblyHandled(IStorage storage) {
		// no additional storage types are processed
		return false;
	}

	@Override
	public boolean isRejected(IFolder folder) {
		// do not build contents of nested node_modules folders
		if (folder.getName().equals(N4JSGlobals.NODE_MODULES)) {
			return true;
		}

		Optional<? extends IN4JSSourceContainer> sourceContainerOpt = eclipseCore.create(folder);
		return !sourceContainerOpt.isPresent();
	}

}
