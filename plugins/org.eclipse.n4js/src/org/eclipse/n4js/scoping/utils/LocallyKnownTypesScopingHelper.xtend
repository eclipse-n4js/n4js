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
package org.eclipse.n4js.scoping.utils

import com.google.inject.Inject
import java.util.function.Supplier
import org.eclipse.emf.ecore.EObject
import org.eclipse.n4js.n4JS.N4NamespaceDeclaration
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.scoping.N4JSScopeProvider
import org.eclipse.n4js.scoping.imports.ImportedElementsScopingHelper
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression
import org.eclipse.n4js.ts.types.AbstractNamespace
import org.eclipse.n4js.ts.types.TClassifier
import org.eclipse.n4js.ts.types.TEnum
import org.eclipse.n4js.ts.types.TStructMethod
import org.eclipse.n4js.ts.types.Type
import org.eclipse.xtext.resource.EObjectDescription
import org.eclipse.xtext.scoping.IScope
import org.eclipse.xtext.scoping.impl.SingletonScope
import org.eclipse.xtext.util.IResourceScopeCache
import com.google.common.collect.Iterables

/**
 * Helper for {@link N4JSScopeProvider N4JSScopeProvider} using
 * {@link ImportedElementsScopingHelper ImportedElementsScopingHelper}
 * for providing scope for types provider.
 */
class LocallyKnownTypesScopingHelper {

	@Inject
	IResourceScopeCache cache

	@Inject
	ImportedElementsScopingHelper importedElementsScopingHelper

	@Inject
	ScopeSnapshotHelper scopeSnapshotHelper

	/** Returns the type itself and type variables in case the type is generic. */
	def IScope scopeWithTypeAndItsTypeVariables(IScope parent, Type type, boolean staticAccess) {
		var IScope result = parent;
		if (type !== null) {

			// add the type itself
			if (type.name !== null && type instanceof TClassifier) {
				// note that functions cannot be used as type references
				result = new SingletonScope(EObjectDescription.create(type.name, type), result)
			}

			// add the type variables
			if (type.generic) {
				if (type instanceof TClassifier && staticAccess) {
					// error case: type variables of a classifier cannot be accessed from static members
					// e.g. class C<T> { static x: T; }
					// --> return same scope as in success case, but wrap descriptions with a WrongStaticAccessorDescription
					val wrapEODs = [new WrongStaticAccessDescription(it, staticAccess)];
					result = scopeSnapshotHelper.scopeForEObjects("scopeWithTypeAndItsTypeVariables-1", type, result, type.typeVars, wrapEODs);
				} else {
					// success case: simply add type variables to scope
					result = scopeSnapshotHelper.scopeForEObjects("scopeWithTypeAndItsTypeVariables-2", type, result, type.typeVars);
				}
			}
		}

		return result;
	}

	/** Returns the type variables if the TStructMethod is generic. */
	def IScope scopeWithTypeVarsOfTStructMethod(IScope parent, TStructMethod m) {
		val mDef = m.definedMember
		if (mDef instanceof TStructMethod) {
			if (mDef.generic) {
				return scopeSnapshotHelper.scopeForEObjects("scopeWithTypeVarsOfTStructMethod", mDef, parent, mDef.typeVars);
			}
		}
		return parent;
	}

	/** Returns the type variables if the function type expression is generic. */
	def IScope scopeWithTypeVarsOfFunctionTypeExpression(IScope parent, FunctionTypeExpression funTypeExpr) {
		if (funTypeExpr !== null && funTypeExpr.generic) {
			return scopeSnapshotHelper.scopeForEObjects("scopeWithTypeVarsOfFunctionTypeExpression", funTypeExpr, parent, funTypeExpr.typeVars);
		}
		return parent;
	}

