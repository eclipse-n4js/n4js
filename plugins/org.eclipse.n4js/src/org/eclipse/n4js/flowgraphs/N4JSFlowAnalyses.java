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

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.eclipse.n4js.flowgraphs.factories.ControlFlowGraphFactory;
import org.eclipse.n4js.flowgraphs.model.ComplexNode;
import org.eclipse.n4js.flowgraphs.model.FlowGraph;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.Script;

import com.google.inject.Singleton;

/**
 *
 */
@Singleton
public class N4JSFlowAnalyses {
	private FlowGraph cfg;

	/**
	 * Performs the control flow analyses for all {@link ControlFlowElement}s in the given {@link Script}.
	 * <p/>
	 * Never completes abruptly, i.e. throws an exception.
	 */
	public void perform(Script script) {
		Objects.requireNonNull(script);

		/*
		 * Protect ASTPostprocessing from failures of flow analyses.
		 */
		try {
			_perform(script);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	private void _perform(Script script) {
		cfg = ControlFlowGraphFactory.build(script);
	}

	/**
	 * @returns the underlying control flow graph
	 */
	public FlowGraph getControlFlowGraph() {
		return cfg;
	}

	/**
	 * @returns a list of all direct predecessors of cfe
	 */
	public List<ControlFlowElement> getPredecessors(ControlFlowElement cfe) {
		Objects.requireNonNull(cfe);

		ComplexNode cn = cfg.getComplexNode(cfe);
		List<ControlFlowElement> cfElems = cn.getExit().getPredecessors();
		return cfElems;
	}

	/**
	 * @returns a list of all direct successors of cfe
	 */
	public List<ControlFlowElement> getSuccessors(ControlFlowElement cfe) {
		Objects.requireNonNull(cfe);

		ComplexNode cn = cfg.getComplexNode(cfe);
		List<ControlFlowElement> cfElems = cn.getExit().getSuccessors();
		return cfElems;
	}

	/**
	 * @returns true iff cfeTo is a transitive successor of cfeFrom
	 */
	public boolean isTransitiveSuccessor(ControlFlowElement cfeFrom, ControlFlowElement cfeTo) {
		Objects.requireNonNull(cfeFrom);
		Objects.requireNonNull(cfeTo);

		if (cfeFrom == cfeTo) {
			return true;
		}

		List<ControlFlowElement> cfElems = getSuccessors(cfeFrom);
		for (ControlFlowElement cfe : cfElems) {
			if (isTransitiveSuccessor(cfe, cfeTo)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns the common predecessor of two {@link ControlFlowElement}s.
	 * <p/>
	 * Time: O(N)<br/>
	 * Space: O(N)<br/>
	 * N - Number of {@link ControlFlowElement}s connected to cfeA or cfeB
	 * <p/>
	 * The common predecessor is computed as follows. First, the CF graph is traversed beginning from cfeA backwards
	 * until an element is reached which has no predecessor. All elements that were visited during that traversion are
	 * marked. Second, analogous the CF graph is now traversed beginning from cfeB backwards until an element is reached
	 * which has no predecessor. If an already marked element can be found during that traversion, this element is
	 * supposed to be the common predecessor of cfeA and cfeB.
	 */
	public ControlFlowElement getCommonPredecessor(ControlFlowElement cfeA, ControlFlowElement cfeB) {
		Objects.requireNonNull(cfeA);
		Objects.requireNonNull(cfeB);

		// step 1: traverse all predecessors, beginning from cfeA: mark each
		Set<ControlFlowElement> marked = new HashSet<>();
		List<ControlFlowElement> curCFEs = new LinkedList<>();
		curCFEs.add(cfeA);
		while (!curCFEs.isEmpty()) {
			ControlFlowElement cfe = curCFEs.remove(0);
			marked.add(cfe);
			List<ControlFlowElement> preds = getPredecessors(cfe);
			curCFEs.addAll(preds);
		}

		// step 2: traverse all predecessors, beginning from cfeB: find mark (this is the common pred.)
		curCFEs.clear();
		curCFEs.add(cfeB);
		while (!curCFEs.isEmpty()) {
			ControlFlowElement cfe = curCFEs.remove(0);
			if (marked.contains(cfe)) {
				return cfe;
			}
			List<ControlFlowElement> preds = getPredecessors(cfe);
			curCFEs.addAll(preds);
		}

		return null;
	}

	/**
	 * Returns an identifier for all paths between two {@link ControlFlowElement}s.
	 * <p/>
	 * Time: O(N)<br/>
	 * Space: O(N)<br/>
	 * N - Number of {@link ControlFlowElement}s connected to cfeA or cfeB
	 * <p/>
	 * The path identifier is computed as follows. First, the CF graph is traversed beginning from cfeB backwards until
	 * an element is reached which has no predecessor. All elements that were visited during that traversion are saved
	 * in P. Second, the CF graph is now traversed beginning from cfeA forwards. All elements that are visited during
	 * that second traversion are part of the path identifier iff they are contained in P.
	 */
	public String getPathIdentifier(ControlFlowElement cfeFrom, ControlFlowElement cfeTo) {
		Objects.requireNonNull(cfeFrom);
		Objects.requireNonNull(cfeTo);

		LinkedHashSet<ControlFlowElement> predSet = new LinkedHashSet<>();

		List<ControlFlowElement> curCFEs = new LinkedList<>();
		curCFEs.add(cfeTo);
		while (!curCFEs.isEmpty()) {
			ControlFlowElement cfe = curCFEs.remove(0);
			predSet.add(cfe);
			List<ControlFlowElement> preds = getPredecessors(cfe);
			curCFEs.addAll(preds);
		}

		String pathString = "";
		curCFEs.clear();
		curCFEs.add(cfeFrom);
		while (!curCFEs.isEmpty()) {
			ControlFlowElement cfe = curCFEs.remove(0);
			if (predSet.contains(cfe)) {
				List<ControlFlowElement> succs = getSuccessors(cfe);
				curCFEs.addAll(succs);
				String nameID = FGUtils.getNameID(cfe);
				pathString += nameID + "->";
			}
		}

		pathString = pathString.substring(0, pathString.length() - 2);
		return pathString;
	}

	/**
	 * @return collection of all non-control {@link ControlFlowElement}s covered by the control flow analyses
	 */
	public Collection<ControlFlowElement> getAllElements() {
		List<ControlFlowElement> allCFEs = new LinkedList<>();
		for (ComplexNode cn : cfg.getAllComplexNodes()) {
			if (!cn.isControlElement()) {
				ControlFlowElement cfe = cn.getControlFlowElement();
				allCFEs.add(cfe);
			}
		}
		return allCFEs;
	}

}
