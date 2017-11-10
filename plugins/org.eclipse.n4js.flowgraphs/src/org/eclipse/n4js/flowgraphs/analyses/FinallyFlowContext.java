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

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.n4js.flowgraphs.ControlFlowType;
import org.eclipse.n4js.flowgraphs.model.ControlFlowEdge;
import org.eclipse.n4js.flowgraphs.model.JumpToken;
import org.eclipse.n4js.utils.collections.Collections2;

import com.google.common.collect.Lists;

/**
 *
 */
public class FinallyFlowContext {
	final Set<JumpToken> finallyBlockContexts = new HashSet<>();

	FinallyFlowContext(ControlFlowEdge edge) {
		this(null, edge);
	}

	FinallyFlowContext(FinallyFlowContext flowContext) {
		this(flowContext, null);
	}

	FinallyFlowContext(FinallyFlowContext flowContext, ControlFlowEdge edge) {
		joinWith(flowContext);
		update(edge);
	}

	void update(ControlFlowEdge edge) {
		if (edge.finallyPathContext != null) {
			finallyBlockContexts.add(edge.finallyPathContext);
		}
	}

	void joinWith(FinallyFlowContext flowContext) {
		if (flowContext != null) {
			finallyBlockContexts.addAll(flowContext.finallyBlockContexts);
		}
	}

	List<ControlFlowEdge> filterEdges(List<ControlFlowEdge> nextEdges) {
		List<ControlFlowEdge> finallyBlockContextEdges = findFinallyBlockContextEdge(nextEdges);

		if (!finallyBlockContextEdges.isEmpty()) {
			return finallyBlockContextEdges;
		}

		return nextEdges;
	}

	/**
	 * This method searches all FinallyBlock-entry/exit edges E to chose the correct next following edges. The following
	 * rules are implemented:
	 * <ul>
	 * <li/>If there exists no next edge with a context, then null is returned.
	 * <li/>If there exists a next edge with a context, and the current {@link EdgeGuide} instance has no context that
	 * matches with one of the next edges, then all edges without context are returned.
	 * <li/>If there exists a next edge with a context, and the current {@link EdgeGuide} instance has a context that
	 * matches with one of the next edges, the matching edge is returned.
	 * </ul>
	 */
	private List<ControlFlowEdge> findFinallyBlockContextEdge(List<ControlFlowEdge> nextEdges) {
		LinkedList<ControlFlowEdge> fbContextFreeEdges = new LinkedList<>();
		Map<JumpToken, ControlFlowEdge> contextEdges = new HashMap<>();
		mapFinallyBlockContextEdges(nextEdges, fbContextFreeEdges, contextEdges);
		if (contextEdges.isEmpty()) {
			return Lists.newLinkedList();
		}

		ControlFlowEdge matchedFBContextEdge = null;
		Map.Entry<JumpToken, ControlFlowEdge> otherEdgePair = null;
		for (Map.Entry<JumpToken, ControlFlowEdge> ctxEdgePair : contextEdges.entrySet()) {
			JumpToken fbContext = ctxEdgePair.getKey();
			otherEdgePair = ctxEdgePair;
			if (finallyBlockContexts.contains(fbContext)) {
				matchedFBContextEdge = ctxEdgePair.getValue();
			}
		}

		if (matchedFBContextEdge != null) {
			return Collections2.newLinkedList(matchedFBContextEdge);

		} else if (!fbContextFreeEdges.isEmpty()) {
			return fbContextFreeEdges;

		} else if (otherEdgePair != null) {
			LinkedList<ControlFlowEdge> contextAndDeadEdges = new LinkedList<>();
			contextAndDeadEdges.add(otherEdgePair.getValue());
			for (ControlFlowEdge edge : nextEdges) {
				if (edge.cfType == ControlFlowType.DeadCode) {
					contextAndDeadEdges.add(edge);
				}
			}
			return contextAndDeadEdges;
		}
		return null;
	}

	private void mapFinallyBlockContextEdges(List<ControlFlowEdge> nextEdges, List<ControlFlowEdge> fbContextFreeEdges,
			Map<JumpToken, ControlFlowEdge> contextEdges) {

		for (ControlFlowEdge nE : nextEdges) {
			if (nE.cfType.isInOrEmpty(ControlFlowType.NonDeadTypes)) {
				JumpToken finallyPathContext = nE.finallyPathContext;
				if (finallyPathContext != null) {
					contextEdges.put(finallyPathContext, nE);
				} else {
					fbContextFreeEdges.add(nE);
				}
			}
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof FinallyFlowContext)) {
			return false;
		}
		FinallyFlowContext ffc = (FinallyFlowContext) obj;
		return this.finallyBlockContexts.equals(ffc.finallyBlockContexts);
	}

	@Override
	public int hashCode() {
		int hashCode = 0;
		for (Object o : finallyBlockContexts) {
			hashCode += o.hashCode();
		}
		return hashCode;
	}

	@Override
	public String toString() {
		return finallyBlockContexts.isEmpty() ? "empty" : finallyBlockContexts.toString();
	}

}
