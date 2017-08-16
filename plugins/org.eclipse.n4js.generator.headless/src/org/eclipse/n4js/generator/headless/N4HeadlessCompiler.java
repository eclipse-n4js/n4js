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
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.generator.CompositeGenerator;
import org.eclipse.n4js.generator.common.CompilerDescriptor;
import org.eclipse.n4js.generator.common.GeneratorException;
import org.eclipse.n4js.generator.headless.logging.IHeadlessLogger;
import org.eclipse.n4js.internal.FileBasedWorkspace;
import org.eclipse.n4js.internal.N4FilebasedWorkspaceResourceSetContainerState;
import org.eclipse.n4js.internal.N4JSBrokenProjectException;
import org.eclipse.n4js.internal.N4JSModel;
import org.eclipse.n4js.internal.N4JSProject;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.IN4JSSourceContainer;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.resource.OrderedResourceDescriptionsData;
import org.eclipse.n4js.utils.Lazy;
import org.eclipse.n4js.utils.ResourceType;
import org.eclipse.n4js.utils.collections.Collections2;
import org.eclipse.n4js.utils.io.FileUtils;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.generator.JavaIoFileSystemAccess;
import org.eclipse.xtext.generator.OutputConfiguration;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.resource.containers.DelegatingIAllContainerAdapter;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.validation.Issue;

