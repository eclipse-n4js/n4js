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
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.eclipse.n4js.projectDescription.ProjectDescription;
import org.eclipse.n4js.projectDescription.ProjectType;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;
import org.eclipse.n4js.runner.exceptions.DependencyCycleDetectedException;
import org.eclipse.n4js.runner.exceptions.InsolvableRuntimeEnvironmentException;
import org.eclipse.n4js.runner.extension.RuntimeEnvironment;
import org.eclipse.n4js.utils.DependencyCycle;
import org.eclipse.n4js.utils.DependencyTraverser;
import org.eclipse.n4js.utils.DependencyTraverser.DependencyProvider;
import org.eclipse.n4js.utils.DependencyTraverser.DependencyVisitor;
import org.eclipse.n4js.validation.helper.SourceContainerAwareDependencyProvider;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;

/**
 * Helper that resolves {@link ProjectType#RUNTIME_ENVIRONMENT}s, required to execute a given {@link IN4JSProject}.
 */
public class RuntimeEnvironmentsHelper {

	private static final Logger LOGGER = getLogger(RuntimeEnvironmentsHelper.class);

	@Inject
	private IN4JSCore in4jscore;

	/**
	 * Returns the project in the current workspace for the given runtime environment ID.
	 */
	public Optional<? extends IN4JSProject> findRuntimeEnvironmentProject(RuntimeEnvironment runtimeEnvironment) {
		return findRuntimeEnvironmentProject(runtimeEnvironment, getAllProjects());
	}

	/**
	 * Returns the project from the provided iterable of projects for the given runtime environment ID.
	 */
	public Optional<? extends IN4JSProject> findRuntimeEnvironmentProject(RuntimeEnvironment runtimeEnvironment,
			Iterable<? extends IN4JSProject> projects) {
		return from(projects)
				.filter(p -> isRuntimeEnvironment(p))
				.filter(p -> runtimeEnvironment.getProjectName().equals(p.getProjectName()))
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
		if (isRuntimeEnvironment(project) || isRuntimeLibrary(project)) {
			throw new InsolvableRuntimeEnvironmentException(project);
		}
		Collection<IN4JSProject> reqRuntimeLibraries = collectRequiredRuntimeLibraries(project);
		return from(getAllProjects())
				.filter(p -> isRuntimeEnvironment(p))
				.transform(p -> new AbstractMap.SimpleEntry<>(p, getProjectProvidedRuntimeLibraries(p)))
				.filter(e -> e.getValue().containsAll(reqRuntimeLibraries))
				.transform(e -> e.getKey())
				.transformAndConcat(re -> getRuntimeEnvironmentAndAllExtendedEnvironments(re))
				.transform(rRE -> RuntimeEnvironment.fromProjectName(rRE.getProjectName()))
				.filter(rRE -> rRE != null)
				.toSet();
	}

	/**
	 * For a given environment, this method returns the set containing given environment and all environments it
	 * extends.
	 *
	 * If the provided project is not of type {@link ProjectType#RUNTIME_ENVIRONMENT}, this method returns an empty set.
	 */
	public Set<IN4JSProject> getRuntimeEnvironmentAndAllExtendedEnvironments(IN4JSProject project) {
		if (!project.getProjectType().equals(ProjectType.RUNTIME_ENVIRONMENT)) {
			return null;
		}

		// obtain all extended REs
		final HashSet<IN4JSProject> allREs = new HashSet<>(getExtendedRuntimeEnvironments(project));
		// add base RE itself
		allREs.add(project);

		return allREs;
	}

