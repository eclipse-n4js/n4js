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

import com.google.common.base.Optional
import java.util.Comparator
import java.util.Iterator
import java.util.NoSuchElementException
import org.eclipse.n4js.ts.types.FieldAccessor
import org.eclipse.n4js.ts.types.MemberAccessModifier
import org.eclipse.n4js.ts.types.TField
import org.eclipse.n4js.ts.types.TGetter
import org.eclipse.n4js.ts.types.TMember
import org.eclipse.n4js.ts.types.TMethod
import org.eclipse.n4js.ts.types.TSetter
import org.eclipse.n4js.ts.types.TStructField
import org.eclipse.n4js.ts.types.TypingStrategy

import static org.eclipse.n4js.ts.types.TypingStrategy.*
import static org.eclipse.n4js.utils.StructuralMembersPredicates.*

import static extension org.eclipse.n4js.utils.N4JSLanguageUtils.*

/**
 * An iterator over pairs of corresponding members from two structural types. Collecting the members is not
 * part of this class, see {@link StructuralTypesHelper} for this.
 */
class StructuralMembersTripleIterator implements Iterator<StructuralMembersTriple> {

	static val MEMBERS_BY_NameTypeAndAccess = new MembersByNameTypeAndAccessComparator(false);
	static val MEMBERS_BY_NameAndAccess = new MembersByNameTypeAndAccessComparator(true);

	val TMember[] membersLeft;
	val TMember[] membersRight;
	val TypingStrategy leftStrategy;
	val TypingStrategy rightStrategy;

	var leftIndex = 0;
	var rightIndex = 0;
	var nextTriple = null as Optional<StructuralMembersTriple>; // null means: not computed yet!

	private new(TMember[] membersLeft, TMember[] membersRight, TypingStrategy leftStrategy, TypingStrategy rightStrategy) {
		this.membersLeft = membersLeft;
		this.membersRight = membersRight;
		this.leftStrategy = leftStrategy;
		this.rightStrategy = rightStrategy;
		computeNext();
	}

	/**
	 * Creates a new iterator from two <em>unprepared</em> member lists, i.e. this method will remove duplicate
	 * members and sort the members. If this has already been done, use method {@link #ofPrepared(TMember[],TMember[])}
	 * instead.
	 */
	def static StructuralMembersTripleIterator ofUnprepared(Iterable<TMember> membersLeft, Iterable<TMember> membersRight,
		TypingStrategy leftStrategy, TypingStrategy rightStrategy) {

		return ofPrepared(membersLeft.toSortedArray, membersRight.toSortedArray, leftStrategy, rightStrategy);
	}
	/**
	 * Creates a new iterator from two <em>prepared</em> member lists, i.e. members must be sorted and must not
	 * contain duplicates. <b>No copy of the given arrays will be created!</b>.
	 */
	def static StructuralMembersTripleIterator ofPrepared(TMember[] membersLeft, TMember[] membersRight,
		TypingStrategy leftStrategy, TypingStrategy rightStrategy) {

		return new StructuralMembersTripleIterator(membersLeft, membersRight, leftStrategy, rightStrategy);
	}

	override hasNext() {
		return nextTriple.present;
	}

	override next() {
		if (!nextTriple.present) {
			throw new NoSuchElementException;
		}
		val currentTriple = nextTriple.get;
		computeNext(); // will change fields 'nextTriple'
		return currentTriple;
	}

	def private void computeNext() {
		if (rightIndex <  membersRight.size) {

			// pick next rightMember
			var TMember rightMember = membersRight.get(rightIndex); // rightIndex will be incremented at end of while-loop

			// find corresponding leftMember
			var TMember leftMember = null;
			var compare = 0;
			do {
				// pick next candidate on left side
				leftMember = if (leftIndex < membersLeft.size) membersLeft.get(leftIndex) else null;
				compare = MEMBERS_BY_NameAndAccess.compare(leftMember, rightMember);
				if (compare === 0 && !compatibleMemberTypes(leftMember, rightMember)) {
					compare = MEMBERS_BY_NameTypeAndAccess.compare(leftMember, rightMember);
				}
				// match?
				if (compare < 0) {
					// mismatch: since leftMember is lower than rightMember, upcoming leftMembers might be a match
					// -> so skip current leftMember and continue the search for the current rightMember ...
					leftIndex++;
				} else if (compare > 0) {
					// mismatch: since leftMember is greater than rightMember, later leftMembers won't be a match either
					// -> so stop searching and do *not* increment leftIndex, because the current leftMember might be a
					// match for the next rightMember
					leftMember = null;
				} else {
					// match!
					// -> do nothing (in particular, do not increase leftIndex, as there may be duplicates in
					// membersRight, so the current leftMember might be a match for the next rightMember)
				}
			} while (compare < 0); // exit if equals or greater

			var leftOtherAccessor = null as FieldAccessor;

			// check for the second required accessor on the left side for a right-hand side field
			if (rightMember instanceof TField && leftMember instanceof FieldAccessor) {
				val leftMemberNext = if (leftIndex + 1 < membersLeft.size) membersLeft.get(leftIndex + 1) else null;
				if (leftMemberNext instanceof FieldAccessor && leftMemberNext.name == rightMember.name) {
					leftOtherAccessor = leftMemberNext as FieldAccessor;
					leftIndex++;
				}
			}

			nextTriple = Optional.of(new StructuralMembersTriple(leftMember, rightMember, leftOtherAccessor));

			// continue with next rightMember
			rightIndex++;
		} else {
			nextTriple = Optional.absent;
		}
	}

