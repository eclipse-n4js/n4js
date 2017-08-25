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
package org.eclipse.n4js.flowgraphs;

import java.util.LinkedList;
import java.util.Objects;
import java.util.TreeSet;

import org.eclipse.n4js.flowgraphs.analyses.GraphPathWalker;
import org.eclipse.n4js.flowgraphs.analyses.IGraphPathWalker;
import org.eclipse.n4js.flowgraphs.model.ControlFlowEdge;
import org.eclipse.n4js.flowgraphs.model.Node;
import org.eclipse.n4js.flowgraphs.model.RepresentingNode;
import org.eclipse.n4js.n4JS.ControlFlowElement;

/**
 *
 */
public class Path {
	final Node start;
	final Node end;
	final boolean forward;
	final private LinkedList<ControlFlowEdge> edges;
	final private TreeSet<ControlFlowType> edgeTypes = new TreeSet<>();
	private String toString;

	public Path(Node start, Node end, LinkedList<ControlFlowEdge> edges, boolean forward) {
		Objects.requireNonNull(edges);
		this.start = start;
		this.end = end;
		this.edges = edges;
		this.forward = forward;
		init();
	}

	public boolean isConnecting() {
		return true;
	}

	public boolean isSelfReturning() {
		return start == end;
	}

	public boolean isInternal() {
		return !(start instanceof RepresentingNode) || !(end instanceof RepresentingNode);
	}

	public ControlFlowElement getStart() {
		return start.getRepresentedControlFlowElement();
	}

	public ControlFlowElement getEnd() {
		return end.getRepresentedControlFlowElement();
	}

	public boolean isEmpty() {
		return edges.isEmpty();
	}

	public TreeSet<ControlFlowType> getControlFlowTypes() {
		return edgeTypes;
	}

	public int getLength() {
		return edges.size();
	}

	public Comparable<?> getIdentifier() {
		return toString;
	}

	@Override
	public String toString() {
		return toString;
	}

	public void accept(IPathWalker walker) {
		PathWalkerGuide walkerGuide = new PathWalkerGuide(walker);
		accept(walkerGuide);
	}

	void accept(IGraphPathWalker walker) {
		walkMe(walker);
	}

	private void init() {
		InitPathWalker initWalker = new InitPathWalker();
		accept(initWalker);
	}

	private void walkMe(IGraphPathWalker walker) {
		walker.visitNode(start);
		for (ControlFlowEdge edge : edges) {
			Node fNode = getForwardNode(edge);
			Node bNode = getBackwardNode(edge);
			walker.visitEdge(bNode, fNode, edge.cfType);
			walker.visitNode(fNode);
		}
	}

	private Node getForwardNode(ControlFlowEdge edge) {
		if (forward) {
			return edge.end;
		} else {
			return edge.start;
		}
	}

	private Node getBackwardNode(ControlFlowEdge edge) {
		if (forward) {
			return edge.start;
		} else {
			return edge.end;
		}
	}

	private class InitPathWalker extends GraphPathWalker {
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
		public void visitEdge(Node start, Node end, ControlFlowType cfType) {
			toString += " -" + cfType.name() + "-> ";
			edgeTypes.add(cfType);
		}
	}

}
