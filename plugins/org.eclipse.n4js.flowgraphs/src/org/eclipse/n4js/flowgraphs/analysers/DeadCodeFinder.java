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
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.flowgraphs.FGUtils;
import org.eclipse.n4js.flowgraphs.FlowEdge;
import org.eclipse.n4js.flowgraphs.analyses.GraphVisitor;
import org.eclipse.n4js.n4JS.Block;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.ExpressionStatement;
import org.eclipse.n4js.n4JS.FunctionOrFieldAccessor;
import org.eclipse.n4js.n4JS.ReturnStatement;
import org.eclipse.n4js.n4JS.Statement;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.util.TextRegion;

/**
 * Collects all reachable nodes and hence finds all unreachable nodes, alias <i>dead code</i>.
 */
public class DeadCodeFinder extends GraphVisitor {
	Set<ControlFlowElement> allForwardCFEs = new HashSet<>();
	Set<ControlFlowElement> allBackwardCFEs = new HashSet<>();
	Set<ControlFlowElement> allIslandsCFEs = new HashSet<>();
	Set<ControlFlowElement> allCatchBlocksCFEs = new HashSet<>();
	Set<ControlFlowElement> unreachableCFEs = new HashSet<>();

	/** Constructor */
	public DeadCodeFinder() {
		super(Direction.Forward, Direction.Backward, Direction.Islands, Direction.CatchBlocks);
	}

	@Override
	protected void initAll() {
		// nothing to do
	}

	@Override
	protected void init(Direction curDirection, ControlFlowElement curContainer) {
		// nothing to do
	}

