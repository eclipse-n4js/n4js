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

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.n4js.flowgraphs.analyses.BranchWalker;
import org.eclipse.n4js.flowgraphs.analyses.BranchWalkerInternal;
import org.eclipse.n4js.flowgraphs.analyses.GraphExplorer;
import org.eclipse.n4js.flowgraphs.analyses.GraphExplorerInternal;
import org.eclipse.n4js.flowgraphs.analyses.GraphVisitor;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.VariableDeclaration;

/**
 * Analysis to detect uses of {@link IdentifierRef}s that are located in the control flow before their corresponding
 * variables are declared.
 */
public class UsedBeforeDeclaredAnalyser extends GraphVisitor {
	static int branCount = 0;

	/** Constructor */
	public UsedBeforeDeclaredAnalyser() {
		super(Mode.Backward);
	}

	@Override
	protected void visit(ControlFlowElement cfe) {
		if (cfe instanceof VariableDeclaration) {
			super.requestActivation(new CheckVariablePathExplorer((VariableDeclaration) cfe));
		}
	}

	/** @return all {@link IdentifierRef}s that are used before declared */
	public List<IdentifierRef> getUsedButNotDeclaredIdentifierRefs() {
		List<IdentifierRef> idRefs = new LinkedList<>();

		for (GraphExplorerInternal explorer : getActivatedExplorers()) {
			CheckVariablePathExplorer cvExplorer = (CheckVariablePathExplorer) explorer;
			idRefs.addAll(cvExplorer.checkLists);
		}
		return idRefs;
	}

	class CheckVariablePathExplorer extends GraphExplorer {
		final VariableDeclaration vd;
		HashSet<IdentifierRef> checkLists = new HashSet<>();

		CheckVariablePathExplorer(VariableDeclaration vd) {
			this.vd = vd;
		}

		@Override
		protected BranchWalkerInternal firstBranchWalker() {
			return new CheckVariablePathWalker();
		}

		@Override
		protected BranchWalker joinBranches(List<BranchWalker> branchWalkers) {
			return new CheckVariablePathWalker();
		}
	}

	class CheckVariablePathWalker extends BranchWalker {

		CheckVariablePathWalker() {
			branCount++;
		}

		@Override
		protected CheckVariablePathWalker forkPath() {
			return new CheckVariablePathWalker();
		}

		@Override
		protected void visit(ControlFlowElement cfe) {
			if (cfe instanceof IdentifierRef) {
				IdentifierRef ir = (IdentifierRef) cfe;

				CheckVariablePathExplorer explorer = (CheckVariablePathExplorer) getExplorer();
				explorer.checkLists.add(ir);
			}
		}
	}

}
