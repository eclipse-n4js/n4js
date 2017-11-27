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
import java.util.LinkedList;
import java.util.List;

import org.eclipse.n4js.flowgraphs.model.Node;

import com.google.common.collect.ComparisonChain;

/**
 * The {@link EdgeGuideQueue} is a sorted {@link List} of all {@link EdgeGuide}s that could be handled next. The sorting
 * guarantees that either (1) the head of the queue can always removed xor (2) several head elements can be merged to a
 * new {@link EdgeGuide} which can then be removed.
 */
public class EdgeGuideQueue {
	private final ArrayList<EdgeGuide> edgeGuideQueue = new ArrayList<>();

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
		Collections.sort(edgeGuideQueue, this::compareForRemoveFirst);
		return edgeGuideQueue.remove(0);
	}

	/** @return and removes the head of this queue */
	List<EdgeGuide> removeFirstJoinGuide() {
		LinkedList<EdgeGuide> guideGroup = new LinkedList<>();

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

		return Collections.emptyList();
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
		return ComparisonChain.start()
				.compare(eg1, eg2, EdgeGuideQueue::compareDeadFlowContext)
				.compare(eg1, eg2, EdgeGuideQueue::compareASTPosition)
				.result();
	}

	/** Prioritize live flow branches to get reliable dead code information in forward walkthrough. */
	private static int compareDeadFlowContext(EdgeGuide eg1, EdgeGuide eg2) {
		DeadFlowContext dfc1 = eg1.deadContext;
		DeadFlowContext dfc2 = eg2.deadContext;

		if (dfc1.isForwardDeadFlow() == dfc2.isForwardDeadFlow())
			return 0;
		return dfc1.isForwardDeadFlow() ? 1 : -1;
	}

	private static int compareASTPosition(EdgeGuide eg1, EdgeGuide eg2) {
		Node nextNode1 = eg1.getNextNode();
		Node nextNode2 = eg2.getNextNode();
		int p2 = nextNode2.astPosition;
		int p1 = nextNode1.astPosition;
		boolean isForward = eg1.edgeProvider.isForward();
		int posDiff = (isForward) ? p1 - p2 : p2 - p1;
		return posDiff;
	}

	@Override
	public String toString() {
		String s = "";
		for (EdgeGuide eg : edgeGuideQueue) {
			s += getEdgeString(eg) + "\n";
		}
		return s;
	}

	/** @return a {@link EdgeGuide} String that contains all sort properties */
	public String getEdgeString(EdgeGuide eg) {
		String egs = "[";
		egs += eg.isMerged() ? "J " : "j ";
		egs += eg.deadContext.isForwardDeadFlow() ? "D " : "d ";
		egs += eg.getNextNode().astPosition;
		egs += "] " + eg.getEdge().toString();
		return egs;
	}

}
