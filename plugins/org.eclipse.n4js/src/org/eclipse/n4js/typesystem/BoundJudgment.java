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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.ts.typeRefs.BoundThisTypeRef;
import org.eclipse.n4js.ts.typeRefs.ExistentialTypeRef;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeRef;
import org.eclipse.n4js.ts.typeRefs.IntersectionTypeExpression;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeArgument;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage;
import org.eclipse.n4js.ts.typeRefs.TypeTypeRef;
import org.eclipse.n4js.ts.typeRefs.UnionTypeExpression;
import org.eclipse.n4js.ts.typeRefs.Wildcard;
import org.eclipse.n4js.ts.typeRefs.util.TypeRefsSwitch;
import org.eclipse.n4js.ts.types.TypeVariable;
import org.eclipse.n4js.ts.utils.TypeUtils;
import org.eclipse.n4js.typesystem.utils.BoundType;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.utils.RecursionGuard;

import com.google.common.base.Optional;

/* package */ class BoundJudgment extends AbstractJudgment {

	/** See {@link N4JSTypeSystem#upperBound(RuleEnvironment, TypeArgument)}. */
	public TypeRef applyUpperBound(RuleEnvironment G, TypeArgument typeArg) {
		final BoundSwitch theSwitch = new BoundSwitch(G, BoundType.UPPER);
		final TypeRef result = theSwitch.doSwitch(typeArg);
		if (result == null) {
			final String stringRep = typeArg != null ? typeArg.getTypeRefAsString() : "<null>";
			throw new IllegalStateException("null return value in upperBound judgment for type argument: " + stringRep);
		}
		return result;
	}

	/** See {@link N4JSTypeSystem#lowerBound(RuleEnvironment, TypeArgument)}. */
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

		private final RecursionGuard<EObject> guard = new RecursionGuard<>();

		public BoundSwitch(RuleEnvironment G, BoundType boundType) {
			this.G = G;
			this.boundType = boundType;
		}

		@Override
		protected TypeRef doSwitch(int classifierID, EObject eObject) {
			if (guard.tryNext(eObject)) {
				try {
					return super.doSwitch(classifierID, eObject);
				} finally {
					guard.done(eObject);
				}
			}
			return eObject instanceof Wildcard
					? getFallbackBoundForWildcard((Wildcard) eObject)
					: (TypeRef) eObject;
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
			return getFallbackBoundForWildcard(wildcard);
		}

		private TypeRef getFallbackBoundForWildcard(@SuppressWarnings("unused") Wildcard wildcard) {
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
				final List<TypeArgument> typeArgs = ptr.getTypeArgs();
				final Optional<List<TypeArgument>> typeArgsBounds = pushBoundsOfTypeArguments(typeArgs);
				if (typeArgsBounds.isPresent()) {
					final ParameterizedTypeRef ptrCpy = TypeUtils.copyPartial(ptr,
							TypeRefsPackage.eINSTANCE.getParameterizedTypeRef_TypeArgs());
					ptrCpy.getTypeArgs().clear();
					ptrCpy.getTypeArgs().addAll(TypeUtils.copyAll(typeArgsBounds.get()));
					return ptrCpy;
				}
				return ptr;
			}
		}

		private Optional<List<TypeArgument>> pushBoundsOfTypeArguments(List<TypeArgument> typeArgs) {
			final List<TypeArgument> typeArgsBounds = new ArrayList<>(typeArgs.size());
			boolean haveChange = false;
			for (TypeArgument typeArg : typeArgs) {
				final TypeArgument typeArgBound = pushBoundOfTypeArgument(typeArg);
				typeArgsBounds.add(typeArgBound);
				haveChange |= typeArgBound != typeArg;
			}
			return haveChange ? Optional.of(typeArgsBounds) : Optional.absent();
		}

		// TODO add support for def-site variance
		private TypeArgument pushBoundOfTypeArgument(TypeArgument typeArg) {
			if (typeArg instanceof Wildcard) {
				final Wildcard wildcard = (Wildcard) typeArg;
				final TypeRef declBound = boundType == BoundType.UPPER
						? wildcard.getDeclaredOrImplicitUpperBound()
						: wildcard.getDeclaredLowerBound();
				if (declBound != null) {
					TypeRef boundOfDeclBound = doSwitch(declBound);
					if (boundOfDeclBound != declBound) {
						final Wildcard wildcardCpy = TypeUtils.copy(wildcard);
						if (boundType == BoundType.UPPER) {
							wildcardCpy.setDeclaredUpperBound(TypeUtils.copyIfContained(boundOfDeclBound));
						} else {
							wildcardCpy.setDeclaredLowerBound(TypeUtils.copyIfContained(boundOfDeclBound));
						}
						return wildcardCpy;
					}
				}
			} else if (typeArg instanceof ExistentialTypeRef) {
				final ExistentialTypeRef etr = (ExistentialTypeRef) typeArg;
				final Wildcard wildcard = etr.getWildcard();
				final Wildcard wildcardPushed = (Wildcard) pushBoundOfTypeArgument(wildcard);
				return wildcardPushed;
			}
			return typeArg; // no change
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
				return doSwitch(TypeUtils.createResolvedThisTypeRef(boundThisTypeRef));
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
