/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.flowgraphs.dataflow;

import static com.google.common.base.Preconditions.checkState;

import java.util.HashSet;
import java.util.Set;

/**
 */
class AssumptionGroup {
	private final Set<Assumption> copies = new HashSet<>();

	/** Constructor */
	public AssumptionGroup() {
	}

	void add(Assumption assumption) {
		checkState(copies.add(assumption));
	}

	void remove(Assumption assumption) {
		checkState(copies.remove(assumption));
	}

	void assure(Assumption assumption) {
		copies.add(assumption);
	}

	/** @return true iff there are no copies of this {@link AssumptionGroup} on other {@link DataFlowBranchWalker}s */
	boolean noCopies() {
		return copies.size() == 1;
	}

	@Override
	public String toString() {
		return "GroupSize:" + copies.size();
	}

}
