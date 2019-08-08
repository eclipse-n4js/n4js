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

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.eclipse.n4js.generator.headless.logging.IHeadlessLogger;
import org.eclipse.n4js.internal.FileBasedWorkspace;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.locations.FileURI;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;
import org.eclipse.n4js.utils.collections.Collections2;

import com.google.common.base.Joiner;
import com.google.common.base.Predicates;
import com.google.common.collect.Sets;
import com.google.inject.Inject;

/**
 * Computer class which computes the set of projects to build based on user settings such as project search paths,
 * specific project paths and single source files.
 *
 * Use the {@code create*BuildSet}-methods to obtain a {@link BuildSet} instance.
 *
 * This class allows the creation of the following types of {@link BuildSet}s:
 * <ol>
 *
 * <li>Single File produces a {@link BuildSet} that instructs the compiler to only compile a limited set of single
 * source files (see {@link #createSingleFileBuildSet(File, Set)}, {@link #createSingleFilesBuildSet(List, Set)} and
 * {@link #createSingleFilesBuildSet(List, List, Set)})</li>
 *
 * <li>Projects produces a {@link BuildSet} that instructs the compiler to only compile a given list of projects. (See
 * {@link #createProjectsBuildSet(List, Set)}, {@link #createProjectsBuildSet(List, List, Set)})</li>
 *
 * <li>All Projects produces a {@link BuildSet} that instructs the compiler to compile all projects directly contained
 * in a given projects-location (See {@link #createAllProjectsBuildSet(List, Set)}).
 *
 * </ol>
 *
 * You can use {@link HeadlessHelper#registerProjects(BuildSet, FileBasedWorkspace)} to register the projects in the
 * returned {@link BuildSet} instances with the {@link FileBasedWorkspace} in use.
 */
public class BuildSetComputer {

	@Inject
	private HeadlessHelper headlessHelper;

	@Inject
	private IHeadlessLogger logger;

	/**
	 * Creates a {@link BuildSet} for compiling a single source file.
	 *
	 * @param singleSourceFile
	 *            if non-empty limit compilation to the sources files listed here
	 * @throws N4JSCompileException
	 *             if one or multiple errors occur during compilation
	 */
	public BuildSet createSingleFileBuildSet(File singleSourceFile, Set<N4JSProjectName> shadowedProjectNames)
			throws N4JSCompileException {
		return createSingleFilesBuildSet(Arrays.asList(singleSourceFile), shadowedProjectNames);
	}

	/**
	 * Creates a {@link BuildSet} for compiling the specified source files and their dependencies only.
	 *
	 * @param singleSourceFiles
	 *            if non-empty limit compilation to the sources files listed here
	 * @throws N4JSCompileException
	 *             if one or multiple errors occur during compilation
	 */
	public BuildSet createSingleFilesBuildSet(List<File> singleSourceFiles, Set<N4JSProjectName> shadowedProjectNames)
			throws N4JSCompileException {
		return createSingleFilesBuildSet(Collections.emptyList(), singleSourceFiles, shadowedProjectNames);
	}

	/**
	 * Creates a {@link BuildSet} for compiling the specified source files and their dependencies only.
	 *
	 * @param searchPaths
	 *            where to search for dependent projects.
	 * @param singleSourceFiles
	 *            if non-empty limit compilation to the sources files listed here
	 * @throws N4JSCompileException
	 *             if one or multiple errors occur during compilation
	 */
	public BuildSet createSingleFilesBuildSet(List<File> searchPaths, List<File> singleSourceFiles,
			Set<N4JSProjectName> shadowedProjectNames) throws N4JSCompileException {
		return createBuildSet(searchPaths, Collections.emptyList(), singleSourceFiles, shadowedProjectNames);
	}

	/**
	 * Creates a {@link BuildSet} for compiling a list of projects. Dependencies will be searched in the current
	 * directory.
	 *
	 * @param projectPaths
	 *            the projects to compile. the base folder of each project must be provided.
	 * @throws N4JSCompileException
	 *             if one or multiple errors occur during compilation
	 */
	public BuildSet createProjectsBuildSet(List<File> projectPaths, Set<N4JSProjectName> shadowedProjectNames)
			throws N4JSCompileException {
		return createProjectsBuildSet(Arrays.asList(new File(".")), projectPaths, shadowedProjectNames);
	}

