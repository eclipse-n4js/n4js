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
package org.eclipse.n4js.flowgraphs;

import org.eclipse.n4js.flowgraphs.model.ComplexNode;
import org.eclipse.n4js.n4JS.ControlFlowElement;

/**
 * Interface that provides the method {@link #getComplexNode(ControlFlowElement)}.
 */
public interface ComplexNodeProvider {

	/** Returns the {@link ComplexNode} that represents the {@link ControlFlowElement} in the flow graph */
	public ComplexNode getComplexNode(ControlFlowElement cfe);

}
