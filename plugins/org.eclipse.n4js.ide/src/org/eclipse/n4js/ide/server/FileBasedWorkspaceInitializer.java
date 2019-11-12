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
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.generator.headless.BuildSet;
import org.eclipse.n4js.generator.headless.BuildSetComputer;
import org.eclipse.n4js.generator.headless.HeadlessHelper;
import org.eclipse.n4js.generator.headless.N4JSCompileException;
import org.eclipse.n4js.ide.xtext.server.XIWorkspaceConfigFactory;
import org.eclipse.n4js.internal.FileBasedWorkspace;
import org.eclipse.n4js.internal.lsp.N4JSWorkspaceConfig;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.locations.FileURI;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;
import org.eclipse.n4js.utils.NodeModulesDiscoveryHelper;
import org.eclipse.n4js.utils.ProjectDiscoveryHelper;
import org.eclipse.xtext.workspace.IWorkspaceConfig;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 *
 */
@Singleton
@SuppressWarnings("restriction")
public class FileBasedWorkspaceInitializer implements XIWorkspaceConfigFactory {

	@Inject
	private IN4JSCore n4jsCore;

	@Inject
	private BuildSetComputer buildSetComputer;

	@Inject
	private HeadlessHelper headlessHelper;

	@Inject
	private FileBasedWorkspace workspace;

	@Inject
	private NodeModulesDiscoveryHelper nodeModulesDiscoveryHelper;

	@Inject
	private ProjectDiscoveryHelper projectDiscoveryHelper;

	@Override
	public IWorkspaceConfig getWorkspaceConfig(URI workspaceBaseURI) {
		try {
			// TODO is this correct if we have multiple workspace URIs?
			workspace.clear();

			File workspaceRoot = new File(workspaceBaseURI.toFileString());
			LinkedHashSet<Path> allProjectLocations = projectDiscoveryHelper
					.collectAllProjectDirs(workspaceRoot.toPath());

			List<FileURI> allProjectURIs = new LinkedList<>();
			for (Path path : allProjectLocations) {
				allProjectURIs.add(new FileURI(path.toFile()));
			}

			headlessHelper.registerProjectsToFileBasedWorkspace(allProjectURIs, workspace);
			final BuildSet targetPlatformBuildSet = computeTargetPlatformBuildSet(allProjectURIs);
			// make sure all installed dependencies are registered with the workspace
			headlessHelper.registerProjects(targetPlatformBuildSet, workspace);
			return new N4JSWorkspaceConfig(n4jsCore);
		} catch (N4JSCompileException e) {
			// TODO exception handling
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	// TODO adapted from N4jscBase.computeTargetPlatformBuildSet(Collection<? extends IN4JSProject>)
	private BuildSet computeTargetPlatformBuildSet(Collection<? extends FileURI> workspaceProjects)
			throws N4JSCompileException {

		Set<N4JSProjectName> namesOfWorkspaceProjects = new LinkedHashSet<>();
		List<java.nio.file.Path> n4jsProjectPaths = new LinkedList<>();
		for (FileURI location : workspaceProjects) {
			IN4JSProject project = n4jsCore.create(location.toURI());
			n4jsProjectPaths.add(project.getLocation().toFileSystemPath());
			namesOfWorkspaceProjects.add(project.getProjectName());
		}

		List<File> toBuild = new ArrayList<>();
		for (java.nio.file.Path nmPath : nodeModulesDiscoveryHelper.findNodeModulesFolders(n4jsProjectPaths)) {
			toBuild.add(nmPath.toFile());
		}

		return buildSetComputer.createBuildSet(toBuild, Collections.emptyList(), Collections.emptyList(),
				namesOfWorkspaceProjects);
	}

}
