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
package org.eclipse.n4js.runner;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Collections.emptyList;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.compare.ApiImplMapping;
import org.eclipse.n4js.generator.AbstractSubGenerator;
import org.eclipse.n4js.n4mf.BootstrapModule;
import org.eclipse.n4js.n4mf.ProjectType;
import org.eclipse.n4js.projectModel.IN4JSArchive;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.IN4JSSourceContainerAware;
import org.eclipse.n4js.runner.extension.IRunnerDescriptor;
import org.eclipse.n4js.runner.extension.RunnerRegistry;
import org.eclipse.n4js.runner.extension.RuntimeEnvironment;
import org.eclipse.n4js.utils.FindArtifactHelper;
import org.eclipse.n4js.utils.RecursionGuard;
import org.eclipse.n4js.utils.ResourceNameComputer;

import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.base.Strings;
import com.google.inject.Inject;

/**
 * Central processing of project-related computation working on our IN4JS abstractions.
 *
 * UI- or CLI-Interfaces should use this as a shared code-base.
 */
public class RunnerHelper {

	@Inject
	private IN4JSCore n4jsCore;

	@Inject
	private FindArtifactHelper artifactHelper;

	@Inject
	private ResourceNameComputer compilerHelper;

	@Inject
	private RunnerRegistry runnerRegistry;

	/**
	 * Returns list of absolute paths to each of the given projects' output folder in the local files system.
	 */
	public Collection<String> getCoreProjectPaths(List<IN4JSProject> projects) {
		return projects.stream()
				.map(project -> getProjectPaths(project))
				.flatMap(paths -> paths.stream())
				.filter(path -> !Strings.isNullOrEmpty(path))
				.collect(Collectors.toSet());
	}

	private Collection<String> getProjectPaths(IN4JSProject project) {
		Set<String> projectPaths = new HashSet<>();
		projectPaths.add(getProjectPath(project));
		return projectPaths;
	}

	/** get path to the project itself */
	private String getProjectPath(IN4JSProject project) {
		if (!project.exists())
			return null;
		final String pp = project.getLocationPath().normalize().toAbsolutePath().toString();
		return pp;
	}

	/**
	 * Returns absolute path to the resources defined in the projects manifest.
	 *
	 * This method was used before GH-394 to provide basic support for project resources at runtime. After GH-394 it is
	 * not used, but its future should be decided in GH-70
	 */
	// TODO GH-70 handle projects resources
	@SuppressWarnings("unused")
	private List<String> getProjectResourcePaths(IN4JSProject project) {
		final List<String> relativeResourcePathStr = new ArrayList<>(project.getResourcePaths());
		if (relativeResourcePathStr.isEmpty()) {
			return relativeResourcePathStr;
		}

		return relativeResourcePathStr.stream()
				.map(s -> toAbsolutePath(project, s))
				.collect(Collectors.toList());
	}

	private String toAbsolutePath(IN4JSProject project, String projectRelativePath) {
		if (projectRelativePath.startsWith(File.separator)) {
			projectRelativePath = projectRelativePath.substring(File.separator.length());
		}
		final Path projectPath = project.getLocationPath().toAbsolutePath();
		final Path absolutePath = projectPath.resolve(projectRelativePath);
		return absolutePath.normalize().toString();
	}

	/**
	 * Returns paths to all bootstrap files defined in the given N4JS projects. The paths are relative to their
	 * containing project's output folder.
	 */
	public List<String> getInitModulePaths(List<IN4JSProject> extendedDeps) {
		return extendedDeps.stream()
				.filter(p -> {
					ProjectType pt = p.getProjectType();
					return ProjectType.RUNTIME_LIBRARY.equals(pt) || ProjectType.RUNTIME_ENVIRONMENT.equals(pt);
				})
				.flatMap(p -> getInitModulesAsURIs(p).stream()
						.map(bmURI -> getProjectRelativePath(p,
								compilerHelper.generateFileDescriptor(p, bmURI, N4JSGlobals.JS_FILE_EXTENSION))))
				.collect(Collectors.toList());
	}

