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
package org.eclipse.n4js;

import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.n4js.conversion.N4JSStringValueConverter;
import org.eclipse.n4js.conversion.ValueConverters;
import org.eclipse.n4js.documentation.N4JSDocumentationProvider;
import org.eclipse.n4js.findReferences.ConcreteSyntaxAwareReferenceFinder;
import org.eclipse.n4js.findReferences.InferredElementsTargetURICollector;
import org.eclipse.n4js.flowgraphs.N4JSFlowAnalyser;
import org.eclipse.n4js.formatting2.N4JSSimpleFormattingPreferenceProvider;
import org.eclipse.n4js.generator.ICompositeGenerator;
import org.eclipse.n4js.generator.N4JSCompositeGenerator;
import org.eclipse.n4js.internal.FileBasedWorkspace;
import org.eclipse.n4js.internal.InternalN4JSWorkspace;
import org.eclipse.n4js.internal.N4JSRuntimeCore;
import org.eclipse.n4js.naming.N4JSImportedNamesAdapter;
import org.eclipse.n4js.naming.N4JSQualifiedNameConverter;
import org.eclipse.n4js.naming.N4JSQualifiedNameProvider;
import org.eclipse.n4js.parser.BadEscapementAwareMessageProvider;
import org.eclipse.n4js.parser.N4JSHiddenTokenHelper;
import org.eclipse.n4js.parser.N4JSSemicolonInjectingParser;
import org.eclipse.n4js.parser.PropertyNameAwareElementFactory;
import org.eclipse.n4js.parser.RegExLiteralAwareLexer;
import org.eclipse.n4js.parser.antlr.lexer.InternalN4JSLexer;
import org.eclipse.n4js.postprocessing.N4JSPostProcessor;
import org.eclipse.n4js.preferences.ExternalLibraryPreferenceStore;
import org.eclipse.n4js.preferences.FileBasedExternalLibraryPreferenceStore;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.locations.SafeURI;
import org.eclipse.n4js.resource.AccessibleSerializer;
import org.eclipse.n4js.resource.ErrorAwareLinkingService;
import org.eclipse.n4js.resource.N4JSCache;
import org.eclipse.n4js.resource.N4JSDerivedStateComputer;
import org.eclipse.n4js.resource.N4JSDescriptionUtils;
import org.eclipse.n4js.resource.N4JSLinker;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.resource.N4JSResourceDescription;
import org.eclipse.n4js.resource.N4JSResourceDescriptionManager;
import org.eclipse.n4js.resource.N4JSResourceDescriptionStrategy;
import org.eclipse.n4js.resource.N4JSUnloader;
import org.eclipse.n4js.resource.PostProcessingAwareResource.PostProcessor;
import org.eclipse.n4js.resource.UserdataMapper;
import org.eclipse.n4js.resource.XpectAwareFileExtensionCalculator;
import org.eclipse.n4js.scoping.N4JSGlobalScopeProvider;
import org.eclipse.n4js.scoping.N4JSScopeProvider;
import org.eclipse.n4js.scoping.builtin.ScopeRegistrar;
import org.eclipse.n4js.scoping.imports.ImportedElementsMap;
import org.eclipse.n4js.scoping.imports.N4JSImportedNamespaceAwareLocalScopeProvider;
import org.eclipse.n4js.ts.scoping.builtin.BuiltInSchemeRegistrar;
import org.eclipse.n4js.ts.scoping.builtin.ResourceSetWithBuiltInScheme;
import org.eclipse.n4js.ts.validation.TypesKeywordProvider;
import org.eclipse.n4js.typesbuilder.N4JSTypesBuilder;
import org.eclipse.n4js.typesystem.N4JSTypeSystem;
import org.eclipse.n4js.utils.di.scopes.ScopeManager;
import org.eclipse.n4js.utils.di.scopes.TransformationScoped;
import org.eclipse.n4js.utils.validation.PrePostDiagnostician;
import org.eclipse.n4js.validation.JavaScriptVariantHelper;
import org.eclipse.n4js.validation.N4JSElementKeywordProvider;
import org.eclipse.n4js.validation.N4JSIssueSeveritiesProvider;
import org.eclipse.n4js.validation.N4JSJavaScriptVariantHelper;
import org.eclipse.n4js.validation.N4JSResourceValidator;
import org.eclipse.n4js.xtext.serializer.SerializerPatchModule;
import org.eclipse.xtext.conversion.IValueConverterService;
import org.eclipse.xtext.conversion.impl.STRINGValueConverter;
import org.eclipse.xtext.documentation.IEObjectDocumentationProvider;
import org.eclipse.xtext.documentation.IEObjectDocumentationProviderExtension;
import org.eclipse.xtext.documentation.impl.AbstractMultiLineCommentProvider;
import org.eclipse.xtext.findReferences.IReferenceFinder;
import org.eclipse.xtext.findReferences.TargetURICollector;
import org.eclipse.xtext.formatting2.FormatterPreferences;
import org.eclipse.xtext.linking.ILinker;
import org.eclipse.xtext.linking.ILinkingService;
import org.eclipse.xtext.linking.impl.ImportedNamesAdapter;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.parser.IAstFactory;
import org.eclipse.xtext.parser.IParser;
import org.eclipse.xtext.parser.antlr.IReferableElementsUnloader;
import org.eclipse.xtext.parser.antlr.SyntaxErrorMessageProvider;
import org.eclipse.xtext.parsetree.reconstr.IHiddenTokenHelper;
import org.eclipse.xtext.preferences.IPreferenceValuesProvider;
import org.eclipse.xtext.resource.DescriptionUtils;
import org.eclipse.xtext.resource.IDerivedStateComputer;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.scoping.IGlobalScopeProvider;
import org.eclipse.xtext.scoping.IScopeProvider;
import org.eclipse.xtext.serializer.ISerializer;
import org.eclipse.xtext.service.SingletonBinding;
import org.eclipse.xtext.util.OnChangeEvictingCache;
import org.eclipse.xtext.validation.ConfigurableIssueCodesProvider;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.validation.IssueSeveritiesProvider;

