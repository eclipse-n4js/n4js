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
package org.eclipse.n4js.generator.headless;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.generator.headless.logging.IHeadlessLogger;
import org.eclipse.n4js.internal.FileBasedWorkspace;
import org.eclipse.n4js.internal.N4JSBrokenProjectException;
import org.eclipse.n4js.n4mf.ProjectDescription;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.utils.ProjectDescriptionHelper;
import org.eclipse.n4js.utils.URIUtils;

import com.google.common.collect.Sets;
import com.google.inject.Inject;

/**
 * Helper methods to deal with projects and {@link FileBasedWorkspace}.
 */
public class HeadlessHelper {

	@Inject
	private ProjectDescriptionHelper projectDescriptionHelper;

	/**
	 * Configure FileBasedWorkspace with all projects found in sub-folders of {@code projectLocations}.
	 *
	 * @param projectLocations
	 *            list of project roots
	 * @param n4jsFileBasedWorkspace
	 *            instance of FileBasedWorkspace to configure (in N4JS injector)
	 * @throws N4JSCompileException
	 *             in error Case.
	 */
	public void registerProjects(List<File> projectLocations, FileBasedWorkspace n4jsFileBasedWorkspace)
			throws N4JSCompileException {
		// make absolute, since downstream URI conversion doesn't work if relative dir only.
		List<File> absProjectRoots = this.toAbsoluteFileList(projectLocations);

		// Collect all Projects in first Level
		List<URI> pUris = collectAllProjectUris(absProjectRoots);

		registerProjectsToFileBasedWorkspace(pUris, n4jsFileBasedWorkspace, null);
	}

	/** Registers provided project uris in a given workspace. Logger (if provided) used to log errors. */
	public void registerProjectsToFileBasedWorkspace(Iterable<URI> projectURIs,
			FileBasedWorkspace n4jsFileBasedWorkspace, IHeadlessLogger logger)
			throws N4JSCompileException {

		// TODO GH-783 refactor FileBasedWorkspace, https://github.com/eclipse/n4js/issues/783
		// this is reverse mapping of the one that is kept in the workspace
		Map<String, URI> registeredProjects = new HashMap<>();
		n4jsFileBasedWorkspace.getAllProjectsLocations().forEachRemaining(uri -> {
			String projectID = n4jsFileBasedWorkspace.getProjectDescription(uri).getProjectId();
			registeredProjects.put(projectID, URIUtils.normalize(uri));
		});

		// Register all projects with the file based workspace.
		for (URI uri : projectURIs) {
			URI projectURI = URIUtils.normalize(uri);

			final ProjectDescription projectDescription = projectDescriptionHelper
					.loadProjectDescriptionAtLocation(projectURI);

			if (projectDescription == null) {
				throw new N4JSCompileException(
						"Cannot load project description for project at " + projectURI.toFileString()
								+ ". Make sure the project contains a valid package.json file.");
			}

			final String projectId = projectDescription.getProjectId();

			if (skipRegistering(projectId, projectURI, registeredProjects)) {
				if (logger != null && logger.isCreateDebugOutput()) {
					logger.debug("Skipping already registered project '" + projectURI + "'");
				}
				/*
				 * We could call FileBasedWorkspace.registerProject which would silently. Still to avoid potential side
				 * effects and to keep {@code registeredProjects} management simpler,we will skip it explicitly.
				 */
				continue;
			}

			try {
				if (logger != null && logger.isCreateDebugOutput()) {
					logger.debug("Registering project '" + projectURI + "'");
				}
				n4jsFileBasedWorkspace.registerProject(projectURI);
				registeredProjects.put(projectId, projectURI);
			} catch (N4JSBrokenProjectException e) {
				throw new N4JSCompileException("Unable to register project '" + projectURI + "'", e);
			}
		}
	}

	/**
	 * Creates a new list, calling {@link File#getAbsoluteFile()} on each member
	 *
	 * @param relativeFiles
	 *            list of possibly relative paths.
	 * @return new list of absolute paths.
	 * @throws N4JSCompileException
	 *             in case of underlying IO-problems
	 */
	public List<File> toAbsoluteFileList(List<File> relativeFiles) throws N4JSCompileException {
		List<File> absProjectRoots = new ArrayList<>(relativeFiles.size());
		for (File relPR : relativeFiles) {
			try {
				absProjectRoots.add(relPR.getCanonicalFile());
			} catch (IOException e) {
				throw new N4JSCompileException("Unable to create canonical filename of file '" + relPR + "'", e);
			}
		}
		return absProjectRoots;
	}

