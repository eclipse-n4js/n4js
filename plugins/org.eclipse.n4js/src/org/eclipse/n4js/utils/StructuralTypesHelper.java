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
package org.eclipse.n4js.utils;

import static com.google.common.collect.Iterables.concat;
import static org.eclipse.n4js.ts.types.TypingStrategy.STRUCTURAL_FIELDS;
import static org.eclipse.n4js.ts.types.TypingStrategy.STRUCTURAL_FIELD_INITIALIZER;
import static org.eclipse.n4js.ts.types.TypingStrategy.STRUCTURAL_READ_ONLY_FIELDS;
import static org.eclipse.n4js.ts.types.TypingStrategy.STRUCTURAL_WRITE_ONLY_FIELDS;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.functionType;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.getContextResource;
import static org.eclipse.n4js.utils.StructuralMembersPredicates.FIELDS_PREDICATE;
import static org.eclipse.n4js.utils.StructuralMembersPredicates.MEMBERS_PREDICATE;
import static org.eclipse.n4js.utils.StructuralMembersPredicates.READABLE_FIELDS_PREDICATE;
import static org.eclipse.n4js.utils.StructuralMembersPredicates.WRITABLE_FIELDS_PREDICATE;
import static org.eclipse.n4js.utils.StructuralMembersPredicates.createBaseStructuralMembersPredicate;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.n4js.ts.typeRefs.BoundThisTypeRef;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression;
import org.eclipse.n4js.ts.typeRefs.IntersectionTypeExpression;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.StructuralTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.ContainerType;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TStructMember;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypeVariable;
import org.eclipse.n4js.ts.types.TypingStrategy;
import org.eclipse.n4js.ts.types.util.MemberList;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.typesystem.utils.TypeSystemHelper;
import org.eclipse.n4js.utils.ContainerTypesHelper.MemberCollector;
import org.eclipse.xtext.xbase.lib.Functions.Function1;

import com.google.inject.Inject;

/**
 * Helper methods for dealing with structural types.
 */
public class StructuralTypesHelper {

	@Inject
	private ContainerTypesHelper containerTypesHelper;
	@Inject
	private TypeSystemHelper tsh;

	/**
	 * Collect the members of the two given structural types and create an iterator for iterating over the triple of
	 * corresponding members.
	 * <p>
	 *
	 * If the {@code restrictInitTyping} argument is {@code true}, then the members will be filtered into read- and
	 * write-only subsets of members depending whether the type reference is on left or right side. If this flag is
	 * {@code false}, then all field structural members will be included in the iterator.
	 *
	 * <TODO IDEBUG-723 remove restrictInitTyping argument. Currently the getters are returned from the member filter if
	 * a type has a getter/setter accessor pair. The getter is the proper one when creating the type constraints. But
	 * when having ~i~ type, setters should return if both getter and setters are available, which breaks the type
	 * checking. For instance union{string,int} is returned when string should be returned. See:
	 * Constraints_55_9_Structural_Fields_Can_Be_Assigned_Via_Public_Accessors.n4js.xt for more details.>
	 *
	 */
	public StructuralMembersTripleIterator getMembersTripleIterator(RuleEnvironment G, TypeRef left, TypeRef right,
			boolean restrictInitTyping) {

		TypingStrategy rightStrategy = right.getTypingStrategy();
		TypingStrategy leftStrategy = left.getTypingStrategy();

		if (STRUCTURAL_FIELD_INITIALIZER == rightStrategy) {
			leftStrategy = (restrictInitTyping) ? STRUCTURAL_READ_ONLY_FIELDS : STRUCTURAL_FIELDS;
			rightStrategy = (restrictInitTyping) ? STRUCTURAL_WRITE_ONLY_FIELDS : STRUCTURAL_FIELDS;
		}

		if (STRUCTURAL_FIELD_INITIALIZER == leftStrategy) {
			leftStrategy = (restrictInitTyping) ? STRUCTURAL_READ_ONLY_FIELDS : STRUCTURAL_FIELDS;
		}

		java.lang.Iterable<TMember> membersLeft = collectStructuralMembers(G, left, leftStrategy);
		java.lang.Iterable<TMember> membersRight = collectStructuralMembers(G, right, rightStrategy);
		return StructuralMembersTripleIterator.ofUnprepared(
				membersLeft,
				membersRight,
				left.getTypingStrategy(), // XXX Intentionally the original typing strategy.
				right.getTypingStrategy() // XXX Same as above.
		);
		// TODO performance: refactor collectStructuralMembers() to already return sorted lists without duplicates and
		// create iterator with method #ofPrepared()
	}

