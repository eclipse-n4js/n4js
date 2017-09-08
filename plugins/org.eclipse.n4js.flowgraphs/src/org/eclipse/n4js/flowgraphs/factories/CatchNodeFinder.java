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

import java.util.Objects;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.flowgraphs.ControlFlowType;
import org.eclipse.n4js.flowgraphs.FGUtils;
import org.eclipse.n4js.flowgraphs.model.CatchToken;
import org.eclipse.n4js.flowgraphs.model.ComplexNode;
import org.eclipse.n4js.flowgraphs.model.JumpToken;
import org.eclipse.n4js.flowgraphs.model.Node;
import org.eclipse.n4js.n4JS.Block;
import org.eclipse.n4js.n4JS.CatchBlock;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.DoStatement;
import org.eclipse.n4js.n4JS.FinallyBlock;
import org.eclipse.n4js.n4JS.ForStatement;
import org.eclipse.n4js.n4JS.SwitchStatement;
import org.eclipse.n4js.n4JS.TryStatement;
import org.eclipse.n4js.n4JS.WhileStatement;

/**
 *
 */
public class CatchNodeFinder {
	static private final CatchEvaluator catchBreakEvaluator = new CatchBreakEvaluator();
	static private final CatchEvaluator catchContinueEvluator = new CatchContinueEvaluator();
	static private final CatchEvaluator catchReturnEvaluator = new CatchReturnEvaluator();
	static private final CatchEvaluator catchThrowEvaluator = new CatchThrowEvaluator();

	static Node find(JumpToken jumpToken, Node jumpNode, ComplexNodeProvider cnProvider) {
		ControlFlowElement cfe = jumpNode.getControlFlowElement();
		CatchEvaluator catchEvaluator = getCatchEvaluator(jumpToken);
		while (cfe != null) {
			Node catchNode = findCatchNode(jumpToken, cfe, catchEvaluator, cnProvider);
			if (catchNode != null)
				return catchNode;

			cfe = getContainer(cfe);
		}

		return null;
	}

	private static ControlFlowElement getContainer(ControlFlowElement cfe) {
		EObject container = cfe.eContainer();
		if (container instanceof ControlFlowElement) {
			return (ControlFlowElement) container;
		}
		boolean getNextContainer = false;
		getNextContainer |= container instanceof CatchBlock;
		getNextContainer |= container instanceof FinallyBlock;
		if (getNextContainer) {
			return (ControlFlowElement) container.eContainer();
		}
		return null;
	}

	private static CatchEvaluator getCatchEvaluator(JumpToken jumpToken) {
		switch (jumpToken.cfType) {
		case Break:
			return catchBreakEvaluator;
		case Continue:
			return catchContinueEvluator;
		case Return:
			return catchReturnEvaluator;
		case Throw:
			return catchThrowEvaluator;
		default:
			throw new IllegalArgumentException("not implemented");
		}
	}

	private static Node findCatchNode(JumpToken jumpToken, ControlFlowElement cfe,
			CatchEvaluator catchEvaluator, ComplexNodeProvider cnProvider) {

		Node catchNode = findCatchAllNode(cfe, cnProvider);
		if (catchNode != null)
			return catchNode;

		if (catchEvaluator.isCatchingType(cfe)) {
			catchNode = catchEvaluator.getCatchingNode(cfe, cnProvider);
			for (CatchToken catchToken : catchNode.catchToken) {
				if (catchEvaluator.isCatchingToken(cfe, jumpToken, catchToken))
					return catchNode;
			}
		}
		return null;
	}

