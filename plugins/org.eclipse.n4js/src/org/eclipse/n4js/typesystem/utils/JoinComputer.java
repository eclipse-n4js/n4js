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

import static java.util.Collections.singletonList;
import static org.eclipse.n4js.types.utils.SuperTypesList.newSuperTypesList;
import static org.eclipse.n4js.types.utils.TypeUtils.declaredSuperTypes;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.anyTypeRef;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.getContextResource;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.objectTypeRef;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.wrap;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.exists;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.filterNull;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.forall;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.head;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.isEmpty;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.size;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.n4js.scoping.members.TypingStrategyFilter;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression;
import org.eclipse.n4js.ts.typeRefs.IntersectionTypeExpression;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRefStructural;
import org.eclipse.n4js.ts.typeRefs.TypeArgument;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRefsFactory;
import org.eclipse.n4js.ts.typeRefs.UnionTypeExpression;
import org.eclipse.n4js.ts.typeRefs.Wildcard;
import org.eclipse.n4js.ts.types.AnyType;
import org.eclipse.n4js.ts.types.ContainerType;
import org.eclipse.n4js.ts.types.PrimitiveType;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.TField;
import org.eclipse.n4js.ts.types.TFormalParameter;
import org.eclipse.n4js.ts.types.TGetter;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.ts.types.TSetter;
import org.eclipse.n4js.ts.types.TStructField;
import org.eclipse.n4js.ts.types.TStructGetter;
import org.eclipse.n4js.ts.types.TStructMember;
import org.eclipse.n4js.ts.types.TStructMethod;
import org.eclipse.n4js.ts.types.TStructSetter;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypeVariable;
import org.eclipse.n4js.ts.types.TypesFactory;
import org.eclipse.n4js.ts.types.TypingStrategy;
import org.eclipse.n4js.types.utils.SuperTypesList;
import org.eclipse.n4js.types.utils.TypeCompareHelper;
import org.eclipse.n4js.types.utils.TypeHelper;
import org.eclipse.n4js.types.utils.TypeUtils;
import org.eclipse.n4js.typesystem.N4JSTypeSystem;
import org.eclipse.n4js.utils.ContainerTypesHelper;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.inject.Inject;

/**
 * Type System Helper Strategy computing the join of a given collection of types.
 * <p>
 * Definition from [Pierce02a, pp. 218]:
 *
 * <pre>
 * Join J = S v T, if
 *     S <: J, T <:J,                         common super type
 *     forall U: S <:U and T <:U => J <: U    least
 * </pre>
 * <p>
 * It is also rigor with regard to auto-conversion. That is, {@code string} and {@String} are not automatically
 * converted into each other. This is because both types behave differently. E.g., if used as a boolean expression,
 * {@code ""} and {@code new String("")} will be evaluated differently. <em>The object type will always return
 * true.</em> This is even true for {@code new Boolean(false)}, which will also be evaluated to true (note however that
 * {@code Boolean(false)} will return a {@code boolean} value, which is then evaluated to false).
 * <p>
 * Special pseudo sub types of string, e.g. pathSelector, are handled as well.
 */
class JoinComputer extends TypeSystemHelperStrategy {

	@Inject
	N4JSTypeSystem ts;

	@Inject
	TypeCompareHelper tch;

	@Inject
	TypeHelper th;

	@Inject
	ContainerTypesHelper containerTypesHelper;

	@Inject
	GenericsComputer genericsComputer;

	/**
	 * Returns the join, sometimes called least common super type (LCST), of the given types. This may be an
	 * intersection type but not a union type. See class description for details.
	 *
	 * @return the join, may be a contained reference. Thus clients may need to create a copy!
	 */
	TypeRef join(RuleEnvironment G, Iterable<? extends TypeRef> typeRefsToJoin) {

		if (typeRefsToJoin == null) { // quick return
			return null;
		}

		Iterable<? extends TypeRef> typeRefs = filterNull(typeRefsToJoin);
		if (isEmpty(typeRefs)) { // quick return
			return null;
		}
		if (size(typeRefs) == 1) { // quick return
			return head(typeRefs);
		}

		Iterable<UnionTypeExpression> unionTypeRefs = filter(typeRefs, UnionTypeExpression.class);
		Iterable<? extends TypeRef> nonUnionTypeRefs = filter(typeRefs, tr -> !(tr instanceof UnionTypeExpression));

		TypeRef nonUnionJoin = joinNonUnionTypes(G, nonUnionTypeRefs);
		if (isEmpty(unionTypeRefs)) {
			return nonUnionJoin;
		} else {
			return joinNonUnionTypeWithUniontypes(G, nonUnionJoin, unionTypeRefs);
		}
	}

