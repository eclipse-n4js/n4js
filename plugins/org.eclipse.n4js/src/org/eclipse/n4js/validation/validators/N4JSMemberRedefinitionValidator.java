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
package org.eclipse.n4js.validation.validators;

import static org.eclipse.n4js.validation.IssueCodes.CLF_CONSUMED_FIELD_ACCESSOR_PAIR_INCOMPLETE;
import static org.eclipse.n4js.validation.IssueCodes.CLF_CONSUMED_INHERITED_MEMBER_UNSOLVABLE_CONFLICT;
import static org.eclipse.n4js.validation.IssueCodes.CLF_CONSUMED_MEMBER_SOLVABLE_CONFLICT;
import static org.eclipse.n4js.validation.IssueCodes.CLF_CONSUMED_MEMBER_UNSOLVABLE_CONFLICT;
import static org.eclipse.n4js.validation.IssueCodes.CLF_IMPLEMENT_MEMBERTYPE_INCOMPATIBLE;
import static org.eclipse.n4js.validation.IssueCodes.CLF_MISSING_IMPLEMENTATION;
import static org.eclipse.n4js.validation.IssueCodes.CLF_MISSING_IMPLEMENTATION_EXT;
import static org.eclipse.n4js.validation.IssueCodes.CLF_NON_ACCESSIBLE_ABSTRACT_MEMBERS;
import static org.eclipse.n4js.validation.IssueCodes.CLF_OVERRIDEN_CONCRETE_WITH_ABSTRACT;
import static org.eclipse.n4js.validation.IssueCodes.CLF_OVERRIDE_ANNOTATION;
import static org.eclipse.n4js.validation.IssueCodes.CLF_OVERRIDE_CONST;
import static org.eclipse.n4js.validation.IssueCodes.CLF_OVERRIDE_FIELD_REQUIRES_ACCESSOR_PAIR;
import static org.eclipse.n4js.validation.IssueCodes.CLF_OVERRIDE_FINAL;
import static org.eclipse.n4js.validation.IssueCodes.CLF_OVERRIDE_MEMBERTYPE_INCOMPATIBLE;
import static org.eclipse.n4js.validation.IssueCodes.CLF_OVERRIDE_NON_EXISTENT;
import static org.eclipse.n4js.validation.IssueCodes.CLF_OVERRIDE_NON_EXISTENT_INTERFACE;
import static org.eclipse.n4js.validation.IssueCodes.CLF_OVERRIDE_VISIBILITY;
import static org.eclipse.n4js.validation.IssueCodes.CLF_OVERRIDE_WITH_FINAL_OR_CONST_FIELD;
import static org.eclipse.n4js.validation.IssueCodes.CLF_PSEUDO_REDEFINED_SPEC_CTOR_INCOMPATIBLE;
import static org.eclipse.n4js.validation.IssueCodes.CLF_REDEFINED_MEMBER_TYPE_INVALID;
import static org.eclipse.n4js.validation.IssueCodes.CLF_REDEFINED_METHOD_TYPE_CONFLICT;
import static org.eclipse.n4js.validation.IssueCodes.CLF_REDEFINED_NON_ACCESSIBLE;
import static org.eclipse.n4js.validation.IssueCodes.CLF_REDEFINED_TYPE_NOT_SAME_TYPE;
import static org.eclipse.n4js.validation.IssueCodes.CLF_UNMATCHED_ACCESSOR_OVERRIDE;
import static org.eclipse.n4js.validation.IssueCodes.CLF_UNMATCHED_ACCESSOR_OVERRIDE_JS;
import static org.eclipse.n4js.validation.IssueCodes.getMessageForCLF_CONSUMED_FIELD_ACCESSOR_PAIR_INCOMPLETE;
import static org.eclipse.n4js.validation.IssueCodes.getMessageForCLF_CONSUMED_INHERITED_MEMBER_UNSOLVABLE_CONFLICT;
import static org.eclipse.n4js.validation.IssueCodes.getMessageForCLF_CONSUMED_MEMBER_SOLVABLE_CONFLICT;
import static org.eclipse.n4js.validation.IssueCodes.getMessageForCLF_CONSUMED_MEMBER_UNSOLVABLE_CONFLICT;
import static org.eclipse.n4js.validation.IssueCodes.getMessageForCLF_IMPLEMENT_MEMBERTYPE_INCOMPATIBLE;
import static org.eclipse.n4js.validation.IssueCodes.getMessageForCLF_MISSING_IMPLEMENTATION;
import static org.eclipse.n4js.validation.IssueCodes.getMessageForCLF_MISSING_IMPLEMENTATION_EXT;
import static org.eclipse.n4js.validation.IssueCodes.getMessageForCLF_NON_ACCESSIBLE_ABSTRACT_MEMBERS;
import static org.eclipse.n4js.validation.IssueCodes.getMessageForCLF_OVERRIDEN_CONCRETE_WITH_ABSTRACT;
import static org.eclipse.n4js.validation.IssueCodes.getMessageForCLF_OVERRIDE_ANNOTATION;
import static org.eclipse.n4js.validation.IssueCodes.getMessageForCLF_OVERRIDE_CONST;
import static org.eclipse.n4js.validation.IssueCodes.getMessageForCLF_OVERRIDE_FIELD_REQUIRES_ACCESSOR_PAIR;
import static org.eclipse.n4js.validation.IssueCodes.getMessageForCLF_OVERRIDE_FINAL;
import static org.eclipse.n4js.validation.IssueCodes.getMessageForCLF_OVERRIDE_MEMBERTYPE_INCOMPATIBLE;
import static org.eclipse.n4js.validation.IssueCodes.getMessageForCLF_OVERRIDE_NON_EXISTENT;
import static org.eclipse.n4js.validation.IssueCodes.getMessageForCLF_OVERRIDE_NON_EXISTENT_INTERFACE;
import static org.eclipse.n4js.validation.IssueCodes.getMessageForCLF_OVERRIDE_VISIBILITY;
import static org.eclipse.n4js.validation.IssueCodes.getMessageForCLF_OVERRIDE_WITH_FINAL_OR_CONST_FIELD;
import static org.eclipse.n4js.validation.IssueCodes.getMessageForCLF_PSEUDO_REDEFINED_SPEC_CTOR_INCOMPATIBLE;
import static org.eclipse.n4js.validation.IssueCodes.getMessageForCLF_REDEFINED_MEMBER_TYPE_INVALID;
import static org.eclipse.n4js.validation.IssueCodes.getMessageForCLF_REDEFINED_METHOD_TYPE_CONFLICT;
import static org.eclipse.n4js.validation.IssueCodes.getMessageForCLF_REDEFINED_NON_ACCESSIBLE;
import static org.eclipse.n4js.validation.IssueCodes.getMessageForCLF_REDEFINED_TYPE_NOT_SAME_TYPE;
import static org.eclipse.n4js.validation.IssueCodes.getMessageForCLF_UNMATCHED_ACCESSOR_OVERRIDE;
import static org.eclipse.n4js.validation.IssueCodes.getMessageForCLF_UNMATCHED_ACCESSOR_OVERRIDE_JS;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.n4js.n4JS.MethodDeclaration;
import org.eclipse.n4js.n4JS.N4ClassDefinition;
import org.eclipse.n4js.n4JS.N4ClassifierDefinition;
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.scoping.accessModifiers.MemberVisibilityChecker;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.ContainerType;
import org.eclipse.n4js.ts.types.FieldAccessor;
import org.eclipse.n4js.ts.types.MemberAccessModifier;
import org.eclipse.n4js.ts.types.MemberType;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.TField;
import org.eclipse.n4js.ts.types.TInterface;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.TSetter;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.util.AccessModifiers;
import org.eclipse.n4js.ts.types.util.MemberList;
import org.eclipse.n4js.ts.types.util.NameStaticPair;
import org.eclipse.n4js.ts.utils.TypeUtils;
import org.eclipse.n4js.typesystem.N4JSTypeSystem;
import org.eclipse.n4js.typesystem.utils.Result;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions;
import org.eclipse.n4js.utils.ContainerTypesHelper;
import org.eclipse.n4js.utils.ContainerTypesHelper.MemberCollector;
import org.eclipse.n4js.utils.N4JSLanguageUtils;
import org.eclipse.n4js.validation.AbstractN4JSDeclarativeValidator;
import org.eclipse.n4js.validation.IssueUserDataKeys;
import org.eclipse.n4js.validation.JavaScriptVariantHelper;
import org.eclipse.n4js.validation.utils.MemberCube;
import org.eclipse.n4js.validation.utils.MemberMatrix;
import org.eclipse.n4js.validation.utils.MemberRedefinitionUtils;
import org.eclipse.n4js.validation.utils.MemberMatrix.SourceAwareIterator;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.util.Arrays;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.EValidatorRegistrar;

