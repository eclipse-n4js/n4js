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
package org.eclipse.n4js.flowgraphs;

import org.eclipse.n4js.flowgraphs.analyses.GraphWalker;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.UnaryExpression;
import org.eclipse.n4js.n4JS.UnaryOperator;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.typesystem.N4JSTypeSystem;
import org.eclipse.n4js.typesystem.RuleEnvironmentExtensions;

import it.xsemantics.runtime.RuleEnvironment;

/**
 * Collects all reachable nodes.
 */
public class TypeGuardPathPredicate extends GraphWalker {
	final N4JSTypeSystem ts;
	final TypeRef reqTypeRef;
	final ControlFlowElement cfElem;

	TypeGuardPathPredicate(N4JSTypeSystem ts, TypeRef reqTypeRef, ControlFlowElement cfElem) {
		this.ts = ts;
		this.reqTypeRef = reqTypeRef;
		this.cfElem = cfElem;
	}

	@Override
	protected boolean isBackwards() {
		return true;
	}

	@Override
	protected void visit(ControlFlowElement cfe) {
		if (cfElem == cfe) {
			super.activate(new TypeGuardActivatedPathPredicate());
		}
	}

	@Override
	protected void visit(ControlFlowElement start, ControlFlowElement end, ControlFlowType cfType) {
		// nothing
	}

	class TypeGuardActivatedPathPredicate extends ActivatedPathPredicate {

		@Override
		protected void init() {

		}

		@Override
		protected TypeGuardActivePath first() {
			return new TypeGuardActivePath();
		}

		@Override
		protected void terminate() {

		}

		class TypeGuardActivePath extends ActivePath {

			@Override
			protected void init() {

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
			protected void visit(ControlFlowElement start, ControlFlowElement end, ControlFlowType cfType) {

			}

			@Override
			protected TypeGuardActivePath fork() {
				return new TypeGuardActivePath();
			}

			@Override
			protected void terminate() {
				fail();
			}

		}
	}

}