import com.google.inject.Binder;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Names;

/**
 * Use this class to register components to be used at runtime / without the Equinox extension registry.
 */
@SuppressWarnings("restriction")
public class N4JSRuntimeModule extends org.eclipse.n4js.AbstractN4JSRuntimeModule {

	/**
	 * customized parser that changes the handling of hidden tokens: they are disabled in favor of semicolon injection
	 *
	 * @return Class<{@link N4JSSemicolonInjectingParser}>
	 */
	@Override
	public Class<? extends IParser> bindIParser() {
		return N4JSSemicolonInjectingParser.class;
	}

	/**
	 * customized AST element factory, that sets the PropertyAssignment kind depending if the element is a
	 * name-value-pair, a getter or setter and the feature is a name.
	 *
	 * @return Class<{@link PropertyNameAwareElementFactory}>
	 */
	@Override
	public Class<? extends IAstFactory> bindIAstFactory() {
		return PropertyNameAwareElementFactory.class;
	}

	/**
	 * Registers value converters for rules like STRING, FQN, OCTAL_INT, REGEX_LITERAL and so on.
	 *
	 * @return Class<{@link ValueConverters}>
	 */
	@Override
	public Class<? extends IValueConverterService> bindIValueConverterService() {
		return ValueConverters.class;
	}

	/**
	 * Value converter to handle Unicode characters.
	 *
	 * @return Class<{@link N4JSStringValueConverter}>
	 */
	@Inject
	public Class<? extends STRINGValueConverter> bindN4JSStringValueConverter() {
		return N4JSStringValueConverter.class;
	}

	/**
	 * Handle octal escapes without a leading zero in JS strings.
	 *
	 * @return Class<{@link BadEscapementAwareMessageProvider}>
	 */
	@Inject
	public Class<? extends SyntaxErrorMessageProvider> bindSyntaxErrorMessageProvider() {
		return BadEscapementAwareMessageProvider.class;
	}

