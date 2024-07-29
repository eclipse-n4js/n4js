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
package org.eclipse.n4js.typesystem.utils;

import static org.eclipse.n4js.ts.types.TypingStrategy.STRUCTURAL_FIELD_INITIALIZER;
import static org.eclipse.n4js.ts.types.TypingStrategy.STRUCTURAL_READ_ONLY_FIELDS;
import static org.eclipse.n4js.ts.types.TypingStrategy.STRUCTURAL_WRITE_ONLY_FIELDS;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.GUARD_STRUCTURAL_TYPING_COMPUTER__IN_PROGRESS_FOR_TYPE_REF;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.functionTypeRef;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.numberTypeRef;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.objectType;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.stringTypeRef;
import static org.eclipse.n4js.typesystem.utils.StructuralTypingResult.failure;
import static org.eclipse.n4js.typesystem.utils.StructuralTypingResult.result;
import static org.eclipse.n4js.typesystem.utils.StructuralTypingResult.success;
import static org.eclipse.n4js.utils.N4JSLanguageUtils.isOptionalityLessRestrictedOrEqual;
import static org.eclipse.n4js.utils.N4JSLanguageUtils.isReadOnlyField;
import static org.eclipse.n4js.utils.N4JSLanguageUtils.isWriteableField;
import static org.eclipse.n4js.utils.StructuralMembersPredicates.GETTERS_PREDICATE;
import static org.eclipse.n4js.utils.StructuralMembersPredicates.READABLE_FIELDS_PREDICATE;
import static org.eclipse.n4js.utils.StructuralMembersPredicates.SETTERS_PREDICATE;
import static org.eclipse.n4js.utils.StructuralMembersPredicates.WRITABLE_FIELDS_PREDICATE;
import static org.eclipse.n4js.validation.IssueCodes.TYS_NO_SUBTYPE;

import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil.EqualityHelper;
import org.eclipse.n4js.ts.typeRefs.OptionalFieldStrategy;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeArgument;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.FieldAccessor;
import org.eclipse.n4js.ts.types.PrimitiveType;
import org.eclipse.n4js.ts.types.TEnum;
import org.eclipse.n4js.ts.types.TField;
import org.eclipse.n4js.ts.types.TGetter;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.ts.types.TN4Classifier;
import org.eclipse.n4js.ts.types.TSetter;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypeVariable;
import org.eclipse.n4js.ts.types.TypingStrategy;
import org.eclipse.n4js.ts.types.util.Variance;
import org.eclipse.n4js.types.utils.TypeCompareUtils;
import org.eclipse.n4js.types.utils.TypeUtils;
import org.eclipse.n4js.typesystem.N4JSTypeSystem;
import org.eclipse.n4js.typesystem.constraints.TypeConstraint;
import org.eclipse.n4js.utils.N4JSLanguageUtils;
import org.eclipse.n4js.utils.N4JSLanguageUtils.EnumKind;
import org.eclipse.n4js.utils.StructuralMembersTriple;
import org.eclipse.n4js.utils.StructuralMembersTripleIterator;
import org.eclipse.n4js.utils.StructuralTypesHelper;
import org.eclipse.n4js.validation.N4JSElementKeywordProvider;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Pair;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 */
@Singleton
public class StructuralTypingComputer extends TypeSystemHelperStrategy {

	@Inject
	private N4JSTypeSystem ts;
	@Inject
	private N4JSElementKeywordProvider keywordProvider;
	@Inject
	private StructuralTypesHelper structuralTypesHelper;

	/***/
	public static class StructTypingInfo {
		private final RuleEnvironment G;

		private final TypeRef left;

		private final TypeRef right;

		private final TypingStrategy leftStrategy;

		private final TypingStrategy rightStrategy;

		private final List<String> missingMembers = CollectionLiterals.<String> newArrayList();

		private final List<String> wrongMembers = CollectionLiterals.<String> newArrayList();

