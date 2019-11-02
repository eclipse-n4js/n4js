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

import java.util.LinkedList;
import java.util.List;

import org.eclipse.n4js.flowgraphs.ControlFlowType;
import org.eclipse.n4js.flowgraphs.model.CatchToken;
import org.eclipse.n4js.flowgraphs.model.ComplexNode;
import org.eclipse.n4js.flowgraphs.model.HelperNode;
import org.eclipse.n4js.flowgraphs.model.JumpToken;
import org.eclipse.n4js.flowgraphs.model.Node;
import org.eclipse.n4js.flowgraphs.model.RepresentingNode;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.ExpressionWithTarget;
import org.eclipse.n4js.n4JS.N4JSPackage;

/**
 * Used for {@link ExpressionWithTarget}. Children nodes are retrieved from
 * {@link CFEChildren#get(ReentrantASTIterator, ControlFlowElement)} and do not include the target expression node.
 * <p/>
 * <b>Attention:</b> The order of {@link Node#astPosition}s is important, and thus the order of Node instantiation! In
 * case this order is inconsistent to {@link OrderedEContentProvider}, the assertion with the message
 * {@link ReentrantASTIterator#ASSERTION_MSG_AST_ORDER} is thrown.
 */
class ExpressionWithTargetFactory {

	static ComplexNode buildComplexNode(ReentrantASTIterator astpp, ExpressionWithTarget cfe) {
		ComplexNode cNode = new ComplexNode(astpp.container(), cfe);
		HelperNode entryNode = new HelperNode(NodeNames.ENTRY, astpp.pos(), cfe);
		Node targetNode = DelegatingNodeFactory.create(astpp, "target", cfe, cfe.getTarget());

		cNode.addNode(entryNode);
		cNode.addNode(targetNode);

		List<Node> nodes = new LinkedList<>();
		nodes.add(entryNode);
		nodes.add(targetNode);

		if (cfe.isOptionalChaining()) {
			HelperNode forkNode = new HelperNode(NodeNames.OPTIONAL_CHAINING_FORK, astpp.pos(), cfe.getTarget());
			cNode.addNode(forkNode);
			nodes.add(forkNode);
			forkNode.addJumpToken(new JumpToken(ControlFlowType.IfNullishTarget));
			cNode.setJumpNode(forkNode);
		}

		List<Node> argumentNodes = CFEChildren.get(astpp, cfe);
		Node representing = new RepresentingNode(NodeNames.EXPRESSION, astpp.pos(), cfe);
		Node exitNode = new HelperNode(NodeNames.EXIT, astpp.pos(), cfe);

		for (Node arg : argumentNodes)
			cNode.addNode(arg);
		cNode.addNode(representing);
		cNode.addNode(exitNode);

		nodes.addAll(argumentNodes);
		nodes.add(representing);
		nodes.add(exitNode);
		cNode.connectInternalSucc(nodes);

		cNode.setEntryNode(entryNode);
		cNode.setExitNode(exitNode);

		if (cfe.eContainingFeature() != N4JSPackage.Literals.EXPRESSION_WITH_TARGET__TARGET) {
			exitNode.addCatchToken(new CatchToken(ControlFlowType.IfNullishTarget));
		}
		return cNode;
	}

}
