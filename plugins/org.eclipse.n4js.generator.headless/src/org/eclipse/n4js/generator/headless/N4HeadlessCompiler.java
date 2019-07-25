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
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Predicate;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.generator.GeneratorException;
import org.eclipse.n4js.generator.headless.logging.IHeadlessLogger;
import org.eclipse.n4js.internal.FileBasedWorkspace;
import org.eclipse.n4js.internal.N4FilebasedWorkspaceResourceSetContainerState;
import org.eclipse.n4js.projectDescription.ProjectType;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.IN4JSSourceContainer;
import org.eclipse.n4js.projectModel.locations.FileURI;
import org.eclipse.n4js.projectModel.locations.SafeURI;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.resource.OrderedResourceDescriptionsData;
import org.eclipse.n4js.utils.Lazy;
import org.eclipse.n4js.utils.ResourceType;
import org.eclipse.n4js.utils.io.FileUtils;
import org.eclipse.n4js.utils.resources.IBuildSuppressingResourceDescriptionManager;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.generator.JavaIoFileSystemAccess;
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
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multimap;
import com.google.common.collect.TreeMultimap;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * Entry for headless compilation.
 *
 * This class has three ways of operation which all map down to a single algorithm implemented in
 * {@link #compile(BuildSet, IssueAcceptor)}. All other compileXXXX methods call this algorithm providing the correct
 * content of the arguments.
 *
 * Use {@link BuildSetComputer} to compute {@link BuildSet}s of files/projects to compile.
 *
 * The way the compiler behaves can be configured through the flags {@link #keepOnCompiling}, {@link #processTestCode}
 * and {@link #compileSourceCode}
 *
 * IMPORTANT: Before using the functionalities of this class, make sure to register composite generators first.
 * Moreover, the subgenerators must have been registered as well.
 */
public class N4HeadlessCompiler {

	/** Helper for configuring {@link JavaIoFileSystemAccess} */
	@Inject
	private ConfiguredGeneratorFactory generatorFactory;

	/** N4JS-Implementation of a workspace without OSGI */
	@Inject
	private FileBasedWorkspace n4jsFileBasedWorkspace;

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

	@Inject
	private HeadlessHelper headlessHelper;

	@Inject
	private BuildSetComputer buildSetComputer;

	/** if set to true should try to compile even if errors are in some projects */
	private boolean keepOnCompiling = false;

	/** if set to false all source-containers of type 'test' are not passed to the generator */
	private boolean processTestCode = true;

	/** if set to false all source-containers of type 'source' are not passed to the generator */
	private boolean compileSourceCode = true;

	/** if set additional log will be written to this filename */
	private String logFile = null;

	/*
	 * ===============================================================================================================
	 *
	 * ENTRY POINTS TO LAUNCH COMPILATION
	 *
	 * ===============================================================================================================
	 */

	/**
	 * Compile the given {@link BuildSet}.
	 *
	 * Delegates to {@link #compile(BuildSet, IssueAcceptor)}.
	 *
	 * @param buildSet
	 *            the build set to compile
	 * @throws N4JSCompileException
	 *             if one or multiple errors occur during compilation
	 */
	public void compile(BuildSet buildSet) throws N4JSCompileException {
		compile(buildSet, new DismissingIssueAcceptor());
	}

	/**
	 * Compile the given {@link BuildSet}. This method controls the actual build process.
	 *
	 * If any of the project in the given {@link BuildSet} is not registered with the {@link FileBasedWorkspace}, this
	 * method will register them before the actual compilation is performed.
	 *
	 * @param buildSet
	 *            the build set to compile
	 * @param issueAcceptor
	 *            the issue acceptor that can be used to collect or evaluate the issues occurring during compilation
	 * @throws N4JSCompileException
	 *             if one or multiple errors occur during compilation
	 */
	public void compile(BuildSet buildSet, IssueAcceptor issueAcceptor) throws N4JSCompileException {
		Set<IN4JSProject> allProjects = buildSet.getAllProjects();
		Set<IN4JSProject> requestedProjects = buildSet.requestedProjects;
		Predicate<FileURI> singleSourceFilter = buildSet.resourceFilter;

		// make sure all to-be-compiled projects are registered with the workspace
		// if a project had been registered before, it will be skipped by this registration method
		headlessHelper.registerProjects(buildSet, n4jsFileBasedWorkspace);

		configureResourceSetContainerState(allProjects);

		final List<MarkedProject> buildOrder = computeBuildOrder(allProjects, requestedProjects);
		printBuildOrder(buildOrder);

		processProjects(buildOrder, singleSourceFilter, issueAcceptor);
	}

	/**
	 * Clean the output folders of all <b>directly contained</b> projects found in {@code searchPaths}.
	 *
	 * @param searchPaths
	 *            where to search for projects to be cleaned.
	 * @throws N4JSCompileException
	 *             if one or multiple errors occur.
	 */
	public void cleanProjectsInSearchPath(List<File> searchPaths)
			throws N4JSCompileException {
		List<File> absProjectPaths = headlessHelper.toAbsoluteFileList(searchPaths);
		// Collect all projects in first Level.
		List<File> projectPaths = headlessHelper.collectAllProjectPaths(absProjectPaths);
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
		List<FileURI> projectURIs = convertProjectPathsToProjectURIs(projectPaths);
		headlessHelper.registerProjectsToFileBasedWorkspace(projectURIs, n4jsFileBasedWorkspace);
		List<IN4JSProject> projectsToClean = headlessHelper.getN4JSProjects(projectURIs);
		projectsToClean.forEach(project -> {
			if (project.getProjectType() != ProjectType.VALIDATION && project.getProjectType() != ProjectType.PLAINJS)
				// Do NOT clean project of type validation or plainjs because we don't want to
				// accidently remove source content
				cleanProject(project);
		});
	}

	private void cleanProject(IN4JSProject project) {
		String outputFolder = project.getOutputPath();
		File outputPath = project.getLocation().resolve(outputFolder).toJavaIoFile();
		FileUtils.cleanFolder(outputPath);
	}

	private List<FileURI> convertProjectPathsToProjectURIs(List<File> projectPaths)
			throws N4JSCompileException {
		List<File> absProjectPaths = headlessHelper.toAbsoluteFileList(projectPaths);
		// Convert absolute locations to file URIs.
		List<FileURI> projectURIs = headlessHelper.createFileURIs(absProjectPaths);
		return projectURIs;
	}

	/*
	 * ===============================================================================================================
	 *
	 * COLLECTING PROJECTS FROM USER-PROVIDED ARGUMENTS
	 *
	 * ===============================================================================================================
	 */

	// TODO GH-793 processing broken projects causes exceptions
	private void configureResourceSetContainerState(final Set<IN4JSProject> allProjects) {
		// a container is a project.
		List<String> containers = new LinkedList<>();
		BiMap<String, IN4JSProject> container2project = HashBiMap.create();

		// the URIs of all resources directly contained in a project/container.
		Multimap<String, URI> container2Uris = HashMultimap.create();

		for (IN4JSProject project : allProjects) {
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
	private List<MarkedProject> computeBuildOrder(Collection<? extends IN4JSProject> allProjectsToCompile,
			Collection<? extends IN4JSProject> requestedProjects) {

		// This algorithm only operates on the following map of marked projects.
		Map<IN4JSProject, MarkedProject> markedProjects = new HashMap<>();
		allProjectsToCompile.stream().forEach(project -> markedProjects.put(project, new MarkedProject(project)));

		// Maps a project to the projects that depend on it.
		Multimap<IN4JSProject, IN4JSProject> pendencies = TreeMultimap.create(N4JSProjectComparator.INSTANCE,
				N4JSProjectComparator.INSTANCE);

		// Maps a project to the projects it depends on.
		Multimap<IN4JSProject, IN4JSProject> dependencies = TreeMultimap.create(N4JSProjectComparator.INSTANCE,
				N4JSProjectComparator.INSTANCE);

		// Sorted set of projects without dependencies (starting points) to determine their order.
		SortedSet<IN4JSProject> independentProjects = new TreeSet<>(N4JSProjectComparator.INSTANCE);

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
		MarkedProject projectToMark = markables.get(markee);

		if (projectToMark == null) {
			// in this case we have discovered a dependency to an external non-N4JS project
			// which does not need to be build
			return;
		}

		projectToMark.markWith(marker);

		// Recursively apply to all dependencies of the given markee
		for (IN4JSProject dependency : dependencies.get(markee)) {
			markDependencies(marker, dependency, markables, dependencies);
		}
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
	private void computeDependencyGraph(Set<IN4JSProject> projects,
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
	private void computeDependencyGraph(IN4JSProject project, Set<IN4JSProject> visitedProjects,
			Multimap<IN4JSProject, IN4JSProject> pendencies,
			Multimap<IN4JSProject, IN4JSProject> dependencies, Collection<IN4JSProject> independent) {

		if (!visitedProjects.add(project))
			return;

		ImmutableList<? extends IN4JSProject> pendingProjects = project.getDependenciesAndImplementedApis();

		// Do not process dependencies of projects that need not to be built (e.g. transitive non-N4JS npm
		// dependencies or projects without "n4js" nature).
		// In the context of the headless compiler, we regard such projects as independent.
		// TODO Revisit once GH-821 is being worked on
		if (!headlessHelper.isProjectToBeBuilt(project) || !project.hasN4JSNature()) {
			independent.add(project);
			return;
		}

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
			final MarkedProject markedProject = markedProjects.get(project);

			// only add marked projects to build order list
			if (markedProject != null) {
				// The current project is ready to be processed.
				result.add(markedProject);
			}
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
	private void processProjects(List<MarkedProject> projects, final Predicate<FileURI> filter,
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
			logger.debug("Loading project " + markedProject.project.getProjectName());
		}

		/*
		 * GH-448: We need to populate the index in two passes. The first pass installs prelinking modules which are
		 * necessary for post processing. The second pass installs full modules created from fully initialized
		 * resources.
		 */

		collectResources(markedProject, resSet, recorder);
		loadResources(markedProject, recorder);

		// First indexing pass.
		indexResources(markedProject, resSet);
		postProcessResources(markedProject);

		// Second indexing pass - now the index is valid.
		indexResources(markedProject, resSet);
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
		ResourceType resourceType = ResourceType.getResourceType(uri);
		switch (resourceType) {
		case UNKOWN:
			return false;
		default:
			return true;
		}
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
			logger.debug("Loading resources for project " + markedProject.project.getProjectName());
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
					throw new N4JSCompileErrorException(message, markedProject.project.getProjectName(), e);
				}
				logger.warn(message);
			}
		}
	}

	/**
	 * Indexes the resources in the given project including the package json resource and adds them to the index stored
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
			logger.debug("Indexing project " + markedProject.project.getProjectName());
		}

		for (Resource resource : markedProject.resources) {
			indexResource(resource, index);
		}

		// Index package.json file, too. Index artifact names among project types and library dependencies.
		SafeURI<?> packageJson = markedProject.project.getProjectDescriptionLocation();
		if (packageJson != null) {
			final Resource packageJsonResource = resourceSet.getResource(packageJson.toURI(), true);
			if (packageJsonResource != null) {
				indexResource(packageJsonResource, index);
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
			if (logger.isCreateDebugOutput()) {
				logger.debug("  Indexing resource " + uri);
			}

			IResourceDescription.Manager resourceDescriptionManager = serviceProvider.getResourceDescriptionManager();
			IResourceDescription resourceDescription = resourceDescriptionManager.getResourceDescription(resource);

			// first consult manager whether to built (index) 'resource'
			if (resourceDescriptionManager instanceof IBuildSuppressingResourceDescriptionManager) {
				if (!((IBuildSuppressingResourceDescriptionManager) resourceDescriptionManager).isToBeBuilt(uri,
						resource)) {
					return;
				}
			}

			if (resourceDescription != null) {
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
		switch (resourceType) {
		case JS:
			return n4jsCore.findN4JSSourceContainer(uri).isPresent();
		case JSX:
			return n4jsCore.findN4JSSourceContainer(uri).isPresent();
		case UNKOWN:
			return false;
		default:
			return true;
		}
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
			failOnErrors(issueCollector.getCollectedIssues(), markedProject.project.getProjectName());
		}
	}

	/**
	 * In case of errors: print all non-error issues and throw exception.
	 *
	 * @param errors
	 *            list of errors
	 * @param projectName
	 *            the N4JS project name of the bad project.
	 * @throws N4JSCompileErrorException
	 *             if the given issues contain errors
	 */
	private void failOnErrors(List<Issue> errors, N4JSProjectName projectName)
			throws N4JSCompileErrorException {

		if (!errors.isEmpty()) {
			StringBuilder message = new StringBuilder();
			message.append("Cannot compile project " + projectName + " due to " + errors.size() + " errors.");
			errors.forEach(error -> message.append("\n").append(error));
			throw new N4JSCompileErrorException(message.toString(), projectName);
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
	private void generateProject(MarkedProject markedProject, ResourceSet resSet,
			Predicate<FileURI> compileFilter, N4ProgressStateRecorder rec) throws N4JSCompileException {
		rec.markStartCompiling(markedProject);

		final N4JSProjectName projectName = markedProject.project.getProjectName();
		if (logger.isVerbose()) {
			logger.info("Generating " + projectName);
		}

		Lazy<N4JSCompoundCompileException> collectedErrors = Lazy.create(() -> {
			return new N4JSCompoundCompileException("Errors during generation of project " + projectName);
		});

		ConfiguredGenerator generator = generatorFactory.getConfiguredGenerator(markedProject.project);

		// then compile each file.
		for (Resource resource : markedProject.resources) {
			if (compileFilter.test(new FileURI(resource.getURI()))) {
				boolean isTest = markedProject.isTest(resource);
				boolean compile = (isTest && isProcessTestCode()) || (!isTest && isCompileSourceCode());
				if (compile) {
					try {
						rec.markStartCompile(resource);
						if (logger.isVerbose()) {
							logger.info("  Generating resource " + resource.getURI());
						}

						// Ask composite generator to try to generate the current resource
						generator.generate(resource);

						rec.markEndCompile(resource);
					} catch (GeneratorException e) {
						rec.markBrokenCompile(e);

						if (isKeepOnCompiling()) {
							collectedErrors.get().add(new N4JSCompileErrorException(e.getMessage(), projectName, e));
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
	 * PRINTING INFORMATION
	 *
	 * ===============================================================================================================
	 */

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

	/**
	 * Returns the {@link BuildSetComputer} instance used by this headless compiler instance.
	 */
	public BuildSetComputer getBuildSetComputer() {
		return buildSetComputer;
	}
}
