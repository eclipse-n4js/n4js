/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ui;

import org.eclipse.jface.text.hyperlink.IHyperlinkDetector;
import org.eclipse.jface.text.rules.IPartitionTokenScanner;
import org.eclipse.jface.text.rules.ITokenScanner;
import org.eclipse.n4js.CancelIndicatorBaseExtractor;
import org.eclipse.n4js.binaries.BinariesCommandFactory;
import org.eclipse.n4js.binaries.BinariesPreferenceStore;
import org.eclipse.n4js.binaries.BinariesValidator;
import org.eclipse.n4js.external.ExternalIndexSynchronizer;
import org.eclipse.n4js.external.ExternalLibraryUriHelper;
import org.eclipse.n4js.external.ExternalLibraryWorkspace;
import org.eclipse.n4js.external.ExternalProjectsCollector;
import org.eclipse.n4js.external.NpmLogger;
import org.eclipse.n4js.findReferences.ConcreteSyntaxAwareReferenceFinder;
import org.eclipse.n4js.generator.ICompositeGenerator;
import org.eclipse.n4js.generator.IGeneratorMarkerSupport;
import org.eclipse.n4js.generator.N4JSCompositeGenerator;
import org.eclipse.n4js.internal.FileBasedExternalPackageManager;
import org.eclipse.n4js.internal.InternalN4JSWorkspace;
import org.eclipse.n4js.internal.MultiCleartriggerCache;
import org.eclipse.n4js.internal.N4JSModel;
import org.eclipse.n4js.packagejson.PackageJsonHelper;
import org.eclipse.n4js.preferences.ExternalLibraryPreferenceStore;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.locations.SafeURI;
import org.eclipse.n4js.resource.N4JSResourceDescriptionManager;
import org.eclipse.n4js.scoping.utils.CanLoadFromDescriptionHelper;
import org.eclipse.n4js.semver.SemverHelper;
import org.eclipse.n4js.ts.findReferences.TargetURIKey;
import org.eclipse.n4js.ts.ui.navigation.URIBasedStorageEditorInputFactory;
import org.eclipse.n4js.ts.ui.search.BuiltinSchemeAwareTargetURIKey;
import org.eclipse.n4js.ts.validation.TypesKeywordProvider;
import org.eclipse.n4js.ui.building.FileSystemAccessWithoutTraceFileSupport;
import org.eclipse.n4js.ui.building.N4JSBuilderParticipant;
import org.eclipse.n4js.ui.containers.N4JSAllContainersStateProvider;
import org.eclipse.n4js.ui.containers.N4JSProjectsStateHelper;
import org.eclipse.n4js.ui.contentassist.ContentAssistContextFactory;
import org.eclipse.n4js.ui.contentassist.ContentAssistantFactory;
import org.eclipse.n4js.ui.contentassist.CustomN4JSParser;
import org.eclipse.n4js.ui.contentassist.N4JSContentProposalPriorities;
import org.eclipse.n4js.ui.contentassist.N4JSFollowElementCalculator;
import org.eclipse.n4js.ui.contentassist.SimpleLastSegmentFinder;
import org.eclipse.n4js.ui.editor.AlwaysAddNatureCallback;
import org.eclipse.n4js.ui.editor.EditorAwareCanLoadFromDescriptionHelper;
import org.eclipse.n4js.ui.editor.N4JSDirtyStateEditorSupport;
import org.eclipse.n4js.ui.editor.N4JSDocument;
import org.eclipse.n4js.ui.editor.N4JSDoubleClickStrategyProvider;
import org.eclipse.n4js.ui.editor.N4JSHover;
import org.eclipse.n4js.ui.editor.N4JSHyperlinkDetector;
import org.eclipse.n4js.ui.editor.N4JSHyperlinkHelper;
import org.eclipse.n4js.ui.editor.N4JSLocationInFileProvider;
import org.eclipse.n4js.ui.editor.N4JSReconciler;
import org.eclipse.n4js.ui.editor.PrevStateAwareDocumentBasedDirtyResource;
import org.eclipse.n4js.ui.editor.autoedit.AutoEditStrategyProvider;
import org.eclipse.n4js.ui.editor.syntaxcoloring.HighlightingConfiguration;
import org.eclipse.n4js.ui.editor.syntaxcoloring.InvalidatingHighlightingHelper;
import org.eclipse.n4js.ui.editor.syntaxcoloring.ParserBasedDocumentTokenSource;
import org.eclipse.n4js.ui.editor.syntaxcoloring.TemplateAwarePartitionTokenScanner;
import org.eclipse.n4js.ui.editor.syntaxcoloring.TemplateAwareTokenScanner;
import org.eclipse.n4js.ui.editor.syntaxcoloring.TokenToAttributeIdMapper;
import org.eclipse.n4js.ui.editor.syntaxcoloring.TokenTypeToPartitionMapper;
import org.eclipse.n4js.ui.external.BuildOrderComputer;
import org.eclipse.n4js.ui.external.EclipseExternalIndexSynchronizer;
import org.eclipse.n4js.ui.external.EclipseExternalLibraryWorkspace;
import org.eclipse.n4js.ui.external.ExternalLibraryBuildQueue;
import org.eclipse.n4js.ui.external.ExternalLibraryBuildScheduler;
import org.eclipse.n4js.ui.external.ExternalLibraryBuilder;
import org.eclipse.n4js.ui.external.ExternalProjectProvider;
import org.eclipse.n4js.ui.formatting2.FixedContentFormatter;
import org.eclipse.n4js.ui.generator.GeneratorMarkerSupport;
import org.eclipse.n4js.ui.internal.ConsoleOutputStreamProvider;
import org.eclipse.n4js.ui.internal.ContributingModule;
import org.eclipse.n4js.ui.internal.ContributingResourceDescriptionPersister;
import org.eclipse.n4js.ui.internal.EclipseBasedN4JSWorkspace;
import org.eclipse.n4js.ui.internal.ExternalProjectLoader;
import org.eclipse.n4js.ui.internal.N4JSEclipseCore;
import org.eclipse.n4js.ui.internal.N4JSEclipseModel;
import org.eclipse.n4js.ui.internal.ResourceUIValidatorExtension;
import org.eclipse.n4js.ui.labeling.N4JSContentAssistLabelProvider;
import org.eclipse.n4js.ui.labeling.N4JSHoverProvider;
import org.eclipse.n4js.ui.labeling.N4JSHyperlinkLabelProvider;
import org.eclipse.n4js.ui.logging.N4jsUiLoggingInitializer;
import org.eclipse.n4js.ui.navigator.internal.N4JSProjectExplorerHelper;
import org.eclipse.n4js.ui.outline.MetaTypeAwareComparator;
import org.eclipse.n4js.ui.outline.N4JSFilterLocalTypesOutlineContribution;
import org.eclipse.n4js.ui.outline.N4JSFilterNonPublicMembersOutlineContribution;
import org.eclipse.n4js.ui.outline.N4JSFilterStaticMembersOutlineContribution;
import org.eclipse.n4js.ui.outline.N4JSOutlineModes;
import org.eclipse.n4js.ui.outline.N4JSOutlineNodeFactory;
import org.eclipse.n4js.ui.outline.N4JSShowInheritedMembersOutlineContribution;
import org.eclipse.n4js.ui.preferences.N4JSBuilderPreferenceAccess;
import org.eclipse.n4js.ui.projectModel.IN4JSEclipseCore;
import org.eclipse.n4js.ui.quickfix.N4JSIssue;
import org.eclipse.n4js.ui.quickfix.N4JSMarkerResolutionGenerator;
import org.eclipse.n4js.ui.refactoring.N4JSDependentElementsCalculator;
import org.eclipse.n4js.ui.refactoring.N4JSLinkedPositionGroupCalculator;
import org.eclipse.n4js.ui.refactoring.N4JSRefactoringCrossReferenceSerializer;
import org.eclipse.n4js.ui.refactoring.N4JSRefactoringResourceSetProvider;
import org.eclipse.n4js.ui.refactoring.N4JSRenameElementHandler;
import org.eclipse.n4js.ui.refactoring.N4JSRenameElementProcessor;
import org.eclipse.n4js.ui.refactoring.N4JSRenameStrategy;
import org.eclipse.n4js.ui.resource.N4JSEclipseResourceDescriptionManager;
import org.eclipse.n4js.ui.resource.OutputFolderAwareResourceServiceProvider;
import org.eclipse.n4js.ui.search.LabellingReferenceFinder;
import org.eclipse.n4js.ui.search.MyReferenceSearchResultContentProvider;
import org.eclipse.n4js.ui.search.N4JSEditorResourceAccess;
import org.eclipse.n4js.ui.search.N4JSReferenceQueryExecutor;
import org.eclipse.n4js.ui.utils.CancelIndicatorUiExtractor;
import org.eclipse.n4js.ui.utils.PrefixMatcherHelper.N4JSPrefixMatcher;
import org.eclipse.n4js.ui.validation.SourceContainerAwareResourceValidator;
import org.eclipse.n4js.ui.wizard.project.N4JSProjectCreator;
import org.eclipse.n4js.ui.workingsets.WorkingSetManagerBroker;
import org.eclipse.n4js.ui.workingsets.WorkingSetManagerBrokerImpl;
import org.eclipse.n4js.ui.workingsets.WorkspaceRepositoriesProvider;
import org.eclipse.n4js.utils.ProjectDescriptionLoader;
import org.eclipse.n4js.utils.StatusHelper;
import org.eclipse.n4js.utils.process.OutputStreamPrinterThreadProvider;
import org.eclipse.n4js.utils.process.OutputStreamProvider;
import org.eclipse.n4js.utils.process.ProcessExecutor;
import org.eclipse.n4js.utils.ui.editor.AvoidRefreshDocumentProvider;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.xtext.builder.EclipseResourceFileSystemAccess2;
import org.eclipse.xtext.builder.IXtextBuilderParticipant;
import org.eclipse.xtext.builder.preferences.BuilderPreferenceAccess;
import org.eclipse.xtext.findReferences.IReferenceFinder;
import org.eclipse.xtext.generator.IOutputConfigurationProvider;
import org.eclipse.xtext.ide.editor.contentassist.antlr.IContentAssistParser;
import org.eclipse.xtext.resource.ILocationInFileProvider;
import org.eclipse.xtext.resource.SynchronizedXtextResourceSet;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.resource.containers.IAllContainersState;
import org.eclipse.xtext.ui.editor.DirtyStateEditorSupport;
import org.eclipse.xtext.ui.editor.DocumentBasedDirtyResource;
import org.eclipse.xtext.ui.editor.IXtextEditorCallback;
import org.eclipse.xtext.ui.editor.LanguageSpecificURIEditorOpener;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.ui.editor.autoedit.AbstractEditStrategyProvider;
import org.eclipse.xtext.ui.editor.contentassist.ContentProposalPriorities;
import org.eclipse.xtext.ui.editor.contentassist.FQNPrefixMatcher;
import org.eclipse.xtext.ui.editor.contentassist.FQNPrefixMatcher.LastSegmentFinder;
import org.eclipse.xtext.ui.editor.contentassist.IContentAssistantFactory;
import org.eclipse.xtext.ui.editor.contentassist.PrefixMatcher;
import org.eclipse.xtext.ui.editor.doubleClicking.DoubleClickStrategyProvider;
import org.eclipse.xtext.ui.editor.findrefs.EditorResourceAccess;
import org.eclipse.xtext.ui.editor.findrefs.ReferenceSearchResultContentProvider;
import org.eclipse.xtext.ui.editor.formatting2.ContentFormatter;
import org.eclipse.xtext.ui.editor.hover.IEObjectHover;
import org.eclipse.xtext.ui.editor.hover.IEObjectHoverProvider;
import org.eclipse.xtext.ui.editor.hyperlinking.HyperlinkHelper;
import org.eclipse.xtext.ui.editor.model.DocumentTokenSource;
import org.eclipse.xtext.ui.editor.model.IResourceForEditorInputFactory;
import org.eclipse.xtext.ui.editor.model.TerminalsTokenTypeToPartitionMapper;
import org.eclipse.xtext.ui.editor.model.XtextDocument;
import org.eclipse.xtext.ui.editor.model.XtextDocumentProvider;
import org.eclipse.xtext.ui.editor.outline.IOutlineTreeProvider;
import org.eclipse.xtext.ui.editor.outline.actions.IOutlineContribution;
import org.eclipse.xtext.ui.editor.outline.impl.OutlineFilterAndSorter.IComparator;
import org.eclipse.xtext.ui.editor.outline.impl.OutlineNodeFactory;
import org.eclipse.xtext.ui.editor.quickfix.MarkerResolutionGenerator;
import org.eclipse.xtext.ui.editor.reconciler.XtextReconciler;
import org.eclipse.xtext.ui.editor.syntaxcoloring.AbstractAntlrTokenToAttributeIdMapper;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightingConfiguration;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightingHelper;
import org.eclipse.xtext.ui.editor.validation.MarkerCreator;
import org.eclipse.xtext.ui.refactoring.IDependentElementsCalculator;
import org.eclipse.xtext.ui.refactoring.impl.AbstractRenameProcessor;
import org.eclipse.xtext.ui.refactoring.impl.DefaultRenameStrategy;
import org.eclipse.xtext.ui.refactoring.impl.RefactoringCrossReferenceSerializer;
import org.eclipse.xtext.ui.refactoring.impl.RefactoringResourceSetProvider;
import org.eclipse.xtext.ui.refactoring.ui.DefaultLinkedPositionGroupCalculator;
import org.eclipse.xtext.ui.refactoring.ui.DefaultRenameElementHandler;
import org.eclipse.xtext.ui.resource.DefaultResourceUIServiceProvider;
import org.eclipse.xtext.ui.shared.Access;
import org.eclipse.xtext.ui.util.IssueUtil;
import org.eclipse.xtext.ui.validation.IResourceUIValidatorExtension;
import org.eclipse.xtext.ui.wizard.IProjectCreator;
import org.eclipse.xtext.validation.IResourceValidator;

