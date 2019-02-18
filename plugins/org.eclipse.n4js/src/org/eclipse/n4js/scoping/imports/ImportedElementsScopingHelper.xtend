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
package org.eclipse.n4js.scoping.imports

import com.google.inject.Inject
import com.google.inject.Singleton
import java.util.HashMap
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.n4js.n4JS.ImportDeclaration
import org.eclipse.n4js.n4JS.ImportSpecifier
import org.eclipse.n4js.n4JS.NamedImportSpecifier
import org.eclipse.n4js.n4JS.NamespaceImportSpecifier
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.n4idl.versioning.VersionHelper
import org.eclipse.n4js.resource.N4JSEObjectDescription
import org.eclipse.n4js.scoping.N4JSScopeProvider
import org.eclipse.n4js.scoping.accessModifiers.AbstractTypeVisibilityChecker
import org.eclipse.n4js.scoping.accessModifiers.InvisibleTypeOrVariableDescription
import org.eclipse.n4js.scoping.accessModifiers.TypeVisibilityChecker
import org.eclipse.n4js.scoping.accessModifiers.VariableVisibilityChecker
import org.eclipse.n4js.scoping.builtin.GlobalObjectScope
import org.eclipse.n4js.scoping.builtin.NoPrimitiveTypesScope
import org.eclipse.n4js.scoping.members.MemberScope.MemberScopeFactory
import org.eclipse.n4js.scoping.utils.LocallyKnownTypesScopingHelper
import org.eclipse.n4js.scoping.utils.MergedScope
import org.eclipse.n4js.scoping.utils.ScopesHelper
import org.eclipse.n4js.ts.scoping.builtin.BuiltInTypeScope
import org.eclipse.n4js.ts.typeRefs.Versionable
import org.eclipse.n4js.ts.types.IdentifiableElement
import org.eclipse.n4js.ts.types.ModuleNamespaceVirtualType
import org.eclipse.n4js.ts.types.TExportableElement
import org.eclipse.n4js.ts.types.TVariable
import org.eclipse.n4js.ts.types.Type
import org.eclipse.n4js.ts.versions.VersionableUtils
import org.eclipse.n4js.validation.IssueCodes
import org.eclipse.n4js.validation.JavaScriptVariantHelper
import org.eclipse.xtext.naming.IQualifiedNameProvider
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.resource.IEObjectDescription
import org.eclipse.xtext.resource.impl.AliasedEObjectDescription
import org.eclipse.xtext.scoping.IScope
import org.eclipse.xtext.scoping.impl.SimpleScope
import org.eclipse.xtext.util.IResourceScopeCache

/** internal helper collection type */
class IEODesc2ISpec extends HashMap<IEObjectDescription, ImportSpecifier> {}

/**
 * Helper for {@link N4JSScopeProvider N4JSScopeProvider}
 * {@link N4JSScopeProvider#scope_IdentifierRef_id(org.eclipse.n4js.n4JS.VariableEnvironmentElement) .scope_IdentifierRef_id()},
 * also used by helper {@link LocallyKnownTypesScopingHelper LocallyKnownTypesScopingHelper}.
 */
@Singleton
class ImportedElementsScopingHelper {

	@Inject
	IResourceScopeCache cache

	@Inject
	private TypeVisibilityChecker typeVisibilityChecker
	
	@Inject
	private IQualifiedNameProvider qualifiedNameProvider

	@Inject
	private VariableVisibilityChecker variableVisibilityChecker

	@Inject
	private ImportedElementsMap.Provider elementsMapProvider

	@Inject
	private MemberScopeFactory memberScopeFactory

	@Inject
	private ScopesHelper scopesHelper;

	@Inject
	private JavaScriptVariantHelper variantHelper;

	@Inject
	private VersionHelper versionHelper;

	def IScope getImportedIdentifiables(IScope parentScope, Script script) {
		val IScope scriptScope = cache.get(script -> 'importedIdentifiables', script.eResource) [|
			// TODO parentScope (usually global scope) arg is not part of cache key but used in value!
			// filter out primitive types in next line (otherwise code like "let x = int;" would be allowed)
			val builtInTypes = new NoPrimitiveTypesScope(BuiltInTypeScope.get(script.eResource.resourceSet))
			val globalObjectScope = getGlobalObjectProperties(new MergedScope(parentScope, builtInTypes), script)
			val result = script.findImportedElements(globalObjectScope, true);
			return result;
		]
		return scriptScope
	}

