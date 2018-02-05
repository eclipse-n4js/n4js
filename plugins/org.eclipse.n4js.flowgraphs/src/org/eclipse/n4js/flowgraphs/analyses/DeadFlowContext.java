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

	static DeadFlowContext create(DeadFlowContext deadCtx, NextEdgesProvider edgeProv, ControlFlowEdge edge) {
		if (edgeProv.isForward()) {
			return new DeadFlowContext.Forward(deadCtx, edgeProv, edge);
		} else {
			return new DeadFlowContext.Backward(deadCtx);
		}
	}

	static class Forward extends DeadFlowContext {
		private ControlFlowEdge switchEdge;

		Forward(DeadFlowContext deadContext, NextEdgesProvider edgeProv, ControlFlowEdge edge) {
			if (deadContext != null) {
				follow(deadContext);
			}
			update(edgeProv, edge);
		}

		@Override
		void follow(DeadFlowContext deadContext) {
			switchEdge = ((Forward) deadContext).switchEdge;
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
		boolean isForwardDeadFlow() {
			return switchEdge != null;
		}

		@Override
		boolean isDead() {
			return switchEdge != null;
		}

		private void setDeadCode(Node nextNode) {
			if (!nextNode.isReachable()) {
				if (isDead()) {
					nextNode.setUnreachable();
				} else {
					nextNode.setReachable();
				}
			}
		}
	}

	static class Backward extends DeadFlowContext {
		boolean isDeadCode = false;

		Backward(DeadFlowContext deadContext) {
			if (deadContext != null) {
				follow(deadContext);
			}
		}

		@Override
		void follow(DeadFlowContext deadContext) {
			isDeadCode = deadContext.isDead();
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
		boolean isForwardDeadFlow() {
			return false;
		}

		@Override
		boolean isDead() {
			return isDeadCode;
		}

		private void setDeadCode(Node node) {
			assert node.isVisited();
			isDeadCode = node.isUnreachable();
		}
	}

	abstract void follow(DeadFlowContext deadContext);

	abstract void update(Node node);

	abstract void update(NextEdgesProvider edgeProvider, ControlFlowEdge edge);

	abstract void joinWith(DeadFlowContext deadContext);

	abstract boolean isForwardDeadFlow();

	abstract boolean isDead();

	@Override
	public String toString() {
		return isDead() ? "dead" : "live";
	}
}
