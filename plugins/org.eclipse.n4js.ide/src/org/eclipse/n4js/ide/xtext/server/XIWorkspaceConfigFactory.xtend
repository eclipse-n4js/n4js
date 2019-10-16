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
package org.eclipse.n4js.ide.xtext.server

import org.eclipse.emf.common.util.URI
import org.eclipse.xtext.workspace.FileProjectConfig
import org.eclipse.xtext.workspace.IWorkspaceConfig
import org.eclipse.xtext.workspace.WorkspaceConfig

/**
 * @author Jan Koehnlein - Initial contribution and API
 * @since 2.11
 */
interface XIWorkspaceConfigFactory {

	def IWorkspaceConfig getWorkspaceConfig(URI workspaceBaseURI)

}

/**
 * The workspace itself is a single project
 * 
 * @author Jan Koehnlein - Initial contribution and API
 * @since 2.11
 */
class XProjectWorkspaceConfigFactory implements XIWorkspaceConfigFactory {

	override getWorkspaceConfig(URI workspaceBaseURI) {
		val workspaceConfig = new WorkspaceConfig
		workspaceConfig.findProjects(workspaceBaseURI)
		return workspaceConfig
	}

	def void findProjects(WorkspaceConfig workspaceConfig, URI uri) {
		if (uri !== null) {
			val project = new FileProjectConfig(uri, workspaceConfig)
			project.addSourceFolder('.')
			workspaceConfig.addProject(project)
		}
	}

}