	/**
	 * Returns true iff member 'left' can fulfill the structural requirement represented by member 'right'.
	 * This method takes into account <b>only</b> the member kind (i.e. field, getter, etc.), not things like
	 * return types, parameter types (in case of methods), etc.
	 */
	def private boolean compatibleMemberTypes(TMember left, TMember right) {

		// Special shortcut cases:
		// Right is ~i~ while left is ~w~. Writable right field and left getter cannot work for sure.
		if (STRUCTURAL_FIELD_INITIALIZER === rightStrategy
			&& STRUCTURAL_WRITE_ONLY_FIELDS === leftStrategy
			&& left instanceof FieldAccessor
			&& right.isWriteableField) {

			return false;
		}

			// trivial cases
		return (left instanceof TField && right instanceof TField)
			|| (left instanceof TGetter && right instanceof TGetter)
			|| (left instanceof TSetter && right instanceof TSetter)
			|| (left instanceof TMethod && right instanceof TMethod)

			// getters and/or setters can play the role of writable(!) fields and vice versa.
			|| (left instanceof FieldAccessor && right.isWriteableField)
			|| (left.isWriteableField && right instanceof FieldAccessor)

			// only getters can play the role of read-only fields and vice versa (setters can't!)
			|| (left instanceof TGetter && right.isReadOnlyField)
			|| (left.isReadOnlyField && right instanceof TGetter)

			// an object literal's property of type function is compared to a class's method.
			// This is not possible vice versa, as the first one is writable and the second one is not.
			|| (left instanceof TStructField && right instanceof TMethod)

			// Special handling when right has ~i~ strategy and left is NOT ~w~
			|| (STRUCTURAL_FIELD_INITIALIZER === rightStrategy
				&& STRUCTURAL_WRITE_ONLY_FIELDS !== leftStrategy
				&& WRITABLE_FIELDS_PREDICATE.apply(right)
				&& READABLE_FIELDS_PREDICATE.apply(left)
			)

	}

	/** Made public only to allow access from tests. Don't use from outside. */
	def static synchronized TMember[] toSortedArray(Iterable<TMember> members) {
		// Takes 3sec in OPR build
		return members.toSet.sortWith(MEMBERS_BY_NameTypeAndAccess);
	}
}


class MembersByNameTypeAndAccessComparator implements Comparator<TMember> {

	final boolean ignoreTypes;

	new(boolean ignoreTypes) {
		this.ignoreTypes = ignoreTypes;
	}

	/**
	 * @return a negative integer, zero, or a positive integer as the
     *         first argument is less than, equal to, or greater than the
     *         second.
	 */
	override compare(TMember m1, TMember m2) {

		if (m1 === null || m1.name === null) {
			return 1;
		}
		if (m2 === null || m2.name === null) {
			return -1;
		}

		var c = m1.name.compareTo(m2.name);
		if (c === 0) {
			c = if (!ignoreTypes) compareMemberType(m1, m2) else c;
			if (c === 0) {
				m1.memberAccessModifier.compareAccessModifier(m2.memberAccessModifier);
			}
		}
		return c
	}

	def private int compareMemberType(TMember m1, TMember m2) {
		return Integer.compare(m1.memberTypeOrdinal, m2.memberTypeOrdinal);
	}

	def private int getMemberTypeOrdinal(TMember m) {
		return switch(m) {
			TField: 1
			TGetter: 2
			TSetter: 3
			TMethod: 4
			default:
				throw new IllegalArgumentException("unsupported subclass of TMember: "+m.eClass.name)
		}
	}

	def private compareAccessModifier(MemberAccessModifier m1, MemberAccessModifier m2) {
		return if (m1.ordinal >= m2.ordinal) 0 else -1;
	}
}