import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.inject.Inject;

/**
 * Implementation of constraints in chapter 5.4. Redefinition of Members.
 *
 * Remarks:
 * <ul>
 * <li>The implementation matches the constraints defined in the specification. Differences are caused by extended error
 * generation in case of not-consumed members.</li>
 * <li>Access modifiers are "fixed" before comparison in order to avoid strange (missing) errors.
 * </ul>
 */
public class N4JSMemberRedefinitionValidator extends AbstractN4JSDeclarativeValidator {

	private enum OverrideCompatibilityResult {
		COMPATIBLE, ERROR, ACCESSOR_PAIR
	}

	private enum RedefinitionType {
		overridden, implemented
	}

	@Inject
	private ContainerTypesHelper containerTypesHelper;
	@Inject
	private N4JSTypeSystem ts;
	@Inject
	private MemberVisibilityChecker memberVisibilityChecker;
	@Inject
	private JavaScriptVariantHelper jsVariantHelper;

	private final static String TYPE_VAR_CONTEXT = "TYPE_VAR_CONTEXT";

	/**
	 * NEEDED, when removed check methods will be called twice once by N4JSValidator, and once by
	 * AbstractDeclarativeN4JSValidator
	 */
	@Override
	public void register(EValidatorRegistrar registrar) {
		// nop
	}

	/**
	 * Checks constraints defined in chapter 5.4. Redefinition of Members.
	 */
	@Check
	public void checkMemberRedefinitions(N4ClassifierDefinition n4ClassifierDefinition) { // NO_UCD (unused code)

		if (!(n4ClassifierDefinition.getDefinedType() instanceof TClassifier)) {
			return; // wrongly parsed
		}
		TClassifier tClassifier = (TClassifier) n4ClassifierDefinition.getDefinedType();
		getContext().put(TClassifier.class, tClassifier);

		RuleEnvironment g = RuleEnvironmentExtensions.newRuleEnvironment(tClassifier);
		getContext().put(RuleEnvironment.class, g);
		ParameterizedTypeRef classTypeRef = TypeUtils.createTypeRef(tClassifier); // the context for type variables
		getContext().put(TYPE_VAR_CONTEXT, classTypeRef);

		MemberCube memberCube = createMemberValidationList();

		final boolean isClass = tClassifier instanceof TClass;
		final Map<ParameterizedTypeRef, MemberList<TMember>> nonAccessibleAbstractMembersBySuperTypeRef = new HashMap<>();

		for (Entry<NameStaticPair, MemberMatrix> entry : memberCube.entrySet()) {
			MemberMatrix mm = entry.getValue();

			// Set to collect all owned members that are lacking an override annotation.
			Collection<TMember> membersMissingOverrideAnnotation = new HashSet<>();

			if (isClass) {
				constraints_67_MemberOverride_checkEntry(mm, membersMissingOverrideAnnotation);
			}
			if (mm.hasImplemented()) {
				// first mix in
				if (holdConstraints_68_Consumption(mm)) {
					// then check if everything is implemented
					constraints_69_Implementation(mm, membersMissingOverrideAnnotation);
				}
			}
			constraints_60_InheritedConsumedCovariantSpecConstructor(tClassifier, mm);
			constraints_66_NonOverride(mm);
			constraints_42_45_46_AbstractMember(mm, nonAccessibleAbstractMembersBySuperTypeRef);
			unusedGenericTypeVariable(mm);

			checkUnpairedAccessorConsumption(mm, n4ClassifierDefinition);
			checkUnpairedAccessorFilling(mm, n4ClassifierDefinition);

			messageMissingOverrideAnnotation(mm, membersMissingOverrideAnnotation);
		}

		final boolean foundImpossibleExtendsImplements = !nonAccessibleAbstractMembersBySuperTypeRef.isEmpty();
		if (foundImpossibleExtendsImplements) {
			messageImpossibleExtendsImplements(n4ClassifierDefinition, nonAccessibleAbstractMembersBySuperTypeRef);
		}

		if (!foundImpossibleExtendsImplements) { // avoid consequential errors
			constraints_41_AbstractClass(tClassifier, memberCube);
		}
	}

	/**
	 * Checks a certain special case of Constraints 60 related to &#64;Spec constructors: if the classifier being
	 * validated does *not* have an owned constructor, normally nothing would be checked; in case of &#64;Spec
	 * constructors, however, we have to check that the inherited/consumed constructor (if any) is compatible to itself
	 * within the classifier being validated:
	 *
	 * <pre>
	 * class C {
	 *     &#64;CovariantConstructor constructor(&#64;Spec spec: ~i~this) {}
	 * }
	 * class D { // &lt;-- must show error here, because inherited &#64;Spec constructor not compatible to itself
	 *     public badField;
	 * }
	 * </pre>
	 */
	private boolean constraints_60_InheritedConsumedCovariantSpecConstructor(TClassifier tClassifier, MemberMatrix mm) {
		boolean isValid = true;
		if (!mm.hasOwned()) {
			final TMember firstInherited = mm.hasInherited() ? mm.inherited().iterator().next() : null;
			if (firstInherited instanceof TMethod && firstInherited.isConstructor()
					&& isCovarianceForConstructorsRequired(tClassifier, firstInherited)) {
				isValid &= checkSpecConstructorOverrideCompatibility(tClassifier, (TMethod) firstInherited);
			}
			final TMember firstImplemented = mm.hasImplemented() ? mm.implemented().iterator().next() : null;
			if (firstImplemented instanceof TMethod && firstImplemented.isConstructor()
					&& isCovarianceForConstructorsRequired(tClassifier, firstImplemented)) {
				isValid &= checkSpecConstructorOverrideCompatibility(tClassifier, (TMethod) firstImplemented);
			}
		}
		return isValid;
	}

