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
package org.eclipse.n4js.ui;

import com.google.inject.Binder;
import com.google.inject.Provider;
import com.google.inject.name.Names;
import org.eclipse.compare.IViewerCreator;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.n4js.ide.contentassist.antlr.N4JSParser;
import org.eclipse.n4js.ide.contentassist.antlr.PartialN4JSContentAssistParser;
import org.eclipse.n4js.ide.contentassist.antlr.lexer.InternalN4JSLexer;
import org.eclipse.n4js.ui.contentassist.N4JSProposalProvider;
import org.eclipse.n4js.ui.labeling.N4JSDescriptionLabelProvider;
import org.eclipse.n4js.ui.labeling.N4JSLabelProvider;
import org.eclipse.n4js.ui.outline.N4JSOutlineTreeProvider;
import org.eclipse.n4js.ui.quickfix.N4JSQuickfixProvider;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.xtext.builder.BuilderParticipant;
import org.eclipse.xtext.builder.EclipseOutputConfigurationProvider;
import org.eclipse.xtext.builder.IXtextBuilderParticipant;
import org.eclipse.xtext.builder.builderState.IBuilderState;
import org.eclipse.xtext.builder.clustering.CurrentDescriptions;
import org.eclipse.xtext.builder.impl.PersistentDataAwareDirtyResource;
import org.eclipse.xtext.builder.nature.NatureAddingEditorCallback;
import org.eclipse.xtext.builder.preferences.BuilderPreferenceAccess;
import org.eclipse.xtext.generator.IContextualOutputConfigurationProvider;
import org.eclipse.xtext.ide.LexerIdeBindings;
import org.eclipse.xtext.ide.editor.contentassist.antlr.IContentAssistParser;
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.Lexer;
import org.eclipse.xtext.ide.editor.partialEditing.IPartialEditingContentAssistParser;
import org.eclipse.xtext.parser.antlr.AntlrTokenDefProvider;
import org.eclipse.xtext.parser.antlr.ITokenDefProvider;
import org.eclipse.xtext.parser.antlr.LexerProvider;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.containers.IAllContainersState;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider;
import org.eclipse.xtext.service.SingletonBinding;
import org.eclipse.xtext.ui.DefaultUiModule;
import org.eclipse.xtext.ui.UIBindings;
import org.eclipse.xtext.ui.codetemplates.ui.AccessibleCodetemplatesActivator;
import org.eclipse.xtext.ui.codetemplates.ui.partialEditing.IPartialEditingContentAssistContextFactory;
import org.eclipse.xtext.ui.codetemplates.ui.partialEditing.PartialEditingContentAssistContextFactory;
import org.eclipse.xtext.ui.codetemplates.ui.preferences.AdvancedTemplatesPreferencePage;
import org.eclipse.xtext.ui.codetemplates.ui.preferences.TemplatesLanguageConfiguration;
import org.eclipse.xtext.ui.codetemplates.ui.registry.LanguageRegistrar;
import org.eclipse.xtext.ui.codetemplates.ui.registry.LanguageRegistry;
import org.eclipse.xtext.ui.compare.DefaultViewerCreator;
import org.eclipse.xtext.ui.editor.DocumentBasedDirtyResource;
import org.eclipse.xtext.ui.editor.IXtextEditorCallback;
import org.eclipse.xtext.ui.editor.contentassist.ContentAssistContext;
import org.eclipse.xtext.ui.editor.contentassist.IContentProposalProvider;
import org.eclipse.xtext.ui.editor.contentassist.IProposalConflictHelper;
import org.eclipse.xtext.ui.editor.contentassist.antlr.AntlrProposalConflictHelper;
import org.eclipse.xtext.ui.editor.contentassist.antlr.DelegatingContentAssistContextFactory;
import org.eclipse.xtext.ui.editor.formatting.IContentFormatterFactory;
import org.eclipse.xtext.ui.editor.formatting2.ContentFormatterFactory;
import org.eclipse.xtext.ui.editor.outline.IOutlineTreeProvider;
import org.eclipse.xtext.ui.editor.outline.impl.IOutlineTreeStructureProvider;
import org.eclipse.xtext.ui.editor.preferences.IPreferenceStoreInitializer;
import org.eclipse.xtext.ui.editor.quickfix.IssueResolutionProvider;
import org.eclipse.xtext.ui.editor.templates.XtextTemplatePreferencePage;
import org.eclipse.xtext.ui.refactoring.IDependentElementsCalculator;
import org.eclipse.xtext.ui.refactoring.IReferenceUpdater;
import org.eclipse.xtext.ui.refactoring.IRenameRefactoringProvider;
import org.eclipse.xtext.ui.refactoring.IRenameStrategy;
import org.eclipse.xtext.ui.refactoring.impl.DefaultDependentElementsCalculator;
import org.eclipse.xtext.ui.refactoring.impl.DefaultReferenceUpdater;
import org.eclipse.xtext.ui.refactoring.impl.DefaultRenameRefactoringProvider;
import org.eclipse.xtext.ui.refactoring.impl.DefaultRenameStrategy;
import org.eclipse.xtext.ui.refactoring.ui.DefaultRenameSupport;
import org.eclipse.xtext.ui.refactoring.ui.IRenameSupport;
import org.eclipse.xtext.ui.refactoring.ui.RefactoringPreferences;
import org.eclipse.xtext.ui.resource.ResourceServiceDescriptionLabelProvider;
import org.eclipse.xtext.ui.shared.Access;

