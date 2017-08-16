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
package org.eclipse.n4js.n4jsx.ui

import com.google.inject.Binder
import com.google.inject.Provider
import com.google.inject.Singleton
import com.google.inject.name.Names
import java.util.HashSet
import java.util.List
import java.util.Set
import org.eclipse.core.resources.IWorkspace
import org.eclipse.core.runtime.Platform
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.jface.text.rules.IPartitionTokenScanner
import org.eclipse.jface.text.rules.ITokenScanner
import org.eclipse.jface.viewers.ILabelProvider
import org.eclipse.n4js.CancelIndicatorBaseExtractor
import org.eclipse.n4js.N4JSRuntimeModule
import org.eclipse.n4js.binaries.BinariesPreferenceStore
import org.eclipse.n4js.binaries.OsgiBinariesPreferenceStore
import org.eclipse.n4js.external.ExternalLibraryWorkspace
import org.eclipse.n4js.external.GitCloneSupplier
import org.eclipse.n4js.external.TargetPlatformInstallLocationProvider
import org.eclipse.n4js.external.TypeDefinitionGitLocationProvider
import org.eclipse.n4js.generator.common.CompilerDescriptor
import org.eclipse.n4js.generator.common.IComposedGenerator
import org.eclipse.n4js.generator.common.IGeneratorMarkerSupport
import org.eclipse.n4js.generator.ui.GeneratorMarkerSupport
import org.eclipse.n4js.n4jsx.ui.contentassist.ContentAssistContextFactory
import org.eclipse.n4js.n4jsx.ui.contentassist.CustomN4JSXParser
import org.eclipse.n4js.n4jsx.ui.editor.syntaxcoloring.ParserBasedDocumentTokenSource
import org.eclipse.n4js.n4jsx.ui.organize.imports.N4JSXReferencesFilter
import org.eclipse.n4js.preferences.ExternalLibraryPreferenceStore
import org.eclipse.n4js.preferences.OsgiExternalLibraryPreferenceStore
import org.eclipse.n4js.projectModel.IN4JSCore
import org.eclipse.n4js.scoping.utils.CanLoadFromDescriptionHelper
import org.eclipse.n4js.ts.findReferences.TargetURIKey
import org.eclipse.n4js.ts.ui.search.BuiltinSchemeAwareTargetURIKey
import org.eclipse.n4js.ts.ui.search.LabellingReferenceFinder
import org.eclipse.n4js.ui.N4JSEditor
import org.eclipse.n4js.ui.building.FileSystemAccessWithoutTraceFileSupport
import org.eclipse.n4js.ui.building.N4JSBuilderParticipant
import org.eclipse.n4js.ui.building.N4JSOutputConfigurationProvider
import org.eclipse.n4js.ui.building.instructions.ComposedGeneratorRegistry
import org.eclipse.n4js.ui.containers.N4JSAllContainersStateProvider
import org.eclipse.n4js.ui.contentassist.ContentAssistantFactory
import org.eclipse.n4js.ui.contentassist.N4JSFollowElementCalculator
import org.eclipse.n4js.ui.contentassist.PatchedFollowElementComputer
import org.eclipse.n4js.ui.contentassist.SimpleLastSegmentFinder
import org.eclipse.n4js.ui.editor.AlwaysAddNatureCallback
import org.eclipse.n4js.ui.editor.EditorAwareCanLoadFromDescriptionHelper
import org.eclipse.n4js.ui.editor.N4JSDirtyStateEditorSupport
import org.eclipse.n4js.ui.editor.N4JSDoubleClickStrategyProvider
import org.eclipse.n4js.ui.editor.N4JSLocationInFileProvider
import org.eclipse.n4js.ui.editor.NFARAwareResourceForEditorInputFactory
import org.eclipse.n4js.ui.editor.autoedit.AutoEditStrategyProvider
import org.eclipse.n4js.ui.editor.syntaxcoloring.HighlightingConfiguration
import org.eclipse.n4js.ui.editor.syntaxcoloring.InvalidatingHighlightingHelper
import org.eclipse.n4js.ui.editor.syntaxcoloring.TemplateAwarePartitionTokenScanner
import org.eclipse.n4js.ui.editor.syntaxcoloring.TemplateAwareTokenScanner
import org.eclipse.n4js.ui.editor.syntaxcoloring.TokenToAttributeIdMapper
import org.eclipse.n4js.ui.editor.syntaxcoloring.TokenTypeToPartitionMapper
import org.eclipse.n4js.ui.formatting2.FixedContentFormatter
import org.eclipse.n4js.ui.internal.ConsoleOutputStreamProvider
import org.eclipse.n4js.ui.internal.EclipseBasedN4JSWorkspace
import org.eclipse.n4js.ui.labeling.N4JSContentAssistLabelProvider
import org.eclipse.n4js.ui.labeling.N4JSHoverProvider
import org.eclipse.n4js.ui.labeling.N4JSHyperlinkLabelProvider
import org.eclipse.n4js.ui.organize.imports.IReferenceFilter
import org.eclipse.n4js.ui.preferences.N4JSBuilderPreferenceAccess
import org.eclipse.n4js.ui.projectModel.IN4JSEclipseCore
import org.eclipse.n4js.ui.quickfix.N4JSIssue
import org.eclipse.n4js.ui.quickfix.N4JSMarkerResolutionGenerator
import org.eclipse.n4js.ui.resource.OutputFolderAwareResourceServiceProvider
import org.eclipse.n4js.ui.search.N4JSReferenceQueryExecutor
import org.eclipse.n4js.ui.utils.CancelIndicatorUiExtractor
import org.eclipse.n4js.ui.validation.ManifestAwareResourceValidator
import org.eclipse.n4js.ui.workingsets.WorkingSetManagerBroker
import org.eclipse.n4js.ui.workingsets.WorkingSetManagerBrokerImpl
import org.eclipse.n4js.utils.process.OutputStreamProvider
import org.eclipse.ui.PlatformUI
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import org.eclipse.xtext.builder.EclipseResourceFileSystemAccess2
import org.eclipse.xtext.builder.IXtextBuilderParticipant
import org.eclipse.xtext.builder.preferences.BuilderPreferenceAccess
import org.eclipse.xtext.generator.IFileSystemAccess
import org.eclipse.xtext.generator.IGenerator
import org.eclipse.xtext.generator.IOutputConfigurationProvider
import org.eclipse.xtext.ide.editor.contentassist.antlr.FollowElementCalculator
import org.eclipse.xtext.ide.editor.contentassist.antlr.FollowElementComputer
import org.eclipse.xtext.ide.editor.contentassist.antlr.IContentAssistParser
import org.eclipse.xtext.resource.ILocationInFileProvider
import org.eclipse.xtext.resource.SynchronizedXtextResourceSet
import org.eclipse.xtext.resource.XtextResourceSet
import org.eclipse.xtext.resource.containers.IAllContainersState
import org.eclipse.xtext.ui.editor.DirtyStateEditorSupport
import org.eclipse.xtext.ui.editor.IXtextEditorCallback
import org.eclipse.xtext.ui.editor.XtextEditor
import org.eclipse.xtext.ui.editor.autoedit.AbstractEditStrategyProvider
import org.eclipse.xtext.ui.editor.contentassist.ContentProposalLabelProvider
import org.eclipse.xtext.ui.editor.contentassist.FQNPrefixMatcher
import org.eclipse.xtext.ui.editor.contentassist.FQNPrefixMatcher.LastSegmentFinder
import org.eclipse.xtext.ui.editor.contentassist.IContentAssistantFactory
import org.eclipse.xtext.ui.editor.contentassist.PrefixMatcher
import org.eclipse.xtext.ui.editor.doubleClicking.DoubleClickStrategyProvider
import org.eclipse.xtext.ui.editor.findrefs.IReferenceFinder
import org.eclipse.xtext.ui.editor.findrefs.ReferenceQueryExecutor
import org.eclipse.xtext.ui.editor.formatting2.ContentFormatter
import org.eclipse.xtext.ui.editor.hover.IEObjectHoverProvider
import org.eclipse.xtext.ui.editor.hyperlinking.HyperlinkLabelProvider
import org.eclipse.xtext.ui.editor.model.DocumentTokenSource
import org.eclipse.xtext.ui.editor.model.IResourceForEditorInputFactory
import org.eclipse.xtext.ui.editor.model.TerminalsTokenTypeToPartitionMapper
import org.eclipse.xtext.ui.editor.preferences.IPreferenceStoreInitializer
import org.eclipse.xtext.ui.editor.quickfix.MarkerResolutionGenerator
import org.eclipse.xtext.ui.editor.syntaxcoloring.AbstractAntlrTokenToAttributeIdMapper
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightingConfiguration
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightingHelper
import org.eclipse.xtext.ui.resource.DefaultResourceUIServiceProvider
import org.eclipse.xtext.ui.shared.Access
import org.eclipse.xtext.ui.util.IssueUtil
import org.eclipse.xtext.validation.IResourceValidator