import com.google.inject.Binder;
import com.google.inject.Provider;
import com.google.inject.name.Names;

/**
 * Use this class to register components to be used within the IDE.
 */
@SuppressWarnings("restriction")
public class N4JSUiModule extends org.eclipse.n4js.ui.AbstractN4JSUiModule {

	@Override
	public void configure(Binder binder) {
		super.configure(binder);
	}

	/**
	 * Create a new UIModule in the given plugin.
	 */
	public N4JSUiModule(AbstractUIPlugin plugin) {
		super(plugin);
		// get the logging initialized for UI presentation:
		N4jsUiLoggingInitializer.init();
	}

	/** Delegate to shared injector and obtain an instance directly from the shared injector */
	public Provider<ContributingResourceDescriptionPersister> providerContributingResourceDescriptionPersister() {
		return Access.provider(ContributingResourceDescriptionPersister.class);
	}

	/**
	 * Delegate to shared injector and obtain a contributed instance that is not a direct object in the shared injector
	 */
	public Provider<InternalN4JSWorkspace<? extends SafeURI<?>>> provideInternalN4JSWorkspace() {
		return Access.contributedProvider(InternalN4JSWorkspace.class);
	}

	/** Delegate to shared injector */
	public Provider<ExternalLibraryWorkspace> provideExternalLibraryWorkspace() {
		return Access.contributedProvider(ExternalLibraryWorkspace.class);
	}

