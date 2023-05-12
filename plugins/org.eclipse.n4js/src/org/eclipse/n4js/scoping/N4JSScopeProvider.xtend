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
import com.google.inject.Singleton
import com.google.inject.name.Named
import java.util.Collections
import java.util.Comparator
import java.util.Iterator
import java.util.List
import java.util.concurrent.atomic.AtomicBoolean
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.InternalEObject
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.n4js.n4JS.Argument
import org.eclipse.n4js.n4JS.ArrayBindingPattern
import org.eclipse.n4js.n4JS.BindingElement
import org.eclipse.n4js.n4JS.BindingProperty
import org.eclipse.n4js.n4JS.DestructNode
import org.eclipse.n4js.n4JS.DestructureUtils
import org.eclipse.n4js.n4JS.ExportDeclaration
import org.eclipse.n4js.n4JS.Expression
import org.eclipse.n4js.n4JS.IdentifierRef
import org.eclipse.n4js.n4JS.ImportDeclaration
import org.eclipse.n4js.n4JS.JSXElement
import org.eclipse.n4js.n4JS.JSXElementName
import org.eclipse.n4js.n4JS.JSXPropertyAttribute
import org.eclipse.n4js.n4JS.LabelledStatement
import org.eclipse.n4js.n4JS.LiteralOrComputedPropertyName
import org.eclipse.n4js.n4JS.MemberAccess
import org.eclipse.n4js.n4JS.ModuleRef
import org.eclipse.n4js.n4JS.N4ClassifierDeclaration
import org.eclipse.n4js.n4JS.N4ClassifierDefinition
import org.eclipse.n4js.n4JS.N4FieldAccessor
import org.eclipse.n4js.n4JS.N4FieldDeclaration
import org.eclipse.n4js.n4JS.N4JSPackage
import org.eclipse.n4js.n4JS.N4MemberDeclaration
import org.eclipse.n4js.n4JS.N4NamespaceDeclaration
import org.eclipse.n4js.n4JS.N4TypeDeclaration
import org.eclipse.n4js.n4JS.NamedExportSpecifier
import org.eclipse.n4js.n4JS.NamedImportSpecifier
import org.eclipse.n4js.n4JS.NewExpression
import org.eclipse.n4js.n4JS.ParameterizedCallExpression
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.n4JS.Statement
import org.eclipse.n4js.n4JS.TypeDefiningElement
import org.eclipse.n4js.n4JS.VariableDeclaration
import org.eclipse.n4js.n4JS.VariableEnvironmentElement
import org.eclipse.n4js.resource.N4JSCache
import org.eclipse.n4js.resource.N4JSResource
import org.eclipse.n4js.scoping.accessModifiers.ContextAwareTypeScope
import org.eclipse.n4js.scoping.accessModifiers.MemberVisibilityChecker
import org.eclipse.n4js.scoping.imports.ImportedElementsScopingHelper
import org.eclipse.n4js.scoping.imports.N4JSImportedNamespaceAwareLocalScopeProvider
import org.eclipse.n4js.scoping.members.MemberScopingHelper
import org.eclipse.n4js.scoping.utils.DynamicPseudoScope
import org.eclipse.n4js.scoping.utils.LocallyKnownTypesScopingHelper
import org.eclipse.n4js.scoping.utils.MainModuleAwareSelectableBasedScope
import org.eclipse.n4js.scoping.utils.MergedScope
import org.eclipse.n4js.scoping.utils.ProjectImportEnablingScope
import org.eclipse.n4js.scoping.utils.ScopeSnapshotHelper
import org.eclipse.n4js.scoping.utils.SourceElementExtensions
import org.eclipse.n4js.scoping.utils.UberParentScope
import org.eclipse.n4js.scoping.validation.ContextAwareTypeScopeValidator
import org.eclipse.n4js.scoping.validation.ScopeInfo
import org.eclipse.n4js.scoping.validation.VeeScopeValidator
import org.eclipse.n4js.scoping.validation.VisibilityAwareCtorScopeValidator
import org.eclipse.n4js.tooling.react.ReactHelper
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression
import org.eclipse.n4js.ts.typeRefs.NamespaceLikeRef
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage
import org.eclipse.n4js.ts.typeRefs.TypeTypeRef
import org.eclipse.n4js.ts.types.AbstractNamespace
import org.eclipse.n4js.ts.types.ModuleNamespaceVirtualType
import org.eclipse.n4js.ts.types.TClass
import org.eclipse.n4js.ts.types.TEnum
import org.eclipse.n4js.ts.types.TFunction
import org.eclipse.n4js.ts.types.TModule
import org.eclipse.n4js.ts.types.TNamespace
import org.eclipse.n4js.ts.types.TStructMethod
import org.eclipse.n4js.ts.types.Type
import org.eclipse.n4js.ts.types.TypesPackage
import org.eclipse.n4js.ts.types.TypingStrategy
import org.eclipse.n4js.typesystem.N4JSTypeSystem
import org.eclipse.n4js.typesystem.utils.RuleEnvironment
import org.eclipse.n4js.typesystem.utils.TypeSystemHelper
import org.eclipse.n4js.utils.ContainerTypesHelper
import org.eclipse.n4js.utils.DeclMergingHelper
import org.eclipse.n4js.utils.DeclMergingUtils
import org.eclipse.n4js.utils.EObjectDescriptionHelper
import org.eclipse.n4js.utils.N4JSLanguageUtils
import org.eclipse.n4js.utils.ResourceType
import org.eclipse.n4js.utils.URIUtils
import org.eclipse.n4js.validation.JavaScriptVariantHelper
import org.eclipse.n4js.workspace.WorkspaceAccess
import org.eclipse.n4js.xtext.scoping.FilteringScope
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.xtext.resource.EObjectDescription
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider
import org.eclipse.xtext.scoping.IScope
import org.eclipse.xtext.scoping.IScopeProvider
import org.eclipse.xtext.scoping.impl.AbstractScopeProvider
import org.eclipse.xtext.scoping.impl.IDelegatingScopeProvider