/**
 * Use this class to register components to be used within the Eclipse IDE.
 */
@FinalFieldsConstructor
class N4JSXUiModule extends AbstractN4JSXUiModule {

	override void configure(Binder binder) {
		super.configure(binder);
		doBindIGenerator(binder);
	}

	override Class<? extends IXtextBuilderParticipant> bindIXtextBuilderParticipant() {
		return N4JSBuilderParticipant;
	}

	/**
	 * Re-binds the {@link Singleton @Singleton} {@link ExternalLibraryWorkspace N4JS external library workspace}
	 * instance declared and created in the {@link N4JSRuntimeModule}.
	 */
	def Provider<ExternalLibraryWorkspace> provideN4JSExternalLibraryWorkspace() {
		return Access.contributedProvider(ExternalLibraryWorkspace);
	}

	/**
	 * Re-binds the {@link GitCloneSupplier} to the singleton instance declared in the contribution module.
	 */
	def Provider<GitCloneSupplier> provideGitCloneSupplier() {
		return Access.contributedProvider(GitCloneSupplier);
	}

	
	override Provider<IAllContainersState> provideIAllContainersState() {
		return new N4JSAllContainersStateProvider();
	}

	/**
	 * A custom {@link DirtyStateEditorSupport} that uses a custom JobFamily.
	 */
	def Class<? extends DirtyStateEditorSupport> bindDirtyStateEditorSupport() {
		return N4JSDirtyStateEditorSupport;
	}