	/** Delegate to shared injector */
	public Provider<SemverHelper> provideSEMVERHelper() {
		return Access.contributedProvider(SemverHelper.class);
	}

	/** Delegate to shared injector */
	public Provider<EclipseExternalLibraryWorkspace> provideEclipseExternalLibraryWorkspace() {
		return Access.contributedProvider(EclipseExternalLibraryWorkspace.class);
	}

	/** Delegate to shared injector */
	public Provider<ExternalIndexSynchronizer> provideExternalIndexSynchronizer() {
		return Access.contributedProvider(ExternalIndexSynchronizer.class);
	}

	/** Delegate to shared injector */
	public Provider<N4JSProjectsStateHelper> provideN4JSProjectsStateHelper() {
		return Access.contributedProvider(N4JSProjectsStateHelper.class);
	}

	/** Delegate to shared injector */
	public Provider<MultiCleartriggerCache> provideMultiCleartriggerCache() {
		return Access.contributedProvider(MultiCleartriggerCache.class);
	}

	/** Delegate to shared injector */
	public Provider<EclipseExternalIndexSynchronizer> provideEclipseExternalIndexSynchronizer() {
		return Access.contributedProvider(EclipseExternalIndexSynchronizer.class);
	}

	/** Delegate to shared injector */
	public Provider<ExternalProjectLoader> provideExternalProjectCacheLoader() {
		return Access.contributedProvider(ExternalProjectLoader.class);
	}

