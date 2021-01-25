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
package org.eclipse.n4js.typesystem.utils

import com.google.inject.Inject
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef
import org.eclipse.n4js.ts.typeRefs.TypeArgument
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.typeRefs.TypeRefsFactory
import org.eclipse.n4js.ts.types.TypeAlias
import org.eclipse.n4js.ts.utils.TypeUtils
import org.eclipse.n4js.typesystem.N4JSTypeSystem
import org.eclipse.n4js.utils.RecursionGuard

/**
 * Type system helper strategy for dealing with {@link TypeAlias type aliases}.
 */
package class TypeAliasComputer extends TypeSystemHelperStrategy {

	@Inject
	private N4JSTypeSystem ts;

	// FIXME API doc
	def package TypeRef resolveTypeAliasFlat(RuleEnvironment G, TypeRef typeRef) {
		if (!typeRef.isAliasUnresolved) {
			return typeRef;
		}

		var RecursionGuard<TypeAlias> guard = null;
		var currTypeRef = typeRef;
		while (true) {
			val typeAlias = currTypeRef.declaredType as TypeAlias; // this is non-null and of type TypeAlias, because #isAliasUnresolved() returned true
			val actualTypeRef = typeAlias.actualTypeRef;
			if (actualTypeRef === null) {
				return currTypeRef;
			}

			// convert to a resolved type alias reference
			var resolvedTypeRef = TypeUtils.copy(actualTypeRef);
			resolvedTypeRef.originalAliasTypeRef = TypeUtils.copy(currTypeRef as ParameterizedTypeRef);
			// FIXME merge type modifiers, etc.

			// if we have a parameterized type reference to a generic type alias, we have to substitute
			// to not lose the bindings defined by the type arguments in 'typeRef':
			if (!currTypeRef.typeArgs.empty) {
				val G_temp = RuleEnvironmentExtensions.wrap(G);
				tsh.addSubstitutions(G_temp, currTypeRef);
				resolvedTypeRef = ts.substTypeVariables(G_temp, resolvedTypeRef);
			}

			// done?
			if (!resolvedTypeRef.isAliasUnresolved) {
				return resolvedTypeRef;
			}

			if (guard === null) {
				guard = new RecursionGuard<TypeAlias>();
			}
			if (!guard.tryNext(typeAlias)) {
				// cyclic type alias declaration
				return TypeRefsFactory.eINSTANCE.createUnknownTypeRef();
			}

			currTypeRef = resolvedTypeRef;
		}
	}

	// FIXME API doc
	def package TypeRef resolveTypeAliases(RuleEnvironment G, TypeRef typeRef) {
		return resolveTypeAliases(G, typeRef as TypeArgument) as TypeRef;
	}

	// FIXME API doc
	def package TypeArgument resolveTypeAliases(RuleEnvironment G, TypeArgument typeArg) {
		val theSwitch = new ResolveTypeAliasesSwitch(G, this);
		val result = theSwitch.doSwitch(typeArg);
		return result;
	}

	private static class ResolveTypeAliasesSwitch extends NestedTypeRefsSwitch {

		private final TypeAliasComputer tac; // required, because inner classes not supported

		new(RuleEnvironment G, TypeAliasComputer tac) {
			super(G);
			this.tac = tac;
		}

		override protected derive(RuleEnvironment G_NEW) {
			return new ResolveTypeAliasesSwitch(G_NEW, tac);
		}

		override protected caseParameterizedTypeRef_processDeclaredType(ParameterizedTypeRef typeRef) {
			return tac.resolveTypeAliasFlat(G, typeRef);
		}
	}
}
