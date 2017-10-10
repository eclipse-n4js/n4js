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
package org.eclipse.n4js.flowgraphs.analysers;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.flowgraphs.FGUtils;
import org.eclipse.n4js.flowgraphs.FlowEdge;
import org.eclipse.n4js.flowgraphs.analyses.GraphVisitor;
import org.eclipse.n4js.flowgraphs.factories.CFEMapper;
import org.eclipse.n4js.n4JS.Block;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.ExpressionStatement;
import org.eclipse.n4js.n4JS.FunctionOrFieldAccessor;
import org.eclipse.n4js.n4JS.ReturnStatement;
import org.eclipse.n4js.n4JS.Statement;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.util.TextRegion;

/**
 * Collects all reachable nodes and hence finds all unreachable nodes, alias <i>dead code</i>. Provides methods to
 * determine if a given {@link ControlFlowElement} is dead code, or to compute a minimal set of {@link TextRegion}s of
 * dead code.
 */
public class DeadCodeVisitor extends GraphVisitor {
	Set<ControlFlowElement> allForwardCFEs = new HashSet<>();
	Set<ControlFlowElement> allBackwardCFEs = new HashSet<>();
	Set<ControlFlowElement> allIslandsCFEs = new HashSet<>();
	Set<ControlFlowElement> allCatchBlocksCFEs = new HashSet<>();
	Set<ControlFlowElement> unreachableCFEs = new HashSet<>();

	/** Constructor */
	public DeadCodeVisitor() {
		super(Mode.Forward, Mode.Backward, Mode.Islands, Mode.CatchBlocks);
	}

	@Override
	protected void initialize() {
		// nothing to do
	}

	@Override
	protected void initializeMode(Mode curMode, ControlFlowElement curContainer) {
		// nothing to do
	}

	@Override
	protected void visit(ControlFlowElement cfe) {
		switch (getCurrentMode()) {
		case Forward:
			allForwardCFEs.add(cfe);
			break;
		case Backward:
			allBackwardCFEs.add(cfe);
			break;
		case Islands:
			allIslandsCFEs.add(cfe);
			break;
		case CatchBlocks:
			allCatchBlocksCFEs.add(cfe);
			break;
		}
	}

	@Override
	protected void visit(ControlFlowElement start, ControlFlowElement end, FlowEdge edge) {
		// nothing to do
	}

	@Override
	protected void terminateMode(Mode curMode, ControlFlowElement curContainer) {
		// nothing to do
	}

	@Override
	protected void terminate() {
		unreachableCFEs.addAll(allBackwardCFEs);
		unreachableCFEs.removeAll(allForwardCFEs);
		unreachableCFEs.addAll(allIslandsCFEs);
		unreachableCFEs.removeAll(allCatchBlocksCFEs);
	}

	/** @return all reachable {@link ControlFlowElement}s */
	public Set<ControlFlowElement> getReachableCFEs() {
		return allForwardCFEs;
	}

	/** @return all unreachable {@link ControlFlowElement}s */
	public Set<ControlFlowElement> getUnreachableCFEs() {
		return unreachableCFEs;
	}

	/**
	 * This method deals with the fact that {@link Statement}s are not represented in the control flow graph.
	 *
	 * @return true iff the given {@link ControlFlowElement} is dead code.
	 */
	public boolean isDeadCode(ControlFlowElement cfe) {
		cfe = CFEMapper.map(cfe);

		if (FGUtils.isControlStatement(cfe)) {
			Set<ControlFlowElement> succs = flowAnalyzer.getSuccessors(cfe);
			for (ControlFlowElement succ : succs) {
				if (!isDeadCode(succ)) {
					return false;
				}
			}
			return true;
		}

		if (allForwardCFEs.contains(cfe)) {
			return false;
		}
		Set<ControlFlowElement> preds = flowAnalyzer.getPredecessorsSkipInternal(cfe);
		if (preds.isEmpty()) {
			return true;
		}

		Set<ControlFlowElement> visited = new HashSet<>();
		while (!preds.isEmpty()) {
			ControlFlowElement pred = preds.iterator().next();
			preds.remove(pred);
			if (visited.contains(pred))
				continue;

			if (allForwardCFEs.contains(pred)) {
				return false;
			}
			preds.addAll(flowAnalyzer.getPredecessorsSkipInternal(pred));
			visited.add(pred);
		}
		return true;
	}

	/** @return all {@link TextRegion}s of dead code */
	public Set<DeadCodeRegion> getDeadCodeRegions() {
		Collection<Set<ControlFlowElement>> deadCodeGroups = separateOnTheirBlocks(unreachableCFEs);
		Set<DeadCodeRegion> deadCodeRegions = new HashSet<>();
		for (Set<ControlFlowElement> deadCodeGroup : deadCodeGroups) {
			DeadCodeRegion textRegion = getDeadCodeRegion(deadCodeGroup);
			deadCodeRegions.add(textRegion);
		}
		return deadCodeRegions;
	}