	/** Delegate to shared injector */
	public Provider<ExternalLibraryBuildScheduler> provideExternalLibraryBuildJobProvider() {
		return Access.contributedProvider(ExternalLibraryBuildScheduler.class);
	}

	/** Delegate to shared injector */
	public Provider<ExternalLibraryBuildQueue> provideExternalLibraryBuildQueue() {
		return Access.contributedProvider(ExternalLibraryBuildQueue.class);
	}

	/** Delegate to shared injector */
	public Provider<ExternalLibraryBuilder> provideExternalLibraryBuilder() {
		return Access.contributedProvider(ExternalLibraryBuilder.class);
	}

	/** Delegate to shared injector */
	public Provider<BuildOrderComputer> provideBuildOrderComputer() {
		return Access.contributedProvider(BuildOrderComputer.class);
	}

	/** Delegate to shared injector */
	public Provider<NpmLogger> provideNpmLogger() {
		return Access.contributedProvider(NpmLogger.class);
	}

	/** Delegate to shared injector */
	public Provider<OutputStreamProvider> provideOutputStreamProvider() {
		return Access.contributedProvider(OutputStreamProvider.class);
	}

	/** Delegate to shared injector */
	public Provider<ConsoleOutputStreamProvider> provideConsoleOutputStreamProvider() {
		return Access.contributedProvider(ConsoleOutputStreamProvider.class);
	}

	/** Delegate to shared injector */
	public Provider<ExternalProjectsCollector> provideExternalProjectsCollector() {
		return Access.contributedProvider(ExternalProjectsCollector.class);
	}

	/** Delegate to shared injector */
	public Provider<ExternalProjectProvider> provideExternalProjectProvider() {
		return Access.contributedProvider(ExternalProjectProvider.class);
	}

	/** Delegate to shared injector */
	public Provider<ExternalLibraryPreferenceStore> provideExternalLibraryPreferenceStore() {
		return Access.contributedProvider(ExternalLibraryPreferenceStore.class);
	}

	/** Delegate to shared injector */
	public Provider<WorkingSetManagerBroker> provideWorkingSetManagerBroker() {
		return Access.contributedProvider(WorkingSetManagerBroker.class);
	}

	/** Delegate to shared injector */
	public Provider<WorkingSetManagerBrokerImpl> provideWorkingSetManagerBrokerImpl() {
		return Access.contributedProvider(WorkingSetManagerBrokerImpl.class);
	}

	/** Delegate to shared injector */
	public Provider<BinariesPreferenceStore> provideBinariesPreferenceStore() {
		return Access.contributedProvider(BinariesPreferenceStore.class);
	}

	/** Delegate to shared injector */
	public Provider<IN4JSCore> provideIN4JSCore() {
		return Access.contributedProvider(IN4JSCore.class);
	}

	/** Delegate to shared injector */
	public Provider<IN4JSEclipseCore> provideIN4JSEclipseCore() {
		return Access.contributedProvider(IN4JSEclipseCore.class);
	}

	/** Delegate to shared injector */
	public Provider<N4JSEclipseCore> provideN4JSEclipseCore() {
		return Access.contributedProvider(N4JSEclipseCore.class);
	}

	/** Delegate to shared injector */
	public Provider<EclipseBasedN4JSWorkspace> provideEclipseBasedN4JSWorkspace() {
		return Access.contributedProvider(EclipseBasedN4JSWorkspace.class);
	}

	/** Delegate to shared injector */
	public Provider<ProjectDescriptionLoader> provideProjectDescriptionLoader() {
		return Access.contributedProvider(ProjectDescriptionLoader.class);
	}

	/** Delegate to shared injector */
	public Provider<PackageJsonHelper> providePackageJsonHelper() {
		return Access.contributedProvider(PackageJsonHelper.class);
	}