	/**
	 * join result of non-union types join with all union types
	 *
	 * see [N4JS Spec], 4.12 Union Type
	 *
	 * @param unionTypeRefs
	 *            cannot be empty, called only from method above where it is checked
	 */
	private TypeRef joinNonUnionTypeWithUniontypes(RuleEnvironment G, TypeRef nonUnionJoin,
			Iterable<UnionTypeExpression> unionTypeRefs) {

		if (nonUnionJoin == null && size(unionTypeRefs) == 1) {
			return tsh.simplify(G, head(unionTypeRefs));
		}

		UnionTypeExpression union = TypeRefsFactory.eINSTANCE.createUnionTypeExpression();
		if (nonUnionJoin != null) {
			union.getTypeRefs().add(TypeUtils.copyIfContained(nonUnionJoin));
		}
		union.getTypeRefs().addAll(toList(map(unionTypeRefs, tr -> TypeUtils.copyIfContained(tr))));
		return tsh.simplify(G, union);
	}

	/**
	 * Called internally to compute the join of non-union types only.
	 *
	 * @param nonUnionTypeRefs
	 *            type references, must not contain UnionTypeExpressions
	 */
	private TypeRef joinNonUnionTypes(RuleEnvironment G, Iterable<? extends TypeRef> nonUnionTypeRefs) {
		if (isEmpty(nonUnionTypeRefs)) { // quick return
			return null;
		}
		if (size(nonUnionTypeRefs) == 1) { // quick return
			return head(nonUnionTypeRefs);
		}

		// 1) find least common raw type. Although typerefs are used, the type arguments are ignored
		List<TypeRef> commonSuperTypesIgnoreTypeArgs;
		Type firstType = head(nonUnionTypeRefs).getDeclaredType();
		if (firstType != null && forall(nonUnionTypeRefs, tr -> tr.getDeclaredType() == firstType)) { // shortcut
			TypeRef head = head(nonUnionTypeRefs);
			if (!head.isParameterized() && forall(nonUnionTypeRefs, tr -> !tr.isUseSiteStructuralTyping())) { // quick
																												// return
				return head;
			}
			commonSuperTypesIgnoreTypeArgs = singletonList(head);
		} else {

			// 1.1) collect all common super (raw) types.
			commonSuperTypesIgnoreTypeArgs = commonSuperTypesTypeargsIgnored(G, nonUnionTypeRefs);
			if (commonSuperTypesIgnoreTypeArgs.isEmpty()) { // quick return
				return anyTypeRef(G);
			}

			// 1.2) find LEAST common super (raw) type
			if (commonSuperTypesIgnoreTypeArgs.size() > 1) {

				// remove all types which are super types of other types contained in commonSuperTypes
				// precondition: the list is sorted: index of a type is always less than index of its supertypes!
				// this is ensured by the order in which the elements are added to the list
				int i = 0;
				while (i < commonSuperTypesIgnoreTypeArgs.size()) {
					removeAllSuperTypesOfType(commonSuperTypesIgnoreTypeArgs, commonSuperTypesIgnoreTypeArgs.get(i), G);
					i = i + 1;
				}
			}
		}

		// 2) handle type arguments
		List<TypeRef> commonSuperTypesParameterized;
		if (forall(commonSuperTypesIgnoreTypeArgs, ta -> !ta.isParameterized())) { // shortcut
			commonSuperTypesParameterized = commonSuperTypesIgnoreTypeArgs;
		} else {
			commonSuperTypesParameterized = Lists.newArrayListWithCapacity(commonSuperTypesIgnoreTypeArgs.size());
			for (TypeRef superTypeIgnoreTypeArgs : commonSuperTypesIgnoreTypeArgs) {
				if (!superTypeIgnoreTypeArgs.isParameterized()) {

					// TODO raw, it it that simple? what about the bound in the generic declaration?!
					commonSuperTypesParameterized.add(TypeUtils.copyIfContained(superTypeIgnoreTypeArgs));
				} else {

					// collect all super types again with these bounds, but this time properly substitute type variables
					Set<TypeRef> parameterizedSuperTypes = collectParameterizedSuperType(nonUnionTypeRefs,
							superTypeIgnoreTypeArgs.getDeclaredType(), G);
					if (parameterizedSuperTypes.size() == 1) {
						commonSuperTypesParameterized.add(TypeUtils.copyIfContained(head(parameterizedSuperTypes)));
					} else {
						TypeRef merged = TypeUtils.copy(head(parameterizedSuperTypes));
						merged.getDeclaredTypeArgs().clear();
						int i = 0;
						while (i < merged.getDeclaredType().getTypeVars().size()) {
							int currentIndex = i;
							TypeRef upperBound = join(G,
									map(parameterizedSuperTypes, tr ->
									// (typeArgs.get(currentIndex) as TypeRef).declaredUpperBound
									ts.upperBound(G, (tr.getDeclaredTypeArgs().get(currentIndex)))));
							TypeRef lowerBound = tsh.meet(G,
									map(parameterizedSuperTypes, tr ->
									// (typeArgs.get(currentIndex) as TypeRef).declaredLowerBound
									ts.lowerBound(G, (tr.getDeclaredTypeArgs().get(currentIndex)))));
							if (tch.compare(upperBound, lowerBound) == 0) {
								merged.getDeclaredTypeArgs().add(TypeUtils.copyIfContained(upperBound));
							} else {
								Wildcard wildcard = TypeRefsFactory.eINSTANCE.createWildcard();
								if (upperBound.isTopType() && !lowerBound.isBottomType()) {

									// this is the Java 6 and 7 behavior with wildcard capture:
									// wildcard.declaredUpperBound = TypeUtils.copyIfContained(G.anyTypeRef)
									// this is the Java 8 (and probably correct) behavior:
									wildcard.setDeclaredLowerBound(TypeUtils.copyIfContained(lowerBound));
								} else {
									wildcard.setDeclaredUpperBound(TypeUtils.copyIfContained(upperBound));
								}
								merged.getDeclaredTypeArgs().add(wildcard);
							}
							i = i + 1;
						}
						commonSuperTypesParameterized.add(merged);
					}
				}
			}
		}

		// 3) handle structurally added members, first version in IDE-691, tested in IDE-142
		if (forall(nonUnionTypeRefs, tr -> tr.getDeclaredType() instanceof ContainerType<?>)) {
			TypingStrategy typingStrategy = TypingStrategy.DEFAULT;

			if (exists(nonUnionTypeRefs, tr -> tr.getTypingStrategy() == TypingStrategy.STRUCTURAL_FIELDS)) {
				typingStrategy = TypingStrategy.STRUCTURAL_FIELDS;
			} else if (exists(nonUnionTypeRefs, tr -> tr.getTypingStrategy() == TypingStrategy.STRUCTURAL)) {
				typingStrategy = TypingStrategy.STRUCTURAL;
			}

			if (typingStrategy != TypingStrategy.DEFAULT) {
				ParameterizedTypeRefStructural ptrs = TypeRefsFactory.eINSTANCE.createParameterizedTypeRefStructural();
				TypeRef trTemplate;
				if (head(commonSuperTypesParameterized) instanceof ParameterizedTypeRef) {
					trTemplate = TypeUtils.copyIfContained(head(commonSuperTypesParameterized));
				} else {
					trTemplate = objectTypeRef(G);
				}
				ptrs.setDefinedTypingStrategy(typingStrategy);
				ptrs.setDeclaredType(trTemplate.getDeclaredType());
				ptrs.getDeclaredTypeArgs().addAll(trTemplate.getDeclaredTypeArgs());

				TypingStrategyFilter filter = new TypingStrategyFilter(typingStrategy);
				Map<String, TMember> structuralMembersByName = new HashMap<>();
				Set<String> structuralMembersWithDifferentTypeOrAlreadyContained = new HashSet<>();

				Iterable<TMember> filteredMembers = filter(containerTypesHelper.fromContext(getContextResource(G))
						.members((ContainerType<?>) ptrs.getDeclaredType()), m -> filter.apply(m));

				structuralMembersWithDifferentTypeOrAlreadyContained.addAll(
						toList(map(filteredMembers, m -> m.getName())));

				for (TypeRef tr : filter(nonUnionTypeRefs, tr -> tr.getDeclaredType() != ptrs.getDeclaredType())) {

					for (TMember structMember : Iterables.concat(tr.getStructuralMembers(),
							filter(containerTypesHelper.fromContext(getContextResource(G))
									.members((ContainerType<?>) tr.getDeclaredType()), m -> filter.apply(m)))) {

						if (!structuralMembersWithDifferentTypeOrAlreadyContained.contains(structMember.getName())) {
							TMember duplicate = structuralMembersByName.get(structMember.getName());
							if (duplicate == null) {
								structuralMembersByName.put(structMember.getName(), structMember);
							} else if (!similarMember(G, duplicate, structMember)) {
								structuralMembersWithDifferentTypeOrAlreadyContained.add(structMember.getName());
								structuralMembersByName.remove(structMember.getName());
							}
						}
					}
				}

				ptrs.getGenStructuralMembers()
						.addAll(toList(filter(map(structuralMembersByName.values(), m -> substituted(G, m)),
								TStructMember.class)));
				return ptrs;
			}
		}

		// 4) if more than one LCST has been found, create intersection
		switch (commonSuperTypesParameterized.size()) {
		case 0:
			throw new IllegalStateException(
					"Error processing least common super type, parameterization removed all types");
		case 1:
			return head(commonSuperTypesParameterized);
		default:
			IntersectionTypeExpression intersectionTypeExpr = TypeRefsFactory.eINSTANCE
					.createIntersectionTypeExpression();
			for (TypeRef tr : commonSuperTypesParameterized) {
				intersectionTypeExpr.getTypeRefs().add(TypeUtils.copyIfContained(tr));
			}

			return intersectionTypeExpr;

		}
	}

