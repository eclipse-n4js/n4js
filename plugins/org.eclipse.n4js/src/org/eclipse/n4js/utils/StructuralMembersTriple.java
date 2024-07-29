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

import org.eclipse.n4js.ts.types.FieldAccessor;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Triple of structural members. Encapsulates a left and a right {@link TMember member} and additionally a
 * {@link FieldAccessor field accessor} for the right hand side. All members could be {@code null}. Its the client's
 * responsibility to check whether the members can be referenced or not.
 */
public class StructuralMembersTriple {

	private final TMember left;

	private final TMember right;

	private final FieldAccessor leftOtherAccessor;

	/***/
	public StructuralMembersTriple(final TMember left, final TMember right, final FieldAccessor leftOtherAccessor) {
		super();
		this.left = left;
		this.right = right;
		this.leftOtherAccessor = leftOtherAccessor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.left == null) ? 0 : this.left.hashCode());
		result = prime * result + ((this.right == null) ? 0 : this.right.hashCode());
		return prime * result + ((this.leftOtherAccessor == null) ? 0 : this.leftOtherAccessor.hashCode());
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StructuralMembersTriple other = (StructuralMembersTriple) obj;
		if (this.left == null) {
			if (other.left != null)
				return false;
		} else if (!this.left.equals(other.left))
			return false;
		if (this.right == null) {
			if (other.right != null)
				return false;
		} else if (!this.right.equals(other.right))
			return false;
		if (this.leftOtherAccessor == null) {
			if (other.leftOtherAccessor != null)
				return false;
		} else if (!this.leftOtherAccessor.equals(other.leftOtherAccessor))
			return false;
		return true;
	}

	@Override
	public String toString() {
		ToStringBuilder b = new ToStringBuilder(this);
		b.add("left", this.left);
		b.add("right", this.right);
		b.add("leftOtherAccessor", this.leftOtherAccessor);
		return b.toString();
	}

	/***/
	public TMember getLeft() {
		return this.left;
	}

	/***/
	public TMember getRight() {
		return this.right;
	}

	/***/
	public FieldAccessor getLeftOtherAccessor() {
		return this.leftOtherAccessor;
	}
}
