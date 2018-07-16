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

import static com.google.common.collect.FluentIterable.from;
import static org.apache.log4j.Logger.getLogger;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.eclipse.n4js.n4mf.ProjectType;
import org.eclipse.n4js.projectModel.IN4JSArchive;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.IN4JSSourceContainerAware;
import org.eclipse.n4js.runner.exceptions.DependencyCycleDetectedException;
import org.eclipse.n4js.runner.exceptions.InsolvableRuntimeEnvironmentException;
import org.eclipse.n4js.runner.extension.RuntimeEnvironment;
import org.eclipse.n4js.validation.helper.SourceContainerAwareDependencyTraverser;

import com.google.common.base.Optional;
import com.google.inject.Inject;

/**
 * Helper that resolves Runtime Environments required to execute given {@link IN4JSProject}.
 */
public class RuntimeEnvironmentsHelper {

	private static final Logger LOGGER = getLogger(RuntimeEnvironmentsHelper.class);

	@Inject
	private IN4JSCore in4jscore;

	/**
	 * Returns the project in the current workspace for the given runtime environment ID.
	 */
	public Optional<IN4JSProject> findRuntimeEnvironmentProject(RuntimeEnvironment runtimeEnvironment) {
		return findRuntimeEnvironmentProject(runtimeEnvironment, getAllProjects());
	}

	/**
	 * Returns the project from the provided iterable of projects for the given runtime environment ID.
	 */
	public Optional<IN4JSProject> findRuntimeEnvironmentProject(RuntimeEnvironment runtimeEnvironment,
			Iterable<IN4JSProject> projects) {
		return from(projects)
				.filter(p -> isRuntimeEnvironemnt(p))
				.filter(p -> runtimeEnvironment.getProjectId().equals(p.getProjectId()))
				.first();
	}

	/**
	 * Analyzes the (transitive) dependencies of the provided {@link IN4JSProject} to check which of the runtime
	 * environments defined in the workspace can be used to run that project and returns their IDs in the form of
	 * literals from the {@link RuntimeEnvironment} enumeration.
	 * <p>
	 * More precisely, let P be an N4JS project and RL<sub>P</sub> the set of all runtime libraries that are directly or
	 * indirectly <em>required</em> by P or any of its direct or indirect dependencies. Then, a runtime environment RE
	 * with RL<sub>RE</sub> being the set of all runtime libraries directly or indirectly <em>provided</em> by RE
	 * (including those provided by runtime environments extended by RE) is assumed to support running project P iff RL
	 * <sub>P</sub> is a (not necessarily true) subset of RL<sub>RE</sub>. This method will return all runtime
	 * environments defined in the workspace that support running the given project.
	 *
	 * Note: this returned set contains directly compatible environments, and environments they extend.
	 *
	 * @param project
	 *            to analyze
	 * @return set of runtime environments defined in the workspace that may be used to run the given project.
	 * @throws InsolvableRuntimeEnvironmentException
	 *             when called on runtime environment or runtime library.
	 * @throws DependencyCycleDetectedException
	 *             if the corresponding project has a dependency cycle.
	 */
	public Set<RuntimeEnvironment> findCompatibleRuntimeEnvironments(IN4JSProject project) {
		if (isRuntimeEnvironemnt(project) || isRuntimeLibrary(project)) {
			throw new InsolvableRuntimeEnvironmentException(project);
		}
		List<IN4JSProject> reqRuntiemLibraries = collectRequiredRuntimeLibraries(project);
		return from(getAllProjects())
				.filter(p -> isRuntimeEnvironemnt(p))
				.transform(p -> new AbstractMap.SimpleEntry<>(p, getProjectProvidedRuntimeLibraries(p)))
				.filter(e -> e.getValue().containsAll(reqRuntiemLibraries))
				.transform(e -> e.getKey())
				.transformAndConcat(re -> getEnvironemntWithAncestors(re))
				.transform(rRE -> RuntimeEnvironment.fromProjectId(rRE.getProjectId()))
				.filter(rRE -> rRE != null)
				.toSet();
	}

