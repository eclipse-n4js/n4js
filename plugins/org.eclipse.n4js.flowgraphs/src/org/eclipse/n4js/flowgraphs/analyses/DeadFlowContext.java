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
package org.eclipse.n4js.flowgraphs.analyses;

import org.eclipse.n4js.flowgraphs.ControlFlowType;
import org.eclipse.n4js.flowgraphs.model.ControlFlowEdge;
import org.eclipse.n4js.flowgraphs.model.Node;

/**
 *
 */
abstract class DeadFlowContext {
	boolean isDeadCode = false;

	static DeadFlowContext create(NextEdgesProvider edgeProv, DeadFlowContext deadCtx, Node node) {
		if (edgeProv.isForward()) {
			return new DeadFlowContext.Forward(deadCtx, node);
		} else {
			return new DeadFlowContext.Backward(deadCtx);
		}
	}

	static class Forward extends DeadFlowContext {
		private ControlFlowEdge switchEdge;

		Forward(DeadFlowContext deadContext, Node node) {
			if (deadContext != null) {
				switchEdge = ((Forward) deadContext).switchEdge;
			}
			update(node);
		}

		@Override
		void update(Node node) {
			setDeadCode(node);
		}

		@Override
		void update(NextEdgesProvider edgeProvider, ControlFlowEdge edge) {
			if (edge.cfType == ControlFlowType.DeadCode) {
				switchEdge = edge;
			}
			Node nextNode = edgeProvider.getNextNode(edge);
			setDeadCode(nextNode);
		}

		@Override
		void joinWith(DeadFlowContext deadContext) {
			if (((Forward) deadContext).switchEdge == null) {
				switchEdge = null;
			}
		}

		@Override
		boolean isForwardAndDeadInside() {
			return switchEdge != null;
		}

		private void setDeadCode(Node nextNode) {
			if (nextNode.isReachable()) {
				isDeadCode = false;
			} else {
				isDeadCode = isDeadCode || switchEdge != null;
				if (isDeadCode) {
					nextNode.setUnreachable();
				} else {
					nextNode.setReachable();
				}
			}
		}
	}

	static class Backward extends DeadFlowContext {

		Backward(DeadFlowContext deadContext) {
			if (deadContext != null) {
				isDeadCode = deadContext.isDeadCode;
			}
		}

		@Override
		void update(Node node) {
			setDeadCode(node);
		}

		@Override
		void update(NextEdgesProvider edgeProvider, ControlFlowEdge edge) {
			Node nextNode = edgeProvider.getNextNode(edge);
			setDeadCode(nextNode);
		}

		@Override
		void joinWith(DeadFlowContext deadContext) {
			//
		}

		@Override
		boolean isForwardAndDeadInside() {
			return false;
		}

		private void setDeadCode(Node node) {
			assert node.isVisited();
			isDeadCode = node.isUnreachable();
		}
	}

	abstract void update(Node node);

	abstract void update(NextEdgesProvider edgeProvider, ControlFlowEdge edge);

	abstract void joinWith(DeadFlowContext deadContext);

	abstract boolean isForwardAndDeadInside();

	boolean isDead() {
		return isDeadCode;
	}

	@Override
	public String toString() {
		return isDead() ? "dead" : "live";
	}
}