	/**
	 * Customized resource implementation that is able to have a proxified first slot (get(0)) and a resolved second
	 * slot (get(1)) where the second slot content has been derived from the first slot when it has been loaded.
	 *
	 * @return Class<{@link N4JSResource}>
	 */
	@Override
	public Class<? extends XtextResource> bindXtextResource() {
		return N4JSResource.class;
	}

	/**
	 * Customized post processor for {@link N4JSResource}s.
	 */
	public Class<? extends PostProcessor> bindPostProcessor() {
		return N4JSPostProcessor.class;
	}

	/**
	 * Customized linker, that produces a linked AST with customized encoded URIs. It also triggers the validation of
	 * the so produced linked AST.
	 *
	 * @return Class<{@link N4JSLinker}>
	 */
	@Override
	public Class<? extends ILinker> bindILinker() {
		return N4JSLinker.class;
	}

	/**
	 * Customized to add linking diagnostics to EObjectDescriptions that are specially marked.
	 *
	 * @return Class<{@link ErrorAwareLinkingService}>
	 */
	@Override
	public Class<? extends ILinkingService> bindILinkingService() {
		return ErrorAwareLinkingService.class;
	}

	/**
	 * Returns type {@link N4JSResourceDescriptionStrategy}, creates EObjectDescriptions in index.
	 */
	public Class<? extends org.eclipse.xtext.resource.IDefaultResourceDescriptionStrategy> bindIDefaultResourceDescriptionStrategy() {
		return N4JSResourceDescriptionStrategy.class;
	}

	/**
	 * Customized so that it produces {@link N4JSResourceDescription}.
	 *
	 * @return Class<{@link N4JSResourceDescriptionManager}>
	 */
	public Class<? extends IResourceDescription.Manager> bindIResourceDescriptionManager() {
		return N4JSResourceDescriptionManager.class;
	}

	/**
	 *
	 *
	 * @return Class<{@link N4JSDerivedStateComputer}>
	 */
	public Class<? extends IDerivedStateComputer> bindIDerivedStateComputer() {
		return N4JSDerivedStateComputer.class;
	}

	/**
	 * Unloads type model instances.
	 *
	 * @return Class<{@link N4JSUnloader}>
	 */
	public Class<? extends IReferableElementsUnloader> bindIReferableElementsUnloader() {
		return N4JSUnloader.class;
	}

	/**
	 * Required for serializing/deserializing type instances in order to save them to the user data field of
	 * EObjectDescriptions
	 *
	 * @return Class<{@link UserdataMapper}>
	 */
	public Class<? extends UserdataMapper> bindTypeUserdataMapper() {
		return UserdataMapper.class;
	}

	@Override
	public Class<? extends IGlobalScopeProvider> bindIGlobalScopeProvider() {
		return N4JSGlobalScopeProvider.class;
	}

	/**
	 * Sets the scope provider to use as delegate for the local scope provider. This delegate is used to handle imported
	 * elements. The customization makes elements that name is equal to the resource name both referenceable by e.g
	 * my/pack/A/A as well as my/pack/A if the resource name is A. In this delegate later the import of the built in
	 * types should be made.
	 */
	public void configureIScopeProviderDelegate(Binder binder) {
		binder.bind(org.eclipse.xtext.scoping.IScopeProvider.class)
				.annotatedWith(
						com.google.inject.name.Names
								.named(org.eclipse.xtext.scoping.impl.AbstractDeclarativeScopeProvider.NAMED_DELEGATE))
				.to(N4JSImportedNamespaceAwareLocalScopeProvider.class);
	}

	@Override
	public Class<? extends IScopeProvider> bindIScopeProvider() {
		return N4JSScopeProvider.class;
	}

	/**
	 * Binds a custom qualified name converter changing the delimiter to "/".
	 */
	public Class<? extends IQualifiedNameConverter> bindIQualifiedNameConverter() {
		return N4JSQualifiedNameConverter.class;
	}