	/**
	 * Collects the projects containing the given single source files.
	 *
	 * @param sourceFiles
	 *            the list of single source files
	 * @param n4jsFileBasedWorkspace
	 *            the workspace to be checked for containing projects
	 * @return list of N4JS project locations
	 * @throws N4JSCompileException
	 *             if no project cannot be found for one of the given files
	 */
	public List<File> findProjectsForSingleFiles(List<File> sourceFiles,
			FileBasedWorkspace n4jsFileBasedWorkspace)
			throws N4JSCompileException {

		Set<URI> result = Sets.newLinkedHashSet();

		for (File sourceFile : sourceFiles) {
			URI sourceFileURI = URI.createFileURI(sourceFile.toString());
			URI projectURI = n4jsFileBasedWorkspace.findProjectWith(sourceFileURI);
			if (projectURI == null) {
				throw new N4JSCompileException("No project for file '" + sourceFile.toString() + "' found.");
			}
			result.add(projectURI);
		}

		// convert back to Files:
		return result.stream().map(u -> new File(u.toFileString())).collect(Collectors.toList());
	}

	/**
	 * Searches for direct sub-folders containing a File named {@link IN4JSProject#PACKAGE_JSON}
	 *
	 * @param absProjectRoots
	 *            all project root (must be absolute)
	 * @return list of directories being a project
	 */
	public List<File> collectAllProjectPaths(List<File> absProjectRoots) {
		return getProjectStream(absProjectRoots)
				.collect(Collectors.toList());
	}

	/**
	 * Searches for direct sub-folders containing a File named {@link IN4JSProject#PACKAGE_JSON}
	 *
	 * @param absProjectRoots
	 *            all project root (must be absolute)
	 * @return list of directories being a project
	 */
	public List<URI> collectAllProjectUris(List<File> absProjectRoots) {
		return getProjectStream(absProjectRoots)
				.map(HeadlessHelper::fileToURI)
				.collect(Collectors.toList());
	}

	/**
	 * Utility for deciding if a given project location should be registered in the FileBasedWorkspace. Note that this
	 * method has three "return values". {@code false} if provided project manifest describes new project that has to be
	 * registered. {@code false} when project manifest describes already known project in the same location, in which
	 * case project is safe to be skipped. {@code N4JSCompileException} is thrown when provided project manifest
	 * describes already known project but in different location in which case compilation should be stopped.
	 *
	 * @param projectId
	 *            of the new project to be considered for registering
	 * @param projectLocation
	 *            of the new project to be considered for registering
	 * @param registeredProjects
	 *            local cache of already known projects
	 * @return {@code false} if projects needs to be registered
	 * @throws N4JSCompileException
	 *             if project conflicts with project in different location
	 */
	private boolean skipRegistering(String projectId, URI projectLocation, Map<String, URI> registeredProjects)
			throws N4JSCompileException {

		// new ID, don't skip registering
		if (!registeredProjects.containsKey(projectId))
			return false;

		URI registeredProjectLocation = registeredProjects.get(projectId);

		// duplicate is the same location, so the same project passed twice, skip registering
		if (projectLocation.equals(registeredProjectLocation))
			return true;

		if (registeredProjectLocation == null)
			// our local cache of known projects is out of sync with FileBasedWorkspace -> stop compilation
			throw new N4JSCompileException("Duplicate project id [" + projectId
					+ "]. Already registered project at " + registeredProjects.get(projectId)
					+ ", trying to register project at " + projectLocation + ".");

		// duplicate is in new location, so new project with the same name -> stop compilation
		throw new N4JSCompileException("Duplicate project id [" + projectId
				+ "]. Already registered project at " + registeredProjectLocation
				+ ", trying to register project at " + projectLocation + ".");

	}

	private static Stream<File> getProjectStream(List<File> absProjectRoots) {
		return absProjectRoots.stream()
				// find all contained folders
				.flatMap(root -> Arrays.asList(root.listFiles(File::isDirectory)).stream())
				// only those with package.json file
				.filter(dir -> new File(dir, IN4JSProject.PACKAGE_JSON).isFile());
	}

	private static URI fileToURI(File file) {
		return URI.createFileURI(file.toString());
	}
}
