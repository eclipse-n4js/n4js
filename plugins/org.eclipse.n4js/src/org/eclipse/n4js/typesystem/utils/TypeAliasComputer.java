/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.typesystem.utils;

import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.GUARD_RESOLVE_TYPE_ALIASES_SWITCH;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.wrap;

import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeArgument;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRefsFactory;
import org.eclipse.n4js.ts.types.TypeAlias;
import org.eclipse.n4js.types.utils.TypeUtils;
import org.eclipse.n4js.typesystem.N4JSTypeSystem;
import org.eclipse.n4js.utils.RecursionGuard;
import org.eclipse.xtext.xbase.lib.Pair;

import com.google.inject.Inject;

/**
 * Type system helper strategy for dealing with {@link TypeAlias type aliases}.
 */
class TypeAliasComputer extends TypeSystemHelperStrategy {

	@Inject
	private N4JSTypeSystem ts;

	/**
	 * If the given 'typeRef' is an unresolved reference to a type alias, this method computes and returns a
	 * corresponding resolved reference to this type alias. Nested type references a <b>not</b> considered.
	 * <p>
	 * For details on the difference between resolved and unresolved references to type aliases, see
	 * {@link TypeRef#isAliasResolved()}.
	 */
	TypeRef resolveTypeAliasFlat(RuleEnvironment G, TypeRef typeRef) {
		if (!typeRef.isAliasUnresolved()) {
			return typeRef;
		}

		ParameterizedTypeRef originalAliasTypeRef = null;
		RecursionGuard<TypeAlias> guard = null;
		TypeRef currTypeRef = typeRef;
		while (true) {
			// this is non-null and of type TypeAlias, because #isAliasUnresolved() returned true
			TypeAlias typeAlias = (TypeAlias) currTypeRef.getDeclaredType();
			TypeRef actualTypeRef = typeAlias.getTypeRef();
			if (actualTypeRef == null) {
				return currTypeRef;
			}

			// convert to a resolved type alias reference
			TypeRef resolvedTypeRef = TypeUtils.copy(actualTypeRef);
			if (originalAliasTypeRef == null) {
				originalAliasTypeRef = TypeUtils.copy((ParameterizedTypeRef) typeRef);
			}
			resolvedTypeRef.setOriginalAliasTypeRef(originalAliasTypeRef);
			resolvedTypeRef = TypeUtils.mergeTypeModifiers(resolvedTypeRef, currTypeRef, true);

			// if we have a parameterized type reference to a generic type alias, we have to substitute
			// to not lose the bindings defined by the type arguments in 'typeRef':
			if (currTypeRef.isGeneric()) {
				RuleEnvironment G_temp = RuleEnvironmentExtensions.wrap(G);
				tsh.addSubstitutions(G_temp, currTypeRef);
				resolvedTypeRef = ts.substTypeVariables(G_temp, resolvedTypeRef);
			}

			// done?
			if (!resolvedTypeRef.isAliasUnresolved()) {
				return resolvedTypeRef;
			}

			if (guard == null) {
				guard = new RecursionGuard<>();
			}
			if (!guard.tryNext(typeAlias)) {
				// cyclic type alias declaration
				// NOTE #1: validation N4JSTypeAliasValidator#checkCyclicAliasDeclaration() relies on
				// UnknownTypeRef being returned here!
				// NOTE #2: we could set 'originalAliasTypeRef' in the UnknownTypeRef, but we do not want
				// to hide the fact that an error occurred!
				return TypeRefsFactory.eINSTANCE.createUnknownTypeRef();
			}

			currTypeRef = resolvedTypeRef;
		}
	}

	/**
	 * Same as {@link #resolveTypeAliasFlat(RuleEnvironment, TypeRef)}, but also considers nested type references.
	 */
	TypeRef resolveTypeAliases(RuleEnvironment G, TypeRef typeRef) {
		return (TypeRef) resolveTypeAliases(G, (TypeArgument) typeRef);
	}

	/**
	 * Same as {@link #resolveTypeAliasFlat(RuleEnvironment, TypeRef)}, but also considers nested type references.
	 */
	TypeArgument resolveTypeAliases(RuleEnvironment G, TypeArgument typeArg) {
		ResolveTypeAliasesSwitch theSwitch = new ResolveTypeAliasesSwitch(G, this);
		TypeArgument result = theSwitch.doSwitch(typeArg);
		return result;
	}

	private static class ResolveTypeAliasesSwitch extends NestedTypeRefsSwitch {

		private final TypeAliasComputer tac; // required, because inner classes not supported

		ResolveTypeAliasesSwitch(RuleEnvironment G, TypeAliasComputer tac) {
			super(G);
			this.tac = tac;
		}

		@Override
		protected ResolveTypeAliasesSwitch derive(RuleEnvironment G_NEW) {
			return new ResolveTypeAliasesSwitch(G_NEW, tac);
		}

		@Override
		protected TypeRef caseParameterizedTypeRef_processDeclaredType(ParameterizedTypeRef typeRef) {
			TypeRef result = typeRef;
			if (result.isAliasUnresolved()) {
				// we know it's a TypeAlias, because #isAliasUnresolved() returned true
				TypeAlias tAlias = (TypeAlias) typeRef.getDeclaredType();
				result = tac.resolveTypeAliasFlat(G, result);
				if (result != typeRef
						&& !(result instanceof ParameterizedTypeRef)) {
					// the aliased type of 'tAlias' is a union, function type expression, etc.
					// --> need to further process any nested type references (as this is not done by
					// #resolveTypeAliasFlat())
					Pair<String, TypeAlias> guardKey = Pair.of(GUARD_RESOLVE_TYPE_ALIASES_SWITCH, tAlias);
					if (G.get(guardKey) == null) {
						RuleEnvironment G2 = wrap(G);
						G2.put(guardKey, Boolean.TRUE);
						result = processNested(G2, result);
					} else {
						// NOTE: validation N4JSTypeAliasValidator#checkCyclicAliasDeclaration() relies on
						// UnknownTypeRef being returned here!
						result = TypeRefsFactory.eINSTANCE.createUnknownTypeRef();
					}
				}
			}
			return result;
		}
	}
}
