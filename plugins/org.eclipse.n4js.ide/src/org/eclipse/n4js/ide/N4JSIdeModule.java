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
import org.eclipse.n4js.ide.editor.contentassist.CamelCasePrefixMatcher;
import org.eclipse.n4js.ide.editor.contentassist.ContentAssistDataCollectors;
import org.eclipse.n4js.ide.editor.contentassist.CustomN4JSParser;
import org.eclipse.n4js.ide.editor.contentassist.N4JSContentAssistContextFactory;
import org.eclipse.n4js.ide.editor.contentassist.N4JSContentAssistService;
import org.eclipse.n4js.ide.editor.contentassist.N4JSFollowElementCalculator;
import org.eclipse.n4js.ide.editor.contentassist.N4JSIdeContentProposalProvider;
import org.eclipse.n4js.ide.server.FileBasedWorkspaceInitializer;
import org.eclipse.n4js.ide.server.N4JSLanguageServer;
import org.eclipse.n4js.ide.server.N4JSOutputConfigurationProvider;
import org.eclipse.n4js.ide.server.N4JSProjectDescriptionFactory;
import org.eclipse.n4js.ide.server.N4JSProjectStatePersister;
import org.eclipse.n4js.ide.server.N4JSWorkspaceManager;
import org.eclipse.n4js.ide.server.codeActions.N4JSCodeActionService;
import org.eclipse.n4js.ide.server.commands.N4JSCommandService;
import org.eclipse.n4js.ide.server.concurrent.N4JSQueuedExecutorService;
import org.eclipse.n4js.ide.server.hover.N4JSHoverService;
import org.eclipse.n4js.ide.server.symbol.N4JSDocumentSymbolMapper;
import org.eclipse.n4js.ide.server.symbol.N4JSHierarchicalDocumentSymbolService;
import org.eclipse.n4js.ide.validation.N4JSDiagnosticConverter;
import org.eclipse.n4js.ide.xtext.editor.contentassist.XIdeContentProposalAcceptor;
import org.eclipse.n4js.ide.xtext.server.BuiltInAwareIncrementalBuilder;
import org.eclipse.n4js.ide.xtext.server.DebugService;
import org.eclipse.n4js.ide.xtext.server.QueuedExecutorService;
import org.eclipse.n4js.ide.xtext.server.WorkspaceAwareCanLoadFromDescriptionHelper;
import org.eclipse.n4js.ide.xtext.server.XExecutableCommandRegistry;
import org.eclipse.n4js.ide.xtext.server.XIProjectDescriptionFactory;
import org.eclipse.n4js.ide.xtext.server.XIWorkspaceConfigFactory;
import org.eclipse.n4js.ide.xtext.server.XLanguageServerImpl;
import org.eclipse.n4js.ide.xtext.server.build.DefaultBuildRequestFactory;
import org.eclipse.n4js.ide.xtext.server.build.IBuildRequestFactory;
import org.eclipse.n4js.ide.xtext.server.build.ProjectBuilder;
import org.eclipse.n4js.ide.xtext.server.build.ProjectStatePersister;
import org.eclipse.n4js.ide.xtext.server.build.XBuildManager;
import org.eclipse.n4js.ide.xtext.server.build.XStatefulIncrementalBuilder;
import org.eclipse.n4js.ide.xtext.server.build.XWorkspaceManager;
import org.eclipse.n4js.ide.xtext.server.contentassist.XContentAssistService;
import org.eclipse.n4js.ide.xtext.server.util.XOperationCanceledManager;
import org.eclipse.n4js.internal.lsp.FileSystemScanner;
import org.eclipse.n4js.scoping.utils.CanLoadFromDescriptionHelper;
import org.eclipse.xtext.generator.IGenerator;
import org.eclipse.xtext.generator.OutputConfigurationProvider;
import org.eclipse.xtext.ide.editor.contentassist.FQNPrefixMatcher;
import org.eclipse.xtext.ide.editor.contentassist.IPrefixMatcher;
import org.eclipse.xtext.ide.editor.contentassist.IdeContentProposalAcceptor;
import org.eclipse.xtext.ide.editor.contentassist.IdeContentProposalProvider;
import org.eclipse.xtext.ide.editor.contentassist.antlr.ContentAssistContextFactory;
import org.eclipse.xtext.ide.editor.contentassist.antlr.IContentAssistParser;
import org.eclipse.xtext.ide.server.ILanguageServerShutdownAndExitHandler;
import org.eclipse.xtext.ide.server.codeActions.ICodeActionService2;
import org.eclipse.xtext.ide.server.commands.ExecutableCommandRegistry;
import org.eclipse.xtext.ide.server.commands.IExecutableCommandService;
import org.eclipse.xtext.ide.server.hover.HoverService;
import org.eclipse.xtext.ide.server.symbol.DocumentSymbolMapper;
import org.eclipse.xtext.ide.server.symbol.HierarchicalDocumentSymbolService;
import org.eclipse.xtext.service.OperationCanceledManager;
import org.eclipse.xtext.util.IFileSystemScanner;
import org.eclipse.xtext.validation.IDiagnosticConverter;

