/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.n4idl

import com.enfore.n4idl.AbstractN4IDLRuntimeModule
import com.google.inject.Provider
import org.eclipse.n4js.n4idl.scoping.N4IDLImportedElementsScopingHelper
import org.eclipse.n4js.n4idl.scoping.N4IDLVersionAwareScopeProvider
import org.eclipse.n4js.n4idl.scoping.VersionScopeProvider
import org.eclipse.n4js.n4idl.scoping.utils.MultiImportedElementsMap
import org.eclipse.n4js.n4idl.validation.N4IDLImportValidator
import org.eclipse.n4js.n4idl.versioning.VersionHelper
import org.eclipse.n4js.resource.XpectAwareFileExtensionCalculator
import org.eclipse.n4js.scoping.imports.ImportedElementsMap
import org.eclipse.n4js.scoping.imports.ImportedElementsScopingHelper
import org.eclipse.n4js.typesystem.VersionResolver
import org.eclipse.n4js.validation.validators.N4JSImportValidator
import org.eclipse.xtext.scoping.IScopeProvider

/**
 * Use this class to register components to be used at runtime / without the Equinox extension registry.
 */
class N4IDLRuntimeModule extends AbstractN4IDLRuntimeModule {
//	/**
//	 * customized parser that changes the handling of hidden tokens: they are disabled in favor of semicolon injection
//	 *
//	 * @return Class<{@link N4JSSemicolonInjectingParser}>
//	 */
//	override Class<? extends IParser> bindIParser() {
//		return N4IDLSemicolonInjectingParser;
//	}

//	/**
//	 * customized AST element factory, that sets the PropertyAssignment kind depending if the element is a
//	 * name-valur-pair, a getter or setter and the feature is a name.
//	 *
//	 * @return Class<{@link PropertyNameAwareElementFactory}>
//	 */
//	override Class<? extends IAstFactory> bindIAstFactory() {
//		return PropertyNameAwareElementFactory;
//	}
//
//	/**
//	 * Registers value converters for rules like STRING, FQN, OCTAL_INT, REGEX_LITERAL and so on.
//	 *
//	 * @return Class<{@link ValueConverters}>
//	 */
//	override Class<? extends IValueConverterService> bindIValueConverterService() {
//		return ValueConverters;
//	}
//
//	/**
//	 * Value converter to handle unicode characters.
//	 *
//	 * @return Class<{@link N4JSStringValueConverter}>
//	 */
//	@Inject
//	def Class<? extends STRINGValueConverter> bindN4JSStringValueConverter() {
//		return N4JSStringValueConverter;
//	}
//
//	/**
//	 * Handle octal escapes without a leading zero in JS strings.
//	 *
//	 * @return Class<{@link BadEscapementAwareMessageProvider}>
//	 */
//	@Inject
//	def Class<? extends SyntaxErrorMessageProvider> bindSyntaxErrorMessageProvider() {
//		return BadEscapementAwareMessageProvider;
//	}
//
//	/**
//	 * Customized resource implementation that is able to have a proxified first slot (get(0)) and a resolved second
//	 * slot (get(1)) where the second slot content has been derived from the first slot when it has been loaded.
//	 *
//	 * @return Class<{@link N4JSResource}>
//	 */
//	override Class<? extends XtextResource> bindXtextResource() {
//		return N4JSResource;
//	}
//
//	/**
//	 * Customized post processor for {@link N4JSResource}s.
//	 */
//	def Class<? extends PostProcessor> bindPostProcessor() {
//		return N4JSPostProcessor;
//	}
//
//	/**
//	 * Customized linker, that produces a linked AST with customized encoded URIs. It also triggers the validation of
//	 * the so produced linked AST.
//	 *
//	 * @return Class<{@link N4JSLinker}>
//	 */
//	override Class<? extends ILinker> bindILinker() {
//		return N4JSLinker;
//	}
//
//	/**
//	 * Customized to add linking diagnostics to EObjectDescriptions that are specially marked.
//	 *
//	 * @return Class<{@link ErrorAwareLinkingService}>
//	 */
//	override Class<? extends ILinkingService> bindILinkingService() {
//		return ErrorAwareLinkingService;
//	}
//
//	/**
//	 * Returns type {@link N4JSResourceDescriptionStrategy}, creates EObjectDescriptions in index.
//	 */
//	def Class<? extends IDefaultResourceDescriptionStrategy> bindIDefaultResourceDescriptionStrategy() {
//		return N4JSResourceDescriptionStrategy;
//	}
//
//	/**
//	 * Customized so that it produces {@link N4JSResourceDescription}.
//	 *
//	 * @return Class<{@link N4JSResourceDescriptionManager}>
//	 */
//	def Class<? extends IResourceDescription.Manager> bindIResourceDescriptionManager() {
//		return N4JSResourceDescriptionManager;
//	}
//
//	/**
//	 *
//	 *
//	 * @return Class<{@link N4JSDerivedStateComputer}>
//	 */
//	def Class<? extends IDerivedStateComputer> bindIDerivedStateComputer() {
//		return N4JSDerivedStateComputer;
//	}
//
//	/**
//	 * Unloads type model instances.
//	 *
//	 * @return Class<{@link N4JSUnloader}>
//	 */
//	def Class<? extends IReferableElementsUnloader> bindIReferableElementsUnloader() {
//		return N4JSUnloader;
//	}
//
//	/**
//	 * Binds the target platform location provider to the headless implementation.
//	 */
//	def Class<? extends TargetPlatformInstallLocationProvider> bindTargetPlatformInstallLocationProvider() {
//		return HeadlessTargetPlatformInstallLocationProvider;
//	}
//
//	/**
//	 * Required for serializing/deserializing type instances in order to save them to the user data field of
//	 * EObjectDescriptions
//	 *
//	 * @return Class<{@link UserdataMapper}>
//	 */
//	def Class<? extends UserdataMapper> bindTypeUserdataMapper() {
//		return UserdataMapper;
//	}
//
//	override Class<? extends IGlobalScopeProvider> bindIGlobalScopeProvider() {
//		return N4JSGlobalScopeProvider;
//	}
//
//	/**
//	 * Sets the scope provider to use as delegate for the local scope provider. This delegate is used to handle imported
//	 * elements. The customization makes elements that name is equal to the resource name both referencable by e.g
//	 * my/pack/A/A as well as my/pack/A if the resource name is A. In this delegate later the import of the built in
//	 * types should be made.
//	 */
//	def void configureIScopeProviderDelegate(Binder binder) {
//		binder.bind(IScopeProvider)
//				.annotatedWith(
//						Names
//								.named(AbstractDeclarativeScopeProvider.NAMED_DELEGATE))
//				.to(N4JSImportedNamespaceAwareLocalScopeProvider);
//	}