	/**
	 * For a given environment returns set containing given environment and all environments it extends. If provided
	 * project is not of type {@link ProjectType#RUNTIME_ENVIRONMENT} then returns empty set.
	 */
	public Set<IN4JSProject> getEnvironemntWithAncestors(IN4JSProject project) {
		if (!project.getProjectType().equals(ProjectType.RUNTIME_ENVIRONMENT)) {
			return null;
		}
		Set<IN4JSProject> environemtns = new HashSet<>();
		environemtns.add(project);
		getEnvironemntWithAncestorsRecursive(project, environemtns);
		return environemtns;
	}

	private void getEnvironemntWithAncestorsRecursive(IN4JSProject project, Set<IN4JSProject> environemtns) {
		if (!project.getProjectType().equals(ProjectType.RUNTIME_ENVIRONMENT)) {
			return;
		}
		Optional<IN4JSSourceContainerAware> maybePArent = project.getExtendedRuntimeEnvironment();
		if (maybePArent.isPresent()) {
			IN4JSProject parent = (IN4JSProject) maybePArent.get();
			environemtns.add(parent);
			getEnvironemntWithAncestorsRecursive(parent, environemtns);
		}
		return;
	}

	private Iterable<IN4JSProject> getAllProjects() {
		return in4jscore.findAllProjects();
	}

	/**
	 * Analyzes all transitive dependencies of the provided provided {@link IN4JSProject} . Collects all dependencies
	 * that are of type {@link ProjectType#RUNTIME_LIBRARY}. Resulting collection contains no duplicates.
	 *
	 * @param project
	 *            to be analyzed
	 * @return list of transitive dependencies of type runtime library, no duplicates
	 */
	private List<IN4JSProject> collectRequiredRuntimeLibraries(IN4JSProject project) {
		if (new SourceContainerAwareDependencyTraverser(project).getResult().hasCycle()) {
			throw new DependencyCycleDetectedException(project);
		}
		Set<IN4JSProject> depsRuntimeLibraries = new HashSet<>();
		recursiveDependencyCollector(project, depsRuntimeLibraries, p -> isRuntimeLibrary(p));
		return new ArrayList<>(depsRuntimeLibraries);
	}

	/**
	 * Collects dependencies of the provided source container, by analyzing direct dependencies of the container and
	 * recursively their dependencies. Dependencies in form of {@link IN4JSSourceContainerAware} are mapped to
	 * {@link IN4JSProject}s, that is instances of {@link IN4JSProject} project are returned as they are, while
	 * instances of {@link IN4JSArchive} have contained project extracted.
	 *
	 * Discovered dependencies are collected only if they pass test specified by provided predicate.
	 *
	 * @param sourceContainer
	 *            whose dependencies will be collected
	 * @param collection
	 *            where dependencies are collected
	 * @param predicate
	 *            to test if given dependency should be collected
	 */
	private void recursiveDependencyCollector(IN4JSSourceContainerAware sourceContainer,
			Collection<IN4JSProject> collection,
			Predicate<IN4JSProject> predicate) {

		IN4JSProject project = (extractProject(sourceContainer));

		if (predicate.test(project))
			collection.add(project);

		sourceContainer.getAllDirectDependencies().forEach(
				dep -> recursiveDependencyCollector(dep, collection, predicate));
	}

	/**
	 * Analyzes passed project {@link IN4JSProject#getProvidedRuntimeLibraries()}. All provided project of type
	 * {@link ProjectType#RUNTIME_LIBRARY} are stored in transitive list. If project type of passed project is not
	 * {@link ProjectType#RUNTIME_ENVIRONMENT} then returns empty list.
	 *
	 * @param runtimeEnvironment
	 *            project to be examined
	 * @return transitive list of provided runtime libraries (might be empty, but not null)
	 */
	private List<IN4JSProject> getProjectProvidedRuntimeLibraries(IN4JSProject runtimeEnvironment) {
		Set<IN4JSProject> providedRuntimeLibraries = new HashSet<>();

		if (isRuntimeEnvironemnt(runtimeEnvironment))
			recursiveProvidedRuntimeLibrariesCollector(runtimeEnvironment.getProvidedRuntimeLibraries(),
					providedRuntimeLibraries, p -> isRuntimeLibrary(p));

		// include RLs provided by extended REs
		recursiveCollectRlFromChain(runtimeEnvironment, providedRuntimeLibraries);

		return new ArrayList<>(providedRuntimeLibraries);
	}

