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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.flowgraphs.ControlFlowType;
import org.eclipse.n4js.flowgraphs.model.ControlFlowEdge;
import org.eclipse.n4js.flowgraphs.model.Node;
import org.eclipse.n4js.n4JS.ControlFlowElement;

import com.google.common.collect.ComparisonChain;

/**
 * TODO in GH-235 part 2
 */
public class EdgeGuideQueue implements Iterable<EdgeGuide> {
	private final ArrayList<EdgeGuide> currEdgeGuides = new ArrayList<>();

	void add(EdgeGuide edgeGuide) {
		currEdgeGuides.add(edgeGuide);
	}

	void addAll(Collection<EdgeGuide> edgeGuides) {
		currEdgeGuides.addAll(edgeGuides);
	}

	boolean isEmpty() {
		return currEdgeGuides.isEmpty();
	}

	Collection<LinkedList<EdgeGuide>> removeJoinGuides() {
		Map<Node, LinkedList<EdgeGuide>> guideGroups = new HashMap<>();
		LinkedList<EdgeGuide> guideGroup = new LinkedList<>();
		Collections.sort(currEdgeGuides, EdgeGuideQueue::compareForJoin);

		for (Iterator<EdgeGuide> iter = currEdgeGuides.iterator(); iter.hasNext();) {
			EdgeGuide eg = iter.next();
			if (!guideGroup.isEmpty() && guideGroup.getFirst().getEdge().end != eg.getEdge().end) {
				if (guideGroup.size() > 1) {
					guideGroups.put(eg.getEdge().end, guideGroup);
				}

				guideGroup = new LinkedList<>();
			}
			guideGroup.add(eg);
			if (guideGroup.size() > 1) {
				iter.remove();
			}
		}
		if (guideGroup.size() > 1) {
			guideGroups.put(guideGroup.getFirst().getEdge().end, guideGroup);
		}
		for (LinkedList<EdgeGuide> guideGroup2 : guideGroups.values()) {
			currEdgeGuides.remove(guideGroup2.getFirst());
		}
		return guideGroups.values();
	}

	void removeAll(List<EdgeGuide> rList) {
		currEdgeGuides.removeAll(rList);
	}

	EdgeGuide removeFirst() {
		Collections.sort(currEdgeGuides, EdgeGuideQueue::compareForRemoveFirst);
		return currEdgeGuides.remove(0);
	}

	@Override
	public Iterator<EdgeGuide> iterator() {
		return currEdgeGuides.iterator();
	}

	static int compareForJoin(EdgeGuide eg1, EdgeGuide eg2) {
		ControlFlowEdge e1 = eg1.getEdge();
		ControlFlowEdge e2 = eg2.getEdge();
		Node end1 = e1.end;
		Node end2 = e2.end;
		ControlFlowType cft1 = e1.cfType;
		ControlFlowType cft2 = e2.cfType;

		return ComparisonChain.start()
				.compare(end1.hashCode(), end2.hashCode())
				.compare(cft1.hashCode(), cft2.hashCode())
				.result();
	}

	static int compareForRemoveFirst(EdgeGuide eg1, EdgeGuide eg2) {
		ControlFlowEdge e1 = eg1.getEdge();
		ControlFlowEdge e2 = eg2.getEdge();
		Node end1 = e1.end;
		Node end2 = e2.end;
		ControlFlowElement cfe1 = end1.getControlFlowElement();
		ControlFlowElement cfe2 = end2.getControlFlowElement();
		ControlFlowType cft1 = e1.cfType;
		ControlFlowType cft2 = e2.cfType;

		return ComparisonChain.start()
				.compare(eg1, eg2, EdgeGuideQueue::compareJoined)
				.compare(end1, end2, EdgeGuideQueue::compareJoining)
				.compare(cfe1, cfe2, EdgeGuideQueue::compareDepth)
				.compare(cft1, cft2, EdgeGuideQueue::compareEdgeTypes)
				.compare(e1.hashCode(), e2.hashCode())
				.result();
	}

	static int compareJoined(EdgeGuide eg1, EdgeGuide eg2) {
		boolean isJoined1 = eg1.getAllEdges().size() > 1;
		boolean isJoined2 = eg2.getAllEdges().size() > 1;
		if (isJoined1 == isJoined2) {
			return 0;
		}
		return isJoined1 ? 1 : -1;
	}

	static int compareJoining(Node end1, Node end2) {
		boolean isJoining1 = end1.pred.size() > 1;
		boolean isJoining2 = end2.pred.size() > 1;
		if (isJoining1 == isJoining2) {
			return 0;
		}
		return isJoining1 ? 1 : -1;
	}

	static int compareDepth(ControlFlowElement cfe1, ControlFlowElement cfe2) {
		int d1 = getASTDepth(cfe1);
		int d2 = getASTDepth(cfe2);
		if (d1 != d2) {
			return d2 - d1;
		}
		return 0;
	}

	static Map<ControlFlowType, Integer> cftOrderMap = new EnumMap<>(ControlFlowType.class);
	static {
		cftOrderMap.put(ControlFlowType.Successor, 0);
		cftOrderMap.put(ControlFlowType.Repeat, 1);
		cftOrderMap.put(ControlFlowType.Continue, 2);
		cftOrderMap.put(ControlFlowType.Break, 3);
		cftOrderMap.put(ControlFlowType.Throw, 4);
		cftOrderMap.put(ControlFlowType.Return, 5);
		cftOrderMap.put(ControlFlowType.CatchesAll, 10);
		cftOrderMap.put(ControlFlowType.CatchesErrors, 10);
	}

	static int compareEdgeTypes(ControlFlowType cft1, ControlFlowType cft2) {
		if (cft1 == cft2)
			return 0;

		return cftOrderMap.get(cft1) - cftOrderMap.get(cft2);
	}

	static int getASTDepth(EObject eObj) {
		int i;
		for (i = 0; eObj != null; i++) {
			eObj = eObj.eContainer();
		}
		return i;
	}

	static String getString(EdgeGuide egi) {
		String s = "(";
		s += getASTDepth(egi.getEdge().end.getControlFlowElement());
		s += ":";
		s += egi.getEdge().cfType;
		s += ")";
		return s;
	}

}
