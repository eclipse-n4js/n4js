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
package org.eclipse.n4js.scoping.imports;

import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.findFirst;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.n4JS.DefaultImportSpecifier;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.ImportDeclaration;
import org.eclipse.n4js.n4JS.ImportSpecifier;
import org.eclipse.n4js.n4JS.NamedImportSpecifier;
import org.eclipse.n4js.n4JS.NamespaceImportSpecifier;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.resource.N4JSCache;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.scoping.ExportedElementsCollector;
import org.eclipse.n4js.scoping.N4JSScopeProvider;
import org.eclipse.n4js.scoping.accessModifiers.AbstractTypeVisibilityChecker;
import org.eclipse.n4js.scoping.accessModifiers.AbstractTypeVisibilityChecker.TypeVisibility;
import org.eclipse.n4js.scoping.accessModifiers.InvisibleTypeOrVariableDescription;
import org.eclipse.n4js.scoping.accessModifiers.TypeVisibilityChecker;
import org.eclipse.n4js.scoping.accessModifiers.VariableVisibilityChecker;
import org.eclipse.n4js.scoping.builtin.BuiltInTypeScope;
import org.eclipse.n4js.scoping.builtin.GlobalObjectScope;
import org.eclipse.n4js.scoping.builtin.NoPrimitiveTypesScope;
import org.eclipse.n4js.scoping.members.MemberScope.MemberScopeFactory;
import org.eclipse.n4js.scoping.utils.LocallyKnownTypesScopingHelper;
import org.eclipse.n4js.scoping.utils.MultiImportedElementsMap;
import org.eclipse.n4js.scoping.utils.ScopeSnapshotHelper;
import org.eclipse.n4js.scoping.utils.UberParentScope;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.ModuleNamespaceVirtualType;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TDynamicElement;
import org.eclipse.n4js.ts.types.TExportableElement;
import org.eclipse.n4js.ts.types.TFunction;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.TVariable;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.utils.ResourceType;
import org.eclipse.n4js.validation.IssueCodes;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.EObjectDescription;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.impl.AliasedEObjectDescription;
import org.eclipse.xtext.scoping.IScope;

