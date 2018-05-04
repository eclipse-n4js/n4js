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
import org.eclipse.n4js.n4mf.utils.parsing.ProjectDescriptionProviderUtil;
import org.eclipse.n4js.projectModel.IN4JSProject;

import com.google.common.collect.Sets;

/**
 * Helper Methods to deal with projects and FileBasedWorkspace.
 */
public class HeadlessHelper {

	/**
	 * Configure fbWorkspace with all projects found in Subfolders of projectLocations.
	 *
	 * @param projectLocations
	 *            list of project roots
	 * @param n4jsFileBasedWorkspace
	 *            instance of FileBasedWorkspace to configure (in N4JS injector)
	 * @throws N4JSCompileException
	 *             in error Case.
	 */
	public static void registerProjects(List<File> projectLocations, FileBasedWorkspace n4jsFileBasedWorkspace)
			throws N4JSCompileException {
		// make absolute, since downstream URI conversion doesn't work if relative dir only.
		List<File> absProjectRoots = HeadlessHelper.toAbsoluteFileList(projectLocations);

		// Collect all Projects in first Level
		List<URI> pUris = collectAllProjectUris(absProjectRoots);

		registerProjectsToFileBasedWorkspace(pUris, n4jsFileBasedWorkspace, null);
	}

	/** Registers provided project uris in a given workspace. Logger (if provided) used to log errors. */
	public static void registerProjectsToFileBasedWorkspace(Iterable<URI> projectURIs,
			FileBasedWorkspace n4jsFileBasedWorkspace, IHeadlessLogger logger)
			throws N4JSCompileException {
		Map<String, URI> registeredProjects = new HashMap<>();
		// Register all projects with the file based workspace.
		for (URI projectURI : projectURIs) {
			try {

				File root = new File(projectURI.toFileString());
				File manifest = new File(root, IN4JSProject.N4MF_MANIFEST);
				if (!manifest.isFile()) {
					throw new N4JSCompileException("Cannot locate manifest at " + manifest + ".");
				}

				String projectID = ProjectDescriptionProviderUtil.getFromFile(manifest).getProjectId();

				if (registeredProjects.containsKey(projectID))
					throw new N4JSCompileException("Duplicate project id [" + projectID
							+ "]. Already registered project at " + registeredProjects.get(projectID)
							+ ", trying to register project at " + projectURI + ".");

				if (logger != null && logger.isCreateDebugOutput()) {
					logger.debug("Registering project '" + projectURI + "'");
				}
				registeredProjects.put(projectID, projectURI);
				n4jsFileBasedWorkspace.registerProject(projectURI);
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
	static List<File> toAbsoluteFileList(List<File> relativeFiles) throws N4JSCompileException {
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
	public static List<File> findProjectsForSingleFiles(List<File> sourceFiles,
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
	 * Searches for direct sub-folders containing a File named {@link IN4JSProject#N4MF_MANIFEST}
	 *
	 * @param absProjectRoots
	 *            all project root (must be absolute)
	 * @return list of directories being a project
	 */
	public static List<File> collectAllProjectPaths(List<File> absProjectRoots) {
		return getProjectStream(absProjectRoots)
				.collect(Collectors.toList());
	}

	/**
	 * Searches for direct sub-folders containing a File named {@link IN4JSProject#N4MF_MANIFEST}
	 *
	 * @param absProjectRoots
	 *            all project root (must be absolute)
	 * @return list of directories being a project
	 */
	public static List<URI> collectAllProjectUris(List<File> absProjectRoots) {
		return getProjectStream(absProjectRoots)
				.map(HeadlessHelper::fileToURI)
				.collect(Collectors.toList());
	}

	private static Stream<File> getProjectStream(List<File> absProjectRoots) {
		return absProjectRoots.stream()
				// find all contained folders
				.flatMap(root -> Arrays.asList(root.listFiles(File::isDirectory)).stream())
				// only those with manifest
				.filter(dir -> new File(dir, IN4JSProject.N4MF_MANIFEST).isFile());
	}

	private static URI fileToURI(File file) {
		return URI.createFileURI(file.toString());
	}
}
