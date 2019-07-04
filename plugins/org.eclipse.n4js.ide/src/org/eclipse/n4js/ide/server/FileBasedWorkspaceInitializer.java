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
import org.eclipse.n4js.internal.FileBasedWorkspace;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.utils.NodeModulesDiscoveryHelper;
import org.eclipse.xtext.ide.server.IWorkspaceConfigFactory;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 *
 */
@Singleton
public class FileBasedWorkspaceInitializer implements IWorkspaceConfigFactory {

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

	@Override
	public IN4JSCore getWorkspaceConfig(URI workspaceBaseURI) {
		try {
			// TODO is this correct if we have multiple workspace URIs?
			workspace.clear();

			// TODO copied from N4jscBase
			File workspaceRoot = new File(workspaceBaseURI.toFileString());
			BuildSet buildSet = buildSetComputer.createAllProjectsBuildSet(
					Collections.singletonList(workspaceRoot),
					Collections.emptySet());

			headlessHelper.registerProjects(buildSet, workspace);
			final BuildSet targetPlatformBuildSet = computeTargetPlatformBuildSet(buildSet.getAllProjects());
			// make sure all newly installed dependencies are registered with the workspace
			headlessHelper.registerProjects(targetPlatformBuildSet, workspace);

			return n4jsCore;
		} catch (N4JSCompileException e) {
			// TODO exception handling
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	// TODO copied from N4jscBase.computeTargetPlatformBuildSet(Collection<? extends IN4JSProject>)
	private BuildSet computeTargetPlatformBuildSet(Collection<? extends IN4JSProject> workspaceProjects)
			throws N4JSCompileException {

		Set<String> namesOfWorkspaceProjects = new LinkedHashSet<>();
		List<java.nio.file.Path> n4jsProjectPaths = new LinkedList<>();
		for (IN4JSProject prj : workspaceProjects) {
			n4jsProjectPaths.add(prj.getLocationPath());
			namesOfWorkspaceProjects.add(prj.getProjectName());
		}

		List<File> toBuild = new ArrayList<>();
		for (java.nio.file.Path nmPath : nodeModulesDiscoveryHelper.findNodeModulesFolders(n4jsProjectPaths)) {
			toBuild.add(nmPath.toFile());
		}

		return buildSetComputer.createBuildSet(toBuild, Collections.emptyList(), Collections.emptyList(),
				namesOfWorkspaceProjects);
	}

}