	private TMember substituted(RuleEnvironment G, TMember member) {
		if (member instanceof TField) {
			return substituted(G, (TField) member);
		}
		if (member instanceof TGetter) {
			return substituted(G, (TGetter) member);
		}
		if (member instanceof TSetter) {
			return substituted(G, (TSetter) member);
		}
		if (member instanceof TMethod) {
			return substituted(G, (TMethod) member);
		}
		return member;
	}

	private TMember substituted(RuleEnvironment G, TField member) {
		if (member.getTypeRef().isParameterized()) {
			TStructField subst = TypesFactory.eINSTANCE.createTStructField();
			subst.setName(member.getName());
			subst.setTypeRef(ts.substTypeVariables(G, member.getTypeRef()));
			if (subst.getTypeRef() == null) {
				subst.setTypeRef(anyTypeRef(G));
			}
			return subst;
		} else {
			return member;
		}
	}

	private TMember substituted(RuleEnvironment G, TGetter member) {
		if (member.getTypeRef() != null && member.getTypeRef().isParameterized()) {
			TStructGetter subst = TypesFactory.eINSTANCE.createTStructGetter();
			subst.setName(member.getName());
			subst.setTypeRef(ts.substTypeVariables(G, member.getTypeRef()));
			if (subst.getTypeRef() == null) {
				subst.setTypeRef(anyTypeRef(G));
			}
			return subst;
		} else {
			return member;
		}
	}

