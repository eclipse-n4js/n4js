/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.generator.headless;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.locations.SafeURI;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;

import com.google.common.collect.Iterables;

/**
 * A wrapper around N4JS projects that has the ability to track dependent projects in the form of markers. A project is
 * added as a marker of this project if it has an active dependency on it. A dependency is active as long as the
 * dependent project is not yet built.
 * <p>
 * Additionally, projects that have been explicitly requested to be compiled by the user are added as markers to
 * themselves, as opposed to projects that have been discovered solely due to dependencies of explicitly requested
 * projects.
 * </p>
 * <p>
 * Furthermore, this class tracks the loaded resources of the wrapped projects in order to be able to unload them as
 * soon as possible.
 * </p>
 */
class MarkedProject {
	/**
	 * The wrapped project.
	 */
	final IN4JSProject project;

	/**
	 * Contains the active markers, i.e., dependent projects that have not yet been built themselves.
	 */
	final Set<IN4JSProject> markers = new TreeSet<>(N4JSProjectComparator.INSTANCE);

	/**
	 * All loaded resources of this project.
	 */
	final Set<Resource> resources = new LinkedHashSet<>();

	/**
	 * All loaded external resources of this project. This is a subset of {@link #resources}.
	 */
	final Set<Resource> externalResources = new HashSet<>();

	/**
	 * All loaded test resources of this project. This is a subset of {@link #resources}.
	 */
	final Set<Resource> testResources = new HashSet<>();

	/**
	 * Create a wrapper around a project;
	 */
	public MarkedProject(IN4JSProject project) {
		this.project = project;
	}

	/**
	 * Indicates whether the given resource is external in the context of this project.
	 *
	 * @param resource
	 *            the resource to check
	 * @return <code>true</code> if the given resource is external and <code>false</code> otherwise
	 */
	public boolean isExternal(Resource resource) {
		return externalResources.contains(resource);
	}

	/**
	 * Indicates whether the given resource is a test in the context of this project.
	 *
	 * @param resource
	 *            the resource to check
	 * @return <code>true</code> if the given resource is a test and <code>false</code> otherwise
	 */
	public boolean isTest(Resource resource) {
		return testResources.contains(resource);
	}

	/**
	 * Adds the given project as a marker, indicating that it depends on this project.
	 *
	 * @param marker
	 *            the project to mark this project with
	 */
	public void markWith(IN4JSProject marker) {
		markers.add(marker);
	}

	/**
	 * Indicates whether or not the given project is a marker of this project.
	 *
	 * @param marker
	 *            the project to check
	 * @return <code>true</code> if the given project is a marker of this project and <code>false</code> otherwise
	 */
	public boolean hasMarker(IN4JSProject marker) {
		return markers.contains(marker);
	}

	/**
	 * Indicates whether or not this project still has markers.
	 *
	 * @return <code>true</code> if this project still has markers and <code>false</code> otherwise
	 */
	public boolean hasMarkers() {
		return !markers.isEmpty();
	}

	/**
	 * Remove the given project as a marker of this project, indicating that it is no longer has an active dependency on
	 * this project.
	 *
	 * @param marker
	 *            dependent project to be removed
	 * @return <code>true</code> if the given project was a marker of this project and <code>false</code> otherwise
	 */
	public boolean remove(IN4JSProject marker) {
		return markers.remove(marker);
	}

	/**
	 * Unload all resources associated with this marked project and remove them from the given resource set.
	 *
	 * @param resourceSet
	 *            the resource set containing the resources of this project
	 * @param recorder
	 *            the progress state recorder
	 */
	public void unload(ResourceSet resourceSet, N4ProgressStateRecorder recorder) {
		recorder.markStartUnloading(this);

		ResourceDescriptionsData index = ResourceDescriptionsData.ResourceSetAdapter
				.findResourceDescriptionsData(resourceSet);

		unloadResources(resourceSet, index, recorder);
		unloadPackageJsonResource(resourceSet, index, recorder);
		clearResources();

		recorder.markFinishedUnloading(this);
	}

	private void unloadResources(ResourceSet resourceSet, ResourceDescriptionsData index,
			N4ProgressStateRecorder recorder) {
		for (Resource res : resources)
			unloadResource(res, resourceSet, index, recorder);
	}

	private void unloadPackageJsonResource(ResourceSet resourceSet, ResourceDescriptionsData index,
			N4ProgressStateRecorder recorder) {
		SafeURI<?> packageJsonLocation = project.getProjectDescriptionLocation();
		if (packageJsonLocation != null) {
			Resource resource = resourceSet.getResource(packageJsonLocation.toURI(), false);
			if (resource != null)
				unloadResource(resource, resourceSet, index, recorder);
		}
	}

	private void unloadResource(Resource resource, ResourceSet resourceSet, ResourceDescriptionsData index,
			N4ProgressStateRecorder recorder) {
		recorder.markUnloadingOf(resource);
		if (index != null)
			index.removeDescription(resource.getURI());
		resource.unload();
		resourceSet.getResources().remove(resource);
	}

	public void clearResources() {
		resources.clear();
		externalResources.clear();
		testResources.clear();
	}

	/**
	 * Unload the ASTs and clear the resource scope caches of all resources that belong to this marked project.
	 */
	public void unloadASTAndClearCaches() {
		Iterables.filter(resources, resource -> resource.isLoaded()).forEach(resource -> {
			if (resource instanceof N4JSResource) {
				N4JSResource n4jsResource = (N4JSResource) resource;
				n4jsResource.unloadAST();
			}
		});
	}

	@Override
	public String toString() {
		return project.toString();
	}
}