	/**
	 * Bind the {@link ILocationInFileProvider} that is aware of derived elements.
	 */
	def Class<? extends ILocationInFileProvider> bindILocationInFileProvider() {
		return N4JSLocationInFileProvider;
	}

	/**
	 * Bind the {@link ReferenceQueryExecutor} that maps to types.
	 */
	def Class<? extends ReferenceQueryExecutor> bindReferenceQueryExecutor() {
		return N4JSReferenceQueryExecutor;
	}

	/**
	 * Bind the {@link IReferenceFinder} that find references solely to types (and
	 * TVariables, IdentifiableElement and TEnumLiterals).
	 */
	def Class<? extends IReferenceFinder> bindIReferenceFinder() {
		return LabellingReferenceFinder;
	}

	/**
	 * Bind the {@link IResourceValidator} that knows how to ignore files that are not in source folders according to
	 * the manifest.
	 */
	def Class<? extends IResourceValidator> bindResourceValidator() {
		return ManifestAwareResourceValidator;
	}

	/**
	 * De-configure the hard coded built in scheme from the runtime bundle.
	 */
	def Class<? extends XtextResourceSet> bindXtextResourceSet() {
		return SynchronizedXtextResourceSet;
	}

	/**
	 * Configure the IN4JSCore instance to use the implementation that is backed by the Eclipse workspace.
	 */
	def Class<? extends IN4JSCore> bindIN4JSCore() {
		return IN4JSEclipseCore;
	}

