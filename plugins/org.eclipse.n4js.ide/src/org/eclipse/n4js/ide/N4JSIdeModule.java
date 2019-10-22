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

import org.eclipse.n4js.generator.N4JSCompositeGenerator;
import org.eclipse.n4js.ide.editor.contentassist.N4JSIdeContentProposalProvider;
import org.eclipse.n4js.ide.server.FileBasedWorkspaceInitializer;
import org.eclipse.n4js.ide.server.N4JSBuildManager;
import org.eclipse.n4js.ide.server.N4JSInternalStatefulIncrementalBuilder;
import org.eclipse.n4js.ide.server.N4JSOutputConfigurationProvider;
import org.eclipse.n4js.ide.server.N4JSProjectDescriptionFactory;
import org.eclipse.n4js.ide.server.N4JSProjectManager;
import org.eclipse.n4js.ide.server.N4JSWorkspaceManager;
import org.eclipse.n4js.ide.server.hover.N4JSHoverService;
import org.eclipse.n4js.ide.server.symbol.N4JSDocumentSymbolMapper;
import org.eclipse.n4js.ide.server.symbol.N4JSHierarchicalDocumentSymbolService;
import org.eclipse.n4js.ide.xtext.server.XBuildManager;
import org.eclipse.n4js.ide.xtext.server.XIProjectDescriptionFactory;
import org.eclipse.n4js.ide.xtext.server.XIWorkspaceConfigFactory;
import org.eclipse.n4js.ide.xtext.server.XProjectManager;
import org.eclipse.n4js.ide.xtext.server.XWorkspaceManager;
import org.eclipse.xtext.build.IncrementalBuilder.InternalStatefulIncrementalBuilder;
import org.eclipse.xtext.generator.IGenerator;
import org.eclipse.xtext.generator.IShouldGenerate;
import org.eclipse.xtext.generator.OutputConfigurationProvider;
import org.eclipse.xtext.ide.editor.contentassist.IdeContentProposalProvider;
import org.eclipse.xtext.ide.server.ILanguageServerShutdownAndExitHandler;
import org.eclipse.xtext.ide.server.hover.HoverService;
import org.eclipse.xtext.ide.server.symbol.DocumentSymbolMapper;
import org.eclipse.xtext.ide.server.symbol.HierarchicalDocumentSymbolService;

/**
 * Use this class to register ide components.
 */
@SuppressWarnings({ "javadoc", "restriction" })
public class N4JSIdeModule extends AbstractN4JSIdeModule {

	public ClassLoader bindClassLoaderToInstance() {
		return getClass().getClassLoader();
	}

	public Class<? extends ILanguageServerShutdownAndExitHandler> bindILanguageServerShutdownAndExitHandler() {
		return ILanguageServerShutdownAndExitHandler.NullImpl.class;
	}

	public Class<? extends XWorkspaceManager> bindXWorkspaceManager() {
		return N4JSWorkspaceManager.class;
	}

	public Class<? extends XIWorkspaceConfigFactory> bindXIWorkspaceConfigFactory() {
		return FileBasedWorkspaceInitializer.class;
	}

	public Class<? extends XProjectManager> bindXProjectManager() {
		return N4JSProjectManager.class;
	}

	public Class<? extends XIProjectDescriptionFactory> bindXIProjectDescriptionFactory() {
		return N4JSProjectDescriptionFactory.class;
	}

	public Class<? extends IGenerator> bindIGenerator() {
		return N4JSCompositeGenerator.class;
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

	public Class<? extends HoverService> bindHoverService() {
		return N4JSHoverService.class;
	}

	public Class<? extends InternalStatefulIncrementalBuilder> bindInternalStatefulIncrementalBuilder() {
		return N4JSInternalStatefulIncrementalBuilder.class;
	}

	/** TODO: Fixes Xtext issue of double validation */
	public Class<? extends IShouldGenerate> bindIShouldGenerate() {
		return IShouldGenerate.Always.class;
	}

	public Class<? extends XBuildManager> bindXBuildManager() {
		return N4JSBuildManager.class;
	}

}
