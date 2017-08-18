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
package org.eclipse.n4js.flowgraphs.factories;

import org.eclipse.n4js.flowgraphs.model.ComplexNode;
import org.eclipse.n4js.flowgraphs.model.HelperNode;
import org.eclipse.n4js.flowgraphs.model.Node;
import org.eclipse.n4js.n4JS.Statement;

class EmptyStatementFactory {

	static ComplexNode buildComplexNode(Statement empty) {
		ComplexNode cNode = new ComplexNode(empty);

		Node entryAndExitNode = new HelperNode("entryAndExit", empty);

		cNode.addNode(entryAndExitNode);

		cNode.setEntryNode(entryAndExitNode);
		cNode.setExitNode(entryAndExitNode);

		return cNode;
	}

}
