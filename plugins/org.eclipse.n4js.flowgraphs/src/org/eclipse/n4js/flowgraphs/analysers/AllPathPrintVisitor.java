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

import java.util.LinkedList;
import java.util.List;

import org.eclipse.n4js.flowgraphs.FlowEdge;
import org.eclipse.n4js.flowgraphs.analysers.AllPathPrintVisitor.AllBranchPrintExplorer.AllBranchPrintWalker;
import org.eclipse.n4js.flowgraphs.analyses.BranchWalker;
import org.eclipse.n4js.flowgraphs.analyses.BranchWalkerInternal;
import org.eclipse.n4js.flowgraphs.analyses.GraphExplorer;
import org.eclipse.n4js.flowgraphs.analyses.GraphExplorerInternal;
import org.eclipse.n4js.flowgraphs.analyses.GraphVisitor;
import org.eclipse.n4js.n4JS.ControlFlowElement;

/**
 * Finds all control flow paths beginning from a given start element.
 */
class AllPathPrintVisitor extends GraphVisitor {

	class AllBranchPrintExplorer extends GraphExplorer {

		class AllBranchPrintWalker extends BranchWalker {
			String curString = "";

			@Override
			protected void visit(ControlFlowElement cfe) {
				curString += cfe.toString();
			}

			@Override
			protected void visit(FlowEdge edge) {
				curString += " -> ";
			}

			@Override
			protected AllBranchPrintWalker forkPath() {
				return new AllBranchPrintWalker();
			}
		}

		@Override
		protected BranchWalker joinBranches(List<BranchWalker> branchWalkers) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		protected BranchWalkerInternal firstBranchWalker() {
			return new AllBranchPrintWalker();
		}
	}

	List<String> getBranchStrings() {
		List<String> branchStrings = new LinkedList<>();
		for (GraphExplorerInternal app : getActivatedExplorers()) {
			for (BranchWalkerInternal ap : app.getAllBranches()) {
				AllBranchPrintWalker printPath = (AllBranchPrintWalker) ap;
				branchStrings.add(printPath.curString);
			}
		}
		return branchStrings;
	}
}