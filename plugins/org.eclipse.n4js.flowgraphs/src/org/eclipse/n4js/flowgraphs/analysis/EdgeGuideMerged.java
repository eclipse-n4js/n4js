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
package org.eclipse.n4js.flowgraphs.analysis;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.n4js.flowgraphs.model.Node;

/**
 * This subclass of {@link EdgeGuide} models a merged {@link EdgeGuide}. This merged {@link EdgeGuide} is created when
 * control flow branches are joined.
 */
public class EdgeGuideMerged extends EdgeGuide {

	/**
	 * The constructor will join all the given {@link EdgeGuide}s. The created instance reflects the joined state.
	 * <p>
	 * <b>Assumptions:</b>
	 * <ul>
	 * <li/>All joined {@link EdgeGuide}s have the same {@link EdgeGuide#getNextNode()}
	 * <li/>All joined {@link EdgeGuide}s have different {@link EdgeGuide#getPrevNode()}s
	 * </ul>
	 */
	EdgeGuideMerged(List<EdgeGuide> edgeGuides) {
		super(edgeGuides.get(0).edgeProvider,
				edgeGuides.get(0).getEdge(),
				edgeGuides.get(0).finallyContext,
				edgeGuides.get(0).deadContext);

		Map<GraphExplorerInternal, List<BranchWalkerInternal>> joiningWalkerMap = new HashMap<>();

		for (EdgeGuide eg : edgeGuides) {
			for (BranchWalkerInternal bwi : eg.branchWalkers) {

				GraphExplorerInternal explorer = bwi.getExplorer();
				if (!joiningWalkerMap.containsKey(explorer)) {
					joiningWalkerMap.put(explorer, new LinkedList<>());
				}
				List<BranchWalkerInternal> joiningWalkers = joiningWalkerMap.get(explorer);
				joiningWalkers.add(bwi);
			}

			finallyContext.joinWith(eg.finallyContext);
			edgeProvider.join(eg.edgeProvider);
			deadContext.joinWith(eg.deadContext);
		}

		branchWalkers.clear();
		for (Map.Entry<GraphExplorerInternal, List<BranchWalkerInternal>> joiningWalkers : joiningWalkerMap
				.entrySet()) {

			GraphExplorerInternal explorer = joiningWalkers.getKey();
			List<BranchWalkerInternal> walkers = joiningWalkers.getValue();
			BranchWalkerInternal joinedWalker = explorer.callJoinBranchWalkers(walkers);
			if (joinedWalker != null) {
				branchWalkers.add(joinedWalker);
			}
		}

		DataRecorderPackageProxy.addMergedEdges(edgeGuides);
	}

	@Override
	List<EdgeGuide> getNextEdgeGuides() {
		EdgeGuide edgeGuide = new EdgeGuide(edgeProvider, getEdge(), branchWalkers, finallyContext, deadContext);
		return edgeGuide.getNextEdgeGuides();
	}

	/** @return always returns null since a {@link EdgeGuideMerged} has several preceding nodes */
	@Override
	Node getPrevNode() {
		return null;
	}

	@Override
	boolean isMerged() {
		return true;
	}
}