		/***/
		public StructTypingInfo(final RuleEnvironment G, final TypeRef left, final TypeRef right,
				final TypingStrategy leftStrategy, final TypingStrategy rightStrategy) {
			super();
			this.G = G;
			this.left = left;
			this.right = right;
			this.leftStrategy = leftStrategy;
			this.rightStrategy = rightStrategy;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((this.G == null) ? 0 : this.G.hashCode());
			result = prime * result + ((this.left == null) ? 0 : this.left.hashCode());
			result = prime * result + ((this.right == null) ? 0 : this.right.hashCode());
			result = prime * result + ((this.leftStrategy == null) ? 0 : this.leftStrategy.hashCode());
			result = prime * result + ((this.rightStrategy == null) ? 0 : this.rightStrategy.hashCode());
			result = prime * result + ((this.missingMembers == null) ? 0 : this.missingMembers.hashCode());
			return prime * result + ((this.wrongMembers == null) ? 0 : this.wrongMembers.hashCode());
		}

		@Override

		public boolean equals(final Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			StructuralTypingComputer.StructTypingInfo other = (StructuralTypingComputer.StructTypingInfo) obj;
			if (this.G == null) {
				if (other.G != null)
					return false;
			} else if (!this.G.equals(other.G))
				return false;
			if (this.left == null) {
				if (other.left != null)
					return false;
			} else if (!this.left.equals(other.left))
				return false;
			if (this.right == null) {
				if (other.right != null)
					return false;
			} else if (!this.right.equals(other.right))
				return false;
			if (this.leftStrategy == null) {
				if (other.leftStrategy != null)
					return false;
			} else if (!this.leftStrategy.equals(other.leftStrategy))
				return false;
			if (this.rightStrategy == null) {
				if (other.rightStrategy != null)
					return false;
			} else if (!this.rightStrategy.equals(other.rightStrategy))
				return false;
			if (this.missingMembers == null) {
				if (other.missingMembers != null)
					return false;
			} else if (!this.missingMembers.equals(other.missingMembers))
				return false;
			if (this.wrongMembers == null) {
				if (other.wrongMembers != null)
					return false;
			} else if (!this.wrongMembers.equals(other.wrongMembers))
				return false;
			return true;
		}

		@Override
		public String toString() {
			ToStringBuilder b = new ToStringBuilder(this);
			b.add("G", this.G);
			b.add("left", this.left);
			b.add("right", this.right);
			b.add("leftStrategy", this.leftStrategy);
			b.add("rightStrategy", this.rightStrategy);
			b.add("missingMembers", this.missingMembers);
			b.add("wrongMembers", this.wrongMembers);
			return b.toString();
		}

		/***/
		public RuleEnvironment getG() {
			return this.G;
		}

		/***/
		public TypeRef getLeft() {
			return this.left;
		}

		/***/
		public TypeRef getRight() {
			return this.right;
		}

		/***/
		public TypingStrategy getLeftStrategy() {
			return this.leftStrategy;
		}

		/***/
		public TypingStrategy getRightStrategy() {
			return this.rightStrategy;
		}

		/***/
		public List<String> getMissingMembers() {
			return this.missingMembers;
		}

		/***/
		public List<String> getWrongMembers() {
			return this.wrongMembers;
		}
	}

