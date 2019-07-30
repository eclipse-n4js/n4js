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
package org.eclipse.n4js.typesystem.constraints;

import static org.eclipse.n4js.ts.types.util.Variance.CO;
import static org.eclipse.n4js.ts.types.util.Variance.CONTRA;
import static org.eclipse.n4js.ts.types.util.Variance.INV;
import static org.eclipse.n4js.typesystem.constraints.Reducer.BooleanOp.CONJUNCTION;
import static org.eclipse.n4js.typesystem.constraints.Reducer.BooleanOp.DISJUNCTION;

import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.ts.typeRefs.ComposedTypeRef;
import org.eclipse.n4js.ts.typeRefs.ExistentialTypeRef;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef;
import org.eclipse.n4js.ts.typeRefs.IntersectionTypeExpression;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeArgument;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRefsFactory;
import org.eclipse.n4js.ts.typeRefs.TypeTypeRef;
import org.eclipse.n4js.ts.typeRefs.UnionTypeExpression;
import org.eclipse.n4js.ts.typeRefs.Wildcard;
import org.eclipse.n4js.ts.types.ContainerType;
import org.eclipse.n4js.ts.types.InferenceVariable;
import org.eclipse.n4js.ts.types.PrimitiveType;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.TFormalParameter;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypeVariable;
import org.eclipse.n4js.ts.types.util.AllSuperTypesCollector;
import org.eclipse.n4js.ts.types.util.Variance;
import org.eclipse.n4js.ts.utils.TypeUtils;
import org.eclipse.n4js.typesystem.N4JSTypeSystem;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions;
import org.eclipse.n4js.typesystem.utils.StructuralTypingComputer;
import org.eclipse.n4js.typesystem.utils.StructuralTypingComputer.StructTypingInfo;
import org.eclipse.n4js.typesystem.utils.TypeSystemHelper;
import org.eclipse.n4js.utils.StructuralMembersTriple;
import org.eclipse.n4js.utils.StructuralMembersTripleIterator;
import org.eclipse.n4js.utils.StructuralTypesHelper;
import org.eclipse.xtext.xbase.lib.Pair;

/**
 * Contains all logic for reduction, i.e. for reducing a {@link TypeConstraint} into simpler {@link TypeBound}s. A
 * {@code Reducer} does not own any state. Instead, it's a collaborator of its {@link InferenceContext inference
 * context}, operating on the {@link BoundSet bound set} of that inference context.
 */