	/**
	 * Collect the members of a structural type.
	 */
	public Iterable<TMember> collectStructuralMembers(RuleEnvironment G, TypeRef typeRef, TypingStrategy strategy) {
		AndFunction1<TMember> predicate1 = MEMBERS_PREDICATE;
		if (strategy == STRUCTURAL_WRITE_ONLY_FIELDS) {
			predicate1 = WRITABLE_FIELDS_PREDICATE;
		}
		if (strategy == STRUCTURAL_READ_ONLY_FIELDS) {
			predicate1 = READABLE_FIELDS_PREDICATE;
		}
		if (strategy == STRUCTURAL_FIELDS) {
			predicate1 = FIELDS_PREDICATE;
		}
		if (strategy == STRUCTURAL_FIELD_INITIALIZER) {
			throw new IllegalStateException("Expected read-only and write-only variants instead.");
		}

		@SuppressWarnings("unchecked")
		AndFunction1<TMember> predicate = createBaseStructuralMembersPredicate(G).and(predicate1);
		return doCollectMembers(G, typeRef, predicate);
	}

	private Iterable<TMember> doCollectMembers(RuleEnvironment G, TypeRef ref, Function1<TMember, Boolean> predicate) {
		if (ref instanceof BoundThisTypeRef) {
			return doCollectMembers(G, (BoundThisTypeRef) ref, predicate);
		} else if (ref instanceof FunctionTypeExpression) {
			return doCollectMembers(G, (FunctionTypeExpression) ref, predicate);
		} else if (ref instanceof IntersectionTypeExpression) {
			return doCollectMembers(G, (IntersectionTypeExpression) ref, predicate);
		} else if (ref instanceof ParameterizedTypeRef) {
			return doCollectMembers(G, (ParameterizedTypeRef) ref, predicate);
		} else if (ref != null) {
			return Collections.emptyList();
		} else {
			throw new IllegalArgumentException("Unhandled parameter types: " +
					Arrays.<Object> asList(G, ref, predicate).toString());
		}
	}

	private Iterable<TMember> doCollectMembers(RuleEnvironment G, IntersectionTypeExpression ref,
			Function1<TMember, Boolean> predicate) {

		Set<TMember> allMembers = new LinkedHashSet<>();
		for (TypeRef tRef : ref.getTypeRefs()) {
			allMembers.addAll(toList(doCollectMembers(G, tRef, predicate)));
		}

		return allMembers;
	}

	private Iterable<TMember> doCollectMembers(RuleEnvironment G, BoundThisTypeRef ref,
			Function1<TMember, Boolean> predicate) {

		Iterable<TMember> structMembersOfThis = doCollectMembers(G, ref.getActualThisTypeRef(), predicate);
		EList<TStructMember> structMembersOfRef = ref.getStructuralMembersWithCallConstructSignatures();
		if (!structMembersOfRef.isEmpty()) {
			return concat(structMembersOfThis, structMembersOfRef);
		}
		return structMembersOfThis;
	}

	private Iterable<TMember> doCollectMembers(RuleEnvironment G, ParameterizedTypeRef ref,
			Function1<TMember, Boolean> predicate) {

		Iterable<TMember> nominalMembers = doCollectMembersOfType(G, ref.getDeclaredType(), predicate);
		if (ref instanceof StructuralTypeRef) {
			EList<TStructMember> structMembersOfRef = ((StructuralTypeRef) ref)
					.getStructuralMembersWithCallConstructSignatures();
			if (!structMembersOfRef.isEmpty()) {
				return concat(nominalMembers, structMembersOfRef);
			}
		}
		return nominalMembers;
	}

	private Iterable<TMember> doCollectMembers(RuleEnvironment G,
			@SuppressWarnings("unused") FunctionTypeExpression ref,
			Function1<TMember, Boolean> predicate) {

		return doCollectMembersOfType(G, functionType(G), predicate);
	}

	private Iterable<TMember> doCollectMembersOfType(RuleEnvironment G, Type type,
			Function1<TMember, Boolean> predicate) {
		if (type instanceof ContainerType) {
			return doCollectMembersOfType(G, (ContainerType<?>) type, predicate);
		} else if (type instanceof TypeVariable) {
			return doCollectMembersOfType(G, (TypeVariable) type, predicate);
		} else if (type != null) {
			return Collections.emptyList();
		} else {
			throw new IllegalArgumentException("Unhandled parameter types: " +
					Arrays.<Object> asList(G, type, predicate).toString());
		}
	}

	private Iterable<TMember> doCollectMembersOfType(RuleEnvironment G, ContainerType<?> type,
			Function1<TMember, Boolean> predicate) {

		MemberCollector mColl = containerTypesHelper.fromContext(getContextResource(G));
		MemberList<TMember> members = mColl.members(type, true, true, true);
		return filter(members, predicate);
	}

	private Iterable<TMember> doCollectMembersOfType(RuleEnvironment G, TypeVariable type,
			Function1<TMember, Boolean> predicate) {

		TypeRef declUB = type.getDeclaredUpperBound();
		TypeRef joinOfUpperBounds = (declUB instanceof IntersectionTypeExpression)
				? tsh.join(G, ((IntersectionTypeExpression) declUB).getTypeRefs())
				: declUB;
		if (joinOfUpperBounds != null) {
			return doCollectMembers(G, joinOfUpperBounds, predicate);
		}
		return Collections.emptyList();
	}
}
