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
package org.eclipse.n4js.ts.types.util;

import org.eclipse.n4js.ts.types.MemberType;
import org.eclipse.n4js.ts.types.TMember;

/**
 * Key used to sort members so that methods, fields and accessors exclude each other, but that it is possible to add
 * getters and setters.
 *
 * The key is not symmetric. That is, two keys are equal, iff both have same name and static modifier, and if they are
 * <em>not</em> an accessor pair.
 */
public class NonSymetricMemberKey {
	/**
	 * Name of member
	 */
	public final String name;
	/**
	 * Static modifier of member
	 */
	public final boolean isStatic;

	/**
	 * The type of the member.
	 */
	public final MemberType memberType;

	/**
	 * Short version of constructor with member
	 */
	public static NonSymetricMemberKey of(TMember member) {
		return new NonSymetricMemberKey(member);
	}

	/**
	 * Creates a name/static pair from a given member.
	 */
	public NonSymetricMemberKey(TMember member) {
		this(member.getName(), member.isStatic(), member.getMemberType());
	}

	/**
	 * Creates a member key.
	 */
	public NonSymetricMemberKey(String name, boolean isStatic, MemberType memberType) {
		this.name = name;
		this.isStatic = isStatic;
		this.memberType = memberType;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof NonSymetricMemberKey) {
			NonSymetricMemberKey key = (NonSymetricMemberKey) obj;
			if (key.isStatic == isStatic && key.name.equals(name)) {
				return !accessorPair(memberType, key.memberType);
			}
		}
		return false;
	}

	private boolean accessorPair(MemberType mt1, MemberType mt2) {
		return (mt1 == MemberType.GETTER && mt2 == MemberType.SETTER)
				|| (mt1 == MemberType.SETTER && mt2 == MemberType.GETTER);
	}

	@Override
	public int hashCode() {
		return (isStatic ? 31 : 0) + name.hashCode();
	}

	@Override
	public String toString() {
		if (isStatic) {
			return "static " + name + " " + memberType;
		}
		return name;
	}
}
