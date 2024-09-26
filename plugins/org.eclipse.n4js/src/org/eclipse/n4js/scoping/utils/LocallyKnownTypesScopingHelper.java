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
package org.eclipse.n4js.scoping.utils;

import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;

import java.util.function.Function;
import java.util.function.Supplier;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.n4JS.N4NamespaceDeclaration;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.resource.N4JSCache;
import org.eclipse.n4js.scoping.N4JSScopeProvider;
import org.eclipse.n4js.scoping.imports.ImportedElementsScopingHelper;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression;
import org.eclipse.n4js.ts.types.AbstractNamespace;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.TEnum;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.TStructMember;
import org.eclipse.n4js.ts.types.TStructMethod;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.xtext.resource.EObjectDescription;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.impl.SingletonScope;

import com.google.common.collect.Iterables;
import com.google.inject.Inject;

/**
 * Helper for {@link N4JSScopeProvider N4JSScopeProvider} using {@link ImportedElementsScopingHelper
 * ImportedElementsScopingHelper} for providing scope for types provider.
 */
public class LocallyKnownTypesScopingHelper {

	@Inject
	N4JSCache cache;

	@Inject
	ImportedElementsScopingHelper importedElementsScopingHelper;

	@Inject
	ScopeSnapshotHelper scopeSnapshotHelper;

	/** Returns the type itself and type variables in case the type is generic. */
	public IScope scopeWithTypeAndItsTypeVariables(IScope parent, Type type, boolean staticAccess) {
		IScope result = parent;
		if (type != null) {

			// add the type itself
			if (type.getName() != null && type instanceof TClassifier) {
				// note that functions cannot be used as type references
				result = new SingletonScope(EObjectDescription.create(type.getName(), type), result);
			}

			// add the type variables
			if (type.isGeneric()) {
				if (type instanceof TClassifier && staticAccess) {
					// error case: type variables of a classifier cannot be accessed from static members
					// e.g. class C<T> { static x: T; }
					// --> return same scope as in success case, but wrap descriptions with a
					// WrongStaticAccessorDescription
					Function<IEObjectDescription, IEObjectDescription> wrapEODs = dscr -> new WrongStaticAccessDescription(
							dscr, staticAccess);
					result = scopeSnapshotHelper.scopeForEObjects("scopeWithTypeAndItsTypeVariables-1",
							type, result, type.getTypeVars(), wrapEODs);
				} else {
					// success case: simply add type variables to scope
					result = scopeSnapshotHelper.scopeForEObjects("scopeWithTypeAndItsTypeVariables-2", type, result,
							type.getTypeVars());
				}
			}
		}

		return result;
	}

	/** Returns the type variables if the TStructMethod is generic. */
	public IScope scopeWithTypeVarsOfTStructMethod(IScope parent, TStructMethod m) {
		TStructMember mDef = m.getDefinedMember();
		if (mDef instanceof TStructMethod) {
			if (((TStructMethod) mDef).isGeneric()) {
				return scopeSnapshotHelper.scopeForEObjects("scopeWithTypeVarsOfTStructMethod", mDef, parent,
						((TStructMethod) mDef).getTypeVars());
			}
		}
		return parent;
	}

	/** Returns the type variables if the function type expression is generic. */
	public IScope scopeWithTypeVarsOfFunctionTypeExpression(IScope parent, FunctionTypeExpression funTypeExpr) {
		if (funTypeExpr != null && funTypeExpr.isGeneric()) {
			return scopeSnapshotHelper.scopeForEObjects("scopeWithTypeVarsOfFunctionTypeExpression", funTypeExpr,
					parent, funTypeExpr.getTypeVars());
		}
		return parent;
	}

	/** Returns scope with locally known types and (as parent) import scope; the result is cached. */
	public IScope scopeWithLocallyDeclaredElems(Script script, Supplier<IScope> parentSupplier,
			boolean onlyNamespacelikes) {
		return cache.get(script.eResource(), () -> {
			// all types in the index:
			IScope parent = parentSupplier.get();
			// but imported types are preferred (or maybe renamed with aliases):
			IScope importScope = importedElementsScopingHelper.getImportedTypes(parent, script);
			// finally, add locally declared types as the outer scope
			IScope localTypes = scopeWithLocallyDeclaredElems(script, importScope, onlyNamespacelikes);

			return localTypes;
		}, script, "locallyKnownTypes_", onlyNamespacelikes);
	}

	/** Returns scope with locally declared types (without import scope). */
	public IScope scopeWithLocallyDeclaredElems(Script script, IScope parent, boolean onlyNamespacelikes) {
		return scopeWithLocallyDeclaredElems(script.getModule(), script, parent, onlyNamespacelikes);
	}

	/** Returns scope with locally declared types (without import scope). */
	public IScope scopeWithLocallyDeclaredElems(N4NamespaceDeclaration namespace, IScope parent,
			boolean onlyNamespacelikes) {
		return cache.get(namespace.eResource(),
				() -> scopeWithLocallyDeclaredElems((AbstractNamespace) namespace.getDefinedType(), namespace, parent,
						onlyNamespacelikes),
				namespace, "scopeWithLocallyDeclaredElems_", onlyNamespacelikes);
	}

	/** Returns scope with locally declared types (without import scope). */
	public IScope scopeWithLocallyDeclaredElems(AbstractNamespace namespace, IScope parent,
			boolean onlyNamespacelikes) {
		return scopeWithLocallyDeclaredElems(namespace, namespace, parent, onlyNamespacelikes);
	}

	/** Returns scope with locally declared types (without import scope). */
	private IScope scopeWithLocallyDeclaredElems(AbstractNamespace ans, EObject context, IScope parent,
			boolean onlyNamespacelikes) {
		if (ans == null || ans.eIsProxy()) {
			return parent;
		}
		Iterable<Type> tlElems = onlyNamespacelikes
				? Iterables.concat(ans.getNamespaces(), filter(ans.getTypes(), TEnum.class))
				: filter(ans.getTypes(), t -> !t.isPolyfill());
		Iterable<IEObjectDescription> eoDescrs = map(tlElems,
				topLevelType -> EObjectDescription.create(topLevelType.getName(), topLevelType));
		return scopeSnapshotHelper.scopeFor("scopeWithLocallyDeclaredTypes", context, parent, eoDescrs);
	}

	/**
	 * Returns scope with locally known types specially configured for super reference in case of polyfill definitions.
	 * It does not add the polyfillType itself. Instead, only its type variables are added, which are otherwise hidden
	 * in case of polyfills. The result is not cached as this scope is needed only one time.
	 */
	public IScope scopeWithLocallyKnownTypesForPolyfillSuperRef(Script script, IScope parent, Type polyfillType) {

		// imported and locally defined types are preferred (or maybe renamed with aliases):
		IScope importScope = importedElementsScopingHelper.getImportedTypes(parent, script);

		// locally defined types except polyfillType itself
		TModule local = script.getModule();
		Iterable<IEObjectDescription> eoDescrs = map(filter(local.getTypes(), t -> t != polyfillType),
				t -> EObjectDescription.create(t.getName(), t));
		IScope localTypesScope = scopeSnapshotHelper.scopeFor("scopeWithLocallyKnownTypesForPolyfillSuperRef", script,
				importScope, eoDescrs);

		// type variables of polyfill
		if (polyfillType != null && polyfillType.isGeneric()) {
			return scopeSnapshotHelper.scopeForEObjects("scopeWithLocallyKnownTypesForPolyfillSuperRef-polyfillType",
					polyfillType, localTypesScope, polyfillType.getTypeVars());
		}

		// non generic:
		return localTypesScope;

	}

}