	/**
	 * Returns URI to execution module from runtime environment.
	 */
	public Optional<String> getExecModuleURI(List<IN4JSProject> extendedDeps) {
		List<String> execModules = extendedDeps.stream()
				.filter(p -> ProjectType.RUNTIME_ENVIRONMENT.equals(p.getProjectType()))
				.map(re -> {
					Optional<URI> execModuleAsURI = getExecModuleAsURI(re);
					if (!execModuleAsURI.isPresent()) {
						return null;
					}
					return getProjectRelativePath(re,
							compilerHelper.generateFileDescriptor(re, execModuleAsURI.get(),
									N4JSGlobals.JS_FILE_EXTENSION));
				})
				.filter(s -> !Strings.isNullOrEmpty(s))
				.collect(Collectors.toList());

		if (execModules.size() >= 1)
			return Optional.of(execModules.get(0));

		return Optional.absent();
	}

	private String getProjectRelativePath(IN4JSProject project, String subPath) {
		return AbstractSubGenerator.calculateProjectBasedOutputDirectory(project) + "/" + subPath;
	}

	/**
	 * Convenience method. Forwards to
	 * {@link #getProjectExtendedDepsAndApiImplMapping(RuntimeEnvironment, URI, String, boolean)}, but you provide a
	 * runnerId instead of a runtime environment (will use runtime environment of the runner with the given id),
	 * implementationId is <code>null</code> throwOnError is <code>false</code> .
	 *
	 * Analyzes the containing project of the moduleToRun and returns list of extended dependencies, that is:
	 * <ul>
	 * <li>the containing project itself (of moduleToRun)
	 * <li>transitive (project) dependencies
	 * <li>transitive (runtime library) dependencies
	 * <li>effective runtime environment project (for the runtime environment of the runner with the given ID)
	 * </ul>
	 *
	 * @param moduleToRun
	 *            Full emf-URI to the module.
	 *
	 * @return list of projects representing extended dependencies all wrapped in an instance of ApiUsage
	 *
	 *
	 */
	public ApiUsage getProjectExtendedDeps(String runnerId, URI moduleToRun) {
		final IRunnerDescriptor runnerDesc = runnerRegistry.getDescriptor(runnerId);
		return getProjectExtendedDepsAndApiImplMapping(runnerDesc.getEnvironment(), moduleToRun, null, false);
	}

	/**
	 * Collects transitive collection of project extended RuntimeEnvironemnts
	 *
	 * @see RuntimeEnvironmentsHelper#recursiveDependencyCollector
	 */
	public void recursiveExtendedREsCollector(IN4JSSourceContainerAware sourceContainer,
			Collection<IN4JSProject> addHere) {
		recursiveExtendedREsCollector(sourceContainer, addHere, n4jsCore.findAllProjects());
	}

	/**
	 * Collects transitive collection of project extended RuntimeEnvironemnts
	 *
	 * @see RuntimeEnvironmentsHelper#recursiveDependencyCollector
	 */
	public void recursiveExtendedREsCollector(IN4JSSourceContainerAware sourceContainer,
			Collection<IN4JSProject> addHere, Iterable<IN4JSProject> projects) {
		final IN4JSProject project = extractProject(sourceContainer);

		if (project.getProjectType().equals(ProjectType.RUNTIME_ENVIRONMENT)) {
			addHere.add(project);
			// TODO RLs can extend each other, should we use recursive RL deps collector?
			// if RLs extend each other and are provided by REs in hierarchy we will collect them anyway
			// if RLs extend each other but are from independent REs, that is and error?
			project.getProvidedRuntimeLibraries().forEach(rl -> addHere.add(extractProject(rl)));

			Optional<String> ep = project.getExtendedRuntimeEnvironmentId();
			Optional<IN4JSProject> extendedRE = Optional.absent();
			if (ep.isPresent()) {
				extendedRE = findRuntimeEnvironemtnWithName(ep.get(), projects);
			}
			if (extendedRE.isPresent()) {
				IN4JSProject e = extendedRE.get();
				recursiveExtendedREsCollector(e, addHere, projects);
			}
		}
	}

