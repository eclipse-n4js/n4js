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
package org.eclipse.n4js.scoping

import com.google.common.base.Optional
import com.google.inject.Inject
import com.google.inject.name.Named
import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.n4js.n4JS.Argument
import org.eclipse.n4js.n4JS.Expression
import org.eclipse.n4js.n4JS.IdentifierRef
import org.eclipse.n4js.n4JS.ImportDeclaration
import org.eclipse.n4js.n4JS.JSXElement
import org.eclipse.n4js.n4JS.JSXElementName
import org.eclipse.n4js.n4JS.JSXPropertyAttribute
import org.eclipse.n4js.n4JS.LabelledStatement
import org.eclipse.n4js.n4JS.LiteralOrComputedPropertyName
import org.eclipse.n4js.n4JS.N4ClassDeclaration
import org.eclipse.n4js.n4JS.N4ClassifierDefinition
import org.eclipse.n4js.n4JS.N4FieldAccessor
import org.eclipse.n4js.n4JS.N4FieldDeclaration
import org.eclipse.n4js.n4JS.N4JSPackage
import org.eclipse.n4js.n4JS.N4MemberDeclaration
import org.eclipse.n4js.n4JS.N4TypeDeclaration
import org.eclipse.n4js.n4JS.NamedImportSpecifier
import org.eclipse.n4js.n4JS.NewExpression
import org.eclipse.n4js.n4JS.ParameterizedCallExpression
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.n4JS.Statement
import org.eclipse.n4js.n4JS.TypeDefiningElement
import org.eclipse.n4js.n4JS.VariableDeclaration
import org.eclipse.n4js.n4JS.VariableEnvironmentElement
import org.eclipse.n4js.n4JS.extensions.SourceElementExtensions
import org.eclipse.n4js.n4idl.scoping.FailedToInferContextVersionWrappingScope
import org.eclipse.n4js.n4idl.scoping.MigrationScopeHelper
import org.eclipse.n4js.n4idl.scoping.N4IDLVersionAwareScope
import org.eclipse.n4js.n4idl.scoping.NonVersionAwareContextScope
import org.eclipse.n4js.n4idl.versioning.MigrationUtils
import org.eclipse.n4js.n4idl.versioning.VersionHelper
import org.eclipse.n4js.n4idl.versioning.VersionUtils
import org.eclipse.n4js.n4jsx.ReactHelper
import org.eclipse.n4js.projectModel.IN4JSCore
import org.eclipse.n4js.resource.N4JSResource
import org.eclipse.n4js.scoping.accessModifiers.MemberVisibilityChecker
import org.eclipse.n4js.scoping.accessModifiers.VisibilityAwareCtorScope
import org.eclipse.n4js.scoping.imports.ImportedElementsScopingHelper
import org.eclipse.n4js.scoping.members.MemberScopingHelper
import org.eclipse.n4js.scoping.utils.DynamicPseudoScope
import org.eclipse.n4js.scoping.utils.LocallyKnownTypesScopingHelper
import org.eclipse.n4js.scoping.utils.MainModuleAwareSelectableBasedScope
import org.eclipse.n4js.scoping.utils.N4JSTypesScopeFilter
import org.eclipse.n4js.scoping.utils.ProjectImportEnablingScope
import org.eclipse.n4js.scoping.utils.ScopesHelper
import org.eclipse.n4js.ts.scoping.ValidatingScope
import org.eclipse.n4js.ts.scoping.builtin.BuiltInTypeScope
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage
import org.eclipse.n4js.ts.typeRefs.TypeTypeRef
import org.eclipse.n4js.ts.types.ModuleNamespaceVirtualType
import org.eclipse.n4js.ts.types.TModule
import org.eclipse.n4js.ts.types.TStructMethod
import org.eclipse.n4js.ts.types.TypeDefs
import org.eclipse.n4js.ts.types.TypingStrategy
import org.eclipse.n4js.typesystem.N4JSTypeSystem
import org.eclipse.n4js.utils.ContainerTypesHelper
import org.eclipse.n4js.utils.EObjectDescriptionHelper
import org.eclipse.n4js.utils.ResourceType
import org.eclipse.n4js.utils.TameAutoClosable
import org.eclipse.n4js.validation.JavaScriptVariantHelper
import org.eclipse.n4js.validation.ValidatorMessageHelper
import org.eclipse.n4js.xtext.scoping.FilteringScope
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.xtext.resource.EObjectDescription
import org.eclipse.xtext.resource.IEObjectDescription
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider
import org.eclipse.xtext.scoping.IScope
import org.eclipse.xtext.scoping.IScopeProvider
import org.eclipse.xtext.scoping.Scopes
import org.eclipse.xtext.scoping.impl.AbstractScopeProvider
import org.eclipse.xtext.scoping.impl.IDelegatingScopeProvider
import org.eclipse.xtext.scoping.impl.SimpleScope
import org.eclipse.xtext.util.IResourceScopeCache