	/***/
	public StructuralTypingResult isStructuralSubtype(RuleEnvironment G, TypeRef left, TypeRef right) {
		TypingStrategy leftStrategy = left.getTypingStrategy();
		TypingStrategy rightStrategy = right.getTypingStrategy();

		// shortcut: keep in sync with Reducer#reduceStructuralTypeRef()
		if (leftStrategy == rightStrategy
				&& left.getDeclaredType() == right.getDeclaredType()
				&& (left.getDeclaredType() instanceof TN4Classifier || left.getDeclaredType() instanceof TypeVariable) // <--
																														// IDEBUG-838
				&& left.getStructuralMembers().isEmpty() && right.getStructuralMembers().isEmpty()) {

			if (!left.isGeneric()) {
				return result(left, right, Collections.emptyList(), Collections.emptyList());
			} else if (left instanceof ParameterizedTypeRef && right instanceof ParameterizedTypeRef) {
				EList<TypeArgument> leftTypeArgs = left.getTypeArgsWithDefaults();
				EList<TypeArgument> rightTypeArgs = right.getTypeArgsWithDefaults();
				if (leftTypeArgs.size() == rightTypeArgs.size()
						&& leftTypeArgs.size() == right.getDeclaredType().getTypeVars().size()) {
					var typeArgsEqual = true;
					for (var i = 0; typeArgsEqual && i < leftTypeArgs.size(); i++) {
						TypeArgument leftTypeArg = leftTypeArgs.get(i);
						TypeArgument rightTypeArg = rightTypeArgs.get(i);

						Variance variance = right.getDeclaredType().getVarianceOfTypeVar(i);
						Result tempResult = tsh.checkTypeArgumentCompatibility(G, leftTypeArg, rightTypeArg,
								Optional.of(variance), false);

						if (tempResult.isFailure()) {
							return failure(left.getTypeRefAsString() + " is not a structural subtype of "
									+ right.getTypeRefAsString() + " due to type argument incompatibility: "
									+ tempResult.getFailureMessage());
						}
					}
					return result(left, right, Collections.emptyList(), Collections.emptyList());
				}
			}
		}

		// check if we are dealing with structural primitive types
		StructuralTypingResult primitiveSubtypingResult = isPrimitiveStructuralSubtype(G, left, right);
		if (null != primitiveSubtypingResult) {
			return primitiveSubtypingResult;
		}

		// recursion guard (see method #isStructuralSubtypingInProgressFor() for details)
		if (isStructuralSubtypingInProgressFor(G, left, right)) {
			return result(left, right, Collections.emptyList(), Collections.emptyList());
		}
		RuleEnvironment G2 = RuleEnvironmentExtensions.wrap(G);
		rememberStructuralSubtypingInProgressFor(G2, left, right);

		StructTypingInfo info = new StructTypingInfo(G2, left, right, leftStrategy, rightStrategy); // we'll collect
																									// error messages in
																									// here

		StructuralMembersTripleIterator iter = structuralTypesHelper.getMembersTripleIterator(G2, left, right, true);
		while (iter.hasNext()) {
			// check if left member can fulfill the structural requirement imposed by right member
			checkMembers(left, iter.next(), info);
		}

		return result(left, right, info.missingMembers, info.wrongMembers);
	}

	/**
	 * Special handling for primitive-structural types.
	 *
	 * <p>
	 * Note that this method only returns a non-null {@link StructuralTypingResult} if primitive structural subtyping is
	 * applicable for the given operands {@code left} and {@code right}.
	 * </p>
	 *
	 * @returns A {@link StructuralTypingResult} if primitive structural typing is applicable. {@code null} otherwise.
	 */
	StructuralTypingResult isPrimitiveStructuralSubtype(RuleEnvironment G, TypeRef leftRaw, TypeRef right) {

		// for the purpose of the rules implemented here, a number-/string-based enum behaves like type
		// 'number'/'string' (lower-case)
		TypeRef left = changeNumberOrStringBasedEnumToPrimitive(G, leftRaw);

		// check if we're dealing with structural primitive types
		boolean rightIsPrimitive = right.getDeclaredType() instanceof PrimitiveType;
		boolean leftIsPrimitive = left.getDeclaredType() instanceof PrimitiveType;

		// primitive type on the right and non-primitive on the left
		if (rightIsPrimitive && !leftIsPrimitive) {
			return failure(TYS_NO_SUBTYPE.getMessage(leftRaw.getTypeRefAsString(), right.getTypeRefAsString()));
		}
		// primitive type on the left and non-primitive on the right
		else if (leftIsPrimitive && !rightIsPrimitive) {
			return failure(TYS_NO_SUBTYPE.getMessage(leftRaw.getTypeRefAsString(), right.getTypeRefAsString()));
		}
		// primitive types on both sides
		else if (leftIsPrimitive && rightIsPrimitive) {
			// types must match nominally
			if (left.getDeclaredType() == right.getDeclaredType()) {
				return success();
			} else {
				return failure(TYS_NO_SUBTYPE.getMessage(leftRaw.getTypeRefAsString(), right.getTypeRefAsString()));
			}
		}
		// neither left nor right is primitive
		else {
			// shouldn't be handled by this method
			return null;
		}
	}

