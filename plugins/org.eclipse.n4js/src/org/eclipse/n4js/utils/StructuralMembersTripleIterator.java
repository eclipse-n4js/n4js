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

import static org.eclipse.n4js.ts.types.TypingStrategy.STRUCTURAL_FIELD_INITIALIZER;
import static org.eclipse.n4js.ts.types.TypingStrategy.STRUCTURAL_WRITE_ONLY_FIELDS;
import static org.eclipse.n4js.utils.N4JSLanguageUtils.isReadOnlyField;
import static org.eclipse.n4js.utils.N4JSLanguageUtils.isWriteableField;
import static org.eclipse.n4js.utils.StructuralMembersPredicates.READABLE_FIELDS_PREDICATE;
import static org.eclipse.n4js.utils.StructuralMembersPredicates.WRITABLE_FIELDS_PREDICATE;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.sortWith;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toSet;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.eclipse.n4js.ts.types.FieldAccessor;
import org.eclipse.n4js.ts.types.MemberAccessModifier;
import org.eclipse.n4js.ts.types.TField;
import org.eclipse.n4js.ts.types.TGetter;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.ts.types.TSetter;
import org.eclipse.n4js.ts.types.TStructField;
import org.eclipse.n4js.ts.types.TypingStrategy;

import com.google.common.base.Objects;
import com.google.common.base.Optional;

/**
 * An iterator over pairs of corresponding members from two structural types. Collecting the members is not part of this
 * class, see {@link StructuralTypesHelper} for this.
 */
public class StructuralMembersTripleIterator implements Iterator<StructuralMembersTriple> {

	static MembersByNameTypeAndAccessComparator MEMBERS_BY_NameTypeAndAccess = new MembersByNameTypeAndAccessComparator(
			false);
	static MembersByNameTypeAndAccessComparator MEMBERS_BY_NameAndAccess = new MembersByNameTypeAndAccessComparator(
			true);

	TMember[] membersLeft;
	TMember[] membersRight;
	TypingStrategy leftStrategy;
	TypingStrategy rightStrategy;

	int leftIndex = 0;
	int rightIndex = 0;
	Optional<StructuralMembersTriple> nextTriple = null; // null means: not computed yet!

	private StructuralMembersTripleIterator(TMember[] membersLeft, TMember[] membersRight, TypingStrategy leftStrategy,
			TypingStrategy rightStrategy) {
		this.membersLeft = membersLeft;
		this.membersRight = membersRight;
		this.leftStrategy = leftStrategy;
		this.rightStrategy = rightStrategy;
		computeNext();
	}

	/**
	 * Creates a new iterator from two <em>unprepared</em> member lists, i.e. this method will remove duplicate members
	 * and sort the members. If this has already been done, use method
	 * {@link #ofPrepared(TMember[], TMember[], TypingStrategy, TypingStrategy)} instead.
	 */
	public static StructuralMembersTripleIterator ofUnprepared(Iterable<TMember> membersLeft,
			Iterable<TMember> membersRight,
			TypingStrategy leftStrategy, TypingStrategy rightStrategy) {

		return ofPrepared(toSortedArray(membersLeft), toSortedArray(membersRight), leftStrategy, rightStrategy);
	}

	/**
	 * Creates a new iterator from two <em>prepared</em> member lists, i.e. members must be sorted and must not contain
	 * duplicates. <b>No copy of the given arrays will be created!</b>.
	 */
	public static StructuralMembersTripleIterator ofPrepared(TMember[] membersLeft, TMember[] membersRight,
			TypingStrategy leftStrategy, TypingStrategy rightStrategy) {

		return new StructuralMembersTripleIterator(membersLeft, membersRight, leftStrategy, rightStrategy);
	}

	@Override
	public boolean hasNext() {
		return nextTriple.isPresent();
	}

	@Override
	public StructuralMembersTriple next() {
		if (!nextTriple.isPresent()) {
			throw new NoSuchElementException();
		}
		StructuralMembersTriple currentTriple = nextTriple.get();
		computeNext(); // will change fields 'nextTriple'
		return currentTriple;
	}

