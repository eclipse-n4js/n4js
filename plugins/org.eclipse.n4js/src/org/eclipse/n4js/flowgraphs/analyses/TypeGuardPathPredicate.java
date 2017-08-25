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
package org.eclipse.n4js.flowgraphs.analyses;

import java.util.Set;

import org.eclipse.n4js.flowgraphs.ControlFlowType;
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
		super(Direction.Backward);
		this.ts = ts;
		this.reqTypeRef = reqTypeRef;
		this.cfElem = cfElem;
	}

	@Override
	protected void init() {
	}

	@Override
	protected void terminate() {
	}

	@Override
	protected void visit(ControlFlowElement cfe) {
		if (cfElem == cfe) {
			super.requestActivation(new TypeGuardActivatedPathPredicate());
		}
	}

	@Override
	protected void visit(ControlFlowElement start, ControlFlowElement end, Set<ControlFlowType> cfTypes) {
		// nothing
	}

	class TypeGuardActivatedPathPredicate extends ActivatedPathPredicate {

		TypeGuardActivatedPathPredicate() {
			super(PredicateType.ForAllPaths);
		}

		@Override
		protected TypeGuardActivePath first() {
			return new TypeGuardActivePath();
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
			protected void visit(ControlFlowElement start, ControlFlowElement end, Set<ControlFlowType> cfTypes) {

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