	/**
	 * Replace type references pointing to the type of a <code>@NumberBased</code> / <code>@StringBased</code> enum by a
	 * reference to built-in type <code>number</code> / <code>string</code>, leaving all other types unchanged.
	 */
	private TypeRef changeNumberOrStringBasedEnumToPrimitive(RuleEnvironment G, TypeRef typeRef) {
		Type declType = typeRef.getDeclaredType();
		if (declType instanceof TEnum) {
			EnumKind enumKind = N4JSLanguageUtils.getEnumKind((TEnum) declType);
			if (enumKind == EnumKind.NumberBased) {
				return numberTypeRef(G);
			} else if (enumKind == EnumKind.StringBased) {
				return stringTypeRef(G);
			}
		}
		return typeRef;
	}

	private void checkMembers(TypeRef leftTypeRef, StructuralMembersTriple triple, StructTypingInfo info) {
		TMember leftMember = triple.getLeft();
		TMember rightMember = triple.getRight();
		FieldAccessor leftOtherAccessor = triple.getLeftOtherAccessor();
		TypingStrategy leftStrategy = info.leftStrategy;
		TypingStrategy rightStrategy = info.rightStrategy;

		checkMembers(leftTypeRef, leftMember, rightMember, info);

		switch (rightStrategy) {

		// For any ~r~ right members.
		case STRUCTURAL_READ_ONLY_FIELDS: {

			// For any readable, non-optional right members. Initialized fields does not count as optional.
			boolean handleOptionality = isOptionalityLessRestrictedOrEqual(
					leftTypeRef.getASTNodeOptionalFieldStrategy(), OptionalFieldStrategy.GETTERS_OPTIONAL);
			boolean memberNecessary = !rightMember.isOptional() || (rightMember.isOptional() && !handleOptionality);
			if (memberNecessary && READABLE_FIELDS_PREDICATE.apply(rightMember)) {

				// For ~w~ left members requires an explicit getter.
				if (STRUCTURAL_WRITE_ONLY_FIELDS == leftStrategy
						&& !GETTERS_PREDICATE.apply(leftMember)
						&& !GETTERS_PREDICATE.apply(leftOtherAccessor)) {

					info.wrongMembers
							.add(rightMember.getName() + " failed: readable field requires a getter in subtype.");

					// Otherwise any readable field is enough.
				} else if (!READABLE_FIELDS_PREDICATE.apply(leftMember)
						&& !READABLE_FIELDS_PREDICATE.apply(leftOtherAccessor)) {
					info.wrongMembers.add(rightMember.getName()
							+ " failed: readable field requires a readable field or a getter in subtype.");
				}
			}
			break;
		}

		// For any ~w~ right members.
		case STRUCTURAL_WRITE_ONLY_FIELDS: {

			// For any writable right members.
			if (WRITABLE_FIELDS_PREDICATE.apply(rightMember) && (leftTypeRef
					.getASTNodeOptionalFieldStrategy() != OptionalFieldStrategy.FIELDS_AND_ACCESSORS_OPTIONAL)) {

				// If left is either ~r~ or ~i~, then an explicit setter is required.
				if ((STRUCTURAL_READ_ONLY_FIELDS == leftStrategy || STRUCTURAL_FIELD_INITIALIZER == leftStrategy)
						&& !SETTERS_PREDICATE.apply(leftMember)
						&& !SETTERS_PREDICATE.apply(leftOtherAccessor)) {

					info.wrongMembers
							.add(rightMember.getName() + " failed: writable field requires a setter in subtype.");

					// Otherwise any writable field (data field or setter) is sufficient.
				} else if (!WRITABLE_FIELDS_PREDICATE.apply(leftMember)
						&& !WRITABLE_FIELDS_PREDICATE.apply(leftOtherAccessor)) {
					info.wrongMembers.add(rightMember.getName()
							+ " failed: writable field requires a writable field or a setter in subtype.");
				}
			}
			break;
		}

		// For any ~i~ right members.
		case STRUCTURAL_FIELD_INITIALIZER: {

			// For any non-optional and writable right members.
			// Important: unlike in case of ~r~, here we treat initialized fields, such as @Final ones as optional
			// fields.
			if (WRITABLE_FIELDS_PREDICATE.apply(rightMember) && TypeUtils.isMandatoryField(rightMember)) {

				// If left is ~w~, then getters are required.
				if (STRUCTURAL_WRITE_ONLY_FIELDS == leftStrategy
						&& !(GETTERS_PREDICATE.apply(leftMember) || GETTERS_PREDICATE.apply(leftOtherAccessor))) {

					info.wrongMembers.add(rightMember.getName()
							+ " failed: non-optional writable field requires a getter in subtype.");

					// Otherwise any readable fields are fine.
				} else if (!(READABLE_FIELDS_PREDICATE.apply(leftMember)
						|| READABLE_FIELDS_PREDICATE.apply(leftOtherAccessor))) {
					info.wrongMembers.add(rightMember.getName()
							+ " failed: non-optional writable field requires a readable field or a getter in subtype.");
				}
			}
			break;
		}

		default:

			// If the left member is ~r~, ~w~ and/or ~i~ type.
			if (STRUCTURAL_READ_ONLY_FIELDS == leftStrategy || STRUCTURAL_WRITE_ONLY_FIELDS == leftStrategy
					|| STRUCTURAL_FIELD_INITIALIZER == leftStrategy) {

				// If right is writable field, a getter/setter pair is mandatory on the left.
				if (isWriteableField(rightMember)) {
					if (!isGetterSetterPair(leftMember, leftOtherAccessor)) {
						info.wrongMembers.add(rightMember.getName()
								+ " failed: writable field requires a getter/setter pair in subtype.");
					}
				} else
				// If right is readable, we require an explicit getter.
				if (READABLE_FIELDS_PREDICATE.apply(rightMember)) {
					if (!(GETTERS_PREDICATE.apply(leftMember) || GETTERS_PREDICATE.apply(leftOtherAccessor))) {
						info.wrongMembers
								.add(rightMember.getName() + " failed: read-only field requires a getter in subtype.");
					}
				} else
				// If there is a setter on the right, then we need a setter on the left.
				if (SETTERS_PREDICATE.apply(rightMember)) {
					if (!(SETTERS_PREDICATE.apply(leftMember) || SETTERS_PREDICATE.apply(leftOtherAccessor))) {
						info.wrongMembers.add(rightMember.getName() + " failed: setter requires a setter in subtype.");
					}
				}

			} else {
				// for a writable field on the right-hand side, require a getter/setter pair on the left
				if (isWriteableField(rightMember) && leftMember instanceof FieldAccessor) {
					if (!(leftOtherAccessor instanceof TSetter)) {
						if (leftTypeRef
								.getASTNodeOptionalFieldStrategy() != OptionalFieldStrategy.FIELDS_AND_ACCESSORS_OPTIONAL) {
							boolean isSpecialCaseOfDispensableGetterForOptionalField = leftMember instanceof TSetter
									&& rightMember.isOptional()
									&& (leftTypeRef
											.getASTNodeOptionalFieldStrategy() == OptionalFieldStrategy.GETTERS_OPTIONAL);
							if (!isSpecialCaseOfDispensableGetterForOptionalField) {
								// special error message in case only either a getter XOR setter is supplied for a field
								String msgSpecial = (leftMember instanceof TGetter && rightMember.isOptional())
										? "optional writable field requires at least a setter in subtype."
										: "writable field requires a field or a getter/setter pair in subtype.";
								info.wrongMembers.add(rightMember.getName() + " failed: " + msgSpecial);
							}
						}
					} else {
						// check type of setter as usual
						checkMembers(leftTypeRef, leftOtherAccessor, rightMember, info);
					}
				}

			}
			break;
		}

	}