	def IScope getImportedTypes(IScope parentScope, Script script) {
//		val IScope scriptScope = cache.get(script -> 'importedTypes', script.eResource) [|
			val result = script.findImportedElements(parentScope, false);
			return result;
//		]
//		return scriptScope
	}

	/**
	 * Creates a new QualifiedNamed for the given named import specifier.
	 *
	 * Determines the local name of the imported element based on the given import specifier.
	 */
	private def QualifiedName createQualifiedNameForAlias(NamedImportSpecifier specifier,
		TExportableElement importedElement) {
		val importedName = if (specifier.isDefaultImport) {
				// we got a default import of the form: import localName from "some/module"
				// -> use the string 'localName' (but this is not the alias property!)
				specifier.importedElementAsText
			} else {
				specifier.alias ?: importedElement.exportedName ?: importedElement.name
		};
		return QualifiedName.create(importedName)
	}

	private def QualifiedName createImportedQualifiedTypeName(Type type) {
		return QualifiedName.create(getImportedName(type));
	}

	private def String getImportedName(Type type) {
		return type.exportedName ?: type.name;
	}

	private def QualifiedName createImportedQualifiedTypeName(String namespace, Type type) {
		return QualifiedName.create(namespace, getImportedName(type));
	}

	private def IScope findImportedElements(Script script, IScope parentScope, boolean includeVariables) {
		val contextResource = script.eResource;
		val imports = script.scriptElements.filter(ImportDeclaration)

		if (imports.empty) return parentScope

		/** broken/invisible imported eObjects descriptions (in case of broken state of imports this can be {@link AmbiguousImportDescription})*/
		val invalidImports = elementsMapProvider.get(script);
		/** valid imported eObjects descriptions (in case of broken state of imports this can be {@link AmbiguousImportDescription})*/
		val validImports = elementsMapProvider.get(script);
		val originatorMap = new IEODesc2ISpec

		for (imp : imports) {
			if (imp?.module !== null) {
				for (specifier : imp.importSpecifiers) {
					switch (specifier) {
						NamedImportSpecifier: {
							processNamedImportSpecifier(specifier, imp, contextResource, originatorMap, validImports,
								invalidImports, includeVariables)
						}
						NamespaceImportSpecifier: {
							processNamespaceSpecifier(specifier, imp, script, contextResource, originatorMap, validImports,
								invalidImports, includeVariables)
						}
					}
				}
			}
		}

		// local broken elements are hidden by parent scope, both are hidden by valid local elements
		val invalidLocalScope = new SimpleScope(invalidImports.values)
		val localBaseScope = new MergedScope(invalidLocalScope, parentScope)
		val localValidScope = scopesHelper.mapBasedScopeFor(script, localBaseScope, validImports.values)

		return new OriginAwareScope(localValidScope, originatorMap);
	}

	protected def void processNamedImportSpecifier(NamedImportSpecifier specifier, ImportDeclaration imp,
		Resource contextResource, IEODesc2ISpec originatorMap,
		ImportedElementsMap validImports,
		ImportedElementsMap invalidImports, boolean importVariables) {
		val element = specifier.importedElement
		if (element !== null && !element.eIsProxy) {

			if (!importVariables && element.isVariableFrom(imp)) {
				return;
			}

			val importedQName = createQualifiedNameForAlias(specifier, element);
			val typeVisibility = isVisible(contextResource, element);
			if (typeVisibility.visibility) {

				addNamedImports(specifier, element, importedQName,
					originatorMap, validImports);

				val originalName = QualifiedName.create(element.name)

				if (specifier.alias !== null && !invalidImports.containsElement(originalName)) {
					element.handleAliasedAccess(originalName, importedQName.toString, invalidImports, originatorMap, specifier)
				}
			} else {
				element.handleInvisible(invalidImports, importedQName, typeVisibility.accessModifierSuggestion,
					originatorMap, specifier)
			}
		}
	}

