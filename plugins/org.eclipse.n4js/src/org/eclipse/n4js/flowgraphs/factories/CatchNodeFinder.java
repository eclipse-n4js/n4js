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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.flowgraphs.ComplexNodeProvider;
import org.eclipse.n4js.flowgraphs.model.CatchToken;
import org.eclipse.n4js.flowgraphs.model.ComplexNode;
import org.eclipse.n4js.flowgraphs.model.ControlFlowType;
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

			EObject container = cfe.eContainer();
			cfe = (container instanceof ControlFlowElement) ? (ControlFlowElement) container : null;
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
			isCatchingBreakStatement |= cfe instanceof SwitchStatement;
			isCatchingBreakStatement |= cfe instanceof WhileStatement;
			return isCatchingBreakStatement;
		}

		@Override
		public Node getCatchingNode(ControlFlowElement cfe, ComplexNodeProvider cnProvider) {
			ComplexNode cn = cnProvider.get(cfe);
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
			if (jumpToken.id == null && catchToken.id == null) {
				return true;
			}
			boolean isCatchingToken = jumpToken.id.equals(catchToken.id);
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
			boolean isCatchingReturnStatement = false;

			EObject container = cfe.eContainer();
			if (container instanceof FunctionDeclaration) {
				FunctionDeclaration fncDecl = (FunctionDeclaration) container;
				isCatchingReturnStatement |= fncDecl.getBody() == cfe;
			}

			return isCatchingReturnStatement;
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

			EObject container = cfe.eContainer();
			if (container instanceof TryStatement) {
				TryStatement tryStmt = (TryStatement) container;
				isCatchingThrowStatement |= tryStmt.getBlock() == cfe && tryStmt.getCatch() != null;
			}
			if (container instanceof FunctionDeclaration) {
				FunctionDeclaration fncDecl = (FunctionDeclaration) container;
				isCatchingThrowStatement |= fncDecl.getBody() == cfe;
			}

			return isCatchingThrowStatement;
		}

		@Override
		public Node getCatchingNode(ControlFlowElement cfe, ComplexNodeProvider cnProvider) {
			EObject container = cfe.eContainer();
			if (container instanceof TryStatement) {
				TryStatement tryStmt = (TryStatement) container;
				ComplexNode cnCatch = cnProvider.get(tryStmt.getCatch().getBlock());
				return cnCatch.getEntry();
			}
			if (container instanceof FunctionDeclaration) {
				FunctionDeclaration fncDecl = (FunctionDeclaration) container;
				ComplexNode cnBody = cnProvider.get(fncDecl.getBody());
				return cnBody.getExit();
			}
			throw new IllegalStateException("Method 'isCatchingType' should be true first");
		}

		@Override
		public boolean isCatchingToken(ControlFlowElement cfe, JumpToken jumpToken, CatchToken catchToken) {
			return true;
		}
	}

}
