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

import java.util.List;

import org.eclipse.n4js.flowgraphs.analysis.BranchWalker;
import org.eclipse.n4js.flowgraphs.analysis.GraphExplorer;
import org.eclipse.n4js.flowgraphs.analysis.GraphVisitor;
import org.eclipse.n4js.flowgraphs.analysis.TraverseDirection;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.UnaryExpression;
import org.eclipse.n4js.n4JS.UnaryOperator;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.typesystem.N4JSTypeSystem;
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;

/**
 * Checks if all paths to a given a given node have a type constraint that is assignable from the given {@link TypeRef}.
 */
// TODO: not active/tested
public class TypeGuardVisitor extends GraphVisitor {
	final N4JSTypeSystem ts;
	final TypeRef reqTypeRef;
	final ControlFlowElement cfElem;

	TypeGuardVisitor(N4JSTypeSystem ts, TypeRef reqTypeRef, ControlFlowElement cfElem) {
		super(TraverseDirection.Backward);
		this.ts = ts;
		this.reqTypeRef = reqTypeRef;
		this.cfElem = cfElem;
	}

	@Override
	protected void visit(ControlFlowElement cfe) {
		if (cfElem == cfe) {
			super.requestActivation(new TypeGuardExplorer());
		}
	}

	class TypeGuardExplorer extends GraphExplorer {

		TypeGuardExplorer() {
			super(Quantor.ForAllBranches);
		}

		@Override
		protected TypeGuardWalker firstBranchWalker() {
			return new TypeGuardWalker();
		}

		@Override
		protected BranchWalker joinBranches(List<BranchWalker> branchWalkers) {
			return new TypeGuardWalker();
		}

	}

	class TypeGuardWalker extends BranchWalker {

		@Override
		protected void visit(ControlFlowElement cfe) {
			if (cfe instanceof UnaryExpression) {
				UnaryExpression ue = (UnaryExpression) cfe;
				if (ue.getOp() == UnaryOperator.TYPEOF) {
					Expression typeExpression = ue.getExpression();
					RuleEnvironment G = RuleEnvironmentExtensions.newRuleEnvironment(typeExpression);
					TypeRef tRef = ts.type(G, ue).getValue();
					if (ts.subtypeSucceeded(G, reqTypeRef, tRef)) {
						super.deactivate();
					}
				}
			}
		}

		@Override
		protected TypeGuardWalker forkPath() {
			return new TypeGuardWalker();
		}

		@Override
		protected void terminate() {
			fail();
		}

	}
}
