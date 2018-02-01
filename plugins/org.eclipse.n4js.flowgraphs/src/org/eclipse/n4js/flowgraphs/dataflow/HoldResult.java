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
 *
 */
public class HoldResult {
	static public final MayHold MayHold = new MayHold();
	static public final Passed Passed = new Passed();

	public final FlowAssertion type;
	public final GuardType expectation;
	public final String details;
	public final Symbol symbol;

	HoldResult(FlowAssertion type, GuardType expectation, Symbol symbol, String details) {
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
		return type.name() + expected + symbolName + detail;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof HoldResult)) {
			return false;
		}
		HoldResult result = (HoldResult) obj;
		return type == result.type && expectation == result.expectation;
	}

	@Override
	public int hashCode() {
		if (expectation != null) {
			return Objects.hash(type, expectation);
		}
		return Objects.hash(type);
	}

	static private class MayHold extends HoldResult {
		MayHold() {
			super(FlowAssertion.MayHold, null, null, null);
		}
	}

	static public class Passed extends HoldResult {
		public Passed() {
			super(FlowAssertion.AlwaysHolds, null, null, null);
		}
	}

	static public class Failed extends HoldResult {
		public Failed(GuardType expectation) {
			super(FlowAssertion.NeverHolds, expectation, null, null);
		}

		public Failed(GuardType expectation, Symbol symbol) {
			super(FlowAssertion.NeverHolds, expectation, symbol, null);
		}

		public Failed(GuardType expectation, String details) {
			super(FlowAssertion.NeverHolds, expectation, null, details);
		}
	}

}
