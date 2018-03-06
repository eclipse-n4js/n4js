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
package org.eclipse.n4js.external;

import static com.google.common.collect.Maps.newHashMap;
import static org.eclipse.n4js.projectModel.IN4JSProject.N4MF_MANIFEST;

import java.io.File;
import java.net.URI;
import java.util.Collection;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.core.internal.resources.BuildConfiguration;
import org.eclipse.core.resources.IProject;
import org.eclipse.n4js.internal.FileBasedExternalPackageManager;
import org.eclipse.n4js.n4mf.ProjectDescription;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.utils.resources.ExternalProject;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Service for providing {@link ExternalProject external project} instances.
 */
@Singleton
@SuppressWarnings("restriction")
public class N4JSExternalProjectProvider10 {
	private static final Logger logger = Logger.getLogger(N4JSExternalProjectProvider10.class);

	@Inject
	private IN4JSCore core;

	@Inject
	private ExternalLibraryWorkspace externalLibraryWorkspace;

	@Inject
	private FileBasedExternalPackageManager packageManager;

	/**
	 * Optionally returns with an {@link N4JSExternalProject N4JS external project} for the given project root location.
	 * If the project cannot be resolved, the this method will return with an {@link Optional#absent() absent} instance.
	 *
	 * @param projectRootLocation
	 *            the project root location on the file system. Must not be {@code null}.
	 * @return the N4JS external project instance or {@link Optional#absent() missing} if cannot be located.
	 */
	private Optional<N4JSExternalProject> get(final URI projectRootLocation) {
		final File projectRoot = new File(projectRootLocation);
		if (projectRoot.exists() && projectRoot.isDirectory()) {
			final File manifest = new File(projectRoot, N4MF_MANIFEST);
			if (manifest.exists() && manifest.isFile()) {
				final ProjectDescription projectDescription = tryLoadProjectDescription(manifest);
				if (null != projectDescription) {
					final IN4JSProject externalPackage = findExternalPackage(toFileUri(projectRootLocation));
					if (null != externalPackage) {
						return Optional.of(new N4JSExternalProject(projectRoot, externalPackage));
					}
				}
			}
		}
		return Optional.absent();
	}

	/** Converts the {@link File} resource into an EMF file URI. */
	private org.eclipse.emf.common.util.URI toFileUri(final URI resourceURI) {
		return org.eclipse.emf.common.util.URI.createFileURI(new File(resourceURI).getAbsolutePath());
	}

	private IN4JSProject findExternalPackage(final org.eclipse.emf.common.util.URI nestedLocation) {
		return core.findProject(nestedLocation).orNull();
	}

	private ProjectDescription tryLoadProjectDescription(final File manifest) {
		try {
			return packageManager.loadManifest(toFileUri(manifest.toURI()));
		} catch (final Exception e) {
			logger.error("Error while trying to load project description: " + manifest, e);
			return null;
		}
	}

	private N4JSExternalProject getN4JSExternalProject(ExternalProject externalProject) {
		N4JSExternalProject n4ExternalProject = get(externalProject.getLocationURI()).orNull();
		return n4ExternalProject;
	}

	private Collection<N4JSExternalProject> getWithDependencies(Iterable<N4JSExternalProject> externalProjects) {
		Map<String, N4JSExternalProject> allProjects = newHashMap();
		for (IProject project : externalProjects) {
			if (project instanceof ExternalProject) {
				N4JSExternalProject externalProject = getN4JSExternalProject((ExternalProject) project);
				allProjects.put(project.getName(), externalProject);
			}
		}

		for (N4JSExternalProject project : allProjects.values()) {
			Iterable<String> projectIds = project.getAllDirectDependencyIds();
			for (final String projectId : projectIds) {
				final ExternalProject referencedProject = allProjects.get(projectId);
				if (null != referencedProject) {
					project.add(new BuildConfiguration(referencedProject));
				}
			}
		}

		return allProjects.values();
	}

	// public Collection<N4JSExternalProject> getAllProjects() {
	// return getWithDependencies(externalLibraryWorkspace.getProjects());
	// }

	// public N4JSExternalProject getProject(String projectName) {
	// Map<String, N4JSExternalProject> allProjects = newHashMap();
	// for (N4JSExternalProject project : getAllProjects()) {
	// allProjects.put(project.getName(), project);
	// }
	// return allProjects.get(projectName);
	// }
	//
	// public N4JSExternalProject getProject(URI projectLocation) {
	// Map<URI, N4JSExternalProject> allProjects = getMapURI();
	// return allProjects.get(projectLocation);
	// }
	//
	// public Collection<N4JSExternalProject> getProjects(Collection<URI> projectLocations) {
	// Map<URI, N4JSExternalProject> allProjects = getMapURI();
	// Collection<N4JSExternalProject> n4ExtProjs = new HashSet<>();
	// for (URI loc : projectLocations) {
	// n4ExtProjs.add(allProjects.get(loc));
	// }
	// return n4ExtProjs;
	// }

	// private Map<URI, N4JSExternalProject> getMapURI() {
	// Map<URI, N4JSExternalProject> allProjects = newHashMap();
	// for (N4JSExternalProject project : getAllProjects()) {
	// allProjects.put(project.getLocationURI(), project);
	// }
	// return allProjects;
	// }

	// public Collection<N4JSExternalProject> getProjects(URI rootLocation) {
	// return getWithDependencies(externalLibraryWorkspace.getProjects(rootLocation));
	// }
}
