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

import java.util.ArrayList;
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
 * This graph visitor prints all paths
 */
public class CheckVariableGraphVisitor extends GraphVisitor {

	/** Constructor */
	public CheckVariableGraphVisitor() {
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
		for (GraphExplorerInternal pathExplorer : getActivatedExplorers()) {
			CheckVariablePathExplorer checkVariablePathExplorer = (CheckVariablePathExplorer) pathExplorer;
			idRefs.addAll(checkVariablePathExplorer.getUsedButNotDeclaredIdentifierRefs());
		}
		return idRefs;
	}

	static class CheckVariablePathExplorer extends GraphExplorer {
		final VariableDeclaration varDecl;
		final List<IdentifierRef> idRefs = new ArrayList<>();

		CheckVariablePathExplorer(VariableDeclaration varDecl) {
			super(Quantor.None);
			this.varDecl = varDecl;
		}

		public List<IdentifierRef> getUsedButNotDeclaredIdentifierRefs() {
			return idRefs;
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

	static class CheckVariablePathWalker extends BranchWalker {

		@Override
		protected CheckVariablePathWalker forkPath() {
			return new CheckVariablePathWalker();
		}

		@Override
		protected void visit(ControlFlowElement cfe) {
			CheckVariablePathExplorer explorer = (CheckVariablePathExplorer) this.getExplorer();
			if (cfe instanceof IdentifierRef && ((IdentifierRef) cfe).getId() == explorer.varDecl) {
				explorer.idRefs.add((IdentifierRef) cfe);
			}
		}

	}

}
