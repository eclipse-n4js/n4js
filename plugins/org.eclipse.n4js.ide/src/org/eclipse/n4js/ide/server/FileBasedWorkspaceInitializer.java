/**
 * Copyright (c) 2019 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.server;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.ide.xtext.server.XIWorkspaceConfigFactory;
import org.eclipse.n4js.ide.xtext.server.XLanguageServerImpl;
import org.eclipse.n4js.internal.FileBasedWorkspace;
import org.eclipse.n4js.internal.N4JSBrokenProjectException;
import org.eclipse.n4js.internal.lsp.N4JSWorkspaceConfig;
import org.eclipse.n4js.projectDescription.ProjectDescription;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.locations.FileURI;
import org.eclipse.n4js.utils.ProjectDescriptionLoader;
import org.eclipse.n4js.utils.ProjectDiscoveryHelper;
import org.eclipse.n4js.utils.URIUtils;
import org.eclipse.n4js.xtext.workspace.XIWorkspaceConfig;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 *
 */
@Singleton
public class FileBasedWorkspaceInitializer implements XIWorkspaceConfigFactory {
	private static Logger LOG = Logger.getLogger(XLanguageServerImpl.class);

	@Inject
	private IN4JSCore n4jsCore;

	@Inject
	private ProjectDescriptionLoader projectDescriptionLoader;

	@Inject
	private FileBasedWorkspace workspace;

	private ProjectDiscoveryHelper projectDiscoveryHelper;

	private URI knownWorkspaceBaseURI = null;

	/** Sets {@link #projectDiscoveryHelper} */
	@Inject
	public void setProjectDiscoveryHelper(ProjectDiscoveryHelper projectDiscoveryHelper) {
		this.projectDiscoveryHelper = projectDiscoveryHelper;
	}

	@Override
	public XIWorkspaceConfig createWorkspaceConfig(URI workspaceBaseURI) {
		try {
			if (workspaceBaseURI.equals(knownWorkspaceBaseURI)) {
				return new N4JSWorkspaceConfig(workspaceBaseURI, n4jsCore);
			}

			// TODO is this correct if we have multiple workspace URIs?
			workspace.clear();

			File workspaceRoot = URIUtils.toFile(workspaceBaseURI);

			Set<Path> allProjectLocations = projectDiscoveryHelper.collectAllProjectDirs(workspaceRoot.toPath());

			List<FileURI> allProjectURIs = new ArrayList<>();
			for (Path path : allProjectLocations) {
				allProjectURIs.add(new FileURI(path.toFile()));
			}

			registerProjectsToFileBasedWorkspace(allProjectURIs);

			return new N4JSWorkspaceConfig(workspaceBaseURI, n4jsCore);

		} finally {
			this.knownWorkspaceBaseURI = workspaceBaseURI;
		}
	}

	/**
	 * Registers provided project URIs in the given workspace.
	 *
	 * Skips {@link IN4JSProject}s that are already registered with the given {@code workspace}.
	 *
	 */
	private void registerProjectsToFileBasedWorkspace(Iterable<FileURI> projectURIs) {
		// TODO GH-783 refactor FileBasedWorkspace, https://github.com/eclipse/n4js/issues/783
		// this is reverse mapping of the one that is kept in the workspace
		Map<String, FileURI> registeredProjects = new HashMap<>();
		workspace.getAllProjectLocationsIterator().forEachRemaining(uri -> {
			String projectName = workspace.getProjectDescription(uri).getProjectName();
			registeredProjects.put(projectName, uri);
		});

		// register all projects with the file based workspace.
		for (FileURI projectURI : projectURIs) {
			final ProjectDescription projectDescription = projectDescriptionLoader
					.loadProjectDescriptionAtLocation(projectURI);

			if (projectDescription == null) {
				throw new RuntimeException(
						"Cannot load project description for project at " + projectURI
								+ ". Make sure the project contains a valid package.json file.");
			}

			final String projectName = projectDescription.getProjectName();

			if (skipRegistering(projectName, projectURI, registeredProjects)) {
				LOG.debug("Skipping already registered project '" + projectURI + "'");
				/*
				 * We could call FileBasedWorkspace.registerProject which would fail silently. Still to avoid potential
				 * side effects and to keep {@code registeredProjects} management simpler,we will skip it explicitly.
				 */
				continue;
			}

			try {
				LOG.debug("Registering project '" + projectURI + "'");
				workspace.registerProject(projectURI, projectDescription);
				registeredProjects.put(projectName, projectURI);
			} catch (N4JSBrokenProjectException e) {
				throw new RuntimeException("Unable to register project '" + projectURI + "'", e);
			}
		}
	}

	/**
	 * Utility for deciding if a given project location should be registered in the FileBasedWorkspace. Note that this
	 * method has three "return values". {@code false} if provided project manifest describes new project that has to be
	 * registered. {@code false} when project manifest describes already known project in the same location, in which
	 * case project is safe to be skipped. {@code N4JSCompileException} is thrown when provided project manifest
	 * describes already known project but in different location in which case compilation should be stopped.
	 *
	 * @param projectName
	 *            of the new project to be considered for registering
	 * @param projectLocation
	 *            of the new project to be considered for registering
	 * @param registeredProjects
	 *            local cache of already known projects
	 * @return {@code false} if projects needs to be registered
	 */
	private boolean skipRegistering(String projectName, FileURI projectLocation,
			Map<String, FileURI> registeredProjects) {

		// new ID, don't skip registering
		if (!registeredProjects.containsKey(projectName))
			return false;

		FileURI registeredProjectLocation = registeredProjects.get(projectName);

		// duplicate is the same location, so the same project passed twice, skip registering
		if (projectLocation.equals(registeredProjectLocation))
			return true;

		if (registeredProjectLocation == null)
			// our local cache of known projects is out of sync with FileBasedWorkspace -> stop compilation
			throw new RuntimeException("Duplicate project id [" + projectName
					+ "]. Already registered project at " + registeredProjects.get(projectName)
					+ ", trying to register project at " + projectLocation + ".");

		// Return true here causes shadowing of projects with the same name. Will be removed with GH-1314
		return true;
	}

}
