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
package org.eclipse.n4js.scoping;

import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.isIterableN;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.newRuleEnvironment;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.flatten;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.last;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.toIterable;
import static org.eclipse.xtext.xbase.lib.ListExtensions.reverseView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.n4js.n4JS.Argument;
import org.eclipse.n4js.n4JS.ArrayBindingPattern;
import org.eclipse.n4js.n4JS.BindingElement;
import org.eclipse.n4js.n4JS.BindingProperty;
import org.eclipse.n4js.n4JS.DestructNode;
import org.eclipse.n4js.n4JS.DestructureUtils;
import org.eclipse.n4js.n4JS.ExportDeclaration;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.ImportDeclaration;
import org.eclipse.n4js.n4JS.JSXElement;
import org.eclipse.n4js.n4JS.JSXElementName;
import org.eclipse.n4js.n4JS.JSXPropertyAttribute;
import org.eclipse.n4js.n4JS.LabelledStatement;
import org.eclipse.n4js.n4JS.LiteralOrComputedPropertyName;
import org.eclipse.n4js.n4JS.MemberAccess;
import org.eclipse.n4js.n4JS.ModuleRef;
import org.eclipse.n4js.n4JS.N4ClassifierDeclaration;
import org.eclipse.n4js.n4JS.N4ClassifierDefinition;
import org.eclipse.n4js.n4JS.N4FieldAccessor;
import org.eclipse.n4js.n4JS.N4FieldDeclaration;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.N4MemberDeclaration;
import org.eclipse.n4js.n4JS.N4NamespaceDeclaration;
import org.eclipse.n4js.n4JS.N4TypeDeclaration;
import org.eclipse.n4js.n4JS.NamedExportSpecifier;
import org.eclipse.n4js.n4JS.NamedImportSpecifier;
import org.eclipse.n4js.n4JS.NewExpression;
import org.eclipse.n4js.n4JS.ObjectLiteral;
import org.eclipse.n4js.n4JS.ParameterizedCallExpression;
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;
import org.eclipse.n4js.n4JS.PropertyNameValuePair;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.Statement;
import org.eclipse.n4js.n4JS.TypeDefiningElement;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.n4JS.VariableEnvironmentElement;
import org.eclipse.n4js.resource.N4JSCache;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.scoping.accessModifiers.ContextAwareTypeScope;
import org.eclipse.n4js.scoping.accessModifiers.MemberVisibilityChecker;
import org.eclipse.n4js.scoping.imports.ImportedElementsScopingHelper;
import org.eclipse.n4js.scoping.imports.N4JSImportedNamespaceAwareLocalScopeProvider;
import org.eclipse.n4js.scoping.members.MemberScopingHelper;
import org.eclipse.n4js.scoping.utils.DynamicPseudoScope;
import org.eclipse.n4js.scoping.utils.LocallyKnownTypesScopingHelper;
import org.eclipse.n4js.scoping.utils.MainModuleAwareSelectableBasedScope;
import org.eclipse.n4js.scoping.utils.MergedScope;
import org.eclipse.n4js.scoping.utils.ProjectImportEnablingScope;
import org.eclipse.n4js.scoping.utils.ScopeSnapshotHelper;
import org.eclipse.n4js.scoping.utils.SourceElementExtensions;
import org.eclipse.n4js.scoping.utils.UberParentScope;
import org.eclipse.n4js.scoping.validation.ContextAwareTypeScopeValidator;
import org.eclipse.n4js.scoping.validation.ScopeInfo;
import org.eclipse.n4js.scoping.validation.VeeScopeValidator;
import org.eclipse.n4js.scoping.validation.VisibilityAwareCtorScopeValidator;
import org.eclipse.n4js.tooling.react.ReactHelper;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression;
import org.eclipse.n4js.ts.typeRefs.NamespaceLikeRef;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRefStructural;
import org.eclipse.n4js.ts.typeRefs.TypeArgument;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage;
import org.eclipse.n4js.ts.typeRefs.TypeTypeRef;
import org.eclipse.n4js.ts.types.AbstractNamespace;
import org.eclipse.n4js.ts.types.ModuleNamespaceVirtualType;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TEnum;
import org.eclipse.n4js.ts.types.TFunction;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.TNamespace;
import org.eclipse.n4js.ts.types.TStructMethod;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.n4js.ts.types.TypingStrategy;
import org.eclipse.n4js.types.utils.TypeUtils;
import org.eclipse.n4js.typesystem.N4JSTypeSystem;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.typesystem.utils.TypeSystemHelper;
import org.eclipse.n4js.utils.ContainerTypesHelper;
import org.eclipse.n4js.utils.DeclMergingHelper;
import org.eclipse.n4js.utils.DeclMergingUtils;
import org.eclipse.n4js.utils.EObjectDescriptionHelper;
import org.eclipse.n4js.utils.N4JSLanguageUtils;
import org.eclipse.n4js.utils.ResourceType;
import org.eclipse.n4js.utils.URIUtils;
import org.eclipse.n4js.validation.JavaScriptVariantHelper;
import org.eclipse.n4js.workspace.N4JSWorkspaceConfigSnapshot;
import org.eclipse.n4js.workspace.WorkspaceAccess;
import org.eclipse.n4js.xtext.scoping.FilteringScope;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.EObjectDescription;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.IScopeProvider;
import org.eclipse.xtext.scoping.impl.AbstractScopeProvider;
import org.eclipse.xtext.scoping.impl.IDelegatingScopeProvider;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

/**
 * This class contains custom scoping description.
 *
 * Although some methods are called according to declarative scope provider, no reflection in
 * {@link #getScope(EObject,EReference)}.
 *
 * see : http://www.eclipse.org/Xtext/documentation/latest/xtext.html#scoping on how and when to use it
 */