	override Class<? extends IScopeProvider> bindIScopeProvider() {
		return N4IDLVersionAwareScopeProvider;
	}

	/**
	 * Binds a scope provider for version scopes.
	 */
	def Class<? extends VersionScopeProvider> bindVersionScopeProvider() {
		return N4IDLVersionAwareScopeProvider;
	}
//
//	/**
//	 * Binds a custom qualified name converter changing the delimiter to "/".
//	 */
//	def Class<? extends IQualifiedNameConverter> bindIQualifiedNameConverter() {
//		return N4JSQualifiedNameConverter;
//	}
//
//	// contributed by org.eclipse.xtext.generator.exporting.SimpleNamesFragment
//	override Class<? extends IQualifiedNameProvider> bindIQualifiedNameProvider() {
//		return N4JSQualifiedNameProvider;
//	}
//
//	override void configure(Binder binder) {
//		super.configure(binder);
//		binder.bindConstant()
//				.annotatedWith(Names.named("org.eclipse.xtext.validation.CompositeEValidator.USE_EOBJECT_VALIDATOR"))
//				.to(false);
//
//		// set-up infrastructure for custom scopes
//		val ScopeManager scopeManager = new ScopeManager();
//		binder.bind(ScopeManager).toInstance(scopeManager);
//		binder.bindScope(TransformationScoped, scopeManager);
//
//		// setup documentation provider to match jsdoc-style exactly two stars only:
//		binder.bind(String)
//				.annotatedWith(Names.named(AbstractMultiLineCommentProvider.START_TAG))
//				.toInstance("/\\*\\*[^*]");
//	}
//
////	/**
////	 * Provides new instances of the concrete lexer implementation.
////	 *
////	 * @see RegExLiteralAwareLexer
////	 */
////	override Provider<InternalN4IDLLexer> provideInternalN4IDLLexer() {
////		return new Provider<InternalN4IDLLexer>() {
////			override InternalN4IDLLexer get() {
////				return new RegExLiteralAwareLexer();
////			}
////		};
////	}
//
//	/**
//	 * Returns empty set for out going references (as calculation of reference descriptions is disabled)
//	 */
//	def Class<? extends DescriptionUtils> bindDescriptionUtils() {
//		return N4JSDescriptionUtils;
//	}
//
//	/**
//	 * Provides new instances of the ImportedNamesAdapter, e.g. concrete instances of N4JSImportedNamesAdapter.
//	 *
//	 * @see ImportedNamesAdapter
//	 */
//	def Provider<ImportedNamesAdapter> provideImportedNamesAdapter() {
//		return new Provider<ImportedNamesAdapter>() {
//			override ImportedNamesAdapter get() {
//				return new N4JSImportedNamesAdapter();
//			}
//		};
//	}
//
//	/**
//	 * adds EOL to white spaces
//	 */
//	def Class<? extends IHiddenTokenHelper> bindIHiddenTokenHelper() {
//		return N4JSHiddenTokenHelper;
//	}
//
//	override Class<? extends XtextResourceSet> bindXtextResourceSet() {
//		return ResourceSetWithBuiltInScheme;
//	}
//
//	/**
//	 * Configure the IN4JSCore instance to use the implementation that is backed by {@link File files}.
//	 */
//	def Class<? extends IN4JSCore> bindN4JSCore() {
//		return N4JSRuntimeCore;
//	}
//
//	/**
//	 * Binds the preference store implementation for the external libraries to the
//	 * {@link FileBasedExternalLibraryPreferenceStore file based} one.
//	 */
//	def Class<? extends ExternalLibraryPreferenceStore> bindExternalLibraryPreferenceStore() {
//		return FileBasedExternalLibraryPreferenceStore;
//	}
//
//	/**
//	 * Configure the IN4JSCore instance to use the implementation that is backed by {@link File files}.
//	 */
//	def Class<? extends InternalN4JSWorkspace> bindInternalN4JSWorkspace() {
//		return FileBasedWorkspace;
//	}
//
////	/**
////	 * Binds the types builder -- binds to N4IDLTypesBuilder instead of N4JSTypesBuilder!
////	 */
////	def Class<? extends N4JSTypesBuilder> bindTypesBuilder() {
////		return N4IDLTypesBuilder;
////	}
//
//	/**
//	 * Improved string representation for Xsemantics
//	 */
//	def Class<? extends StringRepresentation> bindStringRepresentation() {
//		return N4JSStringRepresenation;
//	}
//
//	/**
//	 * Binds the main type system facade used as an entry point into the type system.
//	 */
//	def Class<? extends N4JSTypeSystem> bindTypeSystem() {
//		return N4JSTypeSystem;
//	}
//
//	/**
//	 * Binds the internal, Xsemantics-generated type system. Binds an implementation that doesn't use stack-traces in
//	 * its exception protocol.
//	 */
//	def Class<? extends InternalTypeSystem> bindInternalTypeSystem() {
//		return CustomInternalTypeSystem;
//	}

//	/**
//	 * Bind an implementation that use custom issue severities, that uses the default severity provided in
//	 * org.eclipse.n4js/validation/messages.properties for a given issue code, when there is no other severity
//	 * configured by the {@link ConfigurableIssueCodesProvider}.
//	 */
//	def Class<? extends IssueSeveritiesProvider> bindIssueSeveritiesProvider() {
//		return N4IDLIssueSeveritiesProvider;
////	}
//
//	/**
//	 * Binds to special N4JS version removing the "failed: " prefix from error messages.
//	 */
//	def Class<? extends XsemanticsValidatorErrorGenerator> bindXsemanticsValidatorErrorGenerator() {
//		return N4JSValidatorErrorGenerator;
//	}
//
//	/**
//	 * Binds to special N4JS version of xtext's {@link OnChangeEvictingCache}.
//	 */
//	def Class<? extends OnChangeEvictingCache> bindCache() {
//		return N4JSCache;
//	}
//
//	/**
//	 * Binds to special N4JS version of IResourceValidator.
//	 */
//	def Class<? extends IResourceValidator> bindIResourceValidator() {
//		return N4JSResourceValidator;
//	}
//
//	/**
//	 * Binds to special N4JS version of CancelableDiagnostician supporting pre- and post validation phases.
//	 */
//	@SingletonBinding
//	override Class<? extends Diagnostician> bindDiagnostician() {
//		return PrePostDiagnostician;
//	}
//
//	/**
//	 * A custom serializer class that allows to access otherwise protected methods.
//	 */
//	override Class<? extends ISerializer> bindISerializer() {
//		return AccessibleSerializer;
//	}
//
//	/**
//	 * Also register the built in scopes for the virtual base type and the global object.
//	 */
//	def Class<? extends BuiltInSchemeRegistrar> bindBuiltInSchemeRegistrar() {
//		return ScopeRegistrar;
//	}
//
//	/**
//	 * Binds a special language-independent validator checking project setups, mainly used for polyfill-clashes.
//	 */
//	@SingletonBinding(eager = true)
//	def Class<? extends N4JSProjectSetupValidator> bindN4JSProjectSetupValidator() {
//		return N4JSProjectSetupValidator;
//	}
//
//	/***/
//	def Class<? extends IReferenceFinder> bindReferenceFinder() {
//		return ConcreteSyntaxAwareReferenceFinder;
//	}
//
//	/***/
//	def Class<? extends TargetURICollector> bindTargetURICollector() {
//		return InferredElementsTargetURICollector;
//	}
//
//	/***/
//	def Class<? extends TypesKeywordProvider> bindTypesKeywordProvider() {
//		return N4JSElementKeywordProvider;
//	}
//
//	/**
//	 * Bind custom IEObjectDocumentationProvider.
//	 */
//	def Class<? extends IEObjectDocumentationProvider> bindIEObjectDocumentationProvider() {
//		return N4JSDocumentationProvider;
//	}
//
//	/**
//	 * Bind custom IEObjectDocumentationProviderExtension.
//	 */
//	def Class<? extends IEObjectDocumentationProviderExtension> bindIEObjectDocumentationProviderExtension() {
//		return N4JSDocumentationProvider;
//	}
//
//	/**
//	 * Bind the mechanism to extract a cancel indicator (a "real" cancel indicator in IDE scenario, a "NullImpl" one in
//	 * the headless compiler).
//	 */
//	def Class<? extends CancelIndicatorBaseExtractor> bindCancelIndicatorExtractor() {
//		return CancelIndicatorBaseExtractor;
//	}
//
//	/** Configures the formatter preference value provider */
//	override void configureFormatterPreferences(Binder binder) {
//		binder
//				.bind(IPreferenceValuesProvider)
//				.annotatedWith(FormatterPreferences)
//				.to(N4JSSimpleFormattingPreferenceProvider);
//	}

//	/***
//	 * Bind JavaScriptVariantHelper
//	 */
//	@SingletonBinding
//	def Class<? extends JavaScriptVariantHelper> bindJavaScriptVariantHelper() {
//		return N4IDLJavaScriptVariantHelper;
//	}

