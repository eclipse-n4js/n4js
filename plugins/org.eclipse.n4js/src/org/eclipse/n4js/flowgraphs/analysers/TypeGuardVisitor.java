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

import org.eclipse.n4js.flowgraphs.FlowEdge;
import org.eclipse.n4js.flowgraphs.analyses.GraphVisitor;
import org.eclipse.n4js.flowgraphs.analyses.PathExplorer;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.UnaryExpression;
import org.eclipse.n4js.n4JS.UnaryOperator;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.typesystem.N4JSTypeSystem;
import org.eclipse.n4js.typesystem.RuleEnvironmentExtensions;

import it.xsemantics.runtime.RuleEnvironment;

/**
 * Checks if all paths to a given a given node have a type constraint that is assignable from the given {@link TypeRef}.
 */
public class TypeGuardVisitor extends GraphVisitor {
	final N4JSTypeSystem ts;
	final TypeRef reqTypeRef;
	final ControlFlowElement cfElem;

	TypeGuardVisitor(N4JSTypeSystem ts, TypeRef reqTypeRef, ControlFlowElement cfElem) {
		super(Mode.Backward);
		this.ts = ts;
		this.reqTypeRef = reqTypeRef;
		this.cfElem = cfElem;
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
		if (cfElem == cfe) {
			super.requestActivation(new TypeGuardExplorer());
		}
	}

	@Override
	protected void visit(ControlFlowElement start, ControlFlowElement end, FlowEdge edge) {
		// nothing to do
	}

	class TypeGuardExplorer extends PathExplorer {

		TypeGuardExplorer() {
			super(Quantor.ForAllPaths);
		}

		@Override
		protected TypeGuardWalker firstPathWalker() {
			return new TypeGuardWalker();
		}

		class TypeGuardWalker extends PathWalker {

			@Override
			protected void initialize() {
				// nothing to do
			}

			@Override
			protected void visit(ControlFlowElement cfe) {
				if (cfe instanceof UnaryExpression) {
					UnaryExpression ue = (UnaryExpression) cfe;
					if (ue.getOp() == UnaryOperator.TYPEOF) {
						Expression typeExpression = ue.getExpression();
						RuleEnvironment G = RuleEnvironmentExtensions.newRuleEnvironment(typeExpression);
						TypeRef tRef = ts.type(G, ue).getFirst();
						if (ts.subtypeSucceeded(G, reqTypeRef, tRef)) {
							super.deactivate();
						}
					}
				}
			}

			@Override
			protected void visit(FlowEdge edge) {
				// nothing to do
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

}
