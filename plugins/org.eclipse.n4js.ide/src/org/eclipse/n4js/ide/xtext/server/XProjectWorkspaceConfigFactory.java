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
package org.eclipse.n4js.ide.xtext.server;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.workspace.FileProjectConfig;
import org.eclipse.xtext.workspace.IWorkspaceConfig;
import org.eclipse.xtext.workspace.WorkspaceConfig;

/**
 * The workspace itself is a single project
 *
 * @author Jan Koehnlein - Initial contribution and API
 * @since 2.11
 */
@SuppressWarnings("restriction")
public class XProjectWorkspaceConfigFactory implements XIWorkspaceConfigFactory {
	@Override
	public IWorkspaceConfig getWorkspaceConfig(URI workspaceBaseURI) {
		WorkspaceConfig workspaceConfig = new WorkspaceConfig();
		this.findProjects(workspaceConfig, workspaceBaseURI);
		return workspaceConfig;
	}

	/**
	 * Find all projects at the given location. By default, only the a single project at the workspace root is created.
	 */
	protected void findProjects(WorkspaceConfig workspaceConfig, URI location) {
		if (location != null) {
			FileProjectConfig project = new FileProjectConfig(location, workspaceConfig);
			project.addSourceFolder(".");
			workspaceConfig.addProject(project);
		}
	}
}