	private boolean checkSpecConstructorOverrideCompatibility(TClassifier tClassifier, TMethod inheritedConstructor) {
		final Type rightThisContext = MemberRedefinitionUtils.findThisContextForConstructor(tClassifier,
				inheritedConstructor);
		final Result subtypeResult = isSubTypeResult(inheritedConstructor, rightThisContext, inheritedConstructor);
		if (subtypeResult.isFailure()) {
			final String msg = getMessageForCLF_PSEUDO_REDEFINED_SPEC_CTOR_INCOMPATIBLE(
					validatorMessageHelper.description(inheritedConstructor),
					validatorMessageHelper.description(tClassifier),
					validatorMessageHelper.description(rightThisContext));
			final EObject astNode = tClassifier.getAstElement(); // ok, because tClassifier is coming from AST
			addIssue(msg, astNode, N4JSPackage.eINSTANCE.getN4TypeDeclaration_Name(),
					CLF_PSEUDO_REDEFINED_SPEC_CTOR_INCOMPATIBLE);
			return false;
		}
		return true;
	}

	/**
	 * Checks if any accessor has been consumed for which the counterpart accessor is only inherited.
	 */
	private void checkUnpairedAccessorConsumption(MemberMatrix mm, N4ClassifierDefinition definition) {
		// validate conflicts between inherited and consumed accessor members only
		if (!mm.hasOwned() && mm.hasInherited() && mm.hasImplemented()) {
			// Collect whether getter and setter were consumed as well as the consumed member
			boolean getterConsumed = false;
			boolean setterConsumed = false;
			TMember consumedAccessor = null;

			for (TMember implementedMember : mm.implemented()) {
				if (implementedMember.isAccessor() && mm.isConsumed(implementedMember)) {
					if (implementedMember.getMemberType() == MemberType.GETTER) {
						getterConsumed = true;
					} else {
						setterConsumed = true;
					}
					consumedAccessor = implementedMember;
				}
			}
			// Issue error if getter/setter has been consumed and the counterpart is inherited.
			if ((getterConsumed != setterConsumed) && mm.hasAccessorPair() && null != consumedAccessor) {
				messageMissingOwnedAccessorCorrespondingConsumedAccessor((FieldAccessor) consumedAccessor,
						definition);
			}
		}
	}

	private void checkUnpairedAccessorFilling(MemberMatrix mm, N4ClassifierDefinition definition) {
		if (definition.getDefinedType().isStaticPolyfill() && mm.hasMixedAccessorPair()) {
			FieldAccessor ownedAccessor = (FieldAccessor) Iterables.getFirst(mm.owned(), null);

			if (null == ownedAccessor) {
				// Should not happen, a mixed accessor pair implies at least one owned member
				return;
			}
			if (!(definition instanceof N4ClassDefinition)) {
				// Non-class static polyfills aren't allowed. Validated somewhere else.
				return;
			}

			TClass filledClass = MemberRedefinitionUtils.getFilledClass((N4ClassDefinition) definition);
			if (null == filledClass) {
				// Invalid static polyfill class. Validated somewhere else.
				return;
			}

			// Iterate over all inherited members
			SourceAwareIterator memberIterator = mm.actuallyInheritedAndMixedMembers();
			while (memberIterator.hasNext()) {
				TMember next = memberIterator.next();
				ContainerType<?> containingType = next.getContainingType();

				// Issue an error if the member isn't owned by the filled class
				if (containingType != filledClass) {
					messageMissingOwnedAccessor(ownedAccessor);
				}
			}
		}
	}

	/**
	 * Constraints 66: Non-Override Declaration
	 */
	private boolean constraints_66_NonOverride(MemberMatrix mm) {
		if (mm.hasOwned()) {
			boolean bFoundWronglyDeclaredMember = false;
			for (TMember member : mm.owned()) {
				if (member.isDeclaredOverride()) {
					TMember m = mm.possibleOverrideCandidateOrError(member);
					if (m == null) {
						bFoundWronglyDeclaredMember = true;
						if (member.isStatic() && mm.hasNonImplemented() && !mm.hasInherited() && !mm.hasImplemented()) {
							// special case of false @Override annotation: "overriding" a static member of an interface
							final TMember other = mm.nonImplemented().iterator().next(); // simply take the first one
							String message = getMessageForCLF_OVERRIDE_NON_EXISTENT_INTERFACE(
									validatorMessageHelper.description(member),
									validatorMessageHelper.description(other));
							addIssue(message, member.getAstElement(),
									N4JSPackage.Literals.PROPERTY_NAME_OWNER__DECLARED_NAME,
									CLF_OVERRIDE_NON_EXISTENT_INTERFACE);
						} else {
							// standard case of false @Override annotation
							String message = getMessageForCLF_OVERRIDE_NON_EXISTENT(keywordProvider.keyword(member),
									member.getName());
							addIssue(message, member.getAstElement(),
									N4JSPackage.Literals.PROPERTY_NAME_OWNER__DECLARED_NAME, CLF_OVERRIDE_NON_EXISTENT);
						}
					}
				}
			}
			return bFoundWronglyDeclaredMember;
		}
		return true;
	}

	/**
	 * Constraints 67: Overriding Members (Section 5.4.1 Overriding of Members)
	 *
	 * Check constraints wrt member overrides.
	 *
	 * This method doesn't add issues for missing override annotations but adds the missing-annotation-members to the
	 * given collection.
	 */
	private void constraints_67_MemberOverride_checkEntry(MemberMatrix mm,
			Collection<TMember> membersMissingOverrideAnnotation) {
		for (TMember m : mm.owned()) {
			for (TMember s : mm.inherited()) {
				// 1. s must be accessible and
				// 2. m must be override compatible to s
				if (checkAccessibilityAndOverrideCompatibility(RedefinitionType.overridden, m, s, false,
						mm) == OverrideCompatibilityResult.COMPATIBLE) {
					// avoid consequential errors

					// 3. accessor pair for fields
					if (s.isField() && m.isAccessor()) {
						if (!mm.hasOwnedAccessorPair()) {
							messageFieldOverrideNeedsAccessorPair(m, s);
							continue; // avoid consequential errors
						}
					}

					// 4. declared overridden
					if (!m.isDeclaredOverride()) {
						membersMissingOverrideAnnotation.add(m);
					}
				}
			}
			// 5. don't allow incomplete accessor overrides / declarations (for classes that aren't annotated as
			// StaticPolyfill)
			if (m.isAccessor() && mm.hasMixedAccessorPair()) {
				if (!m.getContainingType().isStaticPolyfill()) {
					messageMissingOwnedAccessor((FieldAccessor) m);
				}
			}
		}
	}

	/**
	 * Constraints 68: Consumption of Interface Members
	 *
	 * Returns false if an error occurred which is not solvable in current classifier (i.e., incompatible meta types).
	 */
	private boolean holdConstraints_68_Consumption(MemberMatrix mm) {
		TClassifier currentType = getCurrentClassifier();
		MemberList<TMember> consumedMembers = new MemberList<>(2);
		for (TMember m : mm.implemented()) {
			boolean consume = true;
			for (SourceAwareIterator iter = mm.allMembers(); iter.hasNext();) {
				TMember m_ = iter.next();
				if (m_ == m && iter.isInterfaceMember()) {
					// the super class may implement the same interface, in that case the member should not be consumed
					// again. Thus, we only consume if both members stem from interfaces
					continue;
				}

				// 1. meta type
				if ((m.isMethod() && !m_.isMethod())
						|| (!m.isMethod() && !(m_.isAccessor() || m_.isField()))) {
					if (iter.isInterfaceMember()) {
						messageIncompatibleMembersToImplement(mm.implemented());
						return false;
					} else if (iter.isInheritedMember()) {
						messageIncompatibleInheritedMembersToImplement(m_, mm.implemented());
						return false;
					} else {
						return true;
					}
				}

				// 2 (abstract/owned), 3 (visibility) and 4 (type):
				boolean accessorPair = TypeUtils.isAccessorPair(m, m_);
				if (!accessorPair) {
					// 2. not abstract or owned
					if ((!m_.isAbstract() || m_.getContainingType() == currentType)) {
						consume = false;
						break;
					}
					// 3. access modifier
					if (AccessModifiers.less(m, m_)) {
						consume = false;
						break;
					}
					// 4. type
					if (!m_.isSetter()) {
						if (!isSubType(m, m_)) {
							consume = false;
							break;
						}
					}
					if (m_.isSetter() || m_.isField()) {
						if (!isSubType(m_, m)) {
							consume = false;
							break;
						}
					}
				}
			}
			if (consume) {
				if (!consumedMembers.contains(m)) { // in case an interface is (indirectly) redundantly implemented
					consumedMembers.add(m);
				}
			}
		}
		mm.markConsumed(consumedMembers);
		return true;
	}

