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
import com.google.common.collect.Lists;

/**
 * TODO in GH-235 part 2
 */
public class EdgeGuideQueue implements Iterable<EdgeGuide> {
	private final EdgeGuideWorklist guideWorklist;
	private final ArrayList<EdgeGuide> currEdgeGuides = new ArrayList<>();

	/** Constructor */
	public EdgeGuideQueue(EdgeGuideWorklist guideWorklist) {
		this.guideWorklist = guideWorklist;
	}

	void clear() {
		currEdgeGuides.clear();
	}

	void add(EdgeGuide edgeGuide) {
		currEdgeGuides.add(edgeGuide);
	}

	void addAll(Collection<EdgeGuide> edgeGuides) {
		currEdgeGuides.addAll(edgeGuides);
	}

	boolean isEmpty() {
		return currEdgeGuides.isEmpty();
	}

	LinkedList<EdgeGuide> removeFirstJoinGuide() {
		LinkedList<EdgeGuide> guideGroup = new LinkedList<>();
		Collections.sort(currEdgeGuides, this::compareForRemoveFirst);

		for (Iterator<EdgeGuide> iter = currEdgeGuides.iterator(); iter.hasNext();) {
			EdgeGuide eg = iter.next();
			if (!guideGroup.isEmpty() && guideGroup.getFirst().getEdge().end != eg.getEdge().end) {
				break;
			}
			guideGroup.add(eg);
			if (guideGroup.size() > 1) {
				iter.remove();
			}
		}

		if (guideGroup.size() > 1) {
			currEdgeGuides.remove(guideGroup.getFirst());
			return guideGroup;
		}

		return Lists.newLinkedList();
	}

	void removeAll(List<EdgeGuide> rList) {
		currEdgeGuides.removeAll(rList);
	}

	EdgeGuide removeFirst() {
		Collections.sort(currEdgeGuides, this::compareForRemoveFirst);
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

	int compareForRemoveFirst(EdgeGuide eg1, EdgeGuide eg2) {
		ControlFlowEdge e1 = eg1.getEdge();
		ControlFlowEdge e2 = eg2.getEdge();
		Node end1 = e1.end;
		Node end2 = e2.end;
		ControlFlowElement cfe1 = end1.getControlFlowElement();
		ControlFlowElement cfe2 = end1.getControlFlowElement();
		ControlFlowType cft1 = e1.cfType;
		ControlFlowType cft2 = e2.cfType;

		return ComparisonChain.start()
				// sort also within one element, e.g.: for (cond) exit <-- first use cond!
				.compare(eg1, eg2, EdgeGuideQueue::compareJoined)
				.compare(cfe1, cfe2, EdgeGuideQueue::compareDepth)
				.compare(e1, e2, this::compareVisited)
				.compare(cft1, cft2, EdgeGuideQueue::compareEdgeTypes)
				.compare(end1, end2, EdgeGuideQueue::compareJoining)
				.compare(end1.hashCode(), end2.hashCode())
				.compare(e1.hashCode(), e2.hashCode())
				.result();
	}

	static int compareJoined(EdgeGuide eg1, EdgeGuide eg2) {
		if (eg1.isMerged() == eg2.isMerged()) {
			return 0;
		}
		return eg1.isMerged() ? 1 : -1;
	}

	static int compareDepth(ControlFlowElement cfe1, ControlFlowElement cfe2) {
		int d1 = getASTDepth(cfe1);
		int d2 = getASTDepth(cfe2);
		if (d1 != d2) {
			return d2 - d1;
		}
		return 0;
	}

	int compareVisited(ControlFlowEdge e1, ControlFlowEdge e2) {
		boolean isVisited1 = guideWorklist.edgeVisited(e1);
		boolean isVisited2 = guideWorklist.edgeVisited(e2);
		if (isVisited1 == isVisited2) {
			return 0;
		}
		return isVisited1 ? -1 : 1;
	}

	static int compareJoining(Node end1, Node end2) {
		boolean isJoining1 = end1.pred.size() > 1;
		boolean isJoining2 = end2.pred.size() > 1;
		if (isJoining1 == isJoining2) {
			return 0;
		}
		return isJoining1 ? 1 : -1;
	}

	static Map<ControlFlowType, Integer> cftOrderMap = new EnumMap<>(ControlFlowType.class);
	static {
		cftOrderMap.put(ControlFlowType.Successor, 10);
		cftOrderMap.put(ControlFlowType.Repeat, 9);
		cftOrderMap.put(ControlFlowType.Continue, 8);
		cftOrderMap.put(ControlFlowType.Break, 7);
		cftOrderMap.put(ControlFlowType.Throw, 6);
		cftOrderMap.put(ControlFlowType.Return, 5);
		cftOrderMap.put(ControlFlowType.Exit, 4);
		cftOrderMap.put(ControlFlowType.CatchesAll, 0);
		cftOrderMap.put(ControlFlowType.CatchesErrors, 0);
	}

	static int compareEdgeTypes(ControlFlowType cft1, ControlFlowType cft2) {
		if (cft1 == cft2)
			return 0;

		return cftOrderMap.get(cft2) - cftOrderMap.get(cft1);
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
