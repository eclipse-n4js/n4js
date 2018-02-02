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

/**
 * {@link PartialResult}s are returned from client side methods of {@link Assumption}s. {@link PartialResult} state if
 * certain data-flows, data effects or guaranteed guards can make the {@link Assumption} pass or fail. If an
 * {@link Assumption} neither passes nor fails, its {@link PartialResult} is {@link Unclear}.
 */
public class PartialResult {
	/** Singleton instance */
	static public final Unclear Unclear = new Unclear();
	/** Singleton instance */
	static public final Passed Passed = new Passed();

	/** Type of the result */
	public final Type type;
	/** Expectation. Only used iff the type is {@link Failed} */
	public final GuardType expectation;
	/** Details, e.g. in case the expectation in not detailed enough. Only used iff the type is {@link Failed} */
	public final String details;
	/** Symbol that did not fulfill the expectation. Only used iff the type is {@link Failed} */
	public final Symbol symbol;

	/** Type of the result. Introduced for convenience. Analog to the subclasses of {@link PartialResult}. */
	@SuppressWarnings("hiding")
	static public enum Type {
		/** Used only in classes of type {@link Passed} */
		Passed,
		/** Used only in classes of type {@link Failed} */
		Failed,
		/** Used only in classes of type {@link Unclear} */
		Unclear
	}

	PartialResult(Type type, GuardType expectation, Symbol symbol, String details) {
		this.type = type;
		this.expectation = expectation;
		this.symbol = symbol;
		this.details = details;
	}

	@Override
	public String toString() {
		String symbolName = (symbol != null) ? " " + symbol.getName() : "";
		String detail = (details != null) ? " " + details : "";
		String expected = (expectation != null) ? " " + expectation.toString() : "";
		return type + expected + symbolName + detail;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof PartialResult)) {
			return false;
		}
		PartialResult result = (PartialResult) obj;
		return type == result.type && expectation == result.expectation;
	}

	@Override
	public int hashCode() {
		if (expectation != null) {
			return Objects.hash(getClass(), expectation);
		}
		return Objects.hash(getClass());
	}

	static private class Unclear extends PartialResult {
		Unclear() {
			super(Type.Unclear, null, null, null);
		}
	}

	static private class Passed extends PartialResult {
		public Passed() {
			super(Type.Passed, null, null, null);
		}
	}

	/** Subclass to provide convenient constructors for failed {@link PartialResult}s */
	static public class Failed extends PartialResult {
		/** Constructor. */
		public Failed(GuardType expectation) {
			super(Type.Failed, expectation, null, null);
		}

		/** Constructor. */
		public Failed(GuardType expectation, Symbol symbol) {
			super(Type.Failed, expectation, symbol, null);
		}

		/** Constructor. */
		public Failed(GuardType expectation, String details) {
			super(Type.Failed, expectation, null, details);
		}
	}

}
