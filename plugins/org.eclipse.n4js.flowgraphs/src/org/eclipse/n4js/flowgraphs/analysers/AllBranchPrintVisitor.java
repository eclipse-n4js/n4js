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

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.n4js.flowgraphs.FGUtils;
import org.eclipse.n4js.flowgraphs.FlowEdge;
import org.eclipse.n4js.flowgraphs.analyses.BranchWalker;
import org.eclipse.n4js.flowgraphs.analyses.BranchWalkerInternal;
import org.eclipse.n4js.flowgraphs.analyses.GraphExplorer;
import org.eclipse.n4js.flowgraphs.analyses.GraphExplorerInternal;
import org.eclipse.n4js.flowgraphs.analyses.GraphVisitor;
import org.eclipse.n4js.n4JS.ControlFlowElement;

/**
 * Finds all control flow paths beginning from a given start element.
 */
public class AllBranchPrintVisitor extends GraphVisitor {
	final ControlFlowElement startElement;

	/**
	 * Constructor.
	 */
	public AllBranchPrintVisitor() {
		this(null, null, Mode.Forward);
	}

	/**
	 * Constructor.
	 * <p>
	 * Creates a {@link AllBranchPrintVisitor} in direction {@literal Direction.Forward}.
	 *
	 * @param container
	 *            the container of which all paths are computed. Must not be null.
	 */
	public AllBranchPrintVisitor(ControlFlowElement container) {
		this(container, null, Mode.Forward);
	}

	/**
	 * Constructor.
	 * <p>
	 * Creates a {@link AllBranchPrintVisitor} in direction {@literal Direction.Forward}.
	 *
	 * @param container
	 *            the container of which all paths are computed. Must not be null.
	 * @param startElement
	 *            if not null, all paths are found beginning at the startElement. Otherwise, all paths are found
	 *            beginning from the first element of one of the containers in the script.
	 */
	public AllBranchPrintVisitor(ControlFlowElement container, ControlFlowElement startElement) {
		this(container, startElement, Mode.Forward);
	}

	/**
	 * Constructor.
	 *
	 * @param container
	 *            the container of which all paths are computed. Must not be null.
	 * @param startElement
	 *            if not null, all paths are found beginning at the startElement. Otherwise, all paths are found
	 *            beginning from the first element of one of the containers in the script.
	 * @param direction
	 *            the direction of the paths. Use only with {@literal Direction.Forward} and
	 *            {@literal Direction.Backward}
	 */
	public AllBranchPrintVisitor(ControlFlowElement container, ControlFlowElement startElement, Mode direction) {
		super(container, direction);
		this.startElement = startElement;
	}

	@Override
	protected void initializeMode(Mode curDirection, ControlFlowElement curContainer) {
		if (startElement == null) {
			super.requestActivation(new AllBranchPrintExplorer());
		}
	}

	@Override
	protected void visit(ControlFlowElement cfe) {
		if (startElement != null && startElement == cfe && getActivatedExplorerCount() == 0) {
			super.requestActivation(new AllBranchPrintExplorer());
		}
	}

	/** @return all found paths as strings */
	public List<String> getPathStrings() {
		List<String> pathStrings = new LinkedList<>();
		for (GraphExplorerInternal gei : getActivatedExplorers()) {
			BranchWalkerInternal firstBranch = gei.getFirstBranch();
			AllBranchPrintWalker firstBranchPW = (AllBranchPrintWalker) firstBranch;
			List<String> explPathStrings = getPathStrings(firstBranchPW, firstBranch.isDeadCode());
			pathStrings.addAll(explPathStrings);
		}
		return pathStrings;
	}

	/** @return all found branches as strings */
	public List<String> getBranchStrings() {
		List<String> pathStrings = new LinkedList<>();
		for (GraphExplorerInternal gei : getActivatedExplorers()) {
			for (BranchWalkerInternal ap : gei.getAllBranches()) {
				AllBranchPrintWalker printPath = (AllBranchPrintWalker) ap;
				pathStrings.add(printPath.getCompleteBranchString());
			}
		}
		return pathStrings;
	}

	static private List<String> getPathStrings(AllBranchPrintWalker bw, boolean isDead) {
		List<String> allStrings = new LinkedList<>();
		boolean visitedSuccessor = false;
		for (BranchWalker succ : bw.getSuccessors()) {
			if (isDead == succ.isDeadCode()) {
				visitedSuccessor = true;
				List<String> succStrings = getPathStrings((AllBranchPrintWalker) succ, isDead);
				for (String succString : succStrings) {
					String prefixedString = bw.branchString + succString;
					allStrings.add(prefixedString);
				}
			}
		}
		if (!visitedSuccessor) {
			allStrings.add(bw.branchString);
		}

		return allStrings;
	}

	static class AllBranchPrintExplorer extends GraphExplorer {

		AllBranchPrintExplorer() {
			super(Quantor.ForAllBranches);
		}

		@Override
		protected AllBranchPrintWalker firstBranchWalker() {
			return new AllBranchPrintWalker();
		}

		@Override
		protected BranchWalker joinBranches(List<BranchWalker> branchWalkers) {
			return new AllBranchPrintWalker();
		}

	}

	static class AllBranchPrintWalker extends BranchWalker {
		private String branchString = "";

		AllBranchPrintWalker() {
		}

		@Override
		protected void visit(ControlFlowElement cfe) {
			branchString += FGUtils.getSourceText(cfe);
		}

		@Override
		protected void visit(FlowEdge edge) {
			branchString += " -> ";
			// branchString += " --" + edge.toString() + "--> ";
		}

		@Override
		protected AllBranchPrintWalker forkPath() {
			return new AllBranchPrintWalker();
		}

		String getCompleteBranchString() {
			String cbs = "";
			cbs += getBranchLetter(this) + getNumber() + ": ";
			cbs += getBranchNames(this.getPredecessors());
			cbs += branchString;
			cbs += getBranchNames(this.getSuccessors());
			return cbs;
		}

		private String getBranchNames(List<BranchWalker> walkers) {
			String cbs = "";
			if (!walkers.isEmpty()) {
				cbs += "[";
				Collections.sort(walkers, AllBranchPrintWalker::compareBranches);
				boolean addedBW = false;
				for (BranchWalker bw : walkers) {
					cbs += getBranchLetter(bw) + bw.getNumber() + "|";
					addedBW = true;
				}
				if (!addedBW) {
					return "";
				}
				cbs = cbs.substring(0, cbs.length() - 1) + "]";
			}
			return cbs;
		}

		private String getBranchLetter(BranchWalker bw) {
			return bw.isDeadCode() ? "b" : "B";
		}

		static int compareBranches(BranchWalkerInternal b1, BranchWalkerInternal b2) {
			return b1.getNumber() - b2.getNumber();
		}

		@Override
		public String toString() {
			return branchString;
		}
	}

}
