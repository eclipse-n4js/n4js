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

import java.util.LinkedList;
import java.util.Objects;
import java.util.TreeSet;

import org.eclipse.n4js.flowgraphs.ControlFlowType;
import org.eclipse.n4js.flowgraphs.model.ControlFlowEdge;
import org.eclipse.n4js.flowgraphs.model.Node;
import org.eclipse.n4js.flowgraphs.model.RepresentingNode;
import org.eclipse.n4js.n4JS.ControlFlowElement;

/**
 * A path connects two nodes by a set of subsequent edges. Paths where start=end that have no edges are also valid
 * paths. A path that does not connect two nodes can be created using {@link NoPath}.
 */
public class Path {
	final Node start;
	final Node end;
	final NextEdgesProvider nextEdgesProvider;
	final private LinkedList<ControlFlowEdge> edges;
	final private TreeSet<ControlFlowType> edgeTypes = new TreeSet<>();
	private String toString;

	/** Constructor */
	public Path(Node start, Node end, LinkedList<ControlFlowEdge> edges, NextEdgesProvider nextEdgesProvider) {
		Objects.requireNonNull(edges);
		this.start = start;
		this.end = end;
		this.edges = edges;
		this.nextEdgesProvider = nextEdgesProvider;
		init();
	}

	/** @return true, iff start and end nodes are connected by this path. */
	public boolean isConnecting() {
		return true;
	}

	/** @return true, iff start and end nodes are the same node. */
	public boolean isSelfReturning() {
		return start == end;
	}

	/** @return true, iff either start or end node is a node that is not representing a {@link ControlFlowElement}. */
	public boolean isInternal() {
		return !(start instanceof RepresentingNode) || !(end instanceof RepresentingNode);
	}

	/** @return the start {@link ControlFlowElement}. */
	public ControlFlowElement getStart() {
		return start.getRepresentedControlFlowElement();
	}

	/** @return the end {@link ControlFlowElement}. */
	public ControlFlowElement getEnd() {
		return end.getRepresentedControlFlowElement();
	}

	/** @return true, iff this path has no edges. */
	public boolean isEmpty() {
		return edges.isEmpty();
	}

	/** @return the set of all edge types on the path. */
	public TreeSet<ControlFlowType> getControlFlowTypes() {
		return edgeTypes;
	}

	/** @return the number of edges. */
	public int getLength() {
		return edges.size();
	}

	/** @return a unique identifier for this path */
	public Comparable<?> getIdentifier() {
		return toString;
	}

	/** Traverses the given path walker over this path. */
	public void accept(IPathWalker walker) {
		PathWalkerGuide walkerGuide = new PathWalkerGuide(walker);
		walkMe(walkerGuide);
	}

	private void init() {
		InitPathWalker initWalker = new InitPathWalker();
		walkMe(initWalker);
	}

	private void walkMe(IPathWalkerInternal walker) {
		walker.visitNode(start);
		for (ControlFlowEdge edge : edges) {
			Node bNode = nextEdgesProvider.getPrevNode(edge);
			Node fNode = nextEdgesProvider.getNextNode(edge);
			walker.visitEdge(bNode, fNode, edge);
			walker.visitNode(fNode);
		}
	}

	@Override
	public String toString() {
		return toString;
	}

	private class InitPathWalker implements IPathWalkerInternal {
		@Override
		public void init() {
			toString = "";
			edgeTypes.clear();
		}

		@Override
		public void visitNode(Node node) {
			toString += node.name;
		}

		@SuppressWarnings("hiding")
		@Override
		public void visitEdge(Node start, Node end, ControlFlowEdge edge) {
			toString += " -" + edge.cfType.name() + "-> ";
			edgeTypes.add(edge.cfType);
		}

		@Override
		public void finish() {
			// nothing to do
		}
	}

}