	private static Node findCatchAllNode(ControlFlowElement cfe, ComplexNodeProvider cnProvider) {
		ComplexNode cnCFE = cnProvider.get(cfe);
		for (Node node : cnCFE.getNodes()) {
			for (CatchToken cToken : node.catchToken) {
				if (cToken.cfType == ControlFlowType.CatchesAll) {
					return node;
				}
			}
		}
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
	static private class CatchBreakEvaluator implements CatchEvaluator {

		@Override
		public boolean isCatchingType(ControlFlowElement cfe) {
			boolean isCatchingBreakStatement = false;
			isCatchingBreakStatement |= cfe instanceof DoStatement;
			isCatchingBreakStatement |= cfe instanceof ForStatement;
			isCatchingBreakStatement |= cfe instanceof WhileStatement;
			isCatchingBreakStatement |= cfe instanceof SwitchStatement;
			return isCatchingBreakStatement;
		}

		@Override
		public Node getCatchingNode(ControlFlowElement cfe, ComplexNodeProvider cnProvider) {
			ComplexNode cn = cnProvider.get(cfe);
			return cn.getExit();
		}

		@Override
		public boolean isCatchingToken(ControlFlowElement cfe, JumpToken jumpToken, CatchToken catchToken) {
			boolean isCatchingToken = false;
			isCatchingToken |= jumpToken.lblStmt != null && jumpToken.lblStmt == catchToken.lblStmt;
			isCatchingToken |= jumpToken.lblStmt == null;
			return isCatchingToken;
		}
	}

	/**
	 * Provides means to (1) evaluate if an element can catch a {@link JumpToken} of type {@literal JumpType.Continue},
	 * and (2) to retrieve the catching node.
	 */
	static private class CatchContinueEvaluator implements CatchEvaluator {

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
				ComplexNode cn = cnProvider.get(cfe);
				Node conditionNode = cn.getNode(DoWhileFactory.CONDITION_NODE_NAME);
				return conditionNode.getEntry();
			}
			if (cfe instanceof ForStatement) {
				ComplexNode cn = cnProvider.get(cfe);
				Node conditionNode = cn.getNode(ForFactory.LOOPCATCH_NODE_NAME);
				return conditionNode.getEntry();
			}
			if (cfe instanceof WhileStatement) {
				ComplexNode cn = cnProvider.get(cfe);
				Node conditionNode = cn.getNode(WhileFactory.CONDITION_NODE_NAME);
				return conditionNode.getEntry();
			}
			throw new IllegalStateException("Method 'isCatchingType' should be true first");
		}

		@Override
		public boolean isCatchingToken(ControlFlowElement cfe, JumpToken jumpToken, CatchToken catchToken) {
			boolean isCatchingToken = false;
			isCatchingToken |= jumpToken.lblStmt != null && jumpToken.lblStmt == catchToken.lblStmt;
			isCatchingToken |= jumpToken.lblStmt == null;
			return isCatchingToken;
		}
	}

	/**
	 * Provides means to (1) evaluate if an element can catch a {@link JumpToken} of type {@literal JumpType.Return},
	 * and (2) to retrieve the catching node.
	 */
	static private class CatchReturnEvaluator implements CatchEvaluator {

		@Override
		public boolean isCatchingType(ControlFlowElement cfe) {
			return FGUtils.isCFContainer(cfe);
		}

		@Override
		public Node getCatchingNode(ControlFlowElement cfe, ComplexNodeProvider cnProvider) {
			ComplexNode cn = cnProvider.get(cfe);
			return cn.getExit();
		}

		@Override
		public boolean isCatchingToken(ControlFlowElement cfe, JumpToken jumpToken, CatchToken catchToken) {
			return true;
		}
	}

	/**
	 * Provides means to (1) evaluate if an element can catch a {@link JumpToken} of type {@literal JumpType.Throw}, and
	 * (2) to retrieve the catching node.
	 */
	static private class CatchThrowEvaluator implements CatchEvaluator {

		@Override
		public boolean isCatchingType(ControlFlowElement cfe) {
			boolean isCatchingThrowStatement = false;

			isCatchingThrowStatement |= FGUtils.isCFContainer(cfe);

			EObject container = cfe.eContainer();
			if (!isCatchingThrowStatement && container instanceof TryStatement) {
				TryStatement tryStmt = (TryStatement) container;
				Block block = tryStmt.getBlock();
				CatchBlock catcher = tryStmt.getCatch();
				FinallyBlock finalizer = tryStmt.getFinally();

				isCatchingThrowStatement |= block == cfe && (catcher != null || finalizer != null);
			}

			return isCatchingThrowStatement;
		}

		@Override
		public Node getCatchingNode(ControlFlowElement cfe, ComplexNodeProvider cnProvider) {
			EObject container = cfe.eContainer();
			if (FGUtils.isCFContainer(cfe)) {
				ComplexNode cnContainer = cnProvider.get(cfe);
				return cnContainer.getExit();
			}
			if (container instanceof TryStatement) {
				TryStatement tryStmt = (TryStatement) container;
				ComplexNode cnTryStmt = cnProvider.get(tryStmt);
				Node catchNode = null;
				if (tryStmt.getCatch() != null) {
					catchNode = cnTryStmt.getNode(TryFactory.CATCH_NODE_NAME);
				}
				if (catchNode == null && tryStmt.getFinally() != null) {
					catchNode = cnTryStmt.getNode(TryFactory.FINALLY_NODE_NAME);
				}
				Objects.requireNonNull(catchNode);
				return catchNode;
			}
			throw new IllegalStateException("Method 'isCatchingType' should be true first");
		}

		@Override
		public boolean isCatchingToken(ControlFlowElement cfe, JumpToken jumpToken, CatchToken catchToken) {
			return true;
		}
	}
}