	private TMember substituted(RuleEnvironment G, TSetter member) {
		if (member.getFpar() != null && member.getFpar().getTypeRef() != null
				&& member.getFpar().getTypeRef().isParameterized()) {
			TStructSetter subst = TypesFactory.eINSTANCE.createTStructSetter();
			subst.setName(member.getName());

			TypeRef tr = ts.substTypeVariables(G, member.getFpar().getTypeRef());
			if (tr == null) {
				tr = anyTypeRef(G);
			}
			subst.setFpar(TypesFactory.eINSTANCE.createTFormalParameter());
			subst.getFpar().setName(member.getFpar().getName());
			subst.getFpar().setTypeRef(TypeUtils.copyIfContained(tr));
			return subst;
		} else {
			return member;
		}
	}

	private TMember substituted(RuleEnvironment G, TMethod member) {
		FunctionTypeExpression ftype = (FunctionTypeExpression) ts.type(G, member);
		TStructMethod subst = TypesFactory.eINSTANCE.createTStructMethod();
		subst.setName(member.getName());
		subst.getFpars().addAll(ftype.getFpars());
		subst.setReturnTypeRef(ftype.getReturnTypeRef());
		subst.getTypeVars().addAll(toList(map(member.getTypeVars(), tv -> TypeUtils.copyIfContained(tv))));
		return subst;
	}