	private void recursiveCollectRlFromChain(IN4JSProject runtimeEnvironment, Collection<IN4JSProject> collection) {
		Optional<String> extended = runtimeEnvironment.getExtendedRuntimeEnvironmentId();
		if (extended.isPresent()) {
			String id = extended.get();
			List<IN4JSProject> extendedRE = from(getAllProjects()).filter(p -> id.equals(p.getProjectId()))
					.toList();

			if (extendedRE.isEmpty()) {
				return;
			}

			if (extendedRE.size() > 1) {
				LOGGER.debug("multiple projects match id " + id);
				LOGGER.error(new RuntimeException("Cannot obtain transitive list of provided libraries"));
				return;
			}

			IN4JSProject extendedRuntimeEnvironemnt = extendedRE.get(0);
			recursiveProvidedRuntimeLibrariesCollector(extendedRuntimeEnvironemnt.getProvidedRuntimeLibraries(),
					collection, p -> isRuntimeLibrary(p));

			recursiveCollectRlFromChain(extendedRuntimeEnvironemnt, collection);

		}
	}

	/**
	 * Maps passed collection of {@link IN4JSSourceContainerAware} to list of {@link IN4JSProject}, that is instances of
	 * {@link IN4JSProject} project are returned as they are, while instances of {@link IN4JSArchive} have contained
	 * project extracted. For each result of that transformation, examines its
	 * {@link IN4JSProject#getProvidedRuntimeLibraries()} to check if they pass predicate test. Instances that do are
	 * stored in the passed collection.
	 *
	 * Calls itself recursively on each processed element, accumulating results in collection passed along call chains.
	 *
	 * @param runtimeLibraries
	 *            list of source containers to analyze (usually of type {@link ProjectType#RUNTIME_LIBRARY})
	 * @param collection
	 *            where provided runtime libraries are collected
	 * @param predicate
	 *            to test if provided project is of type {@link ProjectType#RUNTIME_LIBRARY}
	 */
	private void recursiveProvidedRuntimeLibrariesCollector(
			com.google.common.collect.ImmutableList<? extends IN4JSSourceContainerAware> runtimeLibraries,
			Collection<IN4JSProject> collection, Predicate<IN4JSProject> predicate) {

		runtimeLibraries.forEach(runtimeLibrary -> {
			IN4JSProject project = (extractProject(runtimeLibrary));
			if (predicate.test(project))
				collection.add(project);
			recursiveProvidedRuntimeLibrariesCollector(project.getProvidedRuntimeLibraries(), collection, predicate);
		});
	}

	/**
	 * Map provided source container to instance of {@link IN4JSProject}, that is instances of {@link IN4JSProject}
	 * project are returned as they are, while instances of {@link IN4JSArchive} have contained project extracted.
	 *
	 * @param container
	 *            that is mapped to project
	 *
	 * @return project resulting from mapping
	 * @throws RuntimeException
	 *             if mapping cannot be performed
	 */

	private IN4JSProject extractProject(IN4JSSourceContainerAware container) {
		if (container instanceof IN4JSProject) {
			return (IN4JSProject) container;
		}
		if (container instanceof IN4JSArchive) {
			return ((IN4JSArchive) container).getProject();
		}
		throw new RuntimeException("Unknown instance type of container " + container.getClass().getName());
	}

