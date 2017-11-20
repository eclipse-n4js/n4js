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

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.n4js.flowgraphs.analyses.BranchWalker;
import org.eclipse.n4js.flowgraphs.analyses.BranchWalkerInternal;
import org.eclipse.n4js.flowgraphs.analyses.GraphExplorer;
import org.eclipse.n4js.flowgraphs.analyses.GraphVisitor;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.VariableDeclaration;

/**
 *
 */
public class CheckVariableGraphVisitor extends GraphVisitor {
	static int branCount = 0;
	final CheckVariablePathExplorer cvpe;

	/** Constructor */
	public CheckVariableGraphVisitor() {
		super(Mode.Backward);
		cvpe = new CheckVariablePathExplorer();
	}

	@Override
	protected void initializeMode(Mode curMode, ControlFlowElement curContainer) {
		super.requestActivation(cvpe);
	}

	@Override
	protected void terminate() {
		// System.out.println("branches: " + branCount);
	}

	/** @return all {@link IdentifierRef}s that are used before declared */
	public List<IdentifierRef> getUsedButNotDeclaredIdentifierRefs() {
		List<IdentifierRef> idRefs = new LinkedList<>();
		for (BranchWalkerInternal activeBW : cvpe.getActiveBranches()) {
			CheckVariablePathWalker cvbw = (CheckVariablePathWalker) activeBW;
			for (List<IdentifierRef> idRefList : cvbw.checkLists.values()) {
				idRefs.addAll(idRefList);
			}
		}
		return idRefs;
	}

	class CheckVariablePathExplorer extends GraphExplorer {

		@Override
		protected BranchWalkerInternal firstBranchWalker() {
			return new CheckVariablePathWalker();
		}

		@Override
		protected BranchWalker joinBranches(List<BranchWalker> branchWalkers) {
			CheckVariablePathWalker joinedWalker = new CheckVariablePathWalker();
			for (BranchWalker bW : branchWalkers) {
				CheckVariablePathWalker cvbw = (CheckVariablePathWalker) bW;
				joinedWalker.checkLists.putAll(cvbw.checkLists);
			}
			return joinedWalker;
		}
	}

	class CheckVariablePathWalker extends BranchWalker {
		final Map<VariableDeclaration, List<IdentifierRef>> checkLists = new HashMap<>();

		CheckVariablePathWalker() {
			branCount++;
		}

		@Override
		protected CheckVariablePathWalker forkPath() {
			CheckVariablePathWalker newBranch = new CheckVariablePathWalker();
			newBranch.checkLists.putAll(checkLists);
			return newBranch;
		}

		@Override
		protected void visit(ControlFlowElement cfe) {
			if (cfe instanceof VariableDeclaration) {
				checkLists.put((VariableDeclaration) cfe, new LinkedList<>());
			} else if (cfe instanceof IdentifierRef) {
				IdentifierRef ir = (IdentifierRef) cfe;

				if (!checkLists.containsKey(ir.getId())) {
					return;
				}

				List<IdentifierRef> idRefs = checkLists.get(ir.getId());
				idRefs.add(ir);
			}
		}
	}

}