	@Override
	protected void visit(ControlFlowElement cfe) {
		switch (getCurrentDirection()) {
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
	protected void terminate(Direction curDirection, ControlFlowElement curContainer) {
		// nothing to do
	}

	@Override
	protected void terminateAll() {
		unreachableCFEs.addAll(allBackwardCFEs);
		unreachableCFEs.removeAll(allForwardCFEs);
		unreachableCFEs.addAll(allIslandsCFEs);
		unreachableCFEs.removeAll(allCatchBlocksCFEs);
	}

	/** @returns all reachable {@link ControlFlowElement}s */
	public Set<ControlFlowElement> getReachableCFEs() {
		return allForwardCFEs;
	}

	/** @returns all unreachable {@link ControlFlowElement}s */
	public Set<ControlFlowElement> getUnreachableCFEs() {
		return unreachableCFEs;
	}

	/** @returns all {@link TextRegion}s of dead code */
	public Set<DeadCodeRegion> getDeadCodeRegions() {
		List<Set<ControlFlowElement>> deadCodeGroups = getDeadCodeGroups();
		widenDeadCodeRegions(deadCodeGroups);
		Set<DeadCodeRegion> deadCodeRegions = new HashSet<>();
		for (Set<ControlFlowElement> deadCodeGroup : deadCodeGroups) {
			DeadCodeRegion textRegion = getDeadCodeRegion(deadCodeGroup);
			deadCodeRegions.add(textRegion);
		}
		return deadCodeRegions;
	}

	private Set<ControlFlowElement> getWrappingControlElements(ControlFlowElement deadCodeElem) {
		Set<ControlFlowElement> wrappingControlElems = new HashSet<>();
		EObject cfeContainer = deadCodeElem.eContainer();
		if (cfeContainer instanceof ExpressionStatement && deadCodeElem instanceof Expression) {
			wrappingControlElems.add((ExpressionStatement) cfeContainer);
		}
		return wrappingControlElems;
	}

	/**
	 * Returns all {@link ControlFlowElement}s that are unreachable.
	 * <p>
	 * However, control elements (see {@link FGUtils#isControlElement(ControlFlowElement)}) are not part of the dead
	 * code regions.
	 */
	private List<Set<ControlFlowElement>> getDeadCodeGroups() {
		List<Set<ControlFlowElement>> allDeadCodeGroups = new LinkedList<>();

		HashSet<ControlFlowElement> someUnreachableCFEs = new HashSet<>();
		someUnreachableCFEs.addAll(allIslandsCFEs);
		Collection<Set<ControlFlowElement>> unreachablesInBlocks = separateOnTheirBlocks(someUnreachableCFEs);
		for (Set<ControlFlowElement> unreachablesInBlock : unreachablesInBlocks) {
			List<Set<ControlFlowElement>> deadCodeGroups = getDeadCodeGroups(unreachablesInBlock);
			allDeadCodeGroups.addAll(deadCodeGroups);
		}

		someUnreachableCFEs.clear();
		someUnreachableCFEs.addAll(allBackwardCFEs);
		someUnreachableCFEs.removeAll(allForwardCFEs);
		someUnreachableCFEs.removeAll(allCatchBlocksCFEs);
		unreachablesInBlocks = separateOnTheirBlocks(someUnreachableCFEs);
		for (Set<ControlFlowElement> unreachablesInBlock : unreachablesInBlocks) {
			List<Set<ControlFlowElement>> deadCodeGroups = getDeadCodeGroups(unreachablesInBlock);
			allDeadCodeGroups.addAll(deadCodeGroups);
		}
		return allDeadCodeGroups;
	}

	/**
	 * Separates the given set into sets where all {@link ControlFlowElement}s of each set have the same containing
	 * {@link Block}.
	 */
	private Collection<Set<ControlFlowElement>> separateOnTheirBlocks(HashSet<ControlFlowElement> unreachableElems) {
		Map<Block, Set<ControlFlowElement>> unreachablesMap = new HashMap<>();
		for (ControlFlowElement unreachable : unreachableElems) {
			Block cfeBlock = EcoreUtil2.getContainerOfType(unreachable, Block.class);
			if (!unreachablesMap.containsKey(cfeBlock)) {
				unreachablesMap.put(cfeBlock, new HashSet<>());
			}
			Set<ControlFlowElement> unreachableInBlock = unreachablesMap.get(cfeBlock);
			unreachableInBlock.add(unreachable);
		}

		return unreachablesMap.values();
	}

	private List<Set<ControlFlowElement>> getDeadCodeGroups(Set<ControlFlowElement> unreachablesInBlock) {
		List<Set<ControlFlowElement>> deadCodeGroups = new LinkedList<>();

		while (!unreachablesInBlock.isEmpty()) {
			ControlFlowElement oneCFE = unreachablesInBlock.iterator().next();
			Set<ControlFlowElement> nextDeadGroup = new HashSet<>();
			HashSet<ControlFlowElement> workListPreds = new HashSet<>();
			workListPreds.add(oneCFE);
			Set<ControlFlowElement> mergeWith = null;

			while (!workListPreds.isEmpty()) {
				Iterator<ControlFlowElement> workListIter = workListPreds.iterator();
				ControlFlowElement cfe = workListIter.next();
				workListIter.remove();

				// Add predecessors of the current cfe
				if (unreachablesInBlock.remove(cfe)) {
					nextDeadGroup.add(cfe);
					Set<ControlFlowElement> preds = flowAnalyses.getPredecessors(cfe);
					workListPreds.addAll(preds);
				}

				// Check if the current group is connected to an already found group
				for (Set<ControlFlowElement> deadCodeGroup : deadCodeGroups) {
					if (deadCodeGroup.contains(cfe)) {
						mergeWith = deadCodeGroup;
					}
				}
			}

			if (mergeWith == null) {
				deadCodeGroups.add(nextDeadGroup);
			} else {
				mergeWith.addAll(nextDeadGroup);
			}
		}

		return deadCodeGroups;
	}

	/**
	 * Add control elements (see {@link FGUtils#isControlElement(ControlFlowElement)}) to the dead code region, if
	 * possible.
	 */
	private void widenDeadCodeRegions(List<Set<ControlFlowElement>> deadCodeGroups) {
		for (Set<ControlFlowElement> deadCodeGroup : deadCodeGroups) {
			Set<ControlFlowElement> controlElements = new HashSet<>();
			Iterator<ControlFlowElement> deadCodeGroupIt = deadCodeGroup.iterator();
			while (deadCodeGroupIt.hasNext()) {
				ControlFlowElement deadCodeElem = deadCodeGroupIt.next();
				Set<ControlFlowElement> wrappingControlElems = getWrappingControlElements(deadCodeElem);
				controlElements.addAll(wrappingControlElems);
			}
			deadCodeGroup.addAll(controlElements);
		}
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

		ControlFlowElement containerCFE = flowAnalyses.getContainer(firstElement);
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

		/** @returns the containing element of this {@link DeadCodeRegion} */
		public ControlFlowElement getContainer() {
			return container;
		}

		/** @returns the last reachable {@link ControlFlowElement} before this {@link DeadCodeRegion}. Can be null. */
		public ControlFlowElement getReachablePredecessor() {
			return reachablePredecessor;
		}
	}
}
