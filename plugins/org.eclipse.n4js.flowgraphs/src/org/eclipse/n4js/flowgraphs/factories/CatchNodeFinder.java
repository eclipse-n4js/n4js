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
import org.eclipse.n4js.n4JS.BinaryLogicalExpression;
import org.eclipse.n4js.n4JS.Block;
import org.eclipse.n4js.n4JS.CatchBlock;
import org.eclipse.n4js.n4JS.ConditionalExpression;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.DoStatement;
import org.eclipse.n4js.n4JS.ExpressionWithTarget;
import org.eclipse.n4js.n4JS.FinallyBlock;
import org.eclipse.n4js.n4JS.ForStatement;
import org.eclipse.n4js.n4JS.IfStatement;
import org.eclipse.n4js.n4JS.LabelledStatement;
import org.eclipse.n4js.n4JS.SwitchStatement;
import org.eclipse.n4js.n4JS.TryStatement;
import org.eclipse.n4js.n4JS.WhileStatement;
import org.eclipse.xtext.xbase.lib.Pair;

/**
 * For a given {@link JumpToken}s of a given start {@link Node} the method
 * {@link #find(JumpToken, Node, ComplexNodeMapper)} finds the target node to which the start node jumps via the given
 * {@link JumpToken}.
 */
public class CatchNodeFinder {
	static private final CatchEvaluator catchBreakEvaluator = new CatchBreakEvaluator();
	static private final CatchEvaluator catchContinueEvluator = new CatchContinueEvaluator();
	static private final CatchEvaluator catchReturnEvaluator = new CatchReturnEvaluator();
	static private final CatchEvaluator catchThrowEvaluator = new CatchThrowEvaluator();
	static private final CatchEvaluator catchShortCircuitThenEvaluator = new CatchShortCircuitThenEvaluator();
	static private final CatchEvaluator catchShortCircuitElseEvaluator = new CatchShortCircuitElseEvaluator();
	static private final CatchEvaluator catchOptionalChainingEvaluator = new CatchOptionalChainingEvaluator();

	/** @return the node to which the given {@code jumpNode} jumps via the given {@link JumpToken}. Can return null. */
	static Pair<Node, ControlFlowType> find(JumpToken jumpToken, Node jumpNode, ComplexNodeMapper cnMapper) {
		CatchEvaluator catchEvaluator = getCatchEvaluator(jumpToken);
		ControlFlowElement cfe = jumpNode.getControlFlowElement();
		cfe = skipContainers(cfe);

		ControlFlowElement lastCFE = null;
		while (cfe != null) {
			Pair<Node, ControlFlowType> catcher = findCatchNode(jumpToken, cfe, lastCFE, catchEvaluator, cnMapper);
			if (catcher != null)
				return catcher;

			lastCFE = cfe;
			cfe = getContainer(cfe);
		}

		return null;
	}

