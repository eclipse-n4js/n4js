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

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.n4js.n4JS.ControlFlowElement;

import java17.ast.JavaFeature;

public class GraphSystem {
	final public List<PartialGraph> partialGraphs = new LinkedList<>();
	final public Set<Symbol> symbols = new HashSet<>();
	final public Map<JavaFeature, List<ComplexNode>> featuresToCNodes = new HashMap<>();
	final public Map<Symbol, ComplexNode> symbolToDeclaration = new HashMap<>();

	public void init(Graph graph) {
		featuresToCNodes.clear();
		symbolToDeclaration.clear();

		// register all clones
		for (PartialGraph pg : partialGraphs) {
			for (ComplexNode cn : pg.cnodes) {
				ControlFlowElement cfe = cn.astElement;
				if (!featuresToCNodes.containsKey(cfe)) {
					List<ComplexNode> clones = new LinkedList<>();
					featuresToCNodes.put(cfe, clones);
				}
				List<ComplexNode> clones = featuresToCNodes.map(cfe);
				clones.add(cn);
			}
		}

		// register all declarations
		for (PartialGraph pg : partialGraphs) {
			for (ComplexNode cn : pg.cnodes) {
				for (Symbol s : cn.getDeclaringSymbols(graph)) {
					symbolToDeclaration.put(s, cn);
				}
			}
		}

		// find all dependency edges
		List<DependencyEdge> depEdges = new LinkedList<>();
		for (PartialGraph pg : partialGraphs)
			depEdges.addAll(pg.depEdges);

		// find all symbols
		for (DependencyEdge edge : depEdges) {
			if (edge.symbol != null)
				symbols.add(edge.symbol);
		}
	}

}