	private boolean similarMember(RuleEnvironment G, TMember m1, TMember m2) {
		TypeRef t1 = ts.type(G, m1);
		TypeRef t2 = ts.type(G, m2);
		return ts.subtypeSucceeded(G, t1, t2) && ts.subtypeSucceeded(G, t2, t1);
	}

	/*
	 * Removes all super types of ref from list of refs. This method is optimized for leastCommonSuperType and assumes
	 * that all types in orderedRefs are ordered as returned by collecAllDeclaredSuperTypes().
	 */
	private void removeAllSuperTypesOfType(List<TypeRef> orderedRefs, TypeRef ref, RuleEnvironment G) {
		Iterable<TypeRef> nonLeastSuperTypes = Iterables.concat(
				th.collectAllDeclaredSuperTypesTypeargsIgnored(ref, false),
				RuleEnvironmentExtensions.collectAllImplicitSuperTypes(G, ref));

		for (TypeRef nonLeastSuperType : nonLeastSuperTypes) {
			if (!th.removeTypeRef(orderedRefs, nonLeastSuperType)) {
				return;
			}
		}
	}

	/*
	 * Search for reference in list of super types of each type ref matching given rawSuperType, using a depth first
	 * search.
	 *
	 * @param refs collection of type references, size > 1
	 *
	 * @param rawSuperTypeRef a raw super type, which is a common super type of all types in refs
	 */
	private Set<TypeRef> collectParameterizedSuperType(Iterable<? extends TypeRef> refs,
			Type rawSuperType, RuleEnvironment G) {

		Set<TypeRef> result = new TreeSet<>(tch.getTypeRefComparator());
		for (TypeRef typeRef : refs) {
			List<TypeRef> pathFromSuperType = computePathFromSuperTypeReflexive(typeRef, rawSuperType, new HashSet<>());
			if (pathFromSuperType == null) {
				throw new IllegalStateException("Did not found " + rawSuperType + " in super types of " + typeRef);
			}
			TypeRef concreteSuperTypeRef = head(pathFromSuperType);

			if (containsUnboundTypeVariables(concreteSuperTypeRef)) {
				RuleEnvironment Gnext = wrap(G);
				// original code:
				// var Gnext = G;
				// // parameterize the references:
				// for (TypeRef tr : pathFromSuperType.reverseView) {
				// var Gnew = new RuleEnvironment();
				// Gnew.next = Gnext;
				// Gnext = Gnew;
				// }
				concreteSuperTypeRef = genericsComputer.bindTypeVariables(Gnext, concreteSuperTypeRef);
			}

			result.add(
					TypeUtils.copyIfContained(concreteSuperTypeRef));
		}

		return result;
	}

	/**
	 * Returns transitive reflexive closure of <em>common</em> super types. Type arguments are ignored here.
	 *
	 * @return the returned list is sorted, that is, a type's super types are always AFTER the type in the list
	 */
	private List<TypeRef> commonSuperTypesTypeargsIgnored(RuleEnvironment G, Iterable<? extends TypeRef> typeRefs) {
		List<TypeRef> commonSuperTypes = new ArrayList<>();
		for (TypeRef t : typeRefs) {
			if (!addSuperTypesToCommonList(G, t, commonSuperTypes)) {
				return Collections.emptyList();
			}
		}
		return commonSuperTypes;
	}