	/** Delegate to shared injector */
	public Provider<? extends WorkspaceRepositoriesProvider> provideWorkspaceRepositoryProvider() {
		return Access.contributedProvider(WorkspaceRepositoriesProvider.class);
	}

	/** Delegate to shared injector */
	public Provider<? extends BinariesCommandFactory> provideBinaryCommandFactory() {
		return Access.contributedProvider(BinariesCommandFactory.class);
	}

	/** Delegate to shared injector */
	public Provider<? extends ExternalLibraryUriHelper> provideExternalLibraryUriHelper() {
		return Access.contributedProvider(ExternalLibraryUriHelper.class);
	}

	/** Delegate to shared injector */
	public Provider<? extends FileBasedExternalPackageManager> provideFileBasedExternalPackageManager() {
		return Access.contributedProvider(FileBasedExternalPackageManager.class);
	}

	/** Delegate to shared injector */
	public Provider<N4JSModel<?>> provideN4JSModel() {
		return Access.contributedProvider(N4JSModel.class);
	}

	/** Delegate to shared injector */
	public Provider<N4JSEclipseModel> provideN4JSEclipseModel() {
		return Access.contributedProvider(N4JSEclipseModel.class);
	}

	/** Delegate to shared injector */
	public Provider<? extends N4JSProjectExplorerHelper> provideN4JSProjectExplorerHelper() {
		return Access.contributedProvider(N4JSProjectExplorerHelper.class);
	}

	/** Delegate to shared injector */
	public Provider<? extends StatusHelper> provideStatusHelper() {
		return Access.contributedProvider(StatusHelper.class);
	}

	/** Delegate to shared injector */
	public Provider<? extends OutputStreamPrinterThreadProvider> provideOutputStreamPrinterThreadProvider() {
		return Access.contributedProvider(OutputStreamPrinterThreadProvider.class);
	}

	/** Delegate to shared injector */
	public Provider<? extends TypesKeywordProvider> provideTypesKeywordProvider() {
		return Access.contributedProvider(TypesKeywordProvider.class);
	}

	/** Delegate to shared injector */
	public Provider<? extends BinariesValidator> provideBinariesValidator() {
		return Access.contributedProvider(BinariesValidator.class);
	}

	/** Delegate to shared injector */
	public Provider<? extends ProcessExecutor> provideProcessExecutor() {
		return Access.contributedProvider(ProcessExecutor.class);
	}

	/** Bind {@link URIBasedStorageEditorInputFactory} to support hyperlinks to external library modules */
	@Override
	public Class<? extends IResourceForEditorInputFactory> bindIResourceForEditorInputFactory() {
		return URIBasedStorageEditorInputFactory.class;
	}

	/**
	 * Bind the {@link IXtextBuilderParticipant} being aware of generating the Javascript files in the output directory.
	 */
	@Override
	public Class<? extends IXtextBuilderParticipant> bindIXtextBuilderParticipant() {
		return N4JSBuilderParticipant.class;
	}

	/**
	 * Bind the {@link IResourceUIValidatorExtension}.
	 */
	public Class<? extends IResourceUIValidatorExtension> bindIResourceUIValidatorExtension() {
		return ResourceUIValidatorExtension.class;
	}

	/**
	 * Bind the {@link MarkerCreator}. Do not delegate to {@link ContributingModule}, see GH-866.
	 */
	public Class<? extends MarkerCreator> bindMarkerCreator() {
		return MarkerCreator.class;
	}

	@Override
	public Provider<IAllContainersState> provideIAllContainersState() {
		return new N4JSAllContainersStateProvider();
	}

	/**
	 * A custom {@link DirtyStateEditorSupport} that uses a custom JobFamily.
	 */
	public Class<? extends DirtyStateEditorSupport> bindDirtyStateEditorSupport() {
		return N4JSDirtyStateEditorSupport.class;
	}

	/**
	 * Bind the {@link ILocationInFileProvider} that is aware of derived elements.
	 */
	public Class<? extends ILocationInFileProvider> bindILocationInFileProvider() {
		return N4JSLocationInFileProvider.class;
	}

	/**
	 * Bind the {@link org.eclipse.xtext.ui.editor.findrefs.ReferenceQueryExecutor} that maps to types.
	 */
	public Class<? extends org.eclipse.xtext.ui.editor.findrefs.ReferenceQueryExecutor> bindReferenceQueryExecutor() {
		return N4JSReferenceQueryExecutor.class;
	}

	/**
	 * Bind the {@link org.eclipse.xtext.ui.editor.findrefs.IReferenceFinder} that find references solely to types (and
	 * TVariables, IdentifiableElement and TEnumLiterals).
	 */
	public Class<? extends org.eclipse.xtext.ui.editor.findrefs.IReferenceFinder> bindIReferenceFinder() {
		return LabellingReferenceFinder.class;
	}

	/**
	 * Bind the {@link IResourceValidator} that knows how to ignore files that are not in source folders according to
	 * the manifest.
	 */
	public Class<? extends IResourceValidator> bindResourceValidator() {
		return SourceContainerAwareResourceValidator.class;
	}

	/**
	 * De-configure the hard coded built in scheme from the runtime bundle.
	 */
	public Class<? extends XtextResourceSet> bindXtextResourceSet() {
		return SynchronizedXtextResourceSet.class;
	}

