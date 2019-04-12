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

import java.util.Collection;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.generator.headless.HeadlessHelper;
import org.eclipse.n4js.generator.headless.N4JSCompileException;
import org.eclipse.n4js.internal.FileBasedWorkspace;
import org.eclipse.n4js.internal.N4JSModel;
import org.eclipse.xtext.ide.server.ProjectWorkspaceConfigFactory;
import org.eclipse.xtext.workspace.IWorkspaceConfig;
import org.eclipse.xtext.workspace.WorkspaceConfig;

import com.google.inject.Inject;

/**
 *
 */
@SuppressWarnings("restriction")
public class N4JSProjectWorkspaceConfigFactory extends ProjectWorkspaceConfigFactory {

	@Inject
	private HeadlessHelper headlessHelper;

	@Inject
	private FileBasedWorkspace workspace;

	@Inject
	private N4JSModel model;

	@Override
	public IWorkspaceConfig getWorkspaceConfig(URI workspaceBaseURI) {

		Collection<URI> allProjectDirs = ProjectFinderUtil.collectAllProjectDirs(workspaceBaseURI);
		try {
			headlessHelper.registerProjectsToFileBasedWorkspace(allProjectDirs, workspace);
		} catch (N4JSCompileException e) {
			e.printStackTrace();
		}

		return model;
	}

	@Override
	public void findProjects(WorkspaceConfig workspaceConfig, URI uri) {
		// obsolete
	}

}