	/**
	 * Collects transitive collection of project dependencies including project itself.
	 *
	 * @see RuntimeEnvironmentsHelper#recursiveDependencyCollector
	 */
	public Collection<IN4JSProject> recursiveDependencyCollector(IN4JSSourceContainerAware sourceContainerAware) {
		if (null == sourceContainerAware) {
			return emptyList();
		}
		final Collection<IN4JSProject> dependencies = newArrayList();
		final RecursionGuard<URI> guard = new RecursionGuard<>();
		recursiveDependencyCollector(sourceContainerAware, dependencies, guard);
		return dependencies;
	}

	/**
	 * Same as {@link IN4JSProject#getExecModule()}, but returns the execution module as URI.
	 */
	private Optional<URI> getExecModuleAsURI(IN4JSProject project) {
		Optional<BootstrapModule> oExecModule = project.getExecModule();
		if (oExecModule.isPresent()) {
			return Optional.of(artifactHelper.findArtifact(project, oExecModule.get().getModuleSpecifierWithWildcard(),
					Optional.of(".js")));
		}
		return Optional.absent();
	}

	/**
	 * Same as {@link IN4JSProject#getInitModules()}, but returns the initialization modules as URIs.
	 */
	private List<URI> getInitModulesAsURIs(IN4JSProject project) {
		return project.getInitModules().stream()

				.map(bm -> artifactHelper.findArtifact(project, bm.getModuleSpecifierWithWildcard(),
						Optional.of(".js")))
				.filter(module -> module != null).collect(Collectors.toList());
	}

	private void recursiveDependencyCollector(IN4JSSourceContainerAware sourceContainer,
			Collection<IN4JSProject> addHere, RecursionGuard<URI> guard) {

		final IN4JSProject project = extractProject(sourceContainer);
		if (project.exists()) {
			addHere.add(project);
		}

		for (final IN4JSSourceContainerAware dep : sourceContainer.getAllDirectDependencies()) {
			if (guard.tryNext(dep.getLocation())) {
				recursiveDependencyCollector(dep, addHere, guard);
			}
		}
	}

	private IN4JSProject extractProject(IN4JSSourceContainerAware container) {
		if (container instanceof IN4JSProject) {
			return (IN4JSProject) container;
		}
		if (container instanceof IN4JSArchive) {
			// TODO #getProject() in next line will return containing(!) project, which is probably not what we want
			return ((IN4JSArchive) container).getProject();
		}
		throw new RuntimeException("unknown instance type of container " + container.getClass().getName());
	}

	/**
	 * Find custom runtime environment project in the user workspace for the supported runtime environment of the N4JS
	 * runner with the given ID.
	 */
	private Optional<IN4JSProject> getCustomRuntimeEnvironmentProject(RuntimeEnvironment runEnv) {
		// final RuntimeEnvironment reOfRunner = runnerRegistry.getDescriptor(runnerId).getEnvironment();
		if (runEnv != null) {
			final String projectId = runEnv.getProjectId();
			return findRuntimeEnvironemtnWithName(projectId);
		}
		return Optional.absent();
	}

	/**
	 * Looks up all runtime environment with provided name.
	 *
	 * @param projectId
	 *            of the project that servers as the desired environment.
	 * @return optional with project if found, empty optional otherwise.
	 */
	public Optional<IN4JSProject> findRuntimeEnvironemtnWithName(final String projectId,
			Iterable<IN4JSProject> projects) {
		for (IN4JSProject project : projects) {
			if (project.getProjectType() == ProjectType.RUNTIME_ENVIRONMENT
					&& project.getProjectId().equals(projectId)) {
				return Optional.of(project);
			}
		}
		return Optional.absent();
	}

	/**
	 * Looks up all runtime environment with provided name.
	 *
	 * @param projectId
	 *            of the project that servers as the desired environment.
	 * @return optional with project if found, empty optional otherwise.
	 */
	private Optional<IN4JSProject> findRuntimeEnvironemtnWithName(final String projectId) {
		return findRuntimeEnvironemtnWithName(projectId, n4jsCore.findAllProjects());
	}

