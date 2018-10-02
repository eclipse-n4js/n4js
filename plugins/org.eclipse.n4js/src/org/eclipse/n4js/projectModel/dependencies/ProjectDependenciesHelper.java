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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Stream;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.internal.N4JSModel;
import org.eclipse.n4js.internal.N4JSProject;
import org.eclipse.n4js.projectDescription.ProjectDescription;
import org.eclipse.n4js.projectDescription.ProjectReference;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.semver.SemverUtils;
import org.eclipse.n4js.semver.Semver.NPMVersionRequirement;

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
		Map<String, NPMVersionRequirement> versionedPackages = updateMissingDependenciesMap(
				getProjectsDescriptions(projects));

		logResult(versionedPackages);
		return versionedPackages;
	}

	private List<ProjectDescription> getProjectsDescriptions(Collection<N4JSProject> projects) {
		List<ProjectDescription> pds = new LinkedList<>();
		for (IN4JSProject prj : projects) {
			URI prjLocation = prj.getLocation();
			if (prjLocation != null) {
				pds.add(model.getProjectDescription(prjLocation));
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
