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
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.ts.typeRefs.BoundThisTypeRef;
import org.eclipse.n4js.ts.typeRefs.ExistentialTypeRef;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeRef;
import org.eclipse.n4js.ts.typeRefs.IntersectionTypeExpression;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeArgument;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRefsFactory;
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage;
import org.eclipse.n4js.ts.typeRefs.TypeTypeRef;
import org.eclipse.n4js.ts.typeRefs.UnionTypeExpression;
import org.eclipse.n4js.ts.typeRefs.Wildcard;
import org.eclipse.n4js.ts.typeRefs.util.TypeRefsSwitch;
import org.eclipse.n4js.ts.types.TFormalParameter;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypeVariable;
import org.eclipse.n4js.ts.types.TypesFactory;
import org.eclipse.n4js.ts.types.util.Variance;
import org.eclipse.n4js.ts.utils.TypeUtils;
import org.eclipse.n4js.typesystem.utils.BoundType;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.utils.RecursionGuard;

import com.google.common.base.Optional;

/* package */ class BoundJudgment extends AbstractJudgment {

	/** See {@link N4JSTypeSystem#upperBound(RuleEnvironment, TypeArgument)}. */
	public TypeRef applyUpperBound(RuleEnvironment G, TypeArgument typeArg, boolean force) {
		final BoundSwitch theSwitch = new BoundSwitch(G, BoundType.UPPER, force);
		final TypeRef result = theSwitch.doSwitch(typeArg);
		if (result == null) {
			final String stringRep = typeArg != null ? typeArg.getTypeRefAsString() : "<null>";
			throw new IllegalStateException("null return value in upperBound judgment for type argument: " + stringRep);
		}
		return result;
	}

	/** See {@link N4JSTypeSystem#lowerBound(RuleEnvironment, TypeArgument)}. */
	public TypeRef applyLowerBound(RuleEnvironment G, TypeArgument typeArg, boolean force) {
		final BoundSwitch theSwitch = new BoundSwitch(G, BoundType.LOWER, force);
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
		private final boolean force;

		private final RecursionGuard<EObject> guard = new RecursionGuard<>();

		public BoundSwitch(RuleEnvironment G, BoundType boundType, boolean force) {
			this.G = G;
			this.boundType = boundType;
			this.force = force;
		}

		// the following method is provided to increase readability of recursive invocations of #doSwitch()
		private TypeRef bound(RuleEnvironment G_NEW, TypeArgument typeArg, BoundType boundTypeNEW, boolean forceNEW) {
			if (G_NEW == G && boundTypeNEW == boundType && forceNEW == force) {
				return doSwitch(typeArg);
			} else {
				final BoundSwitch nestedSwitch = new BoundSwitch(G_NEW, boundTypeNEW, forceNEW);
				return nestedSwitch.doSwitch(typeArg);
			}
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
			if (force || existentialTypeRef.isReopened()) {
				TypeRef result = caseWildcard(existentialTypeRef.getWildcard());
				result = TypeUtils.copy(result);
				TypeUtils.copyTypeModifiers(result, existentialTypeRef);
				return result;
			}
			return existentialTypeRef;
		}

		@Override
		public TypeRef caseUnionTypeExpression(UnionTypeExpression union) {
			final Optional<List<TypeRef>> typeRefsBounds = mapAndCompare(union.getTypeRefs(), this::doSwitch);
			if (typeRefsBounds.isPresent()) {
				final UnionTypeExpression result = TypeUtils
						.createNonSimplifiedUnionType(typeRefsBounds.get());
				TypeUtils.copyTypeModifiers(result, union);
				return result;
			}
			return union;
		}

		@Override
		public TypeRef caseIntersectionTypeExpression(IntersectionTypeExpression intersection) {
			final Optional<List<TypeRef>> typeRefsBounds = mapAndCompare(intersection.getTypeRefs(), this::doSwitch);
			if (typeRefsBounds.isPresent()) {
				final IntersectionTypeExpression result = TypeUtils
						.createNonSimplifiedIntersectionType(typeRefsBounds.get());
				TypeUtils.copyTypeModifiers(result, intersection);
				return result;
			}
			return intersection;
		}

		private <T extends TypeArgument> Optional<List<T>> mapAndCompare(List<T> typeArgs, Function<T, T> fn) {
			final List<T> typeArgsBounds = new ArrayList<>(typeArgs.size());
			boolean haveChange = false;
			for (T typeArg : typeArgs) {
				final T typeArgBound = fn.apply(typeArg);
				typeArgsBounds.add(typeArgBound);
				haveChange |= typeArgBound != typeArg;
			}
			return haveChange ? Optional.of(typeArgsBounds) : Optional.absent();
		}

		@Override
		public TypeRef caseParameterizedTypeRef(ParameterizedTypeRef ptr) {
			final Type declType = ptr.getDeclaredType();
			if (declType instanceof TypeVariable) {
				return ptr; // do not return the declaredLowerBounds here! (a type variable is not an existential type)
			} else {
				final Iterator<TypeVariable> typeParamsIter = declType != null ? declType.getTypeVars().iterator()
						: Collections.emptyIterator();
				final List<TypeArgument> typeArgsPushed = new ArrayList<>();
				boolean haveChange = false;
				for (TypeArgument typeArg : ptr.getTypeArgs()) {
					final TypeVariable typeParam = typeParamsIter.hasNext() ? typeParamsIter.next() : null;
					final Variance defSiteVariance = typeParam != null ? typeParam.getVariance() : Variance.INV;
					final TypeArgument typeArgPushed = pushBoundOfTypeArgument(typeArg, defSiteVariance);
					typeArgsPushed.add(typeArgPushed);
					haveChange |= typeArgPushed != typeArg;
				}
				if (haveChange) {
					final ParameterizedTypeRef ptrCpy = TypeUtils.copyPartial(ptr,
							TypeRefsPackage.eINSTANCE.getParameterizedTypeRef_TypeArgs());
					ptrCpy.getTypeArgs().clear();
					ptrCpy.getTypeArgs().addAll(TypeUtils.copyAll(typeArgsPushed));
					return ptrCpy;
				}
				return ptr;
			}
		}

		private TypeArgument pushBoundOfTypeArgument(TypeArgument typeArg, Variance defSiteVariance) {
			if (typeArg instanceof Wildcard) {
				final Wildcard wildcard = (Wildcard) typeArg;
				final TypeRef upperBound = wildcard.getDeclaredOrImplicitUpperBound();
				final TypeRef lowerBound = wildcard.getDeclaredLowerBound();
				// STEP 1: decide which bound should be pushed into which direction
				// If we are computing an upper bound of a ParameterizedTypeRef, we push upper bounds of wildcards up
				// and lower bounds of wildcards down (i.e. we widen the range of accepted type arguments).
				// If we are computing a lower bound of a ParameterizedTypeRef, we push upper bounds of wildcards down
				// and lower bounds of wildcards up (i.e. we narrow the range of accepted type arguments).
				final TypeRef boundToBePushed;
				final BoundType pushDirection;
				if (upperBound != null) {
					boundToBePushed = upperBound;
					pushDirection = boundType;
				} else if (lowerBound != null) {
					boundToBePushed = lowerBound;
					pushDirection = boundType.inverse();
				} else {
					boundToBePushed = null;
					pushDirection = null;
				}
				// STEP 2: actually push the bound into the desired direction
				if (boundToBePushed != null) {
					TypeRef boundOfBoundToBePushed = bound(G, boundToBePushed, pushDirection, force);
					if (boundOfBoundToBePushed != boundToBePushed) {
						final Wildcard wildcardCpy = TypeUtils.copy(wildcard);
						if (upperBound != null) {
							wildcardCpy.setDeclaredUpperBound(TypeUtils.copyIfContained(boundOfBoundToBePushed));
						} else if (lowerBound != null) {
							wildcardCpy.setDeclaredLowerBound(TypeUtils.copyIfContained(boundOfBoundToBePushed));
						}
						return wildcardCpy;
					}
				}
			} else if (typeArg instanceof ExistentialTypeRef && ((ExistentialTypeRef) typeArg).isReopened()) {
				final Wildcard wildcard = ((ExistentialTypeRef) typeArg).getWildcard();
				final Wildcard wildcardPushed = (Wildcard) pushBoundOfTypeArgument(wildcard, defSiteVariance);
				return wildcardPushed;
			} else if (defSiteVariance == Variance.CO) {
				// treat 'typeArg' like the upper bound of a wildcard
				final TypeRef boundToBePushed = (TypeRef) typeArg; // n.b. we know 'typeArg' isn't a Wildcard
				final BoundType pushDirection = boundType;
				final TypeRef boundOfBoundToBePushed = bound(G, boundToBePushed, pushDirection, force);
				if (boundOfBoundToBePushed != boundToBePushed) {
					return boundOfBoundToBePushed;
				}
			} else if (defSiteVariance == Variance.CONTRA) {
				// treat 'typeArg' like the lower bound of a wildcard
				final TypeRef boundToBePushed = (TypeRef) typeArg; // n.b. we know 'typeArg' isn't a Wildcard
				final BoundType pushDirection = boundType.inverse();
				final TypeRef boundOfBoundToBePushed = bound(G, boundToBePushed, pushDirection, force);
				if (boundOfBoundToBePushed != boundToBePushed) {
					return boundOfBoundToBePushed;
				}
			} else if (typeArg instanceof ExistentialTypeRef && force) {
				// NOTE: don't merge this case with the above case for ExistentialTypeRef, because order matters!
				final Wildcard wildcard = ((ExistentialTypeRef) typeArg).getWildcard();
				final Wildcard wildcardPushed = (Wildcard) pushBoundOfTypeArgument(wildcard, defSiteVariance);
				return wildcardPushed;
			}
			return typeArg; // no change
		}

		// required due to multiple inheritance, to ensure FunctionTypeRef is handled like a FunctionTypeExpression,
		// not as a ParameterizedTypeRef
		@Override
		public TypeRef caseFunctionTypeRef(FunctionTypeRef F) {
			return caseFunctionTypeExprOrRef(F);
		}

		@Override
		public TypeRef caseFunctionTypeExprOrRef(FunctionTypeExprOrRef F) {
			boolean haveReplacement = false;

			// upper/lower bound of return type
			final TypeRef returnTypeRef = F.getReturnTypeRef();
			final TypeRef resultReturnTypeRef = returnTypeRef != null ? bound(G, F.getReturnTypeRef(), boundType, force)
					: null;
			haveReplacement |= resultReturnTypeRef != returnTypeRef;

			// lower/upper bounds of parameter types
			final List<TFormalParameter> fpars = F.getFpars();
			final List<TFormalParameter> resultFpars = new ArrayList<>(fpars.size());
			for (TFormalParameter oldPar : fpars) {
				if (oldPar == null) {
					resultFpars.add(null);
					continue;
				}
				final TFormalParameter newPar = TypesFactory.eINSTANCE.createTFormalParameter();
				newPar.setName(oldPar.getName());
				newPar.setVariadic(oldPar.isVariadic());
				newPar.setHasInitializerAssignment(oldPar.isHasInitializerAssignment());
				// note: property 'astInitializer' is not copied since it's part of the AST

				final TypeRef oldParTypeRef = oldPar.getTypeRef();
				if (oldParTypeRef != null) {
					final TypeRef newParTypeRef = bound(G, oldParTypeRef, boundType.inverse(), force);
					newPar.setTypeRef(TypeUtils.copyIfContained(newParTypeRef));
					haveReplacement |= newParTypeRef != oldParTypeRef;
				}

				resultFpars.add(newPar);
			}

			if (haveReplacement) {
				final FunctionTypeExpression result = TypeRefsFactory.eINSTANCE.createFunctionTypeExpression();

				// let posterity know that the newly created FunctionTypeExpression
				// represents the binding of another FunctionTypeExprOrRef
				result.setBinding(true);

				// retain the pointer to declared type of original FunctionTypeExprOrRef (if any)
				// (see API doc of FunctionTypeExpression#declaredType for more info)
				result.setDeclaredType(F.getFunctionType());

				result.getUnboundTypeVars().addAll(F.getTypeVars());
				if (F instanceof FunctionTypeExpression) {
					result.getUnboundTypeVarsUpperBounds().addAll(TypeUtils.copyAll(
							((FunctionTypeExpression) F).getUnboundTypeVarsUpperBounds()));
				}

				if (F.getDeclaredThisType() != null) {
					result.setDeclaredThisType(TypeUtils.copyIfContained(F.getDeclaredThisType()));
				}

				result.setReturnTypeRef(TypeUtils.copyIfContained(resultReturnTypeRef));
				result.setReturnValueMarkedOptional(F.isReturnValueOptional());

				result.getFpars().addAll(resultFpars); // no need to copy; all fpars were newly created above!

				TypeUtils.copyTypeModifiers(result, F);

				return result;
			}
			return F;
		}

		@Override
		public TypeRef caseBoundThisTypeRef(BoundThisTypeRef boundThisTypeRef) {
			if (force) {
				if (boundType == BoundType.UPPER) {
					return doSwitch(TypeUtils.createResolvedThisTypeRef(boundThisTypeRef));
				} else {
					final TypeRef result = bottomTypeRef(G);
					TypeUtils.copyTypeModifiers(result, boundThisTypeRef);
					return result;
				}
			}
			return boundThisTypeRef;
		}

		@Override
		public TypeRef caseTypeTypeRef(TypeTypeRef ct) {
			// special case: handle BoundThisTypeRef
			// (note: the this-type is a very special beast that may only appear at certain locations
			// within a type reference; this is why we handle it here up-front instead of moving this
			// case to method #pushBoundOfTypeArgument())
			if (force) {
				if (boundType == BoundType.UPPER) {
					final TypeArgument typeArg = ct.getTypeArg();
					if (typeArg instanceof BoundThisTypeRef) {
						final TypeArgument typeArgNew = TypeUtils.createResolvedThisTypeRef((BoundThisTypeRef) typeArg);
						return TypeUtils.createTypeTypeRef(typeArgNew, ct.isConstructorRef());
					}
				}
			}
			// ordinary handling of type argument
			final TypeArgument typeArg = ct.getTypeArg();
			final TypeArgument typeArgBound = pushBoundOfTypeArgument(typeArg, Variance.INV);
			if (typeArgBound != typeArg) {
				TypeTypeRef ctCpy = TypeUtils.copyPartial(ct, TypeRefsPackage.eINSTANCE.getTypeTypeRef_TypeArg());
				ctCpy.setTypeArg(TypeUtils.copyIfContained(typeArgBound));
				return ctCpy;
			}
			return ct;
		}
	}
}