	/**
	 * Create a default name for a new run configuration with the given runnerId and moduleToRun.
	 */
	public String computeConfigurationName(String runnerId, URI moduleToRun) {
		String modulePath = moduleToRun.path();
		modulePath = stripStart(modulePath, "/", "resource/", "plugin/");
		final String moduleName = modulePath.replace('/', '-');
		final String runnerName = runnerRegistry.getDescriptor(runnerId).getName();
		return moduleName + " (" + runnerName + ")";
	}

	private static final String stripStart(String str, String... prefixesToStrip) {
		for (String currPrefix : prefixesToStrip)
			if (str.startsWith(currPrefix))
				str = str.substring(currPrefix.length());
		return str;
	}

	/**
	 * Returns a mapping from all API projects among <code>dependencies</code> to their corresponding implementation
	 * project for implementation ID <code>implementationId</code>.
	 * <p>
	 * Special cases: if there are no API projects among the dependencies, this method will return an empty map in
	 * {@link ApiUsage#concreteApiImplProjectMapping} and {@link ApiUsage#implementationIdRequired}<code>==false</code>;
	 * otherwise, if <code>implementationId</code> is <code>null</code>, then this method will assert that there exists
	 * exactly one implementation for the API projects among the dependencies and use that (stored in
	 * {@link ApiUsage#implementationId}).
	 * <p>
	 * Throws exception in case of error given <code>throwOnError==true</code>, never returns null.
	 *
	 *
	 *
	 * @param runtimeEnvironment
	 *            active runtime environment.
	 * @param moduleToRun
	 *            what to run.
	 * @param implementationId
	 *            might be <code>null</code>
	 * @param throwOnError
	 *            if true fast fail in erroneous situations. Otherwise tries to proceed in a meaningful way. State can
	 *            then be queried on the ApiUsage instance.
	 *
	 * @return result wrapped in an {@link ApiUsage} instance.
	 */
	// TODO this methods could require some cleanup after the concepts of API-Implementation mappings stabilized...
	public ApiUsage getProjectExtendedDepsAndApiImplMapping(
			RuntimeEnvironment runtimeEnvironment, URI moduleToRun,
			String implementationId, boolean throwOnError) {
		final LinkedHashSet<IN4JSProject> deps = new LinkedHashSet<>();

		// 1) add project containing the moduleToRun and its direct AND indirect dependencies
		final Optional<? extends IN4JSProject> project = n4jsCore.findProject(moduleToRun);
		if (project.isPresent() == false) {
			throw new RuntimeException("can't obtain containing project for moduleToRun: " + moduleToRun);
		}
		recursiveDependencyCollector(project.get(), deps, new RecursionGuard<>());

		// TODO need to add not only REs but also RLs they provide
		// 2) add the runtime environment project, REs it extends and RLs provided
		final Optional<IN4JSProject> reProject = getCustomRuntimeEnvironmentProject(runtimeEnvironment);
		if (reProject.isPresent()) {
			IN4JSProject re = reProject.get();
			recursiveExtendedREsCollector(re, deps);
			// also add dependencies of the runtime environment
			recursiveDependencyCollector(re, deps, new RecursionGuard<>());
		} else {
			// IDE-1359: don't throw exception to make runners work without user-defined runtime environment
			// (will be changed later when library manager is available!)
			// throw new RuntimeException("can't obtain runtime environment project for " + moduleToRun2);
		}

		// TODO actually we would like to return the dependencies in load order:
		// - RuntimeEnvironment boot
		// - all RuntimeLibrary boots (take deps2 between RLs into account)
		// - all other deps2 (order does not matter they just needs to be on path later)
		// - project to be called
		// maybe some in order DFS or something above?
		// manually transform to list, or is this one ok?

		final ApiImplMapping apiImplMapping = ApiImplMapping.of(deps, n4jsCore.findAllProjects());
		if (apiImplMapping.hasErrors()) {
			if (throwOnError)
				throw new IllegalStateException(
						"the workspace setup contains errors related to API / implementation projects (check manifests of related projects):\n    "
								+ Joiner.on("\n    ").join(apiImplMapping.getErrorMessages()));
		}

		if (apiImplMapping.isEmpty()) {
			// no API projects among the dependencies
			// -> all good, no project replacements required -> return empty map

			return ApiUsage.of(new ArrayList<>(deps),
					Collections.<IN4JSProject, IN4JSProject> emptyMap(), apiImplMapping);
		}

		// special case: implementationId is null
		// there must be exactly one implementation for the API projects in the workspace
		if (implementationId == null) {
			final List<String> allImplIds = apiImplMapping.getAllImplIds();
			if (allImplIds.size() != 1) {
				if (throwOnError) {
					throw new IllegalStateException(
							"no implementationId specified while several are available in the workspace: "
									+ allImplIds);
				} else {
					// back out, further processing not possible without implementations id.
					return ApiUsage.of(new ArrayList<>(deps), Collections.emptyMap(), apiImplMapping, true);
				}
			} else {
				implementationId = allImplIds.get(0);
			}
		}

		final Map<IN4JSProject, IN4JSProject> apiImplProjectMapping = new LinkedHashMap<>();
		final List<String> missing = new ArrayList<>(); // projectIds of projects without an implementation
		for (IN4JSProject dep : deps) {
			if (dep != null) {
				final String depId = dep.getProjectId();
				if (depId != null && apiImplMapping.isApi(depId)) {
					// so: dep is an API project ...
					final IN4JSProject impl = apiImplMapping.getImpl(depId, implementationId);
					if (impl != null) {
						// so: impl is the implementation project for dep for implementation ID 'implementationId'
						apiImplProjectMapping.put(dep, impl);
					} else {
						// bad: no implementation for dep for implementation ID 'implementationId'
						missing.add(depId);
					}
				}
			}
		}
		if (!missing.isEmpty()) {
			if (throwOnError) {
				throw new IllegalStateException("no implementation for implementation ID \"" + implementationId
						+ "\" found for the following projects: " + Joiner.on(", ").join(missing));
			}
		}

		/// #-#-#-#-#-#-#-#-#-#-#-#-#-#-#
		// IDEBUG-506 look for additional dependencies, pulled in from api-impl project not yet processed:
		HashSet<IN4JSProject> processedDepProjects = deps;

		LinkedHashSet<IN4JSProject> tobeInspectedApiImplProjects = new LinkedHashSet<>();
		apiImplProjectMapping.entrySet().forEach(p -> {
			IN4JSProject v = p.getValue();
			if (!processedDepProjects.contains(v))
				tobeInspectedApiImplProjects.add(v);
		});
		// Collect transitive mappings. Populate with original ones.
		final Map<IN4JSProject, IN4JSProject> joinedApiImplProjectMapping = new LinkedHashMap<>(apiImplProjectMapping);
		while (!tobeInspectedApiImplProjects.isEmpty()) {

			// compute dependencies if necessary
			LinkedHashSet<IN4JSProject> batchedPivotDependencies = new LinkedHashSet<>();
			RecursionGuard<URI> guard = new RecursionGuard<>();
			for (IN4JSProject pivotProject : tobeInspectedApiImplProjects) {
				recursiveDependencyCollector(pivotProject, batchedPivotDependencies, guard);
			}
			tobeInspectedApiImplProjects.clear();

			List<IN4JSProject> batchedPivotNewDepList = batchedPivotDependencies.stream()
					.filter(p -> null != p && !processedDepProjects.contains(p)).collect(Collectors.toList());

			// new Api-mapping
			apiImplMapping.enhance(batchedPivotNewDepList, n4jsCore.findAllProjects());
			// go over new dependencies and decide:
			for (IN4JSProject pivNewDep : batchedPivotNewDepList) {
				final String depId = pivNewDep.getProjectId();
				if (apiImplMapping.isApi(depId)) {
					// API-mapping
					if (joinedApiImplProjectMapping.containsKey(pivNewDep)) {
						// already done.
					} else {
						// put new entry
						IN4JSProject pivImpl = apiImplMapping.getImpl(depId, implementationId);
						if (null != pivImpl) {
							joinedApiImplProjectMapping.put(pivNewDep, pivImpl);
							tobeInspectedApiImplProjects.add(pivImpl);
						} else {
							missing.add(depId);
						}
					}
				} else {
					// no API.
					if (!processedDepProjects.contains(pivNewDep)) {
						// add to deps
						processedDepProjects.add(pivNewDep);
					}
				}
			}
		}
		if (!missing.isEmpty()) {
			if (throwOnError) {
				throw new IllegalStateException("no implementation for implementation ID \"" + implementationId
						+ "\" found for the following projects: " + Joiner.on(", ").join(missing));
			}
		}

		return ApiUsage.of(implementationId, new ArrayList<>(deps), apiImplProjectMapping, apiImplMapping, missing);

	}

