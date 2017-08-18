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
package org.eclipse.n4js.flowgraphs.factories;

import org.eclipse.n4js.flowgraphs.ComplexNodeProvider;
import org.eclipse.n4js.flowgraphs.model.CatchToken;
import org.eclipse.n4js.flowgraphs.model.ComplexNode;
import org.eclipse.n4js.flowgraphs.model.JumpToken;
import org.eclipse.n4js.flowgraphs.model.Node;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.DoStatement;
import org.eclipse.n4js.n4JS.ForStatement;
import org.eclipse.n4js.n4JS.FunctionDeclaration;
import org.eclipse.n4js.n4JS.SwitchStatement;
import org.eclipse.n4js.n4JS.TryStatement;
import org.eclipse.n4js.n4JS.WhileStatement;

/**
 *
 */
public class CatchNodeFinder {

	static Node find(JumpToken jumpToken, Node jumpNode) {

		return null;
	}

	static private interface CatchEvaluator {
		boolean isCatchingType(ControlFlowElement cfe);

		/**
		 * Result is valid only if {@link #isCatchingType(ControlFlowElement)} returns true on the same <code>cfe</code>
		 */
		Node getCatchingNode(ControlFlowElement cfe, ComplexNodeProvider cnProvider);

		boolean isCatchingToken(ControlFlowElement cfe, JumpToken jumpToken, CatchToken catchToken);

	}

	/**
	 * Provides means to (1) evaluate if an element can catch a {@link JumpToken} of type {@literal JumpType.Break}, and
	 * (2) to retrieve the catching node.
	 */
	static private class BreakCatchEvaluator implements CatchEvaluator {

		@Override
		public boolean isCatchingType(ControlFlowElement cfe) {
			boolean isCatchingBreakStatement = false;
			isCatchingBreakStatement |= cfe instanceof DoStatement;
			isCatchingBreakStatement |= cfe instanceof ForStatement;
			isCatchingBreakStatement |= cfe instanceof SwitchStatement;
			isCatchingBreakStatement |= cfe instanceof WhileStatement;
			return isCatchingBreakStatement;
		}

		@Override
		public Node getCatchingNode(ControlFlowElement cfe, ComplexNodeProvider cnProvider) {
			ComplexNode cn = cnProvider.getComplexNode(cfe);
			return cn.getExit();
		}

		@Override
		public boolean isCatchingToken(ControlFlowElement cfe, JumpToken jumpToken, CatchToken catchToken) {
			if (jumpToken.id == null && catchToken.id == null) {
				return true;
			}
			boolean isCatchingToken = jumpToken.id.equals(catchToken.id);
			return isCatchingToken;
		}
	}

	/**
	 * Provides means to (1) evaluate if an element can catch a {@link JumpToken} of type {@literal JumpType.Continue},
	 * and (2) to retrieve the catching node.
	 */
	static private class ContinueCatchEvaluator implements CatchEvaluator {

		@Override
		public boolean isCatchingType(ControlFlowElement cfe) {
			boolean isCatchingContinueStatement = false;
			isCatchingContinueStatement |= cfe instanceof DoStatement;
			isCatchingContinueStatement |= cfe instanceof ForStatement;
			isCatchingContinueStatement |= cfe instanceof WhileStatement;
			return isCatchingContinueStatement;
		}

		@Override
		public Node getCatchingNode(ControlFlowElement cfe, ComplexNodeProvider cnProvider) {
			if (cfe instanceof DoStatement) {
				ComplexNode cn = cnProvider.getComplexNode(cfe);
				Node conditionNode = cn.getNode(DoWhileFactory.CONDITION_NODE_NAME);
				return conditionNode.getEntry();
			}
			if (cfe instanceof ForStatement) {
				ComplexNode cn = cnProvider.getComplexNode(cfe);
				Node conditionNode = cn.getNode(ForFactory.CONDITION_NODE_NAME);
				return conditionNode.getEntry();
			}
			if (cfe instanceof WhileStatement) {
				ComplexNode cn = cnProvider.getComplexNode(cfe);
				Node conditionNode = cn.getNode(WhileFactory.CONDITION_NODE_NAME);
				return conditionNode.getEntry();
			}
			throw new IllegalStateException("Cannot happen");
		}

		@Override
		public boolean isCatchingToken(ControlFlowElement cfe, JumpToken jumpToken, CatchToken catchToken) {
			if (jumpToken.id == null && catchToken.id == null) {
				return true;
			}
			boolean isCatchingToken = jumpToken.id.equals(catchToken.id);
			return isCatchingToken;
		}
	}

	static boolean isCatchingReturnStatement(ControlFlowElement cfe) {
		boolean isCatchingReturnStatement = false;
		isCatchingReturnStatement |= cfe instanceof FunctionDeclaration;
		return isCatchingReturnStatement;
	}

	static boolean isCatchingThrowStatement(ControlFlowElement cfe) {
		boolean isCatchingThrowStatement = false;
		isCatchingThrowStatement |= cfe instanceof TryStatement;
		return isCatchingThrowStatement;
	}
}