	/**
	 * Constraints 69: Implementation of Interface Members
	 *
	 * This method doesn't add issues for missing override annotations but adds the missing-annotation-members to the
	 * given collection.
	 */
	private void constraints_69_Implementation(MemberMatrix mm, Collection<TMember> membersMissingOverrideAnnotation) {

		String missingAccessor = null;
		List<TMember> missingAccessors = new MemberList<>();
		List<TMember> conflictingMembers = new MemberList<>();

		TClassifier currentClassifier = getCurrentClassifier();
		Set<TMember> ownedErroneousMembers = null; // avoid multiple errors on a single element (but not on
		// getter/setter pairs

		for (TMember m : mm.implemented()) {
			if (mm.isConsumed(m)) { // m is mixed in, so it exits and we do not need other tests
				continue;
			}

			boolean bExistCompatibleMember = false;
			boolean bExistCompatibleGetter = false;
			boolean bExistCompatibleSetter = false;

			for (SourceAwareIterator iter = mm.ownedConsumedInheritedImplemented(); iter.hasNext();) {
				TMember m_ = iter.next();
				if (ownedErroneousMembers != null && !iter.isOwnedMember()) {
					break; // we found problems with owned members, we do not need more error messages.
				}
				// short cut and avoid multiple errors for single member
				if (m_ == m || ownedErroneousMembers != null && ownedErroneousMembers.contains(m_)) {
					if (iter.isInheritedMember()) { // consumed by super class and then inherited
						bExistCompatibleMember = true;
					}
					continue; // we do not break since we want to find possible consumption problems
				}

				// 1. m must be accessible and
				// 2.a & 2.b: m_ must be implementation-compatible to m
				OverrideCompatibilityResult compatibility = checkAccessibilityAndOverrideCompatibility(
						RedefinitionType.implemented, m_, m,
						!iter.isActualMember(), mm);
				if (compatibility == OverrideCompatibilityResult.ACCESSOR_PAIR) {
					continue;
				} else if (compatibility == OverrideCompatibilityResult.ERROR) {
					if (iter.isOwnedMember()) { // do not skip other errors for owned members, usually accessor pairs
						if (ownedErroneousMembers == null) {
							ownedErroneousMembers = new HashSet<>();
						}
						ownedErroneousMembers.add(m_);
					} else if (iter.isActualMember()) { // inherited member caused trouble, we see everything in the
						// error message already
						return;
					} else {
						break;
					}
				} else if (iter.isActualMember()) { // no error, mark as found and check override

					// mark found implementor
					if (m.isField()) {
						if (m_.isGetter()) {
							bExistCompatibleGetter = true;
						} else if (m_.isSetter()) {
							bExistCompatibleSetter = true;
						} else {
							bExistCompatibleMember = true;
						}
					} else {
						bExistCompatibleMember = true;
					}

					// 1 & 2 declared overridden
					if (!m_.isDeclaredOverride()
							&& m_.getContainingType() == currentClassifier) {
						membersMissingOverrideAnnotation.add(m_);
					}
				}
			}

			if (bExistCompatibleGetter != bExistCompatibleSetter) {
				missingAccessor = bExistCompatibleGetter ? "setter" : "getter";
				missingAccessors.add(m);
			} else if (!bExistCompatibleMember && !(bExistCompatibleGetter && bExistCompatibleSetter)) {
				conflictingMembers.add(m);

			}
		}
		if (ownedErroneousMembers != null) {
			return; // avoid consequential errors
		}

		if (!conflictingMembers.isEmpty()) {
			messageConflictingMixins(conflictingMembers);
		} else if (!missingAccessors.isEmpty()) {
			messageMissingAccessor(missingAccessor, missingAccessors);
		}

	}

	/**
	 * @param m
	 *            the overriding member
	 * @param s
	 *            the overridden or implemented member
	 * @param consumptionConflict
	 *            check override or implementation override, the latter usually stems from a consumption conflict (so
	 *            that s did not get consumed in the first place)
	 * @param mm
	 *            member matrix, only to improve error message
	 */
	private OverrideCompatibilityResult checkAccessibilityAndOverrideCompatibility(RedefinitionType redefinitionType,
			TMember m, TMember s, boolean consumptionConflict, MemberMatrix mm) {

		// getter/setter combination not checked here
		if (TypeUtils.isAccessorPair(m, s)) {
			return OverrideCompatibilityResult.ACCESSOR_PAIR;
		}

		// constructors not checked here, except constructors defined in polyfills and those defined in subclasses
		// of @CovariantConstructor classes or implementing @CovariantConstructor interfaces
		if (m.isConstructor() && s.isConstructor() && !(isPolyfill(m) || isCovarianceForConstructorsRequired(m, s))) {
			return OverrideCompatibilityResult.COMPATIBLE;
		}

		// Nr.1 of Constraints 67 (Overriding Members) and Constraints 69 (Implementation of Interface Members)
		// --> s must be accessible
		final TModule contextModule = m.getContainingModule();
		final ContainerType<?> contextType = m.getContainingType();
		if (contextModule != null && contextType != null
				&& !memberVisibilityChecker.isVisibleWhenOverriding(contextModule, contextType, contextType, s)) {
			if (!consumptionConflict) { // avoid consequential errors
				messageOverrideNonAccessible(redefinitionType, m, s);
			}
			return OverrideCompatibilityResult.ERROR;
		}

		// continue checking Constraints 65
		return constraints_65_overrideCompatible(redefinitionType, m, s, consumptionConflict, mm);
	}

