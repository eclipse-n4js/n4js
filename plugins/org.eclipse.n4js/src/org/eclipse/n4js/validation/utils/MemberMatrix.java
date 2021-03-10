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
package org.eclipse.n4js.validation.utils;

import static org.eclipse.n4js.ts.types.MemberType.FIELD;
import static org.eclipse.n4js.ts.types.MemberType.GETTER;
import static org.eclipse.n4js.ts.types.MemberType.METHOD;
import static org.eclipse.n4js.ts.types.MemberType.SETTER;

import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.Iterables;

import org.eclipse.n4js.ts.types.MemberAccessModifier;
import org.eclipse.n4js.ts.types.MemberType;
import org.eclipse.n4js.ts.types.TInterface;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.util.MemberList;
import org.eclipse.n4js.ts.utils.TypeUtils;
import org.eclipse.n4js.utils.UtilN4;
import org.eclipse.n4js.validation.validators.N4JSMemberRedefinitionValidator;

/**
 * Helper class for {@link N4JSMemberRedefinitionValidator} storing all members with same name and static modifier in a
 * matrix, in which the columns represent the source of the member (owned, inherited, implemented) and the rows the type
 * of the member (method, getter, setter, field).
 * <p>
 * The debug output illustrates the structure of the matrix. The example here shows a method f defined in two interfaces
 * (I and J), which is implemented by the class itself (owned C):
 *
 * <pre>
 * MemberMatrix: f
 *           |owned         |inherited     |implemented
 * GETTER:   |              |              |
 * SETTER:   |              |              |
 * FIELD:    |              |              |
 * METHOD:   |C             |              |I,J
 * </pre>
 *
 * In addition, this class holds some helper info:
 * <ul>
 * <li>members can be marked as consumed, see {@link #markConsumed(MemberList)}.
 * <li>a list of "non-implemented" members is maintained, i.e. static members from interfaces which are ignored in the
 * matrix, because there is no inheritance for static members of interfaces. See {@link #add(int, TMember)} for details.
 * </ul>
 */
public class MemberMatrix {

	// columns (index 0)
	final static String[] COLS = { "owned", "inherited", "implemented" };

	final static int OWNED = 0;
	final static int INHERITED = 1;
	final static int IMPLEMENTED = 2;

	final static int CONSUMED = 3;

	/** Number of source kinds, *not* including {@link #CONSUMED}. */
	final static int SOURCE_COUNT_WITHOUT_CONSUMED = 3; // number of columns (not considering CONSUMED)
	final static int MEMBER_TYPE_COUNT = MemberType.values().length; // number of rows

	/**
	 * Set of actually consumed members, added in via {@link #markConsumed(MemberList)}.
	 */
	private final Set<TMember> consumed;

	// rows (index 1) according to MemberType values

	private final MemberList<TMember>[][] memberMatrix;

	/**
	 * List of members from interfaces that are not implemented, because they are static.
	 */
	private final MemberList<TMember> nonImplemented;

	/**
	 * Iterator which holds information about the source (owned, consumed, inherited, implemented) of the last returned
	 * element.
	 */
	public class SourceAwareIterator implements Iterator<TMember> {

		private int lastRetrievedSource = -1;
		private TMember lastRetrievedElement;

		/** The source used to find the next element */
		protected int source;
		/** The current iterator */
		protected Iterator<TMember> currentIter;
		private TMember next;
		final private boolean returnConsumed;

		SourceAwareIterator(boolean returnConsumed) {
			this.returnConsumed = returnConsumed;
			initIter();
			next = findNext();
		}

		/**
		 * Initialized iterator with which we start, the owned members by default.
		 */
		protected void initIter() {
			source = OWNED;
			currentIter = members(source).iterator();
		}

		/**
		 * Returns true if the last returned member is actually contained in the type, that is it is either owned,
		 * inherited, or mixed in. If it is inherited, it is not overridden.
		 */
		public boolean isActualMember() {
			return lastRetrievedSource == OWNED || lastRetrievedSource == CONSUMED
					|| (lastRetrievedSource == INHERITED && isActuallyInherited(lastRetrievedElement));
		}

		/**
		 * Returns true if the source of the last returned element is an interface (either consumed in classifier or to
		 * be implemented).
		 */
		public boolean isInterfaceMember() {
			return lastRetrievedSource == IMPLEMENTED;
		}

		/**
		 * Returns true if the source of the last returned element is the super class.
		 */
		public boolean isInheritedMember() {
			return lastRetrievedSource == INHERITED;
		}

		/**
		 * Returns true if the source of the last returned element is the class itself.
		 */
		public boolean isOwnedMember() {
			return lastRetrievedSource == OWNED;
		}