/**
 * Use this class to register ide components.
 */
@SuppressWarnings({ "restriction", "javadoc" })
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

	public Class<? extends IBuildRequestFactory> bindBuildRequestFactory() {
		return DefaultBuildRequestFactory.class;
	}

	public Class<? extends XIWorkspaceConfigFactory> bindXIWorkspaceConfigFactory() {
		return FileBasedWorkspaceInitializer.class;
	}

	public Class<? extends CanLoadFromDescriptionHelper> bindCanLoadFromDescriptionHelper() {
		return WorkspaceAwareCanLoadFromDescriptionHelper.class;
	}

	public Class<? extends ProjectBuilder> bindXProjectManager() {
		return ProjectBuilder.class;
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

	public Class<? extends XBuildManager> bindXBuildManager() {
		return XBuildManager.class;
	}

	public Class<? extends ExecutableCommandRegistry> bindExecutableCommandRegistry() {
		return XExecutableCommandRegistry.class;
	}

	public Class<? extends XStatefulIncrementalBuilder> bindStatefulIncrementalBuilder() {
		return BuiltInAwareIncrementalBuilder.class;
	}

	public Class<? extends IDiagnosticConverter> bindIDiagnosticConverter() {
		return N4JSDiagnosticConverter.class;
	}

	public Class<? extends IFileSystemScanner> bindFileSystemScanner() {
		return FileSystemScanner.class;
	}

	public Class<? extends IdeContentProposalAcceptor> bindIdeContentProposalAcceptor() {
		return XIdeContentProposalAcceptor.class;
	}

	/** TODO: Xtext: should not restrict access here */
	public Class<? extends ICodeActionService2> bindICodeActionService2() {
		return N4JSCodeActionService.class;
	}

	public Class<? extends IExecutableCommandService> bindIExecutableCommandService() {
		return N4JSCommandService.class;
	}

	public Class<? extends IPrefixMatcher> bindIPrefixMatcher() {
		return FQNPrefixMatcher.class;
	}

	public Class<? extends IPrefixMatcher.IgnoreCase> bindIPrefixMatcherDelegate() {
		return CamelCasePrefixMatcher.class;
	}

	public Class<? extends XLanguageServerImpl> bindXLanguageServerImpl() {
		return N4JSLanguageServer.class;
	}

	public Class<? extends QueuedExecutorService> bindLSPExecutorService() {
		return N4JSQueuedExecutorService.class;
	}

	public ContentAssistDataCollectors bindContentAssistDataCollectors() {
		return new ContentAssistDataCollectors(N4JSIdeDataCollectors.request("completion"));
	}

	public Class<? extends ContentAssistContextFactory> bindContentAssistContextFactory() {
		return N4JSContentAssistContextFactory.class;
	}

	public Class<? extends XContentAssistService> bindContentAssistService() {
		return N4JSContentAssistService.class;
	}

	public Class<? extends org.eclipse.xtext.ide.editor.contentassist.antlr.FollowElementCalculator> bindFollowElementCalculator() {
		return N4JSFollowElementCalculator.class;
	}

	@Override
	public Class<? extends IContentAssistParser> bindIContentAssistParser() {
		return CustomN4JSParser.class;
	}

	public Class<? extends DebugService> bindDebugService() {
		return DebugService.DebugServiceDefaultImpl.class;
	}

	public Class<? extends ProjectStatePersister> bindProjectStatePersister() {
		return N4JSProjectStatePersister.class;
	}

	public Class<? extends OperationCanceledManager> bindOperationCanceledManager() {
		return XOperationCanceledManager.class;
	}
}
