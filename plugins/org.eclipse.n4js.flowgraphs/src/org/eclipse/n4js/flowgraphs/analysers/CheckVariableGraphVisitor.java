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

import org.eclipse.n4js.flowgraphs.FlowEdge;
import org.eclipse.n4js.flowgraphs.analyses.GraphVisitor;
import org.eclipse.n4js.flowgraphs.analyses.PathExplorer;
import org.eclipse.n4js.flowgraphs.analyses.PathExplorerInternal;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.VariableDeclaration;

/**
 * This graph visitor prints all paths
 */
public class CheckVariableGraphVisitor extends GraphVisitor {

	public CheckVariableGraphVisitor() {
		super(Mode.Backward);
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
	protected void terminateMode(Mode curMode, ControlFlowElement curContainer) {
		// nothing to do

	}

	@Override
	protected void terminate() {
		// nothing to do

	}

	@Override
	protected void visit(ControlFlowElement cfe) {
		if (cfe instanceof VariableDeclaration) {
			super.requestActivation(new CheckVariablePathExplorer((VariableDeclaration) cfe));
		}
	}

	@Override
	protected void visit(ControlFlowElement lastCFE, ControlFlowElement currentCFE, FlowEdge edge) {
		// Do nothing
	}

	public List<IdentifierRef> getUsedButNotDeclaredIdentifierRefs() {
		List<IdentifierRef> idRefs = new LinkedList<>();
		for (PathExplorerInternal pathExplorer : getActivatedExplorers()) {
			CheckVariablePathExplorer checkVariablePathExplorer = (CheckVariablePathExplorer) pathExplorer;
			idRefs.addAll(checkVariablePathExplorer.getUsedButNotDeclaredIdentifierRefs());
		}
		return idRefs;
	}

	class CheckVariablePathExplorer extends PathExplorer {
		final VariableDeclaration varDecl;
		final List<IdentifierRef> idRefs = new ArrayList<IdentifierRef>();

		CheckVariablePathExplorer(VariableDeclaration varDecl) {
			super(Quantor.None);
			this.varDecl = varDecl;
		}

		public List<IdentifierRef> getUsedButNotDeclaredIdentifierRefs() {
			return idRefs;
		}

		@Override
		protected PathWalker firstPathWalker() {
			return new CheckVariablePathWalker();
		}

		class CheckVariablePathWalker extends PathWalker {

			@Override
			protected PathWalker forkPath() {
				return new CheckVariablePathWalker();
			}

			@Override
			protected void visit(ControlFlowElement cfe) {
				if (cfe instanceof IdentifierRef && ((IdentifierRef) cfe).getId() == varDecl) {
					idRefs.add((IdentifierRef) cfe);
				}
			}

			@Override
			protected void visit(FlowEdge edge) {
				// nothing to do
			}

			@Override
			protected void terminate() {
				// nothing to do
			}

			@Override
			protected void initialize() {
				// nothing to do

			}

		}

	}

}