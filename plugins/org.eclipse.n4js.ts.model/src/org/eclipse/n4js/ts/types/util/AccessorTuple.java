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

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

import java.util.Arrays;

import org.eclipse.n4js.ts.types.FieldAccessor;
import org.eclipse.n4js.ts.types.TGetter;
import org.eclipse.n4js.ts.types.TSetter;

/**
 * Helper structure to hold {@link TGetter}s and {@link TSetter}s tuples. It also holds the inherited accessors, as they
 * may be needed in code generation as it is not possible to split up a getter-setter pair across this and super
 * (proto-) type.
 */
public class AccessorTuple {
	/** name of field accessors and this tuple */
	private final String name;
	/** owned getter, may be null (in this case, setter must not be null) */
	private TGetter getter;
	/** owned setter, may be null (in this case, getter must not be null) */
	private TSetter setter;

	/** inherited setter, may be null (in particular, if setter is not null) */
	private TSetter inheritedSetter;
	/** inherited getter, may be null (in particular, if getter is not null) */
	private TGetter inheritedGetter;
	private final boolean _static;

	/**
	 * Creates tuple with given name. For performance reason name is not compared later, however client has to make sure
	 * that getter and setter names match this name.
	 */
	public AccessorTuple(String name, boolean _static) {
		this.name = name;
		this._static = _static;
	}

	/**
	 * Returns true if the tuple is static.
	 */
	public boolean isStatic() {
		return _static;
	}

	/**
	 * Returns name of getter-setter (or field) tuple.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the owned setter, may be null.
	 */
	public TGetter getGetter() {
		return getter;
	}

	/**
	 * Returns the owned setter, may be null.
	 */
	public TSetter getSetter() {
		return setter;
	}

	/**
	 * Returns the inherited getter, may be null.
	 */
	public TGetter getInheritedGetter() {
		return inheritedGetter;
	}

	/**
	 * Returns the inherited setter, may be null.
	 */
	public TSetter getInheritedSetter() {
		return inheritedSetter;
	}

	/**
	 * Sets getter, must not have been set before.
	 */
	public void setGetter(TGetter getter) {
		if (this.getter != null) {
			throw new IllegalStateException();
		}
		this.getter = getter;
		if (getter != null) {
			this.inheritedGetter = null;
		}
	}

	/**
	 * Sets setter, must not have been set before.
	 */
	public void setSetter(TSetter setter) {
		if (this.setter != null) {
			throw new IllegalStateException();
		}
		this.setter = setter;
		if (setter != null) {
			this.inheritedSetter = null;
		}

	}

	/**
	 * Sets inherited getter, there must be an owned setter set before, but no owned getter.
	 */
	public void setInheritedGetter(TGetter inhGetter) {
		if (this.getter != null) {
			throw new IllegalStateException();
		}
		this.inheritedGetter = inhGetter;
	}

	/**
	 * Sets inherited setter, there must be an owned getter set before, but no owned setter.
	 */
	public void setInheritedSetter(TSetter inhSetter) {
		if (this.setter != null) {
			throw new IllegalStateException();
		}
		this.inheritedSetter = inhSetter;
	}

	/**
	 * Special replace method used in incompleteAPI-tracking of VirtualGetter/Setter pairs for missing
	 * field-implementations.
	 */
	public void replaceGetterByInheretedGetter_caseIncompletAPI(TGetter inhGetter) {
		if (this.getter == null) {
			throw new IllegalStateException();
		}
		if (this.inheritedGetter != null) {
			throw new IllegalStateException();
		}
		if (inhGetter == null) {
			throw new IllegalStateException();
		}
		this.getter = null;
		this.inheritedGetter = inhGetter;
	}

	/**
	 * Special replace method used in incompleteAPI-tracking of VirtualGetter/Setter pairs for missing
	 * field-implementations.
	 */
	public void replaceSetterByInheretedSetter_caseIncompletAPI(TSetter inhSetter) {
		if (this.setter == null) {
			throw new IllegalStateException();
		}
		if (this.inheritedSetter != null) {
			throw new IllegalStateException();
		}
		if (inhSetter == null) {
			throw new IllegalStateException();
		}
		this.setter = null;
		this.inheritedSetter = inhSetter;
	}

	/**
	 * Returns true if getter and setter are present. One of them may be inherited.
	 */
	public boolean hasPair() {
		return (getter != null || inheritedGetter != null) && (setter != null || inheritedSetter != null);
	}

	/**
	 * Returns the getter and setter as iterable, inherited accessors are ignored.
	 */
	public Iterable<FieldAccessor> ownedOrMixedIn() {
		if (getter != null) {
			if (setter != null) {
				return Arrays.asList(getter, setter);
			} else {
				return singletonList(getter);
			}
		} else if (setter != null) {
			return singletonList(setter);
		}
		return emptyList();
	}

	/**
	 * Only for debugging purposes.
	 */
	@Override
	public String toString() {
		StringBuilder strb = new StringBuilder("Accessors ");
		if (isStatic()) {
			strb.append("static ");
		}
		strb.append(this.name);
		strb.append(": ");
		StringBuilder members = new StringBuilder();
		append(members, getter, "");
		append(members, setter, "");
		append(members, inheritedGetter, "*");
		append(members, inheritedSetter, "*");
		strb.append(members);
		return strb.toString();
	}

	/**
	 * Only for debugging purposes.
	 */
	private StringBuilder append(StringBuilder strb, FieldAccessor fa, String prefix) {
		if (fa != null) {
			if (strb.length() > 0) {
				strb.append(",");
			}
			strb.append(prefix);
			if (fa.getContainingType() != null) {
				strb.append(fa.getContainingType().getTypeAsString());
			} else {
				strb.append("?");
			}
			strb.append(".");
			if (fa instanceof TGetter) {
				strb.append("get");
			} else {
				strb.append("set");
			}
		}
		return strb;
	}
}
