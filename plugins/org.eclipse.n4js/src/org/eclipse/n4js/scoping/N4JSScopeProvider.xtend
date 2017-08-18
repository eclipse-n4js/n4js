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

import com.google.inject.Inject
import com.google.inject.name.Named
import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.n4js.n4JS.Argument
import org.eclipse.n4js.n4JS.Expression
import org.eclipse.n4js.n4JS.IdentifierRef
import org.eclipse.n4js.n4JS.ImportDeclaration
import org.eclipse.n4js.n4JS.LabelledStatement
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
import org.eclipse.n4js.projectModel.IN4JSCore
import org.eclipse.n4js.projectModel.ProjectUtils
import org.eclipse.n4js.scoping.accessModifiers.InvisibleTypeOrVariableDescription
import org.eclipse.n4js.scoping.accessModifiers.TypeVisibilityChecker
import org.eclipse.n4js.scoping.accessModifiers.VariableVisibilityChecker
import org.eclipse.n4js.scoping.imports.ImportedElementsScopingHelper
import org.eclipse.n4js.scoping.members.MemberScopingHelper
import org.eclipse.n4js.scoping.utils.DynamicPseudoScope
import org.eclipse.n4js.scoping.utils.LocallyKnownTypesScopingHelper
import org.eclipse.n4js.scoping.utils.MainModuleAwareSelectableBasedScope
import org.eclipse.n4js.scoping.utils.N4JSTypesScopeFilter
import org.eclipse.n4js.scoping.utils.ProjectImportEnablingScope
import org.eclipse.n4js.ts.scoping.ValidatingScope
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage
import org.eclipse.n4js.ts.typeRefs.TypeTypeRef
import org.eclipse.n4js.ts.types.ModuleNamespaceVirtualType
import org.eclipse.n4js.ts.types.TModule
import org.eclipse.n4js.ts.types.TStructMethod
import org.eclipse.n4js.typesystem.N4JSTypeSystem
import org.eclipse.n4js.validation.JavaScriptVariantHelper
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
import org.eclipse.xtext.scoping.impl.MapBasedScope
import org.eclipse.xtext.scoping.impl.SimpleScope
import org.eclipse.xtext.util.IResourceScopeCache