	/**
	 * custom builder preferences initializer that iterates over all default configurations provided by the sub
	 * generators contained in the found composite generators.
	 */
	@Override
	public void configureBuilderPreferenceStoreInitializer(com.google.inject.Binder binder) {
		binder.bind(org.eclipse.xtext.ui.editor.preferences.IPreferenceStoreInitializer.class)
				.annotatedWith(com.google.inject.name.Names.named("builderPreferenceInitializer"))
				.to(org.eclipse.n4js.ui.preferences.N4JSBuilderPreferenceAccess.Initializer.class);
	}

	/**
	 * @return custom builder preference access sets the activation of the builder participant to be always true so that
	 *         dirty state handling is always enabled.
	 */
	public Class<? extends BuilderPreferenceAccess> bindBuilderPreferenceAccess() {
		return N4JSBuilderPreferenceAccess.class;
	}

	/**
	 * @return iterates over all registered composite generators to pick up the default output configurations of their
	 *         contained sub generators
	 */
	public Class<? extends IOutputConfigurationProvider> bindIOutputConfigurationProvider() {
		return org.eclipse.n4js.ui.building.N4JSOutputConfigurationProvider.class;
	}

	/**
	 * Filter fully qualified proposals also by their last segment and not only by their fully qualified name.
	 */
	public Class<? extends PrefixMatcher> bindPrefixMatcher() {
		return FQNPrefixMatcher.class;
	}

	/**
	 * No special treatment for uppercase module name segments.
	 */
	public Class<? extends LastSegmentFinder> bindLastSegmentFinder() {
		return SimpleLastSegmentFinder.class;
	}

	/**
	 * Bind the customized content assist parser infrastructure.
	 */
	public Class<? extends org.eclipse.xtext.ide.editor.contentassist.antlr.ContentAssistContextFactory> bindContentAssistContextFactory() {
		return ContentAssistContextFactory.class;
	}

	/**
	 * Bind the customized content assist follow element calculator that drops parser rules of "bogus" language parts.
	 */
	public Class<? extends org.eclipse.xtext.ide.editor.contentassist.antlr.FollowElementCalculator> bindFollowElementCalculator() {
		return N4JSFollowElementCalculator.class;
	}

	@Override
	public Class<? extends IContentAssistParser> bindIContentAssistParser() {
		return CustomN4JSParser.class;
	}

	/**
	 * Adjustments to default content proposal priorities.
	 */
	public Class<? extends ContentProposalPriorities> bindContentProposalPriorities() {
		return N4JSContentProposalPriorities.class;
	}

	/**
	 * Custom prefix matcher.
	 */
	public Class<? extends PrefixMatcher.IgnoreCase> bindPrefixMatcherIgnoreCase() {
		return N4JSPrefixMatcher.class;
	}

	/**
	 * Binds a specific label provider for the content assist use case.
	 */
	@Override
	public void configureContentProposalLabelProvider(com.google.inject.Binder binder) {
		binder.bind(org.eclipse.jface.viewers.ILabelProvider.class)
				.annotatedWith(org.eclipse.xtext.ui.editor.contentassist.ContentProposalLabelProvider.class)
				.to(N4JSContentAssistLabelProvider.class);
	}

	/**
	 * Binds a specific label provider for the hyper linking use case.
	 */
	@Override
	public void configureHyperlinkLabelProvider(com.google.inject.Binder binder) {
		binder.bind(org.eclipse.jface.viewers.ILabelProvider.class)
				.annotatedWith(org.eclipse.xtext.ui.editor.hyperlinking.HyperlinkLabelProvider.class)
				.to(N4JSHyperlinkLabelProvider.class);
	}

	/**
	 * Bind custom MarkerResolutionGenerator.
	 */

	// ==== BEGIN injection work-around ====
	// work-around for an injection problem with MarkerResolutionGenerator:
	// MarkerResolutionGenerator contains a non-optional injection of IWorkbench which causes problems in
	// headless tests; we cannot change that in N4JSMarkerResolutionGenerator, because the field is private
	//
	// TODO remove work-around when this injection is changed to optional in a future Xtext version
	// (also remove related hacks in classes ChangeManager and IssueUtilN4 (search for "==== BEGIN"))
	//
	// public Class<? extends MarkerResolutionGenerator> bindMarkerResolutionGenerator() {
	// return N4JSMarkerResolutionGenerator.class;
	// }
	public void configureMarkerResolutionGenerator(com.google.inject.Binder binder) {
		if (org.eclipse.ui.PlatformUI.isWorkbenchRunning()) {
			binder.bind(MarkerResolutionGenerator.class).to(N4JSMarkerResolutionGenerator.class);
		}
	}

	// ==== END injection work-around ====

	/**
	 * Bind custom XtextEditor.
	 */
	public Class<? extends XtextEditor> bindXtextEditor() {
		return N4JSEditor.class;
	}

	/**
	 * Bind custom IssueUtil.
	 */
	public Class<? extends IssueUtil> bindIssueUtil() {
		return N4JSIssue.Util.class;
	}

