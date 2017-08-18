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
package org.eclipse.n4js.flowgraphs.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.n4js.flowgraphs.factories.CFEMapper;
import org.eclipse.n4js.flowgraphs.factories.ListUtils;
import org.eclipse.n4js.n4JS.AbstractCaseClause;
import org.eclipse.n4js.n4JS.Block;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.DoStatement;
import org.eclipse.n4js.n4JS.EmptyStatement;
import org.eclipse.n4js.n4JS.ForStatement;
import org.eclipse.n4js.n4JS.IfStatement;
import org.eclipse.n4js.n4JS.SwitchStatement;
import org.eclipse.n4js.n4JS.TryStatement;
import org.eclipse.n4js.n4JS.WhileStatement;

public class ComplexNode implements ControlFlowable {
	final private ControlFlowElement astElement;
	final private Map<String, Node> nodeMap = new HashMap<>();

	private Node entry, exit;
	private boolean isLoopContainer = false;
	private boolean hasOuterBlockUse = false;

	public ComplexNode(ControlFlowElement astElement) {
		this.astElement = astElement;
	}

	public void connectInternalSucc(Node... nodes) {
		connectInternalSucc(false, Arrays.asList(nodes));
	}

	public void connectInternalSuccLC(Node... nodes) {
		connectInternalSucc(true, Arrays.asList(nodes));
	}

	public void connectInternalSucc(List<Node> nodes) {
		connectInternalSucc(false, nodes);
	}

	public void connectInternalSuccLC(List<Node> nodes) {
		connectInternalSucc(true, nodes);
	}

	private void connectInternalSucc(boolean loopCarried, List<Node> nodes) {
		nodes = ListUtils.filterNulls(nodes);

		Iterator<Node> it = nodes.iterator();
		if (!it.hasNext())
			return;

		Node n1 = it.next();
		while (it.hasNext()) {
			if (!nodeMap.values().contains(n1))
				throw new IllegalArgumentException("Node not child of complex node");

			Node n2 = n1;
			n1 = it.next();
			n2.addInternalSuccessors(n1);
		}

		if (!nodeMap.values().contains(n1))
			throw new IllegalArgumentException("Node not child of complex node");
	}

	public void addNode(Node node) {
		if (node == null)
			return;
		nodeMap.put(node.name, node);
	}

	public void setEntryNode(Node entryNode) {
		if (!nodeMap.values().contains(entryNode))
			throw new IllegalArgumentException("Node not child of complex node");
		this.entry = entryNode;
	}

	public void setExitNode(Node exitNode) {
		if (!nodeMap.values().contains(exitNode))
			throw new IllegalArgumentException("Node not child of complex node");
		this.exit = exitNode;
	}

	@Override
	public ControlFlowElement getControlFlowElement() {
		return CFEMapper.map(astElement);
	}

	@Override
	public Node getEntry() {
		return entry;
	}

	@Override
	public Node getExit() {
		return exit;
	}

	/**
	 * @return list of nodes not containing the exit node.
	 */
	public List<Node> getAllButExitNodes() {
		List<Node> mNodes = new LinkedList<>();
		mNodes.addAll(nodeMap.values());
		mNodes.remove(exit);
		return mNodes;
	}

	/**
	 * Returns true iff the represented {@link ControlFlowElement} is one of the following:
	 * <ul>
	 * <li>Block</li>
	 * <li>IfStatement</li>
	 * <li>ForStatement</li>
	 * <li>DoStatement</li>
	 * <li>WhileStatement</li>
	 * <li>TryStatement</li>
	 * <li>SwitchStatement</li>
	 * <li>AbstractCaseClause</li>
	 * <li>EmptyStatement</li>
	 * </ul>
	 */
	public boolean isControlElement() {
		ControlFlowElement cfe = getControlFlowElement();

		boolean isControlElement = false;
		isControlElement |= cfe instanceof Block;
		isControlElement |= cfe instanceof IfStatement;
		isControlElement |= cfe instanceof ForStatement;
		isControlElement |= cfe instanceof DoStatement;
		isControlElement |= cfe instanceof WhileStatement;
		isControlElement |= cfe instanceof TryStatement;
		isControlElement |= cfe instanceof SwitchStatement;
		isControlElement |= cfe instanceof AbstractCaseClause;
		isControlElement |= cfe instanceof EmptyStatement;
		return isControlElement;
	}

	@Override
	public String toString() {
		return "CN[" + astElement.getClass().getSimpleName() + "]";
	}

	public Set<DependencyEdge> getDependencyEdges() {
		Set<DependencyEdge> edges = new HashSet<>();
		for (Node n : getNodes()) {
			for (DependencyEdge e : n.startEdges)
				edges.add(e);
			for (DependencyEdge e : n.endEdges)
				edges.add(e);
		}
		return edges;
	}

	public Collection<Node> getNodes() {
		return nodeMap.values();
	}

	public Node getNode(String name) {
		return nodeMap.get(name);
	}

	public void setLoopContainer(boolean loopContainer) {
		this.isLoopContainer = loopContainer;
	}

	public boolean isLoopContainer() {
		return isLoopContainer;
	}

	public boolean hasOuterBlockUse() {
		return hasOuterBlockUse;
	}

	public void setHasOuterBlockUses(boolean b) {
		this.hasOuterBlockUse = b;
	}

}
