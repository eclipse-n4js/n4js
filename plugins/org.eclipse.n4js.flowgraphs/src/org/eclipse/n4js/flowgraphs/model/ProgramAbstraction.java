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

import java.util.HashMap;
import java.util.Map;

import org.eclipse.n4js.n4JS.ControlFlowElement;

public class ProgramAbstraction {
	final public BodyDeclaration bodyDeclaration;
	final public Map<ASTNode, ControlFlowElement> astNodeMap = new HashMap<>();

	public ProgramAbstraction(BodyDeclaration md) {
		this.bodyDeclaration = md;
	}

}