	// contributed by org.eclipse.xtext.generator.exporting.SimpleNamesFragment
	@Override
	public Class<? extends IQualifiedNameProvider> bindIQualifiedNameProvider() {
		return N4JSQualifiedNameProvider.class;
	}

	@Override
	public void configure(Binder binder) {
		super.configure(binder);
		binder.bindConstant()
				.annotatedWith(Names.named("org.eclipse.xtext.validation.CompositeEValidator.USE_EOBJECT_VALIDATOR"))
				.to(false);

		// set-up infrastructure for custom scopes
		final ScopeManager scopeManager = new ScopeManager();
		binder.bind(ScopeManager.class).toInstance(scopeManager);
		binder.bindScope(TransformationScoped.class, scopeManager);

		// setup documentation provider to match jsdoc-style exactly two stars only:
		binder.bind(String.class)
				.annotatedWith(Names.named(AbstractMultiLineCommentProvider.START_TAG))
				.toInstance("/\\*\\*(?=[^*])");
	}

	/**
	 * Provides new instances of the concrete lexer implementation.
	 *
	 * @see RegExLiteralAwareLexer
	 */
	@Override
	public Provider<InternalN4JSLexer> provideInternalN4JSLexer() {
		return new Provider<>() {
			@Override
			public InternalN4JSLexer get() {
				return new RegExLiteralAwareLexer();
			}
		};
	}

	/**
	 * Returns empty set for out going references (as calculation of reference descriptions is disabled)
	 */
	public Class<? extends DescriptionUtils> bindDescriptionUtils() {
		return N4JSDescriptionUtils.class;
	}

	/**
	 * Provides new instances of the ImportedNamesAdapter, e.g. concrete instances of N4JSImportedNamesAdapter.
	 *
	 * @see ImportedNamesAdapter
	 */
	public Provider<ImportedNamesAdapter> provideImportedNamesAdapter() {
		return new Provider<>() {
			@Override
			public ImportedNamesAdapter get() {
				return new N4JSImportedNamesAdapter();
			}
		};
	}

	/**
	 * adds EOL to white spaces
	 */
	public Class<? extends IHiddenTokenHelper> bindIHiddenTokenHelper() {
		return N4JSHiddenTokenHelper.class;
	}

	@Override
	public Class<? extends XtextResourceSet> bindXtextResourceSet() {
		return ResourceSetWithBuiltInScheme.class;
	}

	/**
	 * Configure the IN4JSCore instance to use the implementation that is backed by {@link java.io.File files}.
	 */
	public Class<? extends IN4JSCore> bindN4JSCore() {
		return N4JSRuntimeCore.class;
	}

	/**
	 * Binds the preference store implementation for the external libraries to the
	 * {@link FileBasedExternalLibraryPreferenceStore file based} one.
	 */
	public Class<? extends ExternalLibraryPreferenceStore> bindExternalLibraryPreferenceStore() {
		return FileBasedExternalLibraryPreferenceStore.class;
	}

	/**
	 * Configure the IN4JSCore instance to use the implementation that is backed by {@link java.io.File files}.
	 */
	public Class<? extends InternalN4JSWorkspace<? extends SafeURI<?>>> bindInternalN4JSWorkspace() {
		return FileBasedWorkspace.class;
	}

	/**
	 * Binds the types builder.
	 */
	public Class<? extends N4JSTypesBuilder> bindTypesBuilder() {
		return N4JSTypesBuilder.class;
	}

	/**
	 * Binds the main type system facade used as an entry point into the type system.
	 */
	public Class<? extends N4JSTypeSystem> bindTypeSystem() {
		return N4JSTypeSystem.class;
	}

	/**
	 * Binds the flow analyses facade used as an entry point into flow analyses.
	 */
	public Class<? extends N4JSFlowAnalyser> bindFlowAnalyses() {
		return N4JSFlowAnalyser.class;
	}