	/**
	 * Bind custom IEObjectHoverProvider.
	 */
	public Class<? extends IEObjectHoverProvider> bindIEObjectHoverProvider() {
		return N4JSHoverProvider.class;
	}

	/**
	 * Bind a callback that always add the nature silently.
	 */
	@Override
	public Class<? extends IXtextEditorCallback> bindIXtextEditorCallback() {
		return AlwaysAddNatureCallback.class;
	}

	/**
	 * Bind an implementation can handle find references to builtin types.
	 */
	public Class<? extends TargetURIKey> bindTargetURIKey() {
		return BuiltinSchemeAwareTargetURIKey.class;
	}

	/**
	 * Bind a proper token mapper for the special token types in N4JS
	 */
	public Class<? extends AbstractAntlrTokenToAttributeIdMapper> bindAbstractAntlrTokenToAttributeIdMapper() {
		return TokenToAttributeIdMapper.class;
	}

	/**
	 * Bind a proper highlighting configuration
	 */
	public Class<? extends IHighlightingConfiguration> bindIHighlightingConfiguration() {
		return HighlightingConfiguration.class;
	}

	/**
	 * Configure the parser based token source for the coloring.
	 */
	public Class<? extends DocumentTokenSource> bindDocumentTokenSource() {
		return ParserBasedDocumentTokenSource.class;
	}

	/**
	 * Configure the token to partition type mapper.
	 */
	public Class<? extends TerminalsTokenTypeToPartitionMapper> bindTerminalTokenTypeToPartitionMapper() {
		return TokenTypeToPartitionMapper.class;
	}

	@Override
	public Class<? extends IHighlightingHelper> bindIHighlightingHelper() {
		return InvalidatingHighlightingHelper.class;
	}

	@Override
	public Class<? extends ITokenScanner> bindITokenScanner() {
		return TemplateAwareTokenScanner.class;
	}

	@Override
	public Class<? extends AbstractEditStrategyProvider> bindAbstractEditStrategyProvider() {
		return AutoEditStrategyProvider.class;
	}

	@Override
	public Class<? extends IContentAssistantFactory> bindIContentAssistantFactory() {
		return ContentAssistantFactory.class;
	}

	@Override
	public Class<? extends IPartitionTokenScanner> bindIPartitionTokenScanner() {
		return TemplateAwarePartitionTokenScanner.class;
	}

	/**
	 * Bind the double click strategy provider.
	 */
	public Class<? extends DoubleClickStrategyProvider> bindDoubleClickStrategyProvider() {
		return N4JSDoubleClickStrategyProvider.class;
	}

	/**
	 * Bind the mechanism to extract a cancel indicator (a "real" cancel indicator in IDE scenario, a "NullImpl" one in
	 * the headless compiler).
	 */
	public Class<? extends CancelIndicatorBaseExtractor> bindCancelIndicatorExtractor() {
		return CancelIndicatorUiExtractor.class;
	}

	/**
	 * Avoid unnecessary overhead for trace file lookup.
	 */
	public Class<? extends EclipseResourceFileSystemAccess2> bindEclipseResourceFileSystemAccess2() {
		return FileSystemAccessWithoutTraceFileSupport.class;
	}

	/**
	 * Filter out output-folders from compilation-processing.
	 */
	public Class<? extends DefaultResourceUIServiceProvider> bindResourceUIServiceProvider() {
		return OutputFolderAwareResourceServiceProvider.class;
	}

	/** Generating markers. */
	public Class<? extends IGeneratorMarkerSupport> bindIGeneratorMarkerSupport() {
		return GeneratorMarkerSupport.class;
	}

	/** Performance workaround, see https://github.com/NumberFour/n4js/issues/246 */
	public Class<? extends ContentFormatter> bindContentFormatter() {
		return FixedContentFormatter.class;
	}

	/**
	 * Binds outline factory which creates special nodes to allow for inherited members to be filtered.
	 */
	public Class<? extends OutlineNodeFactory> bindOutlineNodeFactory() {
		return N4JSOutlineNodeFactory.class;
	}

	/** Outline modes for showing inherited members or not */
	public Class<? extends IOutlineTreeProvider.ModeAware> bindIOutlineTreeProvider_ModeAware() {
		return N4JSOutlineModes.class;
	}

	/**
	 * Toggle showing inherited members or not.
	 */
	public void configureInheritedMembersOutlineContribution(Binder binder) {
		binder.bind(IOutlineContribution.class).annotatedWith(Names.named("InheritedMembersOutlineContribution")).to(
				N4JSShowInheritedMembersOutlineContribution.class);
	}

	/**
	 * Toggle showing static members or not.
	 */
	public void configureFilterStaticMembersOutlineContribution(Binder binder) {
		binder.bind(IOutlineContribution.class).annotatedWith(Names.named("FilterStaticMembersOutlineContribution")).to(
				N4JSFilterStaticMembersOutlineContribution.class);
	}

	/**
	 * Toggle showing non-public members or not.
	 */
	public void configureFilterNonPublicMembersOutlineContribution(Binder binder) {
		binder.bind(IOutlineContribution.class).annotatedWith(Names.named("FilterNonPublicMembersOutlineContribution"))
				.to(N4JSFilterNonPublicMembersOutlineContribution.class);
	}

