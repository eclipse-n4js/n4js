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
import org.eclipse.n4js.n4JS.ImportDeclaration
import org.eclipse.n4js.n4JS.ImportSpecifier
import org.eclipse.n4js.n4JS.NamedImportSpecifier
import org.eclipse.n4js.n4JS.NamespaceImportSpecifier
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.scoping.N4JSScopeProvider
import org.eclipse.n4js.scoping.accessModifiers.AbstractTypeVisibilityChecker
import org.eclipse.n4js.scoping.accessModifiers.InvisibleTypeOrVariableDescription
import org.eclipse.n4js.scoping.accessModifiers.TypeVisibilityChecker
import org.eclipse.n4js.scoping.accessModifiers.VariableVisibilityChecker
import org.eclipse.n4js.scoping.builtin.GlobalObjectScope
import org.eclipse.n4js.scoping.members.MemberScope.MemberScopeFactory
import org.eclipse.n4js.scoping.utils.LocallyKnownTypesScopingHelper
import org.eclipse.n4js.scoping.utils.MergedScope
import org.eclipse.n4js.validation.IssueCodes
import org.eclipse.n4js.ts.scoping.builtin.BuiltInTypeScope
import org.eclipse.n4js.ts.types.IdentifiableElement
import org.eclipse.n4js.ts.types.ModuleNamespaceVirtualType
import org.eclipse.n4js.ts.types.TVariable
import org.eclipse.n4js.ts.types.Type
import java.util.HashMap
import java.util.Map
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.resource.EObjectDescription
import org.eclipse.xtext.resource.IEObjectDescription
import org.eclipse.xtext.scoping.IScope
import org.eclipse.xtext.scoping.impl.MapBasedScope
import org.eclipse.xtext.scoping.impl.SimpleScope
import org.eclipse.xtext.util.IResourceScopeCache
import org.eclipse.n4js.scoping.builtin.NoPrimitiveTypesScope
import org.eclipse.xtext.resource.impl.AliasedEObjectDescription
import org.eclipse.xtext.naming.IQualifiedNameProvider
import com.google.inject.Singleton

/** internal helper collection type */
class QName2IEODesc extends HashMap<QualifiedName, IEObjectDescription> {
}