/**
 * Manual modifications go to {@link N4JSUiModule}.
 */
@SuppressWarnings("all")
public abstract class AbstractN4JSUiModule extends DefaultUiModule {

	public AbstractN4JSUiModule(AbstractUIPlugin plugin) {
		super(plugin);
	}
	
	// contributed by org.eclipse.xtext.xtext.generator.ImplicitFragment
	public Provider<? extends IAllContainersState> provideIAllContainersState() {
		return Access.getJavaProjectsState();
	}
	
	// contributed by org.eclipse.xtext.xtext.generator.parser.antlr.XtextAntlrGeneratorFragment2
	public Class<? extends IProposalConflictHelper> bindIProposalConflictHelper() {
		return AntlrProposalConflictHelper.class;
	}
	
	// contributed by org.eclipse.xtext.xtext.generator.parser.antlr.XtextAntlrGeneratorFragment2
	public void configureContentAssistLexer(Binder binder) {
		binder.bind(Lexer.class)
			.annotatedWith(Names.named(LexerIdeBindings.CONTENT_ASSIST))
			.to(InternalN4JSLexer.class);
	}
	
	// contributed by org.eclipse.xtext.xtext.generator.parser.antlr.XtextAntlrGeneratorFragment2
	public void configureHighlightingLexer(Binder binder) {
		binder.bind(org.eclipse.xtext.parser.antlr.Lexer.class)
			.annotatedWith(Names.named(LexerIdeBindings.HIGHLIGHTING))
			.to(org.eclipse.n4js.parser.antlr.lexer.InternalN4JSLexer.class);
	}
	
	// contributed by org.eclipse.xtext.xtext.generator.parser.antlr.XtextAntlrGeneratorFragment2
	public void configureHighlightingTokenDefProvider(Binder binder) {
		binder.bind(ITokenDefProvider.class)
			.annotatedWith(Names.named(LexerIdeBindings.HIGHLIGHTING))
			.to(AntlrTokenDefProvider.class);
	}
	
	// contributed by org.eclipse.xtext.xtext.generator.parser.antlr.XtextAntlrGeneratorFragment2
	public Class<? extends ContentAssistContext.Factory> bindContentAssistContext$Factory() {
		return DelegatingContentAssistContextFactory.class;
	}
	
	// contributed by org.eclipse.xtext.xtext.generator.parser.antlr.XtextAntlrGeneratorFragment2
	public Class<? extends IContentAssistParser> bindIContentAssistParser() {
		return N4JSParser.class;
	}
	
	// contributed by org.eclipse.xtext.xtext.generator.parser.antlr.XtextAntlrGeneratorFragment2
	public void configureContentAssistLexerProvider(Binder binder) {
		binder.bind(InternalN4JSLexer.class).toProvider(LexerProvider.create(InternalN4JSLexer.class));
	}
	
