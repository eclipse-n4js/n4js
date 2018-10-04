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
package org.eclipse.n4js.projectModel.dependencies;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.external.ExternalLibraryWorkspace;
import org.eclipse.n4js.external.NpmLogger;
import org.eclipse.n4js.internal.N4JSModel;
import org.eclipse.n4js.internal.N4JSProject;
import org.eclipse.n4js.projectDescription.ProjectDescription;
import org.eclipse.n4js.projectDescription.ProjectReference;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.semver.SemverMatcher;
import org.eclipse.n4js.semver.SemverUtils;
import org.eclipse.n4js.semver.Semver.NPMVersionRequirement;
import org.eclipse.n4js.semver.Semver.VersionNumber;
import org.eclipse.n4js.semver.model.SemverSerializer;

import com.google.common.base.Joiner;

/**
 * Allows to access data in the manifests of the provided {@link IN4JSProject}s. Unlike {@link N4JSModel} will return
 * data that describes unknown or missing projects. of known projects, and not arbitrary {@code .n4mf} file.
 */
public class ProjectDependenciesHelper {
	private static final Logger LOGGER = Logger.getLogger(ProjectDependenciesHelper.class);

	@Inject
	private IN4JSCore core;
	@Inject
	private N4JSModel model;
	@Inject
	private ExternalLibraryWorkspace externalWS;
	@Inject
	private NpmLogger logger;

	/**
	 * Same as {@link #calculateDependenciesOfProjects(Collection)} but uses all user workspace projects as input.
	 */
	public Map<String, NPMVersionRequirement> computeDependenciesOfWorkspace() {
		List<N4JSProject> wsProjects = new LinkedList<>();
		for (IN4JSProject prj : core.findAllProjects()) {
			if (!prj.isExternal()) {
				wsProjects.add((N4JSProject) prj);
			}
		}
		return calculateDependenciesOfProjects(wsProjects);
	}

	/**
	 * Creates map of project name to version of all dependencies of the given projects. Note that the list of
	 * dependencies is merged, but in case of conflicting versions info, workspace based data wins, e.g.
	 *
	 * <pre>
	 *  <ul>
	 *   <li> platform files requests "express" but no project depends on "express" then express is installed (in latest version)</li>
	 *   <li> platform files requests "express@2.0.0" but workspace project depends on "express@1.0.0"  then express is installed in version "2.0.0"</li>
	 *  <ul>
	 * </pre>
	 */
	public Map<String, NPMVersionRequirement> calculateDependenciesOfProjects(Collection<N4JSProject> projects) {
		List<ProjectDescription> projectsDescriptions = getProjectsDescriptions(projects);
		Map<String, NPMVersionRequirement> versionedPackages = updateMissingDependenciesMap(projectsDescriptions);

		logResult(versionedPackages);
		return versionedPackages;
	}

	private List<ProjectDescription> getProjectsDescriptions(Collection<N4JSProject> projects) {
		List<ProjectDescription> pds = new LinkedList<>();
		for (IN4JSProject prj : projects) {
			URI prjLocation = prj.getLocation();
			if (prjLocation != null) {
				ProjectDescription projectDescription = model.getProjectDescription(prjLocation);
				if (projectDescription != null) {
					// not all eclipse projects have a package.json file
					pds.add(projectDescription);
				}
			}
		}
		return pds;
	}

	private void logResult(Map<String, NPMVersionRequirement> versionedPackages) {
		if (LOGGER.isDebugEnabled()) {
			StringJoiner messages = new StringJoiner(System.lineSeparator());
			messages.add("dependencies to install: ");
			versionedPackages.forEach((id, v) -> messages.add(" - " + id + v));
			LOGGER.debug(messages);
		}
	}