@Singleton
public class N4JSScopeProvider extends AbstractScopeProvider
		implements IDelegatingScopeProvider, IContentAssistScopeProvider {

	/***/
	public final static String NAMED_DELEGATE = "org.eclipse.xtext.scoping.impl.AbstractDeclarativeScopeProvider.delegate";

	@Inject
	N4JSCache cache;

	/**
	 * The scope provider creating the "parent" scope, i.e. including elements from the index. At runtime, the value
	 * will be of type {@link N4JSImportedNamespaceAwareLocalScopeProvider}.
	 */
	@Inject
	@Named(NAMED_DELEGATE)
	IScopeProvider delegate;

	@Inject
	WorkspaceAccess workspaceAccess;

	@Inject
	ResourceDescriptionsProvider resourceDescriptionsProvider;

	@Inject
	N4JSTypeSystem ts;

	@Inject
	TypeSystemHelper tsh;

	@Inject
	MemberScopingHelper memberScopingHelper;

	@Inject
	LocallyKnownTypesScopingHelper locallyKnownTypesScopingHelper;

	@Inject
	ImportedElementsScopingHelper importedElementsScopingHelper;

	@Inject
	SourceElementExtensions sourceElementExtensions;

	@Inject
	EObjectDescriptionHelper descriptionsHelper;

	@Inject
	ReactHelper reactHelper;

	@Inject
	JavaScriptVariantHelper jsVariantHelper;

	@Inject
	MemberVisibilityChecker checker;

	@Inject
	ContainerTypesHelper containerTypesHelper;

	@Inject
	ExportedElementsCollector exportedElementCollector;

	@Inject
	ScopeSnapshotHelper scopeSnapshotHelper;

	@Inject
	DeclMergingHelper declMergingHelper;

	/***/
	protected boolean isSuppressCrossFileResolutionOfIdentifierRefs() {
		return false;
	}

	/**
	 * Delegates to {@link N4JSImportedNamespaceAwareLocalScopeProvider#getScope(EObject, EReference)}, which in turn
	 * delegates further to {@link N4JSGlobalScopeProvider}.
	 */
	protected IScope delegateGetScope(EObject context, EReference reference) {
		return delegate.getScope(context, reference);
	}

	@Override
	public IScopeProvider getDelegate() {
		return delegate;
	}

	/** Dispatches to concrete scopes based on the context and reference inspection */
	@Override
	public IScope getScope(EObject context, EReference reference) {
		try {
			// dispatch based on language variant
			ResourceType resourceType = ResourceType.getResourceType(context);
			switch (resourceType) {
			case N4JSX:
				return getN4JSXScope(context, reference);
			case JSX:
				return getN4JSXScope(context, reference);
			default:
				return getN4JSScope(context, reference);
			}

		} catch (Error ex) {
			if (context != null && context.eResource().getErrors().isEmpty()) {
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
	 * The returned scope is not sensitive to any of the language variants of N4JS. In order to obtain a
	 * variant-specific scope, please use {@link N4JSScopeProvider#getScope(EObject, EReference)}.
	 */
	public IScope getN4JSScope(EObject context, EReference reference) {
		// maybe can use scope shortcut
		IScope maybeScopeShortcut = getScopeByShortcut(context, reference);
		if (maybeScopeShortcut != IScope.NULLSCOPE) {
			return maybeScopeShortcut;
		}

		// otherwise use context:
		return getScopeForContext(context, reference);
	}

	/**
	 * shortcut to concrete scopes based on reference sniffing. Will return {@link IScope#NULLSCOPE} if no suitable
	 * scope found
	 */
	private IScope getScopeByShortcut(EObject context, EReference reference) {
		if (reference == TypeRefsPackage.Literals.NAMESPACE_LIKE_REF__DECLARED_TYPE) {
			if (context instanceof NamespaceLikeRef) {
				NamespaceLikeRef nslr = (NamespaceLikeRef) context;
				Type namespaceLikeType = nslr.getPreviousSibling() == null
						? null
						: nslr.getPreviousSibling().getDeclaredType();

				Script script = EcoreUtil2.getContainerOfType(context, Script.class);
				N4NamespaceDeclaration parentNamespace = EcoreUtil2.getContainerOfType(context,
						N4NamespaceDeclaration.class);
				EObject parentContainer = parentNamespace == null ? script : parentNamespace;
				// also check for eIsProxy in case of broken AST
				EObject namespace = namespaceLikeType == null || namespaceLikeType.eIsProxy() ? parentContainer
						: namespaceLikeType;
				return new FilteringScope(getTypeScope(namespace, false, true),
						descr -> TypesPackage.Literals.MODULE_NAMESPACE_VIRTUAL_TYPE.isSuperTypeOf(descr.getEClass())
								// only included, because classes might have a namespace merged onto them!
								|| TypesPackage.Literals.TCLASS.isSuperTypeOf(descr.getEClass())
								|| TypesPackage.Literals.TENUM.isSuperTypeOf(descr.getEClass())
								|| TypesPackage.Literals.TNAMESPACE.isSuperTypeOf(descr.getEClass()));
			}
		} else if (reference == TypeRefsPackage.Literals.PARAMETERIZED_TYPE_REF__DECLARED_TYPE) {
			if (context instanceof ParameterizedTypeRef) {
				ParameterizedTypeRef ptr = (ParameterizedTypeRef) context;
				Type namespaceLikeType = null;
				if (ptr.getAstNamespaceLikeRefs() != null && !ptr.getAstNamespaceLikeRefs().isEmpty()) {
					namespaceLikeType = last(ptr.getAstNamespaceLikeRefs()).getDeclaredType();
				}
				if (namespaceLikeType instanceof ModuleNamespaceVirtualType) {
					return createScopeForNamespaceAccess((ModuleNamespaceVirtualType) namespaceLikeType, context, true,
							false);
				} else if (namespaceLikeType instanceof TClass) {
					return createScopeForMergedNamespaces(context, namespaceLikeType, IScope.NULLSCOPE);
				} else if (namespaceLikeType instanceof TEnum) {
					return new DynamicPseudoScope();
				} else if (namespaceLikeType instanceof TNamespace) {
					return scope_AllTopLevelElementsFromAbstractNamespace((TNamespace) namespaceLikeType, context, true,
							false);
				}
			}
			return getTypeScope(context, false, false);
		} else if (reference.getEReferenceType() == N4JSPackage.Literals.LABELLED_STATEMENT) {
			return scope_LabelledStatement(context);
		}
		return IScope.NULLSCOPE;
	}

	/** dispatch to internal methods based on the context */
	private IScope getScopeForContext(EObject context, EReference reference) {
		if (context instanceof ImportDeclaration) {
			return scope_ImportedModule((ImportDeclaration) context);
		} else if (context instanceof NamedImportSpecifier) {
			return scope_ImportedElement((NamedImportSpecifier) context);
		} else if (context instanceof ExportDeclaration) {
			return scope_ImportedModule((ExportDeclaration) context);
		} else if (context instanceof IdentifierRef) {
			return scope_IdentifierRef_id((IdentifierRef) context, reference);
		} else if (context instanceof BindingProperty) {
			return scope_BindingProperty_property((BindingProperty) context);
		} else if (context instanceof PropertyNameValuePair) {
			return scope_PropertyNameValuePair_property((PropertyNameValuePair) context);
		} else if (context instanceof ParameterizedPropertyAccessExpression) {
			return scope_PropertyAccessExpression_property((ParameterizedPropertyAccessExpression) context);
		} else if (context instanceof N4FieldAccessor) {
			N4ClassifierDefinition container = EcoreUtil2.getContainerOfType((N4FieldAccessor) context,
					N4ClassifierDefinition.class);
			return scopeSnapshotHelper.scopeForEObjects("N4FieldAccessor", container, container.getOwnedFields());
		}
		return IScope.NULLSCOPE;
	}

	@Override
	public IScope getScopeForContentAssist(EObject context, EReference reference) {
		IScope scope = getScope(context, reference);

		if (scope == IScope.NULLSCOPE) {
			// used for type references in JSDoc (see JSDocCompletionProposalComputer):
			if (reference == N4JSPackage.Literals.MODULE_REF__MODULE) {
				return scope_ImportedAndCurrentModule(context, reference);
			}

			// the following cases are only required for content assist (i.e. scoping on broken ASTs or at unusual
			// locations) otherwise use context:

			if (context instanceof Script) {
				return scope_EObject_id(context, reference);
			} else if (context instanceof N4TypeDeclaration) {
				return scope_EObject_id(context, reference);
			} else if (context instanceof VariableDeclaration) {
				return scope_EObject_id(context, reference);
			} else if (context instanceof Statement) {
				return scope_EObject_id(context, reference);
			} else if (context instanceof NewExpression) {
				return scope_EObject_id(context, reference);
			} else if (context instanceof ParameterizedCallExpression) {
				return scope_EObject_id(context, reference);
			} else if (context instanceof Argument) {
				return scope_EObject_id(context, reference);
			} else if (context instanceof Expression) {
				return scope_EObject_id(context, reference);
			} else if (context instanceof LiteralOrComputedPropertyName) {
				return scope_EObject_id(context, reference);
			}
		}

		return scope;
	}

	/**
	 * Returns a scope as created by {@link #getScope(EObject, EReference)} for the "from" part of an import declaration
	 * in the AST, but without the need for providing any AST nodes. This can be used to implement implicit imports
	 * without duplicating any logic from the scoping.
	 * <p>
	 * There are two minor differences to the scope created by {@code #getScope()}:
	 * <ol>
	 * <li>the current module, i.e. the module represented by the given resource, won"t be filtered out, and
	 * <li>advanced error reporting will be disabled, i.e. {@link IScope#getSingleElement(QualifiedName)} will simply
	 * return <code>null</code> instead of returning an {@code IEObjectDescriptionWithError}.
	 * </ol>
	 */
	public IScope getScopeForImplicitImports(N4JSResource resource) {
		return scope_ImportedModule(resource, Optional.absent());
	}

	/**
	 * In
	 *
	 * <pre>
	 * continue XYZ
	 * </pre>
	 *
	 * , bind XYZ to label. Bind to ALL labels in script, although not all of them are valid. Later is to be validated
	 * in ASTStructureValidator and allows for better error and quick fix handling. However, most inner (and correct)
	 * scope is preferred (solving problems in case of duplicate names).
	 */
	private IScope scope_LabelledStatement(EObject context) {
		IScope parent = getAllLabels((Script) EcoreUtil.getRootContainer(context));
		Set<Object> names = new HashSet<>();
		List<IEObjectDescription> elements = new ArrayList<>();
		EObject current = context;
		while (current != null) {
			if (current instanceof LabelledStatement) {
				LabelledStatement ls = (LabelledStatement) current;
				if (names.add(ls.getName())) {
					elements.add(EObjectDescription.create(ls.getName(), current));
				}
			}
			current = current.eContainer(); // labeled statement must be a container
		}
		if (elements.isEmpty()) {
			return parent;
		}
		IScope result = scopeSnapshotHelper.scopeFor("contextLabels", current, parent, elements);
		return result;
	}

	private IScope getAllLabels(Script script) {
		return scopeSnapshotHelper.scopeForEObjects("allLabels", script,
				toIterable(filter(script.eAllContents(), LabelledStatement.class)));
	}

	/**
	 * E.g. in
	 *
	 * <pre>
	 * import { e1,e2 } from "a/b/importedModule"
	 * </pre>
	 *
	 * bind "a/b/importedModule" to module with qualified name "a.b.importedModule"
	 */
	private IScope scope_ImportedModule(ModuleRef importOrExportDecl) {

		N4JSResource resource = (N4JSResource) importOrExportDecl.eResource();
		IScope projectImportEnabledScope = scope_ImportedModule(resource, Optional.of(importOrExportDecl));

		// filter out clashing module name (can be main module with the same name but in different project)
		return new FilteringScope(projectImportEnabledScope, descr -> (descr == null) ? false
				: !descriptionsHelper.isDescriptionOfModuleWith(resource, descr, importOrExportDecl));
	}

	private IScope scope_ImportedModule(N4JSResource resource, Optional<ModuleRef> importOrExportDecl) {

		EReference reference = N4JSPackage.eINSTANCE.getModuleRef_Module();

		IScope initialScope = scope_ImportedAndCurrentModule(resource.getScript(), reference);

		IResourceDescriptions resourceDescriptions = resourceDescriptionsProvider.getResourceDescriptions(resource);
		IScope delegateMainModuleAwareScope = MainModuleAwareSelectableBasedScope.createMainModuleAwareScope(
				initialScope,
				resourceDescriptions, reference.getEReferenceType());

		N4JSWorkspaceConfigSnapshot ws = workspaceAccess.getWorkspaceConfig(resource);
		IScope projectImportEnabledScope = ProjectImportEnablingScope.create(ws, resource, importOrExportDecl,
				initialScope, delegateMainModuleAwareScope, declMergingHelper);

		return projectImportEnabledScope;
	}

	/**
	 * Same as {@link #scope_ImportedModule(ModuleRef )}, but also includes the current module.
	 */
	private IScope scope_ImportedAndCurrentModule(EObject importDeclaration, EReference reference) {
		return delegateGetScope(importDeclaration, reference);
	}

	/**
	 * E.g. in
	 *
	 * <pre>
	 * import { e1,e2 } from "importedModule"
	 * </pre>
	 *
	 * bind e1 or e2 by retrieving all (not only exported, see below!) top level elements of importedModule (variables,
	 * types; functions are types!). All elements enables better error handling and quick fixes, as links are not
	 * broken.
	 */
	protected IScope scope_ImportedElement(NamedImportSpecifier specifier) {
		ImportDeclaration impDecl = EcoreUtil2.getContainerOfType(specifier, ImportDeclaration.class);
		TModule targetModule = impDecl.getModule(); // may trigger reentrant scoping for module specifier (cf.
													// #scope_ImportedModule())
		return scope_AllTopLevelElementsFromAbstractNamespace(targetModule, impDecl, true, true);
	}

	/**
	 * E.g. in
	 *
	 * <pre>
	 * export { e1, e2 } from "importedModule"
	 * </pre>
	 *
	 * See {@link #scope_ImportedElement(NamedImportSpecifier )}.
	 */
	protected IScope scope_ImportedElement(NamedExportSpecifier specifier) {
		ExportDeclaration expDecl = EcoreUtil2.getContainerOfType(specifier, ExportDeclaration.class);
		TModule targetModule = expDecl.getModule(); // may trigger reentrant scoping for module specifier (cf.
													// #scope_ImportedModule())
		return scope_AllTopLevelElementsFromAbstractNamespace(targetModule, expDecl, true, true);
	}

	/**
	 * Called from getScope(), binds an identifier reference.
	 */
	private IScope scope_IdentifierRef_id(IdentifierRef identifierRef, EReference ref) {
		EObject parent = identifierRef.eContainer();
		// handle re-exports
		if (parent instanceof NamedExportSpecifier) {
			EObject grandParent = parent.eContainer();
			if (grandParent instanceof ExportDeclaration) {
				if (((ExportDeclaration) grandParent).isReexport()) {
					if (isSuppressCrossFileResolutionOfIdentifierRefs()) {
						return IScope.NULLSCOPE;
					}
					return scope_ImportedElement((NamedExportSpecifier) parent);
				}
			}
		}
		VariableEnvironmentElement vee = ancestor(identifierRef, VariableEnvironmentElement.class);
		if (vee == null) {
			return IScope.NULLSCOPE;
		}
		ScopeInfo scope = getLexicalEnvironmentScope(vee, identifierRef, ref);
		// Handle constructor visibility
		if (parent instanceof NewExpression) {
			return scope.addValidator(
					new VisibilityAwareCtorScopeValidator(checker, containerTypesHelper, (NewExpression) parent));
		}
		return scope;
	}

	/**
	 * Only used for content assist. Modeled after method {@link #scope_IdentifierRef_id(IdentifierRef,EReference)}.
	 */
	private IScope scope_EObject_id(EObject obj, EReference ref) {
		VariableEnvironmentElement vee = (obj instanceof VariableEnvironmentElement)
				? (VariableEnvironmentElement) obj
				: ancestor(obj, VariableEnvironmentElement.class);

		if (vee == null) {
			return IScope.NULLSCOPE;
		}
		return getLexicalEnvironmentScope(vee, obj, ref);
	}

	private ScopeInfo getLexicalEnvironmentScope(VariableEnvironmentElement vee, EObject context, EReference ref) {
		ensureLexicalEnvironmentScopes(context, ref);
		return cache.mustGet(vee.eResource(), "scope_IdentifierRef_id", vee);
	}

	private void ensureLexicalEnvironmentScopes(EObject context, EReference reference) {
		Script script = (Script) EcoreUtil.getRootContainer(context);
		Resource resource = script.eResource();
		Object key = N4JSCache.makeKey("scope_IdentifierRef_id", script);
		boolean veeScopesBuilt = cache.contains(resource, key); // note that a script is a vee
		if (!veeScopesBuilt) {
			cache.get(resource, () -> buildLexicalEnvironmentScope(script, context, reference), key);
			Iterator<EObject> scriptIterator = script.eAllContents();
			while (scriptIterator.hasNext()) {
				EObject veeObj = scriptIterator.next();
				if (veeObj instanceof VariableEnvironmentElement) {
					// fill the cache
					VariableEnvironmentElement vee = (VariableEnvironmentElement) veeObj;
					cache.get(resource, () -> buildLexicalEnvironmentScope(vee, context, reference),
							"scope_IdentifierRef_id", vee);
				}
			}
		}
	}

	/**
	 * Builds a lexical environment scope with the given parameters. Filters out primitive types.
	 */
	private ScopeInfo buildLexicalEnvironmentScope(VariableEnvironmentElement vee, EObject context,
			EReference reference) {
		List<Iterable<? extends EObject>> scopeLists = new ArrayList<>();
		// variables declared in module
		collectLexialEnvironmentsScopeLists(vee, scopeLists);

		IScope scope;
		if (isSuppressCrossFileResolutionOfIdentifierRefs()) {
			// Suppressing cross-file resolution is necessary for the CFG/DFG analysis
			// triggered in N4JSPostProcessor#postProcessN4JSResource(...).
			scope = IScope.NULLSCOPE;
		} else {
			Script script = (Script) EcoreUtil.getRootContainer(vee);
			Resource resource = script.eResource();
			// TODO GH-2338 reconsider the following recursion guard (required for chains of re-exports in cyclic
			// modules)
			AtomicBoolean guard = cache.get(resource, () -> new AtomicBoolean(false),
					"buildLexicalEnvironmentScope__importedValuesComputationGuard", script);
			boolean alreadyInProgress = guard.getAndSet(true);
			if (alreadyInProgress) {
				scope = IScope.NULLSCOPE;
			} else {
				try {
					IScope baseScope = getScriptBaseScope(script, context, reference);
					// imported variables (added as second step to enable shadowing of imported elements)
					scope = importedElementsScopingHelper.getImportedValues(baseScope, script);
				} finally {
					guard.set(false);
				}
			}
		}

		scope = scopeSnapshotHelper.scopeForEObjects("buildLexicalEnvironmentScope", context, scope, false,
				flatten(scopeLists));

		ScopeInfo scopeInfo = new ScopeInfo(scope, scope, new VeeScopeValidator(context));

		return scopeInfo;
	}

	private IScope getScriptBaseScope(Script script, EObject context, EReference ref) {
		// IDE-1065: there may be user declared globals (i.e. @@Global)
		IScope globalScope = delegateGetScope(script, ref);

		if (jsVariantHelper.activateDynamicPseudoScope(context)) { // cf. sec. 13.1
			return new DynamicPseudoScope(globalScope);
		}
		return globalScope;
	}

	private void collectLexialEnvironmentsScopeLists(VariableEnvironmentElement vee,
			List<Iterable<? extends EObject>> result) {

		result.add(sourceElementExtensions.collectVisibleIdentifiableElements(vee));

		// implicit variable "arguments" must be in own outer scope in order to enable shadowing of inner variables
		// named "arguments"
		result.add(sourceElementExtensions.collectLocalArguments(vee));

		VariableEnvironmentElement parent = ancestor(vee, VariableEnvironmentElement.class);
		if (parent != null) {
			collectLexialEnvironmentsScopeLists(parent, result);
		}
	}

	/**
	 * Creates IScope with all top level elements (variables and types, including functions), from target module or
	 * namespace. Provided resource is used to check visibility of module elements. Not visible elements are imported
	 * too, which allows better error handling and quick fixes, as links are not broken.
	 *
	 * Used for elements imported with named import and to access elements via namespace import.
	 *
	 * @param ns
	 *            target {@link TModule} from which elements are imported
	 * @param context
	 *            Receiver context {@link EObject} which is importing elements
	 */
	private IScope scope_AllTopLevelElementsFromAbstractNamespace(AbstractNamespace ns, EObject context,
			boolean includeHollows, boolean includeValueOnlyElements) {

		return cache.get(context.eResource(),
				() -> scope_AllTopLevelElementsFromAbstractNamespace(ns, context, IScope.NULLSCOPE, includeHollows,
						includeValueOnlyElements),
				"createScopeForMergedNamespaces", ns, includeHollows, includeValueOnlyElements);

	}

	private IScope scope_AllTopLevelElementsFromAbstractNamespace(AbstractNamespace ns, EObject context,
			IScope parentOrNull,
			boolean includeHollows, boolean includeValueOnlyElements) {

		if (ns == null) {
			return parentOrNull;
		}

		Resource resource = context.eResource();

		// TODO GH-2338 reconsider the following recursion guard (required for chains of re-exports in cyclic modules)
		AtomicBoolean guard = cache.get(resource, () -> new AtomicBoolean(false),
				"scope_AllTopLevelElementsFromAbstractNamespace__exportedElementsComputationGuard", context);
		boolean alreadyInProgress = guard.getAndSet(true);
		if (alreadyInProgress) {
			return parentOrNull;
		}
		try {
			// get regular top-level elements scope
			Optional<MemberAccess> memberAccess = (context instanceof MemberAccess)
					? Optional.of((MemberAccess) context)
					: Optional.absent();
			Iterable<IEObjectDescription> tlElems = exportedElementCollector.getExportedElements(ns,
					(N4JSResource) context.eResource(), memberAccess, includeHollows, includeValueOnlyElements);
			IScope topLevelElementsScope = scopeSnapshotHelper.scopeFor(
					"scope_AllTopLevelElementsFromAbstractNamespace", ns,
					parentOrNull != null ? parentOrNull : IScope.NULLSCOPE, false, tlElems);
			return topLevelElementsScope;
		} finally {
			guard.set(false);
		}
	}

	/**
	 * Called from getScope(), binds a property reference.
	 */
	private IScope scope_BindingProperty_property(BindingProperty bindingProperty) {
		return scope_DestructPattern_property(bindingProperty);
	}

	/**
	 * Called from getScope(), binds a property reference.
	 */
	private IScope scope_PropertyNameValuePair_property(PropertyNameValuePair pnvPair) {
		return scope_DestructPattern_property(pnvPair);
	}

	private IScope scope_DestructPattern_property(EObject propertyContainer) {
		TypeRef cTypeRef = null;
		DestructNode destNodeTop = DestructNode.unify(DestructureUtils.getTop(propertyContainer));
		DestructNode parentDestNode = destNodeTop == null ? null
				: destNodeTop.findNodeOrParentForElement(propertyContainer, true);
		RuleEnvironment G = newRuleEnvironment(propertyContainer);

		if (parentDestNode != null) {
			EObject parentAstElem = parentDestNode.astElement;
			if (parentAstElem instanceof BindingProperty) {
				BindingProperty bp = (BindingProperty) parentAstElem;
				if (bp.getProperty() != null) {
					cTypeRef = ts.type(G, bp.getProperty());
				}
			} else if (parentAstElem instanceof BindingElement
					&& parentAstElem.eContainer() instanceof ArrayBindingPattern) {
				DestructNode parent2DestNode = destNodeTop == null ? null
						: destNodeTop.findNodeOrParentForElement(parentAstElem, true);
				if (parent2DestNode != null) {
					TypeRef arrayType = null;
					EObject parent2AstElem = parent2DestNode.astElement;
					if (parent2AstElem instanceof BindingProperty) {
						BindingProperty bp = (BindingProperty) parent2AstElem;
						if (bp.getProperty() != null) {
							arrayType = ts.type(G, bp.getProperty());
						}
					} else {
						arrayType = ts.type(G, parent2DestNode.assignedElem);
					}

					int idx = Arrays.asList(parent2DestNode.nestedNodes).indexOf(parentDestNode);
					if (arrayType != null && arrayType.getTypeArgsWithDefaults().size() > idx
							&& isIterableN(G, arrayType) && arrayType.eResource() != null) {
						TypeArgument typeArg = arrayType.getTypeArgsWithDefaults().get(idx);
						if (typeArg instanceof TypeRef) {
							cTypeRef = (TypeRef) typeArg;
						}
					}
				}
			} else if (parentDestNode.assignedElem instanceof TypeDefiningElement
					&& ((TypeDefiningElement) parentDestNode.assignedElem).getDefinedType() != null) {
				cTypeRef = TypeUtils
						.createTypeRef(((TypeDefiningElement) parentDestNode.assignedElem).getDefinedType());
			} else {
				// fallback
				cTypeRef = ts.type(G, parentDestNode.assignedElem);
			}
			if (DestructureUtils.isTopOfDestructuringForStatement(parentDestNode.astElement) &&
					cTypeRef != null && cTypeRef.isArrayLike()) {
				tsh.addSubstitutions(G, cTypeRef);
				cTypeRef = ts.substTypeVariables(G, cTypeRef.getDeclaredType().getElementType());
			}
		}

		if (cTypeRef == null && propertyContainer instanceof PropertyNameValuePair
				&& propertyContainer.eContainer() instanceof ObjectLiteral) {
			ObjectLiteral objLit = (ObjectLiteral) propertyContainer.eContainer();
			if (objLit.getDefinedType() != null) {
				cTypeRef = TypeUtils.createTypeRef(objLit.getDefinedType());
			}
		}

		if (cTypeRef != null && isContained(cTypeRef)) {
			return new UberParentScope("scope_DestructPattern_property",
					createScopeForMemberAccess(cTypeRef, propertyContainer), new DynamicPseudoScope());
		}
		return new DynamicPseudoScope();
	}

	private boolean isContained(TypeRef tRef) {
		if (tRef.eResource() != null) {
			// type ref is contained
			return true;
		}
		if (tRef instanceof ParameterizedTypeRefStructural) {
			ParameterizedTypeRefStructural ptrs = (ParameterizedTypeRefStructural) tRef;
			if (!ptrs.getAstStructuralMembers().isEmpty() || !ptrs.getGenStructuralMembers().isEmpty()) {
				// type ref is not contained, hence structural members are not contained
				return false;
			}
		}
		if (tRef.getDeclaredType() != null && tRef.getDeclaredType().eResource() != null) {
			// nominal type (or similar) is contained
			return true;
		}
		return false;
	}

	/*
	 * In <pre>receiver.property</pre>, binds "property".
	 *
	 */
	private IScope scope_PropertyAccessExpression_property(ParameterizedPropertyAccessExpression propertyAccess) {
		Expression receiver = propertyAccess.getTarget();

		RuleEnvironment G = newRuleEnvironment(propertyAccess);
		TypeRef typeRefRaw = ts.type(G, receiver);
		// take upper bound to get rid of ExistentialTypeRefs, ThisTypeRefs, etc.
		// (literal types are handled in dispatch method #members() of MemberScopingHelper)
		TypeRef typeRef = ts.upperBoundWithReopenAndResolveTypeVars(G, typeRefRaw);
		Type declaredType = typeRef.getDeclaredType();
		if (declaredType instanceof TNamespace) {
			return scope_AllTopLevelElementsFromAbstractNamespace((TNamespace) declaredType, propertyAccess, false,
					true);
		}
		if (declaredType instanceof ModuleNamespaceVirtualType) {
			return createScopeForNamespaceAccess((ModuleNamespaceVirtualType) declaredType, propertyAccess, false,
					true);
		}

		var result = createScopeForMemberAccess(typeRef, propertyAccess);

		// functions and classes may have namespaces merged onto them
		result = handleDeclMergingForPropertyAccess(G, propertyAccess, typeRef, result);

		return result;
	}

	private IScope createScopeForNamespaceAccess(ModuleNamespaceVirtualType namespace, EObject context,
			boolean includeHollows, boolean includeValueOnlyElements) {
		TModule module = namespace.getModule();

		IScope result;

		if (module != null && !module.eIsProxy()) {
			result = scope_AllTopLevelElementsFromAbstractNamespace(module, context, includeHollows,
					includeValueOnlyElements);
		} else {
			// error cases
			if (namespace.eIsProxy()) {
				// name space does not exist -> avoid duplicate error messages
				// (cf. MemberScopingHelper#members(UnknownTypeRef, MemberScopeRequest))
				result = new DynamicPseudoScope();
			} else {
				// name space exists, but imported module does not -> show additional error at location of reference
				result = IScope.NULLSCOPE;
			}
		}
		if (namespace.isDeclaredDynamic() && !(result instanceof DynamicPseudoScope)) {
			return new DynamicPseudoScope(result);
		}
		return result;
	}

	private IScope createScopeForMemberAccess(TypeRef targetTypeRef, EObject context) {
		boolean staticAccess = targetTypeRef instanceof TypeTypeRef;
		boolean structFieldInitMode = targetTypeRef.getTypingStrategy() == TypingStrategy.STRUCTURAL_FIELD_INITIALIZER;
		boolean checkVisibility = true;
		IScope result = memberScopingHelper.createMemberScope(targetTypeRef, context, checkVisibility, staticAccess,
				structFieldInitMode);
		return result;
	}

	private IScope handleDeclMergingForPropertyAccess(RuleEnvironment G,
			ParameterizedPropertyAccessExpression propertyAccess,
			TypeRef typeRef, IScope parent) {

		boolean staticAccess = typeRef instanceof TypeTypeRef;

		Type mergeCandidate = null;
		if (staticAccess) {
			Type staticType = tsh.getStaticType(G, (TypeTypeRef) typeRef, true);
			if (staticType instanceof TClass) {
				mergeCandidate = staticType;
			}
		} else {
			Type declaredType = typeRef.getDeclaredType();
			if (declaredType instanceof TFunction) {
				mergeCandidate = declaredType;
			}
		}

		if (mergeCandidate != null) {
			IScope scopeNamespace = createScopeForMergedNamespaces(propertyAccess, mergeCandidate, null);
			if (scopeNamespace != null) {
				return new MergedScope(scopeNamespace, parent);
			}
		}

		return parent;
	}

	/** Returns <code>parentOrNull</code> unchanged if no namespaces are merged onto "elem". */
	private IScope createScopeForMergedNamespaces(EObject context, Type elem, IScope parentOrNull) {
		return cache.get(context.eResource(),
				() -> createScopeForMergedNamespacesUncached(context, elem, parentOrNull),
				"createScopeForMergedNamespaces", elem);
	}

	private IScope createScopeForMergedNamespacesUncached(EObject context, Type elem, IScope parentOrNull) {
		if (elem instanceof AbstractNamespace) {
			return scope_AllTopLevelElementsFromAbstractNamespace((AbstractNamespace) elem, context, parentOrNull,
					false, true);
		}
		IScope result = parentOrNull;
		if (DeclMergingUtils.mayBeMerged(elem)) {
			Set<Type> mergedElems = declMergingHelper.getMergedElements((N4JSResource) context.eResource(), elem);
			List<TNamespace> mergedNamespaces = toList(filter(mergedElems, TNamespace.class));
			if (mergedNamespaces.size() > 1) {
				Collections.sort(mergedNamespaces,
						Comparator.comparing(ns -> ((InternalEObject) ns).eProxyURI(), new URIUtils.URIComparator()));
			}
			for (TNamespace mergedNS : reverseView(mergedNamespaces)) {
				if (mergedNS != null) {
					result = scope_AllTopLevelElementsFromAbstractNamespace(mergedNS, context, result, false, true);
				}
			}
		}
		return result;
	}

	/** Returns <code>parentOrNull</code> unchanged if no TModules are merged onto "script". */
	private IScope createScopeForMergedTModules(Script script, boolean onlyNamespaces, IScope parentOrNull) {
		if (!DeclMergingUtils.mayBeMerged(script)) {
			return parentOrNull;
		}
		IScope result = parentOrNull;
		N4JSResource resource = (N4JSResource) script.eResource();
		List<AbstractNamespace> mergedTModules = new ArrayList<>(
				declMergingHelper.getMergedElements(resource, script.getModule())); // can be empty
		if (mergedTModules.size() > 1) {
			Collections.sort(mergedTModules,
					Comparator.comparing(m -> ((InternalEObject) m).eProxyURI(), new URIUtils.URIComparator()));
		}
		for (AbstractNamespace mergedTModule : reverseView(mergedTModules)) {
			if (mergedTModule != null) {
				result = locallyKnownTypesScopingHelper.scopeWithLocallyDeclaredElems(mergedTModule, result,
						onlyNamespaces);
			}
		}
		return result;
	}

	/**
	 * Is entered to initially bind "T" in
	 *
	 * <pre>
	 * var x : T;
	 * </pre>
	 *
	 * or other parameterized type references.
	 */
	public IScope getTypeScope(EObject context, boolean fromStaticContext, boolean onlyNamespaces) {
		IScope internal = getTypeScopeInternal(context, fromStaticContext, onlyNamespaces);

		IScope legacy = new ContextAwareTypeScope(internal, context);
		ScopeInfo scopeInfo = new ScopeInfo(internal, legacy, new ContextAwareTypeScopeValidator(context));

		return scopeInfo;
	}

	private IScope getTypeScopeInternal(EObject context, boolean fromStaticContext, boolean onlyNamespaces) {
		if (context instanceof Script) {
			Script script = (Script) context;
			return locallyKnownTypesScopingHelper.scopeWithLocallyDeclaredElems(script, () -> {
				// provide any reference that expects instances of Type as target objects
				IScope parent = delegateGetScope(script,
						TypeRefsPackage.Literals.PARAMETERIZED_TYPE_REF__DECLARED_TYPE);
				parent = createScopeForMergedTModules(script, onlyNamespaces, parent);
				return parent;
			}, onlyNamespaces);

		} else if (context instanceof N4NamespaceDeclaration) {
			N4NamespaceDeclaration ns = (N4NamespaceDeclaration) context;
			IScope parent = getTypeScopeInternal(ns.eContainer(), fromStaticContext, onlyNamespaces);
			TNamespace tns = ns.getDefinedNamespace();
			if (tns != null && DeclMergingUtils.mayBeMerged(tns)) {
				parent = createScopeForMergedNamespaces(tns, tns, parent);
			}
			return locallyKnownTypesScopingHelper.scopeWithLocallyDeclaredElems(ns, parent, onlyNamespaces);
		} else if (context instanceof TNamespace) {
			TNamespace tns = (TNamespace) context;
			var parent = getTypeScopeInternal(tns.eContainer(), fromStaticContext, onlyNamespaces);
			if (DeclMergingUtils.mayBeMerged(tns)) {
				parent = createScopeForMergedNamespaces(tns, tns, parent);
			}
			return locallyKnownTypesScopingHelper.scopeWithLocallyDeclaredElems(tns, parent, onlyNamespaces);
		} else if (context instanceof TModule) {
			TModule module = (TModule) context;
			Script script = (Script) module.getAstElement();
			return getTypeScopeInternal(script, fromStaticContext, onlyNamespaces);
		} else if (context instanceof N4FieldDeclaration) {
			N4FieldDeclaration fd = (N4FieldDeclaration) context;
			boolean isStaticContext = fd.isStatic();
			// use new static access status for parent scope
			return getTypeScopeInternal(fd.eContainer(), isStaticContext, onlyNamespaces);
		} else if (context instanceof N4FieldAccessor) {
			N4FieldAccessor fa = (N4FieldAccessor) context;
			boolean isStaticContext = fa.isStatic();
			// use new static access status for parent scope
			return getTypeScopeInternal(fa.eContainer(), isStaticContext, onlyNamespaces);
		} else if (context instanceof TypeDefiningElement) {
			TypeDefiningElement tde = (TypeDefiningElement) context;
			boolean isStaticContext = tde instanceof N4MemberDeclaration && ((N4MemberDeclaration) tde).isStatic();
			// use new static access status for parent scope
			IScope parent = getTypeScopeInternal(tde.eContainer(), isStaticContext, onlyNamespaces);
			Type polyfilledOrOriginalType = sourceElementExtensions.getTypeOrPolyfilledType(tde);

			// use old static access status for current scope
			return locallyKnownTypesScopingHelper.scopeWithTypeAndItsTypeVariables(parent, polyfilledOrOriginalType,
					fromStaticContext);
		} else if (context instanceof TStructMethod) {
			IScope parent = getTypeScopeInternal(context.eContainer(), fromStaticContext, onlyNamespaces);
			return locallyKnownTypesScopingHelper.scopeWithTypeVarsOfTStructMethod(parent, (TStructMethod) context);
		} else if (context instanceof FunctionTypeExpression) {
			IScope parent = getTypeScopeInternal(context.eContainer(), fromStaticContext, onlyNamespaces);
			return locallyKnownTypesScopingHelper.scopeWithTypeVarsOfFunctionTypeExpression(parent,
					(FunctionTypeExpression) context);
		} else {
			EObject container = context.eContainer();

			// handle special areas inside a polyfill that should *not* get the usual polyfill handling implemented
			// in the above case for "TypeDefiningElement":
			if (container instanceof N4ClassifierDeclaration) {
				N4ClassifierDeclaration cd = (N4ClassifierDeclaration) container;
				if (N4JSLanguageUtils.isNonStaticPolyfill(cd) || N4JSLanguageUtils.isStaticPolyfill(cd)) {
					if (cd.getTypeVars().contains(context)) {
						// area #1: upper/lower bound of type parameter of polyfill, e.g. the 2nd "T" in:
						// @@StaticPolyfillModule
						// @StaticPolyfill export public class ToBeFilled<T,S extends T> extends ToBeFilled<T,S> {}
						IScope parent = getTypeScopeInternal(context.eContainer(), false, onlyNamespaces);
						return locallyKnownTypesScopingHelper.scopeWithTypeAndItsTypeVariables(parent,
								cd.getDefinedType(), fromStaticContext);
					} else if (toList(cd.getSuperClassifierRefs()).contains(context)) {
						// area #2: super type reference of polyfill, e.g. everything after "extends" in:
						// @@StaticPolyfillModule
						// @StaticPolyfill export public class ToBeFilled<T> extends ToBeFilled<T> {}
						Script script = EcoreUtil2.getContainerOfType(cd, Script.class);
						IScope globalScope = delegateGetScope(script,
								TypeRefsPackage.Literals.PARAMETERIZED_TYPE_REF__DECLARED_TYPE);
						IScope parent = locallyKnownTypesScopingHelper.scopeWithLocallyKnownTypesForPolyfillSuperRef(
								script, globalScope,
								cd.getDefinedType());
						return parent;
					}
				}
			}

			return getTypeScopeInternal(container, fromStaticContext, onlyNamespaces);
		}
	}

	/**
	 * Returns ancestor of given type, or null, if no such container exists. Note that this method is slightly different
	 * from EcoreUtil2::getContainerOfType, as it only returns a container and not the element itself. That is, ancestor
	 * is not reflexive here.
	 */
	private <T extends EObject> T ancestor(EObject obj, Class<T> ancestorType) {
		if (obj == null) {
			return null;
		}
		return EcoreUtil2.getContainerOfType(obj.eContainer(), ancestorType);
	}

	private IScope getN4JSXScope(EObject context, EReference reference) {
		IScope jsxPropertyAttributeScope = getJSXPropertyAttributeScope(context, reference);
		if (jsxPropertyAttributeScope != IScope.NULLSCOPE) {
			return jsxPropertyAttributeScope;
		}

		IScope jsxElementScope = getJSXElementScope(context, reference);
		if (jsxElementScope != IScope.NULLSCOPE) {
			return jsxElementScope;
		}

		// delegate to host -> N4JS scope
		return getN4JSScope(context, reference);
	}

	/** Returns scope for the {@link JSXPropertyAttribute} (obtained from context) or {@link IScope#NULLSCOPE} */
	private IScope getJSXPropertyAttributeScope(EObject context, EReference reference) {
		if (reference == N4JSPackage.Literals.JSX_PROPERTY_ATTRIBUTE__PROPERTY) {
			if (context instanceof JSXPropertyAttribute) {
				JSXElement jsxElem = (JSXElement) context.eContainer();
				TypeRef propsTypeRef = reactHelper.getPropsType(jsxElem);
				boolean checkVisibility = true;
				boolean staticAccess = false;
				boolean structFieldInitMode = false;
				if (propsTypeRef != null) {
					// Prevent "Cannot resolve to element" error message of unknown attributes since
					// we want to issue a warning instead
					TypeRef propsTypeRefUB = ts.upperBoundWithReopenAndResolveTypeVars(newRuleEnvironment(context),
							propsTypeRef);
					IScope memberScope = memberScopingHelper.createMemberScope(propsTypeRefUB, context, checkVisibility,
							staticAccess, structFieldInitMode);
					return new DynamicPseudoScope(memberScope);
				} else {
					IScope scope = getN4JSScope(context, reference);
					return new DynamicPseudoScope(scope);
				}
			}
		}
		return IScope.NULLSCOPE;
	}

	/** Returns scope for the JSXElement (obtained from context) or {@link IScope#NULLSCOPE} */
	private IScope getJSXElementScope(EObject context, EReference reference) {

		if (EcoreUtil2.getContainerOfType(context, JSXElementName.class) == null)
			return IScope.NULLSCOPE;

		IScope scope = getN4JSScope(context, reference);
		return new DynamicPseudoScope(scope);
	}
}