	/**
	 * Compares two provided lists of {@link IN4JSProject}. Assumes both contain instances of type
	 * {@link ProjectType#RUNTIME_ENVIRONMENT}. Checks if either all elements of latter list are contained in first one,
	 * or if all elements of latter one are compatible (are in extend chain) of elements of the first one. If this check
	 * holds returns true, otherwise false (also when either of the lists is empty)
	 *
	 *
	 * @param runnerEnvironments
	 *            lists which must contain (might be indirectly via extend chain) elements of latter list
	 * @param requiredEnvironments
	 *            lists that is checked if it is supported by first one
	 * @return true if all elements of latter list are (directly or indirectly) compatible with elements of the first
	 *         list.
	 */
	public boolean containsAllCompatible(List<RuntimeEnvironment> runnerEnvironments,
			List<RuntimeEnvironment> requiredEnvironments) {

		if (runnerEnvironments.isEmpty() || requiredEnvironments.isEmpty()) {
			LOGGER.debug("cannot compare empty runtime environments lists");
			return false;
		}

		if (runnerEnvironments.containsAll(requiredEnvironments))
			return true;

		// check compatible / extend feature

		boolean result = true;

		List<IN4JSProject> allRuntimeEnvironments = from(getAllProjects())
				.filter(p -> isRuntimeEnvironemnt(p)).toList();

		Map<IN4JSProject, List<String>> reExtendedEnvironments = allRuntimeEnvironments.stream()
				.map(re -> getExtendedRuntimeEnvironmentsNames(re, allRuntimeEnvironments))
				.collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));

		// if runnerEnvironments (first param) would be single IN4JSProject (instead of collection)
		// code below could be simplified
		Iterator<RuntimeEnvironment> iterRuntimeEnvironment = runnerEnvironments.iterator();
		while (result && iterRuntimeEnvironment.hasNext()) {
			RuntimeEnvironment re = iterRuntimeEnvironment.next();
			List<IN4JSProject> listExtendedEnvironments = reExtendedEnvironments.keySet().stream()
					.filter(p -> p.getProjectId().equals(re.getProjectId())).collect(Collectors.toList());

			if (listExtendedEnvironments.size() != 1) {
				LOGGER.debug("Multiple projects with name "
						+ re.getProjectId()
						+ " : "
						+ listExtendedEnvironments.stream().map(p -> p.getProjectId())
								.reduce(new String(), (String r, String e) -> r += ", " + e));
				LOGGER.error("Cannot obtain project for name " + re.getProjectId());
				return false;
			}

			IN4JSProject extendedRuntimeEnvironment = listExtendedEnvironments.get(0);
			List<String> listExtendedEnvironemntsNames = reExtendedEnvironments.get(extendedRuntimeEnvironment);
			result = result
					&& requiredEnvironments.stream().map(bre -> {
						return bre.getProjectId();
					}).allMatch(breName -> listExtendedEnvironemntsNames.contains(breName));
		}
		return result;
	}

	/**
	 * Analyzes provided list of all runtime environments and creates {@link Entry} for mapping between particular
	 * runtime environment and ones it extends.
	 *
	 * @param runtimeEnv
	 *            RE for which mapping is created
	 * @param allRuntimeEnv
	 *            collection of REs for which are taken into account
	 * @return map entry of mapping between RE and REs it extends
	 */
	private AbstractMap.SimpleEntry<IN4JSProject, List<String>> getExtendedRuntimeEnvironmentsNames(
			IN4JSProject runtimeEnv,
			List<IN4JSProject> allRuntimeEnv) {
		Set<String> depsRuntimeLibraries = new HashSet<>();
		recursiveCompatibleEnvironemntCollector(runtimeEnv, depsRuntimeLibraries, p -> isRuntimeEnvironemnt(p),
				allRuntimeEnv);
		return new AbstractMap.SimpleEntry<>(runtimeEnv, new ArrayList<>(depsRuntimeLibraries));
	}

	/**
	 * recursively searches given source container for provided runtime environments
	 */
	private void recursiveCompatibleEnvironemntCollector(IN4JSSourceContainerAware sourceContainer,
			Collection<String> collection,
			Predicate<IN4JSProject> predicate, List<IN4JSProject> allRuntimeEnv) {

		IN4JSProject project = (extractProject(sourceContainer));

		if (predicate.test(project)) {
			com.google.common.base.Optional<String> oExtendedProjectId = project.getExtendedRuntimeEnvironmentId();

			if (!oExtendedProjectId.isPresent()) {
				return;
			}

			String extendedProjectId = oExtendedProjectId.get();
			collection.add(extendedProjectId);
			allRuntimeEnv
					.stream()
					.filter(p -> p.getProjectId().equals(extendedProjectId))
					.findFirst()
					.ifPresent(exre -> recursiveCompatibleEnvironemntCollector(exre, collection, predicate,
							allRuntimeEnv));

		}
	}

	private boolean isRuntimeEnvironemnt(IN4JSProject project) {
		return ProjectType.RUNTIME_ENVIRONMENT.equals(project.getProjectType());
	}

	private boolean isRuntimeLibrary(IN4JSProject project) {
		return ProjectType.RUNTIME_LIBRARY.equals(project.getProjectType());
	}
}