	/**
	 * Bind type version resolver (used in N4JS.xsemantics). This customization point is used in N4IDL to support
	 * versions in the type system.
	 */
	def Class<? extends VersionResolver> bindVersionResolver() {
		return N4IDLVersionResolver;
	}

	/**
	 * Bind the version helper.
	 */
	def Class<? extends VersionHelper> bindVersionHelper() {
		return VersionHelper;
	}

//	/**
//	 * Bind a helper for typing expression types which are unknown in N4JS. This is a variation point for sub languages
//	 * that need to extend N4JS's type system.
//	 */
//	def Class<? extends UnsupportedExpressionTypeHelper> bindUnsupportedExpressionTypeHelper() {
//		return DefaultUnsupportedExpressionTypeHelper;
//	}

	/**
	 * Bind file extension calculator
	 */
	def Class<? extends XpectAwareFileExtensionCalculator> bindXpectAwareFileExtensionCalculator() {
		return XpectAwareFileExtensionCalculator;
	}
	
//	/**
//	 * Bind N4IDL EcmaScriptTranspiler.
//	 */
//	def Class<? extends EcmaScriptTranspiler> bindEcmaScriptTranspiler() {
//		return N4IDLTranspiler;
//	}
//	
//	/**
//	 * Bind N4IDL specific transpilation preparation step.
//	 */
//	def Class<? extends AST2IMCopierProvider> bindAST2IMCopierProvider() {
//		return N4IDLAST2IMCopier.Provider;
//	}
//	
//	def Class<? extends ExportCallAssistant> bindExportCallAssistant() {
//		return N4IDLExportCallAssistant;
//	}
//	
//	
//	def Class<? extends ImportRewriteAssistant> bindImportRewriteAssistant() {
//		return VersionAwareImportRewriteAssistant;
//	}
	
	def Class<? extends N4JSImportValidator> bindImportValidator() {
		return N4IDLImportValidator;
	}
	
	def Class<? extends ImportedElementsScopingHelper> bindImportedElementsScopingHelper() {
		return N4IDLImportedElementsScopingHelper;
	}
	
	def Provider<? extends ImportedElementsMap> provideImportedElementsMap() {
		return new Provider<ImportedElementsMap> {
			override get() {
				return new MultiImportedElementsMap();
			}
		}
	}

}