	private def void addNamedImports(NamedImportSpecifier specifier, TExportableElement element, QualifiedName importedName,
		IEODesc2ISpec originatorMap, ImportedElementsMap validImports) {
		if (variantHelper.allowVersionedTypes(specifier) && VersionableUtils.isTVersionable(element)) {
			// If the current context supports versioned types, import all versions of the
			// specified type.
			versionHelper.findTypeVersions(element as Type).forEach[ type |
				val description = validImports.putOrError(type, importedName,
					IssueCodes.IMP_AMBIGUOUS
				);
				originatorMap.putWithOrigin(description, specifier);
			]
		} else {
			// Otherwise only import the type which was linked to the import specifier
			// at link-time.
			val ieod = validImports.putOrError(element, importedName, IssueCodes.IMP_AMBIGUOUS);
			originatorMap.putWithOrigin(ieod, specifier)
		}
	}

	private def void processNamespaceSpecifier(
		NamespaceImportSpecifier specifier,
		ImportDeclaration imp,
		Script script,
		Resource contextResource,
		IEODesc2ISpec originatorMap,
		ImportedElementsMap validImports,
		ImportedElementsMap invalidImports,
		boolean importVariables
	) {
		if (specifier.alias === null) {
			return; // if broken code, e.g. "import * as 123 as N from 'some/Module'"
		}

		// add namespace to scope
		val namespaceName = specifier.alias;
		val namespaceQName = QualifiedName.create(namespaceName)
		val Type namespaceType = (script.module.internalTypes + script.module.exposedInternalTypes).findFirst [ interType |
			interType instanceof ModuleNamespaceVirtualType &&
				(interType as ModuleNamespaceVirtualType).module === imp.module
		]
		val ieodx = validImports.putOrError(namespaceType, namespaceQName, IssueCodes.IMP_AMBIGUOUS)
		originatorMap.putWithOrigin(ieodx, specifier)

		if (importVariables) {
			// add vars to namespace
			// (this is *only* about adding some IEObjectDescriptionWithError to improve error messages)
			for (importedVar : imp.module.variables) {
				val varVisibility = variableVisibilityChecker.isVisible(contextResource, importedVar);
				val varName = importedVar.exportedName
				val qn = QualifiedName.create(namespaceName, varName)
				if (varVisibility.visibility) {
					val originalName = QualifiedName.create(varName)
					if (!invalidImports.containsElement(originalName)) {
						importedVar.handleNamespacedAccess(originalName, qn, invalidImports, originatorMap,
							specifier)
					}
				}
			}
		}

		// add types
		// (this is *only* about adding some IEObjectDescriptionWithError to improve error messages)
		for (importedType : imp.module.topLevelTypes) {
			val typeVisibility = typeVisibilityChecker.isVisible(contextResource, importedType);

			val qn = createImportedQualifiedTypeName(namespaceName, importedType)
			if (typeVisibility.visibility) {
				val originalName = createImportedQualifiedTypeName(importedType)
				if (!invalidImports.containsElement(originalName)) {
					importedType.handleNamespacedAccess(originalName, qn, invalidImports, originatorMap, specifier)
				}
			}
		}
	}

	private def handleAliasedAccess(IdentifiableElement element, QualifiedName originalName, String importedName,
		ImportedElementsMap invalidImports, IEODesc2ISpec originatorMap, ImportSpecifier specifier) {
		val invalidAccess = new PlainAccessOfAliasedImportDescription(N4JSEObjectDescription.create(originalName, element),
			importedName)
		invalidImports.put(originalName, invalidAccess)
	// TODO IDEBUG-702 originatorMap.putWithOrigin(invalidAccess, specifier)
	}

	private def handleNamespacedAccess(IdentifiableElement importedType, QualifiedName originalName, QualifiedName qn,
		ImportedElementsMap invalidImports, IEODesc2ISpec originatorMap, ImportSpecifier specifier) {
		val invalidAccess = new PlainAccessOfNamespacedImportDescription(
			N4JSEObjectDescription.create(originalName, importedType), qn.toString)
		invalidImports.put(originalName, invalidAccess)
	// TODO IDEBUG-702 originatorMap.putWithOrigin(invalidAccess, specifier)
	}

	private def handleInvisible(IdentifiableElement importedElement, ImportedElementsMap invalidImports, QualifiedName qn,
		String visibilitySuggestion, IEODesc2ISpec originatorMap, ImportSpecifier specifier) {
		// TODO IDEBUG-702 val invalidAccess = new InvisibleTypeOrVariableDescription(EObjectDescription.create(qn, importedElement))
		val invalidAccess = invalidImports.putOrError(importedElement, qn, null)
		invalidAccess.addAccessModifierSuggestion(visibilitySuggestion)
		originatorMap.putWithOrigin(invalidAccess, specifier)
	}