	/**
	 * Binds the external library preference store to use the {@link OsgiExternalLibraryPreferenceStore OSGi} one. This
	 * provider binding is required to share the same singleton instance between modules, hence injectors.
	 */
	def Provider<ExternalLibraryPreferenceStore> provideExternalLibraryPreferenceStore() {
		return Access.contributedProvider(ExternalLibraryPreferenceStore);
	}

	/**
	 * Binds the broker for the working set managers in a singleton scope.
	 */
	def Provider<WorkingSetManagerBroker> provideWorkingSetManagerBroker() {
		return Access.contributedProvider(WorkingSetManagerBroker);
	}

	/**
	 * Binds the broker implementation for the working set managers in a singleton scope.
	 */
	def Provider<WorkingSetManagerBrokerImpl> provideWorkingSetManagerBrokerImpl() {
		return Access.contributedProvider(WorkingSetManagerBrokerImpl);
	}

	/**
	 * Binds the binaries preference store to use the {@link OsgiBinariesPreferenceStore} one. This provider binding is
	 * required to share the same singleton instance between modules, hence injectors.
	 */
	def Provider<BinariesPreferenceStore> provideBinariesPreferenceStore() {
		return Access.contributedProvider(BinariesPreferenceStore);
	}

	/**
	 * Binds the target platform location provider to the Eclipse based implementation. This requires a running
	 * {@link Platform platform} and an existing and available {@link IWorkspace workspace}.
	 */
	def Provider<TargetPlatformInstallLocationProvider> provideTargetPlatformInstallLocationProvider() {
		return Access.contributedProvider(TargetPlatformInstallLocationProvider);
	}

	/**
	 * Binds the type definition Git location provider.
	 */
	def Provider<TypeDefinitionGitLocationProvider> provideTypeDefinitionGitLocationProvider() {
		return Access.contributedProvider(TypeDefinitionGitLocationProvider);
	}

	/**
	 * Configure the IN4JSCore instance to use the implementation that is backed by the Eclipse workspace.
	 */
	def Provider<IN4JSEclipseCore> provideIN4JSEclipseCore() {
		return Access.contributedProvider(IN4JSEclipseCore);
	}

	/**
	 * Configure the IN4JSCore instance to use the implementation that is backed by the Eclipse workspace.
	 */
	def Provider<EclipseBasedN4JSWorkspace> provideEclipseBasedN4JSWorkspace() {
		return Access.contributedProvider(EclipseBasedN4JSWorkspace);
	}

	
	override Class<? extends IResourceForEditorInputFactory> bindIResourceForEditorInputFactory() {
		return NFARAwareResourceForEditorInputFactory;
	}

	/**
	 * custom builder preferences initializer that iterates over all default configurations provided by the sub
	 * generators contained in the found composite generators.
	 */
	
	override void configureBuilderPreferenceStoreInitializer(Binder binder) {
		binder.bind(IPreferenceStoreInitializer)
				.annotatedWith(Names.named("builderPreferenceInitializer"))
				.to(N4JSBuilderPreferenceAccess.Initializer);
	}

	/**
	 * @return custom builder preference access sets the activation of the builder participant to be always true so that
	 *         dirty state handling is always enabled.
	 */
	def Class<? extends BuilderPreferenceAccess> bindBuilderPreferenceAccess() {
		return N4JSBuilderPreferenceAccess;
	}

	/**
	 * @return iterates over all registered composite generators to pick up the default output configurations of their
	 *         contained sub generators
	 */
	def Class<? extends IOutputConfigurationProvider> bindIOutputConfigurationProvider() {
		return N4JSOutputConfigurationProvider;
	}

	/**
	 * Filter fully qualified proposals also by their last segment and not only by their fully qualified name.
	 */
	def Class<? extends PrefixMatcher> bindPrefixMatcher() {
		return FQNPrefixMatcher;
	}