		/**
		 * Finds next member.
		 */
		protected TMember findNext() {
			do {
				while (currentIter.hasNext()) {
					TMember m = currentIter.next();
					if (!returnConsumed || source != IMPLEMENTED || !consumed.contains(m)) {
						return m;
					}
				}
				currentIter = nextIter();
			} while (source >= 0);
			return null;
		}

		/**
		 * Selects the next iterator.
		 */
		protected Iterator<TMember> nextIter() {
			switch (source) {
			case OWNED:
				if (returnConsumed) {
					source = CONSUMED;
					return consumed.iterator();
				} // else
					// $FALL-THROUGH$
			case CONSUMED: {
				source = INHERITED;
				return members(source).iterator();
			}
			case INHERITED: {
				source = IMPLEMENTED;
				return members(source).iterator();
			}
			case IMPLEMENTED:
				// $FALL-THROUGH$
			default:
				source = -1;
				return null;
			}
		}

		@Override
		public boolean hasNext() {
			return source >= 0;
		}

		@Override
		public TMember next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			lastRetrievedElement = next;
			lastRetrievedSource = source;
			next = findNext();
			return lastRetrievedElement;
		}

	}

	/**
	 * Iterator that iterates only over consumed and actually inherited members.
	 */
	public class ActuallyInheritedAndConsumedMembersIterator extends SourceAwareIterator {

		/** Delegates to super constructor */
		ActuallyInheritedAndConsumedMembersIterator() {
			super(true);
		}

		@Override
		protected void initIter() {
			source = CONSUMED;
			currentIter = consumed.iterator();
		}

		@Override
		protected TMember findNext() {
			do {
				while (currentIter.hasNext()) {
					TMember m = currentIter.next();
					if (source == CONSUMED || isActuallyInherited(m)) {
						return m;
					}
				}
				currentIter = nextIter();
			} while (source >= 0);
			return null;
		}

		@Override
		protected Iterator<TMember> nextIter() {
			switch (source) {
			case CONSUMED: {
				source = INHERITED;
				return members(source).iterator();
			}
			default:
				source = -1;
				return null;
			}
		}

	}

	/**
	 * Creates an empty matrix.
	 */
	@SuppressWarnings("unchecked")
	public MemberMatrix() {
		memberMatrix = new MemberList[SOURCE_COUNT_WITHOUT_CONSUMED][MEMBER_TYPE_COUNT];
		consumed = new HashSet<>(2);
		nonImplemented = new MemberList<>(2);
	}

	/**
	 * @see N4JSMemberRedefinitionValidator#constraints_61_Consumption(MemberMatrix)
	 */
	@SuppressWarnings("javadoc")
	public void markConsumed(MemberList<TMember> consumedMembers) {
		consumed.addAll(consumedMembers);
	}

	/**
	 * Tells if the given member is one of the "consumed members" added via method {@link #markConsumed(MemberList)}.
	 */
	public boolean isConsumed(TMember member) {
		return consumed.contains(member);
	}

	/**
	 * Returns true if the matrix contains an owned getter and an owned setter.
	 */
	public boolean hasOwnedAccessorPair() {
		return !members(OWNED, GETTER).isEmpty() && !members(OWNED, SETTER).isEmpty();
	}

	/**
	 * Returns true if the matrix contains a mixed accessor pair. That is that getter and setter have different origins
	 * (inherited, owned). This method doesn't consider consumed members.
	 */
	public boolean hasMixedAccessorPair() {
		boolean hasOwnedGetter = !members(OWNED, GETTER).isEmpty();
		boolean hasOwnedSetter = !members(OWNED, SETTER).isEmpty();
		boolean hasInheritedGetter = !members(INHERITED, GETTER).isEmpty();
		boolean hasInheritedSetter = !members(INHERITED, SETTER).isEmpty();

		return (!hasOwnedGetter && hasInheritedGetter && hasOwnedSetter) ||
				(hasOwnedGetter && hasInheritedSetter && !hasOwnedSetter);
	}

	/**
	 * Returns {@code true} if the matrix contains a getter as well as a setter.
	 */
	public boolean hasAccessorPair() {
		return (!members(OWNED, GETTER).isEmpty() || !members(INHERITED, GETTER).isEmpty()
				|| !members(IMPLEMENTED, GETTER).isEmpty()) &&
				(!members(OWNED, SETTER).isEmpty() || !members(INHERITED, SETTER).isEmpty()
						|| !members(IMPLEMENTED, SETTER).isEmpty());
	}

	/**
	 * Returns all owned, inherited and implemented members (but not consumed members, since they are already contained
	 * in "implemented").
	 */
	public SourceAwareIterator allMembers() {
		return new SourceAwareIterator(false);
	}

	/**
	 * Returns all actually inherited and actually consumed in members. The latter requires them to be set before. Owned
	 * members are always actual members (and usually handled elsewhere by caller).
	 */
	public ActuallyInheritedAndConsumedMembersIterator actuallyInheritedAndMixedMembers() {
		return new ActuallyInheritedAndConsumedMembersIterator();
	}

	/**
	 * Returns a source aware iterator, with the elements are ordered according to owned, consumed, inherited, and
	 * implemented. Consumed members are only returned once.
	 */
	public SourceAwareIterator ownedConsumedInheritedImplemented() {
		return new SourceAwareIterator(true);
	}

	private <T extends TMember> MemberList<T> members(int source, MemberType type) {
		@SuppressWarnings("unchecked")
		MemberList<T> list = (MemberList<T>) memberMatrix[source][type.getValue()];
		if (list == null) {
			return MemberList.emptyList();
		}
		return list;
	}

	private boolean hasOwnedOrNotAbstractMember(int source, MemberType type) {
		MemberList<TMember> list = members(source, type);
		if (list.isEmpty()) {
			return false;
		}
		if (source == OWNED) {
			return true;
		}
		return list.stream().anyMatch(m -> !m.isAbstract());
	}

	/**
	 * Returns all owned members, which could be multiple members in case of accessors or in error cases.
	 */
	public Iterable<TMember> owned() {
		return members(OWNED);
	}

	/**
	 * Returns all inherited members, which could be multiple members in case of accessors or in error cases.
	 */
	public Iterable<TMember> inherited() {
		return members(INHERITED);
	}

	/**
	 * Returns all implemented members, which could be multiple members in case of accessors, if several interfaces
	 * provide the same member, or in error cases.
	 */
	public Iterable<TMember> implemented() {
		return members(IMPLEMENTED);
	}

	private Iterable<TMember> members(int source) {
		return hasSource(source) ? Iterables.concat(members(
				source, GETTER),
				members(source, SETTER),
				members(source, FIELD),
				members(source, METHOD)) : MemberList.emptyList();
	}

	/**
	 * Returns the "non-implemented" members, i.e. static members from interfaces which are ignored in the matrix,
	 * because there is no inheritance for static members of interfaces. See {@link #add(int, TMember)} for details.
	 */
	public Iterable<TMember> nonImplemented() {
		return nonImplemented;
	}

	/**
	 * Returns true if the matrix contains owned members.
	 */
	public boolean hasOwned() {
		return hasSource(OWNED);
	}

	/**
	 * Returns true if the matrix contains inherited members.
	 */
	public boolean hasInherited() {
		return hasSource(INHERITED);
	}

	/**
	 * Returns true if the matrix contains implemented members.
	 */
	public boolean hasImplemented() {
		return hasSource(IMPLEMENTED);
	}

	/**
	 * Returns true iff there were "non-implemented" members, i.e. static members from interfaces which are ignored in
	 * the matrix, because there is no inheritance for static members of interfaces. See {@link #add(int, TMember)} for
	 * details.
	 */
	public boolean hasNonImplemented() {
		return !nonImplemented.isEmpty();
	}

	private boolean hasSource(int source) {
		for (int i = 0; i < MEMBER_TYPE_COUNT; i++) {
			if (memberMatrix[source][i] != null) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns true if the given member stemming from the super class (not checked here) is actually inherited and not
	 * overridden by owned or consumed members. In case of meta-type problems, the inherited member is not actually
	 * inherited. Note that consumed members have not to be calculated up-front.
	 */
	boolean isActuallyInherited(TMember m) {
		if (hasOwned()) {
			if (m.getMemberType() == GETTER) {
				if (hasOwnedOrNotAbstractMember(OWNED, FIELD)
						|| hasOwnedOrNotAbstractMember(OWNED, GETTER)
						|| hasOwnedOrNotAbstractMember(OWNED, METHOD)) {
					return false;
				}
			} else if (m.getMemberType() == SETTER) {
				if (hasOwnedOrNotAbstractMember(OWNED, FIELD)
						|| hasOwnedOrNotAbstractMember(OWNED, SETTER)
						|| hasOwnedOrNotAbstractMember(OWNED, METHOD)) {
					return false;
				}
			} else {
				return false;
			}
		}
		if (m.isField() || !m.isAbstract()) {
			return true;
		}
		if (hasImplemented()) {
			if (m.getMemberType() == GETTER) {
				if (hasOwnedOrNotAbstractMember(IMPLEMENTED, FIELD)
						|| hasOwnedOrNotAbstractMember(IMPLEMENTED, GETTER)
						|| hasOwnedOrNotAbstractMember(IMPLEMENTED, METHOD)) {
					return false;
				}
			} else if (m.getMemberType() == SETTER) {
				if (hasOwnedOrNotAbstractMember(IMPLEMENTED, FIELD)
						|| hasOwnedOrNotAbstractMember(IMPLEMENTED, SETTER)
						|| hasOwnedOrNotAbstractMember(IMPLEMENTED, METHOD)) {
					return false;
				}
			} else {
				return false;
			}
		}
		return true;
	}

	/**
	 * Adds a member from the given source.
	 */
	public void add(int source, TMember member) {
		if (source == IMPLEMENTED && member.isStatic() && member.getContainingType() instanceof TInterface) {
			// no inheritance of static methods in interfaces
			// -> ignore this member in the matrix but keep it in helper field 'nonImplemented' to allow client code to
			// retrieve these members for showing better error messages, etc.
			nonImplemented.add(member);
			return;
		}
		int row = member.getMemberType().getValue();
		MemberList<TMember> list = memberMatrix[source][row];
		if (list == null) {
			list = new MemberList<>();
			memberMatrix[source][row] = list;
		}
		list.add(member);
	}

	@Override
	public String toString() {
		if (!allMembers().hasNext()) {
			return "MemberMatrix not initialized yet.";
		}
		TMember first = allMembers().next();
		StringBuilder strb = new StringBuilder("MemberMatrix: ");
		if (first.isStatic()) {
			strb.append("static ");
		}
		strb.append(first.getName());
		strb.append("\n");
		final int tab = 15;
		StringBuilder row = new StringBuilder();
		tab(row, 10);
		for (int source = 0; source < SOURCE_COUNT_WITHOUT_CONSUMED; source++) {
			row.append(COLS[source]);
			if (source < 2) {
				tab(row, 10 + (1 + source) * tab);
			}
		}
		strb.append(row);

		for (MemberType type : MemberType.values()) {
			row.setLength(0);
			row.append(type.getName()).append(": ");
			tab(row, 10);
			for (int source = 0; source < SOURCE_COUNT_WITHOUT_CONSUMED; source++) {
				row.append(members(source, type).stream().map(m -> m.getContainingType().getName())
						.collect(Collectors.joining(",")));
				if (source < 2) {
					tab(row, 10 + (1 + source) * tab);
				}
			}
			strb.append("\n").append(row);
		}
		strb.append("\n");
		if (!consumed.isEmpty()) {
			strb.append("consumed: ");
			strb.append(
					consumed.stream()
							.map(m -> m != null ? m.getMemberType() + " " + m.getContainingType().getName() + "."
									+ m.getName() : "null")
							.collect(Collectors.joining(",")));
		}

		return strb.toString();
	}

	private void tab(StringBuilder strb, int offset) {
		UtilN4.fill(strb, offset);
		strb.append('|');
	}

	/**
	 * Returns a one-line description, only for debugging purposes
	 */
	String toShortString() {
		if (!allMembers().hasNext()) {
			return "MemberMatrix not initialized yet.";
		}
		StringBuilder strb = new StringBuilder("[");
		for (MemberType type : MemberType.values()) {
			for (int source = 0; source < SOURCE_COUNT_WITHOUT_CONSUMED; source++) {
				strb.append(members(source, type).stream()
						.map(m -> m.getMemberType().getName().charAt(0) + " " + m.getContainingType().getName())
						.collect(Collectors.joining(",")));
			}
		}
		if (!consumed.isEmpty()) {
			strb.append(", consumed: ");
			strb.append(
					consumed.stream()
							.map(m -> m != null ? m.getMemberType().getName().charAt(0) + " "
									+ m.getContainingType().getName() : "null")
							.collect(Collectors.joining(",")));
		}
		strb.append("]");
		return strb.toString();
	}

	/**
	 * Returns a possible override candidate, even a "wrong" candidate (e.g., a field for a method). Returns null if no
	 * such candidate has been found. If possible, a non-private candidate is searched.
	 */
	public TMember possibleOverrideCandidateOrError(TMember member) {
		TMember result = null;
		for (TMember m : Iterables.concat(implemented(), inherited())) {
			if (!TypeUtils.isAccessorPair(member, m)) {
				if (m.getMemberAccessModifier() == MemberAccessModifier.PRIVATE) {
					result = m;
				} else {
					return m;
				}
			}
		}

		return result;
	}
}