	/**
	 * Constraints 65 (Override Compatible) and relation overrideCompatible.
	 *
	 * @param m
	 *            the overriding member
	 * @param s
	 *            the overridden or implemented member
	 * @param consumptionConflict
	 *            check override or implementation override, the latter usually stems from a consumption conflict (so
	 *            that s did not get consumed in the first place)
	 * @param mm
	 *            member matrix, only to improve error message
	 * @return true if m is override compatible to s. Note that false does not necessarily means that an error occurred,
	 *         since e.g., a getter does not effect a setter
	 */
	private OverrideCompatibilityResult constraints_65_overrideCompatible(RedefinitionType redefinitionType, TMember m,
			TMember s, boolean consumptionConflict, MemberMatrix mm) {
		// 1. name and static modifier are always equal here, so we do not have to check that again

		// 2. meta type
		boolean metaTypeCompatible = MemberRedefinitionUtils.isMetaTypeCompatible(m, s);
		if (!metaTypeCompatible) {
			if (!consumptionConflict) { // avoid consequential errors
				messageOverrideMetaTypeIncompatible(redefinitionType, m, s, mm);
			}
			return OverrideCompatibilityResult.ERROR;
		}

		// 3. s not final
		if (s.isFinal()) {
			if (!consumptionConflict) { // avoid consequential errors
				messageOverrideFinal(redefinitionType, m, s);
			}
			return OverrideCompatibilityResult.ERROR;
		}

		final boolean sIsField = s instanceof TField;
		final boolean sIsSetter = s instanceof TSetter;
		final boolean mIsField = m instanceof TField;

		// 4. s not const
		if (sIsField) { // const only defined on TField & TStructuralField
			TField sF = (TField) s;
			if (sF.isConst()) { // 2. const
				// By GHOLD-186 const redefinition is allowed for const fields
				if (!((mIsField)
						&& ((TField) m).isConst())) {
					if (!consumptionConflict) { // avoid consequential errors
						messageOverrideConst(redefinitionType, m, sF);
					}
					return OverrideCompatibilityResult.ERROR;
				}
			}
		}

		// 5. must not override non-final/non-const field or setter with a @Final/const field
		if (sIsField || sIsSetter) {
			if (!s.isFinal() && !s.isConst()) {
				if (mIsField && (m.isFinal() || m.isConst())) {
					if (!consumptionConflict) { // avoid consequential errors
						messageOverrideWithFinalOrConstField(redefinitionType, m, s);
					}
					return OverrideCompatibilityResult.ERROR;
				}
			}
		}

		// 6. abstract
		if (m.isAbstract() && !s.isAbstract()) {
			if (!consumptionConflict) { // avoid consequential errors
				messageOverrideAbstract(redefinitionType, m, s);
			}
			return OverrideCompatibilityResult.ERROR;
		}

		// 7. type compatible
		if (!m.isSetter() && !s.isSetter()) { // in Method (including constructor), Getter, Field
			Result result = isSubTypeResult(m, s);
			if (result.isFailure()) {
				if (!consumptionConflict) { // avoid consequential errors
					messageOverrideMemberTypeConflict(redefinitionType, m, s, result, mm);
				}
				return OverrideCompatibilityResult.ERROR;
			}
		}

		boolean sIsConst = false;
		if (sIsField) {
			sIsConst = ((TField) s).isConst();
		}

		if ((m.isSetter() || m.isField()) && !s.isGetter() && !sIsConst) {
			Result result = isSubTypeResult(s, m);
			if (result.isFailure()) {
				if (!consumptionConflict) { // avoid consequential errors
					messageOverrideMemberTypeConflict(redefinitionType, m, s, result, mm);
				}
				return OverrideCompatibilityResult.ERROR;
			}
		}

		// 8.1 accessibility must not be reduced
		if (AccessModifiers.checkedLess(m, s)) { // fix modifiers in order to avoid strange behavior
			if (!consumptionConflict) { // avoid consequential errors
				messageOverrideAccessibilityReduced(redefinitionType, m, s);
			}
			return OverrideCompatibilityResult.ERROR;
		}

		// 8.2 special accessibility handling of public@Internal and protected as they reduce each other
		MemberAccessModifier fixedLeft = AccessModifiers.fixed(m);
		MemberAccessModifier fixedRight = AccessModifiers.fixed(s);
		if ((fixedLeft == MemberAccessModifier.PROTECTED && fixedRight == MemberAccessModifier.PUBLIC_INTERNAL) ||
				(fixedLeft == MemberAccessModifier.PUBLIC_INTERNAL && fixedRight == MemberAccessModifier.PROTECTED)) {
			messageOverrideAccessibilityReduced(redefinitionType, m, s);
			return OverrideCompatibilityResult.ERROR;
		}

		return OverrideCompatibilityResult.COMPATIBLE;
	}

	/** Tells if 'm' belongs to a polyfill (i.e. the filling, not the filled type; cf. {@link Type#isPolyfill()}. */
	private boolean isPolyfill(TMember m) {
		final Type mContainer = m.getContainingType();
		return mContainer != null && mContainer.isPolyfill();
	}

	/** Assuming m and s are constructors (not checked), tells if override compatibility is required. */
	private boolean isCovarianceForConstructorsRequired(TMember m, TMember s) {
		return isCovarianceForConstructorsRequired(m.getContainingType(), s);
	}