	/** DS to transport the results of the APIimpl computation for a concrete realization. */
	public static class ApiUsage {
		/** can be null, see {@link #implementationIdRequired} */
		final public String implementationId;
		/** list of dependent projects */
		final public List<IN4JSProject> projects;
		/** concrete mapping for the given implementationId */
		// TODO possible improvement: move the mapping to ApiImplMapping
		final public Map<IN4JSProject, IN4JSProject> concreteApiImplProjectMapping;
		/** general access to the apiImplMapping state */
		final public ApiImplMapping apiImplMapping;
		/** list of api projects missing an implementations for the given implementationId */
		final public List<String> missingImplementationIds;
		/** false if no Api-replacement has to take place. */
		final public boolean implementationIdRequired;

		private ApiUsage(String implementationatId, List<IN4JSProject> projects,
				Map<IN4JSProject, IN4JSProject> concreteApiImplProjectMapping,
				ApiImplMapping apiImplMapping, List<String> missingImplementationIds,
				boolean implementationIdRequired) {
			this.implementationId = implementationatId;
			this.apiImplMapping = apiImplMapping;
			this.concreteApiImplProjectMapping = concreteApiImplProjectMapping;
			this.projects = projects;
			this.missingImplementationIds = missingImplementationIds;
			this.implementationIdRequired = implementationIdRequired;
		}

