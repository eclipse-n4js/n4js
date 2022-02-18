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
package org.eclipse.n4js.flowgraphs.model;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.n4js.flowgraphs.N4JSFlowAnalyser;
import org.eclipse.n4js.flowgraphs.factories.CFEMapper;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.Script;

/**
 * Stores information about all control flow graphs of one {@link Script}.
 */
public class FlowGraph {
	final private Script script;
	final private Set<ControlFlowElement> cfContainers;
	final private Map<ControlFlowElement, ComplexNode> cnMap; // this map can grow very large

	/** Constructor. */
	public FlowGraph(Script script, Set<ControlFlowElement> cfContainers,
			Map<ControlFlowElement, ComplexNode> cnMap) {

		this.script = script;
		this.cfContainers = cfContainers;
		this.cnMap = cnMap;
	}

	/** @return the script of this {@link FlowGraph} */
	public Script getScript() {
		return script;
	}

	/** @return the URI String of the script of this {@link FlowGraph} */
	public String getScriptName() {
		return script.eResource().getURI().toString();
	}

	/** @return the {@link ComplexNode} for the given {@link ControlFlowElement} cfe. */
	public ComplexNode getComplexNode(ControlFlowElement cfe) {
		cfe = CFEMapper.map(cfe);
		if (!cnMap.containsKey(cfe))
			return null;
		return cnMap.get(cfe);
	}

	/** see {@link N4JSFlowAnalyser#getContainer(ControlFlowElement)} */
	public ControlFlowElement getContainer(ControlFlowElement cfe) {
		ComplexNode cn = getComplexNode(cfe);
		return cn.getControlFlowContainer();
	}

	/** see {@link N4JSFlowAnalyser#getAllContainers()}. */
	public Collection<ControlFlowElement> getAllContainers() {
		// The order of containers is reversed.
		// This provokes fail-fast behavior regarding the assertion 'isVisited()'
		// in {@link DeadFlowContext.Backward#setDeadCode(Node)}
		List<ControlFlowElement> containerList = new LinkedList<>();
		for (ControlFlowElement cfe : cfContainers) {
			containerList.add(0, cfe);
		}
		return containerList;
	}

	/** Call to free memory */
	public void reset() {
		cfContainers.clear();
		cnMap.clear();
	}
}