	/**
	 * Returns transitive, non-reflexive closure of implicit super types. Relexive means, that in case of e.g., Object,
	 * Object is returned itself.
	 */
	private void collectAllImplicitSuperTypes(TypeRef ref, RuleEnvironment G,
			SuperTypesList<TypeRef> superTypesList) {
		superTypesList.addAll(RuleEnvironmentExtensions.collectAllImplicitSuperTypes(G, ref));
	}

	/**
	 * Adds or intersects types in reflexive transitive closure of a given type <i>t</i> to/with given list of super
	 * types.
	 *
	 * @return true, if super types have been added and if client should proceed; <br/>
	 *         false, if no common super type can ever by found and client can stop looking for it
	 */
	private boolean addSuperTypesToCommonList(RuleEnvironment G, TypeRef t,
			List<TypeRef> commonSuperTypes) {

		if (t instanceof IntersectionTypeExpression) {
			return addSuperTypesToCommonList(G, (IntersectionTypeExpression) t, commonSuperTypes);
		}
		if (t instanceof UnionTypeExpression) {
			return addSuperTypesToCommonList(G, (UnionTypeExpression) t, commonSuperTypes);
		}
		if (t instanceof FunctionTypeExprOrRef) {
			return addSuperTypesToCommonList(G, (FunctionTypeExprOrRef) t, commonSuperTypes);
		}

		if (t.getDeclaredType() instanceof TClassifier) {
			SuperTypesList<TypeRef> allDeclaredSuperTypes = newSuperTypesList(tch.getTypeRefComparator());
			allDeclaredSuperTypes.add(t);
			th.collectAllDeclaredSuperTypesTypeargsIgnored(t, allDeclaredSuperTypes);
			collectAllImplicitSuperTypes(t, G, allDeclaredSuperTypes);

			addOrIntersectTypes(G, commonSuperTypes, allDeclaredSuperTypes);
		} else if (t.getDeclaredType() instanceof AnyType) {
			return false;
		} else if (t.getDeclaredType() instanceof PrimitiveType) {
			addOrIntersectTypeWithAssignmentCompatibles(commonSuperTypes, t);
		} else {

			// ignored (as they are common pseudo-sub types): NullType, UndefinedType, VoidType
			// or
			// handled in other addSuperTypesToCommonList, as they have different references
		}
		return true;
	}

	private boolean addSuperTypesToCommonList(RuleEnvironment G, IntersectionTypeExpression t,
			List<TypeRef> commonSuperTypes) {
		SuperTypesList<TypeRef> allDeclaredSuperTypes = newSuperTypesList(tch.getTypeRefComparator());
		for (TypeRef containedRef : t.getTypeRefs()) {
			allDeclaredSuperTypes.add(containedRef);
			th.collectAllDeclaredSuperTypesTypeargsIgnored(containedRef, allDeclaredSuperTypes);
			collectAllImplicitSuperTypes(containedRef, G, allDeclaredSuperTypes);
		}
		addOrIntersectTypes(G, commonSuperTypes, allDeclaredSuperTypes);
		return true;
	}

	private boolean addSuperTypesToCommonList(RuleEnvironment G, UnionTypeExpression t,
			List<TypeRef> commonSuperTypes) {
		SuperTypesList<TypeRef> allDeclaredSuperTypes = newSuperTypesList(tch.getTypeRefComparator());

		allDeclaredSuperTypes.add(t);
		addOrIntersectTypes(G, commonSuperTypes, allDeclaredSuperTypes);
		return true;
	}

