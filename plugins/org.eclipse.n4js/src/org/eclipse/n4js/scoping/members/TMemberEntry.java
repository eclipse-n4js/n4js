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
package org.eclipse.n4js.scoping.members;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import com.google.common.base.Joiner;

import org.eclipse.n4js.ts.types.TField;
import org.eclipse.n4js.ts.types.TGetter;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.ts.types.TSetter;

/**
 * Helper value object for container type helper, storing concrete and abstract members of a type. Fields defined in
 * roles are handled as abstract.
 */
public class TMemberEntry implements Iterable<TMember> {

	/**
	 * Constant value of selecting concrete members, note that fields are ALWAYS concrete.
	 */
	public final static boolean CONCRETE = false;
	/**
	 * Constant value of selecting abstract members, note that fields are ALWAYS concrete.
	 */
	public final static boolean ABSTRACT = true;

	/**
	 * Source of a member relative to current type.
	 */
	public static enum MemberSource {
		/** Defined in classifier ({@link #OWNED}) */
		OWNED,
		/**
		 * Inherited from a super class. Note that inherited members may have been mixed into the super class
		 * previously, so the source is relative to the current type. {@link #INHERITED}.
		 */
		INHERITED,
		/**
		 * Mixed in from an interface.
		 */
		MIXEDIN
	}

	private static final int METHOD = 0;
	private static final int GETTER = 1;
	private static final int SETTER = 2;
	private static final int FIELD = 3;
	private static final int ABSTRACT_OFFSET = 4;
	private static final int LENGTH = 7; // no abstract field!

	private final String name;
	private final boolean _static;

	/**
	 * Field, Getter, Setter, Method + abstract vs. concrete
	 */
	private final TMember[] members = new TMember[LENGTH];
	private final MemberSource[] sources = new MemberSource[LENGTH];

	private class TMemberEntryIterator implements Iterator<TMember> {

		private int next;

		private TMemberEntryIterator() {
			next = 0;
			findNext();
		}

		private void findNext() {
			while (next < LENGTH && members[next] == null) {
				next++;
			}
		}

		@Override
		public boolean hasNext() {
			return next < LENGTH;
		}

		@Override
		public TMember next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			TMember m = members[next];
			next++;
			findNext();
			return m;
		}

	}

	/**
	 * Creates a new entry for a member with given name and static modifier. The actual members are to be added via the
	 * add methods.
	 */
	public TMemberEntry(String name, boolean _static) {
		this.name = name;
		this._static = _static;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof TMemberEntry) {
			TMemberEntry other = (TMemberEntry) obj;
			return other._static == _static && other.name.equals(name);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return name.hashCode() + (_static ? 7345 : 0);
	}

	@Override
	public Iterator<TMember> iterator() {
		return new TMemberEntryIterator();
	}

	private int index(TMember m) {
		int i = m.isField() ? FIELD : m.isGetter() ? GETTER : m.isSetter() ? SETTER : METHOD /* method */;
		if (m.isAbstract() && i != FIELD) {
			i += ABSTRACT_OFFSET;
		}
		return i;
	}

	/**
	 * Adds a member if no other member with same meta type has been added before. Returns true if member actuall has
	 * been added.
	 */
	public boolean add(TMember m, MemberSource source) {
		int index = index(m);
		if (members[index] == null) {
			members[index] = m;
			sources[index] = source;
			return true;
		}
		return false;
	}

	/**
	 * Returns the concrete or abstract method if contained in the entry, null otherwise.
	 */
	public TMethod method(boolean _abstract) {
		return (TMethod) ((_abstract) ? members[ABSTRACT_OFFSET + METHOD] : members[METHOD]);
	}

	/**
	 * Returns the concrete or abstract setter if contained in the entry, null otherwise.
	 */
	public TSetter setter(boolean _abstract) {
		return (TSetter) ((_abstract) ? members[ABSTRACT_OFFSET + SETTER] : members[SETTER]);
	}

	/**
	 * Returns the concrete or abstract getter if contained in the entry, null otherwise.
	 */
	public TGetter getter(boolean _abstract) {
		return (TGetter) ((_abstract) ? members[ABSTRACT_OFFSET + GETTER] : members[GETTER]);
	}

	/**
	 * Returns the concrete or abstract method if contained in the entry, null otherwise.
	 */
	public TField field() {
		return (TField) members[FIELD];
	}

	/**
	 * Returns true if this entry contains either an abstract or concrete method.
	 */
	public boolean hasMethod() {
		return method(CONCRETE) != null || method(ABSTRACT) != null;
	}

	/**
	 * Returns true if this entry contains either an abstract or concrete method.
	 */
	public boolean hasGetter() {
		return getter(CONCRETE) != null || getter(ABSTRACT) != null;
	}

	/**
	 * Returns true if this entry contains either an abstract or concrete method.
	 */
	public boolean hasSetter() {
		return setter(CONCRETE) != null || setter(ABSTRACT) != null;
	}

	/**
	 * Returns true if this entry contains either an abstract or concrete method.
	 */
	public boolean hasField() {
		return field() != null;
	}

	/**
	 * Returns true if entry contains either setter or getter
	 */
	public boolean hasAccessor() {
		return hasGetter() || hasSetter();
	}

	/**
	 * Returns true if entry contains setter and getter
	 */
	public boolean hasAccessorPair() {
		return hasGetter() && hasSetter();
	}

	/**
	 * Returns true if the entry does not contain method and (data) field (accessors).
	 */
	public boolean checkMetaTypeConsistency() {
		if (hasMethod()) {
			return !(hasField() || hasAccessor());
		}
		return true;
	}

	/**
	 * Returns true if this entry contains only abstract members.
	 */
	public boolean isAbstract() {
		return !containsConcrete();
	}

	/**
	 * Returns true if this entry contains only abstract members which are NOT defined in the type itself
	 */
	public boolean isAbstractInherited() {
		for (int i = 0; i < LENGTH; i++) {
			if (members[i] != null) {
				if (!members[i].isAbstract() || sources[i] == MemberSource.OWNED) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Returns true if the entry contains any abstract member.
	 */
	public boolean containsAbstract() {
		return method(ABSTRACT) != null || setter(ABSTRACT) != null || getter(ABSTRACT) != null;
	}

	/**
	 * Returns true if the entry contains any concrete member.
	 */
	public boolean containsConcrete() {
		return method(CONCRETE) != null || setter(CONCRETE) != null || getter(CONCRETE) != null || field() != null;
	}

	/**
	 * Returns true if the entry contains a concrete getter and concrete setter.
	 */
	public boolean hasConcreteAccessorPair() {
		return setter(CONCRETE) != null && getter(CONCRETE) != null;
	}

	/**
	 * Returns true if the entry contains only one concrete accessor.
	 */
	public boolean hasSingleConcreteAccessor() {
		return (setter(CONCRETE) != null) != (getter(CONCRETE) != null);
	}

	/**
	 * Returns the source of the given member.
	 */
	public MemberSource getSource(TMember member) {
		return sources[index(member)];
	}

	@Override
	public String toString() {
		String pre = (_static ? "static " : "") + name + ": {";
		List<String> out = new ArrayList<>(8);
		for (int i = 0; i < 4; i++) {
			TMember m = members[i];
			if (m != null) {
				out.add(m.eClass().getName());
			}
			if (i != FIELD) {
				m = members[i + ABSTRACT_OFFSET];
				if (m != null) {
					out.add("a." + m.eClass().getName());
				}
			}
		}
		return pre + Joiner.on(", ").join(out) + "}";
	}

}