	/**
	 * Assuming 'ctor' is a constructor (not checked), tells if override compatibility with 'ctor' is required when seen
	 * from type 'baseType'. For convenience, 'baseType' may be <code>null</code>.
	 */
	private boolean isCovarianceForConstructorsRequired(Type baseType, TMember ctor) {
		if (ctor.getContainingType() instanceof TInterface) {
			// s is coming from an interface -> covariance always required (because we enforce @CovariantConstructor
			// whenever an interface contains a constructor)
			return true;
		}
		if (baseType instanceof TClass) {
			final TClass tSuperClass = ((TClass) baseType).getSuperClass();
			if (tSuperClass != null && N4JSLanguageUtils.hasCovariantConstructor(tSuperClass)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Constraints 42, 3 (Abstract Member)<br>
	 * Constraints 45, 4 (Extending Interfaces)<br>
	 * Constraints 46, 5 (Implementing Interfaces)
	 *
	 * This method doesn't add issues for missing override annotations but adds the missing-annotation-members to the
	 * given collection.
	 */
	private void constraints_42_45_46_AbstractMember(MemberMatrix mm,
			Map<ParameterizedTypeRef, MemberList<TMember>> nonAccessibleAbstractMembersBySuperTypeRef) {

		N4ClassifierDefinition classifierDefinition = getCurrentClassifierDefinition();
		TClassifier classifier = getCurrentClassifier();
		TModule contextModule = EcoreUtil2.getContainerOfType(classifier, TModule.class);

		for (SourceAwareIterator iter = mm.allMembers(); iter.hasNext();) {
			TMember m = iter.next();
			if (!iter.isOwnedMember() && m.isAbstract()) {
				if (!memberVisibilityChecker.isVisibleWhenOverriding(contextModule, classifier, classifier, m)) {
					Iterable<ParameterizedTypeRef> superTypeRefs = FindClassifierInHierarchyUtils
							.findSuperTypesWithMember(classifierDefinition, m);
					for (ParameterizedTypeRef superTypeRef : superTypeRefs) {
						MemberList<TMember> nonAccessible = nonAccessibleAbstractMembersBySuperTypeRef
								.get(superTypeRef);
						if (nonAccessible == null) {
							nonAccessible = new MemberList<>();
							nonAccessibleAbstractMembersBySuperTypeRef.put(superTypeRef, nonAccessible);
						}
						nonAccessible.add(m);
					}
				}
			}
		}
	}

	/**
	 * GHOLD-234 add warning for unused type variables in function and method declarations (unless the method overrides
	 * any other method).
	 */
	private void unusedGenericTypeVariable(MemberMatrix mm) {
		for (TMember member : mm.owned()) {
			if (member instanceof TMethod) {
				TMethod method = (TMethod) member;
				if (!mm.hasInherited() && !mm.hasImplemented()) {
					// We need the method declaration from the AST in order to pass it to the internal
					// validation function. This is necessary because we want to attach the warning to the actual unused
					// type variable in the method declaration, and the type variable in the type model is not identical
					// to the one in the AST which we actually need.
					// Since method is owned by the type being validated, we can safely navigate back from the type
					// model to the AST without triggering another parse.

					MethodDeclaration methodDeclaration = (MethodDeclaration) method.getAstElement();
					internalCheckNoUnusedTypeParameters(methodDeclaration);
				}
			}
		}
	}

	/**
	 * Constraints 41 (Abstract Class)
	 */
	private boolean constraints_41_AbstractClass(TClassifier classifier, MemberCube memberCube) {
		List<TMember> abstractMembers = null;
		if (!classifier.isAbstract() && classifier instanceof TClass) {
			for (Entry<NameStaticPair, MemberMatrix> entry : memberCube.entrySet()) {
				MemberMatrix mm = entry.getValue();
				MemberList<TMember> l = new MemberList<>();
				Iterators.addAll(l, mm.actuallyInheritedAndMixedMembers());
				for (SourceAwareIterator iter = mm.actuallyInheritedAndMixedMembers(); iter.hasNext();) {
					TMember m = iter.next();
					if (m.isAbstract()) {
						if (abstractMembers == null) {
							abstractMembers = new ArrayList<>();
						}
						abstractMembers.add(m);
					}
				}
			}
		}
		if (abstractMembers != null) {
			messageMissingImplementations(abstractMembers);
			return false;
		}
		return true;
	}

	private void messageMissingImplementations(List<TMember> abstractMembers) {
		TClassifier classifier = getCurrentClassifier();
		if (!jsVariantHelper.allowMissingImplementation(classifier)) {
			String message = getMessageForCLF_MISSING_IMPLEMENTATION(classifier.getName(),
					validatorMessageHelper.descriptions(abstractMembers));
			addIssue(message, CLF_MISSING_IMPLEMENTATION);
		} else { // to be removed, only temporary (IDE-1236)
			String message = getMessageForCLF_MISSING_IMPLEMENTATION_EXT(classifier.getName(),
					validatorMessageHelper.descriptions(abstractMembers));
			addIssue(message, CLF_MISSING_IMPLEMENTATION_EXT);
		}
	}

	private void addIssue(String message, String issueCode) {
		N4ClassifierDefinition classifier = getCurrentClassifierDefinition();
		EStructuralFeature nameFeature = classifier.eClass().getEStructuralFeature("name");
		addIssue(message, classifier, nameFeature, issueCode);
	}

	private void messageImpossibleExtendsImplements(N4ClassifierDefinition n4ClassifierDefinition,
			Map<ParameterizedTypeRef, MemberList<TMember>> nonAccessibleAbstractMembersBySuperTypeRef) {
		for (Entry<ParameterizedTypeRef, MemberList<TMember>> entry : nonAccessibleAbstractMembersBySuperTypeRef
				.entrySet()) {
			final ParameterizedTypeRef superTypeRef = entry.getKey();
			final Type type = superTypeRef.getDeclaredType();
			final String mode = type instanceof TInterface
					&& !(n4ClassifierDefinition instanceof N4InterfaceDeclaration) ? "implement" : "extend";
			final String message = getMessageForCLF_NON_ACCESSIBLE_ABSTRACT_MEMBERS(mode,
					validatorMessageHelper.description(type),
					validatorMessageHelper.descriptions(entry.getValue()));
			addIssue(message, superTypeRef.eContainer(), superTypeRef.eContainingFeature(),
					CLF_NON_ACCESSIBLE_ABSTRACT_MEMBERS);
		}
	}

	/**
	 * Adds a missing override issue with the fitting verb for all members in the given collection.
	 *
	 * @param mm
	 *            The current member matrix
	 * @param missingOverrideAnnotationMembers
	 *            A collection of overridden members
	 *
	 */
	private void messageMissingOverrideAnnotation(MemberMatrix mm,
			Collection<TMember> missingOverrideAnnotationMembers) {
		if (mm.hasOwned() && missingOverrideAnnotationMembers.size() > 0) {
			Iterable<TMember> overriddenMembers = Iterables.concat(mm.inherited(), mm.implemented());

			for (TMember overriding : mm.owned()) {
				// skip members with proper annotation
				if (!missingOverrideAnnotationMembers.contains(overriding)) {
					continue;
				}
				// skip constructors
				if (overriding.isConstructor()) {
					continue;
				}
				// skip non-n4js variant members
				if (!jsVariantHelper.checkOverrideAnnotation(overriding)) {
					continue;
				}
				/*
				 * Filter overridden members to only contain metatype compatible members to prevent the generation of
				 * confusing error message
				 */
				Iterable<TMember> filteredOverriddenMembers = MemberRedefinitionUtils
						.getMetatypeCompatibleOverriddenMembers(overriding, overriddenMembers);

				// choose redefinition verb based on the origin of the overridden members
				String redefinitionVerb = MemberRedefinitionUtils.getRedefinitionVerb(filteredOverriddenMembers,
						getCurrentClassifier());

				String message = getMessageForCLF_OVERRIDE_ANNOTATION(
						validatorMessageHelper.descriptionDifferentFrom(overriding, overriddenMembers),
						redefinitionVerb,
						validatorMessageHelper.descriptions(filteredOverriddenMembers));

				addIssue(message, overriding.getAstElement(), N4JSPackage.Literals.PROPERTY_NAME_OWNER__DECLARED_NAME,
						CLF_OVERRIDE_ANNOTATION);
			}
		}
	}

	private void messageFieldOverrideNeedsAccessorPair(TMember overriding, TMember overridden) {
		if (overriding.getContainingType() == getCurrentClassifier()) {
			String missingAccessor = overriding.isGetter() ? "setter" : "getter";
			String message = getMessageForCLF_OVERRIDE_FIELD_REQUIRES_ACCESSOR_PAIR(
					validatorMessageHelper.descriptionDifferentFrom(overriding, overridden),
					validatorMessageHelper.descriptionDifferentFrom(overridden, overriding), missingAccessor);
			addIssue(message, overriding.getAstElement(), N4JSPackage.Literals.PROPERTY_NAME_OWNER__DECLARED_NAME,
					CLF_OVERRIDE_FIELD_REQUIRES_ACCESSOR_PAIR);
		} else {
			throw new IllegalStateException("must not happen as member is not consumed");
		}
	}

	private void messageOverrideMemberTypeConflict(RedefinitionType redefinitionType, TMember overriding,
			TMember overridden, Result result, MemberMatrix mm) {

		String message;
		String code;
		String redefinitionTypeName = redefinitionType.name();
		if (redefinitionType == RedefinitionType.implemented &&
				Iterables.contains(mm.implemented(), overriding)) {
			redefinitionTypeName = "consumed";
		}

		String overridingSource = "";
		if (redefinitionType == RedefinitionType.implemented &&
				Iterables.contains(mm.inherited(), overriding)) {
			overridingSource = "inherited ";
		}

		String extraMessage = cfOtherImplementedMembers(mm, overriding, overridden);

		if (overriding.isField() && overridden.isField() && !((TField) overridden).isConst()) {
			code = CLF_REDEFINED_TYPE_NOT_SAME_TYPE;
			message = getMessageForCLF_REDEFINED_TYPE_NOT_SAME_TYPE(
					overridingSource + validatorMessageHelper.descriptionDifferentFrom(overriding, overridden),
					redefinitionTypeName,
					validatorMessageHelper.descriptionDifferentFrom(overridden, overriding),
					extraMessage);
		} else if (overriding.isMethod() && overridden.isMethod()) {
			code = CLF_REDEFINED_METHOD_TYPE_CONFLICT;

			message = getMessageForCLF_REDEFINED_METHOD_TYPE_CONFLICT(
					overridingSource + validatorMessageHelper.descriptionDifferentFrom(overriding, overridden),
					redefinitionTypeName,
					validatorMessageHelper.descriptionDifferentFrom(overridden, overriding),
					validatorMessageHelper.trimTypesystemMessage(result),
					extraMessage);
		} else {
			code = CLF_REDEFINED_MEMBER_TYPE_INVALID;
			message = getMessageForCLF_REDEFINED_MEMBER_TYPE_INVALID(
					overridingSource + validatorMessageHelper.descriptionDifferentFrom(overriding, overridden),
					validatorMessageHelper.descriptionDifferentFrom(overridden, overriding),
					redefinitionTypeName,
					validatorMessageHelper.trimTypesystemMessage(result),
					extraMessage);
		}

		addIssueToMemberOrInterfaceReference(redefinitionType, overriding, overridden, message, code);
	}

	private void messageOverrideAbstract(RedefinitionType redefinitionType, TMember overriding, TMember overridden) {
		String message = getMessageForCLF_OVERRIDEN_CONCRETE_WITH_ABSTRACT(
				validatorMessageHelper.descriptionDifferentFrom(overriding, overridden),
				validatorMessageHelper.descriptionDifferentFrom(overridden, overriding));
		addIssueToMemberOrInterfaceReference(redefinitionType, overriding, overridden, message,
				CLF_OVERRIDEN_CONCRETE_WITH_ABSTRACT);
	}

	private void messageOverrideAccessibilityReduced(RedefinitionType redefinitionType, TMember overriding,
			TMember overridden) {
		String message = getMessageForCLF_OVERRIDE_VISIBILITY(
				validatorMessageHelper.descriptionDifferentFrom(overriding, overridden),
				validatorMessageHelper.descriptionDifferentFrom(overridden, overriding));
		addIssueToMemberOrInterfaceReference(redefinitionType, overriding, overridden, message,
				CLF_OVERRIDE_VISIBILITY,
				IssueUserDataKeys.CLF_OVERRIDE_VISIBILITY.OVERRIDDEN_MEMBER_ACCESS_MODIFIER,
				overridden.getMemberAccessModifier().getName(),
				IssueUserDataKeys.CLF_OVERRIDE_VISIBILITY.OVERRIDDEN_MEMBER_NAME, overridden.getName(),
				IssueUserDataKeys.CLF_OVERRIDE_VISIBILITY.SUPER_CLASS_NAME, overridden.getContainingType().getName());
	}

	private void messageOverrideNonAccessible(@SuppressWarnings("unused") RedefinitionType redefinitionType,
			TMember overriding, TMember overridden) {
		String message = getMessageForCLF_REDEFINED_NON_ACCESSIBLE(
				validatorMessageHelper.descriptionDifferentFrom(overriding, overridden),
				validatorMessageHelper.descriptionDifferentFrom(overridden, overriding));
		addIssue(message, overriding.getAstElement(), N4JSPackage.Literals.PROPERTY_NAME_OWNER__DECLARED_NAME,
				CLF_REDEFINED_NON_ACCESSIBLE);
	}

	private void messageOverrideFinal(RedefinitionType redefinitionType, TMember overriding, TMember overridden) {
		String message = getMessageForCLF_OVERRIDE_FINAL(
				validatorMessageHelper.descriptionDifferentFrom(overriding, overridden),
				validatorMessageHelper.descriptionDifferentFrom(overridden, overriding));
		addIssueToMemberOrInterfaceReference(redefinitionType, overriding, overridden, message,
				CLF_OVERRIDE_FINAL,
				IssueUserDataKeys.CLF_OVERRIDE_FINAL.OVERRIDDEN_MEMBER_URI,
				EcoreUtil.getURI(overridden).toString());
	}

	private void messageOverrideWithFinalOrConstField(RedefinitionType redefinitionType, TMember overriding,
			TMember overridden) {
		String badModifier = overriding.isConst() ? "const" : "final";
		String prefix = overridden instanceof TField ? "non-" + badModifier + " " : "";
		String message = getMessageForCLF_OVERRIDE_WITH_FINAL_OR_CONST_FIELD(
				prefix + validatorMessageHelper.descriptionDifferentFrom(overridden, overriding),
				badModifier);
		addIssueToMemberOrInterfaceReference(redefinitionType, overriding, overridden, message,
				CLF_OVERRIDE_WITH_FINAL_OR_CONST_FIELD);
	}

	private void messageOverrideConst(RedefinitionType redefinitionType, TMember overriding, TField overridden) {
		String message = getMessageForCLF_OVERRIDE_CONST(
				validatorMessageHelper.descriptionDifferentFrom(overriding, overridden),
				validatorMessageHelper.descriptionDifferentFrom(overridden, overriding));
		addIssueToMemberOrInterfaceReference(redefinitionType, overriding, overridden, message,
				CLF_OVERRIDE_CONST);
	}

	/**
	 * @param mm
	 *            only for improving the error message in case of implementation problems.
	 */
	private void messageOverrideMetaTypeIncompatible(RedefinitionType redefinitionType, TMember overriding,
			TMember overridden, MemberMatrix mm) {
		if (redefinitionType == RedefinitionType.overridden) {
			String message = getMessageForCLF_OVERRIDE_MEMBERTYPE_INCOMPATIBLE(
					validatorMessageHelper.descriptionDifferentFrom(overriding, overridden),
					validatorMessageHelper.descriptionDifferentFrom(overridden, overriding));
			addIssueToMemberOrInterfaceReference(redefinitionType, overriding, overridden, message,
					CLF_OVERRIDE_MEMBERTYPE_INCOMPATIBLE);
		} else { // consumed method implicitly overrides:
			String message = getMessageForCLF_IMPLEMENT_MEMBERTYPE_INCOMPATIBLE(
					validatorMessageHelper.descriptionDifferentFrom(overriding, overridden),
					validatorMessageHelper.descriptionDifferentFrom(overridden, overriding),
					cfOtherImplementedMembers(mm, overridden));
			addIssueToMemberOrInterfaceReference(redefinitionType, overriding, overridden, message,
					CLF_IMPLEMENT_MEMBERTYPE_INCOMPATIBLE);
		}
	}

	private String cfOtherImplementedMembers(MemberMatrix mm, TMember... filteredMembers) {
		String others = validatorMessageHelper.descriptions(
				StreamSupport.stream(mm.implemented().spliterator(), false)
						.filter(m -> !Arrays.contains(filteredMembers, m))
						.collect(Collectors.toList()));
		if (others.length() == 0) {
			return "";
		}
		return " Also cf. " + others + ".";
	}

	private void messageIncompatibleMembersToImplement(Iterable<TMember> implementedMembers) {
		String message = getMessageForCLF_CONSUMED_MEMBER_UNSOLVABLE_CONFLICT(
				validatorMessageHelper.descriptions(implementedMembers));
		addIssue(message, CLF_CONSUMED_MEMBER_UNSOLVABLE_CONFLICT);
	}

	private void messageIncompatibleInheritedMembersToImplement(TMember inheritedMember,
			Iterable<TMember> implementedMembers) {
		String message = getMessageForCLF_CONSUMED_INHERITED_MEMBER_UNSOLVABLE_CONFLICT(
				validatorMessageHelper.description(inheritedMember),
				validatorMessageHelper.descriptions(implementedMembers));
		addIssue(message, CLF_CONSUMED_INHERITED_MEMBER_UNSOLVABLE_CONFLICT);
	}

	private void messageConflictingMixins(List<? extends TMember> conflictingMembers) {
		String message = getMessageForCLF_CONSUMED_MEMBER_SOLVABLE_CONFLICT(
				validatorMessageHelper.descriptions(conflictingMembers));
		addIssue(message, CLF_CONSUMED_MEMBER_SOLVABLE_CONFLICT);
	}

	private void messageMissingAccessor(String missingAccessor, List<? extends TMember> conflictingMembers) {
		// CLF_IMPLEMENT_MIXIN_MISSING_ACCESSOR
		String message = getMessageForCLF_CONSUMED_FIELD_ACCESSOR_PAIR_INCOMPLETE(
				missingAccessor,
				validatorMessageHelper.descriptions(conflictingMembers));
		addIssue(message, CLF_CONSUMED_FIELD_ACCESSOR_PAIR_INCOMPLETE);
	}

	private void messageMissingOwnedAccessor(FieldAccessor accessor) {
		String accessorDescription = org.eclipse.xtext.util.Strings
				.toFirstUpper(validatorMessageHelper.description(accessor));

		String verb = accessor.getContainingType().isStaticPolyfill() ? "filled" : "declared";
		verb = accessor.isDeclaredOverride() ? "overridden" : verb;

		String counterpartDescription = accessor instanceof TSetter ? "getter" : "setter";

		if (jsVariantHelper.isN4JSMode(accessor) || jsVariantHelper.isExternalMode(accessor)) {
			messageMissingOwnedAccessorN4JS(accessorDescription, verb, counterpartDescription, accessor);
		} else {
			messageMissingOwnedAccessorJS(accessorDescription, verb, counterpartDescription, accessor);
		}
	}

	private void messageMissingOwnedAccessorN4JS(String accessorDescription, String verb, String counterpartDescription,
			FieldAccessor accessor) {
		String message = getMessageForCLF_UNMATCHED_ACCESSOR_OVERRIDE(accessorDescription, verb,
				counterpartDescription);
		addIssue(message, accessor.getAstElement(),
				N4JSPackage.Literals.PROPERTY_NAME_OWNER__DECLARED_NAME, CLF_UNMATCHED_ACCESSOR_OVERRIDE);
	}

	private void messageMissingOwnedAccessorJS(String accessorDescription, String verb, String counterpartDescription,
			FieldAccessor accessor) {
		String message = getMessageForCLF_UNMATCHED_ACCESSOR_OVERRIDE_JS(accessorDescription, verb,
				counterpartDescription);
		addIssue(message, accessor.getAstElement(),
				N4JSPackage.Literals.PROPERTY_NAME_OWNER__DECLARED_NAME, CLF_UNMATCHED_ACCESSOR_OVERRIDE_JS);
	}

	private void messageMissingOwnedAccessorCorrespondingConsumedAccessor(FieldAccessor accessor,
			N4ClassifierDefinition definition) {
		String message = getMessageForCLF_UNMATCHED_ACCESSOR_OVERRIDE(
				org.eclipse.xtext.util.Strings.toFirstUpper(validatorMessageHelper.description(accessor)),
				"consumed",
				accessor instanceof TSetter ? "getter" : "setter");
		addIssue(message, definition,
				N4JSPackage.Literals.N4_TYPE_DECLARATION__NAME, CLF_UNMATCHED_ACCESSOR_OVERRIDE);
	}

	private void addIssueToMemberOrInterfaceReference(RedefinitionType redefinitionType, TMember overriding,
			TMember implemented,
			String message, String issueCode, String... issueData) {

		if (redefinitionType == RedefinitionType.overridden
				&& overriding.getContainingType() != getCurrentClassifier()) {
			throw new IllegalStateException("must not happen as member is not consumed");
		}

		TClassifier currentClassifier = getCurrentClassifier();
		if (overriding.getContainingType() == currentClassifier) {
			addIssue(message, overriding.getAstElement(), N4JSPackage.Literals.PROPERTY_NAME_OWNER__DECLARED_NAME,
					issueCode,
					issueData);
		} else {
			MemberCollector memberCollector = containerTypesHelper.fromContext(getCurrentClassifierDefinition());
			ContainerType<?> bequestingType = memberCollector.directSuperTypeBequestingMember(currentClassifier,
					implemented);
			Optional<ParameterizedTypeRef> optRef = StreamSupport
					.stream(getCurrentClassifierDefinition().getImplementedOrExtendedInterfaceRefs().spliterator(),
							false)
					.filter(ref -> ref.getDeclaredType() == bequestingType).findAny();
			ParameterizedTypeRef ref = optRef.get();
			EStructuralFeature feature = ref.eContainingFeature();
			List<?> list = (List<?>) getCurrentClassifierDefinition().eGet(feature);
			int index = list.indexOf(ref);
			addIssue(message, getCurrentClassifierDefinition(), feature, index, issueCode, issueData);
		}
	}

	private boolean isSubType(TMember left, TMember right) {
		return isSubTypeResult(left, right).isSuccess();
	}

	private Result isSubTypeResult(TMember left, TMember right) {
		final Type rightThisContext = left.isConstructor() && right.isConstructor() && !isPolyfill(left)
				? MemberRedefinitionUtils.findThisContextForConstructor(left.getContainingType(), (TMethod) right)
				: null; // null means: use default context
		return isSubTypeResult(left, rightThisContext, right);
	}

	/**
	 * @param rightThisContextType
	 *            the type to use as context for the "this binding" of the right-hand-side member or <code>null</code>
	 *            to use the default, i.e. the {@link #getCurrentTypeContext() current type context}.
	 */
	private Result isSubTypeResult(TMember left, Type rightThisContextType, TMember right) {
		// will return type of value for fields, function type for methods, type of return value for getters, type of
		// parameter for setters
		final Resource res = left.eResource();
		final TypeRef mainContext = getCurrentTypeContext();
		final TypeRef rightThisContext = rightThisContextType != null
				? TypeUtils.createTypeRef(rightThisContextType)
				: mainContext;
		final RuleEnvironment G_left = ts.createRuleEnvironmentForContext(mainContext, mainContext, res);
		final RuleEnvironment G_right = ts.createRuleEnvironmentForContext(mainContext, rightThisContext, res);
		TypeRef typeLeft = ts.tau(left, G_left);
		TypeRef typeRight = ts.tau(right, G_right);

		// in case of checking compatibility of constructors, we can ignore the return types
		// i.e. turn {function(string):this[C]?} into {function(string)}
		// (simplifies subtype check and, more importantly, leads to better error messages)
		RuleEnvironment G = getCurrentRuleEnvironment();
		if (left.isConstructor() && typeLeft instanceof FunctionTypeExprOrRef) {
			typeLeft = TypeUtils.createFunctionTypeExpression(null, Collections.emptyList(),
					((FunctionTypeExprOrRef) typeLeft).getFpars(), null);
		}
		if (right.isConstructor() && typeRight instanceof FunctionTypeExprOrRef) {
			typeRight = TypeUtils.createFunctionTypeExpression(null, Collections.emptyList(),
					((FunctionTypeExprOrRef) typeRight).getFpars(), null);
		}

		return ts.subtype(G, typeLeft, typeRight);
	}

	private TClassifier getCurrentClassifier() {
		return (TClassifier) getContext().get(TClassifier.class);
	}

	private N4ClassifierDefinition getCurrentClassifierDefinition() {
		return (N4ClassifierDefinition) getCurrentObject();
	}

	private RuleEnvironment getCurrentRuleEnvironment() {
		return (RuleEnvironment) getContext().get(RuleEnvironment.class);
	}

	private ParameterizedTypeRef getCurrentTypeContext() {
		return (ParameterizedTypeRef) getContext().get(TYPE_VAR_CONTEXT);
	}

	private MemberCube createMemberValidationList() {
		MemberCollector memberCollector = containerTypesHelper.fromContext(getCurrentClassifierDefinition());
		TClassifier tClassifier = getCurrentClassifier();
		return new MemberCube(tClassifier, memberCollector);
	}
}