	/**
	 * In case some dependencies are shipped code, the following will be done here: All shipped code will be installed
	 * with the given version and hence shadow the shipped code of the IDE.
	 * <p>
	 * In case the dependencies do not contain shipped code, nothing is done.
	 */
	public void fixDependenciesToInstall(Map<String, NPMVersionRequirement> dependenciesToInstall) {
		Map<String, VersionNumber> projectNamesOfShippedCode = externalWS.getProjectInfos();
		removeDependenciesToShippedCodeIfVersionMatches(dependenciesToInstall, projectNamesOfShippedCode);
		addDependenciesForRemainingShippedCode(dependenciesToInstall, projectNamesOfShippedCode.keySet());
		logShippedCodeInstallationStatus(dependenciesToInstall, projectNamesOfShippedCode.keySet());
	}

	/**
	 * Removes from map 'dependenciesToInstall' all entries for projects that are in the shipped code, if and only if
	 * the version provided by the shipped code matches the version requirement.
	 */
	private void removeDependenciesToShippedCodeIfVersionMatches(
			Map<String, NPMVersionRequirement> dependenciesToInstall,
			Map<String, VersionNumber> projectNamesOfShippedCode) {

		Set<String> prjNames = new HashSet<>(dependenciesToInstall.keySet());
		for (String projectName : prjNames) {
			VersionNumber availableVersionInShippedCode = projectNamesOfShippedCode.get(projectName);
			if (availableVersionInShippedCode != null) {
				NPMVersionRequirement versionRequirement = dependenciesToInstall.get(projectName);
				if (versionRequirement != null
						&& SemverMatcher.matchesStrict(availableVersionInShippedCode, versionRequirement)) {
					// so remove from list of dependencies to be installed:
					dependenciesToInstall.remove(projectName);
				}
			}
		}
	}

	/**
	 * If map 'dependenciesToInstall' contains at least one project that is among the shipped code projects, this method
	 * will add new entries to map 'dependenciesToInstall' for all remaining shipped code projects, using the same
	 * version constraint as for those already in the map.
	 * <p>
	 * Rationale is that when shadowing a shipped code project with a newly installed NPM in the library manager, then
	 * *all* shipped code projects need to be shadowed, because shipped code is now published in clusters in which each
	 * project depends on the others with a fixed version.
	 */
	private void addDependenciesForRemainingShippedCode(Map<String, NPMVersionRequirement> dependenciesToInstall,
			Set<String> projectNamesOfShippedCode) {
		Set<String> projectNamesOfShippedCodeToInstall = new HashSet<>(dependenciesToInstall.keySet());
		projectNamesOfShippedCodeToInstall.retainAll(projectNamesOfShippedCode);
		if (!projectNamesOfShippedCodeToInstall.isEmpty()
				&& projectNamesOfShippedCodeToInstall.size() < projectNamesOfShippedCode.size()) {
			Map<String, NPMVersionRequirement> versionRequirementsOfShippedCodeToInstall = projectNamesOfShippedCodeToInstall
					.stream()
					.map(id -> dependenciesToInstall.get(id))
					.collect(Collectors.toMap(SemverSerializer::serialize, Function.identity(), (vr1, vr2) -> vr1));
			NPMVersionRequirement versionConstraint = versionRequirementsOfShippedCodeToInstall.values().stream()
					.findFirst().orElse(null);
			if (versionConstraint != null) {
				if (versionRequirementsOfShippedCodeToInstall.size() > 1) {
					logger.logInfo("WARNING: differing version requirements for shipped code: "
							+ Joiner.on(", ").join(versionRequirementsOfShippedCodeToInstall.values())
							+ "; using: " + versionConstraint);
				}
				for (String id : projectNamesOfShippedCode) {
					if (!projectNamesOfShippedCodeToInstall.contains(id)) {
						dependenciesToInstall.put(id, versionConstraint);
					}
				}
			}
		}
	}

