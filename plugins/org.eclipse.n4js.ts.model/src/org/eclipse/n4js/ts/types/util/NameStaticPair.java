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

import org.eclipse.n4js.ts.types.TMember;

/**
 * Pair used in maps to identify members by name and static modifier. This type does not differentiate between methods,
 * fields or accessors; so you may want to use {@link NonSymetricMemberKey} instead.
 */
public class NameStaticPair {
	/**
	 * Name of member
	 */
	public final String name;
	/**
	 * Static modifier of member
	 */
	public final boolean isStatic;

	/**
	 * Short version of constructor with member
	 */
	public static NameStaticPair of(TMember member) {
		return new NameStaticPair(member);
	}

	/**
	 * Creates a name/static pair from a given member.
	 */
	public NameStaticPair(TMember member) {
		this(member.getName(), member.isStatic());
	}

	/**
	 * Creates a name/static pair.
	 */
	public NameStaticPair(String name, boolean isStatic) {
		if (name == null) {
			throw new IllegalArgumentException("name may not be null");
		}
		this.name = name;
		this.isStatic = isStatic;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof NameStaticPair) {
			NameStaticPair nsp = (NameStaticPair) obj;
			return nsp.isStatic == isStatic && nsp.name.equals(name);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return (isStatic ? 31 : 0) + name.hashCode();
	}

	@Override
	public String toString() {
		if (isStatic) {
			return "static " + name;
		}
		return name;
	}

}