	private void computeNext() {
		if (rightIndex < membersRight.length) {

			// pick next rightMember
			TMember rightMember = membersRight[rightIndex]; // rightIndex will be incremented at end of while-loop

			// find corresponding leftMember
			TMember leftMember = null;
			int compare = 0;
			do {
				// pick next candidate on left side
				leftMember = (leftIndex < membersLeft.length) ? membersLeft[leftIndex] : null;
				compare = MEMBERS_BY_NameAndAccess.compare(leftMember, rightMember);
				if (compare == 0 && !compatibleMemberTypes(leftMember, rightMember)) {
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

			FieldAccessor leftOtherAccessor = null;

			// check for the second required accessor on the left side for a right-hand side field
			if (rightMember instanceof TField && leftMember instanceof FieldAccessor) {
				TMember leftMemberNext = (leftIndex + 1 < membersLeft.length) ? membersLeft[leftIndex + 1] : null;
				if (leftMemberNext instanceof FieldAccessor
						&& Objects.equal(leftMemberNext.getName(), rightMember.getName())) {
					leftOtherAccessor = (FieldAccessor) leftMemberNext;
					leftIndex++;
				}
			}

			nextTriple = Optional.of(new StructuralMembersTriple(leftMember, rightMember, leftOtherAccessor));

			// continue with next rightMember
			rightIndex++;
		} else {
			nextTriple = Optional.absent();
		}
	}

	/**
	 * Returns true iff member 'left' can fulfill the structural requirement represented by member 'right'. This method
	 * takes into account <b>only</b> the member kind (i.e. field, getter, etc.), not things like return types,
	 * parameter types (in case of methods), etc.
	 */
	private boolean compatibleMemberTypes(TMember left, TMember right) {

		// Special shortcut cases:
		// Right is ~i~ while left is ~w~. Writable right field and left getter cannot work for sure.
		if (STRUCTURAL_FIELD_INITIALIZER == rightStrategy
				&& STRUCTURAL_WRITE_ONLY_FIELDS == leftStrategy
				&& left instanceof FieldAccessor
				&& isWriteableField(right)) {

			return false;
		}

		// trivial cases
		return (left instanceof TField && right instanceof TField)
				|| (left instanceof TGetter && right instanceof TGetter)
				|| (left instanceof TSetter && right instanceof TSetter)
				|| (left instanceof TMethod && right instanceof TMethod)

				// getters and/or setters can play the role of writable(!) fields and vice versa.
				|| (left instanceof FieldAccessor && isWriteableField(right))
				|| (isWriteableField(left) && right instanceof FieldAccessor)

				// only getters can play the role of read-only fields and vice versa (setters can't!)
				|| (left instanceof TGetter && isReadOnlyField(right))
				|| (isReadOnlyField(left) && right instanceof TGetter)

				// an object literal's property of type function is compared to a class's method.
				// This is not possible vice versa, as the first one is writable and the second one is not.
				|| (left instanceof TStructField && right instanceof TMethod)

				// Special handling when right has ~i~ strategy and left is NOT ~w~
				|| (STRUCTURAL_FIELD_INITIALIZER == rightStrategy
						&& STRUCTURAL_WRITE_ONLY_FIELDS != leftStrategy
						&& WRITABLE_FIELDS_PREDICATE.apply(right)
						&& READABLE_FIELDS_PREDICATE.apply(left));

	}

	/** Made public only to allow access from tests. Don't use from outside. */
	public static synchronized TMember[] toSortedArray(Iterable<TMember> members) {
		// Takes 3sec in OPR build
		return sortWith(toSet(members), MEMBERS_BY_NameTypeAndAccess).toArray(new TMember[0]);
	}

	/***/
	public static class MembersByNameTypeAndAccessComparator implements Comparator<TMember> {
		final boolean ignoreTypes;

		MembersByNameTypeAndAccessComparator(boolean ignoreTypes) {
			this.ignoreTypes = ignoreTypes;
		}

		/**
		 * @return a negative integer, zero, or a positive integer as the first argument is less than, equal to, or
		 *         greater than the second.
		 */
		@Override
		public int compare(TMember m1, TMember m2) {
			if (m1 == null || m1.getName() == null) {
				return 1;
			}
			if (m2 == null || m2.getName() == null) {
				return -1;
			}

			int c = m1.getName().compareTo(m2.getName());
			if (c == 0) {
				c = (!ignoreTypes) ? compareMemberType(m1, m2) : c;
				if (c == 0) {
					c = compareAccessModifier(m1.getMemberAccessModifier(), m2.getMemberAccessModifier());
				}
			}
			return c;
		}

		private int compareMemberType(TMember m1, TMember m2) {
			return Integer.compare(getMemberTypeOrdinal(m1), getMemberTypeOrdinal(m2));
		}

		private int getMemberTypeOrdinal(TMember m) {
			if (m instanceof TField) {
				return 1;
			}
			if (m instanceof TGetter) {
				return 2;
			}
			if (m instanceof TSetter) {
				return 3;
			}
			if (m instanceof TMethod) {
				return 4;
			}

			throw new IllegalArgumentException("unsupported subclass of TMember: " + m.eClass().getName());
		}

		private int compareAccessModifier(MemberAccessModifier m1, MemberAccessModifier m2) {
			return (m1.ordinal() >= m2.ordinal()) ? 0 : -1;
		}
	}
}