	/**
	 * Analyzes all transitive dependencies of the provided {@link IN4JSProject}. Collects all dependencies that are of
	 * type {@link ProjectType#RUNTIME_LIBRARY}. The resulting collection contains no duplicates.
	 *
	 * @param project
	 *            to be analyzed
	 * @return list of transitive dependencies of type runtime library, no duplicates
	 */
	private ImmutableList<IN4JSProject> collectRequiredRuntimeLibraries(IN4JSProject project) {
		Set<IN4JSProject> runtimeLibraryDependencies = new HashSet<>();

		final DependencyVisitor<IN4JSProject> visitor = new ProjectsCollectingDependencyVisitor(
				runtimeLibraryDependencies, p -> isRuntimeLibrary(p));
		final DependencyTraverser<IN4JSProject> traverser = new DependencyTraverser<>(
				project, visitor, new SourceContainerAwareDependencyProvider(true), true);

		// traverse and check for cycles
		final DependencyCycle<IN4JSProject> result = traverser.findCycle();

		// check whether a dependency cycle between workspace projects was discovered
		if (result.hasCycle()) {
			throw new DependencyCycleDetectedException(project);
		}

		return ImmutableList.copyOf(runtimeLibraryDependencies);
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

		if (isRuntimeEnvironment(runtimeEnvironment)) {
			collectProvidedRuntimeLibraries(runtimeEnvironment, providedRuntimeLibraries);
		}

		// include RLs provided by extended REs and runtimeEnvironment itself
		collectRuntimeLibrariesFromExtendedRuntimeEnvironments(runtimeEnvironment, providedRuntimeLibraries);

		return new ArrayList<>(providedRuntimeLibraries);
	}

	/**
	 * Collects all runtime libraries that are provided by all runtime environments, {@code runtimeEnvironment} extends.
	 *
	 * Does not include runtime libraries that are only provided directly by {@code runtimeEnvironment}.
	 */
	private void collectRuntimeLibrariesFromExtendedRuntimeEnvironments(IN4JSProject runtimeEnvironment,
			Collection<IN4JSProject> collection) {
		// collect all provided runtime environments by all extended runtime environments of runtimeEnvironment
		// and runtimeEnvironment itself
		for (IN4JSProject extendedRuntimeEnvironment : getExtendedRuntimeEnvironments(runtimeEnvironment)) {
			collectProvidedRuntimeLibraries(extendedRuntimeEnvironment, collection);
		}
	}

	/**
	 * Maps passed collection of {@link IN4JSProject} to list of {@link IN4JSProject}, that is instances of
	 * {@link IN4JSProject} project are returned. For each result of that transformation, examines its
	 * {@link IN4JSProject#getProvidedRuntimeLibraries()} to check if they are runtime library projects.
	 *
	 * All discovered runtime library projects are then added to the given {@code collection}.
	 *
	 * @param project
	 *            project whose provided runtime libraries should be collected
	 * @param collection
	 *            where provided runtime libraries are collected
	 */
	private void collectProvidedRuntimeLibraries(IN4JSProject project, Collection<IN4JSProject> collection) {
		// collect provided runtime libraries into 'collection'
		final ProjectsCollectingDependencyVisitor visitor = new ProjectsCollectingDependencyVisitor(collection,
				s -> isRuntimeLibrary(s));
		// traverse provided runtime libraries only
		final DependencyProvider<IN4JSProject> dependencyProvider = p -> p.getProvidedRuntimeLibraries();
		final DependencyTraverser<IN4JSProject> traverser = new DependencyTraverser<>(project, visitor,
				dependencyProvider, true);
		// trigger actual traversal
		traverser.traverse();
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

		List<? extends IN4JSProject> allRuntimeEnvironments = from(getAllProjects())
				.filter(p -> isRuntimeEnvironment(p)).toList();

		Map<IN4JSProject, List<N4JSProjectName>> reExtendedEnvironments = allRuntimeEnvironments.stream()
				.collect(Collectors.toMap(e -> e, e -> getExtendedRuntimeEnvironmentsIds(e, allRuntimeEnvironments)));

		// if runnerEnvironments (first param) would be single IN4JSProject (instead of collection)
		// code below could be simplified
		Iterator<RuntimeEnvironment> iterRuntimeEnvironment = runnerEnvironments.iterator();
		while (result && iterRuntimeEnvironment.hasNext()) {
			RuntimeEnvironment re = iterRuntimeEnvironment.next();
			List<IN4JSProject> listExtendedEnvironments = reExtendedEnvironments.keySet().stream()
					.filter(p -> p.getProjectName().equals(re.getProjectName())).collect(Collectors.toList());

			if (listExtendedEnvironments.size() != 1) {
				LOGGER.debug("Multiple projects with name "
						+ re.getProjectName()
						+ " : "
						+ listExtendedEnvironments.stream().map(p -> p.getProjectName().getRawName())
								.reduce(new String(), (String r, String e) -> r += ", " + e));
				LOGGER.error("Cannot obtain project for name " + re.getProjectName());
				return false;
			}

			IN4JSProject extendedRuntimeEnvironment = listExtendedEnvironments.get(0);
			List<N4JSProjectName> listExtendedEnvironmentsNames = reExtendedEnvironments
					.get(extendedRuntimeEnvironment);
			result = result && requiredEnvironments.stream().map(bre -> {
				return bre.getProjectName();
			}).allMatch(breName -> listExtendedEnvironmentsNames.contains(breName));
		}
		return result;
	}

