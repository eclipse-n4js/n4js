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
 * The {@link EdgeGuideQueue} is a sorted {@link List} of all {@link EdgeGuide}s that could be handled next. The sorting
 * guarantees that either (1) the head of the queue can always removed xor (2) several head elements can be merged to a
 * new {@link EdgeGuide} which can then be removed.
 */
public class EdgeGuideQueue {
	private final EdgeGuideWorklist parent;
	private final ArrayList<EdgeGuide> edgeGuideQueue = new ArrayList<>();

	/** Constructor */
	EdgeGuideQueue(EdgeGuideWorklist guideWorklist) {
		this.parent = guideWorklist;
	}

	/** Clears the queue */
	void clear() {
		edgeGuideQueue.clear();
	}

	/** Adds an {@link EdgeGuide} to this queue */
	void add(EdgeGuide edgeGuide) {
		edgeGuideQueue.add(edgeGuide);
		Collections.sort(edgeGuideQueue, this::compareForRemoveFirst);
	}

	/** Adds several {@link EdgeGuide}s to this queue */
	void addAll(Collection<EdgeGuide> edgeGuides) {
		edgeGuideQueue.addAll(edgeGuides);
		Collections.sort(edgeGuideQueue, this::compareForRemoveFirst);
	}

	/** @return true iff this queue is empty */
	boolean isEmpty() {
		return edgeGuideQueue.isEmpty();
	}

	/** @return and removes the head of this queue */
	EdgeGuide removeFirst() {
		// Collections.sort(edgeGuideQueue, this::compareForRemoveFirst);
		return edgeGuideQueue.remove(0);
	}

	/** @return and removes the head of this queue */
	LinkedList<EdgeGuide> removeFirstJoinGuide() {
		LinkedList<EdgeGuide> guideGroup = new LinkedList<>();
		// Collections.sort(edgeGuideQueue, this::compareForRemoveFirst);

		for (Iterator<EdgeGuide> iter = edgeGuideQueue.iterator(); iter.hasNext();) {
			EdgeGuide eg = iter.next();
			if (!guideGroup.isEmpty() && !isJoinGroup(guideGroup.getFirst(), eg)) {
				break;
			}
			guideGroup.add(eg);
			if (guideGroup.size() > 1) {
				iter.remove();
			}
		}

		if (guideGroup.size() > 1) {
			edgeGuideQueue.remove(guideGroup.getFirst());
			return guideGroup;
		}

		return Lists.newLinkedList();
	}

	/** @return an {@link Iterable} over all {@link EdgeGuide} in the queue */
	Iterable<EdgeGuide> getIterator() {
		return edgeGuideQueue;
	}

	private boolean isJoinGroup(EdgeGuide eg1, EdgeGuide eg2) {
		Node nextN1 = eg1.getNextNode();
		Node nextN2 = eg2.getNextNode();
		boolean isJoinGroup = nextN1 == nextN2 && eg1.finallyContext.equals(eg2.finallyContext);
		return isJoinGroup;
	}

	private int compareForRemoveFirst(EdgeGuide eg1, EdgeGuide eg2) {
		ControlFlowEdge e1 = eg1.getEdge();
		ControlFlowEdge e2 = eg2.getEdge();
		Node nextNode1 = eg1.getNextNode();
		Node nextNode2 = eg2.getNextNode();
		ControlFlowElement cfe1 = nextNode1.getControlFlowElement();
		ControlFlowElement cfe2 = nextNode2.getControlFlowElement();
		ControlFlowType cft1 = e1.cfType;
		ControlFlowType cft2 = e2.cfType;

		return ComparisonChain.start()
				.compare(eg1, eg2, EdgeGuideQueue::compareJoined)
				.compare(cfe1, cfe2, EdgeGuideQueue::compareDepth)
				.compare(eg1, eg2, EdgeGuideQueue::compareInternalPosition)
				.compare(e1, e2, this::compareVisited)
				.compare(eg1, eg2, EdgeGuideQueue::compareDeadFlowContext)
				.compare(cft1, cft2, EdgeGuideQueue::compareEdgeTypes)
				.compare(eg1, eg2, EdgeGuideQueue::compareJoining)
				.compare(nextNode1.hashCode(), nextNode2.hashCode())
				.compare(e1.hashCode(), e2.hashCode())
				.result();
	}

