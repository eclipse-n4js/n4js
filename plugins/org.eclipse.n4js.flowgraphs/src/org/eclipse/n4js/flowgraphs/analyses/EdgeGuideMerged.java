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
package org.eclipse.n4js.flowgraphs.analyses;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
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
		super(edgeGuides.get(0).edgeProvider, edgeGuides.get(0).getEdge());

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

			finallyBlockContexts.addAll(eg.finallyBlockContexts);
			edgeProvider.join(eg.edgeProvider);
		}

		branchWalkers.clear();
		for (Map.Entry<GraphExplorerInternal, List<BranchWalkerInternal>> joiningWalkers : joiningWalkerMap
				.entrySet()) {

			GraphExplorerInternal explorer = joiningWalkers.getKey();
			List<BranchWalkerInternal> walkers = joiningWalkers.getValue();
			BranchWalkerInternal joinedWalker = explorer.callJoinBranchWalkers(walkers);
			branchWalkers.add(joinedWalker);
		}
	}

	@Override
	List<EdgeGuide> getNextEdgeGuides() {
		EdgeGuide edgeGuide = new EdgeGuide(edgeProvider, getEdge(), branchWalkers, finallyBlockContexts);
		return edgeGuide.getNextEdgeGuides();
	}

	@Override
	boolean isMerged() {
		return true;
	}
}