	/** Add the description to the orginatorMap and include trace to the specifier in special Error-Aware IEObjectDesciptoins
	 * like AmbigousImportDescriptions.
	 */
	private def putWithOrigin(HashMap<IEObjectDescription, ImportSpecifier> originiatorMap,
		IEObjectDescription description, ImportSpecifier specifier) {
		originiatorMap.put(description, specifier);
		switch (description) {
			AmbiguousImportDescription: {
				description.originatingImports.add(specifier);
				// Handling of wrapped delegatee, since this information will not be available in other cases:
				val firstPlaceSpec = originiatorMap.get(description.delegate)
				// add only if not there yet:
				if (firstPlaceSpec !== null && ! description.originatingImports.contains(firstPlaceSpec)) {
					description.originatingImports.add(firstPlaceSpec)
				}
			}
		}
	}

	def private boolean isVariableFrom(IdentifiableElement element, ImportDeclaration imp) {
		var res = false;
		if ((imp?.module !== null && imp?.module.variables.contains(element))) {
			res = true
		}
		return res;
	}

	private def AbstractTypeVisibilityChecker.TypeVisibility isVisible(Resource contextResource,
		IdentifiableElement element) {
		if (element instanceof Type)
			typeVisibilityChecker.isVisible(contextResource, element)
		else if (element instanceof TVariable)
			variableVisibilityChecker.isVisible(contextResource, element)
		else
			return new AbstractTypeVisibilityChecker.TypeVisibility(false);
	}

	/**
	 * Returns {@code true} if an import of the given {@link IEObjectDescription} should be
	 * regarded as ambiguous with the given {@link IdentifiableElement}.
	 */
	protected def boolean isAmbiguous(IEObjectDescription existing, IdentifiableElement element) {
		// make sure ambiguity is only detected in case of the same imported version of a type
		if (existing.getEObjectOrProxy instanceof Versionable && element instanceof Versionable) {
			return (existing.getEObjectOrProxy as Versionable).version == (element as Versionable).version;
		} else {
			return true;
		}
	}

	private def IEObjectDescription putOrError(ImportedElementsMap result,
		IdentifiableElement element, QualifiedName importedName, String issueCode) {
		// TODO IDEBUG-702 refactor InvisibleTypeOrVariableDescription / AmbiguousImportDescription relation
		var IEObjectDescription ret = null;
		val existing = result.getElements(importedName)

		if (!existing.empty && existing.findFirst[isAmbiguous(it, element)] !== null) {
			if (issueCode !== null) {
				switch existing {
					AmbiguousImportDescription: {
						existing.elements += element;
						ret = existing
					}
					default: {
						val error = new AmbiguousImportDescription(existing.head, issueCode, element)
						result.put(importedName, error)
						error.elements += element;
						ret = error
					}
				}
			}
		} else if (issueCode === null) {
			ret = createDescription(importedName, element)
			ret = new InvisibleTypeOrVariableDescription(ret)
			result.put(importedName, ret)
		} else {
			ret = createDescription(importedName, element)
			result.put(importedName, ret)
		}
		return ret;
	}
	
	private def IEObjectDescription createDescription(QualifiedName name, IdentifiableElement element) {
		if (name.lastSegment != element.name) {
			return new AliasedEObjectDescription(name, N4JSEObjectDescription.create(qualifiedNameProvider.getFullyQualifiedName(element), element))
		} else {
			return N4JSEObjectDescription.create(name, element)
		}
	}

	/**
	 * global object scope indirectly cached, as this method is only called by getImportedIdentifiables (which is cached)
	 */
	private def IScope getGlobalObjectProperties(IScope parent, EObject context) {
		val globalObject = GlobalObjectScope.get(context.eResource.resourceSet).getGlobalObject
		memberScopeFactory.create(parent, globalObject, context, false, false)
	}

	private def void addAccessModifierSuggestion(IEObjectDescription description, String suggestion) {
		if (description instanceof InvisibleTypeOrVariableDescription) {
			description.accessModifierSuggestion = suggestion;
		}
	}
}
