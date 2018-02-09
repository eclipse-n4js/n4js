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
package org.eclipse.n4js.ui.handler;

import static org.eclipse.n4js.external.version.VersionConstraintFormatUtil.npmFormat;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.external.ExternalLibraryWorkspace;
import org.eclipse.n4js.internal.N4JSModel;
import org.eclipse.n4js.n4mf.ProjectDependencies;
import org.eclipse.n4js.n4mf.ProjectDependency;
import org.eclipse.n4js.n4mf.ProjectDescription;
import org.eclipse.n4js.n4mf.VersionConstraint;
import org.eclipse.n4js.n4mf.utils.parsing.ManifestValuesParsingUtil;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.ui.internal.EclipseBasedN4JSWorkspace;

/**
 * Allows to access data in the manifests of the provided {@link IN4JSProject}s. Unlike {@link N4JSModel} will return
 * data that describes unknown or missing projects. Unlike {@link ManifestValuesParsingUtil} it allows to read manifest
 * of known projects, and not arbitrary {@code .n4mf} file.
 */
class DependneciesHelper {
	@Inject
	private EclipseBasedN4JSWorkspace workspace;
	@Inject
	private ExternalLibraryWorkspace externalLibraryWorkspace;

	@Inject
	private IN4JSCore core;

	/**
	 * Calculates missing dependencies based on current workspace and library manager state.
	 *
	 * @return collection of missing dependencies infos TODO calculate versions too!
	 */
	public Map<String, String> calculateMissingDependnecies() {
		final Set<String> availableProjectsIds = new HashSet<>();
		final Map<String, String> versionedDependnecies = new HashMap<>();
		// TODO Core is filtering out broken project references
		// TODO use SimpleProjectDescripttion (from Manifest) to get dependnecies names

		core.findAllProjects().forEach(p -> {
			availableProjectsIds.add(p.getProjectId());
			getVersionedDependencies(p, versionedDependnecies);
			// if (p.isExternal()) {
			// availableProjectsIds.add(p.getProjectId());
			// } else {
			// availableProjectsIds.add(p.getProjectId());
			// // for now we just look at ordinary dependencies,
			// // but what about misconfigured projects and what special project types?
			// // TODO consider Libraries, ProvidedRuntimeLibraries, RequiredRuntimeLibraries,
			// // ImplementedProjects, TestedProjects, ExtendedRuntimeEnvironment
			// p.getDependencies().forEach(d -> workspaceDependenciesIds.add(d.getProjectId()));
			// }
		});

		// TODO we should validate list of missing dependencies,
		// we cannot install dependency for a broken definition, e.g. '*&^%$#@!_brokenDep'

		// TODO calculate and return versions too
		// TODO deal with version conflict

		availableProjectsIds.forEach(versionedDependnecies::remove);
		return versionedDependnecies;
	}

	/**
	 *
	 */
	public void getVersionedDependencies(IN4JSProject project, Map<String, String> dependencies) {
		ProjectDescription projectDescription = getProjectDescription(project.getLocation());
		if (projectDescription != null) {
			ProjectDependencies projectDependencies = projectDescription.getProjectDependencies();
			if (projectDependencies != null) {
				EList<ProjectDependency> projectDependencies2 = projectDependencies.getProjectDependencies();
				projectDependencies2.forEach(d -> {
					String version = "";
					VersionConstraint vc = d.getVersionConstraint();
					if (vc != null) {
						version = npmFormat(d.getVersionConstraint());
					}
					dependencies.put(d.getProject().getProjectId(), version);
				});

			}
		}
	}

	/**
	 * Mimic hidden {@link N4JSModel#getProjectDescription(URI)}.
	 */
	@SuppressWarnings("javadoc")
	protected ProjectDescription getProjectDescription(URI location) {
		ProjectDescription description = workspace.getProjectDescription(location);
		if (null == description) {
			description = externalLibraryWorkspace.getProjectDescription(location);
		}
		return description;
	}

}
