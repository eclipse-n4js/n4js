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

/**
 *
 */
public class GuardResultWithReason {
	static public final MayHold MayHold = new MayHold();

	public final HoldAssertion result;
	public final GuardType expectation;
	public final String details;

	GuardResultWithReason(HoldAssertion result, GuardType expectation, String details) {
		this.result = result;
		this.expectation = expectation;
		this.details = details;
	}

	static private class MayHold extends GuardResultWithReason {
		MayHold() {
			super(HoldAssertion.MayHold, null, null);
		}
	}

	static public class Passed extends GuardResultWithReason {
		public Passed() {
			super(HoldAssertion.AlwaysHolds, null, null);
		}
	}

	static public class Failed extends GuardResultWithReason {
		public Failed(GuardType expectation) {
			super(HoldAssertion.NeverHolds, expectation, null);
		}

		public Failed(GuardType expectation, String details) {
			super(HoldAssertion.NeverHolds, expectation, details);
		}
	}
}