	/** Returns with {@code true} iff the the arguments are a getter-setter accessor pair. */
	private boolean isGetterSetterPair(TMember firstLeft, TMember secondLeft) {
		return (GETTERS_PREDICATE.apply(firstLeft) || GETTERS_PREDICATE.apply(secondLeft))
				&& (SETTERS_PREDICATE.apply(secondLeft) || SETTERS_PREDICATE.apply(secondLeft));
	}

	/**
	 * Checks if the member 'left' (may be <code>null</code> if not found) fulfills the structural requirement
	 * represented by member 'right'. In the error case, the two lists of error messages in 'info' are updated
	 * accordingly.
	 * <p>
	 * NOTE: in case of a field on right-hand side, this method accepts a getter OR(!) a setter of appropriate type on
	 * left side; the requirement that BOTH a getter AND setter must be provided for a writable field must be checked
	 * outside this method.
	 */
	private void checkMembers(TypeRef leftTypeRef, TMember left, TMember right, StructTypingInfo info) {
		RuleEnvironment G = info.G;

		// !!! keep the following aligned with below method #reduceMembers() !!!
		if (left == null) {
			// no corresponding member found on left side
			if (memberIsMissing(leftTypeRef, right, info)) {

				if (right instanceof TMethod) {
					if (((TMethod) right).isCallSignature()) {
						// in case a call signature is missing on the left side, it is ok
						// if the left side is itself a function and is override-compatible:
						tsh.addSubstitutions(G, info.right);
						TypeRef rightTypeRef = ts.substTypeVariables(G, TypeUtils.createTypeRef((TMethod) right));
						Result result = ts.subtype(G, info.left, rightTypeRef);
						if (result.isSuccess()) {
							return; // success
						}
						if (ts.subtypeSucceeded(G, info.left, functionTypeRef(G))) {
							info.wrongMembers.add(keywordProvider.keyword(right, info.rightStrategy) + " failed: "
									+ result.getFailureMessage());
							return;
						}
					}

				} else if (right instanceof TField) {
					OptionalFieldStrategy leftOptionalStrategy = leftTypeRef.getASTNodeOptionalFieldStrategy();
					if (leftOptionalStrategy == OptionalFieldStrategy.GETTERS_OPTIONAL) {
						info.missingMembers.add("setter or field " + right.getName());
						return;
					}
				}

				info.missingMembers.add(keywordProvider.keyword(right, info.rightStrategy) + " " + right.getName());
			}

		} else {
			// found a corresponding member
			// -> make sure types are compatible

			Variance variance;
			if (left.isOptional() && !right.isOptional()) {
				info.missingMembers.add(left.getName()
						+ " failed: non-optional member requires a corresponding non-optional member in the structural subtype.");
				return;
			} else if (isWriteableField(right) && left instanceof TField) {
				// Fields are on both sides.
				// T_FL = T_FR
				if (isReadOnlyField(left)
						&& STRUCTURAL_FIELD_INITIALIZER != info.rightStrategy
						&& STRUCTURAL_READ_ONLY_FIELDS != info.rightStrategy) {
					info.wrongMembers.add(right.getName() + " failed: field is read-only.");
					return;
				} else {
					variance = Variance.INV;
				}
			} else if (right instanceof TSetter || left instanceof TSetter) {
				// Setter on one side (other side may be field or setter).
				// contra-variant
				variance = Variance.CONTRA;
			} else {
				// Other cases such as methods and function expressions.
				// Only L<:R.
				variance = Variance.CO;
			}

			Pair<TypeArgument, TypeArgument> mtypes = getMemberTypes(left, right, info);
			Result result = tsh.checkTypeArgumentCompatibility(G, mtypes.getKey(), mtypes.getValue(),
					Optional.of(variance), true);
			if (result.isFailure()) {
				info.wrongMembers.add(getMemberName(right) + " failed: " + result.getFailureMessage());
			}
		}
	}

