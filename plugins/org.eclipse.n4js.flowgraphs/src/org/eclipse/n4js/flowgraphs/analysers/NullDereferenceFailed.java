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
package org.eclipse.n4js.flowgraphs.analysers;

import org.eclipse.n4js.flowgraphs.dataflow.PartialResult;
import org.eclipse.n4js.flowgraphs.dataflow.guards.GuardType;
import org.eclipse.n4js.flowgraphs.dataflow.symbols.Symbol;

/**
 * This class augments {@link org.eclipse.n4js.flowgraphs.dataflow.PartialResult.Failed} with additional information
 * about the reason for failing.
 */
public class NullDereferenceFailed extends PartialResult.Failed {
	/** Expectation that was not fulfilled */
	public final GuardType expectation;
	/** Symbol that did not fulfill the expectation. */
	public final Symbol symbol;

	NullDereferenceFailed(Type failType, GuardType expectation, Symbol symbol) {
		super(failType);
		this.expectation = expectation;
		this.symbol = symbol;
	}

	NullDereferenceFailed(GuardType expectation, Symbol symbol) {
		this(Type.Failed, expectation, symbol);
	}

	/** Constructor. */
	public NullDereferenceFailed(GuardType expectation) {
		this(Type.Failed, expectation, null);
	}

	@Override
	public String toString() {
		String symbolName = (symbol != null) ? " " + symbol.getName() : "";
		String expected = (expectation != null) ? " " + expectation.toString() : "";
		return type + expected + symbolName;
	}

	@Override
	public Object[] getEqualityProperties() {
		return new Object[] { expectation };
	}

}
