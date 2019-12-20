/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.flowgraphs.dataflow;

import java.util.Objects;

import com.google.common.base.Preconditions;

/**
 * {@link PartialResult}s are returned from client side methods of {@link Assumption}s. {@link PartialResult} state if
 * certain data-flows, data effects or guaranteed guards can make the {@link Assumption} pass or fail. If an
 * {@link Assumption} neither passes nor fails, its {@link PartialResult} is {@link Unclear}.
 */
abstract public class PartialResult {
	/** Singleton instance */
	static public final Unclear Unclear = new Unclear();
	/** Singleton instance */
	static public final Passed Passed = new Passed();

	/** Type of the result */
	public final Type type;

	/** Type of the result. Introduced for convenience. Analog to the subclasses of {@link PartialResult}. */
	@SuppressWarnings("hiding")
	static public enum Type {
		/** Used only in classes of type {@link Passed} */
		Passed,
		/** Used only in classes of type {@link Failed} */
		Failed,
		/**
		 * Used only in classes of type {@link Failed}. Tells that there is obvious, yet uncertain reason for one branch
		 * to fail, e.g. regarding property access of x.length in {@code let x = a?.b; x.length}.
		 */
		MayFailed,
		/** Used only in classes of type {@link Unclear} */
		Unclear
	}

	PartialResult(Type type) {
		this.type = type;
	}

	/** Overwrite this method in case your subclass provides properties to distinguish the result in detail */
	public Object[] getEqualityProperties() {
		return new Object[0];
	}

	@Override
	public String toString() {
		return type.name();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Failed)) {
			return false;
		}
		Failed failed = (Failed) obj;
		if (this.getClass().equals(obj.getClass())) {
			Object[] ep1 = getEqualityProperties();
			Object[] ep2 = failed.getEqualityProperties();

			boolean equals = type == failed.type;
			for (int i = 0; i < ep1.length; i++) {
				equals &= ep1[i] == ep2[i];
			}
			return equals;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return type.hashCode() + Objects.hash(getEqualityProperties());
	}

	static private class Unclear extends PartialResult {
		Unclear() {
			super(Type.Unclear);
		}
	}

	static private class Passed extends PartialResult {
		public Passed() {
			super(Type.Passed);
		}
	}

	/** Overwrite this class when the failed result has to be augmented with further data. */
	static public class Failed extends PartialResult {
		/** Constructor */
		public Failed() {
			this(Type.Failed);
		}

		/** Constructor */
		public Failed(Type failType) {
			super(failType);
			Preconditions.checkArgument(failType == Type.Failed || failType == Type.MayFailed);
		}
	}

}