import static extension org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.*
import static extension org.eclipse.n4js.utils.N4JSLanguageUtils.*

/**
 * This class contains custom scoping description.
 *
 * Although some methods are called according to declarative scope provider, no reflection in
 * {@link #getScope(EObject,EReference)}.
 *
 * see : http://www.eclipse.org/Xtext/documentation/latest/xtext.html#scoping
 * on how and when to use it
 */
class N4JSScopeProvider extends AbstractScopeProvider implements IDelegatingScopeProvider, IContentAssistScopeProvider {


	public final static String NAMED_DELEGATE = "org.eclipse.xtext.scoping.impl.AbstractDeclarativeScopeProvider.delegate";

	@Inject
	IResourceScopeCache cache

	/* The scope provider creating the "parent" scope, i.e. including elements from the index */
	@Inject
	@Named(NAMED_DELEGATE)
	IScopeProvider delegate;

	@Inject private IN4JSCore n4jsCore;

	@Inject
	ResourceDescriptionsProvider resourceDescriptionsProvider;

	/* Poor mans filter to reduce number of elements in getAllElements */
	@Inject extension N4JSTypesScopeFilter

	@Inject N4JSTypeSystem ts

	@Inject MemberScopingHelper memberScopingHelper

	@Inject extension LocallyKnownTypesScopingHelper locallyKnownTypesScopingHelper

	@Inject extension ImportedElementsScopingHelper

	@Inject extension SourceElementExtensions

	@Inject EObjectDescriptionHelper descriptionsHelper;

	@Inject extension ReactHelper;

	@Inject JavaScriptVariantHelper jsVariantHelper;

	@Inject MemberVisibilityChecker checker;

	@Inject ContainerTypesHelper containerTypesHelper;

	@Inject TopLevelElementsCollector topLevelElementCollector

	@Inject ScopesHelper scopesHelper

	@Inject private VersionHelper versionHelper;

	@Inject private ValidatorMessageHelper messageHelper;

	@Inject private MigrationScopeHelper migrationScopeHelper;
	
	/** True: Proxies of IdentifierRefs are only resolved within the resource. Otherwise, the proxy is returned. */
	private boolean suppressCrossFileResolutionOfIdentifierRef = false;
	
	public def TameAutoClosable newCrossFileResolutionSuppressor() {
		val TameAutoClosable tac =  new TameAutoClosable() {
			private boolean tmpSuppressCrossFileResolutionOfIdentifierRef = init();
			private def boolean init() {
				this.tmpSuppressCrossFileResolutionOfIdentifierRef = suppressCrossFileResolutionOfIdentifierRef;
				suppressCrossFileResolutionOfIdentifierRef = true;
			}	
			override close() {
				suppressCrossFileResolutionOfIdentifierRef = tmpSuppressCrossFileResolutionOfIdentifierRef;
			}
		};
		return tac;
	}

	protected def IScope delegateGetScope(EObject context, EReference reference) {
		return delegate.getScope(context, reference)
	}