	private void addOrIntersectTypeWithAssignmentCompatibles(List<TypeRef> commonSuperTypes,
			TypeRef typeRef) {
		if (commonSuperTypes.isEmpty()) { // quick break
			commonSuperTypes.add(typeRef);
			return;
		}

		if (th.containsByType(commonSuperTypes, typeRef)) {
			if (commonSuperTypes.size() == 1) {
				return;
			}
			commonSuperTypes.clear();
			commonSuperTypes.add(typeRef);
			return;
		}

		Type type = typeRef.getDeclaredType();
		if (type instanceof PrimitiveType) {
			int index = th.findTypeRefOrAssignmentCompatible(commonSuperTypes, typeRef);
			if (index >= 0) {
				// e.g. we have string and found pathselector
				if (((PrimitiveType) type).getAssignmentCompatible() == null) {
					commonSuperTypes.clear();
					commonSuperTypes.add(typeRef);
				} else { // e.g., we have pathselector and found string
					if (commonSuperTypes.size() != 1) {
						TypeRef tr = commonSuperTypes.get(index);
						commonSuperTypes.clear();
						commonSuperTypes.add(tr);
					} // else e.g. there is only string in the list, leave it there
				}
				return;
			}
		}
		commonSuperTypes.clear();
	}

	private boolean addSuperTypesToCommonList(RuleEnvironment G, FunctionTypeExprOrRef f,
			List<TypeRef> commonSuperTypes) {
		SuperTypesList<TypeRef> allDeclaredSuperTypes = newSuperTypesList(tch.getTypeRefComparator());
		allDeclaredSuperTypes.add(f);
		collectAllImplicitSuperTypes(f, G, allDeclaredSuperTypes);
		addOrIntersectTypes(G, commonSuperTypes, allDeclaredSuperTypes);
		return true;
	}

	/**
	 * Returns path from super type to current ref, including the super type and the initial type.
	 *
	 * @return path, or null if the raw super type has not been found (which probably is an illegal result, except in
	 *         combination with intersection types)
	 */
	private List<TypeRef> computePathFromSuperTypeReflexive(TypeRef ref, Type rawSuperType,
			Set<Type> processedTypes) {

		if (ref instanceof IntersectionTypeExpression) {
			return computePathFromSuperTypeReflexive((IntersectionTypeExpression) ref, rawSuperType, processedTypes);
		}

		if (ref.getDeclaredType() == rawSuperType) {
			ArrayList<TypeRef> result = new ArrayList<>();
			result.add(ref);
			return result;
		} else {
			for (ParameterizedTypeRef superTypeRef : declaredSuperTypes(ref.getDeclaredType())) {
				if (processedTypes.add(superTypeRef.getDeclaredType())) {
					List<TypeRef> superPath = computePathFromSuperTypeReflexive(superTypeRef, rawSuperType,
							processedTypes);
					if (superPath != null) {
						superPath.add(ref);

						// we can break here, although there might be several paths to the rawSuperType. However,
						// all paths must result in the same parameterized version, so we only need one
						return superPath;
					}
				}
			}
		}
		return null;
	}

	/**
	 * For intersection types, this returns the first path found.
	 */
	private List<TypeRef> computePathFromSuperTypeReflexive(IntersectionTypeExpression ref,
			Type rawSuperType, Set<Type> processedTypes) {
		for (TypeRef typeRef : ref.getTypeRefs()) {
			List<TypeRef> path = computePathFromSuperTypeReflexive(typeRef, rawSuperType, processedTypes);
			if (path != null) {
				return path;
			}
		}
		return null;
	}

	private void addOrIntersectTypes(RuleEnvironment G, List<TypeRef> commonSuperTypes,
			SuperTypesList<TypeRef> allDeclaredSuperTypes) {
		if (commonSuperTypes.isEmpty()) {
			commonSuperTypes.addAll(allDeclaredSuperTypes);
		} else {

			// extract all functions, they have to be handled differently:
			// there must be only one FunctionTypeExprOrRef in the list:
			FunctionTypeExprOrRef currentSuperFunction = head(
					filter(allDeclaredSuperTypes, FunctionTypeExprOrRef.class));
			FunctionTypeExprOrRef prevCommonSuperFunction = (currentSuperFunction != null)
					? head(filter(commonSuperTypes, FunctionTypeExprOrRef.class))
					: null; // do not search, would be removed anyway

			th.retainAllTypeRefs(commonSuperTypes, allDeclaredSuperTypes);
			if (tch.getTypeRefComparator().compare(prevCommonSuperFunction, currentSuperFunction) != 0) {
				// null or retained anyway
				FunctionTypeExprOrRef commonSuperFunction = joinFunctionTypeRefs(G, currentSuperFunction,
						prevCommonSuperFunction);
				if (commonSuperFunction != null) {
					commonSuperTypes.add(commonSuperFunction);
				}
			}
		}

	}

