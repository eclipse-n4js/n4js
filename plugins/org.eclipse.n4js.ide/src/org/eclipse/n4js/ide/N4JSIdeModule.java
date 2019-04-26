/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide;

import org.eclipse.n4js.ide.editor.contentassist.N4JSIdeContentProposalProvider;
import org.eclipse.n4js.ide.server.N4JSBuildManager;
import org.eclipse.n4js.ide.server.N4JSOutputConfigurationProvider;
import org.eclipse.n4js.ide.server.N4JSProjectDescriptionFactory;
import org.eclipse.n4js.ide.server.N4JSProjectManager;
import org.eclipse.n4js.ide.server.N4JSProjectWorkspaceConfigFactory;
import org.eclipse.n4js.ide.server.N4JSWorkspaceManager;
import org.eclipse.n4js.ide.server.symbol.N4JSDocumentSymbolMapper;
import org.eclipse.n4js.ide.server.symbol.N4JSHierarchicalDocumentSymbolService;
import org.eclipse.n4js.internal.N4JSModel;
import org.eclipse.n4js.transpiler.es.EcmaScriptSubGenerator;
import org.eclipse.xtext.generator.IGenerator2;
import org.eclipse.xtext.generator.OutputConfigurationProvider;
import org.eclipse.xtext.ide.editor.contentassist.IdeContentProposalProvider;
import org.eclipse.xtext.ide.server.BuildManager;
import org.eclipse.xtext.ide.server.IProjectDescriptionFactory;
import org.eclipse.xtext.ide.server.IWorkspaceConfigFactory;
import org.eclipse.xtext.ide.server.ProjectManager;
import org.eclipse.xtext.ide.server.WorkspaceManager;
import org.eclipse.xtext.ide.server.symbol.DocumentSymbolMapper;
import org.eclipse.xtext.ide.server.symbol.HierarchicalDocumentSymbolService;
import org.eclipse.xtext.workspace.IWorkspaceConfig;

/**
 * Use this class to register ide components.
 */
@SuppressWarnings("restriction")
public class N4JSIdeModule extends AbstractN4JSIdeModule {

	public ClassLoader bindClassLoaderToInstance() {
		return getClass().getClassLoader();
	}

	public Class<? extends IWorkspaceConfig> bindIWorkspaceConfig() {
		return N4JSModel.class;
	}

	public Class<? extends WorkspaceManager> bindWorkspaceManager() {
		return N4JSWorkspaceManager.class;
	}

	public Class<? extends IWorkspaceConfigFactory> bindIWorkspaceConfigFactory() {
		return N4JSProjectWorkspaceConfigFactory.class;
	}

	public Class<? extends BuildManager> bindBuildManager() {
		return N4JSBuildManager.class;
	}

	public Class<? extends ProjectManager> bindProjectManager() {
		return N4JSProjectManager.class;
	}

	public Class<? extends IProjectDescriptionFactory> bindIProjectDescriptionFactory() {
		return N4JSProjectDescriptionFactory.class;
	}

	public Class<? extends IGenerator2> bindIGenerator2() {
		return EcmaScriptSubGenerator.class;
	}

	public Class<? extends OutputConfigurationProvider> bindOutputConfigurationProvider() {
		return N4JSOutputConfigurationProvider.class;
	}

	public Class<? extends HierarchicalDocumentSymbolService> bindHierarchicalDocumentSymbolService() {
		return N4JSHierarchicalDocumentSymbolService.class;
	}

	public Class<? extends DocumentSymbolMapper> bindDocumentSymbolMapper() {
		return N4JSDocumentSymbolMapper.class;
	}

	public Class<? extends IdeContentProposalProvider> bindIdeContentProposalProvider() {
		return N4JSIdeContentProposalProvider.class;
	}

}