	override IScopeProvider getDelegate() {
		return delegate;
	}

	/** Dispatches to concrete scopes based on the context and reference inspection */
	override getScope(EObject context, EReference reference) {
		try {
			// dispatch based on language variant
			val resourceType = ResourceType.getResourceType(context)
			switch (resourceType) {
				case ResourceType.N4JSX	: return getN4JSXScope(context, reference)
				case ResourceType.JSX	: return getN4JSXScope(context, reference)
				case ResourceType.N4IDL : return getN4IDLScope(context, reference)
				default					: return getN4JSScope(context, reference)
			}

		} catch (Error ex) {
			if (context !== null && context.eResource.errors.empty) {
				throw ex;
			} else {
				// swallow exception, we got a parse error anyway
			}
		}

		return IScope.NULLSCOPE;
	}

	/**
	 * Returns the N4JS scope for the given context and reference.
	 *
	 * The returned scope is not sensitive to any of the language variants of N4JS. In order
	 * to obtain a variant-specific scope, please use {@link N4JSScopeProvider#getScope(EObject, EReference)}.
	 */
	public def getN4JSScope(EObject context, EReference reference) {
		// maybe can use scope shortcut
		val maybeScopeShortcut = getScopeByShortcut(context, reference);
		if (maybeScopeShortcut !== IScope.NULLSCOPE)
			return maybeScopeShortcut;

		// otherwise use context:
		return getScopeForContext(context, reference)
	}

	/** shortcut to concrete scopes based on reference sniffing. Will return {@link IScope#NULLSCOPE} if no suitable scope found */
	private def getScopeByShortcut(EObject context, EReference reference) {
		if (reference == TypeRefsPackage.Literals.PARAMETERIZED_TYPE_REF__DECLARED_TYPE) {
			if (context instanceof ParameterizedTypeRef) {
				val namespace = context.astNamespace;
				if (namespace!==null) {
					return createScopeForNamespaceAccess(namespace, context);
				}
			}
		}
		if (reference == TypeRefsPackage.Literals.PARAMETERIZED_TYPE_REF__DECLARED_TYPE
			|| reference == TypeRefsPackage.Literals.PARAMETERIZED_TYPE_REF__AST_NAMESPACE) {
			return new ValidatingScope(getTypeScope(context, false),
				context.getTypesFilterCriteria(reference));
		}

		if (reference.EReferenceType == N4JSPackage.Literals.LABELLED_STATEMENT) {
			return scope_LabelledStatement(context);
		}

		return IScope.NULLSCOPE;
	}

	/** dispatch to internal methods based on the context */
	private def getScopeForContext(EObject context, EReference reference) {
		switch (context) {
			ImportDeclaration						: return scope_ImportedModule(context, reference)
			NamedImportSpecifier					: return scope_ImportedElement(context, reference)
			IdentifierRef							: return scope_IdentifierRef_id(context, reference)
			ParameterizedPropertyAccessExpression	: return scope_PropertyAccessExpression_property(context, reference)
			N4FieldAccessor							: return Scopes.scopeFor(EcoreUtil2.getContainerOfType(context, N4ClassifierDefinition).ownedFields)
			default									: return IScope.NULLSCOPE
		}
	}

	override getScopeForContentAssist(EObject context, EReference reference) {

		val scope = getScope(context, reference);

		if (scope === IScope.NULLSCOPE) {
			// used for type references in JSDoc (see JSDocCompletionProposalComputer):
			if (reference == N4JSPackage.Literals.IMPORT_DECLARATION__MODULE) {
				return scope_ImportedAndCurrentModule(context, reference);
			}

			// the following cases are only required for content assist (i.e. scoping on broken ASTs or at unusual
			// locations) otherwise use context:
			switch (context) {
				Script:
					return scope_EObject_id(context, reference)
				N4TypeDeclaration:
					return scope_EObject_id(context, reference)
				VariableDeclaration:
					return scope_EObject_id(context, reference)
				Statement:
					return scope_EObject_id(context, reference)
				NewExpression:
					return scope_EObject_id(context, reference)
				ParameterizedCallExpression:
					return scope_EObject_id(context, reference)
				Argument:
					return scope_EObject_id(context, reference)
				Expression:
					return scope_EObject_id(context, reference)
				LiteralOrComputedPropertyName:
					return scope_EObject_id(context, reference)
			}
		}
		return scope;
	}