import static extension org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.*

/**
 * This class contains custom scoping description.
 *
 * Although some methods are called according to declarative scope provider, no reflection in
 * {@link #getScope(EObject,EReference)}.
 *
 * see : http://www.eclipse.org/Xtext/documentation/latest/xtext.html#scoping
 * on how and when to use it
 */
@Singleton
class N4JSScopeProvider extends AbstractScopeProvider implements IDelegatingScopeProvider, IContentAssistScopeProvider {

	public final static String NAMED_DELEGATE = "org.eclipse.xtext.scoping.impl.AbstractDeclarativeScopeProvider.delegate";

	@Inject
	N4JSCache cache

	/**
	 * The scope provider creating the "parent" scope, i.e. including elements from the index.
	 * At runtime, the value will be of type {@link N4JSImportedNamespaceAwareLocalScopeProvider}.
	 */
	@Inject
	@Named(NAMED_DELEGATE)
	IScopeProvider delegate;

	@Inject WorkspaceAccess workspaceAccess;

	@Inject ResourceDescriptionsProvider resourceDescriptionsProvider;

	@Inject N4JSTypeSystem ts

	@Inject TypeSystemHelper tsh

	@Inject MemberScopingHelper memberScopingHelper

	@Inject LocallyKnownTypesScopingHelper locallyKnownTypesScopingHelper

	@Inject ImportedElementsScopingHelper importedElementsScopingHelper

	@Inject SourceElementExtensions sourceElementExtensions;

	@Inject EObjectDescriptionHelper descriptionsHelper;

	@Inject ReactHelper reactHelper;

	@Inject JavaScriptVariantHelper jsVariantHelper;

	@Inject MemberVisibilityChecker checker;

	@Inject ContainerTypesHelper containerTypesHelper;

	@Inject ExportedElementsCollector exportedElementCollector

	@Inject ScopeSnapshotHelper scopeSnapshotHelper

	@Inject DeclMergingHelper declMergingHelper;


	protected def boolean isSuppressCrossFileResolutionOfIdentifierRefs() {
		return false;
	}