	/**
	 * No special treatment for uppercase module name segments.
	 */
	def Class<? extends LastSegmentFinder> bindLastSegmentFinder() {
		return SimpleLastSegmentFinder;
	}

	/**
	 * Binds the output stream provider to the console based one in the UI.
	 */
	def Class<? extends OutputStreamProvider> bindOutputStreamProvider() {
		return ConsoleOutputStreamProvider;
	}

	/**
	 * Bind the customized content assist parser infrastructure.
	 */
	def Class<? extends org.eclipse.xtext.ide.editor.contentassist.antlr.ContentAssistContextFactory> bindContentAssistContextFactory() {
		return ContentAssistContextFactory;
	}

	/**
	 * Bind the customized content assist follow element calculator that drops parser rules of "bogus" language parts.
	 */
	def Class<? extends FollowElementCalculator> bindFollowElementCalculator() {
		return N4JSFollowElementCalculator;
	}

	/**
	 * Remove this binding once the change of https://github.com/eclipse/xtext-core/pull/167 is available in the target
	 * platform.
	 */
	def Class<? extends FollowElementComputer> bindFollowElementComputer() {
		return PatchedFollowElementComputer;
	}

	
	override Class<? extends IContentAssistParser> bindIContentAssistParser() {
		return CustomN4JSXParser;
	}

	/**
	 * Loads all registered composed generators via the extension point if there are some the first found composite
	 * generator is registered as IGenerator (this binding is required internally by the Xtext builder participant) or
	 * if there are no composite generators found, a dummy IComposedGenerator implementation is bound as IGenerator.
	 *
	 *
	 * @param binder
	 *            the Google guice binder
	 */
	def void doBindIGenerator(Binder binder) {
		var IComposedGenerator composedGenerator = null;
		val List<IComposedGenerator> composedGenerators = ComposedGeneratorRegistry.getComposedGenerators();
		if (!composedGenerators.isEmpty()) {
			composedGenerator = composedGenerators.get(0);
		} else {
			composedGenerator = new IComposedGenerator() {

				
				override void doGenerate(Resource input, IFileSystemAccess fsa) {
					// nothing to do, as dummy generator
				}

				
				override Set<CompilerDescriptor> getCompilerDescriptors() {
					return new HashSet();
				}
			};
		}
		binder.bind(IGenerator).toInstance(composedGenerator);
	}

	/**
	 * Binds a specific label provider for the content assist use case.
	 */
	
	override void configureContentProposalLabelProvider(Binder binder) {
		binder.bind(ILabelProvider)
				.annotatedWith(ContentProposalLabelProvider)
				.to(N4JSContentAssistLabelProvider);
	}

	/**
	 * Binds a specific label provider for the hyper linking use case.
	 */
	