	/**
	 * Returns a scope as created by {@link #getScope(EObject, EReference)} for the 'from' part of an import declaration
	 * in the AST, but without the need for providing any AST nodes. This can be used to implement implicit imports
	 * without duplicating any logic from the scoping.
	 * <p>
	 * There are two minor differences to the scope created by {@code #getScope()}:
	 * <ol>
	 * <li>the current module, i.e. the module represented by the given resource, won't be filtered out, and
	 * <li>advanced error reporting will be disabled, i.e. {@link IScope#getSingleElement(QualifiedName)} will simply
	 *     return <code>null</code> instead of returning an {@code IEObjectDescriptionWithError}.
	 * </ol>
	 */
	public def IScope getScopeForImplicitImports(N4JSResource resource) {
		return scope_ImportedModule(resource, Optional.absent());
	}

	/**
	 * In <pre>continue XYZ</pre>, bind XYZ to label.
	 * Bind to ALL labels in script, although not all of them are valid. Later is to be validated in ASTStructureValidator and
	 * allows for better error and quick fix handling. However, most inner (and correct) scope is preferred (solving problems in case of duplicate names).
	 */
	private def IScope scope_LabelledStatement(EObject context) {
		val parent = (EcoreUtil.getRootContainer(context) as Script).allLabels;
		val names = newHashSet;
		val elements = newArrayList;
		var current = context;
		while (current !== null) {
			switch (current) {
				LabelledStatement:
					if (names.add(current.name)) {
						elements += EObjectDescription.create(current.name, current);
					}
			}
			current = current.eContainer; // labeled statement must be a container
		}
		if (elements.empty)
			return parent;
		val result = new SimpleScope(parent, elements);
		return result;
	}

	private def IScope getAllLabels(Script script) {
		return Scopes.scopeFor(script.eAllContents.filter(LabelledStatement).toIterable);
	}

	/**
	 * E.g. in
	 * <pre>import { e1,e2 } from "a/b/importedModule"</pre> bind "a/b/importedModule" to module with qualified name "a.b.importedModule"
	 *
	 * @param importDeclaration usually of type ImportDeclaratoin in case of N4JS bindings, but maybe used in JSDoc as well
	 */
	private def IScope scope_ImportedModule(ImportDeclaration importDeclaration, EReference reference) {

		val resource = importDeclaration.eResource as N4JSResource;
		val projectImportEnabledScope = scope_ImportedModule(resource, Optional.of(importDeclaration));

		// filter out clashing module name (can be main module with the same name but in different project)
		return new FilteringScope(projectImportEnabledScope, [
			if (it === null) false else !descriptionsHelper.isDescriptionOfModuleWith(it, importDeclaration);
		]);
	}

	private def IScope scope_ImportedModule(N4JSResource resource, Optional<ImportDeclaration> importDeclaration) {

		val reference = N4JSPackage.eINSTANCE.importDeclaration_Module;

		val initialScope = scope_ImportedAndCurrentModule(resource.script, reference);

		val resourceDescriptions = resourceDescriptionsProvider.getResourceDescriptions(resource);
		val delegateMainModuleAwareScope = MainModuleAwareSelectableBasedScope.createMainModuleAwareScope(initialScope,
			resourceDescriptions, reference.EReferenceType);

		val projectImportEnabledScope = ProjectImportEnablingScope.create(n4jsCore, resource, importDeclaration,
			initialScope, delegateMainModuleAwareScope);

		return projectImportEnabledScope;
	}

