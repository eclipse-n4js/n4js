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
package org.eclipse.n4js.flowgraphs.analyses;

import org.eclipse.n4js.flowgraphs.model.Symbol;

/**
 * {@link AssumptionWithContext} are split at each control flow fork, and they are merged again at each control flow
 * join. When a {@link AssumptionWithContext} is deactivated (by returning {@code false} in {@link #isActive()}, they
 * will be merged will all other deactivated {@link AssumptionWithContext} of their kind.
 */
abstract public class AssumptionWithContext extends Assumption {

	/** Constructor */
	public AssumptionWithContext(Symbol symbol) {
		super(symbol);
	}

	@Override
	final public boolean isCopyNecessary() {
		return true;
	}

	@Override
	abstract public Assumption copy();

	@Override
	abstract public void mergeWith(Assumption assumption);

}