	/**
	 * Same as previous method, but instead of actually checking the types, we return 0..2 constraints. This would
	 * normally belong into class <code>InferenceContext</code>, but is placed here to keep it aligned with above method
	 * more easily.
	 */
	public List<TypeConstraint> reduceMembers(RuleEnvironment G,
			TypeRef leftTypeRef, TMember left, TMember right, StructTypingInfo info) {

		// !!! keep the following aligned with above method #checkMembers() !!!
		if (left == null) {
			// no corresponding member found on left side
			if (memberIsMissing(leftTypeRef, right, info)) {
				if (right instanceof TMethod) {
					if (((TMethod) right).isCallSignature()) {
						// in case a call signature is missing on the left side, it is ok
						// if the left side is itself a function and is override-compatible:
						tsh.addSubstitutions(G, info.right);
						TypeRef rightTypeRef = ts.substTypeVariables(G, TypeUtils.createTypeRef((TMethod) right));
						return Collections.singletonList(
								new TypeConstraint(info.left, rightTypeRef, Variance.CO));
					}
				}
				return Collections.singletonList(TypeConstraint.FALSE);
			} else {
				return Collections.emptyList(); // this is like returning TypeConstraint.TRUE
			}

		} else {

			Variance variance;
			if (left.isOptional() && !right.isOptional()) {
				return Collections.singletonList(TypeConstraint.FALSE);
			} else if (isWriteableField(right) && left instanceof TField) {
				if (isReadOnlyField(left)
						&& STRUCTURAL_FIELD_INITIALIZER != info.rightStrategy
						&& STRUCTURAL_READ_ONLY_FIELDS != info.rightStrategy) {
					return Collections.singletonList(TypeConstraint.FALSE);
				} else {
					variance = Variance.INV;
				}
			} else if (right instanceof TSetter || left instanceof TSetter) {
				variance = Variance.CONTRA;
			} else {
				variance = Variance.CO;
			}
			Pair<TypeArgument, TypeArgument> mtypes = getMemberTypes(left, right, info);
			return tsh.reduceTypeArgumentCompatibilityCheck(G, mtypes.getKey(), mtypes.getValue(),
					Optional.of(variance), false);
		}
	}