	/**
	 * Same as {@link #scope_ImportedModule(EObject,EReference)}, but also includes the current module.
	 */
	private def IScope scope_ImportedAndCurrentModule(EObject importDeclaration, EReference reference) {
		return delegateGetScope(importDeclaration, reference);
	}

	/**
	 *
	 * E.g. in
	 * <pre>import { e1,e2 } from "importedModule"</pre>
	 * bind e1 or e2 by retrieving all (not only exported, see below!) top level elements of
	 * importedModule (variables, types; functions are types!). All elements enables better error handling and quick fixes, as links are not broken.
	 */
	protected def IScope scope_ImportedElement(NamedImportSpecifier specifier, EReference reference) {
		val declaration = EcoreUtil2.getContainerOfType(specifier, ImportDeclaration);
		return scope_AllTopLevelElementsFromModule(declaration.module, declaration);
	}

	/**
	 * Called from getScope(), binds an identifier reference.
	 */
	private def IScope scope_IdentifierRef_id(IdentifierRef identifierRef, EReference ref) {
		val VariableEnvironmentElement vee = identifierRef.ancestor(VariableEnvironmentElement);
		val scope = getLexicalEnvironmentScope(vee, identifierRef, ref);
		// Handle constructor visibility
		if (identifierRef.eContainer instanceof NewExpression) {
			val newExpr = identifierRef.eContainer as NewExpression
			val vacs = new VisibilityAwareCtorScope(scope, checker, containerTypesHelper, newExpr);
			return vacs;
		}
		return scope;
	}

	/**
	 * Only used for content assist. Modeled after method {@link #scope_IdentifierRef_id(IdentifierRef,EReference)}.
	 */
	private def IScope scope_EObject_id(EObject obj, EReference ref) {
		val VariableEnvironmentElement vee = if (obj instanceof VariableEnvironmentElement)
				obj
			else
				obj.ancestor(VariableEnvironmentElement);
		return getLexicalEnvironmentScope(vee, obj, ref);
	}

	private def IScope getLexicalEnvironmentScope(VariableEnvironmentElement vee, EObject context, EReference ref) {
		if (vee === null) {
			return IScope.NULLSCOPE;
		}

		// TODO parent vee-s should be cached as well
		return cache.get('scope_IdentifierRef_id' -> vee,
			vee.eResource, [| return buildLexicalEnvironmentScope(vee, context, ref)]
		);
	}

	/**
	 * Builds a lexical environment scope with the given parameters.
	 * Filters out primitive types.
	 */
	private def IScope buildLexicalEnvironmentScope(VariableEnvironmentElement vee, EObject context, EReference reference) {
		val scopeLists = newArrayList;
		// variables declared in module
		collectLexialEnvironmentsScopeLists(vee, scopeLists);

		var IScope scope;
		if (suppressCrossFileResolutionOfIdentifierRef) {
			// Suppressing cross-file resolution is necessary for the CFG/DFG analysis
			// triggered in N4JSPostProcessor#postProcessN4JSResource(...).
			scope = IScope.NULLSCOPE;
		} else {
			val Script script = EcoreUtil.getRootContainer(vee) as Script;
			val IScope baseScope = script.getScriptBaseScope(context, reference);
			// imported variables (added as second step to enable shadowing of imported elements)
			scope = getImportedIdentifiables(baseScope, script);
		}

		for (scopeList : scopeLists.reverseView) {
			scope = scopesHelper.mapBasedScopeFor(context, scope, scopeList);
		}
		return scope;
	}

	private def IScope getScriptBaseScope(Script script, EObject context, EReference ref) {
		// IDE-1065: there may be user declared globals (i.e. @@Global)
		val IScope globalScope = delegate.getScope(script, ref);

		if (jsVariantHelper.activateDynamicPseudoScope(context)) { // cf. sec. 13.1
			return new DynamicPseudoScope(globalScope);
		}
		return globalScope;
	}