	/**
	 * Separates the given set into sets where all {@link ControlFlowElement}s of each set have the same containing
	 * {@link Block}.
	 * <p>
	 * Note that the assumption is:<br/>
	 * <i>No block can contain more than one single dead code region.</i>
	 */
	private Collection<Set<ControlFlowElement>> separateOnTheirBlocks(Set<ControlFlowElement> unreachableElems) {
		Map<Block, Set<ControlFlowElement>> unreachablesMap = new HashMap<>();
		for (ControlFlowElement unreachableElem : unreachableElems) {
			HashSet<ControlFlowElement> moreUnreachableElems = new HashSet<>();
			Block cfeBlock = getReachableBlock(unreachableElems, unreachableElem, moreUnreachableElems);
			if (cfeBlock == null)
				continue;

			if (!unreachablesMap.containsKey(cfeBlock)) {
				unreachablesMap.put(cfeBlock, new HashSet<>());
			}
			Set<ControlFlowElement> unreachableInBlock = unreachablesMap.get(cfeBlock);
			unreachableInBlock.add(unreachableElem);
			unreachableInBlock.addAll(moreUnreachableElems);
		}

		return unreachablesMap.values();
	}

	/** Finds the nearest reachable {@link Block} of the given {@link ControlFlowElement} */
	private Block getReachableBlock(Set<ControlFlowElement> unreachableElems, ControlFlowElement unreachableElem,
			Set<ControlFlowElement> moreUnreachableElems) {

		EObject elemContainer = unreachableElem.eContainer();
		if (elemContainer instanceof ExpressionStatement) {
			moreUnreachableElems.add((ExpressionStatement) elemContainer);
		}

		Block block = EcoreUtil2.getContainerOfType(unreachableElem, Block.class);
		if (block == null) // can be null in case of broken ASTs
			return null;

		EObject blockContainer = block.eContainer();
		boolean isDeadContainer = blockContainer instanceof ControlFlowElement;
		isDeadContainer &= isDeadContainer && FGUtils.isControlStatement((ControlFlowElement) blockContainer);
		isDeadContainer &= isDeadContainer && isDeadCode((ControlFlowElement) blockContainer);

		if (isDeadContainer) {
			ControlFlowElement cfe = (ControlFlowElement) blockContainer;
			moreUnreachableElems.add(cfe);
			return getReachableBlock(unreachableElems, cfe, moreUnreachableElems);
		}

		return block;
	}

	private DeadCodeRegion getDeadCodeRegion(Set<ControlFlowElement> deadCodeGroup) {
		int startIdx = Integer.MAX_VALUE;
		int endIdx = 0;
		int firstElementOffset = Integer.MAX_VALUE;
		ControlFlowElement firstElement = null;

		for (ControlFlowElement deadCodeElement : deadCodeGroup) {
			ICompositeNode compNode = NodeModelUtils.findActualNodeFor(deadCodeElement);
			int elemStartIdx = compNode.getOffset();
			int elemEndIdx = elemStartIdx + compNode.getLength();
			startIdx = Math.min(startIdx, elemStartIdx);
			endIdx = Math.max(endIdx, elemEndIdx);
			if (elemStartIdx < firstElementOffset) {
				firstElementOffset = elemStartIdx;
				firstElement = deadCodeElement;
			}
		}

		ControlFlowElement containerCFE = flowAnalyzer.getContainer(firstElement);
		ControlFlowElement reachablePredecessor = findPrecedingStatement(firstElement);
		return new DeadCodeRegion(startIdx, endIdx - startIdx, containerCFE, reachablePredecessor);
	}

	private ControlFlowElement findPrecedingStatement(ControlFlowElement cfe) {
		ControlFlowElement precedingStatement = null;
		Statement stmt = EcoreUtil2.getContainerOfType(cfe, Statement.class);
		if (stmt != null) {
			EObject stmtContainer = stmt.eContainer();
			if (stmtContainer != null && stmtContainer instanceof Block) {
				Block block = (Block) stmtContainer;
				EList<Statement> stmts = block.getStatements();
				int index = stmts.indexOf(stmt);
				if (index > 0) {
					precedingStatement = stmts.get(index - 1);
				}
			}
		}
		return precedingStatement;
	}

	/**
	 * A dead code region is a {@link TextRegion} that also knows about its containing element such as
	 * {@link FunctionOrFieldAccessor}, and the its last reachable preceding statement such as a
	 * {@link ReturnStatement}.
	 */
	static public class DeadCodeRegion extends TextRegion {
		final ControlFlowElement reachablePredecessor;
		final ControlFlowElement container;

		DeadCodeRegion(int offset, int length, ControlFlowElement container, ControlFlowElement reachablePredecessor) {
			super(offset, length);
			this.container = container;
			this.reachablePredecessor = reachablePredecessor;
		}

		/** @return the containing element of this {@link DeadCodeRegion} */
		public ControlFlowElement getContainer() {
			return container;
		}

		/** @return the last reachable {@link ControlFlowElement} before this {@link DeadCodeRegion}. Can be null. */
		public ControlFlowElement getReachablePredecessor() {
			return reachablePredecessor;
		}
	}
}