	/** Pure logging, won't change the arguments. */
	private void logShippedCodeInstallationStatus(Map<String, NPMVersionRequirement> dependenciesToInstall,
			Set<String> projectNamesOfShippedCode) {
		// compute status of shipped code installation
		Map<String, NPMVersionRequirement> shippedCodeToInstall = new HashMap<>(dependenciesToInstall);
		Set<String> nonShadowedShippedCode = new HashSet<>(projectNamesOfShippedCode);
		shippedCodeToInstall.keySet().retainAll(projectNamesOfShippedCode);
		nonShadowedShippedCode.removeAll(shippedCodeToInstall.keySet());
		// report status to user
		if (shippedCodeToInstall.isEmpty()) {
			logger.logInfo("Not going to shadow any shipped code with installed packages.");
		} else {
			String separator = "\n\t";
			logger.logInfo("The following installed packages will shadow shipped code:" + separator
					+ shippedCodeToInstall.entrySet().stream()
							.map(e -> e.getKey() + "@" + e.getValue())
							.collect(Collectors.joining(separator)));
			if (nonShadowedShippedCode.isEmpty()) {
				logger.logInfo("The entire shipped code will be shadowed.");
			} else {
				logger.logInfo("WARNING: shipped code will be shadowed only partially; non-shadowed shipped projects:"
						+ separator + Joiner.on(separator).join(nonShadowedShippedCode));
			}
		}
	}

	/**
	 * Updates provided mapping of {@code id->version} with information computed from the provided project descriptions.
	 * Returned map will not contain entries where the key matches {@code ProjectDescription#getProjectName() id} of the
	 * processed descriptions.
	 *
	 * Note that {@code ids} of the returned dependencies are not validated.
	 *
	 * Note that in case of dependency being defined in multiple places of the dependency graph only one mapping will be
	 * present. In case of different versions simple resolution is performed, first found with non empty version is
	 * used.
	 */
	private static Map<String, NPMVersionRequirement> updateMissingDependenciesMap(
			List<ProjectDescription> projectDescriptions) {

		Map<String, NPMVersionRequirement> versionedPackages = new HashMap<>();
		projectDescriptions.forEach(pd -> {
			// in case we get non N4JS projects, user docs projects that are not N4JS projects, or something created by
			// the plugins e.g RemoteSystemsTempFiles (see https://stackoverflow.com/q/3627463/52564 )
			if (pd != null) {
				updateFromProjectDescription(versionedPackages, pd);
			}
		});
		for (ProjectDescription pd : projectDescriptions) {
			versionedPackages.remove(pd.getProjectName());
		}

		return versionedPackages;
	}

	/** Add to the provided map all possible dependencies based on the {@link ProjectDescription} */
	private static void updateFromProjectDescription(Map<String, NPMVersionRequirement> dependencies,
			ProjectDescription pd) {
		if (pd == null) {
			return;
		}
		Stream.of(
				pd.getProjectDependencies().stream().map(DependencyInfo::create),
				// TODO GH-613, user projects can be misconfigured
				pd.getProvidedRuntimeLibraries().stream().map(DependencyInfo::create),
				getVersionedExtendedRuntimeEnvironment(pd),
				pd.getImplementedProjects().stream().map(DependencyInfo::create))
				.reduce(Stream::concat)
				.orElseGet(Stream::empty)
				.forEach(info -> dependencies.merge(info.name, info.version, ProjectDependenciesHelper::resolve));
	}

	/**
	 * Resolve conflict between two version requirements. Simple strategy - returns first if it is not empty and not
	 * "*".
	 * <p>
	 * This implements the following heuristic: if we encounter (during dependency collection) two version requirements
	 * for the same npm package, we always use the first version requirement encountered, unless the first version is a
	 * form of version requirement which does not provide much information, i.e. null, "", or "*" (then we use the
	 * second version).
	 * <p>
	 * TODO GH-1017 improve this heuristic
	 */
	private static NPMVersionRequirement resolve(NPMVersionRequirement vr1, NPMVersionRequirement vr2) {
		if (vr1 == null
				|| SemverUtils.isEmptyVersionRequirement(vr1)
				|| SemverUtils.isWildcardVersionRequirement(vr1)) {
			return vr2;
		}
		return vr1;
	}

	/** TODO https://github.com/eclipse/n4js/issues/613 */
	private static Stream<DependencyInfo> getVersionedExtendedRuntimeEnvironment(ProjectDescription description) {
		final ProjectReference re = description.getExtendedRuntimeEnvironment();
		return re != null ? Stream.of(re).map(DependencyInfo::create) : Stream.empty();
	}
}