	def private List<Iterable<IEObjectDescription>> collectLexialEnvironmentsScopeLists(VariableEnvironmentElement vee,
		List<Iterable<IEObjectDescription>> result) {

		result.add(Scopes.scopedElementsFor(vee.collectVisibleIdentifiableElements));

		// arguments must be in own outer scope in order to enable shadowing of inner variables named "arguments"
		result.add(Scopes.scopedElementsFor(vee.collectLocalArguments));

		val parent = vee.ancestor(VariableEnvironmentElement);
		if (parent !== null) {
			collectLexialEnvironmentsScopeLists(parent, result);
		}
	}

	/**
	 * Creates IScope with all top level elements (variables and types, including functions), from target module.
	 * Provided resource is used to check visibility of module elements. Not visible elements are imported too, which
	 * allows better error handling and quick fixes, as links are not broken.
	 *
	 * Used for elements imported with named import and to access elements vi namespace import.
	 *
	 * @param importedModule target {@link TModule} from which elements are imported
	 * @param contextResource Receiver context {@link EObject} which is importing elements
	 */
	private def IScope scope_AllTopLevelElementsFromModule(TModule importedModule, EObject context) {
		if (importedModule === null) {
			return IScope.NULLSCOPE;
		}
		
		// get regular top-level elements scope
		val topLevelElementsScope = scopesHelper.mapBasedScopeFor(importedModule, IScope.NULLSCOPE,
			topLevelElementCollector.getTopLevelElements(importedModule, context.eResource));
		
		// if the context resource does not allow for versioned types but the imported module does...
		if (!jsVariantHelper.allowVersionedTypes(context) && jsVariantHelper.allowVersionedTypes(importedModule)) {
			// ...make sure that all results are validated according to @VersionAware reference constraints
			return new NonVersionAwareContextScope(topLevelElementsScope, false, messageHelper);
		}
		
		return topLevelElementsScope;
	}

	/*
	 * In <pre>receiver.property</pre>, binds "property".
	 *
	 */
	private def IScope scope_PropertyAccessExpression_property(ParameterizedPropertyAccessExpression propertyAccess,
		EReference ref) {
		val Expression receiver = propertyAccess.target;

		// if accessing namespace import
		if (receiver instanceof IdentifierRef) {
			val id = receiver.id;
			if (id instanceof ModuleNamespaceVirtualType) {
				return createScopeForNamespaceAccess(id, propertyAccess);
			}
		}

		val G = propertyAccess.newRuleEnvironment;
		val TypeRef typeRefRaw = ts.type(G, receiver);
		// take upper bound to get rid of ExistentialTypeRefs, ThisTypeRefs, etc.
		val TypeRef typeRef = ts.upperBound(G, typeRefRaw);

		val staticAccess = typeRef instanceof TypeTypeRef;
		val structFieldInitMode = typeRef.typingStrategy === TypingStrategy.STRUCTURAL_FIELD_INITIALIZER;
		val checkVisibility = true;
		return memberScopingHelper.createMemberScope(typeRef, propertyAccess, checkVisibility, staticAccess, structFieldInitMode);
	}

	private def IScope createScopeForNamespaceAccess(ModuleNamespaceVirtualType namespace, EObject context) {
		val module = namespace.module;
		val result = if (module !== null && !module.eIsProxy) {
				scope_AllTopLevelElementsFromModule(module, context)
			} else {
				// error cases
				if (namespace.eIsProxy) {
					// name space does not exist -> avoid duplicate error messages
					// (cf. MemberScopingHelper#members(UnknownTypeRef, MemberScopeRequest))
					new DynamicPseudoScope()
				} else {
					// name space exists, but imported module does not -> show additional error at location of reference
					IScope.NULLSCOPE
				}
			};
		if (namespace.declaredDynamic && !(result instanceof DynamicPseudoScope)) {
			return new DynamicPseudoScope(result);
		}
		return result;
	}