	private boolean memberIsMissing(TypeRef leftTypeRef, TMember right, StructTypingInfo info) {
		boolean rightMemberIsOptional = rightMemberIsOptional(leftTypeRef, right, info.rightStrategy);
		if (rightMemberIsOptional) {
			// nothing to do (rightMember is optional or initialized for ~i~ typing.)
			return false;
		} else if (right != null && right.getContainingType() == objectType(info.G)) {
			// ignore object members, can only happen if left is structural field type
			return false;
		} else {
			// standard case of missing member on left side -> report error
			return true;
		}
	}

	/**
	 * This method implements Req-IDE-240500 in language spec.
	 */
	private boolean rightMemberIsOptional(TypeRef leftTypeRef, TMember right, TypingStrategy rightStrategy) {
		OptionalFieldStrategy leftOptionalStrategy = leftTypeRef.getASTNodeOptionalFieldStrategy();

		// Whether a right member is optional depends on both the right typing strategy and the right optionality field
		// strategy.
		switch (rightStrategy) {
		case STRUCTURAL:
		case STRUCTURAL_FIELDS: {
			// L <: ~N or L <: ~~N
			if (!right.isOptional()) {
				return false;
			} else {
				return (leftOptionalStrategy == OptionalFieldStrategy.FIELDS_AND_ACCESSORS_OPTIONAL) ||
						(right instanceof TGetter && isOptionalityLessRestrictedOrEqual(leftOptionalStrategy,
								OptionalFieldStrategy.GETTERS_OPTIONAL));
			}
		}

		case STRUCTURAL_WRITE_ONLY_FIELDS: {
			// L <: ~w~N
			return right.isOptional() && (leftOptionalStrategy == OptionalFieldStrategy.FIELDS_AND_ACCESSORS_OPTIONAL);
		}

		case STRUCTURAL_READ_ONLY_FIELDS: {
			// L <: ~r~N
			return right.isOptional()
					&& isOptionalityLessRestrictedOrEqual(leftOptionalStrategy, OptionalFieldStrategy.GETTERS_OPTIONAL);
		}

		case STRUCTURAL_FIELD_INITIALIZER: {
			// L <: ~i~N
			return (right.isOptional()
					&& isOptionalityLessRestrictedOrEqual(leftOptionalStrategy, OptionalFieldStrategy.GETTERS_OPTIONAL))
					||
					TypeUtils.isInitializedField(right) || TypeUtils.isOptionalSetter(right);
		}

		default: {
			return false;
		}
		}
	}