/** internal helper collection type */
class IEODesc2ISpec extends HashMap<IEObjectDescription, ImportSpecifier> {
}

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

	@Inject MemberScopeFactory memberScopeFactory

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
		val IScope scriptScope = cache.get(script -> 'importedTypes', script.eResource) [|
			val result = script.findImportedElements(parentScope, false);
			return result;
		]
		return scriptScope
	}

	private def IScope findImportedElements(Script script, IScope parentScope, boolean includeVariables) {
		val contextResource = script.eResource;
		val imports = script.scriptElements.filter(ImportDeclaration)

		if (imports.empty) return parentScope

		/** broken/invisible imported eObjects descriptions (in case of broken state of imports this can be {@link AmbiguousImportDescription})*/
		val invalidImports = new QName2IEODesc
		/** valid imported eObjects descriptions (in case of broken state of imports this can be {@link AmbiguousImportDescription})*/
		val validImports = new QName2IEODesc
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
		val localValidScope = MapBasedScope.createScope(localBaseScope, validImports.values)

		return new OriginAwareScope(localValidScope, originatorMap);
	}

	private def void processNamedImportSpecifier(NamedImportSpecifier specifier, ImportDeclaration imp,
		Resource contextResource, IEODesc2ISpec originatorMap, QName2IEODesc validImports, QName2IEODesc invalidImports,
		boolean importVariables) {
		val element = specifier.importedElement
		if (element !== null && !element.eIsProxy) {

			if (!importVariables && element.isVariableFrom(imp)) {
				return;
			}

			val importedName = if(specifier.isDefaultImport) {
				// we got a default import of the form: import localName from "some/module"
				// -> use the string 'localName' (but this is not the alias property!)
				specifier.importedElementAsText
			} else {
				specifier.alias ?: element.exportedName ?: element.name
			};
			val importedQName = QualifiedName.create(importedName)
			val typeVisibility = isVisible(contextResource, element);
			if (typeVisibility.visibility) {
				val ieod = validImports.putOrError(element, importedQName, IssueCodes.IMP_AMBIGUOUS);
				originatorMap.putWithOrigin(ieod, specifier)
				val originalName = QualifiedName.create(element.name)
				if (specifier.alias !== null && !invalidImports.containsKey(originalName)) {
					element.handleAliasedAccess(originalName, importedName, invalidImports, originatorMap, specifier)
				}
			} else {
				element.handleInvisible(invalidImports, importedQName, typeVisibility.accessModifierSuggestion,
					originatorMap, specifier)
			}
		}
	}

	private def void processNamespaceSpecifier(
		NamespaceImportSpecifier specifier,
		ImportDeclaration imp,
		Script script,
		Resource contextResource,
		IEODesc2ISpec originatorMap,
		QName2IEODesc validImports,
		QName2IEODesc invalidImports,
		boolean importVariables
	) {
		if(specifier.alias === null){
			return;//if broken code, e.g. "import * as 123 as N from 'some/Module'"
		}

		// add namespace to scope
		var namespaceName = specifier.alias;
		val namespaceQName = QualifiedName.create(namespaceName)
		val Type namespaceType = script.module.internalTypes.findFirst [ interType |
			interType instanceof ModuleNamespaceVirtualType &&
				(interType as ModuleNamespaceVirtualType).module === imp.module
		]
		val ieodx = validImports.putOrError(namespaceType, namespaceQName, IssueCodes.IMP_AMBIGUOUS)
		originatorMap.putWithOrigin(ieodx, specifier)

		if (importVariables) {
			// add vars to namespace
			for (importedVar : imp.module.variables) {
				val varVisibility = variableVisibilityChecker.isVisible(contextResource, importedVar);
				val varName = importedVar.exportedName
				val qn = QualifiedName.create(namespaceName, varName)
				if (varVisibility.visibility) {
					val originalName = QualifiedName.create(varName)
					if (!invalidImports.containsKey(originalName)) {
						importedVar.handleNamespacedAccess(originalName, qn, invalidImports, originatorMap,
							specifier)
					}
				} else {
					importedVar.handleInvisible(invalidImports, qn, varVisibility.accessModifierSuggestion,
						originatorMap, specifier)
				}
			}
		}

		// add types
		for (importedType : imp.module.topLevelTypes) {
			val typeVisibility = typeVisibilityChecker.isVisible(contextResource, importedType);
			val typeName = importedType.exportedName ?: importedType.name;
			val qn = QualifiedName.create(namespaceName, typeName)
			if (typeVisibility.visibility) {
				if (!importVariables) {
					// when we are not importing variables we ask for types, types are not access expressions,
					// so we add type with namespace name into the scope
					val ieod = validImports.putOrError(importedType, qn, IssueCodes.IMP_AMBIGUOUS)
					originatorMap.putWithOrigin(ieod, specifier)
				}
				val originalName = QualifiedName.create(typeName)
				if (!invalidImports.containsKey(originalName)) {
					importedType.handleNamespacedAccess(originalName, qn, invalidImports, originatorMap, specifier)
				}
			} else {
				importedType.handleInvisible(invalidImports, qn, typeVisibility.accessModifierSuggestion, originatorMap,
					specifier)
			}
		}
	}

	private def handleAliasedAccess(IdentifiableElement element, QualifiedName originalName, String importedName,
		QName2IEODesc invalidImports, IEODesc2ISpec originatorMap, ImportSpecifier specifier) {
		val invalidAccess = new PlainAccessOfAliasedImportDescription(EObjectDescription.create(originalName, element),
			importedName)
		invalidImports.put(originalName, invalidAccess)
	// TODO IDEBUG-702 originatorMap.putWithOrigin(invalidAccess, specifier)
	}

	private def handleNamespacedAccess(IdentifiableElement importedType, QualifiedName originalName, QualifiedName qn,
		QName2IEODesc invalidImports, IEODesc2ISpec originatorMap, ImportSpecifier specifier) {
		val invalidAccess = new PlainAccessOfNamespacedImportDescription(
			EObjectDescription.create(originalName, importedType), qn.toString)
		invalidImports.put(originalName, invalidAccess)
	// TODO IDEBUG-702 originatorMap.putWithOrigin(invalidAccess, specifier)
	}

	private def handleInvisible(IdentifiableElement importedElement, QName2IEODesc invalidImports, QualifiedName qn,
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

	private def IEObjectDescription putOrError(Map<QualifiedName, IEObjectDescription> result,
		IdentifiableElement element, QualifiedName importedName, String issueCode) {
		// TODO IDEBUG-702 refactor InvisibleTypeOrVariableDescription / AmbiguousImportDescription relation
		var IEObjectDescription ret = null;
		val IEObjectDescription existing = result.get(importedName)
		if (existing !== null) {
			if (issueCode !== null) {
				switch existing {
					AmbiguousImportDescription: {
						existing.elements += element;
						ret = existing
					}
					default: {
						val error = new AmbiguousImportDescription(existing, issueCode, element)
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
			return new AliasedEObjectDescription(name, EObjectDescription.create(qualifiedNameProvider.getFullyQualifiedName(element), element))
		} else {
			return EObjectDescription.create(name, element)
		}
	}

	/**
	 * global object scope indirectly cached, as this method is only called by getImportedIdentifiables (which is cached)
	 */
	private def IScope getGlobalObjectProperties(IScope parent, EObject context) {
		val globalObject = GlobalObjectScope.get(context.eResource.resourceSet).getGlobalObject
		memberScopeFactory.create(parent, globalObject, context, false)
	}

	private def void addAccessModifierSuggestion(IEObjectDescription description, String suggestion) {
		if (description instanceof InvisibleTypeOrVariableDescription) {
			description.accessModifierSuggestion = suggestion;
		}
	}
}