	private static int compareJoined(EdgeGuide eg1, EdgeGuide eg2) {
		if (eg1.isMerged() == eg2.isMerged()) {
			return 0;
		}
		return eg1.isMerged() ? 1 : -1;
	}

	private static int compareDepth(ControlFlowElement cfe1, ControlFlowElement cfe2) {
		int d1 = getASTDepth(cfe1);
		int d2 = getASTDepth(cfe2);
		if (d1 != d2) {
			return d2 - d1;
		}
		return 0;
	}

	private int compareVisited(ControlFlowEdge e1, ControlFlowEdge e2) {
		boolean isVisited1 = parent.edgeVisited(e1);
		boolean isVisited2 = parent.edgeVisited(e2);
		if (isVisited1 == isVisited2) {
			return 0;
		}
		return isVisited1 ? -1 : 1;
	}

	private static int compareJoining(EdgeGuide eg1, EdgeGuide eg2) {
		Node nextNode1 = eg1.getNextNode();
		Node nextNode2 = eg2.getNextNode();
		boolean isJoining1 = eg1.edgeProvider.getPlainPrevEdges(nextNode1).size() > 1;
		boolean isJoining2 = eg2.edgeProvider.getPlainPrevEdges(nextNode2).size() > 1;
		if (isJoining1 == isJoining2) {
			return 0;
		}
		return isJoining1 ? 1 : -1;
	}

	private static int compareInternalPosition(EdgeGuide eg1, EdgeGuide eg2) {
		Node nextNode1 = eg1.getNextNode();
		Node nextNode2 = eg2.getNextNode();
		if (nextNode1.getControlFlowElement() != nextNode2.getControlFlowElement()) {
			return 0;
		}
		if (eg1.edgeProvider.isForward()) {
			return nextNode1.internalPosition - nextNode2.internalPosition;
		} else {
			return nextNode2.internalPosition - nextNode1.internalPosition;
		}
	}

	private static int compareDeadFlowContext(EdgeGuide eg1, EdgeGuide eg2) {
		DeadFlowContext dfc1 = eg1.deadContext;
		DeadFlowContext dfc2 = eg2.deadContext;

		if (dfc1.isForwardDeadFlow() == dfc2.isForwardDeadFlow())
			return 0;
		return dfc1.isForwardDeadFlow() ? 1 : -1;
	}

	private static Map<ControlFlowType, Integer> cftOrderMap = new EnumMap<>(ControlFlowType.class);
	static {
		cftOrderMap.put(ControlFlowType.Successor, 10);
		cftOrderMap.put(ControlFlowType.DeadCode, 10);
		cftOrderMap.put(ControlFlowType.Repeat, 10);
		cftOrderMap.put(ControlFlowType.Continue, 7);
		cftOrderMap.put(ControlFlowType.Break, 6);
		cftOrderMap.put(ControlFlowType.Throw, 5);
		cftOrderMap.put(ControlFlowType.Return, 4);
		cftOrderMap.put(ControlFlowType.Exit, 3);
		cftOrderMap.put(ControlFlowType.CatchesAll, 0);
		cftOrderMap.put(ControlFlowType.CatchesErrors, 0);
	}

	private static int compareEdgeTypes(ControlFlowType cft1, ControlFlowType cft2) {
		if (cft1 == cft2)
			return 0;

		return cftOrderMap.get(cft2) - cftOrderMap.get(cft1);
	}

	private static int getASTDepth(EObject eObj) {
		int i;
		for (i = 0; eObj != null; i++) {
			eObj = eObj.eContainer();
		}
		return i;
	}

	@Override
	public String toString() {
		return edgeGuideQueue.isEmpty() ? "empty" : edgeGuideQueue.toString();
	}

}