	/**
	 * Store a guard in the given rule environment to note that we are in the process of inferring left ~<: right.
	 * <p>
	 * IDEBUG-171: If we are already computing the structural subtype relation left ~<: right and we are again asked
	 * whether left ~<: right, then we simply want to return success. The rationale is that if we run into a cycle while
	 * checking the members' types, we can simply say the members causing the cycle won't render the overall evaluation
	 * false ("an uns soll es nicht scheitern").
	 * <p>
	 * EXAMPLE 1:
	 *
	 * <pre>
	 * class Element {
	 * 	public ~Element child;
	 * }
	 * var ~Element e1;
	 * var ~Element e2;
	 * //e1 = e2;   // remove comment to get stack overflow when disabling if-clause in #isSubtype()
	 * </pre>
	 * <p>
	 * EXAMPLE 2:
	 *
	 * <pre>
	 * class A {
	 * 	public ~B f;
	 * }
	 * class B {
	 * 	public ~A f;
	 * }
	 * var ~A a;
	 * var ~B b;
	 * //a = b;   // remove comment to get stack overflow when disabling if-clause in #isSubtype()
	 * </pre>
	 *
	 * Note how this is analogous to what EMF is doing when computing structural equality as explained in the paragraph
	 * on "populating a two way map" in the following EMF API documentation: {@link EqualityHelper}
	 */
	private void rememberStructuralSubtypingInProgressFor(RuleEnvironment G, TypeRef left, TypeRef right) {
		G.put(Pair.of(GUARD_STRUCTURAL_TYPING_COMPUTER__IN_PROGRESS_FOR_TYPE_REF, (Pair.of(wrap(left), wrap(right)))),
				Boolean.TRUE);
	}

	private boolean isStructuralSubtypingInProgressFor(RuleEnvironment G, TypeRef left, TypeRef right) {
		return G.get(Pair.of(GUARD_STRUCTURAL_TYPING_COMPUTER__IN_PROGRESS_FOR_TYPE_REF,
				(Pair.of(wrap(left), wrap(right))))) != null;
	}

	private TypeCompareUtils.SemanticEqualsWrapper wrap(TypeRef typeRef) {
		return new TypeCompareUtils.SemanticEqualsWrapper(typeRef);
	}

	private Pair<TypeArgument, TypeArgument> getMemberTypes(TMember leftMember, TMember rightMember,
			StructTypingInfo info) {

		RuleEnvironment G = info.G;
		TypeRef typeLeftRaw = ts.type(G, leftMember);
		TypeRef typeRightRaw = ts.type(G, rightMember);

		// replace bound type variables with type arguments
		RuleEnvironment G_left = RuleEnvironmentExtensions.wrap(G);
		RuleEnvironment G_right = RuleEnvironmentExtensions.wrap(G);
		tsh.addSubstitutions(G_left, info.left);
		tsh.addSubstitutions(G_right, info.right);

		TypeRef typeLeft = ts.substTypeVariables(G_left, typeLeftRaw);
		TypeRef typeRight = ts.substTypeVariables(G_right, typeRightRaw);

		return Pair.of(typeLeft, typeRight);
	}

	private String getMemberName(TMember member) {
		if (member instanceof TMethod) {
			if (((TMethod) member).isCallSignature()) {
				return "call signature";
			} else if (((TMethod) member).isConstructSignature()) {
				return "construct signature";
			}
		}
		return member.getName();
	}
}
