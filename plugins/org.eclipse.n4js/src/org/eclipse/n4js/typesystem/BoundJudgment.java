/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.typesystem;

import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.bottomTypeRef;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.topTypeRef;

import java.util.stream.Collectors;

import org.eclipse.n4js.ts.typeRefs.BoundThisTypeRef;
import org.eclipse.n4js.ts.typeRefs.ExistentialTypeRef;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeRef;
import org.eclipse.n4js.ts.typeRefs.IntersectionTypeExpression;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeArgument;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeTypeRef;
import org.eclipse.n4js.ts.typeRefs.UnionTypeExpression;
import org.eclipse.n4js.ts.typeRefs.Wildcard;
import org.eclipse.n4js.ts.typeRefs.util.TypeRefsSwitch;
import org.eclipse.n4js.ts.types.TypeVariable;
import org.eclipse.n4js.ts.utils.TypeUtils;
import org.eclipse.n4js.typesystem.utils.BoundType;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;

/* package */ class BoundJudgment extends AbstractJudgment {

	public TypeRef applyUpperBound(RuleEnvironment G, TypeArgument typeArg) {
		final BoundSwitch theSwitch = new BoundSwitch(G, BoundType.UPPER);
		final TypeRef result = theSwitch.doSwitch(typeArg);
		if (result == null) {
			final String stringRep = typeArg != null ? typeArg.getTypeRefAsString() : "<null>";
			throw new IllegalStateException("null return value in upperBound judgment for type argument: " + stringRep);
		}
		return result;
	}

	public TypeRef applyLowerBound(RuleEnvironment G, TypeArgument typeArg) {
		final BoundSwitch theSwitch = new BoundSwitch(G, BoundType.LOWER);
		final TypeRef result = theSwitch.doSwitch(typeArg);
		if (result == null) {
			final String stringRep = typeArg != null ? typeArg.getTypeRefAsString() : "<null>";
			throw new IllegalStateException("null return value in lowerBound judgment for type argument: " + stringRep);
		}
		return result;
	}

	private final class BoundSwitch extends TypeRefsSwitch<TypeRef> {

		private final RuleEnvironment G;
		private final BoundType boundType;

		public BoundSwitch(RuleEnvironment G, BoundType boundType) {
			this.G = G;
			this.boundType = boundType;
		}

		@Override
		public TypeRef caseTypeRef(TypeRef typeRef) {
			return typeRef;
		}

		@Override
		public TypeRef caseWildcard(Wildcard wildcard) {
			final TypeRef declBound = boundType == BoundType.UPPER
					? wildcard.getDeclaredOrImplicitUpperBound()
					: wildcard.getDeclaredLowerBound();
			if (declBound != null) {
				return declBound;
			}
			return boundType == BoundType.UPPER ? topTypeRef(G) : bottomTypeRef(G);
		}

		@Override
		public TypeRef caseExistentialTypeRef(ExistentialTypeRef existentialTypeRef) {
			TypeRef result = caseWildcard(existentialTypeRef.getWildcard());
			// retain undef- & nullable-modifiers
			result = TypeUtils.copy(result);
			TypeUtils.copyTypeModifiers(result, existentialTypeRef);
			return result;
		}

		@Override
		public TypeRef caseUnionTypeExpression(UnionTypeExpression union) {
			final UnionTypeExpression result = TypeUtils.createNonSimplifiedUnionType(
					union.getTypeRefs().stream().map(tr -> doSwitch(tr)).collect(Collectors.toList()));
			TypeUtils.copyTypeModifiers(result, union);
			return result;
		}

		@Override
		public TypeRef caseIntersectionTypeExpression(IntersectionTypeExpression intersection) {
			final IntersectionTypeExpression result = TypeUtils.createNonSimplifiedIntersectionType(
					intersection.getTypeRefs().stream().map(tr -> doSwitch(tr)).collect(Collectors.toList()));
			TypeUtils.copyTypeModifiers(result, intersection);
			return result;
		}

		@Override
		public TypeRef caseParameterizedTypeRef(ParameterizedTypeRef ptr) {
			if (ptr.getDeclaredType() instanceof TypeVariable) {
				return ptr; // do not return the declaredLowerBounds here! (a type variable is not an existential type)
			} else {
				return ptr;
			}
		}

		// required due to multiple inheritance
		@Override
		public TypeRef caseFunctionTypeRef(FunctionTypeRef F) {
			return caseFunctionTypeExprOrRef(F);
		}

		@Override
		public TypeRef caseFunctionTypeExprOrRef(FunctionTypeExprOrRef F) {
			return typeSystemHelper.createBoundOfFunctionTypeExprOrRef(G, F, boundType);
		}

		@Override
		public TypeRef caseBoundThisTypeRef(BoundThisTypeRef boundThisTypeRef) {
			if (boundType == BoundType.UPPER) {
				return TypeUtils.createResolvedThisTypeRef(boundThisTypeRef);
			} else {
				final TypeRef result = bottomTypeRef(G);
				TypeUtils.copyTypeModifiers(result, boundThisTypeRef);
				return result;
			}
		}

		@Override
		public TypeRef caseTypeTypeRef(TypeTypeRef ct) {
			if (boundType == BoundType.UPPER) {
				final TypeArgument typeArg = ct.getTypeArg();
				if (typeArg instanceof BoundThisTypeRef) {
					final TypeArgument typeArgNew = TypeUtils.createResolvedThisTypeRef((BoundThisTypeRef) typeArg);
					return TypeUtils.createTypeTypeRef(typeArgNew, ct.isConstructorRef());
				}
			}
			return ct;
		}
	}
}
