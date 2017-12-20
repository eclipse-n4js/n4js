/**
 * Copyright (c) 2017 Marcus Mews.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Marcus Mews - Initial API and implementation
 */
package org.eclipse.n4js.flowgraphs.model;

import java.util.Objects;

import org.eclipse.n4js.flowgraphs.ControlFlowType;
import org.eclipse.n4js.n4JS.LabelledStatement;

/**
 * Represents the ability to jump at a {@link Node}, which can be added to {@link Node}s. The {@link JumpToken} can be
 * specialized by giving a specific Object as an identifier.
 */
public class JumpToken implements Comparable<JumpToken> {
	/** Specifies the control flow type that invokes the jump */
	final public ControlFlowType cfType;
	/** Specifies an identifier, such as a label in a {@link LabelledStatement}. */
	final public LabelledStatement lblStmt;

	final private int cachedHash;

	/**
	 * Constructor.<br/>
	 * Jumps due to the given {@link ControlFlowType}.
	 */
	public JumpToken(ControlFlowType type) {
		this(type, null);
	}

	/**
	 * Constructor.<br/>
	 * Jumps due to the given {@link ControlFlowType} with a specific jump identifier.
	 */
	public JumpToken(ControlFlowType type, LabelledStatement lblStmt) {
		this.cfType = type;
		this.lblStmt = lblStmt;
		this.cachedHash = Objects.hash(cfType, lblStmt);
	}

	@Override
	public int compareTo(JumpToken jumpToken) {
		int result = cfType.compareTo(jumpToken.cfType);
		if (result == 0) {
			if (lblStmt != null && jumpToken.lblStmt != null) {
				return lblStmt.hashCode() - jumpToken.lblStmt.hashCode();
			}
			if (lblStmt != null) {
				return -1;
			}
			if (jumpToken.lblStmt != null) {
				return 1;
			}
		}
		return result;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof JumpToken))
			return false;
		JumpToken jt = (JumpToken) o;

		boolean equals = true;
		equals &= cfType == jt.cfType;
		equals &= lblStmt == jt.lblStmt;
		return equals;
	}

	@Override
	public int hashCode() {
		return cachedHash;
	}

	@Override
	public String toString() {
		String s = cfType.name();
		if (lblStmt != null)
			s += " " + lblStmt;
		return s;
	}

}