import static extension org.eclipse.n4js.typesystem.RuleEnvironmentExtensions.*
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

	@Inject extension LocallyKnownTypesScopingHelper

	@Inject extension ImportedElementsScopingHelper

	@Inject extension SourceElementExtensions

	@Inject extension TypeVisibilityChecker

	@Inject extension VariableVisibilityChecker

	@Inject extension ProjectUtils;

	@Inject JavaScriptVariantHelper jsVariantHelper;

	protected def IScope delegateGetScope(EObject context, EReference reference) {
		return delegate.getScope(context, reference)
	}

	override IScopeProvider getDelegate() {
		return delegate;
	}

	override getScope(EObject context, EReference reference) {
		try {
			if (reference == TypeRefsPackage.Literals.PARAMETERIZED_TYPE_REF__DECLARED_TYPE) {
				return new ValidatingScope(getTypeScope(context, reference, false),
					context.getTypesFilterCriteria(reference));
			}

			// early catch:
			if (reference.EReferenceType == N4JSPackage.Literals.LABELLED_STATEMENT) {
				return scope_LabelledStatement(context);
			}

			// otherwise use context:
			switch (context) {
				ImportDeclaration:
					return scope_ImportedModule(context, reference)
				NamedImportSpecifier:
					return scope_ImportedElement(context, reference)
				IdentifierRef:
					return scope_IdentifierRef_id(context, reference)
				ParameterizedPropertyAccessExpression:
					return scope_PropertyAccessExpression_property(context, reference)
				N4FieldAccessor:
					return Scopes.scopeFor(EcoreUtil2.getContainerOfType(context, N4ClassifierDefinition).ownedFields)
			}
		} catch (Error ex) {
			if (context!==null && context.eResource.errors.empty) {
				throw ex;
			}
			// swallow exception, we got a parse error anyway
		}

		return IScope.NULLSCOPE;
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
			}
		}
		return scope;
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

		val initialScope = scope_ImportedAndCurrentModule(importDeclaration, reference);

		val resourceDescriptions = resourceDescriptionsProvider.getResourceDescriptions(importDeclaration.eResource);
		val delegateMainModuleAwareScope = MainModuleAwareSelectableBasedScope.createMainModuleAwareScope(initialScope,
			resourceDescriptions, reference.EReferenceType);

		val projectImportEnabledScope = ProjectImportEnablingScope.create(n4jsCore, importDeclaration, initialScope,
			delegateMainModuleAwareScope);

		// filter out clashing module name (can be main module with the same name but in different project)
		return new FilteringScope(projectImportEnabledScope, [
			if (it === null) false else !it.isDescriptionOfModuleWith(importDeclaration);
		]);
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
	private def IScope scope_ImportedElement(NamedImportSpecifier specifier, EReference reference) {
		val declaration = EcoreUtil2.getContainerOfType(specifier, ImportDeclaration);
		return scope_AllTopLevelElementsFromModule(declaration.module, declaration.eResource);
	}

	/**
	 * Called from getScope(), binds an identifier reference.
	 */
	private def IScope scope_IdentifierRef_id(IdentifierRef identifierRef, EReference ref) {
		val VariableEnvironmentElement vee = identifierRef.ancestor(VariableEnvironmentElement);
		return getLexicalEnvironmentScope(vee, identifierRef, ref);
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
		val Script script = EcoreUtil.getRootContainer(vee) as Script;

		val IScope baseScope = script.getScriptBaseScope(context, reference);

		// imported variables (added as second step to enable shadowing of imported elements)
		var IScope scope = getImportedIdentifiables(baseScope, script);

		for (scopeList : scopeLists.reverseView) {
			scope = buildMapBasedScope(scope, scopeList);
		}
		return scope;
	}

	protected def buildMapBasedScope(IScope scope, Iterable<IEObjectDescription> descriptions) {
		return MapBasedScope.createScope(scope, descriptions);
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
	 * @param contextResource receiver {@link Resource} importing elements
	 */
	private def IScope scope_AllTopLevelElementsFromModule(TModule importedModule, Resource contextResource) {
		if (importedModule === null) {
			return IScope.NULLSCOPE;
		}

		val visible = newArrayList;
		val invisible = newArrayList;
		importedModule.topLevelTypes.forEach [
			val typeVisiblity = isVisible(contextResource, it);
			if (typeVisiblity.visibility) {
				visible.add(EObjectDescription.create(exportedName ?: name, it));
			} else {
				invisible.add(
					new InvisibleTypeOrVariableDescription(EObjectDescription.create(name, it),
						typeVisiblity.accessModifierSuggestion));
			}
		];
		importedModule.variables.forEach [
			val typeVisiblity = isVisible(contextResource, it);
			if (typeVisiblity.visibility) {
				visible.add(EObjectDescription.create(exportedName ?: name, it));
			} else {
				invisible.add(
					new InvisibleTypeOrVariableDescription(EObjectDescription.create(name, it),
						typeVisiblity.accessModifierSuggestion));
			}
		];
		return MapBasedScope.createScope(IScope.NULLSCOPE, visible + invisible);
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
			if (receiver.id instanceof ModuleNamespaceVirtualType) {
				val namespace = receiver.id as ModuleNamespaceVirtualType;
				val result = scope_AllTopLevelElementsFromModule(namespace.module, propertyAccess.eResource);
				if (namespace.declaredDynamic && !(result instanceof DynamicPseudoScope)) {
					return new DynamicPseudoScope(result);
				}
				return result;
			}
		}

		val G = propertyAccess.newRuleEnvironment;
		val TypeRef typeRefRaw = ts.type(G, receiver).value;
		// take upper bound to get rid of ExistentialTypeRefs, ThisTypeRefs, etc.
		val TypeRef typeRef = if(typeRefRaw!==null) ts.upperBound(G, typeRefRaw).value else null;

		val staticAccess = typeRef instanceof TypeTypeRef;
		val checkVisibility = true;
		return memberScopingHelper.createMemberScope(typeRef, propertyAccess, checkVisibility, staticAccess);
	}

	/**
	 * Is entered (and later recursively called) to initially bind "T" in <pre>var T x;</pre> or other parameterized type references.
	 */
	def public IScope getTypeScope(EObject context, EReference reference, boolean fromStaticContext) {
		switch context {
			Script: {
				return scopeWithLocallyKnownTypes(context, reference, delegate);
			}
			TModule: {
				return scopeWithLocallyKnownTypes(context.astElement as Script, reference, delegate);
			}
			N4FieldDeclaration: {
				val isStaticContext = context.static;
				return getTypeScope(context.eContainer, reference, isStaticContext); // use new static access status for parent scope
			}
			N4FieldAccessor: {
				val isStaticContext = context.static;
				return getTypeScope(context.eContainer, reference, isStaticContext); // use new static access status for parent scope
			}
			TypeDefiningElement: {
				val isStaticContext = context instanceof N4MemberDeclaration && (context as N4MemberDeclaration).static;
				val IScope parent = getTypeScope(context.eContainer, reference, isStaticContext); // use new static access status for parent scope
				if (context instanceof N4ClassDeclaration) {
					if ( context.isPolyfill
						||	context.isStaticPolyfill ) { // in polyfill? delegate to filled type and its type variables
						val filledType = context.definedTypeAsClass?.superClassRef?.declaredType;
						return scopeWithTypeAndItsTypeVariables(parent, filledType, fromStaticContext); // use old static access status for current scope
					}
				}

				return scopeWithTypeAndItsTypeVariables(parent, context.definedType, fromStaticContext); // use old static access status for current scope
			}
			TStructMethod: {
				val parent = getTypeScope(context.eContainer, reference, fromStaticContext);
				return scopeWithTypeVarsOfTStructMethod(parent, context);
			}
			FunctionTypeExpression: {
				val parent = getTypeScope(context.eContainer, reference, fromStaticContext);
				return scopeWithTypeVarsOfFunctionTypeExpression(parent, context);
			}
			default: {
				val container = context.eContainer;

				// do we set super type reference of polyfill?
				if (container instanceof N4ClassDeclaration) {
					if (container.superClassRef === context
						&&	(container.isPolyfill || container.isStaticPolyfill)
					) {
						val script = EcoreUtil2.getContainerOfType(container, Script);
						val parent = scopeWithLocallyKnownTypesForPolyfillSuperRef(script, reference, delegate,
							container.definedType);
						return parent;
					}
				}
				return getTypeScope(context.eContainer, reference, fromStaticContext);
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
}