	/**
	 * Delegates to {@link N4JSImportedNamespaceAwareLocalScopeProvider#getScope(EObject, EReference)}, which in turn
	 * delegates further to {@link N4JSGlobalScopeProvider}.
	 */
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
		if (reference == TypeRefsPackage.Literals.NAMESPACE_LIKE_REF__DECLARED_TYPE) {
			if (context instanceof NamespaceLikeRef) {
				val namespaceLikeType = context.previousSibling?.declaredType;
				val script = EcoreUtil2.getContainerOfType(context, Script);
				val parentNamespace = EcoreUtil2.getContainerOfType(context, N4NamespaceDeclaration);
				val parentContainer = parentNamespace === null ? script : parentNamespace;
				// also check for eIsProxy in case of broken AST
				val namespace = namespaceLikeType === null || namespaceLikeType.eIsProxy ? parentContainer : namespaceLikeType;
				return new FilteringScope(getTypeScope(namespace, false, true), [
					TypesPackage.Literals.MODULE_NAMESPACE_VIRTUAL_TYPE.isSuperTypeOf(it.getEClass)
					|| TypesPackage.Literals.TCLASS.isSuperTypeOf(it.getEClass) // only included, because classes might have a namespace merged onto them!
					|| TypesPackage.Literals.TENUM.isSuperTypeOf(it.getEClass)
					|| TypesPackage.Literals.TNAMESPACE.isSuperTypeOf(it.getEClass)
				]);
			}
		} else if (reference == TypeRefsPackage.Literals.PARAMETERIZED_TYPE_REF__DECLARED_TYPE) {
			if (context instanceof ParameterizedTypeRef) {
				val namespaceLikeType = context.astNamespaceLikeRefs?.last?.declaredType;
				switch (namespaceLikeType) {
					ModuleNamespaceVirtualType:
						return createScopeForNamespaceAccess(namespaceLikeType, context, true, false)
					TClass:
						return createScopeForMergedNamespaces(context, namespaceLikeType, IScope.NULLSCOPE)
					TEnum:
						return new DynamicPseudoScope()
					TNamespace:
						return scope_AllTopLevelElementsFromAbstractNamespace(namespaceLikeType, context, true, false)
				}
			}
			return getTypeScope(context, false, false);
		} else if (reference.EReferenceType == N4JSPackage.Literals.LABELLED_STATEMENT) {
			return scope_LabelledStatement(context);
		}
		return IScope.NULLSCOPE;
	}

	/** dispatch to internal methods based on the context */
	private def getScopeForContext(EObject context, EReference reference) {
		switch (context) {
			ImportDeclaration						: return scope_ImportedModule(context, reference)
			NamedImportSpecifier					: return scope_ImportedElement(context, reference)
			ExportDeclaration						: return scope_ImportedModule(context, reference)
			IdentifierRef							: return scope_IdentifierRef_id(context, reference)
			BindingProperty							: return scope_BindingProperty_property(context, reference)
			ParameterizedPropertyAccessExpression	: return scope_PropertyAccessExpression_property(context, reference)
			N4FieldAccessor							: {
				val container = EcoreUtil2.getContainerOfType(context, N4ClassifierDefinition);
				return scopeSnapshotHelper.scopeForEObjects("N4FieldAccessor", container, container.ownedFields);
			}
			default									: return IScope.NULLSCOPE
		}
	}

	override getScopeForContentAssist(EObject context, EReference reference) {
		val scope = getScope(context, reference);

		if (scope === IScope.NULLSCOPE) {
			// used for type references in JSDoc (see JSDocCompletionProposalComputer):
			if (reference == N4JSPackage.Literals.MODULE_REF__MODULE) {
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
		val parent = getAllLabels(EcoreUtil.getRootContainer(context) as Script);
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
		if (elements.empty) {
			return parent;
		}
		val result = scopeSnapshotHelper.scopeFor("contextLabels", current, parent, elements);
		return result;
	}

	private def IScope getAllLabels(Script script) {
		return scopeSnapshotHelper.scopeForEObjects("allLabels", script, script.eAllContents.filter(LabelledStatement).toIterable);
	}

	/**
	 * E.g. in
	 * <pre>import { e1,e2 } from "a/b/importedModule"</pre> bind "a/b/importedModule" to module with qualified name "a.b.importedModule"
	 */
	private def IScope scope_ImportedModule(ModuleRef importOrExportDecl, EReference reference) {

		val resource = importOrExportDecl.eResource as N4JSResource;
		val projectImportEnabledScope = scope_ImportedModule(resource, Optional.of(importOrExportDecl));

		// filter out clashing module name (can be main module with the same name but in different project)
		return new FilteringScope(projectImportEnabledScope, [
			if (it === null) false else !descriptionsHelper.isDescriptionOfModuleWith(resource, it, importOrExportDecl);
		]);
	}

	private def IScope scope_ImportedModule(N4JSResource resource, Optional<ModuleRef> importOrExportDecl) {

		val reference = N4JSPackage.eINSTANCE.moduleRef_Module;

		val initialScope = scope_ImportedAndCurrentModule(resource.script, reference);

		val resourceDescriptions = resourceDescriptionsProvider.getResourceDescriptions(resource);
		val delegateMainModuleAwareScope = MainModuleAwareSelectableBasedScope.createMainModuleAwareScope(initialScope,
			resourceDescriptions, reference.EReferenceType);

		val ws = workspaceAccess.getWorkspaceConfig(resource);
		val projectImportEnabledScope = ProjectImportEnablingScope.create(ws, resource, importOrExportDecl,
			initialScope, delegateMainModuleAwareScope, declMergingHelper);

		return projectImportEnabledScope;
	}

	/**
	 * Same as {@link #scope_ImportedModule(EObject,EReference)}, but also includes the current module.
	 */
	private def IScope scope_ImportedAndCurrentModule(EObject importDeclaration, EReference reference) {
		return delegateGetScope(importDeclaration, reference);
	}

	/**
	 * E.g. in
	 * <pre>import { e1,e2 } from "importedModule"</pre>
	 * bind e1 or e2 by retrieving all (not only exported, see below!) top level elements of
	 * importedModule (variables, types; functions are types!). All elements enables better error handling and quick fixes, as links are not broken.
	 */
	protected def IScope scope_ImportedElement(NamedImportSpecifier specifier, EReference reference) {
		val impDecl = EcoreUtil2.getContainerOfType(specifier, ImportDeclaration);
		val targetModule = impDecl.module; // may trigger reentrant scoping for module specifier (cf. #scope_ImportedModule())
		return scope_AllTopLevelElementsFromAbstractNamespace(targetModule, impDecl, true, true);
	}

	/**
	 * E.g. in
	 * <pre>export { e1, e2 } from "importedModule"</pre>
	 * See {@link #scope_ImportedElement(NamedImportSpecifier, EReference)}.
	 */
	protected def IScope scope_ImportedElement(NamedExportSpecifier specifier, EReference reference) {
		val expDecl = EcoreUtil2.getContainerOfType(specifier, ExportDeclaration);
		val targetModule = expDecl.module; // may trigger reentrant scoping for module specifier (cf. #scope_ImportedModule())
		return scope_AllTopLevelElementsFromAbstractNamespace(targetModule, expDecl, true, true);
	}

	/**
	 * Called from getScope(), binds an identifier reference.
	 */
	private def IScope scope_IdentifierRef_id(IdentifierRef identifierRef, EReference ref) {
		val parent = identifierRef.eContainer;
		// handle re-exports
		if (parent instanceof NamedExportSpecifier) {
			val grandParent = parent.eContainer;
			if (grandParent instanceof ExportDeclaration) {
				if (grandParent.isReexport()) {
					if (suppressCrossFileResolutionOfIdentifierRefs) {
						return IScope.NULLSCOPE;
					}
					return scope_ImportedElement(parent, ref);
				}
			}
		}
		val VariableEnvironmentElement vee = ancestor(identifierRef, VariableEnvironmentElement);
		if (vee === null) {
			return IScope.NULLSCOPE;
		}
		val scope = getLexicalEnvironmentScope(vee, identifierRef, ref);
		// Handle constructor visibility
		if (parent instanceof NewExpression) {
			return scope.addValidator(new VisibilityAwareCtorScopeValidator(checker, containerTypesHelper, parent));
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
				ancestor(obj, VariableEnvironmentElement);

		if (vee === null) {
			return IScope.NULLSCOPE;
		}
		return getLexicalEnvironmentScope(vee, obj, ref);
	}

	private def ScopeInfo getLexicalEnvironmentScope(VariableEnvironmentElement vee, EObject context, EReference ref) {
		ensureLexicalEnvironmentScopes(context, ref);
		return cache.mustGet(vee.eResource, 'scope_IdentifierRef_id', vee);
	}


	private def void ensureLexicalEnvironmentScopes(EObject context, EReference reference) {
		val Script script = EcoreUtil.getRootContainer(context) as Script;
		val resource = script.eResource;
		val key = N4JSCache.makeKey('scope_IdentifierRef_id', script);
		val veeScopesBuilt = cache.contains(resource, key); // note that a script is a vee
		if (!veeScopesBuilt) {
			cache.get(resource, [buildLexicalEnvironmentScope(script, context, reference)], key);
			val Iterator<EObject> scriptIterator = script.eAllContents;
			while (scriptIterator.hasNext) {
				val vee = scriptIterator.next;
				if (vee instanceof VariableEnvironmentElement) {
					// fill the cache
					cache.get(resource, [buildLexicalEnvironmentScope(vee, context, reference)], 'scope_IdentifierRef_id', vee);
				}
			}
		}
	}

	/**
	 * Builds a lexical environment scope with the given parameters.
	 * Filters out primitive types.
	 */
	private def ScopeInfo buildLexicalEnvironmentScope(VariableEnvironmentElement vee, EObject context, EReference reference) {
		val scopeLists = newArrayList;
		// variables declared in module
		collectLexialEnvironmentsScopeLists(vee, scopeLists);

		var IScope scope;
		if (suppressCrossFileResolutionOfIdentifierRefs) {
			// Suppressing cross-file resolution is necessary for the CFG/DFG analysis
			// triggered in N4JSPostProcessor#postProcessN4JSResource(...).
			scope = IScope.NULLSCOPE;
		} else {
			val Script script = EcoreUtil.getRootContainer(vee) as Script;
			val resource = script.eResource;
			// TODO GH-2338 reconsider the following recursion guard (required for chains of re-exports in cyclic modules)
			val guard = cache.get(resource, [new AtomicBoolean(false)], "buildLexicalEnvironmentScope__importedValuesComputationGuard", script);
			val alreadyInProgress = guard.getAndSet(true);
			if (alreadyInProgress) {
				scope = IScope.NULLSCOPE;
			} else {
				try {
					val IScope baseScope = getScriptBaseScope(script, context, reference);
					// imported variables (added as second step to enable shadowing of imported elements)
					scope = importedElementsScopingHelper.getImportedValues(baseScope, script);
				} finally {
					guard.set(false);
				}
			}
		}


		scope = scopeSnapshotHelper.scopeForEObjects("buildLexicalEnvironmentScope", context, scope, false, scopeLists.flatten);

		val scopeInfo = new ScopeInfo(scope, scope, new VeeScopeValidator(context, jsVariantHelper));

		return scopeInfo;
	}

	private def IScope getScriptBaseScope(Script script, EObject context, EReference ref) {
		// IDE-1065: there may be user declared globals (i.e. @@Global)
		val IScope globalScope = delegateGetScope(script, ref);

		if (jsVariantHelper.activateDynamicPseudoScope(context)) { // cf. sec. 13.1
			return new DynamicPseudoScope(globalScope);
		}
		return globalScope;
	}

	def private void collectLexialEnvironmentsScopeLists(VariableEnvironmentElement vee,
			List<Iterable<? extends EObject>> result) {

		result.add(sourceElementExtensions.collectVisibleIdentifiableElements(vee));

		// implicit variable "arguments" must be in own outer scope in order to enable shadowing of inner variables named "arguments"
		result.add(sourceElementExtensions.collectLocalArguments(vee));

		val parent = ancestor(vee, VariableEnvironmentElement);
		if (parent !== null) {
			collectLexialEnvironmentsScopeLists(parent, result);
		}
	}

	/**
	 * Creates IScope with all top level elements (variables and types, including functions), from target module or namespace.
	 * Provided resource is used to check visibility of module elements. Not visible elements are imported too, which
	 * allows better error handling and quick fixes, as links are not broken.
	 *
	 * Used for elements imported with named import and to access elements via namespace import.
	 *
	 * @param importedModule target {@link TModule} from which elements are imported
	 * @param contextResource Receiver context {@link EObject} which is importing elements
	 */
	private def IScope scope_AllTopLevelElementsFromAbstractNamespace(AbstractNamespace ns, EObject context,
		boolean includeHollows, boolean includeValueOnlyElements) {

		return scope_AllTopLevelElementsFromAbstractNamespace(ns, context, IScope.NULLSCOPE, includeHollows, includeValueOnlyElements);
	}

	private def IScope scope_AllTopLevelElementsFromAbstractNamespace(AbstractNamespace ns, EObject context, IScope parentOrNull,
		boolean includeHollows, boolean includeValueOnlyElements) {

		if (ns === null) {
			return parentOrNull;
		}

		val resource = context.eResource;

		// TODO GH-2338 reconsider the following recursion guard (required for chains of re-exports in cyclic modules)
		val guard = cache.get(resource, [new AtomicBoolean(false)], "scope_AllTopLevelElementsFromAbstractNamespace__exportedElementsComputationGuard", context);
		val alreadyInProgress = guard.getAndSet(true);
		if (alreadyInProgress) {
			return parentOrNull;
		}
		try {
			// get regular top-level elements scope
			val memberAccess = if (context instanceof MemberAccess) Optional.of(context) else Optional.absent();
			val tlElems = exportedElementCollector.getExportedElements(ns, context.eResource as N4JSResource, memberAccess, includeHollows, includeValueOnlyElements);
			val topLevelElementsScope = scopeSnapshotHelper.scopeFor("scope_AllTopLevelElementsFromAbstractNamespace", ns, parentOrNull ?: IScope.NULLSCOPE, false, tlElems);
			return topLevelElementsScope;
		} finally {
			guard.set(false);
		}
	}


	/**
	 * Called from getScope(), binds a property reference.
	 */
	private def IScope scope_BindingProperty_property(BindingProperty bindingProperty, EReference ref) {
		var TypeRef cTypeRef = null;
		val destNodeTop = DestructNode.unify(DestructureUtils.getTop(bindingProperty));
		val parentDestNode = destNodeTop?.findNodeOrParentForElement(bindingProperty, true);
		if (parentDestNode !== null) {
			val G = newRuleEnvironment(bindingProperty);
			val parentAstElem = parentDestNode.astElement;
			if (parentAstElem instanceof BindingProperty) {
				if (parentAstElem.property !== null) {
					cTypeRef = ts.type(G, parentAstElem.property);
				}
			} else if (parentAstElem instanceof BindingElement && parentAstElem.eContainer instanceof ArrayBindingPattern) {
				val parent2DestNode = destNodeTop?.findNodeOrParentForElement(parentAstElem, true);
				if (parent2DestNode !== null) {
					var TypeRef arrayType = null;
					val parent2AstElem = parent2DestNode.astElement;
					if (parent2AstElem instanceof BindingProperty) {
						if (parent2AstElem.property !== null) {
							arrayType = ts.type(G, parent2AstElem.property);
						}
					} else {
						arrayType = ts.type(G, parent2DestNode.assignedElem);
					}
					
					val idx = parent2DestNode.nestedNodes.indexOf(parentDestNode);
					if (arrayType !== null && arrayType.typeArgsWithDefaults.size > idx && G.isIterableN(arrayType) && arrayType.eResource !== null) {
						val typeArg = arrayType.typeArgsWithDefaults.get(idx);
						if (typeArg instanceof TypeRef) {
							cTypeRef = typeArg;
						}
					}
				}
			} else {
				// fallback
				cTypeRef = ts.type(G, parentDestNode.assignedElem);
			}
			if (DestructureUtils.isTopOfDestructuringForStatement(parentDestNode.astElement) && cTypeRef.isArrayLike) {
				tsh.addSubstitutions(G, cTypeRef);
				cTypeRef = ts.substTypeVariables(G, cTypeRef.declaredType.elementType);
			}
		}
		
		if (cTypeRef !== null) {
			return new UberParentScope("scope_BindingProperty_property", createScopeForMemberAccess(cTypeRef, bindingProperty), new DynamicPseudoScope());
		}
		return new DynamicPseudoScope();
	}

	/*
	 * In <pre>receiver.property</pre>, binds "property".
	 *
	 */
	private def IScope scope_PropertyAccessExpression_property(ParameterizedPropertyAccessExpression propertyAccess, EReference ref) {
		val Expression receiver = propertyAccess.target;

		val G = propertyAccess.newRuleEnvironment;
		val TypeRef typeRefRaw = ts.type(G, receiver);
		// take upper bound to get rid of ExistentialTypeRefs, ThisTypeRefs, etc.
		// (literal types are handled in dispatch method #members() of MemberScopingHelper)
		val TypeRef typeRef = ts.upperBoundWithReopenAndResolveTypeVars(G, typeRefRaw);
		val declaredType = typeRef.declaredType;
		if (declaredType instanceof TNamespace) {
			return scope_AllTopLevelElementsFromAbstractNamespace(declaredType, propertyAccess, false, true);
		}
		if (declaredType instanceof ModuleNamespaceVirtualType) {
			return createScopeForNamespaceAccess(declaredType, propertyAccess, false, true);
		}

		var result = createScopeForMemberAccess(typeRef, propertyAccess);

		// functions and classes may have namespaces merged onto them
		result = handleDeclMergingForPropertyAccess(G, propertyAccess, typeRef, result);

		return result;
	}

	private def IScope createScopeForNamespaceAccess(ModuleNamespaceVirtualType namespace, EObject context,
		boolean includeHollows, boolean includeValueOnlyElements
	) {
		val module = namespace.module;

		val result = if (module !== null && !module.eIsProxy) {
				scope_AllTopLevelElementsFromAbstractNamespace(module, context, includeHollows, includeValueOnlyElements);
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

	private def IScope createScopeForMemberAccess(TypeRef targetTypeRef, EObject context) {
		val staticAccess = targetTypeRef instanceof TypeTypeRef;
		val structFieldInitMode = targetTypeRef.typingStrategy === TypingStrategy.STRUCTURAL_FIELD_INITIALIZER;
		val checkVisibility = true;
		val result = memberScopingHelper.createMemberScope(targetTypeRef, context, checkVisibility, staticAccess, structFieldInitMode);
		return result;
	}

	private def IScope handleDeclMergingForPropertyAccess(RuleEnvironment G, ParameterizedPropertyAccessExpression propertyAccess,
		TypeRef typeRef, IScope parent) {

		val staticAccess = typeRef instanceof TypeTypeRef;

		var Type mergeCandidate;
		if (staticAccess) {
			val staticType = tsh.getStaticType(G, typeRef as TypeTypeRef, true);
			if (staticType instanceof TClass) {
				mergeCandidate = staticType;
			}
		} else {
			val declaredType = typeRef.declaredType;
			if (declaredType instanceof TFunction) {
				mergeCandidate = declaredType;
			}
		}

		if (mergeCandidate !== null) {
			val scopeNamespace = createScopeForMergedNamespaces(propertyAccess, mergeCandidate, null);
			if (scopeNamespace !== null) {
				return new MergedScope(scopeNamespace, parent);
			}
		}

		return parent;
	}

	/** Returns <code>parentOrNull</code> unchanged if no namespaces are merged onto 'elem'. */
	private def IScope createScopeForMergedNamespaces(EObject context, Type elem, IScope parentOrNull) {
		var result = parentOrNull;
		if (DeclMergingUtils.mayBeMerged(elem)) {
			val mergedElems = declMergingHelper.getMergedElements(context.eResource as N4JSResource, elem);
			val mergedNamespaces = mergedElems.filter(TNamespace).toList;
			if (mergedNamespaces.size > 1) {
				Collections.sort(mergedNamespaces, Comparator.comparing([(it as InternalEObject).eProxyURI], new URIUtils.URIComparator()));
			}
			for (mergedNS : mergedNamespaces.reverseView) {
				if (mergedNS !== null) {
					result = scope_AllTopLevelElementsFromAbstractNamespace(mergedNS, context, result, false, true);
				}
			}
		}
		return result;
	}

	/** Returns <code>parentOrNull</code> unchanged if no TModules are merged onto 'script'. */
	private def IScope createScopeForMergedTModules(Script script, boolean onlyNamespaces, IScope parentOrNull) {
		if (!DeclMergingUtils.mayBeMerged(script)) {
			return parentOrNull;
		}
		var result = parentOrNull;
		val resource = script.eResource as N4JSResource;
		val mergedTModules = declMergingHelper.getMergedElements(resource, script.module); // can be empty
		if (mergedTModules.size > 1) {
			Collections.sort(mergedTModules, Comparator.comparing([(it as InternalEObject).eProxyURI], new URIUtils.URIComparator()));
		}
		for (mergedTModule : mergedTModules.reverseView) {
			if (mergedTModule !== null) {
				result = locallyKnownTypesScopingHelper.scopeWithLocallyDeclaredElems(mergedTModule, result, onlyNamespaces);
			}
		}
		return result;
	}

	/**
	 * Is entered to initially bind "T" in <pre>var x : T;</pre> or other parameterized type references.
	 */
	def public IScope getTypeScope(EObject context, boolean fromStaticContext, boolean onlyNamespaces) {
		val internal = getTypeScopeInternal(context, fromStaticContext, onlyNamespaces);
		
		val legacy = new ContextAwareTypeScope(internal, context);
		val scopeInfo = new ScopeInfo(internal, legacy, new ContextAwareTypeScopeValidator(context));
		
		return scopeInfo;
	}

	def private IScope getTypeScopeInternal(EObject context, boolean fromStaticContext, boolean onlyNamespaces) {
		switch context {
			Script: {
				return locallyKnownTypesScopingHelper.scopeWithLocallyDeclaredElems(context, [
					var parent = delegateGetScope(context, TypeRefsPackage.Literals.PARAMETERIZED_TYPE_REF__DECLARED_TYPE); // provide any reference that expects instances of Type as target objects
					parent = createScopeForMergedTModules(context, onlyNamespaces, parent);
					return parent;
				], onlyNamespaces);
			}
			N4NamespaceDeclaration: {
				var parent = getTypeScopeInternal(context.eContainer, fromStaticContext, onlyNamespaces);
				val ns = context.definedNamespace;
				if (ns !== null && DeclMergingUtils.mayBeMerged(ns)) {
					parent = createScopeForMergedNamespaces(context, ns, parent);
				}
				return locallyKnownTypesScopingHelper.scopeWithLocallyDeclaredElems(context, parent, onlyNamespaces);
			}
			TNamespace: {
				var parent = getTypeScopeInternal(context.eContainer, fromStaticContext, onlyNamespaces);
				if (DeclMergingUtils.mayBeMerged(context)) {
					parent = createScopeForMergedNamespaces(context, context, parent);
				}
				return locallyKnownTypesScopingHelper.scopeWithLocallyDeclaredElems(context, parent, onlyNamespaces);
			}
			TModule: {
				val script = context.astElement as Script;
				return getTypeScopeInternal(script, fromStaticContext, onlyNamespaces);
			}
			N4FieldDeclaration: {
				val isStaticContext = context.static;
				return getTypeScopeInternal(context.eContainer, isStaticContext, onlyNamespaces); // use new static access status for parent scope
			}
			N4FieldAccessor: {
				val isStaticContext = context.static;
				return getTypeScopeInternal(context.eContainer, isStaticContext, onlyNamespaces); // use new static access status for parent scope
			}
			TypeDefiningElement: {
				val isStaticContext = context instanceof N4MemberDeclaration && (context as N4MemberDeclaration).static;
				val IScope parent = getTypeScopeInternal(context.eContainer, isStaticContext, onlyNamespaces); // use new static access status for parent scope
				val polyfilledOrOriginalType = sourceElementExtensions.getTypeOrPolyfilledType(context);

				return locallyKnownTypesScopingHelper.scopeWithTypeAndItsTypeVariables(parent, polyfilledOrOriginalType, fromStaticContext); // use old static access status for current scope
			}
			TStructMethod: {
				val parent = getTypeScopeInternal(context.eContainer, fromStaticContext, onlyNamespaces);
				return locallyKnownTypesScopingHelper.scopeWithTypeVarsOfTStructMethod(parent, context);
			}
			FunctionTypeExpression: {
				val parent = getTypeScopeInternal(context.eContainer, fromStaticContext, onlyNamespaces);
				return locallyKnownTypesScopingHelper.scopeWithTypeVarsOfFunctionTypeExpression(parent, context);
			}
			default: {
				val container = context.eContainer;

				// handle special areas inside a polyfill that should *not* get the usual polyfill handling implemented
				// in the above case for "TypeDefiningElement":
				if (container instanceof N4ClassifierDeclaration) {
					if (N4JSLanguageUtils.isNonStaticPolyfill(container) || N4JSLanguageUtils.isStaticPolyfill(container)) {
						if (container.typeVars.contains(context)) {
							// area #1: upper/lower bound of type parameter of polyfill, e.g. the 2nd 'T' in:
							// @@StaticPolyfillModule
							// @StaticPolyfill export public class ToBeFilled<T,S extends T> extends ToBeFilled<T,S> {}
							val IScope parent = getTypeScopeInternal(context.eContainer, false, onlyNamespaces);
							return locallyKnownTypesScopingHelper.scopeWithTypeAndItsTypeVariables(parent, container.definedType, fromStaticContext);
						} else if (container.superClassifierRefs.contains(context)) {
							// area #2: super type reference of polyfill, e.g. everything after 'extends' in:
							// @@StaticPolyfillModule
							// @StaticPolyfill export public class ToBeFilled<T> extends ToBeFilled<T> {}
							val script = EcoreUtil2.getContainerOfType(container, Script);
							val globalScope = delegateGetScope(script, TypeRefsPackage.Literals.PARAMETERIZED_TYPE_REF__DECLARED_TYPE);
							val parent = locallyKnownTypesScopingHelper.scopeWithLocallyKnownTypesForPolyfillSuperRef(script, globalScope,
								container.definedType);
							return parent;
						}
					}
				}

				return getTypeScopeInternal(container, fromStaticContext, onlyNamespaces);
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
				val TypeRef propsTypeRef = reactHelper.getPropsType(jsxElem);
				val checkVisibility = true;
				val staticAccess = false;
				val structFieldInitMode = false;
				if (propsTypeRef !== null) {
					// Prevent "Cannot resolve to element" error message of unknown attributes since
					// we want to issue a warning instead
					val TypeRef propsTypeRefUB = ts.upperBoundWithReopenAndResolveTypeVars(context.newRuleEnvironment, propsTypeRef);
					val memberScope = memberScopingHelper.createMemberScope(propsTypeRefUB, context, checkVisibility,
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