	/**
	 * Toggle showing local types or not.
	 */
	public void configureFilterLocalTypesOutlineContribution(Binder binder) {
		binder.bind(IOutlineContribution.class).annotatedWith(Names.named("FilterLocalTypesOutlineContribution"))
				.to(N4JSFilterLocalTypesOutlineContribution.class);
	}

	@Override
	public Class<? extends IComparator> bindOutlineFilterAndSorter$IComparator() {
		return MetaTypeAwareComparator.class;
	}

	/**
	 * LoadFromSourceHelper specific to the interactive editor scenario.
	 */
	public Class<? extends CanLoadFromDescriptionHelper> bindcanLoadFromDescriptionHelper() {
		return EditorAwareCanLoadFromDescriptionHelper.class;
	}

	/**
	 * Provide multiple hyperlink for composed members.
	 */
	public Class<? extends HyperlinkHelper> bindHyperlinkHelper() {
		return N4JSHyperlinkHelper.class;
	}

	/***/
	public Class<? extends IReferenceFinder> bindReferenceFinder() {
		return ConcreteSyntaxAwareReferenceFinder.class;
	}

	/** Custom EditorResourceAccess as a fix for GH-234. */
	public Class<? extends EditorResourceAccess> bindEditorResourceAccess() {
		return N4JSEditorResourceAccess.class;
	}

	/** A document provider that will not cancel a build when opening a file. */
	public Class<? extends XtextDocumentProvider> bindXtextDocumentProvider() {
		return AvoidRefreshDocumentProvider.class;
	}

	/** Custom XtextDocument. */
	public Class<? extends XtextDocument> bindXtextDocument() {
		return N4JSDocument.class;
	}

	/** Custom LanguageSpecificURIEditorOpener. */
	public Class<? extends LanguageSpecificURIEditorOpener> bindLanguageSpecificURIEditorOpener() {
		return N4JSLanguageSpecificURIEditorOpener.class;
	}

	/** Custom XtextReconciler. */
	public Class<? extends XtextReconciler> bindXtextReconciler() {
		return N4JSReconciler.class;
	}

	/** Custom IEObjectHover. */
	@Override
	public Class<? extends IEObjectHover> bindIEObjectHover() {
		return N4JSHover.class;
	}

	/** Custom IHyperlinkDetector. */
	@Override
	public Class<? extends IHyperlinkDetector> bindIHyperlinkDetector() {
		return N4JSHyperlinkDetector.class;
	}

	@Override
	public void configureXtextEditorErrorTickUpdater(com.google.inject.Binder binder) {
		binder.bind(IXtextEditorCallback.class).annotatedWith(Names.named("IXtextEditorCallBack")).to( //$NON-NLS-1$
				N4JSEditorErrorTickUpdater.class);
	}

	@Override
	public Class<? extends DocumentBasedDirtyResource> bindDocumentBasedDirtyResource() {
		return PrevStateAwareDocumentBasedDirtyResource.class;
	}

	/** Bind N4JS composite generator */
	public Class<? extends ICompositeGenerator> bindICompositeGenerator() {
		return N4JSCompositeGenerator.class;
	}

	/** Bind custom ReferenceSearchResultContentProvider. Workaround to fix GH-724. */
	public Class<? extends ReferenceSearchResultContentProvider> bindReferenceSearchResultContentProvider() {
		return MyReferenceSearchResultContentProvider.class;
	}

	/** Bind custom IProjectCreator for creating N4JS projects using the project wizard. */
	public Class<? extends IProjectCreator> bindN4JSProjectCreator() {
		return N4JSProjectCreator.class;
	}

	/** Custom RenameElementHandler */
	public Class<? extends DefaultRenameElementHandler> bindDefaultRenameElementHandler() {
		return N4JSRenameElementHandler.class;
	}

	/** Custom LinkedPositionGroupCalculator */
	public Class<? extends DefaultLinkedPositionGroupCalculator> bindDefaultLinkedPositionGroupCalculator() {
		return N4JSLinkedPositionGroupCalculator.class;
	}

	/** Custom Rename strategy */
	public Class<? extends DefaultRenameStrategy> bindDefaultRenameStrategy() {
		return N4JSRenameStrategy.class;
	}

	/** Custom RefactoringCrossReferenceSerializer */
	public Class<? extends RefactoringCrossReferenceSerializer> bindN4JSRefactoringCrossReferenceSerializer() {
		return N4JSRefactoringCrossReferenceSerializer.class;
	}

	/** Custom RefactoringResourceSetProvider */
	public Class<? extends RefactoringResourceSetProvider> bindRefactoringResourceSetProvider() {
		return N4JSRefactoringResourceSetProvider.class;
	}

	/** Custom N4JSRenameElementProcessor */
	public Class<? extends AbstractRenameProcessor> bindAbstractRenameProcessor() {
		return N4JSRenameElementProcessor.class;
	}

	@Override
	public Class<? extends IDependentElementsCalculator> bindIDependentElementsCalculator() {
		return N4JSDependentElementsCalculator.class;
	}

	/** Optimized N4JSResourceDescriptionManager */
	public Class<? extends N4JSResourceDescriptionManager> bindN4JSResourceDescriptionManager() {
		return N4JSEclipseResourceDescriptionManager.class;
	}
}