import com.google.common.base.Optional;
import com.google.common.base.Throwables;
import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Helper for {@link N4JSScopeProvider N4JSScopeProvider}
 * {@link N4JSScopeProvider#scope_IdentifierRef_id(IdentifierRef, EReference) .scope_IdentifierRef_id()}, also used by
 * helper {@link LocallyKnownTypesScopingHelper LocallyKnownTypesScopingHelper}.
 */
@Singleton
public class ImportedElementsScopingHelper {
	/** internal helper collection type */
	static public class IEODesc2ISpec extends HashMap<IEObjectDescription, ImportSpecifier> {
		/// empty
	}

	private final static Logger LOGGER = Logger.getLogger(ImportedElementsScopingHelper.class);

	@Inject
	N4JSCache cache;

	@Inject
	private TypeVisibilityChecker typeVisibilityChecker;

	@Inject
	private IQualifiedNameProvider qualifiedNameProvider;

	@Inject
	private VariableVisibilityChecker variableVisibilityChecker;

	@Inject
	private ImportedElementsMap.Provider elementsMapProvider;

	@Inject
	private MemberScopeFactory memberScopeFactory;

	@Inject
	private ScopeSnapshotHelper scopesHelper;

	@Inject
	private ExportedElementsCollector exportedElementsCollector;

	/***/
	public IScope getImportedTypes(IScope parentScope, Script script) {
		IScope scriptScope = cache.get(script.eResource(), () -> findImportedElements(script, parentScope, true, false),
				"importedTypes", script);
		return scriptScope;
	}

	/***/
	public IScope getImportedValues(IScope parentScope, Script script) {
		IScope scriptScope = cache.get(script.eResource(), () -> {
			// filter out primitive types in next line (otherwise code like "let x = int;" would be allowed)
			NoPrimitiveTypesScope noPrimitiveBuiltIns = new NoPrimitiveTypesScope(
					BuiltInTypeScope.get(script.eResource().getResourceSet()));
			UberParentScope uberParent = new UberParentScope("ImportedElementsScopingHelper-uberParent",
					noPrimitiveBuiltIns, parentScope);
			IScope globalObjectScope = getGlobalObjectProperties(uberParent, script);
			IScope result = findImportedElements(script, globalObjectScope, false, true);
			return result;
		}, "importedValues", script);
		return scriptScope;
	}

	/**
	 * Creates a new QualifiedNamed for the given named import specifier.
	 *
	 * Determines the local name of the imported element based on the given import specifier.
	 */
	private QualifiedName createQualifiedNameForAlias(NamedImportSpecifier specifier) {
		String importedName = (specifier.isDefaultImport())
				// we got a default import of the form: import localName from "some/module"
				// -> use the string "localName" (but this is not the alias property!)
				? specifier.getImportedElementAsText()
				: specifier.getAlias() != null ? specifier.getAlias() : specifier.getImportedElementAsText();

		return QualifiedName.create(importedName);
	}

	private QualifiedName createImportedQualifiedTypeName(Type type) {
		return QualifiedName.create(getImportedName(type));
	}

	private String getImportedName(Type type) {
		return type.getName();
	}

	private QualifiedName createImportedQualifiedTypeName(String namespace, Type type) {
		return QualifiedName.create(namespace, getImportedName(type));
	}

	private IScope findImportedElements(Script script, IScope parentScope, boolean includeHollows,
			boolean includeValueOnlyElements) {
		N4JSResource contextResource = (N4JSResource) script.eResource();
		List<ImportDeclaration> imports = toList(filter(script.getScriptElements(), ImportDeclaration.class));

		if (imports.isEmpty()) {
			return parentScope;
		}

		/**
		 * broken/invisible imported eObjects descriptions - in case of broken state of imports this can be
		 * {@link AmbiguousImportDescription} - in case of alias/namespace imports multiple imported elements can have
		 * the same qualified name
		 */
		MultiImportedElementsMap invalidImports = new MultiImportedElementsMap();
		/**
		 * valid imported eObjects descriptions (in case of broken state of imports this can be
		 * {@link AmbiguousImportDescription})
		 */
		ImportedElementsMap validImports = elementsMapProvider.get(script);
		IEODesc2ISpec originatorMap = new IEODesc2ISpec();

		for (ImportDeclaration imp : imports) {
			TModule module = imp == null ? null : imp.getModule();
			if (imp != null && module != null) {

				Iterable<IEObjectDescription> topLevelElements = exportedElementsCollector.getExportedElements(module,
						contextResource, Optional.of(imp), includeHollows, includeValueOnlyElements);
				IScope tleScope = scopesHelper.scopeFor("scope_AllTopLevelElementsFromModule", module, IScope.NULLSCOPE,
						false, topLevelElements);

				for (ImportSpecifier specifier : imp.getImportSpecifiers()) {
					if (specifier instanceof NamedImportSpecifier) {
						processNamedImportSpecifier((NamedImportSpecifier) specifier, imp, contextResource,
								originatorMap, validImports,
								invalidImports, includeValueOnlyElements, tleScope);
					} else if (specifier instanceof NamespaceImportSpecifier) {
						processNamespaceSpecifier((NamespaceImportSpecifier) specifier, imp, script, contextResource,
								originatorMap, validImports,
								invalidImports, includeValueOnlyElements);
					}
				}
			}
		}

		// local broken elements are hidden by parent scope, both are hidden by valid local elements
		IScope invalidLocalScope = scopesHelper.scopeFor("findImportedElements-invalidImports", script,
				invalidImports.values());
		IScope localValidScope = scopesHelper.scopeFor("findImportedElements-validImports", script, parentScope,
				validImports.values());
		UberParentScope importScope = new UberParentScope("findImportedElements-uberParent", localValidScope,
				invalidLocalScope);
		return new OriginAwareScope(script, importScope, originatorMap);
	}

	private void processNamedImportSpecifier(NamedImportSpecifier specifier, ImportDeclaration imp,
			Resource contextResource, IEODesc2ISpec originatorMap,
			ImportedElementsMap validImports,
			ImportedElementsMap invalidImports, boolean includeValueOnlyElements, IScope tleScope) {

		TExportableElement element = null;
		if (specifier.isDeclaredDynamic()) {
			element = findFirst(((N4JSResource) specifier.eResource()).getModule().getInternalDynamicElements(),
					de -> de.getAstElement() == specifier);
		} else {
			String name = (specifier instanceof DefaultImportSpecifier)
					? "default"
					: specifier.getImportedElementAsText();
			QualifiedName qName = QualifiedName.create(name);

			IEObjectDescription importedElem = tleScope.getSingleElement(qName);
			if (importedElem != null && importedElem.getEObjectOrProxy() instanceof TExportableElement) {
				element = (TExportableElement) importedElem.getEObjectOrProxy();
			}
		}

		if (element != null && !element.eIsProxy()) {

			if (!includeValueOnlyElements && isValueOnlyFrom(element, imp)) {
				return;
			}

			QualifiedName importedQName = createQualifiedNameForAlias(specifier);
			TypeVisibility typeVisibility = isVisible(contextResource, element);
			if (typeVisibility.visibility) {

				addNamedImports(specifier, element, importedQName,
						originatorMap, validImports);

				QualifiedName originalName = QualifiedName.create(element.getName());

				if (specifier.getAlias() != null) {
					handleAliasedAccess(element, originalName, importedQName.toString(), invalidImports);
				}
			} else {
				handleInvisible(element, invalidImports, importedQName, typeVisibility.accessModifierSuggestion,
						originatorMap, specifier);
			}
		}
	}

	private void addNamedImports(NamedImportSpecifier specifier, TExportableElement element, QualifiedName importedName,
			IEODesc2ISpec originatorMap, ImportedElementsMap validImports) {
		IEObjectDescription ieod = putOrError(validImports, element, importedName, IssueCodes.IMP_AMBIGUOUS.name());
		putWithOrigin(originatorMap, ieod, specifier);
	}

	private void processNamespaceSpecifier(
			NamespaceImportSpecifier specifier,
			ImportDeclaration imp,
			Script script,
			Resource contextResource,
			IEODesc2ISpec originatorMap,
			ImportedElementsMap validImports,
			ImportedElementsMap invalidImports,
			boolean includeValueOnlyElements) {
		if (specifier.getAlias() == null) {
			return; // if broken code, e.g. "import * as 123 as N from "some/Module""
		}
		if (script.getModule() == null) {
			return; // when reconciliation of TModule fails due to hash mismatch
		}

		// add namespace to scope
		String namespaceName = specifier.getAlias();
		QualifiedName namespaceQName = QualifiedName.create(namespaceName);
		TModule sModule = script.getModule();

		Iterable<Type> allTypes = Iterables.concat(sModule.getInternalTypes(), sModule.getExposedInternalTypes());

		Type namespaceType = findFirst(allTypes, (interType) -> interType instanceof ModuleNamespaceVirtualType &&
				((ModuleNamespaceVirtualType) interType).getModule() == imp.getModule());

		if (namespaceType == null) {
			// TODO GH-2002 remove this temporary debug logging
			StringBuilder sb = new StringBuilder();
			sb.append(
					"contextResource?.getURI(): " + (contextResource == null ? null : contextResource.getURI()) + "\n");
			sb.append("specifier.definedType: " + specifier.getDefinedType() + "\n");
			sb.append("imp.module: " + imp.getModule() + "\n");
			sb.append("script.module: " + sModule + "\n");
			sb.append("script.module.isPreLinkingPhase: " + sModule.isPreLinkingPhase() + "\n");
			sb.append("script.module.isReconciled: " + sModule.isReconciled() + "\n");
			sb.append("script.module.internalTypes.size: " + sModule.getInternalTypes().size() + "\n");
			sb.append("script.module.exposedInternalTypes.size: " + sModule.getExposedInternalTypes().size() + "\n");
			for (Type type : allTypes) {
				if (type instanceof ModuleNamespaceVirtualType) {
					sb.append("type[n].module: " + ((ModuleNamespaceVirtualType) type).getModule() + "\n");
				}
			}
			sb.append("\n");
			sb.append(Throwables.getStackTraceAsString(new IllegalStateException()));
			LOGGER.error("namespaceType not found\n" + sb.toString());
			return;
		}
		IEObjectDescription ieodx = putOrError(validImports, namespaceType, namespaceQName,
				IssueCodes.IMP_AMBIGUOUS.name());
		putWithOrigin(originatorMap, ieodx, specifier);

		if (includeValueOnlyElements) {
			// add vars to namespace
			// (this is *only* about adding some IEObjectDescriptionWithError to improve error messages)
			for (TVariable importedVar : imp.getModule().getExportedVariables()) {
				TypeVisibility varVisibility = variableVisibilityChecker.isVisible(contextResource, importedVar);
				String varName = importedVar.getName();
				QualifiedName qn = QualifiedName.create(namespaceName, varName);
				if (varVisibility.visibility) {
					QualifiedName originalName = QualifiedName.create(varName);
					if (!invalidImports.containsElement(originalName)) {
						handleNamespacedAccess(importedVar, originalName, qn, invalidImports);
					}
				}
			}
			// add functions to namespace
			// (this is *only* about adding some IEObjectDescriptionWithError to improve error messages)
			for (TFunction importedFun : imp.getModule().getFunctions()) {
				TypeVisibility varVisibility = typeVisibilityChecker.isVisible(contextResource, importedFun);
				String varName = importedFun.getName();
				QualifiedName qn = QualifiedName.create(namespaceName, varName);
				if (varVisibility.visibility) {
					QualifiedName originalName = QualifiedName.create(varName);
					if (!invalidImports.containsElement(originalName)) {
						handleNamespacedAccess(importedFun, originalName, qn, invalidImports);
					}
				}
			}
		}

		// add types
		// (this is *only* about adding some IEObjectDescriptionWithError to improve error messages)
		for (Type importedType : imp.getModule().getTypes()) {
			TypeVisibility typeVisibility = typeVisibilityChecker.isVisible(contextResource, importedType);

			QualifiedName qn = createImportedQualifiedTypeName(namespaceName, importedType);
			if (typeVisibility.visibility) {
				QualifiedName originalName = createImportedQualifiedTypeName(importedType);
				if (!invalidImports.containsElement(originalName)) {
					handleNamespacedAccess(importedType, originalName, qn, invalidImports);
				}
			}
		}
	}

	private void handleAliasedAccess(IdentifiableElement element, QualifiedName originalName, String importedName,
			ImportedElementsMap invalidImports) {
		PlainAccessOfAliasedImportDescription invalidAccess = new PlainAccessOfAliasedImportDescription(
				EObjectDescription.create(originalName, element),
				importedName);
		invalidImports.put(originalName, invalidAccess);
		// TODO IDEBUG-702 originatorMap.putWithOrigin(invalidAccess, specifier)
	}

	private void handleNamespacedAccess(IdentifiableElement importedType, QualifiedName originalName, QualifiedName qn,
			ImportedElementsMap invalidImports) {
		PlainAccessOfNamespacedImportDescription invalidAccess = new PlainAccessOfNamespacedImportDescription(
				EObjectDescription.create(originalName, importedType), qn);
		invalidImports.put(originalName, invalidAccess);
		// TODO IDEBUG-702 originatorMap.putWithOrigin(invalidAccess, specifier)
	}

	private void handleInvisible(IdentifiableElement importedElement, ImportedElementsMap invalidImports,
			QualifiedName qn,
			String visibilitySuggestion, IEODesc2ISpec originatorMap, ImportSpecifier specifier) {
		// TODO IDEBUG-702 val invalidAccess = new InvisibleTypeOrVariableDescription(EObjectDescription.create(qn,
		// importedElement))
		IEObjectDescription invalidAccess = putOrError(invalidImports, importedElement, qn, null);
		addAccessModifierSuggestion(invalidAccess, visibilitySuggestion);
		putWithOrigin(originatorMap, invalidAccess, specifier);
	}

	/**
	 * Add the description to the orginatorMap and include trace to the specifier in special Error-Aware
	 * IEObjectDesciptoins like AmbigousImportDescriptions.
	 */
	private void putWithOrigin(HashMap<IEObjectDescription, ImportSpecifier> originiatorMap,
			IEObjectDescription description, ImportSpecifier specifier) {
		originiatorMap.put(description, specifier);
		if (description instanceof AmbiguousImportDescription) {
			AmbiguousImportDescription aid = (AmbiguousImportDescription) description;
			aid.getOriginatingImports().add(specifier);
			// Handling of wrapped delegatee, since this information will not be available in other cases:
			ImportSpecifier firstPlaceSpec = originiatorMap.get(aid.delegate());
			// add only if not there yet:
			if (firstPlaceSpec != null && !aid.getOriginatingImports().contains(firstPlaceSpec)) {
				aid.getOriginatingImports().add(firstPlaceSpec);
			}
		}
	}

	private boolean isValueOnlyFrom(IdentifiableElement element, ImportDeclaration imp) {
		if (imp == null || imp.getModule() == null) {
			return false;
		}
		if (imp.getModule().getFunctions().contains(element)) {
			return true;
		}
		if (imp.getModule().getExportedVariables().contains(element)) {
			return true;
		}
		return false;
	}

	private AbstractTypeVisibilityChecker.TypeVisibility isVisible(Resource contextResource,
			IdentifiableElement element) {
		if (element instanceof TMember && ResourceType.getResourceType(element) == ResourceType.DTS)
			return new AbstractTypeVisibilityChecker.TypeVisibility(true);
		else if (element instanceof Type)
			return typeVisibilityChecker.isVisible(contextResource, (Type) element);
		else if (element instanceof TVariable)
			return variableVisibilityChecker.isVisible(contextResource, (TVariable) element);
		else if (element instanceof TDynamicElement)
			return new AbstractTypeVisibilityChecker.TypeVisibility(true);
		else
			return new AbstractTypeVisibilityChecker.TypeVisibility(false);
	}

	/**
	 * Returns {@code true} if an import of the given {@link IEObjectDescription} should be regarded as ambiguous with
	 * the given {@link IdentifiableElement}.
	 */
	@SuppressWarnings("unused")
	protected boolean isAmbiguous(IEObjectDescription existing, IdentifiableElement element) {
		// make sure ambiguity is only detected in case of the same imported version of a type
		return true;
	}

	private IEObjectDescription putOrError(ImportedElementsMap result,
			IdentifiableElement element, QualifiedName importedName, String issueCode) {
		// TODO IDEBUG-702 refactor InvisibleTypeOrVariableDescription / AmbiguousImportDescription relation
		IEObjectDescription ret = null;
		List<IEObjectDescription> existing = toList(result.getElements(importedName));

		if (!existing.isEmpty() && existing.get(0) != null) {
			if (issueCode != null) {
				if (existing instanceof AmbiguousImportDescription) {
					((AmbiguousImportDescription) existing).getElements().add(element);
					ret = (AmbiguousImportDescription) existing;
				} else {
					AmbiguousImportDescription error = new AmbiguousImportDescription(existing.get(0), issueCode,
							element);
					result.put(importedName, error);
					error.getElements().add(element);
					ret = error;
				}
			}
		} else if (issueCode == null) {
			ret = createDescription(importedName, element);
			ret = new InvisibleTypeOrVariableDescription(ret);
			result.put(importedName, ret);
		} else {
			ret = createDescription(importedName, element);
			result.put(importedName, ret);
		}
		return ret;
	}

	private IEObjectDescription createDescription(QualifiedName name, IdentifiableElement element) {
		if (!Objects.equals(name.getLastSegment(), element.getName())) {
			var qn = qualifiedNameProvider.getFullyQualifiedName(element);
			if (qn == null) {
				// non-directly-exported variable / function / type alias that is exported under an alias via a separate
				// export declaration:
				QualifiedName base = qualifiedNameProvider.getFullyQualifiedName(element.getContainingModule());
				qn = base == null ? null : base.append(element.getName());
			}
			return new AliasedEObjectDescription(name, EObjectDescription.create(qn, element));
		} else {
			return EObjectDescription.create(name, element);
		}
	}

	/**
	 * global object scope indirectly cached, as this method is only called by getImportedIdentifiables (which is
	 * cached)
	 */
	private IScope getGlobalObjectProperties(IScope parent, EObject context) {
		TClass globalObject = GlobalObjectScope.get(context.eResource().getResourceSet()).getGlobalObject();
		return memberScopeFactory.create(parent, globalObject, context, false, false, false);
	}

	private void addAccessModifierSuggestion(IEObjectDescription description, String suggestion) {
		if (description instanceof InvisibleTypeOrVariableDescription) {
			((InvisibleTypeOrVariableDescription) description).setAccessModifierSuggestion(suggestion);
		}
	}
}