	/**
	 * Is entered (and later recursively called) to initially bind "T" in <pre>var x : T;</pre> or other parameterized type references.
	 */
	def public IScope getTypeScope(EObject context, boolean fromStaticContext) {
		switch context {
			Script: {
				return locallyKnownTypesScopingHelper.scopeWithLocallyKnownTypes(context, delegate);
			}
			TModule: {
				return locallyKnownTypesScopingHelper.scopeWithLocallyKnownTypes(context.astElement as Script, delegate);
			}
			N4FieldDeclaration: {
				val isStaticContext = context.static;
				return getTypeScope(context.eContainer, isStaticContext); // use new static access status for parent scope
			}
			N4FieldAccessor: {
				val isStaticContext = context.static;
				return getTypeScope(context.eContainer, isStaticContext); // use new static access status for parent scope
			}
			TypeDefiningElement: {
				val isStaticContext = context instanceof N4MemberDeclaration && (context as N4MemberDeclaration).static;
				val IScope parent = getTypeScope(context.eContainer, isStaticContext); // use new static access status for parent scope
				val polyfilledOrOriginalType = context.getTypeOrPolyfilledType();

				return scopeWithTypeAndItsTypeVariables(parent, polyfilledOrOriginalType, fromStaticContext); // use old static access status for current scope
			}
			TStructMethod: {
				val parent = getTypeScope(context.eContainer, fromStaticContext);
				return scopeWithTypeVarsOfTStructMethod(parent, context);
			}
			FunctionTypeExpression: {
				val parent = getTypeScope(context.eContainer, fromStaticContext);
				return scopeWithTypeVarsOfFunctionTypeExpression(parent, context);
			}
			TypeDefs: {
				// This case applies when a scope for the built-in type definition
				// files (builtin_n4.n4ts) is requested. This can simply be handled by a BuiltInTypeScope.
				return BuiltInTypeScope.get(context.eResource.resourceSet);
			}
			default: {
				val container = context.eContainer;

				// handle special areas inside a polyfill that should *not* get the usual polyfill handling implemented
				// in the above case for "TypeDefiningElement":
				if (container instanceof N4ClassDeclaration) {
					if (container.isPolyfill || container.isStaticPolyfill) {
						if (container.typeVars.contains(context)) {
							// area #1: upper/lower bound of type parameter of polyfill, e.g. the 2nd 'T' in:
							// @@StaticPolyfillModule
							// @StaticPolyfill export public class ToBeFilled<T,S extends T> extends ToBeFilled<T,S> {}
							val IScope parent = getTypeScope(context.eContainer, false);
							return scopeWithTypeAndItsTypeVariables(parent, container.definedType, fromStaticContext);
						} else if (container.superClassRef === context) {
							// area #2: super type reference of polyfill, e.g. everything after 'extends' in:
							// @@StaticPolyfillModule
							// @StaticPolyfill export public class ToBeFilled<T> extends ToBeFilled<T> {}
							val script = EcoreUtil2.getContainerOfType(container, Script);
							val parent = scopeWithLocallyKnownTypesForPolyfillSuperRef(script, delegate,
								container.definedType);
							return parent;
						}
					}
				}

				return getTypeScope(container, fromStaticContext);
			}
		}
	}

	/**
	 * Returns ancestor of given type, or null, if no such container exists. Note that this method is slightly different from
	 * EcoreUtil2::getContainerOfType, as it only returns a container and not the element itself. That is, ancestor is not reflexive here.
	 */
	private def <T extends EObject> T ancestor(EObject obj, Class<T> ancestorType) {
		if (obj === null) return null;
		return EcoreUtil2.getContainerOfType(obj.eContainer, ancestorType);
	}