	// contributed by org.eclipse.xtext.xtext.generator.exporting.SimpleNamesFragment2
	public Class<? extends IDependentElementsCalculator> bindIDependentElementsCalculator() {
		return DefaultDependentElementsCalculator.class;
	}
	
	// contributed by org.eclipse.xtext.xtext.generator.builder.BuilderIntegrationFragment2
	public void configureIResourceDescriptionsBuilderScope(Binder binder) {
		binder.bind(IResourceDescriptions.class).annotatedWith(Names.named(ResourceDescriptionsProvider.NAMED_BUILDER_SCOPE)).to(CurrentDescriptions.ResourceSetAware.class);
	}
	
	// contributed by org.eclipse.xtext.xtext.generator.builder.BuilderIntegrationFragment2
	public Class<? extends IXtextEditorCallback> bindIXtextEditorCallback() {
		return NatureAddingEditorCallback.class;
	}
	
	// contributed by org.eclipse.xtext.xtext.generator.builder.BuilderIntegrationFragment2
	public Class<? extends IContextualOutputConfigurationProvider> bindIContextualOutputConfigurationProvider() {
		return EclipseOutputConfigurationProvider.class;
	}
	
	// contributed by org.eclipse.xtext.xtext.generator.builder.BuilderIntegrationFragment2
	public void configureIResourceDescriptionsPersisted(Binder binder) {
		binder.bind(IResourceDescriptions.class).annotatedWith(Names.named(ResourceDescriptionsProvider.PERSISTED_DESCRIPTIONS)).to(IBuilderState.class);
	}
	
	// contributed by org.eclipse.xtext.xtext.generator.builder.BuilderIntegrationFragment2
	public Class<? extends DocumentBasedDirtyResource> bindDocumentBasedDirtyResource() {
		return PersistentDataAwareDirtyResource.class;
	}
	
	// contributed by org.eclipse.xtext.xtext.generator.generator.GeneratorFragment2
	public Class<? extends IXtextBuilderParticipant> bindIXtextBuilderParticipant() {
		return BuilderParticipant.class;
	}
	
	// contributed by org.eclipse.xtext.xtext.generator.generator.GeneratorFragment2
	public IWorkspaceRoot bindIWorkspaceRootToInstance() {
		return ResourcesPlugin.getWorkspace().getRoot();
	}
	
	// contributed by org.eclipse.xtext.xtext.generator.generator.GeneratorFragment2
	public void configureBuilderPreferenceStoreInitializer(Binder binder) {
		binder.bind(IPreferenceStoreInitializer.class)
			.annotatedWith(Names.named("builderPreferenceInitializer"))
			.to(BuilderPreferenceAccess.Initializer.class);
	}
	
	// contributed by org.eclipse.xtext.xtext.generator.formatting.Formatter2Fragment2
	public Class<? extends IContentFormatterFactory> bindIContentFormatterFactory() {
		return ContentFormatterFactory.class;
	}
	
	// contributed by org.eclipse.xtext.xtext.generator.ui.labeling.LabelProviderFragment2
	public Class<? extends ILabelProvider> bindILabelProvider() {
		return N4JSLabelProvider.class;
	}
	
	// contributed by org.eclipse.xtext.xtext.generator.ui.labeling.LabelProviderFragment2
	public void configureResourceUIServiceLabelProvider(Binder binder) {
		binder.bind(ILabelProvider.class).annotatedWith(ResourceServiceDescriptionLabelProvider.class).to(N4JSDescriptionLabelProvider.class);
	}
	
	// contributed by org.eclipse.xtext.xtext.generator.ui.outline.OutlineTreeProviderFragment2
	public Class<? extends IOutlineTreeProvider> bindIOutlineTreeProvider() {
		return N4JSOutlineTreeProvider.class;
	}
	
	// contributed by org.eclipse.xtext.xtext.generator.ui.outline.OutlineTreeProviderFragment2
	public Class<? extends IOutlineTreeStructureProvider> bindIOutlineTreeStructureProvider() {
		return N4JSOutlineTreeProvider.class;
	}
	