	/**
	 * Creates a {@link BuildSet} for compiling a list of projects. Dependencies will be searched for at the given
	 * {@code searchPaths}.
	 *
	 * @param searchPaths
	 *            where to search for dependent projects.
	 * @param projectPaths
	 *            the projects to compile. the base folder of each project must be provided.
	 * @throws N4JSCompileException
	 *             if one or multiple errors occur during compilation
	 */
	public BuildSet createProjectsBuildSet(List<File> searchPaths, List<File> projectPaths,
			Set<N4JSProjectName> shadowedProjectNames) throws N4JSCompileException {
		return createBuildSet(searchPaths, projectPaths, Collections.emptyList(), shadowedProjectNames);
	}

	/**
	 * Creates a {@link BuildSet} for compiling all projects to be found in the given search paths.
	 *
	 * @param searchPaths
	 *            where to search for the projects to compile.
	 * @throws N4JSCompileException
	 *             if one or multiple errors occur during compilation
	 */
	public BuildSet createAllProjectsBuildSet(List<File> searchPaths, Set<N4JSProjectName> shadowedProjectNames)
			throws N4JSCompileException {
		// make absolute, since downstream URI conversion doesn't work if relative directory only.
		List<File> absProjectPaths = headlessHelper.toAbsoluteFileList(searchPaths);

		// Collect all projects in first Level.
		List<File> projectPaths = headlessHelper.collectAllProjectPaths(absProjectPaths);

		return createBuildSet(searchPaths, projectPaths, Collections.emptyList(), shadowedProjectNames);
	}

	/**
	 * Creates a {@link BuildSet} for compiling a list of projects or source files ({@code projectsPaths} and
	 * {@code singleSourceFiles} respectively).
	 *
	 * Uses {@code searchPaths} for looking up dependency projects.
	 *
	 * @param searchPaths
	 *            where to search for dependent projects.
	 * @param projectPaths
	 *            the projects to compile. the base folder of each project must be provided.
	 * @param singleSourceFiles
	 *            if non-empty limit compilation to the sources files listed here
	 * @throws N4JSCompileException
	 *             if one or multiple errors occur during compilation
	 */
	public BuildSet createBuildSet(List<File> searchPaths, List<File> projectPaths, List<File> singleSourceFiles,
			Set<N4JSProjectName> shadowedProjectNames) throws N4JSCompileException {
		logBuildSetComputerConfiguration(searchPaths, projectPaths, singleSourceFiles);
		return collectProjects(searchPaths, projectPaths, singleSourceFiles, shadowedProjectNames);
	}

	/**
	 * Logs some debug information about the user-provided project discovery arguments (only if {@link #logger} is
	 * configured in debug mode).
	 *
	 * This includes the specified project locations, project search paths and potential paths of single-file arguments.
	 *
	 * @param searchPaths
	 *            where to search for dependent projects.
	 * @param projectPaths
	 *            the projects to compile. the base folder of each project must be provided.
	 * @param singleSourceFiles
	 *            if non-empty limit compilation to the sources files listed here
	 */
	private void logBuildSetComputerConfiguration(List<File> searchPaths, List<File> projectPaths,
			List<File> singleSourceFiles) {
		if (logger.isCreateDebugOutput()) {
			logger.debug("Computing build set with the following arguments");
			logger.debug("  Search paths: " + Joiner.on(", ").join(searchPaths));
			logger.debug("  Projects    : " + Joiner.on(", ").join(projectPaths));
			logger.debug("  Source files: " + Joiner.on(", ").join(singleSourceFiles));
		}
	}

