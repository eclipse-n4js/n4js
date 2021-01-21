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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.ts.typeRefs.BoundThisTypeRef;
import org.eclipse.n4js.ts.typeRefs.ExistentialTypeRef;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeArgument;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeTypeRef;
import org.eclipse.n4js.ts.typeRefs.Wildcard;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypeVariable;
import org.eclipse.n4js.ts.types.util.Variance;
import org.eclipse.n4js.ts.utils.TypeUtils;
import org.eclipse.n4js.typesystem.utils.BoundType;
import org.eclipse.n4js.typesystem.utils.NestedTypeRefsSwitch;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions;
import org.eclipse.n4js.utils.RecursionGuard;

/* package */ class BoundJudgment extends AbstractJudgment {

	/** See {@link N4JSTypeSystem#upperBound(RuleEnvironment, TypeArgument)}. */
	public TypeRef applyUpperBound(RuleEnvironment G, TypeArgument typeArg, boolean reopen, boolean resolveAliases,
			boolean resolveTypeVariables) {
		final BoundSwitch theSwitch = new BoundSwitch(G, BoundType.UPPER, reopen, resolveTypeVariables);
		final TypeRef result = theSwitch.doSwitch(typeArg);
		if (result == null) {
			final String stringRep = typeArg != null ? typeArg.getTypeRefAsString() : "<null>";
			throw new IllegalStateException("null return value in upperBound judgment for type argument: " + stringRep);
		}
		return result;
	}

	/** See {@link N4JSTypeSystem#lowerBound(RuleEnvironment, TypeArgument)}. */
	public TypeRef applyLowerBound(RuleEnvironment G, TypeArgument typeArg, boolean reopen, boolean resolveAliases,
			boolean resolveTypeVariables) {
		final BoundSwitch theSwitch = new BoundSwitch(G, BoundType.LOWER, reopen, resolveTypeVariables);
		final TypeRef result = theSwitch.doSwitch(typeArg);
		if (result == null) {
			final String stringRep = typeArg != null ? typeArg.getTypeRefAsString() : "<null>";
			throw new IllegalStateException("null return value in lowerBound judgment for type argument: " + stringRep);
		}
		return result;
	}

	private final class BoundSwitch extends NestedTypeRefsSwitch {

		private final BoundType boundType;
		private final boolean reopen;
		private final boolean resolve;

		private final RecursionGuard<EObject> guard = new RecursionGuard<>();

		public BoundSwitch(RuleEnvironment G, BoundType boundType, boolean reopen, boolean resolve) {
			super(G);
			this.boundType = boundType;
			this.reopen = reopen;
			this.resolve = resolve;
		}

		@Override
		protected NestedTypeRefsSwitch derive(RuleEnvironment G_NEW) {
			return new BoundSwitch(G_NEW, boundType, reopen, resolve);
		}

		// the following two methods are provided to increase readability of recursive invocations of #doSwitch()

		protected TypeRef modify(RuleEnvironment G_NEW, TypeArgument typeArg, BoundType boundTypeNEW,
				boolean reopenNEW, boolean resolveNEW) {
			if (G_NEW == G && boundTypeNEW == boundType && reopenNEW == reopen && resolveNEW == resolve) {
				return doSwitch(typeArg);
			} else {
				final BoundSwitch nestedSwitch = new BoundSwitch(G_NEW, boundTypeNEW, reopenNEW, resolveNEW);
				return nestedSwitch.doSwitch(typeArg);
			}
		}

		@Override
		public TypeRef doSwitch(EObject eObject) {
			return (TypeRef) super.doSwitch(eObject);
		}

		@Override
		protected TypeRef doSwitch(int classifierID, EObject eObject) {
			if (guard.tryNext(eObject)) {
				try {
					return (TypeRef) super.doSwitch(classifierID, eObject);
				} finally {
					guard.done(eObject);
				}
			}
			return eObject instanceof Wildcard
					? getFallbackBoundForWildcard((Wildcard) eObject)
					: (TypeRef) eObject;
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
			if (existentialTypeRef.isReopened() || shouldBeReopened(existentialTypeRef)) {
				TypeRef result = caseWildcard(existentialTypeRef.getWildcard());
				result = TypeUtils.copy(result);
				TypeUtils.copyTypeModifiers(result, existentialTypeRef);
				return result;
			}
			return existentialTypeRef;
		}

		@Override
		protected TypeRef caseParameterizedTypeRef_modifyDeclaredType(ParameterizedTypeRef typeRef) {
			if (resolve) {
				final Type declType = typeRef.getDeclaredType();
				if (declType instanceof TypeVariable) {
					switch (boundType) {
					case UPPER:
						final TypeRef upperBound = ((TypeVariable) declType).getDeclaredUpperBound();
						if (upperBound != null) {
							final TypeRef upperBoundOfUpperBound = modify(G, upperBound);
							return upperBoundOfUpperBound;
						}
						return RuleEnvironmentExtensions.topTypeRef(G);
					case LOWER:
						return RuleEnvironmentExtensions.bottomTypeRef(G);
					}
				}
			}
			return typeRef;
		}

		@Override
		protected TypeRef caseParameterizedTypeRef_modifyTypeArguments(TypeRef typeRef, Type declType,
				boolean alreadyCopied) {
			return super.caseParameterizedTypeRef_modifyTypeArguments(typeRef, declType, false);
		}

		@Override
		protected TypeArgument caseParameterizedTypeRef_modifyTypeArgument(RuleEnvironment G2, TypeVariable typeParam,
				TypeArgument typeArg) {
			final Variance defSiteVariance = typeParam != null ? typeParam.getVariance() : Variance.INV;
			TypeArgument typeArgPushed = pushBoundOfTypeArgument(typeArg, defSiteVariance);
			if (typeArgPushed != typeArg) {
				typeArgPushed = TypeUtils.copyIfContained(typeArgPushed);
			}
			return typeArgPushed;
		}

		/**
		 * Taking the bound of a Wildcard / open ExistentialTypeRef is different depending on whether we are on top
		 * level or nested inside another type reference:
		 * <ul>
		 * <li>the upper bound of <code>? extends A</code> is <code>A</code>, but
		 * <li>the upper bound of <code>G&lt;? extends A></code> is *not* <code>G&lt;A></code>.
		 * </ul>
		 * This method implements the second case, i.e. handling a nested type argument while computing the upper/lower
		 * bound of its containing type reference.
		 */
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
					TypeRef boundOfBoundToBePushed = modify(G, boundToBePushed, pushDirection, reopen, false);
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
				final TypeRef boundOfBoundToBePushed = modify(G, boundToBePushed, pushDirection, reopen, false);
				if (boundOfBoundToBePushed != boundToBePushed) {
					return boundOfBoundToBePushed;
				}
			} else if (defSiteVariance == Variance.CONTRA) {
				// treat 'typeArg' like the lower bound of a wildcard
				final TypeRef boundToBePushed = (TypeRef) typeArg; // n.b. we know 'typeArg' isn't a Wildcard
				final BoundType pushDirection = boundType.inverse();
				final TypeRef boundOfBoundToBePushed = modify(G, boundToBePushed, pushDirection, reopen, false);
				if (boundOfBoundToBePushed != boundToBePushed) {
					return boundOfBoundToBePushed;
				}
			} else if (typeArg instanceof ExistentialTypeRef && shouldBeReopened((ExistentialTypeRef) typeArg)) {
				// NOTE: don't merge this case with the above case for ExistentialTypeRef, because order matters!
				final Wildcard wildcard = ((ExistentialTypeRef) typeArg).getWildcard();
				final Wildcard wildcardPushed = (Wildcard) pushBoundOfTypeArgument(wildcard, defSiteVariance);
				return wildcardPushed;
			}
			return typeArg; // no change
		}

		@Override
		protected TypeRef caseFunctionTypeExprOrRef_modifyReturnType(TypeRef returnTypeRef) {
			return modify(G, returnTypeRef, boundType, reopen, false);
		}

		@Override
		protected TypeRef caseFunctionTypeExprOrRef_modifyParameterType(TypeRef fparTypeRef) {
			return modify(G, fparTypeRef, boundType.inverse(), reopen, false);
		}

		@Override
		public TypeRef caseBoundThisTypeRef(BoundThisTypeRef boundThisTypeRef) {
			if (reopen) {
				if (boundType == BoundType.UPPER) {
					return modify(G, TypeUtils.createResolvedThisTypeRef(boundThisTypeRef));
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
			if (reopen) {
				if (boundType == BoundType.UPPER) {
					final TypeArgument typeArg = ct.getTypeArg();
					if (typeArg instanceof BoundThisTypeRef) {
						final TypeArgument typeArgNew = TypeUtils.createResolvedThisTypeRef((BoundThisTypeRef) typeArg);
						return TypeUtils.createTypeTypeRef(typeArgNew, ct.isConstructorRef());
					}
				}
			}
			// ordinary handling of type argument
			return super.caseTypeTypeRef(ct);
		}

		@Override
		protected TypeArgument caseTypeTypeRef_modifyTypeArg(TypeArgument typeArg) {
			TypeArgument typeArgPushed = pushBoundOfTypeArgument(typeArg, Variance.INV);
			if (typeArgPushed != typeArg) {
				typeArgPushed = TypeUtils.copyIfContained(typeArgPushed);
			}
			return typeArgPushed;
		}

		private boolean shouldBeReopened(ExistentialTypeRef etr) {
			return reopen && !RuleEnvironmentExtensions.isFixedCapture(G, etr);
		}
	}
}