	// contributed by org.eclipse.xtext.xtext.generator.ui.quickfix.QuickfixProviderFragment2
	public Class<? extends IssueResolutionProvider> bindIssueResolutionProvider() {
		return N4JSQuickfixProvider.class;
	}
	
	// contributed by org.eclipse.xtext.xtext.generator.ui.contentAssist.ContentAssistFragment2
	public Class<? extends IContentProposalProvider> bindIContentProposalProvider() {
		return N4JSProposalProvider.class;
	}
	
	// contributed by org.eclipse.xtext.xtext.generator.ui.refactoring.RefactorElementNameFragment2
	public void configureIPreferenceStoreInitializer(Binder binder) {
		binder.bind(IPreferenceStoreInitializer.class)
			.annotatedWith(Names.named("RefactoringPreferences"))
			.to(RefactoringPreferences.Initializer.class);
	}
	
	// contributed by org.eclipse.xtext.xtext.generator.ui.refactoring.RefactorElementNameFragment2
	public Class<? extends IRenameStrategy> bindIRenameStrategy() {
		return DefaultRenameStrategy.class;
	}
	
	// contributed by org.eclipse.xtext.xtext.generator.ui.refactoring.RefactorElementNameFragment2
	public Class<? extends IReferenceUpdater> bindIReferenceUpdater() {
		return DefaultReferenceUpdater.class;
	}
	
	// contributed by org.eclipse.xtext.xtext.generator.ui.refactoring.RefactorElementNameFragment2
	public Class<? extends IRenameRefactoringProvider> bindIRenameRefactoringProvider() {
		return DefaultRenameRefactoringProvider.class;
	}
	
	// contributed by org.eclipse.xtext.xtext.generator.ui.refactoring.RefactorElementNameFragment2
	public Class<? extends IRenameSupport.Factory> bindIRenameSupport$Factory() {
		return DefaultRenameSupport.Factory.class;
	}
	
	// contributed by org.eclipse.xtext.xtext.generator.ui.templates.CodetemplatesGeneratorFragment2
	public Provider<? extends TemplatesLanguageConfiguration> provideTemplatesLanguageConfiguration() {
		return AccessibleCodetemplatesActivator.getTemplatesLanguageConfigurationProvider();
	}
	
	// contributed by org.eclipse.xtext.xtext.generator.ui.templates.CodetemplatesGeneratorFragment2
	public Provider<? extends LanguageRegistry> provideLanguageRegistry() {
		return AccessibleCodetemplatesActivator.getLanguageRegistry();
	}
	
	// contributed by org.eclipse.xtext.xtext.generator.ui.templates.CodetemplatesGeneratorFragment2
	@SingletonBinding(eager=true)
	public Class<? extends LanguageRegistrar> bindLanguageRegistrar() {
		return LanguageRegistrar.class;
	}
	
	// contributed by org.eclipse.xtext.xtext.generator.ui.templates.CodetemplatesGeneratorFragment2
	public Class<? extends XtextTemplatePreferencePage> bindXtextTemplatePreferencePage() {
		return AdvancedTemplatesPreferencePage.class;
	}
	
	// contributed by org.eclipse.xtext.xtext.generator.ui.templates.CodetemplatesGeneratorFragment2
	public Class<? extends IPartialEditingContentAssistParser> bindIPartialEditingContentAssistParser() {
		return PartialN4JSContentAssistParser.class;
	}
	
	// contributed by org.eclipse.xtext.xtext.generator.ui.templates.CodetemplatesGeneratorFragment2
	public Class<? extends IPartialEditingContentAssistContextFactory> bindIPartialEditingContentAssistContextFactory() {
		return PartialEditingContentAssistContextFactory.class;
	}
	
	// contributed by org.eclipse.xtext.xtext.generator.ui.compare.CompareFragment2
	public Class<? extends IViewerCreator> bindIViewerCreator() {
		return DefaultViewerCreator.class;
	}
	
	// contributed by org.eclipse.xtext.xtext.generator.ui.compare.CompareFragment2
	public void configureCompareViewerTitle(Binder binder) {
		binder.bind(String.class).annotatedWith(Names.named(UIBindings.COMPARE_VIEWER_TITLE)).toInstance("N4JS Compare");
	}
	
}