	/**
	 * Bind an implementation that use custom issue severities, that uses the default severity provided in
	 * org.eclipse.n4js/validation/messages.properties for a given issue code, when there is no other severity
	 * configured by the {@link ConfigurableIssueCodesProvider}.
	 */
	public Class<? extends IssueSeveritiesProvider> bindIssueSeveritiesProvider() {
		return N4JSIssueSeveritiesProvider.class;
	}

	/**
	 * Binds to special N4JS version of xtext's {@link OnChangeEvictingCache}.
	 */
	public Class<? extends OnChangeEvictingCache> bindCache() {
		return N4JSCache.class;
	}

	/**
	 * Binds to special N4JS version of IResourceValidator.
	 */
	public Class<? extends IResourceValidator> bindIResourceValidator() {
		return N4JSResourceValidator.class;
	}

	/**
	 * Binds to special N4JS version of CancelableDiagnostician supporting pre- and post validation phases.
	 */
	@Override
	@SingletonBinding
	public Class<? extends Diagnostician> bindDiagnostician() {
		return PrePostDiagnostician.class;
	}

	/**
	 * A custom serializer class that allows to access otherwise protected methods.
	 */
	@Override
	public Class<? extends ISerializer> bindISerializer() {
		return AccessibleSerializer.class;
	}

	/**
	 * Also register the built in scopes for the virtual base type and the global object.
	 */
	public Class<? extends BuiltInSchemeRegistrar> bindBuiltInSchemeRegistrar() {
		return ScopeRegistrar.class;
	}

	/***/
	public Class<? extends IReferenceFinder> bindReferenceFinder() {
		return ConcreteSyntaxAwareReferenceFinder.class;
	}

	/***/
	public Class<? extends TargetURICollector> bindTargetURICollector() {
		return InferredElementsTargetURICollector.class;
	}

	/***/
	public Class<? extends TypesKeywordProvider> bindTypesKeywordProvider() {
		return N4JSElementKeywordProvider.class;
	}

	/**
	 * Bind custom IEObjectDocumentationProvider.
	 */
	public Class<? extends IEObjectDocumentationProvider> bindIEObjectDocumentationProvider() {
		return N4JSDocumentationProvider.class;
	}

	/**
	 * Bind custom IEObjectDocumentationProviderExtension.
	 */
	public Class<? extends IEObjectDocumentationProviderExtension> bindIEObjectDocumentationProviderExtension() {
		return N4JSDocumentationProvider.class;
	}

	/**
	 * Bind the mechanism to extract a cancel indicator (a "real" cancel indicator in IDE scenario, a "NullImpl" one in
	 * the headless compiler).
	 */
	public Class<? extends CancelIndicatorBaseExtractor> bindCancelIndicatorExtractor() {
		return CancelIndicatorBaseExtractor.class;
	}

	/** Configures the formatter preference value provider */
	@Override
	public void configureFormatterPreferences(Binder binder) {
		binder
				.bind(IPreferenceValuesProvider.class)
				.annotatedWith(FormatterPreferences.class)
				.to(N4JSSimpleFormattingPreferenceProvider.class);
	}

	/**
	 * Bind JavaScriptVariantHelper
	 */
	@SingletonBinding
	public Class<? extends JavaScriptVariantHelper> bindJavaScriptVariantHelper() {
		return N4JSJavaScriptVariantHelper.class;
	}

	/**
	 * Bind file extension calculator
	 */
	public Class<? extends XpectAwareFileExtensionCalculator> bindXpectAwareFileExtensionCalculator() {
		return XpectAwareFileExtensionCalculator.class;
	}

	/** Bind N4JS composite generator */
	public Class<? extends ICompositeGenerator> bindICompositeGenerator() {
		return N4JSCompositeGenerator.class;
	}

	/** Avoid races in internal serializer caches */
	public void configureSerializerPatches(Binder binder) {
		new SerializerPatchModule().configure(binder);
	}

	//// N4IDL specific bindings

	/**
	 */
	public Class<? extends ImportedElementsMap.Provider> bindImportedElementsMapProvider() {
		return ImportedElementsMap.Provider.class;
	}

}