	/**
	 * Analyzes provided list of all runtime environments and returns a list of the project IDs of
	 * {@link ProjectType#RUNTIME_ENVIRONMENT} projects that {@code runtimeEnv} extends.
	 *
	 * @param runtimeEnv
	 *            RE for which mapping is created
	 * @param allRuntimeEnv
	 *            collection of REs for which are taken into account
	 * @return map entry of mapping between RE and REs it extends
	 */
	private List<N4JSProjectName> getExtendedRuntimeEnvironmentsIds(
			IN4JSProject runtimeEnv,
			List<? extends IN4JSProject> allRuntimeEnv) {
		return getExtendedRuntimeEnvironments(runtimeEnv).stream()
				.map(p -> p.getProjectName()).collect(Collectors.toList());
	}

	/**
	 * Returns a list of the {@link IN4JSProject} representation of the {@link ProjectType#RUNTIME_ENVIRONMENT} projects
	 * that can be obtained by following the inheritance hierarchy defined by
	 * {@link ProjectDescription#getExtendedRuntimeEnvironment()}.
	 *
	 * Does not include {@code runtimeEnvironment} itself.
	 */
	private List<IN4JSProject> getExtendedRuntimeEnvironments(IN4JSProject runtimeEnvironment) {
		final List<IN4JSProject> runtimeEnvironments = new ArrayList<>();
		Optional<N4JSProjectName> extended = runtimeEnvironment.getExtendedRuntimeEnvironmentId();

		while (extended.isPresent()) {
			N4JSProjectName id = extended.get();
			List<? extends IN4JSProject> extendedRE = from(getAllProjects()).filter(p -> id.equals(p.getProjectName()))
					.toList();

			if (extendedRE.isEmpty()) {
				break;
			}

			if (extendedRE.size() > 1) {
				LOGGER.debug("multiple projects match id " + id);
			}

			final IN4JSProject extendedRuntimeEnvironment = extendedRE.get(0);

			runtimeEnvironments.add(extendedRuntimeEnvironment);

			// check next element in inheritance hierarchy
			extended = extendedRuntimeEnvironment.getExtendedRuntimeEnvironmentId();
		}

		return runtimeEnvironments;
	}

	private static boolean isRuntimeEnvironment(IN4JSProject project) {
		return ProjectType.RUNTIME_ENVIRONMENT.equals(project.getProjectType());
	}

	private static boolean isRuntimeLibrary(IN4JSProject project) {
		return ProjectType.RUNTIME_LIBRARY.equals(project.getProjectType());
	}

	private Iterable<? extends IN4JSProject> getAllProjects() {
		return in4jscore.findAllProjects();
	}

	/** A {@link DependencyVisitor} that collect a filtered set of discovered transitive dependencies. */
	private final class ProjectsCollectingDependencyVisitor implements DependencyVisitor<IN4JSProject> {
		private final Collection<IN4JSProject> collectedProjects;
		private final Predicate<IN4JSProject> projectFilter;

		/** */
		private ProjectsCollectingDependencyVisitor(Collection<IN4JSProject> depsRuntimeLibraries,
				Predicate<IN4JSProject> projectFilter) {
			this.projectFilter = projectFilter;
			this.collectedProjects = depsRuntimeLibraries;
		}

		@Override
		public void accept(IN4JSProject p) {
			if (projectFilter.test(p)) {
				// collect runtime library projects only
				collectedProjects.add(p);
			}

		}
	}
}
