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

import org.eclipse.n4js.flowgraphs.ControlFlowType;
import org.eclipse.n4js.n4JS.LabelledStatement;

/**
 * Represents the ability to catch {@link JumpToken}s of and can be added to {@link Node}s. The {@link CatchToken} can
 * be specialized by giving a specific Object as an identifier.
 */
public class CatchToken extends JumpToken {
	/** The edge type of the edge that will jump from the jump to the catch node */
	public final ControlFlowType newEdgeType;

	/**
	 * Constructor.<br/>
	 * Catches the given {@link ControlFlowType}.
	 */
	public CatchToken(ControlFlowType type) {
		this(type, type, null);
	}

	/**
	 * Constructor.<br/>
	 * Catches the given {@link ControlFlowType} and creates edges of the given type.
	 */
	public CatchToken(ControlFlowType type, ControlFlowType newEdgeType) {
		this(type, newEdgeType, null);
	}

	/**
	 * Constructor.<br/>
	 * Catches the given {@link ControlFlowType} iff the given id matches the id from the {@link JumpToken}.
	 */
	public CatchToken(ControlFlowType type, LabelledStatement lblStmt) {
		this(type, type, lblStmt);
	}

	private CatchToken(ControlFlowType type, ControlFlowType newEdgeType, LabelledStatement lblStmt) {
		super(type, lblStmt);
		this.newEdgeType = newEdgeType;
	}

}