/* package */ final class Reducer {

	private static final boolean DEBUG = InferenceContext.DEBUG;

	private final InferenceContext ic;

	private final RuleEnvironment G;
	private final N4JSTypeSystem ts;
	private final TypeSystemHelper tsh;

	enum BooleanOp {
		CONJUNCTION, DISJUNCTION
	}

	/**
	 * Creates an instance.
	 */
	public Reducer(InferenceContext ic, RuleEnvironment G, N4JSTypeSystem ts, TypeSystemHelper tsh) {
		this.ic = ic;
		this.G = G;
		this.ts = ts;
		this.tsh = tsh;
	}

	/**
	 * Convenience method, forwards to {@link BoundSet#addBound(boolean)}.
	 *
	 * @return true iff new bounds were added (this signals a round of incorporation should follow)
	 */
	private boolean addBound(boolean b) {
		return ic.currentBounds.addBound(b);
	}

	/**
	 * Convenience method, creates a new {@link TypeBound} and forwards to {@link BoundSet#addBound(TypeBound)}.
	 *
	 * @return true iff new bounds were added (this signals a round of incorporation should follow)
	 */
	private boolean addBound(InferenceVariable infVar, TypeRef bound, Variance variance) {
		return ic.currentBounds.addBound(new TypeBound(infVar, bound, variance));
	}

	/**
	 * Add bound <code>FALSE</code>, thus making the inference context {@link InferenceContext#isDoomed() doomed}.
	 */
	private boolean giveUp(EObject left, EObject right, Variance variance) {
		if (DEBUG) {
			log("GIVING UP ON: " + TypeConstraint.toString(left, right, variance));
		}
		return addBound(false);
	}

	// ###############################################################################################################
	// REDUCTION

	/**
	 * Convenience method for {@link #reduce(TypeArgument, TypeArgument, Variance)}, accepting a {@link TypeConstraint}.
	 *
	 * @return true iff new bounds were added (this signals a round of incorporation should follow)
	 */
	public boolean reduce(TypeConstraint constraint) {
		if (constraint == TypeConstraint.TRUE) {
			return addBound(true);
		} else if (constraint == TypeConstraint.FALSE) {
			return addBound(false);
		} else {
			return reduce(constraint.left, constraint.right, constraint.variance);
		}
	}

	/**
	 * Reduces the the type constraint defined by the given left-hand and right-hand side and the given variance.
	 * <p>
	 * Always invoke this method instead of the more specific <code>#reduce*()</code> methods, both for readability and
	 * to ensure completeness of log messages.
	 *
	 * @return true iff new bounds were added (this signals a round of incorporation should follow)
	 */
	public boolean reduce(TypeArgument left, TypeArgument right, Variance variance) {
		if (DEBUG) {
			log("reducing: " + TypeConstraint.toString(left, right, variance));
		}
		if (left == null || right == null) {
			if (DEBUG) {
				log("ignoring constraint due to null values");
			}
			return false;
		}
		if (left instanceof ExistentialTypeRef) {
			ExistentialTypeRef leftCasted = (ExistentialTypeRef) left;
			if (leftCasted.isReopened()) {
				Wildcard wildcard = leftCasted.getWildcard();
				if (wildcard != null && !wildcard.eIsProxy()) {
					return reduce(wildcard, right, variance);
				}
			}
		}
		if (right instanceof ExistentialTypeRef) {
			ExistentialTypeRef rightCasted = (ExistentialTypeRef) right;
			if (rightCasted.isReopened()) {
				Wildcard wildcard = rightCasted.getWildcard();
				if (wildcard != null && !wildcard.eIsProxy()) {
					return reduce(left, wildcard, variance);
				}
			}
		}
		if ((left instanceof TypeRef) && (right instanceof TypeRef)) {
			// both TypeRefs
			return reduceTypeRef((TypeRef) left, (TypeRef) right, variance);
		}
		// at least one wildcard
		if ((left instanceof Wildcard) && (right instanceof Wildcard)) {
			// both wildcards
			return reduceWildcardBoth((Wildcard) left, (Wildcard) right, variance);
		}
		// a wildcard and a TypeRef, in any order
		if (right instanceof Wildcard) {
			return reduceWildcardRight((TypeRef) left, (Wildcard) right, variance);
		} else {
			return reduceWildcardRight((TypeRef) right, (Wildcard) left, variance.inverse());
		}
	}

	/**
	 * Reduces a set of type constraints with the same left-hand side and variance.
	 * <p>
	 * If <code>operator</code> is {@link BooleanOp#CONJUNCTION CONJUNCTION}, then this is merely a convenience method
	 * for invoking method {@link #reduce(TypeArgument, TypeArgument, Variance)} several times. If <code>operator</code>
	 * is {@link BooleanOp#DISJUNCTION DISJUNCTION}, then this is more tricky.
	 * <p>
	 * We could easily implement disjunctions of type constraints and bounds using backtracking (start with adding the
	 * first disjoint constraint/bound and continue; if constraint system unsolvable, go back to previous state and add
	 * second disjoint constraint/bound and continue; and so on until a solution is found). However, we do not want to
	 * do this, for performance reasons.
	 * <p>
	 * Therefore, this method applies heuristics to choose the "most promising" of the disjoint constraints and
	 * continues only with that single constraint; all other possible paths are ignored. This is an over-approximation
	 * (we might overlook valid solutions, but a solution we find is never invalid).
	 *
	 * @return true iff new bounds were added (this signals a round of incorporation should follow)
	 */
	private boolean reduce(TypeRef left, List<TypeRef> rights, Variance variance, BooleanOp operator) {
		if (operator == CONJUNCTION) {
			// simple case: simply call #reduce() several times
			boolean wasAdded = false;
			for (TypeRef currRight : rights) {
				wasAdded |= reduce(left, currRight, variance);
			}
			return wasAdded;
		} else /* operator == DISJUNCTION */ {
			// tricky case (because we want to avoid backtracking)
			final int rightsSize = rights.size();
			if (rightsSize == 0) {
				return false;
			} else if (rightsSize == 1) {
				return reduce(left, rights.get(0), variance);
			} else {
				// choose the "most promising" of the disjoint constraints and continue with that (and simply ignore the
				// other possible paths)
				int idx = -1;
				if (idx == -1 && left instanceof FunctionTypeExprOrRef) {
					// choose first function type (except those for which it is obvious they cannot match)
					for (int i = 0; i < rightsSize; i++) {
						final TypeRef currElem = rights.get(i);
						if (currElem instanceof FunctionTypeExprOrRef) {
							final FunctionTypeExprOrRef leftCasted = (FunctionTypeExprOrRef) left;
							final FunctionTypeExprOrRef currElemCasted = (FunctionTypeExprOrRef) currElem;
							final boolean mightMatch = (variance == CO && mightBeSubtypeOf(leftCasted, currElemCasted))
									|| (variance == CONTRA && mightBeSubtypeOf(currElemCasted, leftCasted))
									|| (variance == INV && mightBeSubtypeOf(leftCasted, currElemCasted)
											&& mightBeSubtypeOf(currElemCasted, leftCasted));
							if (mightMatch) {
								idx = i;
								break;
							}
						}
					}
				}
				if (idx == -1 && left instanceof ParameterizedTypeRef && !TypeUtils.isInferenceVariable(left)) {
					final Type leftDecl = left.getDeclaredType();
					if (idx == -1 && leftDecl != null) {
						// choose first matching declared type
						for (int i = 0; i < rightsSize; i++) {
							final TypeRef currElem = rights.get(i);
							if (leftDecl == currElem.getDeclaredType()) {
								idx = i;
								break;
							}
						}
					}
					if (idx == -1 && leftDecl instanceof PrimitiveType) {
						// choose first naked inference variable (if any)
						// (note: same as below, but has higher priority for primitive types than next heuristic)
						idx = chooseFirstInferenceVariable(rights);
					}
					if (idx == -1 && variance == CO && leftDecl instanceof ContainerType<?>) {
						// choose first supertype of left
						final List<TClassifier> superTypesOfLeft = AllSuperTypesCollector
								.collect((ContainerType<?>) leftDecl);
						for (int i = 0; i < rightsSize; i++) {
							final TypeRef currElem = rights.get(i);
							final Type currElemDecl = currElem.getDeclaredType();
							if (currElemDecl != null && superTypesOfLeft.contains(currElemDecl)) {
								idx = i;
								break;
							}
						}
					}
					if (idx == -1 && variance == CONTRA && leftDecl != null) {
						// choose first subtype of left
						for (int i = 0; i < rightsSize; i++) {
							final TypeRef currElem = rights.get(i);
							final Type currElemDecl = currElem.getDeclaredType();
							if (currElemDecl instanceof ContainerType<?>) {
								// TODO improve performance by using a super class iterator or super interfaces iterator
								// depending on type of leftDecl
								final List<TClassifier> superTypesOfCurrElem = AllSuperTypesCollector
										.collect((ContainerType<?>) currElemDecl);
								if (superTypesOfCurrElem.contains(leftDecl)) {
									idx = i;
									break;
								}
							}
						}
					}
				}
				if (idx == -1) {
					// choose first naked inference variable (if present)
					idx = chooseFirstInferenceVariable(rights);
				}
				if (idx == -1 && variance == CO) {
					// choose the top type 'any' or one of the pseudo-top types: Object, N4Object
					if (idx == -1)
						idx = chooseFirstWithDeclTypeOf(rights, RuleEnvironmentExtensions.topType(G));
					if (idx == -1)
						idx = chooseFirstWithDeclTypeOf(rights, RuleEnvironmentExtensions.objectType(G));
					if (idx == -1)
						idx = chooseFirstWithDeclTypeOf(rights, RuleEnvironmentExtensions.n4ObjectType(G));
				}
				if (idx == -1 && variance == CONTRA) {
					// choose the bottom type 'undefined' or one of the pseudo-bottom types: null
					if (idx == -1)
						idx = chooseFirstWithDeclTypeOf(rights, RuleEnvironmentExtensions.bottomType(G));
					if (idx == -1)
						idx = chooseFirstWithDeclTypeOf(rights, RuleEnvironmentExtensions.nullType(G));
				}
				if (idx == -1) {
					// simply choose the first member type (yes, we're pretty desperate at this point)
					idx = 0;
				}
				return reduce(left, rights.get(idx), variance);
			}
		}
	}

	private final int chooseFirstInferenceVariable(List<TypeRef> typeRefs) {
		final int typeRefsSize = typeRefs.size();
		for (int i = 0; i < typeRefsSize; i++) {
			final TypeRef currTypeRef = typeRefs.get(i);
			if (TypeUtils.isInferenceVariable(currTypeRef)) {
				return i;
			}
		}
		return -1;
	}

	private final int chooseFirstWithDeclTypeOf(List<TypeRef> typeRefs, Type declType) {
		final int typeRefsSize = typeRefs.size();
		for (int i = 0; i < typeRefsSize; i++) {
			final TypeRef currElem = typeRefs.get(i);
			if (currElem != null && currElem.getDeclaredType() == declType) {
				return i;
			}
		}
		return -1;
	}

	private boolean reduceTypeRef(TypeRef left, TypeRef right, Variance variance) {
		final boolean isLeftProper = TypeUtils.isProper(left);
		final boolean isRightProper = TypeUtils.isProper(right);
		if (isLeftProper && isRightProper) {
			return reduceProper(left, right, variance);
		}

		final boolean isLeftInfVar = TypeUtils.isInferenceVariable(left);
		final boolean isRightInfVar = TypeUtils.isInferenceVariable(right);
		if (isLeftInfVar || isRightInfVar) {
			if (isLeftInfVar) {
				return addBound((InferenceVariable) left.getDeclaredType(), right, variance);
			} else {
				return addBound((InferenceVariable) right.getDeclaredType(), left, variance.inverse());
			}
		}

		final boolean isLeftStructural = left.isUseSiteStructuralTyping() || left.isDefSiteStructuralTyping();
		final boolean isRightStructural = right.isUseSiteStructuralTyping() || right.isDefSiteStructuralTyping();
		if ((isLeftStructural && (variance == CONTRA || variance == INV))
				|| (isRightStructural && (variance == CO || variance == INV))) {
			return reduceStructuralTypeRef(left, right, variance);
		}
		// note: one side might still be structural, but we can ignore this
		// (e.g. given ⟨ S <: N ⟩ with S being structural, N nominal, we have a plain nominal subtype relation)

		if (left instanceof ComposedTypeRef) {
			return reduceComposedTypeRef(right, (ComposedTypeRef) left, variance.inverse());
		}
		if (right instanceof ComposedTypeRef) {
			return reduceComposedTypeRef(left, (ComposedTypeRef) right, variance);
		}

		if (left instanceof TypeTypeRef && right instanceof TypeTypeRef) {
			return reduceTypeTypeRef((TypeTypeRef) left, (TypeTypeRef) right, variance);
		} else if (left instanceof FunctionTypeExprOrRef && right instanceof FunctionTypeExprOrRef) {
			return reduceFunctionTypeExprOrRef((FunctionTypeExprOrRef) left, (FunctionTypeExprOrRef) right, variance);
		} else if (left instanceof ParameterizedTypeRef && right instanceof ParameterizedTypeRef) {
			return reduceParameterizedTypeRefNominal((ParameterizedTypeRef) left, (ParameterizedTypeRef) right,
					variance);
		} else {
			// different subtypes of TypeRef on left and right side
			// --> this looks an awful lot like an inconsistency, we're almost ready to give up on the entire constraint
			// system. However, there is one last hope: we might have a trivial constraint such as
			//
			// ⟨ undefined <: {function(number):α} ⟩
			// ⟨ {function(number):α} <: any ⟩
			//
			// To easily identify those cases and to avoid code duplication with the subtype judgment in Xsemantics, we
			// can use a small trick: we simply perform a subtype check on the non-proper type reference(s) (note: we
			// know from above that 'left' or 'right' is non-proper); normally such a type check on unresolved inference
			// variables does not make much sense, because it will fail most of the time, but here we are only looking
			// for trivial constraints that are (almost) always true.
			if (isSubtypeOf(left, right, variance)) {
				return addBound(true);
			}
			// in all other cases
			return giveUp(left, right, variance);
		}
	}

	// TODO IDE-1653 reconsider handling of wildcards in Reducer#reduceWildcard()
	private boolean reduceWildcardBoth(Wildcard left, Wildcard right, @SuppressWarnings("unused") Variance variance) {
		if (left == right) {
			// trivial ==, <:, and :> of a wildcard to itself.
			return false;
		}
		boolean wasAdded = false;
		final TypeRef lbLeft = left.getDeclaredLowerBound();
		final TypeRef lbRight = right.getDeclaredLowerBound();
		if (lbLeft != null || lbRight != null) {
			// ⟨ ? super L Φ ? ⟩ implies `L = bottom`
			// ⟨ ? super L Φ ? extends R ⟩ implies `L = bottom`
			// ⟨ ? super L Φ ? super R ⟩ implies `L = R`
			final TypeRef lbLeftOrBottom = (lbLeft != null) ? lbLeft : bottom();
			final TypeRef lbRightOrBottom = (lbRight != null) ? lbRight : bottom();
			wasAdded |= reduce(lbLeftOrBottom, lbRightOrBottom, INV);
		}
		final TypeRef ubLeft = left.getDeclaredUpperBound();
		final TypeRef ubRight = right.getDeclaredUpperBound();
		if (ubLeft != null || ubRight != null) {
			// ⟨ ? extends L Φ ? ⟩ implies `L = top`
			// ⟨ ? extends L Φ ? super R ⟩ implies `L = top`
			// ⟨ ? extends L Φ ? extends R ⟩ implies `L = R`
			final TypeRef ubLeftOrTop = (ubLeft != null) ? ubLeft : top();
			final TypeRef ubRightOrTop = (ubRight != null) ? ubRight : top();
			wasAdded |= reduce(ubLeftOrTop, ubRightOrTop, INV);
		}
		return wasAdded;
	}

	private boolean reduceWildcardRight(TypeRef left, Wildcard right, Variance variance) {
		boolean wasAdded = false;
		if (variance == CO) {
			final TypeRef ubRight = right.getDeclaredOrImplicitUpperBound();
			if (ubRight != null) {
				// ⟨ L <: ? extends UB ⟩ implies ⟨ L <: UB ⟩
				wasAdded |= reduce(left, ubRight, CO);
			}
		} else if (variance == CONTRA) {
			final TypeRef lbRight = right.getDeclaredLowerBound();
			if (lbRight != null) {
				// ⟨ L :> ? super LB ⟩ implies ⟨ L :> LB ⟩
				wasAdded |= reduce(left, lbRight, CONTRA);
			}
		}
		return wasAdded;
	}

	private boolean reduceProper(TypeRef left, TypeRef right, Variance variance) {
		return addBound(isSubtypeOf(left, right, variance));
	}

	private boolean reduceComposedTypeRef(TypeRef left, ComposedTypeRef right, Variance variance) {
		if (variance == INV) {
			boolean wasAdded = false;
			wasAdded |= reduceComposedTypeRef(left, right, CO); // n.b.: not invoking #reduce() here!
			wasAdded |= reduceComposedTypeRef(left, right, CONTRA); // n.b.: not invoking #reduce() here!
			return wasAdded;
		}
		if (right instanceof UnionTypeExpression) {
			return reduceUnion(left, (UnionTypeExpression) right, variance);
		}
		if (right instanceof IntersectionTypeExpression) {
			return reduceIntersection(left, (IntersectionTypeExpression) right, variance);
		}
		throw new IllegalStateException("shouldn't get here");
	}

	private boolean reduceUnion(TypeRef left, UnionTypeExpression right, Variance variance) {
		switch (variance) {
		case CO:
			// ⟨ L <: union{R1,R2} ⟩ implies `L <: R1` or(!) `L <: R2`
			// we've got a disjunction of several type bounds -> tricky case!
			return reduce(left, right.getTypeRefs(), CO, DISJUNCTION);
		case CONTRA:
			// ⟨ L :> union{R1,R2} ⟩ implies `L :> R1` and `L :> R2`
			// we've got a conjunction of several type bounds -> standard case
			return reduce(left, right.getTypeRefs(), CONTRA, CONJUNCTION);
		case INV:
			throw new IllegalStateException("shouldn't get here"); // should have been handled by invoker
		}
		throw new IllegalStateException("unreachable"); // actually unreachable, each case above returns or throws
	}

	private boolean reduceIntersection(TypeRef left, IntersectionTypeExpression right, Variance variance) {
		switch (variance) {
		case CO:
			// ⟨ L <: intersection{R1,R2} ⟩ implies `L <: R1` and `L <: R2`
			// we've got a conjunction of several type bounds -> standard case
			return reduce(left, right.getTypeRefs(), CO, CONJUNCTION);
		case CONTRA:
			// ⟨ L :> intersection{R1,R2} ⟩ implies `L :> R1` or(!) `L :> R2`
			// we've got a disjunction of several type bounds -> tricky case!
			return reduce(left, right.getTypeRefs(), CONTRA, DISJUNCTION);
		case INV:
			throw new IllegalStateException("shouldn't get here"); // should have been handled by invoker
		}
		throw new IllegalStateException("unreachable"); // actually unreachable, each case above returns or throws
	}

	private boolean reduceTypeTypeRef(TypeTypeRef left, TypeTypeRef right, Variance variance) {
		final TypeArgument leftStatic = TypeUtils.copy(left.getTypeArg());
		final TypeArgument rightStatic = TypeUtils.copy(right.getTypeArg());
		if (!left.isConstructorRef() && !right.isConstructorRef()) {
			// both sides are plain TypeTypeRefs
			return reduce(leftStatic, rightStatic, variance);
		} else {
			// at least one side is constructor{...}
			return reduce(leftStatic, rightStatic, INV); // TODO this is wrong
			// instead:
			// ⟨ constructor{D} <: constructor{C} ⟩ implies ⟨ D <: C ⟩ (as above) and ⟨ D#constructor <: C#constructor ⟩
		}
	}

	/**
	 * IMPORTANT: the implementation of this method has to be kept consistent with
	 * {@code SubtypeComputer#isSubtypeFunction(RuleEnvironment, FunctionTypeExprOrRef, FunctionTypeExprOrRef)} and esp.
	 * <code>#primIsSubtypeFunction()</code>.
	 */
	private boolean reduceFunctionTypeExprOrRef(FunctionTypeExprOrRef left, FunctionTypeExprOrRef right,
			Variance variance) {
		if (left.isGeneric() || right.isGeneric()) {
			final FunctionTypeExprOrRef leftNonGen = ic.newInferenceVariablesFor(left);
			final FunctionTypeExprOrRef rightNonGen = ic.newInferenceVariablesFor(right);
			return reduceFunctionTypeExprOrRef(leftNonGen, rightNonGen, variance);
		}
		boolean wasAdded = false;
		// derive constraints for types of fpars
		final Iterator<TFormalParameter> valueParsIt = right.getFpars().iterator();
		for (TFormalParameter keyPar : left.getFpars()) {
			if (valueParsIt.hasNext()) {
				wasAdded |= reduce(keyPar.getTypeRef(), valueParsIt.next().getTypeRef(),
						variance.mult(CONTRA));
			}
		}
		// derive constraints for return types
		final boolean isVoidLeft = TypeUtils.isVoidReturnType(left);
		final boolean isVoidRight = TypeUtils.isVoidReturnType(right);
		if (isVoidLeft && isVoidRight) {
			// void on both sides:
			wasAdded |= addBound(true);
		} else if ((variance == CO && isVoidRight) || (variance == CONTRA && isVoidLeft)) {
			// we have a constraint like:
			// ⟨ {function():α} <: {function():void} ⟩
			// --> α is not constrained in any way --> just add bound TRUE
			wasAdded |= addBound(true);
		} else if (isVoidLeft || isVoidRight) {
			// we have a constraint like:
			// ⟨ {function():void} <: {function():α} ⟩ or ⟨ {function():void} = {function():α} ⟩
			// --> we're doomed, unless the non-void return value is optional
			final boolean isRetValOpt = isVoidLeft ? right.isReturnValueOptional() : left.isReturnValueOptional();
			wasAdded |= addBound(isRetValOpt);
		} else {
			wasAdded |= reduce(left.getReturnTypeRef(), right.getReturnTypeRef(), variance.mult(CO));
		}
		// derive constraints for declared this types
		final TypeRef leftThis = left.getDeclaredThisType();
		final TypeRef rightThis = right.getDeclaredThisType();
		if (leftThis != null || rightThis != null) {
			if (leftThis == null && rightThis != null) {
				if (variance == CO) {
					wasAdded |= addBound(true);
				} else {
					wasAdded |= giveUp(left, right, variance);
				}
			} else if (leftThis != null && rightThis == null) {
				if (variance == CONTRA) {
					wasAdded |= addBound(true);
				} else {
					wasAdded |= giveUp(left, right, variance);
				}
			} else if (leftThis != null && rightThis != null) {
				wasAdded |= reduce(leftThis, rightThis, variance.mult(CONTRA));
			}
		}
		return wasAdded;
	}

	/**
	 * Reduction for parameterized type references according to nominal subtyping rules.
	 * <p>
	 * NOTE: 'left' might be a structural type reference iff variance == CO / 'right' might be structural (iff variance
	 * == CONTRA) but that is irrelevant and the reduction must still follow nominal subtyping rules (because the RHS of
	 * the subtype relation determines whether to use nominal or structural rules).
	 */
	private boolean reduceParameterizedTypeRefNominal(ParameterizedTypeRef left, ParameterizedTypeRef right,
			Variance variance) {
		// special case: handling of IterableN
		// (required because Array does not explicitly inherit from IterableN and the IterableN are not structural)
		// e.g., ⟨ Iterable3<int,string,int> :> Array<α> ⟩ should be reduced to ⟨ int >: α ⟩ and ⟨ string >: α ⟩
		if ((variance == CO && isSpecialCaseOfArraySubtypeIterableN(left, right))
				|| (variance == CONTRA && isSpecialCaseOfArraySubtypeIterableN(right, left))) {
			final List<TypeArgument> typeArgsOfArray = variance == CO ? left.getTypeArgs() : right.getTypeArgs();
			final List<TypeArgument> typeArgsOfIterableN = variance == CO ? right.getTypeArgs() : left.getTypeArgs();
			final List<TypeVariable> typeParamsOfIterableN = variance == CO ? right.getDeclaredType().getTypeVars()
					: left.getDeclaredType().getTypeVars();
			final TypeArgument singleTypeArgOfArray = !typeArgsOfArray.isEmpty() ? typeArgsOfArray.get(0) : null;
			boolean wasAdded = false;
			final int len = Math.min(typeArgsOfIterableN.size(), typeParamsOfIterableN.size());
			for (int idx = 0; idx < len; idx++) {
				TypeArgument currTypeArgOfIterableN = typeArgsOfIterableN.get(idx);
				TypeVariable curTypeParamOfIterableN = typeParamsOfIterableN.get(idx);
				wasAdded |= reduceConstraintForTypeArgumentPair(currTypeArgOfIterableN, curTypeParamOfIterableN,
						singleTypeArgOfArray);
			}
			return wasAdded;
		}

		// standard cases:
		final TypeRef leftRaw = TypeUtils.createTypeRef(left.getDeclaredType());
		final TypeRef rightRaw = TypeUtils.createTypeRef(right.getDeclaredType()); // note: enforcing nominal here!
		if ((variance == CO && !ts.subtypeSucceeded(G, leftRaw, rightRaw))
				|| (variance == CONTRA && !ts.subtypeSucceeded(G, rightRaw, leftRaw))
				|| (variance == INV && !ts.equaltypeSucceeded(G, leftRaw, rightRaw))) {
			return giveUp(left, right, variance);
		}
		//
		// here we have a situation like ⟨ G<IV> Φ G<string> ⟩ (with IV being an inference variable) which may result
		// from code such as:
		//
		// class G<T> {}
		// function <IV> f(G<IV> p) : void {}
		// var G<string> gstr;
		// f(gstr); // "expected type :> actual type" will lead to "G<IV> :> G<string>"
		//
		// resulting in the constraint "IV = string".
		//
		// However, it is not always that simple. The type argument corresponding to the
		// inference variable ('string' corresponding to 'IV' in the above example) might
		// not be held by the ParameterizedTypeRef but may instead be contained somewhere
		// in the inheritance hierarchy of 'value'. For example:
		//
		// class G<T> {}
		// function <IV> f(G<IV> p) : void {}
		// class C extends G<string> {}
		// var C c;
		// f(c); // will lead to "G<IV> :> C"
		//
		// Of course, the inheritance hierarchy might be arbitrarily complex and it is the
		// job of method #addSubstitutions(RuleEnv,TypeRef) in GenericsComputer to deal with
		// that. So, we have to be careful to reuse that method here to not duplicate logic!
		//
		// Solution:
		// We derive a constraint for the above situations in several steps
		// (start with:)
		// G<IV> :> C
		// (now, map each type argument of 'left' to corresponding type parameter of 'left':)
		// IV <-> T
		// (then, perform type variable substitution on the current right-hand side ("T" in our example) based on the
		// substitutions defined by the original right-hand side ("C" in our example), i.e. substitutions obtained by
		// calling #addSubstitutions(RuleEnv,right):)
		// IV <-> string
		if (variance == CO) {
			// normalize ⟨ B <: A ⟩ to ⟨ A :> B ⟩ to make sure the (raw) subtype is on the right-hand side
			final ParameterizedTypeRef tmp = left;
			left = right;
			right = tmp;
			variance = CONTRA;
		}
		boolean wasAdded = false;
		final RuleEnvironment Gx = RuleEnvironmentExtensions.newRuleEnvironment(G);
		tsh.addSubstitutions(Gx, right);
		final Type leftType = left.getDeclaredType();
		final List<TypeArgument> leftArgs = left.getTypeArgs();
		final List<TypeVariable> leftParams = leftType.getTypeVars();
		final int len = Math.min(leftArgs.size(), leftParams.size());
		for (int idx = 0; idx < len; ++idx) {
			final TypeArgument leftArg = leftArgs.get(idx);
			final TypeVariable leftParam = leftParams.get(idx);
			if (RuleEnvironmentExtensions.hasSubstitutionFor(Gx, leftParam)) {
				final TypeRef leftParamSubst = ts.substTypeVariables(Gx, TypeUtils.createTypeRef(leftParam));
				wasAdded |= reduceConstraintForTypeArgumentPair(leftArg, leftParam, leftParamSubst);
			}
		}
		return wasAdded;

	}

	/**
	 * Will add a constraint ⟨ leftArg :> rightArg ⟩, taking into account wildcards, closed existential types, and
	 * definition site variance.
	 */
	private boolean reduceConstraintForTypeArgumentPair(TypeArgument leftArg, TypeVariable leftParam,
			TypeArgument rightArg) {
		boolean wasAdded = false;
		if (leftArg instanceof Wildcard) {
			final TypeRef ub = ((Wildcard) leftArg).getDeclaredUpperBound();
			if (ub != null) {
				wasAdded |= reduce(ub, ts.upperBound(G, rightArg), CONTRA);
			}
			final TypeRef lb = ((Wildcard) leftArg).getDeclaredLowerBound();
			if (lb != null) {
				wasAdded |= reduce(lb, ts.lowerBound(G, rightArg), CO);
			}
		} else if (rightArg instanceof ExistentialTypeRef) {
			// TODO IDE-1653 reconsider this entire case
			// re-open the existential type, because we assume it was closed only while adding substitutions
			// UPDATE: this is wrong if right.typeArgs already contained an ExistentialTypeRef! (but might be
			// an non-harmful over approximation)
			final Wildcard w = ((ExistentialTypeRef) rightArg).getWildcard();
			final TypeRef ub = w.getDeclaredUpperBound();
			if (ub != null) {
				wasAdded |= reduce(ub, ts.upperBound(G, leftArg), CONTRA);
			}
			final TypeRef lb = w.getDeclaredLowerBound();
			if (lb != null) {
				wasAdded |= reduce(lb, ts.lowerBound(G, leftArg), CO);
			}
		} else {
			if (!(leftArg instanceof TypeRef)) {
				throw new UnsupportedOperationException("unsupported subtype of TypeArgument: "
						+ leftArg.getClass().getName());
			}
			// due to the assumption of the method, we always have: leftArg :> rightArg
			// (so for def-site variance we just look at the left side in this case, i.e. leftParam)
			final Variance leftDefSiteVarianceRaw = leftParam.getVariance();
			final Variance leftDefSiteVariance = leftDefSiteVarianceRaw != null ? leftDefSiteVarianceRaw : INV;
			wasAdded |= reduce(leftArg, rightArg, CONTRA.mult(leftDefSiteVariance));
		}
		return wasAdded;
	}

	/**
	 * Returns true iff left has declared type <code>Array</code> and right has an <code>IterableN</code> as declared
	 * type.
	 */
	private boolean isSpecialCaseOfArraySubtypeIterableN(ParameterizedTypeRef left, ParameterizedTypeRef right) {
		final boolean leftIsArray = left.getDeclaredType() == RuleEnvironmentExtensions.arrayType(G);
		final boolean rightIsIterableN = RuleEnvironmentExtensions.isIterableN(G, right);
		return leftIsArray && rightIsIterableN;
	}

	private boolean reduceStructuralTypeRef(TypeRef left, TypeRef right, Variance variance) {
		if (variance == CONTRA) {
			return reduceStructuralTypeRef(right, left, CO);
		}
		// now, variance is either CO or INV

		final StructuralTypingComputer stc = tsh.getStructuralTypingComputer();
		final RuleEnvironment G2 = G; // RuleEnvironmentExtensions.wrap(G); // FIXME!!!!
		final StructTypingInfo infoFaked = new StructTypingInfo(G2, left, right, // <- G2 will be changed!
				left.getTypingStrategy(), right.getTypingStrategy());

		boolean wasAdded = false;
		final StructuralTypesHelper structTypesHelper = tsh.getStructuralTypesHelper();
		final StructuralMembersTripleIterator iter = structTypesHelper.getMembersTripleIterator(G2, left, right, false);
		while (iter.hasNext() && !ic.isDoomed()) {
			final StructuralMembersTriple next = iter.next();
			final TMember l = next.getLeft();
			final TMember r = next.getRight();
			if (l == null || r == null) {
				// ignore missing members
				// (Note: we're ignoring this altogether, here. We could check if the existing member is optional and
				// otherwise add bound FALSE, but this is not our job. There are other validations checking that and
				// commencing with type inference here produces better error messages.)
				continue;
			}
			final TypeConstraint constraint = stc.reduceMembers(left, l, r, variance, infoFaked);
			wasAdded |= reduce(constraint);
		}
		return wasAdded;
	}

	/**
	 * Convenience method to perform subtype checks. Depending on the given variance, this will check
	 * <code>left &lt;: right</code> OR <code>left >: right</code> OR both.
	 */
	private boolean isSubtypeOf(TypeRef left, TypeRef right, Variance variance) {
		// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		// recursion guard
		final Pair<String, Pair<TypeRef, TypeRef>> key = Pair.of(RuleEnvironmentExtensions.GUARD_REDUCER_IS_SUBTYPE_OF,
				Pair.of(left, right));
		if (G.get(key) != null) {
			return true;
		}
		final RuleEnvironment G2 = RuleEnvironmentExtensions.wrap(G);
		G2.put(key, Boolean.TRUE);
		// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		switch (variance) {
		case CO:
			return ts.subtypeSucceeded(G2, left, right);
		case CONTRA:
			return ts.subtypeSucceeded(G2, right, left);
		case INV:
			return ts.equaltypeSucceeded(G2, left, right);
		}
		throw new IllegalStateException("unreachable"); // actually unreachable, each case above returns
	}

	private boolean mightBeSubtypeOf(FunctionTypeExprOrRef left, FunctionTypeExprOrRef right) {
		// step 1: replace all inference variables by UnknownTypeRef
		final TypeRef unknown = TypeRefsFactory.eINSTANCE.createUnknownTypeRef();
		final RuleEnvironment G_temp = RuleEnvironmentExtensions.newRuleEnvironment(G);
		for (InferenceVariable iv : ic.getInferenceVariables()) {
			RuleEnvironmentExtensions.addTypeMapping(G_temp, iv, unknown);
		}
		final TypeRef leftSubst = ts.substTypeVariables(G_temp, left);
		final TypeRef rightSubst = ts.substTypeVariables(G_temp, right);
		// step 2: now, perform subtype check reusing existing logic
		return ts.subtypeSucceeded(G, leftSubst, rightSubst);
	}

	private TypeRef bottom() {
		return RuleEnvironmentExtensions.bottomTypeRef(G);
	}

	private TypeRef top() {
		return RuleEnvironmentExtensions.topTypeRef(G);
	}

	private void log(final String message) {
		ic.log(message);
	}
}