	private def getN4IDLScope(EObject context, EReference reference) {
		// get a scope filtering by the context version as specified by {@param context}.
		val contextVersionScope = getN4IDLContextVersionScope(context, reference);
		
		// Make sure that references to version-aware contexts, from non-version-aware contexts
		// are detected and prevented.
		if (!VersionUtils.isVersionAwareContext(context)) {
			return new NonVersionAwareContextScope(contextVersionScope, true, messageHelper);
		} else {
			// detect whether this scope is lexically contained by a migration declaration
			val migrationDeclaration = MigrationUtils.getMigrationDeclaration(context)
			if (migrationDeclaration.present) {
				// if the context is a 'migrate' calls
				if (context instanceof IdentifierRef && MigrationUtils.isMigrateCallIdentifier(context as IdentifierRef)) {
					// provide an argument-sensitive migration scope 
					val callExpression = context.eContainer as ParameterizedCallExpression;
					return migrationScopeHelper.migrationsScope(callExpression.arguments, context);
				} else { // otherwise make sure to include the MigrationContext 'context' identifier
					return migrationScopeHelper.migrationContextAwareScope(migrationDeclaration.get(), contextVersionScope);
				}
			}
		}
		
		return contextVersionScope;
	}
	
	/**
	 * Returns a version-aware scope based on the context version that is specified in {@param context}.
	 * 
	 * If no context version can be inferred from {@param context}, all versionable results will 
	 * be wrapped in a {@link FailedToInferContextVersionWrappingScope}.
	 */
	private def getN4IDLContextVersionScope(EObject context, EReference reference) {
		val IScope scope = getN4JSScope(context, reference);

		// If the N4JS scope is a NULLSCOPE there
		// is nothing to filter for a context version.
		if (scope == IScope.NULLSCOPE) {
			return scope;
		}

		if (reference === TypeRefsPackage.Literals.PARAMETERIZED_TYPE_REF__DECLARED_TYPE ||
			reference === N4JSPackage.Literals.IDENTIFIER_REF__ID
		) {
			val contextVersion = versionHelper.computeMaximumVersion(context);
			val versionAwareScope = new N4IDLVersionAwareScope(scope, contextVersion.or(Integer.MAX_VALUE));

			if (contextVersion.present) {
				return versionAwareScope;
			} else {
				// If the context version cannot be determined, wrap all results in a
				// corresponding ({@link FailedToInferContextVersionDescription}).
				return new FailedToInferContextVersionWrappingScope(scope)
			}
		}

		return scope;
	}

	private def getN4JSXScope(EObject context, EReference reference) {
		val jsxPropertyAttributeScope = getJSXPropertyAttributeScope(context, reference)
		if(jsxPropertyAttributeScope !== IScope.NULLSCOPE)
			return jsxPropertyAttributeScope

		val jsxElementScope = getJSXElementScope(context, reference)
		if(jsxElementScope !== IScope.NULLSCOPE)
			return jsxElementScope

		//delegate to host -> N4JS scope
		return getN4JSScope(context, reference);
	}
	/** Returns scope for the {@link JSXPropertyAttribute} (obtained from context) or {@link IScope#NULLSCOPE} */
	private def getJSXPropertyAttributeScope(EObject context, EReference reference) {
		if (reference == N4JSPackage.Literals.JSX_PROPERTY_ATTRIBUTE__PROPERTY) {
			if (context instanceof JSXPropertyAttribute) {
				val jsxElem = (context.eContainer as JSXElement);
				val TypeRef propsTypeRef = jsxElem.getPropsType();
				val checkVisibility = true;
				val staticAccess = false;
				val structFieldInitMode = false;
				if (propsTypeRef !== null) {
					// Prevent "Cannot resolve to element" error message of unknown attributes since
					// we want to issue a warning instead
					val memberScope = memberScopingHelper.createMemberScope(propsTypeRef, context, checkVisibility,
						staticAccess, structFieldInitMode);
					return new DynamicPseudoScope(memberScope);
				} else {
					val scope = getN4JSScope(context, reference);
					return new DynamicPseudoScope(scope);
				}
			}
		}
		return IScope.NULLSCOPE;
	}

	/** Returns scope for the JSXElement (obtained from context) or {@link IScope#NULLSCOPE} */
	private def getJSXElementScope(EObject context, EReference reference) {

		if(EcoreUtil2.getContainerOfType(context, JSXElementName) === null)
			return IScope.NULLSCOPE;

		val scope = getN4JSScope(context, reference)
		return new DynamicPseudoScope(scope);
	}
}
