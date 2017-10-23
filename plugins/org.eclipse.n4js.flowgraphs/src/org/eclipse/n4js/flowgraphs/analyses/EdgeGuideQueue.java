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
import java.util.Iterator;

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

	void addAll(Collection<EdgeGuide> edgeGuides) {
		currEdgeGuides.addAll(edgeGuides);
		Collections.sort(currEdgeGuides, EdgeGuideQueue::compareForJoin);
		Iterator<EdgeGuide> egIter = currEdgeGuides.iterator();
		if (egIter.hasNext()) {
			EdgeGuide lastEG = egIter.next();
			while (egIter.hasNext()) {
				EdgeGuide currEG = egIter.next();
				if (lastEG.edge.end == currEG.edge.end) {
					lastEG.join(currEG);
					egIter.remove();
				}
				lastEG = currEG;
			}
		}
		Collections.sort(currEdgeGuides, EdgeGuideQueue::compareForRemoveFirst);
	}

	boolean isEmpty() {
		return currEdgeGuides.isEmpty();
	}

	EdgeGuide removeFirst() {
		return currEdgeGuides.remove(0);
	}

	@Override
	public Iterator<EdgeGuide> iterator() {
		return currEdgeGuides.iterator();
	}

	static int compareForJoin(EdgeGuide eg1, EdgeGuide eg2) {
		ControlFlowEdge e1 = eg1.edge;
		ControlFlowEdge e2 = eg2.edge;
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

		ControlFlowEdge e1 = eg1.edge;
		ControlFlowEdge e2 = eg2.edge;
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
		s += getASTDepth(egi.edge.end.getControlFlowElement());
		s += ":";
		s += egi.edge.cfType;
		s += ")";
		return s;
	}
}
