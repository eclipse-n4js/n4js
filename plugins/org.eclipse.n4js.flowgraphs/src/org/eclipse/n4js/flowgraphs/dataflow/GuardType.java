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

import org.eclipse.n4js.n4JS.Expression;

/**
 * Type of a guard.
 */
public enum GuardType {
	/**
	 * For {@link Expression}s that check for null:<br/>
	 * {@code if (a == null)}
	 */
	IsNull,
	/**
	 * For {@link Expression}s that check for undefined:<br/>
	 * {@code if (a == undefined || a == void 0 || typeof a == "undefined")}
	 */
	IsUndefined,
	/**
	 * For {@link Expression}s that check for truthy:<br/>
	 * {@code if (a)}
	 */
	IsTruthy,
	/**
	 * For {@link Expression}s that check for 0:<br/>
	 * {@code if (a == 0)}
	 */
	IsZero,
	/**
	 * For {@link Expression}s that check for a type:<br/>
	 * {@code if (a instanceof AType)}
	 */
	InstanceOf,
	/**
	 * For {@link Expression}s that check for state:<br/>
	 * {@code if (a.inState())}
	 */
	InState;

	/** @return true iff this is {@link #IsNull} or {@link #IsUndefined} */
	public boolean IsNullOrUndefined() {
		return this == IsNull || this == IsUndefined;
	}
}
