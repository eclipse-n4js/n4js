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
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.generator.headless.HeadlessHelper;
import org.eclipse.n4js.generator.headless.N4JSCompileException;
import org.eclipse.n4js.ide.xtext.server.XIWorkspaceConfigFactory;
import org.eclipse.n4js.internal.FileBasedWorkspace;
import org.eclipse.n4js.internal.lsp.N4JSWorkspaceConfig;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.locations.FileURI;
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
	private HeadlessHelper headlessHelper;

	@Inject
	private FileBasedWorkspace workspace;

	@Inject
	private ProjectDiscoveryHelper projectDiscoveryHelper;

	@Override
	public IWorkspaceConfig getWorkspaceConfig(URI workspaceBaseURI) {
		try {
			// TODO is this correct if we have multiple workspace URIs?
			workspace.clear();

			File workspaceRoot = new File(workspaceBaseURI.toFileString());
			Set<Path> allProjectLocations = projectDiscoveryHelper.collectAllProjectDirs(workspaceRoot.toPath());
			List<FileURI> allProjectURIs = new LinkedList<>();
			for (Path path : allProjectLocations) {
				allProjectURIs.add(new FileURI(path.toFile()));
			}

			headlessHelper.registerProjectsToFileBasedWorkspace(allProjectURIs, workspace);
			return new N4JSWorkspaceConfig(n4jsCore);

		} catch (N4JSCompileException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
