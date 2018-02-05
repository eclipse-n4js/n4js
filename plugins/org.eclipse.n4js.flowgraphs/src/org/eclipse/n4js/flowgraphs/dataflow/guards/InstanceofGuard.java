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
package org.eclipse.n4js.flowgraphs.dataflow.guards;

import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.IdentifierRef;

/**
 * Subclass of {@link Guard} that hold context information, such as {@link #typeIdentifier}.
 */
public class InstanceofGuard extends Guard {
	/** {@link IdentifierRef} to the type of the type guard */
	final public Expression typeIdentifier;

	/** Constructor */
	public InstanceofGuard(Expression condition, GuardAssertion asserts, ControlFlowElement symbolCFE,
			Expression typeIdentifier) {

		super(condition, GuardType.InstanceOf, asserts, symbolCFE);
		this.typeIdentifier = typeIdentifier;
	}

}