import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.google.common.collect.TreeMultimap;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * Entry for headless compilation.
 *
 * This class has three ways of operation which all map down to a single algorithm implemented in
 * {@link #compileProjects(List, List, List, IssueAcceptor)}. All other compileXXXX methods call this algorithm
 * providing the correct content of the arguments.
 *
 * <ol>
 * <li>compile "single file" takes a (list of) source-file(s) to compile and just compiles these if possible
 * {@link #compileSingleFile(File)}, {@link #compileSingleFiles(List, IssueAcceptor)},
 * {@link #compileSingleFiles(List, List, IssueAcceptor)}
 * <li>compile "projects" takes a list of project-location and compiles exactly them.
 * {@link #compileProjects(List, IssueAcceptor)}, {@link #compileProjects(List, List, IssueAcceptor)}
 * <li>compile "all project" takes a list of folders and compiles each project found as direct content of one of the
 * folders. {@link #compileAllProjects(List, IssueAcceptor)}
 * </ol>
 *
 * The way how the compiler behaves can be configured through flags like {@link #keepOnCompiling},
 * {@link #processTestCode}, {@link #compileSourceCode}
 */
public class N4HeadlessCompiler {

	/** The Generator to compile with */
	private final CompositeGenerator compositeGenerator;

	/** Abstraction to the filesystem, used by the Generator */
	private final JavaIoFileSystemAccess fsa;

	/** Compares two N4JS projects. Used for sorting and comparing project dependencies. */
	private final static Comparator<IN4JSProject> n4JSProjComparator = new Comparator<IN4JSProject>() {
		@Override
		public int compare(IN4JSProject o1, IN4JSProject o2) {
			return o1.getProjectId().compareTo(o2.getProjectId());
		}
	};

	/** N4JS-Implementation of a workspace without OSGI */
	@Inject
	private FileBasedWorkspace fbWorkspace;

	@Inject
	private N4JSModel n4jsModel;

	@Inject
	private IN4JSCore n4jsCore;

	@Inject
	private IHeadlessLogger logger;

	@Inject
	private N4FilebasedWorkspaceResourceSetContainerState rsbAcs;

	/** provider to create correct ResourceSet instances */
	@Inject
	private Provider<XtextResourceSet> xtextResourceSetProvider;

	@Inject
	private ClassLoader classLoader;

	/**
	 * original outputConfiguration, possibly requires a reconfiguration based on the project-path.
	 * {@link JavaIoFileSystemAccess#getFile} relies on the assumption, that the basepath (for new File) is the current
	 * project. If that assumption doesn't hold, we need to be creative with the output-configuration.
	 */
	private final Map<String, OutputConfiguration> outputs;

	/** if set to true should try to compile even if errors are in some projects */
	private boolean keepOnCompiling = false;

	/** if set to false all source-containers of type 'test' are not passed to the generator */
	private boolean processTestCode = true;

	/** if set to false all source-containers of type 'source' are not passed to the generator */
	private boolean compileSourceCode = true;

	/** if set additional log will be written to this filename */
	private String logFile = null;

	/**
	 * Private constructor to prevent accidental instantiation.
	 */
	@Inject
	private N4HeadlessCompiler(CompositeGenerator compositeGenerator, JavaIoFileSystemAccess fsa) {
		this.compositeGenerator = compositeGenerator;
		this.fsa = fsa;
		this.outputs = buildInitialOutputConfiguration();
		fsa.setOutputConfigurations(this.outputs);
	}

	private Map<String, OutputConfiguration> buildInitialOutputConfiguration() {
		Map<String, OutputConfiguration> result = new HashMap<>();
		for (CompilerDescriptor desc : compositeGenerator.getCompilerDescriptors()) {
			result.put(desc.getIdentifier(), desc.getOutputConfiguration());
		}
		return result;
	}

	/*
	 * ===============================================================================================================
	 *
	 * ENTRY POINTS TO LAUNCH COMPILATION
	 *
	 * ===============================================================================================================
	 */

	/**
	 * Compile one single source file.
	 *
	 * @param singleSourceFile
	 *            if non-empty limit compilation to the sources files listed here
	 * @throws N4JSCompileException
	 *             if one or multiple errors occur during compilation
	 */
	public void compileSingleFile(File singleSourceFile) throws N4JSCompileException {
		compileSingleFile(singleSourceFile, new DismissingIssueAcceptor());
	}

	/**
	 * Compile one single source file.
	 *
	 * @param singleSourceFile
	 *            if non-empty limit compilation to the sources files listed here
	 * @param issueAcceptor
	 *            the issue acceptor that can be used to collect or evaluate the issues occurring during compilation
	 * @throws N4JSCompileException
	 *             if one or multiple errors occur during compilation
	 */
	public void compileSingleFile(File singleSourceFile, IssueAcceptor issueAcceptor)
			throws N4JSCompileException {
		compileSingleFiles(Arrays.asList(singleSourceFile));
	}

	/**
	 * Compile multiple single source files.
	 *
	 * @param singleSourceFiles
	 *            if non-empty limit compilation to the sources files listed here
	 * @throws N4JSCompileException
	 *             if one or multiple errors occur during compilation
	 */
	public void compileSingleFiles(List<File> singleSourceFiles) throws N4JSCompileException {
		compileSingleFiles(singleSourceFiles, new DismissingIssueAcceptor());
	}

	/**
	 * Compile multiple single source files.
	 *
	 * @param singleSourceFiles
	 *            if non-empty limit compilation to the sources files listed here
	 * @param issueAcceptor
	 *            the issue acceptor that can be used to collect or evaluate the issues occurring during compilation
	 * @throws N4JSCompileException
	 *             if one or multiple errors occur during compilation
	 */
	public void compileSingleFiles(List<File> singleSourceFiles, IssueAcceptor issueAcceptor)
			throws N4JSCompileException {
		compileSingleFiles(Collections.emptyList(), singleSourceFiles, issueAcceptor);
	}

	/**
	 * Compile multiple single source files.
	 *
	 * @param searchPaths
	 *            where to search for dependent projects.
	 * @param singleSourceFiles
	 *            if non-empty limit compilation to the sources files listed here
	 * @throws N4JSCompileException
	 *             if one or multiple errors occur during compilation
	 */
	public void compileSingleFiles(List<File> searchPaths, List<File> singleSourceFiles)
			throws N4JSCompileException {
		compileSingleFiles(searchPaths, singleSourceFiles, new DismissingIssueAcceptor());
	}

	/**
	 * Compile multiple single source files.
	 *
	 * @param searchPaths
	 *            where to search for dependent projects.
	 * @param singleSourceFiles
	 *            if non-empty limit compilation to the sources files listed here
	 * @param issueAcceptor
	 *            the issue acceptor that can be used to collect or evaluate the issues occurring during compilation
	 * @throws N4JSCompileException
	 *             if one or multiple errors occur during compilation
	 */
	public void compileSingleFiles(List<File> searchPaths, List<File> singleSourceFiles, IssueAcceptor issueAcceptor)
			throws N4JSCompileException {
		compileProjects(searchPaths, Collections.emptyList(), singleSourceFiles, issueAcceptor);
	}

	/**
	 * Compile a list of projects. Main algorithm.
	 *
	 * @param searchPaths
	 *            where to search for the projects to compile.
	 * @throws N4JSCompileException
	 *             if one or multiple errors occur during compilation
	 */
	public void compileAllProjects(List<File> searchPaths) throws N4JSCompileException {
		compileAllProjects(searchPaths, new DismissingIssueAcceptor());
	}

	/**
	 * Compile a list of projects. Main algorithm.
	 *
	 * @param searchPaths
	 *            where to search for the projects to compile.
	 * @param issueAcceptor
	 *            the issue acceptor that can be used to collect or evaluate the issues occurring during compilation
	 * @throws N4JSCompileException
	 *             if one or multiple errors occur during compilation
	 */
	public void compileAllProjects(List<File> searchPaths, IssueAcceptor issueAcceptor)
			throws N4JSCompileException {
		// make absolute, since downstream URI conversion doesn't work if relative directory only.
		List<File> absProjectPaths = HeadlessHelper.toAbsoluteFileList(searchPaths);

		// Collect all projects in first Level.
		List<File> projectPaths = HeadlessHelper.collectAllProjectPaths(absProjectPaths);

		compileProjects(searchPaths, projectPaths, Collections.emptyList(), issueAcceptor);
	}

	/**
	 * Compile a list of projects. Dependencies will be searched in the current directory.
	 *
	 * @param projectPaths
	 *            the projects to compile. the base folder of each project must be provided.
	 * @throws N4JSCompileException
	 *             if one or multiple errors occur during compilation
	 */
	public void compileProjects(List<File> projectPaths) throws N4JSCompileException {
		compileProjects(Arrays.asList(new File(".")), projectPaths, Collections.emptyList(),
				new DismissingIssueAcceptor());
	}

	/**
	 * Compile a list of projects. Dependencies will be searched in the current directory.
	 *
	 * @param projectPaths
	 *            the projects to compile. the base folder of each project must be provided.
	 * @param issueAcceptor
	 *            the issue acceptor that can be used to collect or evaluate the issues occurring during compilation
	 * @throws N4JSCompileException
	 *             if one or multiple errors occur during compilation
	 */
	public void compileProjects(List<File> projectPaths, IssueAcceptor issueAcceptor)
			throws N4JSCompileException {
		compileProjects(Arrays.asList(new File(".")), projectPaths, Collections.emptyList(), issueAcceptor);
	}

	/**
	 * Compile a list of projects.
	 *
	 * @param searchPaths
	 *            where to search for dependent projects.
	 * @param projectPaths
	 *            the projects to compile. the base folder of each project must be provided.
	 * @throws N4JSCompileException
	 *             if one or multiple errors occur during compilation
	 */
	public void compileProjects(List<File> searchPaths, List<File> projectPaths)
			throws N4JSCompileException {
		compileProjects(searchPaths, projectPaths, new DismissingIssueAcceptor());
	}

	/**
	 * Compile a list of projects.
	 *
	 * @param searchPaths
	 *            where to search for dependent projects.
	 * @param projectPaths
	 *            the projects to compile. the base folder of each project must be provided.
	 * @param issueAcceptor
	 *            the issue acceptor that can be used to collect or evaluate the issues occurring during compilation
	 * @throws N4JSCompileException
	 *             if one or multiple errors occur during compilation
	 */
	public void compileProjects(List<File> searchPaths, List<File> projectPaths, IssueAcceptor issueAcceptor)
			throws N4JSCompileException {
		compileProjects(searchPaths, projectPaths, Collections.emptyList(), issueAcceptor);
	}

	/**
	 * Compile a list of projects. Delegates to {@link #compileProjects(List, List, List, IssueAcceptor)}.
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
	public void compileProjects(List<File> searchPaths, List<File> projectPaths, List<File> singleSourceFiles)
			throws N4JSCompileException {
		compileProjects(searchPaths, projectPaths, singleSourceFiles, new DismissingIssueAcceptor());
	}

	/**
	 * Encapsulates the result of {@link N4HeadlessCompiler#collectAndRegisterProjects(List, List, List)}.
	 */
	private static class BuildSet {

		/**
		 * The projects which the user explicitly requested to be compiled. If the user requested compilation of
		 * specific single files, then this list contains the projects containing the files.
		 */
		public final List<N4JSProject> requestedProjects;

		/**
		 * The projects which were discovered as dependencies of the above projects, without having been requested to be
		 * compiled by the user. In other words, these projects are only being compiled because a requested project
		 * depends on them.
		 */
		public final List<N4JSProject> discoveredProjects;

		/**
		 * A predicate that indicates whether or not a given resource, identified by its URI, should be processed. If
		 * the user requested compilation of specific single files, then this predicate applies only to those files, and
		 * no others. In all other cases, the predicate applies to every file, i.e., it always returns
		 * <code>true</code>.
		 */
		public final Predicate<URI> resourceFilter;

		public BuildSet(List<N4JSProject> requestedProjects, List<N4JSProject> discoveredProjects,
				Predicate<URI> projectFilter) {
			this.requestedProjects = requestedProjects;
			this.discoveredProjects = discoveredProjects;
			this.resourceFilter = projectFilter;
		}

	}

	/**
	 * Clean the output folders of all <b>direct</b> projects found in search paths.
	 *
	 * @param searchPaths
	 *            where to search for projects to be cleaned.
	 * @throws N4JSCompileException
	 *             if one or multiple errors occur.
	 */
	public void cleanProjectsInSearchPath(List<File> searchPaths)
			throws N4JSCompileException {
		List<File> absProjectPaths = HeadlessHelper.toAbsoluteFileList(searchPaths);
		// Collect all projects in first Level.
		List<File> projectPaths = HeadlessHelper.collectAllProjectPaths(absProjectPaths);
		cleanProjects(projectPaths);
	}

	/**
	 * Clean the output folders of projects.
	 *
	 * @param projectPaths
	 *            the projects to be cleaned.
	 * @throws N4JSCompileException
	 *             if one or multiple errors occur.
	 */
	public void cleanProjects(List<File> projectPaths)
			throws N4JSCompileException {
		List<URI> projectURIs = convertProjectPathsToProjectURIs(projectPaths);
		registerProjectsToFileBasedWorkspace(projectURIs);
		List<N4JSProject> projectsToClean = getN4JSProjects(projectURIs);
		projectsToClean.forEach(project -> {
			cleanProject(project);
		});
	}

	private void cleanProject(N4JSProject project) {
		String outputFolder = project.getOutputPath();
		Path outputPath = project.getLocationPath().resolve(outputFolder);
		FileUtils.cleanFolder(outputPath.toFile());
	}

	private List<URI> convertProjectPathsToProjectURIs(List<File> projectPaths) throws N4JSCompileException {
		List<File> absProjectPaths = HeadlessHelper.toAbsoluteFileList(projectPaths);
		// Convert absolute locations to file URIs.
		List<URI> projectURIs = createFileURIs(absProjectPaths);
		return projectURIs;
	}

	private void registerProjectsToFileBasedWorkspace(Iterable<URI> projectURIs) throws N4JSCompileException {
		// Register all projects with the file based workspace.
		for (URI projectURI : projectURIs) {
			try {
				fbWorkspace.registerProject(projectURI);
			} catch (N4JSBrokenProjectException e) {
				throw new N4JSCompileException("Unable to register project '" + projectURI + "'", e);
			}
		}
	}

	/**
	 * Compile a list of projects. This method controls the actual build process. All other <code>compile*</code>
	 * methods are just convenience overloads that delegate to this method.
	 *
	 * @param searchPaths
	 *            where to search for dependent projects.
	 * @param projectPaths
	 *            the projects to compile. the base folder of each project must be provided.
	 * @param singleSourceFiles
	 *            if non-empty limit compilation to the sources files listed here
	 * @param issueAcceptor
	 *            the issue acceptor that can be used to collect or evaluate the issues occurring during compilation
	 * @throws N4JSCompileException
	 *             if one or multiple errors occur during compilation
	 */
	public void compileProjects(List<File> searchPaths, List<File> projectPaths, List<File> singleSourceFiles,
			IssueAcceptor issueAcceptor)
			throws N4JSCompileException {

		printCompileArguments(searchPaths, projectPaths, singleSourceFiles);

		BuildSet buildSet = collectAndRegisterProjects(searchPaths, projectPaths, singleSourceFiles);

		List<N4JSProject> allProjects = Collections2.concatUnique(buildSet.discoveredProjects,
				buildSet.requestedProjects);
		List<N4JSProject> requestedProjects = buildSet.requestedProjects;
		Predicate<URI> singleSourceFilter = buildSet.resourceFilter;

		configureResourceSetContainerState(allProjects);

		final List<MarkedProject> buildOrder = computeBuildOrder(allProjects, requestedProjects);
		printBuildOrder(buildOrder);

		processProjects(buildOrder, singleSourceFilter, issueAcceptor);
	}

	/*
	 * ===============================================================================================================
	 *
	 * COLLECTING PROJECTS FROM USER-PROVIDED ARGUMENTS
	 *
	 * ===============================================================================================================
	 */

	/**
	 * Collects the projects to compile and finds their dependencies in the given search paths and registers them with
	 * the file-based workspace.
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
	private BuildSet collectAndRegisterProjects(List<File> searchPaths, List<File> projectPaths,
			List<File> singleSourceFiles) throws N4JSCompileException {

		// Make absolute, since downstream URI conversion doesn't work if relative dir only.
		List<File> absSearchPaths = HeadlessHelper.toAbsoluteFileList(searchPaths);
		List<File> absProjectPaths = HeadlessHelper.toAbsoluteFileList(projectPaths);
		List<File> absSingleSourceFiles = HeadlessHelper.toAbsoluteFileList(singleSourceFiles);

		// Discover projects in search paths.
		List<File> discoveredProjectLocations = HeadlessHelper.collectAllProjectPaths(absSearchPaths);

		// Discover projects for single source files.
		List<File> singleSourceProjectLocations = findProjectsForSingleFiles(absSingleSourceFiles);

		// Register single-source projects as to be compiled as well.
		List<File> absRequestedProjectLocations = Collections2.concatUnique(absProjectPaths,
				singleSourceProjectLocations);

		// Convert absolute locations to file URIs.
		List<URI> requestedProjectURIs = createFileURIs(absRequestedProjectLocations);
		List<URI> discoveredProjectURIs = createFileURIs(discoveredProjectLocations);

		// Obtain the projects and store them.
		List<N4JSProject> requestedProjects = getN4JSProjects(requestedProjectURIs);
		List<N4JSProject> discoveredProjects = getN4JSProjects(discoveredProjectURIs);

		// Register all projects with the file based workspace.
		registerProjectsToFileBasedWorkspace(Iterables.concat(requestedProjectURIs, discoveredProjectURIs));

		// Create a filter that applies only to the given single source files if any were requested to be compiled.
		Predicate<URI> resourceFilter;
		if (absSingleSourceFiles.isEmpty()) {
			resourceFilter = u -> true;
		} else {
			Set<URI> singleSourceURIs = new HashSet<>(createFileURIs(absSingleSourceFiles));
			resourceFilter = u -> singleSourceURIs.contains(u);
		}

		return new BuildSet(requestedProjects, discoveredProjects, resourceFilter);
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
	private List<File> findProjectsForSingleFiles(List<File> sourceFiles)
			throws N4JSCompileException {

		Set<URI> result = Sets.newLinkedHashSet();

		for (File sourceFile : sourceFiles) {
			URI sourceFileURI = URI.createFileURI(sourceFile.toString());
			URI projectURI = fbWorkspace.findProjectWith(sourceFileURI);
			if (projectURI == null) {
				throw new N4JSCompileException("No project for file '" + sourceFile.toString() + "' found.");
			}
			result.add(projectURI);
		}

		// convert back to Files:
		return result.stream().map(u -> new File(u.toFileString())).collect(Collectors.toList());
	}

	/**
	 * Convert the given list of files to a list of URIs. Each file is converted to a URI by means of
	 * {@link URI#createFileURI(String)}.
	 *
	 * @param files
	 *            the files to convert
	 * @return the list of URIs
	 */
	private List<URI> createFileURIs(List<File> files) {
		return files.stream().map(f -> URI.createFileURI(f.toString())).collect(Collectors.toList());
	}

	/**
	 * Returns a list of {@link N4JSProject} instances representing the projects at the given locations.
	 *
	 * @param projectURIs
	 *            the URIs to process
	 * @return a list of projects at the given URIs
	 */
	private List<N4JSProject> getN4JSProjects(List<URI> projectURIs) {
		return projectURIs.stream().map(u -> n4jsModel.getN4JSProject(u)).collect(Collectors.toList());
	}

	private void configureResourceSetContainerState(final List<N4JSProject> allProjects) {
		// a container is a project.
		List<String> containers = new LinkedList<>();
		BiMap<String, N4JSProject> container2project = HashBiMap.create();

		// the URIs of all resources directly contained in a project/container.
		Multimap<String, URI> container2Uris = HashMultimap.create();

		for (N4JSProject project : allProjects) {
			String container = FileBasedWorkspace.N4FBPRJ + project.getLocation();
			container2project.put(container, project);
			containers.add(container);

			for (IN4JSSourceContainer sourceContainer : project.getSourceContainers()) {
				Iterables.addAll(container2Uris.get(container), sourceContainer);
			}
		}

		// Define the Mapping of Resources (URIs to Container === Projects),
		rsbAcs.configure(containers, container2Uris);
	}

	/*
	 * ===============================================================================================================
	 *
	 * COMPUTING THE PROJECT BUILD ORDER AND INITIALIZING THE MARKINGS
	 *
	 * ===============================================================================================================
	 */

	/**
	 * Sort in build-order. Wraps each element of {@code toSort} with {@link MarkedProject} and applies all
	 * {@code buildMarker} for which the element is a (transitively) declared dependency
	 *
	 * @param allProjectsToCompile
	 *            unsorted projects, these include the dependencies as well as the projects to compile
	 * @param requestedProjects
	 *            only the projects which were requested to be compiled
	 * @return sorted projects: earlier projects don't depend on later
	 */
	private static List<MarkedProject> computeBuildOrder(List<? extends IN4JSProject> allProjectsToCompile,
			List<? extends IN4JSProject> requestedProjects) {

		// This algorithm only operates on the following map of marked projects.
		Map<IN4JSProject, MarkedProject> markedProjects = new HashMap<>();
		allProjectsToCompile.stream().forEach(project -> markedProjects.put(project, new MarkedProject(project)));

		// Maps a project to the projects that depend on it.
		Multimap<IN4JSProject, IN4JSProject> pendencies = TreeMultimap.create(n4JSProjComparator, n4JSProjComparator);

		// Maps a project to the projects it depends on.
		Multimap<IN4JSProject, IN4JSProject> dependencies = TreeMultimap.create(n4JSProjComparator, n4JSProjComparator);

		// Sorted set of projects without dependencies (starting points) to determine their order.
		SortedSet<IN4JSProject> independentProjects = new TreeSet<>(n4JSProjComparator);

		// Initialize preconditions, dependencies, and independent projects.
		computeDependencyGraph(markedProjects.keySet(), pendencies, dependencies, independentProjects);

		// Mark the projects to build, using a set to remove duplicates.
		for (IN4JSProject project : new HashSet<>(requestedProjects))
			markDependencies(project, project, markedProjects, dependencies);

		return computeBuildOrderDepthFirst(markedProjects, pendencies, dependencies, independentProjects);
	}

	/**
	 * Recursively marks the dependency subgraph by applying the given marker to each transitive dependency of the given
	 * markee. Assumes that there are no cyclic dependencies in the given dependency map.
	 *
	 * @param marker
	 *            the marker to apply
	 * @param markee
	 *            the project to be marked
	 * @param markables
	 *            lookup map for the markable projects
	 * @param dependencies
	 *            maps a project to the projects it depends on
	 */
	private static void markDependencies(IN4JSProject marker, IN4JSProject markee,
			Map<IN4JSProject, MarkedProject> markables,
			Multimap<IN4JSProject, IN4JSProject> dependencies) {

		// Set the mark
		markables.get(markee).markWith(marker);

		// Recursively apply to all dependencies of the given markee
		for (IN4JSProject dependency : dependencies.get(markee))
			markDependencies(marker, dependency, markables, dependencies);
	}

	/**
	 * Computes a dependency graph for the given projects and stores the results in the given data structures.
	 *
	 * @param projects
	 *            the projects to compute the dependency graph for
	 * @param pendencies
	 *            maps projects to the projects that depend on them
	 * @param dependencies
	 *            maps projects to the projects they depend on
	 * @param independent
	 *            projects without dependencies
	 */
	private static void computeDependencyGraph(Set<IN4JSProject> projects,
			Multimap<IN4JSProject, IN4JSProject> pendencies,
			Multimap<IN4JSProject, IN4JSProject> dependencies, Collection<IN4JSProject> independent) {

		// already processed projects
		Set<IN4JSProject> visited = new HashSet<>(projects.size());

		// Populate dependencies
		for (IN4JSProject project : projects)
			computeDependencyGraph(project, visited, pendencies, dependencies, independent);
	}

	/**
	 * Recursive part of {@link #computeDependencyGraph(Set, Multimap, Multimap, Collection)}. The given project is
	 * processed only if it has not been processed already. If that is the case, it will either be added to the given
	 * list of independent projects, or a dependency from the given project to each of the projects it depends on is
	 * added. Finally, the algorithm calls itself for each dependency.
	 *
	 * @param project
	 *            the project being processed
	 * @param visitedProjects
	 *            set of projects already processed
	 * @param pendencies
	 *            maps projects to the projects that depend on them
	 * @param dependencies
	 *            maps projects to the projects they depend on
	 * @param independent
	 *            projects without dependencies
	 */
	private static void computeDependencyGraph(IN4JSProject project, Set<IN4JSProject> visitedProjects,
			Multimap<IN4JSProject, IN4JSProject> pendencies,
			Multimap<IN4JSProject, IN4JSProject> dependencies, Collection<IN4JSProject> independent) {

		if (!visitedProjects.add(project))
			return;

		ImmutableList<? extends IN4JSProject> pendingProjects = project.getDependenciesAndImplementedApis();
		if (pendingProjects.isEmpty()) {
			independent.add(project);
		} else {
			for (IN4JSProject pendingProject : pendingProjects) {
				pendencies.put(pendingProject, project);
				dependencies.put(project, pendingProject);

				computeDependencyGraph(pendingProject, visitedProjects, pendencies, dependencies, independent);
			}
		}
	}

	/**
	 * Compute the build order by processing the dependency graph in a depth first manner. We use a depth first
	 * traversal here because it is more likely to result in projects being unloaded as early as possible.
	 *
	 * <p>
	 * In a breath first traversal, we would first compile all the dependencies, and we would most likely get to the
	 * leaves of the graph last. The leaves represent projects on which no other projects depend. This results in a
	 * build that takes up more and more memory as it proceeds since projects will only be unloaded in the very end,
	 * when the leafs are processed.
	 * </p>
	 *
	 * <p>
	 * In a depth first traversal, we attempt to process the leafs as early as possible. However, we have to consider
	 * that not all dependencies have already been processed when we reach a leaf, so there is no guarantee that a depth
	 * first traversal will really free projects earlier than a breadth first traversal, but it is more likely.
	 * </p>
	 *
	 * <p>
	 * When we reach a leaf with unvisited dependencies, we return to its parent and continue with its siblings. The
	 * leaf will only be added to the build order when all of its dependencies have already been visited in the normal
	 * depth first traversal.
	 * </p>
	 *
	 * <p>
	 * It may be possible to optimize this by changing the traversal order when we reach a leaf with unvisited
	 * dependencies. We might choose to attempt to visit those dependencies right away by following them "upwards" from
	 * the leaf to the unvisited dependencies until the leaf can finally be unloaded. However, at this point we decided
	 * not to implement this optimization due to its inherent complexities. Furthermore, it is unclear whether it truly
	 * delivers any significant improvements for memory consumption during a large build.
	 * </p>
	 *
	 * @param markedProjects
	 *            the projects to be compiled
	 * @param pendencies
	 *            maps projects to the projects that depend on them
	 * @param dependencies
	 *            maps projects to the projects they depend on
	 * @param rootProjects
	 *            projects without dependencies
	 * @return a build order in which each project only depends on projects to its left
	 */
	private static List<MarkedProject> computeBuildOrderDepthFirst(Map<IN4JSProject, MarkedProject> markedProjects,
			Multimap<IN4JSProject, IN4JSProject> pendencies, Multimap<IN4JSProject, IN4JSProject> dependencies,
			Collection<IN4JSProject> rootProjects) {
		List<MarkedProject> result = new LinkedList<>();

		for (IN4JSProject rootProject : rootProjects)
			computeBuildOrderDepthFirst(rootProject, markedProjects, pendencies, dependencies, result);

		return result;
	}

	/**
	 * Recursive part of {@link #computeBuildOrderDepthFirst(Map, Multimap, Multimap, Collection)}. If all dependencies
	 * of the given project have already been processed, it is added to the build order. Then, all projects that depend
	 * on the given project are processed recursively.
	 *
	 * @param project
	 *            the project to process
	 * @param markedProjects
	 *            the marked projects
	 * @param pendencies
	 *            maps projects to the projects that depend on them
	 * @param dependencies
	 *            maps projects to the projects they depend on
	 * @param result
	 *            the build order being computed
	 */
	private static void computeBuildOrderDepthFirst(IN4JSProject project,
			Map<IN4JSProject, MarkedProject> markedProjects, Multimap<IN4JSProject, IN4JSProject> pendencies,
			Multimap<IN4JSProject, IN4JSProject> dependencies, List<MarkedProject> result) {

		// once all dependencies of the current project have been processed, we can add it to the build and
		// process its children.
		if (dependencies.get(project).isEmpty()) {
			// The current project is ready to be processed.
			result.add(markedProjects.get(project));

			// Remove this project from the dependencies of all pending projects.
			for (IN4JSProject dependentProject : pendencies.get(project)) {
				dependencies.get(dependentProject).remove(project);

				// Now process the pending project itself.
				computeBuildOrderDepthFirst(dependentProject, markedProjects, pendencies, dependencies, result);
			}
		}
	}

	/*
	 * ===============================================================================================================
	 *
	 * PROJECT PROCESSING
	 *
	 * ===============================================================================================================
	 */

	/**
	 * Process the given projects in the given order. Processing entails the following steps.
	 *
	 * <ul>
	 * <li>Create a resource set to host all resources during compilation.</li>
	 * <li>For each project to compile:
	 * <ul>
	 * <li>Load the project.</li>
	 * <li>Validate and compile the project.</li>
	 * <li>Unload the ASTs and resource caches of every resource in the project.</li>
	 * <li>Unload every project whose pending projects have all been processed already.</li>
	 * </ul>
	 * </li>
	 * </ul>
	 *
	 * If an error occurs during compilation, it is either throw immediately or it is thrown after the project has been
	 * processed in full, depending on the value of {@link #isKeepOnCompiling()}.
	 *
	 * @param projects
	 *            the projects to compile. This list contains both the projects passed in by the user and the discovered
	 *            dependencies of those projects
	 * @param filter
	 *            a filter to decide whether or not a given resource should be compiled
	 * @param issueAcceptor
	 *            the issue acceptor that can be used to collect or evaluate the issues occurring during compilation
	 * @throws N4JSCompileException
	 *             if an error occurs during compilation
	 */
	private void processProjects(List<MarkedProject> projects, final Predicate<URI> filter,
			IssueAcceptor issueAcceptor)
			throws N4JSCompileException {

		Lazy<N4JSCompoundCompileException> collectedErrors = Lazy
				.create(() -> new N4JSCompoundCompileException("Errors during compiling."));
		N4ProgressStateRecorder recorder = new N4ProgressStateRecorder();

		ResourceSet resourceSet = createResourceSet();
		List<MarkedProject> loadedProjects = new LinkedList<>();

		for (MarkedProject markedProject : projects) {
			// Only load a project if it was requested to be compile or if other requested projects depend on it.
			if (markedProject.hasMarkers()) {
				recorder.markProcessing(markedProject.project);
				configureFSA(markedProject.project);

				try {
					// Add to loaded projects immediately so that the project gets unloaded even if loading fails.
					loadedProjects.add(markedProject);

					loadProject(markedProject, resourceSet, recorder, issueAcceptor);
					validateProject(markedProject, recorder, issueAcceptor);

					// generate only if it has itself as marker and non-external
					if (markedProject.hasMarker(markedProject.project) && !markedProject.project.isExternal()) {
						generateProject(markedProject, resourceSet, filter, recorder);
					}
				} catch (N4JSCompileErrorException e) {
					recorder.compileException(e);
					if (isKeepOnCompiling()) {
						collectedErrors.get().add(e);
					} else {
						throw e;
					}
				} finally {
					markedProject.unloadASTAndClearCaches();
					unmarkAndUnloadProjects(loadedProjects, markedProject, resourceSet, recorder);
					resetFSA();
				}
				recorder.markEndProcessing(markedProject.project);
			}
		}

		recorder.dumpToLogfile(logFile);

		if (collectedErrors.isInitialized()) {
			throw collectedErrors.get();
		}
	}

	/**
	 * Creates the common resource set to use during compilation. Installs a light weight index.
	 *
	 * @return the resource set
	 */
	private ResourceSet createResourceSet() {
		// TODO try to reuse code from IN4JSCore.createResourceSet

		XtextResourceSet resourceSet = xtextResourceSetProvider.get();
		resourceSet.setClasspathURIContext(classLoader);

		// Install containerState as adapter.
		resourceSet.eAdapters().add(new DelegatingIAllContainerAdapter(rsbAcs));

		// Install a lightweight index.
		OrderedResourceDescriptionsData index = new OrderedResourceDescriptionsData(Collections.emptyList());
		ResourceDescriptionsData.ResourceSetAdapter.installResourceDescriptionsData(resourceSet, index);

		return resourceSet;
	}

	/*
	 * ===============================================================================================================
	 *
	 * PROJECT LOADING AND INDEXING
	 *
	 * ===============================================================================================================
	 */

	/**
	 * Loads all resources in the given project and indexes them.
	 *
	 * FileSystemAccess has to be correctly configured, see {@link #configureFSA(IN4JSProject)} and {@link #resetFSA()}
	 *
	 * @param markedProject
	 *            project to load
	 * @param resSet
	 *            the resource set to load resources into
	 * @param recorder
	 *            failure-recording
	 * @param issueAcceptor
	 *            the issue acceptor that can be used to collect or evaluate the issues occurring during compilation
	 * @throws N4JSCompileErrorException
	 *             if an error occurs during loading or indexing
	 */
	private void loadProject(MarkedProject markedProject, ResourceSet resSet, N4ProgressStateRecorder recorder,
			IssueAcceptor issueAcceptor)
			throws N4JSCompileErrorException {

		recorder.markStartLoading(markedProject);

		if (logger.isCreateDebugOutput()) {
			logger.debug("Loading project " + markedProject.project.getProjectId());
		}

		collectResources(markedProject, resSet, recorder);
		loadResources(markedProject, recorder);
		indexResources(markedProject, resSet);
		postProcessResources(markedProject);
	}

	/**
	 * Collects all resources belonging to the given project and adds them to the given resource set.
	 *
	 * @param markedProject
	 *            the project being loaded
	 * @param resourceSet
	 *            the resource set to load the resources into
	 * @param recorder
	 *            the progress recorder
	 */
	private void collectResources(MarkedProject markedProject, ResourceSet resourceSet,
			N4ProgressStateRecorder recorder) {

		markedProject.clearResources();

		for (IN4JSSourceContainer container : markedProject.project.getSourceContainers()) {
			// Conditionally filter test resources if not desired
			if (shouldLoadSourceContainer(container)) {
				if (logger.isCreateDebugOutput()) {
					logger.debug("Collecting resources from source container " + container.getLocation());
				}

				Iterables.filter(container, uri -> shouldLoadResource(uri)).forEach(uri -> {
					Resource resource = resourceSet.createResource(uri);
					if (resource != null) {
						if (logger.isCreateDebugOutput()) {
							logger.debug("  Creating resource " + resource.getURI());
						}

						markedProject.resources.add(resource);

						if (container.isExternal()) {
							markedProject.externalResources.add(resource);
						}

						if (container.isTest()) {
							markedProject.testResources.add(resource);
						}
					} else {
						recorder.markFailedCreateResource(uri);
						logger.warn("  Could not create resource for " + uri);
					}
				});
			}
		}
	}

	/**
	 * Indicates whether the resources in the given source container should be loaded or not.
	 *
	 * @param sourceContainer
	 *            the source container to decide on
	 * @return <code>true</code> if the resources in the given source container should be loaded and <code>false</code>
	 *         otherwise
	 */
	private boolean shouldLoadSourceContainer(final IN4JSSourceContainer sourceContainer) {
		return (isProcessTestCode() || !sourceContainer.isTest());
	}

	/**
	 * Indicates whether the resources with the given URI should be loaded or not.
	 *
	 * @param uri
	 *            the URI of the resource to be loaded
	 * @return <code>true</code> if the resource with the given URI should be loaded and <code>false</code> otherwise
	 */
	private boolean shouldLoadResource(final URI uri) {
		if (uri == null)
			return false;
		final String ext = uri.fileExtension();
		if (ext == null)
			return false;

		// FIXME: This will not work with N4JSX.
		return N4JSGlobals.ALL_N4_FILE_EXTENSIONS.contains(ext.toLowerCase());
	}

	/**
	 * Load all resources of the given marked project.
	 *
	 * @param markedProject
	 *            the project to load
	 * @param recorder
	 *            the progress recorder
	 * @throws N4JSCompileErrorException
	 *             if an error occurs while loading the resources
	 */
	private void loadResources(MarkedProject markedProject, N4ProgressStateRecorder recorder)
			throws N4JSCompileErrorException {
		if (logger.isCreateDebugOutput()) {
			logger.debug("Loading resources for project " + markedProject.project.getProjectId());
		}

		for (Resource resource : markedProject.resources) {
			try {
				if (logger.isCreateDebugOutput()) {
					logger.debug("  Loading resource " + resource.getURI());
				}

				resource.load(Collections.EMPTY_MAP);
			} catch (IOException e) {
				recorder.markLoadResourceFailed(resource);
				String message = "Cannot load resource=" + resource.getURI();
				if (!isKeepOnCompiling()) {
					throw new N4JSCompileErrorException(message, markedProject.project.getProjectId(), e);
				}
				logger.warn(message);
			}
		}
	}

	/**
	 * Indexes the resources in the given project including the manifest file resource and adds them to the index stored
	 * in the given resource set. Assumes that the resources have been loaded, but not fully processed.
	 *
	 * @param markedProject
	 *            the project to index
	 * @param resourceSet
	 *            the resource set that contains the index
	 */
	private void indexResources(MarkedProject markedProject, ResourceSet resourceSet) {
		ResourceDescriptionsData index = ResourceDescriptionsData.ResourceSetAdapter
				.findResourceDescriptionsData(resourceSet);

		if (logger.isCreateDebugOutput()) {
			logger.debug("Indexing project " + markedProject.project.getProjectId());
		}

		for (Resource resource : markedProject.resources) {
			indexResource(resource, index);
		}

		// Index manifest file, too. Index artifact names among project types and library dependencies.
		Optional<URI> manifestUri = markedProject.project.getManifestLocation();
		if (manifestUri.isPresent()) {
			final Resource manifestResource = resourceSet.getResource(manifestUri.get(), true);
			if (manifestResource != null) {
				indexResource(manifestResource, index);
			}
		}
	}

	/**
	 * Install the given resource's description into the given index. Raw JavaScript files will not be indexed. Note
	 * that when this method is called for the given resource, it is not yet fully processed and therefore the
	 * serialized type model is not added to the index.
	 * <p>
	 * This is due to the fact that we keep a common resource set for all projects that contains the resources of all
	 * projects with unprocessed dependencies, unlike in the IDE case where we have one resource set per open document
	 * and load the type models from the index.
	 * </p>
	 * <p>
	 * Since the type models are available in the resource set as long as they may still be referenced, they need not be
	 * serialized and stored into the index.
	 * </p>
	 *
	 * @param resource
	 *            the resource to be indexed
	 * @param index
	 *            the index to add the given resource to
	 */
	private void indexResource(Resource resource, ResourceDescriptionsData index) {
		if (!shouldIndexResource(resource))
			return;

		final URI uri = resource.getURI();
		IResourceServiceProvider serviceProvider = IResourceServiceProvider.Registry.INSTANCE
				.getResourceServiceProvider(uri);
		if (serviceProvider != null) {
			IResourceDescription.Manager resourceDescriptionManager = serviceProvider.getResourceDescriptionManager();
			IResourceDescription resourceDescription = resourceDescriptionManager.getResourceDescription(resource);

			if (resourceDescription != null) {
				if (logger.isCreateDebugOutput()) {
					logger.debug("  Indexing resource " + uri);
				}

				index.addDescription(uri, resourceDescription);
			}
		}
	}

	/**
	 * Indicates whether or not the given resource should be indexed.
	 *
	 * @param resource
	 *            the resource to be indexed
	 * @return <code>true</code> if the given resource should be indexed and <code>false</code> otherwise
	 */
	private boolean shouldIndexResource(Resource resource) {
		final URI uri = resource.getURI();
		final ResourceType resourceType = ResourceType.getResourceType(uri);

		// We only want to index raw JS files if they are contained in an N4JS source container.
		return resourceType != ResourceType.JS || n4jsCore.findN4JSSourceContainer(uri).isPresent();
	}

	/*
	 * ===============================================================================================================
	 *
	 * PROJECT POST-PROCESSING
	 *
	 * ===============================================================================================================
	 */

	/**
	 * Post processing on the whole project. While validations can trigger post processing in a lazy way, they do not
	 * guarantee that all resources will be fully processed. This is an issue in larger project graphs. Unlike in the
	 * IDE which can at any point load AST on demand based on the TModel, the HLC does not allow to access AST after
	 * unloading therefore we explicitly post process all N4JS resources in the given project.
	 *
	 * @param markedProject
	 *            project to trigger post process.
	 */
	private void postProcessResources(MarkedProject markedProject) {
		if (logger.isCreateDebugOutput())
			logger.debug(" PostProcessing " + markedProject);
		Iterables.filter(markedProject.resources, resource -> resource.isLoaded()).forEach(resource -> {
			if (resource instanceof N4JSResource) {
				N4JSResource n4jsResource = (N4JSResource) resource;

				// Make sure the resource is fully postprocessed before unloading the AST. Otherwise, resolving
				// cross references to the elements inside the resources from dependent projects will fail.
				n4jsResource.performPostProcessing();
			}
		});
	}

	/*
	 * ===============================================================================================================
	 *
	 * PROJECT VALIDATION
	 *
	 * ===============================================================================================================
	 */

	/**
	 * Validates all non-external Xtext resources of the given project. Prints issues and adds them to the given issue
	 * acceptor.
	 *
	 * @param markedProject
	 *            the project to validate
	 * @param recorder
	 *            the progress recorder
	 * @param issueAcceptor
	 *            the issue acceptor
	 * @throws N4JSCompileErrorException
	 *             if an error occurs during validation
	 */
	private void validateProject(MarkedProject markedProject, N4ProgressStateRecorder recorder,
			IssueAcceptor issueAcceptor) throws N4JSCompileErrorException {

		if (logger.isVerbose())
			logger.info(" Validating project " + markedProject);
		IssueCollector issueCollector = new IssueCollector();
		IssueFilter issueFilter = new IssueFilter(issueCollector, issue -> issue.getSeverity() == Severity.ERROR);
		issueAcceptor = new IssueAcceptorTee(issueAcceptor, issueFilter);

		// validation TODO see IDE-1426 redesign validation calls with generators
		for (Resource resource : markedProject.resources) {
			if (resource instanceof XtextResource && // is Xtext resource
					(!n4jsCore.isNoValidate(resource.getURI())) && // is validating
					(!markedProject.externalResources.contains(resource)) // not in external folder
			) {
				if (logger.isCreateDebugOutput())
					logger.debug("   Validating resource " + resource.getURI());
				XtextResource xtextResource = (XtextResource) resource;
				IResourceValidator validator = xtextResource.getResourceServiceProvider().getResourceValidator();
				List<Issue> issues = validator.validate(xtextResource, CheckMode.ALL, CancelIndicator.NullImpl);

				if (!issues.isEmpty()) {
					recorder.markResourceIssues(resource, issues);
					issueAcceptor.acceptAll(issues);
					issues.stream().forEach(logger::issue);
				}
			}
		}

		// Projects should not compile if there are severe errors:
		if (!isKeepOnCompiling()) {
			failOnErrors(issueCollector.getCollectedIssues(), markedProject.project.getProjectId());
		}
	}

	/**
	 * In case of errors: print all non-error issues and throw exception.
	 *
	 * @param errors
	 *            list of errors
	 * @param projectId
	 *            projectId of the bad project.
	 * @throws N4JSCompileErrorException
	 *             if the given issues contain errors
	 */
	private void failOnErrors(List<Issue> errors, String projectId)
			throws N4JSCompileErrorException {

		if (!errors.isEmpty()) {
			StringBuilder message = new StringBuilder();
			message.append("Cannot compile project " + projectId + " due to " + errors.size() + " errors.");
			errors.forEach(error -> message.append("\n").append(error));
			throw new N4JSCompileErrorException(message.toString(), projectId);
		}

	}

	/*
	 * ===============================================================================================================
	 *
	 * GENERATING CODE
	 *
	 * ===============================================================================================================
	 */

	/**
	 * Generates code for all resources in the given project.
	 *
	 * FileSystemAccess has to be correctly configured, see {@link #configureFSA(IN4JSProject)} and {@link #resetFSA()}
	 *
	 * @param markedProject
	 *            project to compile.
	 * @param resSet
	 *            outer resource set
	 * @param compileFilter
	 *            if not empty limit to this.
	 * @param rec
	 *            state reporter
	 * @throws N4JSCompileException
	 *             in case of compile-problems. Possibly wrapping other N4SJCompileExceptions.
	 */
	private void generateProject(MarkedProject markedProject, ResourceSet resSet, Predicate<URI> compileFilter,
			N4ProgressStateRecorder rec)
			throws N4JSCompileException {
		rec.markStartCompiling(markedProject);

		final String projectId = markedProject.project.getProjectId();
		if (logger.isVerbose()) {
			logger.info("Generating " + projectId);
		}

		Lazy<N4JSCompoundCompileException> collectedErrors = Lazy.create(() -> {
			return new N4JSCompoundCompileException("Errors during generation of project " + projectId);
		});

		// then compile each file.
		for (Resource resource : markedProject.resources) {
			if (compileFilter.test(resource.getURI())) {
				boolean isTest = markedProject.isTest(resource);
				boolean compile = (isTest && isProcessTestCode()) || (!isTest && isCompileSourceCode());
				if (compile) {
					try {
						rec.markStartCompile(resource);
						if (logger.isVerbose()) {
							logger.info("  Generating resource " + resource.getURI());
						}
						compositeGenerator.doGenerate(resource, fsa);
						rec.markEndCompile(resource);
					} catch (GeneratorException e) {
						rec.markBrokenCompile(e);

						if (isKeepOnCompiling()) {
							collectedErrors.get().add(new N4JSCompileErrorException(e.getMessage(), projectId, e));
							if (logger.isVerbose()) {
								logger.info(e.getMessage());
							}
						} else {
							// fail fast
							throw e;
						}
					}
				} else {
					rec.markSkippedCompile(resource);
				}
			}
		}

		rec.markEndCompiling(markedProject);

		if (collectedErrors.isInitialized()) {
			throw collectedErrors.get();
		}
	}

	/*
	 * ===============================================================================================================
	 *
	 * PROJECT UNLOADING & CLEANUP
	 *
	 * ===============================================================================================================
	 */

	/**
	 * Remove the given marked project from every loaded project that it depends on and unload those projects which have
	 * no marks anymore, since those are no longer required to process other projects.
	 *
	 * @param loadedProjects
	 *            the currently loaded projects
	 * @param markedProject
	 *            the project that was just processed
	 * @param resourceSet
	 *            the resource set
	 * @param recorder
	 *            the progress recorder
	 */
	private void unmarkAndUnloadProjects(List<MarkedProject> loadedProjects, MarkedProject markedProject,
			ResourceSet resourceSet, N4ProgressStateRecorder recorder) {
		ListIterator<MarkedProject> loadedIter = loadedProjects.listIterator();
		while (loadedIter.hasNext()) {
			MarkedProject loaded = loadedIter.next();
			loaded.remove(markedProject.project);

			if (!loaded.hasMarkers()) {
				if (logger.isCreateDebugOutput()) {
					logger.debug("Unloading project " + loaded.project);
				}

				loaded.unload(resourceSet, recorder);
				loadedIter.remove();
			}
		}
	}

	/*
	 * ===============================================================================================================
	 *
	 * OUTPUT CONFIGURATION
	 *
	 * ===============================================================================================================
	 */

	/**
	 * Setting the compile output-configurations to contain path-locations relative to the user.dir: Wrapper function
	 * written against Xtext 2.7.1.
	 *
	 * In Eclipse-compile mode there are "projects" and the FSA is configured relative to these projects. In this
	 * filebasedWorkspace here there is no "project"-concept for the generator. So the paths of the FSA need to be
	 * reconfigured to contain the navigation to the IN4JSProject-root.
	 *
	 * @param project
	 *            project to be compiled
	 */
	private void configureFSA(IN4JSProject project) {
		File currentDirectory = new File(".");
		File projectLocation = new File(project.getLocation().toFileString());

		// If project is not in a sub directory of the current directory an absolute path is computed.
		final java.net.URI projectURI = currentDirectory.toURI().relativize(projectLocation.toURI());
		final String projectPath = projectURI.getPath();
		if (projectPath.length() == 0) {
			// same directory, skip
			return;
		}

		// set different output configuration.
		fsa.setOutputConfigurations(transformedOutputConfiguration(projectPath));
	}

	/**
	 * Wraps the output-configurations {@link #outputs} with a delegate that transparently injects the relative path to
	 * the project-root.
	 *
	 * @param projectPath
	 *            relative path to the project-root
	 * @return wrapped configurations.
	 */
	private Map<String, OutputConfiguration> transformedOutputConfiguration(String projectPath) {
		Map<String, OutputConfiguration> result = new HashMap<>();

		for (Entry<String, OutputConfiguration> pair : outputs.entrySet()) {
			final OutputConfiguration input = pair.getValue();
			OutputConfiguration transOC = new WrappedOutputConfiguration(input, projectPath);
			result.put(pair.getKey(), transOC);
		}

		return result;
	}

	/**
	 * Reset output configuration to initial settings stored in {@link #outputs}.
	 *
	 * @see #configureFSA(IN4JSProject) how to set to specific project.
	 */
	private void resetFSA() {
		fsa.setOutputConfigurations(outputs);
	}

	/*
	 * ===============================================================================================================
	 *
	 * PRINTING INFORMATION
	 *
	 * ===============================================================================================================
	 */

	/**
	 * Prints out some debug information about the user-provided compilation arguments (only if {@link #logger} is
	 * configured in debug mode.
	 *
	 * @param searchPaths
	 *            where to search for dependent projects.
	 * @param projectPaths
	 *            the projects to compile. the base folder of each project must be provided.
	 * @param singleSourceFiles
	 *            if non-empty limit compilation to the sources files listed here
	 */
	private void printCompileArguments(List<File> searchPaths, List<File> projectPaths, List<File> singleSourceFiles) {
		if (logger.isCreateDebugOutput()) {
			logger.debug("Starting compilation with the following arguments");
			logger.debug("  Search paths: " + Joiner.on(", ").join(searchPaths));
			logger.debug("  Projects    : " + Joiner.on(", ").join(projectPaths));
			logger.debug("  Source files: " + Joiner.on(", ").join(singleSourceFiles));
		}
	}

	/**
	 * Prints the build order (only if {@link #logger} is configured in debug mode).
	 *
	 * @param buildOrder
	 *            the build order
	 */
	private void printBuildOrder(List<MarkedProject> buildOrder) {
		if (logger.isCreateDebugOutput()) {
			logger.debug("Building " + buildOrder.size() + " projects in the following order");

			long generated = buildOrder.stream().filter(mp -> mp.hasMarkers()).count();
			int decimals = Long.toString(generated).length();

			StringBuilder pattern = new StringBuilder();
			StringBuilder placeHolderB = new StringBuilder();
			for (long i = 0; i < decimals; i++) {
				pattern.append("#");
				placeHolderB.append("-");
			}

			DecimalFormat indexFormat = new DecimalFormat(pattern.toString());
			String indexPlaceHolder = placeHolderB.toString();

			int index = 1;
			for (MarkedProject mp : buildOrder) {
				boolean generate = mp.hasMarkers();

				StringBuilder msg = new StringBuilder();
				if (generate) {
					msg.append(indexFormat.format(index)).append(".");
					index++;
				} else {
					msg.append(indexPlaceHolder).append(" ");
				}

				msg.append(" Project ").append(mp.project);
				msg.append(" used by [").append(Joiner.on(", ").join(mp.markers)).append("]");

				logger.debug(msg.toString());
			}
		}
	}

	/**
	 * Indicates whether or not compilation should proceed in case of errors.
	 */
	public boolean isKeepOnCompiling() {
		return keepOnCompiling;
	}

	/**
	 * Specifies whether or not compilation should proceed in case of errors.
	 */
	public void setKeepOnCompiling(boolean keepOnCompiling) {
		this.keepOnCompiling = keepOnCompiling;
	}

	/**
	 * Indicates whether or not test code should be processed.
	 */
	public boolean isProcessTestCode() {
		return processTestCode;
	}

	/**
	 * Specifies whether or not test code should be processed.
	 */
	public void setProcessTestCode(boolean processTestCode) {
		this.processTestCode = processTestCode;
	}

	/**
	 * Indicates whether or not source code should be generated by the transpiler.
	 */
	public boolean isCompileSourceCode() {
		return compileSourceCode;
	}

	/**
	 * Specifies whether or not source code should be generated by the transpiler.
	 */
	public void setCompileSourceCode(boolean compileSourceCode) {
		this.compileSourceCode = compileSourceCode;
	}

	/**
	 * Returns the log file name to use for progress logging.
	 *
	 * @return Returns the log file name or <code>null</code> if no log file name has been set
	 */
	public String getLogFile() {
		return logFile;
	}

	/**
	 * Sets the log file name to use for progress logging.
	 *
	 * @param logFile
	 *            the log file name
	 */
	public void setLogFile(String logFile) {
		this.logFile = logFile;
	}

	/*
	 * ===============================================================================================================
	 *
	 * MARKED PROJECT UTILITY CLASS
	 *
	 * ===============================================================================================================
	 */
	/**
	 * A wrapper around N4JS projects that has the ability to track dependent projects in the form of markers. A project
	 * is added as a marker of this project if it has an active dependency on it. A dependency is active as long as the
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
	static class MarkedProject {
		/**
		 * The wrapped project.
		 */
		final IN4JSProject project;

		/**
		 * Contains the active markers, i.e., dependent projects that have not yet been built themselves.
		 */
		final Set<IN4JSProject> markers = new TreeSet<>(n4JSProjComparator);

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
		 * Remove the given project as a marker of this project, indicating that it is no longer has an active
		 * dependency on this project.
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
			unloadManifestResource(resourceSet, index, recorder);
			clearResources();

			recorder.markFinishedUnloading(this);
		}

		private void unloadResources(ResourceSet resourceSet, ResourceDescriptionsData index,
				N4ProgressStateRecorder recorder) {
			for (Resource res : resources)
				unloadResource(res, resourceSet, index, recorder);
		}

		private void unloadManifestResource(ResourceSet resourceSet, ResourceDescriptionsData index,
				N4ProgressStateRecorder recorder) {
			Optional<URI> manifestLocation = project.getManifestLocation();
			if (manifestLocation.isPresent()) {
				Resource resource = resourceSet.getResource(manifestLocation.get(), false);
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

		private void clearResources() {
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
}