	/** Returns scope with locally known types and (as parent) import scope; the result is cached. */
	def IScope scopeWithLocallyDeclaredElems(Script script, Supplier<IScope> parentSupplier, boolean onlyNamespacelikes) {
		return cache.get(script -> 'locallyKnownTypes_'+String.valueOf(onlyNamespacelikes), script.eResource) [|
			// all types in the index:
			val parent = parentSupplier.get();
			// but imported types are preferred (or maybe renamed with aliases):
			val IScope importScope = importedElementsScopingHelper.getImportedTypes(parent, script);
			// finally, add locally declared types as the outer scope
			val localTypes = scopeWithLocallyDeclaredElems(script, importScope, onlyNamespacelikes);
			
			return localTypes;
		];
	}
	
	/** Returns scope with locally declared types (without import scope). */
	def IScope scopeWithLocallyDeclaredElems(Script script, IScope parent, boolean onlyNamespacelikes) {
		return scopeWithLocallyDeclaredElems(script.module, script, parent, onlyNamespacelikes);
	}
	
	/** Returns scope with locally declared types (without import scope). */
	def IScope scopeWithLocallyDeclaredElems(N4NamespaceDeclaration namespace, IScope parent, boolean onlyNamespacelikes) {
		return cache.get(namespace -> 'scopeWithLocallyDeclaredElems_'+String.valueOf(onlyNamespacelikes), namespace.eResource) [|
			return scopeWithLocallyDeclaredElems(namespace.definedType as AbstractNamespace, namespace, parent, onlyNamespacelikes);
		];
	}
	
	/** Returns scope with locally declared types (without import scope). */
	def IScope scopeWithLocallyDeclaredElems(AbstractNamespace namespace, IScope parent, boolean onlyNamespacelikes) {
		return scopeWithLocallyDeclaredElems(namespace, namespace, parent, onlyNamespacelikes);
	}
	
	/** Returns scope with locally declared types (without import scope). */
	def private IScope scopeWithLocallyDeclaredElems(AbstractNamespace ans, EObject context, IScope parent, boolean onlyNamespacelikes) {
		if (ans === null || ans.eIsProxy) {
			return parent;
		}
		val tlElems = if (onlyNamespacelikes) {
				Iterables.concat(ans.namespaces, ans.types.filter[t | t instanceof TEnum ])
			} else {
				ans.types.filter[t | !t.polyfill ];
			};
		val eoDescrs = tlElems.map[ topLevelType |
			return EObjectDescription.create(topLevelType.name, topLevelType);
		];
		return scopeSnapshotHelper.scopeFor("scopeWithLocallyDeclaredTypes", context, parent, eoDescrs);
	}

	/**
	 * Returns scope with locally known types specially configured for super reference in case of polyfill definitions.
	 * It is comparable to {@link #getLocallyKnownTypes(Script, EReference, IScopeProvider), but it does not
	 * add the polyfillType itself. Instead, only its type variables are added, which are otherwise hidden in case of polyfills.
	 * The result is not cached as this scope is needed only one time.
	 */
	def IScope scopeWithLocallyKnownTypesForPolyfillSuperRef(Script script,
		IScope parent, Type polyfillType) {

		// imported and locally defined types are preferred (or maybe renamed with aliases):
		val IScope importScope = importedElementsScopingHelper.getImportedTypes(parent, script)

		// locally defined types except polyfillType itself
		val local = script.module
		val eoDescrs = local.types.filter[it !== polyfillType].map[EObjectDescription.create(name, it)];
		val IScope localTypesScope = scopeSnapshotHelper.scopeFor("scopeWithLocallyKnownTypesForPolyfillSuperRef", script, importScope, eoDescrs);

		// type variables of polyfill
		if (polyfillType !== null && polyfillType.generic) {
			return scopeSnapshotHelper.scopeForEObjects("scopeWithLocallyKnownTypesForPolyfillSuperRef-polyfillType", polyfillType, localTypesScope, polyfillType.typeVars);
		}

		// non generic:
		return localTypesScope;

	}

}
