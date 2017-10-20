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
			if (!guideGroup.isEmpty() && guideGroup.getFirst().getEdge() != eg.getEdge()) {
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
		int compareValue = end1.hashCode() - end2.hashCode();
		if (compareValue == 0) {
			compareValue = e1.cfType.hashCode() - e2.cfType.hashCode();
		}
		return compareValue;
	}

	static int compareForRemoveFirst(EdgeGuide eg1, EdgeGuide eg2) {
		int e1IsFirst = 1;
		int e2IsFirst = -1;

		ControlFlowEdge e1 = eg1.getEdge();
		ControlFlowEdge e2 = eg2.getEdge();
		Node end1 = e1.end;
		Node end2 = e2.end;
		boolean isJoining1 = end1.pred.size() > 1;
		boolean isJoining2 = end2.pred.size() > 1;
		if (isJoining1 && !isJoining2) {
			return e2IsFirst;
		}
		if (!isJoining1 && isJoining2) {
			return e1IsFirst;
		}

		ControlFlowElement cfe1 = end1.getControlFlowElement();
		ControlFlowElement cfe2 = end2.getControlFlowElement();
		int d1 = getASTDepth(cfe1);
		int d2 = getASTDepth(cfe2);
		if (d1 != d2) {
			return d1 - d2;
		}

		if (e1.cfType != e2.cfType) {

			if (e1.cfType == ControlFlowType.Successor)
				return e1IsFirst;
			if (e2.cfType == ControlFlowType.Successor)
				return e2IsFirst;

			if (e1.cfType == ControlFlowType.Repeat)
				return e1IsFirst;
			if (e2.cfType == ControlFlowType.Repeat)
				return e2IsFirst;

			if (e1.cfType == ControlFlowType.Continue)
				return e1IsFirst;
			if (e2.cfType == ControlFlowType.Continue)
				return e2IsFirst;

			if (e1.cfType == ControlFlowType.Break)
				return e1IsFirst;
			if (e2.cfType == ControlFlowType.Break)
				return e2IsFirst;

			if (e1.cfType == ControlFlowType.Throw)
				return e1IsFirst;
			if (e2.cfType == ControlFlowType.Throw)
				return e2IsFirst;

			if (e1.cfType == ControlFlowType.Return)
				return e1IsFirst;
			if (e2.cfType == ControlFlowType.Return)
				return e2IsFirst;
		}
		return e1.hashCode() - e2.hashCode();
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
