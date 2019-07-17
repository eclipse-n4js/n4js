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

import java.net.URI;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.external.N4JSExternalProject;
import org.eclipse.n4js.preferences.ExternalLibraryPreferenceStore;
import org.eclipse.n4js.projectModel.locations.FileURI;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;
import org.eclipse.n4js.ui.external.EclipseExternalLibraryWorkspace;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.service.OperationCanceledError;
import org.eclipse.xtext.ui.editor.validation.MarkerCreator;
import org.eclipse.xtext.ui.validation.DefaultResourceUIValidatorExtension;
import org.eclipse.xtext.ui.validation.IResourceUIValidatorExtension;
import org.eclipse.xtext.ui.validation.MarkerEraser;
import org.eclipse.xtext.ui.validation.MarkerTypeProvider;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.validation.Issue;

import com.google.inject.Inject;

/**
 * {@link IResourceUIValidatorExtension} implementation that uses the services associated with the validated resource
 * instead of injecting them with Guice. With this solution N4MF related validations can be triggered from N4JS
 * projects.
 */
public class ResourceUIValidatorExtension extends MarkerEraser implements IResourceUIValidatorExtension {

	private final static Logger LOGGER = Logger.getLogger(DefaultResourceUIValidatorExtension.class);

	@Inject
	private ExternalLibraryPreferenceStore externalLibraryPreferenceStore;

	@Inject
	private EclipseExternalLibraryWorkspace externalLibraryWorkspace;

	@Override
	public void updateValidationMarkers(IFile file, Resource resource, CheckMode mode, IProgressMonitor monitor)
			throws OperationCanceledException {
		if (shouldProcess(file)) {
			addMarkers(file, resource, mode, monitor);
		}
	}

	@Override
	public void deleteValidationMarkers(IFile file, CheckMode checkMode, IProgressMonitor monitor) {
		super.deleteValidationMarkers(file, checkMode, monitor);
	}

	private void addMarkers(IFile file, Resource resource, CheckMode mode, IProgressMonitor monitor)
			throws OperationCanceledException {
		try {
			List<Issue> list = getValidator(resource).validate(resource, mode, getCancelIndicator(monitor));
			if (monitor.isCanceled()) {
				throw new OperationCanceledException();
			}
			deleteMarkers(file, mode, monitor);
			if (monitor.isCanceled()) {
				throw new OperationCanceledException();
			}
			createMarkers(file, resource, list);
		} catch (OperationCanceledError error) {
			throw error.getWrapped();
		} catch (CoreException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	/** Add list of issues to the given file. Used for issues of external libraries. */
	public void createMarkers(IResource iResource, Resource resource, List<Issue> list) throws CoreException {
		createMarkers(iResource, list, getMarkerCreator(resource), getMarkerTypeProvider(resource));
	}

	private void createMarkers(IResource iResource, List<Issue> list, MarkerCreator markerCreator,
			MarkerTypeProvider markerTypeProvider) throws CoreException {

		for (Issue issue : list) {
			markerCreator.createMarker(issue, iResource, markerTypeProvider.getMarkerType(issue));
		}
	}

	private CancelIndicator getCancelIndicator(final IProgressMonitor monitor) {
		return () -> monitor.isCanceled();
	}

	private IResourceValidator getValidator(Resource resource) {
		return getService(resource, IResourceValidator.class);
	}

	private MarkerCreator getMarkerCreator(Resource resource) {
		return getService(resource, MarkerCreator.class);
	}

	private MarkerTypeProvider getMarkerTypeProvider(Resource resource) {
		return getService(resource, MarkerTypeProvider.class);
	}

	private <T> T getService(final Resource resource, final Class<T> clazz) {
		final IResourceServiceProvider serviceProvider = ((XtextResource) resource).getResourceServiceProvider();
		return serviceProvider.get(clazz);
	}

	/**
	 *
	 * Deletes all markers of all external projects with the given name.
	 *
	 * @param projectName
	 *            name of the project to be cleaned
	 */
	public void clearAllMarkersOfExternalProject(N4JSProjectName projectName) {
		List<N4JSExternalProject> projects = externalLibraryWorkspace.getProjectsForName(projectName);
		N4JSExternalProject[] projectsArray = projects.toArray(new N4JSExternalProject[projects.size()]);
		clearAllMarkersOfExternalProjects(projectsArray);
	}

	/** Deletes all markers of all external projects. */
	public void clearAllMarkersOfAllExternalProjects() {
		Collection<FileURI> locations = externalLibraryPreferenceStore.getLocations();
		for (FileURI location : locations) {
			deleteMarkersRecursively(location);
		}
	}

	/** Deletes all markers of all given external projects. */
	public void clearAllMarkersOfExternalProjects(N4JSExternalProject[] projects) {
		if (projects == null) {
			return;
		}

		for (N4JSExternalProject prj : projects) {
			deleteMarkersRecursively(prj.getSafeLocation());
		}
	}

	private void deleteMarkersRecursively(FileURI location) {
		if (location == null) {
			return;
		}

		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IContainer[] containers = root.findContainersForLocationURI(URI.create(location.toString()));
		if (containers == null || containers.length == 0) {
			return;
		}

		try {
			for (IContainer container : containers) {
				if (container.isAccessible()) {
					container.deleteMarkers(null, true, IResource.DEPTH_INFINITE);
				}
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

}