	/**
	 * Collects the projects to compile and finds their dependencies in the given search paths.
	 *
	 * Does not register the collected {@link IN4JSProject N4JS projects} with any workspace yet.
	 *
	 * @param searchPaths
	 *            where to search for dependent projects.
	 * @param projectPaths
	 *            the projects to compile. the base folder of each project must be provided.
	 * @param singleSourceFiles
	 *            if non-empty limit compilation to the sources files listed here
	 * @return an instance of {@link BuildSet} containing the collected projects and a filter to apply if single source
	 *         files were requested to be compiled
	 * @throws N4JSCompileException
	 *             if an error occurs while registering the projects
	 */
	private BuildSet collectProjects(List<File> searchPaths, List<File> projectPaths,
			List<File> singleSourceFiles, Set<N4JSProjectName> shadowedProjectNames) throws N4JSCompileException {

		// Make absolute, since downstream URI conversion doesn't work if relative dir only.
		List<File> absSearchPaths = headlessHelper.toAbsoluteFileList(searchPaths);
		List<File> absProjectPaths = headlessHelper.toAbsoluteFileList(projectPaths);
		List<File> absSingleSourceFiles = headlessHelper.toAbsoluteFileList(singleSourceFiles);

		// Discover projects in search paths.
		List<File> discoveredProjectLocations = headlessHelper.collectAllProjectPaths(absSearchPaths);

		// Discover projects for single source files.
		List<File> singleSourceProjectLocations = findProjectsForSingleFiles(absSingleSourceFiles);

		// Register single-source projects.
		List<File> absRequestedProjectLocations = Collections2.concatUnique(absProjectPaths,
				singleSourceProjectLocations);

		// Convert absolute locations to file URIs.
		List<FileURI> requestedProjectURIs = headlessHelper.createFileURIs(absRequestedProjectLocations);
		List<FileURI> discoveredProjectURIs = headlessHelper.createFileURIs(discoveredProjectLocations);

		// Obtain the projects and store them.
		List<IN4JSProject> requestedProjects = headlessHelper.getN4JSProjects(requestedProjectURIs);
		List<IN4JSProject> discoveredProjects = headlessHelper.getN4JSProjects(discoveredProjectURIs);

		// Resolve shadowing among discovered and requested projects
		LinkedHashMap<N4JSProjectName, IN4JSProject> discoveredResolvedMap = new LinkedHashMap<>();
		for (IN4JSProject prj : discoveredProjects) {
			discoveredResolvedMap.put(prj.getProjectName(), prj);
		}
		for (IN4JSProject prj : requestedProjects) {
			discoveredResolvedMap.remove(prj.getProjectName());
		}
		List<IN4JSProject> discoveredResolvedList = new LinkedList<>(discoveredResolvedMap.values());

		// Filter out shadowed projects
		Predicate<IN4JSProject> pred = p -> shadowedProjectNames.contains(p.getProjectName());
		requestedProjects.removeIf(pred);
		discoveredResolvedList.removeIf(pred);

		// Create a filter that applies only to the given single source files if any were requested to be compiled.
		Predicate<FileURI> resourceFilter;
		if (absSingleSourceFiles.isEmpty()) {
			resourceFilter = Predicates.alwaysTrue();
		} else {
			Set<FileURI> singleSourceURIs = new HashSet<>(headlessHelper.createFileURIs(absSingleSourceFiles));
			resourceFilter = Predicates.in(singleSourceURIs);
		}

		return new BuildSet(requestedProjects, discoveredResolvedList, resourceFilter);
	}

	/**
	 * Collects the projects containing the given single source files.
	 *
	 * @param sourceFiles
	 *            the list of single source files
	 * @return list of N4JS project locations
	 * @throws N4JSCompileException
	 *             if no project cannot be found for one of the given files
	 */
	private List<File> findProjectsForSingleFiles(List<File> sourceFiles) throws N4JSCompileException {

		Set<FileURI> result = Sets.newLinkedHashSet();

		for (File sourceFile : sourceFiles) {
			FileURI sourceFileURI = new FileURI(sourceFile);
			FileURI projectURI = sourceFileURI.getProjectRoot();
			if (projectURI == null) {
				throw new N4JSCompileException("No project for file '" + sourceFile.toString() + "' found.");
			}
			result.add(projectURI);
		}

		// convert back to Files:
		return result.stream().map(u -> new File(u.toFileString()))
				.collect(Collectors.toList());
	}

}