	/**
	 * May return null if no join is possible, e.g., in f(string) and f(number)
	 */
	private FunctionTypeExprOrRef joinFunctionTypeRefs(RuleEnvironment G, FunctionTypeExprOrRef f1,
			FunctionTypeExprOrRef f2) {
		FunctionTypeExpression joinedFunctionTypeExpr = TypeRefsFactory.eINSTANCE.createFunctionTypeExpression();

		if (f1.getReturnTypeRef() != null && f2.getReturnTypeRef() != null) {
			joinedFunctionTypeExpr.setReturnTypeRef(
					TypeUtils.copyIfContained(tsh.join(G, f1.getReturnTypeRef(), f2.getReturnTypeRef())));
		}
		joinedFunctionTypeExpr.setReturnValueMarkedOptional(f1.isReturnValueOptional() || f2.isReturnValueOptional());

		int maxParSize = Math.max(f1.getFpars().size(), f2.getFpars().size());
		int i = 0;
		boolean varOrOpt1 = false;
		boolean varOrOpt2 = false;
		while (i < maxParSize) {
			TFormalParameter par1 = getFParSmartAndFailSafe(f1, i);
			TFormalParameter par2 = getFParSmartAndFailSafe(f2, i);

			TFormalParameter fpar = null;
			if (par1 == null) {
				fpar = TypeUtils.copy(par2);
			} else if (par2 == null) {
				fpar = TypeUtils.copy(par1);
			} else {
				if (par1.isVariadicOrOptional()) {
					varOrOpt1 = true;
				}
				if (par2.isVariadicOrOptional()) {
					varOrOpt2 = true;
				}

				fpar = TypesFactory.eINSTANCE.createTFormalParameter();
				TypeRef meet = tsh.meet(G, par1.getTypeRef(), par2.getTypeRef());

				if (meet == null) {
					if (varOrOpt1 && varOrOpt2) {
						return joinedFunctionTypeExpr; // cut optional or variadic non-matching arguments
					} else {
						return null; // no join of function possible
					}
				}

				TypeRef parType = TypeUtils.copyIfContained(meet);
				fpar.setTypeRef(parType);

				if (par1.isVariadic() && par2.isVariadic()) {
					fpar.setVariadic(true);
				} else if (par1.isVariadicOrOptional() && par2.isVariadicOrOptional()) {
					fpar.setHasInitializerAssignment(true);
				}
			}
			joinedFunctionTypeExpr.getFpars().add(fpar);
			i = i + 1;
		}
		return joinedFunctionTypeExpr;
	}

	private TFormalParameter getFParSmartAndFailSafe(FunctionTypeExprOrRef f, int index) {
		if (f.getFpars().size() == 0) {
			return null;
		}
		if (index < f.getFpars().size()) {
			return f.getFpars().get(index);
		}
		TFormalParameter last = IterableExtensions.last(f.getFpars());
		if (last.isVariadic()) {
			return last;
		}
		return null;
	}

	private boolean containsUnboundTypeVariables(TypeArgument typeArg) {
		if (typeArg instanceof ParameterizedTypeRef) {
			ParameterizedTypeRef ptr = (ParameterizedTypeRef) typeArg;
			Type declType = ptr.getDeclaredType();
			return declType instanceof TypeVariable // ok, that's simple
					// no type args, type variable is indirectly referenced from raw type
					|| (!ptr.isParameterized() && declType.isGeneric())
					// transitively
					|| exists(ptr.getDeclaredTypeArgs(), ta -> containsUnboundTypeVariables(ta));
		}
		return false;
	}
}