		/**
		 * returns true, if the algorithm aborted/saw an error condition. The caller can still query the partially
		 * computed state, in particular the {@link #missingImplementationIds} or the internals of
		 * {@link #apiImplMapping}.
		 *
		 * Error states could be:
		 * <ul>
		 * <li>implementationId could not be computed</li>
		 * <li>apiImplMapping saw some severe errors in project setups</li>
		 * <li>some API are missing implementation for implementationId</li>
		 * </ul>
		 *
		 * @return true if an error occurred.
		 */
		public boolean isInErrorState() {
			return !missingImplementationIds.isEmpty() || apiImplMapping.hasErrors()
					|| (implementationIdRequired && implementationId == null);
		}

		/** Create standard result for required api-impl replacement. */
		private static ApiUsage of(String implementationId, List<IN4JSProject> projects,
				Map<IN4JSProject, IN4JSProject> concreteApiImplProjectMapping,
				ApiImplMapping apiImplMapping, List<String> missingImplementationIds) {
			return new ApiUsage(implementationId, projects, concreteApiImplProjectMapping, apiImplMapping,
					missingImplementationIds, true);
		}

		/** Create result for cases where no api-impl replacement is required. */
		private static ApiUsage of(List<IN4JSProject> projects,
				Map<IN4JSProject, IN4JSProject> concreteApiImplProjectMapping,
				ApiImplMapping apiImplMapping) {
			return new ApiUsage(null, projects, concreteApiImplProjectMapping, apiImplMapping, Collections.emptyList(),
					false);
		}

		/** Create result for cases where no no implemenationId could be determined. */
		private static ApiUsage of(List<IN4JSProject> projects,
				Map<IN4JSProject, IN4JSProject> concreteApiImplProjectMapping,
				ApiImplMapping apiImplMapping, boolean implementationIdRequired) {
			return new ApiUsage(null, projects, concreteApiImplProjectMapping, apiImplMapping, Collections.emptyList(),
					implementationIdRequired);
		}

	}

}