	/** @return the correct {@link CatchEvaluator} based on the {@link JumpToken}. */
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
		case IfTrue:
			return catchShortCircuitThenEvaluator;
		case IfFalse:
			return catchShortCircuitElseEvaluator;
		case IfNullishTarget:
			return catchOptionalChainingEvaluator;
		default:
			throw new IllegalArgumentException("not implemented");
		}
	}

	/**
	 * Skipping containers can be necessary when a jump node lies within a {@link FinallyBlock} (that would catch
	 * anything itself).
	 */
	private static ControlFlowElement skipContainers(ControlFlowElement cfe) {
		if (cfe instanceof Block) {
			cfe = getContainer(cfe);
		}
		while (FGUtils.isControlStatement(cfe) && !(cfe instanceof Block)) {
			cfe = getContainer(cfe);
		}
		return cfe;
	}

	/** To find the correct catch node, all containers are searched, starting from the innermost. */
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

	/** @return a node that can catch the given {@link JumpToken}. */
	private static Pair<Node, ControlFlowType> findCatchNode(JumpToken jumpToken, ControlFlowElement cfe,
			ControlFlowElement lastCFE, CatchEvaluator catchEvaluator, ComplexNodeMapper cnMapper) {

		if (catchEvaluator.isCatchingType(cfe)) {
			Node catchNode = catchEvaluator.getCatchingNode(cfe, lastCFE, cnMapper);
			for (CatchToken catchToken : catchNode.catchToken) {
				if (catchEvaluator.isCatchingToken(cfe, jumpToken, catchToken)) {
					ControlFlowType newEdgeType = catchEvaluator.getJumpCatchType(catchToken);
					if (newEdgeType == ControlFlowType.CatchesAll) {
						newEdgeType = jumpToken.cfType;
					}
					return Pair.of(catchNode, newEdgeType);
				}
			}
		}

		Node catchAllNode = findCatchAllNode(cfe, cnMapper);
		if (catchAllNode != null) {
			return Pair.of(catchAllNode, jumpToken.cfType);
		}
		return null;
	}

	/**
	 * Wrapper method of {@link #findCatchAllNodeInOtherStmt(ControlFlowElement, ComplexNodeMapper)}.
	 * <p>
	 * Necessary since neither the {@link TryStatement} itself nor the {@link FinallyBlock} can ever catch a node (only
	 * the try block can).
	 */
	private static Node findCatchAllNode(ControlFlowElement cfe, ComplexNodeMapper cnMapper) {
		if (cfe instanceof TryStatement) {
			return null;
		}
		EObject container = cfe.eContainer();
		if (container instanceof TryStatement) {
			return findCatchAllNodeInOtherStmt((TryStatement) container, cnMapper);
		}
		if (container instanceof CatchBlock) {
			return findCatchAllNodeInOtherStmt((TryStatement) container.eContainer(), cnMapper);
		}
		if (container instanceof FinallyBlock) {
			return null;
		}

		return findCatchAllNodeInOtherStmt(cfe, cnMapper);
	}

	/** @return a node that can catch any {@link JumpToken} */
	private static Node findCatchAllNodeInOtherStmt(ControlFlowElement cfe, ComplexNodeMapper cnMapper) {
		ComplexNode cnCFE = cnMapper.get(cfe);
		for (Node node : cnCFE.getNodes()) {
			for (CatchToken cToken : node.catchToken) {
				if (cToken.cfType == ControlFlowType.CatchesAll) {
					return node;
				}
			}
		}
		return null;
	}

	static private abstract class CatchEvaluator {

		/** @return true iff the given {@link ControlFlowElement} is a possible catch candidate */
		abstract boolean isCatchingType(ControlFlowElement cfe);

		/**
		 * Result is valid only if {@link #isCatchingType(ControlFlowElement)} returns true on the same <code>cfe</code>
		 *
		 * @return the catching node.
		 */
		abstract Node getCatchingNode(ControlFlowElement cfe, ControlFlowElement lastCFE, ComplexNodeMapper cnMapper);

		/** @return true iff the given {@link CatchToken} can catch the given {@link JumpToken}. */
		abstract boolean isCatchingToken(ControlFlowElement cfe, JumpToken jumpToken, CatchToken catchToken);

		/** @return the {@link ControlFlowType} of the newly created jump edge */
		public ControlFlowType getJumpCatchType(CatchToken catchToken) {
			return catchToken.newEdgeType;
		}
	}

	/**
	 * Provides means to (1) evaluate if an element can catch a {@link JumpToken} of type {@literal JumpType.Break}, and
	 * (2) to retrieve the catching node.
	 */
	static private class CatchBreakEvaluator extends CatchEvaluator {

		@Override
		public boolean isCatchingType(ControlFlowElement cfe) {
			boolean isCatchingBreakStatement = false;
			isCatchingBreakStatement |= cfe instanceof DoStatement;
			isCatchingBreakStatement |= cfe instanceof ForStatement;
			isCatchingBreakStatement |= cfe instanceof WhileStatement;
			isCatchingBreakStatement |= cfe instanceof SwitchStatement;
			isCatchingBreakStatement |= cfe instanceof Block && cfe.eContainer() instanceof LabelledStatement;
			return isCatchingBreakStatement;
		}

		@Override
		public Node getCatchingNode(ControlFlowElement cfe, ControlFlowElement lastCFE, ComplexNodeMapper cnMapper) {
			ComplexNode cn = cnMapper.get(cfe);
			return cn.getExit();
		}

		@Override
		public boolean isCatchingToken(ControlFlowElement cfe, JumpToken jumpToken, CatchToken catchToken) {
			boolean isCatchingToken = false;
			isCatchingToken |= jumpToken.lblStmt != null && jumpToken.lblStmt == catchToken.lblStmt;
			isCatchingToken |= jumpToken.lblStmt == null;
			isCatchingToken &= jumpToken.cfType == catchToken.cfType;
			return isCatchingToken;
		}
	}

	/**
	 * Provides means to (1) evaluate if an element can catch a {@link JumpToken} of type {@literal JumpType.Continue},
	 * and (2) to retrieve the catching node.
	 */
	static private class CatchContinueEvaluator extends CatchEvaluator {

		@Override
		public boolean isCatchingType(ControlFlowElement cfe) {
			boolean isCatchingContinueStatement = false;
			isCatchingContinueStatement |= cfe instanceof DoStatement;
			isCatchingContinueStatement |= cfe instanceof ForStatement;
			isCatchingContinueStatement |= cfe instanceof WhileStatement;
			return isCatchingContinueStatement;
		}

		@Override
		public Node getCatchingNode(ControlFlowElement cfe, ControlFlowElement lastCFE, ComplexNodeMapper cnMapper) {
			if (cfe instanceof DoStatement) {
				ComplexNode cn = cnMapper.get(cfe);
				Node conditionNode = cn.getNode(NodeNames.CONDITION);
				return conditionNode.getEntry();
			}
			if (cfe instanceof ForStatement) {
				ComplexNode cn = cnMapper.get(cfe);
				Node conditionNode = cn.getNode(NodeNames.CONTINUE_CATCH);
				return conditionNode.getEntry();
			}
			if (cfe instanceof WhileStatement) {
				ComplexNode cn = cnMapper.get(cfe);
				Node conditionNode = cn.getNode(NodeNames.CONTINUE_CATCH);
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
	static private class CatchReturnEvaluator extends CatchEvaluator {

		@Override
		public boolean isCatchingType(ControlFlowElement cfe) {
			return FGUtils.isCFContainer(cfe);
		}

		@Override
		public Node getCatchingNode(ControlFlowElement cfe, ControlFlowElement lastCFE, ComplexNodeMapper cnMapper) {
			ComplexNode cn = cnMapper.get(cfe);
			return cn.getExit();
		}

		@Override
		public boolean isCatchingToken(ControlFlowElement cfe, JumpToken jumpToken, CatchToken catchToken) {
			return jumpToken.cfType == catchToken.cfType;
		}
	}

	/**
	 * Provides means to (1) evaluate if an element can catch a {@link JumpToken} of type {@literal JumpType.Throw}, and
	 * (2) to retrieve the catching node.
	 */
	static private class CatchThrowEvaluator extends CatchEvaluator {

		@Override
		public boolean isCatchingType(ControlFlowElement cfe) {
			boolean isCatchingThrowStatement = FGUtils.isCFContainer(cfe);

			if (!isCatchingThrowStatement) {
				EObject container = cfe.eContainer();

				if (container instanceof TryStatement) {
					TryStatement tryStmt = (TryStatement) container;
					Block block = tryStmt.getBlock();
					CatchBlock catchBlock = tryStmt.getCatch();
					FinallyBlock finalizer = tryStmt.getFinally();

					isCatchingThrowStatement |= block == cfe && (catchBlock != null || finalizer != null);
				}

				if (container instanceof CatchBlock) {
					CatchBlock catchBlock = (CatchBlock) container;
					TryStatement tryStmt = (TryStatement) catchBlock.eContainer();
					Block block = catchBlock.getBlock();
					FinallyBlock finalizer = tryStmt.getFinally();

					isCatchingThrowStatement |= block == cfe && finalizer != null;
				}
			}

			return isCatchingThrowStatement;
		}

		@Override
		public Node getCatchingNode(ControlFlowElement cfe, ControlFlowElement lastCFE, ComplexNodeMapper cnMapper) {
			EObject container = cfe.eContainer();
			if (FGUtils.isCFContainer(cfe)) {
				ComplexNode cnContainer = cnMapper.get(cfe);
				return cnContainer.getExit();
			}
			if (container instanceof TryStatement) {
				TryStatement tryStmt = (TryStatement) container;
				ComplexNode cnTryStmt = cnMapper.get(tryStmt);
				Node catchNode = null;
				if (tryStmt.getCatch() != null) {
					catchNode = cnTryStmt.getNode(NodeNames.CATCH);
				}
				if (catchNode == null && tryStmt.getFinally() != null) {
					catchNode = cnTryStmt.getNode(NodeNames.FINALLY);
				}
				Objects.requireNonNull(catchNode);
				return catchNode;
			}
			if (container instanceof CatchBlock) {
				TryStatement tryStmt = (TryStatement) container.eContainer();
				ComplexNode cnTryStmt = cnMapper.get(tryStmt);
				Node catchNode = cnTryStmt.getNode(NodeNames.FINALLY);
				Objects.requireNonNull(catchNode);
				return catchNode;
			}
			throw new IllegalStateException("Method 'isCatchingType' should be true first");
		}

		@Override
		public boolean isCatchingToken(ControlFlowElement cfe, JumpToken jumpToken, CatchToken catchToken) {
			return jumpToken.cfType == catchToken.cfType;
		}
	}

	/**
	 * Catches jumps that happen due to short circuit execution in {@link BinaryLogicalExpression}
	 */
	static private class CatchShortCircuitThenEvaluator extends CatchEvaluator {

		@Override
		public boolean isCatchingType(ControlFlowElement cfe) {
			boolean isCatching = false;
			isCatching |= cfe instanceof BinaryLogicalExpression;
			isCatching |= cfe instanceof ConditionalExpression;
			isCatching |= cfe instanceof IfStatement;
			isCatching |= cfe instanceof DoStatement;
			isCatching |= cfe instanceof ForStatement;
			isCatching |= cfe instanceof WhileStatement;
			return isCatching;
		}

		@Override
		public Node getCatchingNode(ControlFlowElement cfe, ControlFlowElement lastCFE, ComplexNodeMapper cnMapper) {
			if (cfe instanceof ConditionalExpression) {
				ComplexNode cn = cnMapper.get(cfe);
				return cn.getNode(NodeNames.THEN);
			}
			if (cfe instanceof IfStatement) {
				ComplexNode cn = cnMapper.get(cfe);
				return cn.getNode(NodeNames.THEN);
			}
			if (cfe instanceof DoStatement) {
				ComplexNode cn = cnMapper.get(cfe);
				return cn.getNode(NodeNames.BODY);
			}
			if (cfe instanceof WhileStatement) {
				ComplexNode cn = cnMapper.get(cfe);
				return cn.getNode(NodeNames.BODY);
			}
			if (cfe instanceof ForStatement) {
				ComplexNode cn = cnMapper.get(cfe);
				return cn.getNode(NodeNames.BODY);
			}
			if (cfe instanceof BinaryLogicalExpression) {
				ComplexNode cn = cnMapper.get(cfe);
				BinaryLogicalExpression ble = (BinaryLogicalExpression) cfe;
				if (ble.getLhs() == lastCFE) {
					Node potentialCatchNode = cn.getNode(NodeNames.RHS);
					if (potentialCatchNode.catchToken.get(0).cfType == ControlFlowType.IfTrue) {
						return potentialCatchNode;
					}
				}
				return cn.getNode(NodeNames.EXIT);
			}
			ComplexNode cn = cnMapper.get(cfe);
			return cn.getNode(NodeNames.EXIT);
		}

		@Override
		public boolean isCatchingToken(ControlFlowElement cfe, JumpToken jumpToken, CatchToken catchToken) {
			return jumpToken.cfType == catchToken.cfType;
		}
	}

	/**
	 * Catches jumps that happen due to short circuit execution in {@link BinaryLogicalExpression}
	 */
	static private class CatchShortCircuitElseEvaluator extends CatchEvaluator {

		@Override
		public boolean isCatchingType(ControlFlowElement cfe) {
			boolean isCatching = false;
			isCatching |= cfe instanceof BinaryLogicalExpression;
			isCatching |= cfe instanceof ConditionalExpression;
			isCatching |= cfe instanceof IfStatement;
			isCatching |= cfe instanceof DoStatement;
			isCatching |= cfe instanceof ForStatement;
			isCatching |= cfe instanceof WhileStatement;
			return isCatching;
		}

		@Override
		public Node getCatchingNode(ControlFlowElement cfe, ControlFlowElement lastCFE, ComplexNodeMapper cnMapper) {
			if (cfe instanceof ConditionalExpression) {
				ComplexNode cn = cnMapper.get(cfe);
				return cn.getNode(NodeNames.ELSE);
			}
			if (cfe instanceof IfStatement) {
				ComplexNode cn = cnMapper.get(cfe);
				Node elseNode = cn.getNode(NodeNames.ELSE);
				if (elseNode != null) {
					return elseNode;
				}
				return cn.getNode(NodeNames.EXIT);
			}
			if (cfe instanceof BinaryLogicalExpression) {
				ComplexNode cn = cnMapper.get(cfe);
				BinaryLogicalExpression ble = (BinaryLogicalExpression) cfe;
				if (ble.getLhs() == lastCFE) {
					Node potentialCatchNode = cn.getNode(NodeNames.RHS);
					if (potentialCatchNode.catchToken.get(0).cfType == ControlFlowType.IfFalse) {
						return potentialCatchNode;
					}
				}
				return cn.getNode(NodeNames.EXIT);
			}

			ComplexNode cn = cnMapper.get(cfe);
			return cn.getNode(NodeNames.EXIT);
		}

		@Override
		public boolean isCatchingToken(ControlFlowElement cfe, JumpToken jumpToken, CatchToken catchToken) {
			return jumpToken.cfType == catchToken.cfType;
		}
	}

	/**
	 * Catches jumps that happen due to nullish targets in optional chains (see
	 * {@link ExpressionWithTarget#isOptionalChaining()}.
	 */
	static private class CatchOptionalChainingEvaluator extends CatchEvaluator {

		@Override
		public boolean isCatchingType(ControlFlowElement cfe) {
			return cfe instanceof ExpressionWithTarget;
		}

		@Override
		public Node getCatchingNode(ControlFlowElement cfe, ControlFlowElement lastCFE, ComplexNodeMapper cnMapper) {
			ComplexNode cn = cnMapper.get(cfe);
			return cn.getExit();
		}

		@Override
		public boolean isCatchingToken(ControlFlowElement cfe, JumpToken jumpToken, CatchToken catchToken) {
			return jumpToken.cfType == catchToken.cfType;
		}
	}
}