	override void configureHyperlinkLabelProvider(Binder binder) {
		binder.bind(ILabelProvider)
				.annotatedWith(HyperlinkLabelProvider)
				.to(N4JSHyperlinkLabelProvider);
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
	// def Class<? extends MarkerResolutionGenerator> bindMarkerResolutionGenerator() {
	// return N4JSMarkerResolutionGenerator;
	// }
	def void configureMarkerResolutionGenerator(Binder binder) {
		if (PlatformUI.isWorkbenchRunning()) {
			binder.bind(MarkerResolutionGenerator).to(N4JSMarkerResolutionGenerator);
		}
	}

	// ==== END injection work-around ====

	/**
	 * Bind custom XtextEditor.
	 */
	def Class<? extends XtextEditor> bindXtextEditor() {
		return N4JSEditor;
	}

	/**
	 * Bind custom IssueUtil.
	 */
	def Class<? extends IssueUtil> bindIssueUtil() {
		return N4JSIssue.Util;
	}

	/**
	 * Bind custom IEObjectHoverProvider.
	 */
	def Class<? extends IEObjectHoverProvider> bindIEObjectHoverProvider() {
		return N4JSHoverProvider;
	}

	/**
	 * Bind a callback that always add the nature silently.
	 */
	
	override Class<? extends IXtextEditorCallback> bindIXtextEditorCallback() {
		return AlwaysAddNatureCallback;
	}

	/**
	 * Bind an implementation can handle find references to builtin types.
	 */
	def Class<? extends TargetURIKey> bindTargetURIKey() {
		return BuiltinSchemeAwareTargetURIKey;
	}

	/**
	 * Bind a proper token mapper for the special token types in N4JS
	 */
	def Class<? extends AbstractAntlrTokenToAttributeIdMapper> bindAbstractAntlrTokenToAttributeIdMapper() {
		return TokenToAttributeIdMapper;
	}

	/**
	 * Bind a proper highlighting configuration
	 */
	def Class<? extends IHighlightingConfiguration> bindIHighlightingConfiguration() {
		return HighlightingConfiguration;
	}

	/**
	 * Configure the parser based token source for the coloring.
	 */
	def Class<? extends DocumentTokenSource> bindDocumentTokenSource() {
		return ParserBasedDocumentTokenSource;
	}

	/**
	 * Configure the token to partition type mapper.
	 */
	def Class<? extends TerminalsTokenTypeToPartitionMapper> bindTerminalTokenTypeToPartitionMapper() {
		return TokenTypeToPartitionMapper;
	}

	
	override Class<? extends IHighlightingHelper> bindIHighlightingHelper() {
		return InvalidatingHighlightingHelper;
	}

	
	override Class<? extends ITokenScanner> bindITokenScanner() {
		return TemplateAwareTokenScanner;
	}

	
	override Class<? extends AbstractEditStrategyProvider> bindAbstractEditStrategyProvider() {
		return AutoEditStrategyProvider;
	}

	
	override Class<? extends IContentAssistantFactory> bindIContentAssistantFactory() {
		return ContentAssistantFactory;
	}

	
	override Class<? extends IPartitionTokenScanner> bindIPartitionTokenScanner() {
		return TemplateAwarePartitionTokenScanner;
	}

	/**
	 * Bind the double click strategy provider.
	 */
	def Class<? extends DoubleClickStrategyProvider> bindDoubleClickStrategyProvider() {
		return N4JSDoubleClickStrategyProvider;
	}

	/**
	 * Bind the mechanism to extract a cancel indicator (a "real" cancel indicator in IDE scenario, a "NullImpl" one in
	 * the headless compiler).
	 */
	def Class<? extends CancelIndicatorBaseExtractor> bindCancelIndicatorExtractor() {
		return CancelIndicatorUiExtractor;
	}

	/**
	 * Avoid unnecessary overhead for trace file lookup.
	 */
	def Class<? extends EclipseResourceFileSystemAccess2> bindEclipseResourceFileSystemAccess2() {
		return FileSystemAccessWithoutTraceFileSupport;
	}

	/**
	 * Filter out output-folders from compilation-processing.
	 */
	def Class<? extends DefaultResourceUIServiceProvider> bindResourceUIServiceProvider() {
		return OutputFolderAwareResourceServiceProvider;
	}

	/** Generating markers. */
	def Class<? extends IGeneratorMarkerSupport> bindIGeneratorMarkerSupport() {
		return GeneratorMarkerSupport;
	}

	/** Performance workaround, see https://github.com/NumberFour/n4js/issues/246 */
	def Class<? extends ContentFormatter> bindContentFormatter() {
		return FixedContentFormatter;
	}

	/** Languages variation point for the organize imports */
	def Class<? extends IReferenceFilter> bindContentReferenceFilter() {
		return N4JSXReferencesFilter;
	}
	
	/**
	 * CanLoadFromDescriptionHelper specific to the interactive editor scenario.
	 */
	def Class<? extends CanLoadFromDescriptionHelper> bindCanLoadFromDescriptionHelper() {
		return EditorAwareCanLoadFromDescriptionHelper;
	}
}
