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
package org.eclipse.n4js.utils

import com.google.inject.Inject
import org.eclipse.n4js.typesystem.TypeSystemHelper
import org.eclipse.n4js.ts.typeRefs.BoundThisTypeRef
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.types.ContainerType
import org.eclipse.n4js.ts.types.TMember
import org.eclipse.n4js.ts.types.Type
import org.eclipse.n4js.ts.types.TypeVariable
import org.eclipse.n4js.ts.types.TypingStrategy
import org.eclipse.n4js.typesystem.utils.RuleEnvironment
import org.eclipse.xtext.xbase.lib.Functions.Function1

import static org.eclipse.n4js.ts.types.TypingStrategy.*

import static extension com.google.common.collect.Iterables.concat
import static extension org.eclipse.n4js.typesystem.RuleEnvironmentExtensions.*
import static extension org.eclipse.n4js.utils.StructuralMembersPredicates.*
import org.eclipse.n4js.ts.typeRefs.IntersectionTypeExpression

/**
 * Helper methods for dealing with structural types.
 */
class StructuralTypesHelper {

	@Inject
	private ContainerTypesHelper containerTypesHelper;
	@Inject
	private TypeSystemHelper tsh;

	/**
	 * Collect the members of the two given structural types and create an iterator for iterating over
	 * the triple of corresponding members.
	 * <p>
	 *
	 * If the {@code restrictInitTyping} argument is {@code true}, then the members will be filtered into read- and write-only
	 * subsets of members depending whether the type reference is on left or right side. If this flag is {@code false}, then all field structural
	 * members will be included in the iterator.
	 *
	 * <TODO IDEBUG-723 remove restrictInitTyping argument. Currently the getters are returned from the member filter if a type has a getter/setter accessor
	 * pair. The getter is the proper one when creating the type constraints. But when having ~i~ type, setters should return if both getter and
	 * setters are available, which breaks the type checking. For instance union{string,int} is returned when string should be returned.
	 * See: Constraints_55_9_Structural_Fields_Can_Be_Assigned_Via_Public_Accessors.n4js.xt for more details.>
	 *
	 */
	def StructuralMembersTripleIterator getMembersTripleIterator(RuleEnvironment G, TypeRef left, TypeRef right, boolean restrictInitTyping) {

		var rightStrategy = right.typingStrategy;
		var leftStrategy = left.typingStrategy;

		if (STRUCTURAL_FIELD_INITIALIZER === rightStrategy) {
			leftStrategy = if (restrictInitTyping) STRUCTURAL_READ_ONLY_FIELDS else STRUCTURAL_FIELDS;
			rightStrategy = if (restrictInitTyping) STRUCTURAL_WRITE_ONLY_FIELDS else STRUCTURAL_FIELDS;
		}

		if (STRUCTURAL_FIELD_INITIALIZER === leftStrategy) {
			leftStrategy = if (restrictInitTyping) STRUCTURAL_READ_ONLY_FIELDS else STRUCTURAL_FIELDS;
		}

		val membersLeft = collectStructuralMembers(G, left, leftStrategy);
		val membersRight = collectStructuralMembers(G, right, rightStrategy);
		return StructuralMembersTripleIterator.ofUnprepared(
			membersLeft,
			membersRight,
			left.typingStrategy, //XXX Intentionally the original typing strategy.
			right.typingStrategy //XXX Same as above.
		);
		// TODO performance: refactor collectStructuralMembers() to already return sorted lists without duplicates and create iterator with method #ofPrepared()
	}

	/**
	 * Collect the members of a structural type.
	 */
	def Iterable<TMember> collectStructuralMembers(RuleEnvironment G, TypeRef typeRef, TypingStrategy strategy) {
		val predicate = G.createBaseStructuralMembersPredicate.and(
			switch (strategy) {
				case STRUCTURAL_WRITE_ONLY_FIELDS: WRITABLE_FIELDS_PREDICATE
				case STRUCTURAL_READ_ONLY_FIELDS: READABLE_FIELDS_PREDICATE
				case STRUCTURAL_FIELDS: FIELDS_PREDICATE
				case STRUCTURAL_FIELD_INITIALIZER: throw new IllegalStateException('Expected read-only and write-only variants instead.')
				default: MEMBERS_PREDICATE
			}
		);
		return doCollectMembers(G, typeRef, predicate);
	}

	def private dispatch Iterable<TMember> doCollectMembers(RuleEnvironment G, TypeRef ref,
		Function1<TMember, Boolean> predicate) {

		return emptyList;
	}

	def private dispatch Iterable<TMember> doCollectMembers(RuleEnvironment G, BoundThisTypeRef ref,
		Function1<TMember, Boolean> predicate) {

		val structMembersOfThis = doCollectMembers(G, ref.actualThisTypeRef, predicate);
		if (!ref.structuralMembers.empty) {
			return structMembersOfThis.concat(ref.structuralMembers);
		}
		return structMembersOfThis;
	}

	def private dispatch Iterable<TMember> doCollectMembers(RuleEnvironment G, ParameterizedTypeRef ref,
		Function1<TMember, Boolean> predicate) {

		val nominalMembers = doCollectMembersOfType(G, ref.declaredType, predicate);
		if (!ref.structuralMembers.empty) {
			return nominalMembers.concat(ref.structuralMembers);
		}
		return nominalMembers;
	}

	def private dispatch Iterable<TMember> doCollectMembersOfType(RuleEnvironment G, Type type,
		Function1<TMember, Boolean> predicate) {

		return emptyList;
	}

	def private dispatch Iterable<TMember> doCollectMembersOfType(RuleEnvironment G, ContainerType<?> type,
		Function1<TMember, Boolean> predicate) {

		return containerTypesHelper.fromContext(G.contextResource).members(type).filter(predicate);
	}

	def private dispatch Iterable<TMember> doCollectMembersOfType(RuleEnvironment G, TypeVariable type,
		Function1<TMember, Boolean> predicate) {

		val declUB = type.declaredUpperBound;
		val joinOfUpperBounds = if(declUB instanceof IntersectionTypeExpression) {
			tsh.join(G, declUB.typeRefs)
		} else {
			declUB
		};
		if (joinOfUpperBounds !== null) {
			return doCollectMembers(G, joinOfUpperBounds, predicate);
		}
		return emptyList;
	}
}
