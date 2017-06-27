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
package org.eclipse.n4js.ts.types;

/**
 * Complex key to identify members not only by name but also by their accessor type. So with this key getters and
 * setters can be distinguished.
 */
public class NameAndAccess {
	private final String name;
	private final boolean writeAccess;
	private final boolean staticAccess;

	/**
	 * Returns NameAndAccess key(s) for given member, representing the one or two valid ways to access the given member.
	 * Fields can be both readable and writeable, so in case of fields this method may return two instances of
	 * {@link NameAndAccess}.
	 * <p>
	 * Will always return an array of size 1 or 2 with non-null entries.
	 */
	public static NameAndAccess[] of(TMember member) {
		if (member.isReadable() && member.isWriteable()) {
			return new NameAndAccess[] {
					new NameAndAccess(member.getName(), false, member.isStatic()),
					new NameAndAccess(member.getName(), true, member.isStatic())
			};
		}
		else {
			return new NameAndAccess[] {
					new NameAndAccess(member.getName(), member.isWriteable(), member.isStatic())
			};
		}
	}

	/**
	 * Creates new name and access key, instead of calling this constructor you may want to use {@link #of(TMember)} for
	 * members.
	 */
	public NameAndAccess(String name, boolean writeAccess, boolean staticAccess) {
		this.name = name;
		this.writeAccess = writeAccess;
		this.staticAccess = staticAccess;
	}

	@SuppressWarnings("javadoc")
	public String getName() {
		return name;
	}

	@SuppressWarnings("javadoc")
	public boolean isWriteAccess() {
		return writeAccess;
	}

	@SuppressWarnings("javadoc")
	public boolean isStaticAccess() {
		return staticAccess;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + (staticAccess ? 1231 : 1237);
		result = prime * result + (writeAccess ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NameAndAccess other = (NameAndAccess) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (staticAccess != other.staticAccess)
			return false;
		if (writeAccess != other.writeAccess)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "NameAndAccess [name=" + name + ", writeAccess=" + writeAccess + ", staticAccess=" + staticAccess + "]";
	}
}
